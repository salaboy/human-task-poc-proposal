/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbpm.executor.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jboss.seam.transaction.Transactional;
import org.jbpm.executor.api.Command;
import org.jbpm.executor.api.CommandCallback;
import org.jbpm.executor.api.CommandContext;
import org.jbpm.executor.api.ExecutionResults;
import org.jbpm.executor.entities.ErrorInfo;
import org.jbpm.executor.entities.RequestInfo;
import org.jbpm.executor.entities.STATUS;

/**
 *
 * @author salaboy
 */
public class ExecutorRunnable implements Runnable {

    @Inject
    private EntityManager em;
    @Inject
    private BeanManager beanManager;
    
    private final Map<String, Command> commandCache = new HashMap<String, Command>();
    private final Map<String, CommandCallback> callbackCache = new HashMap<String, CommandCallback>();

    @Transactional
    public void run() {
        System.out.println(System.currentTimeMillis() + " >>> Waking Up!!!");
        List<?> resultList = em.createQuery("Select r from RequestInfo as r where r.status ='QUEUED' or r.status = 'RETRYING' ORDER BY r.time DESC").getResultList();
        System.out.println(" XXXXXXXX >>> Number of request pending for execution = " + resultList.size());
        if (resultList.size() > 0) {
            RequestInfo r = null;
            Throwable exception = null;
            try {
                r = (RequestInfo) resultList.get(0);
                r.setStatus(STATUS.RUNNING);
                em.merge(r);
                System.out.println(" >> Processing Request Id: " + r.getId());
                System.out.println(" >> Request Status =" + r.getStatus());
                System.out.println(" >> Command Name to execute = " + r.getCommandName());


                Command cmd = this.findCommand(r.getCommandName());

                CommandContext ctx = null;
                byte[] reqData = r.getRequestData();
                if (reqData != null) {
                    try {
                        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(reqData));
                        ctx = (CommandContext) in.readObject();
                    } catch (IOException e) {
                        ctx = null;
                        e.printStackTrace();
                    }
                }
                ExecutionResults results = cmd.execute(ctx);
                if (ctx != null && ctx.getData("callbacks") != null) {
                    System.out.println(" ### Callback: " + ctx.getData("callbacks"));
                    String[] callbacksArray = ((String) ctx.getData("callbacks")).split(",");;
                    List<String> callbacks = (List<String>) Arrays.asList(callbacksArray);
                    for (String callbackName : callbacks) {
                        CommandCallback handler = this.findCommandCallback(callbackName);
                        handler.onCommandDone(ctx, results);
                    }
                } else {
                    System.out.println(" ### Callbacks: NULL");
                }
                if (results != null) {
                    try {
                        ByteArrayOutputStream bout = new ByteArrayOutputStream();
                        ObjectOutputStream out = new ObjectOutputStream(bout);
                        out.writeObject(results);
                        byte[] respData = bout.toByteArray();
                        r.setResponseData(respData);
                    } catch (IOException e) {
                        r.setResponseData(null);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                exception = e;
            }
            if (exception != null) {
                System.out.println(System.currentTimeMillis() + " XXXXX >>> Before - Error Found!!!" + exception.getMessage());



                ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), ExceptionUtils.getFullStackTrace(exception.fillInStackTrace()));
                errorInfo.setRequestInfo(r);
                r.getErrorInfo().add(errorInfo);
                System.out.println(" >>> Number of Error: " + r.getErrorInfo().size());
                if (r.getRetries() > 0) {
                    r.setStatus(STATUS.RETRYING);
                    r.setRetries(r.getRetries() - 1);
                    r.setExecutions(r.getExecutions() + 1);
                    System.out.println(System.currentTimeMillis() + " >>> Retrying (" + r.getRetries() + ") still available!");
                } else {
                    System.out.println(System.currentTimeMillis() + " >>> Error no retries left!");
                    r.setStatus(STATUS.ERROR);
                    r.setExecutions(r.getExecutions() + 1);
                }

                em.merge(r);


                System.out.println(System.currentTimeMillis() + " >>> XXXXXXX After - Error Found!!!" + exception.getMessage());


            } else {

                r.setStatus(STATUS.DONE);
                em.merge(r);

            }
        }
    }

    private Command findCommand(String name) {

        synchronized (commandCache) {
            if (!commandCache.containsKey(name)) {
                Set<Bean<?>> beans = beanManager.getBeans(name);
                Bean<?> bean = beans.iterator().next();
                commandCache.put(name, (Command) beanManager.getReference(bean, Command.class, beanManager.createCreationalContext(bean)));
            }
        }
        
        return commandCache.get(name);
    }
    
    private CommandCallback findCommandCallback(String name) {

        synchronized (callbackCache) {
            if (!callbackCache.containsKey(name)) {
                Set<Bean<?>> beans = beanManager.getBeans(name);
                Bean<?> bean = beans.iterator().next();
                callbackCache.put(name, (CommandCallback) beanManager.getReference(bean, Command.class, beanManager.createCreationalContext(bean)));
            }
        }
        
        return callbackCache.get(name);
    }
}

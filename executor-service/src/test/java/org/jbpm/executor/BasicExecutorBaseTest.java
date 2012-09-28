/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbpm.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import org.jbpm.executor.api.CommandContext;
import org.jbpm.executor.api.Executor;
import org.jbpm.executor.api.ExecutorQueryService;
import org.jbpm.executor.api.ExecutorRequestAdminService;
import org.jbpm.executor.commands.PrintOutCommand;
import org.jbpm.executor.entities.RequestInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author salaboy
 */
public abstract class BasicExecutorBaseTest {

    @Inject
    Executor executor;
    @Inject
    ExecutorQueryService queryService;
    
    @Inject 
    ExecutorRequestAdminService adminService;

    public static Map<String, Object> cachedEntities = new HashMap<String, Object>();
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        adminService.clearAllRequests();
        executor.destroy();
    }

    @Test
    public void hello() throws InterruptedException {
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setData("businessKey", UUID.randomUUID().toString());

      

        executor.scheduleRequest(PrintOutCommand.class.getCanonicalName(), ctxCMD);

        Thread.sleep(10000);

        List<RequestInfo> inErrorRequests = queryService.getInErrorRequests();
        assertEquals(0, inErrorRequests.size());
        List<RequestInfo> queuedRequests = queryService.getQueuedRequests();
        assertEquals(0, queuedRequests.size());
        List<RequestInfo> executedRequests = queryService.getExecutedRequests();
        assertEquals(1, executedRequests.size());


    }
    
    @Test
    public void hello2() throws InterruptedException {
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setData("businessKey", UUID.randomUUID().toString());
       

        CommandContext commandContext = new CommandContext();
        commandContext.setData("businessKey", UUID.randomUUID().toString());
        cachedEntities.put((String)commandContext.getData("businessKey"), new Long(1));
        String callbacks = SimpleIncrementCallback.class.getCanonicalName();
        commandContext.setData("callbacks", callbacks);
        executor.scheduleRequest(PrintOutCommand.class.getCanonicalName(), commandContext);

        Thread.sleep(10000);

        List<RequestInfo> inErrorRequests = queryService.getInErrorRequests();
        assertEquals(0, inErrorRequests.size());
        List<RequestInfo> queuedRequests = queryService.getQueuedRequests();
        assertEquals(0, queuedRequests.size());
        List<RequestInfo> executedRequests = queryService.getExecutedRequests();
        assertEquals(1, executedRequests.size());

        assertEquals(2, ((Long)cachedEntities.get((String)commandContext.getData("businessKey"))).intValue());
        
        

    }
}

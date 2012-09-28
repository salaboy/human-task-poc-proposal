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
import org.jbpm.executor.entities.ErrorInfo;
import org.jbpm.executor.entities.RequestInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        adminService.clearAllErrors();
        
        executor.destroy();
    }
    
    @Test
    public void correctExcecutionTest() throws InterruptedException {
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setData("businessKey", UUID.randomUUID().toString());

        executor.scheduleRequest("PrintOutCmd", ctxCMD);

        Thread.sleep(10000);

        List<RequestInfo> inErrorRequests = queryService.getInErrorRequests();
        assertEquals(0, inErrorRequests.size());
        List<RequestInfo> queuedRequests = queryService.getQueuedRequests();
        assertEquals(0, queuedRequests.size());
        List<RequestInfo> executedRequests = queryService.getExecutedRequests();
        assertEquals(1, executedRequests.size());


    }
    
    @Test
    public void callbackTest() throws InterruptedException {

        CommandContext commandContext = new CommandContext();
        commandContext.setData("businessKey", UUID.randomUUID().toString());
        cachedEntities.put((String)commandContext.getData("businessKey"), new Long(1));
        
        commandContext.setData("callbacks", "SimpleIncrementCallback");
        executor.scheduleRequest("PrintOutCmd", commandContext);

        Thread.sleep(10000);

        List<RequestInfo> inErrorRequests = queryService.getInErrorRequests();
        assertEquals(0, inErrorRequests.size());
        List<RequestInfo> queuedRequests = queryService.getQueuedRequests();
        assertEquals(0, queuedRequests.size());
        List<RequestInfo> executedRequests = queryService.getExecutedRequests();
        assertEquals(1, executedRequests.size());

        assertEquals(2, ((Long)cachedEntities.get((String)commandContext.getData("businessKey"))).intValue());
        
        

    }
    
    @Test
    public void executorExceptionTest() throws InterruptedException {

        CommandContext commandContext = new CommandContext();
        commandContext.setData("businessKey", UUID.randomUUID().toString());
        cachedEntities.put((String) commandContext.getData("businessKey"), new Long(1));
        
        commandContext.setData("callbacks", "SimpleIncrementCallback");
        commandContext.setData("retries", 0);
        executor.scheduleRequest("ThrowExceptionCmd", commandContext);
        System.out.println(System.currentTimeMillis() + "  >>> Sleeping for 10 secs");
        Thread.sleep(10000);
        
        List<RequestInfo> inErrorRequests = queryService.getInErrorRequests();
        assertEquals(1, inErrorRequests.size());
        System.out.println("Error: " + inErrorRequests.get(0));

        List<ErrorInfo> errors = queryService.getAllErrors();
        System.out.println(" >>> Errors: " + errors);
        assertEquals(1, errors.size());

        
    }

    @Test
    public void defaultRequestRetryTest() throws InterruptedException {
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setData("businessKey", UUID.randomUUID().toString());

        executor.scheduleRequest("ThrowExceptionCmd", ctxCMD);

        Thread.sleep(25000);

       

        List<RequestInfo> inErrorRequests = queryService.getInErrorRequests();
        assertEquals(1, inErrorRequests.size());

        List<ErrorInfo> errors = queryService.getAllErrors();
        System.out.println(" >>> Errors: " + errors);
        // Three retries means 4 executions in total 1(regular) + 3(retries)
        assertEquals(4, errors.size());

    }
    
    
    @Test
    public void cancelRequestTest() throws InterruptedException { 

        //  The executor is on purpose not started to not fight against race condition 
        // with the request cancelations.
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setData("businessKey", UUID.randomUUID().toString());

        Long requestId = executor.scheduleRequest("PrintOutCmd", ctxCMD);
        
        // cancel the task immediately
        executor.cancelRequest(requestId);
        
        List<RequestInfo> cancelledRequests = queryService.getCancelledRequests();
        assertEquals(1, cancelledRequests.size());

    }
    
}

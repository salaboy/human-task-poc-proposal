/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbpm.executor;

import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.jbpm.executor.api.CommandContext;
import org.jbpm.executor.api.Executor;
import org.jbpm.executor.api.ExecutorQueryService;
import org.jbpm.executor.commands.PrintOutCommand;
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
}

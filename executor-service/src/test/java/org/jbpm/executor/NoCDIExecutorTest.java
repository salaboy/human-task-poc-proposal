/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbpm.executor;

import java.util.List;
import java.util.UUID;
import org.jbpm.executor.api.CommandContext;
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
public class NoCDIExecutorTest {
    
    public NoCDIExecutorTest() {
    }

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
        
    }

    @Test
    public void noCDIEnvUsingServiceFacade() throws InterruptedException {

        
        ExecutorServiceEntryPoint executor = ExecutorModule.getInstance().getExecutorServiceEntryPoint();
        
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setData("businessKey", UUID.randomUUID().toString());
        
        executor.scheduleRequest(PrintOutCommand.class.getCanonicalName(), ctxCMD);
        
        Thread.sleep(10000);
        
        List<RequestInfo> executedRequests = executor.getExecutedRequests();
        
        assertEquals(1, executedRequests.size());

    }
}

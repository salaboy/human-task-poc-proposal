/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbpm.executor;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Server;
import org.jbpm.executor.api.CommandContext;
import org.jbpm.executor.api.Executor;
import org.jbpm.executor.api.ExecutorQueryService;
import org.jbpm.executor.commands.PrintOutCommand;
import org.jbpm.executor.entities.RequestInfo;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
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
public class CDIExecutorTest {
    
    public CDIExecutorTest() {
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
    public void hello() throws InterruptedException {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        Executor executor = container.instance().select(Executor.class).get();
        ExecutorQueryService adminService = container.instance().select(ExecutorQueryService.class).get();
        
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setData("businessKey", UUID.randomUUID().toString());
        
        executor.scheduleRequest(PrintOutCommand.class.getCanonicalName(), ctxCMD);
        
        Thread.sleep(10000);
        
        List<RequestInfo> executedRequests = adminService.getExecutedRequests();
        
        assertEquals(1, executedRequests.size());

    }
}

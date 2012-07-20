/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.executor;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Server;
import org.jboss.executor.api.CommandContext;
import org.jboss.executor.api.Executor;
import org.jboss.executor.api.ExecutorQueryService;
import org.jboss.executor.commands.PrintOutCommand;
import org.jboss.executor.entities.RequestInfo;
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
public class SwitchyardCDITests {
    
    public SwitchyardCDITests() {
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

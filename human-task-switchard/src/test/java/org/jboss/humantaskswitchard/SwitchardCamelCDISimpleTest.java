/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.humantaskswitchard;

import org.jboss.human.interactions.model.TaskDef;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.bean.config.model.BeanSwitchYardScanner;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.test.mixins.CDIMixIn;

/**
 *
 * @author salaboy
 */
@SwitchYardTestCaseConfig(
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
mixins = {CDIMixIn.class},
scanners = BeanSwitchYardScanner.class)
@RunWith(SwitchYardRunner.class)
public class SwitchardCamelCDISimpleTest {

    @ServiceOperation("TaskTestService")
    private Invoker newTask;

    public SwitchardCamelCDISimpleTest() {
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
    public void hello() {
        newTask.operation("newTask").sendInOnly(new TaskDef("myTask"));
    }
}

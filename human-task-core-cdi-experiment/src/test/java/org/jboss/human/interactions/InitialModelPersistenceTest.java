/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author salaboy
 */
public class InitialModelPersistenceTest {

    public InitialModelPersistenceTest() {
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

//    @Test
//    public void weldContainerTest() {
//        Weld weld = new Weld();
//        WeldContainer container = weld.initialize();
//
//        Task task = container.instance().select(Task.class).get();
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("human-task-PU");
//        EntityManager em = emf.createEntityManager();
//        
//        em.persist(task);
//        
//        
//        
//        
//    }
    
//    @Test
//    public void weldContainerJBPMMOdelTest() {
//        Weld weld = new Weld();
//        WeldContainer container = weld.initialize();
//        org.jbpm.task.Task task = container.instance().select(org.jbpm.task.Task.class).get();
//       
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("human-task-PU-original");
//        EntityManager em = emf.createEntityManager();
//        
//        em.persist(task);
//        
//        
//    }
}

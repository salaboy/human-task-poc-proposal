/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.internals;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author salaboy
 */
public class TaskDatabaseProducer {
    
   @Produces @TaskDatabase
   static EntityManagerFactory taskEmf = Persistence.createEntityManagerFactory("human-task-PU");


}

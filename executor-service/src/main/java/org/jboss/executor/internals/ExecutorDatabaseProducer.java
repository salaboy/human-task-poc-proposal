/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.executor.internals;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.jboss.solder.core.ExtensionManaged;

/**
 *
 * @author salaboy
 */
public class ExecutorDatabaseProducer {

    @ExtensionManaged
    @Produces
    @PersistenceUnit(unitName = "org.jbpm.executor")
    @ApplicationScoped
    EntityManagerFactory taskEmf;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.lifecycle.listeners;

import org.jboss.human.interactions.internals.annotations.JPA;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.human.interactions.events.BeforeTaskStartedEvent;
import org.jboss.human.interactions.internals.annotations.External;
import org.jboss.human.interactions.model.TaskEvent;
import org.jboss.human.interactions.model.TaskInstance;

/**
 *
 * @author salaboy
 */

@External @JPA @ApplicationScoped
public class JPATaskLifeCycleEventListener implements TaskLifeCycleEventListener{

    @Inject  
    private EntityManager em;
    
    public JPATaskLifeCycleEventListener() {
        
    }

    public void afterTaskStartedEvent(@Observes(notifyObserver= Reception.IF_EXISTS) @BeforeTaskStartedEvent TaskInstance ti) {
        
        em.getTransaction().begin();
        em.persist(new TaskEvent(ti.getId(), TaskEvent.TaskEventType.STARTED, ti.getActualOwner()));
        em.getTransaction().commit();
        
    }
    
}

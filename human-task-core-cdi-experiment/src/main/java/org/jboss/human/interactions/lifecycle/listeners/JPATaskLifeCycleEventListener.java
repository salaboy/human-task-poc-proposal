/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.lifecycle.listeners;

import org.jboss.human.interactions.internals.annotations.Persistent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import org.jboss.human.interactions.events.BeforeTaskStartedEvent;
import org.jboss.human.interactions.internals.annotations.External;
import org.jboss.human.interactions.model.TaskEvent;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.seam.transaction.Transactional;

/**
 *
 * @author salaboy
 */

@Alternative @Singleton
public class JPATaskLifeCycleEventListener implements TaskLifeCycleEventListener{

    @Inject  
    private EntityManager em;
    
    public JPATaskLifeCycleEventListener() {
        
    }
    @Transactional
    public void afterTaskStartedEvent(@Observes(notifyObserver= Reception.IF_EXISTS) @BeforeTaskStartedEvent TaskInstance ti) {
        System.out.println(" XXX After Task Started Event -> "+ti);
        em.persist(new TaskEvent(ti.getId(), TaskEvent.TaskEventType.STARTED, ti.getActualOwner()));
    }
    
}

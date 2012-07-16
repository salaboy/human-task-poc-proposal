/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import org.jboss.human.interactions.events.BeforeTaskCompletedEvent;
import org.jboss.human.interactions.internals.annotations.External;
import org.jboss.human.interactions.model.Operation;
import org.switchyard.component.bean.Reference;

/**
 *
 * @author salaboy
 */

@External @ApplicationScoped
public class TaskLifeCycleOperationEventListener {

    @Inject @Reference
    private TaskInstanceNotification notifier;
    
    public TaskLifeCycleOperationEventListener() {
        
    }
 
    public void afterTaskStartedEvent(@Observes(notifyObserver= Reception.ALWAYS) @BeforeTaskCompletedEvent Operation ti) {
        System.out.println(" XXX Default Log -> "+ti);
        notifier.process(ti);
    }
    
}

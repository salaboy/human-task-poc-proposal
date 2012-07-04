/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.lifecycle.listeners;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import org.jboss.human.interactions.events.BeforeTaskStartedEvent;
import org.jboss.human.interactions.internals.annotations.Internal;
import org.jboss.human.interactions.model.TaskInstance;

/**
 *
 * @author salaboy
 */

@Internal
public class InternalTaskLifeCycleEventListener implements TaskLifeCycleEventListener{

    public InternalTaskLifeCycleEventListener() {
    }

    
    public void afterTaskStartedEvent(@Observes(notifyObserver= Reception.ALWAYS) @BeforeTaskStartedEvent TaskInstance ti) {
        System.out.println(" >>> Task Instance Started: "+ti);
    }
    
}

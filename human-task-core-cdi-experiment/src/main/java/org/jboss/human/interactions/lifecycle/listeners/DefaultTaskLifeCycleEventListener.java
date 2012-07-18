/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.lifecycle.listeners;

import org.jboss.human.interactions.internals.annotations.Log;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;
import org.jboss.human.interactions.events.BeforeTaskStartedEvent;
import org.jboss.human.interactions.internals.annotations.External;
import org.jboss.human.interactions.model.TaskInstance;

/**
 *
 * @author salaboy
 */

@Alternative @Singleton
public class DefaultTaskLifeCycleEventListener implements TaskLifeCycleEventListener{

    public DefaultTaskLifeCycleEventListener() {
        
    }

    public void afterTaskStartedEvent(@Observes(notifyObserver= Reception.IF_EXISTS) @BeforeTaskStartedEvent TaskInstance ti) {
        System.out.println(" XXX Default Log Task Started");
    }
    
}

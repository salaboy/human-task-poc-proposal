/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.lifecycle.listeners;

import org.jboss.human.interactions.model.TaskInstance;

/**
 *
 * @author salaboy
 */

public interface TaskLifeCycleEventListener {
    public void afterTaskStartedEvent(TaskInstance ti);
    
}

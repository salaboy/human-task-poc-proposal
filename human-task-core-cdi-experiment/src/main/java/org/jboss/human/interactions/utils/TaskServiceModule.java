/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.utils;

import org.jboss.human.interactions.api.TaskServiceEntryPoint;
import org.jboss.human.interactions.api.TaskServiceEntryPointImpl;
import org.jboss.human.interactions.lifecycle.listeners.TaskLifeCycleEventListener;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author salaboy
 */
public class TaskServiceModule {
    private static TaskServiceModule instance = new TaskServiceModule();
    private TaskServiceEntryPoint taskService;
    private WeldContainer container;
    public static TaskServiceModule getInstance(){
        return instance;
    }

    public TaskServiceModule() {
        Weld weld = new Weld();
        this.container = weld.initialize();
        
        this.taskService = this.container.instance().select(TaskServiceEntryPointImpl.class).get();
        //Singleton.. that we need to instantiate
        this.container.instance().select(TaskLifeCycleEventListener.class).get(); 
    }

    public TaskServiceEntryPoint getTaskService() {
        return this.taskService;
    }

    public WeldContainer getContainer() {
        return container;
    }
    
    
}

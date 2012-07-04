/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.api;

import javax.inject.Inject;
import org.jboss.human.interactions.internals.annotations.External;
import org.jboss.human.interactions.internals.annotations.JPA;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.lifecycle.listeners.TaskLifeCycleEventListener;

/**
 *
 * @author salaboy
 */

public class TaskServiceEntryPoint {

    @Inject
    @Local
    private TaskDefService taskDefService;
    @Inject
    @Local
    private TaskInstanceService taskInstanceService;
    @Inject
    @Local
    private TaskIdentityService taskIdentityService;
    @Inject
    @Local
    private TaskAdminService taskAdminService;
    @Inject
    @Local
    private TaskQueryService taskQueryService;

    @Inject
    @JPA
    @External
    private TaskLifeCycleEventListener taskListener;
    
    public TaskServiceEntryPoint() {
    }

    public TaskDefService getTaskDefService() {
        return taskDefService;
    }

    public TaskInstanceService getTaskInstanceService() {
        return taskInstanceService;
    }

    public TaskIdentityService getTaskIdentityService() {
        return taskIdentityService;
    }

    public TaskAdminService getTaskAdminService() {
        return taskAdminService;
    }

    public TaskQueryService getTaskQueryService() {
        return taskQueryService;
    }

    public TaskLifeCycleEventListener getTaskListener() {
        return taskListener;
    }
    
    
}

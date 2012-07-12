/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht.services;

import javax.inject.Inject;
import org.jboss.ht.services.annotations.Mock;
import org.jboss.human.interactions.api.TaskInstanceService;
import org.jboss.human.interactions.internals.annotations.Local;
import org.switchyard.component.bean.Service;

/**
 *
 * @author salaboy
 */
@Service(TaskInstanceEndpoint.class)
public class TaskInstanceEndpointBean implements TaskInstanceEndpoint{
    @Inject @Mock
    private TaskInstanceService taskInstanceService;

    public TaskInstanceEndpointBean() {
        
    }
    
    public void activate(TaskUserRequest request){
        System.out.println(" XXXX ");
        taskInstanceService.activate(request.getTaskId(), request.getUserId());
        
    }
    
    public void start(TaskUserRequest request){
        taskInstanceService.start(request.getTaskId(), request.getUserId());
    }

    public void stop(TaskUserRequest request) {
        taskInstanceService.stop(request.getTaskId(), request.getUserId());
    }

    public void complete(TaskUserRequest request) {
        taskInstanceService.complete(request.getTaskId(), request.getUserId(), request.getData());
    }
    
    
    
}

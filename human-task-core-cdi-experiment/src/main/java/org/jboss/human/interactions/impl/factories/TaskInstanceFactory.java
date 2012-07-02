/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl.factories;

import org.jboss.human.interactions.internals.exceptions.IllegalTaskStateException;
import org.jboss.human.interactions.model.Group;
import org.jboss.human.interactions.model.OrganizationalEntity;
import org.jboss.human.interactions.model.Status;
import org.jboss.human.interactions.model.TaskDef;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.human.interactions.model.User;

/**
 *
 * @author salaboy
 */
public class TaskInstanceFactory {
    
    
    
    public static TaskInstance newTaskInstance(TaskDef taskDef){
        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setTaskType(taskDef.getName());
        taskInstance.setStatus(Status.Created);
        initializeTaskInstance(taskDef, taskInstance);
        return taskInstance;
    }
    /**
     * This method contains the logic to initialize a Task using the defined semantic
     *  in the WS-HT specification
     * @param taskInstance 
     */
    public static void initializeTaskInstance(TaskDef taskDef, TaskInstance taskInstance){
        if (taskInstance.getStatus() != Status.Created) {
            throw new IllegalTaskStateException("We can only initialize tasks in the Created Status!");
        }

        Status assignedStatus = null;

        if (taskDef.getPeopleAssignments().getPotentialOwners().size() == 1) {
            // if there is a single potential owner, assign and set status to Reserved
            OrganizationalEntity potentialOwner = taskDef.getPeopleAssignments().getPotentialOwners().get(0);
            // if there is a single potential user owner, assign and set status to Reserved
            if (potentialOwner instanceof User) {
                taskInstance.setActualOwner((User) potentialOwner);

                assignedStatus = Status.Reserved;
            }
            //If there is a group set as potentialOwners, set the status to Ready ??
            if (potentialOwner instanceof Group) {

                assignedStatus = Status.Ready;
            }
        } else if (taskDef.getPeopleAssignments().getPotentialOwners().size() > 1) {
            // multiple potential owners, so set to Ready so one can claim.
            assignedStatus = Status.Ready;
        } else {
            //@TODO we have no potential owners
        }

        if (assignedStatus != null) {
            taskInstance.setStatus(assignedStatus);
        } 

        
    
    }
}

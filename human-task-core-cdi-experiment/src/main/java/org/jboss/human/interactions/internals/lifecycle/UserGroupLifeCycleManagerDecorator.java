/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.internals.lifecycle;

import java.util.List;
import java.util.Map;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.internals.exceptions.TaskException;
import org.jboss.human.interactions.model.Operation;

/**
 *
 * @author salaboy
 */
@Decorator
public abstract class UserGroupLifeCycleManagerDecorator implements LifeCycleManager{
    @Inject @Delegate @Mvel LifeCycleManager manager;
 
    
    public void taskOperation(Operation operation, long taskId, String userId, String targetEntityId, Map<String, Object> data, List<String> groupIds) throws TaskException {
        System.out.println(" XXX Decorating with User Group Decorator");
        //        groupIds = doUserGroupCallbackOperation(userId, groupIds);
//        doCallbackUserOperation(targetEntityId);
//        if (targetEntityId != null) {
//            targetEntity = getEntity(OrganizationalEntity.class, targetEntityId);
//        }
        manager.taskOperation(operation, taskId, userId, targetEntityId, data, groupIds);
    }
    
}

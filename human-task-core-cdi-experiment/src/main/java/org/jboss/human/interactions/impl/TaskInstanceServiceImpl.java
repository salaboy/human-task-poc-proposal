/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.SSLEngineResult.Status;
import javax.persistence.EntityManager;
import org.jboss.human.interactions.api.TaskDefService;
import org.jboss.human.interactions.api.TaskInstanceService;
import org.jboss.human.interactions.impl.factories.TaskInstanceFactory;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.internals.lifecycle.LifeCycleManager;
import org.jboss.human.interactions.internals.lifecycle.Mvel;
import org.jboss.human.interactions.model.FaultData;
import org.jboss.human.interactions.model.Operation;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.human.interactions.model.TaskDef;
import org.jboss.human.interactions.model.TaskSummary;
import org.jboss.seam.transaction.Transactional;
/**
 *
 * @author salaboy
 */

@Local
@Transactional
@Named
public class TaskInstanceServiceImpl implements TaskInstanceService {
    
    @Inject @Local
    private TaskDefService taskDefService;

    @Inject @Mvel
    private LifeCycleManager lifeCycleManager;
    
    @Inject 
    private EntityManager em;
    
    
    public TaskInstanceServiceImpl() {
        
    }

    
    
    public long newTask(String name, Map<String, Object> params) {
        TaskDef taskDef = taskDefService.getTaskDefById(name);
        
        //em.getTransaction().begin();
        TaskInstance task = TaskInstanceFactory.newTaskInstance(taskDef);
        em.persist(task);
        //em.getTransaction().commit();
        
        return task.getId();
        
    }

    public void activate(long taskInstanceId, String userId) {
        lifeCycleManager.taskOperation(Operation.Activate, taskInstanceId, userId, null, null, null);
    }

    public void claim(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void claim(long taskId, String userId, List<String> groupIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void claimNextAvailable(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void claimNextAvailable(String userId, List<String> groupIds, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void complete(long taskInstanceId, String userId, Map<String, Object> data) {
        lifeCycleManager.taskOperation(Operation.Complete, taskInstanceId, userId, null, data, null);
    }

    public void delegate(long taskId, String userId, String targetUserId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteFault(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteOutput(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void exit(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fail(long taskId, String userId, FaultData faultData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void forward(long taskId, String userId, String targetEntityId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void release(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resume(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFault(long taskId, String userId, FaultData fault) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setOutput(long taskId, String userId, Object outputContentData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPriority(long taskId, String userId, int priority) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void skip(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void start(long taskInstanceId, String userId) {
        lifeCycleManager.taskOperation(Operation.Start, taskInstanceId, userId, null, null, null);
    }

    public void stop(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void suspend(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsExcludedOwner(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, List<String> groupIds, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId, List<String> groupIds, String language, int firstResult, int maxResult) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsRecipient(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsTaskInitiator(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsTaskStakeholder(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksOwned(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksOwned(String userId, List<Status> status, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String salaboy, List<Status> status, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatusByGroup(String userId, List<String> groupIds, List<Status> status, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}

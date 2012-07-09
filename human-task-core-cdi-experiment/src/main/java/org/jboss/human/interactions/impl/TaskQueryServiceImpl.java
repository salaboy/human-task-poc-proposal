/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import org.jboss.human.interactions.api.TaskQueryService;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.model.Content;
import org.jboss.human.interactions.model.Status;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.human.interactions.model.TaskSummary;
import org.jboss.seam.transaction.Transactional;


/**
 *
 * @author salaboy
 */
@Local
@Transactional
@Named
public class TaskQueryServiceImpl implements TaskQueryService {
    
    
    @Inject 
    private EntityManager em;
    
    public TaskQueryServiceImpl() {
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
    
    public List<TaskSummary> getTasksOwned(String userId) {
        
        List<TaskSummary> taskOwned = em.createQuery("select"
                + "    new org.jboss.human.interactions.model.TaskSummary(\n"
                + "    t.id,\n"
                + "    t.status,\n"
                + "    t.skipable,\n"
                + "    t.actualOwner,\n"
                + "    t.createdTime)\n"
                + "from\n"
                + "    TaskInstance t \n"
                + "where\n"
                + "    t.actualOwner.id = :userId and\n"
                + "    t.expirationTime is null").setParameter("userId", userId).getResultList();
        
        return taskOwned;
        
    }
    
    public List<TaskSummary> getTasksOwned(String userId, List<Status> status, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(String userId, List<Status> status, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatusByGroup(String userId, List<String> groupIds, List<Status> status, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<TaskSummary> getSubTasksAssignedAsPotentialOwner(long parentId, String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<TaskSummary> getSubTasksByParent(long parentId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public TaskInstance getTaskInstanceById(long taskId) {
        TaskInstance taskInstance = em.find(TaskInstance.class, taskId);
        return taskInstance;
        
    }
    
    public TaskInstance getTaskByWorkItemId(long workItemId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Content getContentById(long contentId) {
        
        Content content = em.find(Content.class, contentId);
        
        return content;
    }
}

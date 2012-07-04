/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jboss.human.interactions.api.TaskQueryService;
import org.jboss.human.interactions.internals.TaskDatabase;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.model.Content;
import org.jboss.human.interactions.model.Status;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.human.interactions.model.TaskSummary;

/**
 *
 * @author salaboy
 */
@Local
public class TaskQueryServiceImpl implements TaskQueryService {
    
    @Inject
    @TaskDatabase    
    private EntityManagerFactory emf;
    
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
        EntityManager em = emf.createEntityManager();
        List<TaskSummary> taskOwned = em.createQuery("select"
                + "    new org.jboss.human.interactions.model.TaskSummary(\n"
                + "    t.id,\n"
                + "    t.status,\n"
                + "    t.skipable,\n"
                + "    t.actualOwner,\n"
                + "    t.createdBy,\n"
                + "    t.createdTime)\n"
                + "from\n"
                + "    TaskInstance t \n"
                + "where\n"
                + "    t.actualOwner.id = :userId and\n"
                + "    t.expirationTime is null").setParameter("userId", userId).getResultList();
        em.close();
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
        EntityManager em = emf.createEntityManager();
        TaskInstance taskInstance = (TaskInstance) em.createQuery("select ti from TaskInstance ti where ti.id = :id").setParameter("id", taskId).getSingleResult();
        em.close();
        return taskInstance;
        
    }
    
    public TaskInstance getTaskByWorkItemId(long workItemId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Content getContentById(long contentId) {
        EntityManager em = emf.createEntityManager();
        Content content = em.find(Content.class, contentId);
        em.close();
        return content;
    }
}

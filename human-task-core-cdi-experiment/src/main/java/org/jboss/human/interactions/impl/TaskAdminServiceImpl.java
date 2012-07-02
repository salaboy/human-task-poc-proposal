/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jboss.human.interactions.api.TaskAdminService;
import org.jboss.human.interactions.internals.TaskDatabase;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.human.interactions.model.TaskSummary;

/**
 *
 * @author salaboy
 */
@Local
public class TaskAdminServiceImpl implements TaskAdminService{

    @Inject @TaskDatabase 
    private EntityManagerFactory emf;

    public TaskAdminServiceImpl() {
    }
    
    
    public List<TaskSummary> getActiveTasks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getActiveTasks(Date since) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getCompletedTasks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getCompletedTasks(Date since) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getCompletedTasksByProcessId(Long processId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int archiveTasks(List<TaskSummary> tasks) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<TaskSummary> getArchivedTasks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int removeTasks(List<TaskSummary> tasks) {
        EntityManager em = emf.createEntityManager();
        int count = 0;
        em.getTransaction().begin();
        for(TaskSummary taskSummary : tasks){
            TaskInstance task = em.find(TaskInstance.class, taskSummary.getTaskId());
            em.remove(task);
            count++;
        }
        em.getTransaction().commit();
        return count;
    }
    
}

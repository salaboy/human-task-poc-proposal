/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.human.interactions.api.TaskAdminService;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.human.interactions.model.TaskSummary;
import org.jboss.seam.transaction.Transactional;

/**
 *
 * @author salaboy
 */
@Local
@Transactional
public class TaskAdminServiceImpl implements TaskAdminService{

    @Inject 
    private EntityManager em;

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
        
        int count = 0;
        
        for(TaskSummary taskSummary : tasks){
            TaskInstance task = em.find(TaskInstance.class, taskSummary.getTaskId());
            em.remove(task);
            count++;
        }
        
        return count;
    }
    
}

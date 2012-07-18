/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.human.interactions.api.TaskEventsService;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.internals.annotations.Persistent;
import org.jboss.human.interactions.model.TaskEvent;
import org.jboss.seam.transaction.Transactional;

/**
 *
 * @author salaboy
 */
@Persistent 
@Local
@Transactional
public class TaskEventsServiceImpl implements TaskEventsService{
    @Inject  
    private EntityManager em;
    
    public List<TaskEvent> getTaskEventsById(long taskId) {
        return em.createQuery("select te from TaskEvent te where te.taskId =:taskId ").setParameter("taskId", taskId).getResultList();
    }

    public void removeTaskEventsById(long taskId) {
        List<TaskEvent> taskEventsById = getTaskEventsById(taskId);
        for(TaskEvent e : taskEventsById){
            em.remove(e);
        }
    }
    
}

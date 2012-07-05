/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.human.interactions.api.TaskDefService;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.model.TaskDef;
import org.jboss.seam.transaction.TransactionPropagation;
import org.jboss.seam.transaction.Transactional;

/**
 *
 * @author salaboy
 */

@Local
@Transactional(TransactionPropagation.REQUIRED)
public class TaskDefServiceImpl implements TaskDefService{
    
    @Inject 
    private EntityManager em;

    public TaskDefServiceImpl() {
        
    }

    @Transactional(TransactionPropagation.REQUIRED)
    public void deployTaskDef(TaskDef def) {
        
       // em.getTransaction().begin();
        em.persist(def);    
       // em.getTransaction().commit();
        
        
    }

    public List<TaskDef> getAllTaskDef(String filter) {
        
        List<TaskDef> resultList = em.createQuery("select td from TaskDef td").getResultList(); 
        return resultList;
    }

    public TaskDef getTaskDefById(String name) {
        //TODO: FIX LOGIC
        
        List<TaskDef> resultList = em.createQuery("select td from TaskDef td where td.name = :name")
                                 .setParameter("name", name)
                                 .getResultList();
        
        if(resultList.size() > 0){
            return resultList.get(0);
        }
        return null;
        
    }

    public void undeployTaskDef(String name) {
        TaskDef taskDef = getTaskDefById(name);
        em.remove(taskDef);    
    }
    
}

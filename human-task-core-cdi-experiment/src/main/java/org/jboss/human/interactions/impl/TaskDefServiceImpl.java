/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jboss.human.interactions.api.TaskDefService;
import org.jboss.human.interactions.internals.TaskDatabase;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.model.TaskDef;

/**
 *
 * @author salaboy
 */

@Local
public class TaskDefServiceImpl implements TaskDefService{
    @Inject @TaskDatabase 
    private EntityManagerFactory emf;

    public TaskDefServiceImpl() {
        
    }

    
    public void deployTaskDef(TaskDef def) {
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(def);    
        em.getTransaction().commit();
        em.close();
        
    }

    public List<TaskDef> getAllTaskDef(String filter) {
        EntityManager em = this.emf.createEntityManager();
        List<TaskDef> resultList = em.createQuery("select td from TaskDef td").getResultList();
        em.close(); 
        return resultList;
    }

    public TaskDef getTaskDefById(String name) {
        //TODO: FIX LOGIC
        EntityManager em = this.emf.createEntityManager();
        List<TaskDef> resultList = em.createQuery("select td from TaskDef td where td.name = :name")
                                 .setParameter("name", name)
                                 .getResultList();
        em.close(); 
        if(resultList.size() > 0){
            return resultList.get(0);
        }
        return null;
        
    }

    public void undeployTaskDef(String name) {
        //TODO: FIX LOGIC
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        TaskDef taskDef = getTaskDefById(name);
        em.remove(em.merge(taskDef));    
        em.getTransaction().commit();
        em.close();
        
    }
    
}

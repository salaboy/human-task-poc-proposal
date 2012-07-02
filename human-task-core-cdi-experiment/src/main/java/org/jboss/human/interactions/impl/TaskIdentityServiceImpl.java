/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jboss.human.interactions.api.TaskIdentityService;
import org.jboss.human.interactions.internals.TaskDatabase;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.model.Group;
import org.jboss.human.interactions.model.User;

/**
 *
 * @author salaboy
 */

@Local
public class TaskIdentityServiceImpl implements TaskIdentityService {

    @Inject @TaskDatabase 
    private EntityManagerFactory emf;

    public TaskIdentityServiceImpl() {
    }
    
    public void addUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void addGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeGroup(String groupId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeUser(String userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(User.class, userId));
        em.getTransaction().commit();
        em.close();
    }

    public List<User> getUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Group> getGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User getUserById(String userId) {
        EntityManager em = emf.createEntityManager();
        return em.find(User.class, userId);
    }

    public Group getGroupById(String groupId) {
        EntityManager em = emf.createEntityManager();
        return em.find(Group.class, groupId);
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.impl;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.human.interactions.api.TaskIdentityService;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.model.Group;
import org.jboss.human.interactions.model.User;
import org.jboss.seam.transaction.TransactionPropagation;
import org.jboss.seam.transaction.Transactional;

/**
 *
 * @author salaboy
 */
@Local
@Transactional(TransactionPropagation.REQUIRED)
public class TaskIdentityServiceImpl implements TaskIdentityService {

    @Inject 
    private EntityManager em;

    public TaskIdentityServiceImpl() {
    }
    
    @Transactional(TransactionPropagation.REQUIRED)
    public void addUser(User user) {
        em.persist(user);
 
    }

    public void addGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeGroup(String groupId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeUser(String userId) {
        User user = em.find(User.class, userId);
        em.remove(user);

    }

    public List<User> getUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Group> getGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User getUserById(String userId) {
        return em.find(User.class, userId);
    }

    public Group getGroupById(String groupId) {
        
        return em.find(Group.class, groupId);
    }
}

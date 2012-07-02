/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.api;

import java.util.List;
import org.jboss.human.interactions.model.Group;
import org.jboss.human.interactions.model.User;

/**
 *
 * @author salaboy
 */
public interface TaskIdentityService {

    public void addUser(User user);

    public void addGroup(Group group);

    public void removeGroup(String groupId);

    public void removeUser(String userId);

    public List<User> getUsers();

    public List<Group> getGroups();

    public User getUserById(String userId);

    public Group getGroupById(String groupId);
}

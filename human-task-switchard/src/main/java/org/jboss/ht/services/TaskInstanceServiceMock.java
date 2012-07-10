/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht.services;

import java.util.List;
import java.util.Map;
import org.jboss.ht.services.annotations.Mock;
import org.jboss.human.interactions.api.TaskInstanceService;
import org.jboss.human.interactions.model.FaultData;

/**
 *
 * @author salaboy
 */
@Mock
public class TaskInstanceServiceMock implements TaskInstanceService{

    public long newTask(String name, Map<String, Object> params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void activate(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void claim(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void claim(long taskId, String userId, List<String> groupIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void claimNextAvailable(String userId, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void claimNextAvailable(String userId, List<String> groupIds, String language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void complete(long taskId, String userId, Map<String, Object> data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delegate(long taskId, String userId, String targetUserId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteFault(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteOutput(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void exit(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fail(long taskId, String userId, FaultData faultData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void forward(long taskId, String userId, String targetEntityId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void release(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resume(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFault(long taskId, String userId, FaultData fault) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setOutput(long taskId, String userId, Object outputContentData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPriority(long taskId, String userId, int priority) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void skip(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void start(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stop(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void suspend(long taskId, String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

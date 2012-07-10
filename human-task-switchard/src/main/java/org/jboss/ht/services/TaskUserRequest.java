/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht.services;

import java.io.Serializable;

/**
 *
 * @author salaboy
 */
public class TaskUserRequest implements Serializable{
    private long taskId;
    private String userId;

    public TaskUserRequest(long taskId, String userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}

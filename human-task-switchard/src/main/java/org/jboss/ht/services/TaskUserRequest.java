/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht.services;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author salaboy
 */
public class TaskUserRequest implements Serializable{
    private long taskId;
    private String userId;
    private Map<String, Object> data;
    
    public TaskUserRequest(long taskId, String userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public TaskUserRequest(long taskId, String userId, Map<String, Object> data) {
        this.taskId = taskId;
        this.userId = userId;
        this.data = data;
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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    
    
}

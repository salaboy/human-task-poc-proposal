/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author salaboy
 */
public class TaskSummary implements Serializable {
    
    
    private Long taskId;
    private Status status;
    private boolean skipable;
    private User actualOwner;
    private Date createdTime;

    public TaskSummary(Long taskId, Status status, boolean skipable, User actualOwner, Date createdTime) {
        this.taskId = taskId;
        this.status = status;
        this.skipable = skipable;
        this.actualOwner = actualOwner;
        
        this.createdTime = createdTime;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

   

    public boolean isSkipable() {
        return skipable;
    }

    public void setSkipable(boolean skipable) {
        this.skipable = skipable;
    }

    public User getActualOwner() {
        return actualOwner;
    }

    public void setActualOwner(User actualOwner) {
        this.actualOwner = actualOwner;
    }

   
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "TaskSummary{" + "taskId=" + taskId + ", status=" + status + ", skipable=" + skipable + ", actualOwner=" + actualOwner + ", createdOn=" + createdTime + '}';
    }
    
    
   
}

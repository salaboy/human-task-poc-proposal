/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represent a TaskParent as defined in the WS-HT Specification
 *  A Task Parent represent the owner of a Task Instance, in other words
 *  the one interested in achieving the task.
 *  
 *  A valid Task Parent could be the Process Engine, more specifically 
 *   a Process Instance, the Rule Engine or a Third Party Application
 *   interested in creating Human Interactions
 *     
 */
@Entity
public class TaskParent implements Serializable{
    @Id
    @GeneratedValue
    private long id;

    public TaskParent() {
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht.services;



/**
 *
 * @author salaboy
 */
public interface TaskInstanceEndpoint{
    public void activate(TaskUserRequest request);
    public void start(TaskUserRequest request);
    public void stop(TaskUserRequest request);
    public void complete(TaskUserRequest request);
}

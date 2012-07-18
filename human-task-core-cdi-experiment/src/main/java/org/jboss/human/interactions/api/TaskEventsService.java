/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.api;

import java.util.List;
import org.jboss.human.interactions.model.TaskEvent;

/**
 *
 * @author salaboy
 */
public interface TaskEventsService {
    List<TaskEvent> getTaskEventsById(long taskId);
    void removeTaskEventsById(long taskId);
}

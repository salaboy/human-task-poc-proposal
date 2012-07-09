/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.api;

import java.util.Date;
import java.util.List;
import javax.inject.Named;
import org.jboss.human.interactions.model.TaskSummary;

/**
 *
 * @author salaboy
 */
public interface TaskAdminService {

    public List<TaskSummary> getActiveTasks();

    public List<TaskSummary> getActiveTasks(Date since);

    public List<TaskSummary> getCompletedTasks();

    public List<TaskSummary> getCompletedTasks(Date since);

    public List<TaskSummary> getCompletedTasksByProcessId(Long processId);

    public int archiveTasks(List<TaskSummary> tasks);

    public List<TaskSummary> getArchivedTasks();

    public int removeTasks(List<TaskSummary> tasks);
}

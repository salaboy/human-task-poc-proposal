/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.internals.lifecycle;

import java.util.List;
import java.util.Map;
import org.jboss.human.interactions.internals.exceptions.TaskException;
import org.jboss.human.interactions.model.Operation;

/**
 *
 * @author salaboy
 */
public interface LifecycleManager {
    public void taskOperation(final Operation operation, final long taskId, final String userId,
                              final String targetEntityId, final Map<String, Object> data,
                              List<String> groupIds) throws TaskException;
}

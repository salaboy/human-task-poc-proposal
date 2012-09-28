
package org.jbpm.executor.api;


/**
 *
 * @author salaboy
 */
public interface Executor extends Service{
    public Long scheduleRequest(String commandName, CommandContext ctx);
    public void cancelRequest(Long requestId);
    
}

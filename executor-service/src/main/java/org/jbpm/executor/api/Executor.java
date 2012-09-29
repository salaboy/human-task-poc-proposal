package org.jbpm.executor.api;

/**
 *
 * @author salaboy
 */
public interface Executor extends Service {

    public Long scheduleRequest(String commandName, CommandContext ctx);

    public void cancelRequest(Long requestId);

    public int getWaitTime();

    public void setWaitTime(int waitTime);

    public int getDefaultNroOfRetries();

    public void setDefaultNroOfRetries(int defaultNroOfRetries);

    public int getNroOfThreads();

    public void setNroOfThreads(int nroOfThreads);
}

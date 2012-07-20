/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.executor.api;

import java.util.List;
import org.jboss.executor.entities.ErrorInfo;
import org.jboss.executor.entities.RequestInfo;

/**
 *
 * @author salaboy
 */
public interface ExecutorQueryService {
    List<RequestInfo> getQueuedRequests();
    List<RequestInfo> getExecutedRequests();
    List<RequestInfo> getInErrorRequests();
    List<RequestInfo> getCancelledRequests();
    List<ErrorInfo> getAllErrors(); 
    List<RequestInfo> getAllRequests(); 
}

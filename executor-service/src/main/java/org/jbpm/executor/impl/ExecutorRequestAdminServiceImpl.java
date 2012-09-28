/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbpm.executor.impl;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jbpm.executor.api.ExecutorRequestAdminService;
import org.jbpm.executor.entities.RequestInfo;

/**
 *
 * @author salaboy
 */
public class ExecutorRequestAdminServiceImpl implements ExecutorRequestAdminService{
    @Inject
    private EntityManager em;
    public int clearAllRequests() {
        List<RequestInfo> requests = em.createQuery("select r from RequestInfo r").getResultList();
        int count = 0;
        for (RequestInfo r : requests) {
            em.remove(r);
            count++;
        }
        return count;
    }
    
}

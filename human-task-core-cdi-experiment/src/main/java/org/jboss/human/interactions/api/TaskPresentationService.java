/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.api;

import java.util.List;
import org.jboss.human.interactions.model.PresentationElement;

/**
 *
 * @author salaboy
 */
public interface TaskPresentationService {
    
    long addPresentationElement(long taskId, PresentationElement element);
    
    void removePresentationElement(long taskId, long elementId);
    
    List<PresentationElement> getPresentationElements(long taskId);
}

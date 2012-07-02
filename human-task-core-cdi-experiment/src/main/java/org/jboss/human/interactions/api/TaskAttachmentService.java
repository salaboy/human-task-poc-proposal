/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.api;

import java.util.List;
import org.jboss.human.interactions.model.Attachment;

/**
 *
 * @author salaboy
 */
public interface TaskAttachmentService {

    long addAttachment(long taskId, Attachment attachment);

    void deleteAttachment(long taskId, long attachmentId);
    
    List<Attachment> getAttachments(long taskId);
    
    Attachment getAttachmentById(long attachId);
}

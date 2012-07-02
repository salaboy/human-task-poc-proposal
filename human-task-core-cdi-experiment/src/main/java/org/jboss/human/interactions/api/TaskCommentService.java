/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.api;

import java.util.List;
import org.jboss.human.interactions.model.Comment;

/**
 *
 * @author salaboy
 */
public interface TaskCommentService {

    long addComment(long taskId, Comment comment);

    void deleteComment(long taskId, long commentId);

    List<Comment> getComments(long taskId);

    Comment getCommentById(long commentId);
}

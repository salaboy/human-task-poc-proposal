/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jboss.human.interactions.api.TaskAdminService;
import org.jboss.human.interactions.api.TaskDefService;
import org.jboss.human.interactions.api.TaskIdentityService;
import org.jboss.human.interactions.api.TaskInstanceService;
import org.jboss.human.interactions.api.TaskQueryService;
import org.jboss.human.interactions.api.TaskServiceEntryPoint;
import org.jboss.human.interactions.lifecycle.listeners.TaskLifeCycleEventListener;
import org.jboss.human.interactions.model.OrganizationalEntity;
import org.jboss.human.interactions.model.PeopleAssignments;
import org.jboss.human.interactions.model.TaskDef;
import org.jboss.human.interactions.model.TaskSummary;
import org.jboss.human.interactions.model.User;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author salaboy
 */
public class NewAPITest {

    public NewAPITest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void newApiTest() {
        
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        
        
        
        TaskServiceEntryPoint taskServiceEntryPoint = container.instance().select(TaskServiceEntryPoint.class).get();
        TaskLifeCycleEventListener taskListener = taskServiceEntryPoint.getTaskListener();
        
        TaskIdentityService taskIdentityService = taskServiceEntryPoint.getTaskIdentityService();
        taskIdentityService.addUser(new User("salaboy"));
        
        User salaboy = taskIdentityService.getUserById("salaboy");
        assertNotNull(salaboy);
        
        
        TaskDefService taskDefService = taskServiceEntryPoint.getTaskDefService();

        // We can automatically create TaskDef based on the BPMN2 file
        //  We can enrich those tasks with the form builder
        
        TaskDef taskDef = new TaskDef("myTaskDef");
        PeopleAssignments peopleAssignments = new PeopleAssignments();
        List<OrganizationalEntity> potentialOwners = new ArrayList<OrganizationalEntity>();
        potentialOwners.add(salaboy);
        peopleAssignments.setPotentialOwners(potentialOwners);
        taskDef.setPeopleAssignments(peopleAssignments);
        //From the spec: register, port == taskdefid 
        taskDefService.deployTaskDef(taskDef);
        // list
        taskDefService.getAllTaskDef("*");
        // getById
        taskDefService.getTaskDefById("myTaskDef");
        
//        
//        
//        // Lifecycle and query methods for task instances only!!!! 
        TaskInstanceService taskInstanceService = taskServiceEntryPoint.getTaskInstanceService();
        
        Map<String, Object> params = new HashMap<String, Object>();
        
        long taskId = taskInstanceService.newTask("myTaskDef", params);
        
        taskInstanceService.start(taskId, "salaboy");
        Map<String, Object> output = new HashMap<String, Object>();
        output.put("key1", "value1");
        output.put("key2", 2);
        taskInstanceService.complete(taskId, "salaboy", output);
        
        TaskQueryService taskQueryService = taskServiceEntryPoint.getTaskQueryService();
        
        TaskAdminService taskAdminService = taskServiceEntryPoint.getTaskAdminService();
        
        List<TaskSummary> tasksOwned = taskQueryService.getTasksOwned("salaboy");
        
        assertEquals(1, tasksOwned.size());
        
        long outputId = taskQueryService.getTaskInstanceById(taskId).getOutputId();
        assertNotNull(outputId);
        
        assertNotNull(taskQueryService.getContentById(outputId));
        
        //Clean up
        taskAdminService.removeTasks(tasksOwned);
//        //Clean up
        taskIdentityService.removeUser("salaboy");
        
        // unregister
        taskDefService.undeployTaskDef("myTaskDef");
        
        // Granular services will help us to have fine grained control over the configurations and different implementations
        //  for a CMR integration this is vital
//        TaskAttachmentService taskAttachmentService = container.instance().select(TaskAttachmentService.class).get();
//        
//        long attachmentId = taskAttachmentService.addAttachment(taskId, new Attachment());
//        List<Attachment> attachs = taskAttachmentService.getAttachments(attachmentId);
//        
//        TaskCommentService taskCommentService = container.instance().select(TaskCommentService.class).get();
//        
//        long commentId = taskCommentService.addComment(taskId, new Comment());
//        List<Comment> comments = taskCommentService.getComments(taskId);
//        
//        taskCommentService.deleteComment(taskId, commentId);
//        
//        
//        TaskPresentationService taskPresentationService = container.instance().select(TaskPresentationService.class).get();
//        
//        taskPresentationService.addPresentationElement(taskId, new PresentationElement());
//        
//        //Internally it will use taskDef.getTaskDef(id).getPresentationElements()
//        List<PresentationElement> presentationElements = taskPresentationService.getPresentationElements(taskId);
//        
        
        
        
        
        
        
    }
}

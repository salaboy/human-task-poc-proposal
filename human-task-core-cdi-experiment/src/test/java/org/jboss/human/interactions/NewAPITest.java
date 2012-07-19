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
import org.jboss.human.interactions.api.TaskEventsService;
import org.jboss.human.interactions.api.TaskIdentityService;
import org.jboss.human.interactions.api.TaskInstanceService;
import org.jboss.human.interactions.api.TaskQueryService;
import org.jboss.human.interactions.api.TaskServiceEntryPoint;
import org.jboss.human.interactions.api.TaskServiceEntryPointImpl;
import org.jboss.human.interactions.impl.TaskAdminServiceImpl;
import org.jboss.human.interactions.impl.TaskDefServiceImpl;
import org.jboss.human.interactions.impl.TaskEventsServiceImpl;
import org.jboss.human.interactions.impl.TaskIdentityServiceImpl;
import org.jboss.human.interactions.impl.TaskInstanceServiceImpl;
import org.jboss.human.interactions.impl.TaskQueryServiceImpl;
import org.jboss.human.interactions.internals.lifecycle.LifeCycleManager;
import org.jboss.human.interactions.internals.lifecycle.MVELLifeCycleManager;
import org.jboss.human.interactions.lifecycle.listeners.InternalTaskLifeCycleEventListener;
import org.jboss.human.interactions.lifecycle.listeners.JPATaskLifeCycleEventListener;
import org.jboss.human.interactions.lifecycle.listeners.TaskLifeCycleEventListener;
import org.jboss.human.interactions.model.OrganizationalEntity;
import org.jboss.human.interactions.model.PeopleAssignments;
import org.jboss.human.interactions.model.TaskDef;
import org.jboss.human.interactions.model.TaskEvent;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.human.interactions.model.TaskSummary;
import org.jboss.human.interactions.model.User;
import org.jboss.human.interactions.utils.TaskServiceModule;
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
    public void newApiGetDifferentServicesTest() {
        
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        
        TaskServiceEntryPoint taskService = container.instance().select(TaskServiceEntryPointImpl.class).get();
        
        TaskLifeCycleEventListener taskListener = container.instance().select(TaskLifeCycleEventListener.class).get(); 
        
        assertNotNull(taskListener);
        assertTrue(taskListener instanceof JPATaskLifeCycleEventListener);

        TaskEventsService eventsService = taskService.getTaskEventsService();
        
        TaskIdentityService identityService = taskService.getTaskIdentityService();
        identityService.addUser(new User("salaboy"));
        
        User salaboy = identityService.getUserById("salaboy");
        assertNotNull(salaboy);
        
        
        TaskDefService defService = taskService.getTaskDefService();
        TaskDef taskDef = createSimpleTaskDef("myTaskDef", salaboy);
       
        //From the spec: register, port == taskdefid 
        defService.deployTaskDef(taskDef);
        
        // getById
        TaskDef taskDefById = defService.getTaskDefById("myTaskDef");
        assertNotNull(taskDefById);
        
        // list
        List<TaskDef> allTaskDef = defService.getAllTaskDef("*");
        assertEquals(1, allTaskDef.size());
        
//        
//        // Lifecycle and query methods for task instances only!!!! 
        TaskInstanceService taskInstanceService = taskService.getTaskInstanceService();
        
        Map<String, Object> params = new HashMap<String, Object>();
        
        long taskId = taskInstanceService.newTask("myTaskDef", params);
        
        taskInstanceService.start(taskId, "salaboy");
        List<TaskEvent> taskEventsById = eventsService.getTaskEventsById(taskId);
        assertEquals(1, taskEventsById.size());
        
        Map<String, Object> output = new HashMap<String, Object>();
        output.put("key1", "value1");
        output.put("key2", 2);
        taskInstanceService.complete(taskId, "salaboy", output);
        
        TaskQueryService queryService = taskService.getTaskQueryService();
        
        TaskAdminService adminService = taskService.getTaskAdminService();
        
        List<TaskSummary> tasksOwned = queryService.getTasksOwned("salaboy");
        
        assertEquals(1, tasksOwned.size());
        
        long outputId = queryService.getTaskInstanceById(taskId).getOutputId();
        assertNotNull(outputId);
        
        assertNotNull(queryService.getContentById(outputId));
        
        
        
        //Clean up tasks
        adminService.removeTasks(tasksOwned);
//      
        //Clean up events
        eventsService.removeTaskEventsById(taskId);
        
        //Clean up identity
        identityService.removeUser("salaboy");
     
        // unregister
        defService.undeployTaskDef("myTaskDef");
        
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
        weld.shutdown();

    }
    
    @Test
    public void newApiGetMainEntryPointTest() {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        
        TaskServiceEntryPoint taskService = container.instance().select(TaskServiceEntryPointImpl.class).get();
        //Singleton.. that we need to instantiate
        TaskLifeCycleEventListener taskListener = container.instance().select(TaskLifeCycleEventListener.class).get(); 
        
        taskService.addUser(new User("salaboy"));
        
        User salaboy = taskService.getUserById("salaboy");
        
        TaskDef taskDef = createSimpleTaskDef("myTaskDef", salaboy);
       
        //From the spec: register, port == taskdefid 
        taskService.deployTaskDef(taskDef);
        
        Map<String, Object> params = new HashMap<String, Object>();
        long taskId = taskService.newTask("myTaskDef", params);
        
        List<TaskSummary> tasksOwned = taskService.getTasksOwned("salaboy");
        
        taskService.start(taskId, "salaboy");
        
        List<TaskEvent> taskEventsById = taskService.getTaskEventsById(taskId);
        assertEquals(1, taskEventsById.size());
        
        taskService.complete(taskId, "salaboy", null);
        
        
    }
    
    @Test
    public void newApiGetMainEntryPointHidingTaskDefTest() {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        
        TaskServiceEntryPoint taskService = container.instance().select(TaskServiceEntryPointImpl.class).get();
        //Singleton.. that we need to instantiate
        container.instance().select(TaskLifeCycleEventListener.class).get(); 
        
        taskService.addUser(new User("salaboy"));
        
        User salaboy = taskService.getUserById("salaboy");
        // We can have a BPMN2 User Task Parser to create a task def, 
        // like the one inside the WorkItemHandlers now
        TaskDef def = createSimpleTaskDef("myTaskDef", salaboy);
        
        long taskId = taskService.newTask(def, null);
        TaskInstance taskInstanceById = taskService.getTaskInstanceById(taskId);
        
        assertNotNull(taskInstanceById);
        
        
        
    }
    
    @Test
    public void noWeld() {
        // Here we lost and we will be responsible for providing the following features :
        //  - Managed and Shared Persistence Manager
        //  - The event model (fire/observe)
        //  - Transactional model
        TaskServiceEntryPoint taskService = new TaskServiceEntryPointImpl(); 
        taskService.setTaskDefService(new TaskDefServiceImpl());
        taskService.setTaskAdminService(new TaskAdminServiceImpl());
        taskService.setTaskEventsService(new TaskEventsServiceImpl());
        taskService.setTaskIdentityService(new TaskIdentityServiceImpl());
        taskService.setTaskQueryService(new TaskQueryServiceImpl());
        LifeCycleManager lifeCycleManager = new MVELLifeCycleManager(taskService.getTaskDefService(), 
                                                                    taskService.getTaskQueryService(), 
                                                                    taskService.getTaskIdentityService(),
                                                                    new InternalTaskLifeCycleEventListener());
        
        taskService.setTaskInstanceService(
                new TaskInstanceServiceImpl(taskService.getTaskDefService(),
                                            lifeCycleManager));
        
    }
    @Test
    public void hidingWeld(){
        // We hide weld and use it internally, there is no need for the user to know how to use
        //   weld or how it works
        TaskServiceEntryPoint taskService = TaskServiceModule.getInstance().getTaskService();
        //If the user wants to get access to the container to fire events or integrate with 
        // it's own beans the TaskServiceModule they can:
        WeldContainer container = TaskServiceModule.getInstance().getContainer();
        assertNotNull(container);
        
        taskService.addUser(new User("salaboy"));
        
        User salaboy = taskService.getUserById("salaboy");
        // We can have a BPMN2 User Task Parser to create a task def, 
        // like the one inside the WorkItemHandlers now
        TaskDef def = createSimpleTaskDef("myTaskDef", salaboy);
        
        long taskId = taskService.newTask(def, null);
        TaskInstance taskInstanceById = taskService.getTaskInstanceById(taskId);
        
        assertNotNull(taskInstanceById);
    }
    
    @Test
    public void spring() {
        
    }
  
    private TaskDef createSimpleTaskDef(String taskDefName, User salaboy) {
        // We can automatically create TaskDef based on the BPMN2 file
        //  We can enrich those tasks with the form builder
        TaskDef taskDef = new TaskDef(taskDefName);
        PeopleAssignments peopleAssignments = new PeopleAssignments();
        List<OrganizationalEntity> potentialOwners = new ArrayList<OrganizationalEntity>();
        potentialOwners.add(salaboy);
        peopleAssignments.setPotentialOwners(potentialOwners);
        taskDef.setPeopleAssignments(peopleAssignments);
        return taskDef;
    }
    
}

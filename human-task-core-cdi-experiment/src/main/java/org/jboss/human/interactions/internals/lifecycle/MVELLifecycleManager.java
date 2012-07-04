/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.human.interactions.internals.lifecycle;

import org.jboss.human.interactions.internals.annotations.Internal;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jboss.human.interactions.api.TaskDefService;
import org.jboss.human.interactions.api.TaskIdentityService;
import org.mvel2.MVEL;
import org.mvel2.ParserConfiguration;
import org.mvel2.ParserContext;
import org.jboss.human.interactions.api.TaskQueryService;
import org.jboss.human.interactions.events.AfterTaskCompletedEvent;
import org.jboss.human.interactions.events.AfterTaskStartedEvent;
import org.jboss.human.interactions.events.BeforeTaskClaimedEvent;
import org.jboss.human.interactions.events.BeforeTaskCompletedEvent;
import org.jboss.human.interactions.events.BeforeTaskFailedEvent;
import org.jboss.human.interactions.events.BeforeTaskSkippedEvent;
import org.jboss.human.interactions.internals.TaskDatabase;
import org.jboss.human.interactions.internals.annotations.Local;
import org.jboss.human.interactions.internals.exceptions.PermissionDeniedException;
import org.jboss.human.interactions.internals.exceptions.TaskException;
import org.jboss.human.interactions.lifecycle.listeners.TaskLifeCycleEventListener;
import org.jboss.human.interactions.model.Content;
import org.jboss.human.interactions.model.Group;
import org.jboss.human.interactions.model.Operation;
import org.jboss.human.interactions.model.OrganizationalEntity;
import org.jboss.human.interactions.model.Status;
import org.jboss.human.interactions.model.TaskDef;
import org.jboss.human.interactions.model.TaskInstance;
import org.jboss.human.interactions.model.User;

/**
 *
 * @author salaboy
 */
@Mvel
public class MVELLifecycleManager implements LifecycleManager {
//    private @Inject @Logger Log logger;
    @Inject
    private @TaskDatabase EntityManagerFactory emf;
    private TaskDefService taskDefService;
    private TaskQueryService taskQueryService;
    private TaskIdentityService taskIdentityService;
    private Map<Operation, List<OperationCommand>> operations;
    @Inject
    private Event<TaskInstance> taskInstanceEvents;
    private @Inject
    @Internal
    TaskLifeCycleEventListener eventListener;

    @Inject
    public MVELLifecycleManager(@Local TaskDefService taskDefService,
            @Local TaskQueryService taskQueryService,
            @Local TaskIdentityService taskIdentityService) {
        this.taskDefService = taskDefService;
        this.taskQueryService = taskQueryService;
        this.taskIdentityService = taskIdentityService;
    }

    void evalCommand(final Operation operation, final List<OperationCommand> commands, final TaskInstance task,
            final User user, final OrganizationalEntity targetEntity,
            List<String> groupIds) throws PermissionDeniedException {

        boolean statusMatched = false;
        Status previousStatus = task.getStatus();
        for (OperationCommand command : commands) {
            // first find out if we have a matching status
            if (command.getStatus() != null) {
                for (Status status : command.getStatus()) {
                    if (task.getStatus() == status) {
                        statusMatched = true;
                        // next find out if the user can execute this doOperation
                        if (!isAllowed(command, task, user, groupIds)) {
                            String errorMessage = "User '" + user + "' does not have permissions to execution operation '" + operation + "' on task id " + task.getId();

                            throw new PermissionDeniedException(errorMessage);
                        }

                        commands(command, task, user, targetEntity);
                    } else {
                        System.out.println("No match on status for task " + task.getId() + ": status " + task.getStatus() + " != " + status);
                    }
                }
            }

            if (command.getPreviousStatus() != null) {
                for (Status status : command.getPreviousStatus()) {
                    if (previousStatus == status) {
                        statusMatched = true;

                        // next find out if the user can execute this doOperation
                        if (!isAllowed(command, task, user, groupIds)) {
                            String errorMessage = "User '" + user + "' does not have permissions to execution operation '" + operation + "' on task id " + task.getId();
                            throw new PermissionDeniedException(errorMessage);
                        }

                        commands(command, task, user, targetEntity);
                    } else {
                        System.out.println("No match on previous status for task " + task.getId() + ": status " + task.getStatus() + " != " + status);
                    }
                }
            }
        }
        if (!statusMatched) {
            String errorMessage = "User '" + user + "' was unable to execution operation '" + operation + "' on task id " + task.getId() + " due to a no 'current status' match";
            throw new PermissionDeniedException(errorMessage);
        }
    }

    private boolean isAllowed(final OperationCommand command, final TaskInstance task, final User user,
            List<String> groupIds) {
        TaskDef taskDef = taskDefService.getTaskDefById(task.getTaskType());
        boolean operationAllowed = false;
        for (Allowed allowed : command.getAllowed()) {
            if (operationAllowed) {
                break;
            }
            switch (allowed) {
                case Owner: {
                    operationAllowed = (task.getActualOwner() != null && task.getActualOwner().equals(user));
                    break;
                }
                case Initiator: {
                    operationAllowed = (task.getCreatedBy() != null
                            && (task.getCreatedBy().equals(user))
                            || (groupIds != null && groupIds.contains(task.getCreatedBy().getId())));
                    break;
                }
                case PotentialOwner: {
                    operationAllowed = isAllowed(user, groupIds, taskDef.getPeopleAssignments().getPotentialOwners());
                    break;
                }
                case BusinessAdministrator: {
                    operationAllowed = isAllowed(user, groupIds, taskDef.getPeopleAssignments().getBusinessAdministrators());
                    break;
                }
                case Anyone: {
                    operationAllowed = true;
                    break;
                }
            }
        }

        if (operationAllowed && command.isUserIsExplicitPotentialOwner()) {
            // if user has rights to execute the command, make sure user is explicitly specified (not as a group)
            operationAllowed = taskDef.getPeopleAssignments().getPotentialOwners().contains(user);
        }

        if (operationAllowed && command.isSkipable()) {
            operationAllowed = task.isSkipable();
        }

        return operationAllowed;
    }

    private boolean isAllowed(final User user, final List<String> groupIds, final List<OrganizationalEntity> entities) {
        // for now just do a contains, I'll figure out group membership later.
        for (OrganizationalEntity entity : entities) {
            if (entity instanceof User && entity.equals(user)) {
                return true;
            }
            if (entity instanceof Group && groupIds != null && groupIds.contains(entity.getId())) {
                return true;
            }
        }
        return false;
    }

    private void commands(final OperationCommand command, final TaskInstance task, final User user,
            final OrganizationalEntity targetEntity) {

        TaskDef taskDef = taskDefService.getTaskDefById(task.getTaskType());
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        
        
        if (command.getNewStatus() != null) {
            task.setStatus(command.getNewStatus());
        } else if (command.isSetToPreviousStatus()) {
            // TODO: is this to go back into the previous status? why?
            //task.setStatus(task.getPreviousStatus());
        }

        if (command.isAddTargetEntityToPotentialOwners() && !taskDef.getPeopleAssignments().getPotentialOwners().contains(targetEntity)) {
            taskDef.getPeopleAssignments().getPotentialOwners().add(targetEntity);
        }

        if (command.isRemoveUserFromPotentialOwners()) {
            taskDef.getPeopleAssignments().getPotentialOwners().remove(user);
        }

        if (command.isSetNewOwnerToUser()) {
            task.setActualOwner(user);
        }

        if (command.isSetNewOwnerToNull()) {
            task.setActualOwner(null);
        }

        if (command.getExec() != null) {
            switch (command.getExec()) {
                case Claim: {
                    task.setActualOwner((User) targetEntity);
                    // Task was reserved so owner should get icals
//                    SendIcal.getInstance().sendIcalForTask(task, service.getUserinfo());
//
//                    // trigger event support
//                    service.getEventSupport().fireTaskClaimed(task.getId(),
//                            task.getTaskData().getActualOwner().getId());
                    break;
                }
            }
        }
        em.merge(task);
        em.getTransaction().commit();
        em.close();
    }

    public void taskOperation(final Operation operation, final long taskId, final String userId,
            final String targetEntityId, final Map<String, Object> data,
            List<String> groupIds) throws TaskException {
        OrganizationalEntity targetEntity = null;

//        groupIds = doUserGroupCallbackOperation(userId, groupIds);
//        doCallbackUserOperation(targetEntityId);
//        if (targetEntityId != null) {
//            targetEntity = getEntity(OrganizationalEntity.class, targetEntityId);
//        }

        TaskInstance task = taskQueryService.getTaskInstanceById(taskId);
        User user = taskIdentityService.getUserById(userId);

//        boolean transactionOwner = false;
        try {
            final List<OperationCommand> commands = operations.get(operation);

            // transactionOwner = tpm.beginTransaction();
            
            
            
            switch (operation) {
                case Claim: {
//                    taskClaimOperation(task);

                    taskInstanceEvents.select(new AnnotationLiteral<BeforeTaskClaimedEvent>() {
                    }).fire(task);
                    break;
                }
                case Complete: {
//                    taskCompleteOperation(task, data);
                    
                    
                    taskInstanceEvents.select(new AnnotationLiteral<BeforeTaskCompletedEvent>() {
                    }).fire(task);
                    break;
                }
                case Fail: {
//                    taskFailOperation(task, data);
                    taskInstanceEvents.select(new AnnotationLiteral<BeforeTaskFailedEvent>() {
                    }).fire(task);
                    break;
                }
                case Skip: {
//                    taskSkipOperation(task, userId);
                    taskInstanceEvents.select(new AnnotationLiteral<BeforeTaskSkippedEvent>() {
                    }).fire(task);
                    break;
                }
                case Remove: {
//                	taskRemoveOperation(task, user);
                    break;
                }
                case Register: {
//                	taskRegisterOperation(task, user);
                    break;
                }
            }

            evalCommand(operation, commands, task, user, targetEntity, groupIds);

            switch (operation) {
                case Start: {
                    taskInstanceEvents.select(new AnnotationLiteral<AfterTaskStartedEvent>() {
                    }).fire(task);
                    break;
                }
                case Forward: {

                    break;
                }
                case Release: {

                    break;
                }
                case Stop: {

                    break;
                }
                case Claim: {

                    break;
                }
                case Complete: {

                    if (data != null) {
                        
                        
                        
                        Content content = new Content();
                        content.setContent(data.toString().getBytes());
                        EntityManager em = emf.createEntityManager();
                        em.getTransaction().begin();
                        em.persist(content);
                        em.getTransaction().commit();
                        
                        
                        em.getTransaction().begin();
                        task = em.find(TaskInstance.class, task.getId());
                        
                        task.setHasOutput(true);
                        task.setOutputId(content.getId());
                        em.getTransaction().commit();
                        em.close();
                    }
                    
                    taskInstanceEvents.select(new AnnotationLiteral<AfterTaskCompletedEvent>() {
                    }).fire(task);
                    break;
                }
                case Fail: {
//                postTaskFailOperation(task);
                    break;
                }
                case Skip: {
//                postTaskSkipOperation(task, userId);
                    break;
                }
                case Exit: {
//                postTaskExitOperation(task, userId);
                    break;
                }
            }

           
//            tpm.endTransaction(transactionOwner);

        } catch (RuntimeException re) {

            // We may not be the tx owner -- but something has gone wrong.
            // ..which is why we make ourselves owner, and roll the tx back. 
//            boolean takeOverTransaction = true;
            //tpm.rollBackTransaction(takeOverTransaction);

//            doOperationInTransaction(new TransactedOperation() {
//                public void doOperation() {
//                    task.getTaskData().setStatus(Status.Error);
//                }
//            });

            throw re;
        }


    }

    @PostConstruct
    public void initMVELOperations() {

        Map<String, Object> vars = new HashMap<String, Object>();

        // Search operations-dsl.mvel, if necessary using superclass if TaskService is subclassed
        InputStream is = null;
        // for (Class<?> c = getClass(); c != null; c = c.getSuperclass()) {
        is = getClass().getResourceAsStream("/operations-dsl.mvel");
//            if (is != null) {
//                break;
//            }
        //}
        if (is == null) {
            throw new RuntimeException("Unable To initialise TaskService, could not find Operations DSL");
        }
        Reader reader = new InputStreamReader(is);
        try {
            operations = (Map<Operation, List<OperationCommand>>) eval(toString(reader), vars);
        } catch (IOException e) {
            throw new RuntimeException("Unable To initialise TaskService, could not load Operations DSL");
        }


    }

    public static String toString(Reader reader) throws IOException {
        int charValue;
        StringBuffer sb = new StringBuffer(1024);
        while ((charValue = reader.read()) != -1) {
            sb.append((char) charValue);
        }
        return sb.toString();
    }

    public static Object eval(Reader reader) {
        try {
            return eval(toString(reader), null);
        } catch (IOException e) {
            throw new RuntimeException("Exception Thrown", e);
        }
    }

    public static Object eval(Reader reader, Map<String, Object> vars) {
        try {
            return eval(toString(reader), vars);
        } catch (IOException e) {
            throw new RuntimeException("Exception Thrown", e);
        }
    }

    public static Object eval(String str, Map<String, Object> vars) {
        ParserConfiguration pconf = new ParserConfiguration();
//    	pconf.addPackageImport("org.jbpm.task");
//    	pconf.addPackageImport("org.jbpm.task.service");
//    	pconf.addPackageImport("org.jbpm.task.query");

        pconf.addPackageImport("org.jboss.human.interactions.internals.lifecycle");
        pconf.addPackageImport("org.jboss.human.interactions.model");
        pconf.addImport(Status.class);
        pconf.addImport(Allowed.class);
        pconf.addPackageImport("java.util");

//    	for(String entry : getInputs().keySet()){
//    		pconf.addImport(entry, getInputs().get(entry));
//        }
        ParserContext context = new ParserContext(pconf);
        Serializable s = MVEL.compileExpression(str.trim(), context);

        if (vars != null) {
            return MVEL.executeExpression(s, vars);
        } else {
            return MVEL.executeExpression(s);
        }
    }
}

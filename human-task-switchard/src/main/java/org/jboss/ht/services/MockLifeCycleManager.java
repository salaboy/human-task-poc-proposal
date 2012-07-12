/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht.services;

import java.util.List;
import java.util.Map;
import javax.enterprise.event.Event;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import org.jboss.ht.services.annotations.Mock;
import org.jboss.human.interactions.events.BeforeTaskCompletedEvent;
import org.jboss.human.interactions.events.BeforeTaskStartedEvent;
import org.jboss.human.interactions.internals.exceptions.TaskException;
import org.jboss.human.interactions.internals.lifecycle.LifecycleManager;
import org.jboss.human.interactions.model.Operation;

/**
 *
 * @author salaboy
 * Look at: 
 * 
 * https://github.com/jboss-switchyard/components/blob/master/bean/src/test/java/org/switchyard/component/bean/tests/ConsumerBean.java
 * 
 * https://github.com/jboss-switchyard/components/blob/master/camel/camel-core/src/test/resources/org/switchyard/component/camel/deploy/switchyard-activator-math-test.xml
 */
@Mock
public class MockLifeCycleManager implements LifecycleManager{
    @Inject
    private Event<Operation> taskOperationEvents;
    
    public void taskOperation(Operation operation, long taskId, String userId, String targetEntityId, Map<String, Object> data, List<String> groupIds) throws TaskException {
        taskOperationEvents.select(new AnnotationLiteral<BeforeTaskCompletedEvent>() {
                    }).fire(operation);
    }
    
}

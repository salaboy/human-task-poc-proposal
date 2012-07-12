/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.humantaskswitchyard;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.Exchange;
import org.switchyard.component.bean.config.model.BeanSwitchYardScanner;
import org.switchyard.test.MockHandler;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.test.SwitchYardTestKit;
import org.switchyard.test.mixins.CDIMixIn;
import org.switchyard.test.mixins.HornetQMixIn;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.jboss.ht.services.TaskLifeCycleOperationEventListener;
import org.jboss.ht.services.TaskUserRequest;

/**
 *
 * @author salaboy
 */
@SwitchYardTestCaseConfig(
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML, 
        mixins = {CDIMixIn.class, HornetQMixIn.class},
        scanners = BeanSwitchYardScanner.class)
@RunWith(SwitchYardRunner.class)
public class SwitchardCamelCDISimpleTest {

   
    private static final String QUEUE_NAME = "TaskInstanceEndpointQueue";
    private SwitchYardTestKit _testKit;
//    @Inject
//    private CDIMixIn cdi;
    
    @Test
    public void sendTextMessageToJMSQueue() throws Exception {
        final TaskUserRequest payload = new TaskUserRequest(1, "salaboy");
        
        MockHandler mockHandler = _testKit.registerInOnlyService("TaskInstanceEndpointBean");
        //Bean bean = cdi.getBean(TaskLifeCycleOperationEventListener.class);
        
        
        sendTextToQueue(payload, "activate", QUEUE_NAME);
        // Allow for the JMS Message to be processed.
        Thread.sleep(5000);
        
        final LinkedBlockingQueue<Exchange> recievedMessages = mockHandler.getMessages();
        assertThat(recievedMessages, is(notNullValue()));
        final Exchange recievedExchange = recievedMessages.iterator().next();
        assertThat(recievedExchange.getMessage().getContent(TaskUserRequest.class).getUserId(), is(equalTo(payload.getUserId())));
        assertThat(recievedExchange.getMessage().getContent(TaskUserRequest.class).getTaskId(), is(equalTo(payload.getTaskId())));
    }
    
    private void sendTextToQueue(final Serializable payload, final String operationName, final String queueName) throws Exception {
        InitialContext initialContext = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            initialContext = new InitialContext();
            final Queue testQueue = (Queue) initialContext.lookup(queueName);
            final ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(testQueue);
            ObjectMessage message = session.createObjectMessage(payload);
            //message.setStringProperty("operationName", operationName);
            
            producer.send(message);
        } finally {
            if (producer != null) {
	            producer.close();
            }
            if (session != null) {
	            session.close();
            }
            if (connection != null) {
	            connection.close();
            }
            if (initialContext != null) {
	            initialContext.close();
            }
        }
    }
}

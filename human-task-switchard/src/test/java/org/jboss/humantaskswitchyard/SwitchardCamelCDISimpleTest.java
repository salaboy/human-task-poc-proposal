/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.humantaskswitchyard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.bean.config.model.BeanSwitchYardScanner;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.test.SwitchYardTestKit;
import org.switchyard.test.mixins.CDIMixIn;
import org.switchyard.test.mixins.HornetQMixIn;
import org.jboss.ht.services.TaskUserRequest;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.switchyard.test.BeforeDeploy;
import org.switchyard.test.mixins.jca.JCAMixIn;
import org.switchyard.test.mixins.jca.ResourceAdapterConfig;

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
    private static final String NOTIFICATION_QUEUE_NAME = "TaskNotificationTopic";
    private SwitchYardTestKit _testKit;
    private HornetQMixIn _hqMixIn;

    @Test
    public void sendTextMessageToJMSQueue() throws Exception {
        final TaskUserRequest payload = new TaskUserRequest(1, "salaboy");

        
        MessageConsumer notificationConsumer = createNotificationConsumer(NOTIFICATION_QUEUE_NAME);
        MessageConsumer notificationConsumer2 = createNotificationConsumer(NOTIFICATION_QUEUE_NAME);
        
        
        sendOperationToQueue(payload, "activate", QUEUE_NAME);
        // Allow for the JMS Message to be processed.
        Thread.sleep(5000);
        
        Message message = notificationConsumer.receive(1000);
        assertNotNull(message);
        
        Message message2 = notificationConsumer2.receive(1000);
        assertNotNull(message2);
        
        assertEquals(((ObjectMessage)message).getObject(), ((ObjectMessage)message2).getObject());
        Thread.sleep(5000);


    }

    private void sendOperationToQueue(final Serializable payload, final String operationName, final String queueName) throws Exception {
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
            message.setStringProperty("operationName", operationName);

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

    private MessageConsumer createNotificationConsumer(final String queueName) throws Exception {
        InitialContext initialContext = null;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;

        initialContext = new InitialContext();
        final Topic testTopic = (Topic) initialContext.lookup(queueName);
        final ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumer = session.createConsumer(testTopic);

        return consumer;


    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ht.services;

import java.io.Serializable;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import org.switchyard.component.bean.Service;

/**
 *
 * @author salaboy
 */
@Service(TaskInstanceNotification.class)
public class SimpleTaskInstanceNotification implements TaskInstanceNotification{
    private static final String NOTIFICATION_QUEUE_NAME = "TaskNotificationTopic";
    public void process(Object stuff) {
        try {
            System.out.println(" Sending Notification: "+stuff);
            sendOperationToQueue((Serializable)stuff);
        } catch (Exception ex) {
            Logger.getLogger(SimpleTaskInstanceNotification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void sendOperationToQueue(final Serializable payload) throws Exception {
        InitialContext initialContext = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            initialContext = new InitialContext();
            final Topic testQueue = (Topic) initialContext.lookup(NOTIFICATION_QUEUE_NAME);
            final ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(testQueue);
            ObjectMessage message = session.createObjectMessage(payload);

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

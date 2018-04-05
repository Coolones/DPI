package mix.gateway;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;

public class MessageSenderGateway {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;

    public MessageSenderGateway(String channelName) {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(channelName);
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void setDestination(String channelName) {
        try {
            destination = session.createQueue(channelName);
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void SendMessage(Serializable obj) {
        try {
            ObjectMessage message = session.createObjectMessage(obj);
            producer.send(message);
            System.out.println("Sent: " + obj.toString());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

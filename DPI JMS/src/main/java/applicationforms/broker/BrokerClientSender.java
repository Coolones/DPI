package applicationforms.broker;

import mix.model.loan.LoanReply;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class BrokerClientSender {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;

    public BrokerClientSender() {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("CLIENTLOANREPLY");
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendReply(LoanReply reply) {
        try {
            ObjectMessage message = session.createObjectMessage(reply);
            producer.send(message);
            System.out.println("Sent: " + reply.toString());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
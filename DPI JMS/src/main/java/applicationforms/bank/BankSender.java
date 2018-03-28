package applicationforms.bank;

import mix.messaging.RequestReply;
import mix.model.bank.BankInterestReply;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class BankSender {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private static Session session = null;
    private Destination destination = null;
    private static MessageProducer producer = null;

    public BankSender() {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("BANKINTERESTREPLY");
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendReply(RequestReply reply) {
        try {
            ObjectMessage message = session.createObjectMessage(reply);
            producer.send(message);
            System.out.println("Sent: " + reply.toString());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
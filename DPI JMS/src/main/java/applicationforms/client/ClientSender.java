package applicationforms.client;

import mix.model.loan.LoanRequest;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ClientSender {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;

    public ClientSender() {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("CLIENTLOANREQUEST");
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(LoanRequest request) {
        try {
            ObjectMessage message = session.createObjectMessage(request);
            producer.send(message);
            System.out.println("Sent: " + request.toString());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

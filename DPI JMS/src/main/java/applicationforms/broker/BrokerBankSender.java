package applicationforms.broker;

import mix.model.bank.BankInterestRequest;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class BrokerBankSender {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;

    public BrokerBankSender() {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("BANKINTERESTREQUEST");
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(BankInterestRequest request) {
        try {
            ObjectMessage message = session.createObjectMessage(request);
            producer.send(message);
            System.out.println("Sent: " + request.toString());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

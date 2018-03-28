package applicationforms.client;

import mix.messaging.RequestReply;
import mix.model.loan.LoanReply;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ClientReceiver implements MessageListener {
    private ActiveMQConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;

    private LoanClientFrame frame;

    public ClientReceiver(LoanClientFrame frame) {
        this.frame = frame;
    }

    public void receiveMessage() {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            factory.setTrustAllPackages(true);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("CLIENTLOANREPLY");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new ClientReceiver(frame));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        LoanReply reply = null;
        try {
            reply = (LoanReply) ((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        frame.SetReply(reply);

    }
}
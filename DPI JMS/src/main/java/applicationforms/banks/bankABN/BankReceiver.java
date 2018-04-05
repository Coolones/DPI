package applicationforms.banks.bankABN;

import mix.model.bank.BankInterestRequest;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class BankReceiver implements MessageListener {

    private ActiveMQConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;

    private ABNBankFrame frame;

    public BankReceiver() {

    }

    public BankReceiver(ABNBankFrame jmsBankFrame) {
        //this.frame = jmsBankFrame;
    }

    public void receiveMessage() {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            factory.setTrustAllPackages(true);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("BANKINTERESTREQUEST");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new BankReceiver(frame));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        BankInterestRequest request = null;
        try {
            request = (BankInterestRequest) ((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        frame.Add(request);
    }
}
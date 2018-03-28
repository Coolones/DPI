package applicationforms.broker;

import applicationforms.broker.LoanBrokerFrame;
import mix.messaging.RequestReply;
import mix.model.bank.BankInterestReply;
import mix.model.bank.BankInterestRequest;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class BrokerBankReceiver implements MessageListener {
    private ActiveMQConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;

    private LoanBrokerFrame frame;

    public BrokerBankReceiver() {

    }

    public BrokerBankReceiver(LoanBrokerFrame loanBrokerFrame) {
        this.frame = loanBrokerFrame;
    }

    public void receiveMessage() {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            factory.setTrustAllPackages(true);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("BANKINTERESTREPLY");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new BrokerBankReceiver(frame));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        RequestReply reply = null;
        try {
            reply = (RequestReply) ((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        frame.add(frame.getRequestReply((BankInterestRequest) reply.getRequest()).getLoanRequest(), (BankInterestReply) reply.getReply());
    }
}

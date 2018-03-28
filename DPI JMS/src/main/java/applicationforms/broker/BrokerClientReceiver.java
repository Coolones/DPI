package applicationforms.broker;

import applicationforms.broker.LoanBrokerFrame;
import mix.model.bank.BankInterestRequest;
import mix.model.loan.LoanRequest;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class BrokerClientReceiver implements MessageListener {

    private ActiveMQConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;

    private LoanBrokerFrame frame;

    public BrokerClientReceiver() {

    }

    public BrokerClientReceiver(LoanBrokerFrame loanBrokerFrame) {
        this.frame = loanBrokerFrame;
    }

    public void receiveMessage() {
        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            factory.setTrustAllPackages(true);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("CLIENTLOANREQUEST");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new BrokerClientReceiver(frame));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        LoanRequest request = null;
        try {
            request = (LoanRequest) ((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        frame.add(request);
        frame.add(request, new BankInterestRequest(request.getId(), request.getAmount(), request.getTime()));
    }
}
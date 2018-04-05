package applicationforms.broker;

import mix.gateway.MessageReceiverGateway;
import mix.gateway.MessageSenderGateway;
import mix.messaging.RequestReply;
import mix.model.bank.BankInterestRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BrokerBankGateway implements MessageListener {

    // Assignment 2 implementation
    // private LoanBrokerFrame frame;

    // Assignment 3 implementation
    private AggregationProcessor aggregationProcessor;
    private MessageSenderGateway senderGateway;
    private MessageReceiverGateway receiverGateway;

    // Assignment 2 implementation
    /*public BrokerBankGateway(LoanBrokerFrame frame) {
        this.frame = frame;
        senderGateway = new MessageSenderGateway("BANKINTERESTREQUEST");
        receiverGateway = new MessageReceiverGateway(this, "BANKINTERESTREPLY");
    }*/

    // Assignment 3 implementation
    public BrokerBankGateway(AggregationProcessor aggregationProcessor) {
        this.aggregationProcessor = aggregationProcessor;
        senderGateway = new MessageSenderGateway("BANKINTERESTREQUEST");
        receiverGateway = new MessageReceiverGateway(this, "BANKINTERESTREPLY");
    }

    // Assignment 2 implementation
    /*public void applyForBankInterest(BankInterestRequest request) {
        senderGateway.SendMessage(request);
    }*/

    // Assignment 3 implementation
    public void applyForBankInterest(BankInterestRequest request, String channelName) {
        senderGateway.setDestination(channelName);
        senderGateway.SendMessage(request);
    }

    // Assignment 2 implementation
    /*public void onBankInterestReplyArrived(RequestReply reply) {
        frame.add(frame.getRequestReply((BankInterestRequest) reply.getRequest()).getLoanRequest(), (BankInterestReply) reply.getReply());
    }*/

    // Assignment 3 implementation
    public void onBankInterestReplyArrived(RequestReply reply) {
        aggregationProcessor.addReply(reply);
    }

    @Override
    public void onMessage(Message message) {
        try {
            onBankInterestReplyArrived((RequestReply) ((ObjectMessage) message).getObject());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

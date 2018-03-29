package applicationforms.broker;

import mix.gateway.MessageReceiverGateway;
import mix.gateway.MessageSenderGateway;
import mix.messaging.RequestReply;
import mix.model.bank.BankInterestReply;
import mix.model.bank.BankInterestRequest;
import mix.model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BrokerBankGateway implements MessageListener {

    private LoanBrokerFrame frame;
    private MessageSenderGateway senderGateway;
    private MessageReceiverGateway receiverGateway;

    public BrokerBankGateway(LoanBrokerFrame frame) {
        this.frame = frame;
        senderGateway = new MessageSenderGateway("BANKINTERESTREQUEST");
        receiverGateway = new MessageReceiverGateway(this, "BANKINTERESTREPLY");
    }

    public void applyForBankInterest(BankInterestRequest request) {
        senderGateway.SendMessage(request);
    }

    public void onBankInterestReplyArrived(RequestReply reply) {
        frame.add(frame.getRequestReply((BankInterestRequest) reply.getRequest()).getLoanRequest(), (BankInterestReply) reply.getReply());
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

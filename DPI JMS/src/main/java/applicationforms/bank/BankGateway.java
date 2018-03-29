package applicationforms.bank;

import mix.gateway.MessageReceiverGateway;
import mix.gateway.MessageSenderGateway;
import mix.messaging.RequestReply;
import mix.model.bank.BankInterestRequest;
import mix.model.loan.LoanReply;
import mix.model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BankGateway implements MessageListener {

    private JMSBankFrame frame;
    private MessageSenderGateway senderGateway;
    private MessageReceiverGateway receiverGateway;

    public BankGateway(JMSBankFrame frame) {
        this.frame = frame;
        senderGateway = new MessageSenderGateway("BANKINTERESTREPLY");
        receiverGateway = new MessageReceiverGateway(this, "BANKINTERESTREQUEST");
    }

    public void sendInterestReply(RequestReply reply) {
        senderGateway.SendMessage(reply);
    }

    public void onBankInterestRequestArrived(BankInterestRequest request) {
        frame.Add(request);
    }

    @Override
    public void onMessage(Message message) {
        try {
            onBankInterestRequestArrived((BankInterestRequest) ((ObjectMessage) message).getObject());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

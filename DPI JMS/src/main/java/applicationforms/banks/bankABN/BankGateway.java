package applicationforms.banks.bankABN;

import applicationforms.banks.iBankFrame;
import mix.gateway.MessageReceiverGateway;
import mix.gateway.MessageSenderGateway;
import mix.messaging.RequestReply;
import mix.model.bank.BankInterestRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BankGateway implements MessageListener {

    private iBankFrame frame;
    private MessageSenderGateway senderGateway;
    private MessageReceiverGateway receiverGateway;

    public BankGateway(iBankFrame frame) {
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

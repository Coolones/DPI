package applicationforms.client;

import mix.gateway.MessageReceiverGateway;
import mix.gateway.MessageSenderGateway;
import mix.model.loan.LoanReply;
import mix.model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class ClientGateway implements MessageListener {

    private LoanClientFrame frame;
    private MessageSenderGateway senderGateway;
    private MessageReceiverGateway receiverGateway;

    public ClientGateway(LoanClientFrame frame) {
        this.frame = frame;
        senderGateway = new MessageSenderGateway("CLIENTLOANREQUEST");
        receiverGateway = new MessageReceiverGateway(this, "CLIENTLOANREPLY");
    }

    public void applyForLoan(LoanRequest request) {
        senderGateway.SendMessage(request);
    }

    public void onLoanReplyArrived(LoanReply reply) {
        frame.SetReply(reply);
    }

    @Override
    public void onMessage(Message message) {
        try {
            onLoanReplyArrived((LoanReply) ((ObjectMessage) message).getObject());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

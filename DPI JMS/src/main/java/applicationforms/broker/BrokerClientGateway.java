package applicationforms.broker;

import mix.gateway.MessageReceiverGateway;
import mix.gateway.MessageSenderGateway;
import mix.model.bank.BankInterestRequest;
import mix.model.loan.LoanReply;
import mix.model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BrokerClientGateway implements MessageListener {

    private LoanBrokerFrame frame;
    private MessageSenderGateway senderGateway;
    private MessageReceiverGateway receiverGateway;

    public BrokerClientGateway(LoanBrokerFrame frame) {
        this.frame = frame;
        senderGateway = new MessageSenderGateway("CLIENTLOANREPLY");
        receiverGateway = new MessageReceiverGateway(this, "CLIENTLOANREQUEST");
    }

    public void sendLoanReply(LoanReply reply) {
        senderGateway.SendMessage(reply);
    }

    public void onLoanRequestArrived(LoanRequest request) {
        frame.add(request);
        frame.add(request, new BankInterestRequest(request.getId(), request.getAmount(), request.getTime()));
    }

    @Override
    public void onMessage(Message message) {
        try {
            onLoanRequestArrived((LoanRequest) ((ObjectMessage) message).getObject());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

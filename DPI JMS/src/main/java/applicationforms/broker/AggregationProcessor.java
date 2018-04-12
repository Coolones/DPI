package applicationforms.broker;

import mix.messaging.RequestReply;
import mix.model.bank.BankInterestReply;
import mix.model.bank.BankInterestRequest;

import java.util.HashMap;
import java.util.Map;

public class AggregationProcessor {

    private LoanBrokerFrame frame;
    private Map<RequestReply, String> recipientList;

    public AggregationProcessor(LoanBrokerFrame frame) {
        this.frame = frame;
        recipientList = new HashMap();
    }

    public void addRecipient(RequestReply request, String bank) {
        recipientList.put(request, bank);
    }

    public void addReply(RequestReply reply) {

        boolean repliesReceived = true;

        for (Map.Entry<RequestReply, String> recepient : recipientList.entrySet()) {
            if (((BankInterestRequest) recepient.getKey().getRequest()).getId() == ((BankInterestReply) reply.getReply()).getId()) {
                if (recepient.getValue().equals(((BankInterestReply) reply.getReply()).getQuoteId())) {
                    recepient.getKey().setReply(reply.getReply());
                }
                else if (recepient.getKey().getReply() == null) repliesReceived = false;
            }
        }
        if (repliesReceived) SetBestBankReply(((BankInterestReply) reply.getReply()).getId());
    }

    public void SetBestBankReply(int ID) {

        RequestReply bestReply = null;

        for (Map.Entry<RequestReply, String> recepient : recipientList.entrySet()) {
            if (recepient.getKey().getReply() != null && ((BankInterestReply) recepient.getKey().getReply()).getId() == ID) {
                if (bestReply == null || ((BankInterestReply) bestReply.getReply()).getInterest() > ((BankInterestReply) recepient.getKey().getReply()).getInterest()) bestReply = recepient.getKey();
            }
        }

        frame.add(frame.getRequestReply((BankInterestRequest) bestReply.getRequest()).getLoanRequest(), (BankInterestReply) bestReply.getReply());
    }

    public void SetBankNoReply(BankInterestRequest request) {
        frame.add(frame.getRequestReply(request).getLoanRequest(), new BankInterestReply(request.getId(), 0, ""));
    }
}

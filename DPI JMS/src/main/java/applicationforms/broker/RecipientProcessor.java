package applicationforms.broker;

import applicationforms.banks.LoanCriteria;
import mix.messaging.RequestReply;
import mix.model.bank.BankInterestRequest;

import java.util.ArrayList;
import java.util.List;

public class RecipientProcessor {

    private List<LoanCriteria> criterias;
    private AggregationProcessor aggregationProcessor;
    private BrokerBankGateway gateway;

    public RecipientProcessor(LoanBrokerFrame frame) {

        criterias = new ArrayList();
        criterias.add(new LoanCriteria("ABN AMRO", "ABNAMROBANKINTERESTREQUEST", 200000, 300000, 0, 20));
        criterias.add(new LoanCriteria("ING", "INGBANKINTERESTREQUEST", 0, 100000, 0, 10));
        criterias.add(new LoanCriteria("Rabobank", "RABOBANKINTERESTREQUEST", 0, 250000, 0, 15));

        aggregationProcessor = new AggregationProcessor(frame);

        gateway = new BrokerBankGateway(aggregationProcessor);
    }

    public void sendLoanToReceivers(BankInterestRequest request) {

        boolean noLoan = true;

        for (LoanCriteria criteria : criterias) {
            if (criteria.CheckValidLoan(request)) {
                noLoan = false;
                aggregationProcessor.addRecipient(new RequestReply(request, null), criteria.getBankName());
                gateway.applyForBankInterest(request, criteria.getBankGateway());
            }
        }

        if (noLoan) aggregationProcessor.SetBankNoReply(request);
    }
}

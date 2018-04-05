package applicationforms.banks;

import mix.model.bank.BankInterestRequest;

public class LoanCriteria {

    private String bankName;
    private String bankGateway;
    private int minLoanAmount;
    private int maxLoanAmount;
    private int minLoanTime;
    private int maxLoanTime;

    public LoanCriteria(String bankName, String bankGateway, int minLoanAmount, int maxLoanAmount, int minLoanTime, int maxLoanTime) {
        this.bankName = bankName;
        this.bankGateway = bankGateway;
        this.minLoanAmount = minLoanAmount;
        this.maxLoanAmount = maxLoanAmount;
        this.minLoanTime = minLoanTime;
        this.maxLoanTime = maxLoanTime;
    }

    public boolean CheckValidLoan(BankInterestRequest request) {

        int amount = request.getAmount();
        int time = request.getTime();

        return amount >= minLoanAmount &&
                amount <= maxLoanAmount &&
                time >= minLoanTime &&
                time <= maxLoanTime ? true : false;
    }


    public String getBankName() {
        return bankName;
    }

    public String getBankGateway() {
        return bankGateway;
    }
}

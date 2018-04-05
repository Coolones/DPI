package mix.model.bank;

import java.io.Serializable;

/**
 * This class stores information about the bank reply
 *  to a loan request of the specific client
 * 
 */
public class BankInterestReply implements Serializable {

    private int id;
    private double interest; // the loan interest
    private String bankId; // the nunique quote Id
    
    public BankInterestReply() {
        this.id = 0;
        this.interest = 0;
        this.bankId = "";
    }
    
    public BankInterestReply(int id, double interest, String quoteId) {
        this.id = id;
        this.interest = interest;
        this.bankId = quoteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getQuoteId() {
        return bankId;
    }

    public void setQuoteId(String quoteId) {
        this.bankId = quoteId;
    }

    public String toString() {
        return !bankId.equals("") ? "id=" + id + " quote=" + this.bankId + " interest=" + this.interest : "No bank wants give a loan!";
    }
}

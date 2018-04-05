package mix.model.loan;

import java.io.Serializable;

/**
 *
 * This class stores all information about a bank offer
 * as a response to a client loan request.
 */
public class LoanReply implements Serializable {

    private int id;
    private double interest; // the interest that the bank offers
    private String bankID; // the unique quote identification

    public LoanReply() {
        super();
        this.id = 0;
        this.interest = 0;
        this.bankID = "";
    }
    public LoanReply(int id, double interest, String quoteID) {
        super();
        this.id = id;
        this.interest = interest;
        this.bankID = quoteID;
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

    public String getQuoteID() {
        return bankID;
    }

    public void setQuoteID(String quoteID) {
        this.bankID = quoteID;
    }
    
    @Override
    public String toString(){
        return !bankID.equals("") ? "id=" + String.valueOf(id) + " interest="+String.valueOf(interest) + " quoteID="+String.valueOf(bankID) : "No bank wants give you a loan!";
    }
}

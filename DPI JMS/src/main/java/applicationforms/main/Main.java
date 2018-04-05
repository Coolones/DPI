package applicationforms.main;

import applicationforms.banks.bankABN.*;
import applicationforms.banks.bankING.*;
import applicationforms.banks.bankRabo.*;
import applicationforms.broker.LoanBrokerFrame;
import applicationforms.client.LoanClientFrame;

import java.awt.*;

public class Main {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoanClientFrame clientFrame = new LoanClientFrame();
                    LoanBrokerFrame brokerFrame = new LoanBrokerFrame();
                    ABNBankFrame bankABNFrame = new ABNBankFrame();
                    INGBankFrame bankINGFrame = new INGBankFrame();
                    RaboBankFrame bankRaboFrame = new RaboBankFrame();
                    brokerFrame.setVisible(true);
                    clientFrame.setVisible(true);
                    bankABNFrame.setVisible(true);
                    bankINGFrame.setVisible(true);
                    bankRaboFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

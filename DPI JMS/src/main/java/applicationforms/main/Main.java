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
                    ABNBankFrame bankABNFrame = new ABNBankFrame();
                    INGBankFrame bankINGFrame = new INGBankFrame();
                    RaboBankFrame bankRaboFrame = new RaboBankFrame();
                    LoanBrokerFrame brokerFrame = new LoanBrokerFrame();
                    LoanClientFrame clientFrame = new LoanClientFrame();
                    bankABNFrame.setVisible(true);
                    bankINGFrame.setVisible(true);
                    bankRaboFrame.setVisible(true);
                    clientFrame.setVisible(true);
                    brokerFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

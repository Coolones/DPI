package applicationforms.main;

import applicationforms.bank.JMSBankFrame;
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
                    JMSBankFrame bankFrame = new JMSBankFrame();
                    LoanBrokerFrame brokerFrame = new LoanBrokerFrame();
                    LoanClientFrame clientFrame = new LoanClientFrame();
                    bankFrame.setVisible(true);
                    brokerFrame.setVisible(true);
                    clientFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

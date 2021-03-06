package applicationforms.broker;

import mix.model.bank.BankInterestReply;
import mix.model.bank.BankInterestRequest;
import mix.model.loan.LoanReply;
import mix.model.loan.LoanRequest;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoanBrokerFrame extends JFrame {


	/*rivate BrokerClientReceiver clientReceiver;
	private BrokerClientSender clientSender;

	private BrokerBankReceiver bankReceiver;
	private BrokerBankSender bankSender;*/

	private BrokerClientGateway clientGateway;
	//private BrokerBankGateway bankGateway;
    private RecipientProcessor recipientProcessor;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<JListLine> listModel = new DefaultListModel<JListLine>();
	private JList<JListLine> list;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanBrokerFrame frame = new LoanBrokerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public LoanBrokerFrame() {

		/*clientReceiver = new BrokerClientReceiver(this);
		clientReceiver.receiveMessage();

		clientSender = new BrokerClientSender();

		bankReceiver = new BrokerBankReceiver(this);
		bankReceiver.receiveMessage();

		bankSender = new BrokerBankSender();*/

		clientGateway = new BrokerClientGateway(this);
		//bankGateway = new BrokerBankGateway(this);
        recipientProcessor = new RecipientProcessor(this);

		setTitle("Loan Broker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{46, 31, 86, 30, 89, 0};
		gbl_contentPane.rowHeights = new int[]{233, 23, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		list = new JList<JListLine>(listModel);
		scrollPane.setViewportView(list);		
	}

	 private JListLine getRequestReply(LoanRequest request){

	     for (int i = 0; i < listModel.getSize(); i++){
	    	 JListLine rr =listModel.get(i);
	    	 if (rr.getLoanRequest() == request){
	    		 return rr;
	    	 }
	     }

	     return null;
	   }

	public JListLine getRequestReply(BankInterestRequest request){

		for (int i = 0; i < listModel.getSize(); i++){
			JListLine rr =listModel.get(i);
			if (rr.getBankRequest().getId() == request.getId()){
				return rr;
			}
		}

		return null;
	}

	public void add(LoanRequest loanRequest){		
		listModel.addElement(new JListLine(loanRequest));
	}
	

	public void add(LoanRequest loanRequest,BankInterestRequest bankRequest){
		JListLine rr = getRequestReply(loanRequest);
		if (rr!= null && bankRequest != null){
			rr.setBankRequest(bankRequest);
			//bankSender.sendRequest(bankRequest);
			//bankGateway.applyForBankInterest(bankRequest);
            recipientProcessor.sendLoanToReceivers(bankRequest);
            list.repaint();
		}		
	}
	
	public void add(LoanRequest loanRequest, BankInterestReply bankReply){
		JListLine rr = getRequestReply(loanRequest);
		if (rr!= null && bankReply != null){
			rr.setBankReply(bankReply);;
			//clientSender.sendReply(new LoanReply(bankReply.getId(), bankReply.getInterest(), bankReply.getQuoteId()));
            clientGateway.sendLoanReply(new LoanReply(bankReply.getId(), bankReply.getInterest(), bankReply.getQuoteId()));
			list.repaint();
		}		
	}
}

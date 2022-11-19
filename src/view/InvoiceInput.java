package view;

import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceInput extends JDialog {
	private JLabel cusName, date;
	private JButton submit;
	private JTextField cusNameText, dateText;
	
	public InvoiceInput(Frame owner, String title) {
		super(owner,title);
		submit = new JButton("Add Invoice");
		cusName = new JLabel("Customer Name");
		date = new JLabel("Date (dd-mm-yyyy)");
		dateText = new JTextField(10);
		cusNameText = new JTextField(20);
		
		this.setSize(600, 100);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new FlowLayout());

		this.add(cusName);
		this.add(cusNameText);
		this.add(date);
		this.add(dateText);
		this.add(submit);
		
	}


	public JButton getSubmit() {
		return submit;
	}

	public void setSubmit(JButton submit) {
		this.submit = submit;
	}

	public JLabel getCusName() {
		return cusName;
	}

	public void setCusName(JLabel cusName) {
		this.cusName = cusName;
	}

	public JLabel getDate() {
		return date;
	}

	public void setDate(JLabel date) {
		this.date = date;
	}

	public JTextField getCusNameText() {
		return cusNameText;
	}

	public void setCusNameText(JTextField cusNameText) {
		this.cusNameText = cusNameText;
	}

	public JTextField getDateText() {
		return dateText;
	}

	public void setDateText(JTextField dateText) {
		this.dateText = dateText;
	}
}

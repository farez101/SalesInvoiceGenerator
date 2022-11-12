package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.Vector;

public class MainWindow extends JFrame {
	private JFrame mainFrame ;
	private JPanel mainPanel ;

	private JButton createNewInvoiceBtn ;
	private JButton saveBtn ;			
	private JButton cancelBtn ;		
	private JButton deleteInvoiceBtn ;
	private JLabel invoiceNumber,invoiceNumberCount, invoiceDate, customerName, invoiceTotal,invoiceTotalCount;
	private JTextField  customerNameText,invoiceDateText ;
	
	
	public MainWindow (){
		  mainFrame = new JFrame();
		  mainPanel = new JPanel();
		mainFrame.setTitle("SalesInvoiceGenerator");
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 700);
		mainFrame.getContentPane().add(mainPanel);

		 createNewInvoiceBtn = new JButton("Create New Invoice");
		 saveBtn = new JButton("Save");			
		 cancelBtn = new JButton("Cancel");		
		 deleteInvoiceBtn = new JButton("Delete Invoice");

		JTable invoicesTable = new JTable();
		JTable invoiceItemsTable = new JTable();

		// declare menu bar and adding File menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);

		//declare menu items and adding to File Menu
		JMenuItem loadFile = new JMenuItem("Load File");
		JMenuItem saveFile = new JMenuItem("Save File");
		menuFile.add(loadFile);
		menuFile.add(saveFile);
		mainFrame.setJMenuBar(menuBar);

		mainPanel.setLayout(new GridLayout(1, 2));
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();

		leftPanel.setLayout(new BorderLayout());
		JPanel leftPanelTop = new JPanel();
		JPanel leftPanelBottom = new JPanel();

		rightPanel.setLayout(new BorderLayout());
		JPanel rightPanelTop = new JPanel();
		JPanel rightPanelMiddle = new JPanel();
		JPanel rightPanelBottom = new JPanel();



		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);

		leftPanel.add(leftPanelTop,BorderLayout.NORTH);
		leftPanel.add(leftPanelBottom,BorderLayout.SOUTH);

		leftPanelBottom.setLayout(new FlowLayout());

		rightPanel.add(rightPanelTop,BorderLayout.NORTH);
		rightPanel.add(rightPanelMiddle,BorderLayout.CENTER);
		rightPanel.add(rightPanelBottom,BorderLayout.SOUTH);

		rightPanelTop.setLayout(new GridLayout(4, 2));
		rightPanelBottom.setLayout(new FlowLayout());

		/*
		 * Adding Left Panel Components
		 */
		leftPanelTop.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Invoices Table",TitledBorder.LEFT,TitledBorder.TOP));
		Vector <Vector> invoicesTableData =new Vector<Vector>();;
		Vector<String> invoicesTableColumnNames = new Vector();
		invoicesTableColumnNames.add("No.");
		invoicesTableColumnNames.add("Date");
		invoicesTableColumnNames.add("Customer");
		invoicesTableColumnNames.add("Total");

		invoicesTable = new JTable(invoicesTableData, invoicesTableColumnNames);
		JScrollPane invoicesTableScrollPane = new JScrollPane(invoicesTable);
		leftPanelTop.add(invoicesTableScrollPane);

		leftPanelBottom.add(createNewInvoiceBtn);
		leftPanelBottom.add(deleteInvoiceBtn);

		/*
		 * Adding Right Panel Components
		 */

	

		invoiceNumber = new JLabel("Invoice Number");
		invoiceNumberCount = new JLabel();
		invoiceNumberCount.setText("0");

		invoiceDate = new JLabel("Invoice Date");
		invoiceDateText = new JTextField(20);

		customerName = new JLabel("Customer Name");
		customerNameText = new JTextField(20);

		invoiceTotal = new JLabel("Invoice Total");
		invoiceTotalCount = new JLabel();
		invoiceTotalCount.setText("0");
		
		rightPanelTop.add(invoiceNumber);
		rightPanelTop.add(invoiceNumberCount);
		rightPanelTop.add(invoiceDate);
		rightPanelTop.add(invoiceDateText);
		rightPanelTop.add(customerName);
		rightPanelTop.add(customerNameText);
		rightPanelTop.add(invoiceTotal);
		rightPanelTop.add(invoiceTotalCount);

		rightPanelMiddle.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Invoice Items",TitledBorder.LEFT,TitledBorder.TOP));
		Vector <Vector> invoiceItemsData =new Vector<Vector>();;
		Vector<String> invoiceItemsColumnNames = new Vector();
		invoiceItemsColumnNames.add("No.");
		invoiceItemsColumnNames.add("Item Name");
		invoiceItemsColumnNames.add("Item Price");
		invoiceItemsColumnNames.add("Count");
		invoiceItemsColumnNames.add("Item Total");

		invoiceItemsTable = new JTable(invoiceItemsData, invoiceItemsColumnNames);
		JScrollPane invoiceItemsScrollPane = new JScrollPane(invoiceItemsTable);
		rightPanelMiddle.add(invoiceItemsScrollPane);

		rightPanelBottom.add(saveBtn);
		rightPanelBottom.add(cancelBtn);

		mainFrame.setVisible(true);


	}
}

package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame {
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel leftPanelTop;
	private JPanel leftPanelBottom;
	private JPanel rightPanelTop;
	private JPanel rightPanelMiddle;
	private JPanel rightPanelBottom;
	private Vector<Vector<String>> invoicesTableData;
	private Vector<String> invoicesTableColumnNames;
	private Vector<Vector<String>> invoiceItemsData;
	private Vector<String> invoiceItemsColumnNames;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem loadFile;
	private JMenuItem saveFile;
	private JButton createNewInvoiceBtn;
	private JButton saveBtn;
	private JButton addItemBtn;
	private JButton removeItemBtn;
	private JButton deleteInvoiceBtn;
	private JLabel invoiceNumber, invoiceNumberCount, invoiceDate, customerName, invoiceTotal, invoiceTotalCount;
	private JTextField customerNameText, invoiceDateText;
	private JTable invoicesTable;
	private JTable invoiceItemsTable;
	private DefaultTableModel invoicesTableModel;
	private DefaultTableModel itemsTableModel;

	@SuppressWarnings("serial")
	public MainWindow() {
		mainFrame = new JFrame();
		mainPanel = new JPanel();
		mainFrame.setTitle("SalesInvoiceGenerator");
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 700);

		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().add(mainPanel);

		createNewInvoiceBtn = new JButton("Create New Invoice");
		saveBtn = new JButton("Save");
		addItemBtn = new JButton("Add Item");
		removeItemBtn = new JButton("Remove Item");

		deleteInvoiceBtn = new JButton("Delete Invoice");

		invoicesTable = new JTable();
		invoiceItemsTable = new JTable();

		// declare menu bar and adding File menu
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuBar.add(menuFile);

		// declare menu items and adding to File Menu
		loadFile = new JMenuItem("Load File");
		saveFile = new JMenuItem("Save File");
		menuFile.add(loadFile);
		menuFile.add(saveFile);
		mainFrame.setJMenuBar(menuBar);

		mainPanel.setLayout(new GridLayout(1, 2));
		leftPanel = new JPanel();
		rightPanel = new JPanel();

		leftPanel.setLayout(new BorderLayout());
		leftPanelTop = new JPanel();
		leftPanelBottom = new JPanel();

		rightPanel.setLayout(new BorderLayout());
		rightPanelTop = new JPanel();
		rightPanelMiddle = new JPanel();
		rightPanelBottom = new JPanel();

		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);

		leftPanel.add(leftPanelTop, BorderLayout.NORTH);
		leftPanel.add(leftPanelBottom, BorderLayout.SOUTH);

		leftPanelBottom.setLayout(new FlowLayout());

		rightPanel.add(rightPanelTop, BorderLayout.NORTH);
		rightPanel.add(rightPanelMiddle, BorderLayout.CENTER);
		rightPanel.add(rightPanelBottom, BorderLayout.SOUTH);

		rightPanelTop.setLayout(new GridLayout(4, 2));
		rightPanelBottom.setLayout(new FlowLayout());

		/*
		 * Adding Left Panel Components
		 */
		leftPanelTop.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Invoices Table",
				TitledBorder.LEFT, TitledBorder.TOP));
		invoicesTableData = new Vector<Vector<String>>();
		invoicesTableColumnNames = new Vector<String>();
		invoicesTableColumnNames.add("No.");
		invoicesTableColumnNames.add("Date");
		invoicesTableColumnNames.add("Customer");
		invoicesTableColumnNames.add("Total");
		invoicesTableModel = new DefaultTableModel(invoicesTableData, invoicesTableColumnNames);
//		invoicesTable = new JTable(invoicesTableModel);
		invoicesTable = new JTable(invoicesTableModel){
	         public boolean editCellAt(int row, int column, java.util.EventObject e) {
	            return false;
	         }
	      };
		JScrollPane invoicesTableScrollPane = new JScrollPane(invoicesTable);
		leftPanelTop.add(invoicesTableScrollPane);
		invoicesTable.getTableHeader().setReorderingAllowed(false);
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

		rightPanelMiddle.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Invoice Items",
				TitledBorder.LEFT, TitledBorder.TOP));
		invoiceItemsData = new Vector<Vector<String>>();
		invoiceItemsColumnNames = new Vector<String>();
		invoiceItemsColumnNames.add("No.");
		invoiceItemsColumnNames.add("Item Name");
		invoiceItemsColumnNames.add("Item Price");
		invoiceItemsColumnNames.add("Count");
		invoiceItemsColumnNames.add("Item Total");

		itemsTableModel = new DefaultTableModel(invoiceItemsData, invoiceItemsColumnNames);
//		invoiceItemsTable = new JTable(itemsTableModel);
		invoiceItemsTable = new JTable(itemsTableModel){
	         public boolean editCellAt(int row, int column, java.util.EventObject e) {
	            return false;
	         }
	      };
		JScrollPane invoiceItemsScrollPane = new JScrollPane(invoiceItemsTable);
		rightPanelMiddle.add(invoiceItemsScrollPane);
		invoiceItemsTable.getTableHeader().setReorderingAllowed(false);
//		invoiceItemsTable.getTableHeader().setResizingAllowed(false);
		rightPanelBottom.add(saveBtn);
		rightPanelBottom.add(addItemBtn);
		rightPanelBottom.add(removeItemBtn);

		mainFrame.setVisible(true);
		mainFrame.setResizable(false);

	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JButton getCreateNewInvoiceBtn() {
		return createNewInvoiceBtn;
	}

	public void setCreateNewInvoiceBtn(JButton createNewInvoiceBtn) {
		this.createNewInvoiceBtn = createNewInvoiceBtn;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}

	public void setSaveBtn(JButton saveBtn) {
		this.saveBtn = saveBtn;
	}

	public JButton getDeleteInvoiceBtn() {
		return deleteInvoiceBtn;
	}

	public void setDeleteInvoiceBtn(JButton deleteInvoiceBtn) {
		this.deleteInvoiceBtn = deleteInvoiceBtn;
	}

	public JLabel getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(JLabel invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public JLabel getInvoiceNumberCount() {
		return invoiceNumberCount;
	}

	public void setInvoiceNumberCount(JLabel invoiceNumberCount) {
		this.invoiceNumberCount = invoiceNumberCount;
	}

	public JLabel getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(JLabel invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public JLabel getCustomerName() {
		return customerName;
	}

	public void setCustomerName(JLabel customerName) {
		this.customerName = customerName;
	}

	public JLabel getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(JLabel invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public JLabel getInvoiceTotalCount() {
		return invoiceTotalCount;
	}

	public void setInvoiceTotalCount(JLabel invoiceTotalCount) {
		this.invoiceTotalCount = invoiceTotalCount;
	}

	public JTextField getCustomerNameText() {
		return customerNameText;
	}

	public void setCustomerNameText(JTextField customerNameText) {
		this.customerNameText = customerNameText;
	}

	public JTextField getInvoiceDateText() {
		return invoiceDateText;
	}

	public void setInvoiceDateText(JTextField invoiceDateText) {
		this.invoiceDateText = invoiceDateText;
	}

	public JMenu getMenuFile() {
		return menuFile;
	}

	public void setMenuFile(JMenu menuFile) {
		this.menuFile = menuFile;
	}

	public JTable getInvoicesTable() {
		return invoicesTable;
	}

	public void setInvoicesTable(JTable invoicesTable) {
		this.invoicesTable = invoicesTable;
	}

	public JTable getInvoiceItemsTable() {
		return invoiceItemsTable;
	}

	public void setInvoiceItemsTable(JTable invoiceItemsTable) {
		this.invoiceItemsTable = invoiceItemsTable;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenuItem getLoadFile() {
		return loadFile;
	}

	public void setLoadFile(JMenuItem loadFile) {
		this.loadFile = loadFile;
	}

	public JMenuItem getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(JMenuItem saveFile) {
		this.saveFile = saveFile;
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
	}

	public JPanel getRightPanel() {
		return rightPanel;
	}

	public void setRightPanel(JPanel rightPanel) {
		this.rightPanel = rightPanel;
	}

	public JPanel getLeftPanelTop() {
		return leftPanelTop;
	}

	public void setLeftPanelTop(JPanel leftPanelTop) {
		this.leftPanelTop = leftPanelTop;
	}

	public JPanel getLeftPanelBottom() {
		return leftPanelBottom;
	}

	public void setLeftPanelBottom(JPanel leftPanelBottom) {
		this.leftPanelBottom = leftPanelBottom;
	}

	public JPanel getRightPanelTop() {
		return rightPanelTop;
	}

	public void setRightPanelTop(JPanel rightPanelTop) {
		this.rightPanelTop = rightPanelTop;
	}

	public JPanel getRightPanelMiddle() {
		return rightPanelMiddle;
	}

	public void setRightPanelMiddle(JPanel rightPanelMiddle) {
		this.rightPanelMiddle = rightPanelMiddle;
	}

	public JPanel getRightPanelBottom() {
		return rightPanelBottom;
	}

	public void setRightPanelBottom(JPanel rightPanelBottom) {
		this.rightPanelBottom = rightPanelBottom;
	}

	public Vector<Vector<String>> getInvoicesTableData() {
		return invoicesTableData;
	}

	public void setInvoicesTableData(Vector<Vector<String>> invoicesTableData) {
		this.invoicesTableData = invoicesTableData;
	}

	public Vector<String> getInvoicesTableColumnNames() {
		return invoicesTableColumnNames;
	}

	public void setInvoicesTableColumnNames(Vector<String> invoicesTableColumnNames) {
		this.invoicesTableColumnNames = invoicesTableColumnNames;
	}

	public Vector<Vector<String>> getInvoiceItemsData() {
		return invoiceItemsData;
	}

	public void setInvoiceItemsData(Vector<Vector<String>> invoiceItemsData) {
		this.invoiceItemsData = invoiceItemsData;
	}

	public Vector<String> getInvoiceItemsColumnNames() {
		return invoiceItemsColumnNames;
	}

	public void setInvoiceItemsColumnNames(Vector<String> invoiceItemsColumnNames) {
		this.invoiceItemsColumnNames = invoiceItemsColumnNames;
	}

	public JButton getAddItemBtn() {
		return addItemBtn;
	}

	public void setAddItemBtn(JButton addItemBtn) {
		this.addItemBtn = addItemBtn;
	}

	public JButton getRemoveItemBtn() {
		return removeItemBtn;
	}

	public void setRemoveItemBtn(JButton removeItemBtn) {
		this.removeItemBtn = removeItemBtn;
	}

	public DefaultTableModel getInvoicesTableModel() {
		return invoicesTableModel;
	}

	public void setInvoicesTableModel(DefaultTableModel invoicesTableModel) {
		this.invoicesTableModel = invoicesTableModel;
	}

	public DefaultTableModel getItemsTableModel() {
		return itemsTableModel;
	}

	public void setItemsTableModel(DefaultTableModel itemsTableModel) {
		this.itemsTableModel = itemsTableModel;
	}
}

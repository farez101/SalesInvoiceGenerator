package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;
import javax.swing.JOptionPane;
import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.InvoiceInput;
import view.ItemInput;
import view.MainWindow;

public class Control {
	private MainWindow main;
	private Vector<Vector<String>> InvoicesTableData;
	private HashMap<Integer, InvoiceHeader> invoicesIDList = new HashMap<Integer, InvoiceHeader>();
	private ArrayList<InvoiceHeader> invoicesList = new ArrayList<InvoiceHeader>() ;
   
	Control() {
		main = new MainWindow();
		InvoicesTableData = new Vector<Vector<String>>();
		Vector<String> tempInvoice ;
		// load default values
		try {
			// get list from File and convert them to a vector of String to use
		invoicesList = FileOperations.readInitialFile(main.getMainFrame());
		for (int i=0;i<invoicesList.size();i++) {
			tempInvoice = new Vector<String>();
			tempInvoice.add(Integer.toString(invoicesList.get(i).getInvoiceNumber()));
			tempInvoice.add(invoicesList.get(i).getInvoiceDate());
			tempInvoice.add(invoicesList.get(i).getCustomerName());
			tempInvoice.add(invoicesList.get(i).computeCount());
			InvoicesTableData.add(tempInvoice);
		}
		this.storeInvoicesInMap();
		}catch (Exception e) {
			InvoicesTableData = new Vector<Vector<String>>();
			JOptionPane.showMessageDialog(main.getMainFrame(), "Default Values is not loaded correctly", "Default Value Loading",
					JOptionPane.WARNING_MESSAGE);
		}
		// initialize table
		addInvoicesTable(InvoicesTableData);
		
		main.getCreateNewInvoiceBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewInvoice();
			}
		});

		main.getSaveBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String currentInvoiceNum = main.getInvoiceNumberCount().getText();
				Vector <InvoiceLine> lines = new Vector <InvoiceLine>();
				InvoiceLine line;
				Vector<String> row;
				InvoiceHeader invoice = new InvoiceHeader();
				if (Integer.parseInt(currentInvoiceNum) == 0) {
					JOptionPane.showMessageDialog(main.getMainFrame(), "Please Create/Select Invoice First",
							"Add Item Error", JOptionPane.ERROR_MESSAGE);
				}else if (main.getInvoiceItemsTable().getRowCount() == 0) {
					JOptionPane.showMessageDialog(main.getMainFrame(), "Invoics Items Table is Empty", "Save Invoice Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int invoiceNum = Integer.parseInt(main.getInvoiceNumberCount().getText());
					for (int i=0;i<main.getInvoiceItemsTable().getRowCount();i++) {
						line = new InvoiceLine();
						line.setCount((String) main.getInvoiceItemsTable().getValueAt(i, 3));
						line.setItemName((String) main.getInvoiceItemsTable().getValueAt(i, 1));
						line.setItemPrice((String) main.getInvoiceItemsTable().getValueAt(i, 2));
						lines.add(line);
					}
					invoice.setCustomerName(main.getCustomerNameText().getText());
					invoice.setInvoiceLinesList(lines);
					invoice.setInvoiceDate(main.getInvoiceDateText().getText());
					invoice.setInvoiceNumber(invoiceNum);
					row = new Vector<>();
					row.add(Integer.toString(invoiceNum));
					row.add(main.getInvoiceDateText().getText());
					row.add(main.getCustomerNameText().getText());
					row.add(main.getInvoiceTotalCount().getText());
					// check if this is a new invoice or edited invoice
					if (invoicesIDList.containsKey(invoiceNum)) {
						// update edited row
						for (int i=0;i<main.getInvoicesTableModel().getRowCount();i++) {
							if(main.getInvoicesTable().getValueAt(i, 0).equals(main.getInvoiceNumberCount().getText())) {
								main.getInvoicesTableModel().setValueAt(main.getInvoiceTotalCount().getText(), i, 3); // count update
								main.getInvoicesTableModel().setValueAt(main.getCustomerNameText().getText(), i, 2); // name update
								main.getInvoicesTableModel().setValueAt(main.getInvoiceDateText().getText(), i, 1); // date update
							break;
						   }
						}
					}else {
						// add row at end of table for new invoice
						main.getInvoicesTableModel().insertRow(main.getInvoicesTableModel().getRowCount(),row );		
					}
					invoicesIDList.put(invoiceNum,invoice);
					JOptionPane.showMessageDialog(main.getMainFrame(), "Invoices Saved Successfully", "Save Invoice",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		main.getRemoveItemBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (main.getInvoiceItemsTable().getRowCount() == 0) {
					JOptionPane.showMessageDialog(main.getMainFrame(), "Invoics Items Table is Empty", "Delete Item Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int selectedRow = main.getInvoiceItemsTable().getSelectedRow();
					// confirm if a row is selected to remove it
					if (selectedRow != -1) {
						String oldCount = main.getInvoiceTotalCount().getText();
						String selectedRowCount = (String) main.getInvoiceItemsTable().getValueAt(selectedRow, 4);
						int newCount = Integer.parseInt(oldCount) - Integer.parseInt(selectedRowCount);
						main.getInvoiceTotalCount().setText(Integer.toString(newCount));
						// remove item
						main.getItemsTableModel().removeRow(selectedRow);
					}else {
						JOptionPane.showMessageDialog(main.getMainFrame(), "Please select row first to delete", "Delete Item Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}		
			}
		});

		main.getAddItemBtn().addActionListener(new ActionListener() {
			private Vector<String> row;
			private String itemName;
			private String itemPrice;
			private String itemCount;
			private int totalItemPrice;
			@Override
			public void actionPerformed(ActionEvent e) {
				String currentInvoiceNum = main.getInvoiceNumberCount().getText();
				if (Integer.parseInt(currentInvoiceNum) == 0) {
					JOptionPane.showMessageDialog(main.getMainFrame(), "Please Create/Select Invoice First",
							"Add Item Error", JOptionPane.ERROR_MESSAGE);
				} else {
					ItemInput itemIP = new ItemInput(main, "Add Item");
					row = new Vector<String>();
					itemIP.getSubmit().addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// check here that data input not empty
							if (itemIP.getItemCountText().getText().isEmpty()) {
								JOptionPane.showMessageDialog(main.getMainFrame(), "item count can not be empty",
										"Adding Item Error", JOptionPane.ERROR_MESSAGE);
							} else if (itemIP.getItemPriceText().getText().isEmpty()) {
								JOptionPane.showMessageDialog(main.getMainFrame(), "item price can not be empty",
										"Adding Item Error", JOptionPane.ERROR_MESSAGE);
							} else if (itemIP.getItemNameText().getText().isEmpty()) {
								JOptionPane.showMessageDialog(main.getMainFrame(), "item name can not be empty",
										"Adding Item Error", JOptionPane.ERROR_MESSAGE);
							}else
							{
								itemCount = itemIP.getItemCountText().getText();
								itemName = itemIP.getItemNameText().getText();
								itemPrice = itemIP.getItemPriceText().getText();
								row.add(currentInvoiceNum);
								row.add(itemName);
								row.add(itemPrice);
								row.add(itemCount);
								totalItemPrice = Integer.parseInt(itemPrice) * Integer.parseInt(itemCount);
								row.add(Integer.toString(totalItemPrice));
								main.getItemsTableModel().insertRow(main.getItemsTableModel().getRowCount(), row);
								// update total count for invoice
								String oldCount = main.getInvoiceTotalCount().getText();
								int newCount = Integer.parseInt(oldCount) + totalItemPrice;
								main.getInvoiceTotalCount().setText(Integer.toString(newCount));
								itemIP.dispose();
							}
						}
					});
					itemIP.setModal(true);
					itemIP.setVisible(true);
					itemIP.setResizable(false);
				}
			}
		});

		main.getDeleteInvoiceBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (main.getInvoicesTable().getRowCount() == 0) {
					JOptionPane.showMessageDialog(main.getMainFrame(), "Invoics Table is Empty", "Delete Inovice Error",
							JOptionPane.ERROR_MESSAGE);
				} else {					
					int selectedRow = main.getInvoicesTable().getSelectedRow();
					if (selectedRow != -1) {
						int result = JOptionPane.showConfirmDialog(main.getMainFrame(), "Delete Invoice? (Y/N)", "Delete Inovice Confirm",
								JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						if ( result == JOptionPane.YES_OPTION) {
							// remove the invoice from the database of invoices 
						invoicesIDList.remove(Integer.parseInt((String)main.getInvoicesTable().getValueAt(selectedRow, 0)));
						// remove invoice
						main.getInvoicesTableModel().removeRow(selectedRow);
						// clear right panel
						clearItemsPanel();
						JOptionPane.showMessageDialog(main.getMainFrame(), "Invoice Deleted Successfully",
								"Delete Inovice", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(main.getMainFrame(), "Please select row first to delete",
								"Delete Inovice Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		main.getLoadFile().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearItemsPanel();
				InvoicesTableData = new Vector<Vector<String>>();
				Vector<String> loadedInvoice ;
				try {
				invoicesList = new ArrayList<InvoiceHeader>();
				invoicesList = FileOperations.readFile(main.getMainFrame());
				for (int i=0;i<invoicesList.size();i++) {
					loadedInvoice = new Vector<String>();
					loadedInvoice.add(Integer.toString(invoicesList.get(i).getInvoiceNumber()));
					loadedInvoice.add(invoicesList.get(i).getInvoiceDate());
					loadedInvoice.add(invoicesList.get(i).getCustomerName());
					loadedInvoice.add(invoicesList.get(i).computeCount());
					InvoicesTableData.add(loadedInvoice);
				}
				storeInvoicesInMap();
				}catch (Exception e1) {
					InvoicesTableData = new Vector<Vector<String>>();
					JOptionPane.showMessageDialog(main.getMainFrame(), "Values is not loaded correctly", "Default Value Loading",
							JOptionPane.WARNING_MESSAGE);
				}
				// initialize table
				addInvoicesTable(InvoicesTableData);

			}
		});

		main.getSaveFile().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				invoicesList = new ArrayList<InvoiceHeader>();
				for ( Entry<Integer, InvoiceHeader> entry : invoicesIDList.entrySet()) {
					invoicesList.add(entry.getValue()) ;
				}
				FileOperations.writeFile(invoicesList,main.getMainFrame());
			}
		});

		main.getInvoicesTable().addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int totalItemPrice = 0;
				@SuppressWarnings("unchecked")
				Vector<String> temp = main.getInvoicesTableModel().getDataVector().elementAt(
						main.getInvoicesTable().convertRowIndexToModel(main.getInvoicesTable().getSelectedRow()));
				main.getInvoiceTotalCount().setText(temp.get(3));
				main.getCustomerNameText().setText(temp.get(2));
				main.getInvoiceDateText().setText(temp.get(1));
				main.getInvoiceNumberCount().setText(temp.get(0));//get invoice num
				main.getItemsTableModel().setRowCount(0);
				Vector <String> rowItem;
				for(int i =0; i< invoicesIDList.get(Integer.parseInt(temp.get(0))).getInvoiceLinesList().size(); i++) { // 0 here means invoice number, the first value in temp vector
					rowItem = new Vector <>();
					rowItem.add(temp.get(0)); //num
					rowItem.add(invoicesIDList.get(Integer.parseInt(temp.get(0))).getInvoiceLinesList().get(i).getItemName());// item
					rowItem.add(invoicesIDList.get(Integer.parseInt(temp.get(0))).getInvoiceLinesList().get(i).getItemPrice());// price
					rowItem.add(invoicesIDList.get(Integer.parseInt(temp.get(0))).getInvoiceLinesList().get(i).getCount());// count
					totalItemPrice = Integer.parseInt(invoicesIDList.get(Integer.parseInt(temp.get(0))).getInvoiceLinesList().get(i).getItemPrice()) * Integer.parseInt(invoicesIDList.get(Integer.parseInt(temp.get(0))).getInvoiceLinesList().get(i).getCount());
					rowItem.add(Integer.toString(totalItemPrice));//total
					main.getItemsTableModel().insertRow(main.getItemsTableModel().getRowCount(), rowItem);
				}
			}
		});
	}

	

	/*
	 * 
	 * create new invoice preparation
	 */	
	public void createNewInvoice() {
		int currentInvoiceNumber;
		currentInvoiceNumber = getLatestInvoiceNumber() + 1;
		InvoiceInput invIP = new InvoiceInput(main, "AddInvoice");
		invIP.getSubmit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (invIP.getCusNameText().getText().isEmpty()) {
					JOptionPane.showMessageDialog(main.getMainFrame(), "invoice Customer Name can not be empty",
							"creating Invoice Error", JOptionPane.ERROR_MESSAGE);
				} else if (invIP.getDateText().getText().isEmpty()) {
					JOptionPane.showMessageDialog(main.getMainFrame(), "inovice Date can not be empty",
							"creating Invoice Error", JOptionPane.ERROR_MESSAGE);
				} else {
				main.getInvoiceNumberCount().setText(Integer.toString(currentInvoiceNumber));
				main.getInvoiceTotalCount().setText(Integer.toString(0));
				main.getItemsTableModel().setRowCount(0);
				main.getCustomerNameText().setText(invIP.getCusNameText().getText());
				main.getInvoiceDateText().setText(invIP.getDateText().getText());
				invIP.dispose();
			}}
		});
		invIP.setModal(true);
		invIP.setVisible(true);
		invIP.setResizable(false);
	}

	/*
	 * function to return 
	 */
	public int getLatestInvoiceNumber() {
		/*
		 * we return 0 if invoices table is empty otherwise return the last invoices row
		 * invoiceNumber
		 */
		int tableCount = main.getInvoicesTableModel().getRowCount();
		if (tableCount == 0) {
			return 0;
		} else {
			String temp = (String) main.getInvoicesTableModel().getValueAt(tableCount - 1, 0);
			return Integer.parseInt(temp);
		}
	}
	
/*
 * function to clean the right panel of items and invoice data
 */
	public void clearItemsPanel() {
		main.getInvoiceNumberCount().setText("0");
		main.getItemsTableModel().setRowCount(0);
		main.getInvoiceTotalCount().setText("0");
		main.getCustomerNameText().setText(" ");
		main.getInvoiceDateText().setText(" ");
	}
	
	/*
	 * function to store invoices in a hash map
	 * to save current table of invoice and check if new one is needed
	 * when we add or save invoice
	 */
	public void storeInvoicesInMap() {
		invoicesIDList = new HashMap<Integer, InvoiceHeader>();
		 for (int i=0;i<invoicesList.size();i++) {
			 invoicesIDList.put(invoicesList.get(i).getInvoiceNumber(),invoicesList.get(i));
			}
	}
	public void addInvoicesTable(Vector<Vector<String>> invoicesTableDataList) {
		main.getInvoicesTableModel().setRowCount(0);
		for (int i=0;i<invoicesTableDataList.size();i++) {
			main.getInvoicesTableModel().addRow(invoicesTableDataList.get(i));
		}
	}


	public MainWindow getMain() {
		return main;
	}



	public void setMain(MainWindow main) {
		this.main = main;
	}



	public Vector<Vector<String>> getInvoicesTableData() {
		return InvoicesTableData;
	}



	public void setInvoicesTableData(Vector<Vector<String>> invoicesTableData) {
		InvoicesTableData = invoicesTableData;
	}



	public HashMap<Integer, InvoiceHeader> getInvoicesIDList() {
		return invoicesIDList;
	}



	public void setInvoicesIDList(HashMap<Integer, InvoiceHeader> invoicesIDList) {
		this.invoicesIDList = invoicesIDList;
	}



	public ArrayList<InvoiceHeader> getInvoicesList() {
		return invoicesList;
	}



	public void setInvoicesList(ArrayList<InvoiceHeader> invoicesList) {
		this.invoicesList = invoicesList;
	}
}

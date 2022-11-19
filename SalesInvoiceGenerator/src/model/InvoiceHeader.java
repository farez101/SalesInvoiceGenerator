package model;

import java.util.Vector;

public class InvoiceHeader {
private int invoiceNumber;
private String invoiceDate; // DD-MM-YYYY
private String customerName;
private Vector<InvoiceLine> InvoiceLinesList;

public int getInvoiceNumber() {
	return invoiceNumber;
}
public void setInvoiceNumber(int invoiceNumber) {
	this.invoiceNumber = invoiceNumber;
}
public String getInvoiceDate() {
	return invoiceDate;
}
public void setInvoiceDate(String invoiceDate) {
	this.invoiceDate = invoiceDate;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public Vector<InvoiceLine> getInvoiceLinesList() {
	return InvoiceLinesList;
}
public void setInvoiceLinesList(Vector<InvoiceLine> invoiceLinesList) {
	InvoiceLinesList = invoiceLinesList;
}

public String computeCount() {
	// compute total price for invoice from all items it have
	// done this way to have flexibility adding removing items
	int count =0;
	for (int i=0;i<InvoiceLinesList.size();i++) {
		count += Integer.parseInt(InvoiceLinesList.get(i).getCount()) *  Integer.parseInt(InvoiceLinesList.get(i).getItemPrice())  ;
	}
	return Integer.toString(count);
}

}

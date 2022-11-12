package model;

import java.util.ArrayList;

public class InvoiceHeader {
private int invoiceNumber;
private String invoiceDate; // DD/MM/YYYY
private String customerName;
private ArrayList<InvoiceLine> InvoiceLinesList;

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
public ArrayList<InvoiceLine> getInvoiceLinesList() {
	return InvoiceLinesList;
}
public void setInvoiceLinesList(ArrayList<InvoiceLine> invoiceLinesList) {
	InvoiceLinesList = invoiceLinesList;
}


}

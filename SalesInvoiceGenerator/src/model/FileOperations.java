package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FileOperations {

	private static JFileChooser fileChooser = new JFileChooser();
	private static JFileChooser fileChooserItems = new JFileChooser();

	public static ArrayList<InvoiceHeader> readFile(JFrame parent) throws FileNotFoundException {
		HashMap<Integer, InvoiceHeader> invoicesMap = new HashMap<Integer, InvoiceHeader>();
		ArrayList<InvoiceHeader> invoices = new ArrayList<InvoiceHeader>();
		try {
			String line = "";
			String splitBy = ",";
			fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Load Invoices Table");
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src"));
			int result = fileChooser.showOpenDialog(parent);
			fileChooserItems = new JFileChooser();
			fileChooserItems.setDialogTitle("Load Invoices Items");
			fileChooserItems.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src"));
			int resultItems = fileChooserItems.showOpenDialog(parent);
			if (result == JFileChooser.APPROVE_OPTION) {
				// user selects a file
				File selectedFile = fileChooser.getSelectedFile();
				File selectedFileItems = fileChooserItems.getSelectedFile();

//				System.out.println(	fileChooser.getCurrentDirectory());
				BufferedReader br = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
				@SuppressWarnings("resource")
				BufferedReader brItems = new BufferedReader(new FileReader(selectedFileItems.getAbsolutePath()));

//				BufferedReader brItems = new BufferedReader(
//						new FileReader(fileChooser.getCurrentDirectory() + "/InvoiceLine.csv"));
				// add invoices
				while ((line = br.readLine()) != null) {
					String[] invoice = line.split(splitBy); // use comma as separator
					InvoiceHeader h = new InvoiceHeader();
					h.setCustomerName(invoice[2]);
					h.setInvoiceDate(invoice[1]);
					h.setInvoiceNumber(Integer.parseInt(invoice[0]));
					h.setInvoiceLinesList(new Vector<>());
					invoicesMap.put(Integer.parseInt(invoice[0]), h);
				}
				// add items
				while ((line = brItems.readLine()) != null) // returns a Boolean value
				{
					String[] item = line.split(splitBy);
					InvoiceLine l = new InvoiceLine();
					l.setCount(item[3]);
					l.setItemName(item[1]);
					l.setItemPrice(item[2]);
					invoicesMap.get(Integer.parseInt(item[0])).getInvoiceLinesList().add(l);
					invoicesMap.get(Integer.parseInt(item[0])).computeCount();

				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parent, "File is not found", "Load File Error", JOptionPane.ERROR_MESSAGE);
		}

		for (Entry<Integer, InvoiceHeader> entry : invoicesMap.entrySet()) {
			invoices.add(entry.getValue());
		}
		return invoices;
	}

	public static ArrayList<InvoiceHeader> readInitialFile(JFrame parent) {

		HashMap<Integer, InvoiceHeader> invoicesMap = new HashMap<Integer, InvoiceHeader>();
		ArrayList<InvoiceHeader> invoices = new ArrayList<InvoiceHeader>();
		try {
			String line = "";
			String splitBy = ",";
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/defaultCSV/InvoiceHeader.csv"));
			@SuppressWarnings("resource")
			BufferedReader brItems = new BufferedReader(
					new FileReader(System.getProperty("user.dir") + "/src/defaultCSV/InvoiceLine.csv"));
			// add invoices
			while ((line = br.readLine()) != null) {
				String[] invoice = line.split(splitBy); // use comma as separator
				InvoiceHeader h = new InvoiceHeader();
				h.setCustomerName(invoice[2]);
				h.setInvoiceDate(invoice[1]);
				h.setInvoiceNumber(Integer.parseInt(invoice[0]));
				h.setInvoiceLinesList(new Vector<>());
				invoicesMap.put(Integer.parseInt(invoice[0]), h);
			}
			// add items
			while ((line = brItems.readLine()) != null) // returns a Boolean value
			{
				String[] item = line.split(splitBy);
				InvoiceLine l = new InvoiceLine();
				l.setCount(item[3]);
				l.setItemName(item[1]);
				l.setItemPrice(item[2]);
				invoicesMap.get(Integer.parseInt(item[0])).getInvoiceLinesList().add(l);
				invoicesMap.get(Integer.parseInt(item[0])).computeCount();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(parent, "Default File Loading Failed", "Load File Error", JOptionPane.ERROR_MESSAGE);
		}

		for (Entry<Integer, InvoiceHeader> entry : invoicesMap.entrySet()) {
			invoices.add(entry.getValue());
		}
		return invoices;
	}

	public static void writeFile(ArrayList<InvoiceHeader> InvoiceHeaderList, JFrame parent) {
String pathInvoices = System.getProperty("user.dir") + "/src/outputCSV/InvoiceHeader.csv";
String pathItems = System.getProperty("user.dir") + "/src/outputCSV/InvoiceLine.csv";

try {
	FileWriter writer = new FileWriter(pathInvoices);
	FileWriter writer2 = new FileWriter(pathItems);
	for(int i=0;i<InvoiceHeaderList.size();i++ ) {
		writer.append(Integer.toString(InvoiceHeaderList.get(i).getInvoiceNumber())); writer.append(",");	
		writer.append(InvoiceHeaderList.get(i).getInvoiceDate()); writer.append(",");	
		writer.append(InvoiceHeaderList.get(i).getCustomerName());
	    writer.append("\n");
		for(int j=0;j<InvoiceHeaderList.get(i).getInvoiceLinesList().size();j++ ) {
			writer2.append(Integer.toString(InvoiceHeaderList.get(i).getInvoiceNumber()));writer2.append(",");
			writer2.append(InvoiceHeaderList.get(i).getInvoiceLinesList().get(j).getItemName());writer2.append(",");
			writer2.append(InvoiceHeaderList.get(i).getInvoiceLinesList().get(j).getItemPrice());writer2.append(",");
			writer2.append(InvoiceHeaderList.get(i).getInvoiceLinesList().get(j).getCount());
		    writer2.append("\n");
		}
}
	writer.close();
    writer2.close();
}catch (IOException e) {
	JOptionPane.showMessageDialog(parent, "File is not Saved Correctly", "Write File Error", JOptionPane.ERROR_MESSAGE);
}
JOptionPane.showMessageDialog(parent, "File is Saved Successfully", "Write File", JOptionPane.INFORMATION_MESSAGE);
	}
}

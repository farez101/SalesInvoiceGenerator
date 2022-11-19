package view;

import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ItemInput extends JDialog {
	private JLabel ItemName, ItemPrice, ItemCount;
	private JButton submit;
	private JTextField ItemNameText, ItemPriceText, ItemCountText;

	public ItemInput(Frame owner, String title) {
		super(owner,title);
		this.setSize(600, 100);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new FlowLayout());

		submit = new JButton("Add Item");
		ItemName = new JLabel("Item Name");
		ItemPrice = new JLabel("Item Price");
		ItemCount = new JLabel("Item Count");
		ItemNameText = new JTextField(15);
		ItemPriceText = new JTextField(7);
		ItemCountText = new JTextField(5);

		this.add(ItemName);
		this.add(ItemNameText);
		this.add(ItemPrice);
		this.add(ItemPriceText);
		this.add(ItemCount);
		this.add(ItemCountText);
		this.add(submit);

	}

	public JButton getSubmit() {
		return submit;
	}

	public void setSubmit(JButton submit) {
		this.submit = submit;
	}

	public JLabel getItemName() {
		return ItemName;
	}

	public void setItemName(JLabel itemName) {
		ItemName = itemName;
	}

	public JLabel getItemPrice() {
		return ItemPrice;
	}

	public void setItemPrice(JLabel itemPrice) {
		ItemPrice = itemPrice;
	}

	public JLabel getItemCount() {
		return ItemCount;
	}

	public void setItemCount(JLabel itemCount) {
		ItemCount = itemCount;
	}

	public JTextField getItemNameText() {
		return ItemNameText;
	}

	public void setItemNameText(JTextField itemNameText) {
		ItemNameText = itemNameText;
	}

	public JTextField getItemPriceText() {
		return ItemPriceText;
	}

	public void setItemPriceText(JTextField itemPriceText) {
		ItemPriceText = itemPriceText;
	}

	public JTextField getItemCountText() {
		return ItemCountText;
	}

	public void setItemCountText(JTextField itemCountText) {
		ItemCountText = itemCountText;
	}


}

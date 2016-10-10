// This code gives the layout for the UI and
//   demonstrates two ways of updating the data
//   in a JTable.
// Another option to consider when using JTable is
//   creating your own data model by overriding
//   AbstractTableModel. You might use this option
//   if data for table was coming from say a DB.
//   One example: http://www.java2s.com/Code/Java/Swing-JFC/CreatingsimpleJTableusingAbstractTableModel.htm

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AccountTransactionLayout extends JFrame {

	private JTable table;
	
	private String[] columnNames = {"Account ID",
            "Account Name",
            "Balance"};
	
	private Object[][] data; 
	
	//private ArrayList<AccountEntry> dbData = new mysqlHelper().getData();
	
	/**
	private Object[][] data = {
			{new Integer(3), "Savings", new Integer(500)},
			{new Integer(4), "Checking", new Integer(270)}};
	**/
	public AccountTransactionLayout(ArrayList<AccountEntry> entries) {
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		Object[][] data = {
				{entries.get(0).getAccount_id() , entries.get(0).getAccount_name() , entries.get(0).getAccount_balance()},
				{entries.get(1).getAccount_id() , entries.get(1).getAccount_name() , entries.get(1).getAccount_balance()},
				{entries.get(2).getAccount_id() , entries.get(2).getAccount_name() , entries.get(2).getAccount_balance()},
				{entries.get(3).getAccount_id() , entries.get(3).getAccount_name() , entries.get(3).getAccount_balance()}
		};
		
		
		
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		table = new JTable(dtm);
		// The default size of a JTable is something like
		// 450 X 400.
		Dimension smallerSize = new Dimension(450, 50);
		table.setPreferredScrollableViewportSize(smallerSize );
		
		JScrollPane scrollPaneForTable = new JScrollPane(table);
				
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(4, 4, 4, 4);
		constraints.fill = GridBagConstraints.BOTH;

		contentPane.add(scrollPaneForTable,constraints);
		
		constraints.gridx = 0;
//		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(2, 4, 2, 4);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel toLabel = new JLabel("From:");
		contentPane.add(toLabel,constraints);
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField fromField = new JTextField("3",8);
		// Workaround, because of: http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4247013
		fromField.setMinimumSize(fromField.getPreferredSize());
		contentPane.add(fromField,constraints);
		
		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel fromLabel = new JLabel("To:");
		contentPane.add(fromLabel,constraints);
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField toField = new JTextField("4",8);
		toField.setMinimumSize(toField.getPreferredSize());
		contentPane.add(toField,constraints);

		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel amountLabel = new JLabel("Amount:");
		contentPane.add(amountLabel,constraints);
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField amountField = new JTextField("100",8);
		amountField.setMinimumSize(amountField.getPreferredSize());
		contentPane.add(amountField,constraints);

		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JButton clearButton = new JButton("Clear");
		contentPane.add(clearButton,constraints);
		// ATTENTION!!! The action here is just another
		//   example of how to update JTable. It is
		//   certainly not the logic for clearing the
		//   values in the GUI.
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dtm = (DefaultTableModel) (table.getModel());
				int row = 0;
				int column = 2;
				// Example of how to read/write values from/to a JTable
				System.out.println("Old value: " + dtm.getValueAt(row, column));
				dtm.setValueAt(new Integer(999), row, column);
			}
		});
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JButton transferButton = new JButton("Transfer");
		transferButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/**Object[][] newData = {
						{new Integer(3), "Savings", new Integer(400)},
						{new Integer(4), "Checking", new Integer(370)}};
						**/
				// Example of how to change the table model of an
				//   existing JTable
				int toNum, fromNum, amount, toBal, fromBal;
				toNum = Integer.parseInt(toField.getText());
				fromNum = Integer.parseInt(fromField.getText());
				amount = Integer.parseInt(amountField.getText());
				
				
				toBal = (int) data[toNum - 1][2];
				fromBal = (int) data[fromNum - 1][2];
				if(fromBal - amount >= 0){
					entries.get(toNum - 1).setAccount_balance(toBal + amount);
					entries.get(fromNum - 1).setAccount_balance(fromBal - amount);
					mysqlHelper.updateAll(entries);
				}
				
				Object[][] data = {
						{entries.get(0).getAccount_id() , entries.get(0).getAccount_name() , entries.get(0).getAccount_balance()},
						{entries.get(1).getAccount_id() , entries.get(1).getAccount_name() , entries.get(1).getAccount_balance()},
						{entries.get(2).getAccount_id() , entries.get(2).getAccount_name() , entries.get(2).getAccount_balance()},
						{entries.get(3).getAccount_id() , entries.get(3).getAccount_name() , entries.get(3).getAccount_balance()}
				};
				
				table.setModel(new DefaultTableModel(data,columnNames));
				
			}
			
		});
		contentPane.add(transferButton,constraints);
	}

	public static void main(String[] args) {
		
		
		try {
			mysqlHelper helper = new mysqlHelper();
			ArrayList<AccountEntry> theData = helper.getData();
			JFrame frame = new AccountTransactionLayout(theData);
			frame.pack();
			frame.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("helper failed");
			e.printStackTrace();
		}
	}
}
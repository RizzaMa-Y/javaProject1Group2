package package1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class Customers extends JInternalFrame {
	Connection con1;
	Statement st1;
	PreparedStatement pst1, pst2;
	ResultSet rs1;
	CallableStatement call1;

	private static Orders orders;

	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField textSearch;
	private JTextArea txtAddress;
	private JTable tblOrderItems;
	private TableRowSorter sorter;
	private HashMap<Integer, Integer> iDHolder;
	private HashMap<Integer, String> warehouseName;
	private JButton btnNewButton;
	private JButton btnUpdateInformation;
	private JPanel panel;
	private JLabel lblProductId;
	private JLabel lblEmpFirst;
	private JLabel lblPrice;
	private JScrollPane scrollPane;
	private JLabel lblDescription;
	private JButton btnClear;
	private JScrollPane scrollPane_1;
	private JPanel panel_1;
	private JTextField textEmail;
	private JLabel lblItemCnt;
	private JLabel lblDescription_3;
	private JLabel lblTotalAmount;

	private JLabel lblDescription_2;
	public JLabel lblOrderID;

	public int CurrentOrderID = 0;

	private int itemorderID;
	private int itemoderqty;
	private int itemoderProdID;
	private JButton btnAddOrders;
	private JButton btnCancelOrder;
	private JButton btnConfirm;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {

					Customers frame = new Customers(orders);
					//frame.loadWarehouse();

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
	public Customers(Orders orders) {
		Customers.orders = orders;

		setTitle("Customer Information");
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setClosable(true);
		setBounds(100, 100, 842, 450);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Basic Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 10, 352, 310);
		getContentPane().add(panel);

		lblProductId = new JLabel("Client Id");
		lblProductId.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblProductId.setBounds(24, 29, 111, 22);
		panel.add(lblProductId);

		txtID = new JTextField();
		txtID.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(145, 33, 179, 19);
		txtID.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonState();
            }

            private void updateButtonState() {
            	btnNewButton.setEnabled(txtID.getText().isEmpty());
            	//btnDelete.setEnabled(!txtID.getText().isEmpty());
            	btnUpdateInformation.setEnabled(!txtID.getText().isEmpty());
            	btnAddOrders.setEnabled(!txtID.getText().isEmpty());

            	btnClear.setEnabled(!txtID.getText().isEmpty());
            	int selectedRow = tblOrderItems.getSelectedRow();
            	System.out.println("rowcount:" + selectedRow);
                if (selectedRow != -1) {
                	btnCancelOrder.setEnabled(!txtID.getText().isEmpty());
                	btnConfirm.setEnabled(!txtID.getText().isEmpty());
                }
            }
        });


		panel.add(txtID);

		lblEmpFirst = new JLabel("Name");
		lblEmpFirst.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEmpFirst.setBounds(24, 61, 111, 22);
		panel.add(lblEmpFirst);

		txtName = new JTextField();
		txtName.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtName.setColumns(10);
		txtName.setBounds(145, 65, 179, 19);
		panel.add(txtName);

		lblPrice = new JLabel("Phone");
		lblPrice.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblPrice.setBounds(24, 93, 111, 22);
		panel.add(lblPrice);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtPhone.setColumns(10);
		txtPhone.setBounds(145, 97, 179, 19);
		txtPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c = evt.getKeyChar();
				if (!(Character.isDigit(c))) {
					evt.consume();
				}else if (txtPhone.getText().length() >= 11) {
                    evt.consume();
				}
			}
		});
		panel.add(txtPhone);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(145, 157, 179, 50);
		panel.add(scrollPane);

		txtAddress = new JTextArea();
		scrollPane.setViewportView(txtAddress);

		lblDescription = new JLabel("Address");
		lblDescription.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDescription.setBounds(24, 157, 111, 22);
		panel.add(lblDescription);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEmail.setBounds(24, 125, 111, 22);
		panel.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		textEmail.setColumns(10);
		textEmail.setBounds(145, 129, 179, 19);
		panel.add(textEmail);

		btnNewButton = new JButton("Add New");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name,address,email,phone;
				int newOrderID = 0, newCustomerID = 0;
				name = txtName.getText().trim();
				phone = txtPhone.getText().trim();
				address = txtAddress.getText().trim();
				email = textEmail.getText().trim();

				if(name.length() == 0 || phone.length() == 0 ||  email.length() == 0 || address.length() == 0) {

					 JOptionPane.showMessageDialog(null,"Please provide information for the customer",
					 "Customer Creation",JOptionPane.INFORMATION_MESSAGE);

				}else {
					boolean check = EmailValidation.isValid(email);
					if (check) {
						 try {

							connect();
		//						pst1 = con1.prepareStatement("INSERT INTO `customers`( `name`, `email`, `phone`, `address`) VALUES (?,?,?,?)");
							call1 = con1.prepareCall("{ CALL `InsertCustomerAndOrdersID`(?, ?, ?, ?, ?, ?)}");
							call1.setString(1,name);
							call1.setString(2,email);
							call1.setString(3,phone);
							call1.setString(4,address);
							//call1.setInt(5, newOrderID);

							call1.registerOutParameter(5, Types.INTEGER);

							call1.execute();

							newOrderID = call1.getInt(5);
							newCustomerID = call1.getInt(6);

							CurrentOrderID = newOrderID;

							System.out.println("\nNew Order ID: " + newOrderID);
							txtID.setText(String.valueOf(newCustomerID));
							lblOrderID.setText(String.valueOf(CurrentOrderID));

							JOptionPane.showMessageDialog(null,"New Customer has been Added!","Result",JOptionPane.INFORMATION_MESSAGE);

							call1.close();

							disconnect();
							//clearForm();
							load_tbl();
						 }catch(SQLException ex2) {
								ex2.printStackTrace();
						 }
					}else {
						 JOptionPane.showMessageDialog(null,"Please provide a valid email address",
								 "Customer Creation",JOptionPane.INFORMATION_MESSAGE);
					}
				 }

			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setBounds(53, 330, 121, 27);
		getContentPane().add(btnNewButton);

		btnUpdateInformation = new JButton("Update");
		btnUpdateInformation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID,name,address,email,phone;
				int xPhone,cid;
				name = txtName.getText().trim();
				phone = txtPhone.getText().trim();
				address = txtAddress.getText().trim();
				email = textEmail.getText().trim();
				ID = txtID.getText().trim();

				if(name.length() == 0 || phone.length() == 0 ||  email.length() == 0 || address.length() == 0) {
					 JOptionPane.showMessageDialog(null,"Please provide information for the customer",
					 "Customer Creation",JOptionPane.INFORMATION_MESSAGE);

				}else {
					 try {
						//xPhone = Integer.parseInt(phone);
						cid = Integer.parseInt(ID);
						connect();
						pst1 = con1.prepareStatement("UPDATE `customers` SET `name`=?,`email`=?,`phone`=?,`address`=? WHERE `customer_id`=?");
						pst1.setString(1,name);
						pst1.setString(2,email);
						pst1.setString(3,phone);
						pst1.setString(4,address);
						pst1.setInt(5,cid);

						pst1.executeUpdate();
						pst1.close();

						JOptionPane.showMessageDialog(null,"Customer Information updated!","Result",JOptionPane.INFORMATION_MESSAGE);

						disconnect();
						//clearForm();
						load_tbl();
					 } catch (SQLException e2) {
					 // TODO: handle exception
						 e2.printStackTrace();
					 }
				 }

			}
		});
		btnUpdateInformation.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdateInformation.setEnabled(false);
		btnUpdateInformation.setBounds(215, 330, 121, 27);
		getContentPane().add(btnUpdateInformation);

		btnClear = new JButton("Clear");
		btnClear.setEnabled(false);
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "Would you like to process a new transaction?",
				"Product Item",JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					System.out.println("Item ID:" +itemorderID + "\nitem in bucket:" + itemoderqty);

					try {
						connect();
						call1 = con1.prepareCall("{ CALL `cancelTransaction`(?)}");
//						call1.setInt(1,itemorderID);
						call1.setInt(1,CurrentOrderID);
//						call1.setInt(2,itemoderqty);
//						call1.setInt(3,itemoderProdID);

						call1.execute();

						disconnect();
					    //load_tbl();
					} catch (SQLException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					clearForm();
					clearTable();
				}
			}
		});
		btnClear.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnClear.setBounds(53, 367, 121, 27);
		getContentPane().add(btnClear);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(372, 10, 447, 402);
		getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Order Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 41, 427, 166);
		panel_1_1.add(scrollPane_1);

		tblOrderItems = new JTable();
//		tblOrderItems.addContainerListener(new ContainerAdapter() {
//			@Override
//			public void componentAdded(ContainerEvent e) {
//
//			}
//		});
		tblOrderItems.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String itemtempid[];
                int selectedRow = tblOrderItems.getSelectedRow();
                //System.out.println("TESTrows:" + selectedRow);
                if (selectedRow != -1) {
//                	txtID.setText((String) tblOrderItems.getValueAt(selectedRow, 0).toString());
                	itemorderID = ((int) tblOrderItems.getValueAt(selectedRow, 0));
                	itemoderqty = ((int) tblOrderItems.getValueAt(selectedRow, 1));
                	itemtempid = ((String) tblOrderItems.getValueAt(selectedRow, 2)).split("_");
//                	txtName.setText((String) tblOrderItems.getValueAt(selectedRow, 1));
//                	txtPhone.setText((String) tblOrderItems.getValueAt(selectedRow, 2).toString());
//                	txtAddress.setText((String) tblOrderItems.getValueAt(selectedRow, 5));
//
                	//System.out.println(itemtempid[0]);
                	itemoderProdID = Integer.parseInt(itemtempid[0]);
                	btnCancelOrder.setEnabled(true);
                	
                }
			}
		});

		scrollPane_1.setViewportView(tblOrderItems);

		btnAddOrders = new JButton("Add Orders");
		btnAddOrders.setEnabled(false);
		btnAddOrders.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				orders.oderID = CurrentOrderID;
				orders.xCustomers = Customers.this;
				orders.setVisible(true);
				orders.load_tbl();
				orders.toFront();

			}
		});
		btnAddOrders.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnAddOrders.setBounds(153, 358, 121, 27);
		panel_1_1.add(btnAddOrders);

		btnCancelOrder = new JButton("Cancel Order");
		btnCancelOrder.setEnabled(false);
		btnCancelOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println(CurrentOrderID);
				int n = JOptionPane.showConfirmDialog(null, "Would you like to cancel this purchase?",
				"Product Item",JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					System.out.println("Item ID:" +itemorderID + "\nitem in bucket:" + itemoderqty);

					try {
						connect();
						call1 = con1.prepareCall("{ CALL `cancelOrder`(?)}");
						call1.setInt(1,itemorderID);
//						call1.setInt(1,CurrentOrderID);
//						call1.setInt(2,itemoderqty);
//						call1.setInt(3,itemoderProdID);

						call1.execute();

						disconnect();
					    load_tbl();
					} catch (SQLException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}

				}
			}
		});
		btnCancelOrder.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnCancelOrder.setBounds(284, 358, 138, 27);
		panel_1_1.add(btnCancelOrder);

		JLabel lblDescription_1 = new JLabel("Total ordered Items");
		lblDescription_1.setBounds(20, 294, 138, 17);
		panel_1_1.add(lblDescription_1);
		lblDescription_1.setFont(new Font("Century Gothic", Font.BOLD, 13));

		lblItemCnt = new JLabel("0");
		lblItemCnt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblItemCnt.setBounds(311, 294, 111, 22);
		panel_1_1.add(lblItemCnt);

		lblDescription_3 = new JLabel("Total Amount");
		lblDescription_3.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDescription_3.setBounds(20, 323, 111, 22);
		panel_1_1.add(lblDescription_3);

		lblTotalAmount = new JLabel("0.00");
		lblTotalAmount.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblTotalAmount.setBounds(311, 326, 111, 22);
		panel_1_1.add(lblTotalAmount);

		panel_1 = new JPanel();
		panel_1.setBounds(10, 217, 427, 67);
		panel_1_1.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search Table", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		textSearch = new JTextField();
		textSearch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		textSearch.setColumns(10);
		textSearch.setBounds(10, 23, 407, 22);
		textSearch.getDocument().addDocumentListener(new DocumentListener(){
			@Override
	         public void insertUpdate(DocumentEvent e) {
	            search(textSearch.getText());
	         }
	         @Override
	         public void removeUpdate(DocumentEvent e) {
	            search(textSearch.getText());
	         }
	         @Override
	         public void changedUpdate(DocumentEvent e) {
	            search(textSearch.getText());
	         }
	         public void search(String str) {
	            if (str.length() == 0) {
	               sorter.setRowFilter(null);
	            } else {
	               sorter.setRowFilter(RowFilter.regexFilter(str));
	            }
	         }
		});

		panel_1.add(textSearch);

		lblDescription_2 = new JLabel("Order ID:");
		lblDescription_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescription_2.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDescription_2.setBounds(255, 12, 81, 17);
		panel_1_1.add(lblDescription_2);

		lblOrderID = new JLabel("0");
		lblOrderID.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOrderID.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblOrderID.setBounds(359, 9, 63, 22);
		panel_1_1.add(lblOrderID);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		btnConfirm.setBounds(215, 367, 121, 27);
		getContentPane().add(btnConfirm);
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//load_tbl();
				int n = JOptionPane.showConfirmDialog(null, "Would you like to proceed with the purchase?",
				"Orders Complete",JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					try {
						String price;
						Float xTotalPrice;
						price = lblTotalAmount.getText().trim();
						xTotalPrice = Float.parseFloat(price.replace(",", ""));
						connect();
						call1 = con1.prepareCall("{ CALL `CompleteOrders`(?,?)}");
						call1.setInt(1,CurrentOrderID);
						call1.setFloat(2,xTotalPrice);
						call1.execute();

						disconnect();
					} catch (SQLException e2) {
						// TODO: handle exception
					}
					clearForm();
					clearTable();
					orders.load_tbl();
				}
			}
		});
		btnConfirm.setFont(new Font("Century Gothic", Font.BOLD, 14));


		//load_tbl();
	}

	public void load_tbl() {
		//reset table data
		if(!(tblOrderItems.getModel().getRowCount() == 0)) {
			DefaultTableModel modelx = (DefaultTableModel) tblOrderItems.getModel();
			modelx.setRowCount(0);
			System.out.print("\nTable reset orders data...");
		}

		//add the table info and look
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
        model.addColumn("QTY");
        model.addColumn("Product Name");
        model.addColumn("Price");
        model.addColumn("Total Price");

		try {
			connect();

			pst1 = con1.prepareStatement("SELECT o.*, p.name as `ProductName`, p.price as `ProductPrice` FROM `orderitems` as `o` "
					+ "join `products` as `p` where o.order_id=? and p.product_id = o.product_id");
			pst1.setInt(1,CurrentOrderID);
			rs1 = pst1.executeQuery();
//			tableEMP.setModel(DbUtils.resultSetToTableModel(rs1));
			int itemcnt = 0;
			double totalAll = 0.00;
			while (rs1.next()) {
                Double pricePerItem = rs1.getDouble("Price");
//                BigDecimal qtyitem= new BigDecimal(rs1.getInt("quantity"));
                //BigDecimal totalPrice = pricePerItem.multiply(qtyitem);
                int iOid,qtyitem;
                double iPerPrice;
                String pName,priceDisplay,totalPriceDisp;

                iOid = rs1.getInt("order_item_id");
                pName = rs1.getString("product_id") + "_"+ rs1.getString("ProductName");
                iPerPrice= rs1.getInt("ProductPrice");
                qtyitem = rs1.getInt("quantity");

                priceDisplay = String.format("%,.2f",iPerPrice);
                totalPriceDisp = String.format("%,.2f",pricePerItem);
                Object[] row = new Object[5];
                row[0] = iOid;
                row[1] = qtyitem;
                row[2] = pName;
                row[3] = priceDisplay;
                row[4] = totalPriceDisp;
                itemcnt = itemcnt + qtyitem;
                totalAll = pricePerItem + totalAll;
                model.addRow(row);

            }
			lblItemCnt.setText(String.valueOf(itemcnt));
			lblTotalAmount.setText(String.format("%,.2f",totalAll));
			sorter = new TableRowSorter<>(model);
			tblOrderItems.setModel(model);
			tblOrderItems.setRowSorter(sorter);

			rs1.close();
			st1.close();
			disconnect();
			
			if (itemcnt != 0 ) {
				btnConfirm.setEnabled(true);
			}else {
				btnConfirm.setEnabled(false);
			}
		}catch(SQLException e2) {
			e2.printStackTrace();
		}
	}



	public void connect() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/pwcdb1","root","");
			st1 = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

		}catch(ClassNotFoundException ex){

		}catch(Exception e){
			System.out.print(e.toString());
		}
	}

	public void disconnect() {

		try {

			st1.close();
			con1.close();
			//JOptionPane.showMessageDialog(null, "Disconnected");
		} catch (Exception e) {
			System.out.print(e.toString());

		}

	}

	public static void displayHashMap(HashMap<Integer, String> map) {
        // Iterating using a for-each loop
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
	public void clearForm() {
		txtName.setText("");
		txtID.setText("");
		txtAddress.setText("");
		txtPhone.setText("");
		textEmail.setText("");
		lblOrderID.setText("0");
		lblItemCnt.setText("0");
		lblTotalAmount.setText("0.00");
		btnCancelOrder.setEnabled(false);
		btnConfirm.setEnabled(false);
		btnAddOrders.setEnabled(false);
		btnUpdateInformation.setEnabled(false);
		btnClear.setEnabled(false);
		
	}

	public void clearTable() {
		if(!(tblOrderItems.getModel().getRowCount() == 0)) {
			DefaultTableModel modelx = (DefaultTableModel) tblOrderItems.getModel();
			modelx.setRowCount(0);
			System.out.print("Table reset data...");
		}
	}

//	public void allowItemButtons() {
//		int selectedRow = tblOrderItems.getSelectedRow();
//		System.out.println("Rows:" + selectedRow);
//        if (selectedRow != -1) {
//        	//btnCancelOrder.setEnabled(true);
//        	btnConfirm.setEnabled(true);
//        }
//	}

	public class EmailValidation {
	    public static boolean isValid(String email) {
	        String emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\."
	                + "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

	        Pattern p = Pattern.compile(emailFormat);
	        if (email == null) {
	            return false;
	        }
	        return p.matcher(email).matches();
	    }
	}
}

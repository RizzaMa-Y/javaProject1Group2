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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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


public class Orders extends JInternalFrame {
	Connection con1;
	Statement st1;
	PreparedStatement pst1, pst2;
	ResultSet rs1;
	CallableStatement call1;

	public Customers xCustomers;

	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField textSearch;
	private JTextArea txtDesc;
	private JTable tblProduct;
	private TableRowSorter sorter;
	private JTextField txtQty;
	private HashMap<Integer, Integer> iDHolder;
	private HashMap<Integer, String> warehouseName;
	private JComboBox<String> cboBranch;
	private JButton btnNewButton;
	private JLabel lblQuantity;
	private JPanel panel;
	private JLabel lblProductId;
	private JLabel lblEmpFirst;
	private JLabel lblPrice;
	private JScrollPane scrollPane;
	private JLabel lblDescription;
	private JLabel lblEMPBranch;
	private JScrollPane scrollPane_1;
	private JPanel panel_1;
	private String[] warehouseNamesArray = loadWarehouse();
	private JLabel lblQuantityForPurchase;
	private JTextField txtOrderCnt;
	private JLabel lblPriceAmount;
	public int oderID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {

					Orders frame = new Orders();
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
	public Orders() {
		setTitle("Product Items");
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setClosable(true);
		setBounds(100, 100, 842, 378);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Basic Product Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 10, 352, 260);
		getContentPane().add(panel);

		lblProductId = new JLabel("Product Id");
		lblProductId.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblProductId.setBounds(24, 29, 111, 22);
		panel.add(lblProductId);

		txtID = new JTextField();
		txtID.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(145, 33, 179, 19);

		panel.add(txtID);

		lblEmpFirst = new JLabel("Name");
		lblEmpFirst.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEmpFirst.setBounds(24, 61, 111, 22);
		panel.add(lblEmpFirst);

		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtName.setColumns(10);
		txtName.setBounds(145, 65, 179, 19);
		panel.add(txtName);

		lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblPrice.setBounds(24, 127, 111, 22);
		panel.add(lblPrice);

		txtPrice = new JTextField();
		txtPrice.setEditable(false);
		txtPrice.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtPrice.setColumns(10);
		txtPrice.setBounds(145, 131, 179, 19);
		txtPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c = evt.getKeyChar();
                String text = txtPrice.getText();
				// Allow digits and a single decimal point
                if (!Character.isDigit(c) && c != '.') {
                    evt.consume();
                } else if (c == '.' && text.contains(".")) {
                    evt.consume(); // Consume if there's already a decimal point
                }
			}
		});
		panel.add(txtPrice);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(145, 191, 179, 50);
		panel.add(scrollPane);

		txtDesc = new JTextArea();
		txtDesc.setEditable(false);
		scrollPane.setViewportView(txtDesc);

		lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDescription.setBounds(24, 191, 111, 22);
		panel.add(lblDescription);

		lblQuantity = new JLabel("Current Quantity");
		lblQuantity.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblQuantity.setBounds(24, 159, 111, 22);
		panel.add(lblQuantity);

		txtQty = new JTextField();
		txtQty.setEditable(false);
		txtQty.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtQty.setColumns(10);
		txtQty.setBounds(145, 163, 179, 19);
		txtQty.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c = evt.getKeyChar();
				if (!(Character.isDigit(c))) {
					evt.consume();
				}
			}
		});
		panel.add(txtQty);

		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(warehouseNamesArray);

		//cboBranch = new JComboBox<>(warehouseNamesArray);
		cboBranch = new JComboBox<>(comboBoxModel);
		cboBranch.setEditable(true);
		cboBranch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		//cboBranch.setModel(new DefaultComboBoxModel({warehouseNamesArray});
		cboBranch.setBounds(145, 96, 179, 21);
		panel.add(cboBranch);

		lblEMPBranch = new JLabel("Branch Location");
		lblEMPBranch.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEMPBranch.setBounds(24, 93, 111, 22);
		panel.add(lblEMPBranch);

		btnNewButton = new JButton("Add to Orders");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String price,oderItem, prodID;
				Float xPrice;
				int xprodID, ordercnt ;
				prodID = txtID.getText().trim();
				price = lblPriceAmount.getText().trim();
				oderItem = txtOrderCnt.getText().trim();

				if(oderItem.length() == 0 || prodID.length() == 0) {
					 JOptionPane.showMessageDialog(null,"Please check the no of Items for the order!",
					 "Customer Order",JOptionPane.INFORMATION_MESSAGE);

				}else {
					try {
						xprodID = Integer.parseInt(prodID);
						xPrice = Float.parseFloat(price.replace(",", ""));
						ordercnt = Integer.parseInt(oderItem);

						connect(); // set the connection to database

						call1 = con1.prepareCall("{ CALL `DeductStockAndInsertOrder`(?, ?, ?, ?)}");
						call1.setInt(1,xprodID);
						call1.setInt(2,ordercnt);
						call1.setInt(3,oderID);
						call1.setFloat(4, xPrice);// setInt(4,oderID);

						call1.execute();

						disconnect(); //disconnect connection to database
						xCustomers.load_tbl(); //reset table content for orders
						//xCustomers.allowItemButtons();
						clearForm(); //reset textboxes
						load_tbl(); //reset table content

					} catch (SQLException e2) {
						e2.printStackTrace();
					}

				}

			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setBounds(369, 281, 149, 57);
		getContentPane().add(btnNewButton);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(372, 11, 447, 260);
		getContentPane().add(scrollPane_1);

		tblProduct = new JTable();
		tblProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                int selectedRow = tblProduct.getSelectedRow();
                if (selectedRow != -1) {
                	txtID.setText(tblProduct.getValueAt(selectedRow, 0).toString());
                	txtName.setText((String) tblProduct.getValueAt(selectedRow, 1));
                	txtPrice.setText(tblProduct.getValueAt(selectedRow, 2).toString());
                	txtQty.setText(tblProduct.getValueAt(selectedRow, 3).toString());
                	cboBranch.setSelectedItem(tblProduct.getValueAt(selectedRow, 4));
                	txtDesc.setText((String) tblProduct.getValueAt(selectedRow, 5));

                }
			}
		});

		scrollPane_1.setViewportView(tblProduct);

		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search Table", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(528, 281, 291, 57);
		getContentPane().add(panel_1);

		textSearch = new JTextField();
		textSearch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		textSearch.setColumns(10);
		textSearch.setBounds(10, 23, 271, 22);
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

		lblQuantityForPurchase = new JLabel("Quantity for Purchase");
		lblQuantityForPurchase.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblQuantityForPurchase.setBounds(20, 278, 149, 17);
		getContentPane().add(lblQuantityForPurchase);

		txtOrderCnt = new JTextField();
		txtOrderCnt.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtOrderCnt.setColumns(10);
		txtOrderCnt.setBounds(179, 278, 174, 19);
		txtOrderCnt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c = evt.getKeyChar();
				if (!(Character.isDigit(c))) {
					evt.consume();
				}else {
                    // Get the current stock count
                    int stockCount = 0;
                    try {
                        stockCount = Integer.parseInt(txtQty.getText());
                    } catch (NumberFormatException e) {
                        // If the stock count is not a valid number, default to 0
                    }

                    // Get the current order count
                    String currentOrderText = txtOrderCnt.getText();
                    int orderCount = 0;
                    try {
                        orderCount = Integer.parseInt(currentOrderText + c);
                    } catch (NumberFormatException e) {
                        // If the order count is not a valid number, default to 0
                    }

                    // Check if the order count exceeds the stock count
                    if (orderCount > stockCount) {
                        evt.consume();
                    } else {
                        // Calculate the total price
                        double price = 0.0;
                        try {
                            price = Double.parseDouble(txtPrice.getText());
                        } catch (NumberFormatException e) {
                            // If the price is not a valid number, default to 0.0
                        }
                        double totalPrice = orderCount * price;
                        //put a format
//                        DecimalFormat formatter = new DecimalFormat("#,###.00");
                        // Update the price amount label
                        lblPriceAmount.setText(String.format("%,.2f", totalPrice));

//                        String priceNew = String.format("%.2f", totalPrice);
//                        lblPriceAmount.setText(formatter.format(totalPrice));
                    }
                }
			}
		});


		getContentPane().add(txtOrderCnt);

		JLabel lblTotalAmount = new JLabel("Total Amount");
		lblTotalAmount.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblTotalAmount.setBounds(20, 309, 149, 17);
		getContentPane().add(lblTotalAmount);

		lblPriceAmount = new JLabel("0.00");
		lblPriceAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPriceAmount.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblPriceAmount.setBounds(177, 309, 174, 17);
		getContentPane().add(lblPriceAmount);


		load_tbl();
	}
	public void load_tbl() {
		//reset table data
		if(!(tblProduct.getModel().getRowCount() == 0)) {
			DefaultTableModel modelx = (DefaultTableModel) tblProduct.getModel();
			modelx.setRowCount(0);
			System.out.print("Table reset data...");
		}

		//add the table info and look
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Branch");
        model.addColumn("Description");

		try {
			connect();
			//Class.forName("net.proteanit.sql.DbUtils");
			st1 = con1.createStatement();
			//pst1 = con1.prepareStatement("SELECT * from employees");
			rs1 = st1.executeQuery("SELECT products.*, "
					+ "inventory.quantity AS Prod_Qty, "
					+ "inventory.warehouse_id AS branch "
					+ "FROM products "
					+ "JOIN inventory "
					+ "ON  products.product_id = inventory.product_id;");
//			tableEMP.setModel(DbUtils.resultSetToTableModel(rs1));
			while (rs1.next()) {
                Object[] row = new Object[6];
                row[0] = rs1.getInt("product_id");
                row[1] = rs1.getString("name");
                row[2] = rs1.getInt("price");
                row[3] = rs1.getInt("Prod_Qty");
//                row[4] = rs1.getString("branch");
                row[4] = warehouseName.get(rs1.getInt("branch"));
                row[5] = rs1.getString("description");

                model.addRow(row);
            }
			sorter = new TableRowSorter<>(model);
			tblProduct.setModel(model);
			tblProduct.setRowSorter(sorter);

			rs1.close();
			st1.close();
			disconnect();
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
		txtDesc.setText("");
		txtPrice.setText("");
		cboBranch.setSelectedIndex(0);
		txtQty.setText("");
		txtOrderCnt.setText("");
		lblPriceAmount.setText("0.00");

	}

	public String[] loadWarehouse() {
		String[] warehouseNamesArrayx = null;
		try {
			connect();
			iDHolder = new HashMap<>();
			warehouseName = new HashMap<>();
			//cboManager.addItem("N/A");
			int ax=0;
			List<String> warehouseNames = new ArrayList<>();

			st1 = con1.createStatement();

			rs1 = st1.executeQuery("SELECT warehouse_id,name,capacity FROM `warehouse`");

			while (rs1.next()) {
				int whID = rs1.getInt("warehouse_id");
				String whName = rs1.getString("name");
				//cboBranch.addItem(whName);
				System.out.print(whName);
				warehouseNames.add(whName);
				iDHolder.put(ax, whID);
				warehouseName.put(whID, whName);
				//System.out.println("WH ID: " + whName + ", WHName: " + whID);
				ax = ax+1;
			}

			warehouseNamesArrayx = warehouseNames.toArray(new String[0]);
			//displayHashMap(warehouseName);

			rs1.close();
			st1.close();
			disconnect();

		}catch(Exception e){
			System.out.print(e.toString());
		}


		return warehouseNamesArrayx;
	}
}

package package1;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.time.LocalDate; 

public class Delivery extends JInternalFrame {
	private JTable table;
	Connection con1;
	Statement st1;
	PreparedStatement pst1, pst2;
	ResultSet rs1;
	CallableStatement call1;
	private TableRowSorter sorter;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtDeliveryDate;
	private JTextField txtArivalDate;
	private JTextField txtSearch;
	private JTable itemsTable;
	private JButton btnDeploy;
	private JButton btnReceived;
	
	private int deliveryID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delivery frame = new Delivery();
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
	public Delivery() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Delivery Management");
		setBounds(100, 100, 956, 385);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "For Delivery List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 920, 164);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 40, 908, 118);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                String stat;
                if (selectedRow != -1) {
                	deliveryID = ((int) table.getValueAt(selectedRow, 0));
                	
                	txtID.setText(table.getValueAt(selectedRow, 0).toString());
                	txtName.setText((String) table.getValueAt(selectedRow, 1));
                	txtDeliveryDate.setText((String) table.getValueAt(selectedRow, 8).toString());
                	txtArivalDate.setText((String) table.getValueAt(selectedRow, 9).toString());
                	
                	stat = ((String) table.getValueAt(selectedRow, 7).toString());
                	if("FOR PROCESSING".equals(stat)) {
                		btnDeploy.setEnabled(true);
                		btnReceived.setEnabled(false);
                	}else if("DEPLOYED".equals(stat)){
                		btnDeploy.setEnabled(false);
                		btnReceived.setEnabled(true);
                	}
                	else {
                		btnDeploy.setEnabled(false);
                		btnReceived.setEnabled(false);
                	}
                }
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblFilterByStatus = new JLabel("Filter by Status:");
		lblFilterByStatus.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblFilterByStatus.setBounds(248, 10, 111, 22);
		panel.add(lblFilterByStatus);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"FOR PROCESSING", "DEPLOYED", "RECEIVED"}));
		comboBox.setBounds(358, 13, 151, 19);
		comboBox.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String selectedItem = (String) comboBox.getSelectedItem();  
                System.out.println("combo: " +selectedItem);
                search(selectedItem);  
            }  
            public void search(String str) {
	            if (str.length() == 0) {
	               sorter.setRowFilter(null);
	            } else {
	               sorter.setRowFilter(RowFilter.regexFilter(str));
	            }
	         }
	    });  
		
		panel.add(comboBox);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblSearch.setBounds(569, 10, 62, 22);
		panel.add(lblSearch);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtSearch.setColumns(10);
		txtSearch.setBounds(624, 12, 272, 19);
		txtSearch.getDocument().addDocumentListener(new DocumentListener(){
			@Override
	         public void insertUpdate(DocumentEvent e) {
	            search(txtSearch.getText());
	         }
	         @Override
	         public void removeUpdate(DocumentEvent e) {
	            search(txtSearch.getText());
	         }
	         @Override
	         public void changedUpdate(DocumentEvent e) {
	            search(txtSearch.getText());
	         }
	         public void search(String str) {
	            if (str.length() == 0) {
	               sorter.setRowFilter(null);
	            } else {
	               sorter.setRowFilter(RowFilter.regexFilter(str));
	            }
	         }
		});
		panel.add(txtSearch);
		
		btnDeploy = new JButton("Deploy Package");
		btnDeploy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					// Get the current date  
			        LocalDate localDate = LocalDate.now();  
			          
			        // Convert LocalDate to java.sql.Date  
			        Date sqlDate = Date.valueOf(localDate);
			        
					call1 = con1.prepareCall("{ CALL `deliveryUpdate`(?,?,?)}");
					call1.setInt(1,deliveryID);
					call1.setDate(2, sqlDate);
					call1.setInt(3, 1);
					call1.execute();
					disconnect();
					load_tbl();
					clearForm();
				} catch (SQLException e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
			}
		});
		btnDeploy.setEnabled(false);
		btnDeploy.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnDeploy.setBounds(42, 317, 151, 27);
		getContentPane().add(btnDeploy);
		
		JLabel lblDeliveryId = new JLabel("Delivery Id");
		lblDeliveryId.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDeliveryId.setBounds(42, 184, 111, 22);
		getContentPane().add(lblDeliveryId);
		
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblCustomerName.setBounds(42, 216, 111, 22);
		getContentPane().add(lblCustomerName);
		
		JLabel lblDeliveryDate = new JLabel("Delivery Date");
		lblDeliveryDate.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblDeliveryDate.setBounds(42, 250, 111, 22);
		getContentPane().add(lblDeliveryDate);
		
		JLabel lblEstimatedArival = new JLabel("Estimated Arival");
		lblEstimatedArival.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEstimatedArival.setBounds(42, 282, 111, 22);
		getContentPane().add(lblEstimatedArival);
		
		txtID = new JTextField();
		txtID.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(183, 187, 179, 19);
		getContentPane().add(txtID);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtName.setColumns(10);
		txtName.setBounds(183, 219, 179, 19);
		getContentPane().add(txtName);
		
		txtDeliveryDate = new JTextField();
		txtDeliveryDate.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtDeliveryDate.setColumns(10);
		txtDeliveryDate.setBounds(183, 254, 179, 19);
		getContentPane().add(txtDeliveryDate);
		
		txtArivalDate = new JTextField();
		txtArivalDate.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtArivalDate.setColumns(10);
		txtArivalDate.setBounds(183, 286, 179, 19);
		getContentPane().add(txtArivalDate);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(372, 184, 558, 160);
		getContentPane().add(scrollPane_1);
		
		itemsTable = new JTable();
		scrollPane_1.setViewportView(itemsTable);
		
		btnReceived = new JButton("Received");
		btnReceived.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					// Get the current date  
			        LocalDate localDate = LocalDate.now();  
			          
			        // Convert LocalDate to java.sql.Date  
			        Date sqlDate = Date.valueOf(localDate);
			        
					call1 = con1.prepareCall("{ CALL `deliveryUpdate`(?,?,?)}");
					call1.setInt(1,deliveryID);
					call1.setDate(2, sqlDate);
					call1.setInt(3, 2);
					call1.execute();
					disconnect();
					load_tbl();
					clearForm();
				} catch (SQLException e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
			}
		});
		btnReceived.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnReceived.setEnabled(false);
		btnReceived.setBounds(211, 317, 151, 27);
		getContentPane().add(btnReceived);

		//load_tbl();
	}
	public void load_tbl() {
		
		if(!(table.getModel().getRowCount() == 0)) {
			DefaultTableModel modelx = (DefaultTableModel) table.getModel();
			modelx.setRowCount(0);
			System.out.print("Table reset data...");
		}
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
        model.addColumn("Customer Name");
        model.addColumn("Email");
        model.addColumn("Contact No.");
        model.addColumn("Address");
        model.addColumn("Total Price");
        model.addColumn("Order Date");
        model.addColumn("Status");
        model.addColumn("Shipped Date");
        model.addColumn("Delivery Date");

		try {
			connect();
			rs1 = st1.executeQuery("SELECT a.delivery_id as 'id', a.shipped_date as 'shipDate', "
					+ " a.delivery_date as 'deliveryDate',"
					+ " a.status as 'stat', b.order_date as 'orderdate', b.customer_id as 'customerReference',"
					+ " c.name as 'name',c.email as 'email', c.phone as 'phone', c.address as 'address',"
					+ " b.total_amount as 'totalamount' "
					+ " FROM deliveries as a join orders as b join customers as c WHERE "
					+ " a.order_id = b.order_id and b.customer_id = c.customer_id;");
			while (rs1.next()) {
				String prx;
				double price;
				price = rs1.getInt("totalamount");
				prx = String.format("%,.2f",price);
                Object[] row = new Object[10]; 
                row[0] = rs1.getInt("id");
                row[1] = rs1.getString("name");
                row[2] = rs1.getString("email");
                row[3] = rs1.getString("phone");
                row[4] = rs1.getString("address");
                row[5] = prx;
                row[6] = rs1.getDate("orderdate");
                row[7] = rs1.getString("stat");
                row[8] = rs1.getDate("shipDate");
                row[9] = rs1.getDate("deliveryDate");
 
                model.addRow(row);
            }
			sorter = new TableRowSorter<>(model);
			table.setModel(model);
			table.setRowSorter(sorter);
			
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
	
	public void clearForm() {
		btnDeploy.setEnabled(false);
		btnReceived.setEnabled(false);
		txtID.setText("");
		txtName.setText("");
		txtArivalDate.setText("");
		txtDeliveryDate.setText("");
	}
}

package package1;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel; 
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;  
import javax.swing.event.ListSelectionListener;  
import javax.swing.event.DocumentEvent;  
import javax.swing.event.DocumentListener; 
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.DocumentFilter;
import javax.swing.JScrollPane;
import javax.swing.table.TableRowSorter; 
import javax.swing.RowFilter;
import javax.swing.JFrame; 

//import net.proteanit.sql.DbUtils;
public class Practice2 extends JInternalFrame {
	Connection con1;
	Statement st1;
	PreparedStatement pst1;
	ResultSet rs1;
	
	private JTextField txtWHID;
	private JTextField txtName;
	private JTextField txtCapacity;
	private JPanel panel;
	private JTable tableWH;
	private JComboBox cboBranch;
	private JButton btnNewButton;
	private JButton btnUpdateInformation;
	private JButton btnDelete;
	private JButton btnClear;
	private TableRowSorter sorter;
	private HashMap<Integer, Integer> iDHolder;
	private HashMap<Integer, String> ManagersName;
	private JComboBox cboManager;
	private JTextField txtSearch;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Practice2 frame = new Practice2();
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
	public Practice2() {
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(new Dimension(845, 335));
		setPreferredSize(new Dimension(845, 335));
		setMinimumSize(new Dimension(845, 335));
		setMaximumSize(new Dimension(845, 335));
		setTitle("Warehouse Management");
		setBounds(100, 100, 845, 335);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Basic Warehouse Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 12, 352, 200);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblEmpId = new JLabel("Warehouse Id");
		lblEmpId.setBounds(24, 29, 111, 22);
		panel.add(lblEmpId);
		lblEmpId.setFont(new Font("Century Gothic", Font.BOLD, 13));
		
		txtWHID = new JTextField();
		txtWHID.setEditable(false);
		txtWHID.setBounds(145, 33, 179, 19);
		panel.add(txtWHID);
		txtWHID.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtWHID.setColumns(10);
		
		txtWHID.getDocument().addDocumentListener(new DocumentListener() {  
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
            	btnNewButton.setEnabled(txtWHID.getText().isEmpty());
            	btnDelete.setEnabled(!txtWHID.getText().isEmpty());
            	btnUpdateInformation.setEnabled(!txtWHID.getText().isEmpty());
            }  
        });  
		
		JLabel lblEmpFirst = new JLabel("Name");
		lblEmpFirst.setBounds(24, 61, 111, 22);
		panel.add(lblEmpFirst);
		lblEmpFirst.setFont(new Font("Century Gothic", Font.BOLD, 13));
		
		txtName = new JTextField();
		txtName.setBounds(145, 65, 179, 19);
		panel.add(txtName);
		txtName.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtName.setColumns(10);
		
		JLabel lblEmpMid = new JLabel("Manager");
		lblEmpMid.setBounds(24, 93, 111, 22);
		panel.add(lblEmpMid);
		lblEmpMid.setFont(new Font("Century Gothic", Font.BOLD, 13));
		
		JLabel lblEMPBranch = new JLabel("Branch Location");
		lblEMPBranch.setBounds(24, 125, 111, 22);
		panel.add(lblEMPBranch);
		lblEMPBranch.setFont(new Font("Century Gothic", Font.BOLD, 13));
		
		cboBranch = new JComboBox();
		cboBranch.setModel(new DefaultComboBoxModel(new String[] {"NCR", "Baguio", "Manila", "Cebu", "Davao"}));
		cboBranch.setBounds(145, 128, 179, 21);
		panel.add(cboBranch);
		cboBranch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JLabel lblEMPSalary = new JLabel("Capacity");
		lblEMPSalary.setBounds(24, 160, 111, 22);
		panel.add(lblEMPSalary);
		lblEMPSalary.setFont(new Font("Century Gothic", Font.BOLD, 13));
		
		txtCapacity = new JTextField();
		txtCapacity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c = evt.getKeyChar();
				if (!(Character.isDigit(c))) {
					evt.consume();
				}
			}
		});
		
		txtCapacity.setBounds(145, 164, 179, 19);
		panel.add(txtCapacity);
		txtCapacity.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtCapacity.setColumns(10);
		
		cboManager = new JComboBox();
		cboManager.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		cboManager.setBounds(145, 96, 179, 21);
		panel.add(cboManager);
		
		btnNewButton = new JButton("Add New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String whname,branch,whCapacity,manager,managerID;
				int managerID1, xCapacity;
				whname = txtName.getText().trim();
				branch = cboBranch.getSelectedItem().toString();
				whCapacity =  txtCapacity.getText().trim();
				
				managerID1 = iDHolder.get(cboManager.getSelectedIndex());
				xCapacity = Integer.parseInt(whCapacity);
				
				if(whname.length() == 0 || whCapacity.length() == 0) {
					JOptionPane.showMessageDialog(null,"Please provide information for the warehouse","Warehouse Creation",JOptionPane.INFORMATION_MESSAGE);
					
				}
				else {
				
					try {
						connect();
						pst1 = con1.prepareStatement("INSERT INTO `warehouse`( `name`, `location`, `capacity`, `manager_id`) "
																	+ "Values (?,?,?,?)");
						pst1.setString(1,whname);
						pst1.setString(2,branch);
						pst1.setInt(3,xCapacity);
						pst1.setInt(4,managerID1);
						
						pst1.executeUpdate();
						
						pst1.close();
						disconnect();
						
						JOptionPane.showMessageDialog(null,"New Warehouse Added!","Result",JOptionPane.INFORMATION_MESSAGE);
						
						clearEmpForm();
						load_tbl();
					
					}catch(SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setBounds(39, 222, 121, 27);
		getContentPane().add(btnNewButton);
		
		btnUpdateInformation = new JButton("Update");
		btnUpdateInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UPDATE
				String whname,branch,whCapacity,manager,managerID,whid;
				int managerID1, xCapacity,whID1;
				
				whid = txtWHID.getText().trim();
				whname = txtName.getText().trim();
				branch = cboBranch.getSelectedItem().toString();
				whCapacity =  txtCapacity.getText().trim();
				
				whID1 = Integer.parseInt(whid);
				managerID1 = iDHolder.get(cboManager.getSelectedIndex());
				xCapacity = Integer.parseInt(whCapacity);
				if(whname.length() == 0 || whCapacity.length() == 0) {
					JOptionPane.showMessageDialog(null,"Please provide information for the warehouse","Warehouse Update",JOptionPane.INFORMATION_MESSAGE);
					
				}
				else {
					try {
						connect();
						pst1 = con1.prepareStatement("UPDATE `warehouse` SET "
								+ "`name`=?"
								+ ",`location`=?"
								+ ",`capacity`=?"
								+ ",`manager_id`=? "
								+"WHERE `warehouse_id`=?");
						pst1.setString(1,whname);
						pst1.setString(2,branch);
						pst1.setInt(3,xCapacity);
						pst1.setInt(4,managerID1);
						pst1.setInt(5,whID1);
						
						pst1.executeUpdate();
						
						pst1.close();
						disconnect();
	
						JOptionPane.showMessageDialog(null,"Warehouse Information Updated!","Result",JOptionPane.INFORMATION_MESSAGE);
						
						load_tbl();
						clearEmpForm();
						
					
					}catch(SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnUpdateInformation.setEnabled(false);
		btnUpdateInformation.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdateInformation.setBounds(201, 222, 121, 27);
		getContentPane().add(btnUpdateInformation);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//DELETE
				int n = JOptionPane.showConfirmDialog(
				null, "Would you like delete this Warehouse?",
				"Remove Warehouse",JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					String whID;
					int whouseid;
					
					whID = txtWHID.getText().trim();
					whouseid = Integer.parseInt(whID);
					
					try {
						connect();
						pst1 = con1.prepareStatement("DELETE FROM `warehouse`"
								+" WHERE `warehouse_id`=?");
						pst1.setInt(1,whouseid);
						
						pst1.executeUpdate();
						
						pst1.close();
						disconnect();
	
						JOptionPane.showMessageDialog(null,"The Warehouse is Deleted from the list!","Result",JOptionPane.INFORMATION_MESSAGE);
						
						clearEmpForm();
						load_tbl();
					
					}catch(SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnDelete.setBounds(39, 259, 121, 27);
		getContentPane().add(btnDelete);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search Table", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(374, 230, 447, 64);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtSearch.setColumns(10);
		txtSearch.setBounds(10, 23, 427, 22);
		panel_1.add(txtSearch);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(374, 10, 447, 210);
		getContentPane().add(scrollPane);
		
		tableWH = new JTable();
		tableWH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {  
                int selectedRow = tableWH.getSelectedRow();  
                if (selectedRow != -1) {  
                	txtWHID.setText((String) tableWH.getValueAt(selectedRow, 0).toString());  
                	txtName.setText((String) tableWH.getValueAt(selectedRow, 1));  
                	cboBranch.setSelectedItem((String) tableWH.getValueAt(selectedRow, 2));  
                	txtCapacity.setText((String) tableWH.getValueAt(selectedRow, 3).toString()); 
                	cboManager.setSelectedItem((String) tableWH.getValueAt(selectedRow, 5)); //4 ManagerID, 5 ManagerName
                }  
			}
		});

		scrollPane.setViewportView(tableWH);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearEmpForm();
			}
		});
		btnClear.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnClear.setBounds(201, 259, 121, 27);
		getContentPane().add(btnClear);

		loadManager();
		load_tbl();
		
	}
	public void clearEmpForm() {
		txtName.setText("");
		txtWHID.setText("");
		txtCapacity.setText("");
		cboBranch.setSelectedIndex(0);
		cboManager.setSelectedIndex(0);
		//loadManager();
	}
	
	public void load_tbl() {
		//reset table data
		if(!(tableWH.getModel().getRowCount() == 0)) {
			DefaultTableModel modelx = (DefaultTableModel) tableWH.getModel();  
			modelx.setRowCount(0);  
			System.out.print("Table reset data...");
		}
		
		//add the table info and look
		DefaultTableModel model = new DefaultTableModel();  
		//String[] columnNames = {"ID","First Name","Middle Name","Last Name","Position","Branch","Salary","Date Created"};
		model.addColumn("ID");  
        model.addColumn("Name");  
        model.addColumn("Location");
        model.addColumn("Capacity");
        model.addColumn("Manager ID");
        model.addColumn("Manager Name");
		try {
			connect();
			//Class.forName("net.proteanit.sql.DbUtils");
			st1 = con1.createStatement();
			//pst1 = con1.prepareStatement("SELECT * from employees");
			rs1 = st1.executeQuery("SELECT warehouse.*, "
					+ "employees.first_name AS manager_name "
					+ "FROM warehouse "
					+ "JOIN employees "
					+ "ON warehouse.manager_id = employees.employeeID;");
//			tableEMP.setModel(DbUtils.resultSetToTableModel(rs1));
			while (rs1.next()) {  
                Object[] row = new Object[6];  
                row[0] = rs1.getInt("warehouse_id");  
                row[1] = rs1.getString("name");  
                row[2] = rs1.getString("location");  
                row[3] = rs1.getInt("capacity");  
                row[4] = rs1.getInt("manager_id"); 
                row[5] = rs1.getString("manager_name"); 
                
                model.addRow(row);  
            }  
			sorter = new TableRowSorter<>(model);
			tableWH.setModel(model);
			tableWH.setRowSorter(sorter);
			
			rs1.close();
			st1.close();
			disconnect();
		}catch(SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	public void loadManager() {
		try {
			connect();
			iDHolder = new HashMap<>();
			ManagersName = new HashMap<>();
			//cboManager.addItem("N/A");
			int ax=0;
			st1 = con1.createStatement();
			
			rs1 = st1.executeQuery("SELECT employeeID,first_name FROM `employees` WHERE position = 'Manager'");
			
			while (rs1.next()) {  
				int empID = rs1.getInt("employeeID");
				String empName = rs1.getString("first_name");
				cboManager.addItem(empName);
				iDHolder.put(ax, empID);
				ManagersName.put(empID, empName);
				//System.out.println("EmployeeID: " + empID + ", Name: " + empName);
				ax = ax+1;
			}
			//displayHashMap(ManagersName);  
			rs1.close();
			st1.close();
			disconnect();
		}catch(Exception e){
			System.out.print(e.toString());
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
}

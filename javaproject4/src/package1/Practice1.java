package package1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

//import net.proteanit.sql.DbUtils;
public class Practice1 extends JInternalFrame {
	Connection con1;
	Statement st1;
	PreparedStatement pst1;
	ResultSet rs1;

	private JTextField txtEmpID;
	private JTextField txtFirst;
	private JTextField txtMid;
	private JTextField txtLast;
	private JTextField txtSalary;
	private JPanel panel;
	private JTable tableEMP;
	private JComboBox cboBranch;
	private JButton btnNewButton;
	private JButton btnUpdateInformation;
	private JButton btnDelete;
	private JButton btnClear;
	private String[] colNames = {"Employee Id", "First Name", "Middle Name", "Last Name", "Position", "Branch Location", "Salary"};
	private JTextField txtSearch;
	//private JButton btnSearch;
	private JComboBox cboPosition;
	private TableRowSorter sorter;
	private HashMap<Integer, Integer> iDHolder;
	private HashMap<Integer, String> wHName;
	private JPanel panel_2;
	private JButton btnCreateAccount;
	private JButton btnDisable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Practice1 frame = new Practice1();
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
	public Practice1() {
		setClosable(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setSize(new Dimension(1132, 385));
		setPreferredSize(new Dimension(1132, 385));
		setMinimumSize(new Dimension(1132, 385));
		setMaximumSize(new Dimension(1132, 385));
		setTitle("Employee Management");
		setBounds(100, 100, 1132, 385);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Basic User Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 12, 352, 264);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblEmpId = new JLabel("Employee Id");
		lblEmpId.setBounds(24, 29, 111, 22);
		panel.add(lblEmpId);
		lblEmpId.setFont(new Font("Century Gothic", Font.BOLD, 13));

		txtEmpID = new JTextField();
		txtEmpID.setEditable(false);
		txtEmpID.setBounds(145, 33, 179, 19);
		panel.add(txtEmpID);
		txtEmpID.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtEmpID.setColumns(10);

		txtEmpID.getDocument().addDocumentListener(new DocumentListener() {
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
            	btnNewButton.setEnabled(txtEmpID.getText().isEmpty());
            	btnDelete.setEnabled(!txtEmpID.getText().isEmpty());
            	btnUpdateInformation.setEnabled(!txtEmpID.getText().isEmpty());
            }
        });

		JLabel lblEmpFirst = new JLabel("First Name");
		lblEmpFirst.setBounds(24, 61, 111, 22);
		panel.add(lblEmpFirst);
		lblEmpFirst.setFont(new Font("Century Gothic", Font.BOLD, 13));

		txtFirst = new JTextField();
		txtFirst.setBounds(145, 65, 179, 19);
		panel.add(txtFirst);
		txtFirst.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtFirst.setColumns(10);

		JLabel lblEmpMid = new JLabel("Middle Name");
		lblEmpMid.setBounds(24, 93, 111, 22);
		panel.add(lblEmpMid);
		lblEmpMid.setFont(new Font("Century Gothic", Font.BOLD, 13));

		txtMid = new JTextField();
		txtMid.setBounds(145, 97, 179, 19);
		panel.add(txtMid);
		txtMid.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtMid.setColumns(10);

		JLabel lblEMPLast = new JLabel("Last Name");
		lblEMPLast.setBounds(24, 125, 111, 22);
		panel.add(lblEMPLast);
		lblEMPLast.setFont(new Font("Century Gothic", Font.BOLD, 13));

		txtLast = new JTextField();
		txtLast.setBounds(145, 129, 179, 19);
		panel.add(txtLast);
		txtLast.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtLast.setColumns(10);

		JLabel lblEMPPosition = new JLabel("Position");
		lblEMPPosition.setBounds(24, 157, 111, 22);
		panel.add(lblEMPPosition);
		lblEMPPosition.setFont(new Font("Century Gothic", Font.BOLD, 13));

		cboPosition = new JComboBox();
		cboPosition.setModel(new DefaultComboBoxModel(new String[] {"Not Set", "Manager", "Forklift Driver", "Loader-Unloader", "Stocker", "Receiving Associate", "Package Handler", "Inventory Clerk", "Delivery Driver", "Safety Supervisor", "Security Supervisor"}));
		cboPosition.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		cboPosition.setBounds(145, 160, 179, 21);
		panel.add(cboPosition);

		JLabel lblEMPBranch = new JLabel("Branch Location");
		lblEMPBranch.setBounds(24, 189, 111, 22);
		panel.add(lblEMPBranch);
		lblEMPBranch.setFont(new Font("Century Gothic", Font.BOLD, 13));

		cboBranch = new JComboBox();
		cboBranch.setModel(new DefaultComboBoxModel(new String[] {"NCR", "Baguio", "Manila", "Cebu", "Davao"}));
		cboBranch.setBounds(145, 192, 179, 21);
		panel.add(cboBranch);
		cboBranch.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		JLabel lblEMPSalary = new JLabel("Salary");
		lblEMPSalary.setBounds(24, 224, 111, 22);
		panel.add(lblEMPSalary);
		lblEMPSalary.setFont(new Font("Century Gothic", Font.BOLD, 13));

		txtSalary = new JTextField();
		txtSalary.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c = evt.getKeyChar();
				if (!(Character.isDigit(c))) {
					evt.consume();
				}
			}
		});

		txtSalary.setBounds(145, 228, 179, 19);
		panel.add(txtSalary);
		txtSalary.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtSalary.setColumns(10);

		btnNewButton = new JButton("Add New");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fname,mname,lname,empposition,empbranch, empsalary;

				fname = txtFirst.getText().trim();
				mname = txtMid.getText().trim();
				lname = txtLast.getText().trim();
				empposition = cboPosition.getSelectedItem().toString();
				empbranch = cboBranch.getSelectedItem().toString();
				empsalary =  txtSalary.getText().trim();
				int xSalary = Integer.parseInt(empsalary);
				//int whId = iDHolder.get(cboWH.getSelectedIndex());

				if(fname.length() == 0 ||mname.length() == 0||lname.length() == 0||empsalary.length() == 0) {
					JOptionPane.showMessageDialog(null,"Please provide information for the user","User creation",JOptionPane.INFORMATION_MESSAGE);

				}
				else {

					try {
						connect();
						pst1 = con1.prepareStatement("INSERT INTO `employees` (`first_name`, `mid_name`, `last_name`, `position`, `branch`, `salary`) Values (?,?,?,?,?,?)");
						//pst1 = con1.prepareStatement("INSERT INTO `employees` (`first_name`, `mid_name`, `last_name`, `position`, `branch`, `salary`,`warehouse_id`) Values (?,?,?,?,?,?,?)");
						pst1.setString(1,fname);
						pst1.setString(2,mname);
						pst1.setString(3,lname);
						pst1.setString(4,empposition);
						pst1.setString(5,empbranch);
						pst1.setInt(6,xSalary);
						//pst1.setInt(7, whId);

						pst1.executeUpdate();

						pst1.close();
						disconnect();

						JOptionPane.showMessageDialog(null,"New User Added!","Result",JOptionPane.INFORMATION_MESSAGE);

						clearEmpForm();
						load_tbl();

					}catch(SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});

		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setBounds(37, 286, 121, 27);
		getContentPane().add(btnNewButton);

		btnUpdateInformation = new JButton("Update");
		btnUpdateInformation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//UPDATE
				String empid, fname,mname,lname,empposition,empbranch, empsalary;
				int EEid, xSalary;

				empid = txtEmpID.getText().trim();
				EEid = Integer.parseInt(empid);
				fname = txtFirst.getText().trim();
				mname = txtMid.getText().trim();
				lname = txtLast.getText().trim();
				empposition = cboPosition.getSelectedItem().toString();
				empbranch = cboBranch.getSelectedItem().toString();
				empsalary =  txtSalary.getText().trim();
				xSalary = Integer.parseInt(empsalary);
				//int whId = iDHolder.get(cboWH.getSelectedIndex());

				try {
					connect();
					pst1 = con1.prepareStatement("UPDATE `employees` SET "
							+ "`first_name`=?"
							+ ",`mid_name`=?"
							+ ",`last_name`=?"
							+ ",`position`=?"
							+ ",`branch`=?"
							+ ",`salary`=?"
							//+ ",`warehouse_id`=?"
							+" WHERE `employeeID`=?");
					pst1.setString(1,fname);
					pst1.setString(2,mname);
					pst1.setString(3,lname);
					pst1.setString(4,empposition);
					pst1.setString(5,empbranch);
					pst1.setInt(6,xSalary);
					//pst1.setInt(7,whId);
					pst1.setInt(7,EEid);

					pst1.executeUpdate();

					pst1.close();
					disconnect();

					JOptionPane.showMessageDialog(null,"User Information Updated!","Result",JOptionPane.INFORMATION_MESSAGE);

					load_tbl();
					clearEmpForm();


				}catch(SQLException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnUpdateInformation.setEnabled(false);
		btnUpdateInformation.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdateInformation.setBounds(199, 286, 121, 27);
		getContentPane().add(btnUpdateInformation);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(
				null, "Would you like delete this Staff?",
				"Remove Employee",JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					//DELETE
					String empid;
					int EEid;

					empid = txtEmpID.getText().trim();
					EEid = Integer.parseInt(empid);

					try {
						connect();
						pst1 = con1.prepareStatement("DELETE FROM `employees`"
								+" WHERE `employeeID`=?");
						pst1.setInt(1,EEid);

						pst1.executeUpdate();

						pst1.close();
						disconnect();

						JOptionPane.showMessageDialog(null,"The User is Deleted from the list!","Result",JOptionPane.INFORMATION_MESSAGE);

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
		btnDelete.setBounds(37, 323, 121, 27);
		getContentPane().add(btnDelete);


		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search Table", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(754, 293, 352, 54);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtSearch.setColumns(10);
		txtSearch.setBounds(23, 20, 319, 22);
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
		scrollPane.setBounds(374, 10, 734, 274);
		getContentPane().add(scrollPane);

		tableEMP = new JTable();
		tableEMP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                int selectedRow = tableEMP.getSelectedRow();
                String access;
                if (selectedRow != -1) {
                	txtEmpID.setText(tableEMP.getValueAt(selectedRow, 0).toString());
                	txtFirst.setText((String) tableEMP.getValueAt(selectedRow, 1));
                	txtMid.setText((String) tableEMP.getValueAt(selectedRow, 2));
                	txtLast.setText((String) tableEMP.getValueAt(selectedRow, 3));
                	cboPosition.setSelectedItem(tableEMP.getValueAt(selectedRow, 4));
                	cboBranch.setSelectedItem(tableEMP.getValueAt(selectedRow, 5));
                	txtSalary.setText(tableEMP.getValueAt(selectedRow, 6).toString());
                	//cboWH.setSelectedItem((String) tableEMP.getValueAt(selectedRow, 4));
                	access = (String) tableEMP.getValueAt(selectedRow, 8);
                	System.out.println(access);
                	if("With Access".equals(access)) {
                		btnDisable.setEnabled(true);
                		btnCreateAccount.setEnabled(false);
                	}else {
                		btnCreateAccount.setEnabled(true);
                		btnDisable.setEnabled(false);
                	}
                }
			}
		});

		scrollPane.setViewportView(tableEMP);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearEmpForm();
			}
		});
		btnClear.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnClear.setBounds(199, 323, 121, 27);
		getContentPane().add(btnClear);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Account Credentials", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(374, 293, 352, 54);
		getContentPane().add(panel_2);
		
		btnCreateAccount = new JButton("Create account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnCreateAccount.setEnabled(false);
		btnCreateAccount.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnCreateAccount.setBounds(10, 17, 178, 27);
		panel_2.add(btnCreateAccount);
		
		btnDisable = new JButton("Disable");
		btnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int EEid;
					String empid;
					empid = txtEmpID.getText().trim();
					EEid = Integer.parseInt(empid);
					connect();
					pst1 = con1.prepareStatement("UPDATE `employees` SET "
							+ " `isEnabled`=? "
							+ " WHERE `employeeID`=?");
					pst1.setInt(1,0);					
					pst1.setInt(2,EEid);

					pst1.executeUpdate();

					pst1.close();
					
					disconnect();
				} catch (SQLException e2) {
					// TODO: handle exception
				}
			}
		});
		btnDisable.setEnabled(false);
		btnDisable.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnDisable.setBounds(198, 17, 144, 27);
		panel_2.add(btnDisable);

		load_tbl();
		//loadWH();

	}
	public void clearEmpForm() {
		txtEmpID.setText("");
		txtFirst.setText("");
		txtMid.setText("");
		txtLast.setText("");
		cboPosition.setSelectedIndex(0);
		cboBranch.setSelectedIndex(0);
		txtSalary.setText("");
		txtFirst.requestFocus();
		//cboWH.setSelectedIndex(0);

	}

	public void load_tbl() {
		//reset table data
		if(!(tableEMP.getModel().getRowCount() == 0)) {
			DefaultTableModel modelx = (DefaultTableModel) tableEMP.getModel();
			modelx.setRowCount(0);
			System.out.print("Table reset data...");
		}

		//add the table info and look
		DefaultTableModel model = new DefaultTableModel();
		//String[] columnNames = {"ID","First Name","Middle Name","Last Name","Position","Branch","Salary","Date Created"};
		model.addColumn("ID");
        model.addColumn("First Name");
        model.addColumn("Middle Name");
        model.addColumn("Last Name");
        model.addColumn("Position");
        model.addColumn("Branch");
        model.addColumn("Salary");
        model.addColumn("Created Date");
        model.addColumn("Access");

		try {
			int accs = 0;
			connect();
			//Class.forName("net.proteanit.sql.DbUtils");
			st1 = con1.createStatement();
			//pst1 = con1.prepareStatement("SELECT * from employees");
			rs1 = st1.executeQuery("SELECT employeeID,first_name,mid_name,last_name,position"
					+ ",branch,salary,created_date,isEnabled from employees");
//			tableEMP.setModel(DbUtils.resultSetToTableModel(rs1));
			while (rs1.next()) {
                Object[] row = new Object[9];
                row[0] = rs1.getInt("employeeID");
                row[1] = rs1.getString("first_name");
                row[2] = rs1.getString("mid_name");
                row[3] = rs1.getString("last_name");
                row[4] = rs1.getString("position");
                row[5] = rs1.getString("branch");
                row[6] = rs1.getInt("salary");
                row[7] = rs1.getString("created_date");
                accs = rs1.getInt("isEnabled");
                if(accs == 0) {
                	row[8] = "No Access";
                }else {
                	row[8] = "With Access";
                }
                model.addRow(row);
            }
			sorter = new TableRowSorter<>(model);
			tableEMP.setModel(model);
			tableEMP.setRowSorter(sorter);

			rs1.close();
			st1.close();
			disconnect();
		}catch(SQLException e2) {
			e2.printStackTrace();
		}
	}

//	public void loadWH() {
//		try {
//			connect();
//			iDHolder = new HashMap<>();
//			wHName = new HashMap<>();
//
//			int a=1;
//			st1 = con1.createStatement();
//
//			rs1 = st1.executeQuery("SELECT `warehouse_id`, `name`, `location`, `capacity`, `manager_id` FROM `warehouse`");
//
//			while (rs1.next()) {
//				int whID = rs1.getInt("warehouse_id");
//				//cboWH.addItem(rs1.getString("name"));
//				iDHolder.put(a, whID);
//				wHName.put(whID, rs1.getString("name"));
//
//			}
//			rs1.close();
//			st1.close();
//			disconnect();
//		}catch(Exception e){
//			System.out.print(e.toString());
//		}
//	}

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
}

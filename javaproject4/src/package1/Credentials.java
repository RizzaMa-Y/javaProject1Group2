package package1;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import package1.Customers.EmailValidation;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class Credentials extends JInternalFrame {
	Connection con1;
	Statement st1;
	PreparedStatement pst1;
	ResultSet rs1;
	
	public Practice1 xpractice1;
	
	JTextField txtid;
	private JTextField txtemail;
	private JTextField txtUsername;
	private JTextField txtTemppass;
	private JButton btnCreate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Credentials frame = new Credentials();
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
	public Credentials() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Account Creation");
		setBounds(100, 100, 396, 212);
		getContentPane().setLayout(null);
		
		JLabel lblEmpId = new JLabel("Employee Id");
		lblEmpId.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEmpId.setBounds(24, 10, 111, 22);
		getContentPane().add(lblEmpId);
		
		txtid = new JTextField();
		txtid.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtid.setEditable(false);
		txtid.setColumns(10);
		txtid.setBounds(181, 10, 179, 19);
		getContentPane().add(txtid);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEmail.setBounds(24, 42, 111, 22);
		getContentPane().add(lblEmail);
		
		txtemail = new JTextField();
		txtemail.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtemail.setColumns(10);
		txtemail.setBounds(181, 42, 179, 19);
		getContentPane().add(txtemail);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblUserName.setBounds(24, 74, 111, 22);
		getContentPane().add(lblUserName);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtUsername.setColumns(10);
		txtUsername.setBounds(181, 74, 179, 19);
		getContentPane().add(txtUsername);
		
		JLabel lblTemail = new JLabel("Temporary Password");
		lblTemail.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblTemail.setBounds(24, 111, 147, 22);
		getContentPane().add(lblTemail);
		
		txtTemppass = new JTextField();
		txtTemppass.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtTemppass.setColumns(10);
		txtTemppass.setBounds(181, 111, 179, 19);
		getContentPane().add(txtTemppass);
		
		btnCreate = new JButton("Create Credential");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String empid,email,username,temppass;
				int eeid;
				empid = txtid.getText().trim();
				eeid = Integer.parseInt(empid);
				
				email = txtemail.getText().trim();
				username = txtUsername.getText().trim();
				temppass = txtTemppass.getText().trim();
				boolean check = EmailValidation.isValid(email);
				if (check) {	
					// Get the current date  
			        LocalDate localDate = LocalDate.now();  
			          
			        // Convert LocalDate to java.sql.Date  
			        Date sqlDate = Date.valueOf(localDate);
			        
					try {
						connect();
						pst1 = con1.prepareStatement("Update `employees` SET `username` = ?,"
								+ "`password` = ?,"
								+ "`email` = ? ,`userlevel` = ?, `assignedDate` = ?, `isEnabled`=? WHERE `employeeID`=?");
						pst1.setString(1,username);
						pst1.setString(2,temppass);
						pst1.setString(3,email);
						pst1.setInt(4,1);
						pst1.setDate(5, sqlDate);
						pst1.setInt(6,1);
						pst1.setInt(7,eeid);
	
						pst1.executeUpdate();
	
						pst1.close();
						disconnect();
						JOptionPane.showMessageDialog(null,"The user credential is created!","Result",JOptionPane.INFORMATION_MESSAGE);
						clearform();
						xpractice1.load_tbl();
						dispose();
					} catch (SQLException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null,"Please provide a valid email address",
							 "Customer Creation",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnCreate.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnCreate.setBounds(104, 143, 178, 27);
		getContentPane().add(btnCreate);

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
	
	public void clearform() {
		txtid.setText("");
		txtemail.setText("");
		txtUsername.setText("");
		txtTemppass.setText("");
		
	}
}

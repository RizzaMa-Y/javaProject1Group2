package package1;

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
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login{
	Connection con1;
	Statement st1;
	PreparedStatement pst1;
	ResultSet rs1;

	public JFrame frame;
	public JPanel panel;
	public JLabel lblNewLabel;
	public JLabel lblPassword;
	public JTextField textUsername;
	public JTextField passwordField;
	public Application1 app1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		app1 = new Application1(null,0);
		frame = new JFrame();
		frame.setTitle("Login Form");
		frame.setResizable(false);
		frame.setBounds(100, 100, 425, 224);
		frame.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		frame.setContentPane(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabel.setBounds(51, 41, 99, 21);
		panel.add(lblNewLabel);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblPassword.setBounds(51, 78, 99, 21);
		panel.add(lblPassword);

		textUsername = new JTextField();
		textUsername.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		textUsername.setBounds(160, 38, 199, 27);
		panel.add(textUsername);
		textUsername.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		passwordField.setBounds(160, 75, 199, 27);
		panel.add(passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btnLogin.setBounds(160, 124, 123, 29);
		panel.add(btnLogin);

		JLabel lblForgot = new JLabel("Forgot Password");
		lblForgot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null,"Please contact your admin for credentials","Login",JOptionPane.INFORMATION_MESSAGE);

			}
		});
		lblForgot.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		lblForgot.setBounds(181, 163, 86, 14);
		panel.add(lblForgot);
	}
	public void login() {
		String userName, Password,userAccess = null;

		int userLevel, updatePassword,empId;
		userName = textUsername.getText();
		Password = passwordField.getText();

		try {
			connect();
			pst1 = con1.prepareStatement("SELECT employeeID,first_name,mid_name,last_name,position"
					+ ",branch,salary,created_date,userlevel,passwordUpdate from employees where username=? and password=?");
			pst1.setString(1,userName);
			pst1.setString(2,Password);
			rs1 = pst1.executeQuery();

			if(rs1.next()){
				JOptionPane.showMessageDialog(null,"Welcome " + rs1.getString("first_name") +"!","Login Success",JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
				updatePassword = rs1.getInt("passwordUpdate");
				userLevel = rs1.getInt("userlevel");
				empId = rs1.getInt("employeeID");
				//assign user level
				if(userLevel == 0) {
					userAccess = "Super Admin";
				}else if(userLevel == 1){
					userAccess = "Admin";
				}else if(userLevel == 2){
					userAccess = "User";
				}
				//check if password needs update
				if(updatePassword ==0) {
					UpdatePassword.main(empId,userAccess);
				}else {
					Application1.main(userAccess,empId);
					//Application1.getInstance(userAccess,empId);
				}
				
            }
            else{
            	JOptionPane.showMessageDialog(null,"Invalid Credentials...","Login Failed",JOptionPane.INFORMATION_MESSAGE);

            }
			disconnect();
		} catch (Exception e2) {
			// TODO: handle exception
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
		} catch (Exception e) {
			System.out.print(e.toString());

		}

	}

	public void setUserLevel() {

	}
}

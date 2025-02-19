package package1;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class UpdatePassword {
    private JFrame frame;
    private JPanel panel;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private Connection con1;
    private PreparedStatement pst1;

    /**
     * Launch the application.
     */
    public static void main(int empID, String UserAccess) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UpdatePassword window = new UpdatePassword(empID,UserAccess);
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
    public UpdatePassword(int empID, String Userlevel) {
        frame = new JFrame();
        frame.setTitle("Update Password");
        frame.setResizable(false);
        frame.setBounds(100, 100, 468, 224);
        frame.getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(panel);
        panel.setLayout(null);

        JLabel lblNewPassword = new JLabel("New Password");
        lblNewPassword.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        lblNewPassword.setBounds(51, 41, 120, 21);
        panel.add(lblNewPassword);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        lblConfirmPassword.setBounds(51, 78, 150, 21);
        panel.add(lblConfirmPassword);

        newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        newPasswordField.setBounds(210, 38, 199, 27);
        panel.add(newPasswordField);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					updatePassword(empID, Userlevel);
				}
			}
		});
        confirmPasswordField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        confirmPasswordField.setBounds(210, 75, 199, 27);
        panel.add(confirmPasswordField);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassword(empID, Userlevel);
            }
        });
        btnUpdate.setFont(new Font("Century Gothic", Font.BOLD, 16));
        btnUpdate.setBounds(163, 122, 123, 29);
        panel.add(btnUpdate);
    }

    public void updatePassword(int empID, String Userlevel) {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            connect();
            pst1 = con1.prepareStatement("UPDATE employees SET `password`=?,`passwordUpdate`=? WHERE `employeeID`=?");
            pst1.setString(1, newPassword);
            pst1.setInt(2, 1);
            pst1.setInt(3, empID);
            pst1.executeUpdate();
            JOptionPane.showMessageDialog(null, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            disconnect();
            if (Application1.getInstance(Userlevel,empID).frmApplication.isVisible()) {
                Application1.getInstance(Userlevel,empID).frmApplication.toFront();
            } else {
                Application1.main(Userlevel, empID);
                //Application1.getInstance(Userlevel,empID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/pwcdb1", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            pst1.close();
            con1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
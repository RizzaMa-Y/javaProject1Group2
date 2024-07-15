package package1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ComputeNetPay extends JFrame {

	private JPanel contentPane;
	private JTextField txtRate;
	private JTextField txtHrs;
	private JTextField txtDeducts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComputeNetPay frame = new ComputeNetPay();
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
	public ComputeNetPay() {
		setType(Type.POPUP);
		setTitle("Compute Net Pay");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 678, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Rate per Hour");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(73, 46, 218, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Number of Hours Worked");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(73, 109, 218, 22);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Deductions");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(73, 173, 218, 22);
		contentPane.add(lblNewLabel_2);
		
		txtRate = new JTextField();
		txtRate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtRate.setBounds(331, 47, 223, 19);
		contentPane.add(txtRate);
		txtRate.setColumns(10);
		
		txtHrs = new JTextField();
		txtHrs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtHrs.setBounds(331, 110, 223, 19);
		contentPane.add(txtHrs);
		txtHrs.setColumns(10);
		
		txtDeducts = new JTextField();
		txtDeducts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDeducts.setBounds(331, 174, 223, 19);
		contentPane.add(txtDeducts);
		txtDeducts.setColumns(10);
		
		JButton btnNewButton = new JButton("Compute and show answer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rateperhour = Integer.parseInt(txtRate.getText().trim());
				int hrsworked = Integer.parseInt(txtHrs.getText().trim());
				int deductions = Integer.parseInt(txtDeducts.getText().trim());
				
				int myNetPay = (rateperhour * hrsworked) - deductions;
				
				JOptionPane.showMessageDialog(null,"Net pay is PHP " + myNetPay,"Computed Net Pay Result",JOptionPane.INFORMATION_MESSAGE);
				
				txtRate.setText("");
				txtHrs.setText("");
				txtDeducts.setText("");
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(173, 232, 328, 31);
		contentPane.add(btnNewButton);
	}
}

package package1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterEmployee extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox cmbPosition;
	private JCheckBox chkAM;
	private JCheckBox chkPM;
	private JRadioButton locManila;
	private JRadioButton locBaguio;
	private JRadioButton locCebu;
	private JTextArea txtAddress;
	private JList lstExpect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterEmployee frame = new RegisterEmployee();
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
	public RegisterEmployee() {
		setTitle("Register New Employee");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 958, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(41, 52, 106, 19);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Address");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(41, 110, 106, 19);
		contentPane.add(lblNewLabel_1);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setBounds(156, 49, 242, 25);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Position");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(41, 209, 106, 19);
		contentPane.add(lblNewLabel_1_1);
		
		cmbPosition = new JComboBox();
		cmbPosition.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cmbPosition.setModel(new DefaultComboBoxModel(new String[] {"Associate", "Senior Associate", "Super Visor", "Manager", "Director", "Managing Director"}));
		cmbPosition.setBounds(156, 203, 242, 25);
		contentPane.add(cmbPosition);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Schedule");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(41, 252, 106, 19);
		contentPane.add(lblNewLabel_1_1_1);
		
		chkAM = new JCheckBox("AM");
		chkAM.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chkAM.setBounds(156, 253, 93, 21);
		contentPane.add(chkAM);
		
		chkPM = new JCheckBox("PM");
		chkPM.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chkPM.setBounds(156, 288, 93, 21);
		contentPane.add(chkPM);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Location");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(440, 52, 106, 19);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		locManila = new JRadioButton("Manila");
		locManila.setSelected(true);
		buttonGroup.add(locManila);
		locManila.setFont(new Font("Tahoma", Font.PLAIN, 15));
		locManila.setBounds(503, 88, 103, 21);
		contentPane.add(locManila);
		
		locBaguio = new JRadioButton("Baguio");
		buttonGroup.add(locBaguio);
		locBaguio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		locBaguio.setBounds(503, 111, 103, 21);
		contentPane.add(locBaguio);
		
		locCebu = new JRadioButton("Cebu");
		buttonGroup.add(locCebu);
		locCebu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		locCebu.setBounds(503, 134, 103, 21);
		contentPane.add(locCebu);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Expected Monthly Salary");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1_1.setBounds(635, 49, 201, 19);
		contentPane.add(lblNewLabel_1_1_1_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(157, 110, 270, 72);
		contentPane.add(scrollPane);
		
		txtAddress = new JTextArea();
		scrollPane.setViewportView(txtAddress);
		txtAddress.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(645, 78, 289, 92);
		contentPane.add(scrollPane_1);
		
		lstExpect = new JList();
		lstExpect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstExpect.setModel(new AbstractListModel() {
			String[] values = new String[] {"30000-39000", "40000-49000", "50000-59000", "60000-69000"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_1.setViewportView(lstExpect);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ename = txtName.getText().trim();
				String eAddress = txtAddress.getText().trim();
				String ePosition = cmbPosition.getSelectedItem().toString().trim();
				String eSched = "";
				if(chkAM.isSelected() && chkPM.isSelected()){
					eSched = "Whole Day";
				}else if(chkAM.isSelected() && !chkPM.isSelected()){
					eSched = "Morning Shift";
				}else if(!chkAM.isSelected() && chkPM.isSelected()){
					eSched = "Night Shift";
				}else {
					eSched = "N/A";
				}
				
				String eLoc = "";
				if(locManila.isSelected()) {
					eLoc = "Manila";
				}
				
				if (locBaguio.isSelected()) {
					eLoc = "Baguio";
				}
				
				if (locCebu.isSelected()) {
					eLoc = "Cebu";
				}
				
				String eSalary = (String)lstExpect.getSelectedValue();
				
				String eOutput = "Name: " + ename 
						+ "\nAddress: " + eAddress
						+ "\nPosition: " + ePosition
						+ "\nSchedule: " + eSched
						+ "\nLocation: " + eLoc
						+ "\nExpected Salary: " + eSalary;
				JOptionPane.showMessageDialog(null,eOutput,"Result",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSubmit.setBounds(683, 288, 207, 37);
		contentPane.add(btnSubmit);
	}
}

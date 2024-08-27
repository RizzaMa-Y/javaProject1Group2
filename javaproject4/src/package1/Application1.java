package package1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;


//import net.proteanit.sql.DbUtils;

public class Application1 {
	public String userLevelx;
	private JFrame frmApplication;
	private final JDesktopPane desktopPane = new JDesktopPane();
	private JMenuBar menuBar;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;

	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	private JMenu mnNewMenu;
	private JMenuItem mntmProducts;
	private JMenuItem mntmOrders;
	private JMenu mnNewMenu_2;
	private JMenuItem mntmLogNewOrder;
	private Customers cus2;
	private Orders or2;

	/**
	 * Launch the application.
	 */
	public static void main(String userAccess) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Application1 window = new Application1();
					window.frmApplication.setVisible(true);
					System.out.print("System User Access is: " + userAccess);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application1() {
		initialize();
		//connect();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmApplication = new JFrame();
		frmApplication.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmApplication.setTitle("Welcome to the ABZ Warehouse Management System");
		frmApplication.setBounds(100, 100, 1208, 720);
		frmApplication.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frmApplication.getContentPane().setLayout(new BorderLayout(0, 0));

		desktopPane.setBounds(100, 100, 1208, 720);
		frmApplication.getContentPane().add(desktopPane, BorderLayout.CENTER);

		menuBar = new JMenuBar();
		frmApplication.setJMenuBar(menuBar);

		mnNewMenu = new JMenu("Initial Actions");
		menuBar.add(mnNewMenu);
		
		//Warehouse - Practice2
		Practice2 pr2 = new Practice2();
		pr2.setSize(845, 335);
		pr2.setLocation(10, 397);

		//Employee - Practice1
		Practice1 pr1 = new Practice1();
		pr1.setSize(1132, 385);
		pr1.setLocation(10, 10);

		//Products
		Products pro2 = new Products();
		pro2.setSize(845, 390);
		pro2.setLocation(10, 10);
		
		//Delivery
		Delivery del2 = new Delivery();
		del2.setSize(956, 395);
		del2.setLocation(10, 10);
		
		//Orders
		or2 = new Orders();
		or2.setSize(842, 390);
		or2.setLocation(470, 10);

		//Customers
		cus2 = new Customers(or2);
		cus2.setSize(842, 450);
		cus2.setLocation(10, 10);

		///WINDOWSSS FRAME

		desktopPane.add(pr2);
		desktopPane.add(pr1);
		desktopPane.add(pro2);
		desktopPane.add(cus2);
		desktopPane.add(or2);
		desktopPane.add(del2);

		mntmNewMenuItem = new JMenuItem("Employee Management");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pr1.setVisible(true);
				pr1.toFront();
			}
		});

		mntmNewMenuItem_1 = new JMenuItem("Warehouse Management");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pr2.setVisible(true);
				pr2.toFront();
			}
		});

		mnNewMenu.add(mntmNewMenuItem);
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenu mnNewMenu_1 = new JMenu("Inventory Management");
		menuBar.add(mnNewMenu_1);

		mntmProducts = new JMenuItem("Product Management");
		mntmProducts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pro2.setVisible(true);
				pro2.toFront();
			}
		});
		mnNewMenu_1.add(mntmProducts);

		mntmOrders = new JMenuItem("Delivery Management");
		mntmOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				del2.setVisible(true);
				del2.toFront();
				del2.load_tbl();
			}
		});
		mnNewMenu_1.add(mntmOrders);

		mnNewMenu_2 = new JMenu("Product Orders");
		mnNewMenu_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cus2.setVisible(true);
				cus2.toFront();
			}
		});
		menuBar.add(mnNewMenu_2);

		mntmLogNewOrder = new JMenuItem("New order");
		mntmLogNewOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cus2.setVisible(true);
				cus2.toFront();
			}
		});
		mnNewMenu_2.add(mntmLogNewOrder);

	}

	public void connect() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pwcdb1","root","");
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

		}catch(ClassNotFoundException ex){

		}catch(Exception e){
			System.out.print(e.toString());
		}
	}

	public void disconnect() {

		try {

			st.close();
			con.close();
		} catch (Exception e) {
			System.out.print(e.toString());

		}

	}


}

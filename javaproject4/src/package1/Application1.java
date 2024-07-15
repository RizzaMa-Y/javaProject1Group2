package package1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Application1 {

	private JFrame frmApplication;
	private final JDesktopPane desktopPane = new JDesktopPane();
	private JMenuBar menuBar;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application1 window = new Application1();
					window.frmApplication.setVisible(true);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmApplication = new JFrame();
		frmApplication.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmApplication.setTitle("Application1");
		frmApplication.setBounds(100, 100, 1208, 720);
		frmApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmApplication.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//desktopPane = new JDesktopPane();
		desktopPane.setBounds(100, 100, 1208, 720);
		frmApplication.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		frmApplication.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Activities");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Practice 1");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Practice1 pr1 = new Practice1();
				desktopPane.add(pr1);
				pr1.show();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("Practice 2");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Practice2 pr2 = new Practice2();
				desktopPane.add(pr2);
				pr2.show();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("About Us");
		menuBar.add(mnNewMenu_1);
	}
}

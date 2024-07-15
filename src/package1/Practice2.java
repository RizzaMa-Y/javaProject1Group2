package package1;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class Practice2 extends JInternalFrame {

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
		setIconifiable(true);
		setMaximizable(true);
		setTitle("Practice 2");
		setBounds(100, 100, 450, 300);

	}

}

package package1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
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
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Application1 {
    public static String userLevelx;
    private JFrame frmApplication;
    private BackgroundDesktopPane desktopPane;
    private Image backgroundImage;

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
    private Credentials cr1;
    private Practice1 pr1;
    private JMenu mnNewMenu_1;
    private Products pro2;
    private Delivery del2;
    private Practice2 pr2;

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
                    userLevelx = userAccess;
                    window.userAccesss();
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
        // connect();
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

        // Load the background image using a relative path
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/Background.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        // Create the custom JDesktopPane with the background image
        desktopPane = new BackgroundDesktopPane(backgroundImage);
        frmApplication.getContentPane().add(desktopPane, BorderLayout.CENTER);

        menuBar = new JMenuBar();
        frmApplication.setJMenuBar(menuBar);

        mnNewMenu = new JMenu("Initial Actions");

        cr1 = new Credentials();
        cr1.setSize(396, 212);
        cr1.setLocation(10, 397);

        pr2 = new Practice2();
        pr2.setSize(845, 335);
        pr2.setLocation(10, 397);

        pr1 = new Practice1(cr1);
        pr1.setSize(1132, 385);
        pr1.setLocation(10, 10);

        pro2 = new Products();
        pro2.setSize(845, 390);
        pro2.setLocation(10, 10);

        del2 = new Delivery();
        del2.setSize(956, 395);
        del2.setLocation(10, 10);

        // Orders
        or2 = new Orders();
        or2.setSize(842, 390);
        or2.setLocation(470, 10);

        // Customers
        cus2 = new Customers(or2);
        cus2.setSize(842, 450);
        cus2.setLocation(10, 10);

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

        mnNewMenu_1 = new JMenu("Inventory Management");

        mntmProducts = new JMenuItem("Product Management");
        mntmProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pro2.setVisible(true);
                pro2.toFront();
                pro2.load_tbl();
            }
        });

        mntmOrders = new JMenuItem("Delivery Management");
        mntmOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                del2.setVisible(true);
                del2.toFront();
                del2.load_tbl();
            }
        });
        mnNewMenu_2 = new JMenu("Product Orders");
        mnNewMenu_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cus2.setVisible(true);
                cus2.toFront();
            }
        });

        mntmLogNewOrder = new JMenuItem("New order");
        mntmLogNewOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cus2.setVisible(true);
                cus2.toFront();
            }
        });
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pwcdb1", "root", "");
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (ClassNotFoundException ex) {
        } catch (Exception e) {
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

    public void userAccesss() {
        /// WINDOWSSS FRAME
        if ("Super Admin".equals(userLevelx)) {
            menuBar.add(mnNewMenu);
            mnNewMenu.add(mntmNewMenuItem);
            mnNewMenu.add(mntmNewMenuItem_1);
            menuBar.add(mnNewMenu_1);
            mnNewMenu_1.add(mntmProducts);
            mnNewMenu_1.add(mntmOrders);
            menuBar.add(mnNewMenu_2);
            mnNewMenu_2.add(mntmLogNewOrder);
            desktopPane.add(pr2);
            desktopPane.add(pr1);
            desktopPane.add(pro2);
            desktopPane.add(cus2);
            desktopPane.add(or2);
            desktopPane.add(del2);
            desktopPane.add(cr1);
        } else {
            menuBar.add(mnNewMenu_1);
            mnNewMenu_1.add(mntmProducts);
            mnNewMenu_1.add(mntmOrders);
            menuBar.add(mnNewMenu_2);
            mnNewMenu_2.add(mntmLogNewOrder);
            desktopPane.add(pr2);
            desktopPane.add(pr1);
            desktopPane.add(pro2);
            desktopPane.add(cus2);
            desktopPane.add(or2);
            desktopPane.add(del2);
            desktopPane.add(cr1);
        }
    }
}

class BackgroundDesktopPane extends JDesktopPane {
    private Image backgroundImage;

    public BackgroundDesktopPane(Image image) {
        this.backgroundImage = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw the image scaled to the size of the JDesktopPane
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
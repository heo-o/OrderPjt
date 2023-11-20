import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class MenuInfo extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuInfo dialog = new MenuInfo("");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JLabel lblMenuNames;
	private JLabel lblMenuCals;
	private JLabel lblMenuPrices;
	private JLabel lblImage;

	/**
	 * Create the dialog.
	 * @param menuName 
	 */
	public MenuInfo(String menuName) {
		setBounds(100, 100, 521, 284);
		getContentPane().setLayout(null);
		
		 lblImage = new JLabel("");
		lblImage.setOpaque(true);
		lblImage.setBackground(Color.WHITE);
		lblImage.setBounds(28, 23, 279, 206);
		getContentPane().add(lblImage);
		
		JLabel lblMenuName = new JLabel("메뉴 이름");
		lblMenuName.setBounds(344, 52, 61, 16);
		getContentPane().add(lblMenuName);
		
		JLabel lblMenuCal = new JLabel("메뉴 열량");
		lblMenuCal.setBounds(344, 120, 61, 16);
		getContentPane().add(lblMenuCal);
		
		JLabel lblMenuPrice = new JLabel("메뉴 가격");
		lblMenuPrice.setBounds(344, 188, 61, 16);
		getContentPane().add(lblMenuPrice);
		
		 lblMenuNames = new JLabel("New label");
		lblMenuNames.setBounds(417, 52, 61, 16);
		getContentPane().add(lblMenuNames);
		
		 lblMenuCals = new JLabel("New label");
		lblMenuCals.setBounds(417, 120, 61, 16);
		getContentPane().add(lblMenuCals);
		
		 lblMenuPrices = new JLabel("New label");
		lblMenuPrices.setBounds(417, 188, 61, 16);
		getContentPane().add(lblMenuPrices);
		
		setTitle(menuName);
		
		ViewMenu(menuName);

	}

	private void ViewMenu(String menuName) {
		String url ="jdbc:mysql://localhost:3306/order";
		String user="root";
		String password = "12345678";
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, user,password);
				
				String sql = "select * from menutbl where menuName=?";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, menuName);
				
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					String menuKcal = rs.getString("menuKcal");
					String menuPrice = rs.getString("menuPrice");
					String path = rs.getString("menuImage");
					
					lblMenuCals.setText(menuKcal + " kcal");
					lblMenuPrices.setText(menuPrice + " 원");
					lblMenuNames.setText(menuName);
					
					ImageIcon icon = new ImageIcon(path);
					Image image = icon.getImage();
					image = image.getScaledInstance(279, 206, image.SCALE_SMOOTH);
					icon = new ImageIcon(image);
					lblImage.setIcon(icon);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
}

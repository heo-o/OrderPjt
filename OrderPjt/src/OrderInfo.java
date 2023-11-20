import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderInfo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel lblMenuNames;
	private JLabel lblMenuCals;
	private JLabel lblTotalPrice;
	private JLabel lblMenuCount;
	private JLabel lblMenuPrices;
	private JLabel lblImage;
	private JLabel lblTotal;
	private JLabel lblCount;
	private JLabel lblMenuPrice;
	private JLabel lblMenuCal;
	private JLabel lblMenuName;
	private JLabel lblOrderInfomation;
	private JSeparator separator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderInfo dialog = new OrderInfo("");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 * @param userId 
	 */
	public OrderInfo(String userId) {
		setBounds(100, 100, 684, 388);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 57, 304, 240);
		getContentPane().add(scrollPane);
		
		String columnNames[] = {"주문 날짜", "주문 메뉴","주소"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		JLabel lblorderInfo = new JLabel("주문 내역");
		lblorderInfo.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblorderInfo.setBounds(135, 19, 78, 27);
		getContentPane().add(lblorderInfo);
		
		 lblImage = new JLabel("");
		lblImage.setOpaque(true);
		lblImage.setBackground(Color.WHITE);
		lblImage.setBounds(345, 57, 153, 134);
		getContentPane().add(lblImage);
		
		 lblMenuName = new JLabel("메뉴 이름");
		lblMenuName.setBounds(523, 66, 61, 16);
		getContentPane().add(lblMenuName);
		
		 lblMenuCal = new JLabel("메뉴 열량");
		lblMenuCal.setBounds(523, 105, 61, 16);
		getContentPane().add(lblMenuCal);
		
		 lblMenuPrice = new JLabel("메뉴 가격");
		lblMenuPrice.setBounds(523, 144, 61, 16);
		getContentPane().add(lblMenuPrice);
		
		 lblCount = new JLabel("수량");
		lblCount.setBounds(533, 186, 31, 16);
		getContentPane().add(lblCount);
		
		 lblTotal = new JLabel("총 가격");
		lblTotal.setBounds(430, 254, 61, 16);
		getContentPane().add(lblTotal);
		
		 separator = new JSeparator();
		separator.setBounds(345, 214, 332, 12);
		getContentPane().add(separator);
		
		 lblMenuNames = new JLabel("New label");
		lblMenuNames.setBounds(596, 66, 61, 16);
		getContentPane().add(lblMenuNames);
		
		 lblMenuCals = new JLabel("New label");
		lblMenuCals.setBounds(596, 105, 61, 16);
		getContentPane().add(lblMenuCals);
		
		 lblMenuPrices = new JLabel("New label");
		lblMenuPrices.setBounds(596, 144, 61, 16);
		getContentPane().add(lblMenuPrices);
		
		 lblTotalPrice = new JLabel("0");
		lblTotalPrice.setBounds(523, 254, 130, 16);
		getContentPane().add(lblTotalPrice);
		
		 lblOrderInfomation = new JLabel("상세 주문 내역");
		lblOrderInfomation.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblOrderInfomation.setBounds(476, 19, 124, 27);
		getContentPane().add(lblOrderInfomation);
		
		JButton btnOrderInfo = new JButton("상세 내역 보기");
		btnOrderInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderInfo(userId);
				OrderMenuImage();
			}
		});
		btnOrderInfo.setBounds(110, 309, 117, 29);
		getContentPane().add(btnOrderInfo);
		
		 lblMenuCount = new JLabel("New label");
		lblMenuCount.setBounds(596, 186, 61, 16);
		getContentPane().add(lblMenuCount);
		
		
		ViewDisplay(userId);
		NotOrderInfo();

	}

	protected void OrderMenuImage() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			int row = table.getSelectedRow();
			String OrderMenu = table.getValueAt(row, 1).toString();
			
			String sql = "select * from menutbl where menuName=?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, OrderMenu);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String path = rs.getString("menuImage");
				
				ImageIcon icon = new ImageIcon(path);
				Image image = icon.getImage();
				image = image.getScaledInstance(153, 134, image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				
				lblImage.setIcon(icon);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void OrderInfo(String userId) {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			int row = table.getSelectedRow();
			String OrderDate = table.getValueAt(row, 0).toString();
			
			String sql = "select * from ordertbl where orderDate=? and userId=?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, OrderDate);
			pstmt.setString(2, userId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String menuName = rs.getString("menuName");
				String menuKcal = rs.getString("menuKcal");
				String menuTotal = rs.getString("menuTotal");
				String menuCount = rs.getString("menuCount");
				String menuPrice = rs.getString("menuPrice");
				String userAddress = rs.getString("userAddress");
				
				lblMenuNames.setText(menuName);
				lblMenuCals.setText(menuKcal);
				lblTotalPrice.setText(menuTotal);
				lblMenuCount.setText(menuCount);
				lblMenuPrices.setText(menuPrice);
				
				ViewOrderInfo();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void NotOrderInfo() {
		lblMenuNames.setVisible(false);
		lblMenuCals.setVisible(false);
		lblTotalPrice.setVisible(false);
		lblMenuCount.setVisible(false);
		lblMenuPrices.setVisible(false);
		lblImage.setVisible(false);
		lblCount.setVisible(false);
		lblMenuCal.setVisible(false);
		lblMenuName.setVisible(false);
		lblMenuPrice.setVisible(false);
		lblOrderInfomation.setVisible(false);
		lblTotal.setVisible(false);
		separator.setVisible(false);
		
	}

	private void ViewOrderInfo() {
		lblMenuNames.setVisible(true);
		lblMenuCals.setVisible(true);
		lblTotalPrice.setVisible(true);
		lblMenuCount.setVisible(true);
		lblMenuPrices.setVisible(true);
		lblImage.setVisible(true);
		lblCount.setVisible(true);
		lblMenuCal.setVisible(true);
		lblMenuName.setVisible(true);
		lblMenuPrice.setVisible(true);
		lblOrderInfomation.setVisible(true);
		lblTotal.setVisible(true);
		separator.setVisible(true);
		
	}

	private void ViewDisplay(String userId) {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			String sql = "select orderDate,menuName,userAddress from ordertbl where userId = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			while(rs.next()) {
				Vector<String> vec = new Vector<String>();
				for(int i = 1; i <=3; i++) {
					vec.add(rs.getString(i));
				}
				dtm.addRow(vec);
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

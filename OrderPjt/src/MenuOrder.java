import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.awt.event.ActionEvent;

public class MenuOrder extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfCount;
	private JTextField tfAddress;
	private JLabel lblMenuNames;
	private JLabel lblMenuCals;
	private JLabel lblMenuPrices;
	private JLabel lblImage;
	private JLabel lblTotalPrice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuOrder dialog = new MenuOrder("","");
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
	 * @param menuName 
	 * @param  
	 */
	public MenuOrder(String menuName,String userId) {
		setTitle(menuName);
		setBounds(100, 100, 605, 410);
		getContentPane().setLayout(null);
		
		 lblImage = new JLabel("");
		lblImage.setOpaque(true);
		lblImage.setBackground(Color.WHITE);
		lblImage.setBounds(40, 41, 279, 206);
		getContentPane().add(lblImage);
		
		JLabel lblMenuName = new JLabel("메뉴 이름");
		lblMenuName.setBounds(356, 57, 61, 16);
		getContentPane().add(lblMenuName);
		
		JLabel lblMenuCal = new JLabel("메뉴 열량");
		lblMenuCal.setBounds(356, 96, 61, 16);
		getContentPane().add(lblMenuCal);
		
		JLabel lblMenuPrice = new JLabel("메뉴 가격");
		lblMenuPrice.setBounds(356, 135, 61, 16);
		getContentPane().add(lblMenuPrice);
		
		JLabel lblCount = new JLabel("수량");
		lblCount.setBounds(366, 177, 31, 16);
		getContentPane().add(lblCount);
		
		tfCount = new JTextField();
		tfCount.setText("1");
		tfCount.setBounds(415, 163, 43, 41);
		getContentPane().add(tfCount);
		tfCount.setColumns(10);
		
		JButton btnCountUp = new JButton("↑");
		btnCountUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add();
			}
		});
		btnCountUp.setBounds(459, 166, 48, 41);
		getContentPane().add(btnCountUp);
		
		JButton btnCountDown = new JButton("↓");
		btnCountDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Minus();
			}
		});
		btnCountDown.setBounds(507, 166, 48, 41);
		getContentPane().add(btnCountDown);
		
		JLabel lblTotal = new JLabel("총 가격");
		lblTotal.setBounds(402, 259, 61, 16);
		getContentPane().add(lblTotal);
		
		 lblTotalPrice = new JLabel("New label");
		lblTotalPrice.setBounds(475, 259, 130, 16);
		getContentPane().add(lblTotalPrice);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(360, 235, 209, 12);
		getContentPane().add(separator);
		
		JButton btnOrder = new JButton("주문하기");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderMenu(userId);
			}
		});
		btnOrder.setBounds(356, 335, 117, 29);
		getContentPane().add(btnOrder);
		
		JButton btnDispose = new JButton("뒤로가기");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDispose.setBounds(465, 335, 117, 29);
		getContentPane().add(btnDispose);
		
		tfAddress = new JTextField();
		tfAddress.setBounds(40, 291, 303, 29);
		getContentPane().add(tfAddress);
		tfAddress.setColumns(10);
		
		JLabel lblAddress = new JLabel("주 소");
		lblAddress.setBounds(50, 265, 61, 16);
		getContentPane().add(lblAddress);
		
		 lblMenuNames = new JLabel("New label");
		lblMenuNames.setBounds(429, 57, 61, 16);
		getContentPane().add(lblMenuNames);
		
		 lblMenuCals = new JLabel("New label");
		lblMenuCals.setBounds(429, 96, 61, 16);
		getContentPane().add(lblMenuCals);
		
		 lblMenuPrices = new JLabel("New label");
		lblMenuPrices.setBounds(429, 135, 61, 16);
		getContentPane().add(lblMenuPrices);
		
		
		ViewMenuInfo(menuName);
		Viewtotal();
		

	}

	protected void orderMenu(String userId) {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			Timestamp orderDate = new Timestamp(System.currentTimeMillis());
			String Kcal = lblMenuCals.getText().replace(" kcal", "");
			
			String sql = "insert into ordertbl (userId, orderDate, menuName,menuKcal,menuTotal,userAddress,menuPrice, menuCount) values (?,?,?,?,?,?,?,?)";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setTimestamp(2, orderDate);
			pstmt.setString(3, lblMenuNames.getText());
			pstmt.setString(4, Kcal);
			pstmt.setString(5, lblTotalPrice.getText());
			pstmt.setString(6, tfAddress.getText());
			pstmt.setString(7, lblMenuPrices.getText());
			pstmt.setString(8, tfCount.getText());
			
			int rs = pstmt.executeUpdate();
			
			if (rs>0) {
				JOptionPane.showMessageDialog(null, "주문 완료");
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "주문 실패","오류",JOptionPane.WARNING_MESSAGE);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
				
	}

	private void Viewtotal() {
		int count = Integer.parseInt(tfCount.getText());
		String priceStr = lblMenuPrices.getText().replace(" 원", "");
		int price = Integer.parseInt(priceStr);

		
		int totalPrice = count * price;
		lblTotalPrice.setText(Integer.toString(totalPrice));
	}

	protected void Minus() {
		int count = Integer.parseInt(tfCount.getText());
		
		if (count <= 10 && count > 1) {
			count -= 1;
			tfCount.setText(Integer.toString(count));
			Viewtotal();
		} else {
			JOptionPane.showMessageDialog(null, "최소 1개부터 주문 가능합니다.", "오류 메세지", JOptionPane.WARNING_MESSAGE);
		}
	}

	protected void Add() {
		int count = Integer.parseInt(tfCount.getText());
		
		if (count > 0 && count < 10) {
			count += 1;
			tfCount.setText(Integer.toString(count));
			Viewtotal();
		} else {
			JOptionPane.showMessageDialog(null, "최대 10개까지 주문 가능합니다.", "오류 메세지", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void ViewMenuInfo(String menuName) {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			String sql = "Select * from menutbl where menuName=?";
			
			PreparedStatement pstmt= con.prepareStatement(sql);
			
			pstmt.setString(1,menuName);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ImageIcon icon = new ImageIcon(rs.getString("menuImage"));
				Image image = icon.getImage();
				image = image.getScaledInstance(279, 206, image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				
				lblImage.setIcon(icon);
				lblMenuNames.setText(menuName);
				lblMenuCals.setText(rs.getString("menuKcal") + " kcal");
				lblMenuPrices.setText(rs.getString("menuPrice") + " 원");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

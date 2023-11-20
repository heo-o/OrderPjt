import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.io.FileFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class InsertMenu extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfMenuName;
	private JTextField tfMenuCal;
	private JTextField tfMenuPrice;
	private String path;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertMenu dialog = new InsertMenu();
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
	 */
	public InsertMenu() {
		setTitle("메뉴등록");
		setBounds(100, 100, 519, 376);
		getContentPane().setLayout(null);
		
		JLabel lblImage = new JLabel("");
		lblImage.setOpaque(true);
		lblImage.setBackground(new Color(255, 255, 255));
		lblImage.setBounds(39, 25, 218, 199);
		getContentPane().add(lblImage);
		
		JButton btnSearchImage = new JButton("사진찾기");
		btnSearchImage.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif");
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(imageFilter);
				
				int ret = chooser.showOpenDialog(null);
				
				if (ret == JFileChooser.APPROVE_OPTION) {
					 path = chooser.getSelectedFile().getPath();
					
					ImageIcon icon = new ImageIcon(path);
					Image image = icon.getImage();
					image = image.getScaledInstance(218, 199, image.SCALE_SMOOTH);
					icon = new ImageIcon(image);
					
					lblImage.setIcon(icon);
					
				}
				
			}
		});
		btnSearchImage.setBounds(86, 240, 117, 29);
		getContentPane().add(btnSearchImage);
		
		JLabel lblMenuName = new JLabel("메뉴 이름");
		lblMenuName.setBounds(285, 77, 61, 16);
		getContentPane().add(lblMenuName);
		
		tfMenuName = new JTextField();
		tfMenuName.setBounds(358, 72, 130, 26);
		getContentPane().add(tfMenuName);
		tfMenuName.setColumns(10);
		
		JLabel lblMenuCal = new JLabel("메뉴 열량");
		lblMenuCal.setBounds(285, 119, 61, 16);
		getContentPane().add(lblMenuCal);
		
		tfMenuCal = new JTextField();
		tfMenuCal.setColumns(10);
		tfMenuCal.setBounds(358, 114, 130, 26);
		getContentPane().add(tfMenuCal);
		
		JLabel lblMenuPrice = new JLabel("메뉴 가격");
		lblMenuPrice.setBounds(285, 158, 61, 16);
		getContentPane().add(lblMenuPrice);
		
		tfMenuPrice = new JTextField();
		tfMenuPrice.setColumns(10);
		tfMenuPrice.setBounds(358, 153, 130, 26);
		getContentPane().add(tfMenuPrice);
		
		JButton btnInsert = new JButton("메뉴등록");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuInsert();
			}
		});
		btnInsert.setBounds(372, 299, 117, 29);
		getContentPane().add(btnInsert);
		
		JButton btnDispose = new JButton("뒤로가기");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDispose.setBounds(257, 299, 117, 29);
		getContentPane().add(btnDispose);

	}

	protected void MenuInsert() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			String sql = "insert into menutbl (menuName, menuKcal, menuPrice, menuImage) value (?,?,?,?)";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfMenuName.getText());
			pstmt.setString(2, tfMenuCal.getText());
			pstmt.setString(3, tfMenuPrice.getText());
			pstmt.setString(4, path);
			
			int rs = pstmt.executeUpdate();
			
			if(rs>0) {
				JOptionPane.showMessageDialog(null, "메뉴등록 완료");
				dispose();
			} else {
				System.out.println("오류.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

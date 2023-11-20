import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JPasswordField;

public class SearchPw extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfUserName;
	private JTextField tfTel2;
	private JTextField tfTel3;
	private JComboBox cbTel1;
	private JTextField tfUserId;
	private JTextField tfEmail;
	private JComboBox cbEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchPw dialog = new SearchPw();
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
	public SearchPw() {
		setTitle("비밀번호 찾기");
		setBounds(100, 100, 421, 311);
		getContentPane().setLayout(null);
		
		JLabel lblUserName = new JLabel("이름");
		lblUserName.setBounds(23, 34, 61, 16);
		getContentPane().add(lblUserName);
		
		JLabel lblTel = new JLabel("연락처");
		lblTel.setBounds(23, 86, 61, 16);
		getContentPane().add(lblTel);
		
		 cbTel1 = new JComboBox();
		cbTel1.setModel(new DefaultComboBoxModel(new String[] {"010", "011", "017"}));
		cbTel1.setBounds(96, 82, 83, 27);
		getContentPane().add(cbTel1);
		
		JLabel lblNewLabel_2 = new JLabel("-");
		lblNewLabel_2.setBounds(179, 87, 13, 16);
		getContentPane().add(lblNewLabel_2);
		
		tfUserName = new JTextField();
		tfUserName.setBounds(96, 29, 130, 26);
		getContentPane().add(tfUserName);
		tfUserName.setColumns(10);
		
		tfTel2 = new JTextField();
		tfTel2.setBounds(191, 82, 83, 26);
		getContentPane().add(tfTel2);
		tfTel2.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("-");
		lblNewLabel_2_1.setBounds(281, 87, 13, 16);
		getContentPane().add(lblNewLabel_2_1);
		
		tfTel3 = new JTextField();
		tfTel3.setColumns(10);
		tfTel3.setBounds(293, 82, 83, 26);
		getContentPane().add(tfTel3);
		
		JButton btnSearchPw = new JButton("비밀번호 찾기");
		btnSearchPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchId();
			}
		});
		btnSearchPw.setBounds(62, 230, 117, 39);
		getContentPane().add(btnSearchPw);
		
		JButton btnDispose = new JButton("뒤로가기");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDispose.setBounds(241, 230, 117, 39);
		getContentPane().add(btnDispose);
		
		JLabel lblUserId = new JLabel("아이디");
		lblUserId.setBounds(23, 136, 61, 16);
		getContentPane().add(lblUserId);
		
		tfUserId = new JTextField();
		tfUserId.setColumns(10);
		tfUserId.setBounds(96, 131, 130, 26);
		getContentPane().add(tfUserId);
		
		JLabel lblEmail = new JLabel("이메일");
		lblEmail.setBounds(23, 185, 61, 16);
		getContentPane().add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(96, 180, 130, 26);
		getContentPane().add(tfEmail);
		
		JLabel lblNewLabel_5 = new JLabel("@");
		lblNewLabel_5.setBounds(238, 185, 20, 16);
		getContentPane().add(lblNewLabel_5);
		
		 cbEmail = new JComboBox();
		cbEmail.setModel(new DefaultComboBoxModel(new String[] {"naver.com", "hanmail.net", "gmail.com"}));
		cbEmail.setEditable(true);
		cbEmail.setBounds(258, 180, 131, 27);
		getContentPane().add(cbEmail);

	}

	protected void SearchId() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url, user, password);
			
			
			
			String sql = "SELECT * FROM membertbl where userName=? and userTel=? and userId=? and userEmail=?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, tfUserName.getText());
			pstmt.setString(2, cbTel1.getSelectedItem()+"-" + tfTel2.getText() + "-" + tfTel3.getText());
			pstmt.setString(3, tfUserId.getText());
			pstmt.setString(4, tfEmail.getText() + "@" + cbEmail.getSelectedItem());
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String userId = tfUserId.getText();
				UpdatePw updatePw = new UpdatePw(userId);
				updatePw.setVisible(true);
				dispose();
			} else { 
				JOptionPane.showMessageDialog(null, "입력하신 정보를 다시 확인해주세요.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

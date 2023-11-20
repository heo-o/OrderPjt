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

public class SearchId extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfUserName;
	private JTextField tfTel2;
	private JTextField tfTel3;
	private JComboBox cbTel1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchId dialog = new SearchId();
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
	public SearchId() {
		setTitle("아이디 찾기");
		setBounds(100, 100, 458, 242);
		getContentPane().setLayout(null);
		
		JLabel lblUserName = new JLabel("이름");
		lblUserName.setBounds(52, 34, 61, 16);
		getContentPane().add(lblUserName);
		
		JLabel lblTel = new JLabel("연락처");
		lblTel.setBounds(52, 86, 61, 16);
		getContentPane().add(lblTel);
		
		 cbTel1 = new JComboBox();
		cbTel1.setModel(new DefaultComboBoxModel(new String[] {"010", "011", "017"}));
		cbTel1.setBounds(125, 82, 83, 27);
		getContentPane().add(cbTel1);
		
		JLabel lblNewLabel_2 = new JLabel("-");
		lblNewLabel_2.setBounds(208, 87, 13, 16);
		getContentPane().add(lblNewLabel_2);
		
		tfUserName = new JTextField();
		tfUserName.setBounds(125, 29, 130, 26);
		getContentPane().add(tfUserName);
		tfUserName.setColumns(10);
		
		tfTel2 = new JTextField();
		tfTel2.setBounds(220, 82, 83, 26);
		getContentPane().add(tfTel2);
		tfTel2.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("-");
		lblNewLabel_2_1.setBounds(310, 87, 13, 16);
		getContentPane().add(lblNewLabel_2_1);
		
		tfTel3 = new JTextField();
		tfTel3.setColumns(10);
		tfTel3.setBounds(322, 82, 83, 26);
		getContentPane().add(tfTel3);
		
		JButton btnSearchId = new JButton("아이디 찾기");
		btnSearchId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchId();
			}
		});
		btnSearchId.setBounds(74, 142, 117, 39);
		getContentPane().add(btnSearchId);
		
		JButton btnDispose = new JButton("뒤로가기");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDispose.setBounds(265, 142, 117, 39);
		getContentPane().add(btnDispose);

	}

	protected void SearchId() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url, user, password);
			
			String sql = "SELECT userId FROM membertbl where userName=? and userTel=?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, tfUserName.getText());
			pstmt.setString(2, cbTel1.getSelectedItem()+"-" + tfTel2.getText() + "-" + tfTel3.getText());
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String foundId = rs.getString("userId");
				JOptionPane.showMessageDialog(null, "찾으시는 아이디는 " + foundId + " 입니다.");	
			} else { 
				JOptionPane.showMessageDialog(null, "입력하신 정보와 일치하는 아이디가 없습니다.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

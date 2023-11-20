import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Join extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfId;
	private JTextField tfEmail;
	private JTextField tfBirth;
	private JTextField tfTel2;
	private JTextField tfTel3;
	private JPasswordField pfPw;
	private JPasswordField pfPw2;
	private JLabel lblEquals;
	private JTextField tfName;
	private JComboBox cbEmail;
	private JComboBox cbTel1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Join dialog = new Join();
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
	public Join() {
		setTitle("회원가입");
		setBounds(100, 100, 450, 447);
		getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("아이디");
		lblId.setBounds(35, 29, 61, 16);
		getContentPane().add(lblId);
		
		tfId = new JTextField();
		tfId.setBounds(118, 29, 130, 26);
		getContentPane().add(tfId);
		tfId.setColumns(10);
		
		JLabel lblPw = new JLabel("패스워드");
		lblPw.setBounds(35, 74, 61, 16);
		getContentPane().add(lblPw);
		
		JLabel lblEmail = new JLabel("이메일");
		lblEmail.setBounds(35, 209, 61, 16);
		getContentPane().add(lblEmail);
		
		JLabel lblBirth = new JLabel("생년월일");
		lblBirth.setBounds(35, 254, 61, 16);
		getContentPane().add(lblBirth);
		
		JLabel lblTel = new JLabel("연락처");
		lblTel.setBounds(35, 299, 61, 16);
		getContentPane().add(lblTel);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(118, 204, 130, 26);
		getContentPane().add(tfEmail);
		tfEmail.setColumns(10);
		
		JButton btnNewButton = new JButton("중복확인");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// db 연동 후 일치하는 아이디 있는지 비교.
				checkId();
			}
		});
		btnNewButton.setBounds(250, 29, 117, 29);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_5 = new JLabel("@");
		lblNewLabel_5.setBounds(250, 209, 20, 16);
		getContentPane().add(lblNewLabel_5);
		
		tfBirth = new JTextField();
		tfBirth.setBounds(118, 249, 130, 26);
		getContentPane().add(tfBirth);
		tfBirth.setColumns(10);
		
		tfTel2 = new JTextField();
		tfTel2.setBounds(216, 289, 91, 26);
		getContentPane().add(tfTel2);
		tfTel2.setColumns(10);
		
		 cbTel1 = new JComboBox();
		cbTel1.setEditable(true);
		cbTel1.setModel(new DefaultComboBoxModel(new String[] {"010", "011", "017"}));
		cbTel1.setBounds(118, 288, 77, 27);
		getContentPane().add(cbTel1);
		
		tfTel3 = new JTextField();
		tfTel3.setColumns(10);
		tfTel3.setBounds(337, 287, 91, 26);
		getContentPane().add(tfTel3);
		
		JLabel lblNewLabel_5_1 = new JLabel("-");
		lblNewLabel_5_1.setBounds(198, 292, 20, 16);
		getContentPane().add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("-");
		lblNewLabel_5_2.setBounds(322, 292, 20, 16);
		getContentPane().add(lblNewLabel_5_2);
		
		JButton btnJoin = new JButton("회원가입");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Join();
			}
		});
		btnJoin.setBounds(78, 344, 117, 45);
		getContentPane().add(btnJoin);
		
		JButton btnNewButton_2 = new JButton("뒤로가기");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_2.setBounds(261, 344, 117, 45);
		getContentPane().add(btnNewButton_2);
		
		 cbEmail = new JComboBox();
		cbEmail.setEditable(true);
		cbEmail.setModel(new DefaultComboBoxModel(new String[] {"naver.com", "hanmail.net", "gmail.com"}));
		cbEmail.setBounds(270, 204, 131, 27);
		getContentPane().add(cbEmail);
		
		pfPw = new JPasswordField();
		pfPw.setBounds(118, 69, 130, 26);
		getContentPane().add(pfPw);
		
		JLabel lblPw2 = new JLabel("패스워드 확인");
		lblPw2.setBounds(35, 119, 77,
				16);
		getContentPane().add(lblPw2);
		
		pfPw2 = new JPasswordField();
		pfPw2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				equalsPw();
			}
		});
		pfPw2.setBounds(118, 114, 130, 26);
		getContentPane().add(pfPw2);
		
		 lblEquals = new JLabel("");
		lblEquals.setBounds(267, 119, 100, 16);
		getContentPane().add(lblEquals);
		
		JLabel lblName = new JLabel("이름");
		lblName.setBounds(35, 164, 61, 16);
		getContentPane().add(lblName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(118, 162, 130, 26);
		getContentPane().add(tfName);

	}

	protected void checkId() {
		
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url, user, password);
			
			String sql = "select * from membertbl where userId = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, tfId.getText());
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				System.out.println("아이디가 이미 존재합니다.");
				JOptionPane.showMessageDialog(null, "아이디가 이미 존재합니다.");
				tfId.setText("");
			} else {
				System.out.println("사용 가능한 아이디 입니다.");
				JOptionPane.showMessageDialog(null, "사용 가능한 아이디 입니다.");
				tfId.setEnabled(false);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	protected void Join() {
		
		if (!tfId.isEnabled() && lblEquals.getText().equals("비밀번호 일치")) {
			
			if (isEmpty(tfId) || isEmpty(pfPw) || isEmpty(pfPw2) || isEmpty(tfName) || isEmpty(tfEmail) || isEmpty(tfBirth) || isEmpty(tfTel2) || isEmpty(tfTel3)) {
	            JOptionPane.showMessageDialog(null, "입력되지 않은 항목이 있습니다. 모든 필드를 입력해주세요.");
	            return;
			}
			
			
			String url = "jdbc:mysql://localhost:3306/order";
			String user = "root";
			String password = "12345678";
			
			String userPw = new String(pfPw.getPassword());
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection con = DriverManager.getConnection(url, user, password);
				
				String sql = "INSERT INTO membertbl (userId, userPw, userName, userEmail, userBirth, userTel) VALUE (?, ?, ?, ?, ?, ?)";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, tfId.getText());
				pstmt.setString(2, userPw);
				pstmt.setString(3,  tfName.getText());
				pstmt.setString(4, tfEmail.getText() + "@" + cbEmail.getSelectedItem());
				pstmt.setString(5, tfBirth.getText());
				pstmt.setString(6, cbTel1.getSelectedItem() + "-" + tfTel2.getText() + "-" + tfTel3.getText());
				
				int rs = pstmt.executeUpdate();
				
				if (rs > 0) {
					JOptionPane.showMessageDialog(null, "회원가입 성공!");
					dispose();
					
				} else { 
					JOptionPane.showMessageDialog(null, "오류");
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "아이디 중복확인 또는 비밀번호가 일치하는지 확인하세요.");
		}
	
		
	}

	protected void equalsPw() {
		String userPw1 = new String(pfPw.getPassword());	
		String userPw2 = new String(pfPw2.getPassword());
		
		if (userPw1.equals(userPw2)) {
			lblEquals.setText("비밀번호 일치");
			lblEquals.setForeground(Color.green);
		} else {
			lblEquals.setText("비밀번호 불일치");
			lblEquals.setForeground(Color.red);
		}
	}
	
	private boolean isEmpty(JTextField textField) {
	    return textField.getText().trim().isEmpty();
	}
}

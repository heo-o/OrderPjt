import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfId;
	private JPasswordField pfPw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login dialog = new Login();
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
	public Login() {
		setTitle("로그인");
		setBounds(100, 100, 491, 281);
		getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("아이디 : ");
		lblId.setBounds(105, 70, 61, 16);
		getContentPane().add(lblId);
		
		JLabel lblPw = new JLabel("패스워드 : ");
		lblPw.setBounds(105, 117, 61, 16);
		getContentPane().add(lblPw);
		
		tfId = new JTextField();
		tfId.setBounds(160, 63, 130, 26);
		getContentPane().add(tfId);
		tfId.setColumns(10);
		
		JButton btnLogin = new JButton("로그인");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login();		// 로그인 아이디 비밀번호 확인.
			}
		});
		btnLogin.setBounds(302, 59, 117, 79);
		getContentPane().add(btnLogin);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(89, 161, 330, 12);
		getContentPane().add(separator);
		
		JLabel lblSerachId = new JLabel("아이디 찾기");
		lblSerachId.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					SearchId searchId = new SearchId();
					searchId.setModal(true);
					searchId.setVisible(true);
				}
			}
		});
		lblSerachId.setBounds(105, 181, 61, 16);
		getContentPane().add(lblSerachId);
		
		JLabel lblSearchPw = new JLabel("패스워드 찾기");
		lblSearchPw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					SearchPw searchPw = new SearchPw();
					searchPw.setModal(true);
					searchPw.setVisible(true);
				}
			}
		});
		lblSearchPw.setBounds(221, 181, 82, 16);
		getContentPane().add(lblSearchPw);
		
		JLabel lblJoin = new JLabel("회원가입");
		lblJoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					Join join = new Join();
					join.setModal(true);
					join.setVisible(true);
				}
			}
		});
		lblJoin.setBounds(358, 181, 61, 16);
		getContentPane().add(lblJoin);
		
		pfPw = new JPasswordField();
		pfPw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {		// 패스워드에서 엔터를 누를 시 로그인 메소드 사용.
					Login();
				}
			}
		});
		pfPw.setBounds(160, 112, 130, 26);
		getContentPane().add(pfPw);

	}

	protected void Login() {
		
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		String userPw = new String(pfPw.getPassword());
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url, user, password);
			
			String sql = "SELECT * FROM membertbl where userId = ? and userPw = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, tfId.getText());
			pstmt.setString(2, userPw);
			
			ResultSet rs = pstmt.executeQuery();
			
			 if (rs.next()) {
		            // 로그인 성공
		            if (tfId.getText().equals("admin")) {
		                // 관리자 로그인
		                JOptionPane.showMessageDialog(null, "관리자 모드로 로그인 성공");
		                AdminMode adminMode = new AdminMode();
		                adminMode.setVisible(true);
		            } else {
		            	
		                // 일반 사용자 로그인
		                JOptionPane.showMessageDialog(null, "사용자 모드로 로그인 성공");
		                String userName= rs.getString("userName");
		                String userId = rs.getString("userId");
		                UserMode userMode = new UserMode(userName,userId);
		                userMode.setVisible(true);
		            }
		            // 현재 창 닫기
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 일치하지 않습니다.");
		        }
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdatePw extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdatePw dialog = new UpdatePw("");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JLabel lblUserId;
	private JPasswordField pfPw;
	private JPasswordField pfPw2;
	private JLabel lblEquals;

	/**
	 * Create the dialog.
	 * @param userId 
	 */
	public UpdatePw(String userId) {
		setTitle("비밀번호 재설정");
		setBounds(100, 100, 453, 303);
		getContentPane().setLayout(null);
		
		 lblUserId = new JLabel("New label");
		lblUserId.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblUserId.setBounds(118, 38, 243, 26);
		getContentPane().add(lblUserId);
		
		JLabel lblPw = new JLabel("패스워드");
		lblPw.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPw.setBounds(64, 98, 61, 16);
		getContentPane().add(lblPw);
		
		pfPw = new JPasswordField();
		pfPw.setBounds(137, 87, 224, 40);
		getContentPane().add(pfPw);
		
		JLabel lblPw2 = new JLabel("패스워드 확인");
		lblPw2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPw2.setBounds(32, 156, 117, 16);
		getContentPane().add(lblPw2);
		
		pfPw2 = new JPasswordField();
		pfPw2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				equalsPw();
			}
		});
		pfPw2.setBounds(137, 145, 224, 40);
		getContentPane().add(pfPw2);
		
		JButton btnNewButton = new JButton("비밀번호 변경");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePw(userId);
			}
		});
		btnNewButton.setBounds(302, 214, 128, 40);
		getContentPane().add(btnNewButton);
		
		 lblEquals = new JLabel("");
		lblEquals.setBounds(365, 157, 82, 16);
		getContentPane().add(lblEquals);
		
		ViewDisplay(userId);

	}

	protected void updatePw(String userId) {
		if(lblEquals.getText() == "비밀번호 일치") {
			String url = "jdbc:mysql://localhost:3306/order";
			String user = "root";
			String password = "12345678";
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, user, password);
				
				String userPw = new String(pfPw.getPassword());
				String sql = "update membertbl set userPw=? where userId=?";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userPw);
				pstmt.setString(2, userId);
				
				int rs = pstmt.executeUpdate();
				
				if (rs > 0) {
					JOptionPane.showMessageDialog(null, "비밀번호 변경이 완료되었습니다. 다시 로그인 해주세요.");
					dispose();
					Login login = new Login();
					login.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "비밀번호를 다시 확인해주세요.","error",JOptionPane.WARNING_MESSAGE);
				}
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
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

	private void ViewDisplay(String userId) {
		lblUserId.setText("사용자 아이디 : " + userId);		
	}
}

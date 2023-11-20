import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.jar.Attributes.Name;
import java.awt.event.ActionEvent;

public class MemberInfo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfId;
	private JTextField tfEmail;
	private JTextField tfBirth;
	private JTextField tfTel;
	private JLabel lblUserInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberInfo dialog = new MemberInfo("", "", "", "","");
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
	 * @param tel 
	 * @param email 
	 * @param id 
	 * @param name 
	 * @param birth 
	 */
	public MemberInfo(String name, String id, String email, String tel, String birth) {
		setTitle("회원 상세 정보");
		setBounds(100, 100, 500, 323);
		getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("아이디");
		lblId.setBounds(119, 77, 61, 16);
		getContentPane().add(lblId);
		
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setColumns(10);
		tfId.setBounds(202, 72, 130, 26);
		getContentPane().add(tfId);
		
		JLabel lblEmail = new JLabel("이메일");
		lblEmail.setBounds(119, 116, 61, 16);
		getContentPane().add(lblEmail);
		
		JLabel lblBirth = new JLabel("생년월일");
		lblBirth.setBounds(119, 156, 61, 16);
		getContentPane().add(lblBirth);
		
		JLabel lblTel = new JLabel("연락처");
		lblTel.setBounds(119, 201, 61, 16);
		getContentPane().add(lblTel);
		
		tfEmail = new JTextField();
		tfEmail.setEditable(false);
		tfEmail.setColumns(10);
		tfEmail.setBounds(202, 111, 194, 26);
		getContentPane().add(tfEmail);
		
		tfBirth = new JTextField();
		tfBirth.setEditable(false);
		tfBirth.setColumns(10);
		tfBirth.setBounds(202, 151, 130, 26);
		getContentPane().add(tfBirth);
		
		tfTel = new JTextField();
		tfTel.setEditable(false);
		tfTel.setColumns(10);
		tfTel.setBounds(202, 196, 194, 26);
		getContentPane().add(tfTel);
		
		 lblUserInfo = new JLabel("\"\"님의 회원정보");
		lblUserInfo.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblUserInfo.setBounds(148, 23, 209, 26);
		getContentPane().add(lblUserInfo);
		
		JButton btnDispose = new JButton("뒤로가기");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDispose.setBounds(365, 247, 117, 29);
		getContentPane().add(btnDispose);
		
		View(name, id, email, birth, tel);

	}

	private void View(String name, String id, String email, String birth, String tel) {
		lblUserInfo.setText(name +"님의 회원정보");
		tfId.setText(id);
		tfEmail.setText(email);
		tfBirth.setText(birth);
		tfTel.setText(tel);
	}
}

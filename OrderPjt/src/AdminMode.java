import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.tree.VariableHeightLayoutCache;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMode extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMode dialog = new AdminMode();
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
	public AdminMode() {
		setTitle("카페(관리자 모드)");
		setBounds(100, 100, 505, 129);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Admin님 환영합니다.");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBounds(327, 22, 135, 16);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("메뉴 등록");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertMenu insertMenu = new InsertMenu();
				insertMenu.setModal(true);
				insertMenu.setVisible(true);
			}
		});
		btnNewButton.setBounds(7, 50, 117, 29);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("메뉴 수정");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUpdate menuUpdate = new MenuUpdate();
				menuUpdate.setModal(true);
				menuUpdate.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(131, 50, 117, 29);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("회원관리");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewMember();
			}
		});
		btnNewButton_2.setBounds(255, 50, 117, 29);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("로그아웃");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginOut();
			}
		});
		btnNewButton_3.setBounds(379, 50, 117, 29);
		getContentPane().add(btnNewButton_3);

	}

	protected void ViewMember() {
		ViewMember viewMember = new ViewMember();
		viewMember.setModal(true);
		viewMember.setVisible(true);
	}

	protected void LoginOut() {
		int result = JOptionPane.showConfirmDialog(null, "정말 로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
		
		if (result == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null, "로그아웃 하였습니다.");
			dispose();
			Login login = new Login();
			login.setVisible(true);
			
		}
		
	}
}

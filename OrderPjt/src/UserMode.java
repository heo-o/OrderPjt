import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserMode extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMode dialog = new UserMode("","");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JLabel lblMenuName;
	private JLabel lblMenuImage;

	/**
	 * Create the dialog.
	 * @param userName 
	 */
	public UserMode(String userName,String userId) {
		setTitle("카페 메뉴");
		setBounds(100, 100, 602, 463);
		getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel(userName +" 님 환영합니다.");
		lblName.setBounds(427, 20, 156, 16);
		getContentPane().add(lblName);
		
		JButton btnOrderlist = new JButton("주문내역 보기");
		btnOrderlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderInfo orderInfo = new OrderInfo(userId);
				orderInfo.setModal(true);
				orderInfo.setVisible(true);
			}
		});
		btnOrderlist.setBounds(308, 48, 117, 29);
		getContentPane().add(btnOrderlist);
		
		JButton btnLogout = new JButton("로그아웃");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginOut();
			}
		});
		btnLogout.setBounds(437, 48, 117, 29);
		getContentPane().add(btnLogout);
		
		DisplayMenu(userId);

	}

	private void DisplayMenu(String userId) {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);

			String sql = "Select menuName, menuImage FROM menutbl";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			int x = 62;

			while (rs.next()) {
				String menuName = rs.getString("menuName");
				String menuImage = rs.getString("menuImage");

				lblMenuName = new JLabel(menuName);
				lblMenuName.setBounds(x, 85, 117, 16);
				getContentPane().add(lblMenuName);

				// 메뉴 이미지 표시 (이미지 파일 경로를 menuImage로 가정함)
				lblMenuImage = new JLabel();

				ImageIcon icon = new ImageIcon(menuImage);
				Image image = icon.getImage();
				image = image.getScaledInstance(117, 80, image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				lblMenuImage.setIcon(icon);
				lblMenuImage.setBounds(x, 85 + 20, 117, 80);
				getContentPane().add(lblMenuImage);

				x += 180;

				// 클릭 이벤트 추가
				addClickEvent(menuName,userId);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void addClickEvent(String menuName,String userId) {
		lblMenuName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					MenuOrder menuInfo = new MenuOrder(menuName,userId);
					menuInfo.setModal(true);
					menuInfo.setVisible(true);
				}
			}
		});

		lblMenuImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					MenuOrder menuInfo = new MenuOrder(menuName,userId);
					menuInfo.setModal(true);
					menuInfo.setVisible(true);
				}
			}
		});
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

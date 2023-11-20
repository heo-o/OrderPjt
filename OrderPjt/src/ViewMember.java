import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ViewMember extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfSearch;
	private JTable table;
	private JComboBox cbSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewMember dialog = new ViewMember();
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
	public ViewMember() {
		setTitle("회원관리");
		setBounds(100, 100, 575, 442);
		getContentPane().setLayout(null);
		
		 cbSearch = new JComboBox();
		cbSearch.setModel(new DefaultComboBoxModel(new String[] {"아이디", "이름", "이메일"}));
		cbSearch.setBounds(27, 21, 131, 27);
		getContentPane().add(cbSearch);
		
		tfSearch = new JTextField();
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					Search();
				}
			}
		});
		tfSearch.setBounds(170, 20, 270, 26);
		getContentPane().add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("회원 검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search();
			}
		});
		btnSearch.setBounds(452, 20, 117, 29);
		getContentPane().add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 60, 403, 325);
		getContentPane().add(scrollPane);
		
		String columnNames[] = {"아이디", "이름", "이메일", "생년월일"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		ViewMember();
		
		JButton btnInfo = new JButton("회원 상세정보");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userInfo();
			}
		});
		btnInfo.setBounds(452, 104, 117, 29);
		getContentPane().add(btnInfo);
		
		JButton btnDelete = new JButton("회원 삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberDelete();
			}
		});
		btnDelete.setBounds(452, 145, 117, 29);
		getContentPane().add(btnDelete);
		
		JButton btnDispose = new JButton("뒤로가기");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDispose.setBounds(452, 186, 117, 29);
		getContentPane().add(btnDispose);
		
		JButton btnMemberView = new JButton("회원 전체 조회");
		btnMemberView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewMember();
			}
		});
		btnMemberView.setBounds(452, 63, 117, 29);
		getContentPane().add(btnMemberView);

	}

	protected void userInfo() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			int row = table.getSelectedRow();
			String userChoiceId = table.getValueAt(row, 0).toString();
			
			String sql = "select * from membertbl where userId =?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userChoiceId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String Name = rs.getString("userName");
				String Id = rs.getString("userId");
				String Email = rs.getString("userEmail");
				String Tel = rs.getString("userTel");
				String Birth = rs.getString("userBirth");
				MemberInfo memberInfo = new MemberInfo(Name, Id, Email, Tel, Birth);
				memberInfo.setModal(true);
				memberInfo.setVisible(true);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void memberDelete() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			int row = table.getSelectedRow();
			String userChoiceId= table.getValueAt(row, 0).toString();
			
			String sql = "DELETE FROM membertbl WHERE userId = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userChoiceId);
			
			int rs = pstmt.executeUpdate();
			
			if(rs>0) {
				JOptionPane.showMessageDialog(null, "회원삭제 완료");
				dtm.setRowCount(0);		// 초기화.
				ViewMember();			// 다시 보여주기.
			} else {
				JOptionPane.showMessageDialog(null, "회원삭제 오류!!");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void Search() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			if(cbSearch.getSelectedIndex()==0) {
				String sql = "select userId, userName, userEmail, userBirth from membertbl where userId=?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, tfSearch.getText());
				
				ResultSet rs = pstmt.executeQuery();
				
				DefaultTableModel dtm = (DefaultTableModel)table.getModel();
				dtm.setRowCount(0);	//초기화.
				while(rs.next()) {
					Vector<String> vec = new Vector<String>();
						for(int i = 1; i<=4; i++) {
							vec.add(rs.getString(i));
						}
						dtm.addRow(vec);
				}
				
			} else if (cbSearch.getSelectedIndex()==1) {
				String sql = "select userId, userName, userEmail, userBirth from membertbl where userName=?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, tfSearch.getText());
				
				ResultSet rs = pstmt.executeQuery();
				
				DefaultTableModel dtm = (DefaultTableModel)table.getModel();
				dtm.setRowCount(0);	//초기화.
				while(rs.next()) {
					Vector<String> vec = new Vector<String>();
						for(int i = 1; i<=4; i++) {
							vec.add(rs.getString(i));
						}
						dtm.addRow(vec);
				}
				
			} else {
				String sql = "select userId, userName, userEmail, userBirth from membertbl where userEmail=?";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, tfSearch.getText());
				
				ResultSet rs = pstmt.executeQuery();
				
				
				DefaultTableModel dtm = (DefaultTableModel)table.getModel();
				dtm.setRowCount(0);	//초기화.
				while(rs.next()) {
					Vector<String> vec = new Vector<String>();
						for(int i = 1; i<=4; i++) {
							vec.add(rs.getString(i));
						}
						dtm.addRow(vec);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void ViewMember() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			String sql = "select userId, userName, userEmail, userBirth from membertbl";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			dtm.setRowCount(0);
			while(rs.next()) {
				Vector<String> vec = new Vector<String>();
					for(int i = 1; i<=4; i++) {
						vec.add(rs.getString(i));
					}
					dtm.addRow(vec);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

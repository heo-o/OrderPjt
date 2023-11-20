import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MenuUpdate extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JScrollPane scrollPane = new JScrollPane();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuUpdate dialog = new MenuUpdate();
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
	public MenuUpdate() {
		
		setTitle("메뉴 수정");
		setBounds(100, 100, 517, 391);
		getContentPane().setLayout(null);
		scrollPane.setBounds(19, 23, 349, 315);
		getContentPane().add(scrollPane);
		
		String columnsName[] = {"메뉴이름","메뉴 칼로리", "메뉴 가격"};
		DefaultTableModel dtm = new DefaultTableModel(columnsName,0);
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		JButton btnInfo = new JButton("메뉴 상세정보");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuInfo();
			}
		});
		btnInfo.setBounds(380, 41, 117, 39);
		getContentPane().add(btnInfo);
		
		JButton btnDelete = new JButton("메뉴 삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuDelete();
			}
		});
		btnDelete.setBounds(380, 201, 117, 39);
		getContentPane().add(btnDelete);
		
		JButton btnDispose = new JButton("뒤로가기");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDispose.setBounds(380, 281, 117, 39);
		getContentPane().add(btnDispose);
		
		JButton btnInfo_1 = new JButton("메뉴 수정");
		btnInfo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUpdate();
			}
		});
		btnInfo_1.setBounds(380, 121, 117, 39);
		getContentPane().add(btnInfo_1);
		
		ViewMenu();

	}

	protected void MenuUpdate() {
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		int row = table.getSelectedRow();
		String menuName = table.getValueAt(row, 0).toString();
		ModifyMenu modifyMenu = new ModifyMenu(menuName);
		modifyMenu.setModal(true);
		modifyMenu.setVisible(true);
		
		dtm.setRowCount(0);
		
		ViewMenu();
	}

	protected void MenuInfo() {
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		int row = table.getSelectedRow();
		String menuName= table.getValueAt(row, 0).toString();
		MenuInfo menuInfo = new MenuInfo(menuName);
		menuInfo.setModal(true);
		menuInfo.setVisible(true);
	}

	protected void MenuDelete() {
		String url ="jdbc:mysql://localhost:3306/order";
		String user="root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user,password);
			
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	        int row = table.getSelectedRow();
	        String menuName = table.getValueAt(row, 0).toString();

	        String message = menuName + " 메뉴를 삭제하시겠습니까?";
	        int userChoice = JOptionPane.showConfirmDialog(null, message, "메뉴 삭제", JOptionPane.YES_NO_OPTION);

	        if (userChoice == JOptionPane.YES_OPTION) {
	            String sql = "DELETE FROM menutbl WHERE menuName = ?";
	            PreparedStatement pstmt = con.prepareStatement(sql);
	            pstmt.setString(1, menuName);
	            int rs = pstmt.executeUpdate();

	            if (rs > 0) {
	                JOptionPane.showMessageDialog(null, "삭제되었습니다.");
	                dtm.setRowCount(0); // 초기화.
	                ViewMenu();
	            } 
	        }
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ViewMenu() {
		String url = "jdbc:mysql://localhost:3306/order";
		String user = "root";
		String password = "12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			String sql = "select menuName,menuKcal,menuPrice from menutbl";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			while(rs.next()) {
				
				Vector<String> vec = new Vector<String>();
				for (int i = 1; i<=3; i++) {
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

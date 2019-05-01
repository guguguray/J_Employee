package exam;

import java.awt.EventQueue;

import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Exam_login
{

	private JFrame frame;
	private Connection conn = null;
	private Statement stmt;
	private ResultSet rs;	
	
	private String db_name = "EmployeeDB";
	private String db_table = "user_tbl";
	private String db_pwd = "12345678";  	 // 本機資料庫的 root密碼如有不同，變更對應本機的資料庫連線密碼
	
	private String id, password;
	private boolean is_find, is_check;
	
	private JLabel lblTit, lblAcc, lblPwd, lblMemo;
	private JTextField txtAcc, txtShow;
	private JPasswordField txtPwd;
	private JButton btnOK, btnRST;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Exam_login window = new Exam_login();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public Exam_login()
	{
		CheckDB();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{		
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("人事資料管理系統 ver1.1");
		frame.setBounds(100, 100, 363, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblTit= new JLabel("請輸入帳號及密碼", SwingConstants.CENTER);
		lblTit.setBounds(46,30,260,30);
		frame.getContentPane().add(lblTit);	
	
		lblAcc= new JLabel("帳號: ");
		lblAcc.setBounds(78,80,60,20);
		frame.getContentPane().add(lblAcc);
		
		txtAcc = new JTextField();
		txtAcc.setBounds(124,80,159,20);
		txtAcc.setEditable(true);
		frame.getContentPane().add(txtAcc);
		
		lblPwd= new JLabel("密碼: ");
		lblPwd.setBounds(78,110,60,20);
		frame.getContentPane().add(lblPwd);
		
		txtPwd = new JPasswordField();
		txtPwd.setBounds(124, 110, 159, 20);
		txtPwd.setEchoChar('*');
		frame.getContentPane().add(txtPwd);	
		
		txtShow = new JTextField();
		txtShow.setBounds(78,170,205,20);
		txtShow.setEditable(false);
		frame.getContentPane().add(txtShow);	
		
		btnOK = new JButton("確定");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id = txtAcc.getText();
				password = new String(txtPwd.getPassword());
				
//				System.out.println("input id="+ id);
//				System.out.println("input pwd="+ password);
				
				is_find = IsFindDB(id, password, false);
				
				if (!is_find)
					txtShow.setText("帳號或密碼有錯！");
					
				else
				{			
//					JOptionPane.showMessageDialog(null, "帳號密碼正確!!");
					frame.dispose();
					Exam_employeeList emplist = new Exam_employeeList();
					emplist.setVisible(true);
				}
				
				FileClose();
				
			}
		});
		btnOK.setBounds(104, 210, 60, 30);
		frame.getContentPane().add(btnOK);		
		
		btnRST = new JButton("清除");
		btnRST.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAcc.setText("");
				txtPwd.setText("");
				txtShow.setText("");				
			}
		});
		btnRST.setBounds(197, 210, 60, 30);
		frame.getContentPane().add(btnRST);		
		
		lblMemo= new JLabel("Demo 帳/密: demo / demo ", SwingConstants.CENTER);
		lblMemo.setBounds(78,260,205,30);
		frame.getContentPane().add(lblMemo);
		
		frame.repaint();
	}
	
	// 檢查 DB 連線
	void CheckDB()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "驅動程式安裝失敗！");
		}

		try
		{
				conn = DriverManager.getConnection("jdbc:mysql://localhost/" + db_name + "?user=root&password=" + db_pwd);
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "MySQL無法啟動！");
		}
	}
	boolean IsFindDB(String id, String password, boolean is_find)
	{
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM " + db_table + " WHERE user_name='" + id + "' AND user_pwd='"
						+ password + "'");
			
			while (rs.next())
				is_find = true;
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "檢查是否有相同帳號產生的錯誤！");
		}
		return is_find;
	}
	
	
	void FileClose()
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(null, "關閉資料庫產生的錯誤！");
			}

			rs = null;
		}

		if (stmt != null)
		{
			try
			{
				stmt.close();
			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(null, "關閉資料庫產生的錯誤！");
			}

			stmt = null;
		}
	}	

}

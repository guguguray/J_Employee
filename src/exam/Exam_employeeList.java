package exam;


import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Exam_employeeList extends JFrame
{
	private Connection conn = null;
	private Statement stmt;
	private ResultSet rs;
	
	private String db_name = "EmployeeDB";
	private String db_table = "emp_tbl";
	private String db_pwd = "test";  	 // 本機資料庫的 root密碼如有不同，變更對應本機的資料庫連線密碼
	private String id, password;
	private boolean is_find, is_check;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtSrhName;
	private JTextField txtName;
	private JTextField txtBirth;
	private JTextField txtId;
	private JTextField txtTel;
	private JTextField txtAddr;
	private JTextField txtTitle;
	private JTextField txtExt;
	private JTextField txtOnboard;
	private JTextField txtQuit;
	private JComboBox cmbSex, cmbEdu, cmbDept;
	private ImageIcon photo;
	private String nameVar, genderVar, eduVar, deptVar, titleVar, idVer, org_idVer;
	private String telVer, addrVer, extVer, birthVer, onboardVer, quitVer; 
	private String[] columnNames = {"工號", "姓名", "身分證號", "姓別", "生日", "學歷", "電話", "地址", "部門", "職稱", "分機", "到職日", "離職日", "圖片檔"};
	private JTable table_1;
	private JScrollPane scrollPane;
	private int getEmpid;
	private JCheckBox chkSelectAll;

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
					Exam_employeeList frame = new Exam_employeeList();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Exam_employeeList()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ShowData();
			}
		});
		
		photo = new ImageIcon("..\\EXAM\\src\\exam\\images\\photo.png");
		CheckDB();
		setTitle("人事資料管理系統 ver1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSecondFrame = new JLabel("輸入員工姓名");
		lblSecondFrame.setBounds(10, 20, 87, 15);
		contentPane.add(lblSecondFrame);
		
		table = new JTable();
		table.setBounds(74, 20, 1, 1);
		contentPane.add(table);
		
		txtSrhName = new JTextField();
		txtSrhName.setBounds(95, 17, 96, 21);
		contentPane.add(txtSrhName);
		txtSrhName.setColumns(10);
		
		JButton btnQry = new JButton("查詢");
		btnQry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		btnQry.setBounds(201, 16, 87, 23);
		contentPane.add(btnQry);
		
		JButton btnLoadAll = new JButton("全部列表");
		btnLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				ShowData();
				
				
			}
		});
		btnLoadAll.setBounds(507, 16, 87, 23);
		contentPane.add(btnLoadAll);
		
		JLabel lblName = new JLabel("姓名*");
		lblName.setBounds(194, 180, 33, 15);
		contentPane.add(lblName);
		
		JLabel lblSex = new JLabel("性別*");
		lblSex.setBounds(195, 205, 33, 15);
		contentPane.add(lblSex);
		
		JLabel lblBirth = new JLabel("生日*");
		lblBirth.setBounds(195, 261, 33, 15);
		contentPane.add(lblBirth);
		
		JLabel lblEdu = new JLabel("學歷*");
		lblEdu.setBounds(195, 233, 33, 15);
		contentPane.add(lblEdu);
		
		JLabel lblId = new JLabel("身分證號*");
		lblId.setBounds(171, 291, 58, 15);
		contentPane.add(lblId);
		
		JLabel lblTel = new JLabel("電話*");
		lblTel.setBounds(195, 316, 33, 15);
		contentPane.add(lblTel);
		
		JLabel lblAddr = new JLabel("地址*");
		lblAddr.setBounds(195, 341, 33, 15);
		contentPane.add(lblAddr);
		
		JLabel lblDept = new JLabel("部門名*");
		lblDept.setBounds(379, 180, 46, 15);
		contentPane.add(lblDept);
		
		JLabel lblTitle = new JLabel("職稱*");
		lblTitle.setBounds(391, 209, 33, 15);
		contentPane.add(lblTitle);
		
		JLabel lblExt = new JLabel("分機*");
		lblExt.setBounds(391, 236, 33, 15);
		contentPane.add(lblExt);
		
		JLabel lblOnboard = new JLabel("到職日*");
		lblOnboard.setBounds(380, 261, 46, 15);
		contentPane.add(lblOnboard);
		
		JLabel lblQuit = new JLabel("離職日");
		lblQuit.setBounds(381, 288, 46, 15);
		contentPane.add(lblQuit);
		
		txtName = new JTextField();
		txtName.setBounds(230, 177, 96, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtBirth = new JTextField();
		txtBirth.setToolTipText("西元格式: YYYY/MM/DD");
		txtBirth.setBounds(230, 258, 96, 21);
		contentPane.add(txtBirth);
		txtBirth.setColumns(10);
		
		cmbSex = new JComboBox();
		cmbSex.setBounds(230, 202, 76, 21);
		contentPane.add(cmbSex);
		cmbSex.addItem("..請選擇..");
		cmbSex.addItem("男");
		cmbSex.addItem("女");
		
		txtId = new JTextField();
		txtId.setBounds(230, 285, 96, 21);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtTel = new JTextField();
		txtTel.setToolTipText("09xx-xxxxxx");
		txtTel.setBounds(230, 313, 96, 21);
		contentPane.add(txtTel);
		txtTel.setColumns(10);
		
		txtAddr = new JTextField();
		txtAddr.setBounds(230, 341, 292, 21);
		contentPane.add(txtAddr);
		txtAddr.setColumns(10);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(426, 205, 96, 21);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);
		
		cmbDept = new JComboBox();
		cmbDept.setBounds(426, 177, 121, 21);
		contentPane.add(cmbDept);
		cmbDept.addItem("..請選擇..");
		cmbDept.addItem("管理部");
		cmbDept.addItem("行銷業務部");
		cmbDept.addItem("財務部");
		cmbDept.addItem("採購部");
		cmbDept.addItem("軟體開發部");
		cmbDept.addItem("軟體測試部");
		cmbDept.addItem("法務部");		
		
		cmbEdu = new JComboBox();
		cmbEdu.setBounds(230, 230, 76, 21);
		contentPane.add(cmbEdu);
		cmbEdu.addItem("..請選擇..");
		cmbEdu.addItem("博士");
		cmbEdu.addItem("碩士");
		cmbEdu.addItem("大學");
		cmbEdu.addItem("大專");
		cmbEdu.addItem("高中");
		cmbEdu.addItem("國中");
		
		txtExt = new JTextField();
		txtExt.setBounds(426, 231, 96, 21);
		contentPane.add(txtExt);
		txtExt.setColumns(10);
		
		txtOnboard = new JTextField();
		txtOnboard.setToolTipText("西元格式: YYYY/MM/DD");
		txtOnboard.setBounds(426, 258, 96, 21);
		contentPane.add(txtOnboard);
		txtOnboard.setColumns(10);
		
		txtQuit = new JTextField();
		txtQuit.setToolTipText("西元格式: YYYY/MM/DD");
		txtQuit.setBounds(426, 286, 96, 21);
		contentPane.add(txtQuit);
		txtQuit.setColumns(10);
		
		// 新增紀錄
		JButton btnAdd = new JButton("新增");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 新資料檢查
				NewData();				
				
			}
		});
		btnAdd.setBounds(201, 394, 87, 23);
		contentPane.add(btnAdd);
		
		JButton btnUpt = new JButton("更新");
		btnUpt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("取得 Emp_id:"+ getEmpid);
				UpData(getEmpid);
				
			}
		});
		btnUpt.setBounds(298, 394, 87, 23);
		contentPane.add(btnUpt);
		
		JButton btnDel = new JButton("刪除");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("取得 Emp_id:"+ getEmpid);
				DeleteDB(getEmpid);				
				ShowData();
			}
		});
		btnDel.setBounds(393, 394, 87, 23);
		contentPane.add(btnDel);
		
		JButton btnAbout = new JButton("關於");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "湜億 1071228程式設計師班\n\n"
						+ "專案: 人事資料管理系統 Version.1.0 \n\n"
						+ "Author by (GROUP.2): \n周正庭(2)、吳威德(4)、曹晉睿(5)、方奕云(10)、蔡昀倢(17)  \n\nDate: 2019.03");				
			}
		});
		btnAbout.setBounds(490, 429, 87, 23);
		contentPane.add(btnAbout);
		
		JLabel lblHead = new JLabel(photo);
		lblHead.setBounds(10, 180, 130, 130);
		contentPane.add(lblHead);
		
		JButton btnClear = new JButton("清除");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getEmpid = 0;
				txtName.setText("");
				txtBirth.setText("");
				txtId.setText("");
				txtTel.setText("");
				txtAddr.setText("");
				txtTitle.setText("");
				txtExt.setText("");
				cmbSex.setSelectedIndex(0);
				cmbDept.setSelectedIndex(0);
				cmbEdu.setSelectedIndex(0);
				txtOnboard.setText("");
				txtQuit.setText("");
				
			}
		});
		btnClear.setBounds(490, 394, 87, 23);
		contentPane.add(btnClear);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 584, 122);
		contentPane.add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		
		chkSelectAll = new JCheckBox("隱藏顯示已刪除的記錄");
		chkSelectAll.setSelected(true);
		chkSelectAll.setForeground(Color.gray);
		chkSelectAll.setBounds(335, 16, 166, 23);
		contentPane.add(chkSelectAll);
		
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
			ErrDBMsg("驅動程式安裝失敗！", e);
		}

		try
		{
				conn = DriverManager.getConnection("jdbc:mysql://localhost/" + db_name + "?user=root&password=" + db_pwd);
		}
		catch (SQLException e)
		{
			ErrDBMsg("MySQL無法啟動！", e);
		}
	}
	
	// 顯示所有資料
	void ShowData() 
	{	
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		table_1.setModel(model);
		table_1.setModel(model); 
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(30);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(30);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(35);
		table_1.getColumnModel().getColumn(10).setPreferredWidth(35);
		table_1.setFillsViewportHeight(true);
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		int emp_id;
		String emp_name= "";
		String emp_sex = "";
		String emp_title = "";
		String edu = "";
		String photo = "";
		String social_no = "";
		String telno = "";
		String address = "";
		String ext_no = "";
		Date birthday, onboard, quit_date;
		String dept_name = "";
		
		try
		{						
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM emp_tbl");			
			
			int i =0;
			while (rs.next())
			{
			emp_id = rs.getInt("emp_id");
			emp_name = rs.getString("emp_name");
			social_no = rs.getString("social_no");
			emp_sex = rs.getString("emp_sex");
			birthday = rs.getDate("birthday");
			edu = rs.getString("edu");
			telno = rs.getString("telno");
			address = rs.getString("address");
			dept_name = rs.getString("dept_name");
			emp_title = rs.getString("emp_title"); 
			ext_no = rs.getString("ext_no");
			onboard = rs.getDate("onboard");
			quit_date = rs.getDate("quit_date");
			photo = rs.getString("photo");
			
			model.addRow(new Object[]{emp_id, emp_name, social_no, emp_sex, birthday, edu, telno, address, dept_name, emp_title, ext_no, onboard, quit_date, photo});
			i++; 
			}
			
			if(i <1)
			{
			JOptionPane.showMessageDialog(null, "資料庫無任何資料");
			}
			
			table_1.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked (MouseEvent e) {
					int i  = table_1.getSelectedRow();
					getEmpid = Integer.parseInt(model.getValueAt(i, 0).toString());
					
					txtName.setText(model.getValueAt(i, 1).toString());
					txtBirth.setText(model.getValueAt(i, 4).toString().replaceAll("-", "/"));
					txtId.setText(model.getValueAt(i, 2).toString());
					org_idVer = model.getValueAt(i, 2).toString();
					txtTel.setText(model.getValueAt(i, 6).toString());
					txtAddr.setText(model.getValueAt(i, 7).toString());
					txtTitle.setText(model.getValueAt(i, 9).toString());
					txtExt.setText(model.getValueAt(i, 10).toString());
					txtOnboard.setText(model.getValueAt(i, 11).toString().replaceAll("-", "/"));
					if (model.getValueAt(i, 12) == null)
						txtQuit.setText("");
					else
						txtQuit.setText(model.getValueAt(i, 12).toString().replaceAll("-", "/"));
					
					// ComboBox	性別
					switch (model.getValueAt(i, 3).toString()) 
					{
					case "男":
						cmbSex.setSelectedIndex(1);
						break;
					case "女":
						cmbSex.setSelectedIndex(2);
						break;						
					}
					
					// ComboBox	學歷
					switch (model.getValueAt(i, 5).toString()) 
					{
					case "博士":
						cmbEdu.setSelectedIndex(1);
						break;
					case "碩士":
						cmbEdu.setSelectedIndex(2);
						break;
					case "大學":
						cmbEdu.setSelectedIndex(3);
						break;	
					case "大專":
						cmbEdu.setSelectedIndex(4);
						break;	
					case "高中":
						cmbEdu.setSelectedIndex(5);
						break;
					case "國中":
						cmbEdu.setSelectedIndex(6);
						break;
					}

					// ComboBox	部門
					switch (model.getValueAt(i, 8).toString()) 
					{
					case "管理部":
						cmbDept.setSelectedIndex(1);
						break;
					case "行銷業務部":
						cmbDept.setSelectedIndex(2);
						break;
					case "財務部":
						cmbDept.setSelectedIndex(3);
						break;	
					case "採購部":
						cmbDept.setSelectedIndex(4);
						break;	
					case "軟體開發部":
						cmbDept.setSelectedIndex(5);
						break;
					case "軟體測試部":
						cmbDept.setSelectedIndex(6);
						break;
					case "法務部":
						cmbDept.setSelectedIndex(7);
						break;
					}				
				
				}				
			});

		}
		catch (SQLException e)
		{
//			ErrDBMsg("5、檢查是否有相同帳號產生的錯誤！", e);
		}		
		finally
		{
			FileClose();
		}
	}
	// 新資料欄位檢查
	void NewData()
	{
		is_check = true;		
		nameVar = txtName.getText().trim();
		genderVar = (String)cmbSex.getSelectedItem();
		eduVar = (String)cmbEdu.getSelectedItem();
		deptVar = (String)cmbDept.getSelectedItem();
		titleVar = txtTitle.getText().trim();
		idVer = txtId.getText().trim();
		telVer = txtTel.getText().trim();
		addrVer = txtAddr.getText().trim();
		extVer = txtExt.getText().trim();
		birthVer = txtBirth.getText().trim();
		onboardVer = txtOnboard.getText().trim();
		quitVer = txtQuit.getText().trim(); 
		
		if (quitVer.equals(""))
			quitVer = "NULL";
		else {
			quitVer = "'" + quitVer + "'";
			System.out.println(quitVer);
		}		
		
		if (genderVar.equals("..請選擇..") || eduVar.equals("..請選擇..") || deptVar.equals("..請選擇..") ) {
			ErrDataMsg("性別、學歷、部門名 必須選擇！");
		}
		
		if (nameVar.equals("") || titleVar.equals("") || idVer.equals("") || telVer.equals("") || addrVer.equals("") || extVer.equals("") || birthVer.equals("") || onboardVer.equals("")) {
			ErrDataMsg("必填欄位不允許空白！");
			is_check = false;
		}
		

		if (nameVar.length() > 20 || titleVar.length() > 20)
		{
			ErrDataMsg("姓名、職稱最多20個字！");
			is_check = false;
		}
		
		if (idVer.length() > 10)
		{
			ErrDataMsg("身分證號最多10個字！");
			is_check = false;
		}
		if (telVer.length() > 15)
		{
			ErrDataMsg("電話號碼最多15個字！");
			is_check = false;
		}
		if (addrVer.length() > 50)
		{
			ErrDataMsg("地址最多50個字！");
			is_check = false;
		}
		if (extVer.length() > 4)
		{
			ErrDataMsg("分機最多4個字！");
			is_check = false;
		}
		if (!isValidDate(birthVer) || !isValidDate(onboardVer))
		{
			ErrDataMsg("生日或到職日、格式錯誤！ 正確格式為 YYYY/MM/DD");
			is_check = false;
		}
		
		// 檢查 DB 是否有身分證號重複資料
		org_idVer ="";
		is_find = IsFindDB(org_idVer, idVer, false);
		System.out.println("新資料檢查: org_idVer = "+ org_idVer);
		
		if (is_find)
		{
			ErrDataMsg("身分證號重複！無法新增！");
			is_check = false;
		}				

		if (is_check == true)
		{
			InsertDB(nameVar, genderVar, eduVar, deptVar, titleVar, idVer, 
					telVer, addrVer, extVer, birthVer, onboardVer, quitVer);
			ShowData();
		}
	}
	
	boolean isValidDate(String str) {
        boolean convertSuccess = true;
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {            
            
            convertSuccess = false;
        }
        return convertSuccess;
    }
	
	void UpData(int empid)
	{
		is_check = true;		
		nameVar = txtName.getText().trim();
		genderVar = (String)cmbSex.getSelectedItem();
		eduVar = (String)cmbEdu.getSelectedItem();
		deptVar = (String)cmbDept.getSelectedItem();
		titleVar = txtTitle.getText().trim();
		idVer = txtId.getText().trim();
		telVer = txtTel.getText().trim();
		addrVer = txtAddr.getText().trim();
		extVer = txtExt.getText().trim();
		birthVer = txtBirth.getText().trim();
		onboardVer = txtOnboard.getText().trim();
		quitVer = txtQuit.getText().trim(); 
		
		
		if (empid == 0) {
			ErrDataMsg("請先從員工資料列表中選擇欲編輯的資料！");
		}
		else 
		{
			if (quitVer.equals(""))
				quitVer = "NULL";
			else {
				quitVer = "'" + quitVer + "'";
				System.out.println(quitVer);
			}		
			
			if (genderVar.equals("..請選擇..") || eduVar.equals("..請選擇..") || deptVar.equals("..請選擇..") ) {
				ErrDataMsg("性別、學歷、部門名 必須選擇！");
			}
			
			if (nameVar.equals("") || titleVar.equals("") || idVer.equals("") || telVer.equals("") || addrVer.equals("") || extVer.equals("") || birthVer.equals("") || onboardVer.equals("")) {
				ErrDataMsg("必填欄位不允許空白！");
				is_check = false;
			}
			
		
			if (nameVar.length() > 20 || titleVar.length() > 20)
			{
				ErrDataMsg("姓名、職稱最多20個字！");
				is_check = false;
			}
			
			if (idVer.length() > 10)
			{
				ErrDataMsg("身分證號最多10個字！");
				is_check = false;
			}
			if (telVer.length() > 15)
			{
				ErrDataMsg("電話號碼最多15個字！");
				is_check = false;
			}
			if (addrVer.length() > 50)
			{
				ErrDataMsg("地址最多50個字！");
				is_check = false;
			}
			if (extVer.length() > 4)
			{
				ErrDataMsg("分機最多4個字！");
				is_check = false;
			}
			if (!isValidDate(birthVer) || !isValidDate(onboardVer))
			{
				ErrDataMsg("生日或到職日、格式錯誤！ 正確格式為 YYYY/MM/DD");
				is_check = false;
			}
			
			// 檢查 DB 是否有身分證號重複資料
			System.out.println("更新資料檢查: org_idVer= "+ org_idVer);
			is_find = IsFindDB(org_idVer, idVer, false);
		
			if (is_find)
			{
				ErrDataMsg("身分證號重複！無法新增！");
				is_check = false;
			}				
		
			if (is_check == true)
			{
				UpDateDB(getEmpid, nameVar, genderVar, eduVar, deptVar, titleVar, idVer, 
						telVer, addrVer, extVer, birthVer, onboardVer, quitVer);
				ShowData();
			}
		}
				
	}
	
	void InsertDB(String name, String gender, String edu, String dept, String title, String social, 
			String tel, String addr, String ext, String birth, String onboard, String quitd)
	{
		try
		{
			// INSERT DATABASE
			String sql = "INSERT INTO `emp_tbl` (" 
					+ "`emp_id`, `emp_name`, `emp_sex`, `emp_title`, `edu`, `photo`, `social_no`, " 
					+ "`telno`, `address`, `ext_no`, `birthday`, `onboard`, `quit_date`, " 
					+ "`dept_name`, `create_date`, `last_modified`, `quit_mark`) VALUES (" 
					+ "NULL, '" + name + "', '" + gender + "', '" + title + "', " 
					+ "'" + edu + "', NULL, '"+ social +"', '"+ tel + "', " 
					+ "'" + addr + "', '"+ ext +"', '"+ birth +"', '"+ onboard + "', "+ quitd +", " 
					+ "'" + dept + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0')";					
			
			stmt = conn.createStatement();
			// quit_mark don't need to show
			stmt.execute(sql);
			JOptionPane.showMessageDialog(null, "資料新增成功！");
		}
		catch (SQLException e)
		{
			ErrDBMsg("6、插入一個會員資料產生的錯誤！", e);
		}
		finally
		{
			FileClose();
		}
	}
	
	boolean IsFindDB(String orgSocial, String socialNo, boolean is_find)
	{
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT social_no FROM " + db_table + " WHERE social_no ='" + socialNo + "'");

			while (rs.next()) {
				System.out.println(rs.getString("social_no"));
				if (orgSocial.equals(rs.getString("social_no")))
					is_find = false;
				else
					is_find = true;
			}
				
		}
		catch (SQLException e)
		{
			ErrDBMsg("5、請檢查是否有相同身分證號產生的錯誤！", e);
		}
		finally
		{
			FileClose();
		}
		return is_find;
	}
	
	void ErrDBMsg(String msg, Exception e)
	{
		JOptionPane.showMessageDialog(null, msg + "\n訊息：" + e, "錯誤訊息", JOptionPane.ERROR_MESSAGE);

		System.exit(0);
	}

	void ErrDataMsg(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "錯誤訊息", JOptionPane.ERROR_MESSAGE);
	}
	
	void UpDateDB(int empid, String name, String gender, String edu, String dept, String title, String social, 
			String tel, String addr, String ext, String birth, String onboard, String quitd)
	{
		try
		{
			System.out.println("quit date" + quitd);
			stmt = conn.createStatement();
			String sqlUpd = "UPDATE emp_tbl SET emp_name='" + name + "', emp_sex='" + gender 
					+ "', emp_title='" + title + "', edu='" + edu + "', photo=NULL, social_no='" + social 
					+ "', telno='" + tel + "', address='" + addr + "', ext_no='" + ext 
					+ "', birthday='" + birth + "', onboard= '" + onboard + "', quit_date=" + quitd 
					+ ", dept_name='" + dept + "' WHERE emp_id=" + empid;
			System.out.println(sqlUpd);
			
//			stmt.executeUpdate(sqlUpd);
		}
		catch (SQLException e)
		{
			ErrDBMsg("7、更新會員資料產生錯誤！", e);
		}
		finally
		{
			FileClose();			
		}
	}
	
	void DeleteDB(int empid)
	{
		try
		{
			if (empid == 0) {
				ErrDataMsg("請先從員工資料列表中選擇欲編輯的資料！");
			}
			else 
			{
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE emp_tbl SET quit_mark = '1' WHERE emp_id = "+ empid);
				JOptionPane.showMessageDialog(null, "資料刪除成功！");
			}
			
		}
		catch (SQLException e)
		{
			ErrDBMsg("8、刪除一個會員資料產生的錯誤！", e);
		}
		finally
		{
			FileClose();
		}
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

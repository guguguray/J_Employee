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
	private String db_pwd = "test";  	 // ������Ʈw�� root�K�X�p�����P�A�ܧ������������Ʈw�s�u�K�X
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
	private String[] columnNames = {"�u��", "�m�W", "�����Ҹ�", "�m�O", "�ͤ�", "�Ǿ�", "�q��", "�a�}", "����", "¾��", "����", "��¾��", "��¾��", "�Ϥ���"};
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
		setTitle("�H�Ƹ�ƺ޲z�t�� ver1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSecondFrame = new JLabel("��J���u�m�W");
		lblSecondFrame.setBounds(10, 20, 87, 15);
		contentPane.add(lblSecondFrame);
		
		table = new JTable();
		table.setBounds(74, 20, 1, 1);
		contentPane.add(table);
		
		txtSrhName = new JTextField();
		txtSrhName.setBounds(95, 17, 96, 21);
		contentPane.add(txtSrhName);
		txtSrhName.setColumns(10);
		
		JButton btnQry = new JButton("�d��");
		btnQry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		btnQry.setBounds(201, 16, 87, 23);
		contentPane.add(btnQry);
		
		JButton btnLoadAll = new JButton("�����C��");
		btnLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				ShowData();
				
				
			}
		});
		btnLoadAll.setBounds(507, 16, 87, 23);
		contentPane.add(btnLoadAll);
		
		JLabel lblName = new JLabel("�m�W*");
		lblName.setBounds(194, 180, 33, 15);
		contentPane.add(lblName);
		
		JLabel lblSex = new JLabel("�ʧO*");
		lblSex.setBounds(195, 205, 33, 15);
		contentPane.add(lblSex);
		
		JLabel lblBirth = new JLabel("�ͤ�*");
		lblBirth.setBounds(195, 261, 33, 15);
		contentPane.add(lblBirth);
		
		JLabel lblEdu = new JLabel("�Ǿ�*");
		lblEdu.setBounds(195, 233, 33, 15);
		contentPane.add(lblEdu);
		
		JLabel lblId = new JLabel("�����Ҹ�*");
		lblId.setBounds(171, 291, 58, 15);
		contentPane.add(lblId);
		
		JLabel lblTel = new JLabel("�q��*");
		lblTel.setBounds(195, 316, 33, 15);
		contentPane.add(lblTel);
		
		JLabel lblAddr = new JLabel("�a�}*");
		lblAddr.setBounds(195, 341, 33, 15);
		contentPane.add(lblAddr);
		
		JLabel lblDept = new JLabel("�����W*");
		lblDept.setBounds(379, 180, 46, 15);
		contentPane.add(lblDept);
		
		JLabel lblTitle = new JLabel("¾��*");
		lblTitle.setBounds(391, 209, 33, 15);
		contentPane.add(lblTitle);
		
		JLabel lblExt = new JLabel("����*");
		lblExt.setBounds(391, 236, 33, 15);
		contentPane.add(lblExt);
		
		JLabel lblOnboard = new JLabel("��¾��*");
		lblOnboard.setBounds(380, 261, 46, 15);
		contentPane.add(lblOnboard);
		
		JLabel lblQuit = new JLabel("��¾��");
		lblQuit.setBounds(381, 288, 46, 15);
		contentPane.add(lblQuit);
		
		txtName = new JTextField();
		txtName.setBounds(230, 177, 96, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtBirth = new JTextField();
		txtBirth.setToolTipText("�褸�榡: YYYY/MM/DD");
		txtBirth.setBounds(230, 258, 96, 21);
		contentPane.add(txtBirth);
		txtBirth.setColumns(10);
		
		cmbSex = new JComboBox();
		cmbSex.setBounds(230, 202, 76, 21);
		contentPane.add(cmbSex);
		cmbSex.addItem("..�п��..");
		cmbSex.addItem("�k");
		cmbSex.addItem("�k");
		
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
		cmbDept.addItem("..�п��..");
		cmbDept.addItem("�޲z��");
		cmbDept.addItem("��P�~�ȳ�");
		cmbDept.addItem("�]�ȳ�");
		cmbDept.addItem("���ʳ�");
		cmbDept.addItem("�n��}�o��");
		cmbDept.addItem("�n����ճ�");
		cmbDept.addItem("�k�ȳ�");		
		
		cmbEdu = new JComboBox();
		cmbEdu.setBounds(230, 230, 76, 21);
		contentPane.add(cmbEdu);
		cmbEdu.addItem("..�п��..");
		cmbEdu.addItem("�դh");
		cmbEdu.addItem("�Ӥh");
		cmbEdu.addItem("�j��");
		cmbEdu.addItem("�j�M");
		cmbEdu.addItem("����");
		cmbEdu.addItem("�ꤤ");
		
		txtExt = new JTextField();
		txtExt.setBounds(426, 231, 96, 21);
		contentPane.add(txtExt);
		txtExt.setColumns(10);
		
		txtOnboard = new JTextField();
		txtOnboard.setToolTipText("�褸�榡: YYYY/MM/DD");
		txtOnboard.setBounds(426, 258, 96, 21);
		contentPane.add(txtOnboard);
		txtOnboard.setColumns(10);
		
		txtQuit = new JTextField();
		txtQuit.setToolTipText("�褸�榡: YYYY/MM/DD");
		txtQuit.setBounds(426, 286, 96, 21);
		contentPane.add(txtQuit);
		txtQuit.setColumns(10);
		
		// �s�W����
		JButton btnAdd = new JButton("�s�W");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �s����ˬd
				NewData();				
				
			}
		});
		btnAdd.setBounds(201, 394, 87, 23);
		contentPane.add(btnAdd);
		
		JButton btnUpt = new JButton("��s");
		btnUpt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("���o Emp_id:"+ getEmpid);
				UpData(getEmpid);
				
			}
		});
		btnUpt.setBounds(298, 394, 87, 23);
		contentPane.add(btnUpt);
		
		JButton btnDel = new JButton("�R��");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("���o Emp_id:"+ getEmpid);
				DeleteDB(getEmpid);				
				ShowData();
			}
		});
		btnDel.setBounds(393, 394, 87, 23);
		contentPane.add(btnDel);
		
		JButton btnAbout = new JButton("����");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "�A�� 1071228�{���]�p�v�Z\n\n"
						+ "�M��: �H�Ƹ�ƺ޲z�t�� Version.1.0 \n\n"
						+ "Author by (GROUP.2): \n�P���x(2)�B�d�¼w(4)�B��ʺ�(5)�B�諳��(10)�B������(17)  \n\nDate: 2019.03");				
			}
		});
		btnAbout.setBounds(490, 429, 87, 23);
		contentPane.add(btnAbout);
		
		JLabel lblHead = new JLabel(photo);
		lblHead.setBounds(10, 180, 130, 130);
		contentPane.add(lblHead);
		
		JButton btnClear = new JButton("�M��");
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
		
		chkSelectAll = new JCheckBox("������ܤw�R�����O��");
		chkSelectAll.setSelected(true);
		chkSelectAll.setForeground(Color.gray);
		chkSelectAll.setBounds(335, 16, 166, 23);
		contentPane.add(chkSelectAll);
		
	}
	// �ˬd DB �s�u
	void CheckDB()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception e)
		{
			ErrDBMsg("�X�ʵ{���w�˥��ѡI", e);
		}

		try
		{
				conn = DriverManager.getConnection("jdbc:mysql://localhost/" + db_name + "?user=root&password=" + db_pwd);
		}
		catch (SQLException e)
		{
			ErrDBMsg("MySQL�L�k�ҰʡI", e);
		}
	}
	
	// ��ܩҦ����
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
			JOptionPane.showMessageDialog(null, "��Ʈw�L������");
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
					
					// ComboBox	�ʧO
					switch (model.getValueAt(i, 3).toString()) 
					{
					case "�k":
						cmbSex.setSelectedIndex(1);
						break;
					case "�k":
						cmbSex.setSelectedIndex(2);
						break;						
					}
					
					// ComboBox	�Ǿ�
					switch (model.getValueAt(i, 5).toString()) 
					{
					case "�դh":
						cmbEdu.setSelectedIndex(1);
						break;
					case "�Ӥh":
						cmbEdu.setSelectedIndex(2);
						break;
					case "�j��":
						cmbEdu.setSelectedIndex(3);
						break;	
					case "�j�M":
						cmbEdu.setSelectedIndex(4);
						break;	
					case "����":
						cmbEdu.setSelectedIndex(5);
						break;
					case "�ꤤ":
						cmbEdu.setSelectedIndex(6);
						break;
					}

					// ComboBox	����
					switch (model.getValueAt(i, 8).toString()) 
					{
					case "�޲z��":
						cmbDept.setSelectedIndex(1);
						break;
					case "��P�~�ȳ�":
						cmbDept.setSelectedIndex(2);
						break;
					case "�]�ȳ�":
						cmbDept.setSelectedIndex(3);
						break;	
					case "���ʳ�":
						cmbDept.setSelectedIndex(4);
						break;	
					case "�n��}�o��":
						cmbDept.setSelectedIndex(5);
						break;
					case "�n����ճ�":
						cmbDept.setSelectedIndex(6);
						break;
					case "�k�ȳ�":
						cmbDept.setSelectedIndex(7);
						break;
					}				
				
				}				
			});

		}
		catch (SQLException e)
		{
//			ErrDBMsg("5�B�ˬd�O�_���ۦP�b�����ͪ����~�I", e);
		}		
		finally
		{
			FileClose();
		}
	}
	// �s�������ˬd
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
		
		if (genderVar.equals("..�п��..") || eduVar.equals("..�п��..") || deptVar.equals("..�п��..") ) {
			ErrDataMsg("�ʧO�B�Ǿ��B�����W ������ܡI");
		}
		
		if (nameVar.equals("") || titleVar.equals("") || idVer.equals("") || telVer.equals("") || addrVer.equals("") || extVer.equals("") || birthVer.equals("") || onboardVer.equals("")) {
			ErrDataMsg("������줣���\�ťաI");
			is_check = false;
		}
		

		if (nameVar.length() > 20 || titleVar.length() > 20)
		{
			ErrDataMsg("�m�W�B¾�ٳ̦h20�Ӧr�I");
			is_check = false;
		}
		
		if (idVer.length() > 10)
		{
			ErrDataMsg("�����Ҹ��̦h10�Ӧr�I");
			is_check = false;
		}
		if (telVer.length() > 15)
		{
			ErrDataMsg("�q�ܸ��X�̦h15�Ӧr�I");
			is_check = false;
		}
		if (addrVer.length() > 50)
		{
			ErrDataMsg("�a�}�̦h50�Ӧr�I");
			is_check = false;
		}
		if (extVer.length() > 4)
		{
			ErrDataMsg("�����̦h4�Ӧr�I");
			is_check = false;
		}
		if (!isValidDate(birthVer) || !isValidDate(onboardVer))
		{
			ErrDataMsg("�ͤ�Ψ�¾��B�榡���~�I ���T�榡�� YYYY/MM/DD");
			is_check = false;
		}
		
		// �ˬd DB �O�_�������Ҹ����Ƹ��
		org_idVer ="";
		is_find = IsFindDB(org_idVer, idVer, false);
		System.out.println("�s����ˬd: org_idVer = "+ org_idVer);
		
		if (is_find)
		{
			ErrDataMsg("�����Ҹ����ơI�L�k�s�W�I");
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
			ErrDataMsg("�Х��q���u��ƦC����ܱ��s�誺��ơI");
		}
		else 
		{
			if (quitVer.equals(""))
				quitVer = "NULL";
			else {
				quitVer = "'" + quitVer + "'";
				System.out.println(quitVer);
			}		
			
			if (genderVar.equals("..�п��..") || eduVar.equals("..�п��..") || deptVar.equals("..�п��..") ) {
				ErrDataMsg("�ʧO�B�Ǿ��B�����W ������ܡI");
			}
			
			if (nameVar.equals("") || titleVar.equals("") || idVer.equals("") || telVer.equals("") || addrVer.equals("") || extVer.equals("") || birthVer.equals("") || onboardVer.equals("")) {
				ErrDataMsg("������줣���\�ťաI");
				is_check = false;
			}
			
		
			if (nameVar.length() > 20 || titleVar.length() > 20)
			{
				ErrDataMsg("�m�W�B¾�ٳ̦h20�Ӧr�I");
				is_check = false;
			}
			
			if (idVer.length() > 10)
			{
				ErrDataMsg("�����Ҹ��̦h10�Ӧr�I");
				is_check = false;
			}
			if (telVer.length() > 15)
			{
				ErrDataMsg("�q�ܸ��X�̦h15�Ӧr�I");
				is_check = false;
			}
			if (addrVer.length() > 50)
			{
				ErrDataMsg("�a�}�̦h50�Ӧr�I");
				is_check = false;
			}
			if (extVer.length() > 4)
			{
				ErrDataMsg("�����̦h4�Ӧr�I");
				is_check = false;
			}
			if (!isValidDate(birthVer) || !isValidDate(onboardVer))
			{
				ErrDataMsg("�ͤ�Ψ�¾��B�榡���~�I ���T�榡�� YYYY/MM/DD");
				is_check = false;
			}
			
			// �ˬd DB �O�_�������Ҹ����Ƹ��
			System.out.println("��s����ˬd: org_idVer= "+ org_idVer);
			is_find = IsFindDB(org_idVer, idVer, false);
		
			if (is_find)
			{
				ErrDataMsg("�����Ҹ����ơI�L�k�s�W�I");
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
			JOptionPane.showMessageDialog(null, "��Ʒs�W���\�I");
		}
		catch (SQLException e)
		{
			ErrDBMsg("6�B���J�@�ӷ|����Ʋ��ͪ����~�I", e);
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
			ErrDBMsg("5�B���ˬd�O�_���ۦP�����Ҹ����ͪ����~�I", e);
		}
		finally
		{
			FileClose();
		}
		return is_find;
	}
	
	void ErrDBMsg(String msg, Exception e)
	{
		JOptionPane.showMessageDialog(null, msg + "\n�T���G" + e, "���~�T��", JOptionPane.ERROR_MESSAGE);

		System.exit(0);
	}

	void ErrDataMsg(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "���~�T��", JOptionPane.ERROR_MESSAGE);
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
			ErrDBMsg("7�B��s�|����Ʋ��Ϳ��~�I", e);
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
				ErrDataMsg("�Х��q���u��ƦC����ܱ��s�誺��ơI");
			}
			else 
			{
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE emp_tbl SET quit_mark = '1' WHERE emp_id = "+ empid);
				JOptionPane.showMessageDialog(null, "��ƧR�����\�I");
			}
			
		}
		catch (SQLException e)
		{
			ErrDBMsg("8�B�R���@�ӷ|����Ʋ��ͪ����~�I", e);
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
				JOptionPane.showMessageDialog(null, "������Ʈw���ͪ����~�I");
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
				JOptionPane.showMessageDialog(null, "������Ʈw���ͪ����~�I");
			}

			stmt = null;
		}
	}
}

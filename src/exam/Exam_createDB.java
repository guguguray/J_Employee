/* �A�� 1071228�{���]�p�v�Z
 * 
 * �M��: �H�Ƹ�ƺ޲z�t�� Version.1.0
 * 
 * Program: ��Ʈw�إߤΪ�l�Ƹ��
 * 
 * ���{������e����: ***�ȥ����ܧ����������Ʈw�� root�K�X 
 * 
 * author by (Group.2)�d�¼w�B�P���x�B�諳���B�����̡B��ʺ�
 * date: 2019.03
 */

package exam;

import java.sql.*;
import javax.swing.*;

class CCreate_DB_Tabel
{
	private Connection conn;
	private Statement stmt;
	private String db_name = "EmployeeDB";
	private String emp_table = "emp_tbl";
//	private String dept_table = "dept_tbl";
	private String user_table = "user_tbl";
	private String db_pwd = "test";  	 // ������Ʈw�� root�K�X�p�����P�A�ܧ������������Ʈw�s�u�K�X
	
	void ErrMsg (String msg, Exception e) {
		JOptionPane.showMessageDialog(null, msg + "\n�T��:" + e, "���~�T��", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
	public CCreate_DB_Tabel() {
		
		// �إ߸�Ʈw�s��
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			ErrMsg("1.MYSQL �X�ʵ{���w�˥���!",e);
		}
		
		// �إ� Connection ���� �� �إ߸�Ʈw
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password="+db_pwd);
			stmt = conn.createStatement();
			String Create_DB = "CREATE DATABASE " + db_name;
			stmt.executeUpdate(Create_DB);
			
		} catch (SQLException e) {
			if (stmt != null) 
				ErrMsg("2."+ db_name +"��Ʈw�w�s�b!", e);
			else
				ErrMsg("3.MySQL �L�k�Ұ�!",e );
		} catch (Exception e) {
			ErrMsg("4.�o�ͨ�L���~!",e);
		}
		
		// �إ߸�ƪ�
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + db_name + "?user=root&password="+db_pwd);
			stmt = conn.createStatement();
			
			String Create_TB = "CREATE TABLE `" + emp_table + "`";
			Create_TB += "(`emp_id` INT NOT NULL AUTO_INCREMENT COMMENT '���u�s��' , ";
			Create_TB += " `emp_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�m�W' , ";
			Create_TB += " `emp_sex` CHAR(1) NOT NULL COMMENT '�ʧO' , ";
			Create_TB += " `emp_title` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '¾��' , ";
			Create_TB += " `edu` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�Ǿ�' , ";
			Create_TB += " `photo` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '�Ӥ��ɦW' , ";
			Create_TB += " `social_no` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�����Ҹ�' , ";
			Create_TB += " `telno` VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�q��' , ";
			Create_TB += " `address` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�a�}' , ";
			Create_TB += " `ext_no` CHAR(4) NULL COMMENT '����' , ";
			Create_TB += " `birthday` DATETIME NOT NULL COMMENT '�ͤ�' , ";
			Create_TB += " `onboard` DATETIME NOT NULL COMMENT '��Ȥ�' , ";
			Create_TB += " `quit_date` DATETIME NULL COMMENT '��¾��' , ";
			Create_TB += " `dept_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�����W' , ";
			Create_TB += " `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�ɶ��W�O' , ";
			Create_TB += " `last_modified` TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '��s�ɶ��W�O' , ";
			Create_TB += " `quit_mark` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '��¾�аO' , ";
			Create_TB += " PRIMARY KEY (`emp_id`)) ENGINE = InnoDB;";			
			stmt.executeUpdate(Create_TB);			
			JOptionPane.showMessageDialog(null, "5. " + db_name +" ��Ʈw�M " + emp_table +" ��ƪ�إߦ��\!");
			
//			String Create_TB2 = "CREATE TABLE `" + dept_table + "`";
//			Create_TB2 += "(`dept_id` INT NOT NULL AUTO_INCREMENT COMMENT '����id' , ";
//			Create_TB2 += " `dept_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�����W' , ";	
//			Create_TB2 += " PRIMARY KEY (`dept_id`)) ENGINE = InnoDB;";			
//			stmt.executeUpdate(Create_TB2);			
//			JOptionPane.showMessageDialog(null, "6. " + dept_table +" ��ƪ�إߦ��\!");
			
			String Create_TB3 = "CREATE TABLE `" + user_table + "`";
			Create_TB3 += "(`user_id` INT NOT NULL AUTO_INCREMENT COMMENT '�޲z��id' , ";
			Create_TB3 += " `user_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�b���W��' , ";
			Create_TB3 += " `user_pwd` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '�K�X' , ";
			Create_TB3 += " PRIMARY KEY (`user_id`)) ENGINE = InnoDB;";
			
			stmt.executeUpdate(Create_TB3);			
			JOptionPane.showMessageDialog(null, "7. " + user_table +" ��ƪ�إߦ��\!");
			
			// �s�W��luser_table ����� demo/demo
			String Insert_initUser = "INSERT INTO `"+ user_table + "` (`user_name`, `user_pwd`) VALUES ('demo', 'demo')";
			stmt.executeUpdate(Insert_initUser);
			JOptionPane.showMessageDialog(null, "8.��ƪ�:" + user_table + " �޲z�̪�l��Ʒs�W���\!");
			
			// �s�W��l dept_table ����� 
//			String Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('�޲z��')";
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('��P�~�ȳ�')";
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('�]�ȳ�')";
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('���ʳ�')";			
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('�n��}�o��')";			
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('�n����ճ�')";			
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('�k�ȳ�')";			
//			stmt.executeUpdate(Insert_initdept);
//			JOptionPane.showMessageDialog(null, "9.��ƪ�:" + dept_table + " �����W�٪�l��Ʒs�W���\!");
			
			
			stmt.close();
			System.exit(0);
			
		} catch (SQLException e) {
			if (stmt != null) 
				ErrMsg("10.��ƪ�w�s�b!", e);
			else
				ErrMsg("11.MySQL �L�k�Ұ�!",e );
		} catch (Exception e) {
			ErrMsg("12.�o�ͨ�L���~!",e);
		}
		
		
	}
}

public class Exam_createDB
{

	public static void main(String[] args)
	{
		new CCreate_DB_Tabel();

	}

}

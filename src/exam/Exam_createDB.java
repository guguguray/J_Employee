/* 湜億 1071228程式設計師班
 * 
 * 專案: 人事資料管理系統 Version.1.1
 * 
 * Program: 資料庫建立及初始化資料
 * 
 * 本程式執行前須知: ***務必先變更對應本機資料庫的 root密碼 
 * 
 * author by (Group.2)吳威德、周正庭、方奕云、蔡昀倢、曹晉睿
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
	private String db_pwd = "12345678";  	 // 本機資料庫的 root密碼如有不同，變更對應本機的資料庫連線密碼
	
	void ErrMsg (String msg, Exception e) {
		JOptionPane.showMessageDialog(null, msg + "\n訊息:" + e, "錯誤訊息", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
	public CCreate_DB_Tabel() {
		
		// 建立資料庫連接
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			ErrMsg("1.MYSQL 驅動程式安裝失敗!",e);
		}
		
		// 建立 Connection 物件 及 建立資料庫
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password="+db_pwd);
			stmt = conn.createStatement();
			String Create_DB = "CREATE DATABASE " + db_name;
			stmt.executeUpdate(Create_DB);
			
		} catch (SQLException e) {
			if (stmt != null) 
				ErrMsg("2."+ db_name +"資料庫已存在!", e);
			else
				ErrMsg("3.MySQL 無法啟動!",e );
		} catch (Exception e) {
			ErrMsg("4.發生其他錯誤!",e);
		}
		
		// 建立資料表
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + db_name + "?user=root&password="+db_pwd);
			stmt = conn.createStatement();
			
			String Create_TB = "CREATE TABLE `" + emp_table + "`";
			Create_TB += "(`emp_id` INT NOT NULL AUTO_INCREMENT COMMENT '員工編號' , ";
			Create_TB += " `emp_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '姓名' , ";
			Create_TB += " `emp_sex` CHAR(1) NOT NULL COMMENT '性別' , ";
			Create_TB += " `emp_title` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '職稱' , ";
			Create_TB += " `edu` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '學歷' , ";
			Create_TB += " `photo` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '照片檔名' , ";
			Create_TB += " `social_no` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '身分證號' , ";
			Create_TB += " `telno` VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '電話' , ";
			Create_TB += " `address` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '地址' , ";
			Create_TB += " `ext_no` CHAR(4) NULL COMMENT '分機' , ";
			Create_TB += " `birthday` DATETIME NOT NULL COMMENT '生日' , ";
			Create_TB += " `onboard` DATETIME NOT NULL COMMENT '到值日' , ";
			Create_TB += " `quit_date` DATETIME NULL COMMENT '離職日' , ";
			Create_TB += " `dept_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '部門名' , ";
			Create_TB += " `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '時間戳記' , ";
			Create_TB += " `last_modified` TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新時間戳記' , ";
			Create_TB += " `quit_mark` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '離職標記' , ";
			Create_TB += " PRIMARY KEY (`emp_id`)) ENGINE = InnoDB;";			
			stmt.executeUpdate(Create_TB);			
			JOptionPane.showMessageDialog(null, "5. " + db_name +" 資料庫和 " + emp_table +" 資料表建立成功!");
			
//			String Create_TB2 = "CREATE TABLE `" + dept_table + "`";
//			Create_TB2 += "(`dept_id` INT NOT NULL AUTO_INCREMENT COMMENT '部門id' , ";
//			Create_TB2 += " `dept_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '部門名' , ";	
//			Create_TB2 += " PRIMARY KEY (`dept_id`)) ENGINE = InnoDB;";			
//			stmt.executeUpdate(Create_TB2);			
//			JOptionPane.showMessageDialog(null, "6. " + dept_table +" 資料表建立成功!");
			
			String Create_TB3 = "CREATE TABLE `" + user_table + "`";
			Create_TB3 += "(`user_id` INT NOT NULL AUTO_INCREMENT COMMENT '管理者id' , ";
			Create_TB3 += " `user_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '帳號名稱' , ";
			Create_TB3 += " `user_pwd` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密碼' , ";
			Create_TB3 += " PRIMARY KEY (`user_id`)) ENGINE = InnoDB;";
			
			stmt.executeUpdate(Create_TB3);			
			JOptionPane.showMessageDialog(null, "7. " + user_table +" 資料表建立成功!");
			
			// 新增初始user_table 的資料 demo/demo
			String Insert_initUser = "INSERT INTO `"+ user_table + "` (`user_name`, `user_pwd`) VALUES ('demo', 'demo')";
			stmt.executeUpdate(Insert_initUser);
			JOptionPane.showMessageDialog(null, "8.資料表:" + user_table + " 管理者初始資料新增成功!");
			
			// 新增初始 dept_table 的資料 
//			String Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('管理部')";
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('行銷業務部')";
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('財務部')";
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('採購部')";			
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('軟體開發部')";			
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('軟體測試部')";			
//			stmt.executeUpdate(Insert_initdept);
//			Insert_initdept = "INSERT INTO `"+ dept_table + "` (`dept_name`) VALUES ('法務部')";			
//			stmt.executeUpdate(Insert_initdept);
//			JOptionPane.showMessageDialog(null, "9.資料表:" + dept_table + " 部門名稱初始資料新增成功!");
			
			String Insert_initemp = "INSERT INTO `emp_tbl` (`emp_id`, `emp_name`, `emp_sex`, `emp_title`, `edu`, `photo`, `social_no`, `telno`, `address`, `ext_no`, `birthday`, `onboard`, `quit_date`, `dept_name`, `create_date`, `last_modified`, `quit_mark`) VALUES (NULL, '李安', '男', '總經理', '博士', NULL, 'A000000000', '0900000000', 'Taipei', '103', '1978/4/3', '2019/3/26', NULL, '管理部', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0')";
			stmt.executeUpdate(Insert_initemp);
			Insert_initemp = "INSERT INTO `emp_tbl` (`emp_id`, `emp_name`, `emp_sex`, `emp_title`, `edu`, `photo`, `social_no`, `telno`, `address`, `ext_no`, `birthday`, `onboard`, `quit_date`, `dept_name`, `create_date`, `last_modified`, `quit_mark`) VALUES (NULL, '侯孝賢', '男', '專員', '大學', NULL, 'A000000001', '0900000000', '台北市', '111', '1978/4/3', '2019/3/26', NULL, '行銷業務部', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0')";
			stmt.executeUpdate(Insert_initemp);
			Insert_initemp = "INSERT INTO `emp_tbl` (`emp_id`, `emp_name`, `emp_sex`, `emp_title`, `edu`, `photo`, `social_no`, `telno`, `address`, `ext_no`, `birthday`, `onboard`, `quit_date`, `dept_name`, `create_date`, `last_modified`, `quit_mark`) VALUES (NULL, '蔡明亮', '男', '協理', '大專', NULL, 'A000000002', '0900000000', '台中市', '119', '1978/4/3', '2019/3/26', NULL, '軟體開發部', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0')";
			stmt.executeUpdate(Insert_initemp);
			JOptionPane.showMessageDialog(null, "9.資料表:" + emp_table + " 員工資料初始資料新增成功!");			
			
			stmt.close();
			System.exit(0);
			
		} catch (SQLException e) {
			if (stmt != null) 
				ErrMsg("10.資料表已存在!", e);
			else
				ErrMsg("11.MySQL 無法啟動!",e );
		} catch (Exception e) {
			ErrMsg("12.發生其他錯誤!",e);
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

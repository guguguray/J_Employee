--------------
Release Notes
--------------
人事資料管理系統 v1.0 
  - initial 版本  
  
-----------
file description
-----------
src\exam\Exam_createDB.java		    連線mySQL 建立資料庫及相關欄位
src\exam\Exam_login.java			    人事資料管理系統首頁登入畫面
src\exam\Exam_employeeList.java   人事資料管理系統資料管理介面

----------
開發環境
----------
Windows platform
Java 環境: Java SE v1.8.0
Eclipse 
MySQL DB

-----------
事前準備
-----------
1. 必須先有 mySQL 資料庫環境 
2. 使用前請務必先變更資料庫連線的密碼如下:
private String db_pwd = "12345678";  	 // 本機資料庫的 root密碼如有不同，變更對應您本機的資料庫連線密碼

-----------
執行順序
-----------
1. 建立資料庫及相關資料表: 執行 >>  Exam_createDB.java	
2. 進入員工資料系統: 執行 >> Exam_login.java 


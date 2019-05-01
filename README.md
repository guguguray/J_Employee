-----------
Project
-----------
人事資料管理系統 made by Java
 
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


--------------
版本資訊
--------------
 ver1.1
  - bug fixed: 新增刪除資料後，最後一筆資料無法刪除的問題
  - bug fixed: 離職日未正確判斷問題
  - bug fixed: 全部列表未正確顯示的問題 
  - 新增: Exam_createDB.java 內新增三筆初始員工資料
  - 移除: 檢查身分證號重複的判斷
  - 其他 minor issue fixed
  
ver1.0 
  - initial 版本  

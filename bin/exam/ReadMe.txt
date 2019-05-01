--------------
Release Notes
--------------

人事資料管理系統 v1.1
  - bug fixed: 新增刪除資料後，最後一筆資料無法刪除的問題
  - bug fixed: 離職日未正確判斷問題
  - bug fixed: 全部列表未正確顯示的問題 
  - 新增: Exam_createDB.java 內新增三筆初始員工資料
  - 移除: 檢查身分證號重複的判斷
  - 其他 minor issue fixed


人事資料管理系統 v1.0 
  - initial 版本
  
  
-----------
程式說明:
-----------
Exam_createDB.java		連線mySQL 建立資料庫及相關欄位
Exam_login.java			人事資料管理系統首頁登入畫面
Exam_employeeList.java  人事資料管理系統資料管理介面

*** 使用前請務必先變更資料庫連線的密碼如下:
private String db_pwd = "12345678";  	 // 本機資料庫的 root密碼如有不同，變更對應您本機的資料庫連線密碼


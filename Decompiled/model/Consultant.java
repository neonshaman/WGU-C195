/*    */ package model;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import javafx.collections.FXCollections;
/*    */ import javafx.collections.ObservableList;
/*    */ import util.DBConnection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Consultant
/*    */ {
/*    */   private String consultantName;
/*    */   private int consultantId;
/*    */   
/*    */   public Consultant(String name, int id) {
/* 24 */     this.consultantName = name;
/* 25 */     this.consultantId = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getConsultantName() {
/* 30 */     return this.consultantName;
/*    */   }
/*    */   
/*    */   public Integer getConsultantId() {
/* 34 */     return Integer.valueOf(this.consultantId);
/*    */   }
/*    */   
/*    */   public void setConsultantName(String consultantName) {
/* 38 */     this.consultantName = consultantName;
/*    */   }
/*    */   
/*    */   public void setConsultantId(int consultantId) {
/* 42 */     this.consultantId = consultantId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ObservableList<String> getAllActiveConsultants() throws SQLException {
/* 48 */     ObservableList<String> consultantsObs = FXCollections.observableArrayList();
/* 49 */     String sqlStatement = "SELECT * FROM user WHERE active = '1'";
/* 50 */     DBConnection.makeQuery(sqlStatement);
/* 51 */     while (DBConnection.result.next())
/*    */     {
/* 53 */       consultantsObs.add(DBConnection.result.getString("userName")); } 
/* 54 */     return consultantsObs;
/*    */   }
/*    */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\model\Consultant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
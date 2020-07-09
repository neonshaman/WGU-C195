/*    */ package model;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import javafx.beans.property.SimpleIntegerProperty;
/*    */ import javafx.beans.property.SimpleStringProperty;
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
/*    */ public class Customer
/*    */ {
/* 21 */   private SimpleIntegerProperty custId = new SimpleIntegerProperty();
/* 22 */   private SimpleStringProperty custName = new SimpleStringProperty();
/* 23 */   private SimpleStringProperty custAddress = new SimpleStringProperty();
/* 24 */   private SimpleStringProperty custPhone = new SimpleStringProperty();
/* 25 */   private SimpleIntegerProperty custActive = new SimpleIntegerProperty();
/*    */ 
/*    */   
/*    */   public Customer(int id, String name, String address, String phone, int active) {
/* 29 */     setCustId(id);
/* 30 */     setCustName(name);
/* 31 */     setCustAddress(address);
/* 32 */     setCustPhone(phone);
/* 33 */     setCustActive(active);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCustId() {
/* 38 */     return this.custId.get();
/*    */   }
/*    */   
/*    */   public void setCustId(int custId) {
/* 42 */     this.custId.set(custId);
/*    */   }
/*    */   
/*    */   public String getCustName() {
/* 46 */     return this.custName.get();
/*    */   }
/*    */   
/*    */   public void setCustName(String custName) {
/* 50 */     this.custName.set(custName);
/*    */   }
/*    */   
/*    */   public String getCustAddress() {
/* 54 */     return this.custAddress.get();
/*    */   }
/*    */   
/*    */   public void setCustAddress(String custAddress) {
/* 58 */     this.custAddress.set(custAddress);
/*    */   }
/*    */   
/*    */   public String getCustPhone() {
/* 62 */     return this.custPhone.get();
/*    */   }
/*    */   
/*    */   public void setCustPhone(String custPhone) {
/* 66 */     this.custPhone.set(custPhone);
/*    */   }
/*    */   
/*    */   public int getCustActive() {
/* 70 */     return this.custActive.get();
/*    */   }
/*    */   
/*    */   public void setCustActive(int custActive) {
/* 74 */     this.custActive.set(custActive);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ObservableList<String> getAllActiveCustomers() throws SQLException {
/* 80 */     ObservableList<String> customersObs = FXCollections.observableArrayList();
/* 81 */     String sqlStatement = "SELECT * FROM customer WHERE active = '1'";
/* 82 */     DBConnection.makeQuery(sqlStatement);
/* 83 */     while (DBConnection.result.next())
/*    */     {
/* 85 */       customersObs.add(DBConnection.result.getString("customerName")); } 
/* 86 */     return customersObs;
/*    */   }
/*    */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\model\Customer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
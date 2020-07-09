/*     */ package view_controller;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ResourceBundle;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.fxml.FXML;
/*     */ import javafx.fxml.FXMLLoader;
/*     */ import javafx.fxml.Initializable;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Alert;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.RadioButton;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.control.ToggleGroup;
/*     */ import javafx.stage.Stage;
/*     */ import util.DBConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NewCustomerController
/*     */   implements Initializable
/*     */ {
/*     */   Stage stage;
/*     */   Parent scene;
/*     */   @FXML
/*     */   private Button createCustBtn;
/*     */   @FXML
/*     */   private Button backBtn;
/*     */   @FXML
/*     */   private TextField custNameTxt;
/*     */   
/*     */   public void changeScene(ActionEvent event, String resource) throws IOException {
/*  39 */     this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
/*     */     
/*  41 */     this.scene = FXMLLoader.<Parent>load(getClass().getResource(resource));
/*     */     
/*  43 */     this.stage.setScene(new Scene(this.scene));
/*     */     
/*  45 */     this.stage.show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private TextField custAddTxt;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private TextField custPhoneTxt;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private RadioButton activeRad;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private RadioButton inactiveRad;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private ToggleGroup custActiveGrp;
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void backBtnClick(ActionEvent event) throws IOException {
/*  74 */     String resource = "/view_controller/customers.fxml";
/*  75 */     changeScene(event, resource);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void createCustBtnClick(ActionEvent event) throws SQLException, IOException {
/*  82 */     String nameInput = this.custNameTxt.getText();
/*  83 */     String addInput = this.custAddTxt.getText();
/*  84 */     String phoneInput = this.custPhoneTxt.getText();
/*  85 */     int custActivity = 1;
/*  86 */     Boolean inputValid = Boolean.valueOf(false);
/*     */ 
/*     */     
/*  89 */     if (!nameInput.trim().equals("") && !addInput.trim().equals("") && !phoneInput.trim().equals(""))
/*     */     {
/*  91 */       inputValid = Boolean.valueOf(true);
/*     */     }
/*     */ 
/*     */     
/*  95 */     if (this.inactiveRad.isSelected()) {
/*  96 */       custActivity = 0;
/*     */     } else {
/*  98 */       custActivity = 1;
/*     */     } 
/*     */     
/* 101 */     if (inputValid.booleanValue() == true) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       String sqlStatement = "INSERT INTO customer (customerName, address, phone, active, createDate, createdBy, lastUpdateBy) VALUES ('" + nameInput + "', '" + addInput + "', '" + phoneInput + "', " + custActivity + ", NOW(), (SELECT userName FROM user WHERE userId = " + DBConnection.consultantSession.getConsultantId() + "), (SELECT userName FROM user WHERE userId = " + DBConnection.consultantSession.getConsultantId() + "))";
/*     */       
/* 114 */       DBConnection.makeQuery(sqlStatement);
/*     */       
/* 116 */       String resource = "/view_controller/customers.fxml";
/* 117 */       changeScene(event, resource);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 122 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 123 */       alert.setTitle("Input Error");
/* 124 */       alert.setContentText("Please enter all fields.");
/* 125 */       alert.showAndWait();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initialize(URL url, ResourceBundle rb) {}
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\view_controller\NewCustomerController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
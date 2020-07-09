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
/*     */ public class ModifyCustomerController
/*     */   implements Initializable
/*     */ {
/*     */   Stage stage;
/*     */   Parent scene;
/*     */   @FXML
/*     */   private Button modifyCustBtn;
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
/*  75 */     String resource = "/view_controller/customers.fxml";
/*  76 */     changeScene(event, resource);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void modifyCustBtnClick(ActionEvent event) throws SQLException, IOException {
/*  83 */     String nameInput = this.custNameTxt.getText();
/*  84 */     String addInput = this.custAddTxt.getText();
/*  85 */     String phoneInput = this.custPhoneTxt.getText();
/*  86 */     int custActivity = 1;
/*  87 */     Boolean inputValid = Boolean.valueOf(false);
/*     */ 
/*     */     
/*  90 */     if (!nameInput.trim().equals("") && !addInput.trim().equals("") && !phoneInput.trim().equals(""))
/*     */     {
/*  92 */       inputValid = Boolean.valueOf(true);
/*     */     }
/*     */ 
/*     */     
/*  96 */     if (this.inactiveRad.isSelected()) {
/*  97 */       custActivity = 0;
/*     */     } else {
/*  99 */       custActivity = 1;
/*     */     } 
/*     */     
/* 102 */     if (inputValid.booleanValue() == true) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       String sqlStatement = "UPDATE customer SET customerName = '" + nameInput + "', address = '" + addInput + "', phone = '" + phoneInput + "', active = " + custActivity + " WHERE customerId = " + CustomersController.custToModify.getCustId() + "";
/*     */       
/* 112 */       DBConnection.makeQuery(sqlStatement);
/*     */       
/* 114 */       String resource = "/view_controller/customers.fxml";
/* 115 */       changeScene(event, resource);
/*     */     }
/*     */     else {
/*     */       
/* 119 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 120 */       alert.setTitle("Input Error");
/* 121 */       alert.setContentText("Please enter all fields.");
/* 122 */       alert.showAndWait();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(URL url, ResourceBundle rb) {
/* 133 */     this.custNameTxt.setText(CustomersController.custToModify.getCustName());
/* 134 */     this.custAddTxt.setText(CustomersController.custToModify.getCustAddress());
/* 135 */     this.custPhoneTxt.setText(CustomersController.custToModify.getCustPhone());
/*     */ 
/*     */     
/* 138 */     if (CustomersController.custToModify.getCustActive() == 0) {
/*     */       
/* 140 */       this.inactiveRad.setSelected(true);
/* 141 */       this.activeRad.setSelected(false);
/*     */     } else {
/*     */       
/* 144 */       this.inactiveRad.setSelected(false);
/* 145 */       this.activeRad.setSelected(true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\view_controller\ModifyCustomerController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
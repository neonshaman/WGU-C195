/*    */ package view_controller;
/*    */ 
/*    */ import java.awt.Desktop;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.util.ResourceBundle;
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.fxml.FXML;
/*    */ import javafx.fxml.FXMLLoader;
/*    */ import javafx.fxml.Initializable;
/*    */ import javafx.scene.Parent;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.scene.control.Alert;
/*    */ import javafx.scene.control.Button;
/*    */ import javafx.stage.Stage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MainController
/*    */   implements Initializable
/*    */ {
/*    */   Stage stage;
/*    */   Parent scene;
/*    */   @FXML
/*    */   private Button navAppointsBtn;
/*    */   @FXML
/*    */   private Button navCustRecsBtn;
/*    */   @FXML
/*    */   private Button navLogsBtn;
/*    */   
/*    */   public void changeScene(ActionEvent event, String resource) throws IOException {
/* 36 */     this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
/*    */     
/* 38 */     this.scene = FXMLLoader.<Parent>load(getClass().getResource(resource));
/*    */     
/* 40 */     this.stage.setScene(new Scene(this.scene));
/*    */     
/* 42 */     this.stage.show();
/*    */   }
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
/*    */   @FXML
/*    */   void navAppointsBtnClick(ActionEvent event) throws IOException {
/* 57 */     String resource = "/view_controller/appointments.fxml";
/* 58 */     changeScene(event, resource);
/*    */   }
/*    */ 
/*    */   
/*    */   @FXML
/*    */   void navCustRecsClick(ActionEvent event) throws IOException {
/* 64 */     String resource = "/view_controller/customers.fxml";
/* 65 */     changeScene(event, resource);
/*    */   }
/*    */ 
/*    */   
/*    */   @FXML
/*    */   void navLogsBtnClick(ActionEvent event) throws IOException {
/* 71 */     File file = new File("userlog.txt");
/* 72 */     if (Desktop.isDesktopSupported()) {
/*    */       
/* 74 */       if (file.exists()) {
/* 75 */         Desktop.getDesktop().open(file);
/*    */       } else {
/*    */         
/* 78 */         Alert alert = new Alert(Alert.AlertType.ERROR);
/* 79 */         alert.setTitle("Log Error");
/* 80 */         alert.setContentText("Log file: userlog.txt was not found. Please contact administrator.");
/* 81 */         alert.showAndWait();
/*    */       } 
/*    */     } else {
/*    */       
/* 85 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 86 */       alert.setTitle("Desktop Error");
/* 87 */       alert.setContentText("Desktop access is not supported on this device, please contact administrator.");
/* 88 */       alert.showAndWait();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void initialize(URL url, ResourceBundle rb) {}
/*    */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\view_controller\MainController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
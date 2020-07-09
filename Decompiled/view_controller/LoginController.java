/*     */ package view_controller;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.fxml.FXML;
/*     */ import javafx.fxml.FXMLLoader;
/*     */ import javafx.fxml.Initializable;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Alert;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.stage.Stage;
/*     */ import util.DBConnection;
/*     */ 
/*     */ public class LoginController
/*     */   implements Initializable {
/*     */   Stage stage;
/*     */   Parent scene;
/*     */   String loginErrorTitle;
/*     */   String loginErrorContent;
/*     */   @FXML
/*     */   private Label headerLbl;
/*     */   @FXML
/*     */   private Label passLbl;
/*     */   @FXML
/*     */   private Label userLbl;
/*     */   @FXML
/*     */   private TextField loginUserTxt;
/*     */   @FXML
/*     */   private TextField loginPassTxt;
/*     */   @FXML
/*     */   private Button loginBtn;
/*     */   
/*     */   public void changeScene(ActionEvent event, String resource) throws IOException {
/*  41 */     this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
/*     */     
/*  43 */     this.scene = FXMLLoader.<Parent>load(getClass().getResource(resource));
/*     */     
/*  45 */     this.stage.setScene(new Scene(this.scene));
/*     */     
/*  47 */     this.stage.show();
/*     */   }
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
/*     */   @FXML
/*     */   void loginBtnClick(ActionEvent event) throws IOException, SQLException {
/*  71 */     String user = this.loginUserTxt.getText();
/*  72 */     String pass = this.loginPassTxt.getText();
/*     */ 
/*     */     
/*  75 */     if (DBConnection.consultantLogin(user, pass).booleanValue()) {
/*     */       
/*  77 */       String resource = "/view_controller/main.fxml";
/*  78 */       changeScene(event, resource);
/*     */     } else {
/*  80 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/*  81 */       alert.setTitle(this.loginErrorTitle);
/*  82 */       alert.setContentText(this.loginErrorContent);
/*  83 */       alert.showAndWait();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(URL url, ResourceBundle rb) {
/*  93 */     Locale loc = Locale.getDefault();
/*  94 */     rb = ResourceBundle.getBundle("lang/localization", loc);
/*  95 */     this.headerLbl.setText(rb.getString("headerlbl"));
/*  96 */     this.userLbl.setText(rb.getString("userlbl"));
/*  97 */     this.passLbl.setText(rb.getString("passlbl"));
/*  98 */     this.loginBtn.setText(rb.getString("loginBtn"));
/*  99 */     this.loginErrorTitle = rb.getString("loginErrorTitle");
/* 100 */     this.loginErrorContent = rb.getString("loginErrorContent");
/*     */   }
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\view_controller\LoginController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
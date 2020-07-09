/*    */ package main;
/*    */ 
/*    */ import javafx.application.Application;
/*    */ import javafx.fxml.FXMLLoader;
/*    */ import javafx.scene.Parent;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.stage.Stage;
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
/*    */ public class JacobAloSchedulingApp
/*    */   extends Application
/*    */ {
/*    */   public void start(Stage stage) throws Exception {
/* 24 */     Parent root = FXMLLoader.<Parent>load(getClass().getResource("/view_controller/login.fxml"));
/*    */     
/* 26 */     Scene scene = new Scene(root);
/*    */     
/* 28 */     stage.setScene(scene);
/* 29 */     stage.show();
/* 30 */     stage.setResizable(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/*    */     try {
/* 39 */       DBConnection.makeConnection();
/*    */       
/* 41 */       launch(args);
/*    */       
/* 43 */       DBConnection.closeConnection();
/*    */     
/*    */     }
/* 46 */     catch (Exception ex) {
/* 47 */       System.out.println(ex.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\main\JacobAloSchedulingApp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
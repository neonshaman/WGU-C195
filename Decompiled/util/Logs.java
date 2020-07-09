/*    */ package util;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.time.ZonedDateTime;
/*    */ import javafx.scene.control.Alert;
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
/*    */ 
/*    */ public class Logs
/*    */ {
/*    */   public static void logUser(String userName, boolean loginValidity) throws IOException {
/* 24 */     try (PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("userLog.txt", true)))) {
/*    */       
/* 26 */       output.println(userName + ", " + (loginValidity ? "Valid" : "Invalid") + ", " + 
/* 27 */           ZonedDateTime.now());
/* 28 */     } catch (IOException ex) {
/* 29 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 30 */       alert.setTitle("Log Error");
/* 31 */       alert.setContentText("There was an error in accessing logs, please contact administrator.");
/* 32 */       alert.showAndWait();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\\\util\Logs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
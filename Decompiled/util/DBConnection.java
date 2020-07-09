/*     */ package util;
/*     */ 
/*     */ import com.mysql.jdbc.Connection;
/*     */ import java.io.IOException;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import javafx.scene.control.Alert;
/*     */ import model.Consultant;
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
/*     */ public class DBConnection
/*     */ {
/*     */   private static final String DATABASE_NAME = "U05Kjq";
/*     */   private static final String DB_URL = "jdbc:mysql://52.206.157.109/U05Kjq";
/*     */   private static final String USERNAME = "U05Kjq";
/*     */   private static final String PASSWORD = "53688526699";
/*     */   private static final String DRIVER = "com.mysql.jdbc.Driver";
/*     */   public static Consultant consultantSession;
/*  35 */   public static ResultSet result = null;
/*  36 */   public static PreparedStatement prepStmt = null;
/*     */   
/*     */   static Connection conn;
/*     */   
/*     */   public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
/*  41 */     Class.forName("com.mysql.jdbc.Driver");
/*  42 */     conn = (Connection)DriverManager.getConnection("jdbc:mysql://52.206.157.109/U05Kjq", "U05Kjq", "53688526699");
/*  43 */     System.out.println("Connection successful.");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
/*  48 */     conn.close();
/*  49 */     System.out.println("Connection closed.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Boolean consultantLogin(String username, String password) throws IOException, SQLException {
/*  56 */     String sqlStatement = "SELECT * FROM user WHERE userName='" + username + "' AND password='" + password + "'";
/*     */     
/*  58 */     makeQuery(sqlStatement);
/*     */     try {
/*  60 */       if (getResult().next()) {
/*     */         
/*  62 */         int consultantId = getResult().getInt("userId");
/*  63 */         consultantSession = new Consultant(username, consultantId);
/*  64 */         Logs.logUser(username, true);
/*  65 */         System.out.println(consultantSession.getConsultantName() + " " + consultantSession.getConsultantId());
/*     */ 
/*     */         
/*  68 */         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
/*  69 */         LocalDateTime now = LocalDateTime.now();
/*  70 */         ZoneId zid = ZoneId.systemDefault();
/*  71 */         ZonedDateTime zdt = now.atZone(zid);
/*  72 */         LocalDateTime ldt = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
/*  73 */         LocalDateTime ldt15 = ldt.plusMinutes(15L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  80 */         sqlStatement = "SELECT * FROM appointment WHERE start BETWEEN '" + ldt.format(formatter) + "' AND '" + ldt15.format(formatter) + "' AND userId = " + consultantSession.getConsultantId() + "";
/*  81 */         makeQuery(sqlStatement);
/*     */         
/*  83 */         if (result.next()) {
/*  84 */           Alert alert = new Alert(Alert.AlertType.WARNING);
/*  85 */           alert.setTitle("Appointment Alert");
/*  86 */           alert.setContentText("There is an appointment within the next 15 minutes.");
/*  87 */           alert.showAndWait();
/*     */         } 
/*  89 */         return Boolean.valueOf(true);
/*     */       } 
/*     */       
/*  92 */       Logs.logUser(username, false);
/*  93 */       return Boolean.valueOf(false);
/*     */     
/*     */     }
/*  96 */     catch (SQLException ex) {
/*  97 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/*  98 */       alert.setTitle("SQL Error");
/*  99 */       alert.setContentText("There was an SQL error. Please contact administrator.");
/* 100 */       alert.showAndWait();
/* 101 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void makeQuery(String query) throws SQLException {
/* 108 */     if (result != null) {
/* 109 */       result.close();
/*     */     }
/* 111 */     if (prepStmt != null) {
/* 112 */       prepStmt.close();
/*     */     }
/*     */     
/*     */     try {
/* 116 */       prepStmt = conn.prepareStatement(query);
/*     */ 
/*     */       
/* 119 */       if (query.toLowerCase().startsWith("select")) {
/* 120 */         result = prepStmt.executeQuery();
/* 121 */         result.beforeFirst();
/*     */       } 
/*     */       
/* 124 */       if (query.toLowerCase().startsWith("delete") || query
/* 125 */         .toLowerCase().startsWith("insert") || query
/* 126 */         .toLowerCase().startsWith("update"))
/*     */       {
/* 128 */         prepStmt.executeUpdate();
/*     */       }
/* 130 */     } catch (SQLException ex) {
/* 131 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 132 */       alert.setTitle("SQL Error");
/* 133 */       alert.setContentText(ex.getMessage());
/* 134 */       alert.showAndWait();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Consultant getConsultantSession() {
/* 140 */     return consultantSession;
/*     */   }
/*     */   
/*     */   public static Connection getConn() {
/* 144 */     return conn;
/*     */   }
/*     */   
/*     */   public static ResultSet getResult() {
/* 148 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\\\util\DBConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
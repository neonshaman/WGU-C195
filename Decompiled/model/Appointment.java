/*     */ package model;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import javafx.beans.property.SimpleIntegerProperty;
/*     */ import javafx.beans.property.SimpleStringProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import util.DBConnection;
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
/*     */ public class Appointment
/*     */ {
/*  27 */   private SimpleIntegerProperty appId = new SimpleIntegerProperty();
/*  28 */   private SimpleIntegerProperty custId = new SimpleIntegerProperty();
/*  29 */   private SimpleStringProperty custName = new SimpleStringProperty();
/*  30 */   private SimpleIntegerProperty consultId = new SimpleIntegerProperty();
/*  31 */   private SimpleStringProperty consultName = new SimpleStringProperty();
/*  32 */   private SimpleStringProperty appTitle = new SimpleStringProperty();
/*  33 */   private SimpleStringProperty appDescription = new SimpleStringProperty();
/*  34 */   private SimpleStringProperty appLocation = new SimpleStringProperty();
/*  35 */   private SimpleStringProperty appContact = new SimpleStringProperty();
/*  36 */   private SimpleStringProperty appType = new SimpleStringProperty();
/*  37 */   private SimpleStringProperty appURL = new SimpleStringProperty();
/*  38 */   private SimpleStringProperty appStartTime = new SimpleStringProperty();
/*  39 */   private SimpleStringProperty appEndTime = new SimpleStringProperty();
/*     */ 
/*     */ 
/*     */   
/*     */   public Appointment(int appId, int custId, String custName, int consultId, String consultName, String title, String desc, String loc, String contact, String type, String url, String start, String end) {
/*  44 */     setAppId(appId);
/*  45 */     setCustId(custId);
/*  46 */     setCustName(custName);
/*  47 */     setConsultId(consultId);
/*  48 */     setConsultName(consultName);
/*  49 */     setAppTitle(title);
/*  50 */     setAppDescription(desc);
/*  51 */     setAppLocation(loc);
/*  52 */     setAppContact(contact);
/*  53 */     setAppType(type);
/*  54 */     setAppURL(url);
/*  55 */     setAppStartTime(start);
/*  56 */     setAppEndTime(end);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAppId() {
/*  61 */     return this.appId.get();
/*     */   }
/*     */   
/*     */   public void setAppId(int appId) {
/*  65 */     this.appId.set(appId);
/*     */   }
/*     */   
/*     */   public int getCustId() {
/*  69 */     return this.custId.get();
/*     */   }
/*     */   
/*     */   public void setCustId(int custId) {
/*  73 */     this.custId.set(custId);
/*     */   }
/*     */   
/*     */   public String getCustName() {
/*  77 */     return this.custName.get();
/*     */   }
/*     */   
/*     */   public void setCustName(String custName) {
/*  81 */     this.custName.set(custName);
/*     */   }
/*     */   
/*     */   public int getConsultId() {
/*  85 */     return this.consultId.get();
/*     */   }
/*     */   
/*     */   public void setConsultId(int consultId) {
/*  89 */     this.consultId.set(consultId);
/*     */   }
/*     */   
/*     */   public String getConsultName() {
/*  93 */     return this.consultName.get();
/*     */   }
/*     */   
/*     */   public void setConsultName(String consultName) {
/*  97 */     this.consultName.set(consultName);
/*     */   }
/*     */   
/*     */   public String getAppTitle() {
/* 101 */     return this.appTitle.get();
/*     */   }
/*     */   
/*     */   public void setAppTitle(String appTitle) {
/* 105 */     this.appTitle.set(appTitle);
/*     */   }
/*     */   
/*     */   public String getAppDescription() {
/* 109 */     return this.appDescription.get();
/*     */   }
/*     */   
/*     */   public void setAppDescription(String appDescription) {
/* 113 */     this.appDescription.set(appDescription);
/*     */   }
/*     */   
/*     */   public String getAppLocation() {
/* 117 */     return this.appLocation.get();
/*     */   }
/*     */   
/*     */   public void setAppLocation(String appLocation) {
/* 121 */     this.appLocation.set(appLocation);
/*     */   }
/*     */   
/*     */   public String getAppContact() {
/* 125 */     return this.appContact.get();
/*     */   }
/*     */   
/*     */   public void setAppContact(String appContact) {
/* 129 */     this.appContact.set(appContact);
/*     */   }
/*     */   
/*     */   public String getAppType() {
/* 133 */     return this.appType.get();
/*     */   }
/*     */   
/*     */   public void setAppType(String appType) {
/* 137 */     this.appType.set(appType);
/*     */   }
/*     */   
/*     */   public String getAppURL() {
/* 141 */     return this.appURL.get();
/*     */   }
/*     */   
/*     */   public void setAppURL(String appURL) {
/* 145 */     this.appURL.set(appURL);
/*     */   }
/*     */   
/*     */   public String getAppStartTime() {
/* 149 */     return this.appStartTime.get();
/*     */   }
/*     */   
/*     */   public void setAppStartTime(String appStartTime) {
/* 153 */     this.appStartTime.set(appStartTime);
/*     */   }
/*     */   
/*     */   public String getAppEndTime() {
/* 157 */     return this.appEndTime.get();
/*     */   }
/*     */   
/*     */   public void setAppEndTime(String appEndTime) {
/* 161 */     this.appEndTime.set(appEndTime);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObservableList<String> getAllLocations() throws SQLException {
/* 167 */     ObservableList<String> appLocsObs = FXCollections.observableArrayList();
/* 168 */     String initSQLStatement = "SELECT * FROM city";
/* 169 */     DBConnection.makeQuery(initSQLStatement);
/* 170 */     while (DBConnection.result.next())
/*     */     {
/* 172 */       appLocsObs.add(DBConnection.result.getString("city")); } 
/* 173 */     return appLocsObs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObservableList<String> getFilledTimeSlots(String consultant, LocalDate date) throws SQLException {
/* 179 */     ObservableList<String> filledTimeSlotsStrObs = FXCollections.observableArrayList();
/* 180 */     ObservableList<LocalTime> filledTimeSlotsLTObs = FXCollections.observableArrayList();
/* 181 */     ZoneId userZid = ZoneId.systemDefault();
/* 182 */     System.out.println("User Zone: " + userZid);
/*     */     
/* 184 */     String sqlStatement = "SELECT start FROM appointment WHERE userId = (SELECT userId FROM user WHERE userName = '" + consultant + "') AND CAST(start AS DATE) = '" + date + "'";
/*     */ 
/*     */     
/* 187 */     DBConnection.makeQuery(sqlStatement);
/* 188 */     while (DBConnection.result.next())
/*     */     {
/* 190 */       filledTimeSlotsStrObs.add(DBConnection.result.getString(1));
/*     */     }
/* 192 */     System.out.println("Filled Time Slots UTC: " + filledTimeSlotsStrObs);
/*     */     
/* 194 */     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss.S").withZone(ZoneOffset.UTC);
/*     */ 
/*     */     
/* 197 */     filledTimeSlotsStrObs.forEach(dateTimeStr -> filledTimeSlotsLTObs.add(ZonedDateTime.parse(dateTimeStr, formatter).withZoneSameInstant(userZid).toLocalTime()));
/*     */ 
/*     */ 
/*     */     
/* 201 */     filledTimeSlotsStrObs.clear();
/* 202 */     System.out.println("Filled Time Slots Local Time: " + filledTimeSlotsLTObs);
/*     */     
/* 204 */     filledTimeSlotsLTObs.forEach(zonedLocalTime -> filledTimeSlotsStrObs.add(zonedLocalTime.toString()));
/*     */ 
/*     */ 
/*     */     
/* 208 */     return filledTimeSlotsStrObs;
/*     */   }
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\model\Appointment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
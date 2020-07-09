/*     */ package view_controller;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.sql.SQLException;
/*     */ import java.time.DayOfWeek;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.LocalTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ResourceBundle;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.fxml.FXML;
/*     */ import javafx.fxml.FXMLLoader;
/*     */ import javafx.fxml.Initializable;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Alert;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.DateCell;
/*     */ import javafx.scene.control.DatePicker;
/*     */ import javafx.stage.Stage;
/*     */ import model.Appointment;
/*     */ import model.Consultant;
/*     */ import model.Customer;
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
/*     */ public class ModifyAppointmentController
/*     */   implements Initializable
/*     */ {
/*     */   Stage stage;
/*     */   Parent scene;
/*  48 */   ObservableList<String> consultantsObs = FXCollections.observableArrayList();
/*  49 */   ObservableList<String> customersObs = FXCollections.observableArrayList();
/*  50 */   ObservableList<String> locationsObs = FXCollections.observableArrayList();
/*  51 */   ObservableList<String> appTypesObs = FXCollections.observableArrayList(new String[] { "Presentation", "Scrum", "Financial" });
/*  52 */   ObservableList<String> appLocsObs = FXCollections.observableArrayList();
/*  53 */   final ObservableList<String> appTimesObs = FXCollections.observableArrayList(new String[] { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" });
/*     */   
/*  55 */   ObservableList<String> invalidTimesObs = FXCollections.observableArrayList();
/*  56 */   ObservableList<String> availableTimesObs = FXCollections.observableArrayList();
/*     */   @FXML
/*     */   private ChoiceBox<String> custChBox;
/*     */   
/*     */   public void changeScene(ActionEvent event, String resource) throws IOException {
/*  61 */     this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
/*     */     
/*  63 */     this.scene = FXMLLoader.<Parent>load(getClass().getResource(resource));
/*     */     
/*  65 */     this.stage.setScene(new Scene(this.scene));
/*     */     
/*  67 */     this.stage.show();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> consultChBox;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> appTypeChBox;
/*     */   
/*     */   @FXML
/*     */   private Button modifyAppBtn;
/*     */   
/*     */   @FXML
/*     */   private DatePicker appDatePick;
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> appTimeChBox;
/*     */   
/*     */   @FXML
/*     */   private Button backBtn;
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> appLocChBox;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void backBtnClick(ActionEvent event) throws IOException {
/*  97 */     String resource = "/view_controller/appointments.fxml";
/*  98 */     changeScene(event, resource);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void modifyAppBtnClick(ActionEvent event) throws SQLException, IOException {
/* 105 */     String custInput = this.custChBox.getSelectionModel().getSelectedItem();
/* 106 */     String consultInput = this.consultChBox.getSelectionModel().getSelectedItem();
/* 107 */     String appTypeInput = this.appTypeChBox.getSelectionModel().getSelectedItem();
/* 108 */     String appLocInput = this.appLocChBox.getSelectionModel().getSelectedItem();
/* 109 */     LocalDate appDateInput = this.appDatePick.getValue();
/* 110 */     String appTimeInput = this.appTimeChBox.getSelectionModel().getSelectedItem();
/* 111 */     Boolean inputValid = Boolean.valueOf(false);
/* 112 */     Boolean timeValid = Boolean.valueOf(true);
/*     */ 
/*     */     
/* 115 */     if (custInput != null && consultInput != null && appTypeInput != null && appLocInput != null && appDateInput != null && appTimeInput != null)
/*     */     {
/*     */       
/* 118 */       inputValid = Boolean.valueOf(true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 123 */     this.invalidTimesObs.clear();
/* 124 */     this.availableTimesObs.clear();
/*     */     
/* 126 */     String checkConsultant = this.consultChBox.getSelectionModel().getSelectedItem();
/* 127 */     LocalDate checkDate = this.appDatePick.getValue();
/* 128 */     this.invalidTimesObs = Appointment.getFilledTimeSlots(checkConsultant, checkDate);
/*     */     
/* 130 */     System.out.println("Invalid times for check: " + this.invalidTimesObs);
/* 131 */     System.out.println("Input time: " + appTimeInput);
/* 132 */     if (this.invalidTimesObs.contains(appTimeInput)) {
/*     */       
/* 134 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 135 */       alert.setTitle("Time Error");
/* 136 */       alert.setContentText("You have selected an invalid time. Please do not change user timezone while scheduling an appointment.");
/* 137 */       alert.showAndWait();
/*     */     } else {
/*     */       
/* 140 */       timeValid = Boolean.valueOf(true);
/*     */     } 
/*     */     
/* 143 */     if (inputValid.booleanValue() == true && timeValid.booleanValue() == true) {
/*     */ 
/*     */       
/* 146 */       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
/* 147 */       String appTimeString = appDateInput + " " + appTimeInput;
/* 148 */       LocalDateTime ldt = LocalDateTime.parse(appTimeString, formatter);
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
/* 168 */       ZonedDateTime custAppTime = ldt.atZone(ZoneId.systemDefault());
/*     */       
/* 170 */       ZonedDateTime custAppStartUTC = custAppTime.withZoneSameInstant(ZoneId.of("UTC"));
/* 171 */       ZonedDateTime custAppEndUTC = custAppStartUTC.plusHours(1L);
/*     */       
/* 173 */       String formattedAppStart = custAppStartUTC.format(formatter);
/* 174 */       String formattedAppEnd = custAppEndUTC.format(formatter);
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
/* 189 */       String sqlStatement = "UPDATE appointment SET customerId = (SELECT customerId FROM customer WHERE customerName = '" + custInput + "'), userId = (SELECT userId FROM user WHERE userName = '" + consultInput + "'), title = 'not needed', description = 'not needed', location = '" + appLocInput + "', contact = 'not needed', type = '" + appTypeInput + "', url = 'not needed', start = '" + formattedAppStart + "', end = '" + formattedAppEnd + "', lastUpdateBy = (SELECT userName FROM user WHERE userId = " + DBConnection.consultantSession.getConsultantId() + ") WHERE appointmentId = " + AppointmentsController.appToModify.getAppId() + "";
/*     */       
/* 191 */       DBConnection.makeQuery(sqlStatement);
/*     */       
/* 193 */       String resource = "/view_controller/appointments.fxml";
/* 194 */       changeScene(event, resource);
/*     */     }
/*     */     else {
/*     */       
/* 198 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 199 */       alert.setTitle("Input Error");
/* 200 */       alert.setContentText("Please enter all fields.");
/* 201 */       alert.showAndWait();
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
/*     */     try {
/* 213 */       this.consultantsObs = Consultant.getAllActiveConsultants();
/* 214 */       this.consultChBox.setItems(this.consultantsObs);
/* 215 */       this.consultChBox.setValue(AppointmentsController.appToModify.getConsultName());
/* 216 */     } catch (SQLException ex) {
/*     */       
/* 218 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 219 */       alert.setTitle("Initialization Error");
/* 220 */       alert.setContentText("There was an error initializing from MySQL server data with active customers. Please contact administrator.");
/*     */       
/* 222 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 228 */       this.customersObs = Customer.getAllActiveCustomers();
/* 229 */       this.custChBox.setItems(this.customersObs);
/* 230 */       this.custChBox.setValue(AppointmentsController.appToModify.getCustName());
/* 231 */     } catch (SQLException ex) {
/*     */       
/* 233 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 234 */       alert.setTitle("Initialization Error");
/* 235 */       alert.setContentText("There was an error initializing from MySQL server data with active customers. Please contact administrator.");
/*     */       
/* 237 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */     
/* 241 */     this.appTypeChBox.setItems(this.appTypesObs);
/* 242 */     this.appTypeChBox.setValue(AppointmentsController.appToModify.getAppType());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 247 */       this.appLocsObs = Appointment.getAllLocations();
/* 248 */       this.appLocChBox.setItems(this.appLocsObs);
/* 249 */       this.appLocChBox.setValue(AppointmentsController.appToModify.getAppLocation());
/* 250 */     } catch (SQLException ex) {
/*     */       
/* 252 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 253 */       alert.setTitle("Initialization Error");
/* 254 */       alert.setContentText("There was an error initializing from MySQL server data with active customers. Please contact administrator.");
/*     */       
/* 256 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */     
/* 260 */     this.appDatePick.setDayCellFactory(cell -> new DateCell()
/*     */         {
/*     */           public void updateItem(LocalDate date, boolean empty) {
/* 263 */             super.updateItem(date, empty);
/* 264 */             setDisable((empty || date
/*     */                 
/* 266 */                 .getDayOfWeek() == DayOfWeek.SATURDAY || date
/* 267 */                 .getDayOfWeek() == DayOfWeek.SUNDAY || date
/* 268 */                 .isBefore(LocalDate.now())));
/* 269 */             if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isBefore(LocalDate.now())) {
/* 270 */               setStyle("-fx-background-color: #ff0000;");
/*     */             }
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 276 */     DateTimeFormatter dateInitformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
/* 277 */     LocalDateTime initLdt = LocalDateTime.parse(AppointmentsController.appToModify.getAppStartTime(), dateInitformatter);
/* 278 */     this.appDatePick.setValue(initLdt.toLocalDate());
/*     */ 
/*     */     
/* 281 */     this.consultChBox.valueProperty().addListener(newValue -> this.appDatePick.setValue(null));
/*     */ 
/*     */ 
/*     */     
/* 285 */     this.appDatePick.valueProperty().addListener(newValue -> {
/*     */           if (newValue == null) {
/*     */             this.appTimeChBox.setDisable(true);
/*     */           } else {
/*     */             String consultant = this.consultChBox.getSelectionModel().getSelectedItem();
/*     */ 
/*     */             
/*     */             LocalDate date = this.appDatePick.getValue();
/*     */ 
/*     */             
/*     */             this.appTimeChBox.setDisable(false);
/*     */ 
/*     */             
/*     */             try {
/*     */               this.invalidTimesObs.clear();
/*     */               
/*     */               this.availableTimesObs.clear();
/*     */               
/*     */               this.invalidTimesObs = Appointment.getFilledTimeSlots(consultant, date);
/*     */               
/*     */               this.appTimesObs.forEach(());
/*     */               
/*     */               this.appTimeChBox.setItems(this.availableTimesObs);
/* 308 */             } catch (SQLException ex) {
/*     */               Alert alert = new Alert(Alert.AlertType.ERROR);
/*     */               
/*     */               alert.setTitle("Initialization Error");
/*     */               
/*     */               alert.setContentText("There was an error getting available time slots from MySQL server data. Please contact administrator.");
/*     */               
/*     */               alert.showAndWait();
/*     */             } 
/*     */           } 
/*     */         });
/* 319 */     this.appTimeChBox.setDisable(false);
/* 320 */     LocalDateTime timeInitLdt = LocalDateTime.parse(AppointmentsController.appToModify.getAppStartTime(), dateInitformatter);
/* 321 */     LocalTime initLT = timeInitLdt.toLocalTime();
/* 322 */     System.out.println(initLT);
/* 323 */     this.availableTimesObs.add(initLT.toString());
/* 324 */     this.appTimeChBox.setItems(this.availableTimesObs);
/* 325 */     this.appTimeChBox.setValue(initLT.toString());
/* 326 */     System.out.println(this.appTimeChBox.getValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\view_controller\ModifyAppointmentController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
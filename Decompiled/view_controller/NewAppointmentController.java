/*     */ package view_controller;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.sql.SQLException;
/*     */ import java.time.DayOfWeek;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalDateTime;
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
/*     */ public class NewAppointmentController
/*     */   implements Initializable
/*     */ {
/*     */   Stage stage;
/*     */   Parent scene;
/*  47 */   ObservableList<String> consultantsObs = FXCollections.observableArrayList();
/*  48 */   ObservableList<String> customersObs = FXCollections.observableArrayList();
/*  49 */   ObservableList<String> locationsObs = FXCollections.observableArrayList();
/*  50 */   ObservableList<String> appTypesObs = FXCollections.observableArrayList(new String[] { "Presentation", "Scrum", "Financial" });
/*  51 */   ObservableList<String> appLocsObs = FXCollections.observableArrayList();
/*  52 */   final ObservableList<String> appTimesObs = FXCollections.observableArrayList(new String[] { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" });
/*     */   
/*  54 */   ObservableList<String> invalidTimesObs = FXCollections.observableArrayList();
/*  55 */   ObservableList<String> availableTimesObs = FXCollections.observableArrayList();
/*     */   @FXML
/*     */   private ChoiceBox<String> custChBox;
/*     */   
/*     */   public void changeScene(ActionEvent event, String resource) throws IOException {
/*  60 */     this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
/*     */     
/*  62 */     this.scene = FXMLLoader.<Parent>load(getClass().getResource(resource));
/*     */     
/*  64 */     this.stage.setScene(new Scene(this.scene));
/*     */     
/*  66 */     this.stage.show();
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
/*     */   private ChoiceBox<String> appLocChBox;
/*     */   
/*     */   @FXML
/*     */   private Button createAppBtn;
/*     */   
/*     */   @FXML
/*     */   private DatePicker appDatePick;
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> appTimeChBox;
/*     */   
/*     */   private Button backBtn;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void backBtnClick(ActionEvent event) throws IOException {
/*  95 */     String resource = "/view_controller/appointments.fxml";
/*  96 */     changeScene(event, resource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void createAppBtnClick(ActionEvent event) throws SQLException, IOException {
/* 104 */     String custInput = this.custChBox.getSelectionModel().getSelectedItem();
/* 105 */     String consultInput = this.consultChBox.getSelectionModel().getSelectedItem();
/* 106 */     String appTypeInput = this.appTypeChBox.getSelectionModel().getSelectedItem();
/* 107 */     String appLocInput = this.appLocChBox.getSelectionModel().getSelectedItem();
/* 108 */     LocalDate appDateInput = this.appDatePick.getValue();
/* 109 */     String appTimeInput = this.appTimeChBox.getSelectionModel().getSelectedItem();
/* 110 */     Boolean inputValid = Boolean.valueOf(false);
/* 111 */     Boolean timeValid = Boolean.valueOf(false);
/*     */ 
/*     */     
/* 114 */     if (custInput != null && consultInput != null && appTypeInput != null && appLocInput != null && appDateInput != null && appTimeInput != null)
/*     */     {
/*     */       
/* 117 */       inputValid = Boolean.valueOf(true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 122 */     this.invalidTimesObs.clear();
/* 123 */     this.availableTimesObs.clear();
/*     */     
/* 125 */     String checkConsultant = this.consultChBox.getSelectionModel().getSelectedItem();
/* 126 */     LocalDate checkDate = this.appDatePick.getValue();
/* 127 */     this.invalidTimesObs = Appointment.getFilledTimeSlots(checkConsultant, checkDate);
/*     */     
/* 129 */     System.out.println("Invalid times for check: " + this.invalidTimesObs);
/* 130 */     System.out.println("Input time: " + appTimeInput);
/* 131 */     if (this.invalidTimesObs.contains(appTimeInput)) {
/*     */       
/* 133 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 134 */       alert.setTitle("Time Error");
/* 135 */       alert.setContentText("You have selected an invalid time. Please do not change user timezone while scheduling an appointment.");
/* 136 */       alert.showAndWait();
/*     */     } else {
/*     */       
/* 139 */       timeValid = Boolean.valueOf(true);
/*     */     } 
/*     */     
/* 142 */     if (inputValid.booleanValue() == true && timeValid.booleanValue() == true) {
/*     */       
/* 144 */       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
/* 145 */       String appTimeString = appDateInput + " " + appTimeInput;
/* 146 */       LocalDateTime ldt = LocalDateTime.parse(appTimeString, formatter);
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
/* 166 */       ZonedDateTime custAppTime = ldt.atZone(ZoneId.systemDefault());
/*     */       
/* 168 */       ZonedDateTime custAppStartUTC = custAppTime.withZoneSameInstant(ZoneId.of("UTC"));
/* 169 */       ZonedDateTime custAppEndUTC = custAppStartUTC.plusHours(1L);
/*     */       
/* 171 */       String formattedAppStart = custAppStartUTC.format(formatter);
/* 172 */       String formattedAppEnd = custAppEndUTC.format(formatter);
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
/* 193 */       String sqlStatement = "INSERT INTO appointment (customerId, customerName, userId, userName, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy) VALUES ((SELECT customerId FROM customer WHERE customerName = '" + custInput + "'), '" + custInput + "', (SELECT userId FROM user WHERE userName = '" + consultInput + "'), '" + consultInput + "', 'not needed', 'not needed', '" + appLocInput + "', 'not needed', '" + appTypeInput + "', 'not needed', '" + formattedAppStart + "', '" + formattedAppEnd + "', NOW(), (SELECT userName FROM user WHERE userId = " + DBConnection.consultantSession.getConsultantId() + "), (SELECT userName FROM user WHERE userId = " + DBConnection.consultantSession.getConsultantId() + "))";
/*     */       
/* 195 */       DBConnection.makeQuery(sqlStatement);
/*     */       
/* 197 */       String resource = "/view_controller/appointments.fxml";
/* 198 */       changeScene(event, resource);
/*     */     } else {
/*     */       
/* 201 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 202 */       alert.setTitle("Input Error");
/* 203 */       alert.setContentText("Please enter all fields.");
/* 204 */       alert.showAndWait();
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
/* 216 */       this.consultantsObs = Consultant.getAllActiveConsultants();
/* 217 */       this.consultChBox.setItems(this.consultantsObs);
/* 218 */     } catch (SQLException ex) {
/*     */       
/* 220 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 221 */       alert.setTitle("Initialization Error");
/* 222 */       alert.setContentText("There was an error initializing from MySQL server data with active customers. Please contact administrator.");
/*     */       
/* 224 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 230 */       this.customersObs = Customer.getAllActiveCustomers();
/* 231 */       this.custChBox.setItems(this.customersObs);
/* 232 */     } catch (SQLException ex) {
/*     */       
/* 234 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 235 */       alert.setTitle("Initialization Error");
/* 236 */       alert.setContentText("There was an error initializing from MySQL server data with active customers. Please contact administrator.");
/*     */       
/* 238 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */     
/* 242 */     this.appTypeChBox.setItems(this.appTypesObs);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 247 */       this.appLocsObs = Appointment.getAllLocations();
/* 248 */       this.appLocChBox.setItems(this.appLocsObs);
/* 249 */     } catch (SQLException ex) {
/*     */       
/* 251 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 252 */       alert.setTitle("Initialization Error");
/* 253 */       alert.setContentText("There was an error initializing from MySQL server data with active customers. Please contact administrator.");
/*     */       
/* 255 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */     
/* 259 */     this.appDatePick.setDayCellFactory(cell -> new DateCell()
/*     */         {
/*     */           public void updateItem(LocalDate date, boolean empty) {
/* 262 */             super.updateItem(date, empty);
/* 263 */             setDisable((empty || date
/*     */                 
/* 265 */                 .getDayOfWeek() == DayOfWeek.SATURDAY || date
/* 266 */                 .getDayOfWeek() == DayOfWeek.SUNDAY || date
/* 267 */                 .isBefore(LocalDate.now())));
/* 268 */             if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isBefore(LocalDate.now())) {
/* 269 */               setStyle("-fx-background-color: #ff0000;");
/*     */             }
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 275 */     this.consultChBox.valueProperty().addListener(newValue -> this.appDatePick.setValue(null));
/*     */ 
/*     */ 
/*     */     
/* 279 */     this.appDatePick.valueProperty().addListener(newValue -> {
/*     */           if (newValue == null) {
/*     */             this.appTimeChBox.getItems().clear();
/*     */ 
/*     */             
/*     */             this.appTimeChBox.setDisable(true);
/*     */           } else {
/*     */             String consultant = this.consultChBox.getSelectionModel().getSelectedItem();
/*     */ 
/*     */             
/*     */             LocalDate date = this.appDatePick.getValue();
/*     */             
/*     */             this.appTimeChBox.setDisable(false);
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
/* 303 */             } catch (SQLException ex) {
/*     */               Alert alert = new Alert(Alert.AlertType.ERROR);
/*     */               alert.setTitle("Initialization Error");
/*     */               alert.setContentText("There was an error getting available time slots from MySQL server data. Please contact administrator.");
/*     */               alert.showAndWait();
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\view_controller\NewAppointmentController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
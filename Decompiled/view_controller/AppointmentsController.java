/*     */ package view_controller;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.sql.SQLException;
/*     */ import java.time.LocalDate;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.Optional;
/*     */ import java.util.ResourceBundle;
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
/*     */ import javafx.scene.control.ButtonType;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.scene.control.cell.PropertyValueFactory;
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
/*     */ 
/*     */ public class AppointmentsController
/*     */   implements Initializable
/*     */ {
/*     */   Stage stage;
/*     */   Parent scene;
/*  49 */   ObservableList<Appointment> appointmentsObs = FXCollections.observableArrayList();
/*     */   
/*  51 */   ObservableList<String> consultantsObs = FXCollections.observableArrayList();
/*  52 */   ObservableList<String> customersObs = FXCollections.observableArrayList();
/*  53 */   ObservableList<String> locationsObs = FXCollections.observableArrayList();
/*  54 */   ObservableList<String> appTypesObs = FXCollections.observableArrayList(new String[] { "Presentation", "Scrum", "Financial" });
/*  55 */   ObservableList<String> appLocsObs = FXCollections.observableArrayList();
/*     */   
/*     */   public static Appointment appToModify;
/*     */   
/*     */   @FXML
/*     */   private TableView<Appointment> appointmentsTbl;
/*     */   
/*     */   public void changeScene(ActionEvent event, String resource) throws IOException {
/*  63 */     this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
/*     */     
/*  65 */     this.scene = FXMLLoader.<Parent>load(getClass().getResource(resource));
/*     */     
/*  67 */     this.stage.setScene(new Scene(this.scene));
/*     */     
/*  69 */     this.stage.show();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Appointment, Integer> appIdCol;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Appointment, String> consultNameCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Appointment, String> custNameCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Appointment, String> startCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Appointment, String> endCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Appointment, String> typeCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Appointment, String> locCol;
/*     */   
/*     */   @FXML
/*     */   private Button modAppBtn;
/*     */   
/*     */   @FXML
/*     */   private Button mainMenuBtn;
/*     */   
/*     */   @FXML
/*     */   private Button newAppBtn;
/*     */   
/*     */   @FXML
/*     */   private Button filterBtn;
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> consultChBox;
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> custChBox;
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> typeChBox;
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> locChBox;
/*     */   
/*     */   @FXML
/*     */   private ChoiceBox<String> periodChBox;
/*     */   
/*     */   @FXML
/*     */   private Button delAppBtn;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void delAppBtnClick(ActionEvent event) throws SQLException, IOException {
/* 129 */     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
/* 130 */     alert.setTitle("Confirmation");
/* 131 */     alert.setContentText("Permanently delete this appointment?");
/* 132 */     Optional<ButtonType> userInput = alert.showAndWait();
/* 133 */     if (userInput.get() == ButtonType.OK) {
/*     */       
/* 135 */       appToModify = this.appointmentsTbl.getSelectionModel().getSelectedItem();
/* 136 */       String sqlStatement = "DELETE FROM appointment WHERE appointmentId = '" + appToModify.getAppId() + "'";
/* 137 */       DBConnection.makeQuery(sqlStatement);
/* 138 */       String resource = "/view_controller/appointments.fxml";
/* 139 */       changeScene(event, resource);
/*     */     } else {
/* 141 */       alert.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void filterBtnClick(ActionEvent event) {
/* 150 */     LocalDate periodBegin = LocalDate.now();
/* 151 */     LocalDate periodEnd = LocalDate.now().plusMonths(1L);
/*     */     
/* 153 */     int sqlCounter = 0;
/*     */     
/* 155 */     String consultQuery = "'All'";
/* 156 */     String custQuery = "'All'";
/* 157 */     String appTypeQuery = "'All'";
/* 158 */     String appLocQuery = "'All'";
/* 159 */     String periodQuery = "'All Future Appointments'";
/* 160 */     String sqlStatement = "SELECT * FROM appointment WHERE";
/*     */ 
/*     */     
/* 163 */     if (this.consultChBox.getSelectionModel().getSelectedItem() != null)
/*     */     {
/* 165 */       consultQuery = " userId = (SELECT userId FROM user WHERE userName = '" + (String)this.consultChBox.getSelectionModel().getSelectedItem() + "')"; } 
/* 166 */     if (this.custChBox.getSelectionModel().getSelectedItem() != null)
/*     */     {
/* 168 */       custQuery = " customerId = (SELECT customerId FROM customer WHERE customerName = '" + (String)this.custChBox.getSelectionModel().getSelectedItem() + "')"; } 
/* 169 */     if (this.typeChBox.getSelectionModel().getSelectedItem() != null)
/* 170 */       appTypeQuery = " type = '" + (String)this.typeChBox.getSelectionModel().getSelectedItem() + "'"; 
/* 171 */     if (this.locChBox.getSelectionModel().getSelectedItem() != null)
/* 172 */       appLocQuery = " location = '" + (String)this.locChBox.getSelectionModel().getSelectedItem() + "'"; 
/* 173 */     if (this.periodChBox.getSelectionModel().getSelectedItem() != null) {
/* 174 */       periodQuery = this.periodChBox.getSelectionModel().getSelectedItem();
/*     */     }
/*     */     
/* 177 */     if (consultQuery.contains("'All'")) {
/* 178 */       consultQuery = null;
/*     */     }
/* 180 */     if (custQuery.contains("'All'")) {
/* 181 */       custQuery = null;
/*     */     }
/* 183 */     if (appTypeQuery.contains("'All'")) {
/* 184 */       appTypeQuery = null;
/*     */     }
/* 186 */     if (appLocQuery.contains("'All'")) {
/* 187 */       appLocQuery = null;
/*     */     }
/* 189 */     if (periodQuery.contains("'All Future Appointments'")) {
/* 190 */       periodQuery = null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 195 */       if (consultQuery != null) {
/*     */         
/* 197 */         sqlStatement = sqlStatement.concat(consultQuery);
/* 198 */         sqlCounter++;
/*     */       } 
/*     */       
/* 201 */       if (custQuery != null)
/*     */       {
/* 203 */         if (sqlCounter > 0) {
/*     */           
/* 205 */           sqlStatement = sqlStatement.concat(" AND ").concat(custQuery);
/*     */         } else {
/*     */           
/* 208 */           sqlStatement = sqlStatement.concat(custQuery);
/* 209 */           sqlCounter++;
/*     */         } 
/*     */       }
/*     */       
/* 213 */       if (appTypeQuery != null)
/*     */       {
/* 215 */         if (sqlCounter > 0) {
/*     */           
/* 217 */           sqlStatement = sqlStatement.concat(" AND ").concat(appTypeQuery);
/*     */         } else {
/*     */           
/* 220 */           sqlStatement = sqlStatement.concat(appTypeQuery);
/* 221 */           sqlCounter++;
/*     */         } 
/*     */       }
/*     */       
/* 225 */       if (appLocQuery != null)
/*     */       {
/* 227 */         if (sqlCounter > 0) {
/*     */           
/* 229 */           sqlStatement = sqlStatement.concat(" AND ").concat(appLocQuery);
/*     */         } else {
/*     */           
/* 232 */           sqlStatement = sqlStatement.concat(appLocQuery);
/* 233 */           sqlCounter++;
/*     */         } 
/*     */       }
/*     */       
/* 237 */       if (periodQuery != null) {
/* 238 */         if (periodQuery.equals("Weekly"))
/*     */         {
/* 240 */           periodEnd = LocalDate.now().plusWeeks(1L);
/*     */         }
/* 242 */         if (periodQuery.equals("Monthly"))
/*     */         {
/* 244 */           periodEnd = LocalDate.now().plusMonths(1L);
/*     */         }
/*     */         
/* 247 */         if (sqlCounter > 0) {
/*     */           
/* 249 */           sqlStatement = sqlStatement.concat(" AND start >= '" + periodBegin + "' AND start <= '" + periodEnd + "'");
/*     */         }
/*     */         else {
/*     */           
/* 253 */           sqlStatement = sqlStatement.concat(" start >= '" + periodBegin + "' AND start <= '" + periodEnd + "'");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 258 */       System.out.println(sqlStatement);
/* 259 */       DBConnection.makeQuery(sqlStatement);
/*     */       
/* 261 */       this.appointmentsObs.clear();
/* 262 */       while (DBConnection.result.next())
/*     */       {
/* 264 */         this.appointmentsObs.add(new Appointment(DBConnection.result
/* 265 */               .getInt("appointmentId"), DBConnection.result
/* 266 */               .getInt("customerId"), DBConnection.result
/* 267 */               .getString("customerName"), DBConnection.result
/* 268 */               .getInt("userId"), DBConnection.result
/* 269 */               .getString("userName"), DBConnection.result
/* 270 */               .getString("title"), DBConnection.result
/* 271 */               .getString("description"), DBConnection.result
/* 272 */               .getString("location"), DBConnection.result
/* 273 */               .getString("contact"), DBConnection.result
/* 274 */               .getString("type"), DBConnection.result
/* 275 */               .getString("url"), DBConnection.result
/* 276 */               .getString("start"), DBConnection.result
/* 277 */               .getString("end")));
/*     */       }
/*     */       
/* 280 */       this.appointmentsTbl.refresh();
/*     */     }
/* 282 */     catch (Exception ex) {
/*     */       
/* 284 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 285 */       alert.setTitle("Search parameter error");
/* 286 */       alert.setContentText("There were no search parameters. Please make a selection and try again.");
/* 287 */       alert.showAndWait();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void mainMenuBtnClick(ActionEvent event) throws IOException {
/* 294 */     String resource = "/view_controller/main.fxml";
/* 295 */     changeScene(event, resource);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void modAppBtnClick(ActionEvent event) throws IOException {
/* 302 */     if (this.appointmentsTbl.getSelectionModel().getSelectedItem() != null) {
/* 303 */       appToModify = this.appointmentsTbl.getSelectionModel().getSelectedItem();
/*     */       
/* 305 */       String resource = "/view_controller/modifyAppointment.fxml";
/* 306 */       changeScene(event, resource);
/*     */     } else {
/*     */       
/* 309 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 310 */       alert.setTitle("Selection Error");
/* 311 */       alert.setContentText("Please select an appointment to modify.");
/* 312 */       alert.showAndWait();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void newAppBtnClick(ActionEvent event) throws IOException {
/* 319 */     String resource = "/view_controller/newAppointment.fxml";
/* 320 */     changeScene(event, resource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(URL url, ResourceBundle rb) {
/* 329 */     appToModify = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 336 */       String initSQLStatement = "SELECT * FROM appointment WHERE userId = '" + DBConnection.consultantSession.getConsultantId() + "'";
/* 337 */       DBConnection.makeQuery(initSQLStatement);
/* 338 */       while (DBConnection.result.next()) {
/*     */         
/* 340 */         ZoneId userZid = ZoneId.systemDefault();
/* 341 */         DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(ZoneOffset.UTC);
/* 342 */         DateTimeFormatter formatterUser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(ZoneOffset.systemDefault());
/*     */         
/* 344 */         this.appointmentsObs.add(new Appointment(DBConnection.result
/* 345 */               .getInt("appointmentId"), DBConnection.result
/* 346 */               .getInt("customerId"), DBConnection.result
/* 347 */               .getString("customerName"), DBConnection.result
/* 348 */               .getInt("userId"), DBConnection.result
/* 349 */               .getString("userName"), DBConnection.result
/* 350 */               .getString("title"), DBConnection.result
/* 351 */               .getString("description"), DBConnection.result
/* 352 */               .getString("location"), DBConnection.result
/* 353 */               .getString("contact"), DBConnection.result
/* 354 */               .getString("type"), DBConnection.result
/* 355 */               .getString("url"), 
/* 356 */               ZonedDateTime.parse(DBConnection.result.getString("start"), formatterUTC).withZoneSameInstant(userZid).format(formatterUser), 
/* 357 */               ZonedDateTime.parse(DBConnection.result.getString("end"), formatterUTC).withZoneSameInstant(userZid).format(formatterUser)));
/*     */       } 
/* 359 */     } catch (Exception ex) {
/*     */       
/* 361 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 362 */       alert.setTitle("Initialization Error");
/* 363 */       alert.setContentText("There was an error initializing from MySQL server data with current consultant session appointments. Please contact administrator.");
/*     */       
/* 365 */       alert.showAndWait();
/*     */     } 
/*     */     
/* 368 */     this.appointmentsTbl.setItems(this.appointmentsObs);
/* 369 */     this.appIdCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
/* 370 */     this.consultNameCol.setCellValueFactory(new PropertyValueFactory<>("consultName"));
/* 371 */     this.custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
/* 372 */     this.startCol.setCellValueFactory(new PropertyValueFactory<>("appStartTime"));
/* 373 */     this.endCol.setCellValueFactory(new PropertyValueFactory<>("appEndTime"));
/* 374 */     this.typeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
/* 375 */     this.locCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
/* 376 */     this.appointmentsTbl.refresh();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 381 */       this.consultantsObs = Consultant.getAllActiveConsultants();
/* 382 */       this.consultantsObs.add("All");
/* 383 */       this.consultChBox.setItems(this.consultantsObs);
/* 384 */     } catch (SQLException ex) {
/*     */       
/* 386 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 387 */       alert.setTitle("Initialization Error");
/* 388 */       alert.setContentText("There was an error initializing from MySQL server data with active consultants. Please contact administrator.");
/*     */       
/* 390 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 396 */       this.customersObs = Customer.getAllActiveCustomers();
/* 397 */       this.customersObs.add("All");
/* 398 */       this.custChBox.setItems(this.customersObs);
/* 399 */     } catch (SQLException ex) {
/*     */       
/* 401 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 402 */       alert.setTitle("Initialization Error");
/* 403 */       alert.setContentText("There was an error initializing from MySQL server data with active customers. Please contact administrator.");
/*     */       
/* 405 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */     
/* 409 */     this.typeChBox.setItems(this.appTypesObs);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 414 */       this.appLocsObs = Appointment.getAllLocations();
/* 415 */       this.appLocsObs.add("All");
/* 416 */       this.locChBox.setItems(this.appLocsObs);
/* 417 */     } catch (SQLException ex) {
/*     */       
/* 419 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 420 */       alert.setTitle("Initialization Error");
/* 421 */       alert.setContentText("There was an error initializing from MySQL server data with locations. Please contact administrator.");
/*     */       
/* 423 */       alert.showAndWait();
/*     */     } 
/*     */ 
/*     */     
/* 427 */     this.periodChBox.setItems(FXCollections.observableArrayList(new String[] { "Weekly", "Monthly", "All Future Appointments" }));
/*     */   }
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\view_controller\AppointmentsController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
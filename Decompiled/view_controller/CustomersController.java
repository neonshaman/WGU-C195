/*     */ package view_controller;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.sql.SQLException;
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
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.scene.control.cell.PropertyValueFactory;
/*     */ import javafx.stage.Stage;
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
/*     */ public class CustomersController
/*     */   implements Initializable
/*     */ {
/*     */   Stage stage;
/*     */   Parent scene;
/*  41 */   ObservableList<Customer> customersObs = FXCollections.observableArrayList();
/*     */   
/*     */   public static Customer custToModify;
/*     */   
/*     */   @FXML
/*     */   private TableView<Customer> customersTbl;
/*     */   
/*     */   public void changeScene(ActionEvent event, String resource) throws IOException {
/*  49 */     this.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
/*     */     
/*  51 */     this.scene = FXMLLoader.<Parent>load(getClass().getResource(resource));
/*     */     
/*  53 */     this.stage.setScene(new Scene(this.scene));
/*     */     
/*  55 */     this.stage.show();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Customer, Integer> custIdCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Customer, String> custNameCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Customer, String> custAddCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Customer, String> custPhoneCol;
/*     */   
/*     */   @FXML
/*     */   private TableColumn<Customer, Integer> custActiveCol;
/*     */   
/*     */   @FXML
/*     */   private Button newCustBtn;
/*     */   
/*     */   @FXML
/*     */   private Button delCustBtn;
/*     */   
/*     */   @FXML
/*     */   private Button mainMenuBtn;
/*     */   
/*     */   @FXML
/*     */   private Button modifyCustBtn;
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void delCustBtnClick(ActionEvent event) throws SQLException, IOException {
/*  90 */     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
/*  91 */     alert.setTitle("Confirmation");
/*  92 */     alert.setContentText("Permanently delete this customer and all associated appointments?");
/*  93 */     Optional<ButtonType> userInput = alert.showAndWait();
/*  94 */     if (userInput.get() == ButtonType.OK) {
/*     */ 
/*     */       
/*  97 */       custToModify = this.customersTbl.getSelectionModel().getSelectedItem();
/*  98 */       String sqlStatement = "DELETE FROM appointment WHERE customerId = '" + custToModify.getCustId() + "'";
/*  99 */       DBConnection.makeQuery(sqlStatement);
/* 100 */       sqlStatement = "DELETE FROM customer WHERE customerId = '" + custToModify.getCustId() + "'";
/* 101 */       DBConnection.makeQuery(sqlStatement);
/*     */       
/* 103 */       String resource = "/view_controller/customers.fxml";
/* 104 */       changeScene(event, resource);
/*     */     } else {
/* 106 */       alert.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void mainMenuBtnClick(ActionEvent event) throws IOException {
/* 113 */     String resource = "/view_controller/main.fxml";
/* 114 */     changeScene(event, resource);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void modifyCustBtnClick(ActionEvent event) throws IOException {
/* 121 */     if (this.customersTbl.getSelectionModel().getSelectedItem() != null) {
/* 122 */       custToModify = this.customersTbl.getSelectionModel().getSelectedItem();
/*     */       
/* 124 */       String resource = "/view_controller/modifyCustomer.fxml";
/* 125 */       changeScene(event, resource);
/*     */     } else {
/*     */       
/* 128 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 129 */       alert.setTitle("Selection Error");
/* 130 */       alert.setContentText("Please select a customer to modify.");
/* 131 */       alert.showAndWait();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @FXML
/*     */   void newCustBtnClick(ActionEvent event) throws IOException {
/* 138 */     String resource = "/view_controller/newCustomer.fxml";
/* 139 */     changeScene(event, resource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(URL url, ResourceBundle rb) {
/* 149 */     custToModify = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 155 */       String initSQLStatement = "SELECT * FROM customer";
/* 156 */       DBConnection.makeQuery(initSQLStatement);
/* 157 */       while (DBConnection.result.next())
/*     */       {
/* 159 */         this.customersObs.add(new Customer(DBConnection.result
/* 160 */               .getInt("customerId"), DBConnection.result
/* 161 */               .getString("customerName"), DBConnection.result
/* 162 */               .getString("address"), DBConnection.result
/* 163 */               .getString("phone"), DBConnection.result
/* 164 */               .getInt("active")));
/*     */       }
/*     */     }
/* 167 */     catch (Exception ex) {
/*     */       
/* 169 */       Alert alert = new Alert(Alert.AlertType.ERROR);
/* 170 */       alert.setTitle("Initialization Error");
/* 171 */       alert.setContentText("There was an error initializing from MySQL server data with customers. Please contact administrator.");
/*     */       
/* 173 */       alert.showAndWait();
/*     */     } 
/*     */     
/* 176 */     this.customersTbl.setItems(this.customersObs);
/* 177 */     this.custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
/* 178 */     this.custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
/* 179 */     this.custAddCol.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
/* 180 */     this.custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
/* 181 */     this.custActiveCol.setCellValueFactory(new PropertyValueFactory<>("custActive"));
/* 182 */     this.customersTbl.refresh();
/*     */   }
/*     */ }


/* Location:              C:\Users\neons\Desktop\JacobAloSchedulingApp\build\classes\!\view_controller\CustomersController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
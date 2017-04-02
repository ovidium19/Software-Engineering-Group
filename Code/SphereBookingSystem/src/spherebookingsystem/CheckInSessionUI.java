/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 */
public class CheckInSessionUI {
    
    private static Connection conn;
    private static Scene mainScene;    
    private static Stage theStage;

    private Customer tempCustomer;
    private CustomerRepoImpl customerRepo;
    private ComboBox sessionsComboBox;
    private SessionRepoImpl sessRepo;
    private Scene goBack;
    
    
    private static CheckInSessionUI instance = null;
    private Object checkInCustomer;
    private CheckInSessionUI(Stage primaryStage, Connection con,Scene goBack){
        
        theStage=primaryStage;
        conn=con;
        customerRepo=new CustomerRepoImpl();
        tempCustomer=new Customer();
        sessionsComboBox=new ComboBox();
        sessRepo=new SessionRepoImpl();
        this.goBack=goBack;
   
   
               
    }
    
    public static CheckInSessionUI getInstance(Stage stage,Connection con,Scene goBack){
        
        if (instance==null){
            instance=new CheckInSessionUI(stage,con,goBack);
        }
        return instance;
    }
    
    
    public Scene makeCheckInScreen() {
        
        
        Label welcomeText = new Label();
        welcomeText.setText("Welcome to Sphere Booking & Checking In System");
        welcomeText.setAlignment(Pos.TOP_CENTER);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setPadding(new Insets(12,5,20,5));
        
        
        
        Label TitleText = new Label();
        TitleText.setText("Check In Customer");
        TitleText.setAlignment(Pos.TOP_CENTER);
        TitleText.setTextAlignment(TextAlignment.CENTER);
        TitleText.setPadding(new Insets(12,5,20,5));
        
        Label EnterLabel = new Label();
        EnterLabel.setText("Enter Customers ID:  ");
        EnterLabel.setAlignment(Pos.TOP_CENTER);
        EnterLabel.setTextAlignment(TextAlignment.CENTER);
        
         Label CheckLabel = new Label();
        CheckLabel.setText("Enter Customers Email:  ");
       CheckLabel.setAlignment(Pos.TOP_CENTER);
        CheckLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField EmailText = new TextField();
        EmailText.setAlignment(Pos.TOP_CENTER);
        
        HBox CheckHBox = new HBox();
        CheckHBox.getChildren().addAll(CheckLabel, EmailText);
        CheckHBox.setAlignment(Pos.TOP_CENTER);
        
        
        
        
        
        Label  customerStatusLabel=new Label();
        customerStatusLabel.setAlignment(Pos.TOP_CENTER);
        customerStatusLabel.setTextAlignment(TextAlignment.CENTER);
        
        
        TextField EnterText = new TextField();
        EnterText.setAlignment(Pos.TOP_CENTER);
        
         
        
       EnterText.textProperty().addListener((obs,o,n)->{
            try{
                int a = Integer.parseInt(n);
                if (a<=0){
                    EnterText.setText("");
                }
            }
            catch (NumberFormatException ex){
                EnterText.setText("");
            }
        });
        
        
        
        
        HBox EnterHBox = new HBox();
        EnterHBox.getChildren().addAll(EnterLabel, EnterText);
        EnterHBox.setAlignment(Pos.TOP_CENTER);
       
        

        Button searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setAlignment(Pos.TOP_CENTER);
        searchButton.setTextAlignment(TextAlignment.CENTER);
       
        
        
        // When button is pressed, recieves a boolean from the customer controller, to see
        // whether the customer exists or not. If they are registered, rest of the UI is shown
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
           
            
            @Override
            public void handle(ActionEvent event) {
                
                // Retrieve the customer ID that was typed in
                
                int theID = Integer.parseInt(EnterText.getText());
                boolean isACustomer;
                isACustomer = customerRepo.checkCustomerID(conn, theID);
            
                
                if (!isACustomer) {
               
                   customerStatusLabel.setText("Customer is not Registered. Please Register the Customer.");
                }
                else{
                    try {
                    tempCustomer = customerRepo.readByID(conn, theID);
                    Scene lastScene=theStage.getScene();
                    theStage.setScene(makeDetailsScene(lastScene));
                    //theStage.show();
                }
                          
                 catch (Exception ex) {
                    System.out.println(ex);
                }
                }
            }
        });
       
          
        
       
        
        
        
        
        
		
		
		Button BackButton= new Button () ;
		BackButton.setText("Back") ;
		BackButton.setAlignment(Pos.TOP_CENTER);
		BackButton.setTextAlignment(TextAlignment.CENTER);
                BackButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               theStage.setScene(goBack);
            }
        });
		
	
        HBox buttons = new HBox();
        buttons.getChildren().addAll(searchButton,BackButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        VBox root = new VBox();
        root.getChildren().addAll(TitleText, EnterHBox, buttons,customerStatusLabel,CheckHBox);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        
        
        Scene scene = new Scene(root, 500, 450);
        return(scene); 
    } 
    private Scene makeDetailsScene(Scene lastScene){
        BookingRepoImpl book=new BookingRepoImpl();
        ArrayList<Session> sessions=book.readSessionsForCustomer(conn, tempCustomer);
        sessionsComboBox.getItems().clear();
        sessionsComboBox.getItems().addAll(sessions);
        Label title = new Label("Customers Profile");
        BorderPane root= new BorderPane();
        
        root.setTop(title);
        
        GridPane centerRoot=new GridPane();
        centerRoot.setHgap(10);
        centerRoot.setVgap(10);
        Label fname = new Label("First Name:");
        Label lname = new Label("Last Name:");
        Label email=new Label("Email:");
        Label tphone = new Label("Telephone:");
        Label membership = new Label("member:");
        Label sessionLabel=new Label("Sessions: ");
        Label fnameFromDb=new Label(tempCustomer.getFirstName());
        Label lnameFromDb=new Label(tempCustomer.getLastName());
        Label emailFromDb=new Label(tempCustomer.getEmail());
        Label membFromDb=new Label(tempCustomer.getMembership());
        Label tphoneFromDb=new Label(tempCustomer.getTelephoneNo());
        Label success = new Label();
        success.setVisible(false);
        
        Button checkInCustomerButton = new Button();
        checkInCustomerButton.setText("Check In Customer");
        checkInCustomerButton.setAlignment(Pos.TOP_CENTER);
        checkInCustomerButton.setTextAlignment(TextAlignment.CENTER);
        checkInCustomerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               Session tempSession=(Session)sessionsComboBox.getValue();
              //BookingRepoImpl book= new BookingRepoImpl();
               book.checkInCustomer(conn, tempSession, tempCustomer);
               System.out.println("Checked in customer "+tempCustomer.getCustomerID());
               success.setText("Successfully checked in Customer "+tempCustomer.getFirstName()+" "+
                       tempCustomer.getLastName()+" to Session "+tempSession.getId());
               success.setVisible(true);
               
            }
        });
        
        
      
          
        
       Button exitButton=new Button();
       exitButton.setText("Back");
       exitButton.setAlignment(Pos.TOP_CENTER);
       exitButton.setTextAlignment(TextAlignment.CENTER);
       exitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               theStage.setScene(lastScene);
            }
        });
    
       
        centerRoot.add(fname, 0, 0);
        centerRoot.add(fnameFromDb,1,0);
        centerRoot.add(lname,0,1);
        centerRoot.add(lnameFromDb,1,1);
        centerRoot.add(email, 0,2);
        centerRoot.add(emailFromDb,1,2);
        centerRoot.add(tphone,0,3);
        centerRoot.add(tphoneFromDb,1,3);
        centerRoot.add(membership,0,4);
        centerRoot.add(membFromDb,1,4);
        centerRoot.add(sessionLabel,0,5);
        centerRoot.add(sessionsComboBox,1,5);
        centerRoot.add(checkInCustomerButton,0,8);
        centerRoot.add(exitButton,1,8);
        centerRoot.add(success, 0, 10, 2, 1);
        
       
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setPadding(new Insets(20,20,20,20));
        root.setCenter(centerRoot);
        Scene scene=new Scene(root,450,450);
        return scene;
    }
    
            
}
    


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Genaro
 */
public class SphereBookingSystem extends Application {
    
    private Stage theStage;
    private Stage managerStage;
    private final static Connection conn = connectDB();
    private BookingController bookingControllerConnection = new BookingController();
    private CustomerController customerControllerConnection = new CustomerController();
    private SessionController sessionControllerConnection = new SessionController();
    private static LoginRepoImpl loginRepoImpl = new LoginRepoImpl();
    //------------------------------------------------------------
    /*
    properties used in Welcome Screen
    */
    private PasswordField passwordText = new PasswordField();
    private TextField usernameText = new TextField();
    //-------------------------------------------------------------
    
    private TextField firstNameText = new TextField();
    private Label customerStatusLabel = new Label();
    private VBox sessionPickerInfo = new VBox();
    private DatePicker sessionPicker = new DatePicker();
    private HBox availableSessionsInfo = new HBox();
    private HBox confirmationInfo = new HBox();
    private RadioButton chk = new RadioButton();
    //-------------------------------------------------------------
    //Method to connect to our DB
    final static Connection connectDB(){
         String connectionURL = "jdbc:derby://localhost:1527/SphereDB";
         String uName = "admin1";
         String uPass = "admin1";
         try {             
                    Connection conn = DriverManager.getConnection(connectionURL, uName, uPass);
                    System.out.println("Connect to database..."); 
                    return conn;
            } catch (SQLException ex) {             
                System.out.println("Connection failed."); 
                return null;
            }
    }
    //-------------------------------------------------------------
    
    private Scene makeWelcomeScreen(){
        
        Label welcomeText = new Label();
        welcomeText.setText("Welcome to Sphere Booking & Checking In System");
        welcomeText.setAlignment(Pos.TOP_CENTER);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setPadding(new Insets(12,5,20,5));
        
        Label usernameLabel = new Label();
        usernameLabel.setText("Enter Username...");
        
        
        usernameText.setAlignment(Pos.TOP_CENTER);
        usernameText.setPrefSize(250, 30);
        usernameText.setMaxSize(usernameText.getPrefWidth(), usernameText.getPrefHeight());
        
        Label passwordLabel = new Label();
        passwordLabel.setText("Enter Password...");
        
        passwordText.setAlignment(Pos.TOP_CENTER);
        passwordText.setPrefSize(250, 30);
        passwordText.setMaxSize(passwordText.getPrefWidth(), passwordText.getPrefHeight());
        
        Label errorMessage = new Label("Username or password incorrect. Try again!");
        errorMessage.setVisible(false);
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                String uName=usernameText.getText();
                String uPass=passwordText.getText();
                Login tempLogin=loginRepoImpl.read(conn, new Login(uName,uPass));
                if (tempLogin.getLoginid()==-1){
                    System.out.println("no login");
                    errorMessage.setVisible(true);
                    passwordText.setText("");
                    usernameText.setText("");
                }
                else if (tempLogin.getUsertype().equals("slopeoperator")){
                    Scene temp=makeSlopeOperatorScreen();
                    theStage.setScene(temp);
                    
                }
                else if (tempLogin.getUsertype().equals("manager")){
                    makeAddSessionScreen(theStage, conn);
                }  
            }
        });
        
        VBox userInfo = new VBox();
        userInfo.getChildren().addAll(usernameLabel, usernameText);
        userInfo.setAlignment(Pos.TOP_CENTER);
        
        VBox passwordInfo = new VBox();
        passwordInfo.getChildren().addAll(passwordLabel, passwordText);
        passwordInfo.setAlignment(Pos.TOP_CENTER);
        
        VBox root = new VBox();
        root.getChildren().addAll(welcomeText, userInfo, passwordInfo, submitButton,errorMessage);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        Scene scene = new Scene(root,500,450);
        
        return scene;
    }
    
    private Scene makeSlopeOperatorScreen() {
        
        Label slopeOperatorLabel = new Label();
        slopeOperatorLabel.setText("SLOPE OPERATOR FUNCTIONS");
        
        Button registerButton = new Button();
        registerButton.setText("Register A Customer");
        registerButton.setAlignment(Pos.TOP_CENTER);
        registerButton.setPrefSize(250, 30);
        registerButton.setMaxSize(usernameText.getPrefWidth(), usernameText.getPrefHeight());
        registerButton.setTextAlignment(TextAlignment.CENTER);
        registerButton.setPadding(new Insets(12,5,20,5));
        
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Scene temp = makeRegisterScreen();
                theStage.setScene(temp);
            }
        });
        
        Button bookButton = new Button();
        bookButton.setText("Book A Session");
        bookButton.setAlignment(Pos.TOP_CENTER);
        bookButton.setPrefSize(250, 30);
        bookButton.setMaxSize(usernameText.getPrefWidth(), usernameText.getPrefHeight());
        bookButton.setTextAlignment(TextAlignment.CENTER);
        bookButton.setPadding(new Insets(12,5,20,5));
        
        bookButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Scene temp = makeBookingScreen();
                theStage.setScene(temp);
            }
        });
        
        Button checkInButton = new Button();
        checkInButton.setText("Check In A Customer");
        checkInButton.setAlignment(Pos.TOP_CENTER);
        checkInButton.setPrefSize(250, 30);
        checkInButton.setMaxSize(usernameText.getPrefWidth(), usernameText.getPrefHeight());
        checkInButton.setTextAlignment(TextAlignment.CENTER);
        checkInButton.setPadding(new Insets(12,5,20,5));
        
        Button viewScheduleButton = new Button();
        viewScheduleButton.setText("View Schedule");
        viewScheduleButton.setAlignment(Pos.TOP_CENTER);
        viewScheduleButton.setPrefSize(250, 30);
        viewScheduleButton.setMaxSize(usernameText.getPrefWidth(), usernameText.getPrefHeight());
        viewScheduleButton.setTextAlignment(TextAlignment.CENTER);
        viewScheduleButton.setPadding(new Insets(12,5,20,5));
        
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setAlignment(Pos.TOP_CENTER);
        backButton.setPrefSize(250, 30);
        backButton.setMaxSize(usernameText.getPrefWidth(), usernameText.getPrefHeight());
        backButton.setTextAlignment(TextAlignment.CENTER);
        backButton.setPadding(new Insets(12,5,20,5));
        
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Scene temp = makeWelcomeScreen();
                theStage.setScene(temp);
            }
        });
        
        VBox root = new VBox();
        root.getChildren().addAll(slopeOperatorLabel, registerButton, bookButton,
                                  checkInButton, viewScheduleButton, backButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        Scene scene = new Scene(root, 500, 500);
        
        return(scene); 
    }
    
    private Scene makeManagerScreen() {
        VBox root = new VBox();
        Scene scene = new Scene(root, 500, 450); 
        return(scene); 
    }
    
    private Scene makeSkiInstructorScreen() {
        
        Label welcomeText = new Label();
        welcomeText.setText("Welcome to Sphere Booking & Checking In System");
        welcomeText.setAlignment(Pos.TOP_CENTER);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setPadding(new Insets(12,5,20,5));
        
        VBox root = new VBox();
        root.getChildren().addAll(welcomeText);
        
        Scene scene = new Scene(root, 500, 450);
        
        return(scene); 
    }
    
    private Scene makeRegisterScreen() {
        
        Label TitleText = new Label();
        TitleText.setText("Customer Registration");
        TitleText.setAlignment(Pos.TOP_CENTER);
        TitleText.setTextAlignment(TextAlignment.CENTER);
        TitleText.setPadding(new Insets(12,5,20,5));
        
        Label FirstNameLabel = new Label();
        FirstNameLabel.setText("First Name:  ");
        FirstNameLabel.setAlignment(Pos.TOP_CENTER);
        FirstNameLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField FirstNameText = new TextField();
        FirstNameText.setAlignment(Pos.TOP_CENTER);
        
        HBox FirstNameHBox = new HBox();
        FirstNameHBox.getChildren().addAll(FirstNameLabel, FirstNameText);
        FirstNameHBox.setAlignment(Pos.TOP_CENTER);
        
        
        
        Label LastNameLabel = new Label();
        LastNameLabel.setText("Last Name:  ");
        LastNameLabel.setAlignment(Pos.TOP_CENTER);
        LastNameLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField LastNameText = new TextField();
        LastNameText.setAlignment(Pos.TOP_CENTER);
        
        HBox LastNameHBox = new HBox();
        LastNameHBox.getChildren().addAll(LastNameLabel, LastNameText);
        LastNameHBox.setAlignment(Pos.TOP_CENTER);
        
        
        
        Label EmailLabel = new Label();
        EmailLabel.setText("Email:           ");
        EmailLabel.setAlignment(Pos.TOP_CENTER);
        EmailLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField EmailText = new TextField();
        EmailText.setAlignment(Pos.TOP_CENTER);
        
        HBox EmailHBox = new HBox();
        EmailHBox.getChildren().addAll(EmailLabel, EmailText);
        EmailHBox.setAlignment(Pos.TOP_CENTER);
        
        
        
        Label phoneNoLabel = new Label();
        phoneNoLabel.setText("Phone Number:   ");
        phoneNoLabel.setAlignment(Pos.TOP_CENTER);
        phoneNoLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField phoneNoText = new TextField();
        phoneNoText.setAlignment(Pos.TOP_CENTER);
        
        HBox phoneNoHBox = new HBox();
        phoneNoHBox.getChildren().addAll(phoneNoLabel, phoneNoText);
        phoneNoHBox.setAlignment(Pos.TOP_CENTER);
        
        Label MemberLabel = new Label();
        MemberLabel.setText("Membership Type:   ");
        MemberLabel.setAlignment(Pos.TOP_CENTER);
        MemberLabel.setTextAlignment(TextAlignment.CENTER);
        
        ToggleGroup MemToggle = new ToggleGroup();
        
        RadioButton MemberButton = new RadioButton();
        MemberButton.setText("Free Membership   ");
        MemberButton.setAlignment(Pos.TOP_CENTER);
        MemberButton.setTextAlignment(TextAlignment.CENTER);
        
        MemberButton.setToggleGroup(MemToggle);
        
        RadioButton MemberButton2 = new RadioButton();
        MemberButton2.setText("Paid Membership   ");
        MemberButton2.setAlignment(Pos.TOP_CENTER);
        MemberButton2.setTextAlignment(TextAlignment.CENTER);
        
        MemberButton2.setToggleGroup(MemToggle);
        
         MemToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                

            }
        });
        
        HBox MemberHBox = new HBox();
        MemberHBox.getChildren().addAll(MemberLabel, MemberButton, MemberButton2);
        MemberHBox.setAlignment(Pos.TOP_CENTER);
        
        Button ConfirmButton = new Button();
        ConfirmButton.setText("Confirm");
        ConfirmButton.setAlignment(Pos.TOP_CENTER);
        ConfirmButton.setTextAlignment(TextAlignment.CENTER);
        
        ConfirmButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                String theFirstName = FirstNameText.getText();
                String theLastName = LastNameText.getText();
                String theEmail = EmailText.getText();
                String thePhoneNo = phoneNoText.getText();
                String theMembership = chk.getText();
                        
                        
                customerControllerConnection.register(conn, theFirstName, theLastName, theEmail, thePhoneNo, theMembership);
            
            }
        });
        
        
        VBox root = new VBox();
        root.getChildren().addAll(TitleText, FirstNameHBox, LastNameHBox, EmailHBox, phoneNoHBox, MemberHBox, ConfirmButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        Scene scene = new Scene(root, 500, 450);
        
        return(scene); 
    }
    
    private Scene makeBookingScreen() {
        
        Label bookingTitleText = new Label();
        bookingTitleText.setText("BOOKING SCREEN");
        bookingTitleText.setAlignment(Pos.TOP_CENTER);
        bookingTitleText.setTextAlignment(TextAlignment.CENTER);
        
        Label enterCustomerLabel = new Label();
        enterCustomerLabel.setText("Enter Customer ID: ");
        enterCustomerLabel.setAlignment(Pos.TOP_CENTER);
        enterCustomerLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField enterCustomerText = new TextField();
        enterCustomerText.setAlignment(Pos.TOP_CENTER);     
        
        Button checkCustomerButton = new Button();
        checkCustomerButton.setText("Check Customer ID");
        checkCustomerButton.setAlignment(Pos.TOP_CENTER);
        checkCustomerButton.setTextAlignment(TextAlignment.CENTER);
        
        checkCustomerButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                String theCustomerID = enterCustomerText.getText();
                
                boolean isACustomer = customerControllerConnection.checkCustomerID(conn, theCustomerID);
            
                if(isACustomer==true) {
                    
                    customerStatusLabel.setText("Customer is Registered. Continue with Booking.");
                    sessionPickerInfo.setVisible(true);
                }
                else {
                    
                    customerStatusLabel.setText("Customer is not Registered. Unable to complete Booking.");
                }
            }
        });
        
        HBox checkCustomerInfo = new HBox();
        checkCustomerInfo.getChildren().addAll(enterCustomerLabel, enterCustomerText, checkCustomerButton);
        checkCustomerInfo.setAlignment(Pos.TOP_CENTER);

        customerStatusLabel.setText("Check that the Customer is Registered");
        
        HBox customerStatusInfo = new HBox();
        customerStatusInfo.getChildren().add(customerStatusLabel);
        customerStatusInfo.setAlignment(Pos.TOP_CENTER);
        
        VBox totalCustomerInfo = new VBox();
        totalCustomerInfo.getChildren().addAll(checkCustomerInfo, customerStatusInfo);
        totalCustomerInfo.setAlignment(Pos.TOP_CENTER);
        totalCustomerInfo.setSpacing(25);
        totalCustomerInfo.setStyle("-fx-padding: 10;" + 
                                   "-fx-border-style: solid inside;" + 
                                   "-fx-border-width: 2;" +
                                   "-fx-border-insets: 5;" + 
                                   "-fx-border-radius: 5;" + 
                                   "-fx-border-color: blue;");
        
                
        Label sessionTypeLabel = new Label();
        sessionTypeLabel.setText("Choose Session Type: ");        
        sessionTypeLabel.setAlignment(Pos.TOP_CENTER);
        sessionTypeLabel.setTextAlignment(TextAlignment.CENTER);
        
        ToggleGroup sessionTypeToggle = new ToggleGroup();
        
        RadioButton withInstructorRadioButton = new RadioButton();
        withInstructorRadioButton.setText("With Instructor ");
        withInstructorRadioButton.setAlignment(Pos.TOP_CENTER);
        withInstructorRadioButton.setTextAlignment(TextAlignment.CENTER);
        withInstructorRadioButton.setToggleGroup(sessionTypeToggle);
        
        RadioButton withoutInstructorRadioButton = new RadioButton();
        withoutInstructorRadioButton.setText("Without Instructor ");
        withoutInstructorRadioButton.setAlignment(Pos.TOP_CENTER);
        withoutInstructorRadioButton.setTextAlignment(TextAlignment.CENTER);
        withoutInstructorRadioButton.setToggleGroup(sessionTypeToggle);
        
        sessionTypeToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                System.out.println("Selected Radio Button - "+chk.getText());

            }
        });
        
        
        HBox datePickerInfo = new HBox();
        datePickerInfo.getChildren().add(sessionPicker);
        datePickerInfo.setAlignment(Pos.TOP_CENTER);
        
        HBox sessionTypeInfo = new HBox();
        sessionTypeInfo.getChildren().addAll(sessionTypeLabel, withInstructorRadioButton,
                                             withoutInstructorRadioButton);
        sessionTypeInfo.setAlignment(Pos.TOP_CENTER);
        
        Button submitSessionButton = new Button();
        submitSessionButton.setText("Submit");
        
        submitSessionButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                LocalDate theDate = sessionPicker.getValue();
                Toggle theSessionType = sessionTypeToggle.getSelectedToggle();
                        
                sessionControllerConnection.checkDate(conn, theDate, theSessionType);
                availableSessionsInfo.setVisible(true);                
            }
        });
        
        
        HBox submitSessionInfo = new HBox();
        submitSessionInfo.getChildren().add(submitSessionButton);
        submitSessionInfo.setAlignment(Pos.TOP_CENTER);
        
        sessionPickerInfo.getChildren().addAll(datePickerInfo, sessionTypeInfo, submitSessionInfo);
        sessionPickerInfo.setAlignment(Pos.TOP_CENTER);
        sessionPickerInfo.setSpacing(25);
        sessionPickerInfo.setStyle("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" +
                      "-fx-border-insets: 5;" + 
                      "-fx-border-radius: 5;" + 
                      "-fx-border-color: blue;");
        sessionPickerInfo.setVisible(false);
        
        Label availableSessionsLabel = new Label();
        availableSessionsLabel.setText("Avaiable Sessions: ");
        availableSessionsLabel.setAlignment(Pos.TOP_CENTER);
        availableSessionsLabel.setTextAlignment(TextAlignment.CENTER);
        
        ComboBox sessionsDropDown = new ComboBox();
        
        Button submitChosenSessionButton = new Button();
        submitChosenSessionButton.setText("Submit");
        
        submitChosenSessionButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                confirmationInfo.setVisible(true);                
            }
        });
        
        availableSessionsInfo.getChildren().addAll(availableSessionsLabel, sessionsDropDown, submitChosenSessionButton);
        availableSessionsInfo.setAlignment(Pos.TOP_CENTER);
        availableSessionsInfo.setSpacing(25);
        availableSessionsInfo.setStyle("-fx-padding: 10;" + 
                                       "-fx-border-style: solid inside;" + 
                                       "-fx-border-width: 2;" +
                                       "-fx-border-insets: 5;" + 
                                       "-fx-border-radius: 5;" + 
                                       "-fx-border-color: blue;");
        availableSessionsInfo.setVisible(false);
        
        Label bookingCustomer = new Label();
        bookingCustomer.setText("Booking Customer: " + "CUSTOMER ID HERE");
        Label bookingDate = new Label();
        bookingDate.setText("Booking Date: " + "SESSION DATE HERE");
        Label bookingTime = new Label();
        bookingTime.setText("Booking Time: " + "SESSION TIME HERE");
        Label bookingPrice = new Label();
        bookingPrice.setText("Booking Price: " + "SESSION PRICE HERE");
        
        VBox confirmationDetailsInfo = new VBox();
        confirmationDetailsInfo.getChildren().addAll(bookingCustomer, bookingDate, bookingTime, bookingPrice);
        confirmationDetailsInfo.setAlignment(Pos.TOP_LEFT);
        
        Button confirmBookingButton = new Button();
        confirmBookingButton.setText("CONFIRM BOOKING");
        Button cancelBookingButton = new Button();
        cancelBookingButton.setText("CANCEL BOOKING");
        
        confirmBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                String theCustomerID = enterCustomerText.getText();
            
                int theCustomerIDInteger = Integer.parseInt(theCustomerID);
                int theSessionIDInteger = 123;
            
                // Send entered info to controller to run function for book()
                bookingControllerConnection.book(conn, theCustomerIDInteger, theSessionIDInteger);            
            }
        });
        
        HBox finalButtonInfo = new HBox();
        finalButtonInfo.getChildren().addAll(confirmBookingButton, cancelBookingButton);
        finalButtonInfo.setAlignment(Pos.CENTER);
        
        confirmationInfo.getChildren().addAll(confirmationDetailsInfo, finalButtonInfo);
        confirmationInfo.setAlignment(Pos.TOP_CENTER);
        confirmationInfo.setSpacing(25);
        confirmationInfo.setStyle("-fx-padding: 10;" + 
                                       "-fx-border-style: solid inside;" + 
                                       "-fx-border-width: 2;" +
                                       "-fx-border-insets: 5;" + 
                                       "-fx-border-radius: 5;" + 
                                       "-fx-border-color: blue;");
        confirmationInfo.setVisible(false);
        
        
        
        VBox root = new VBox();
        root.getChildren().addAll(bookingTitleText, totalCustomerInfo, sessionPickerInfo, availableSessionsInfo, confirmationInfo);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        Scene scene = new Scene(root, 800, 600);
        
        theStage.show();        
        
        return(scene); 
    }
    
    private Scene makeCheckInScreen() {
        
        Label welcomeText = new Label();
        welcomeText.setText("Welcome to Sphere Booking & Checking In System");
        welcomeText.setAlignment(Pos.TOP_CENTER);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setPadding(new Insets(12,5,20,5));
        
        VBox root = new VBox();
        root.getChildren().addAll(welcomeText);
        
        Scene scene = new Scene(root, 500, 450);
        
        return(scene); 
    }
    
    private Scene makeViewScheduleScreen() {
        
        Label welcomeText = new Label();
        welcomeText.setText("Welcome to Sphere Booking & Checking In System");
        welcomeText.setAlignment(Pos.TOP_CENTER);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setPadding(new Insets(12,5,20,5));
        
        VBox root = new VBox();
        root.getChildren().addAll(welcomeText);
        
        Scene scene = new Scene(root, 500, 450);
        
        return(scene); 
    }
    
    private void makeAddSessionScreen(Stage primaryManagerStage, Connection conn) {
        ManagerUI mui = new ManagerUI(primaryManagerStage,conn);
        
        primaryManagerStage.setScene(mui.setCalendarScene());
        //primaryManagerStage.initModality(Modality.APPLICATION_MODAL);
        primaryManagerStage.show();
        
        //Scene scene = new Scene(root, 500, 450);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        theStage = primaryStage;     
        Scene WelcomeScene = makeWelcomeScreen();
        
        primaryStage.setTitle("Sphere Booking & Checking In System - Provided by InvoTech");
        primaryStage.setScene(WelcomeScene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
       launch(args);      
    }
    
}

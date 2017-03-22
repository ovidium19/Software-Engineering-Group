/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableView;
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
 * @author Genaro Bedenko
 */
public class SphereBookingSystem extends Application {
    
    //-------------------------------------------------------------
    
    // Global attributes used in everyone's functionality (Shared)
    private Stage theStage;
    private Stage managerStage;
    private final static Connection conn = connectDB();
    private BookingController bookingControllerConnection = new BookingController();
    private CustomerController customerControllerConnection = new CustomerController();
    private SessionController sessionControllerConnection = new SessionController();
    private static LoginRepoImpl loginRepoImpl = new LoginRepoImpl();
    private PasswordField passwordText = new PasswordField();
    private TextField usernameText = new TextField();
    //-------------------------------------------------------------
    
    // Global attributes used in Booking a Session (Genaro)
    private TextField firstNameText = new TextField();
    private Label customerStatusLabel = new Label();
    private VBox sessionPickerInfo = new VBox();
    private DatePicker sessionPicker = new DatePicker();
    private HBox availableSessionsInfo = new HBox();
    private HBox confirmationInfo = new HBox();
    private List sessionsListContent = new ArrayList();
    private ComboBox sessionsDropDown = new ComboBox();
    private String theTimeSlot = new String();
    private RadioButton selectedToggle = new RadioButton();
    private TextField enterCustomerText = new TextField();
    
    //-------------------------------------------------------------
    // Global attributes used in Registering a Customer (Rick)
    private RadioButton chk = new RadioButton();

    //-------------------------------------------------------------
    
    // Method to connect to our DB
    // Database is saved locally and is in a different location depending where each user saves it
    final static Connection connectDB(){
        
        String connectionURL = "jdbc:derby://localhost:1527/SphereDB";
        String uName = "admin1";
        String uPass = "admin1";
        
        try {             
                Connection conn = DriverManager.getConnection(connectionURL, uName, uPass);
                System.out.println("Connected to database..."); // Prints this is connection to DB is successful
                return conn;
        } 
        catch (SQLException ex) {             
                System.out.println("Connection failed."); // Prints this is connection to DB failed
                return null;
        }
    }
    
    //-------------------------------------------------------------
    // Below functions are for creating each of the screens that will be in the system
    
    // Creates the user interface for the welcome screen login
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
    
    // Creates the user interface for the slope operator's functions
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
    
    // Creates the user interface for the manager's function
    private Scene makeManagerScreen() {
        VBox root = new VBox();
        Scene scene = new Scene(root, 500, 450); 
        return(scene); 
    }
    
    // Creates the user interface for the ski instructor's function
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
    
    // Creates the user interface for registering a customer
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
    
    // Creates the user interface for booking a session
    private Scene makeBookingScreen() {
        
        // Create title text label
        Label bookingTitleText = new Label();
        bookingTitleText.setText("BOOKING SCREEN");
        bookingTitleText.setAlignment(Pos.TOP_CENTER);
        bookingTitleText.setTextAlignment(TextAlignment.CENTER);
        
        // Create enter customer ID text label
        Label enterCustomerLabel = new Label();
        enterCustomerLabel.setText("Enter Customer ID: ");
        enterCustomerLabel.setAlignment(Pos.TOP_CENTER);
        enterCustomerLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create text field to enter customer ID
        enterCustomerText = new TextField();
        enterCustomerText.setAlignment(Pos.TOP_CENTER);
        
        // Ensure only numbers are entered in the CustomerID Text Field
        enterCustomerText.textProperty().addListener((obs,o,n)->{
            try{
                int a = Integer.parseInt(n);
                if (a<=0){
                    enterCustomerText.setText("");
                }
            }
            catch (NumberFormatException ex){
                enterCustomerText.setText("");
            }
        });
        
        // Create button to submit customer ID
        Button checkCustomerButton = new Button();
        checkCustomerButton.setText("Check Customer ID");
        checkCustomerButton.setAlignment(Pos.TOP_CENTER);
        checkCustomerButton.setTextAlignment(TextAlignment.CENTER);
        
        // When button is pressed, recieves a boolean from the customer controller, to see
        // whether the customer exists or not. If they are registered, rest of the UI is shown
        checkCustomerButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Retrieve the customer ID that was typed in
                String theCustomerID = enterCustomerText.getText(); 
                
                // Return a boolean for whether or not the customer ID is registered or not
                boolean isACustomer = customerControllerConnection.checkCustomerID(conn, theCustomerID);
            
                if(isACustomer==true) {
                // If they are registered, continue with the booking and display next section of the UI
                    
                    customerStatusLabel.setText("Customer is Registered. Continue with Booking.");
                    sessionPickerInfo.setVisible(true);
                }
                else {
                // Otherwise, display text to say that the customer is not registered
                    
                    customerStatusLabel.setText("Customer is not Registered. Unable to complete Booking. Please Register the Customer.");
                }
            }
        });
        
        // Create button to submit customer ID
        Button findCustomerButton = new Button();
        findCustomerButton.setText("Find Customer ID");
        findCustomerButton.setAlignment(Pos.TOP_CENTER);
        findCustomerButton.setTextAlignment(TextAlignment.CENTER);
        
        // Button goes to screen that allows the user to search for a customer's ID
        // based on their email address or telephone number
        findCustomerButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                Scene temp = makeFindCustomerScreen();
                theStage.setScene(temp);
            }
        });
        
        // All above UI elements added to a HBox for layout reasons
        HBox checkCustomerInfo = new HBox();
        checkCustomerInfo.getChildren().addAll(enterCustomerLabel, enterCustomerText, checkCustomerButton, findCustomerButton);
        checkCustomerInfo.setAlignment(Pos.TOP_CENTER);

        // Set the label text to check the customer is registered, this label is changed 
        // depending on result of checkCustomerButton
        customerStatusLabel.setText("Check that the Customer is Registered");
        
        // Label added to a HBox for layout reasons
        HBox customerStatusInfo = new HBox();
        customerStatusInfo.getChildren().add(customerStatusLabel);
        customerStatusInfo.setAlignment(Pos.TOP_CENTER);
        
        // Above HBoxes are added to VBox for layout reasons
        VBox totalCustomerInfo = new VBox();
        totalCustomerInfo.getChildren().addAll(checkCustomerInfo, customerStatusInfo);
        totalCustomerInfo.setAlignment(Pos.TOP_CENTER);
        totalCustomerInfo.setSpacing(25);
        
        // Using a border to clearly show each section of the interface, don't want the
        // user to move to the next section until one is complete, and then it shows 
        // the other sections of the UI
        totalCustomerInfo.setStyle("-fx-padding: 10;" + 
                                   "-fx-border-style: solid inside;" + 
                                   "-fx-border-width: 2;" +
                                   "-fx-border-insets: 5;" + 
                                   "-fx-border-radius: 5;" + 
                                   "-fx-border-color: blue;");
        
        // Create label to ask for session type        
        Label sessionTypeLabel = new Label();
        sessionTypeLabel.setText("Choose Session Type: ");        
        sessionTypeLabel.setAlignment(Pos.TOP_CENTER);
        sessionTypeLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create toggle group for radio buttons to be added to so that they act
        // dependantly of each other, and not like 2 individual radio buttons
        ToggleGroup sessionTypeToggle = new ToggleGroup();
        
        // Create radio button for if they want an instructor
        RadioButton withInstructorRadioButton = new RadioButton();
        withInstructorRadioButton.setText("With Instructor ");
        withInstructorRadioButton.setAlignment(Pos.TOP_CENTER);
        withInstructorRadioButton.setTextAlignment(TextAlignment.CENTER);
        // Add radio button to the toggle group
        withInstructorRadioButton.setToggleGroup(sessionTypeToggle);
        
        // Create radio button for if they don't want an instructor
        RadioButton withoutInstructorRadioButton = new RadioButton();
        withoutInstructorRadioButton.setText("Without Instructor ");
        withoutInstructorRadioButton.setAlignment(Pos.TOP_CENTER);
        withoutInstructorRadioButton.setTextAlignment(TextAlignment.CENTER);
        // Add radio button to the toggle group
        withoutInstructorRadioButton.setToggleGroup(sessionTypeToggle);
        
        // Create listener to save the selected radio button from the toggle group
        sessionTypeToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                selectedToggle = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                
            }
        });
        
        // Create a HBox for the date picker to be saved to
        HBox datePickerInfo = new HBox();
        datePickerInfo.getChildren().add(sessionPicker);
        datePickerInfo.setAlignment(Pos.TOP_CENTER);
        
        // Create a HBox for session type label and radio buttons to be added to
        HBox sessionTypeInfo = new HBox();
        sessionTypeInfo.getChildren().addAll(sessionTypeLabel, withInstructorRadioButton,
                                             withoutInstructorRadioButton);
        sessionTypeInfo.setAlignment(Pos.TOP_CENTER);
        
        // Create button to submit the session type information
        Button submitSessionButton = new Button();
        submitSessionButton.setText("Submit");
        
        // When the button is pressed, it recieves an array of available time slots for
        // the chosen date and session, this is then saved to the drop down menu
        submitSessionButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Save to an attribute the date that the user picked
                LocalDate theDate = sessionPicker.getValue();
                
                // Save to an attribute the radio button that the user picked
                String theSessionType = selectedToggle.getText();
                        
                try {
                    // Sends details to the session controller, which returns a list of timeslots as strings
                    sessionsListContent = sessionControllerConnection.checkDate(conn, theDate, theSessionType);
                } catch (SQLException ex) {
                    Logger.getLogger(SphereBookingSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // List of timeslot strings is converted to an array
                Object[] sessionsArray = sessionsListContent.toArray();
                
                // For every timeslot in the array, add it to the drop down combobox
                for(int i = 0; i < sessionsListContent.size(); i++) {
            
                    sessionsDropDown.getItems().addAll(sessionsArray[i]);
                }
                
                // Display the next part of the UI
                availableSessionsInfo.setVisible(true);                
            }
        });
        
        // Create HBox for layout reasons
        HBox submitSessionInfo = new HBox();
        submitSessionInfo.getChildren().add(submitSessionButton);
        submitSessionInfo.setAlignment(Pos.TOP_CENTER);
        
        // Add above elements into HBox for layout reasons
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
        
        // Create a text label which is shown next to the dropdown
        Label availableSessionsLabel = new Label();
        availableSessionsLabel.setText("Available Sessions: ");
        availableSessionsLabel.setAlignment(Pos.TOP_CENTER);
        availableSessionsLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create button to click when submitting the chosen timeslot
        Button submitChosenSessionButton = new Button();
        submitChosenSessionButton.setText("Submit");
        
        // When this button is pressed, retrieve the chosen timeslot from the dropdown menu
        submitChosenSessionButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Gets the value of the selected item in the drop down
                theTimeSlot = sessionsDropDown.getValue().toString(); 
                
                // After submitting the time slot, display the next part of the UI
                confirmationInfo.setVisible(true);                
            }
        });
        
        // Add above elements to a HBox for layout reasons
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
        
        // Create button for confirming all above details
        Button confirmBookingButton = new Button();
        confirmBookingButton.setText("CONFIRM BOOKING");
        
        // When the confirm button is pressed, the booking information sent to booking controller
        confirmBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Retrieve the customer ID from what the user typed in
                String theCustomerID = enterCustomerText.getText();
                
                // Change the customer ID into an integer so that the controller can use it
                int theCustomerIDInteger = Integer.parseInt(theCustomerID);
                
                // Retrieve the session ID from the timeslot the user picked
                String theSessionID = theTimeSlot.substring(0, 4);
                
                // Change the session ID into an integer so that the controller can use it
                int theSessionIDInteger = Integer.parseInt(theSessionID);
                            
                // Send entered info to controller to run function for book()
                bookingControllerConnection.book(conn, theCustomerIDInteger, theSessionIDInteger);            
            }
        });
        
        // Create a HBox for button to go into
        HBox finalButtonInfo = new HBox();
        finalButtonInfo.getChildren().addAll(confirmBookingButton);
        finalButtonInfo.setAlignment(Pos.CENTER);
        
        // Above button goes into a HBox for layout reasons
        confirmationInfo.getChildren().addAll(finalButtonInfo);
        confirmationInfo.setAlignment(Pos.TOP_CENTER);
        confirmationInfo.setSpacing(25);
        confirmationInfo.setStyle("-fx-padding: 10;" + 
                                       "-fx-border-style: solid inside;" + 
                                       "-fx-border-width: 2;" +
                                       "-fx-border-insets: 5;" + 
                                       "-fx-border-radius: 5;" + 
                                       "-fx-border-color: blue;");
        confirmationInfo.setVisible(false);
        
        
        // All above elements are added to root - which is a VBox so all elements are displayed vertically
        VBox root = new VBox();
        root.getChildren().addAll(bookingTitleText, totalCustomerInfo, sessionPickerInfo, availableSessionsInfo, confirmationInfo);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        // Root is passed as a parameter to create the scene
        Scene scene = new Scene(root, 800, 600);
        
        theStage.show();        
        
        return(scene); 
    }
    
    private Scene makeFindCustomerScreen() {
        
        Label findCustomerIDLabel = new Label();
        findCustomerIDLabel.setText("FIND CUSTOMER ID");
        findCustomerIDLabel.setAlignment(Pos.TOP_CENTER);
        findCustomerIDLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label checkByEmailLabel = new Label();
        checkByEmailLabel.setText("Search for Customer by Email Address: ");
        checkByEmailLabel.setAlignment(Pos.TOP_CENTER);
        checkByEmailLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField checkByEmailTextField = new TextField();
        checkByEmailTextField.setAlignment(Pos.TOP_CENTER);
        
        Button checkByEmailButton = new Button();
        checkByEmailButton.setText("Submit");
        checkByEmailButton.setAlignment(Pos.TOP_CENTER);
        checkByEmailButton.setTextAlignment(TextAlignment.CENTER);
        
        HBox checkByEmailHBox = new HBox();
        checkByEmailHBox.getChildren().addAll(checkByEmailLabel, checkByEmailTextField, checkByEmailButton);
        checkByEmailHBox.setAlignment(Pos.CENTER);
        
        Label checkByPhoneLabel = new Label();
        checkByPhoneLabel.setText("Search for Customer by Telephone Number: ");
        checkByPhoneLabel.setAlignment(Pos.TOP_CENTER);
        checkByPhoneLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField checkByPhoneTextField = new TextField();
        checkByPhoneTextField.setAlignment(Pos.TOP_CENTER);
        
        Button checkByPhoneButton = new Button();
        checkByPhoneButton.setText("Submit");
        checkByPhoneButton.setAlignment(Pos.TOP_CENTER);
        checkByPhoneButton.setTextAlignment(TextAlignment.CENTER);
        
        HBox checkByPhoneHBox = new HBox();
        checkByPhoneHBox.getChildren().addAll(checkByPhoneLabel, checkByPhoneTextField, checkByPhoneButton);
        checkByPhoneHBox.setAlignment(Pos.CENTER);
        
        Label customerStatusLabel = new Label();
        customerStatusLabel.setText("Search for the Customer's ID by their Email or Telephone Number");
        customerStatusLabel.setAlignment(Pos.TOP_CENTER);
        customerStatusLabel.setTextAlignment(TextAlignment.CENTER);
        
        VBox searchBoxesHBox = new VBox();
        searchBoxesHBox.getChildren().addAll(checkByEmailHBox, checkByPhoneHBox, customerStatusLabel);
        searchBoxesHBox.setAlignment(Pos.CENTER);
        searchBoxesHBox.setSpacing(25);
        searchBoxesHBox.setStyle("-fx-padding: 10;" + 
                                       "-fx-border-style: solid inside;" + 
                                       "-fx-border-width: 2;" +
                                       "-fx-border-insets: 5;" + 
                                       "-fx-border-radius: 5;" + 
                                       "-fx-border-color: blue;");
        
        Label customerIDLabel = new Label();
        customerIDLabel.setText("Customer ID:");
        customerIDLabel.setAlignment(Pos.TOP_CENTER);
        customerIDLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label firstNameLabel = new Label();
        firstNameLabel.setText("First Name:");
        firstNameLabel.setAlignment(Pos.TOP_CENTER);
        firstNameLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label lastNameLabel = new Label();
        lastNameLabel.setText("Last Name:");
        lastNameLabel.setAlignment(Pos.TOP_CENTER);
        lastNameLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label emailLabel = new Label();
        emailLabel.setText("Email:");
        emailLabel.setAlignment(Pos.TOP_CENTER);
        emailLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label phoneLabel = new Label();
        phoneLabel.setText("Telephone:");
        phoneLabel.setAlignment(Pos.TOP_CENTER);
        phoneLabel.setTextAlignment(TextAlignment.CENTER);
        
        VBox detailsLabelsVBox = new VBox();
        detailsLabelsVBox.getChildren().addAll(customerIDLabel, firstNameLabel, lastNameLabel, emailLabel, phoneLabel);
        
        Label customerIDShownLabel = new Label();
        customerIDShownLabel.setText("...");
        customerIDShownLabel.setAlignment(Pos.TOP_CENTER);
        customerIDShownLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label firstNameShownLabel = new Label();
        firstNameShownLabel.setText("...");
        firstNameShownLabel.setAlignment(Pos.TOP_CENTER);
        firstNameShownLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label lastNameShownLabel = new Label();
        lastNameShownLabel.setText("...");
        lastNameShownLabel.setAlignment(Pos.TOP_CENTER);
        lastNameShownLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label emailShownLabel = new Label();
        emailShownLabel.setText("...");
        emailShownLabel.setAlignment(Pos.TOP_CENTER);
        emailShownLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label phoneShownLabel = new Label();
        phoneShownLabel.setText("...");
        phoneShownLabel.setAlignment(Pos.TOP_CENTER);
        phoneShownLabel.setTextAlignment(TextAlignment.CENTER);
        
        VBox detailsLabelsShownVBox = new VBox();
        detailsLabelsShownVBox.getChildren().addAll(customerIDShownLabel, firstNameShownLabel, lastNameShownLabel, emailShownLabel, phoneShownLabel);
        
        HBox detailsHBox = new HBox();
        detailsHBox.getChildren().addAll(detailsLabelsVBox, detailsLabelsShownVBox);
        detailsHBox.setAlignment(Pos.CENTER);
        detailsHBox.setSpacing(50);
        detailsHBox.setStyle("-fx-padding: 10;" + 
                                       "-fx-border-style: solid inside;" + 
                                       "-fx-border-width: 2;" +
                                       "-fx-border-insets: 5;" + 
                                       "-fx-border-radius: 5;" + 
                                       "-fx-border-color: blue;");
        
        Button returnToBookingButton = new Button();
        returnToBookingButton.setText("RETURN TO BOOKING SCREEN");
                
        VBox root = new VBox();
        root.getChildren().addAll(findCustomerIDLabel, searchBoxesHBox, detailsHBox, returnToBookingButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        Scene scene = new Scene(root, 800, 600);
        
        return(scene); 
    }
    
    // Creates the user interface for checking in a customer
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
    
    // Creates the user interface for viewing the schedule of sessions
    private Scene makeViewScheduleScreen() {
        
        Label viewScheduleLabel = new Label();
        viewScheduleLabel.setText("View Schedule");
        viewScheduleLabel.setAlignment(Pos.TOP_CENTER);
        viewScheduleLabel.setTextAlignment(TextAlignment.CENTER);
        viewScheduleLabel.setPadding(new Insets(12,5,20,5));
        
        Button mondayButton = new Button();
        mondayButton.setText("Monday");
        mondayButton.setAlignment(Pos.TOP_CENTER);
        mondayButton.setTextAlignment(TextAlignment.CENTER);     
        mondayButton.setPadding(new Insets(12,5,20,5));
        
        mondayButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            }
        });
        
        String monday = "Monday";
        
        TableView table = new TableView();
        
            
        table.getItems().addAll(monday);
            
        
            
            
        
        
        Button tuesdayButton = new Button();
        tuesdayButton.setText("Tuesday");
        tuesdayButton.setAlignment(Pos.TOP_CENTER);
        tuesdayButton.setTextAlignment(TextAlignment.CENTER);     
        tuesdayButton.setPadding(new Insets(12,5,20,5));
        
        Button wednesdayButton = new Button();
        wednesdayButton.setText("Wednesday");
        wednesdayButton.setAlignment(Pos.TOP_CENTER);
        wednesdayButton.setTextAlignment(TextAlignment.CENTER);     
        wednesdayButton.setPadding(new Insets(12,5,20,5));
      
        
        Button thursdayButton = new Button();
        thursdayButton.setText("Thursday");
        thursdayButton.setAlignment(Pos.TOP_CENTER);
        thursdayButton.setTextAlignment(TextAlignment.CENTER);     
        thursdayButton.setPadding(new Insets(12,5,20,5));
        
        Button fridayButton = new Button();
        fridayButton.setText("Friday");
        fridayButton.setAlignment(Pos.TOP_CENTER);
        fridayButton.setTextAlignment(TextAlignment.CENTER);     
        fridayButton.setPadding(new Insets(12,5,20,5));
        
                
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setAlignment(Pos.TOP_RIGHT);
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
        root.getChildren().addAll(viewScheduleLabel, mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, backButton);
        root.getChildren().addAll(mondayButton);
        root.getChildren().addAll(tuesdayButton);
        root.getChildren().addAll(wednesdayButton);
        root.getChildren().addAll(thursdayButton);
        root.getChildren().addAll(fridayButton);
        root.getChildren().addAll(backButton);
        root.getChildren().addAll(TableView);
        
        
        Scene scene = new Scene(root, 500, 450);
        
        return(scene); 
    }
        
    
    // Creates the user interface for adding a session
     private void makeAddSessionScreen(Stage primaryManagerStage, Connection conn) {
        ManagerUI mui = new ManagerUI(primaryManagerStage,conn);
        
        primaryManagerStage.setScene(mui.setCalendarScene());
        //primaryManagerStage.initModality(Modality.APPLICATION_MODAL);
        primaryManagerStage.show();
        
        //Scene scene = new Scene(root, 500, 450);
    }
    
    // Sets up the stage for scenes to be shown in
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
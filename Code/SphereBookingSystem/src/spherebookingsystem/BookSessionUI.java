/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Genaro Bedenko
 */
public class BookSessionUI {
    
    private static Connection conn;
    private static Scene mainScene;    
    private static Stage theStage;
    private Session tempSession;
    
    // Global attributes used in Booking a Session
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
    
    private BookingController bookingControllerConnection = new BookingController();
    private CustomerController customerControllerConnection = new CustomerController();
    private SessionController sessionControllerConnection = new SessionController();
    
    private static BookSessionUI instance = null;
    private BookSessionUI(Stage primaryStage, Connection con){
        
        theStage=primaryStage;
        conn=con;
        tempSession=new Session();
    }
    
    public static BookSessionUI getInstance(Stage stage,Connection con){
        
        if (instance==null){
            instance=new BookSessionUI(stage,con);
        }
        return instance;
    }
    
    
    // Creates the user interface for entering booking details
    public Scene makeBookingScreen() {
        
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
        
        // Label for searching for customer ID
        Label findCustomerLabel = new Label();
        findCustomerLabel.setText("Customer has forgotten their ID? Search for it here:");
        findCustomerLabel.setAlignment(Pos.TOP_CENTER);
        findCustomerLabel.setTextAlignment(TextAlignment.CENTER);
        
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
        
        HBox findCustomerHBox = new HBox();
        findCustomerHBox.getChildren().addAll(findCustomerLabel, findCustomerButton);
        findCustomerHBox.setAlignment(Pos.TOP_CENTER);
        
        // All above UI elements added to a HBox for layout reasons
        HBox checkCustomerInfo = new HBox();
        checkCustomerInfo.getChildren().addAll(enterCustomerLabel, enterCustomerText, checkCustomerButton);
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
        totalCustomerInfo.getChildren().addAll(checkCustomerInfo, customerStatusInfo, findCustomerHBox);
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
        
        // Create label to ask for amount of skiers        
        Label numberOfSkiersLabel = new Label();
        numberOfSkiersLabel.setText("Enter amount of skiers: ");        
        numberOfSkiersLabel.setAlignment(Pos.TOP_CENTER);
        numberOfSkiersLabel.setTextAlignment(TextAlignment.CENTER);
        
        ObservableList<String> numberOfSkiers = FXCollections.observableArrayList();
        
        for(int i=1; i<=100; i++) {
            
            String numberString = Integer.toString(i);
            numberOfSkiers.add(numberString);
        }
              
        final ComboBox numberOfSkiersComboBox = new ComboBox(numberOfSkiers);
        
        HBox numberOfSkiersHBox = new HBox();
        numberOfSkiersHBox.getChildren().addAll(numberOfSkiersLabel, numberOfSkiersComboBox);
        numberOfSkiersHBox.setAlignment(Pos.TOP_CENTER);
        
        // Create label to ask for date        
        Label sessionDateLabel = new Label();
        sessionDateLabel.setText("Choose Date: ");        
        sessionDateLabel.setAlignment(Pos.TOP_CENTER);
        sessionDateLabel.setTextAlignment(TextAlignment.CENTER);
        
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
        datePickerInfo.getChildren().addAll(sessionDateLabel, sessionPicker);
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
        sessionPickerInfo.getChildren().addAll(numberOfSkiersHBox, datePickerInfo, sessionTypeInfo, submitSessionInfo);
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
        confirmBookingButton.setText("PROCEED TO CONFIRMATION");
        
        // When the confirm button is pressed, the booking information sent to booking controller
        confirmBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                             
                Scene temp = confirmationScreen();
                theStage.setScene(temp);
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
        Scene scene = new Scene(root, 800, 700);
        
        theStage.show();        
        
        return(scene); 
    }
    
    // Creates the user interface for searching for the customer's id
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
    
    // Creates the user interface for confirming the booking details
    private Scene confirmationScreen() {
        
        // Create title text label
        Label confirmationTitleText = new Label();
        confirmationTitleText.setText("CONFIRMATION SCREEN");
        confirmationTitleText.setAlignment(Pos.TOP_CENTER);
        confirmationTitleText.setTextAlignment(TextAlignment.CENTER);
        
        Label customerIDText = new Label();
        customerIDText.setText("CUSTOMER ID:");
        customerIDText.setAlignment(Pos.TOP_CENTER);
        customerIDText.setTextAlignment(TextAlignment.RIGHT);
        
        Label sessionIDText = new Label();
        sessionIDText.setText("SESSION ID:");
        sessionIDText.setAlignment(Pos.TOP_CENTER);
        sessionIDText.setTextAlignment(TextAlignment.RIGHT);
        
        Label sessionDateText = new Label();
        sessionDateText.setText("SESSION DATE:");
        sessionDateText.setAlignment(Pos.TOP_CENTER);
        sessionDateText.setTextAlignment(TextAlignment.RIGHT);
        
        Label sessionTimeText = new Label();
        sessionTimeText.setText("SESSION TIME:");
        sessionTimeText.setAlignment(Pos.TOP_CENTER);
        sessionTimeText.setTextAlignment(TextAlignment.RIGHT);
        
        VBox sessionDetailsVBox = new VBox();
        sessionDetailsVBox.getChildren().addAll(customerIDText, sessionIDText, sessionDateText, sessionTimeText);
        sessionDetailsVBox.setAlignment(Pos.TOP_CENTER);
        sessionDetailsVBox.setSpacing(25);
        
        Label sessionPriceText = new Label();
        sessionPriceText.setText("SESSION PRICE:");
        sessionPriceText.setAlignment(Pos.TOP_CENTER);
        sessionPriceText.setTextAlignment(TextAlignment.RIGHT);
        
        Label priceAfterDeductionText = new Label();
        priceAfterDeductionText.setText("PRICE AFTER DISCOUNT (if applicable):");
        priceAfterDeductionText.setAlignment(Pos.TOP_CENTER);
        priceAfterDeductionText.setTextAlignment(TextAlignment.RIGHT);
        
        VBox priceDetailsVBox = new VBox();
        priceDetailsVBox.getChildren().addAll(sessionPriceText, priceAfterDeductionText);
        priceDetailsVBox.setAlignment(Pos.TOP_CENTER);
        priceDetailsVBox.setSpacing(25);                
        
        // All above elements are added to root - which is a VBox so all elements are displayed vertically
        VBox root = new VBox();
        root.getChildren().addAll(confirmationTitleText, sessionDetailsVBox, priceDetailsVBox);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        // Root is passed as a parameter to create the scene
        Scene scene = new Scene(root, 800, 600);
        
        theStage.show();        
        
        return(scene); 
    }
    
    // Creates the user interface for taking payment from customer
    private Scene paymentScreen() {
        
        // Create title text label
        Label confirmationTitleText = new Label();
        confirmationTitleText.setText("PAYMENT SCREEN");
        confirmationTitleText.setAlignment(Pos.TOP_CENTER);
        confirmationTitleText.setTextAlignment(TextAlignment.CENTER);
                
        Label enterPaymentLabel = new Label();
        enterPaymentLabel.setText("Has the Customer paid yet?: ");
        enterPaymentLabel.setAlignment(Pos.TOP_CENTER);
        enterPaymentLabel.setTextAlignment(TextAlignment.CENTER);
                
        
        // All above elements are added to root - which is a VBox so all elements are displayed vertically
        VBox root = new VBox();
        root.getChildren().addAll(confirmationTitleText, enterPaymentLabel);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        // Root is passed as a parameter to create the scene
        Scene scene = new Scene(root, 800, 600);
        
        theStage.show();        
        
        return(scene); 
    }
    
}

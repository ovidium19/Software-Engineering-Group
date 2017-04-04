package spherebookingsystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import javafx.scene.control.DateCell;
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
import javafx.util.Callback;

/**
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
 */
public class BookSessionUI {
    
    // Global attributes for creating an interface
    private static Connection conn;  
    private static Stage theStage;
    private static Stage lastStage = new Stage();
    private static Scene menuScene;
    
    // Global attributes for Labels that will change their text
    private Label customerStatusLabel = new Label();
    private Label numberOfSkiersShownLabel = new Label();
    private Label dateShownLabel = new Label();
    private Label timeSlotShownLabel = new Label();
    private Label sessionTypeShownLabel = new Label();
    private Label sessionPriceShownLabel = new Label();
    private Label priceAfterDeductionShownLabel = new Label();
    private Label customerIDShownLabel = new Label();
    private Label firstNameShownLabel = new Label();
    private Label lastNameShownLabel = new Label();
    private Label emailShownLabel = new Label();
    private Label phoneShownLabel = new Label();
    
    // Global attributes for other GUI elements that will change
    private DatePicker sessionPicker = new DatePicker();
    private List sessionsListContent = new ArrayList();
    private ComboBox sessionsDropDown = new ComboBox();
    private RadioButton selectedSessionToggle = new RadioButton();
    private TextField enterCustomerText = new TextField();    
    private RadioButton selectedPaidStatusToggle = new RadioButton();
    private ComboBox numberOfSkiersComboBox = new ComboBox();
    private LocalDate theSelectedDate;
    
    // Global variables for some VBoxes/HBoxes as they aren't displayed from the start
    private VBox sessionPickerHBox = new VBox();
    private HBox availableSessionsHBox = new HBox();
    private HBox confirmationHBox = new HBox();
     
    // Create a temp customer to hold attributes in whilst booking is being made
    Customer tempCustomer = new Customer();
    
    // Create a temp session to hold attributes in whilst booking is being made
    Session tempSession = new Session();
    
    // Create a temp booking to hold attributes in whilst booking is being made
    Booking tempBooking = new Booking();
        
    // Creating instances of each controller that is used
    private BookingController bookingControllerConnection = new BookingController();
    private CustomerController customerControllerConnection = new CustomerController();
    private SessionController sessionControllerConnection = new SessionController();
    
    // Initialises the class
    public BookSessionUI(Stage primaryStage, Connection con, Scene scene){
                
        theStage=primaryStage;
        conn=con;
        tempSession=new Session();
        menuScene = scene;
    }
    
    // Creates the user interface for entering the booking details
    public Scene makeBookingScreen(Scene lastScene) {
   
        // Create a button to return back to the main menu
        Button returnToMenuButton = new Button();
        returnToMenuButton.setText("RETURN TO MENU");
        returnToMenuButton.setAlignment(Pos.BASELINE_LEFT);
        returnToMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
        
                // Goes to the last scene it came from (i.e. the menu screen)
                theStage.setScene(lastScene);
            }
        });
        
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
                String theCustomerIDString = enterCustomerText.getText();
                
                boolean isACustomer;
                
                if(theCustomerIDString.equals("")) {
                    
                    isACustomer = false;
                }
                else {
                    
                    // Return a boolean for whether or not the customer ID is registered or not
                    isACustomer = customerControllerConnection.checkCustomerID(conn, theCustomerIDString);
                }
                
                if(isACustomer==true) {
                // If they are registered, continue with the booking and display next section of the UI
                    
                    customerStatusLabel.setText("Customer is Registered. Continue with Booking.");
                    sessionPickerHBox.setVisible(true);
                    
                    try {
                        tempCustomer = customerControllerConnection.findCustomer(conn, theCustomerIDString);
                    } catch (SQLException ex) {
                        Logger.getLogger(BookSessionUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if((isACustomer == false) && (theCustomerIDString.equals(""))) {
                // If it returns false, but because they didn't enter anything, then display that this was the reason
                
                    customerStatusLabel.setText("No Customer ID was entered.");
                    sessionPickerHBox.setVisible(false);
                }
                else {
                // Otherwise, display text to say that the customer is not registered
                    
                    customerStatusLabel.setText("Customer is not Registered. Unable to complete Booking. Please Register the Customer.");
                    sessionPickerHBox.setVisible(false);
                }
            }
        });
        
        // Label for searching for customer ID
        Label findCustomerLabel = new Label();
        findCustomerLabel.setText("Customer has forgotten their ID? Search for it here: ");
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
                Scene lastScene=theStage.getScene();
                Scene temp = makeFindCustomerScreen(lastScene);
                theStage.setScene(temp);
            }
        });
        
        // Creates a HBox to add the above elements into
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
        
        // Declare a list of strings for the number of skiers drop down menu
        ObservableList<String> numberOfSkiers = FXCollections.observableArrayList();
        
        for(int i=1; i<=100; i++) {
        // Fills the drop down with the values 1 to 100 (up to 100 skiers in a booking)
        
            String numberString = Integer.toString(i);
            numberOfSkiers.add(numberString);
        }
        
        // Creates the drop down with the list of values
        numberOfSkiersComboBox = new ComboBox(numberOfSkiers);
        
        // Adds the above UI elements to a HBox for layout reasons
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
        // Set this radio button as selected by default so that one has to be chosen
        withInstructorRadioButton.setSelected(true);
        // Add radio button to the toggle group
        withInstructorRadioButton.setToggleGroup(sessionTypeToggle);
        // Get the selected value of the toggle group as the default selected one
        selectedSessionToggle = (RadioButton)sessionTypeToggle.getSelectedToggle();
        
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

                selectedSessionToggle = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                
            }
        });
        
        // For the date picker, do not allow dates before the current date to be picked
        final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                           
                            if (item.isBefore(
                                    LocalDate.now())
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            }   
                    }
                };
            }
        };
        sessionPicker.setDayCellFactory(dayCellFactory);
        
        
        
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
                theSelectedDate = sessionPicker.getValue();
                
                // Save to a temporary booking object the number of skiers
                tempBooking.setNumberOfSkiers(Integer.parseInt(numberOfSkiersComboBox.getValue().toString()));
                                
                try {
                    // Sends details to the session controller, which returns a list of timeslots as strings
                    sessionsListContent = sessionControllerConnection.findSessions(conn, theSelectedDate, selectedSessionToggle.getText(), tempBooking.getNumberOfSkiers());
                } catch (SQLException ex) {
                    Logger.getLogger(SphereBookingSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // List of timeslot strings is converted to an array
                Object[] sessionsArray = sessionsListContent.toArray();
                
                // Clear the session drop down before entering items into it, incase it was
                // filled with a previous list of sessions
                sessionsDropDown.getItems().clear();
                
                for(int i = 0; i < sessionsListContent.size(); i++) {
                // For every timeslot in the array, add it to the drop down combobox    
                    sessionsDropDown.getItems().addAll(sessionsArray[i]);
                }
                
                // Display the next part of the UI
                availableSessionsHBox.setVisible(true);                
            }
        });
        
        // Create HBox for layout reasons
        HBox submitSessionInfo = new HBox();
        submitSessionInfo.getChildren().add(submitSessionButton);
        submitSessionInfo.setAlignment(Pos.TOP_CENTER);
        
        // Add above elements into HBox for layout reasons
        sessionPickerHBox.getChildren().addAll(numberOfSkiersHBox, datePickerInfo, sessionTypeInfo, submitSessionInfo);
        sessionPickerHBox.setAlignment(Pos.TOP_CENTER);
        sessionPickerHBox.setSpacing(25);
        sessionPickerHBox.setStyle("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" +
                      "-fx-border-insets: 5;" + 
                      "-fx-border-radius: 5;" + 
                      "-fx-border-color: blue;");
        sessionPickerHBox.setVisible(false);
        
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
                                
                // After submitting the time slot, display the next part of the UI
                confirmationHBox.setVisible(true);
            }
        });
        
        // Add above elements to a HBox for layout reasons
        availableSessionsHBox.getChildren().addAll(availableSessionsLabel, sessionsDropDown, submitChosenSessionButton);
        availableSessionsHBox.setAlignment(Pos.TOP_CENTER);
        availableSessionsHBox.setSpacing(25);
        availableSessionsHBox.setStyle("-fx-padding: 10;" + 
                                       "-fx-border-style: solid inside;" + 
                                       "-fx-border-width: 2;" +
                                       "-fx-border-insets: 5;" + 
                                       "-fx-border-radius: 5;" + 
                                       "-fx-border-color: blue;");
        availableSessionsHBox.setVisible(false);
        
        // Create button for confirming all above details
        Button confirmBookingButton = new Button();
        confirmBookingButton.setText("PROCEED TO CONFIRMATION");
        
        // When the confirm button is pressed, the booking information sent to booking controller
        confirmBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // From the time slot they have chosen, retrieve the session ID
                String theSessionID = sessionsDropDown.getValue().toString().substring(11, 15);
                                
                try {
                    // Save to a tempSession object the attributes of the chosen session
                    tempSession = sessionControllerConnection.findChosenSession(conn, theSessionID);
                } catch (SQLException ex) {
                    Logger.getLogger(BookSessionUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // Once saved the details of the chosen session, moved to the confirmation screen
                Scene lastScene=theStage.getScene();
                Scene temp = makeConfirmationScreen(lastScene);
                theStage.setScene(temp);
            }
        });
                
        // Above button goes into a HBox for layout reasons, and also because it is used to hide the button
        // until it is supposed to be shown by setting the HBox to be visible
        confirmationHBox.getChildren().addAll(confirmBookingButton);
        confirmationHBox.setAlignment(Pos.TOP_CENTER);
        confirmationHBox.setSpacing(25);
        confirmationHBox.setVisible(false);
        
        
        // All above elements are added to root - which is a VBox so all elements are displayed vertically
        VBox root = new VBox();
        root.getChildren().addAll(bookingTitleText, totalCustomerInfo, sessionPickerHBox, availableSessionsHBox, confirmationHBox, returnToMenuButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        // Root is passed as a parameter to create the scene
        Scene scene = new Scene(root, 800, 700);
        
        theStage.show();        
        
        return(scene); 
    }
    
    // Creates the user interface for searching for the customer's id if they don't know it
    private Scene makeFindCustomerScreen(Scene lastScene) {
        
        // Create find customer ID label
        Label findCustomerIDLabel = new Label();
        findCustomerIDLabel.setText("FIND CUSTOMER ID");
        findCustomerIDLabel.setAlignment(Pos.TOP_CENTER);
        findCustomerIDLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create check by email label
        Label checkByEmailLabel = new Label();
        checkByEmailLabel.setText("Search for Customer by Email Address: ");
        checkByEmailLabel.setAlignment(Pos.TOP_CENTER);
        checkByEmailLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create a text field to search by email address
        TextField checkByEmailTextField = new TextField();
        checkByEmailTextField.setAlignment(Pos.TOP_CENTER);
        
        // Create a button to submit the entered email address
        Button checkByEmailButton = new Button();
        checkByEmailButton.setText("Submit");
        checkByEmailButton.setAlignment(Pos.TOP_CENTER);
        checkByEmailButton.setTextAlignment(TextAlignment.CENTER);
        
        // When the button is pressed, check to see who the customer is
        checkByEmailButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Retrieve the customer ID that was typed in
                String theEmail = checkByEmailTextField.getText();
                
                try {
                    
                    // Call the controller to find the customer by email, and save the attributes to 
                    // a temporary customer object
                    tempCustomer = customerControllerConnection.findCustomerByEmail(conn, theEmail);
                    
                    // Convert the customer ID from the customer object to a string so it can be displayed
                    String customerIDText = Integer.toString(tempCustomer.getCustomerID());     
                    
                    // Display the customer ID to them in the shown label
                    customerIDShownLabel.setText(customerIDText);
                    customerIDShownLabel.setAlignment(Pos.TOP_CENTER);
                    customerIDShownLabel.setTextAlignment(TextAlignment.CENTER);
                
                    // Display the first name to them in the shown label
                    firstNameShownLabel.setText(tempCustomer.getFirstName());
                    firstNameShownLabel.setAlignment(Pos.TOP_CENTER);
                    firstNameShownLabel.setTextAlignment(TextAlignment.CENTER);
        
                    // Display the last name to them in the shown label
                    lastNameShownLabel.setText(tempCustomer.getLastName());
                    lastNameShownLabel.setAlignment(Pos.TOP_CENTER);
                    lastNameShownLabel.setTextAlignment(TextAlignment.CENTER);
        
                    // Display the email address to them in the shown label
                    emailShownLabel.setText(tempCustomer.getEmail());
                    emailShownLabel.setAlignment(Pos.TOP_CENTER);
                    emailShownLabel.setTextAlignment(TextAlignment.CENTER);
                         
                    // Display the phone number to them in the shown label
                    phoneShownLabel.setText(tempCustomer.getTelephoneNo());
                    phoneShownLabel.setAlignment(Pos.TOP_CENTER);
                    phoneShownLabel.setTextAlignment(TextAlignment.CENTER);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(BookSessionUI.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
        });
        
        // Add the above UI elements to a HBox for layout reasons
        HBox checkByEmailHBox = new HBox();
        checkByEmailHBox.getChildren().addAll(checkByEmailLabel, checkByEmailTextField, checkByEmailButton);
        checkByEmailHBox.setAlignment(Pos.CENTER);
        
        // Create label for checking by phone
        Label checkByPhoneLabel = new Label();
        checkByPhoneLabel.setText("Search for Customer by Telephone Number: ");
        checkByPhoneLabel.setAlignment(Pos.TOP_CENTER);
        checkByPhoneLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create a text field to check by phone number
        TextField checkByPhoneTextField = new TextField();
        checkByPhoneTextField.setAlignment(Pos.TOP_CENTER);
        // Only allow numbers to be entered into the text field
        checkByPhoneTextField.textProperty().addListener((obs,o,n)->{
            try{
                Long.parseLong(n);                
            }
            catch (NumberFormatException ex){
                checkByPhoneTextField.clear();
            }
        });
        
        // Create button to submit the phone number
        Button checkByPhoneButton = new Button();
        checkByPhoneButton.setText("Submit");
        checkByPhoneButton.setAlignment(Pos.TOP_CENTER);
        checkByPhoneButton.setTextAlignment(TextAlignment.CENTER);
        
        // When the button is pressed, check for the customer by the entered phone number
        checkByPhoneButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Retrieve the customer ID that was typed in
                String thePhone = checkByPhoneTextField.getText();
                
                try {
                    // Save the attributes of the found customer to a temporary customer object
                    tempCustomer = customerControllerConnection.findCustomerByPhone(conn, thePhone);
                    
                    // Convert the found customer ID to a string so it can be displayed
                    String customerIDText = Integer.toString(tempCustomer.getCustomerID());
                    
                    // Display the customer ID to them in the shown label
                    customerIDShownLabel.setText(customerIDText);
                    customerIDShownLabel.setAlignment(Pos.TOP_CENTER);
                    customerIDShownLabel.setTextAlignment(TextAlignment.CENTER);
                
                    // Display the first name to them in the shown label
                    firstNameShownLabel.setText(tempCustomer.getFirstName());
                    firstNameShownLabel.setAlignment(Pos.TOP_CENTER);
                    firstNameShownLabel.setTextAlignment(TextAlignment.CENTER);
        
                    // Display the last name to them in the shown label
                    lastNameShownLabel.setText(tempCustomer.getLastName());
                    lastNameShownLabel.setAlignment(Pos.TOP_CENTER);
                    lastNameShownLabel.setTextAlignment(TextAlignment.CENTER);
        
                    // Display the email to them in the shown label
                    emailShownLabel.setText(tempCustomer.getEmail());
                    emailShownLabel.setAlignment(Pos.TOP_CENTER);
                    emailShownLabel.setTextAlignment(TextAlignment.CENTER);
                        
                    // Display the phone number to them in the shown label
                    phoneShownLabel.setText(tempCustomer.getTelephoneNo());
                    phoneShownLabel.setAlignment(Pos.TOP_CENTER);
                    phoneShownLabel.setTextAlignment(TextAlignment.CENTER);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(BookSessionUI.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
        });
        
        // Add the above UI elements to a HBox for layout reasons
        HBox checkByPhoneHBox = new HBox();
        checkByPhoneHBox.getChildren().addAll(checkByPhoneLabel, checkByPhoneTextField, checkByPhoneButton);
        checkByPhoneHBox.setAlignment(Pos.CENTER);
        
        // Create a label to tell the user what the screen is for
        Label customerStatusLabel = new Label();
        customerStatusLabel.setText("Search for the Customer's ID by their Email or Telephone Number");
        customerStatusLabel.setAlignment(Pos.TOP_CENTER);
        customerStatusLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Add all of the above elements to a VBox to display them vertically
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
        
        // Create a label for the customer id
        Label customerIDLabel = new Label();
        customerIDLabel.setText("Customer ID:");
        customerIDLabel.setAlignment(Pos.TOP_CENTER);
        customerIDLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create a label for the first name
        Label firstNameLabel = new Label();
        firstNameLabel.setText("First Name:");
        firstNameLabel.setAlignment(Pos.TOP_CENTER);
        firstNameLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create a label for the last name
        Label lastNameLabel = new Label();
        lastNameLabel.setText("Last Name:");
        lastNameLabel.setAlignment(Pos.TOP_CENTER);
        lastNameLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create a label for the email
        Label emailLabel = new Label();
        emailLabel.setText("Email:");
        emailLabel.setAlignment(Pos.TOP_CENTER);
        emailLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create a label for the phone number
        Label phoneLabel = new Label();
        phoneLabel.setText("Telephone:");
        phoneLabel.setAlignment(Pos.TOP_CENTER);
        phoneLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Add the above labels to a VBox for layout reasons
        VBox detailsLabelsVBox = new VBox();
        detailsLabelsVBox.getChildren().addAll(customerIDLabel, firstNameLabel, lastNameLabel, emailLabel, phoneLabel);
        
        
        // Add all of the details shown labels to a VBox 
        VBox detailsLabelsShownVBox = new VBox();
        detailsLabelsShownVBox.getChildren().addAll(customerIDShownLabel, firstNameShownLabel, lastNameShownLabel, emailShownLabel, phoneShownLabel);
        
        // The normal labels and details shown labels are added to a HBox so they are side by side
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
        
        // Once the user has found the customer's information, use this button to
        // go back to the booking screen
        Button returnToBookingButton = new Button();
        returnToBookingButton.setText("RETURN TO BOOKING SCREEN");
               
        returnToBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Go back to the previous screen (i.e. the booking screen)
                theStage.setScene(lastScene);
            }
        });
        
        // Add everything to the root VBox to display everything in the window                
        VBox root = new VBox();
        root.getChildren().addAll(findCustomerIDLabel, searchBoxesHBox, detailsHBox, returnToBookingButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        Scene scene = new Scene(root, 800, 700);
        
        return(scene); 
    }
    
    // Creates the user interface for confirming the booking details
    private Scene makeConfirmationScreen(Scene lastScene) {
        
        // Create title text label
        Label confirmationTitleText = new Label();
        confirmationTitleText.setText("CONFIRMATION SCREEN");
        confirmationTitleText.setAlignment(Pos.TOP_CENTER);
        confirmationTitleText.setTextAlignment(TextAlignment.CENTER);
        
        // Create a back button for returning to entering the booking information
        Button returnToBookingButton = new Button();
        returnToBookingButton.setText("RETURN TO BOOKING SCREEN");
        returnToBookingButton.setAlignment(Pos.BASELINE_LEFT);
        returnToBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Button will go back to the previous screen (i.e. entering the booking info)
                theStage.setScene(lastScene);
            }
        });
                
        // Label to show where customer id will be
        Label customerIDText = new Label();
        customerIDText.setText("CUSTOMER ID:");
        customerIDText.setAlignment(Pos.TOP_CENTER);
        customerIDText.setTextAlignment(TextAlignment.RIGHT);
        
        // Label to show where number of skiers will be
        Label numberOfSkiersText = new Label();
        numberOfSkiersText.setText("NUMBER OF SKIERS:");
        numberOfSkiersText.setAlignment(Pos.TOP_CENTER);
        numberOfSkiersText.setTextAlignment(TextAlignment.RIGHT);
        
        // Label to show where session date will be
        Label sessionDateText = new Label();
        sessionDateText.setText("SESSION DATE:");
        sessionDateText.setAlignment(Pos.TOP_CENTER);
        sessionDateText.setTextAlignment(TextAlignment.RIGHT);
        
        // Label to show where session time will be
        Label sessionTimeText = new Label();
        sessionTimeText.setText("SESSION TIME:");
        sessionTimeText.setAlignment(Pos.TOP_CENTER);
        sessionTimeText.setTextAlignment(TextAlignment.RIGHT);
        
        // Label to show where the session type will be
        Label sessionTypeText = new Label();
        sessionTypeText.setText("SESSION TYPE:");
        sessionTypeText.setAlignment(Pos.TOP_CENTER);
        sessionTypeText.setTextAlignment(TextAlignment.RIGHT);
        
        // All of the above labels are added to a VBox for layout reasons
        VBox sessionDetailsVBox = new VBox();
        sessionDetailsVBox.getChildren().addAll(customerIDText, numberOfSkiersText, sessionDateText, sessionTimeText, sessionTypeText);
        sessionDetailsVBox.setAlignment(Pos.TOP_CENTER);
        sessionDetailsVBox.setSpacing(25);
        
        
        
        // Confirm the customer ID by displaying it from the tempCustomer
        customerIDShownLabel.setText(Integer.toString(tempCustomer.getCustomerID()));
        customerIDShownLabel.setAlignment(Pos.TOP_CENTER);
        customerIDShownLabel.setTextAlignment(TextAlignment.RIGHT);
            
        // Confirm the number of skiers by displaying it from the tempBooking
        numberOfSkiersShownLabel.setText(Integer.toString(tempBooking.getNumberOfSkiers()));
        numberOfSkiersShownLabel.setAlignment(Pos.TOP_CENTER);
        numberOfSkiersShownLabel.setTextAlignment(TextAlignment.RIGHT);
        
        // Confirm the chosen date by displaying it from the tempSession
        // Also converts it to a certain date format according to the date formatter used
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        String theDateShown = tempSession.getDate().format(formatter);
        dateShownLabel.setText(theDateShown);
        dateShownLabel.setAlignment(Pos.TOP_CENTER);
        dateShownLabel.setTextAlignment(TextAlignment.RIGHT);
        
        // Confirm the session type by displaying it from what the user selected
        // This can't take it from a temporary object because session doesn't store the
        // session type - it store the instructor name
        sessionTypeShownLabel.setText(selectedSessionToggle.getText());
        sessionTypeShownLabel.setAlignment(Pos.TOP_CENTER);
        sessionTypeShownLabel.setTextAlignment(TextAlignment.RIGHT);
        
        // Confirm the time slot by displaying the start time and end time from the
        // tempSession object
        timeSlotShownLabel.setText(tempSession.getStartTime() + " - " + tempSession.getEndTime());
        timeSlotShownLabel.setAlignment(Pos.TOP_CENTER);
        timeSlotShownLabel.setTextAlignment(TextAlignment.RIGHT);        
        
        // Add the above shown labels to a VBox for layout reasons
        VBox sessionDetailsShownVBox = new VBox();
        sessionDetailsShownVBox.getChildren().addAll(customerIDShownLabel, numberOfSkiersShownLabel,
                                                     dateShownLabel,timeSlotShownLabel, sessionTypeShownLabel);
        sessionDetailsShownVBox.setAlignment(Pos.TOP_CENTER);
        sessionDetailsShownVBox.setSpacing(25);
        
        // Add the above VBoxes to one HBox
        HBox sessionDetailsTotal = new HBox();
        sessionDetailsTotal.getChildren().addAll(sessionDetailsVBox, sessionDetailsShownVBox);
        sessionDetailsTotal.setAlignment(Pos.TOP_CENTER);
        sessionDetailsTotal.setSpacing(25);
        sessionDetailsTotal.setStyle("-fx-padding: 10;" + 
                                   "-fx-border-style: solid inside;" + 
                                   "-fx-border-width: 2;" +
                                   "-fx-border-insets: 5;" + 
                                   "-fx-border-radius: 5;" + 
                                   "-fx-border-color: blue;");     
        
        // Create a label for session price
        Label sessionPriceText = new Label();
        sessionPriceText.setText("SESSION PRICE:");
        sessionPriceText.setAlignment(Pos.TOP_CENTER);
        sessionPriceText.setTextAlignment(TextAlignment.RIGHT);
        
        // Create a label for booking price (price after deducitoin if applicable)
        Label priceAfterDeductionText = new Label();
        priceAfterDeductionText.setText("PRICE AFTER DISCOUNT (if applicable):");
        priceAfterDeductionText.setAlignment(Pos.TOP_CENTER);
        priceAfterDeductionText.setTextAlignment(TextAlignment.RIGHT);
        
        // Add the above labels to a VBox for layout reasons
        VBox priceDetailsVBox = new VBox();
        priceDetailsVBox.getChildren().addAll(sessionPriceText, priceAfterDeductionText);
        priceDetailsVBox.setAlignment(Pos.TOP_CENTER);
        priceDetailsVBox.setSpacing(25);
        
        // Display the full session price by taking the price from the tempSession object
        // and multiplying it by the number of skiers in the tempBooking object
        sessionPriceShownLabel.setText("£" + Float.toString(tempSession.getPrice() * tempBooking.getNumberOfSkiers()) + " (£" + tempSession.getPrice() + " per person)");
        
        if(tempCustomer.getMembership().equals("Free Membership")) {
        // If the customer is a free member, don't apply any discount
            
            // Set the total booking price with no discount
            tempBooking.setBookingPrice(((tempSession.getPrice() * tempBooking.getNumberOfSkiers()) * 1));
            // Display the booking price and confirm that no discount was used
            priceAfterDeductionShownLabel.setText("£" + Float.toString(tempBooking.getBookingPrice()) + " (NO DISCOUNT APPLIED/BASIC MEMBERSHIP)");
        }
        else if(tempCustomer.getMembership().equals("Paid Membership")) {
        // If the customer is a paid member, apply any discount
        
            // Set the total booking price with a discount of 20% off (i.e. 80% of whatever the price was)
            tempBooking.setBookingPrice((float) ((tempSession.getPrice() * tempBooking.getNumberOfSkiers()) * 0.8));
            // Display the booking price and confirm that the discount was used
            priceAfterDeductionShownLabel.setText("£" + Float.toString(tempBooking.getBookingPrice()) + " (20% OFF DISCOUNT APPLIED/LOYAL MEMBERSHIP)");
        }
        else {
            
            // Previously, the membership type was stored incorrectly (had loads of empty spaces after the string)
            // so this check is here to see if this error occurs
            System.out.println("Something went wrong.. Probably that the membership type was stored wrong");
        }
        
        // Add the above price labels to a VBox
        VBox priceDetailsShownVBox = new VBox();
        priceDetailsShownVBox.getChildren().addAll(sessionPriceShownLabel, priceAfterDeductionShownLabel);
        priceDetailsShownVBox.setAlignment(Pos.TOP_CENTER);
        priceDetailsShownVBox.setSpacing(25);
        
        // Add the normal labels and price details shown labels to a HBox
        HBox priceDetailsTotal = new HBox();
        priceDetailsTotal.getChildren().addAll(priceDetailsVBox, priceDetailsShownVBox);
        priceDetailsTotal.setAlignment(Pos.TOP_CENTER);
        priceDetailsTotal.setSpacing(25);
        priceDetailsTotal.setStyle("-fx-padding: 10;" + 
                                   "-fx-border-style: solid inside;" + 
                                   "-fx-border-width: 2;" +
                                   "-fx-border-insets: 5;" + 
                                   "-fx-border-radius: 5;" + 
                                   "-fx-border-color: blue;");
        
        // Create a button to proceed to payment
        Button proceedToPaymentButton = new Button();
        proceedToPaymentButton.setText("PROCEED TO PAYMENT");
        
        proceedToPaymentButton.setOnAction(new EventHandler<ActionEvent>() {
               
              @Override
              public void handle(ActionEvent event) {
                  
                // Once the user has confirmed all the above details in this screen,
                // they move onto the payment screen
                Scene lastScene=theStage.getScene();
                Scene temp = makePaymentScreen(lastScene);
                theStage.setScene(temp);
            }
        });
        
        
        // All above elements are added to root - which is a VBox so all elements are displayed vertically
        VBox root = new VBox();
        root.getChildren().addAll(confirmationTitleText, sessionDetailsTotal, priceDetailsTotal, proceedToPaymentButton, returnToBookingButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        // Root is passed as a parameter to create the scene
        Scene scene = new Scene(root, 800, 700);
        
        theStage.show();        
        
        return(scene); 
    }
    
    // Creates the user interface for taking payment from customer
    private Scene makePaymentScreen(Scene lastScene) {
        
        // Create a button for returning to the previous screen
        Button returnToConfirmationButton = new Button();
        returnToConfirmationButton.setText("RETURN TO CONFIRMATION SCREEN");
        returnToConfirmationButton.setAlignment(Pos.BASELINE_LEFT);
        returnToConfirmationButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                // Goes back to the confirmation screen
                theStage.setScene(lastScene);
            }
        });
        
        // Create title text label
        Label confirmationTitleText = new Label();
        confirmationTitleText.setText("PAYMENT SCREEN");
        confirmationTitleText.setAlignment(Pos.TOP_CENTER);
        confirmationTitleText.setTextAlignment(TextAlignment.CENTER);
              
        // Create label to show the user what this screen is for
        Label enterPaymentLabel = new Label();
        enterPaymentLabel.setText("Have you taken payment from the customer yet?");
        enterPaymentLabel.setAlignment(Pos.TOP_CENTER);
        enterPaymentLabel.setTextAlignment(TextAlignment.CENTER);
        
        // Create toggle group for radio buttons to be added to so that they act
        // dependantly of each other, and not like 2 individual radio buttons
        ToggleGroup paidStatusToggle = new ToggleGroup();
        
        // Create a radio button for if the customer has paid for the booking
        // Only needs to say if they have paid, not actually take payment in any way
        RadioButton yesRadioButton = new RadioButton();
        yesRadioButton.setText("Yes");
        yesRadioButton.setAlignment(Pos.TOP_CENTER);
        yesRadioButton.setTextAlignment(TextAlignment.CENTER);
        // Set this radio button as selected by default so that one has to be chosen
        yesRadioButton.setSelected(true);
        // Add radio button to the toggle group
        yesRadioButton.setToggleGroup(paidStatusToggle);
        // Get the selected value of the toggle group as the default selected one
        selectedPaidStatusToggle = (RadioButton)paidStatusToggle.getSelectedToggle();
        
        // Create a radio button for if the customer has not paid for the booking yet
        RadioButton noRadioButton = new RadioButton();
        noRadioButton.setText("No");
        noRadioButton.setAlignment(Pos.TOP_CENTER);
        noRadioButton.setTextAlignment(TextAlignment.CENTER);
        // Add radio button to the toggle group
        noRadioButton.setToggleGroup(paidStatusToggle);
        
        // Add the above UI elements to a VBox for layout reasons
        VBox paymentVBox = new VBox();
        paymentVBox.getChildren().addAll(enterPaymentLabel, yesRadioButton, noRadioButton);
        paymentVBox.setAlignment(Pos.TOP_CENTER);
        paymentVBox.setSpacing(25);
        paymentVBox.setStyle("-fx-padding: 10;" + 
                                   "-fx-border-style: solid inside;" + 
                                   "-fx-border-width: 2;" +
                                   "-fx-border-insets: 5;" + 
                                   "-fx-border-radius: 5;" + 
                                   "-fx-border-color: blue;");
        
        // Create listener to save the selected radio button from the toggle group
        paidStatusToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                selectedPaidStatusToggle = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                
            }
        });
          
        // Create final button to create the booking
        Button createBookingButton = new Button();
        createBookingButton.setText("CREATE BOOKING");
        
        createBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                if(selectedPaidStatusToggle.getText().equals("Yes")) {
                // If the customer has paid, set the tempBooking value for paid status to true
              
                    tempBooking.setCustomerPaidStatus(true);
                }
                else if(selectedPaidStatusToggle.getText().equals("No")) {
                // If the customer has not paid, set the tempBooking value for paid status to false
                
                    tempBooking.setCustomerPaidStatus(false);
                }
                else {
                // Just to catch any errors for if somehow a value isn't selected (was a previous error)
                    System.out.println("Something went wrong with the chosen paid status toggle...");
                }
                
                // Now all the attributes have been taken, and the user has clicked to
                // create the booking - call the controller to book with all the retrieved values
                bookingControllerConnection.book(conn,
                                                 tempCustomer.getCustomerID(),
                                                 tempSession.getId(),
                                                 tempBooking.getBookingPrice(),
                                                 tempBooking.getCustomerPaidStatus(),
                                                 tempBooking.getNumberOfSkiers());
                
                // Creates a scene for a different stage so that it can show a confirmation popup
                Scene temp = makeFinalPopUpScreen();
                lastStage.setScene(temp);
                lastStage.show();
            }
        });
        
        // All above elements are added to root - which is a VBox so all elements are displayed vertically
        VBox root = new VBox();
        root.getChildren().addAll(confirmationTitleText, paymentVBox, createBookingButton, returnToConfirmationButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        // Root is passed as a parameter to create the scene
        Scene scene = new Scene(root, 800, 700);
        
        theStage.show();        
        
        return(scene); 
    }
    
    private Scene makeFinalPopUpScreen() {
        
        // Create a label for a confirmation message
        Label confirmationLabel = new Label();
        confirmationLabel.setText("Booking Created.");
        confirmationLabel.setTextAlignment(TextAlignment.CENTER);
        confirmationLabel.setAlignment(Pos.CENTER);
        
        // Create an ok button to close the popup window
        Button okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                // Button closes this stage and in the other stage goes back to the menu
                lastStage.close();
                theStage.setScene(menuScene);
            }
        });
        
        // Add of the above UI elements to root VBox
        VBox root = new VBox();
        root.getChildren().addAll(confirmationLabel, okButton);
        root.setPadding(new Insets(25,25,25,25));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        // Root is passed as a parameter to create the scene
        Scene scene = new Scene(root, 200, 100);
               
        return(scene);
    }
    
}
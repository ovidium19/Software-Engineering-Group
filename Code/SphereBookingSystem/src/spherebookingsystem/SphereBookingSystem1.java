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
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private final static Connection conn = connectDB();
    private BookingController bookingControllerConnection = new BookingController();
    private CustomerController customerControllerConnection = new CustomerController();
    private SessionController sessionControllerConnection = new SessionController();
    private static LoginRepoImpl loginRepoImpl = new LoginRepoImpl();
    private PasswordField passwordText = new PasswordField();
    private TextField usernameText = new TextField();
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
    
    private HBox makeTopBar(String funcName){
        HBox root=new HBox();
        root.setId("titleBar");
        Image logo=new Image("file:src/logo.png",100,65,true,true);
        ImageView logoView=new ImageView(logo);
        logoView.setFitHeight(65);
        logoView.setFitWidth(100);
        Label func=new Label(funcName);
        func.setId("titleLabel");
        func.setPrefWidth(200);
        Region empty1 = new Region();
        empty1.setPrefWidth(200);
        root.getChildren().addAll(logoView,empty1,func);
        root.setPrefHeight(80);
        root.setMaxHeight(100);
        root.setPadding(new Insets(0,0,20,0));
        return root;
        
    }
    
    // Creates the user interface for the welcome screen login
    Scene makeWelcomeScreen(){
        
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
                    makeSlopeOperatorScreen(theStage, conn);                   
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
        BorderPane root = new BorderPane();
        HBox titleBar=makeTopBar("Welcome");
        root.setTop(titleBar);
        BorderPane.setMargin(titleBar, new Insets(5,0,10,5));
        VBox rootCenter = new VBox();
        rootCenter.getChildren().addAll(welcomeText, userInfo, passwordInfo, submitButton,errorMessage);
        rootCenter.setPadding(new Insets(0,50,50,50));
        rootCenter.setAlignment(Pos.TOP_CENTER);
        rootCenter.setSpacing(25);
        root.setCenter(rootCenter);
        Scene scene = new Scene(root,500,500);
        scene.getStylesheets().add(getClass().getResource("welcomeScreen.css").toExternalForm());
        
        return scene;
    }
    
    // Creates the user interface for the slope operator's functions
    private void makeSlopeOperatorScreen(Stage primarySlopeOperatorStage, Connection conn) {
        //Singleton pattern implemented here, therfore to get the instance of
        //the manager UI, we have to call its public getInstance method.
        
        SlopeOperatorUI slopeoperatorui = SlopeOperatorUI.getInstance(primarySlopeOperatorStage, conn);
        
        primarySlopeOperatorStage.setScene(slopeoperatorui.setMenuScene());
        
        primarySlopeOperatorStage.show();          
    }
    
    // Creates the user interface for the manager's function
     private void makeAddSessionScreen(Stage primaryManagerStage, Connection conn) {
        //Singleton pattern implemented here, therfore to get the instance of
        //the manager UI, we have to call its public getInstance method.
        ManagerUI mui = ManagerUI.getInstance(primaryManagerStage, conn);
        
        primaryManagerStage.setScene(mui.setCalendarScene());
        
        primaryManagerStage.show();
        
        
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Genaro
 */
public class SphereBookingSystem extends Application {
    
    private Stage theStage;
    
    private TextField firstNameText = new TextField();
    
    private PasswordField passwordText = new PasswordField();
    private TextField usernameText = new TextField();
    
    
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
        
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Scene temp = makeSlopeOperatorScreen();
                theStage.setScene(temp);
            }
        });
        
        VBox userInfo = new VBox();
        userInfo.getChildren().addAll(usernameLabel, usernameText);
        userInfo.setAlignment(Pos.TOP_CENTER);
        
        VBox passwordInfo = new VBox();
        passwordInfo.getChildren().addAll(passwordLabel, passwordText);
        passwordInfo.setAlignment(Pos.TOP_CENTER);
        
        VBox root = new VBox();
        root.getChildren().addAll(welcomeText, userInfo, passwordInfo, submitButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        Scene scene = new Scene(root,500,450);
        
        return scene;
    }
    
    private Scene makeSlopeOperatorScreen() {
        
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
    
    private Scene makeManagerScreen() {
        
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
    
    private Scene makeBookingScreen() {
        
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
    
    private Scene makeAddSessionScreen() {
        
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

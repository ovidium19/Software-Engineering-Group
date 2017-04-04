/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Genaro
 */
public class SlopeOperatorUI {
    
    private static Connection conn;   
    private static Stage theStage;
    private Session tempSession;
    
    private static SlopeOperatorUI instance = null;
    private SlopeOperatorUI(Stage primaryStage, Connection con){
        
        theStage=primaryStage;
        conn=con;
        tempSession=new Session();
    }
    
    public static SlopeOperatorUI getInstance(Stage stage,Connection con){
        
        if (instance==null){
            instance=new SlopeOperatorUI(stage,con);
        }
        return instance;
    }
    
    public Scene setMenuScene() {
        
        Label slopeOperatorLabel = new Label();
        slopeOperatorLabel.setText("SLOPE OPERATOR FUNCTIONS");
        
        Button registerButton = new Button();
        registerButton.setText("Register A Customer");
        registerButton.setAlignment(Pos.TOP_CENTER);
        registerButton.setPrefSize(250, 30);
        registerButton.setMaxSize(registerButton.getPrefWidth(), registerButton.getPrefHeight());
        registerButton.setTextAlignment(TextAlignment.CENTER);
        registerButton.setPadding(new Insets(12,5,20,5));
        
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                makeRegisterScreen(theStage, conn); 
            }
        });
        
        Button bookButton = new Button();
        bookButton.setText("Book A Session");
        bookButton.setAlignment(Pos.TOP_CENTER);
        bookButton.setPrefSize(250, 30);
        bookButton.setMaxSize(bookButton.getPrefWidth(), bookButton.getPrefHeight());
        bookButton.setTextAlignment(TextAlignment.CENTER);
        bookButton.setPadding(new Insets(12,5,20,5));
        
        bookButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                makeBookingScreen(theStage, conn); 
            }
        });
        
        Button checkInButton = new Button();
        checkInButton.setText("Check In A Customer");
        checkInButton.setAlignment(Pos.TOP_CENTER);
        checkInButton.setPrefSize(250, 30);
        checkInButton.setMaxSize(checkInButton.getPrefWidth(), checkInButton.getPrefHeight());
        checkInButton.setTextAlignment(TextAlignment.CENTER);
        checkInButton.setPadding(new Insets(12,5,20,5));
        
        checkInButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                makeCheckInScreen(theStage, conn); 
            }
        });
        
        Button viewScheduleButton = new Button();
        viewScheduleButton.setText("View Schedule");
        viewScheduleButton.setAlignment(Pos.TOP_CENTER);
        viewScheduleButton.setPrefSize(250, 30);
        viewScheduleButton.setMaxSize(viewScheduleButton.getPrefWidth(), viewScheduleButton.getPrefHeight());
        viewScheduleButton.setTextAlignment(TextAlignment.CENTER);
        viewScheduleButton.setPadding(new Insets(12,5,20,5));
        
        viewScheduleButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                makeViewScheduleScreen(theStage, conn); 
            }
        });
        
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setAlignment(Pos.TOP_CENTER);
        backButton.setPrefSize(250, 30);
        backButton.setMaxSize(backButton.getPrefWidth(), backButton.getPrefHeight());
        backButton.setTextAlignment(TextAlignment.CENTER);
        backButton.setPadding(new Insets(12,5,20,5));
        
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
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
    
    private void makeRegisterScreen(Stage primarySlopeOperatorStage, Connection conn) {
        
        RegisterCustomerUI registercustomerui = RegisterCustomerUI.getInstance(primarySlopeOperatorStage, conn);
        
        primarySlopeOperatorStage.setScene(registercustomerui.makeRegisterScreen());
        
        primarySlopeOperatorStage.show();  
    }
    
    public static void makeBookingScreen(Stage primarySlopeOperatorStage, Connection conn) {
        
        Scene lastScene=theStage.getScene();
        
        BookSessionUI booksessionui = new BookSessionUI(primarySlopeOperatorStage, conn, lastScene);    
        
        Scene temp = booksessionui.makeBookingScreen(lastScene);
        
        primarySlopeOperatorStage.setScene(temp);
        
        primarySlopeOperatorStage.show();  
    }
    
    private void makeCheckInScreen(Stage primarySlopeOperatorStage, Connection conn) {
        
        Scene lastScene=theStage.getScene();
        CheckInSessionUI checkinsessionui = CheckInSessionUI.getInstance(primarySlopeOperatorStage, conn,lastScene);
        
        primarySlopeOperatorStage.setScene(checkinsessionui.makeCheckInScreen());
        
        primarySlopeOperatorStage.show();  
    }
    
    private void makeViewScheduleScreen(Stage primarySlopeOperatorStage, Connection conn) {
        
        ViewScheduleUI viewscheduleui = ViewScheduleUI.getInstance(primarySlopeOperatorStage, conn);
        
        primarySlopeOperatorStage.setScene(viewscheduleui.makeViewScheduleScreen());
        
        primarySlopeOperatorStage.show();  
    }
    
    
}

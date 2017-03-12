/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerfx;

import java.sql.* ;  // for standard JDBC programsimport java.sql.Connection;
import java.util.*;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author BOCU
 */
public class ManagerFX extends Application {
    final static String connectionURL = "jdbc:derby://localhost:1527/AddSessionDB";
    final static  String uName = "ovidium";
    final static String uPass= "boculetz";
    final static Connection conn=connectDB();
    final static SessionController sess = new SessionController();   
    static Scene mainScene;    
    static Stage theStage;
    
    final static Connection connectDB(){
        
         try {             
                    Connection conn = DriverManager.getConnection(connectionURL, uName, uPass);
                    System.out.println("Connect to database..."); 
                    return conn;
            } catch (SQLException ex) {             
                System.out.println("Connection failed."); 
                return null;
            }  
        
    }
    final static Scene setDetailsScene(){
        VBox rootDetailPage = new VBox();
        ArrayList <String> availInstructors;
        ArrayList <String> availSlopes;
        sess.setInstructorNames(conn);
        sess.setSlopeNames(conn);
        availSlopes=sess.getSlopeNames();
        availInstructors=sess.getInstructorNames();
        Label instructors = new Label("instructors");
        Label slopes = new Label("slopes");
        Label result =new Label("");
        result.setId("resultLabel");
        rootDetailPage.setPrefSize(350.0, 400.0);
        rootDetailPage.setAlignment(Pos.CENTER);
        
        instructors.setPrefSize(120.0, 100.0);
        slopes.setPrefSize(120.0, 130.0);
        final ComboBox instructorMenu = new ComboBox();
        final ComboBox slopeMenu = new ComboBox();
        slopeMenu.getItems().addAll(availSlopes);
        instructorMenu.getItems().addAll(availInstructors);
        instructorMenu.valueProperty().addListener(new ChangeListener<String>(){
        @Override public void changed(ObservableValue compos, String oldV, String newV){
            result.setText(newV);
        }});
    
        rootDetailPage.getChildren().addAll(instructors, instructorMenu,slopes,slopeMenu,result);
        Scene scene=new Scene(rootDetailPage,300,250);
        
        return scene;
    }
    final static Scene setCalendarScene(){
        BorderPane rootCal = new BorderPane();
        rootCal.setStyle("-fx-padding:30");
        Label pageTitle = new Label("Add a Session - Step 1");
        //AnchorPane centerContent = new AnchorPane();
        //centerContent.setPadding(new Insets(25,25,25,25));
        
        //GridPane rootCalendar = new GridPane();
        //rootCalendar.setStyle("-fx-padding: 50;");
        //rootCalendar.setHgap(10.0);
        //rootCalendar.setVgap(10.0);
        //rootCalendar.setGridLinesVisible(true);
        Button nextPage = new Button("Next");
        nextPage.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                mainScene=setDetailsScene();
                theStage.setScene(mainScene);
            }
        });
        nextPage.setDisable(true);
        

        DatePicker addSessionDatePicker = new DatePicker();
        addSessionDatePicker.setId("datepicker");
        addSessionDatePicker.setOnAction(new EventHandler() {
            @Override
            public void handle(Event e){
            String datePicked = addSessionDatePicker.getValue().toString();
            System.out.println(datePicked);
            nextPage.setDisable(false);}
    });
        //Creating the GridPane that contains calendar and time pickers.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Label checkInlabel = new Label("Select Date:");
        
        //HBox for start time picking
        GridPane timePicker = new GridPane();
        timePicker.setHgap(10);
        timePicker.setVgap(10);
        Label startLabel=new Label("Choose start time");
        Label endLabel=new Label("Choose end time");
        GridPane.setHalignment(startLabel,HPos.LEFT);
        timePicker.add(startLabel,0,0);
        timePicker.add(endLabel,1,0);
        gridPane.setGridLinesVisible(true);
        gridPane.add(checkInlabel, 0, 0);
        GridPane.setHalignment(checkInlabel, HPos.LEFT);
        gridPane.add(addSessionDatePicker, 0, 1);
        gridPane.add(timePicker,0,2);
        GridPane.setHalignment(timePicker,HPos.LEFT);
        //centerContent.getChildren().add(gridPane);
        //centerContent.setTopAnchor(gridPane,0.0);
        //centerContent.setLeftAnchor(gridPane,50.0);
       
        rootCal.setAlignment(nextPage, Pos.BOTTOM_RIGHT);
        rootCal.setAlignment(pageTitle, Pos.TOP_CENTER);
        rootCal.setAlignment(gridPane, Pos.TOP_CENTER);
        rootCal.setTop(pageTitle);
        rootCal.setCenter(gridPane);
        rootCal.setBottom(nextPage);
        
        
        Scene scene = new Scene(rootCal, 400, 400);
        
        //rootCalendar.add(gridPane, 0, 0, 2, 1);
        //rootCalendar.add(nextPage,2,5);
        
        return scene;
    }
    @Override
    public void start(Stage primaryStage){
        
        theStage=primaryStage;
        mainScene = setCalendarScene();
        DatePicker dp = (DatePicker) mainScene.lookup("#datepicker");
        dp.setShowWeekNumbers(false);
      
        primaryStage.setTitle("Title");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

   
    public static void main(String[] args) {
           launch(args);        }
    
}

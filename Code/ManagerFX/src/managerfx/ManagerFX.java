/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerfx;

import java.sql.* ;  // for standard JDBC programsimport java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    static LocalDate dateP;
    static String startTimePicked, endTimePicked;
    
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
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20");
        //TOP
        Label dateAndTimePicked = new Label(dateP.toString()+" "+startTimePicked+"-"+endTimePicked);
        root.setTop(dateAndTimePicked);
        BorderPane.setAlignment(dateAndTimePicked, Pos.TOP_CENTER);
        
        //CENTER
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
            ColumnConstraints column1 = new ColumnConstraints(200);
            ColumnConstraints column2 = new ColumnConstraints(200);
            gridPane.getColumnConstraints().addAll(column1,column2);
        gridPane.setPadding(new Insets(12,0,20,25));
        ArrayList <String> availInstructors;
        ArrayList <String> availSlopes;
        sess.setInstructorNames(conn);
        sess.setSlopeNames(conn);
        availSlopes=sess.getSlopeNames();
        availInstructors=sess.getInstructorNames();
        Label instructors = new Label("instructors");
        Label slopes = new Label("slopes");
        Label sessDescription = new Label("Write a short description of the session");
        TextArea description = new TextArea();
        description.setPrefColumnCount(100);
        description.setPrefRowCount(5);
        description.setWrapText(true);
        //Label result =new Label("");
        //result.setId("resultLabel");
        
        final ComboBox instructorMenu = new ComboBox();
        final ComboBox slopeMenu = new ComboBox();
        slopeMenu.getItems().addAll(availSlopes);
        instructorMenu.getItems().addAll(availInstructors);
        //instructorMenu.valueProperty().addListener(new ChangeListener<String>(){
        //@Override public void changed(ObservableValue compos, String oldV, String newV){
        //   result.setText(newV);
        //}});
        gridPane.add(instructors, 0, 0);
        gridPane.add(instructorMenu,1,0);
        gridPane.add(slopes,0,1);
        gridPane.add(slopeMenu,1,1);
        gridPane.add(sessDescription,0,3,2,1);
        gridPane.add(description,0,4,2,2);
        
        
        root.setCenter(gridPane);
        Scene scene=new Scene(root,550,400);
       
        return scene;
    }
    
    final static Scene setCalendarScene(){
        BorderPane rootCal = new BorderPane();
        rootCal.setStyle("-fx-padding:20");
        Label pageTitle = new Label("Add a Session - Step 1");
        //AnchorPane centerContent = new AnchorPane();
        //centerContent.setPadding(new Insets(25,25,25,25));
        
        //GridPane rootCalendar = new GridPane();
        //rootCalendar.setStyle("-fx-padding: 50;");
        //rootCalendar.setHgap(10.0);
        //rootCalendar.setVgap(10.0);
        //rootCalendar.setGridLinesVisible(true);
        Button nextPage = new Button("Next");
        
        

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
            ColumnConstraints column1 = new ColumnConstraints(130);
            ColumnConstraints column2 = new ColumnConstraints(200);
            ColumnConstraints column3 = new ColumnConstraints(130);
            gridPane.getColumnConstraints().addAll(column1,column2,column3);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //gridPane.setPadding(new Insets(25,25,25,25));
        
        Label checkInlabel = new Label("Select Date:");
        Button startButton=new Button("Choose start time");
        Button endButton=new Button("Choose end time");
        
        //time HBoxes
        final ObservableList<Integer> hours = FXCollections.observableArrayList();
        final ObservableList<Integer> minutes = FXCollections.observableArrayList();
        hours.addAll(9,10,11,12,13,14,15,16,17,18,19,20);
        minutes.addAll(00,15,30,45);
        
        //startTime HBox
        HBox startTime = new HBox();
        startTime.setSpacing(5);
        //startTime.setPrefSize();
        final ComboBox startHours = new ComboBox(hours);
        
        //startHours.setMinHeight(startTime.getHeight()*2);
        final ComboBox startMinutes = new ComboBox(minutes);
        //startMinutes.setMinHeight(startTime.getHeight()*2);
        final Label colon = new Label(":");
        //colon.setPrefSize(startTime.getWidth()/3,startTime.getHeight());
        startTime.getChildren().addAll(startHours,colon,startMinutes);
        startTime.setVisible(false);
        //--------------------------------------------------
        
        //endTime HBox
        HBox endTime = new HBox();
        endTime.setSpacing(5);
        //startTime.setPrefSize();
        final ComboBox endHours = new ComboBox(hours);
        //startHours.setMinHeight(startTime.getHeight()*2);
        final ComboBox endMinutes = new ComboBox(minutes);
        //startMinutes.setMinHeight(startTime.getHeight()*2);
        final Label colon2 = new Label(":");
        //colon.setPrefSize(startTime.getWidth()/3,startTime.getHeight());
        endTime.getChildren().addAll(endHours,colon2,endMinutes);
        endTime.setVisible(false);
        //GridPane timePicker = new GridPane();
        //timePicker.setHgap(10);
        //timePicker.setVgap(10);
        
        startButton.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                if (startTime.isVisible())
                    startTime.setVisible(false);
                else
                    startTime.setVisible(true);
                       
            }
        });
        endButton.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                if (endTime.isVisible())
                        endTime.setVisible(false);
                else
                endTime.setVisible(true);
            }});
        /*
        startHours.setCellFactory(
        new Callback<ListView<String>, ListCell<String>>() {
                @Override 
                public ListCell<String> call(ListView<String> param) {
                    final ListCell<String> cell = new ListCell<String>() {
                        
                        @Override public void updateItem(String item, 
                            boolean empty) {
                                super.updateItem(item, empty);
                                if (item!=null){
                                    setText(item);
                                }
                                if ((endHours.getValue()!=null) && (endHours.getValue().toString().compareTo(item)<0)){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb");
                                }
                            }
                };
                return cell;
            }
        });
        */
         nextPage.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                dateP = addSessionDatePicker.getValue();
                startTimePicked = startHours.getValue().toString()+":"+startMinutes.getValue().toString()+":00";
                endTimePicked = endHours.getValue().toString()+":"+endMinutes.getValue().toString()+":00";
                mainScene=setDetailsScene();
                theStage.setScene(mainScene);
            }
        });
        
        
        
      
        //GridPane.setHalignment(startLabel,HPos.LEFT);
        //timePicker.add(startLabel,0,0);
        //timePicker.add(endLabel,1,0);
        //gridPane.setGridLinesVisible(true);
        gridPane.add(checkInlabel, 1, 0);
        GridPane.setHalignment(checkInlabel, HPos.CENTER);
        gridPane.add(addSessionDatePicker, 1,1);
        gridPane.add(startButton,0,3);
        gridPane.add(endButton,2,3);
        gridPane.add(startTime,0,4);
        gridPane.add(endTime,2,4);
        GridPane.setHalignment(startTime, HPos.LEFT);
        GridPane.setHalignment(addSessionDatePicker, HPos.CENTER);
        GridPane.setHalignment(startButton,HPos.CENTER);
        GridPane.setHalignment(endButton, HPos.CENTER);
        GridPane.setHalignment(endTime, HPos.RIGHT);
        
        //centerContent.getChildren().add(gridPane);
        //centerContent.setTopAnchor(gridPane,0.0);
        //centerContent.setLeftAnchor(gridPane,50.0);
       
        rootCal.setAlignment(nextPage, Pos.BOTTOM_RIGHT);
        rootCal.setAlignment(pageTitle, Pos.TOP_CENTER);
        //rootCal.setAlignment(gridPane,Pos.CENTER);
        rootCal.setTop(pageTitle);
        rootCal.setCenter(gridPane);
        rootCal.setBottom(nextPage);
        rootCal.setMargin(gridPane,new Insets(12,0,12,25));
        
        
        Scene scene = new Scene(rootCal, 550, 400);
        
        
        //rootCalendar.add(gridPane, 0, 0, 2, 1);
        //rootCalendar.add(nextPage,2,5);
       
        nextPage.setDisable(true);
        
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
        primaryStage.setResizable(false);
        primaryStage.show();
    }

   
    public static void main(String[] args) {
           launch(args);        }
    
}

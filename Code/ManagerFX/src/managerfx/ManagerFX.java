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
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
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
    private SessionController sess = new SessionController();   
    private Scene mainScene;    
    private Stage theStage;
    private LocalDate dateP;
    private String startTimePicked, endTimePicked;
    private ComboBox endHours = new ComboBox();
    private ComboBox startHours = new ComboBox();
    private ComboBox instructorMenu = new ComboBox();
    private ComboBox slopeMenu = new ComboBox();
    private DatePicker addSessionDatePicker = new DatePicker();
    private ObservableList hours = FXCollections.observableArrayList();//
    private ObservableList minutes = FXCollections.observableArrayList();//
    
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
     private class StartHoursCell extends ListCell<String> {
            StartHoursCell() {
                endHours.valueProperty().addListener((obs,oldEndHours,newEndHours) -> updateDisableState());
            }
            @Override
            protected void updateItem(String val, boolean empty){
                super.updateItem(val, empty);
                if (empty){
                    setText(null);
                }
                else{
                    setText(val);
                    updateDisableState();
                }
            }
            private void updateDisableState(){
                boolean disable = getItem() != null && endHours.getValue() != null && 
                    Integer.parseInt(getItem())>=Integer.parseInt((String)endHours.getValue());
                setDisable(disable) ;
                setOpacity(disable ? 0.5 : 1);
            }
        }
     private class EndHoursCell extends ListCell<String> {
            EndHoursCell() {
                startHours.valueProperty().addListener((obs,oldStartHours,newStartHours) -> updateDisableState());
            }
            @Override
            protected void updateItem(String val, boolean empty){
                super.updateItem(val, empty);
                if (empty){
                    setText(null);
                }
                else{
                    setText(val);
                    updateDisableState();
                }
            }
            private void updateDisableState(){
                boolean disable = getItem() != null && startHours.getValue() != null && 
                    Integer.parseInt(getItem())<=Integer.parseInt((String)startHours.getValue());;
                setDisable(disable) ;
                setOpacity(disable ? 0.5 : 1);
            }
        }
        
        
    
    private Scene setDetailsScene(){
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
        description.setPromptText(uName);
        description.setPrefRowCount(5);
        description.setWrapText(true);
        //Label result =new Label("");
        //result.setId("resultLabel");
        
        
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
    
    private Scene setCalendarScene(){
        hours.clear();
        hours.addAll("9","10","11","12","13","14","15","16","17","18","19","20");
        minutes.clear();
        minutes.addAll("00","15","30","45");
        
        
        BorderPane rootCal = new BorderPane();
        rootCal.setStyle("-fx-padding:20");
        
        Label pageTitle = new Label("Add a Session - Step 1  ");
        pageTitle.setAlignment(Pos.CENTER);
        pageTitle.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pageTitle.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        addSessionDatePicker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addSessionDatePicker.setId("datepicker");
       
        
        //Creating the GridPane that contains calendar and time pickers.
        GridPane gridPane = new GridPane();
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setHgrow(Priority.ALWAYS);
            ColumnConstraints column2 = new ColumnConstraints();
            column2.setHgrow(Priority.ALWAYS);
            ColumnConstraints column3 = new ColumnConstraints();
            column3.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().addAll(column1,column2,column3);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Label checkInlabel = new Label("Select Date:");
        Button startButton=new Button("Choose start time");
        Button endButton=new Button("Choose end time");
        Button nextPage=new Button("NEXT");
        //time HBoxes
        
        
        //startTime HBox
        HBox startTime = new HBox();
        startTime.setAlignment(Pos.CENTER);    
        startHours.getItems().addAll(hours);     
        final ComboBox startMinutes = new ComboBox(minutes);     
        final Label colon = new Label(":");
        startTime.getChildren().addAll(startHours,colon,startMinutes);
        startTime.setVisible(false);
        //--------------------------------------------------
        
        //endTime HBox
        HBox endTime = new HBox();
        endTime.setAlignment(Pos.CENTER);    
        endHours.getItems().clear();
        endHours.getItems().addAll(hours);
        
        final ComboBox endMinutes = new ComboBox(minutes);
        final Label colon2 = new Label(":");
        endTime.getChildren().addAll(endHours,colon2,endMinutes);
        endTime.setVisible(false);
        
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
        
        //Adding cell factories to the Time Pickers
        startHours.setCellFactory(lv -> new StartHoursCell());
        endHours.setCellFactory(lv -> new EndHoursCell());
       
        
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
        BooleanBinding dateValid = Bindings.createBooleanBinding(() -> {
           if  (addSessionDatePicker.getValue()!=null) return true;
           else return false;
        },addSessionDatePicker.valueProperty());
        BooleanBinding startTimeValid = Bindings.createBooleanBinding(() -> {
           if  ((startHours.getValue()!=null)&&(startMinutes.getValue()!=null)) return true;
           else return false;
        },startHours.valueProperty(),endHours.valueProperty());
        BooleanBinding endTimeValid = Bindings.createBooleanBinding(() -> {
           if  ((endHours.getValue()!=null)&&(endMinutes.getValue()!=null)) return true;
           else return false;},endHours.valueProperty(),endMinutes.valueProperty());
        nextPage.disableProperty().bind((dateValid.and(startTimeValid).and(endTimeValid)).not());
        
        
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
        gridPane.setStyle("-fx-background-color: #FFE0B2");
        
       
        rootCal.setAlignment(nextPage, Pos.BOTTOM_RIGHT);
        rootCal.setAlignment(pageTitle, Pos.TOP_CENTER);
        rootCal.setTop(pageTitle);
        rootCal.setCenter(gridPane);
        rootCal.setBottom(nextPage);
        rootCal.setMargin(gridPane,new Insets(12,15,12,15));
        rootCal.setMargin(nextPage,new Insets(0,15,12,0));
        rootCal.setStyle("-fx-background-color: #FF9800");
        
        Scene scene = new Scene(rootCal, 550, 400);
        rootCal.prefWidthProperty().bind(scene.widthProperty());
        rootCal.prefHeightProperty().bind(scene.widthProperty());
        
       
        
        
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
        //primaryStage.setResizable(false);
        primaryStage.show();
    }

   
    public static void main(String[] args) {
           launch(args);        }
    
}

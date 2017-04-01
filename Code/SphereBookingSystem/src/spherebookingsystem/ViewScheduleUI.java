/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Genaro
 */
public class ViewScheduleUI {
    private static SlopeRepoImpl slopes = new SlopeRepoImpl();
    private static InstructorRepoImpl instr=new InstructorRepoImpl();
    private static SessionRepoImpl sessions = new SessionRepoImpl();
    private static Connection conn;
    private static Scene mainScene;    
    private static Stage theStage;
    private Session tempSession;
    private DatePicker addSessionDatePicker = new DatePicker();
    
    private static ViewScheduleUI instance = null;
    private ViewScheduleUI(Stage primaryStage, Connection con){
        
        theStage=primaryStage;
        conn=con;
        tempSession=new Session();
    }
    public static ViewScheduleUI getInstance(Stage stage,Connection con){
        
        if (instance==null){
            instance=new ViewScheduleUI(stage,con);
        }
        return instance;
    }
    /*
    public class tableSession {
            
            private final SimpleStringProperty id;
            private final SimpleStringProperty startTime;
            private final SimpleStringProperty endTime;
            private final SimpleStringProperty maxBookings;
            private final SimpleStringProperty insName;
            private final SimpleStringProperty slopeName;
            public tableSession(Session ses){
               this.id=new SimpleStringProperty(String.valueOf(ses.getId()));
               this.startTime=new SimpleStringProperty(ses.getStartTime());
               this.endTime=new SimpleStringProperty(ses.getEndTime());
               this.maxBookings=new SimpleStringProperty(String.valueOf(ses.getMaxBookings()));
               this.insName=new SimpleStringProperty(instr.read(conn, ses.getInstructorId()).getName());
               this.slopeName=new SimpleStringProperty(slopes.read(conn, ses.getSlopeId()).getName());
               
            }

        public String getId() {
            return this.id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getStartTime() {
            return startTime.get();
        }

        public void setStartTime(String startTime) {
            this.startTime.set(startTime);
        }

        public String getEndTime() {
            return endTime.get();
        }

        public void setEndTime(String endTime) {
            this.endTime.set(endTime);
        }

        public String getMaxBookings() {
            return maxBookings.get();
        }

        public void setMaxBookings(String maxBookings) {
            this.maxBookings.set(maxBookings);
        }

        public String getInsName() {
            return insName.get();
        }

        public void setInsName(String insName) {
            this.insName.set(insName);
        }

        public String getSlopeName() {
            return slopeName.get();
        }

        public void setSlopeName(String slopeName) {
            this.slopeName.set(slopeName);
        }
            
    };
    // Creates the user interface for viewing the schedule of sessions
    public Scene makeViewScheduleScreen() {
        
        //DatePicker creation
        addSessionDatePicker.setValue(null);
        addSessionDatePicker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addSessionDatePicker.setId("datepicker");
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
        addSessionDatePicker.setDayCellFactory(dayCellFactory);
        
        addSessionDatePicker.setPrefWidth(200);
        addSessionDatePicker.setMaxWidth(200);
        //-----------------------------------------------------------------
        //TableView creation
        TableView<tableSession> table = new TableView<tableSession>();
        ObservableList<tableSession> data =FXCollections.observableArrayList();
        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(50);
        idCol.setCellValueFactory(
                new PropertyValueFactory<tableSession, String>("id"));
        TableColumn sTimeCol = new TableColumn("Start");
        sTimeCol.setMinWidth(100);
        sTimeCol.setCellValueFactory(
                new PropertyValueFactory<tableSession, String>("startTime"));
        TableColumn eTimeCol = new TableColumn("End");
        eTimeCol.setMinWidth(100);
        eTimeCol.setCellValueFactory(
                new PropertyValueFactory<tableSession, String>("endTime"));
        TableColumn insCol = new TableColumn("Instructor");
        insCol.setMinWidth(100);
        insCol.setCellValueFactory(
                new PropertyValueFactory<tableSession, String>("insName"));
        TableColumn slopeCol = new TableColumn("Slope");
        slopeCol.setMinWidth(100);
        slopeCol.setCellValueFactory(
                new PropertyValueFactory<tableSession, String>("slopeName"));
        TableColumn bookCol = new TableColumn("Bookings");
        eTimeCol.setMinWidth(100);
        eTimeCol.setCellValueFactory(
                new PropertyValueFactory<tableSession, String>("maxBookings"));
        table.getColumns().addAll(idCol,sTimeCol,eTimeCol,insCol,slopeCol,bookCol);
        addSessionDatePicker.valueProperty().addListener((obs,o,n)->{
            ArrayList<Session> temp = sessions.read(conn, n);
            data.clear();
            for (int i=0;i<temp.size();i++){
                data.add((tableSession)(new tableSession(temp.get(i))));
            }
            table.setItems(data);
        });
        
        
        
        
        
        VBox root = new VBox();
        root.setSpacing(30);
        root.setPadding(new Insets(20,0,0,0));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(addSessionDatePicker,table);
        
        Scene scene=new Scene(root,600,600);
        return scene;
    }
     */   
    public Scene makeViewScheduleScreen(){
        return new Scene(new BorderPane(),500,500);
    }
}
        

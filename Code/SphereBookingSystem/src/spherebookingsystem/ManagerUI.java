
package spherebookingsystem;
import java.sql.* ;  // for standard JDBC programsimport java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
//import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * @author Ovidiu Mitroi
 *         SID: 6832432
 *         FUNCTIONALITY: ADD A SESSION
 */
public class ManagerUI {
    /*
    Main interface class. Holds several screens that represent the functionality to 
    ADD A SESSION
    */
    private static Connection conn;
    private static SessionController sess = new SessionController(); 
    private static Scene mainScene;    
    private static Scene loginScene;
    private static Stage theStage;
    private Session tempSession;  //temporary Session instance which updates as it gets created through the steps of the functionality
    private ArrayList<Instructor> availInstructors; //will hold all available instructors for the time slot picked
    private ArrayList<Slope> availSlopes; // will hold all available slopes for the time slot picked
    //------------------------------------------------------------------------------------
    /*
    interface elements. Declared here since the information in them needs to be shared by 
    several screens.
    */
    private ComboBox endHours = new ComboBox();
    private ComboBox startHours = new ComboBox();
    private ComboBox startMinutes = new ComboBox();
    private ComboBox endMinutes = new ComboBox();
    private ComboBox instructorMenu = new ComboBox();
    private ComboBox slopeMenu = new ComboBox();
    private DatePicker addSessionDatePicker = new DatePicker();
    //---------------------------------------------------------
    private ObservableList hours = FXCollections.observableArrayList();//list of available hours
    private ObservableList minutes = FXCollections.observableArrayList();//list of available minutes
    //------------------------------------------------
    //Singleton GoF pattern implementation here
    private static ManagerUI instance = null;
    private ManagerUI(Stage primaryStage, Connection con,Scene loginScene){
        theStage=primaryStage;
        this.loginScene=loginScene;
        conn=con;
        tempSession=new Session();
        availSlopes=new ArrayList();
        availInstructors=new ArrayList();
    }
    public static ManagerUI getInstance(Stage stage,Connection con,Scene loginScene){
        if (instance==null){
            instance=new ManagerUI(stage,con,loginScene);
        }
        return instance;
    }
    //-------------------------------------------------
    private class StartHoursCell extends ListCell<String> {
        /*
        Cell Factory class. Determines the options from the Hour and Minutes comboboxes that need to be disabled
        */
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
        /*
        Cell Factory class. Determines the options from the Hour and Minutes comboboxes that need to be disabled
        */
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
    public Scene mainScene(){
        //Represents the main scene displayed when logging in as a manager
        // TOP Layer: A welcome title
        //CENTER LAYER: Some data about how many instructors,slopes are available
        //              and how many sessions are in the database
        //LEFT MENU: A menu with all actions a manager can take
        //             (Note): only Add Session has been implemented
        //BOTTOM LAYER: A button to log out
        BorderPane root=new BorderPane();
        //TOP
        Label title=new Label("Welcome to Sphere Booking System ");
        title.setAlignment(Pos.CENTER);
        title.setId("titleMain");
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        //CENTER
        VBox centerRoot=new VBox(40);
        centerRoot.setAlignment(Pos.CENTER);
        Label availInstructorsLabel=new Label("There are "+sess.getAllInstructors(conn).size()+" instructors available.");
        Label availSlopesLabel=new Label("There are "+sess.getAllSlopes(conn).size()+" slopes available.");
        Label sessionCount=new Label(sess.countAllSessions(conn)+" sessions registered(in total)");
        centerRoot.getChildren().addAll(availInstructorsLabel,availSlopesLabel,sessionCount);
        
        root.setCenter(centerRoot);
        BorderPane.setMargin(centerRoot, new Insets(20,0,0,0));
        //LEFT
        VBox menuLeft = new VBox(30);
        Label actions=new Label("Actions you can take:");
        Button addSession=new Button("Add a Session");
        Button addInstructor=new Button("Add an instructor");
        Button addSlope=new Button("Add a slope");
        addInstructor.setDisable(true);
        addSlope.setDisable(true);
        menuLeft.getChildren().addAll(actions,addSession,addInstructor,addSlope);
        root.setLeft(menuLeft);
        BorderPane.setMargin(menuLeft, new Insets(20,30,0,10));
        //BOTTOM
        Button logOut=new Button("Log Out");
        root.setBottom(logOut);
        BorderPane.setAlignment(logOut, Pos.CENTER);
        BorderPane.setMargin(logOut,new Insets(10,0,20,0));
        Scene scene=new Scene(root,500,500);
        scene.getStylesheets().add(getClass().getResource("CalendarScene.css").toExternalForm());
        
        //button actions
        addSession.setOnAction(new EventHandler<ActionEvent>() {
            //Start the process of adding a session
            @Override
            public void handle(ActionEvent event) {
                
                theStage.setScene(setCalendarScene());
            }
        });
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            //log out
            @Override
            public void handle(ActionEvent event) {
                theStage.setScene(loginScene);
            }
        });
        return scene;
        
    }
    public Scene setCalendarScene(){
        //The Calendar Scene represents the first step when adding a session
        /*
        Displays:
            A DatePicker to pick a date
            Two sets of comboboxes to select a starttime and an endtime
            A button that goies to the next page when everything has been selected
        */
        hours.clear();
        hours.addAll("9","10","11","12","13","14","15","16","17","18","19","20");
        minutes.clear();
        minutes.addAll("00","15","30","45");
        
        
        BorderPane rootCal = new BorderPane();
        rootCal.setStyle("-fx-padding:20");
        
        Label pageTitle = new Label("Add a Session - Step 1  ");
        pageTitle.setId("pageTitle");
        pageTitle.setAlignment(Pos.CENTER);
        pageTitle.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pageTitle.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addSessionDatePicker.setValue(null);
        addSessionDatePicker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addSessionDatePicker.setId("datepicker");
        //Date Picker cell factory to disable days before today
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
        Label startLabel=new Label("Choose start time");
        Label dayConfirmedLabel= new Label();
        addSessionDatePicker.valueProperty().addListener((obs,o,n)->{
           if (n!=null)
            dayConfirmedLabel.setText(n.getDayOfWeek().toString()); 
        });
        
        Label endLabel=new Label("Choose end time");
        
        //time HBoxes
        
        
        //startTime HBox
        HBox startTime = new HBox();
        startTime.setAlignment(Pos.CENTER);   
        startHours.getItems().clear();
        startHours.getItems().addAll(hours);
        startMinutes.getItems().clear();
        startMinutes.getItems().addAll(minutes);
        final Label colon = new Label(":");
        startTime.getChildren().addAll(startHours,colon,startMinutes);
        startTime.visibleProperty().bind(startLabel.visibleProperty());
        
        //--------------------------------------------------
        
        //endTime HBox
        HBox endTime = new HBox();
        endTime.setAlignment(Pos.CENTER);    
        endHours.getItems().clear();
        endHours.getItems().addAll(hours);
        endMinutes.getItems().clear();
        endMinutes.getItems().addAll(minutes);
        final Label colon2 = new Label(":");
        endTime.getChildren().addAll(endHours,colon2,endMinutes);
        endTime.visibleProperty().bind(endLabel.visibleProperty());
        
       
        //Adding cell factories to the Time Pickers
        startHours.setCellFactory(lv -> new StartHoursCell());
        endHours.setCellFactory(lv -> new EndHoursCell());
        
        //BOTTOM
        
        
        Button logOut=new Button("Back to Menu");
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                theStage.setScene(mainScene());
            }
        });
        Button nextPage=new Button("NEXT");
        nextPage.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                /*
                Sets the picked Date and Time values in the tempSession instance of the class Session
                Then moves on to the next screen
                */
                tempSession.setDate(addSessionDatePicker.getValue());
                tempSession.setStartTime(startHours.getValue().toString()+":"+startMinutes.getValue().toString()+":00");
                tempSession.setEndTime(endHours.getValue().toString()+":"+endMinutes.getValue().toString()+":00"); 
                //Yi Lin's feedback implementation
                //get available slopes and instructors for the date selected
                sess.setInstructorList(conn, tempSession.getDate(),tempSession.getStartTime(), tempSession.getEndTime());
                sess.setSlopeList(conn, tempSession.getDate(),tempSession.getStartTime(), tempSession.getEndTime());
                if (availInstructors.size()>0)
                    availInstructors.clear();
                if (availSlopes.size()>0)
                    availSlopes.clear();
                availInstructors.add(new Instructor());
        
                availSlopes.addAll(sess.getSlopes());
                availInstructors.addAll(sess.getInstructors());
                //if no slopes available, give an error and let the user know he has to select another date
                if (availSlopes.size()==0){
                    Stage errorStage=new Stage();
                    VBox rootError=new VBox(20);
                    Label errorMessage=new Label("There are no available slopes for that time slot"+
                                                 "\n\t  Please choose another time slot");
                    Button okButton=new Button("OK");
                    okButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            errorStage.close();
                        }
                    });
                    rootError.getChildren().addAll(errorMessage,okButton);
                    rootError.setAlignment(Pos.CENTER);
                    Scene errorScene=new Scene(rootError,400,150);
                    errorStage.setScene(errorScene);
                    errorStage.show();
                }
                //if no instructors available, let user know. He can still choose to proceed
                else if (availInstructors.size()<=1){
                    Stage warningStage=new Stage();
                    VBox rootWarning=new VBox(20);
                    HBox buttonsWarning=new HBox(20);
                    Label warning=new Label("There will be no instructors available for this time slot");
                    Label warning2=new Label("You can still proceed if you don't want to set up an instructor");
                    Button goForward=new Button("Proceed");
                    goForward.setStyle("-fx-background-color: green");
                    goForward.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            warningStage.close();
                            mainScene=setDetailsScene();
                            theStage.setScene(mainScene);
                        }
                    });
                    Button goBack=new Button("Choose another time slot");
                    goBack.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            warningStage.close();
                            
                        }
                    });
                    buttonsWarning.getChildren().addAll(goForward,goBack);
                    rootWarning.getChildren().addAll(warning,warning2,buttonsWarning);
                    rootWarning.setAlignment(Pos.CENTER);
                    buttonsWarning.setAlignment(Pos.CENTER);
                    Scene scene=new Scene(rootWarning,400,150);
                    warningStage.setScene(scene);
                    warningStage.show();
                }
                //if everything ok, go forward in the process
                else {mainScene=setDetailsScene();
                theStage.setScene(mainScene);}
            }
        });
        BorderPane bottomButtons=new BorderPane();
        bottomButtons.setPrefWidth(rootCal.getPrefWidth());
        bottomButtons.setLeft(logOut);
        bottomButtons.setRight(nextPage);
        
        /*
        A set of boolean bindings definitions which define the sequence in which our elements will 
        become visible on screen
        The sequence of visiblity is:
                                        DatePicker
                                        StartTime combobox 
                                        EndTime Combobox
                                        NextPage button
        */
        BooleanBinding dateValid = Bindings.createBooleanBinding(() -> 
            (addSessionDatePicker.getValue()!=null),addSessionDatePicker.valueProperty());
        BooleanBinding startTimeHoursValid = Bindings.createBooleanBinding(() -> 
            (startHours.getValue()!=null),startHours.valueProperty());
        BooleanBinding startTimeMinutesValid=Bindings.createBooleanBinding(()->
                (startMinutes.getValue()!=null),startMinutes.valueProperty());
        BooleanBinding endTimeHoursValid = Bindings.createBooleanBinding(() -> 
            (endHours.getValue()!=null),endHours.valueProperty());
        BooleanBinding endTimeMinutesValid=Bindings.createBooleanBinding(()->
                (endMinutes.getValue()!=null),endMinutes.valueProperty());
        
        nextPage.disableProperty().bind(endTimeHoursValid.and(endTimeMinutesValid).not());
        startLabel.visibleProperty().bind(dateValid);
        endLabel.visibleProperty().bind(startTimeHoursValid.and(startTimeMinutesValid));
        dayConfirmedLabel.visibleProperty().bind(endTimeHoursValid.and(endTimeMinutesValid));
        
        //add all elements to the grid
        gridPane.add(checkInlabel, 1, 0);
        GridPane.setHalignment(checkInlabel, HPos.CENTER);
        gridPane.add(addSessionDatePicker, 1,1);
        gridPane.add(startLabel,0,3);
        gridPane.add(endLabel,2,3);
        gridPane.add(startTime,0,4);
        gridPane.add(endTime,2,4);
        gridPane.add(dayConfirmedLabel,1,6);
        GridPane.setHalignment(startTime, HPos.LEFT);
        GridPane.setHalignment(addSessionDatePicker, HPos.CENTER);
        GridPane.setHalignment(startLabel,HPos.CENTER);
        GridPane.setHalignment(endLabel, HPos.CENTER);
        GridPane.setHalignment(dayConfirmedLabel, HPos.CENTER);
        gridPane.setStyle("-fx-background-color: #FFE0B2");
        
        rootCal.setAlignment(logOut,Pos.BOTTOM_LEFT);
        rootCal.setAlignment(pageTitle, Pos.TOP_CENTER);
        rootCal.setTop(pageTitle);
        rootCal.setCenter(gridPane);
        rootCal.setBottom(bottomButtons);
        
        rootCal.setMargin(gridPane,new Insets(12,15,12,15));
        rootCal.setMargin(bottomButtons,new Insets(0,15,12,0));
        rootCal.setStyle("-fx-background-color: #FF9800");
        
        Scene scene = new Scene(rootCal, 550, 400);
        rootCal.prefWidthProperty().bind(scene.widthProperty());
        rootCal.prefHeightProperty().bind(scene.widthProperty());
        scene.getStylesheets().add(getClass().getResource("CalendarScene.css").toExternalForm());
        return scene;
    }
    public Scene setDetailsScene(){
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20");
        //TOP
        Label dateAndTimePicked = new Label(tempSession.getDate().toString()+" "+tempSession.getStartTime()+"-"+tempSession.getEndTime());
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
       
        //fetch the list of available instructors and slopes for the timeslot picked
       /* sess.setInstructorList(conn, tempSession.getDate(),tempSession.getStartTime(), tempSession.getEndTime());
        sess.setSlopeList(conn, tempSession.getDate(),tempSession.getStartTime(), tempSession.getEndTime());
        if (availInstructors.size()>0)
            availInstructors.clear();
        if (availSlopes.size()>0)
            availSlopes.clear();
        availInstructors.add(new Instructor());
        
        availSlopes.addAll(sess.getSlopes());
        availInstructors.addAll(sess.getInstructors());*/
        //-----------------------------------------------------------------------
        Label instructors = new Label("instructors");
        Label slopes = new Label("slopes");
        Label sessDescription = new Label("Write a short description of the session");
        TextArea description = new TextArea();
        description.setPrefColumnCount(100);
        description.setPromptText("Enter text here...");
        description.setPrefRowCount(5);
        description.setWrapText(true);
        
        Label priceEnter = new Label("Price");
        TextField priceText = new TextField();
        priceText.setPromptText("xxx.xx");
        priceText.setPrefWidth(60);
        priceText.setMaxWidth(60);
        //make the price field only able to receive float as text
        priceText.textProperty().addListener((obs,o,n)->{
            try{
                Float a = Float.parseFloat(n);
                if (a<=0){
                    priceText.setText("");
                }
            }
            catch (NumberFormatException ex){
                priceText.setText("");
            }
        });
        
        Label setMaxBookings = new Label ("Max Bookings: ");
        TextField bookingText = new TextField();
        bookingText.setPromptText("xx");
        bookingText.setPrefWidth(60);
        bookingText.setMaxWidth(60);
        //make the maxBookings field only able to receive integers
        bookingText.textProperty().addListener((obs,o,n)->{
            try{
                int a = Integer.parseInt(n);
                if (a<=0){
                    bookingText.setText("");
                }
            }
            catch (NumberFormatException ex){
                bookingText.setText("");
            }
        });
        
        //add all items to the grid
        instructorMenu.getItems().addAll(availInstructors);
        slopeMenu.getItems().addAll(availSlopes);
        gridPane.add(instructors, 0, 0);
        gridPane.add(instructorMenu,1,0);
        gridPane.add(slopes,0,1);
        gridPane.add(slopeMenu,1,1);
        gridPane.add(priceEnter,0,2);
        gridPane.add(priceText,1,2);
        gridPane.add(setMaxBookings,0,3);
        gridPane.add(bookingText,1,3);
        gridPane.add(sessDescription,0,5,2,1);
        gridPane.add(description,0,6,2,2);
        
        //BOTTOM
        Button nextPage = new Button("NEXT");
        Button previousPage = new Button("GO BACK");
        nextPage.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                //sets the values picked in the tempSession instance of Session and goes to next page
                tempSession.setInstructorId(((Instructor)instructorMenu.getValue()).getInstrId());
                tempSession.setSlopeId(((Slope)slopeMenu.getValue()).getId());
                tempSession.setMaxBookings(Integer.parseInt(bookingText.getText()));
                tempSession.setPrice(Integer.parseInt(priceText.getText()));
                tempSession.setDescription(description.getText());
                Scene lastScene=theStage.getScene();
                mainScene=makeResultScene(lastScene);
                theStage.setScene(mainScene);
            }
        });
        previousPage.setOnAction(new EventHandler<ActionEvent>() {
            //go back to date selector
            @Override
            public void handle(ActionEvent event) {
                resetElements();
                theStage.setScene(setCalendarScene());
            }
        });
        BorderPane bottomButtons=new BorderPane();
        bottomButtons.setPrefWidth(root.getPrefWidth());
        bottomButtons.setLeft(previousPage);
        bottomButtons.setRight(nextPage);
        
        root.setCenter(gridPane);
        root.setBottom(bottomButtons);
        root.setMargin(bottomButtons,new Insets(0,15,12,0));
        Scene scene=new Scene(root,550,400);
        return scene;
    }
    public Scene makeResultScene(Scene lastScene){
        
        VBox root=new VBox();
        VBox topLayer=new VBox();
        HBox buttons = new HBox(50);
        buttons.setAlignment(Pos.CENTER);
        Button addDb=new Button("Add this Session");
        Button editDetails = new Button("Edit Details");
        buttons.getChildren().addAll(addDb,editDetails);
        VBox bottomLayer=new VBox();
        Image sIcon=new Image("file:src/successIcon.png", 60, 60, true, true);
        ImageView sIconView=new ImageView();
        sIconView.setImage(sIcon);
        Label confirmText=new Label("Session succesfully added");
        Image dIcon=new Image ("file:src/dIcon.png",60,60,true,true);
        Button finish=new Button("Back to Start");
        finish.setVisible(false);
        bottomLayer.setId("botbox");
       
        finish.setOnAction(new EventHandler() {
            //go back to the beginning
            @Override
            public void handle(Event event) {
                resetElements();
                theStage.setScene(mainScene());
            }
        });
        addDb.setId("addButton");
        addDb.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                //this button will send all the information stored in tempSession to be written in database
                sess.addSession(conn, tempSession);
                bottomLayer.getChildren().clear();
                bottomLayer.getChildren().addAll(sIconView,confirmText);
                bottomLayer.setVisible(true);
                finish.setVisible(true);
            }
        });
        editDetails.setOnAction(new EventHandler<ActionEvent>() {
            //This button will take you back to the previous screen in order to edit details
            @Override
            public void handle(ActionEvent event) {
               theStage.setScene(lastScene);
            }
        });
        topLayer.setId("topbox");
        root.setStyle("-fx-padding:20");
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("MMMM dd yyyy");
        Label date=new Label("Date selected: "+tempSession.getDate().format(dtf));
        Label time=new Label("Time slot: "+ tempSession.getStartTime()+"-"+tempSession.getEndTime());
        Label instructor=new Label("Instructor selected: "+instructorMenu.getValue());
        Label slope=new Label("Slope selected: "+slopeMenu.getValue());
        Label maxB=new Label("Maximum bookings for this session: "+String.valueOf(tempSession.getMaxBookings()));
        Label price=new Label("Price per booking: "+String.valueOf(tempSession.getPrice()));
        Label desc=new Label("Description:\n"+tempSession.getDescription());
        topLayer.getChildren().addAll(date,time,instructor,slope,maxB,price,desc);
        root.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(topLayer,buttons,bottomLayer,finish);
        
        Scene scene=new Scene(root,500,500);
        scene.getStylesheets().add(getClass().getResource("ManagerUIRSScene.css").toExternalForm());
        return scene;
    }
    private void resetElements(){
        instructorMenu.getItems().clear();
        slopeMenu.getItems().clear();
        addSessionDatePicker.setValue(null);
    }
    
}

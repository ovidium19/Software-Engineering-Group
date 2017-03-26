/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.Connection;
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
public class ViewScheduleUI {
    
    private static Connection conn;
    private static Scene mainScene;    
    private static Stage theStage;
    private Session tempSession;
    
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
    
    // Creates the user interface for viewing the schedule of sessions
    public Scene makeViewScheduleScreen() {
        
        Label viewScheduleLabel = new Label();
        viewScheduleLabel.setText("View Schedule");
        viewScheduleLabel.setAlignment(Pos.TOP_CENTER);
        viewScheduleLabel.setTextAlignment(TextAlignment.CENTER);
        viewScheduleLabel.setPadding(new Insets(12,5,20,5));
        
        Button mondayButton = new Button();
        mondayButton.setText("Monday");
        mondayButton.setAlignment(Pos.TOP_CENTER);
        mondayButton.setTextAlignment(TextAlignment.CENTER);     
        mondayButton.setPadding(new Insets(12,5,20,5));
        
        Button tuesdayButton = new Button();
        tuesdayButton.setText("Tuesday");
        tuesdayButton.setAlignment(Pos.TOP_CENTER);
        tuesdayButton.setTextAlignment(TextAlignment.CENTER);     
        tuesdayButton.setPadding(new Insets(12,5,20,5));
        
        Button wednesdayButton = new Button();
        wednesdayButton.setText("Wednesday");
        wednesdayButton.setAlignment(Pos.TOP_CENTER);
        wednesdayButton.setTextAlignment(TextAlignment.CENTER);     
        wednesdayButton.setPadding(new Insets(12,5,20,5));
        
        Button thursdayButton = new Button();
        thursdayButton.setText("Thursday");
        thursdayButton.setAlignment(Pos.TOP_CENTER);
        thursdayButton.setTextAlignment(TextAlignment.CENTER);     
        thursdayButton.setPadding(new Insets(12,5,20,5));
        
        Button fridayButton = new Button();
        fridayButton.setText("Friday");
        fridayButton.setAlignment(Pos.TOP_CENTER);
        fridayButton.setTextAlignment(TextAlignment.CENTER);     
        fridayButton.setPadding(new Insets(12,5,20,5));
        
                
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setAlignment(Pos.TOP_RIGHT);
        backButton.setTextAlignment(TextAlignment.CENTER);     
        backButton.setPadding(new Insets(12,5,20,5));
               
        
        VBox root = new VBox();
        root.getChildren().addAll(viewScheduleLabel, mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, backButton);
                
        Scene scene = new Scene(root, 500, 450);
        
        return(scene); 
    }
    
}

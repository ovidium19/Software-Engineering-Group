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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Genaro
 */
public class CheckInSessionUI {
    
    private static Connection conn;
    private static Scene mainScene;    
    private static Stage theStage;
    private Session tempSession;
    
    private static CheckInSessionUI instance = null;
    private CheckInSessionUI(Stage primaryStage, Connection con){
        
        theStage=primaryStage;
        conn=con;
        tempSession=new Session();
    }
    
    public static CheckInSessionUI getInstance(Stage stage,Connection con){
        
        if (instance==null){
            instance=new CheckInSessionUI(stage,con);
        }
        return instance;
    }
    
    // Creates the user interface for checking in a customer
    public Scene makeCheckInScreen() {
        
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
    
}

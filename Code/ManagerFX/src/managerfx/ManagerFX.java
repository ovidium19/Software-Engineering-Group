/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerfx;

import java.sql.* ;  // for standard JDBC programsimport java.sql.Connection;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author BOCU
 */
public class ManagerFX extends Application {
    final static String connectionURL = "jdbc:derby://localhost:1527/AddSessionDB";
    final static  String uName = "ovidium";
    final static String uPass= "boculetz";
        
            //ConnectionURL, username and password should be specified in getConnection()       
            
    public Connection connectDB(){
        
         try {             
                    Connection conn = DriverManager.getConnection(connectionURL, uName, uPass);
                    System.out.println("Connect to database..."); 
                    return conn;
            } catch (SQLException ex) {             
                System.out.println("Connection failed."); 
                return null;
            }  
        
    }
    @Override
    public void start(Stage primaryStage) {
            SessionController sess = new SessionController();
            ArrayList <String> instructorNames;
            Connection conn=connectDB();
          
          sess.setInstructorNames(conn);
          instructorNames=sess.getInstructorNames();
          for (int i=0;i<instructorNames.size();i++){
              System.out.println(instructorNames.get(i));
          }
        Label instructors = new Label("instructors");
        VBox root = new VBox();
        root.setPrefSize(350.0, 400.0);
        root.setAlignment(Pos.CENTER);
        
        instructors.setPrefSize(120.0, 100.0);
        final ComboBox instructorMenu = new ComboBox();
        instructorMenu.getItems().addAll(instructorNames);
        root.getChildren().addAll(instructors, instructorMenu);
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           launch(args);
            
    }
    
}

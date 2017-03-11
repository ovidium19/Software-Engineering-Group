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
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
    final static Connection conn=connectDB();
    final static SessionController sess = new SessionController(); 
        
            //ConnectionURL, username and password should be specified in getConnection()       
            
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
    @Override
    public void start(Stage primaryStage) {
        
        
        Scene scene = setDetailsScene();
 
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

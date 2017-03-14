package slopeoperator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

/*
 * Class that contains the main function
 * Creates an instance of the SlopeOperatorUI
 *
 * @author Genaro Bedenko
 */
public class SlopeOperator {

    // Initialises a booking repository and connects to the SQL database
    public static void main(String[] args){
            
      String connectionURL = "jdbc:derby://localhost:1527/SphereDB";
      String uName = "admin1";
      String uPass= "admin1";
        
      //ConnectionURL, username and password should be specified in getConnection()       
      try {             
            Connection conn = DriverManager.getConnection(connectionURL, uName, uPass);
            System.out.println("Connect to database..."); 

            if (conn != null){ 

                SlopeOperatorUI slopeOperatorInterface = new SlopeOperatorUI(conn);
                slopeOperatorInterface.homeWindowSetup();    
                        
                //conn.close();
                //System.out.println("Connection is closed.");
                                    
            } 
            else {
                System.out.println("null");  
            }
       } 
       catch (SQLException ex) {
           
            System.out.println(ex);         
       }
    }     
}

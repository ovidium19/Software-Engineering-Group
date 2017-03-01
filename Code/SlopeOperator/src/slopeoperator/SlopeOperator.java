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

    public static void main(String[] args){
        
      BookingRepoImpl bookRepo = new BookingRepoImpl();
      
      String connectionURL = "jdbc:derby://localhost:1527/BookingSessionDB";
      String uName = "admin1";
      String uPass= "admin1";
        
      //ConnectionURL, username and password should be specified in getConnection()       
      try {             
            Connection conn = DriverManager.getConnection(connectionURL, uName, uPass);
            System.out.println("Connect to database..."); 

            if (conn != null){ 
                ArrayList list = bookRepo.read(conn);

                SlopeOperatorUI slopeOperatorInterface = new SlopeOperatorUI(conn, list);
                slopeOperatorInterface.homeWindowSetup();    
                        
                conn.close();
                System.out.println("Connection is closed.");
                        
                System.exit(1);
                    
            } 
            else {
                System.out.println("null");  
            }
       } 
       catch (SQLException ex) {
           
            System.out.println("Connection failed.");         
       }
    }     
}

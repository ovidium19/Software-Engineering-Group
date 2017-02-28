package manager;

import java.sql.* ;  // for standard JDBC programsimport java.sql.Connection;
import java.util.*;
import java.io.*;

/**
 *
 * @author YL Hedley
 */

public class Manager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SessionRepoImpl sessRepo = new SessionRepoImpl();

            //sample code to demo the databaase connection and sql statements            
            String connectionURL = "jdbc:derby://localhost:1527/AddSessionDB";
            String uName = "ovidium";
            String uPass= "boculetz";
        
            //ConnectionURL, username and password should be specified in getConnection()       
            try {             
                    Connection conn = DriverManager.getConnection(connectionURL, uName, uPass);
                    System.out.println("Connect to database..."); 

                    if (conn != null){ 
                        ArrayList list = sessRepo.read(conn);

                        ManagerUI ui = new ManagerUI(conn, list);
                        ui.displayMenu();     
                        
                        conn.close();
                        System.out.println("Connection is closed.");
                        
                        System.exit(1);
                    
                    } else {
                        System.out.println("null");  
                    }
            } catch (SQLException ex) {             
                System.out.println("Connection failed.");         
            }          
    }    
}


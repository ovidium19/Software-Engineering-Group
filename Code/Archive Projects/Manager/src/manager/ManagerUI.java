//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Manager
//  @ File Name : ManagerUI.java
//  @ Date : 15/02/2017
//  @ Author : Genaro Bedenko
//
//

package manager;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;



public class ManagerUI {
    /**
     * @param args the command line arguments
     */   
        
    //Run the menu interface ---------
    String demoOptions[] = {"Add a new session", "View a session", "View all session","Exit the System"};  
    char demoChoice;          
    Menu demoMenu= new Menu("Menu ", demoOptions, "Enter the menu option: ");          
        
    SessionController con = new SessionController(); 
        
    ArrayList list = new ArrayList();
    Connection conn;
        
    public ManagerUI(Connection aConn, ArrayList aList) {
        conn = aConn;
        con.setSessionList(aList);
    }
    

    public void displayMenu() {
        boolean exit = true;
        while (exit){
            System.out.println("----------------------------------------------");
            demoChoice = demoMenu.offerMenu();
            String name = "";
            String spec = "";
            int ref = 0;
            
            
            BufferedReader keyboard;
            switch (demoChoice){
            case 'A': //to add a new dentist
                try {
                  
                    System.out.println("Enter the startTime:"); 
                    keyboard = new  BufferedReader(new InputStreamReader(System.in));
                    Time startTime = Time.valueOf(keyboard.readLine());                    
                    System.out.println("Enter the endTime:"); 
                    Time endTime = Time.valueOf(keyboard.readLine());
                    System.out.println("Enter the date:"); 
                    String fromKeyboard = keyboard.readLine();
                    Date date = Date.valueOf(fromKeyboard);
                    System.out.println("Enter maxBookings:"); 
                    fromKeyboard = keyboard.readLine();
                    int maxBookings=Integer.parseInt(fromKeyboard);
                    System.out.println("Enter slopeId:"); 
                    fromKeyboard = keyboard.readLine();
                    int slopeId=Integer.parseInt(fromKeyboard);
                    System.out.println("Enter instructorId:"); 
                    fromKeyboard = keyboard.readLine();
                    int instructorId=Integer.parseInt(fromKeyboard);
                    

                    con.addASession(startTime,endTime,date,maxBookings,slopeId,instructorId, conn);
                }
                catch (java.io.IOException exception){} 
                //-------------------------------------------                
            break;

            case 'B': //to view a dentist details 
                try {
                    System.out.println("Enter reference number:"); 
                    keyboard = new  BufferedReader(new InputStreamReader(System.in));
                    String fromKeyboard = keyboard.readLine();
                    ref = Integer.parseInt(fromKeyboard);

                    con.viewDetails(ref);
                 }
                catch (java.io.IOException exception){} 
                //-------------------------------------------
            break;       

            case 'C': //to view all dentist details 
                    con.seeAllSessions();
                //-------------------------------------------
            break;       
                        
            
            case 'D': //to exist the system
                

	        System.out.println("You have exited the system."); 
                exit = false;

            }
        }//end while
      }//end 
}     
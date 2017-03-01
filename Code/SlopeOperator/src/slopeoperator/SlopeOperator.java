package slopeoperator;

import java.awt.*;
import java.awt.event.*;
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
        
      SlopeOperatorUI slopeOperatorInterface = new SlopeOperatorUI();  
      slopeOperatorInterface.homeWindowSetup();
   } 
    
}

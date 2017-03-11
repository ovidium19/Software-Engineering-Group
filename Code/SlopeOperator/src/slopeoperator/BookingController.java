//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SlopeOperator
//  @ File Name : BookingController.java
//  @ Date : 15/02/2017
//  @ Author : Genaro Bedenko
//
//

package slopeoperator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Random;

/*
 *Class that contains all of the bookings
 *and performs the actions on the booking entity
 *
 * @author Genaro Bedenko
 */
public class BookingController {
    
        BookingRepoImpl BookingRepo = new BookingRepoImpl();
        
        
        
        public void book(Connection connection, int customerID, int sessionID){
             
             Booking book = new Booking();
                     
             // Generate a random ID number to assign to new booking object
             Random rndGenerator = new Random();             
             int randomNumber = rndGenerator.nextInt(9999);
             int newBookingID = 1000000 + randomNumber;
             
             // Set attributes of the new Booking object
             book.setBookingID(newBookingID);
             book.setCustomerID(customerID);
             book.setSessionID(sessionID);
             book.setCheckInStatus(false);
             
             BookingRepo.write(connection, "add", book);
        }          

        public void viewAll(Connection connection) throws SQLException{
            
            ResultSet rs = BookingRepo.read(connection);
            
            while (rs.next()) {
                
                int id = rs.getInt("BOOKINGID");
                int customerID = rs.getInt("CUSTOMERID");
                int sessionID = rs.getInt("SESSIONID");
                boolean checkIn = rs.getBoolean("CHECKINSTATUS");
        
                // print the results
                System.out.format("%s, %s, %s, %s, %s, %s\n", 
                                  id, customerID, sessionID, checkIn);
        } 
       
    }
        public void setBookingList(ArrayList bookings){
            BookingRepo.setBookings(bookings);
        }
}   

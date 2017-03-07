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

import java.util.*;

/*
 *Class that contains all of the bookings
 *and performs the actions on the booking entity
 *
 * @author Genaro Bedenko
 */
public class BookingController {
    
        BookingRepoImpl BookingRepo = new BookingRepoImpl();
	ArrayList list = new ArrayList();
         
         public void book(int customerID, int sessionID){
             
             Booking book = new Booking();
             book.setCustomerID(customerID);
             book.setSessionID(sessionID); 
             
             list.add(book);
         }          

        public String viewAll(){
        String data  = "";
        
        System.out.print("list: " + list.size());
        for (int i = 0; i<list.size(); i++){
            
                Booking temp = (Booking)list.get(i);
                String str = "\n Booking ref numbers: " + temp.getCustomerID() + " name: " + temp.getSessionID();
                data = data + str;
 
            }  
            return data;
        } 
        
        public void setBookingList(ArrayList bookings){
            BookingRepo.setBookings(bookings);
        }
}   

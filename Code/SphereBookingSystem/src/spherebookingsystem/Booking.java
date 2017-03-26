//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SlopeOperator
//  @ File Name : Booking.java
//  @ Date : 15/02/2017
//  @ Author : Genaro Bedenko
//
//

package spherebookingsystem;

/*
 *Class for the Booking entity
 *
 * @author Genaro Bedenko
 */
public class Booking {
    
         private int bookingID = -1;
	 private int customerID = -1;
         private int sessionID = -1;
         private boolean checkInStatus;
         private int bookingPrice = -1;
         private boolean customerPaidStatus;
        
         public int getBookingID(){
             return bookingID;
         }
         public void setBookingID(int aBookingID){
             bookingID = aBookingID;
         }  
         
         public int getCustomerID(){
             return customerID;
         }
         public void setCustomerID(int aRef){
             customerID = aRef;
         }
         
         public int getSessionID(){
             return sessionID;
         }
         public void setSessionID(int aName){
             sessionID = aName;
         } 
         
         public boolean getCheckInStatus(){
             return checkInStatus;
         }
         public void setCheckInStatus(boolean aCheckInStatus){
             checkInStatus = aCheckInStatus;
         }     
         
         public int getBookingPrice(){
             return bookingPrice;
         }
         public void setBookingPrice(int aBookingPrice){
             bookingPrice = aBookingPrice;
         }
         
         public boolean getCustomerPaidStatus(){
             return customerPaidStatus;
         }
         public void getCustomerPaidStatus(boolean aCustomerPaidStatus){
             customerPaidStatus = aCustomerPaidStatus;
         }
         
}

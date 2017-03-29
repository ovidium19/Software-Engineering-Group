/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

/**
 *
 * @author Genaro
 */
public class BookingFactory {
    
    public Booking createBooking(int aCustomerID, int aSessionID, float aBookingPrice, boolean aCustomerPaidStatus, int aNumberOfSkiers) {
        
        Booking booking = new Booking();
        
        
        
        if(aNumberOfSkiers == 1) {
            
            System.out.println("INDIVIDUAL BOOKING CREATED");
            
            booking = new IndividualBooking();
            
            
        }
        else if(aNumberOfSkiers > 1) {
            
            System.out.println("GROUP BOOKING CREATED");
            
            booking = new GroupBooking();
        }      
        
        booking.setCustomerID(aCustomerID);
        booking.setSessionID(aSessionID);
        booking.setBookingPrice(aBookingPrice);
        booking.setCustomerPaidStatus(aCustomerPaidStatus);
        booking.setNumberOfSkiers(aNumberOfSkiers);
        
        return(booking);
    }
    
}

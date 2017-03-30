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
    
    // Takes in the attributes of a booking object from the controller
    // Based on how many skiers there are, the factory decides which subclass of Booking to make
    // Once it creates an instance of booking, it returns it back to the controller
    public Booking createBooking(int aCustomerID, int aSessionID, float aBookingPrice, boolean aCustomerPaidStatus, int aNumberOfSkiers) {
        
        // Declares a new empty Booking object
        Booking booking = new Booking();
        
        if(aNumberOfSkiers == 1) {
        // If there is only one skier, then create the subclass IndividualBooking
        
            System.out.println("INDIVIDUAL BOOKING CREATED");
            
            booking = new IndividualBooking();
            
        }
        else if(aNumberOfSkiers > 1) {
            
        // If the number of skiers is greater than 1, then create the subclass GroupBooking    
            System.out.println("GROUP BOOKING CREATED");
            
            booking = new GroupBooking();
        }      
        
        // For the new object created, set its attributes to the ones given in the parameters
        booking.setCustomerID(aCustomerID);
        booking.setSessionID(aSessionID);
        booking.setBookingPrice(aBookingPrice);
        booking.setCustomerPaidStatus(aCustomerPaidStatus);
        booking.setNumberOfSkiers(aNumberOfSkiers);
        
        // Return the booking back to the controller
        return(booking);
    }
    
}

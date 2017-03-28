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
    
    public void createBooking(int aCustomerID, int aSessionID, float aBookingPrice, boolean aCustomerPaidStatus, int aNumberOfSkiers) {
        
        if(aNumberOfSkiers == 1) {
            
            System.out.println("INDIVIDUAL BOOKING TO BE CREATED");
            
            // CREATE INDIVIDUAL BOOKING SUBCLASS
        }
        else if(aNumberOfSkiers > 1) {
            
            System.out.println("GROUP BOOKING TO BE CREATED");
            
            // CREATE GROUP BOOKING SUBCLASS
        }
    }
    
}

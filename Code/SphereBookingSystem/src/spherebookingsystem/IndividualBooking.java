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
public class IndividualBooking extends Booking {
    
    // Getters and setters for numberOfSkiers overrides parent class
    @Override
    public int getNumberOfSkiers(){
        
        return 1;
    }
    
    @Override
    public void setNumberOfSkiers(int aNumberOfSkiers){
        
        numberOfSkiers = 1;
    }
}

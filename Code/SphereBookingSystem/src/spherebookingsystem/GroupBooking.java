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
public class GroupBooking extends Booking {
    
    @Override
    public int getNumberOfSkiers(){
        
             return numberOfSkiers;
    }
    
    @Override
    public void setNumberOfSkiers(int aNumberOfSkiers){
        
             numberOfSkiers = aNumberOfSkiers;
    }
}

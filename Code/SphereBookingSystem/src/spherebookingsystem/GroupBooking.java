package spherebookingsystem;

/**
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
 */
public class GroupBooking extends Booking {
    
    // Getters and setters for numberOfSkiers overrides parent class
    @Override
    public int getNumberOfSkiers(){
        
             return numberOfSkiers;
    }
    
    @Override
    public void setNumberOfSkiers(int aNumberOfSkiers){
        
             numberOfSkiers = aNumberOfSkiers;
    }
}

package spherebookingsystem;

/**
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
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

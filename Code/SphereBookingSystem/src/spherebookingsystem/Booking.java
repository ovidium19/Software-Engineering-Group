package spherebookingsystem;

/**
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
 */
public class Booking {
    
        // Attributes of Booking   
        private int bookingID = -1;
	private int customerID = -1;
        private int sessionID = -1;
        private boolean checkInStatus;
        private float bookingPrice = -1;
        private boolean customerPaidStatus;
        protected int numberOfSkiers = -1;
        
        // Getters and setters for all of the attributes below
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
         
        public float getBookingPrice(){
             return bookingPrice;
        }
        public void setBookingPrice(float aBookingPrice){
             bookingPrice = aBookingPrice;
        }
         
        public boolean getCustomerPaidStatus(){
             return customerPaidStatus;
        }
        public void setCustomerPaidStatus(boolean aCustomerPaidStatus){
             customerPaidStatus = aCustomerPaidStatus;
        }
         
        public int getNumberOfSkiers(){
             return numberOfSkiers;
        }
        public void setNumberOfSkiers(int aNumberOfSkiers){
             numberOfSkiers = aNumberOfSkiers;
        }
         
}

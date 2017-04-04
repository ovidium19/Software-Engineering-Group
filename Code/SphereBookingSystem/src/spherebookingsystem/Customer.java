package spherebookingsystem;

/**
 * @author Rishi Mehangra
 *         SID: 
 *         FUNCTIONALITY: REGISTER A CUSTOMER
 * 
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
 */
public class Customer {
    
        // Declare attributes a customer object will have
	private int CustomerID;
	private String FirstName;
	private String LastName;
	private String Email;
	private String TelephoneNo;
        private String Membership;
        
        // Getters and setters for the customer object attributes
        public void setCustomerID(int aCustomerID) {
	
            CustomerID = aCustomerID;
	}
	
	public void setFirstName(String aFirstName) {
	
            FirstName = aFirstName;
	}
	
	public void setLastName(String aLastName) {
	
            LastName = aLastName;
	}
	
	public void setEmail(String anEmail) {
	
            Email = anEmail;
	}
	
	public void setTelephoneNo(String aPhoneNo) {
	
            TelephoneNo = aPhoneNo;
	}

        public void setMembership(String aMembership) {
            
            Membership = aMembership;
        }
        
	public int getCustomerID() {
	
            return(this.CustomerID);
	}
	
	public String getFirstName() {
	
            return(FirstName);
	}
	
	public String getLastName() {
	
            return(LastName);
	}
	
	public String getEmail() {
	
            return(Email);
	}
	
	public String getTelephoneNo() {
	
            return(TelephoneNo);
	}
        
        public String getMembership() {
            
            return(Membership);
        }
}
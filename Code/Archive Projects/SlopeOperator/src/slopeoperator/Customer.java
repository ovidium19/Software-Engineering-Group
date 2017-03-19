//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SlopeOperator
//  @ File Name : Customer.java
//  @ Date : 15/02/2017
//  @ Author : Genaro Bedenko
//
//

package slopeoperator;

/*
 *Class for the Customer entity
 *
 * @author Genaro Bedenko
 */
public class Customer {
    
	public int CustomerID;
	public String FirstName;
	public String LastName;
	public String Email;
	public String TelephoneNo;
               
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
}
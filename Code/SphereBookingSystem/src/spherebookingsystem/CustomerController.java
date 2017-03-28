//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SlopeOperator
//  @ File Name : CustomerController.java
//  @ Date : 15/02/2017
//  @ Author : Genaro Bedenko
//
//

package spherebookingsystem;

import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 *Class that contains all of the bookings
 *and performs the actions on the booking entity
 *
 * @author Genaro Bedenko
 */
public class CustomerController {
    
	ArrayList list = new ArrayList();
        
        CustomerRepoImpl CustomerRepo = new CustomerRepoImpl();
        
        // Takes in a customerID and calls the BookingRepo to execute an SQL statement to 
        // return whether the customerID exists or not
        // Returns a boolean for whethere the customer has been registered or not
        public boolean checkCustomerID(Connection connection, String customerID) {
            
            boolean isACustomer = false;
            
            int customerIDInt = Integer.parseInt(customerID);
            
            isACustomer = CustomerRepo.checkCustomerID(connection, customerIDInt);
            
            return(isACustomer);
        }
        
        public Customer findCustomer(Connection connection, String customerID) throws SQLException {
                        
            Customer foundCustomer = new Customer();
            
            int customerIDInt = Integer.parseInt(customerID);
            
            ResultSet customerResult = CustomerRepo.findCustomer(connection, customerIDInt);
                        
            while (customerResult.next()){
                
                int foundCustomerID = customerResult.getInt("CUSTOMERID");
                foundCustomer.setCustomerID(foundCustomerID);
                
                String foundFirstName = customerResult.getString("FIRSTNAME");
                foundCustomer.setFirstName(foundFirstName);
                
                String foundLastName = customerResult.getString("LASTNAME");
                foundCustomer.setLastName(foundLastName);
                
                String foundEmail = customerResult.getString("EMAIL");
                foundCustomer.setEmail(foundEmail);
                
                String foundTelephone = customerResult.getString("TELEPHONENO");
                foundCustomer.setTelephoneNo(foundTelephone);
                
                String foundMembershipType = customerResult.getString("MEMBERSHIPTYPE");
                foundCustomer.setMembership(foundMembershipType);
                
            }
            
            System.out.println(foundCustomer.getFirstName());
            
            return(foundCustomer);
        }
        
        public Customer findCustomerByEmail(Connection connection, String email) throws SQLException {
                        
            Customer foundCustomer = new Customer();
            
            ResultSet customerResults = CustomerRepo.findCustomerByEmail(connection, email);
                        
            while (customerResults.next()){
                
                int foundCustomerID = customerResults.getInt("CUSTOMERID");
                foundCustomer.setCustomerID(foundCustomerID);
                
                String foundFirstName = customerResults.getString("FIRSTNAME");
                foundCustomer.setFirstName(foundFirstName);
                
                String foundLastName = customerResults.getString("LASTNAME");
                foundCustomer.setLastName(foundLastName);
                
                String foundEmail = customerResults.getString("EMAIL");
                foundCustomer.setEmail(foundEmail);
                
                String foundTelephone = customerResults.getString("TELEPHONENO");
                foundCustomer.setTelephoneNo(foundTelephone);
                
            }
            
            System.out.println(foundCustomer.getFirstName());
            
            return(foundCustomer);
        }
        
        public Customer findCustomerByPhone(Connection connection, String phone) throws SQLException {
                        
            Customer foundCustomer = new Customer();
            
            ResultSet customerResults = CustomerRepo.findCustomerByPhone(connection, phone);
                        
            while (customerResults.next()){
                
                int foundCustomerID = customerResults.getInt("CUSTOMERID");
                foundCustomer.setCustomerID(foundCustomerID);
                
                String foundFirstName = customerResults.getString("FIRSTNAME");
                foundCustomer.setFirstName(foundFirstName);
                
                String foundLastName = customerResults.getString("LASTNAME");
                foundCustomer.setLastName(foundLastName);
                
                String foundEmail = customerResults.getString("EMAIL");
                foundCustomer.setEmail(foundEmail);
                
                String foundTelephone = customerResults.getString("TELEPHONENO");
                foundCustomer.setTelephoneNo(foundTelephone);
                
            }
            
            System.out.println(foundCustomer.getFirstName());
            
            return(foundCustomer);
        }
        
        
        public void register(Connection connection, String firstName, String lastName, String Email, String phoneNo, String Membership){
             
             Customer customer = new Customer();
             customer.setFirstName(firstName);
             customer.setLastName(lastName);
             customer.setEmail(Email);
             customer.setTelephoneNo(phoneNo);
             customer.setMembership(Membership);
             
             CustomerRepo.write(connection, "add", customer);
             
             list.add(customer);
        }          
}   

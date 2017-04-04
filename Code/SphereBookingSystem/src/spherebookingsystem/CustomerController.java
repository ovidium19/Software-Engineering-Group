package spherebookingsystem;

import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
 * 
 * @author Rishi Mehangra
 *         SID:
 *         FUNCTIONALITY: REGISTER A CUSTOMER
 */
public class CustomerController {
    
	ArrayList list = new ArrayList();
        
        CustomerRepoImpl CustomerRepo = new CustomerRepoImpl();
        
        // Takes in a customerID and calls the BookingRepo to execute an SQL statement to 
        // return whether the customerID exists or not
        // Returns a boolean for whethere the customer has been registered or not
        public boolean checkCustomerID(Connection connection, String customerID) {
            
            // Turns the input from the UI into an integer so it can be used
            int customerIDInt = Integer.parseInt(customerID);
            
            // Call the CustomerRepo to return a boolean for where the customer exists or not
            boolean isACustomer = CustomerRepo.checkCustomerID(connection, customerIDInt);
            
            // Return the boolean as the result
            return(isACustomer);
        }
        
        // Function takes in a customer id, and returns a Customer object with all of 
        // that customer's details
        public Customer findCustomer(Connection connection, String customerID) throws SQLException {
            
            // Create new instance of Customer()
            Customer foundCustomer = new Customer();
            
            // Convert the input from the UI into an integer so it can be used
            int customerIDInt = Integer.parseInt(customerID);
            
            // Call the CustomerRepo to return a resultset from the database
            // This resultset should include one record for the customer we are trying to find
            ResultSet customerResult = CustomerRepo.findCustomer(connection, customerIDInt);
                        
            while (customerResult.next()){
            // While there is a record in the resultset, retrieve its details
            
                // Save the customer id and save it to the new object
                int foundCustomerID = customerResult.getInt("CUSTOMERID");
                foundCustomer.setCustomerID(foundCustomerID);
                
                // Save the first name and save it to the new object
                String foundFirstName = customerResult.getString("FIRSTNAME");
                foundCustomer.setFirstName(foundFirstName);
                
                // Save the last name and save it to the new object
                String foundLastName = customerResult.getString("LASTNAME");
                foundCustomer.setLastName(foundLastName);
                
                // Save the email and save it to the new object
                String foundEmail = customerResult.getString("EMAIL");
                foundCustomer.setEmail(foundEmail);
                
                // Save the telephone number and save it to the new object
                String foundTelephone = customerResult.getString("TELEPHONENO");
                foundCustomer.setTelephoneNo(foundTelephone);
                
                // Save the membership type and save it to the new object
                String foundMembershipType = customerResult.getString("MEMBERSHIPTYPE");
                foundCustomer.setMembership(foundMembershipType);
                
            }
            
            // Return the new Customer object as the result
            return(foundCustomer);
        }
        
        // Function used for when you want to find out who the customer is, based on just providing
        // their email address
        public Customer findCustomerByEmail(Connection connection, String email) throws SQLException {
              
            // Create a new instance of Customer()
            Customer foundCustomer = new Customer();
            
            // Call the CustomerRepo to provide a resultset for the customer we are trying to find
            // If it is correct, the resultset should be just one record in the database table
            ResultSet customerResults = CustomerRepo.findCustomerByEmail(connection, email);
                        
            while (customerResults.next()){
            // While there is a record in the resultset, retrieve its details
            
                // Save the customer id and save it to the new object
                int foundCustomerID = customerResults.getInt("CUSTOMERID");
                foundCustomer.setCustomerID(foundCustomerID);
                
                // Save the first name and save it to the new object
                String foundFirstName = customerResults.getString("FIRSTNAME");
                foundCustomer.setFirstName(foundFirstName);
                
                // Save the last name and save it to the new object
                String foundLastName = customerResults.getString("LASTNAME");
                foundCustomer.setLastName(foundLastName);
                
                // Save the email address and save it to the new object
                String foundEmail = customerResults.getString("EMAIL");
                foundCustomer.setEmail(foundEmail);
                
                // Save the telephone number and save it to the new object
                String foundTelephone = customerResults.getString("TELEPHONENO");
                foundCustomer.setTelephoneNo(foundTelephone);
                
            }
            
            // Return the new found customer object as the result
            return(foundCustomer);
        }
        
        // Function used for when you want to find out who the customer is, based on just providing
        // their telephone number
        public Customer findCustomerByPhone(Connection connection, String phone) throws SQLException {
                        
            // Create a new instance of Customer()
            Customer foundCustomer = new Customer();
            
            // Call the CustomerRepo to provide a resultset for the customer we are trying to find
            // If it is correct, the resultset should be just one record in the database table
            ResultSet customerResults = CustomerRepo.findCustomerByPhone(connection, phone);
                        
            while (customerResults.next()){
            // While there is a record in the resultset, retrieve its details
            
                // Save the customer id and save it to the new object
                int foundCustomerID = customerResults.getInt("CUSTOMERID");
                foundCustomer.setCustomerID(foundCustomerID);
                
                // Save the first name and save it to the new object
                String foundFirstName = customerResults.getString("FIRSTNAME");
                foundCustomer.setFirstName(foundFirstName);
                
                // Save the last name and save it to the new object
                String foundLastName = customerResults.getString("LASTNAME");
                foundCustomer.setLastName(foundLastName);
                
                // Save the email address and save it to the new object
                String foundEmail = customerResults.getString("EMAIL");
                foundCustomer.setEmail(foundEmail);
                
                // Save the telephone number and save it to the new object
                String foundTelephone = customerResults.getString("TELEPHONENO");
                foundCustomer.setTelephoneNo(foundTelephone);
                
            }
            
            // Return the foundCustomer as the result
            return(foundCustomer);
        }
        
        
        public void register(Connection connection, String firstName, String lastName, String Email, String phoneNo, String Membership){
             
            CustomerFactory CustomerFactory = new CustomerFactory();
            
            Customer RegCustomer = CustomerFactory.createCustomer(firstName, lastName, Email, phoneNo, Membership);

             CustomerRepo.write(connection, "add", RegCustomer);
             
             list.add(RegCustomer);
        }          
}   

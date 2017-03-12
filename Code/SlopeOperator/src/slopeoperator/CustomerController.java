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

package slopeoperator;

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
    
	ArrayList<Customer> list = new ArrayList();
        
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
              
        public void register(Connection connection, String firstName, String lastName, String Email, String phoneNo){
             
             Customer customer = new Customer();
             customer.setCustomerID(123);
             customer.setFirstName(firstName);
             customer.setLastName(lastName);
             customer.setEmail(Email);
             customer.setTelephoneNo(phoneNo);
             
             CustomerRepo.write(connection, "add", customer);
             
             list.add(customer);
        }          

        public String viewAll(){
        String data  = "";
        
        System.out.print("list: " + list.size());
        for (int i = 0; i<list.size(); i++){
            
                Customer temp = (Customer)list.get(i);
                String str = "\n Customer ref numbers: " + temp.getCustomerID() + " Email: " + temp.getEmail();
                data = data + str;
 
            }  
            return data;
         } 
}   

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SlopeOperator
//  @ File Name : CustomerController.java
//  @ Date : 15/02/2017
//  @ Author : Rishi Mehnagra
//
//

package slopeoperator;

import java.util.*;

/*
 *Class that contains all of the bookings
 *and performs the actions on the booking entity
 *
 * @author Rishi Mehangra
 */
public class CustomerController {
    
	ArrayList list = new ArrayList();
        
        public int generateID() {
            
            return(12313123);
        }
         
        public void register(int customerID, String firstName, String lastName, String Email, String phoneNo){
             
             Customer customer = new Customer();
             customer.setCustomerID(list.size() + 1);
             customer.setFirstName(firstName);
             customer.setLastName(lastName);
             customer.setEmail(Email);
             customer.setTelephone(phoneNo);
             
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

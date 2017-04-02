/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

/**
 *
 * @author Rick
 */
public class CustomerFactory {
    
    
    
    public Customer createCustomer(String firstName, String lastName, String Email, String phoneNo, String Membership) {
        
        Customer Customer = new Customer();
        
        if (Membership.equals("Free Membership")){
            
            System.out.println("Factory created free customer");
            Customer = new FreeCustomer();
        }
        
        else {
            
            Customer = new PaidCustomer();
            System.out.println("Factory created paid customer");
            
        }
        
        Customer.setFirstName(firstName);
        Customer.setLastName(lastName);
        Customer.setEmail(Email);
        Customer.setTelephoneNo(phoneNo);
        Customer.setMembership(Membership);

        
        return Customer;
    }

}


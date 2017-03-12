package slopeoperator;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Genaro Bedenko
 */
public class CustomerRepoImpl implements CustomerRepo {
    private ArrayList<Customer> customers;
    private ResultSet rs;
    
    public CustomerRepoImpl(){
        customers = new ArrayList<Customer>();
    }
    public CustomerRepoImpl(ArrayList list){
        customers=list;
    }
    public ArrayList<Customer> getAllCustomers(){
        return customers;
    }
    public Customer getCustomer(Customer Customer){
        return customers.get(Customer.getCustomerID()-1);
    }
       
    public void setCustomers(ArrayList list){
        customers=list;
    }
    public void createCustomer(Customer customer, Connection conn){
        customers.add(customer);
        write(conn,"add",customer);
    }
    
    public ResultSet read(Connection conn){
        System.out.println("Reading from the database... ");
        try {   
                
                Statement st = conn.createStatement();
                String sql = "SELECT * FROM BOOKINGS";
                
                rs = st.executeQuery(sql);
                   
                st.close();
                 
                
        } catch (SQLException ex) {
                    System.out.println(ex);
                    System.out.println("SQLException failed ! ");
        }
                
        return rs; 
        
    }
    public void write(Connection conn, String str, Customer customer){
        
        ArrayList list = getAllCustomers();
        System.out.println("Writing to the database... ");
        
        if (str.equals("add")){

            try {   
                Statement st = conn.createStatement();
              
                String sql = "INSERT INTO CUSTOMERS VALUES ("+ customer.getCustomerID() + " , '"
                                                            + customer.getFirstName() + "' , '"
                                                            + customer.getLastName() + "' , '"
                                                            + customer.getEmail() + "', '"
                                                            + customer.getTelephoneNo() + "')";
                System.out.println(sql);
                st.executeUpdate(sql);

                st.close();
            }
            catch (SQLException ex) {
                    System.out.println(ex);
            }         
        
        }
         
    }    

    // Function that checks if a given customerID exists in the Customers database table
    // Using a SQL statement it finds the count for the customerID, count will be at least 1 if the
    // customer exists, and therefore returns true
    // Otherwise, it returns false as the customer has not been 
    public boolean checkCustomerID(Connection conn, int customerID) {
        
        try {   
            
                Statement st = conn.createStatement();
                
                int count = 0;
                String sql = "SELECT * FROM CUSTOMERS WHERE CustomerID =" + customerID;
                System.out.println(sql);
                ResultSet rs = st.executeQuery(sql);
                
                while(rs.next()){
                    count++;
                }
                                
                st.close();
                
                if(count == 1) {
                    
                    return(true);
                }
                else {
                    
                    return(false);
                }

        }
        
        catch (SQLException ex) {
            
                    System.out.println(ex);
        }
       
       return(false);
    }
    
}  



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

    public boolean checkCustomerID(Connection conn, String customerID) {
        
       try {   
                Statement st = conn.createStatement();
              
                String sql = "SELECT COUNT(*) FROM CUSTOMERS WHERE CustomerID =" + customerID;
                System.out.println(sql);
                ResultSet rs = st.executeQuery(sql);
                
                
                st.close();
            }
            catch (SQLException ex) {
                    System.out.println(ex);
            }
       return(true);
    }
    
}  



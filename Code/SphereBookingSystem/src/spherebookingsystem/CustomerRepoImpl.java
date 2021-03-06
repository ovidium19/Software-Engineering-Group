package spherebookingsystem;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Genaro Bedenko
 */
public class CustomerRepoImpl implements CustomerRepo {
    private ArrayList customers;
    private ResultSet rs;
    private ResultSet customerDetails;
    
    public CustomerRepoImpl(){
        customers = new ArrayList();
    }
    public CustomerRepoImpl(ArrayList list){
        customers=list;
    }
    public ArrayList<Customer> getAllCustomers(){
        return customers;
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
              
                String sql = "INSERT INTO CUSTOMERS VALUES (DEFAULT,'"
                                                            + customer.getFirstName() + "' , '"
                                                            + customer.getLastName() + "' , '"
                                                            + customer.getEmail() + "', '"
                                                            + customer.getTelephoneNo() + "', '"
                                                            + customer.getMembership() + "')";
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
    @Override
    public boolean checkCustomerID(Connection conn, int customerID) {
        
        try {   
            
                Statement st;
                st = conn.createStatement();
                
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
    
    @Override
    public ResultSet findCustomer(Connection con, int customerID) {
        
        System.out.println("Reading from database...");
        
        try {   
                Statement st = con.createStatement();
                
                String sql = "SELECT * FROM CUSTOMERS WHERE CustomerID = " + customerID + "";
                System.out.println(sql);
                customerDetails = st.executeQuery(sql);

        }
        catch (SQLException ex) {
                    System.out.println(ex);
        } 
                
        return(customerDetails);
    }
    
    @Override
    public ResultSet findCustomerByEmail(Connection con, String email) {

        System.out.println("Reading from database...");
        
        try {   
                Statement st = con.createStatement();
                
                String sql = "SELECT * FROM CUSTOMERS WHERE Email = '" + email + "'";
                System.out.println(sql);
                customerDetails = st.executeQuery(sql);

        }
        catch (SQLException ex) {
                    System.out.println(ex);
        } 
                
        return(customerDetails);   
    }
    
    @Override
    public ResultSet findCustomerByPhone(Connection con, String phone) {
        
        System.out.println("Reading from database...");
        
        try {   
                Statement st = con.createStatement();
                
                String sql = "SELECT * FROM CUSTOMERS WHERE TelephoneNo = '" + phone + "'";
                System.out.println(sql);
                customerDetails = st.executeQuery(sql);

        }
        catch (SQLException ex) {
                    System.out.println(ex);
        } 
                
        return(customerDetails);
    }
    
    /*Michael Sofroni*/
    public Customer readByID(Connection conn,int customerID){
        Customer temp=new Customer();
        ResultSet rs;
        try{
            Statement st=conn.createStatement();
            String sql="SELECT * FROM CUSTOMERS WHERE CustomerID="+customerID;
            rs=st.executeQuery(sql);
            while(rs.next()){
                temp.setCustomerID(rs.getInt("CustomerID"));
                temp.setEmail(rs.getString("Email"));
                temp.setFirstName(rs.getString("Firstname"));
                temp.setLastName(rs.getString("Lastname"));
                temp.setTelephoneNo(rs.getString("Telephoneno"));
                temp.setMembership(rs.getString("Membership"));
            }
            rs.close();
            st.close();
        }
        catch(Exception ex){
            System.out.println(ex);
            return null;
        }
     return temp;
    }
  
    public Customer findCustomerbyEmail(Connection conn, String email){
        Customer temp=new Customer();
        ResultSet rs;
        try{
            Statement st=conn.createStatement();
            String sql="SELECT * FROM CUSTOMERS WHERE Email = '" + email + "'";
            rs=st.executeQuery(sql);
            while(rs.next()){
                temp.setCustomerID(rs.getInt("CustomerID"));
                temp.setEmail(rs.getString("Email"));
                temp.setFirstName(rs.getString("Firstname"));
                temp.setLastName(rs.getString("Lastname"));
                temp.setTelephoneNo(rs.getString("Telephoneno"));
                temp.setMembership(rs.getString("Membership"));
            }
            rs.close();
            st.close();
        }
        catch(Exception ex){
            System.out.println(ex);
            return null;
        }
     return temp; 
    }
    
    public Customer findCustomerbyPhone(Connection conn,String phone){
        Customer temp=new Customer();
        ResultSet rs;
        try{
            Statement st=conn.createStatement();
            String sql="SELECT * FROM CUSTOMERS WHERE TelephoneNo = '" + phone + "'";
            rs=st.executeQuery(sql);
            while(rs.next()){
                temp.setCustomerID(rs.getInt("CustomerID"));
                temp.setEmail(rs.getString("Email"));
                temp.setFirstName(rs.getString("Firstname"));
                temp.setLastName(rs.getString("Lastname"));
                temp.setTelephoneNo(rs.getString("Telephoneno"));
                temp.setMembership(rs.getString("Membership"));
            }
            rs.close();
            st.close();
        }
        catch(Exception ex){
            System.out.println(ex);
            return null;
        }
     return temp; 
    }
}  



package slopeoperator;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Genaro Bedenko
 */
public interface CustomerRepo {
    
    // Interface for functions that can be implemented by CustomerRepoImpl
    
    void write(Connection con, String str, Customer customer);
    ResultSet read(Connection con);
    
    boolean checkCustomerID(Connection con, int customerID);
    
    public ArrayList<Customer> getAllCustomers();
    public void setCustomers(ArrayList list);
}
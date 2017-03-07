package slopeoperator;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Genaro Bedenko
 */
public interface BookingRepo {
    
    // Interface for functions that can be implemented by BookingRepoImpl
    
    void write(Connection con, String str, Booking booking);
    ArrayList read(Connection con);
    
    boolean checkCustomerID(Connection con, String customerID);
    
    public ArrayList<Booking> getAllBookings();
    public void setBookings(ArrayList list);
}
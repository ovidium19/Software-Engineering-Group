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
    void read(Connection con, String str);
    
    public ArrayList<Booking> getAllBookings();
    public void setBookings(ArrayList list);
}
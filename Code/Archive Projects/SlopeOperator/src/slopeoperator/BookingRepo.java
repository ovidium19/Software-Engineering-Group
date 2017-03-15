package slopeoperator;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Genaro Bedenko
 */
public interface BookingRepo {
    
    // Interface for functions that can be implemented by BookingRepoImpl
    
    void write(Connection con, String str, Booking booking);
    List read(Connection con);
    
    
    
    public ArrayList<Booking> getAllBookings();
    public void setBookings(ArrayList list);
}
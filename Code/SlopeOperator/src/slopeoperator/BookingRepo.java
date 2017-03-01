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
    
    public void createBooking(Booking booking, Connection con);
    public Booking getBooking(Booking booking);
    public ArrayList<Booking> getAllBookings();
    public void setBookings(ArrayList list);
    void write(Connection con, String str, Booking booking);
    public ArrayList read(Connection con);
}
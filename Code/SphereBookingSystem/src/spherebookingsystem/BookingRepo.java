package spherebookingsystem;

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
    
    // Interface for functions that are implemented by BookingRepoImpl
    public ArrayList<Booking> getAllBookings();
    public Booking getBooking(Booking booking);
    public void setBookings(ArrayList list);
    public void createBooking(Booking booking, Connection conn);
    void write(Connection con, Booking booking);
    List read(Connection con);
    public ArrayList<Session> readSessionsForCustomer(Connection con,Customer cust);
    public void checkInCustomer(Connection conn, Session tempSession, Customer tempCustomer);
}
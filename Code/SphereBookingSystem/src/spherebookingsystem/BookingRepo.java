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
    void write(Connection con, Booking booking);
    List read(Connection con);
}
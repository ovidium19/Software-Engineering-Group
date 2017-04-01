package spherebookingsystem;
import java.io.PrintStream;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Genaro Bedenko
 */
public class BookingRepoImpl implements BookingRepo {
    private ArrayList<Booking> bookings;
    private ResultSet rs;
    
    public BookingRepoImpl(){
        bookings = new ArrayList<Booking>();
    }
    public BookingRepoImpl(ArrayList list){
        bookings=list;
    }
    public ArrayList<Booking> getAllBookings(){
        return bookings;
    }
    public Booking getBooking(Booking booking){
        return bookings.get(booking.getBookingID()-1);
    }
       
    public void setBookings(ArrayList list){
        bookings=list;
    }
    public void createBooking(Booking booking, Connection conn){
        bookings.add(booking);
        write(conn,booking);
    }
    
    @Override
    public List read(Connection conn){
        
        System.out.println("Reading from the database... ");
        
        List resultsList = new ArrayList();
        
        try {   
                
                Statement st;
                st = conn.createStatement();
                
                String sql = "SELECT * FROM BOOKINGS";
                
                rs = st.executeQuery(sql);
                
                while (rs.next()) {
                
                    int bookingID = rs.getInt("BOOKINGID");
                    int customerID = rs.getInt("CUSTOMERID");
                    int sessionID = rs.getInt("SESSIONID");
                    boolean checkIn = rs.getBoolean("CHECKINSTATUS");
        
                // Save the results                
                String result = bookingID + "," + customerID+ "," +sessionID+ "," + checkIn;
                
                
                resultsList.add(result);
                
                // Clear the result for the next one to be added to the string
                result = "";
                
                } 
                st.close();     
                
        } 
        
        catch (SQLException ex) {
            
                    System.out.println(ex);
                    System.out.println("SQLException failed ! ");
        }
        
        return(resultsList);
    }
    
    // Takes in a booking object and writes the attributes as a record in the database table
    @Override
    public void write(Connection conn, Booking booking){
        
        ArrayList list = getAllBookings();
        System.out.println("Writing to the database... ");
        
        try {   
                // Declare an SQL statement
                Statement st;
                st = conn.createStatement();
              
                // SQL statement to enter the values of the booking object to the database
                // BookingID is DEFAULT as the database increments the ID number itself
                String sql = "INSERT INTO BOOKINGS VALUES (DEFAULT, "
                                                            + booking.getCustomerID() + " , "
                                                            + booking.getSessionID() + " , '"
                                                            + booking.getCheckInStatus() + "' , "
                                                            + booking.getBookingPrice() + " , '"
                                                            + booking.getCustomerPaidStatus() + "' , "
                                                            + booking.getNumberOfSkiers() + ")";
                System.out.println(sql);
                
                // Execute the SQL statement
                st.executeUpdate(sql);
                
                // Closes the SQL statement
                st.close();
        }
        
        catch (SQLException ex) {
                    
                    // If there is an error with writing to the database, display the error
                    System.out.println(ex);
        }         
        
    }
         
}
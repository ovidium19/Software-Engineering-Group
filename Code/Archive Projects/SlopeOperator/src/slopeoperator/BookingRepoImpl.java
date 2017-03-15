package slopeoperator;
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
        write(conn,"add",booking);
    }
    
    public List read(Connection conn){
        
        System.out.println("Reading from the database... ");
        
        List resultsList = new ArrayList();
        
        try {   
                
                Statement st = conn.createStatement();
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
    
    public void write(Connection conn, String str, Booking booking){
        
        ArrayList list = getAllBookings();
        System.out.println("Writing to the database... ");
        
        if (str.equals("add")){

            try {   
                Statement st = conn.createStatement();
              
                String sql = "INSERT INTO BOOKINGS VALUES (" + booking.getBookingID() + " , "
                                                            + booking.getCustomerID() + " , "
                                                            + booking.getSessionID() + ", "
                                                            + booking.getCheckInStatus() + ")";
                System.out.println(sql);
                st.executeUpdate(sql);

                st.close();
            }
            catch (SQLException ex) {
                    System.out.println(ex);
            }         
        
        }
         
    }    

      
}  



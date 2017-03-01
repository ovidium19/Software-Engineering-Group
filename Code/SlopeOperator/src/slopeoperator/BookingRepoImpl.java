package slopeoperator;
import java.util.ArrayList;
import java.sql.*;


/**
 *
 * @author Genaro Bedenko
 */
public class BookingRepoImpl implements BookingRepo {
    private ArrayList<Booking> bookings;
    
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
    public ArrayList read(Connection conn){
        System.out.println("Reading from the database... ");
        ArrayList list = new ArrayList();
        try {   
                Statement st = conn.createStatement();
                
                ResultSet rs=null;
                String sql = "SELECT * FROM BOOKING";
                rs=st.executeQuery(sql);

                while(rs.next()){ 
                    Booking book = new Booking();
                    book.setCustomerID(rs.getInt("CUSTOMERID"));
                    book.setSessionID(rs.getInt("SESSIONID"));
                    boolean value = (rs.getInt("CHECKINSTATUS") == 1);
                    book.setCheckInStatus(value);
                    
                    list.add(book);
                    //System.out.println(rs.getInt("REF")+"\t"+rs.getString("NAME")+
                    //        "\t"+rs.getString("SPECIALITY"));
                }
                    rs.close();
                    st.close();

        } catch (SQLException ex) {
                    System.out.println("SQLException failed ! ");
        } 
        
        bookings = list;
        
        System.out.println("Number of bookings = " + list.size());
        return bookings;
    }
    public void write(Connection conn, String str, Booking booking){
        
        ArrayList list = getAllBookings();
        System.out.println("Writing to the database... ");
        
        if (str.equals("add")){

            try {   
                Statement st = conn.createStatement();
              
                String sql = "INSERT INTO BOOKING VALUES (DEFAULT"+", " + booking.getCustomerID() + " , " + booking.getSessionID() + "', '"
                        +booking.getCheckInStatus()+")";
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



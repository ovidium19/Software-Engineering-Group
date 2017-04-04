package spherebookingsystem;

import java.io.PrintStream;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
 * 
 * @author Michael Sofroni
 *         SID: 
 *         FUNCTIONALITY: CHECK IN CUSTOMER
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
    
    // Reads all of the records from the booking database table
    @Override
    public List read(Connection conn){
        
        // Show that it is reading from the database
        System.out.println("Reading from the database... ");
        
        // Declare a list for the results to be added to
        List resultsList = new ArrayList();
        
        try {   
                // Declare a statement to be run
                Statement st;
                st = conn.createStatement();
                
                // SQL statement is just to read all the bookings
                String sql = "SELECT * FROM BOOKINGS";
                
                rs = st.executeQuery(sql);
                
                while (rs.next()) {
                // While there is a next booking in the result set,
                // take the attributes and save them as a result
                
                    int bookingID = rs.getInt("BOOKINGID");
                    int customerID = rs.getInt("CUSTOMERID");
                    int sessionID = rs.getInt("SESSIONID");
                    boolean checkIn = rs.getBoolean("CHECKINSTATUS");
        
                // Save the results                
                String result = bookingID + "," + customerID+ "," +sessionID+ "," + checkIn;
                
                // Add it to the list of results
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
    
    //Michael Sofroni
    public ArrayList<Session> readSessionsForCustomer(Connection con,Customer cust){
        ArrayList<Session> temp=new ArrayList();
        try{
            Statement st=con.createStatement();
            String sql="Select * from Session where id in("
                    + "select sessionid from bookings where customerid="+cust.getCustomerID()+")";
            System.out.println(sql);
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                Session tempSession=new Session();
                tempSession.setId(rs.getInt("id"));
                tempSession.setStartTime(rs.getString("starttime"));
                tempSession.setEndTime(rs.getString("endtime"));
                tempSession.setInstructorId(rs.getInt("instructorid"));
                tempSession.setSlopeId(rs.getInt("slopeid"));
                tempSession.setMaxBookings(rs.getInt("maxbookings"));
                tempSession.setDate(LocalDate.parse(rs.getString("date")));
                tempSession.setPrice(rs.getFloat("price"));
                tempSession.setDescription(rs.getString("description"));
                temp.add(tempSession);
            }
            rs.close();
            st.close();
        }catch(Exception ex){System.out.println(ex);}
        return temp;
        }
    
    //Michael Sofroni
    public void checkInCustomer(Connection conn, Session tempSession, Customer tempCustomer){
        try{
            Statement st=conn.createStatement();
            String sql="Update Bookings set checkinstatus=true where customerid="+tempCustomer.getCustomerID()
                    +" and sessionid="+tempSession.getId();
            System.out.println(sql);
            int res=st.executeUpdate(sql);
            if (res>0) System.out.println("Succesful");
            else System.out.println("Unsuccesful");
        }
        catch(Exception ex){System.out.println(ex);}
    }
         
}
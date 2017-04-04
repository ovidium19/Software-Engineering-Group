package spherebookingsystem;

import java.sql.Connection;

/**
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
 */
public class BookingController {
    
        // Create an instance of the BookingRepo
        BookingRepoImpl BookingRepo = new BookingRepoImpl();
        
        // Function that takes in a customer id, session id, booking price, paid status and number of skiers
        // Calls the BookingFactory to create a new instance of Booking() with the new attributes
        // Passes the object to the booking repo to write this to the database table
        public Booking book(Connection connection, int customerID, int sessionID, float bookingPrice, boolean customerPaidStatus, int numberOfSkiers){
             
            // Creates an instance of BookingFactory
            BookingFactory bookingFactory = new BookingFactory();
            
            // Sends attributes to the BookingFactory for it to decide which subclass
            // of Booking to create based on the attributes
            Booking book = bookingFactory.createBooking(customerID,
                                                        sessionID,
                                                        bookingPrice,
                                                        customerPaidStatus,
                                                        numberOfSkiers);
            
            // Perform write in booking repo to add a new record
            BookingRepo.write(connection, book);
             
            return(book);
        }          
}   
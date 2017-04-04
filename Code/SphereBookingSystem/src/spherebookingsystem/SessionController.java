
package spherebookingsystem;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.Toggle;

/**
 *
 * @author Ovidiu Mitroi
 *         SID: 6832432
 *         FUNCTIONALITY: ADD A SESSION
 * 
 * @author Genaro Bedenko
 *         SID: 7060234
 *         FUNCTIONALITY: BOOK A SESSION
 */
public class SessionController {
    /*
    sessionRepo -- an instance of SessionRepoImpl in order to be able to write the session to the database
    insRepo -- an instance of InstructorRepoImpl in order to be able to get available instructores.
    slopeRepo - an instance of SlopeRepoImpl meant to read available slopers from the database
    */
    private SessionRepoImpl sessionRepo = new SessionRepoImpl();
    private ArrayList<Instructor> availInstructors;
    private ArrayList<Slope> availSlopes;
    private InstructorRepoImpl insRepo = new InstructorRepoImpl();
    private SlopeRepoImpl slopeRepo=new SlopeRepoImpl();
    
    public ArrayList<Instructor> getAllInstructors(Connection con){
        insRepo.read(con);
        return insRepo.getInstructors();
        
    }
    public ArrayList<Slope> getAllSlopes(Connection con){
        slopeRepo.read(con);
        return slopeRepo.getSlopes();
    }
    public int countAllSessions(Connection con){
        return sessionRepo.countSessions(con);
    }
    public void setInstructorList(Connection con,LocalDate date,String start,String end){
        //read available instructors from db. The query to read available instructors
        //requires a date and a starttime and endtime
        insRepo.read(con, date, start, end);
        availInstructors=insRepo.getInstructors();
    }
    public ArrayList<Instructor> getInstructors(){
        return availInstructors;
    }
    public void setSlopeList(Connection con,LocalDate date,String start,String end){
        //read available slopes from db. The query to read available instructors
        //requires a date and a starttime and endtime
        slopeRepo.read(con,date,start,end);
        availSlopes=slopeRepo.getSlopes();
    }
    public ArrayList<Slope> getSlopes(){
        return availSlopes;
    }
   
    public void addSession(Connection con,Session session){
        //send a Session instance to the SessionRepoImpl to be written in the db
        sessionRepo.addSession(session, con);
    }
    
    
    
    
    // Function that takes in a date and session type
    // Calls the session repo to retieve a resultset from the SQL database table
    // Returns a list of strings for all sessions that match the entered date and session type
    public List findSessions(Connection conn, LocalDate date, String sessionType, int numberOfSkiers) throws SQLException {
                    
            List<String> sessions = new ArrayList<String>();
        
            // Calls the sessionRepo to find all sessions that match the input criteria
            // Returns a resultset of records from the database table
            ResultSet sessionsResults = sessionRepo.findSessions(conn, date, sessionType, numberOfSkiers);
                        
            while (sessionsResults.next()) {
            // While there is a next record on the resultset, add it to the array of timeslots
            
                    // Save the session id to a string
                    int sessionID = sessionsResults.getInt("ID");
                    Integer.toString(sessionID);
                    
                    // Save the other fields from the resultset to strings
                    String startTime = sessionsResults.getString("STARTTIME");
                    String endTime = sessionsResults.getString("ENDTIME");
                    String price = sessionsResults.getString("PRICE");
                    
                    // Timeslot is shown as a string with all the variables linked together
                    String overallTime = "Session ID " + sessionID + ": " + startTime + " - " + endTime + " (£" + price + ")";
                    
                    // Add the timeslot to the list of sessions that meet the criteria
                    sessions.add(overallTime);
            }
            
            // Return the list of timeslots as the result
            return(sessions);
    }
    
    // Function that takes in the id number of a specific session, and returns a Session
    // object with all of that specific session's attributes
    public Session findChosenSession(Connection conn, String sessionID) throws SQLException {
            
            // Declare a new empty Session object
            Session foundSession = new Session();
            
            // Convert the session id into an integer so we can use it
            int sessionIDInt = Integer.parseInt(sessionID);
        
            // Call the sessionRepo to return the resultset with the session id and its record
            // in the database table
            ResultSet sessionResult = sessionRepo.findChosenSession(conn, sessionIDInt);
                        
            while (sessionResult.next()) {
            // While there is a next record on the resultset, add it to the array of timeslots
                    
                    // Save the id number and add it to the object
                    int foundSessionID = sessionResult.getInt("ID");
                    foundSession.setId(foundSessionID);
                    
                    // Save the start time and save it to the object
                    String foundStartTime = sessionResult.getString("STARTTIME");
                    foundSession.setStartTime(foundStartTime);
                    
                    // Save the end time and save it to the object
                    String foundEndTime = sessionResult.getString("ENDTIME");
                    foundSession.setEndTime(foundEndTime);
                    
                    // Save the date, convert to a LocalDate datatype and save it to the object
                    Date foundDate = sessionResult.getDate("DATE");
                    LocalDate foundDateLocalDate = new java.sql.Date(foundDate.getTime()).toLocalDate();
                    foundSession.setDate(foundDateLocalDate);
                    
                    // Save the maxbookings and save it to the object
                    int foundMaxBookings = sessionResult.getInt("MAXBOOKINGS");
                    foundSession.setMaxBookings(foundMaxBookings);
                    
                    // Save the slope id and save it to the object
                    int foundSlopeID = sessionResult.getInt("SLOPEID");
                    foundSession.setSlopeId(foundSlopeID);
                    
                    // Save the instructor id and save it to the object
                    int foundInstructorID = sessionResult.getInt("INSTRUCTORID");
                    foundSession.setInstructorId(foundInstructorID);
                    
                    // Save the session price and save it to the object
                    float foundPrice = sessionResult.getFloat("PRICE");
                    foundSession.setPrice(foundPrice);
                    
                    // Save the session description and save it to the object
                    String foundDescription = sessionResult.getString("DESCRIPTION");
                    foundSession.setDescription(foundDescription);
            
            }
            
            // Return the new Session object as the result
            return(foundSession);
    }
    
}
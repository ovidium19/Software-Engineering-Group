
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
    
    
    /*
    Implemented by Genaro Bedenko for his functionality
    */
    // Function that takes in a date and session type
    // Calls the session repo to retieve a resultset from the SQL database table
    // Returns a list of strings for all sessions that match the entered date and session type
    public List findSessions(Connection conn, LocalDate date, String sessionType, int numberOfSkiers) throws SQLException {
                    
            List<String> sessions = new ArrayList<String>();
        
            ResultSet sessionsResults = sessionRepo.findSessions(conn, date, sessionType, numberOfSkiers);
                        
            while (sessionsResults.next()) {
            // While there is a next record on the resultset, add it to the array of timeslots
            
                    int sessionID = sessionsResults.getInt("ID");
                    Integer.toString(sessionID);
                    
                    String startTime = sessionsResults.getString("STARTTIME");
                    String endTime = sessionsResults.getString("ENDTIME");
                    String price = sessionsResults.getString("PRICE");
                    
                    String overallTime = "Session ID " + sessionID + ": " + startTime + " - " + endTime + " (£" + price + ")";
                    sessions.add(overallTime);
            }
            
            return(sessions);
    }
    
    public Session findChosenSession(Connection conn, String sessionID) throws SQLException {
            
            Session foundSession = new Session();
            
            int sessionIDInt = Integer.parseInt(sessionID);
        
            ResultSet sessionResult = sessionRepo.findChosenSession(conn, sessionIDInt);
                        
            while (sessionResult.next()) {
            // While there is a next record on the resultset, add it to the array of timeslots
            
                    int foundSessionID = sessionResult.getInt("ID");
                    foundSession.setId(foundSessionID);
                    
                    String foundStartTime = sessionResult.getString("STARTTIME");
                    foundSession.setStartTime(foundStartTime);
                    
                    String foundEndTime = sessionResult.getString("ENDTIME");
                    foundSession.setEndTime(foundEndTime);
                    
                    Date foundDate = sessionResult.getDate("DATE");
                    LocalDate foundDateLocalDate = new java.sql.Date(foundDate.getTime()).toLocalDate();
                    foundSession.setDate(foundDateLocalDate);
                    
                    int foundMaxBookings = sessionResult.getInt("MAXBOOKINGS");
                    foundSession.setMaxBookings(foundMaxBookings);
                    
                    int foundSlopeID = sessionResult.getInt("SLOPEID");
                    foundSession.setSlopeId(foundSlopeID);
                    
                    int foundInstructorID = sessionResult.getInt("INSTRUCTORID");
                    foundSession.setInstructorId(foundInstructorID);
                    
                    float foundPrice = sessionResult.getFloat("PRICE");
                    foundSession.setPrice(foundPrice);
                    
                    String foundDescription = sessionResult.getString("DESCRIPTION");
                    foundSession.setDescription(foundDescription);
            
            }
            
            return(foundSession);
    }
    
    
}
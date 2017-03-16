/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.Toggle;

/**
 *
 * @author BOCU
 */
public class SessionController {
    SessionRepoImpl SessionRepo = new SessionRepoImpl();
    private ArrayList<String> instructorNames;
    private ArrayList <String> slopeNames;
    
    public void setInstructorNames(Connection con){
        instructorNames=SessionRepo.readInstructors(con);
    }
    public ArrayList getInstructorNames(){
        return instructorNames;
    }
    public void setSlopeNames(Connection con){
        slopeNames=SessionRepo.readSlopes(con);
    }
    public ArrayList getSlopeNames(){
        return slopeNames;
    }
    public void addASession(String startTime,String endTime, LocalDate date, int maxBookings,int slopeID,int instructorId, int price, 
                            String description, Connection conn){
        Session sess = new Session();
        sess.setStartTime(startTime);
        sess.setEndTime(endTime);
        sess.setDate(date);
        sess.setMaxBookings(maxBookings);
        sess.setSlopeId(slopeID);
        sess.setInstructorId(instructorId);
        sess.setPrice(price);
        sess.setDescription(description);
        SessionRepo.addSession(sess, conn);
        
    }
    public void viewDetails(int id){
        ArrayList list = SessionRepo.getAllSessions();
                 boolean found  = false;
         int i = 0;
         while (i<list.size() && !found){
                Session temp = (Session)list.get(i);
                if (id == temp.getId()){
                    System.out.print("Session details: \n startTime: " + temp.getStartTime()+ 
                     "\n endTime: " +  temp.getEndTime()+ "\n");
                    
                    found = true;
                }
            i++;
            }  

    }
    public void seeAllSessions(){
        ArrayList list = SessionRepo.getAllSessions();
        for (int i=0;i<list.size();i++){
            Session temp = (Session)list.get(i);
            System.out.print("Session details: \n startTime: " + temp.getStartTime()+ 
                     "\n endTime: " +  temp.getEndTime()+ "\n");
        }
    }
    public void setSessionList(ArrayList sessions){
        SessionRepo.setSessions(sessions);
    }
    
    // Function that takes in a date and session type
    // Calls the session repo to retieve a resultset from the SQL database table
    // Returns a list of strings for all sessions that match the entered date and session type
    public List checkDate(Connection conn, LocalDate date, String sessionType) throws SQLException {
                    
            List<String> sessions = new ArrayList<String>();
        
            ResultSet sessionsResults = SessionRepo.checkDate(conn, date, sessionType);
                        
            while (sessionsResults.next()) {
            // While there is a next record on the resultset, add it to the array of timeslots
            
                    int sessionID = sessionsResults.getInt("ID");
                    Integer.toString(sessionID);
                    
                    String startTime = sessionsResults.getString("STARTTIME");
                    String endTime = sessionsResults.getString("ENDTIME");
                    String price = sessionsResults.getString("PRICE");
                    
                    String overallTime = sessionID + ": " + startTime + " - " + endTime + " (£" + price + ")";
                    System.out.println(overallTime);
                    sessions.add(overallTime);
            }
            
            return(sessions);
    }
    
}
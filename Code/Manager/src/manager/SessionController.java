/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;
import java.sql.*;
import java.util.ArrayList;



/**
 *
 * @author BOCU
 */
public class SessionController {
    SessionRepoImpl SessionRepo = new SessionRepoImpl();
    
    public void addASession(Time startTime,Time endTime, Date date, int maxBookings,int slopeID,int instructorId,Connection conn){
        Session sess = new Session();
        sess.setStartTime(startTime);
        sess.setEndTime(endTime);
        sess.setDate(date);
        sess.setMaxBookings(maxBookings);
        sess.setSlopeId(slopeID);
        sess.setInstructorId(instructorId);
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
    
}

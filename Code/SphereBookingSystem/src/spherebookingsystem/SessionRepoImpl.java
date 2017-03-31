
package spherebookingsystem;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;


/**
 *
 * @author Ovidiu Mitroi
 *         SID: 6832432
 *         FUNCTIONALITY: ADD A SESSION
 */
public class SessionRepoImpl implements SessionRepo {
    private ResultSet sessionsForThisDay;
    private ResultSet foundSession;
    
    public SessionRepoImpl(){
        
    }    
    @Override
    public void addSession(Session session, Connection conn){
        //takes a Session parameter and adds it to the db
        write(conn,"add",session);
    }
    
    @Override
    public Session readSessionByID(Connection conn,int id){
        //read a Session by its id
        Session temp = new Session();
        String query = "SELECT * from Session where sessionid="+String.valueOf(id)+";";
        try
        {
            Statement st = conn.createStatement();
            ResultSet rs=null;
            rs=st.executeQuery(query);
            while (rs.next()){
                temp.setId(rs.getInt("sessionid"));
                temp.setStartTime(rs.getString("starttime"));
                temp.setEndTime(rs.getString("endtime"));
                temp.setDate(LocalDate.parse(rs.getString("date")));
                temp.setMaxBookings(rs.getInt("maxbookings"));
                temp.setDescription(rs.getString("description"));
                temp.setInstructorId(rs.getInt("instructorid"));
                temp.setSlopeId(rs.getInt("slopeid"));
                temp.setPrice(rs.getFloat("price"));
                
            }
            rs.close();
            st.close();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        return temp;
    }
   
    @Override
    public void write(Connection conn, String str, Session session){
        //Writes a Session instance to the DB
        System.out.println("Writing to the database... ");
        
        if (str.equals("add")){

            try {   
                Statement st = conn.createStatement();
              
                String sql = "INSERT INTO SESSION VALUES (DEFAULT"+", '" + session.getStartTime() + "' , '" + session.getEndTime() + "', '"
                        +session.getDate()+"', "+ session.getMaxBookings()+", " + session.getSlopeId()+", " + session.getInstructorId()+
                        ", "+session.getPrice()+", '"+session.getDescription()+ "')";
                System.out.println(sql);
                st.executeUpdate(sql);

                st.close();
                System.out.println("Succesful!");
            }
            catch (SQLException ex) {
                    System.out.println(ex);
            }         
        
        }
         
    }
    
    @Override
    public ResultSet findSessions(Connection conn, LocalDate date, String sessionType, int numberOfSkiers) {
               
        
        System.out.println("Reading from database...");
        
        if(sessionType.equals("With Instructor ")) {
        // If they ask for an instructor, run the SQL command with Instructor ID NOT NULL
        
            try {   
                Statement st = conn.createStatement();
                
                String sql = "SELECT * FROM SESSION WHERE Date = '" + date + "' AND  MaxBookings >= " + numberOfSkiers + " AND InstructorID IS NOT NULL";
                System.out.println(sql);
                sessionsForThisDay = st.executeQuery(sql);

            }
            catch (SQLException ex) {
                    System.out.println(ex);
            } 
            
        }
        else {
        // Otherwise, they don't want an instructor, so the SQL command has InstructorID IS NULL
            
           try {   
                Statement st = conn.createStatement();
                
                String sql = "SELECT * FROM SESSION WHERE Date = '" + date + "' AND  InstructorID IS NULL";
                System.out.println(sql);
                sessionsForThisDay = st.executeQuery(sql);

            }
            catch (SQLException ex) {
                    System.out.println(ex);
            } 
        }
        
        
        return(sessionsForThisDay);
    }
    
    @Override
    public ResultSet findChosenSession(Connection con, int sessionIDInt) {
        
        System.out.println("Reading from database...");
        
        try {   
                Statement st = con.createStatement();
                
                String sql = "SELECT * FROM SESSION WHERE ID = " + sessionIDInt + "";
                System.out.println(sql);
                foundSession = st.executeQuery(sql);

        }
        catch (SQLException ex) {
                    System.out.println(ex);
        } 
        
        return(foundSession);
    }
    
    
}  
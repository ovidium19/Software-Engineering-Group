/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;


/**
 *
 * @author BOCU
 */
public class SessionRepoImpl implements SessionRepo {
    private ArrayList<Session> sessions;
    private ResultSet sessionsForThisDay;
    
    public SessionRepoImpl(){
        sessions = new ArrayList<Session>();
    }
    public SessionRepoImpl(ArrayList list){
        sessions=list;
    }
    
    @Override
    public ArrayList<Session> getAllSessions(){
        return sessions;
    }
    
    @Override
    public Session getSession(Session session){
        Session temp = new Session();
        return sessions.get(session.getId()-1);
    }
    
    @Override
    public void setSessions(ArrayList<Session> list){
        sessions=list;
    }
    
    @Override
    public void addSession(Session session, Connection conn){
        sessions.add(session);
        write(conn,"add",session);
    }
    
    @Override
    public Session readSessionByID(Connection conn,int id){
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
    public ArrayList readInstructors(Connection conn){
        System.out.println("Reading instructor names from db...");
        ArrayList list = new ArrayList();
        try
        {
            Statement st = conn.createStatement();
            ResultSet rs=null;
            String sql = "SELECT name from INSTRUCTOR";
            rs=st.executeQuery(sql);
            
            while (rs.next()){
                list.add(rs.getString("NAME"));
                
            }
            rs.close();
            st.close();
            
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        return list;
    }
    
    @Override
    public ArrayList readSlopes(Connection conn){
        System.out.println("Reading slopes names from db...");
        ArrayList list = new ArrayList();
        try
        {
            Statement st = conn.createStatement();
            ResultSet rs=null;
            String sql = "SELECT name from SLOPE";
            rs=st.executeQuery(sql);
            
            while (rs.next()){
                list.add(rs.getString("NAME"));
                
            }
            rs.close();
            st.close();
            
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        return list;
    }
   
    @Override
    public void write(Connection conn, String str, Session session){
        
        ArrayList list = getAllSessions();
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

    public ResultSet checkDate(Connection conn, LocalDate date, String sessionType) {
               
        
        System.out.println("Reading from database...");
        
        if(sessionType == "With Instructor ") {
        // If they ask for an instructor, run the SQL command with Instructor ID NOT NULL
        
            try {   
                Statement st = conn.createStatement();
                
                String sql = "SELECT * FROM SESSION WHERE Date = '" + date + "' AND  InstructorID IS NOT NULL";
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
}  
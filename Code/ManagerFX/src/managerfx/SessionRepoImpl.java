/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerfx;
import java.util.ArrayList;
import java.sql.*;


/**
 *
 * @author BOCU
 */
public class SessionRepoImpl implements SessionRepo {
    private ArrayList<Session> sessions;
    
    public SessionRepoImpl(){
        sessions = new ArrayList<Session>();
    }
    public SessionRepoImpl(ArrayList list){
        sessions=list;
    }
    public ArrayList<Session> getAllSessions(){
        return sessions;
    }
    public Session getSession(Session session){
        return sessions.get(session.getId()-1);
    }
    @Override
    public void setSessions(ArrayList list){
        sessions=list;
    }
    @Override
    public void addSession(Session session, Connection conn){
        sessions.add(session);
        write(conn,"add",session);
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
    /*
    public ArrayList read(Connection conn){
        System.out.println("Reading from the database... ");
        ArrayList list = new ArrayList();
        try {   
                Statement st = conn.createStatement();
                
                ResultSet rs=null;
                String sql = "SELECT * FROM SESSION";
                rs=st.executeQuery(sql);

                while(rs.next()){ 
                    Session sess = new Session();
                    sess.setId(rs.getInt("ID"));
                    sess.setStartTime(Time.valueOf(rs.getString("STARTTIME")));
                    sess.setEndTime(Time.valueOf(rs.getString("ENDTIME")));
                    sess.setDate(Date.valueOf(rs.getString("DATE")));
                    sess.setMaxBookings(rs.getInt("MAXBOOKINGS"));
                    sess.setInstructorId(rs.getInt("INSTRUCTORID"));
                    sess.setSlopeId(rs.getInt("SLOPEID"));
                    
                    list.add(sess);
                    //System.out.println(rs.getInt("REF")+"\t"+rs.getString("NAME")+
                    //        "\t"+rs.getString("SPECIALITY"));
                }
                    rs.close();
                    st.close();

        } catch (SQLException ex) {
                    System.out.println("SQLException failed ! ");
        } 
        
        sessions = list;
        
        System.out.println("sessions..." + list.size());
        return sessions;
    }
*/
    public void write(Connection conn, String str, Session session){
        
        ArrayList list = getAllSessions();
        System.out.println("Writing to the database... ");
        
        if (str.equals("add")){

            try {   
                Statement st = conn.createStatement();
              
                String sql = "INSERT INTO SESSION VALUES (DEFAULT"+", '" + session.getStartTime() + "' , '" + session.getEndTime() + "', '"
                        +session.getDate()+"', "+ session.getMaxBookings()+", " + session.getSlopeId()+", " + session.getInstructorId()+")";
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



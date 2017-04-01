
package spherebookingsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author Ovidiu Mitroi
 *         SID: 6832432
 *         FUNCTIONALITY: ADD A SESSION
 */
public class SlopeRepoImpl implements SlopeRepo {
    private ArrayList<Slope> slopes;

    public SlopeRepoImpl() {
        slopes=new ArrayList();
    }
    
    @Override
    public ArrayList<Slope> getSlopes() {
        return slopes;
    }

    @Override
    public void setSlopes(ArrayList<Slope> rSlopes) {
        slopes=rSlopes;
    }

    @Override
    public void addSlope(Slope slope) {
        slopes.add(slope);
        
    }

    @Override
    public void write(Connection con, Slope slope) {
        //insert a slope in the db
        try{
            Statement st=con.createStatement();
            
            String sql = "INSERT INTO SLOPE VALUES (DEFAULT,'"+slope.getName()+"',"+slope.getLength()+")";
            st.executeUpdate(sql);
            
            st.close();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
    

    @Override
    public void read(Connection con, LocalDate date, String startT, String endT) {
        /*
        Reads slopes from the database that don't have a Session booked
        on a time slot that overlasp with the time slot described in the parameters
        (Date, Starttime - Endtime)
        */
        if(slopes.size()>0)
                slopes.clear();
        try{
            Slope temp;
            Statement st=con.createStatement();
            ResultSet rs=null;
            String sql = "SELECT * from slope where id in ("
                       + "SELECT id from slope "
                       + "group by id "
                       + "having id not in ("
                       + "select distinct(slopeid) from Session " +
                         "where date='"+date.toString()+"' and starttime<='"+endT+"' "
                       + "and endtime>='"+startT+"'))"; 
            System.out.println(sql);
            rs=st.executeQuery(sql);
            while (rs.next()){
                temp = new Slope();
                temp.setId(rs.getInt("id"));
                temp.setName(rs.getString("name"));
                temp.setLength(rs.getInt("length"));
                
                slopes.add(temp);
            }
            rs.close();
            st.close();             
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    @Override
    public void read(Connection con) {
        /*
        Reads slopes from the database that don't have a Session booked
        on a time slot that overlasp with the time slot described in the parameters
        (Date, Starttime - Endtime)
        */
        if(slopes.size()>0)
                slopes.clear();
        try{
            Slope temp;
            Statement st=con.createStatement();
            ResultSet rs=null;
            String sql = "SELECT * from Slope"; 
            System.out.println(sql);
            rs=st.executeQuery(sql);
            while (rs.next()){
                temp = new Slope();
                temp.setId(rs.getInt("id"));
                temp.setName(rs.getString("name"));
                temp.setLength(rs.getInt("length"));
                
                slopes.add(temp);
            }
            rs.close();
            st.close();             
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    @Override
    public Slope read(Connection con, int id){
        Slope tempSlope=new Slope();
        try{
            Statement st=con.createStatement();
            String sql="Select * from slope where id="+id;
            System.out.println(sql);
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                tempSlope.setId(rs.getInt("id"));
                tempSlope.setLength(rs.getInt("length"));
                tempSlope.setName(rs.getString("name"));
                
            }
            rs.close();
            st.close();
        }
        catch(Exception ex){System.out.println(ex);}
        return tempSlope;
        }
}

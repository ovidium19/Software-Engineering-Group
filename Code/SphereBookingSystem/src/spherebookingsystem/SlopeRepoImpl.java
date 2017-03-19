/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author BOCU
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
    
}

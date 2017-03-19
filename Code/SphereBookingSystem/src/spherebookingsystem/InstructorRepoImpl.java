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
public class InstructorRepoImpl implements InstructorRepo {
    private ArrayList<Instructor> instructors;
    
    public InstructorRepoImpl(){
        instructors=new ArrayList();
    }
    @Override
    public ArrayList<Instructor> getInstructors(){
        return instructors;
    }
    @Override
    public void addInstructor(Instructor ins){
        instructors.add(ins);
    }
    @Override
    public void setInstructors(ArrayList<Instructor> insList){
        instructors=insList;
    }
    @Override
    public void write(Connection con, Instructor ins){
        try{
            Statement st=con.createStatement();
            
            String sql = "INSERT INTO INSTRUCTOR VALUES (DEFAULT,'"+ins.getName()+"',"+ins.getAge()+",'"+
                        ins.getSpeciality()+"','"+ins.getPersonalStatement()+"')";
            st.executeUpdate(sql);
            
            st.close();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
    @Override
    public void read(Connection con,LocalDate date,String startTime,String endTime){
        
        if(instructors.size()>0)
                instructors.clear();
        try{
            Instructor temp;
            Statement st=con.createStatement();
            ResultSet rs=null;
            String sql = "SELECT * from instructor where id in ("
                       + "SELECT id from instructor "
                       + "group by id "
                       + "having id not in ("
                       + "select distinct(instructorid) from Session " +
                         "where date='"+date.toString()+"' and starttime<='"+endTime+"' "
                       + "and endtime>='"+startTime+"'))"; 
            System.out.println(sql);
            rs=st.executeQuery(sql);
            while (rs.next()){
                temp = new Instructor();
                temp.setInstrId(rs.getInt("id"));
                temp.setName(rs.getString("name"));
                temp.setAge(rs.getInt("age"));
                temp.setSpeciality(rs.getString("speciality"));
                temp.setPersonalStatement(rs.getString("personalstatement"));
                instructors.add(temp);
            }
            rs.close();
            st.close();             
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    
    } 
    


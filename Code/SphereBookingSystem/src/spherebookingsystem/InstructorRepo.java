/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.util.ArrayList;
import java.sql.Connection;
import java.time.LocalDate;

/**
 *
 * @author BOCU
 */
public interface InstructorRepo {
    public ArrayList<Instructor> getInstructors();
    public void addInstructor(Instructor ins);
    public void setInstructors(ArrayList<Instructor> insList);
    void write(Connection con, Instructor ins);
    void read(Connection con,LocalDate date,String startTime,String endTime);
    public void read(Connection con);
    public Instructor read(Connection con, int id);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;
import java.sql.*;

/**
 *
 * @author BOCU
 */
public class Session {
    private int id;
    private java.sql.Time startTime=new Time(0,0,0);
    private java.sql.Time endTime=new Time(0,0,0);
    private Date date=new Date(0,0,0);
    private int maxBookings=0;
    private int slopeId=0;
    private int instructorId=0;

    public int getId() {
        return id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Date getDate() {
        return date;
    }

    public int getMaxBookings() {
        return maxBookings;
    }

    public int getSlopeId() {
        return slopeId;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMaxBookings(int maxBookings) {
        this.maxBookings = maxBookings;
    }

    public void setSlopeId(int slopeId) {
        this.slopeId = slopeId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
   
    
    
    
    
    
    
}

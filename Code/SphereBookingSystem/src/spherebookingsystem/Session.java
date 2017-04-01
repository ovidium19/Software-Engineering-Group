/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author BOCU
 */
public class Session {
    private int id;
    private String startTime;
    private String endTime;
    private LocalDate date;
    private int maxBookings;
    private int slopeId;
    private int instructorId;
    private float price;
    private String description;
    
    public Session(){
        this.id=0;
        this.startTime=null;
        this.endTime=null;
        this.date=null;
        this.maxBookings=-1;
        this.slopeId=-1;
        this.instructorId=-1;
        this.price=-1;
        this.description="";
    }
    public Session(int id,String startTime,String endTime,LocalDate date,int maxBookings,int slopeId,int instructorId
                    ,int price, String description){
        this.id=id;
        this.startTime=startTime;
        this.endTime=endTime;
        this.date=date;
        this.maxBookings=maxBookings;
        this.slopeId=slopeId;
        this.instructorId=instructorId;
        this.price=price;
        this.description=description;
    }
    
    public int getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public LocalDate getDate() {
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
        this.id=id;
    }

    public void setStartTime(String startTime) {
        this.startTime=startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime=endTime;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setMaxBookings(int maxBookings) {
        this.maxBookings=maxBookings;
    }

    public void setSlopeId(int slopeId) {
        this.slopeId = slopeId;
    }
    
    public float getPrice(){
        return this.price;
    }
    public void setPrice(float price){
        this.price=price;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
    public String toString(){
        return this.id+" "+this.startTime+":"+this.endTime;
    }
   
    
    
    
    
    
    
}

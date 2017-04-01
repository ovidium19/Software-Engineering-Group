/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

/**
 *
 * @author BOCU
 */
public class Instructor {
    private int instrId;
    private String name;
    private int age;
    private String speciality;
    private String personalStatement;
    
    public Instructor(){
        name="None";
        speciality="";
        personalStatement="";
        instrId=0;
    }
    public Instructor(int id,String iName,int iAge,String iSpeciality,String iPersonalStatement){
        instrId=id;
        name=iName;
        age=iAge;
        speciality=iSpeciality;
        personalStatement=iPersonalStatement;
    }

    public int getInstrId() {
        return instrId;
    }

    public void setInstrId(int rinstrId) {
        instrId = rinstrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String iName) {
        name = iName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int rage) {
        age = rage;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String ispeciality) {
        speciality = ispeciality;
    }

    public String getPersonalStatement() {
        return personalStatement;
    }

    public void setPersonalStatement(String ipersonalStatement) {
        personalStatement = ipersonalStatement;
    }
    
    public String toString(){
        return this.name;
    }
}

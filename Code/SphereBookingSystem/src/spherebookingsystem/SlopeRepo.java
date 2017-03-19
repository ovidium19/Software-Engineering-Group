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
public interface SlopeRepo {
    public ArrayList<Slope> getSlopes();
    public void setSlopes(ArrayList<Slope> rSlopes);
    public void addSlope(Slope slope);
    void write(Connection con,Slope slope);
    void read(Connection con,LocalDate date,String startT,String endT);
}

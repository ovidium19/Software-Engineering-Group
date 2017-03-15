/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author BOCU
 */
public interface SessionRepo {
    public void addSession(Session session, Connection con);
    public Session getSession(Session session);
    public ArrayList<Session> getAllSessions();
    public void setSessions(ArrayList list);
    void write(Connection con,String str,Session session);
    public Session readSessionByID(Connection con,int id);
    public ArrayList readInstructors(Connection con);
    public ArrayList readSlopes(Connection con);
    public ResultSet checkDate(Connection con, LocalDate date, String sessionType);
}
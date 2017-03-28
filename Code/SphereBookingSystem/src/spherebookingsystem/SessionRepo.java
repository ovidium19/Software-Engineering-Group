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
    void write(Connection con,String str,Session session);
    public Session readSessionByID(Connection con,int id);
    
    public ResultSet findSessions(Connection con, LocalDate date, String sessionType);
    public ResultSet findChosenSession(Connection con, int sessionIDInt);
}
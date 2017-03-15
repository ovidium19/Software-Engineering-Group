/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slopeoperator;
import java.sql.*;
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
    public ArrayList read(Connection con);
}

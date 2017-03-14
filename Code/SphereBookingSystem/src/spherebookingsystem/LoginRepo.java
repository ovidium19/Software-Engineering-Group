/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;
import java.sql.*;
/**
 *
 * @author BOCU
 */
public interface LoginRepo {
    public Login getLogin ();
    public void addLogin (Login login, Connection conn);
    public void setLogin(Login login);
    void write(Connection conn,Login login);
    Login read(Connection conn,Login login);
}

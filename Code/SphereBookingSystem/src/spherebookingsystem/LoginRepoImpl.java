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
public class LoginRepoImpl implements LoginRepo {
    private Login login;
    
    public LoginRepoImpl(){
        login=new Login();
    }
    public LoginRepoImpl(Login loginSet){
        login=loginSet;
    }
    @Override
    public Login getLogin(){
        return login;
    }
    @Override
    public void setLogin(Login loginSet){
        login=loginSet;
    }
    @Override
    public void addLogin(Login temp, Connection conn){
        login=temp;
        write(conn,login);
    }
    @Override
    public void write(Connection conn, Login loginSet){
        Login temp = getLogin();
        try{
            Statement st=conn.createStatement();
            String query = "INSERT into LOGIN VALUES(DEFAULT,"+temp.getLoginid()+",'"+temp.getUsername()+
                            "', '"+temp.getPassword()+"','"+temp.getUsertype()+"');";
            st.executeUpdate(query);
            st.close();
        }
        catch (SQLException ex) {
                    System.out.println(ex);
            }  
    }
    @Override
    public Login read(Connection conn, Login temp){
       try{
           
       Statement st=conn.createStatement();
       String query="Select * from Logins where username='"+temp.getUsername()+"' and password='"+
                    temp.getPassword()+"'";
       System.out.println(query);
       ResultSet rs=null;
       rs=st.executeQuery(query);
       login=new Login();
       while (rs.next()){
           login.setLoginid(rs.getInt("loginid"));
           login.setUsername(rs.getString("username"));
           login.setPassword(rs.getString("password"));
           login.setUsertype(rs.getString("usertype"));
       }
       rs.close();
       st.close();
       }
       catch (SQLException ex){
           System.out.println(ex);
       }
       return login;
    }
}

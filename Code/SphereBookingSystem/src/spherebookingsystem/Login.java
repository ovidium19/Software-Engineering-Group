/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.util.logging.Logger;

/**
 *
 * @author BOCU
 */
public class Login {
    private int loginid;
    private String username;
    private String password;
    private String usertype;
    
    public Login(){
        loginid=-1;
        username=null;
        password=null;
        usertype=null;
    }
    public Login(int id,String uName,String uPass,String uType){
        loginid=id;
        username=uName;
        password=uPass;
        usertype=uType;
    }
    public Login(String uName,String uPass){
        username=uName;
        password=uPass;
        loginid=-1;
        usertype=null;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    

    public int getLoginid() {
        return loginid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsertype() {
        return usertype;
    }
}

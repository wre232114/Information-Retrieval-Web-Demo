package cn.brightasdream.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 实现了RowMapper接口后可以用于与数据库中的某一行对应，必须重载mapRow方法确定对应关系
 * @see org.springframework.jdbc.core.RowMapper
 */
public class User implements RowMapper<User>{
    private String userName;
    private String psword;
    private String email;

    public User(){}
    public User(String userName,String psword,String email){
        this.userName = userName;
        this.psword = psword;
        this.email = email;
    }

    //set和get方法
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }
    public void setPsWord(String psword){
        this.psword = psword;
    }
    public String getPsword(){
        return psword;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    /**
     * 将对象和数据库中一行匹配，并将一行读取出来存在对象中返回
     */
    @Override
    public User mapRow(ResultSet rs,int rownum) throws SQLException{
        String userName = rs.getString("username");
        String psword = rs.getString("psword");
        String email = rs.getString("email");
        User user = new User();
        user.setEmail(email);
        user.setPsWord(psword);
        user.setUserName(userName);
        return user;
    }
}
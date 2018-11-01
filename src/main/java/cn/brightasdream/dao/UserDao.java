package cn.brightasdream.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import cn.brightasdream.model.User;

@Repository
public class UserDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 从数据库中查找满足条件的user
     */
    public User getUser(String userName){
        String sql = "SELECT * FROM Users WHERE username=?";
        //查询一个对象并将结果返回到User中，new Object[]{userName}表示新建一个数据，并初始化,Object[]中的是匹配?的参数
        User user = jdbcTemplate.queryForObject(sql, new Object[]{userName},new User());
        return user;
    }

    /**
     * 比较用户名和密码是否匹配
     */
    public boolean isUserMatch(User user){
        String querySql = "SELECT * FROM Users WHERE username=? AND psword=?";
        try{
            jdbcTemplate.queryForObject(querySql, new Object[]{user.getUserName(),user.getPsword()},new User());
            return true;
        }catch(DataAccessException e){
            return false;
        }
    }

    /**
     * 查询数据库中是否存在用户，如果存在，则将返回true，否则返回false
     */
    public boolean checkUser(User user){
        String queryUserSql = "SELECT * FROM Users WHERE username=?";
        try{
            jdbcTemplate.queryForObject(queryUserSql, new Object[]{user.getUserName()},new User());
            return true;
        }catch(DataAccessException e){
            return false;
        }
    }

    /**
     * 注册用户
     */
    public boolean registerUser(User user){
        
        String insertUserSql = "INSERT INTO Users VALUES(?,?,?,?)";
        int result = jdbcTemplate.update(insertUserSql, new PreparedStatementSetter(){ //创建匿名类接口实现数据库更新操作
            @Override
            public void setValues(PreparedStatement ps) throws SQLException{//重载方法，传入预处理对象，并为参数设置值
                ps.setString(1, user.getUserName());
                ps.setString(2,user.getPsword());
                ps.setString(3, user.getEmail());
                ps.setString(4, null);
            }
        });
        if(result > 0) //如果受影响的行数大于0，则插入成功
            return true;
        return false;
    }

    /**
     * 注销用户
     */
    public boolean deleteUser(User user){
        String deleteUserSql = "DELETE FROM Users WHERE username=?";
        int result = jdbcTemplate.update(deleteUserSql,new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException{
                ps.setString(1,user.getUserName());
            }
        });
        if(result > 0) return true;
        return false;
    }

    /**
     * 更新用户信息
     * @param attribute 更新的属性名
     * @param vaue 属性值
     * @param user 需要更新的用户
     */
    public boolean updataUser(User user,String attribute,String value){
        String updateUserSql = "UPDATE Users SET ?=? WHERE username=?";
        int result = jdbcTemplate.update(updateUserSql,new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException{
                ps.setString(1,attribute);
                ps.setString(2, value);
                ps.setString(3,user.getUserName());
            }
        });
        if(result > 0) return true;
        else return false;
    }
}
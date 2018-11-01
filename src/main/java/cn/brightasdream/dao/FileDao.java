package cn.brightasdream.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import cn.brightasdream.authorization.types.Constants;
import cn.brightasdream.model.FileModel;
import cn.brightasdream.model.ResBody;

@Repository
public class FileDao{
    @Autowired
    JdbcTemplate jdbcTemplate; // jdbc用于数据库操作

    /**
     * 向数据库中添加一个文件
     */
    public boolean addFile(FileModel fileModel){
        //需要格式化时间
        long uploadtime = Long.parseLong(fileModel.getUploadtime());
        Timestamp start_time = new Timestamp(uploadtime);
        String insertSQL = "INSERT INTO Files VALUES(?,?,?,?,?,?,?)"; // 用问号占位，后面可以用其他方式传入参数
        int result = jdbcTemplate.update(insertSQL, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException{
                ps.setString(1, fileModel.getFilename());
                ps.setString(2, fileModel.getSavename());
                ps.setString(3, fileModel.getUsername());
                ps.setString(4, fileModel.getFiletype());
                ps.setLong(5, fileModel.getFilesize());
                ps.setTimestamp(6,start_time);
                ps.setString(7, fileModel.getDestination());
            }
        });
        if(result > 0) return true;
        else return false; 
    }

    /**
     * 查询数据库中的某一个用户的所有文件
     */
    public List<FileModel> getAllFile(String username,ResBody resBody){
        String querySQL = "SELECT * FROM Files WHERE username='"+username+"'";
        try{
            List<FileModel> fileList = jdbcTemplate.query(querySQL, new FileModel());
            resBody.setStatus(Constants.TASK_GETALL_SUCCESS);
            resBody.setContent(fileList);
            return fileList;
        }catch(DataAccessException e){
            resBody.setStatus(Constants.TASK_GETALL_FAIL);
            resBody.setContent(e.toString());
            return null;
        }
    }
}
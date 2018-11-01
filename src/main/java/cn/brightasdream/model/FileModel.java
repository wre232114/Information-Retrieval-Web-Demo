package cn.brightasdream.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 文件上传模型
 */

 public class FileModel implements RowMapper<FileModel>{
    private String filename;
    private String savename;
    private String username;
    private long filesize;
    private String filetype;
    private String uploadtime;
    private String destination;

    public void setFilename(String filename){
        this.filename = filename;
    }
    public String getFilename(){
        return filename;
    }
    public void setSavename(String savename){
        this.savename = savename;
    }
    public String getSavename(){
        return savename;
    }
    public void setFilesize(long filesize){
        this.filesize = filesize;
    }
    public long getFilesize(){
        return filesize;
    }
    public void setFiletype(String filetype){
        this.filetype = filetype;
    }
    public String getFiletype(){
        return filetype;
    }
    public void setUploadTime(String uploadtime){
        this.uploadtime = uploadtime;
    }
    public String getUploadtime(){
        return uploadtime;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public String getDestination(){
        return destination;
    }
    @Override
	public FileModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        FileModel fileModel = new FileModel();
        fileModel.setFilename(rs.getString("filename"));
        fileModel.setSavename(rs.getString("savename"));
        fileModel.setUsername(rs.getString("username"));
        fileModel.setFiletype(rs.getString("filetype"));
        fileModel.setFilesize(rs.getLong("filesize"));
        fileModel.setUploadTime(rs.getString("uploadtime"));
        fileModel.setDestination(rs.getString("destination"));
		return fileModel;
	}
 }
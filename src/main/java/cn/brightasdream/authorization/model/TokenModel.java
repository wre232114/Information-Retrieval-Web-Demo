package cn.brightasdream.authorization.model;


public class TokenModel{
    private String userName;//用户名
    private String token;//用户token

    public TokenModel(){}
    public TokenModel(String userName,String token){
        this.userName = userName;
        this.token = token;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }

    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return token;
    }
}
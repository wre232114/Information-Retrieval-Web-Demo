package cn.brightasdream.model;

/**
 * 自定义返回结构
 * @author bright
 */

 public class ResBody{
     /**
      * 返回状态值
      */
    private int status;

    /**
     * 返回的内容
     */
    private Object content;

    public ResBody(){}
    public ResBody(int status,Object content){
        this.status = status;
        this.content = content;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public void setContent(Object content){
        this.content = content;
    }
    public int getStatus(){
        return status;
    }

    public Object getContent(){
        return content;
    }
 }
package cn.brightasdream.common;

import java.io.Serializable;

/**
 * 倒排索引中的索引结构类，能够比较和克隆
 */
public class IndexStructure extends IndexComparator implements Cloneable,Serializable{
    // 每一个人的id
    private String personID;
    // 每一个人的某一条信息id，等于该信息名
    private String attrID;
    // 词项在信息内容中的开始位置
    private int startPos;
    // 词项在信息内容中的结束位置
    private int endPos;

    public void setPersonID(String personID){
        this.personID = personID;
    }
    public String getPersonID(){
        return personID;
    }

    public void setAttrID(String attrID){
        this.attrID = attrID;
    }
    public String getAttrID(){
        return attrID;
    }

    public void setStartPos(int start){
        this.startPos = start;
    }
    public int getStartPos(){
        return startPos;
    }

    public void setEndPos(int end){
        this.endPos = end;
    }
    public int getEndPos(){
        return endPos;
    }

    @Override
    public Object clone(){
        IndexStructure structure = null;
        try {
            structure = (IndexStructure) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return structure;
    }
}
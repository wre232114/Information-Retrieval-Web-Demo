package cn.brightasdream.authorization.types;
/**
 * 返回的客户端的状态常量定义
 * @see cn.brightasdream.services
 */
public class Constants{
    public final static int LOGIN_SUCCESS = 1000;
    public final static int LOGIN_FAIL = 1001;
    public final static int REGISTER_SUCCESS = 1100;
    public final static int REGISTER_FAIL = 1101;
    public final static int CHECK_TOKEN_SUCCESS = 1003;
    //获取任务相关的状态常量
    public final static int TASK_ADD_SUCCESS = 1200;
    public final static int TASK_ADD_FAIL = 1201;
    public final static int TASK_GETALL_SUCCESS = 1203;
    public final static int TASK_GETALL_FAIL = 1204;
    public final static int TASK_GETTODO_SUCCESS = 1205;
    public final static int TASK_GETTODO_FAIL = 1206;
    //不需要特殊标记的均状态均计为SUCCESS or FAIL
    public final static int SUCCESS = 1;
    public final static int FAIL = 0;
}
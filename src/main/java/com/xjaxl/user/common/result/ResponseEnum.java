package com.xjaxl.user.common.result;

/**
 * 响应枚举
 *
 * @author JYX
 * @version 1.0.0
 * @date 2023-03-23
 */
public enum ResponseEnum implements ResponseEnumInterface {

    OK(0, "成功"),
    ERROR_REQUEST_METHOD(405, "请求方法有误"),
    ERROR_REQUEST_BODY(400, "请求参数有误"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_SQL(9998, "SQL语法错误"),
    LOGIN_TIMEOUT(9997, "登陆过期"),
    ACT_NO_PERMISSION(8001,"当前用户无发起权限"),
    ACT_NO_FIRSTNODE(8002,"未能获取首节点信息！请联系管理员检查流程配置"),
    ACT_NO_PROCDEF_OBJ(8003,"未能正确查询部署的流程信息，请联系管理员"),
    ACT_REQUEST_POCESSVO(8004,"流程请求参数异常"),
    ACT_NO_ASSIGNEE(8005,"未能找到审核人，请联系管理员检查流程配置"),
    ACT_NO_NODERULE(8008,"未能获取节点规则信息！请联系管理员检查流程配置"),

    ACT_NO_USERS_IN_RULE(8009,"未能查询到人员信息！请联系管理员检查流程配置"),

    ACT_NO_MODEL_INFO(8010,"未查询到模型信息！请联系管理员检查配置"),

    ACT_NO_TASK(8006,"无此任务"),

    ACT_NO_MODELINFO(8020,"未查询到模型信息！请联系管理员检查配置"),

    PROJECT_INFO_NOT_EXIST(8007,"项目信息不存在");

    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}

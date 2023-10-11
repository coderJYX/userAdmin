package com.xjaxl.user.entity.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserVO {

    /**
     *  用户类型【1普通用户  0管理员】
     */
    @NotNull(message = "用户类型不可为空")
    private String userType;

    /**
     *  用户名不可为空
     */
    @NotBlank(message = "用户名不可为空")
    private String username;

    /**
     *  密码
     */
    @NotBlank(message = "密码不可为空")
    private String password;

    /**
     *  盐
     */
    private String salt;

    /**
     *  真实姓名
     */
    @NotBlank(message = "真实姓名不可为空")
    private String realName;

    /**
     *  年龄
     */
    @NotBlank(message = "年龄不可为空")
    private String age;

    /**
     *  单位
     */
    @NotBlank(message = "单位不可为空")
    private String unit;

    /**
     *  角色
     */
    @NotBlank(message = "角色不可为空")
    private String role;

    /**
     *  用户性别【 1：男  2：女 3：未知】
     */
    @NotBlank(message = "用户性别不可为空")
    private String gender;

    /**
     *  在线状态【 1：在线  0离线】
     */
    private String onlineType;

    /**
     *  备注
     */
    private String remark;

}

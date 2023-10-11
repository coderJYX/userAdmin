package com.xjaxl.user.entity.rvo.user;

import com.xjaxl.user.entity.dto.user.SysUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRVO {

    private String id;
    /**
     *  用户类型【1普通用户  0管理员】
     */
    private String userType;

    /**
     *  用户名不可为空
     */
    private String username;

    /**
     *  真实姓名
     */
    private String realName;

    /**
     *  年龄
     */
    private String age;

    /**
     *  单位
     */
    private String unit;

    /**
     *  角色
     */
    private String role;

    /**
     *  用户性别【 1：男  2：女   3：未知】
     */
    private String gender;

    /**
     *  在线状态【 1：在线  0离线】
     */
    private String onlineType;

    private String remark;

    private String token;
}

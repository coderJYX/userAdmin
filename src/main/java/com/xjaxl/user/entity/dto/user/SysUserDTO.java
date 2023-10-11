package com.xjaxl.user.entity.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xjaxl.user.common.base.AbstractGeneralDTO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author JYX
 * @Date 2023-10-10
 */

@TableName(value ="sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUserDTO extends AbstractGeneralDTO {

    /**
     *  用户类型【1普通用户  0管理员】
     */
    private String userType;

    /**
     *  用户名不可为空
     */
    private String username;

    /**
     *  密码
     */
    private String password;

    /**
     *  盐
     */
    private String salt;

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

    /**
     *  备注
     */
    private String remark;

}
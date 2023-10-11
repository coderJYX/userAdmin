package com.xjaxl.user.common.base;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public abstract class AbstractIdDTO implements Serializable {
    private static final long serialVersionUID = 7752221565461211624L;

    @TableId(type = IdType.INPUT)
    protected String id;

    protected Integer delFlag;

}

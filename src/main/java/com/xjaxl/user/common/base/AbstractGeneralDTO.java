package com.xjaxl.user.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class AbstractGeneralDTO extends AbstractBaseDTO {
    protected String updaterId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected String updateTime;
}

package com.xjaxl.user.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractBaseDTO extends AbstractIdDTO{
    protected String creatorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected String createTime;
}

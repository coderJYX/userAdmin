package com.xjaxl.user.common.base;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
public class PageVO {

    @Positive
    @NotNull(message = "页码不能为空")
    private Integer pageNum;

    @Positive
    @NotNull(message = "每页数量不能为空")
    private Integer pageSize;

}

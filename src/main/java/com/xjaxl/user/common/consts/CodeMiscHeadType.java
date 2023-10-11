package com.xjaxl.user.common.consts;

public enum CodeMiscHeadType {

    SYS_USER("user");

    private final String type;

    CodeMiscHeadType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
}

package com.cd.uap.bean;

/**
 * Created by li.mingyang on 2018/4/20.
 */
public enum ReviewStatusCode {

    UNREVIEW(0L,"审核"),
    PASS(1L,"通过"),
    UNPASS(2L,"未通过");

    private Long code;

    private String name;

    ReviewStatusCode(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

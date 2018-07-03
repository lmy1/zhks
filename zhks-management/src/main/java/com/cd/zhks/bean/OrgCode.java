package com.cd.zhks.bean;

/**
 * 描述:
 * ----机构的常量
 *
 * @author ma.xiaofeng
 * @create 2018-05-28 20:04
 */
public enum  OrgCode {

    DISEASE_CONTROL_CENTER("镇海区疾病预防控制中心", 135L)
    ;

    private String orgName;
    private Long orgId;

    OrgCode(String orgName, Long orgId) {
        this.orgName = orgName;
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
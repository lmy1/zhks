package com.cd.uap.bean.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by li.mingyang on 2018/5/4.
 */
public class RegistUserDto {

    @NotBlank(message = "身份证号不能为空")
    //@Pattern(message = "身份证格式错误", regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)")
    private String  idCard;


    @NotBlank(message = "用户姓名不能为空")
    @Size(message = "用户姓名长度必须在20之内", max = 20)
    private String name;


    @NotNull(message = "性别不能为空")
    private Long sex;

    @NotBlank(message = "手机号不能为空")
    @Pattern(message = "手机号格式错误", regexp = "^(1[356789]{1})\\d{9}$")
    private String telephone;

    @NotNull(message="单位不能为空")
    private String orgCode;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}

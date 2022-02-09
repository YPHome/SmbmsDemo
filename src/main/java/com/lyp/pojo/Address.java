package com.lyp.pojo;

import java.math.BigInteger;
import java.util.Date;

public class Address {
    private BigInteger id;
    private String contact;
    private String addressDesc;
    private String postCode;
    private String tel;
    private BigInteger createBy;
    private Date createDate;
    private Date modifyBy;
    private Date modifyDate;
    private BigInteger userId;

    public Address(BigInteger id, String contact, String addressDesc, String postCode, String tel, BigInteger createBy, Date createDate, Date modifyBy, Date modifyDate, BigInteger userId) {
        this.id = id;
        this.contact = contact;
        this.addressDesc = addressDesc;
        this.postCode = postCode;
        this.tel = tel;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.userId = userId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public BigInteger getCreateBy() {
        return createBy;
    }

    public void setCreateBy(BigInteger createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Date modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
}

package vn.com.unit.ep2p.admin.controller;

import java.util.Date;


import jp.sf.amateras.mirage.annotation.Column;

/**
 * AbstractEntity
 * 
 * @version 01-00
 * @since 01-00
 * @author CongDT
 */
public abstract class AbstractEntity {

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate != null ? (Date) createdDate.clone() : null;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate != null ? (Date) createdDate.clone() : null;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate != null ? (Date) updatedDate.clone() : null;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate != null ? (Date) updatedDate.clone() : null;
    }

}

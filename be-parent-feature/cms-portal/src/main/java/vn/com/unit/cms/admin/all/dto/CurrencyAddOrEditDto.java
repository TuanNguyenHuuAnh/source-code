package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

public class CurrencyAddOrEditDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** name */
    private String name;

    /** description */
    private String description;

    /** note */
    private String note;

    /** create date */
    private Date createDate;

    /** update date */
    private Date updateDate;

    /** delete date */
    private Date deleteDate;

    /** creator */
    private String createBy;

    /** updater */
    private String updateBy;

    /** deleter */
    private String deleteBy;

    /** language list */
    private List<CurrencyLanguageDto> currencyLanguageList;

    /** url */
    private String url;

    private String status;
    
    private String currencyComment;
    
    private String publishBy;
    
    private String approveBy;

    /**
     * get Id
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * set id
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get code
     * 
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * set code
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * get name
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get description
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get note
     * 
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * get note
     * 
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * get create date
     * 
     * @return
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * set create date
     * 
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * get update date
     * 
     * @return
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * set update date
     * 
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * get delete date
     * 
     * @return
     */
    public Date getDeleteDate() {
        return deleteDate;
    }

    /**
     * set delete date
     * 
     * @param deleteDate
     */
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    /**
     * get creator
     * 
     * @return
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * set creator
     * 
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * get updater
     * 
     * @return
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * set updater
     * 
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * get deleter
     * 
     * @return
     */
    public String getDeleteBy() {
        return deleteBy;
    }

    /**
     * set deleter
     * 
     * @param deleteBy
     */
    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    /**
     * get currency language list
     * 
     * @return
     */
    public List<CurrencyLanguageDto> getCurrencyLanguageList() {
        return currencyLanguageList;
    }

    /**
     * set currency language list
     * 
     * @param currencyLanguageList
     */
    public void setCurrencyLanguageList(List<CurrencyLanguageDto> currencyLanguageList) {
        this.currencyLanguageList = currencyLanguageList;
    }

    /**
     * get url
     * 
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * set url
     * 
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getCurrencyComment() {
        return currencyComment;
    }

    public void setCurrencyComment(String currencyComment) {
        this.currencyComment = currencyComment;
    }

    public String getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

}

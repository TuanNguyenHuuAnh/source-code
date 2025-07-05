package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

public class CurrencyListDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** name */
    private String name;

    /** title */
    private String title;

    /** description */
    private String description;

    /** create date */
    private Date createDate;

    /** creator */
    private String createBy;

    private String status;
    
//    private String currencyComment;

    /**
     * get id
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * get title
     * 
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * set title
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

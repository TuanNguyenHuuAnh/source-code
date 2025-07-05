package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_currency")
public class Currency extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CURRENCY")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;
    
    @Column(name = "currency_comment")
    private String currencyComment;
    
    @Column(name= "publish_date")
    private Date publishDate;
    
    @Column(name = "publish_by")
    private String publishBy;
    
    @Column(name= "approve_date")
    private Date approveDate;
    
    @Column(name = "approve_by")
    private String approveBy;
    
    @Column(name= "create_date")
    private Date createDate;
    
    @Column(name = "create_by")
    private String createBy;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    
    public Date getPublishDate() {
        return publishDate;
    }

    
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    
    public String getPublishBy() {
        return publishBy;
    }

    
    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

}

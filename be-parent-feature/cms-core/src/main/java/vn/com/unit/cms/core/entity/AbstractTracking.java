/*******************************************************************************
 * Class        AbstractTracking
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       KhoaNA
 * Change log   2017/02/1401-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.cms.core.entity;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;

/**
 * AbstractTracking
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public abstract class AbstractTracking {

    @Column(name = "create_date")
    private Date createDate;
    
    @Column(name = "create_by")
    private String createBy;
    
    @Column(name = "update_date")
    private Date updateDate;
    
    @Column(name = "update_by")
    private String updateBy;
    
    @Column(name = "delete_date")
    private Date deleteDate;
    
    @Column(name = "delete_by")
    private String deleteBy;

    /**
     * Get createDate
     * @return Date
     * @author KhoaNA
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get createBy
     * @return String
     * @author KhoaNA
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Set createBy
     * @param   createBy
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Get updateDate
     * @return Date
     * @author KhoaNA
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Set updateDate
     * @param   updateDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Get updateBy
     * @return String
     * @author KhoaNA
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * Set updateBy
     * @param   updateBy
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * Get deleteDate
     * @return Date
     * @author KhoaNA
     */
    public Date getDeleteDate() {
        return deleteDate;
    }

    /**
     * Set deleteDate
     * @param   deleteDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    /**
     * Get deleteBy
     * @return String
     * @author KhoaNA
     */
    public String getDeleteBy() {
        return deleteBy;
    }

    /**
     * Set deleteBy
     * @param   deleteBy
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }
}

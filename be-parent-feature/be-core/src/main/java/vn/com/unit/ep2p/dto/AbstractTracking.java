/*******************************************************************************
 * Class        AbstractTracking
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       KhoaNA
 * Change log   2017/02/1401-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

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

    @Column(name = "created_date")
    private Date createdDate;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "updated_date")
    private Date updatedDate;
    
    @Column(name = "updated_by")
    private String updatedBy;
    
    @Column(name = "deleted_date")
    private Date deletedDate;
    
    @Column(name = "deleted_by")
    private String deletedBy;

    /**
     * Get createdDate
     * @return Date
     * @author KhoaNA
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     * @param   createdDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get createdBy
     * @return String
     * @author KhoaNA
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set createdBy
     * @param   createdBy
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get updatedDate
     * @return Date
     * @author KhoaNA
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set updatedDate
     * @param   updatedDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get updatedBy
     * @return String
     * @author KhoaNA
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set updatedBy
     * @param   updatedBy
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get deletedDate
     * @return Date
     * @author KhoaNA
     */
    public Date getDeletedDate() {
        return deletedDate;
    }

    /**
     * Set deletedDate
     * @param   deletedDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    /**
     * Get deletedBy
     * @return String
     * @author KhoaNA
     */
    public String getDeletedBy() {
        return deletedBy;
    }

    /**
     * Set deletedBy
     * @param   deletedBy
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
}

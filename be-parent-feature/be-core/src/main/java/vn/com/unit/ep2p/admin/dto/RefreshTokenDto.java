/*******************************************************************************
 * Class        :RefreshTokenDto
 * Created date :2019/07/01
 * Lasted date  :2019/07/01
 * Author       :HungHT
 * Change log   :2019/07/01:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

/**
 * RefreshTokenDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class RefreshTokenDto {

    private Long id;
    private Long accountId;
    private String refreshToken;
    private String os;
    private String versionApp;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

    /**
     * Set id
     * 
     * @param id
     *            type Long
     * @return
     * @author HungHT
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get id
     * 
     * @return Long
     * @author HungHT
     */
    public Long getId() {
        return id;
    }

    /**
     * Set accountId
     * 
     * @param accountId
     *            type Long
     * @return
     * @author HungHT
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * Get accountId
     * 
     * @return Long
     * @author HungHT
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * Set refreshToken
     * 
     * @param refreshToken
     *            type String
     * @return
     * @author HungHT
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Get refreshToken
     * 
     * @return String
     * @author HungHT
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Set createdBy
     * 
     * @param createdBy
     *            type String
     * @return
     * @author HungHT
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get createdBy
     * 
     * @return String
     * @author HungHT
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set createdDate
     * 
     * @param createdDate
     *            type Date
     * @return
     * @author HungHT
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get createdDate
     * 
     * @return Date
     * @author HungHT
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set updatedBy
     * 
     * @param updatedBy
     *            type String
     * @return
     * @author HungHT
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get updatedBy
     * 
     * @return String
     * @author HungHT
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set updatedDate
     * 
     * @param updatedDate
     *            type Date
     * @return
     * @author HungHT
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get updatedDate
     * 
     * @return Date
     * @author HungHT
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersionApp() {
        return versionApp;
    }

    public void setVersionApp(String versionApp) {
        this.versionApp = versionApp;
    }

}
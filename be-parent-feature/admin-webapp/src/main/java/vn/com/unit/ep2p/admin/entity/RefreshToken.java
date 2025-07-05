/*******************************************************************************
 * Class        :RefreshToken
 * Created date :2019/07/01
 * Lasted date  :2019/07/01
 * Author       :HungHT
 * Change log   :2019/07/01:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;

/**
 * RefreshToken
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Table(name = "JCA_API_REFRESH_TOKEN")
public class RefreshToken {

    /** Column: ID type NUMBER(22,20) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_API_REFRESH_TOKEN")
    private Long id;

    /** Column: ACCOUNT_ID type NUMBER(22,20) NOT NULL */
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /** Column: REFRESH_TOKEN type VARCHAR2(2000) NOT NULL */
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    /** Column: OS type VARCHAR2(255) NOT NULL */
    @Column(name = "OS")
    private String os;

    /** Column: VERSION_APP type VARCHAR2(255) NOT NULL */
    @Column(name = "VERSION_APP")
    private String versionApp;

    /** Column: CREATED_BY type VARCHAR2(255) NULL */
    @Column(name = "CREATED_BY")
    private String createdBy;

    /** Column: CREATED_DATE type DATE(7) NULL */
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    /** Column: UPDATED_BY type VARCHAR2(255) NULL */
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    /** Column: UPDATED_DATE type DATE(7) NULL */
    @Column(name = "UPDATED_DATE")
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
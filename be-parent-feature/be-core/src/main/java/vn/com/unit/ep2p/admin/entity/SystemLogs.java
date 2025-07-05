/*******************************************************************************
 * Class        :JcaSystemLogs
 * Created date :2017/10/25
 * Lasted date  :2017/10/25
 * Author       :HungHT
 * Change log   :2017/10/25:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.entity;

import java.util.Date;


import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;

/**
 * JCA_SYSTEM_LOGS
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Table(name = "JCA_SYSTEM_LOGS")
public class SystemLogs {

    /** Column: ID type NUMBER(22,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_SYSTEM_LOGS")
    private Long id;

    /** Column: FUNCTION_CODE type VARCHAR2(100,0) NOT NULL */
    @Column(name = "FUNCTION_CODE")
    private String functionCode;

    /** Column: LOG_SUMMARY type NVARCHAR2(255,0) NOT NULL */
    @Column(name = "LOG_SUMMARY")
    private String logSummary;

    /** Column: LOG_TYPE type NUMBER(22,0) NOT NULL */
    @Column(name = "LOG_TYPE")
    private int logType;

    /** Column: LOG_DATE type DATE(7,0) NOT NULL */
    @Column(name = "LOG_DATE")
    private Date logDate;

    /** Column: LOG_DETAIL type VARCHAR2(3000,0) NULL */
    @Column(name = "LOG_DETAIL")
    private String logDetail;

    /** Column: IP type VARCHAR2(50,0) NULL */
    @Column(name = "IP")
    private String ip;

    /** Column: USERNAME type VARCHAR2(100,0) NULL */
    @Column(name = "USERNAME")
    private String username;
    
    /** Column: COMPANY_ID type decimal(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;

    /**
     * Set id
     * @param id
     *        type Long
     * @return
     * @author HungHT
     */
    public void setId(Long id) {
         this.id = id;
    }

    /**
     * Get id
     * @return Long
     * @author HungHT
     */
    public Long getId() {
         return id;
    }

    /**
     * Set functionCode
     * @param functionCode
     *        type String
     * @return
     * @author HungHT
     */
    public void setFunctionCode(String functionCode) {
         this.functionCode = functionCode;
    }

    /**
     * Get functionCode
     * @return String
     * @author HungHT
     */
    public String getFunctionCode() {
         return functionCode;
    }

    /**
     * Set logSummary
     * @param logSummary
     *        type String
     * @return
     * @author HungHT
     */
    public void setLogSummary(String logSummary) {
         this.logSummary = logSummary;
    }

    /**
     * Get logSummary
     * @return String
     * @author HungHT
     */
    public String getLogSummary() {
         return logSummary;
    }

    /**
     * Set logType
     * @param logType
     *        type Long
     * @return
     * @author HungHT
     */
    public void setLogType(int logType) {
         this.logType = logType;
    }

    /**
     * Get logType
     * @return Long
     * @author HungHT
     */
    public int getLogType() {
         return logType;
    }

    /**
     * Set logDate
     * @param logDate
     *        type Date
     * @return
     * @author HungHT
     */
    public void setLogDate(Date logDate) {
         this.logDate = logDate;
    }

    /**
     * Get logDate
     * @return Date
     * @author HungHT
     */
    public Date getLogDate() {
         return logDate;
    }

    /**
     * Set logDetail
     * @param logDetail
     *        type String
     * @return
     * @author HungHT
     */
    public void setLogDetail(String logDetail) {
         this.logDetail = logDetail;
    }

    /**
     * Get logDetail
     * @return String
     * @author HungHT
     */
    public String getLogDetail() {
         return logDetail;
    }

    /**
     * Set ip
     * @param ip
     *        type String
     * @return
     * @author HungHT
     */
    public void setIp(String ip) {
         this.ip = ip;
    }

    /**
     * Get ip
     * @return String
     * @author HungHT
     */
    public String getIp() {
         return ip;
    }

    /**
     * Set username
     * @param username
     *        type String
     * @return
     * @author HungHT
     */
    public void setUsername(String username) {
         this.username = username;
    }

    /**
     * Get username
     * @return String
     * @author HungHT
     */
    public String getUsername() {
         return username;
    }
    
    /**
     * Get companyId
     * @return Long
     * @author HungHT
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Set companyId
     * @param   companyId
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
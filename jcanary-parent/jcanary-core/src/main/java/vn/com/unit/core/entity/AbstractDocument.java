/*******************************************************************************
 * Class        Document
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * Document
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
public class AbstractDocument extends AbstractTracking {

//    /** Column: FORM_ID type NUMBER(20,0) NULL */
//    @Column(name = "FORM_ID")
//    protected Long formId;
//
//    /** Column: DOC_SUMMARY type NVARCHAR2(255) NULL */
//    @Column(name = "DOC_SUMMARY")
//    private String docSummary;
//
//    /** Column: BUSINESS_ID type NUMBER(20,0) NULL */
//    @Column(name = "BUSINESS_ID")
//    protected Long businessId;
//
//    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NULL */
//    @Column(name = "PROCESS_DEPLOY_ID")
//    protected Long processDeployId;
//
//    /** Column: DOC_TYPE type VARCHAR2(255) NULL */
//    @Column(name = "DOC_TYPE")
//    protected String docType;
//
//    /** Column: EXPECTED_DATE type DATE NULL */
//    @Column(name = "EXPECTED_DATE")
//    protected Date expectedDate;
//
//    /** Column: DOC_TITLE type NVARCHAR2(255) NULL */
//    @Column(name = "DOC_TITLE")
//    protected String docTitle;
//    
//    /** Column: DOC_CODE type NVARCHAR2(255) NULL */
//    @Column(name = "DOC_CODE")
//    protected String docCode;
//
//    /** Column: DOC_TITLE_FULL type NVARCHAR2(255) NULL */
//    @Column(name = "DOC_TITLE_FULL")
//    protected String docTitleFull;

//    /** Column: PROCESS_STATUS_CODE type VARCHAR2(30) NULL */
//    @Column(name = "PROCESS_STATUS_CODE")
//    protected String processStatusCode;
//
//    /** Column: PROCESS_INSTANCE_ID type VARCHAR2(30) NULL */
//    @Column(name = "PROCESS_INSTANCE_ID")
//    protected String processInstanceId;
//
//    /** Column: COMMON_STATUS_CODE type VARCHAR2(30) NULL */
//    @Column(name = "COMMON_STATUS_CODE")
//    protected String commonStatusCode;

//    /** Column: OWNER_ID type NUMBER(20,0) NULL */
//    @Column(name = "OWNER_ID")
//    protected Long ownerId;

//    /** Column: COMPANY_ID type NUMBER(20,0) NOT NULL */
//    @NotNull
//    @Column(name = "COMPANY_ID")
//    protected Long companyId;
//
//    /** Column: ORG_ID type NUMBER(20,0) NULL */
//    @Column(name = "ORG_ID")
//    protected Long orgId;
//
//    /** Column: MAJOR_VERSION type NUMBER(20,0) NULL */
//    @Column(name = "MAJOR_VERSION")
//    protected Long majorVersion;
//
//    /** Column: MINOR_VERSION type NUMBER(20,0) NULL */
//    @Column(name = "MINOR_VERSION")
//    protected Long minorVersion;
//
//    /** Column: SYSTEM_CODE type VARCHAR2(255) NULL */
//    @Column(name = "SYSTEM_CODE")
//    protected String systemCode;
//
//    /** Column: APP_CODE type VARCHAR2(255) NULL */
//    @Column(name = "APP_CODE")
//    protected String appCode;
//    
//    /** Column: PRIORITY type VARCHAR2(100) NULL */
//    @Column(name = "PRIORITY")
//    protected String priority;

}

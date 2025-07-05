/*******************************************************************************
 * Class        :AbstractDocumentDto
 * Created date :2019/06/10
 * Lasted date  :2019/06/10
 * Author       :KhoaNA
 * Change log   :2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;

/**
 * AbstractDocumentDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
public class AbstractDocumentDto {

    /** Column: ID type NUMBER(20,0) NULL */
    protected Long id;
    /** Column: DOC_TITLE type NVARCHAR2(255) NULL */
    protected String docTitle;
    /** Column: DOC_TITLE_FULL type NVARCHAR2(255) NULL */
    protected String docTitleFull;
    /** Column: DOC_SUMMARY type NVARCHAR2(255) NULL */
    private String docSummary;
    /** Column: DOC_TYPE type VARCHAR2(255) NULL */
    protected String docType;
    /** Column: MAJOR_VERSION type NUMBER(20,0) NULL */
    protected Long majorVersion;
    /** Column: MINOR_VERSION type NUMBER(20,0) NULL */
    protected Long minorVersion;
    /** Column: PRIORITY type VARCHAR2(100) NULL */
    protected String priority;
    /** docCode */
    protected String docCode;
    
    /** docId */
    protected Long docId;


    /** Column: FORM_ID type NUMBER(20,0) NULL */
    protected Long formId;
    /** Column: COMPANY_ID type NUMBER(20,0) NOT NULL */
    protected Long companyId;
    /** Column: ORG_ID type NUMBER(20,0) NULL */
    protected Long orgId;



    /** Column: SYSTEM_CODE type VARCHAR2(255) NULL */
    protected String systemCode;
    /** Column: APP_CODE type VARCHAR2(255) NULL */
    protected String appCode;



    /** buttonId **/
    protected Long buttonId;
    /** stepId **/
    protected Long stepId;
    /** coreTaskId */
    protected String coreTaskId;
      /** memberCcs */
    protected List<Long> memberCcs;
    /** functionId */
    protected Long functionId;
    /** note */
    protected String note;
    
    
    /** process instance */
    private Long processStatusId;
    private Long commonStatusId;
    /** Column: PROCESS_STATUS_CODE type VARCHAR2(30) NULL */
    protected String processStatusCode;
    /** Column: PROCESS_INSTANCE_ID type VARCHAR2(30) NULL */
    protected String processInstanceId;
    /** Column: COMMON_STATUS_CODE type VARCHAR2(30) NULL */
    protected String commonStatusCode;
    /** Column: BUSINESS_ID type NUMBER(20,0) NULL */
    protected Long businessId;
    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NULL */
    protected Long processDeployId;
    /** processType */
    protected Integer processType;
    protected String docInputJson;
    protected String inputJsonEmail;
    /**
     * get old status code
     */
    protected String processStatusCodePrev;
    protected String processStatusName;
    protected String itemFunctionCode;
    
    public String generateDocTitleFull() {
        String titleFull = CoreConstant.EMPTY;
        if (docCode != null && docTitle != null) {
            titleFull = docCode.concat(CoreConstant.HYPHEN).concat(docTitle);
        }
        return titleFull;
    }

}

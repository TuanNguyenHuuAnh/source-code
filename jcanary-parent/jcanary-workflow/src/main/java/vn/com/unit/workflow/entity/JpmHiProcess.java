/*******************************************************************************
* Class        JpmHiProcess
* Created date 2021/03/02
* Lasted date  2021/03/02
* Author       KhuongTH
* Change log   2021/03/02 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * JpmHiProcess
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_HI_PROCESS)
public class JpmHiProcess extends AbstractCreatedTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_HI_PROCESS)
    private Long id;

    /** Column: PROCESS_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "PROCESS_CODE")
    private String processCode;

    /** Column: PROCESS_NAME type NVARCHAR2(255.0) NULL */
    @Column(name = "PROCESS_NAME")
    private String processName;

    /** Column: DESCRIPTION type NVARCHAR2(2000.0) NULL */
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: MAJOR_VERSION type NUMBER(10,0) NULL */
    @Column(name = "MAJOR_VERSION")
    private Long majorVersion;

    /** Column: MINOR_VERSION type NUMBER(10,0) NULL */
    @Column(name = "MINOR_VERSION")
    private Long minorVersion;

    /** Column: EFFECTIVE_DATE type DATE NULL */
    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;

    /** Column: BPMN_REPO_ID type NUMBER(20,0) NULL */
    @Column(name = "BPMN_REPO_ID")
    private Long bpmnRepoId;

    /** Column: BPMN_FILE_PATH type VARCHAR2(255.0) NULL */
    @Column(name = "BPMN_FILE_PATH")
    private String bpmnFilePath;

    /** Column: BPMN_FILE_NAME type VARCHAR2(255.0) NULL */
    @Column(name = "BPMN_FILE_NAME")
    private String bpmnFileName;

    /** Column: DEPLOYED type NUMBER(1,0) NULL */
    @Column(name = "DEPLOYED")
    private boolean deployed;

    /** Column: ACTIVED type NUMBER(1,0) NULL */
    @Column(name = "ACTIVED")
    private boolean actived;

    /** Column: BUSINESS_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "BUSINESS_ID")
    private Long businessId;

    /** Column: PROCESS_DEFINITION_ID type VARCHAR2(64.0) NULL */
    @Column(name = "PROCESS_DEFINITION_ID")
    private String processDefinitionId;

    /** Column: SHOW_WORKFLOW type NUMBER(1,0) NULL */
    @Column(name = "SHOW_WORKFLOW")
    private boolean showWorkflow;

    /** Column: COMPANY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;

    /** Column: PROCESS_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PROCESS_ID")
    private Long processId;

}
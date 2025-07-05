/*******************************************************************************
* Class        JpmButtonForStepDeploy
* Created date 2021/03/02
* Lasted date  2021/03/02
* Author       KhuongTH
* Change log   2021/03/02 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * JpmButtonForStepDeploy
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_BUTTON_FOR_STEP_DEPLOY)
public class JpmButtonForStepDeploy extends AbstractAuditTracking {

    /** Column: PROCESS_DEPLOY_ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "PROCESS_DEPLOY_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long processDeployId;

    /** Column: STEP_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Id
    @Column(name = "STEP_DEPLOY_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long stepDeployId;

    /** Column: BUTTON_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Id
    @Column(name = "BUTTON_DEPLOY_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long buttonDeployId;

    /** Column: STEP_CODE type VARCHAR2(100.0) NOT NULL */
    @Column(name = "STEP_CODE")
    private String stepCode;

    /** Column: PERMISSION_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PERMISSION_DEPLOY_ID")
    private Long permissionDeployId;

    /** Column: PERMISSION_CODE type VARCHAR2(100.0) NOT NULL */
    @Column(name = "PERMISSION_CODE")
    private String permissionCode;

    /** Column: BUTTON_FOR_STEP_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "BUTTON_FOR_STEP_ID")
    private Long buttonForStepId;

    /** Column: OPTION_SAVE_FORM type NUMBER(1,0) NULL */
    @Column(name = "OPTION_SAVE_FORM")
    private boolean optionSaveForm;

    /** Column: OPTION_SAVE_EFORM type NUMBER(1,0) NULL */
    @Column(name = "OPTION_SAVE_EFORM")
    private boolean optionSaveEform;

    /** Column: OPTION_AUTHENTICATE type NUMBER(1,0) NULL */
    @Column(name = "OPTION_AUTHENTICATE")
    private boolean optionAuthenticate;

    /** Column: OPTION_SIGNED type NUMBER(1,0) NULL */
    @Column(name = "OPTION_SIGNED")
    private boolean optionSigned;

    /** Column: OPTION_EXPORT_PDF type NUMBER(1,0) NULL */
    @Column(name = "OPTION_EXPORT_PDF")
    private boolean optionExportPdf;

    /** Column: OPTION_SHOW_HISTORY type NUMBER(1,0) NULL */
    @Column(name = "OPTION_SHOW_HISTORY")
    private boolean optionShowHistory;

    /** Column: OPTION_FILL_TO_EFORM type NUMBER(1,0) NULL */
    @Column(name = "OPTION_FILL_TO_EFORM")
    private boolean optionFillToEform;

}
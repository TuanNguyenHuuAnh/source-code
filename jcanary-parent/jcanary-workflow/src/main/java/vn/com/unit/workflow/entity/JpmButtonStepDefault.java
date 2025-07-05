/*******************************************************************************
* Class        JpmButtonStepDefault
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
import vn.com.unit.db.entity.AbstractCreatedTracking;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * JpmButtonStepDefault
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_BUTTON_STEP_DEFAULT)
public class JpmButtonStepDefault extends AbstractCreatedTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_BUTTON_STEP_DEFAULT)
    private Long id;

    /** Column: STEP_ID type NUMBER(20,0) NULL */
    @Column(name = "STEP_ID")
    private Long stepId;

    /** Column: BUTTON_ID type NUMBER(20,0) NULL */
    @Column(name = "BUTTON_ID")
    private Long buttonId;

    /** Column: PERMISSION_ID type NUMBER(20,0) NULL */
    @Column(name = "PERMISSION_ID")
    private Long permissionId;

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

    /** Column: PROCESS_TYPE type VARCHAR2(20.0) NULL */
    @Column(name = "PROCESS_TYPE")
    private String processType;

    /** Column: PROCESS_KIND type VARCHAR2(20.0) NULL */
    @Column(name = "PROCESS_KIND")
    private String processKind;

}
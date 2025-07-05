/*******************************************************************************
* Class        JpmButtonDefault
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
 * JpmButtonDefault
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_BUTTON_DEFAULT)
public class JpmButtonDefault extends AbstractAuditTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_BUTTON_DEFAULT)
    private Long id;

    /** Column: BUTTON_CODE type NVARCHAR2(255.0) NULL */
    @Column(name = "BUTTON_CODE")
    private String buttonCode;

    /** Column: BUTTON_TEXT type NVARCHAR2(255.0) NULL */
    @Column(name = "BUTTON_TEXT")
    private String buttonText;

    /** Column: BUTTON_VALUE type NVARCHAR2(255.0) NULL */
    @Column(name = "BUTTON_VALUE")
    private String buttonValue;

    /** Column: BUTTON_CLASS type NVARCHAR2(255.0) NULL */
    @Column(name = "BUTTON_CLASS")
    private String buttonClass;

    /** Column: BUTTON_TYPE type NVARCHAR2(255.0) NULL */
    @Column(name = "BUTTON_TYPE")
    private String buttonType;

    /** Column: DISPLAY_ORDER type NUMBER(10,0) NULL */
    @Column(name = "DISPLAY_ORDER")
    private Long displayOrder;

    /** Column: PROCESS_TYPE type VARCHAR2(20.0) NULL */
    @Column(name = "PROCESS_TYPE")
    private String processType;

    /** Column: PROCESS_KIND type VARCHAR2(20.0) NULL */
    @Column(name = "PROCESS_KIND")
    private String processKind;

}
/*******************************************************************************
* Class        JpmStepLang
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
 * JpmStepLang
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_STEP_LANG)
public class JpmStepLang extends AbstractCreatedTracking {

    /** Column: STEP_ID type NUMBER(20,0) NOT NULL */
    @Id
    @Column(name = "STEP_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long stepId;
    
    /** Column: LANG_ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "LANG_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long langId;

    /** Column: LANG_CODE type VARCHAR2(10) NULL */
    @Column(name = "LANG_CODE")
    private String langCode;

    /** Column: STEP_NAME type NVARCHAR2(255.0) NULL */
    @Column(name = "STEP_NAME")
    private String stepName;

}
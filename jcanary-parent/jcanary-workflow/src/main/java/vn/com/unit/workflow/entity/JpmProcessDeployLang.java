/*******************************************************************************
* Class        JpmProcessDeployLang
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
 * JpmProcessDeployLang
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_PROCESS_DEPLOY_LANG)
public class JpmProcessDeployLang extends AbstractCreatedTracking {

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NULL */
    @Column(name = "PROCESS_DEPLOY_ID")
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long processDeployId;
    
    /** Column: LANG_ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "LANG_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long langId;

    /** Column: LANG_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "LANG_CODE")
    private String langCode;

    /** Column: PROCESS_NAME type NVARCHAR2(255.0) NULL */
    @Column(name = "PROCESS_NAME")
    private String processName;

}
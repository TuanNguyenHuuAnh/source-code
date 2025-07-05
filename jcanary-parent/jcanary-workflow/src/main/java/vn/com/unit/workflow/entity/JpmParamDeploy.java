/*******************************************************************************
* Class        JpmParamDeploy
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
import vn.com.unit.db.entity.AbstractTracking;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * JpmParamDeploy
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_PARAM_DEPLOY)
public class JpmParamDeploy extends AbstractTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_PARAM_DEPLOY)
    private Long id;

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PROCESS_DEPLOY_ID")
    private Long processDeployId;

    /** Column: FIELD_NAME type VARCHAR2(255.0) NULL */
    @Column(name = "FIELD_NAME")
    private String fieldName;

    /** Column: DATA_TYPE type VARCHAR2(255.0) NULL */
    @Column(name = "DATA_TYPE")
    private String dataType;

    /** Column: FORM_FIELD_NAME type VARCHAR2(255.0) NULL */
    @Column(name = "FORM_FIELD_NAME")
    private String formFieldName;

    /** Column: PARAM_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PARAM_ID")
    private Long paramId;

}
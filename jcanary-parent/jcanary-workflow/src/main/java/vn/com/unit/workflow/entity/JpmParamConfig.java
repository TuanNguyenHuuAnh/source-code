/*******************************************************************************
* Class        JpmParamConfig
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
 * JpmParamConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_PARAM_CONFIG)
public class JpmParamConfig extends AbstractCreatedTracking {

    /** Column: PROCESS_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PROCESS_ID")
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long processId;

    /** Column: PARAM_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PARAM_ID")
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long paramId;

    /** Column: STEP_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "STEP_ID")
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long stepId;

    /** Column: REQUIRED type NUMBER(1,0) NULL */
    @Column(name = "REQUIRED")
    private boolean required;

}
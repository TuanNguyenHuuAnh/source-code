/*******************************************************************************
* Class        JpmParamConfigDeploy
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
 * JpmParamConfigDeploy
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_PARAM_CONFIG_DEPLOY)
public class JpmParamConfigDeploy extends AbstractCreatedTracking {

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PROCESS_DEPLOY_ID")
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long processDeployId;

    /** Column: PARAM_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PARAM_DEPLOY_ID")
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long paramDeployId;

    /** Column: STEP_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "STEP_DEPLOY_ID")
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long stepDeployId;

    /** Column: REQUIRED type NUMBER(1,0) NULL */
    @Column(name = "REQUIRED")
    private boolean required;

}
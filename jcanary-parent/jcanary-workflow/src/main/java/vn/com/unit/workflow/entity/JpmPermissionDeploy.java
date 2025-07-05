/*******************************************************************************
* Class        JpmPermissionDeploy
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
 * JpmPermissionDeploy
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_PERMISSION_DEPLOY)
public class JpmPermissionDeploy extends AbstractCreatedTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_PERMISSION_DEPLOY)
    private Long id;

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PROCESS_DEPLOY_ID")
    private Long processDeployId;

    /** Column: PERMISSION_ID type NUMBER(20,0) NULL */
    @Column(name = "PERMISSION_ID")
    private Long permissionId;

    /** Column: PERMISSION_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "PERMISSION_CODE")
    private String permissionCode;

    /** Column: PERMISSION_NAME type NVARCHAR2(255.0) NULL */
    @Column(name = "PERMISSION_NAME")
    private String permissionName;

    /** Column: PERMISSION_TYPE type NUMBER(2,0) NULL */
    @Column(name = "PERMISSION_TYPE")
    private Integer permissionType;

}
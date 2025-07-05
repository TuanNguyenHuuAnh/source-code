/*******************************************************************************
* Class        JpmAuthority
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
 * JpmAuthority
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_AUTHORITY)
public class JpmAuthority extends AbstractCreatedTracking {

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "PROCESS_DEPLOY_ID")
    private Long processDeployId;
    
    /** Column: PERMISSION_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "PERMISSION_DEPLOY_ID")
    private Long permissionDeployId;

    /** Column: ROLE_ID type NUMBER(20,0) NOT NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ROLE_ID")
    private Long roleId;

}
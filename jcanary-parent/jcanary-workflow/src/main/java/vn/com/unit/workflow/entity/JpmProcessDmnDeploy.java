/*******************************************************************************
* Class        JpmProcessDmnDeploy
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
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
 * JpmProcessDmnDeploy
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_PROCESS_DMN_DEPLOY)
public class JpmProcessDmnDeploy extends AbstractCreatedTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_PROCESS_DMN_DEPLOY)
    private Long id;

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PROCESS_DEPLOY_ID")
    private Long processDeployId;

    /** Column: DEPLOYMENT_ID type VARCHAR2(64.0) NOT NULL */
    @Column(name = "DEPLOYMENT_ID")
    private String deploymentId;

    /** Column: DMN_FILE_NAME type VARCHAR2(255.0) NOT NULL */
    @Column(name = "DMN_FILE_NAME")
    private String dmnFileName;

    /** Column: DMN_FILE_PATH type VARCHAR2(255.0) NOT NULL */
    @Column(name = "DMN_FILE_PATH")
    private String dmnFilePath;

    /** Column: DMN_REPO_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "DMN_REPO_ID")
    private Long dmnRepoId;

}
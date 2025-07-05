/*******************************************************************************
* Class        JpmStatusCommon
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
 * JpmStatusCommon
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_STATUS_COMMON)
public class JpmStatusCommon extends AbstractTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_STATUS_COMMON)
    private Long id;

    /** Column: STATUS_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "STATUS_CODE")
    private String statusCode;

    /** Column: STATUS_NAME type NVARCHAR2(255.0) NULL */
    @Column(name = "STATUS_NAME")
    private String statusName;

}
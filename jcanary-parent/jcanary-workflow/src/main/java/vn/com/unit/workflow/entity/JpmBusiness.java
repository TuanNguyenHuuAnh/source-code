/*******************************************************************************
* Class        JpmBusiness
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
 * JpmBusiness
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_BUSINESS)
public class JpmBusiness extends AbstractTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_BUSINESS)
    private Long id;

    /** Column: BUSINESS_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "BUSINESS_CODE")
    private String businessCode;

    /** Column: BUSINESS_NAME type NVARCHAR2(255.0) NULL */
    @Column(name = "BUSINESS_NAME")
    private String businessName;

    /** Column: DESCRIPTION type NVARCHAR2(2000.0) NULL */
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: PROCESS_TYPE type NUMBER(2,0) NULL */
    @Column(name = "PROCESS_TYPE")
    private Integer processType;

    /** Column: AUTHORITY type NUMBER(1,0) NULL */
    @Column(name = "AUTHORITY")
    private boolean authority;

    /** Column: ACTIVED type NUMBER(1,0) NULL */
    @Column(name = "ACTIVED")
    private boolean actived;

    /** Column: COMPANY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;

}
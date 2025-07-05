/*******************************************************************************
* Class        EfoDoc
* Created date 2021/03/03
* Lasted date  2021/03/03
* Author       TaiTT
* Change log   2021/03/03 01-00 TaiTT create a new
******************************************************************************/

package vn.com.unit.ep2p.core.efo.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.entity.AbstractDocument;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;

/**
 * EfoDoc
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTT
 */

@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_EFO_DOC)
public class EfoDoc extends AbstractDocument{

	/** Column: ID type NUMBER (20,0) NOT NULL */
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long id;

	
	/** Column: DOC_SUMMARY type NCLOBnull */
	@Column(name = "DOC_SUMMARY")
	private String docSummary;

	
	/** Column: DOC_TYPE type VARCHAR2(30,0) null */
	@Column(name = "DOC_TYPE")
	private String docType;

	
	/** Column: DUE_DATE type DATEnull */
	@Column(name = "DUE_DATE")
	private Date dueDate;

	
	/** Column: DOC_CODE type VARCHAR2(100,0) null */
	@Column(name = "DOC_CODE")
	private String docCode;

	
	/** Column: DOC_TITLE type NVARCHAR2(255,0) null */
	@Column(name = "DOC_TITLE")
	private String docTitle;

	
	/** Column: DOC_TITLE_S type NVARCHAR2(400,0) null */
	@Column(name = "DOC_TITLE_S")
	private String docTitleS;

	
	/** Column: PRIORITY type VARCHAR2(100,0) null */
	@Column(name = "PRIORITY")
	private String priority;
	
	/** Column: FORM_ID type NUMBER(20,0) null */
	@Column(name = "FORM_ID")
	private Long formId;

	
	/** Column: SUBMITTED_ID type NUMBER(20,0) null */
	@Column(name = "SUBMITTED_ID")
	private Long submittedId;

	
	/** Column: SUBMITTED_DATE type DATEnull */
	@Column(name = "SUBMITTED_DATE")
	private Date submittedDate;

	
	/** Column: SUBMITTED_ORG_ID type NUMBER(20,0) null */
	@Column(name = "SUBMITTED_ORG_ID")
	private Long submittedOrgId;

	
	/** Column: SUBMITTED_POSITION_ID type NUMBER(20,0) null */
	@Column(name = "SUBMITTED_POSITION_ID")
	private Long submittedPositionId;

	
	/** Column: OWNER_ID type NUMBER(20,0) null */
	@Column(name = "OWNER_ID")
	private Long ownerId;

	
	/** Column: OWNER_ORG_ID type NUMBER(20,0) null */
	@Column(name = "OWNER_ORG_ID")
	private Long ownerOrgId;

	
	/** Column: OWNER_POSITION_ID type NUMBER(20,0) null */
	@Column(name = "OWNER_POSITION_ID")
	private Long ownerPositionId;

	
	/** Column: MAJOR_VERSION type NUMBER(20,0) null */
	@Column(name = "MAJOR_VERSION")
	private Long majorVersion;

	
	/** Column: MINOR_VERSION type NUMBER(20,0) null */
	@Column(name = "MINOR_VERSION")
	private Long minorVersion;

	
	/** Column: SYSTEM_CODE type VARCHAR2(255,0) null */
	@Column(name = "SYSTEM_CODE")
	private String systemCode;

	
	/** Column: APP_CODE type VARCHAR2(255,0) null */
	@Column(name = "APP_CODE")
	private String appCode;

	
	/** Column: COMPANY_ID type NUMBER(20,0) null */
	@Column(name = "COMPANY_ID")
	private Long companyId;

    @Column(name = "ITEM_FUNCTION_CODE")
    private String itemFunctionCode;
}
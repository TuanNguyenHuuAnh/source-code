/*******************************************************************************
* Class        EfoForm
* Created date 2021/03/03
* Lasted date  2021/03/03
* Author       TaiTT
* Change log   2021/03/03 01-00 TaiTT create a new
******************************************************************************/

package vn.com.unit.ep2p.core.efo.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * EfoForm
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTT
 */

@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_EFO_FORM)
public class EfoForm extends AbstractTracking{

	/** Column: ID type NUMBER (20,0) NOT NULL */
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + AppCoreConstant.TABLE_EFO_FORM)
	private Long id;

	
	/** Column: COMPANY_ID type NUMBER(20,0) null */
	@Column(name = "COMPANY_ID")
	private Long companyId;

	
	/** Column: CATEGORY_ID type NUMBER(20,0) null */
	@Column(name = "CATEGORY_ID")
	private Long categoryId;

	
	/** Column: BUSINESS_ID type NUMBER(20,0) null */
	@Column(name = "BUSINESS_ID")
	private Long businessId;

	
	/** Column: NAME type NVARCHAR2(255,0) null */
	@Column(name = "NAME")
	private String name;

	
	/** Column: DESCRIPTION type NVARCHAR2(255,0) null */
	@Column(name = "DESCRIPTION")
	private String description;

	
	/** Column: OZ_FILE_PATH type NVARCHAR2(255,0) null */
	@Column(name = "OZ_FILE_PATH")
	private String ozFilePath;

	
	/** Column: ICON_FILE_PATH type NVARCHAR2(255,0) null */
	@Column(name = "ICON_FILE_PATH")
	private String iconFilePath;

	
	/** Column: ICON_REPO_ID type NUMBER(20,0) null */
	@Column(name = "ICON_REPO_ID")
	private Long iconRepoId;

	
	/** Column: DISPLAY_ORDER type NUMBER(20,0) null */
	@Column(name = "DISPLAY_ORDER")
	private Long displayOrder;

	
	/** Column: DEVICE_TYPE type VARCHAR2(255,0) null */
	@Column(name = "DEVICE_TYPE")
	private String deviceType;

	
	/** Column: ACTIVED type NUMBER(1,0) null */
	@Column(name = "ACTIVED")
	private boolean actived;

	
	/** Column: FORM_TYPE type VARCHAR2(50,0) null */
	@Column(name = "FORM_TYPE")
	private String formType;

	
	/** Column: OZ_APPEND_FILE_PATH type VARCHAR2(255,0) null */
	@Column(name = "OZ_APPEND_FILE_PATH")
	private String ozAppendFilePath;

}
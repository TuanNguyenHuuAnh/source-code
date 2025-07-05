/*******************************************************************************
 * Class        ：EfoOzDocFilterOut
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
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
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.entity.AbstractDocumentFilter;

/**
 * EfoOzDocFilterOut
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_EFO_OZ_DOC_FILTER_OUT)
public class EfoOzDocFilterOut extends AbstractDocumentFilter{

    /** Column: ID type DECIMAL(20) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_EFO_OZ_DOC_FILTER_OUT)
    private Long id;
    
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

	
	/** Column: COMPLETED_ID type NUMBER(20,0) null */
	@Column(name = "COMPLETED_ID")
	private Long completedId;

	
	/** Column: COMPLETED_DATE type DATEnull */
	@Column(name = "COMPLETED_DATE")
	private Date completedDate;

	
	/** Column: COMPLETED_ORG_ID type NUMBER(20,0) null */
	@Column(name = "COMPLETED_ORG_ID")
	private Long completedOrgId;

	
	/** Column: COMPLETED_POSITION_ID type NUMBER(20,0) null */
	@Column(name = "COMPLETED_POSITION_ID")
	private Long completedPositionId;

	
	/** Column: OWNER_ID type NUMBER(20,0) null */
	@Column(name = "OWNER_ID")
	private Long ownerId;

	
	/** Column: OWNER_ORG_ID type NUMBER(20,0) null */
	@Column(name = "OWNER_ORG_ID")
	private Long ownerOrgId;

	
	/** Column: OWNER_POSITION_ID type NUMBER(20,0) null */
	@Column(name = "OWNER_POSITION_ID")
	private Long ownerPositionId;

    
}

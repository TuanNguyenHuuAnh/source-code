/*******************************************************************************
 * Class        :EfoCategory
 * Created date :2019/12/17
 * Lasted date  :2019/12/17
 * Author       :TrongNV
 * Change log   :2019/12/17:01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * 
 * EfoCategory
 * 
 * @version 01-00
 * @since 01-00
 * @author TrongNV
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_EFO_CATEGORY)
public class EfoCategory extends AbstractTracking{

	/** Column: ID type decimal(20,0) NOT NULL */
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_EFO_CATEGORY)
	private Long id;

	/** Column: COMPANY_ID type decimal(20,0) NULL */
	@Column(name = "COMPANY_ID")
	private Long companyId;

	/** Column: DESCRIPTION type nvarchar(255) NULL */
	@Column(name = "DESCRIPTION")
	private String description;

	/** Column: DISPLAY_ORDER type decimal(20,0) NULL */
	@Column(name = "DISPLAY_ORDER")
	private Long displayOrder;

	/** Column: ACTIVE_FLAG type char(1) NOT NULL */
	@Column(name = "ACTIVED")
	private boolean actived;

}
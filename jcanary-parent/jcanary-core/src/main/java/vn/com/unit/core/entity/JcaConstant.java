/*******************************************************************************
 * Class        :JcaConstant
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :tantm
 * Change log   :2020/12/01:01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * 
 * JcaConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_CONSTANT)
public class JcaConstant extends AbstractAuditTracking {

	@Id
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	@Column(name = "GROUP_ID")
	private String groupId;

	/** Column: GROUP_CODE type NVARCHAR2(30) NULL */
	@Column(name = "GROUP_CODE")
	private String groupCode;

	@Id
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	/** Column: CODE type NVARCHAR2(255) NULL */
	@Column(name = "CODE")
	private String code;

	/** Column: DISPLAY_ORDER type NUMBER(20,0) NULL */
	@Column(name = "DISPLAY_ORDER")
	private Long displayOrder;

	@Id
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	/** Column: KIND type NVARCHAR2(255) NULL */
	@Column(name = "KIND")
	private String kind;

	/** Column: ACTIVED type NVARCHAR2(255) NULL */
	@Column(name = "ACTIVED")
	private Integer actived;

	@Id
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	@Column(name = "LANG_ID")
	private Long langId;

	/** Column: LANGUAGE_CODE type VARCHAR2(30) NULL */
	@Column(name = "LANG_CODE")
	private String langCode;

	@Column(name = "NAME")
	private String name;

}
/*******************************************************************************
* Class        I18nLocaleDefault
* Created date 2021/01/06
* Lasted date  2021/01/06
* Author       NhanNV
* Change log   2021/01/06 01-00 NhanNV create a new
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
import vn.com.unit.db.entity.AbstractTracking;

/**
 * I18nLocaleDefault
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_I18N_LOCALE_DEFAULT)
public class I18nLocaleDefault extends AbstractTracking{

	/** Column: ID type NUMBER (20,0) NOT NULL */
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_I18N_LOCALE_DEFAULT)
	private Long id;

	/** Column: COMPANY_ID type NUMBER(20,0)  NOT NULL */
	@Column(name = "COMPANY_ID")
	private Long companyId;

	/** Column: MESSAGE_KEY type VARCHAR2(255) NULL */
	@Column(name = "MESSAGE_KEY")
	private String messageKey;

	/** Column: MESSAGE_CONTENT type NVARCHAR2(255) NULL */
	@Column(name = "MESSAGE_CONTENT")
	private String messageContent;

	/** Column: LOCALE type VARCHAR2(255) NULL */
	@Column(name = "LOCALE")
	private String locale;

}
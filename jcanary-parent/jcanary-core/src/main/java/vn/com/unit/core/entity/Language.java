/*******************************************************************************
 * Class        JcaMBannerLanguage
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       NhanNV
 * Change log   2017/02/1401-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * Language
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Getter
@Setter
@Table(name = "JCA_LANGUAGE")
public class Language extends AbstractTracking {

	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_LANGUAGE")
	private Long id;

	/** Column: COMPANY_ID type decimal(20,0) NULL */
	@Column(name = "COMPANY_ID")
	private Long companyId;

	@Column(name = "CODE")
	private String code;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SORT")
	private Long sort;

	/** Column: PACKAGE_IMAGE_URL type varchar(255) NULL */
	@Column(name = "ICON_CLASSES")
	private String iconClasses;

	/** Column: IMAGE_URL type varchar(255) NULL */
	@Column(name = "VERSION")
	private Long version;

}
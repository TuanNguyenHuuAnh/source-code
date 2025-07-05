package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * CategoryLanguage
 * @author trieuvd
 *
 */
@Getter
@Setter
@Table(name = "EFO_CATEGORY_LANG")
public class CategoryLanguage extends AbstractAuditTracking{
	

	/** Column: CATEGORY_ID type decimal(20,0) NOT NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
	@Column(name = "CATEGORY_ID")
	private Long categoryId;
    
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "LANG_ID")
    private Long langId;
	
	/** Column: M_LANGUAGE_CODE type varchar(30) NOT NULL */
	@Column(name = "LANG_CODE")
	private String langCode;

	/** Column: CATEGORY_NAME type nvarchar(255) NOT NULL */
	@Column(name = "NAME")
	private String name;

	

}

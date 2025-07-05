/*******************************************************************************
 * Class        :JcaOrganization
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaOrganization
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CoreConstant.TABLE_JCA_ORGANIZATION)
public class JcaOrganization extends AbstractTracking {

	/** Column: ID type decimal(20,0) NULL */
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_JCA_ORGANIZATION)
	private Long id;
	
	/** Column: CODE type nvarchar(50) NOT NULL */
    @Column(name = "CODE")
	private String code;
	
    /** Column: NAME type nvarchar(255) NOT NULL */
    @Column(name = "NAME")
	private String name;
    
    /** Column: NAME_ABV type nvarchar(255)  NULL */
    @Column(name = "NAME_ABV")
	private String nameAbv;
	
    /** Column: ORG_TYPE type nvarchar(255) NULL */
    @Column(name = "ORG_TYPE")
    private String orgType;
    
    /** Column: EMAIL type nvarchar(255)  NULL */
    @Column(name ="EMAIL")
    private String email;
    
    /** Column: PHONE type nvarchar(255)  NULL */
    @Column(name ="PHONE")
    private String phone;
    
    /** Column: ADDRESS type nvarchar(2000)  NULL */
    @Column(name ="ADDRESS")
    private String address;
    
    /** Column: ADDRESS type nvarchar(2000)  NULL */
    @Column(name ="DESCRIPTION")
    private String description;
    
    /** Column: ORG_ORDER type decimal(11,0) NULL */
    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
    
    /** Column: ACTIVED type decimal(20,0) NULL */
    @Column(name = "ACTIVED")
    private boolean actived;

    /** Column: COMPANY_ID type decimal(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    @Transient 
    private Long parentOrgId;
    
}
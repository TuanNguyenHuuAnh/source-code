/*******************************************************************************
 * Class        ：JcaRoleForCompany
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;

/**
 * JcaRoleForCompany
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@Table(name = "JCA_ROLE_FOR_COMPANY")
public class JcaRoleForCompany extends AbstractCreatedTracking{
    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL */
    @Id
    @Column(name ="COMPANY_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long companyId;
    
    /** Column: ROLE_ID type NUMBER(20,0) NULL */
    @Id
    @Column(name ="ROLE_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long roleId;
    
    /** Column: ORG_ID type NUMBER(20,0) NULL */
    @Id
    @Column(name ="ORG_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long orgId;
    
    /** Column: IS_ADMIN type NUMBER(1,0) NULL */
    @Column(name ="IS_ADMIN")
    private Boolean isAdmin;
    
}

/*******************************************************************************
 * Class        ：JcaRuleSetting
 * Created date ：2020/11/08
 * Lasted date  ：2020/11/08
 * Author       ：KhoaNA
 * Change log   ：2020/11/08：01-00 KhoaNA create a new
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
 * JcaRuleSetting
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_RULE_SETTING)
public class JcaRuleSetting extends AbstractAuditTracking {

    /** Column: BUSINESS_ID type NUMBER(20,0) NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "BUSINESS_ID")
    private Long businessId;
    
    /** Column: ORG_ID type NUMBER(20,0) NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ORG_ID")
    private Long orgId;
    
    /** Column: POSITION_ID type NUMBER(20,0) NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "POSITION_ID")
    private Long positionId;
    
    /** Column: RULE_ORG_TYPE type NUMBER(1,0) NULL */
    @Column(name = "RULE_ORG_TYPE")
    private Long ruleOrgType;
    
    /** Column: RULE_POSITION_TYPE type NUMBER(1,0) NULL */
    @Column(name = "RULE_POSITION_TYPE")
    private Long rulePositionType;


}

/*******************************************************************************
 * Class        ：JcaRuleException
 * Created date ：2020/11/08
 * Lasted date  ：2020/11/08
 * Author       ：tantm
 * Change log   ：2020/11/08：01-00 tantm create a new
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
 * JcaRuleException
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_RULE_SETTING)
public class JcaRuleException extends AbstractAuditTracking {

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

    /** Column: ACCOUNT_ID type NUMBER(20,0) NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /** Column: RULE_ORG_TYPE type NUMBER(1,0) NULL */
    @Column(name = "RULE_ORG_TYPE")
    private Long ruleOrgType;

}

/*******************************************************************************
 * Class        ：JcaSystemConfig
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：ngannh
 * Change log   ：2020/12/16：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractAuditTracking;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaSystemConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_SYSTEM_SETTING)
public class JcaSystemConfig extends AbstractAuditTracking{
    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    /** Column: SETTING_KEY type NVARCHAR2(255) NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "SETTING_KEY")
    private String settingKey;
    
    /** Column: SETTING_VALUE type NVARCHAR2(255) NULL */
    @Column(name = "SETTING_VALUE")
    private String settingValue;
    
    /** Column: GROUP_CODE type NVARCHAR2(100) NULL */
    @Column(name = "GROUP_CODE")
    private String groupCode;
}

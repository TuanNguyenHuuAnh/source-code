/*******************************************************************************
 * Class        ：JcaSystemConfigHis
 * Created date ：2021/03/02
 * Lasted date  ：2021/03/02
 * Author       ：ngannh
 * Change log   ：2021/03/02：01-00 ngannh create a new
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
 * JcaSystemConfigHis
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_HI_SYSTEM_SETTING)
public class JcaSystemConfigHis extends AbstractTracking{
    
    /** Column: id type decimal(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +   CoreConstant.TABLE_JCA_GROUP_SYSTEM_SETTING)
    private Long id;
    
    /** Column: SETTING_KEY type NVARCHAR2(255) NULL */
    @Column(name = "SETTING_KEY")
    private String settingKey;
    
    /** Column: SETTING_VALUE type NVARCHAR2(255) NULL */
    @Column(name = "SETTING_VALUE")
    private String settingValue;
    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    /** Column: GROUP_CODE type NVARCHAR2(100) NULL */
    @Column(name = "GROUP_CODE")
    private String groupCode;
}

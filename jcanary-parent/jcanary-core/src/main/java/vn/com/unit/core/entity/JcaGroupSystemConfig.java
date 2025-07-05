/*******************************************************************************
 * Class        ：JcaGroupSystemConfig
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
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
 * JcaGroupSystemConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_GROUP_SYSTEM_SETTING)
public class JcaGroupSystemConfig extends AbstractTracking {
    /** Column: id type decimal(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +   CoreConstant.TABLE_JCA_GROUP_SYSTEM_SETTING)
    private Long id;
    
    /** Column: GROUP_CODE type NVARCHAR2(30) NULL */
    @Column(name = "GROUP_CODE")
    private String groupCode;
    
    /** Column: GROUP_NAME type NVARCHAR2(255) NULL */
    @Column(name = "GROUP_NAME")
    private String groupName;
    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;

}

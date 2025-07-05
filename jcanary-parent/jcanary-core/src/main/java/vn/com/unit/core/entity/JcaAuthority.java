/*******************************************************************************
 * Class        ：JcaAuthority
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
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
import vn.com.unit.db.entity.AbstractCreatedTracking;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaAuthority
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_AUTHORITY)
public class JcaAuthority extends AbstractCreatedTracking{
   
    
    /** Column: ROLE_ID type NUMBER(20,0) NULL */
    @Id
    @Column(name ="ROLE_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long roleId;
    
    /** Column: ITEM_ID type NUMBER(20,0) NULL */
    @Id
    @Column(name ="ITEM_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long itemId;
    
    /** Column: ACCESS_FLG type NUMBER(1,0) NULL */
    @Id
    @Column(name ="ACCESS_FLAG")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long accessFlag;
}

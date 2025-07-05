/*******************************************************************************
 * Class        ：JcaRoleForAccount
 * Created date ：2021/01/22
 * Lasted date  ：2021/01/22
 * Author       ：SonND
 * Change log   ：2021/01/22：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractCreatedTracking;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaRoleForAccount
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CoreConstant.TABLE_JCA_ROLE_FOR_ACCOUNT)
public class JcaRoleForAccount extends AbstractCreatedTracking {
    /** Column: ACCOUNT_ID type decimal(20,0) NULL. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    
    /** Column: ROLE_ID type decimal(20,0) NULL. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ROLE_ID")
    private Long roleId;
//    
//    /** Column: START_DATE type decimal(20,0) NULL. */
//    @Column(name = "START_DATE")
//    private Date startDate;
//    
//    /** Column: END_DATE type decimal(20,0) NULL. */
//    @Column(name = "END_DATE")
//    private Date endDate;
}

/*******************************************************************************
 * Class        ：JcaAccountTeam
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
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
 * JcaAccountTeam
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CoreConstant.TABLE_JCA_ACCOUNT_TEAM)
public class JcaAccountTeam extends AbstractCreatedTracking {

    /** Column: ACCOUNT_ID type decimal(20,0) NULL. */
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Id
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    
    /** Column: TEAM_ID type decimal(20,0) NULL. */
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Id
    @Column(name = "TEAM_ID")
    private Long teamId;
    
 
}

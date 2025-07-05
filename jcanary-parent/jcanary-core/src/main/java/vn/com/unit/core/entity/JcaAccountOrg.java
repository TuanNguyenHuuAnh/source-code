/*******************************************************************************
 * Class        :JcaAccountOrg
 * Created date :16/12/2020
 * Lasted date  :16/12/2020
 * Author       :SonND
 * Change log   :16/12/2020:01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.entity;

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
import vn.com.unit.db.entity.AbstractAuditTracking;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaAccountOrg
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CoreConstant.TABLE_JCA_ACCOUNT_ORG)
public class JcaAccountOrg extends AbstractAuditTracking {

//	/** Column: ID type decimal(20,0) NULL. */
//	@Id
//	@Column(name = "ID")
//	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_JCA_ACCOUNT_ORG)
//	private Long id;
	

    /** Column: ACCOUNT_ID type decimal(20,0) NULL. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    
    /** Column: ORG_ID type decimal(20,0) NULL. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ORG_ID")
    private Long orgId;
    
    /** Column: POSITION_ID type decimal(20,0) NULL. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "POSITION_ID")
    private Long positionId;
    
    /** Column: ACTIVED type decimal(20,0) NULL. */
    @Column(name = "ACTIVED")
    private Boolean actived;
    
    /** Column: MAIN_FLAG type decimal(20,0) NULL. */
    // check account org đó có org chính và position chính
    @Column(name = "MAIN_FLAG")
    private Boolean mainFlag;
    
}
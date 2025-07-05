/*******************************************************************************
 * Class        :JcaRoleForGroup
 * Created date :2020/12/22
 * Lasted date  :2020/12/22
 * Author       :SonND
 * Change log   :2020/12/22:01-00 SonND create a new
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
import vn.com.unit.db.entity.AbstractCreatedTracking;

/**
 * <p>
 * JcaRoleForGroup
 * </p>
 * 
 * @version : 01-00
 * @since 01-00
 * @author : SonND
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CoreConstant.TABLE_JCA_ROLE_FOR_POSITION)
public class JcaRoleForPosition extends AbstractCreatedTracking {

    /** Column: POSITION_ID type decimal(20,0) NULL. */
    @Id
    @Column(name = "POSITION_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long positionId;

    /** Column: ROLE_ID type decimal(20,0) NULL. */
    @Id
    @Column(name = "ROLE_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long roleId;

}
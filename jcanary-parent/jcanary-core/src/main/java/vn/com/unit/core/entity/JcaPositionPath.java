/*******************************************************************************
 * Class        :JcaPositionPath
 * Created date :2020/12/25
 * Lasted date  :2020/12/25
 * Author       :SonND
 * Change log   :2020/12/25:01-00 SonND create a new
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

/**
 * JcaPositionPath
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_POSITION_PATH)
public class JcaPositionPath extends AbstractCreatedTracking {

    @Column(name = "DEPTH")
    private int depth;
    
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ANCESTOR_ID")
    private Long ancestorId;
    
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "DESCENDANT_ID")
    private Long descendantId;
}

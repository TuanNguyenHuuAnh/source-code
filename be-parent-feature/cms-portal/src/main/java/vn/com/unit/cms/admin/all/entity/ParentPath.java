/*******************************************************************************
 * Class        :JcaMenuPath
 * Created date :2020/12/08
 * Lasted date  :2020/12/08
 * Author       :SonND
 * Change log   :2020/12/08:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;

/**
 * JcaMenuPath
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "M_PARENT_PATH")
public class ParentPath extends AbstractCreatedTracking {

    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ANCESTOR_ID")
    private Long ancestorId;

    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "DESCENDANT_ID")
    private Long descendantId;

    @Column(name = "DEPTH")
    private Long depth;

    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "TYPE")
    private String type;
}

/*******************************************************************************
 * Class        ：SlaUserType
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;
import vn.com.unit.sla.constant.SlaConstant;

/**
 * <p>
 * SlaUserType
 * </p>
 * 
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@Table(name = SlaConstant.TABLE_SLA_INVOLED_TYPE_LANG)
public class SlaInvoledTypeLang extends AbstractCreatedTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = SlaConstant.SEQ + SlaConstant.TABLE_SLA_INVOLED_TYPE_LANG)
    private long id;

    @Column(name = "INVOLED_TYPE_ID")
    private String involedTypeId;
    
    @Column(name = "LANG_CODE")
    private String langCode;

    @Column(name = "NAME")
    private Date name;

}

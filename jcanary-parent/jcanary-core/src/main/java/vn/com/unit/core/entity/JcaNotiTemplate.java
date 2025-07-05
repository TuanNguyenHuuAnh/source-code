/*******************************************************************************
 * Class        ：JcaNotiTemplate
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
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
 * <p>
 * JcaNotiTemplate
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_NOTI_TEMPLATE)
public class JcaNotiTemplate extends AbstractTracking {

	/** The id. */
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_JCA_NOTI_TEMPLATE)
	private Long id;

    /** The name. */
    @Column(name = "NAME")
    private String name;
    
    /** The code. */
    @Column(name = "CODE")
    private String code;
    
    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;
    
    /** The company id. */
    @Column(name = "COMPANY_ID")
    private Long companyId;
}
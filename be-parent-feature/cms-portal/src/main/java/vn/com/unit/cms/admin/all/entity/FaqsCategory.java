/*******************************************************************************
 * Class        ：FaqsCategory
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * FaqsCategory
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "m_faqs_category")
@Getter
@Setter
public class FaqsCategory extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_FAQS_CATEGORY")
    private Long id;

    @Column(name = "M_FAQS_CATEROTY_PARENT_ID")
    private Long faqsCategoryParentId;

    @Column(name = "code")
    private String code;

    @Column(name = "note")
    private String note;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "M_CUSTOMER_TYPE_ID")
    private Long customerTypeId;

    @Column(name = "ITEM_FUNCTION_CODE")
    private String itemFunctionCode;
}

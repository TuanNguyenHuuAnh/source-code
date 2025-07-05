/*******************************************************************************
 * Class        ：DocumentCategory
 * Created date ：2017/04/17
 * Lasted date  ：2017/04/17
 * Author       ：TaiTM
 * Change log   ：2017/04/17:01-00 TaiTM create a new
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
 * 
 * DocumentCategory
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "M_DOCUMENT_CATEGORY")
@Getter
@Setter
public class DocumentCategory extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_DOCUMENT_CATEGORY")
    private Long id;

    @Column(name = "M_CUSTOMER_TYPE_ID")
    private Long customerTypeId;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "code")
    private String code;

    @Column(name = "note")
    private String note;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "ITEM_FUNCTION_CODE")
    private String itemFunctionCode;

    @Column(name = "FOR_CANDIDATE")
    private Boolean forCandidate;
    
    @Column(name = "CATEGORY_TYPE")
    private String categoryType;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "PARTNER")
    private String partner;
}

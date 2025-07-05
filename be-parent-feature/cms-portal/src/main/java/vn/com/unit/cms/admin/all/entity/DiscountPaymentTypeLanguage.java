/*******************************************************************************
 * Class        ：DiscountPaymentTypeLanguage
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * DiscountPaymentTypeLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_discount_payment_type_language")
public class DiscountPaymentTypeLanguage extends AbstractTracking{
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "m_discount_payment_type_id")
    private Integer discountPaymentTypeId;
    @Column(name = "title")
    private String title;
    @Column(name = "m_language_code")
    private String languageCode;
    
    /**
     * Get id
     * @return int
     * @author thuydtn
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Set id
     * @param   id
     *          type int
     * @return
     * @author  thuydtn
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Get discountPaymentTypeId
     * @return int
     * @author thuydtn
     */
    public Integer getDiscountPaymentTypeId() {
        return discountPaymentTypeId;
    }
    
    /**
     * Set discountPaymentTypeId
     * @param   discountPaymentTypeId
     *          type int
     * @return
     * @author  thuydtn
     */
    public void setDiscountPaymentTypeId(Integer discountPaymentTypeId) {
        this.discountPaymentTypeId = discountPaymentTypeId;
    }
    
    /**
     * Get title
     * @return String
     * @author thuydtn
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Set title
     * @param   title
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get languageCode
     * @return String
     * @author thuydtn
     */
    public String getLanguageCode() {
        return languageCode;
    }
    
    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
    
}

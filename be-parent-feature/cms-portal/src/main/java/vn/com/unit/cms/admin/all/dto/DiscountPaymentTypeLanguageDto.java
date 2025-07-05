/*******************************************************************************
 * Class        ：DiscountPaymentTypeLanguage
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import org.hibernate.validator.constraints.NotEmpty;

import vn.com.unit.cms.admin.all.entity.DiscountPaymentTypeLanguage;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * DiscountPaymentTypeLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@SuppressWarnings("deprecation")
public class DiscountPaymentTypeLanguageDto extends AbstractTracking{
    private int id;
    private int discountPaymentTypeId;
    @NotEmpty
    private String title;
    private String languageCode;
    private String languageDispName;
    
    /**
     * @param infoByLanguage
     */
    public DiscountPaymentTypeLanguageDto(DiscountPaymentTypeLanguage infoByLanguage) {
        this.id = infoByLanguage.getId();
        this.discountPaymentTypeId = infoByLanguage.getDiscountPaymentTypeId();
        this.title = infoByLanguage.getTitle();
        this.languageCode = infoByLanguage.getLanguageCode();
    }

    /**
     * 
     */
    public DiscountPaymentTypeLanguageDto() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Get id
     * @return int
     * @author thuydtn
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set id
     * @param   id
     *          type int
     * @return
     * @author  thuydtn
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Get discountPaymentTypeId
     * @return int
     * @author thuydtn
     */
    public int getDiscountPaymentTypeId() {
        return discountPaymentTypeId;
    }
    
    /**
     * Set discountPaymentTypeId
     * @param   discountPaymentTypeId
     *          type int
     * @return
     * @author  thuydtn
     */
    public void setDiscountPaymentTypeId(int discountPaymentTypeId) {
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

    /**
     * Get languageDispName
     * @return String
     * @author thuydtn
     */
    public String getLanguageDispName() {
        return languageDispName;
    }

    /**
     * Set languageDispName
     * @param   languageDispName
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setLanguageDispName(String languageDispName) {
        this.languageDispName = languageDispName;
    }

    /**
     * @return
     */
    public DiscountPaymentTypeLanguage createEntity() {
        DiscountPaymentTypeLanguage entity = new DiscountPaymentTypeLanguage();
        entity.setLanguageCode(this.languageCode);
        entity.setTitle(this.title);
        entity.setId(this.id);
        entity.setDiscountPaymentTypeId(this.discountPaymentTypeId);
        return entity;
    }
    
}

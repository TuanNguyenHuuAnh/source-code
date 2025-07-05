/*******************************************************************************
 * Class        ：DiscountPaymentType
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jgroups.annotations.Property;

import vn.com.unit.cms.admin.all.entity.DiscountPaymentType;

/**
 * DiscountPaymentType
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@SuppressWarnings("deprecation")
public class DiscountPaymentTypeDto {
    
    private Integer id;
    
    @NotEmpty
    private String name;
    
    private String description;
    @NotEmpty
    private String code;
    @NotNull
    private Integer sortOrder;
    private Date createDate;
    //TODO (only field, please input field on screen)
    private Double discountPayLimitBudget;
    @NotEmpty
    private String strDiscountPayLimitBudget;
    @Property
    private List<DiscountPaymentTypeLanguageDto> infoByLanguages;
    private String url;
    
    public DiscountPaymentTypeDto(){
        this.infoByLanguages = new ArrayList<>();
    }
    /**
     * @param entity
     */
    public DiscountPaymentTypeDto(DiscountPaymentType entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.createDate = entity.getCreateDate();
        this.sortOrder = entity.getSortOrder();
        this.setDiscountPayLimitBudget(entity.getDiscountPayLimitBudget());
    }
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
     * Get name
     * @return String
     * @author thuydtn
     */
    public String getName() {
        return name;
    }
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get description
     * @return String
     * @author thuydtn
     */
    public String getDescription() {
        return description;
    }
    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Get code
     * @return String
     * @author thuydtn
     */
    public String getCode() {
        return code;
    }
    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Get infoByLanguages
     * @return List<DocumentCategoryLanguageDto>
     * @author thuydtn
     */
    public List<DiscountPaymentTypeLanguageDto> getInfoByLanguages() {
        return infoByLanguages;
    }
    /**
     * Set infoByLanguages
     * @param   infoByLanguages
     *          type List<DocumentCategoryLanguageDto>
     * @return
     * @author  thuydtn
     */
    public void setInfoByLanguages(List<DiscountPaymentTypeLanguageDto> infoByLanguages) {
        this.infoByLanguages = infoByLanguages;
    }
    /**
     * Get createDate
     * @return String
     * @author thuydtn
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * Set createDate
     * @param   date
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setCreateDate(Date date) {
        this.createDate = date;
    }
    /**
     * Get url
     * @return String
     * @author thuydtn
     */
    public String getUrl() {
        return url;
    }
    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Get sortOrder
     * @return Integer
     * @author thuydtn
     */
    public Integer getSortOrder() {
        return sortOrder;
    }
    /**
     * Set sortOrder
     * @param   sortOrder
     *          type Integer
     * @return
     * @author  thuydtn
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    /**
     * @return
     */
    public DiscountPaymentType createEntity() {
        DiscountPaymentType entity = new DiscountPaymentType();
        entity.setId(this.id);
        entity.setCode(this.code);
        entity.setSortOrder(this.sortOrder);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setDiscountPayLimitBudget(this.discountPayLimitBudget);
        return entity;
    }
    
    /**
     * Get discountPayLimitBudget
     * @return Double
     * @author thuydtn
     */
    public Double getDiscountPayLimitBudget() {
        return discountPayLimitBudget;
    }
    /**
     * Set discountPayLimitBudget
     * @param   discountPayLimitBudget
     *          type Double
     * @return
     * @author  thuydtn
     */
    public void setDiscountPayLimitBudget(Double discountPayLimitBudget) {
        this.discountPayLimitBudget = discountPayLimitBudget;
        this.strDiscountPayLimitBudget = String.format("%,.2f", (double)discountPayLimitBudget);
    }
    /**
     * Get strDiscountPayLimitBudget
     * @return String
     * @author thuydtn
     */
    public String getStrDiscountPayLimitBudget() {
        return strDiscountPayLimitBudget;
    }
    /**
     * Set strDiscountPayLimitBudget
     * @param   strDiscountPayLimitBudget
     *          type String
     * @return
     * @author  thuydtn
     * @throws ParseException 
     */
    public void setStrDiscountPayLimitBudget(String strDiscountPayLimitBudget) throws ParseException {
        if(strDiscountPayLimitBudget != null && !strDiscountPayLimitBudget.trim().isEmpty()){
            this.strDiscountPayLimitBudget = strDiscountPayLimitBudget;
            NumberFormat numberFormat = NumberFormat.getInstance();
            this.discountPayLimitBudget = numberFormat.parse(strDiscountPayLimitBudget).doubleValue();
        }else{
            this.strDiscountPayLimitBudget = strDiscountPayLimitBudget;
            this.discountPayLimitBudget = 0.0;
        }
    }
}

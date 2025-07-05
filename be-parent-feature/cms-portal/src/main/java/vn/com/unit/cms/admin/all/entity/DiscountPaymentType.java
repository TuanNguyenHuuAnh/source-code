/*******************************************************************************
 * Class        ：DiscountPaymentType
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
 * DiscountPaymentType
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_discount_payment_type")
public class DiscountPaymentType extends AbstractTracking{
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name="sort_order")
    private int sortOrder;
    @Column(name="discount_pay_limit_budget")
    private Double discountPayLimitBudget;
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
     * Get discountPayLimitBudget
     * @return double
     * @author thuydtn
     */
    public Double getDiscountPayLimitBudget() {
        return discountPayLimitBudget;
    }
    /**
     * Set discountPayLimitBudget
     * @param   discountLimitBudget
     *          type double
     * @return
     * @author  thuydtn
     */
    public void setDiscountPayLimitBudget(Double discountLimitBudget) {
        this.discountPayLimitBudget = discountLimitBudget;
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
     * Get sortOrder
     * @return int
     * @author thuydtn
     */
    public int getSortOrder() {
        return sortOrder;
    }
    /**
     * Set sortOrder
     * @param   sortOrder
     *          type int
     * @return
     * @author  thuydtn
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}

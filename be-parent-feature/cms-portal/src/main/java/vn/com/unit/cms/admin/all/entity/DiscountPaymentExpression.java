/*******************************************************************************
 * Class        ：DiscountPaymentExpression
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
 * DiscountPaymentExpression
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_discount_payment_type_expression")
public class DiscountPaymentExpression extends AbstractTracking{
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.IDENTITY)
    private int id;
    @Column(name="code")
    private String code;
    @Column(name="m_discount_payment_type_id")
    private int discountPaymentTypeId;
    @Column(name="m_payment_card_id")
    private int paymentCardId;
    @Column(name="expression")
    private String expression;
    
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
     * Get paymentCardId
     * @return int
     * @author thuydtn
     */
    public int getPaymentCardId() {
        return paymentCardId;
    }
    
    /**
     * Set paymentCardId
     * @param   paymentCardId
     *          type int
     * @return
     * @author  thuydtn
     */
    public void setPaymentCardId(int paymentCardId) {
        this.paymentCardId = paymentCardId;
    }
    
    /**
     * Get expression
     * @return String
     * @author thuydtn
     */
    public String getExpression() {
        return expression;
    }
    
    /**
     * Set expression
     * @param   expression
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }
}

/*******************************************************************************
 * Class        ：DiscountPaymentExpression
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * DiscountPaymentExpression
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DiscountPaymentExpressionDto extends AbstractTracking{
    private int id;
    private String code;
    private int discountPaymentTypeId;
    private int paymentCardId;
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

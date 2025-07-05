/*******************************************************************************
 * Class        ：DocumentTypeSearchEnum
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * DocumentTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public enum MathExpressionTypeEnum {
    LOAN_INTEREST("math-expression.type.loan-interest"),

    DEPOSIT_INTEREST("math-expression.type.deposit-interest"),
    
    MAX_LENDING_AMOUNT("math-expression.type.max-lending-amount"),
    
    INCOME("math-expression.type.income");
    

    private String value;

    private MathExpressionTypeEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}

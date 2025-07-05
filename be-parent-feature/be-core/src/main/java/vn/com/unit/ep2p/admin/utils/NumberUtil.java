/*******************************************************************************
 * Class        NumberUtil
 * Created date 2019/02/19
 * Lasted date  2019/02/19
 * Author       KhoaNA
 * Change log   2019/02/19 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.utils;

/**
 * NumberUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class NumberUtil {

    /**
     * Compares number1, number2. The result is true if (number1 == null && number2 == null) or number1.equals(number2)
     * 
     * @param number1
     * @param number2
     * @return boolean
     * @author KhoaNA
     */
    public static boolean equals(Long number1, Long number2) {
    	boolean isEquals = false;
    	
    	if( number1 == null && number2 == null ) {
    		isEquals = true;
    	} else if( number1 != null && number2 != null ) {
    		isEquals = number1.equals(number2);
    	}

        return isEquals;
    }
}

/*******************************************************************************
 * Class        PageItem
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

/**
 * PageItem
 * This class use for paging
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class PageItem {
    
    /** Page number */
    private int number;
    /** Is current */
    private boolean current;

    /**
     * Constructor default
     * 
     * @param number
     * @param current
     */
    public PageItem(int number, boolean current){
        this.number = number;
        this.current = current;
    }

    /**
     * Get number
     * @return int
     * @author KhoaNA
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set number
     * @param   number
     *          type int
     * @return
     * @author  KhoaNA
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get current
     * @return boolean
     * @author KhoaNA
     */
    public boolean isCurrent() {
        return current;
    }

    /**
     * Set current
     * @param   current
     *          type boolean
     * @return
     * @author  KhoaNA
     */
    public void setCurrent(boolean current) {
        this.current = current;
    }
}
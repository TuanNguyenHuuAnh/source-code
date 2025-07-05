/*******************************************************************************
 * Class        ：TermTypeEnum
 * Created date ：2017/05/25
 * Lasted date  ：2017/05/25
 * Author       ：hand
 * Change log   ：2017/05/25：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * TermTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum TermTypeEnum {

    /** month */
    DAY("day", "term.day"),

    /** main */
    MONTH("month", "term.month"),

    /** year */
    YEAR("year", "term.year"),

    /** quarter */
    QUARTER("quarter", "term.quarter");

    /** value */
    private String value;

    /** name */
    private String name;

    private TermTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String toString() {
        return value;
    }

    public String getName() {
        return name;
    }
}

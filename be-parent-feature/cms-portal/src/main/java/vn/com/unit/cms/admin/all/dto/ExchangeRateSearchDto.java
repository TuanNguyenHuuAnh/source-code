/*******************************************************************************
 * Class        ：ExchangeRateSearchDto
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

/**
* ExchangeRateSearchDto
* 
* @version 01-00
* @since 01-00
* @author phunghn
*/
public class ExchangeRateSearchDto {
    /** displayDate */
	private Date displayDate;
	/** langCode */
	private String langCode;
	/** fieldValues */
	private List<String> fieldValues;
	/** fieldSearch */
	private String fieldSearch;
    /**
     * Get displayDate
     * @return Date
     * @author phunghn
     */
    public Date getDisplayDate() {
        return displayDate;
    }
    /**
     * Set displayDate
     * @param   displayDate
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
    }
    /**
     * Get langCode
     * @return String
     * @author phunghn
     */
    public String getLangCode() {
        return langCode;
    }
    /**
     * Set langCode
     * @param   langCode
     *          type String
     * @return
     * @author  phunghn
     */
    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }
    /**
     * Get fieldValues
     * @return List<String>
     * @author phunghn
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }
    /**
     * Set fieldValues
     * @param   fieldValues
     *          type List<String>
     * @return
     * @author  phunghn
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }
    /**
     * Get fieldSearch
     * @return String
     * @author phunghn
     */
    public String getFieldSearch() {
        return fieldSearch;
    }
    /**
     * Set fieldSearch
     * @param   fieldSearch
     *          type String
     * @return
     * @author  phunghn
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }
	
}

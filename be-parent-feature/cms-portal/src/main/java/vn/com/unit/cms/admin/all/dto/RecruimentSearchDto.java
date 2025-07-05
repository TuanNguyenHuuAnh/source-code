/*******************************************************************************
 * Class        ：RecruimentSearchDto
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;
/**
* RecruimentSearchDto
* 
* @version 01-00
* @since 01-00
* @author phunghn
*/
public class RecruimentSearchDto {
    /** langCode */
	private String langCode;
	/** position */
	private String position;
	/** fieldValues */
	private List<String> fieldValues;
	/** fieldSearch */
	private String fieldSearch;
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
     * Get position
     * @return String
     * @author phunghn
     */
    public String getPosition() {
        return position;
    }
    /**
     * Set position
     * @param   position
     *          type String
     * @return
     * @author  phunghn
     */
    public void setPosition(String position) {
        this.position = position;
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

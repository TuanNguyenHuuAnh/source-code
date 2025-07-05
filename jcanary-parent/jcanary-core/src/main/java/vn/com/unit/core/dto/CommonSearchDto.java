/*******************************************************************************
 * Class        ShareHolderSearchDto
 * Created date 2017/02/15
 * Lasted date  2017/02/15
 * Author       thuydtn
 * Change log   2017/02/1501-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;


/**
 * ShareHolderSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class CommonSearchDto extends AbstractCompanyDto {

    private String searchKeyIds;
    private List<String> listSearchKeyIds;
    private String searchValue;

    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;

    public String getSearchKeyIds() {
        return searchKeyIds;
    }

    public void setSearchKeyIds(String searchKeyIds) {
        this.searchKeyIds = searchKeyIds;
    }

    /**
     * Get searchValue
     * 
     * @return String
     * @author thuydtn
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * Set searchValue
     * 
     * @param searchValue type String
     * @return
     * @author thuydtn
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getFieldSearch() {
        return fieldSearch;
    }

    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public List<String> getListSearchKeyIds() {
        return listSearchKeyIds;
    }

    public void setListSearchKeyIds(List<String> listSearchKeyIds) {
        this.listSearchKeyIds = listSearchKeyIds;
    }

}

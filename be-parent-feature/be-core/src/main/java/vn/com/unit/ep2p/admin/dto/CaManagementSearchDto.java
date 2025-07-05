/*******************************************************************************
 * Class        :CaManagementSearchDto
 * Created date :2019/08/26
 * Lasted date  :2019/08/26
 * Author       :HungHT
 * Change log   :2019/08/26:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

/**
 * CaManagementSearchDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class CaManagementSearchDto extends AbstractCompanyDto {
    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;
    
    private String caName;
    private String accountName;
    private String caSlot;
    
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

    /**
     * getCaName
     * @return
     * @author trieuvd
     */
    public String getCaName() {
        return caName;
    }
    
    /**
     * setCaName
     * @param caName
     * @author trieuvd
     */
    public void setCaName(String caName) {
        this.caName = caName;
    }
    
    /**
     * getAccountName
     * @return
     * @author trieuvd
     */
    public String getAccountName() {
        return accountName;
    }
    
    /**
     * setAccountName
     * @param accountName
     * @author trieuvd
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    /**
     * getCaSlot
     * @return
     * @author trieuvd
     */
    public String getCaSlot() {
        return caSlot;
    }
    
    /**
     * setCaSlot
     * @param caSlot
     * @author trieuvd
     */
    public void setCaSlot(String caSlot) {
        this.caSlot = caSlot;
    }
    
}
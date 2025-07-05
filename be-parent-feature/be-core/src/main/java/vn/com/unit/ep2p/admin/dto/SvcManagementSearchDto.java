/*******************************************************************************
 * Class        :SvcManagementSearchDto
 * Created date :2019/04/21
 * Lasted date  :2019/04/21
 * Author       :HungHT
 * Change log   :2019/04/21:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

/**
 * SvcManagementSearchDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class SvcManagementSearchDto {
    
    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;
    
    private String name;
    private String description;
    private Long companyId;
    private Long categoryId;
    private String fileName;
    private Boolean activeFlag;
    private String formType;
    
    /**
     * Get fieldSearch
     * @return String
     * @author HungHT
     */
    public String getFieldSearch() {
        return fieldSearch;
    }
    
    /**
     * Set fieldSearch
     * @param   fieldSearch
     *          type String
     * @return
     * @author  HungHT
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }
    
    /**
     * Get fieldValues
     * @return List<String>
     * @author HungHT
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }
    
    /**
     * Set fieldValues
     * @param   fieldValues
     *          type List<String>
     * @return
     * @author  HungHT
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }
    
    /**
     * Get name
     * @return String
     * @author HungHT
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  HungHT
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get description
     * @return String
     * @author HungHT
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  HungHT
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get companyId
     * @return Long
     * @author HungHT
     */
    public Long getCompanyId() {
        return companyId;
    }
    
    /**
     * Set companyId
     * @param   companyId
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Get categoryId
     * @return Long
     * @author HungHT
     */
    public Long getCategoryId() {
        return categoryId;
    }
    
    /**
     * Set categoryId
     * @param   categoryId
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    /**
     * Get fileName
     * @return String
     * @author HungHT
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set fileName
     * @param   fileName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * Get activeFlag
     * @return Boolean
     * @author HungHT
     */
    public Boolean getActiveFlag() {
        return activeFlag;
    }
    
    /**
     * Set activeFlag
     * @param   activeFlag
     *          type Boolean
     * @return
     * @author  HungHT
     */
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * getFormType
     * @return
     * @author trieuvd
     */
    public String getFormType() {
        return formType;
    }

    /**
     * setFormType
     * @param formType
     * @author trieuvd
     */
    public void setFormType(String formType) {
        this.formType = formType;
    }
    
}
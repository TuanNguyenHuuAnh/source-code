/*******************************************************************************
 * Class        ：DocumentCategoryViewDto
 * Created date ：2017/05/15
 * Lasted date  ：2017/05/15
 * Author       ：thuydtn
 * Change log   ：2017/05/15：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

/**
 * DocumentCategoryViewDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentCategoryViewDto {
    private Long id;
    private String title;
    
    private List<Long> lstCustomerTypeId;
    private String strCustomerTypeId;
    
    private int numberOfDocument;
    
    /**
     * Get categoryId
     * @return Long
     * @author thuydtn
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set categoryCode
     * @param   categoryId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setId(Long categoryId) {
        this.id = categoryId;
    }
    
    /**
     * Get title
     * @return String
     * @author thuydtn
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Set title
     * @param   title
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get lstCustomerTypeId
     * @return List<Long>
     * @author thuydtn
     */
    public List<Long> getLstCustomerTypeId() {
        return lstCustomerTypeId;
    }
    /**
     * Set lstCustomerTypeId
     * @param   lstCustomerTypeId
     *          type List<Long>
     * @return
     * @author  thuydtn
     */
    public void setLstCustomerTypeId(List<Long> lstCustomerTypeId) {
        this.lstCustomerTypeId = lstCustomerTypeId;
        this.strCustomerTypeId = this.lstCustomerTypeId.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(","));
        
    }
    
    /**
     * Get strCustomerTypeId
     * @return String
     * @author thuydtn
     */
    public String getStrCustomerTypeId() {
        return strCustomerTypeId;
    }
    /**
     * Set strCustomerTypeId
     * @param   strCustomerTypeId
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setStrCustomerTypeId(String strCustomerTypeId) {
        this.strCustomerTypeId = strCustomerTypeId;
        if(!StringUtils.isEmpty(strCustomerTypeId)){
            this.lstCustomerTypeId = Stream.of(strCustomerTypeId.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Get numberOfDocument
     * @return int
     * @author thuydtn
     */
    public int getNumberOfDocument() {
        return numberOfDocument;
    }

    /**
     * Set numberOfDocument
     * @param   numberOfDocument
     *          type int
     * @return
     * @author  thuydtn
     */
    public void setNumberOfDocument(int numberOfDocument) {
        this.numberOfDocument = numberOfDocument;
    }
}

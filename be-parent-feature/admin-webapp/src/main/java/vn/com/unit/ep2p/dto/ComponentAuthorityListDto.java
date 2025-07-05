/*******************************************************************************
 * Class        :ComponentAuthoritySearchDto
 * Created date :2019/04/23
 * Lasted date  :2019/04/23
 * Author       :HungHT
 * Change log   :2019/04/23:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * ComponentAuthoritySearchDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ComponentAuthorityListDto {
    
    private Long itemId;

    /** data */
    private List<ComponentAuthorityDto> data;
    
    /** formId */
    private Long formId;
    
    /**
     * getFormId
     * @return
     * @author trieuvd
     */
    public Long getFormId() {
        return formId;
    }

    
    /**
     * setFormId
     * @param formId
     * @author trieuvd
     */
    public void setFormId(Long formId) {
        this.formId = formId;
    }

    /**
     * Get itemId
     * @return Long
     * @author HungHT
     */
    public Long getItemId() {
        return itemId;
    }
    
    /**
     * Set itemId
     * @param   itemId
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * Get data
     * 
     * @return List<ComponentAuthorityDto>
     * @author HungHT
     */
    public List<ComponentAuthorityDto> getData() {
        return data;
    }

    /**
     * Set data
     * 
     * @param data
     *            type List<ComponentAuthorityDto>
     * @return
     * @author HungHT
     */
    public void setData(List<ComponentAuthorityDto> data) {
        this.data = data;
    }
}
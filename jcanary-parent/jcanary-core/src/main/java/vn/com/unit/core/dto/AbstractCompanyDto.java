/*******************************************************************************
 * Class        ：AbstractCompanyDto
 * Created date ：2019/05/06
 * Lasted date  ：2019/05/06
 * Author       ：HungHT
 * Change log   ：2019/05/06：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import vn.com.unit.core.dto.ConditionSearchCommonDto;

/**
 * AbstractCompanyDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class AbstractCompanyDto extends ConditionSearchCommonDto {

    private Long companyId;
    private String companyName;
    private boolean companyAdmin;
    private List<Long> companyIdList;
    
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
     * Get companyName
     * @return String
     * @author HungHT
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
     * Set companyName
     * @param   companyName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     * Get companyAdmin
     * @return boolean
     * @author HungHT
     */
    public boolean isCompanyAdmin() {
        return companyAdmin;
    }
    
    /**
     * Set companyAdmin
     * @param   companyAdmin
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setCompanyAdmin(boolean companyAdmin) {
        this.companyAdmin = companyAdmin;
    }

    /**
     * Get companyIdList
     * @return List<Long>
     * @author HungHT
     */
    public List<Long> getCompanyIdList() {
        return companyIdList;
    }
    
    /**
     * Set companyIdList
     * @param   companyIdList
     *          type List<Long>
     * @return
     * @author  HungHT
     */
    public void setCompanyIdList(List<Long> companyIdList) {
        this.companyIdList = companyIdList;
    }
}

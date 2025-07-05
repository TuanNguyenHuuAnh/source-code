/*******************************************************************************
 * Class        ：JcaAccountInfoSelect2Dto
 * Created date ：2020/09/07
 * Lasted date  ：2020/09/07
 * Author       ：DaiTrieu
 * Change log   ：2020/09/07：01-00 DaiTrieu create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto.account;

import vn.com.unit.common.dto.Select2Dto;

/**
 * JcaAccountInfoSelect2Dto
 * 
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
public class JcaAccountInfoSelect2Dto extends Select2Dto {

    private String positionName;

    private String departmentName;

    public JcaAccountInfoSelect2Dto(String id, String text, String name, String positionName, String departmentName) {
        super(id, text, name);

        this.positionName = positionName;
        this.departmentName = departmentName;
    }

    /**
     * Get positionName
     * 
     * @return String
     * @author DaiTrieu
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * Set positionName
     * 
     * @param positionName
     *            type String
     * @return
     * @author DaiTrieu
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * Get departmentName
     * 
     * @return String
     * @author DaiTrieu
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Set departmentName
     * 
     * @param departmentName
     *            type String
     * @return
     * @author DaiTrieu
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}

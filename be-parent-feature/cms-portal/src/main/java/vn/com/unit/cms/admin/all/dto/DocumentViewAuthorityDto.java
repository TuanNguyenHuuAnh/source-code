/*******************************************************************************
 * Class        ：DocumentViewAuthorityDto
 * Created date ：2017/04/28
 * Lasted date  ：2017/04/28
 * Author       ：thuydtn
 * Change log   ：2017/04/28：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * DocumentViewAuthorityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentViewAuthorityDto {
    private String authorLoginName;
    private String strViewAuthorities;
    private List<String> lstViewAuthorities;
    /**
     * Get authorLoginName
     * @return String
     * @author thuydtn
     */
    public String getAuthorLoginName() {
        return authorLoginName;
    }
    /**
     * Set authorLoginName
     * @param   authorLoginName
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setAuthorLoginName(String authorLoginName) {
        this.authorLoginName = authorLoginName;
    }
    /**
     * Get strViewAuthorities
     * @return String
     * @author thuydtn
     */
    public String getStrViewAuthorities() {
        return strViewAuthorities;
    }
    /**
     * Set strViewAuthorities
     * @param   strViewAuthorities
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setStrViewAuthorities(String strViewAuthorities) {
        this.strViewAuthorities = strViewAuthorities;
        List<String> lstViewAuthorities = new ArrayList<String>();
        if(!StringUtils.isEmpty(strViewAuthorities)){
            String[] arrViewAuthority = strViewAuthorities.split(":");
            List<String> lstViewAuthority = new ArrayList<String>();
            for(String viewAuthority : arrViewAuthority){
                lstViewAuthority.add(viewAuthority);
            }
        }
        this.lstViewAuthorities = lstViewAuthorities;
    }
    /**
     * Get lstViewAuthorities
     * @return List<String>
     * @author thuydtn
     */
    public List<String> getLstViewAuthorities() {
        return lstViewAuthorities;
    }
    /**
     * Set lstViewAuthorities
     * @param   lstViewAuthorities
     *          type List<String>
     * @return
     * @author  thuydtn
     */
    public void setLstViewAuthorities(List<String> lstViewAuthorities) {
        this.lstViewAuthorities = lstViewAuthorities;
    }
}

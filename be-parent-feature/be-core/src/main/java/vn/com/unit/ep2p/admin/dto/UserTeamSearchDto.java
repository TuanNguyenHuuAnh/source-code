/*******************************************************************************
 * Class        ：UserTeamSearchDto
 * Created date ：2019/10/04
 * Lasted date  ：2019/10/04
 * Author       ：KhuongTH
 * Change log   ：2019/10/04：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

/**
 * UserTeamSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class UserTeamSearchDto {
    private String keySearch;
    private Long teamId;
    private Long companyId;
    
    public String getKeySearch() {
        return keySearch;
    }
    
    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }
    
    public Long getTeamId() {
        return teamId;
    }
    
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}

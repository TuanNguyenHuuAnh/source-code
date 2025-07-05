/*******************************************************************************
 * Class        ：ContactDto
 * Created date ：2019/11/11
 * Lasted date  ：2019/11/11
 * Author       ：NhanNV
 * Change log   ：2019/11/11：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * ContactDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public class JcaContactDto {
    private Long companyId;
    private List<JcaContactLangDto> listContact;
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public List<JcaContactLangDto> getListContact() {
        return listContact;
    }
    
    public void setListContact(List<JcaContactLangDto> listContact) {
        this.listContact = listContact;
    }
    
}

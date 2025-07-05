/*******************************************************************************
 * Class        ：ContactDto
 * Created date ：2019/11/11
 * Lasted date  ：2019/11/11
 * Author       ：NhanNV
 * Change log   ：2019/11/11：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;


/**
 * ContactDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public class JcaContactLangDto {
    private Long id;
    private Long companyId;
    private String languageCode;
    private String content;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
}

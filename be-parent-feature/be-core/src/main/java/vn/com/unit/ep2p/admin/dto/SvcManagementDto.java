/*******************************************************************************
 * Class        :SvcManagementDto
 * Created date :2019/04/21
 * Lasted date  :2019/04/21
 * Author       :HungHT
 * Change log   :2019/04/21:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.efo.entity.EfoFormLang;

/**
 * SvcManagementDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
public class SvcManagementDto extends AbstractCompanyDto {

    private Long formId;
    private Long companyId;
    private Long categoryId;
    private String categoryName;
    private Long businessId;
    private String businessCode;
    private String businessName;
    private String functionCode;
    private String functionName;
    private String formName;
    private String description;
    private String ozFilePath;
    private String iconFilePath;
    private Long iconRepoId;
    private Long displayOrder;
    private String deviceType;
    private boolean actived;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String deletedBy;
    private Date deletedDate;
    private MultipartFile file;
    private String formType;
    private String formTypeName;
    private List<EfoFormLang> formLangs;
    private String multiRecruiting;
    private String userName;
   
    public List<EfoFormLang> getFormLangs() {
        return formLangs;
    }
    
    /**
     * Set formLangs
     * @param   formLangs
     *          type List<FormLang>
     * @return
     * @author  taitt
     */
    public void setFormLangs(List<EfoFormLang> formLangs) {
        this.formLangs = formLangs;
    }
    
    /**
     * Get multiRecruiting
     * @return String
     * @author taitt
     */
   
 
}
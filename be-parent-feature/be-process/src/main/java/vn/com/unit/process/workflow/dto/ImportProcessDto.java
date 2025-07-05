/*******************************************************************************
 * Class        ：ImportProcessDto
 * Created date ：2019/09/19
 * Lasted date  ：2019/09/19
 * Author       ：KhuongTH
 * Change log   ：2019/09/19：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * ImportProcessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class ImportProcessDto {
    private Long companyId;
    private MultipartFile importFile;
    private boolean isOverride;
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public MultipartFile getImportFile() {
        return importFile;
    }
    
    public void setImportFile(MultipartFile importFile) {
        this.importFile = importFile;
    }
    
    public boolean getIsOverride() {
        return isOverride;
    }
    
    public void setIsOverride(boolean isOverride) {
        this.isOverride = isOverride;
    }
    
}

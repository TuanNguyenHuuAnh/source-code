package vn.com.unit.ep2p.dto;


/**
 * TemplateAttachDto
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public class TemplateAttachDto {
    private Long templateId;
    private String fileName; //pdf attach name
    
    public Long getTemplateId() {
        return templateId;
    }
    
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    
}

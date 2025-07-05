/*******************************************************************************
 * Class        ：TemplateParameterDto
 * Created date ：2020/02/03
 * Lasted date  ：2020/02/03
 * Author       ：trieuvd
 * Change log   ：2020/02/03：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * TemplateParameterDto
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public class TemplateParameterDto {
    private Long id;
    private String parameterName;
    private String parameterFullName;
    private String dataType;
    private String languageCode;
    private String format;
    private Long companyId;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getParameterName() {
        return parameterName;
    }
    
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    
    public String getParameterFullName() {
        return parameterFullName;
    }
    
    public void setParameterFullName(String parameterFullName) {
        this.parameterFullName = parameterFullName;
    }
    
    public String getDataType() {
        return dataType;
    }
    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getFormat() {
        return format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
}

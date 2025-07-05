/*******************************************************************************
 * Class        Template
 * Created date 2018/08/16
 * Lasted date  2018/08/16
 * Author       phatvt
 * Change log   2018/08/1601-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

//import org.springframework.web.util.HtmlUtils;

//import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Template
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Getter
@Setter
public class TemplateEmailDto {
    
    private Long templateId;
    
    private String code;
    
    private String templateName;
    
    private String subject; 
    
//    @Getter(value = AccessLevel.NONE)
//    @Setter(value = AccessLevel.NONE)
    private String templateContent;

	private Long companyId;
    
    private String companyName;
    
    private Integer actived;
    
    private String url;

//    public String getTemplateContent() {
//		return HtmlUtils.htmlEscape(templateContent);
//	}
//
//	public void setTemplateContent(String templateContent) {
//		this.templateContent = HtmlUtils.htmlUnescape(templateContent);
//	}
}

/*******************************************************************************
 * Class        :JcaEmailTemplateLangService
 * Created date :2020/12/23
 * Lasted date  :2020/12/23
 * Author       :SonND
 * Change log   :2020/12/23 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaEmailTemplateLangDto;
import vn.com.unit.core.entity.JcaEmailTemplateLang;

/**
 * JcaEmailTemplateLangService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaEmailTemplateLangService {
    
	public  List<JcaEmailTemplateLangDto> getJcaEmailTemplateLangDtoEmailTemplateId(Long emailTemplateId);
	
	public JcaEmailTemplateLangDto getJcaEmailTemplateLangDtoByEmailTemplateIdAndLangCode(Long emailTemplateId, String langCode);
	
	public JcaEmailTemplateLang saveJcaEmailTemplateLang(JcaEmailTemplateLang jcaEmailTemplateLang);
	
	public JcaEmailTemplateLang saveJcaEmailTemplateLangDto(JcaEmailTemplateLangDto jcaEmailTemplateLangDto);
	
    public void savejcaEmailTemplateLangDtos(Long emailTemplateId, List<JcaEmailTemplateLangDto> jcaEmailTemplateLangDtos);

    public void deleteJcaEmailTemplateLangDtosByEmailTemplateId(Long emailTemplateId);
}

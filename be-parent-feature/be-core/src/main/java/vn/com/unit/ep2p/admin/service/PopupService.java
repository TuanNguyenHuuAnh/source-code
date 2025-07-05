/*******************************************************************************
 * Class        TemplateService
 * Created date 2018/08/16
 * Lasted date  2018/08/16
 * Author       phatvt
 * Change log   2018/08/1601-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import org.springframework.web.servlet.ModelAndView;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.PopupDto;
import vn.com.unit.ep2p.dto.PopupSearchDto;
import vn.com.unit.ep2p.dto.TemplateEmailDto;
import vn.com.unit.ep2p.dto.TemplateSearchDto;

import java.util.List;
import java.util.Locale;

/**
 * TemplateService
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public interface PopupService {
    List<Select2Dto> getTemplate(String term, String fileFormat);
    PageWrapper<PopupDto> doSearch(int page, PopupSearchDto searchDto, int pageSize, Locale locale) throws DetailException;
    JcaEmailTemplate getTemplateById(Long templateId);
    void deleteTemplate(Long templateId);
    public TemplateEmailDto getTemplateEmailDtoById(Long id);
    public void InitScreenEdit(ModelAndView mav, PopupDto objectDto, Locale locale);

    PopupDto savePopup(PopupDto objectDto);

    PopupDto getPopupDtoById(Long id);

    List<Select2Dto> getListPopup();
}

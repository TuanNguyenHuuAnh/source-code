/*******************************************************************************
 * Class        TemplateService
 * Created date 2018/08/16
 * Lasted date  2018/08/16
 * Author       phatvt
 * Change log   2018/08/1601-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.TemplateEmailDto;
import vn.com.unit.ep2p.dto.TemplateSearchDto;

/**
 * TemplateService
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public interface TemplateService {

    /** saveTemplate
     *
     * @param template
     * @author phatvt
     */
    void saveTemplate(JcaEmailTemplate template);

    /** getTemplate
     *
     * @param term
     * @param fileFormat
     * @return
     * @author phatvt
     */
    List<Select2Dto> getTemplate(String term, String fileFormat);
    /**
     * doSearch
     *
     * @param page
     * @param searchDto
     * @return
     * @author phatvt
     * @throws DetailException 
     */
    PageWrapper<JcaEmailTemplateDto> doSearch(int page, TemplateSearchDto searchDto, int pageSize,Locale locale) throws DetailException;

    /** getTemplateById
     *
     * @param templateId
     * @return
     * @author phatvt
     */
    JcaEmailTemplate getTemplateById(Long templateId);

    /** deleteTemplate
     *
     * @param templateId
     * @author phatvt
     */
    void deleteTemplate(Long templateId);
    
    /**
     * getTemplateByCompanyId
     * @param term
     * @param fileFormat
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<Select2Dto> getTemplateByCompanyId(String term, String fileFormat, Long companyId);
    
    /**
     * @param name
     * @return
     */
    List<Select2Dto> getTemplateswithoutWorkflow(List<String> names) throws Exception;
    
    /**
     * getByCodeAndCompanyId
     * @param code
     * @param companyId
     * @return
     * @author trieuvd
     */
    JcaEmailTemplate getByCodeAndCompanyId(String code, Long companyId);
    
    /**
     * @param templateId
     * @return
     * @throws Exception
     */
    String getNameById(Long templateId) throws Exception;
    
    /**
     * getListParam
     * @param template
     * @return
     * @author trieuvd
     */
    List<String> getListParam(JcaEmailTemplate template);
    
    /**
     * 
     * getTemplateDtoById
     * @param templateId
     * @return
     * @author taitt
     */
    List<TemplateEmailDto> getTemplateDtoById(Long templateId);
    
    /**
     * 
     * getMapLangAndSubjectNotificationById
     * @param templateId
     * @return
     * @author taitt
     */
    TemplateEmailDto getMapLangAndSubjectNotificationById(Long templateId);
    
    
    /**
     * <p>
     * Save template dto.
     * </p>
     *
     * @author TrieuVD
     * @param templateEmailDto
     *            type {@link TemplateEmailDto}
     */
    TemplateEmailDto saveTemplateDto(TemplateEmailDto templateEmailDto);

    /**
     * <p>
     * Get template email dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link TemplateEmailDto}
     */
    public TemplateEmailDto getTemplateEmailDtoById(Long id);

    /**
     * <p>
     * Inits the screen edit.
     * </p>
     *
     * @author TrieuVD
     * @param mav
     *            type {@link ModelAndView}
     * @param objectDto
     *            type {@link TemplateEmailDto}
     * @param locale
     *            type {@link Locale}
     */
    public void InitScreenEdit(ModelAndView mav, TemplateEmailDto objectDto, Locale locale);
    
}

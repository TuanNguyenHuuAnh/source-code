/*******************************************************************************
 * Class        ：AppNotiTemplateService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaNotiTemplateDto;
import vn.com.unit.core.dto.JcaNotiTemplateSearchDto;
import vn.com.unit.core.service.JcaNotiTemplateService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.NotiTemplateSearchDto;

/**
 * <p>
 * AppNotiTemplateService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface AppNotiTemplateService extends JcaNotiTemplateService {

    /**
     * <p>
     * Get select 2 dto list.
     * </p>
     *
     * @author TrieuVD
     * @param keySearch
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @param isPaging
     *            type {@link boolean}
     * @return {@link List<Select2Dto>}
     */
    public List<Select2Dto> getSelect2DtoList(String keySearch, Long companyId, boolean isPaging);

    /**
     * <p>
     * Get jca noti template dto list.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JcaNotiTemplateSearchDto}
     * @param pageSize
     *            type {@link int}
     * @param page
     *            type {@link int}
     * @return {@link PageWrapper<JcaNotiTemplateDto>}
     * @throws DetailException 
     */
    public PageWrapper<JcaNotiTemplateDto> getJcaNotiTemplateDtoList(NotiTemplateSearchDto searchDto, int pageSize, int page) throws DetailException;
    
    /**
     * <p>
     * Inits the screen edit.
     * </p>
     *
     * @author TrieuVD
     * @param mav
     *            type {@link ModelAndView}
     * @param objectDto
     *            type {@link JcaNotiTemplateDto}
     * @param locale
     *            type {@link Locale}
     */
    public void initScreenEdit(ModelAndView mav, JcaNotiTemplateDto objectDto, Locale locale);
    
    /**
     * <p>
     * Inits the screen list.
     * </p>
     *
     * @author TrieuVD
     * @param mav
     *            type {@link ModelAndView}
     * @param searchDto
     *            type {@link NotiTemplateSearchDto}
     * @param locale
     *            type {@link Locale}
     */
    public void initScreenList(ModelAndView mav, NotiTemplateSearchDto searchDto, Locale locale);
}

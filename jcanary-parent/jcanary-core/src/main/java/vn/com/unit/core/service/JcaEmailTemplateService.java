/*******************************************************************************
 * Class        :JcaEmailTemplateService
 * Created date :2020/12/23
 * Lasted date  :2020/12/23
 * Author       :SonND
 * Change log   :2020/12/23 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.dto.JcaEmailTemplateSearchDto;
import vn.com.unit.core.dto.JcaGroupEmailDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaEmailTemplateService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaEmailTemplateService extends DbRepositoryService<JcaEmailTemplate, Long> {

    /** The Constant TABLE_ALIAS_JCA_EMAIL_TEMPLATE. */
    static final String TABLE_ALIAS_JCA_EMAIL_TEMPLATE= "email";
    
    /**
     * <p>
     * Count jca email template dto by condition.
     * </p>
     *
     * @param jcaEmailTemplateSearchDto
     *            type {@link JcaEmailTemplateSearchDto}
     * @return {@link int}
     * @author sonnd
     */
    public  int countJcaEmailTemplateDtoByCondition(JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto);
    
	/**
     * <p>
     * Gets the jca email template dto by condition.
     * </p>
     *
     * @param jcaEmailTemplateSearchDto
     *            type {@link JcaEmailTemplateSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the jca email template dto by condition
     * @author sonnd
     */
	public  List<JcaEmailTemplateDto> getJcaEmailTemplateDtoByCondition(JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto,  Pageable pageable);
	
	/**
     * <p>
     * Gets the jca email template by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca email template by id
     * @author sonnd
     */
	public JcaEmailTemplateDto getJcaEmailTemplateById(Long id);
	
	/**
     * <p>
     * Save jca email template.
     * </p>
     *
     * @param jcaEmailTemplate
     *            type {@link JcaEmailTemplate}
     * @return {@link JcaEmailTemplate}
     * @author sonnd
     */
	public JcaEmailTemplate saveJcaEmailTemplate(JcaEmailTemplate jcaEmailTemplate);
	
	/**
     * <p>
     * Save jca email template dto.
     * </p>
     *
     * @param jcaEmailTemplateDto
     *            type {@link JcaEmailTemplateDto}
     * @return {@link JcaEmailTemplate}
     * @author sonnd
     */
	public JcaEmailTemplate saveJcaEmailTemplateDto(JcaEmailTemplateDto jcaEmailTemplateDto);
	
	/**
     * <p>
     * Gets the jca email template dto by id.
     * </p>
     *
     * @param templateId
     *            type {@link Long}
     * @return the jca email template dto by template id
     * @author sonnd
     */
	public JcaEmailTemplateDto getJcaEmailTemplateDtoById(Long templateId);
	
	/**
     * <p>
     * Delete jca email template.
     * </p>
     *
     * @param emailTemplateId
     *            type {@link Long}
     * @author sonnd
     */
	public void deleteJcaEmailTemplate(Long emailTemplateId);
	
	/**
     * <p>
     * Delete jca email template dto.
     * </p>
     *
     * @param emailTemplateId
     *            type {@link Long}
     * @author sonnd
     */
	public void deleteJcaEmailTemplateDto(Long emailTemplateId);
	
	/**
     * <p>
     * Escape template html.
     * </p>
     *
     * @param templateHtml
     *            type {@link String}
     * @return {@link String}
     * @author sonnd
     */
	public String escapeTemplateHtml(String templateHtml);
	
	/**
     * <p>
     * Unescape template html.
     * </p>
     *
     * @param templateHtmlEscape
     *            type {@link String}
     * @return {@link String}
     * @author sonnd
     */
	public String unescapeTemplateHtml(String templateHtmlEscape);

    public JcaEmailTemplateDto findJcaEmailTemplateDtoByCode(String code);
    
    public List<JcaGroupEmailDto> findGourpMail(String id);
    
    public JcaGroupEmailDto findUserNameAction(String userName);

}

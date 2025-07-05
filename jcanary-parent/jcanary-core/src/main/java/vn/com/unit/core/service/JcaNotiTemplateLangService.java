/*******************************************************************************
 * Class        ：JcaNotiTemplateLangService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaNotiTemplateLangDto;
import vn.com.unit.core.entity.JcaNotiTemplateLang;

/**
 * <p>
 * JcaNotiTemplateLangService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JcaNotiTemplateLangService {
    
    /**
     * <p>
     * Get jca noti template lang dto list by template id.
     * </p>
     *
     * @author TrieuVD
     * @param templateId
     *            type {@link Long}
     * @return {@link List<JcaNotiTemplateLangDto>}
     */
    public List<JcaNotiTemplateLangDto> getJcaNotiTemplateLangDtoListByTemplateId(Long templateId);
    
    /**
     * <p>
     * Save jca noti template lang.
     * </p>
     *
     * @author TrieuVD
     * @param jcaNotiTemplateLang
     *            type {@link JcaNotiTemplateLang}
     * @return {@link JcaNotiTemplateLang}
     */
    public JcaNotiTemplateLang saveJcaNotiTemplateLang(JcaNotiTemplateLang jcaNotiTemplateLang);
    
    /**
     * <p>
     * Save jca noti template lang dto.
     * </p>
     *
     * @author TrieuVD
     * @param jcaNotiTemplateLangDto
     *            type {@link JcaNotiTemplateLangDto}
     * @return {@link JcaNotiTemplateLang}
     */
    public JcaNotiTemplateLang saveJcaNotiTemplateLangDto(JcaNotiTemplateLangDto jcaNotiTemplateLangDto);
}

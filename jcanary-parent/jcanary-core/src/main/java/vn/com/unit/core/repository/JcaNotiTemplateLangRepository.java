/*******************************************************************************
 * Class        ：JcaNotiTemplateLangRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaNotiTemplateLangDto;
import vn.com.unit.core.entity.JcaNotiTemplateLang;
import vn.com.unit.db.repository.DbRepository;

/**
 * <p>
 * JcaNotiTemplateLangRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JcaNotiTemplateLangRepository extends DbRepository<JcaNotiTemplateLang, Long>{
    
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
    public List<JcaNotiTemplateLangDto> getJcaNotiTemplateLangDtoListByTemplateId(@Param("templateId") Long templateId);
    
    /**
     * <p>
     * Get jca noti template lang dto by template id and lang id.
     * </p>
     *
     * @author TrieuVD
     * @param templateId
     *            type {@link Long}
     * @param langId
     *            type {@link Long}
     * @return {@link JcaNotiTemplateLangDto}
     */
    public JcaNotiTemplateLang getJcaNotiTemplateLangByTemplateIdAndLangId(@Param("templateId") Long templateId, @Param("langId") Long langId);
    
    /**
     * <p>
     * Delete by template id.
     * </p>
     *
     * @author TrieuVD
     * @param templateId
     *            type {@link Long}
     */
    @Modifying
    public void deleteByTemplateId(@Param("templateId") Long templateId);
}

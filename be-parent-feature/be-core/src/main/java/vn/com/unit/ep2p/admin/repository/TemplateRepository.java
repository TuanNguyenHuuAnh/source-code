/*******************************************************************************
 * Class        TemplateRepository
 * Created date 2018/08/16
 * Lasted date  2018/08/16
 * Author       phatvt
 * Change log   2018/08/1601-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.core.repository.JcaEmailTemplateRepository;
import vn.com.unit.ep2p.dto.TemplateEmailDto;
import vn.com.unit.ep2p.dto.TemplateSearchDto;
import vn.com.unit.common.dto.Select2Dto;

/**
 * TemplateRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public interface TemplateRepository extends JcaEmailTemplateRepository {
    /**
     * getTemplate
     *
     * @param term
     * @return
     * @author phatvt
     */
    List<Select2Dto>getTemplate(@Param("term") String term, @Param("fileFormat") String fileFormat);
    
    /**
     * countTemplateByCondition
     *
     * @param searchDto
     * @return
     * @author phatvt
     */
    public int countTemplateByCondition( @Param("searchDto")TemplateSearchDto searchDto);
    /**
     * findAllTemplateListByCondition
     *
     * @param offset
     * @param sizeOfPage
     * @param searchDto
     * @return
     * @author phatvt
     */
    public List<TemplateEmailDto> findAllTemplateListByCondition(
            @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, 
            @Param("searchDto") TemplateSearchDto searchDto,@Param("langCode") String langCode);
    
    @Modifying
    public void deleteTemplate(@Param("templateId") Long templateId, @Param("user") String user);
    
    /**
     * getTemplateByCompanyId
     * @param term
     * @param fileFormat
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<Select2Dto>getTemplateByCompanyId(@Param("term") String term, @Param("fileFormat") String fileFormat, @Param("companyId") Long companyId, @Param("isPaging") boolean isPaging);

    /**
     * @param names
     * @return
     */
    List<Select2Dto> findTemplateswithoutWorkflow(@Param("names") List<String> names);
    
    /**
     * getByCodeAndCompanyId
     * @param code
     * @param companyId
     * @return
     * @author trieuvd
     */
    JcaEmailTemplate getByCodeAndCompanyId(@Param("code") String code, @Param("companyId") Long companyId);
    
    /**
     * @param templateId
     * @return
     */
    String findNameById(@Param("templateId") Long templateId);
    
    /**
     * @return
     */
    List<Select2Dto> findAllSelection();
    
    /**
     * @param code
     * @return
     */
    JcaEmailTemplate findByCode(@Param("code") String code);
    
    /**
     * 
     * findTemplateDtoById
     * @param id
     * @return
     * @author taitt
     */
    List<TemplateEmailDto> findTemplateDtoById(@Param("id") Long id);
    
}

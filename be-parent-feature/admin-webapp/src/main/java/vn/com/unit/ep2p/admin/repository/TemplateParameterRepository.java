/*******************************************************************************
 * Class        ：TemplateParameterRepository
 * Created date ：2020/02/03
 * Lasted date  ：2020/02/03
 * Author       ：trieuvd
 * Change log   ：2020/02/03：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.repository.JcaEmailTemplateLangRepository;
import vn.com.unit.ep2p.dto.TemplateParameterDto;

/**
 * TemplateParameterRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public interface TemplateParameterRepository extends JcaEmailTemplateLangRepository{
    
    /**
     * findByCompany
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<TemplateParameterDto> findByCompany(@Param("companyId") Long companyId);
}

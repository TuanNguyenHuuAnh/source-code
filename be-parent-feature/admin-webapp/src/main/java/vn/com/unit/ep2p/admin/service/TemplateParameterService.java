/*******************************************************************************
 * Class        ：TemplateParameterService
 * Created date ：2020/02/03
 * Lasted date  ：2020/02/03
 * Author       ：trieuvd
 * Change log   ：2020/02/03：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

/**
 * TemplateParameterService
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public interface TemplateParameterService {
    
    /**
     * getListParameter
     * @return
     * @author trieuvd
     */
    List<String> getListParameter();
    
    /**
     * getListParameterByCompanyId
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<String> getListParameterByCompanyId(Long companyId);
}

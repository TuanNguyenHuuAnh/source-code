/*******************************************************************************
 * Class        ：UserGuideRepository
 * Created date ：2019/11/12
 * Lasted date  ：2019/11/12
 * Author       ：taitt
 * Change log   ：2019/11/12：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaUserGuideDto;
import vn.com.unit.core.repository.JcaUserGuideRepository;

/**
 * UserGuideRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface UserGuideRepository extends JcaUserGuideRepository {

    /**
     * 
     * getUserGuideDtoByCompanyIdAndLanguage
     * @param companyId
     * @param lang
     * @return
     * @author taitt
     */
    JcaUserGuideDto getUserGuideDtoByIdAndLanguage(@Param("id")Long id);
    
    /**
     * 
     * getUserGuideDto
     * @return
     * @author taitt
     */
    List<JcaUserGuideDto> getUserGuideDto();
    
    /**
     * 
     * getListUserGuideDtoByCompanyIdAndLanguage
     * @param companyId
     * @param lang
     * @return
     * @author taitt
     */
    List<JcaUserGuideDto> getListUserGuideDtoByCompanyIdAndLanguage(@Param("companyId")Long companyId,@Param("codes")List<String> lang,@Param("appCode")String appCode);
}

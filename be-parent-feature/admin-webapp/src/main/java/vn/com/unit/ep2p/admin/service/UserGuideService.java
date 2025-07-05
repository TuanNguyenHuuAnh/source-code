/*******************************************************************************
 * Class        ：UserGuideService
 * Created date ：2019/11/12
 * Lasted date  ：2019/11/12
 * Author       ：taitt
 * Change log   ：2019/11/12：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import org.springframework.http.ResponseEntity;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.dto.JcaUserGuideDto;
import vn.com.unit.core.service.JcaUserGuideService;

/**
 * UserGuideService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface UserGuideService extends JcaUserGuideService{
    
    public ResponseEntity<byte[]> getUserGuideFileById(Long id);
    
    public List<JcaUserGuideDto> getListUserGuideDtoByCompanyIdAndLocale(Long companyId, List<String> Listlang,String appCode);
    
    public List<JcaUserGuideDto> getListUserGuideDto();
    
    public JcaUserGuideDto getById(Long id);
    
    public void saveUserGuide(JcaUserGuideDto userGuideDto,Locale locale) throws Exception;
    
    public void deleteUserGuide(Long id) throws AppException;
}

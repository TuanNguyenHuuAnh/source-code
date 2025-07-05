/*******************************************************************************
 * Class        SystemSettingRepository
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       trieunh
 * Change log   2017/02/1401-00 trieunh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.entity.JcaSystemConfig;
import vn.com.unit.core.repository.JcaSystemConfigRepository;

/**
 * SystemSettingRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh
 */
public interface SystemSettingRepository extends JcaSystemConfigRepository {
    /**
     * Find System Setting by key
     * @param key
     * @return SystemSetting
     */
    JcaSystemConfig findKey(@Param("key")String key);
	
	/**
	 * Update System Setting by key
	 * @param key
	 * @param value
	 */
	@Modifying
	int updatedByKey(@Param("key") String key, @Param("value") String value);
	
//    /**
//     * findByCompanyId
//     * 
//     * @param companyId
//     * @return
//     * @author HungHT
//     */
//    List<JcaSystemConfig> findByCompanyId(@Param("companyId") Long companyId);
    
    /**
     * findByCompanyId2
     * @param companyId
     * @return
     * @author hangnkm
     */
    List<JcaSystemConfig> findByCompanyId2(@Param("companyId") Long companyId);
    
    /**
     * findKeyAndCompanyId
     * 
     * @param key
     * @param companyId
     * @return
     * @author HungHT
     */
    JcaSystemConfig findKeyAndCompanyId(@Param("key") String key, @Param("companyId") Long companyId);
    
//    /**
//     * mergeSystemSetting
//     * 
//     * @param companyId
//     * @author HungHT
//     */
//    @Modifying
//    void mergeSystemSetting(@Param("companyId") Long companyId);
}

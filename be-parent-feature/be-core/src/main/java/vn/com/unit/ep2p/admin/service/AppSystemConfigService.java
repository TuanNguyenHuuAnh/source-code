/*******************************************************************************
 * Class        AppSystemConfigService
 * Created date 2019/01/22
 * Lasted date  2019/01/22
 * Author       VinhLT
 * Change log   2019/01/2201-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.Locale;

import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.dto.AppSystemConfigDto;
import vn.com.unit.ep2p.dto.ResMapObject;

/**
 * AppSystemConfigService
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public interface AppSystemConfigService extends JcaSystemConfigService{

	/**
	 * buildSystemConfigForcs
	 *
	 * @param systemConfigEP2PDto
	 * @return
	 * @author VinhLT
	 */
	AppSystemConfigDto buildSystemConfigForcs(AppSystemConfigDto systemConfigEP2PDto, Long companyId);

	/**
	 * updateSystemConfigForcs
	 *
	 * @param systemConfigDto
	 * @throws Exception
	 * @author VinhLT
	 */
	void updateSystemConfigForcs(AppSystemConfigDto systemConfigDto) throws Exception;
	
    /**
     * connectToOZRepository
     * 
     * @return
     * @author HungHT
     */
	ResMapObject connectToOZRepository();

    /**
     * updateSystemConfig
     * @param systemConfigDto
     * @throws Exception
     * @author ngannh
     */
    void updateSystemConfig(AppSystemConfigDto systemConfigDto) throws Exception;

    /**
     * buildSystemConfig
     * @param systemConfigDto
     * @param companyId
     * @return
     * @author ngannh
     */
    AppSystemConfigDto buildSystemConfig(AppSystemConfigDto systemConfigDto, Long companyId);

    /**
     * validateSystemConfig
     * @param systemConfigDto
     * @param locale
     * @return
     * @author ngannh
     */
    MessageList validateSystemConfig(AppSystemConfigDto systemConfigDto, Locale locale);
}

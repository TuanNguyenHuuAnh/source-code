/*******************************************************************************
 * Class        MenuConfig
 * Created date 2017/06/06
 * Lasted date  2017/06/06
 * Author       phunghn
 * Change log   2017/06/0601-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.service.MenuService;

/**
 * MenuConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Component(value = "menuConfig")
@RequestScope
public class MenuConfig {

	/** menuService */
	@Autowired
	private MenuService menuService;
	
	@Autowired
    private SystemConfig systemConfig;
	
	
	private static final Logger logger = LoggerFactory.getLogger(MenuConfig.class);
	
	public MenuDto buildMenuStructure(String lange, Long companyId) {

		MenuDto result = new MenuDto();

		try {
			if (lange == null) {
				lange = "en";
			}
			result = menuService.buildMenuStructure(lange, companyId);
		} catch (Exception e) {
		    logger.error("#buildMenuStructure#",e);
		}

		return result;
		
	}
	
	public String getFormDate(){
	    return systemConfig.getConfig(SystemConfig.DATE_PATTERN);
	}
	
	
	public String getSystemName(){
        return systemConfig.getConfig(SystemConfig.DISPLAY_SYSTEM_NAME);
    }
}

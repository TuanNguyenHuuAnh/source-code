/*******************************************************************************
 * Class        ：ChatComponent
 * Created date ：2017/07/13
 * Lasted date  ：2017/07/13
 * Author       ：phunghn
 * Change log   ：2017/07/13：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

//import vn.com.unit.cms.admin.all.service.RoomClientService;
//import vn.com.unit.jcanary.config.SystemConfig;

import vn.com.unit.cms.admin.all.service.RoomClientService;
import vn.com.unit.core.config.SystemConfig;

/**
 * ChatComponent
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Component(value = "chatComponent")
@RequestScope
public class ChatComponent {

	@Autowired
	RoomClientService RoomClientService;

	@Autowired
	SystemConfig systemConfig;

	public int getOnOffChat() {
		return RoomClientService.getOnOffChat();
	}

	public String getDateFormat() {
		return systemConfig.getConfig(SystemConfig.DATE_PATTERN);
	}

}

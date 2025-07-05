/*******************************************************************************
 * Class        ：PageSizeConfig
 * Created date ：2017/08/28
 * Lasted date  ：2017/08/28
 * Author       ：phunghn
 * Change log   ：2017/08/28：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import vn.com.unit.core.config.SystemConfig;

/**
 * PageSizeConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Component(value = "pageSizeConfig")
@RequestScope
public class PageSizeConfig {

	
	public List<Integer> getListPage(int pageSize,  SystemConfig systemConfig){
		
		return systemConfig.getListPageSize();
	}
	
	public int getSizeOfPage(List<Integer> listPageSize, int pageSize, SystemConfig systemConfig){
		
        int sizeOfPage = 0;
        if (pageSize == 0) {
            sizeOfPage = !listPageSize.isEmpty() ? listPageSize.get(0) : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        } else {
            sizeOfPage = pageSize;
        }
        return sizeOfPage;
	}

}

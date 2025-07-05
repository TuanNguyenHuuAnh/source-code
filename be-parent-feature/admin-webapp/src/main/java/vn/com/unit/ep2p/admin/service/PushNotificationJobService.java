/*******************************************************************************
 * Class        ：PushNotificationJobService
 * Created date ：2019/09/03
 * Lasted date  ：2019/09/03
 * Author       ：KhuongTH
 * Change log   ：2019/09/03：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContext;

import vn.com.unit.ep2p.admin.sla.dto.PushNotificationJobDto;


/**
 * PushNotificationJobService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface PushNotificationJobService {
    
    public boolean saveJob(List<PushNotificationJobDto> listPushNotificationJobDto);
    
    public int deleteJobByTaskId(Long taskId);
    
    public int scanTableAndPushNotif(SecurityContext securityContext, Long companyId) throws Exception;
}

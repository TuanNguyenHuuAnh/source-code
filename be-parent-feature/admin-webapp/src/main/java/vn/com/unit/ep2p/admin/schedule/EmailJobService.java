package vn.com.unit.ep2p.admin.schedule;

import java.util.List;

import org.springframework.security.core.context.SecurityContext;

import vn.com.unit.core.entity.JcaEmail;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;

/**
 * EmailJobService
 * 
 * @version 01-00
 * @since 01-00
 * @author hientn
 */
public interface EmailJobService {
	
	/**
	 * @param securityContext
	 * @param job
	 * @param jobSchedule
	 * @throws Exception
	 */
	void sendEmailForAlertJob(SecurityContext securityContext, QrtzMJob job, QrtzMJobLog jobLog) throws Exception;
	
	/**
	 * updateStatusForListEmail
	 * @param listEmail
	 * @throws Exception
	 * @author trieuvd
	 */
	void updateStatusForListEmail(List<JcaEmail> listEmail, String status) throws Exception;

}

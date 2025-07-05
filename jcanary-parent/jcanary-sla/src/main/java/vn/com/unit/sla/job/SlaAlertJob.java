/*******************************************************************************
 * Class        ：SlaAlertJob
 * Created date ：2021/01/22
 * Lasted date  ：2021/01/22
 * Author       ：TrieuVD
 * Change log   ：2021/01/22：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.job;

import java.util.Calendar;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.quartz.job.JcaQuartzJob;
import vn.com.unit.sla.service.SlaActionService;
import vn.com.unit.sla.utils.SlaDateUtils;

/**
 * SlaAlertJob
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SlaAlertJob extends JcaQuartzJob {

    @Autowired
    private SlaActionService slaActionService;

    @Autowired
    private JCommonService commonService;

    //private static final Logger logger = LoggerFactory.getLogger(SlaAlertJob.class);

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.JcaQuartzJob#executeJcaQuartzJob(org.quartz.JobExecutionContext)
     */
    @Override
    protected void executeJcaQuartzJob(JobExecutionContext context) {
        
//        DebugLogger debugLogger = new DebugLogger();
//        DebugLogger.debug("[PT Tracking] | [SlaAlertJob] | [executeJcaQuartzJob] | [%d] | [Begin] | [%s] | [%d] ",
//                Thread.currentThread().getId(), debugLogger.getStart(), 0);
        System.out.println("===================> start executeJcaQuartzJob");
        try {
            Date toDate = commonService.getSystemDate();
            Date fromDate = SlaDateUtils.truncate(toDate, Calendar.DATE);
//            slaActionService.scanSlaAlerJob(fromDate, toDate);
        } catch (Exception e) {
//            DebugLogger.debug("[PT Tracking] | [SlaAlertJob] | [executeJcaQuartzJob] | [%d] | [Error] | [%s] ",
//                    Thread.currentThread().getId(), e.getMessage());
            System.out.println(" error: " + e.getMessage());
        } finally {
//            debugLogger.setEndTime();
//            DebugLogger.debug("[PT Tracking] | [SlaAlertJob] | [executeJcaQuartzJob] | [%d] | [End] | [%s] | [%d] ",
//                    Thread.currentThread().getId(), debugLogger.getEnd(), debugLogger.getElapsedTime());
            System.out.println("end executeJcaQuartzJob <===================");
        }
    }

}

/*******************************************************************************
 * Class        ：JobConstant
 * Created date ：2021/03/18
 * Lasted date  ：2021/03/18
 * Author       ：Tan Tai
 * Change log   ：2021/03/18：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.constant;

import org.springframework.stereotype.Component;

/**
 * JobConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
@Component(value = "jobConstant")
public class JobConstant {

public static final String CUTOFF_JOBGROUP = "Cutoff";
    
    /** MAV_MESSAGE_ALERT */
    public static final String MAV_MESSAGE_ALERT = "/views/commons/message-alert.html";

    // ---------------------------- Constant for role ----------------------------

    // Setup job scheduler
    public static final String SC1_S02_JOBSCHEDULE = "SC1#S02_JobSchedule";

    // Setup job master
    public static final String SC1_S03_JOBMASTER = "SC1#S03_JobMaster";
    
    // Setup schedule master
    public static final String SC1_S04_SCHEDULEMASTER = "SC1#S04_ScheduleMaster";

}

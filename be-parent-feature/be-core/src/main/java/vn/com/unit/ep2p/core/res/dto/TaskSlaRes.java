package vn.com.unit.ep2p.core.res.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskSlaRes {
    
    private Long docId;
    private Long taskId;
    private Long ownerId;
    private Long ownerName;
    private Long ownerOrgId;
    private String ownerOrgName;
    private Date planStartDate;
    private Date planDueDate;
    private Long planEstimateTime;
    private Integer planCalandarType;
    private String planEstimateUnitTime;
    private Long planTotalTime;
    private Date actualStartDate;
    private Date actualEndDate;
    private Long actualElapseTime;
    private List<CalendarTimeOffRes> calendarTaskSlaResList;
}

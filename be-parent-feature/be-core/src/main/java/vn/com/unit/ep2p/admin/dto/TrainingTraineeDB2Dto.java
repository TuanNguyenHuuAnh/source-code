package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrainingTraineeDB2Dto {
    private String courseId;
    private String idNumber;
    private String agentName;
    private String name;
    private String agentCode;
    private Date effectivedDate;
    private Date attendanceTime;
    private Date registerTime;
    private String agentType;
    private String umCode;
    private String umName;
    private String bmCode;
    private String bmName;
    private String officeCode;
    private String officeName;
    private Integer attendance;
    private String territorry;
    private String area;
    private String region;
    private String office;
    private String position;
    private String isUserInvited;
    private String code;
}

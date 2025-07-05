package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsSlaReminderAgentDto {

    private String userMail;
    private String userName;
    private Long no;
    private Long id;
    private String applyForPosition;
    private String idNo;
    private String candidateName;
    private Date dob;
    private String recruiterCode;
    private String recruiterName;
    private String recruiterCodeOrIdCard;
    private String managerIdNo;
    private String managerName;
    private String managerCodeOrIdCard;
    private String managerIndirectIdNo;
    private String managerIndirectName;
    private String managerIndirectCodeOrIdCard;
    private String adCode;
    private String adName;
    private String rdName;
    private String classCode;
    private String statusProcess; // ? trạng thái quy trình
    private String formStatus; // ? trạng thái hồ sơ
    private String assigneeAccountName;
    private Long formId;
    private String formStatusCode;
    private Date dueDate;
}

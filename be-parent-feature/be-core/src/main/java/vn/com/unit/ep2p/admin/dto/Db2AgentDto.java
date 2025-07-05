package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Db2AgentDto {
    private Long id;
    private String eventCode;
    private String eventTitle;
    private Date eventDate;
    private String agentCode;
    private int agentStatusCode;
    private boolean isSendImmediately;

    private String agentName;
    private String territorry;
    private String area;
    private String region;
    private String office;
    private String position;
    private String email;
    private String count;
    private String mobilePhone;
    private String emailAddress1;
    private String emailAddress2;
    private String emailAddress3;

    private String agentType;
    private String agentTypeName;
    private Integer agentLevel;
    private String agentGroup;
    private String groupType;
    private String applicableObject;

    private String notifyCode;
    private String notifyTitle;
    private String contents;
    private String linkNotify;
    private String createDate;
    private String createBy;
    private Integer isGad;
    private String gender;
    private String orgCode;
    private Date sendDate;
    private String fullAddress;
    private String idNumber;
    private String salesOfficeCode;
    private String salesOfficeName;
    private String channel;
}

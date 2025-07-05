package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityGroupDto extends ActivityAgentDto{
	private Double ipFiledInTotal; //tong IP nop vao
	private Integer fcActiveQuantity; //so luong fc nang dong (PFC)
	private String top3User;
	private List<String> top3UserList; //top 3 user tong phi FYP cao nhat
	private Integer newRecruitment; //tuyen dung moi
	private Integer activeQuantity; //so luong active
}

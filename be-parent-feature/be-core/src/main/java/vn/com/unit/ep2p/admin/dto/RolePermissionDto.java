package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ntr.bang
 */
@Getter
@Setter
public class RolePermissionDto {
	
	private String channel;
	private String agentType;
	private String function;
	private int add;
	private int edit;
	private int delete;
	private int view;
	private int approve;
	private int reject;
}

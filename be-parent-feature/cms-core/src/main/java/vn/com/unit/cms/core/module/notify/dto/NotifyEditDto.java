package vn.com.unit.cms.core.module.notify.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.common.tree.TreeNode;

@Getter
@Setter
public class NotifyEditDto extends CmsCommonEditDto implements TreeNode{
	
	private Long id;
	
	private String notifyCode;
	
	private String notifyTitle;
	
	private String linkNotify;
	
	private String contents;
	
	private Date sendDate;
	
	private boolean isSendImmediately;
	
	private boolean isActive;

	private boolean isSend;

	private String applicableObject;
	
	private boolean isFc;

	private String territory;
	private String region;
	private String area;
	private String type;
	private String reporter;
	private String office;
	private Boolean isError;
	private String sessionKey;
	@Override
	public Long getNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNodeParentId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getNodeOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getSelected() {
		// TODO Auto-generated method stub
		return false;
	}

}

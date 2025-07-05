package vn.com.unit.ep2p.admin.dto;

import java.util.List;

public class RoleForDisplayEmailEditDto {

	List<RoleForDisplayEmailDto> data;
	
	Long roleId;

	public List<RoleForDisplayEmailDto> getData() {
		return data;
	}

	public void setData(List<RoleForDisplayEmailDto> data) {
		this.data = data;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}

package vn.com.unit.ep2p.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaRoleForCompanyDto;

@Getter
@Setter
public class RoleForCompanyEditDto {

	List<JcaRoleForCompanyDto> data;
	Long roleId;
}

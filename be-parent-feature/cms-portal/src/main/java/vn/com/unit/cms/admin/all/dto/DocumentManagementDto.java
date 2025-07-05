package vn.com.unit.cms.admin.all.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

@Getter
@Setter
public class DocumentManagementDto extends DocumentActionReq {

	private Long id;

	private String code;
	private String name;
	private Long parentId;

	private Integer isFolder;
	
	private Long companyId;

}

package vn.com.unit.ep2p.admin.service;
import java.util.Date;

import vn.com.unit.ep2p.admin.dto.PolicyMaturedInfoDto;

public interface NotifyPolicyMaturedService {
	
	Long addNewNotify(PolicyMaturedInfoDto dto, Date createDate);

}

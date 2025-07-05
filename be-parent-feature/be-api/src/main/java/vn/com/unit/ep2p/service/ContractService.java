package vn.com.unit.ep2p.service;

import vn.com.unit.cms.core.module.customerManagement.dto.ContractExpiresPersonalDto;
import vn.com.unit.core.res.ObjectDataRes;

public interface ContractService {
	public ObjectDataRes<ContractExpiresPersonalDto> getListContractExpiresPersonal(Integer contractNumber, String contractOwnerName, Integer page, Integer size);
}

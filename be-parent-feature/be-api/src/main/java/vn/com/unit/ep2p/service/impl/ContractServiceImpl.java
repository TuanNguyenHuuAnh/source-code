package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.customerManagement.dto.ContractExpiresPersonalDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractExpiresPersonalParamDto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.service.ContractService;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ContractServiceImpl implements ContractService{
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	private static final String STORE_CONTRACT_EXPIRED_PERSONAL = "";

	@Override
	public ObjectDataRes<ContractExpiresPersonalDto> getListContractExpiresPersonal(Integer contractNumber,
			String contractOwnerName, Integer page, Integer size) {
		ContractExpiresPersonalParamDto param = new ContractExpiresPersonalParamDto();
		param.contractNumber=contractNumber;
		param.contractOwnerName=contractOwnerName;
		param.page=page;
		param.size=size;
		sqlManagerDb2Service.call(STORE_CONTRACT_EXPIRED_PERSONAL, param);
		List<ContractExpiresPersonalDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			total = param.TotalRows;
		}
		ObjectDataRes<ContractExpiresPersonalDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}
	
}

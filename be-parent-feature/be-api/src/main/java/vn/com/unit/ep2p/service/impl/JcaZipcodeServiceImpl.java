package vn.com.unit.ep2p.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.zipcode.repository.ZipcodeRepository;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.db2.Db2ApiRepository;
import vn.com.unit.ep2p.service.JcaZipcodeService;

@Service
public class JcaZipcodeServiceImpl implements JcaZipcodeService{
	
	@Autowired
	ZipcodeRepository zipcodeRepository;

	@Autowired
	Db2ApiRepository db2ApiRepository;
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Select2Dto> getListProvince() {
		return zipcodeRepository.findAllProvince();
	}

	@Override
	@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
	public List<Select2Dto> getListProvinceOds() {
		return db2ApiRepository.findAllProvinceOds();
	}

	@Override
	@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
	public List<String> getOrdId(String agentCode) {
		// TODO Auto-generated method stub
		return db2ApiRepository.getOrdIdByOrdCode(agentCode);
	}
}

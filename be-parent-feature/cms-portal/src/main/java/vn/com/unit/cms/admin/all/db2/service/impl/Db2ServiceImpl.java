package vn.com.unit.cms.admin.all.db2.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.db2.service.Db2Service;
import vn.com.unit.cms.core.dto.ConditionSearchDb2;
import vn.com.unit.cms.core.module.db2.Db2Repository;
import vn.com.unit.cms.core.module.notify.dto.NotifyInfoAgent;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentNotifyParamDto;
import vn.com.unit.ep2p.admin.dto.Db2ContestSummaryParamDto;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class Db2ServiceImpl implements Db2Service{
	
    @Autowired
    Db2Repository db2Repository;
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	@Override
	public List<Select2Dto> findAllTerritory(ConditionSearchDb2 conditionSearchDb2) {
		// TODO Auto-generated method stub
		List<Select2Dto> lstData = db2Repository.findAllTerritory(conditionSearchDb2);
		return lstData;
	}

	@Override
	public List<Select2Dto> findAllRegion(ConditionSearchDb2 conditionSearchDb2) {
		if(conditionSearchDb2 != null && !StringUtils.isBlank(conditionSearchDb2.getTerritory()))
			conditionSearchDb2.setTerritory(",".concat(conditionSearchDb2.getTerritory()).concat(","));
		return db2Repository.findAllRegion(conditionSearchDb2);
	}

	@Override
	public List<Select2Dto> findAllArea(ConditionSearchDb2 conditionSearchDb2) {
		if(conditionSearchDb2 != null && !StringUtils.isBlank(conditionSearchDb2.getTerritory()))
			conditionSearchDb2.setTerritory(",".concat(conditionSearchDb2.getTerritory()).concat(","));

		if(conditionSearchDb2 != null && !StringUtils.isBlank(conditionSearchDb2.getRegion()))
			conditionSearchDb2.setRegion(",".concat(conditionSearchDb2.getRegion()).concat(","));
		return db2Repository.findAllArea(conditionSearchDb2);
	}

	@Override
	public List<Select2Dto> findAllReporter(ConditionSearchDb2 conditionSearchDb2) {
		return db2Repository.findAllReporter(conditionSearchDb2);
	}

	@Override
	public List<Select2Dto> findAllAgenttype(ConditionSearchDb2 conditionSearchDb2) {
		return db2Repository.findAllAgentType(conditionSearchDb2);
	}

	@Override
	public List<Select2Dto> findAllOffice(ConditionSearchDb2 conditionSearchDb2) {
		if(conditionSearchDb2 != null && !StringUtils.isBlank(conditionSearchDb2.getTerritory()))
			conditionSearchDb2.setTerritory(",".concat(conditionSearchDb2.getTerritory()).concat(","));

		if(conditionSearchDb2 != null && !StringUtils.isBlank(conditionSearchDb2.getRegion()))
			conditionSearchDb2.setRegion(",".concat(conditionSearchDb2.getRegion()).concat(","));

		if(conditionSearchDb2 != null && !StringUtils.isBlank(conditionSearchDb2.getArea()))
			conditionSearchDb2.setArea(",".concat(conditionSearchDb2.getArea()).concat(","));
		return db2Repository.findAllOffice(conditionSearchDb2);
	}

	@Override
	public Db2AgentDto findAgentInfoByCondition(String agentCode) {
		return db2Repository.getAgentInfoByCondition(agentCode);
	}

	@Override
	public NotifyInfoAgent findAgentInfoByNotifyId(Long agentCode) {
		return db2Repository.findAgentInfoByNotifyId(agentCode);
	}

	@Override
	public void callStoreDb2(String storeName, Db2AgentNotifyParamDto param) {
		sqlManagerDb2Service.call(storeName, param);
	}

	@Override
	public Db2SummaryDto findAllAgentType(String memoNo) {
		return db2Repository.findAgentTypeByMemo(memoNo);
	}
	
	@Override
	public void callStoreAgentDetailDb2(String storeName, Db2ContestSummaryParamDto param) {
		sqlManagerDb2Service.call(storeName, param);
	}

	@Override
	public List<Select2Dto> getCity() {
		return db2Repository.findCity();
	}

	@Override
	public List<Select2Dto> getDistrictByCity(String zipCode, String district) {
		return db2Repository.findDistrictByCity(zipCode, district);
	}

	@Override
	public String getNameCity(String city) {
		return db2Repository.getNameCity(city);
	}

	@Override
	public String getNameDistrict(String district) {
		return db2Repository.getNameDistrict(district);
	}

	@Override
	public List<Db2AgentDto>  getAllAgentCode() {
		return db2Repository.getAllAgentCodeActive();
	}

	@Override
	public List<Db2SummaryDto> getAllAgent() {
		return db2Repository.getAllAgent();

	}
}

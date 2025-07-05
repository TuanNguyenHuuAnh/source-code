package vn.com.unit.cms.admin.all.db2.service;

import java.util.List;

import vn.com.unit.cms.core.dto.ConditionSearchDb2;
import vn.com.unit.cms.core.module.notify.dto.NotifyInfoAgent;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentNotifyParamDto;
import vn.com.unit.ep2p.admin.dto.Db2ContestSummaryParamDto;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;

public interface Db2Service {
	List<Select2Dto> findAllTerritory(ConditionSearchDb2 conditionSearchDb2);

    List<Select2Dto> findAllRegion(ConditionSearchDb2 conditionSearchDb2);

    List<Select2Dto> findAllArea(ConditionSearchDb2 conditionSearchDb2);

    List<Select2Dto> findAllReporter(ConditionSearchDb2 conditionSearchDb2);

    List<Select2Dto> findAllAgenttype(ConditionSearchDb2 conditionSearchDb2);

    List<Select2Dto> findAllOffice(ConditionSearchDb2 conditionSearchDb2);
    
	Db2AgentDto findAgentInfoByCondition(String agentCode);
	
	NotifyInfoAgent findAgentInfoByNotifyId(Long agentCode);

    void callStoreDb2(String storeName, Db2AgentNotifyParamDto param);
    
    Db2SummaryDto findAllAgentType(String memoNo);
  
    void callStoreAgentDetailDb2(String storeName, Db2ContestSummaryParamDto param);

    List<Select2Dto> getCity();

    List<Select2Dto> getDistrictByCity(String zipCode,String district);

    String getNameCity(String city);

    String getNameDistrict(String district);

    List<Db2AgentDto>  getAllAgentCode();

    List<Db2SummaryDto> getAllAgent();
}

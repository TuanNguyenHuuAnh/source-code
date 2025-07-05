package vn.com.unit.cms.core.module.db2;

import java.util.List;

import org.springframework.data.repository.query.Param;
import vn.com.unit.cms.core.dto.ConditionSearchDb2;
import vn.com.unit.cms.core.module.agent.dto.ContractDocumentDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyInfoAgent;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;

public interface Db2Repository extends DbRepository<Notify, Long> {

	List<Select2Dto> findAllTerritory(@Param("condition") ConditionSearchDb2 conditionSearchDb2);

	List<Select2Dto> findAllRegion(@Param("condition") ConditionSearchDb2 conditionSearchDb2);

	List<Select2Dto> findAllArea(@Param("condition") ConditionSearchDb2 conditionSearchDb2);

	List<Select2Dto> findAllReporter(@Param("condition") ConditionSearchDb2 conditionSearchDb2);

	List<Select2Dto> findAllAgentType(@Param("condition") ConditionSearchDb2 conditionSearchDb2);

	List<Select2Dto> findAllOffice(@Param("condition") ConditionSearchDb2 conditionSearchDb2);
	
    Db2SummaryDto findAgentTypeByMemo(@Param("memoNo")String memoNo);

	Db2AgentDto getAgentInfoByCondition(@Param("agentCode") String agentCode);
	
	NotifyInfoAgent findAgentInfoByNotifyId(@Param("agentCode") Long agentCode);


	List<Select2Dto> findCity();

	List<Select2Dto> findDistrictByCity(@Param("zipCode") String zipCode, @Param("district") String district);

    String getNameCity(@Param("city")String city);

	String getNameDistrict(@Param("district")String district);

	List<Db2AgentDto>  getAllAgentCodeActive();

	List<Db2SummaryDto> getAllAgent();
	
	List<ContractDocumentDto> getContractDocuments(@Param("docIds") List<String> docIds);
}

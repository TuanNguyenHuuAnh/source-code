package vn.com.unit.cms.core.module.agentbank.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.agentbank.entity.AgentBankUpdateHistory;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.BankInfoUpdateHistoryDto;

public interface AgentBankUpdateHistoryRepository extends DbRepository<AgentBankUpdateHistory, Long> {
	List<BankInfoUpdateHistoryDto> getUpdateHistory(@Param("agentCode") String agentCode);
}
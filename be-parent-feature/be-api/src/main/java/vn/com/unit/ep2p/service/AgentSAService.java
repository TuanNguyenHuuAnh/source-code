package vn.com.unit.ep2p.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.agent.dto.AgentPositionDto;
import vn.com.unit.cms.core.module.agent.dto.AgentSADto;
import vn.com.unit.cms.core.module.agent.dto.AgentSASearchDto;
import vn.com.unit.cms.core.module.agent.dto.DataRange;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.config.SystemConfig;

public interface AgentSAService {
	public SystemConfig getSystemConfig();
	
	public ObjectDataRes<AgentSADto> getListAgentSAByCondition(AgentSASearchDto searchDto);
	
	public List<DataRange> getDataRange(String userName);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportAgentSA(AgentSASearchDto searchDto, Locale locale);
	
	public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String dataString, Date runDate, String[] titleHeader);
	
	public AgentPositionDto getAgentTypeName(String agentCode);
}

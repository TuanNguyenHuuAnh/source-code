package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.account.dto.ConditionTable;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetailSearchDto;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentTerminationInfor;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentDto;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentSearchDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.AgentContactInfoDto;

public interface ApiAgentDetailService {
	CmsAgentDetail getCmsAgentDetailByUsername(String username) throws DetailException;
	
	CmsCommonPagination<InfoAgentDto> getListInfoAgent(InfoAgentSearchDto searchDto);
	
	CmsCommonPagination<InfoAgentDto> getListInfoBranchAgent(InfoAgentSearchDto searchDto);

	InfoAgentDto getListInfoAgentDetail(InfoAgentSearchDto searchDto);

	List<CmsAgentDetail> getListAgentByCondition(CmsAgentDetailSearchDto searchDto);
	
	@SuppressWarnings("rawtypes")
    ResponseEntity exportListInfoAgent(InfoAgentSearchDto dto, HttpServletResponse response, Locale locale);
	
	public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader, String titleName, Map<String, CellStyle> mapColStyle);

	Integer checkDataByCondition(ConditionTable condition);

	CmsAgentDetail getCmsAgentDetailByFaceMask(String username);

	CmsAgentTerminationInfor checkAgentByCode(String code, String email, String otp) throws IOException;

	CmsAgentDetail getCmsAgentLoginByUsername(String username);

	boolean checkAgentChild(String agentParent, String agentChild);
	//----------------kk.quan add feature--------------
	AgentContactInfoDto getContactAndCommonInfo(Long agentCode);
	//----------------kk.quan add feature--------------
}

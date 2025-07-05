package vn.com.unit.ep2p.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulationChallengeSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateResp;
import vn.com.unit.core.res.ObjectDataRes;

public interface ApiEmulateService {
	 public List<EmulateResp> getListEmulateInMonth(EmulateSearchDto searchDto, Pageable pageable, Integer modeView);
	 
	 public EmulateResp getHotEmulateInMonth(EmulateSearchDto searchDto);
	 public List<EmulateSearchDto> findByContestType();
	 
	 public int countListEmulateInMonth(EmulateSearchDto searchDto, Integer modeView);
	 //personal
	 public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengePersonal(EmulationChallengeSearchDto searchDto);
	 public ObjectDataRes<EmulateResultSearchResultDto> getListEmulateAndChallengeResultPersonal(EmulationChallengeSearchDto seachDto);

     @SuppressWarnings("rawtypes")
     ResponseEntity exportEmulateAndChellengePersonal(EmulationChallengeSearchDto searchDto);
	 @SuppressWarnings("rawtypes")
	 ResponseEntity exportEmulateAndChellengePersonalResult(EmulationChallengeSearchDto searchDto);
	 
	 //group
	 public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengeGroup(EmulationChallengeSearchDto searchDto);
	 public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengeResultGroup(EmulationChallengeSearchDto searchDto);
	 public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengeResultGroupSumary(EmulationChallengeSearchDto searchDto);
	 public ObjectDataRes<EmulateResultSearchResultDto> getEmulateAndChallengeResultGa(EmulationChallengeSearchDto searchDto);
	 @SuppressWarnings("rawtypes")
	 ResponseEntity exportEmulateAndChellengeGroup(EmulationChallengeSearchDto searchDto);
	 @SuppressWarnings("rawtypes")
     ResponseEntity exportEmulateAndChellengeGroupResult(EmulationChallengeSearchDto searchDto);
	 
	 public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader, String titleName, String startRow , List<String> datas);
	 
	 public boolean checkAgentChild(String territory, String region, String area, String office, String agentParent, String agentChild);

	@SuppressWarnings("rawtypes")
	public ResponseEntity exportEmulateAndChallengeResultGa(EmulationChallengeSearchDto searchDto);
}

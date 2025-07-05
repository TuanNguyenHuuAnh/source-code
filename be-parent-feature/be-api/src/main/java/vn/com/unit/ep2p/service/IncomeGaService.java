package vn.com.unit.ep2p.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.ga.dto.IncomeGaDto;
import vn.com.unit.cms.core.module.ga.dto.IncomeMonthsGaDto;
import vn.com.unit.cms.core.module.ga.dto.search.IncomeSearchGa;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataRes;

public interface IncomeGaService {

    SystemConfig getSystemConfig();

    IncomeMonthsGaDto getListIncomeGaDetail(IncomeSearchGa searchDto) throws Exception;

    ResponseEntity exportListIncomeGa(IncomeSearchGa searchDto, Locale locale);

    ResponseEntity exportListIncomeGaYear(IncomeSearchGa searchDto, Locale locale);

    IncomeGaDto getListIncomeGa(IncomeSearchGa searchDto);

    List<IncomeGaDto> getListIncomeGaYear(IncomeSearchGa searchDto);

	List<IncomeMonthsGaDto> callStoreIncomeGadDetail(IncomeSearchGa searchDto);

	List<IncomeGaDto> getDroplistIncomeWeeklyGA(IncomeSearchGa searchDto);

	ObjectDataRes<IncomeGaDto> getListIncomeWeeklyGA(IncomeSearchGa searchDto);

	ResponseEntity exportIncomeWeeklyGA(List<IncomeGaDto> resultDto, String type, BigDecimal amount, String fileName,
			String row, Class enumDto, Class className, List<String> lstInfor, String startInfo, String appendExportFileName);
	
	
}

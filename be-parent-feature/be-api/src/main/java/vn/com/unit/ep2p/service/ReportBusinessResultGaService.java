package vn.com.unit.ep2p.service;

import liquibase.pro.packaged.T;
import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalInsuranceContractGroupSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportResultSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportResultSumaryDto;
import vn.com.unit.cms.core.module.report.dto.ReportResultViewGADTabPremiumDto;
import vn.com.unit.core.res.ObjectDataRes;

public interface ReportBusinessResultGaService {
    public ReportResultSumaryDto getSumary(String agentCode, String orgCode, String agentGroup);
	public ObjectDataRes<ReportResultViewGADTabPremiumDto> getListResultViewGadPremium(ReportResultSearchDto searchDto);
	public ObjectDataRes<ReportResultViewGADTabPremiumDto> getListResultViewGadManpower(ReportResultSearchDto searchDto);
	@SuppressWarnings("rawtypes")
    ResponseEntity exportGad(ReportResultSearchDto dto, Integer type);

	public<T> String searchAdvance(T searchDto,String agentGroup,String keyword,T searchBd1,T searchBd2);

	public void setConditionSearch(CommonSearchWithPagingDto data, int level,String agentGroup) ;

}

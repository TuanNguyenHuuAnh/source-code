package vn.com.unit.ep2p.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.contract.dto.ClaimAdditionalInformationDto;
import vn.com.unit.cms.core.module.contract.dto.ClaimAdditionalInformationOdsParam;
import vn.com.unit.cms.core.module.contract.dto.ClaimOdsDetailDto;
import vn.com.unit.cms.core.module.contract.dto.ContactHistoryDetailDto;
import vn.com.unit.cms.core.module.contract.dto.ContactHistoryDetailParam;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessHistoryResponseDto;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessPersonalDetailParam;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessPersonalExportParam;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessPersonalPagingParam;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessSearchResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimInforParam;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimPersonalPagingParam;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimReportParam;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimSearchResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractDetailParam;
import vn.com.unit.cms.core.module.contract.dto.ContractDueDatePagingParam;
import vn.com.unit.cms.core.module.contract.dto.ContractDueDateSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractExpiresPagingParam;
import vn.com.unit.cms.core.module.contract.dto.ContractExpiresSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractExpiresSearchResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractPagingParam;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchAllResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchDueDateResultDto;
import vn.com.unit.cms.core.module.contract.dto.CostOfRefusalToPayDto;
import vn.com.unit.cms.core.module.contract.dto.CostOfRefusalToPayParam;
import vn.com.unit.cms.core.module.contract.dto.DetailClaimOdsParam;
import vn.com.unit.cms.core.module.contract.dto.ListContractDueDatePagingParam;
import vn.com.unit.cms.core.module.contract.dto.PolicyMaturedDetailParam;
import vn.com.unit.cms.core.module.contract.dto.PolicyMaturedPagingParam;
import vn.com.unit.cms.core.module.contract.dto.PolicyMaturedResultDto;
import vn.com.unit.cms.core.module.contract.dto.PolicyMaturedSearchDto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.dto.ContractBusinessHistoryDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.enumdef.ListPolicyMaturedPersonal;
import vn.com.unit.ep2p.service.ApiContractService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiContractServiceImpl implements ApiContractService{
	
	private static final String GET_POLICY_DETAIL_BY_POLICY_KEY = "RPT_ODS.GET_POLICY_DETAIL_BY_POLICY_KEY";
	private static final String GET_CONTACT_HISTORY_BY_CLAIM_NO = "RPT_ODS.DS_SP_GET_CONTACT_HISTORY";
	private static final String DS_SP_GET_DUE_DATE_CONTRACT = "RPT_ODS.DS_SP_GET_LIST_PAID_TO_DATE_BY_AGENT ";
	private static final String DS_SP_GET_DUE_DATE_CONTRACT_EXPORT = "RPT_ODS.DS_SP_GET_LIST_PAID_TO_DATE_BY_AGENT_PERSONAL_EXP ";
	private static final String DS_SP_GET_DUE_DATE_CONTRACT_LIST = "RPT_ODS.DS_SP_GET_LIST_PAID_TO_DATE_BY_AGENT_PERSONAL";

	private static final String DS_SP_GET_BUSINESS_CONTRACT_PERSONAL = "RPT_ODS.DS_SP_GET_LIST_HANDLING_CONTRACTS_PERSONAL";
	private static final String DS_SP_GET_BUSINESS_CONTRACT_PERSONAL_EXPORT = "RPT_ODS.DS_SP_GET_LIST_HANDLING_CONTRACTS_PERSONAL_EXPORT";
	private static final String DS_SP_GET_LIST_HANDLING_CONTRACTS_PERSONAL_DETAIL = "RPT_ODS.DS_SP_GET_LIST_HANDLING_CONTRACTS_PERSONAL_DETAIL";
	private static final String DS_SP_GET_EXPIRES_CONTRACT = "RPT_ODS.DS_SP_GET_LIST_MATURED_POLICY_BY_AGENT";
	private static final String DS_SP_GET_CLAIM_CONTRACT_PERSONAL = "RPT_ODS.DS_SP_GET_LIST_CLAIM_CONTRACTS_PERSONAL";
	private static final String DS_SP_GET_CLAIM_CONTRACT_INFOR = "STG_HCMS.HCMS_DSUCCESS_CLAIMDETAIL";
	private static final String DS_SP_GET_CLAIM_CONTRACT_INFOR_PENDING ="STG_HCMS.HCMS_SP_RETRIVEPENDINGNOTIFICATIONLIST";
	private static final String DS_SP_GET_CLAIM_CONTRACT_EXPORT = "RPT_ODS.DS_SP_GET_LIST_CLAIM_CONTRACTS_PERSONAL_EXPORT";
	
	private static final String DS_SP_GET_LIST_POLICY_BY_AGENT_LIST = "RPT_ODS.DS_SP_GET_LIST_POLICY_BY_AGENT_LIST";
	private static final String DS_SP_GET_LIST_POLICY_BY_AGENT_EXP = "RPT_ODS.DS_SP_GET_LIST_POLICY_BY_AGENT_EXP";

	private static final String DS_SP_GET_LIST_POLICY_MATURED = "RPT_ODS.DS_SP_GET_LIST_POLICY_MATURED";
	private static final String DS_SP_GET_DETAIL_POLICY_MATURED = "RPT_ODS.DS_SP_GET_DETAIL_POLICY_MATURED";
	
	private static final String DS_SP_GET_COST_OF_REFUSAL_TO_PAY = "RPT_ODS.DS_SP_GET_COST_OF_REFUSAL_TO_PAY";
	
	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private ServletContext servletContext;
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	@Autowired
	private Db2ApiService db2ApiService;
	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;
	private static final Logger logger = LoggerFactory.getLogger(ApiContractServiceImpl.class);
	
	@Override
	public ObjectDataRes<ContractSearchAllResultDto> getListAllContractByCondition(ContractSearchDto dto, boolean isExport) {
		dto.setFunctionCode("ACTIVE_PERSONAL_POLICY");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "ACTIVE_PERSONAL_POLICY");

		ContractPagingParam param = new ContractPagingParam();
		param.agentCode=dto.getAgentCode();
		param.policyType=dto.getTypeEffect();
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		param.search = common.getSearch();
		if (isExport) {
			sqlManagerDb2Service.call(DS_SP_GET_LIST_POLICY_BY_AGENT_EXP, param);
		} else {
			sqlManagerDb2Service.call(DS_SP_GET_LIST_POLICY_BY_AGENT_LIST, param);
		}
		
		List<ContractSearchAllResultDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			if(StringUtils.equalsIgnoreCase(dto.getTypeEffect(), "INACTIVE"))
			data.stream().forEach(x->{x.setExpirationDate(x.getDateDue());});
			
			if (param.total != null) {
				total = param.total;
			}
		}
		
		ObjectDataRes<ContractSearchAllResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}
	
	public BigDecimal checkNull(BigDecimal sub){
		if(sub == null) sub = new BigDecimal(0);
		return sub;
	}

	@Override
	public ContractSearchAllResultDto getDetailContractByCondition(ContractSearchDto dto) {
		ContractSearchAllResultDto result = new ContractSearchAllResultDto();
		ContractDetailParam param = new ContractDetailParam();
			
			param.agentCode=dto.getAgentCode();
			param.policyNo = dto.getPolicyNo();
			param.policyType=dto.getTypeEffect();
			sqlManagerDb2Service.call(GET_POLICY_DETAIL_BY_POLICY_KEY, param);
			List<ContractSearchAllResultDto> data = new ArrayList<>();
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				if(StringUtils.equalsIgnoreCase(dto.getTypeEffect(), "INACTIVE")) param.data.stream().forEach(x->{x.setExpirationDate(x.getDateDue());});
				data = param.data;
				result = data.get(0);
				result.setPolBaseFaceAmt(result.getPolBaseFaceAmt());
				result.setTotalPremiumPaid(checkNull(result.getTotalPremiumPaid()));
				result.setPolExcessAmt(checkNull(result.getPolExcessAmt()));
				result.setPolPremSuspAmt(checkNull(result.getPolPremSuspAmt()));
				result.setPolMiscSuspAmt(checkNull(result.getPolMiscSuspAmt()));
				result.setPolCount(Optional.ofNullable(result.getPolPremSuspAmt()).orElse(BigDecimal.ZERO)
									.add(Optional.ofNullable(result.getPolMiscSuspAmt()).orElse(BigDecimal.ZERO)));
//				if(result.getPolPremSuspAmt()!=null  && result.getPolMiscSuspAmt() != null) result.setPolCount(result.getPolPremSuspAmt().subtract(result.getPolMiscSuspAmt()));
				result.setPolBasicMprem(result.getTotalPremiumPaid().subtract(result.getPolExcessAmt().subtract(result.getPolPremSuspAmt().subtract(result.getPolMiscSuspAmt()))));
				result.setCheck(true);
			}


			
		return result;
	}
    
	@Override
	public ContractBusinessHistoryResponseDto getBusinessHistory(String policyNo) {
              ContractBusinessHistoryResponseDto res = new ContractBusinessHistoryResponseDto();
		
		try {
			List<ContractBusinessHistoryDto> contractBusinessHistoryLst = new ArrayList<>();

			contractBusinessHistoryLst = db2ApiService.getBusinessHistory(policyNo);
		
			res.setContractBusinessHistory(contractBusinessHistoryLst);
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}

		return res;
	}
	
	@Override
	public ObjectDataRes<ContractSearchDueDateResultDto> getListExportDueDateContractByCondition(
			ContractDueDateSearchDto dto) {
		dto.setFunctionCode("DUEDATE_PERSONAL_POLICY");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "DUEDATE_PERSONAL_POLICY");

		ListContractDueDatePagingParam param = new ListContractDueDatePagingParam();
		param.agentCode=dto.getAgentCode();
		param.type = dto.getType();
		param.search=common.getSearch().replaceAll("RPT_ODS.DS_FN_REMOVEMARK(nvl(C.POLICY_KEY,''))", "RIGHT((1000000000 + C.POLICY_KEY), 9)");
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		sqlManagerDb2Service.call(DS_SP_GET_DUE_DATE_CONTRACT_EXPORT, param);
		List<ContractSearchDueDateResultDto> data = new ArrayList<>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;

			data.stream().filter(x->x.getLoaihopdong()!=null).filter(x->x.getLoaihopdong().equals("2")).forEach(x->{
				if(x.getChargeStatus().equals("Nợ phí quá hạn")){
					x.setFeeExpected(new BigDecimal(0));
			//Ngày thu phí: hiện '0'
				}else if(x.getChargeStatus().equals("Hết giá trị tài khoản")){
			//Ngày thu phí: hiện '0'
				}
			});
		}
		ObjectDataRes<ContractSearchDueDateResultDto> resObj = new ObjectDataRes<>(ObjectUtils.isNotEmpty(param.data)? param.data.size() : 0, data);
		return resObj;
	}

	@Override
	public ObjectDataRes<ContractSearchDueDateResultDto> getListDueDateContractByCondition(
			ContractDueDateSearchDto dto) {
		dto.setFunctionCode("DUEDATE_PERSONAL_POLICY");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "DUEDATE_PERSONAL_POLICY");

		ListContractDueDatePagingParam param = new ListContractDueDatePagingParam();
		param.agentCode=dto.getAgentCode();
		param.type = dto.getType();
		param.search=common.getSearch().replace("RPT_ODS.DS_FN_REMOVEMARK(nvl(C.POLICY_KEY,''))", "RIGHT((1000000000 + C.POLICY_KEY), 9)");
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		sqlManagerDb2Service.call(DS_SP_GET_DUE_DATE_CONTRACT_LIST, param);
		List<ContractSearchDueDateResultDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			if (param.total != null) {
				total = param.total;
			}
		}
		ObjectDataRes<ContractSearchDueDateResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}

	@Override
	public ObjectDataRes<ContractSearchDueDateResultDto> getListDueDateFc(ContractDueDateSearchDto dto) {
		dto.setFunctionCode("DUEDATE_PERSONAL_POLICY");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "DUEDATE_PERSONAL_POLICY");

		ContractDueDatePagingParam param = new ContractDueDatePagingParam();
		param.agentCode=dto.getAgentCode();
		param.agentGroup="FC";
		param.type = dto.getType();
		param.search=common.getSearch();
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		sqlManagerDb2Service.call(DS_SP_GET_DUE_DATE_CONTRACT, param);
		List<ContractSearchDueDateResultDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			addGrandTotal(data, dto);
			if (param.total != null) {
				total = param.total;
			}
		}
		ObjectDataRes<ContractSearchDueDateResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}
	private void addGrandTotal(List<ContractSearchDueDateResultDto> data, ContractDueDateSearchDto dto){
		ContractDueDatePagingParam param = new ContractDueDatePagingParam();
		param.agentCode=dto.getAgentCode();
		param.agentGroup="FC";
		param.type = dto.getType();
		param.search=null;
		param.page=null;
		param.pageSize=null;
		sqlManagerDb2Service.call(DS_SP_GET_DUE_DATE_CONTRACT, param);
		List<ContractSearchDueDateResultDto> dataTotal = new ArrayList<>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			dataTotal = param.data;
			ContractSearchDueDateResultDto contractSearchDueDateResultDto = new ContractSearchDueDateResultDto();
			contractSearchDueDateResultDto.setPolicyNo("Grand Total");

			//total EstimatedRecurringFee from List
			contractSearchDueDateResultDto.setEstimatedRecurringFee(dataTotal.stream()
					.filter(Objects::nonNull)
					.map(ContractSearchDueDateResultDto::getEstimatedRecurringFee)
					.map(BigDecimal::new)
					.reduce(new BigDecimal(0), (a, b) -> a.add(b))
					.toString());

			//total RecurringBasicFee from List
			contractSearchDueDateResultDto.setRecurringBasicFee(dataTotal.stream()
					.filter(Objects::nonNull)
					.map(ContractSearchDueDateResultDto::getRecurringBasicFee)
					.reduce(new BigDecimal(0), (a, b) -> a.add(b)));

			data.add(contractSearchDueDateResultDto);
		}
	}

	// DAO HAN DETAIL
	@Override
	public ContractSearchDueDateResultDto getDetailDueDateContractByCondition(ContractDueDateSearchDto dto) {		
		ContractSearchDueDateResultDto result = new ContractSearchDueDateResultDto();
		ListContractDueDatePagingParam param = new ListContractDueDatePagingParam();
			param.agentCode=dto.getAgentCode();
			param.type = dto.getType();
			param.search= "and nvl(C.POLICY_KEY, 0) ="+dto.getPolicyNo();
			param.page=dto.getPage();
			param.pageSize=dto.getPageSize();
			sqlManagerDb2Service.call(DS_SP_GET_DUE_DATE_CONTRACT_EXPORT, param);
			List<ContractSearchDueDateResultDto> data = new ArrayList<>();
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				data = param.data;
				result = data.get(0);
				if(result.getLoaihopdong() !=null && result.getLoaihopdong().equals("2")){
					if(result.getChargeStatus().equals("Nợ phí quá hạn")){
						result.setFeeExpected(new BigDecimal(0));
						//Ngày thu phí: hiện '0'
					}else if(result.getChargeStatus().equals("Hết giá trị tài khoản")){
						//Ngày thu phí: hiện '0'
					}
				}
				//result = data.stream().filter(e-> StringUtils.equalsIgnoreCase(e.getPolicyNo(), dto.getPolicyNo())).findFirst().get();
			}
			result.setCheck(true);
		return result;
	}
	//DETAIL NGHIEP VU
	@Override
	public ContractBusinessSearchResultDto getDetailBusinessContractByCondition(ContractBusinessSearchDto searchDto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date requestDate = new Date(searchDto.getRequestDateMilisec());
		String requestDateStr = sdf.format(requestDate);
		ContractBusinessSearchResultDto result = new ContractBusinessSearchResultDto();
		ContractBusinessPersonalDetailParam param = new ContractBusinessPersonalDetailParam();
			param.policyNo =  searchDto.getPolicyNo();
			param.processtypecode =  searchDto.getProcesstypecode();
			param.agentCode=searchDto.getAgentCode();
			param.requestDate=requestDateStr;
			sqlManagerDb2Service.call(DS_SP_GET_LIST_HANDLING_CONTRACTS_PERSONAL_DETAIL, param);
			List<ContractBusinessSearchResultDto> data = new ArrayList<>();
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				data = param.data;
				result = data.get(0);
				result.setListWaitingAddInformation(data.stream()
						.filter(x -> StringUtils.isNotEmpty(x.getWaitingAddInformation()))
						.map(ContractBusinessSearchResultDto::getWaitingAddInformation).collect(Collectors.toList()));
			}
			
			
			
			result.setCheck(true);
		return result;
	}


	//HD NGHIEP VU
	@Override
	public ObjectDataRes<ContractBusinessSearchResultDto> getListBusinessContractByCondition(
			ContractBusinessSearchDto dto) {
		String agentCode = dto.getAgentCode();
		dto.setFunctionCode("BUSINESS_PERSONAL_POLICY");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "BUSINESS_PERSONAL_POLICY");
		ContractBusinessPersonalPagingParam param = new ContractBusinessPersonalPagingParam();
		param.search=common.getSearch();
		param.agentCode=agentCode;
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		sqlManagerDb2Service.call(DS_SP_GET_BUSINESS_CONTRACT_PERSONAL, param);
		List<ContractBusinessSearchResultDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			if (param.totalRows != null) {
				total = param.totalRows;
			}
		}

		ObjectDataRes<ContractBusinessSearchResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}

	@Override
	public ObjectDataRes<ContractBusinessSearchResultDto> exportListBusinessContract(ContractBusinessSearchDto dto) {
		String agentCode = dto.getAgentCode();
		dto.setFunctionCode("BUSINESS_PERSONAL_POLICY_EXP");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "BUSINESS_PERSONAL_POLICY");
		ContractBusinessPersonalExportParam param = new ContractBusinessPersonalExportParam();
		param.search=common.getSearch();
		param.agentCode=agentCode;
		sqlManagerDb2Service.call(DS_SP_GET_BUSINESS_CONTRACT_PERSONAL_EXPORT, param);
		List<ContractBusinessSearchResultDto> data = param.data;
		int total = data.size();
		/*if (CommonCollectionUtil.isNotEmpty(param.data)) {
			//group theo keyword
			Map<String, List<ContractBusinessSearchResultDto>> lstTmpData = param.data.stream().collect(Collectors.groupingBy(ContractBusinessSearchResultDto::getKeyGroup));

			for (Map.Entry<String, List<ContractBusinessSearchResultDto>> entry : lstTmpData.entrySet()) {

				List<ContractBusinessSearchResultDto> lstDataEntry = entry.getValue();

				String waitingAddInformation = lstDataEntry.stream()
						.filter(e->StringUtils.isNotBlank(e.getWaitingAddInformation()))
						.map(ContractBusinessSearchResultDto::getWaitingAddInformation)
						.collect(Collectors.joining(";\n"));

				ContractBusinessSearchResultDto dataDetail = lstDataEntry.get(0);
				dataDetail.setWaitingAddInformation(waitingAddInformation);

				data.add(dataDetail);
			}
			total = data.size();
		}
		data.stream().sorted(Comparator.comparing(ContractBusinessSearchResultDto::getNo));*/
		ObjectDataRes<ContractBusinessSearchResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}

	@Override
	public ObjectDataRes<ContractExpiresSearchResultDto> getListExpiresContractByCondition(
			ContractExpiresSearchDto dto) {
		String agentCode = dto.getAgentCode();
		dto.setFunctionCode("EXPIRED_PERSONAL_POLICY");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "EXPIRED_PERSONAL_POLICY");
		ContractExpiresPagingParam param = new ContractExpiresPagingParam();
		param.agentCode= agentCode;
		param.type= dto.getType();
		param.search=common.getSearch();
		param.sort=dto.getSort();
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		param.agentGroup=dto.getAgentGroup();
		sqlManagerDb2Service.call(DS_SP_GET_EXPIRES_CONTRACT, param);
		List<ContractExpiresSearchResultDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			if (param.total != null) {
				total = param.total;
			}
		}
		ObjectDataRes<ContractExpiresSearchResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}

	@Override
	public ObjectDataRes<ContractClaimSearchResultDto> getListClaimContractByCondition(ContractClaimSearchDto dto) {
		String agentCode = dto.getAgentCode();
		dto.setFunctionCode("CLAIM_PERSONAL_POLICY");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("getListClaimContractByCondition", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "CLAIM_PERSONAL_POLICY");
		ContractClaimPersonalPagingParam param = new ContractClaimPersonalPagingParam();
		param.search= common.getSearch(); 
		param.agentCode=agentCode;
		param.policyType=dto.getPolicyType();
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		param.sort = "order by C.SCANDATE desc";
		sqlManagerDb2Service.call(DS_SP_GET_CLAIM_CONTRACT_PERSONAL, param);
		List<ContractClaimSearchResultDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			if (param.total != null) {
				total = param.total;
			}
		}
		ObjectDataRes<ContractClaimSearchResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}

	@Override
	public <T, E extends Enum<E>, M> ResponseEntity exportListData(List<T> resultDto, String fileName, String row, Class<E> enumDto, Class<M> className, String[] titleHeader, String titleName, String agentCode, String agentGroup,String agentName, int total) {
		ResponseEntity res = null;
		try {
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + fileName);
			String startRow = row;
			List<ItemColsExcelDto> cols = new ArrayList<>();

			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = new HashMap<>();
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
         	   Map<String, CellStyle> mapColStyle = mapStyle(xssfWorkbook,0);
         	   
				ImportExcelUtil.setListColumnExcel(enumDto, cols);
				setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, titleHeader, titleName,fileName,total,agentCode, agentGroup,agentName,mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, resultDto,
						className, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, fileName, true,path);
			} catch (Exception e) {
				logger.error("exportListData: ", e);
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	@Override
	public ContractClaimSearchResultDto getDetailClaimContractByCondition(ContractClaimSearchDto searchDto) {	
		ContractClaimSearchResultDto result = new ContractClaimSearchResultDto();
		
		ContractClaimInforParam param = new ContractClaimInforParam();
		param.claimid = searchDto.getClaimNoDetail();
		sqlManagerDb2Service.call(DS_SP_GET_CLAIM_CONTRACT_INFOR, param);
		if (CollectionUtils.isNotEmpty(param.data)) {
			ContractClaimResultDto data = param.data.get(0);
			
			// Set Số hợp đồng bảo hiểm
			result.setPolicyNo(data.getPolicyno());
			// Set Bên mua bảo hiểm
			result.setInsuranceBuyer(data.getPoName());
			result.setCustomerNo(data.getPoId());
			// Set Người được bảo hiểm
			result.setInsuredPerson(data.getLiName());
			// Set TVTC: agent type: agentcode-agentname
			// Agent type
			result.setAgentType(data.getAgentType());
			// Agent code
			result.setAgentCode(data.getServicingAgentKey());
			// Agent name
			result.setAgentName(data.getAgentName());
			// Set Số hồ sơ
			result.setClaimNo(data.getClaimno());
			// Set Ngày mở hồ sơ
			result.setScanDate(data.getScanneddate());
			// Set Loại yêu cầu bồi thường
			result.setClaimtype(data.getClaimtype());
			// Set Tình trạng
			result.setStatusvn(data.getClaimstatus());
			// Set Ngày có kết quả bồi thường
			result.setApprovedate(data.getApprovedate());
			// Set Ngày hết hạn bổ sung
			
			// Set số tiền bồi thường
			result.setTotalapproveamount(data.getTotalapproveamount());
			// Set lý do từ chối
			result.setRejectedremark(data.getRejectedremark());
			// Set Bổ sung chứng từ nhận quyền lợi thanh toán
			result.setRequestDetail(data.getApprovedremark());

			result.setCheck(true);
		}

		return result;
	}
	

	@Override
	public ClaimOdsDetailDto getDetailClaimByClaimNo(String claimNo) {
		ClaimOdsDetailDto claimOdsDetailDto = new ClaimOdsDetailDto();
		DetailClaimOdsParam detailClaimOdsParam = new DetailClaimOdsParam();
		detailClaimOdsParam.claimNo = claimNo;
		sqlManagerDb2Service.call(DS_SP_GET_CLAIM_CONTRACT_INFOR, detailClaimOdsParam);
		List<ClaimOdsDetailDto> lstData = detailClaimOdsParam.data;
		if(lstData != null && !lstData.isEmpty()){
			claimOdsDetailDto = lstData.get(0);
			if(claimOdsDetailDto.getRejectedremark() != null)
				claimOdsDetailDto.setRejectedremark(claimOdsDetailDto.getRejectedremark().replace("##", "\n"));
		}
		return claimOdsDetailDto;
	}

	@Override
	public List<ClaimAdditionalInformationDto> getListAdditionalInformation(String claimNo) {
		ClaimAdditionalInformationOdsParam claimAdditionalInformationOdsParam = new ClaimAdditionalInformationOdsParam();
		claimAdditionalInformationOdsParam.claimid = claimNo;
		sqlManagerDb2Service.call(DS_SP_GET_CLAIM_CONTRACT_INFOR_PENDING, claimAdditionalInformationOdsParam);
		List<ClaimAdditionalInformationDto> lstData = claimAdditionalInformationOdsParam.data;
		return lstData;
	}

	public  Map<String, CellStyle> mapStyle(XSSFWorkbook xssfWorkbook, int sheet){
	         DataFormat dataFormat = xssfWorkbook.createDataFormat();
				String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

			 Font fontAmount = xssfWorkbook.createFont();
		     fontAmount.setFontName("Times New Roman");
		     fontAmount.setFontHeightInPoints((short) 11);
		     
		     
		    Font cellFont3 = xssfWorkbook.createFont();
	        cellFont3.setFontName("Times New Roman");
	        cellFont3.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
	        cellFont3.setFontHeightInPoints((short) 11);
		     ;
			CellStyle cellStyleMain = xssfWorkbook.createCellStyle();
			cellStyleMain.setAlignment(HorizontalAlignment.RIGHT);
			cellStyleMain.setFont(fontAmount);
			cellStyleMain.setDataFormat(dataFormat.getFormat("#,##0"));
			cellStyleMain.setWrapText(false);
			cellStyleMain.setBorderTop(BorderStyle.THIN);
			cellStyleMain.setBorderLeft(BorderStyle.THIN);
			cellStyleMain.setBorderRight(BorderStyle.THIN);
			cellStyleMain.setBorderBottom(BorderStyle.THIN);
			
			CellStyle cellStyleAmount = xssfWorkbook.createCellStyle();
			cellStyleAmount.setAlignment(HorizontalAlignment.RIGHT);
			cellStyleAmount.setDataFormat(dataFormat.getFormat("#,##0"));
			cellStyleMain.setWrapText(false);
			cellStyleAmount.setBorderTop(BorderStyle.THIN);
			cellStyleAmount.setBorderLeft(BorderStyle.THIN);
			cellStyleAmount.setBorderRight(BorderStyle.THIN);
			cellStyleAmount.setBorderBottom(BorderStyle.THIN);
			cellStyleAmount.setFont(cellFont3);

			CellStyle cellStyleDate = xssfWorkbook.createCellStyle();
			cellStyleDate.setAlignment(HorizontalAlignment.CENTER);
			cellStyleDate.setDataFormat(dataFormat.getFormat(datePattern));
			cellStyleMain.setWrapText(false);
			cellStyleDate.setBorderTop(BorderStyle.THIN);
			cellStyleDate.setBorderLeft(BorderStyle.THIN);
			cellStyleDate.setBorderRight(BorderStyle.THIN);
			cellStyleDate.setBorderBottom(BorderStyle.THIN);
			cellStyleDate.setFont(cellFont3);

			

	        Map<String, CellStyle> mapColStyle = new HashMap<>();

	        mapColStyle.put("ESTIMATEDRECURRINGFEESTR", cellStyleMain);
	        mapColStyle.put("RECURRINGBASICFEESTR", cellStyleMain);
	        mapColStyle.put("HANGINGFEESTR", cellStyleMain);
	        
	        mapColStyle.put("FEEEXPECTED", cellStyleAmount);
	        mapColStyle.put("EXPIREDDATE", cellStyleDate);
	        mapColStyle.put("PAYMENTPERIODDATE", cellStyleDate);
	        
	        return mapColStyle;
	    }
	    

	public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader, String titleName,String fileName,int total,String agentCode, String agentGroup, String agentName, Map<String, CellStyle> mapColStyle ){
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);

		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();
		CellStyle titleStylTotal = xssfSheet.getWorkbook().createCellStyle();
		CellStyle no = xssfSheet.getWorkbook().createCellStyle();

		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);

		Font fontTitleDate = xssfWorkbook.createFont();
		fontTitleDate.setColor(IndexedColors.BLUE.index);
		fontTitleDate.setFontName("Times New Roman");
		titleStyleDate.setFont(fontTitleDate);

		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setColor(IndexedColors.BLUE.index);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short)24);

		Font fontNo = xssfWorkbook.createFont();
		fontNo.setFontName("Times New Roman");
		fontNo.setFontHeightInPoints((short)11);
		no.setFont(fontNo);
		no.setVerticalAlignment(VerticalAlignment.CENTER);
		no.setAlignment(HorizontalAlignment.CENTER);
		no.setBorderBottom(BorderStyle.THIN);
		no.setBorderTop(BorderStyle.THIN);
		no.setBorderRight(BorderStyle.THIN);
		no.setBorderLeft(BorderStyle.THIN);

		mapColStyle.put("NO",no);

		if(StringUtils.equalsIgnoreCase(fileName, "HDBH_thu_phi_ca_nhan.xlsx")) {
			titleStylTotal.setFont(fontTitle);
			XSSFRow row4 = xssfSheet.getRow(2);
			if(row4 == null) row4 = xssfSheet.createRow(2);
			XSSFCell cell4 = row4.getCell(0);
			if(cell4 == null) cell4 =row4.createCell(0);
			cell4.setCellValue(agentGroup + ": " + agentCode + " - " +agentName);
			cell4.setCellStyle(titleStyleDate);

			XSSFRow row3 = xssfSheet.getRow(3);
			if(row3 == null) row3 = xssfSheet.createRow(3);
			XSSFCell cell3 = row3.getCell(0);
			if(cell3 == null) cell3 =row3.createCell(0);
			cell3.setCellValue("Tổng số HĐ: " + total);
			cell3.setCellStyle(titleStyleDate);
		}else if(StringUtils.equalsIgnoreCase(fileName, "HDBH_mat_hieu_luc_ca_nhan.xlsx")
				|| StringUtils.equalsIgnoreCase(fileName, "HDBH_yeu_cau_boi_thuong_ca_nhan.xlsx")
				|| StringUtils.equalsIgnoreCase(fileName, "HDBH_dieu_chinh_nghiep_vu_ca_nhan.xlsx")){
			titleStylTotal.setFont(fontTitle);
			XSSFRow row4 = xssfSheet.getRow(2);
			if(row4 == null) row4 = xssfSheet.createRow(2);
			XSSFCell cell4 = row4.getCell(0);
			if(cell4 == null) cell4 =row4.createCell(0);
			cell4.setCellValue(agentGroup + ": " + agentCode + " - " +agentName);
			cell4.setCellStyle(titleStyleDate);

			XSSFRow row3 = xssfSheet.getRow(3);
			if(row3 == null) row3 = xssfSheet.createRow(3);
			XSSFCell cell3 = row3.getCell(0);
			if(cell3 == null) cell3 =row3.createCell(0);
			String totalRow = fileName.equals("HDBH_yeu_cau_boi_thuong_ca_nhan.xlsx")?"Tổng số yêu cầu bồi thường: ":"Tổng số HĐ: ";
			cell3.setCellValue(totalRow + total);
			cell3.setCellStyle(titleStyleDate);

		}else{
			titleStylTotal.setFont(fontTitle);
			XSSFRow row4 = xssfSheet.getRow(2);
			if(row4 == null) row4 = xssfSheet.createRow(2);
			XSSFCell cell4 = row4.getCell(0);
			if(cell4 == null) cell4 =row4.createCell(0);
			cell4.setCellValue(agentGroup + ": " + agentCode + " - " +agentName);
			cell4.setCellStyle(titleStyleDate);

			XSSFRow row3 = xssfSheet.getRow(3);
			if(row3 == null) row3 = xssfSheet.createRow(3);
			XSSFCell cell3 = row3.getCell(0);
			if(cell3 == null) cell3 =row3.createCell(0);
			cell3.setCellValue("Tổng số hợp đồng: " + total);
			cell3.setCellStyle(titleStyleDate);

		}



			
		titleStyle.setFont(fontTitle);

		XSSFRow row1 = xssfSheet.getRow(0);
		if(row1 == null) row1 = xssfSheet.createRow(0);
		
		XSSFCell cell1 = row1.getCell(0);
		if(cell1 == null) cell1 =row1.createCell(0);
		
		cell1.setCellValue(titleName);
		cell1.setCellStyle(titleStyle);
		titleStyle.setFont(fontTitle);

        XSSFRow row2 = xssfSheet.getRow(1);
		if(row2 == null) row2 = xssfSheet.createRow(1);
        XSSFCell cell2 = row2.getCell(0);
		if(cell2 == null) cell2 =row2.createCell(1);
        cell2.setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		cell2.setCellStyle(titleStyleDate);

		XSSFRow row4 = xssfSheet.getRow(5);
		if(row4 == null) row4 = xssfSheet.createRow(5);

		CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Font font = xssfWorkbook.createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);

		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);

		for (int i = 0; i < titleHeader.length; i++) {
			XSSFCell cell4 = row4.getCell(i);
			if(cell4 == null) cell4 = row4.createCell(i);
			cell4.setCellValue(titleHeader[i]);
			cell4.setCellStyle(headerCellStyle);
		}
		
	}
	
	@Override
	public List<ContractClaimResultDto> getListClaimForExport(ContractClaimSearchDto searchDto) {
		// result
		List<ContractClaimResultDto> result = new ArrayList<>();
		try {
			String agentCode = searchDto.getAgentCode();
			searchDto.setFunctionCode("CLAIM_PERSONAL_POLICY");
			searchDto.setSearchType("ALL");

			ObjectMapper mapper = new ObjectMapper();
			String stringJsonParam = mapper.writeValueAsString(searchDto);

			CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
					"CLAIM_PERSONAL_POLICY");
			ContractClaimReportParam param = new ContractClaimReportParam();
			param.search = common.getSearch();
			param.agentCode = agentCode;
			param.policyType = searchDto.getPolicyType();
			param.sort = "order by C.SCANDATE desc";

			sqlManagerDb2Service.call(DS_SP_GET_CLAIM_CONTRACT_EXPORT, param);

			result = param.data;
		} catch (Exception e) {
			logger.error("getListClaimForExport", e);
		}
		return result;
	}
	
	@Override
	public ObjectDataRes<PolicyMaturedResultDto> getListPolicyMatured(PolicyMaturedSearchDto searchDto, boolean isExport) {
		searchDto.setFunctionCode("POLICY_MATURED_PERSONAL");
		searchDto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		if (isExport) {
			searchDto.setPage(null);
			searchDto.setPageSize(null);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "POLICY_MATURED_PERSONAL");

		PolicyMaturedPagingParam param = new PolicyMaturedPagingParam();
		param.agentCode=searchDto.getAgentCode();
		
		String searchCondition = common.getSearch().trim();
		// Search for SỐ LƯỢNG PROPOSAL MỚI (SO VỚI NGÀY ĐÁO HẠN -120 NGÀY -> +60 NGÀY)
		if (StringUtils.isNotBlank(searchCondition) && searchCondition.contains("RPT_ODS.DS_FN_REMOVEMARK(nvl(SURVEY_RESULT,''))= UPPER('2')")) {
			// SURVEY_RESULT = 1 Có nhu cầu mua mới
			searchCondition = searchCondition.replace("RPT_ODS.DS_FN_REMOVEMARK(nvl(SURVEY_RESULT,''))= UPPER('2')", "UPPER(nvl(SURVEY_RESULT,'')) <> UPPER('1') AND IS_NEW_PROPOSAL = 1");
		}
		param.search = searchCondition;
		
		param.sort = common.getSort();
		param.page=searchDto.getPage();
		param.pageSize=searchDto.getPageSize();
		
		try {
			sqlManagerDb2Service.call(DS_SP_GET_LIST_POLICY_MATURED, param);
		} catch (Exception e) {
			logger.error("Exception SQL: ", e);
		}
		
		
		List<PolicyMaturedResultDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
					
			if (param.total != null) {
				total = param.total;
			}
		}
		
		ObjectDataRes<PolicyMaturedResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}
	
	@Override
	public ResponseEntity exportListPolicyMatured(PolicyMaturedSearchDto searchDto) {
		ResponseEntity res = null;
		try {
			ObjectDataRes<PolicyMaturedResultDto> listPolicyMatured = getListPolicyMatured(searchDto, true);
			
			String datePattern = "dd/MM/yyyy";
			String templateName = "HDBH_dao_han_canhan.xlsx";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(ListPolicyMaturedPersonal.class, cols);
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A8";

			List<PolicyMaturedResultDto> lstdata = listPolicyMatured.getDatas();
			for(PolicyMaturedResultDto item: lstdata) {
				item.setPolicyKey(formatPolicyNumber(9, item.getPolicyKey()));
				if ("GROUP".equals(searchDto.getMaturedType())
						&& ("BD".equals(searchDto.getAgentGroupType()) || "CAO".equals(searchDto.getAgentGroupType())
						|| "GAD".equals(searchDto.getAgentGroupType()) || "LEADER".equals(searchDto.getAgentGroupType()))) {
					item.setHomeAddress(null);
					item.setCellPhone(null);
					item.setHomePhone(null);
					item.setWorkPhone(null);
				}
				item.setSurveyDataToDisplay();
			}
			// start fill data to workbook
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat =  new HashMap<>();
			Map<String, Object> setMapColDefaultValue = null;

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
				setDataHeaderForListPolicyMatured(xssfWorkbook, 0, mapColStyle, searchDto, lstdata.size());
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						PolicyMaturedResultDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			logger.error("exportListPolicyMatured: ", e);
		}
		return res;
	}
	
	@Override
	public PolicyMaturedResultDto getDetailPolicyMatured(String policyNo) {
		PolicyMaturedDetailParam param = new PolicyMaturedDetailParam();
		param.policyNo = policyNo;
		
		try {
			sqlManagerDb2Service.call(DS_SP_GET_DETAIL_POLICY_MATURED, param);
		} catch (Exception e) {
			logger.error("Exception SQL: ", e);
		}
		
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			return param.data.get(0);
		}
		
		return null;
	}
	
	private void setDataHeaderForListPolicyMatured(XSSFWorkbook xssfWorkbook, int sheet, Map<String, CellStyle> mapColStyle, PolicyMaturedSearchDto searchDto, Integer total) {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheet);

		CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();
		CellStyle no = xssfSheet.getWorkbook().createCellStyle();

		Font fontNo = xssfWorkbook.createFont();
		fontNo.setFontName("Times New Roman");
		fontNo.setFontHeightInPoints((short)11);
		no.setFont(fontNo);
		no.setAlignment(HorizontalAlignment.CENTER);
		no.setVerticalAlignment(VerticalAlignment.CENTER);
		no.setBorderBottom(BorderStyle.THIN);
		no.setBorderTop(BorderStyle.THIN);
		no.setBorderRight(BorderStyle.THIN);
		no.setBorderLeft(BorderStyle.THIN);

		Font fontTitleDate = xssfWorkbook.createFont();
		fontTitleDate.setColor(IndexedColors.BLUE.index);
		fontTitleDate.setFontName("Times New Roman");
		titleStyleDate.setFont(fontTitleDate);

		Font font = xssfWorkbook.createFont();
		font.setColor(IndexedColors.BLUE.index);
		font.setFontName("Times New Roman");
		headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle.setFont(font);

		mapColStyle.put("NO",no);

		if(xssfSheet.getRow(2) != null)
			xssfSheet.getRow(2).getCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		else xssfSheet.createRow(2).createCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));

		if(xssfSheet.getRow(3) != null)
			xssfSheet.getRow(3).getCell(0).setCellValue(searchDto.getAgentType() + ": " + searchDto.getAgentCode() + " - " + searchDto.getAgentName());
		else xssfSheet.createRow(3).createCell(0).setCellValue(searchDto.getAgentType() + ": " + searchDto.getAgentCode() + " - " + searchDto.getAgentName());
		
		DecimalFormat df = new DecimalFormat("###,###,###");
		if(xssfSheet.getRow(4) != null)
			xssfSheet.getRow(4).getCell(0).setCellValue("Tổng số HĐ: " + df.format(total));
		else xssfSheet.createRow(4).createCell(0).setCellValue("Tổng số HĐ: " + df.format(total));
		
		
		xssfSheet.getRow(2).getCell(0).setCellStyle(titleStyleDate);
	}
	
	private String formatPolicyNumber(int digits, String policyNumber){
		if(StringUtils.isEmpty(policyNumber)) {
			return "";
		}
        return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining("")).concat(policyNumber);
    }
	
	@Override
	public List<ContactHistoryDetailDto> getContactHistoryByClaimNo(String claimNo){
		ContactHistoryDetailParam param = new ContactHistoryDetailParam();
		param.claimNo = claimNo;
		
		try {
			sqlManagerDb2Service.call(GET_CONTACT_HISTORY_BY_CLAIM_NO, param);
		} catch (Exception e) {
			logger.error("Exception SQL: ", e);
		}
		
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			return param.data;
		}
		return null;
	}
	
	@Override
	public List<CostOfRefusalToPayDto> getCostOfRefusalToPay(String claimNo){
		CostOfRefusalToPayParam param = new CostOfRefusalToPayParam();
		param.claimNo = claimNo;
		
		try {
			sqlManagerDb2Service.call(DS_SP_GET_COST_OF_REFUSAL_TO_PAY, param);
		} catch (Exception e) {
			logger.error("Exception SQL: ", e);
		}
		
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			return param.data;
		}
		return null;
	}
	
}

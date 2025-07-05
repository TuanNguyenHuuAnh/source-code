package vn.com.unit.ep2p.adp.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchDueDateResultDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalPolicyDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalPolicyPersonalParam;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.adp.dto.AgentInfoSaleSopDto;
import vn.com.unit.ep2p.adp.dto.AgentInfoSaleSopParamDto;
import vn.com.unit.ep2p.adp.dto.AgentInfoSearchDto;
import vn.com.unit.ep2p.adp.dto.AgentInfoSearchParamDto;
import vn.com.unit.ep2p.adp.dto.AgentInfoSearchResultDto;
import vn.com.unit.ep2p.adp.dto.AggregateReportDataRes;
import vn.com.unit.ep2p.adp.dto.AggregateReportDto;
import vn.com.unit.ep2p.adp.dto.AggregateReportParamDto;
import vn.com.unit.ep2p.adp.dto.AggregateReportRowDetailDto;
import vn.com.unit.ep2p.adp.dto.AggregateReportSearchDto;
import vn.com.unit.ep2p.adp.dto.BenefiDto;
import vn.com.unit.ep2p.adp.dto.BenefiParamDto;
import vn.com.unit.ep2p.adp.dto.CareContactPolicyDto;
import vn.com.unit.ep2p.adp.dto.CareContactPolicyParamDto;
import vn.com.unit.ep2p.adp.dto.CareContactPolicySearchDto;
import vn.com.unit.ep2p.adp.dto.CheckPermissionParamDto;
import vn.com.unit.ep2p.adp.dto.ClaimAssessorCommentParamDto;
import vn.com.unit.ep2p.adp.dto.ClaimAssessorCommentResultDto;
import vn.com.unit.ep2p.adp.dto.ContactHistoryDto;
import vn.com.unit.ep2p.adp.dto.ContactHistorySearchParamDto;
import vn.com.unit.ep2p.adp.dto.DuePolicyCardPersonalDto;
import vn.com.unit.ep2p.adp.dto.DuePolicyCardPersonalParamDto;
import vn.com.unit.ep2p.adp.dto.DuePolicyDetailPersonalDto;
import vn.com.unit.ep2p.adp.dto.DuePolicyDetailPersonalParamDto;
import vn.com.unit.ep2p.adp.dto.GeneralReportDto;
import vn.com.unit.ep2p.adp.dto.GeneralReportParamDto;
import vn.com.unit.ep2p.adp.dto.GeneralReportSearchDto;
import vn.com.unit.ep2p.adp.dto.GroupDocumentDto;
import vn.com.unit.ep2p.adp.dto.GroupDocumentParamDto;
import vn.com.unit.ep2p.adp.dto.ItemDto;
import vn.com.unit.ep2p.adp.dto.ItemParamDto;
import vn.com.unit.ep2p.adp.dto.PartnerParamDto;
import vn.com.unit.ep2p.adp.dto.PersonalInsuranceDto;
import vn.com.unit.ep2p.adp.dto.PersonalInsuranceParamDto;
import vn.com.unit.ep2p.adp.dto.PersonalInsuranceSearchDto;
import vn.com.unit.ep2p.adp.dto.PersonalPolicyParamDto;
import vn.com.unit.ep2p.adp.dto.PersonalPolicyResultDto;
import vn.com.unit.ep2p.adp.dto.PersonalPolicySearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyClaimSearchByNoParam;
import vn.com.unit.ep2p.adp.dto.PolicyClaimSearchParam;
import vn.com.unit.ep2p.adp.dto.PolicyClaimSearchResultDto;
import vn.com.unit.ep2p.adp.dto.PolicyContactHistoryDto;
import vn.com.unit.ep2p.adp.dto.PolicyContactHistoryParamDto;
import vn.com.unit.ep2p.adp.dto.PolicyContactHistorySearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryDetailsDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryDetailsSearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryParamCustomDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryParamDetailsDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryParamDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliverySearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyDueDateDetailParamDto;
import vn.com.unit.ep2p.adp.dto.PolicyFeeCardPersonalDto;
import vn.com.unit.ep2p.adp.dto.PolicyFeeCardPersonalParamDto;
import vn.com.unit.ep2p.adp.dto.PolicyFeeDetailPersonalDto;
import vn.com.unit.ep2p.adp.dto.PolicyFeeDetailPersonalParamDto;
import vn.com.unit.ep2p.adp.dto.PolicyRequestDetailParam;
import vn.com.unit.ep2p.adp.dto.PolicyRequestSearchByNoParam;
import vn.com.unit.ep2p.adp.dto.PolicyRequestSearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyRequestSearchParam;
import vn.com.unit.ep2p.adp.dto.PolicyRequestSearchResultDto;
import vn.com.unit.ep2p.adp.dto.PolicySearchDto;
import vn.com.unit.ep2p.adp.dto.PolicySearchParamDto;
import vn.com.unit.ep2p.adp.dto.ProductDetailDto;
import vn.com.unit.ep2p.adp.dto.ProductDetailParam;
import vn.com.unit.ep2p.adp.dto.ProductDetailSearch;
import vn.com.unit.ep2p.adp.dto.ProposalDetailDto;
import vn.com.unit.ep2p.adp.dto.ProposalDetailParamDto;
import vn.com.unit.ep2p.adp.dto.ProposalDetailSearchDto;
import vn.com.unit.ep2p.adp.dto.ProposalSearchDto;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusDto;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusExportParamDto;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusPagination;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusParamDto;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusSearchDto;
import vn.com.unit.ep2p.adp.dto.TotalProposalDto;
import vn.com.unit.ep2p.adp.dto.TotalProposalParamDto;
import vn.com.unit.ep2p.adp.dto.TrackingDetailReportDto;
import vn.com.unit.ep2p.adp.dto.TrackingDetailReportParamDto;
import vn.com.unit.ep2p.adp.dto.TrackingReportDto;
import vn.com.unit.ep2p.adp.dto.TrackingReportParamDto;
import vn.com.unit.ep2p.adp.dto.TrackingReportSearchDto;
import vn.com.unit.ep2p.adp.enumdef.EnumExportAgentInfo;
import vn.com.unit.ep2p.adp.enumdef.EnumExportAreaTrackingReport;
import vn.com.unit.ep2p.adp.enumdef.EnumExportK2K2plusReport;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListCancelPolicy;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListClaimPolicy;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListConfirmed;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListContactHistory;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListDuePolicy;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListFeePolicy;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListNewPolicy;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListPolicyDelivery;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListPrersonalInsurance;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListPrersonalPolicy;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListRenewPolicy;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListRequestPolicy;
import vn.com.unit.ep2p.adp.enumdef.EnumExportListTransferred;
import vn.com.unit.ep2p.adp.enumdef.EnumExportPFReport;
import vn.com.unit.ep2p.adp.enumdef.EnumExportPartnerTrackingReport;
import vn.com.unit.ep2p.adp.enumdef.EnumExportTrackingDetailReport;
import vn.com.unit.ep2p.adp.enumdef.EnumExportUpDownAgentInfo;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.CellStyleDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class AdportalServiceImpl extends AbstractCommonService implements AdportalService {

    @Autowired
    ParseJsonToParamSearchService parseJsonToParamSearchService;

    @Autowired
    @Qualifier("sqlManageDb2Service")
    private SqlManagerDb2Service sqlManagerDb2Service;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private SystemConfig systemConfig;

    private LoadingCache<String, AggregateReportDataRes> dataOutput;
    private static final Logger logger = LoggerFactory.getLogger(AdportalServiceImpl.class);
    private static final String STORE_GET_PROPOSAL_DETAIL = "RPT_ODS.ADP_SP_GET_PROPOSAL_DETAIL";
    
    private static final String STORE_GET_TOTAL_INSURANCE = "RPT_ODS.ADP_SP_GET_TOTAL_INSURANCE_DOCUMENT";
    private static final String STORE_GET_LIST_INSURANCE = "RPT_ODS.ADP_SP_GET_LIST_INSURANCE_DOCUMENT";
    private static final String STORE_GET_TOTAL_POLICY = "RPT_ODS.ADP_SP_GET_TOTAL_POLICY";
    private static final String STORE_GET_LIST_POLICY = "RPT_ODS.ADP_SP_GET_LIST_POLICY";
    private static final String STORE_GET_LIST_INSURANCE_EXP = "RPT_ODS.ADP_SP_GET_LIST_INSURANCE_DOCUMENT_EXP";
    private static final String STORE_GET_LIST_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_POLICY_EXP";
    
    private static final String STORE_GET_LIST_POLICY_BY_KEYWORD = "RPT_ODS.ADP_SP_GET_LIST_POLICY_BY_KEYWORD";
    
    private static final String STORE_GET_LIST_PRODUCT = "RPT_ODS.ADP_SP_GET_LIST_PRODUCT";
    private static final String STORE_GET_LIST_BENEFI = "RPT_ODS.ADP_SP_GET_LIST_BENEFI";
    
    private static final String STORE_GET_LIST_PARTNER = "RPT_ODS.ADP_SP_GET_PARTNER";
    private static final String STORE_GET_LIST_ITEM = "RPT_ODS.ADP_SP_GET_LIST_ITEM";
    
    /** Quản lý giao nhận hợp đồng **/
    private static final String STORE_GET_DETAIL_POLICY_DELIVERY = "RPT_ODS.ADP_SP_GET_DETAIL_POLICY_DELIVERY";
    private static final String STORE_GET_DETAIL_POLICY_CONFIRMED = "RPT_ODS.ADP_SP_GET_DETAIL_POLICY_CONFIRMED";
    private static final String STORE_GET_DETAIL_POLICY_TRANFERED = "RPT_ODS.ADP_SP_GET_DETAIL_POLICY_TRANFERED";
    private static final String STORE_GET_LIST_POLICY_DELIVERY = "RPT_ODS.ADP_SP_GET_LIST_POLICY_DELIVERY";
    private static final String STORE_GET_LIST_POLICY_DELIVERY_EXP = "RPT_ODS.ADP_SP_GET_LIST_POLICY_DELIVERY_EXP";
    private static final String STORE_GET_LIST_POLICY_CONFIRMED = "RPT_ODS.ADP_SP_GET_LIST_POLICY_CONFIRMED";
    private static final String STORE_GET_LIST_POLICY_CONFIRMED_EXP = "RPT_ODS.ADP_SP_GET_LIST_POLICY_CONFIRMED_EXP";
    private static final String STORE_GET_LIST_POLICY_TRANSFERRED = "RPT_ODS.ADP_SP_GET_LIST_POLICY_TRANFERED";
    private static final String STORE_GET_LIST_POLICY_TRANSFERRED_EXP = "RPT_ODS.ADP_SP_GET_LIST_POLICY_TRANFERED_EXP";
    
    private static final String STORE_GET_GET_REPORT_TRACKING = "RPT_ODS.ADP_SP_GET_REPORT_TRACKING";
    private static final String STORE_GET_GET_REPORT_TRACKING_DETAIL = "RPT_ODS.ADP_SP_GET_REPORT_TRACKING_DETAIL";
    
    private static final String AD_SP_GET_AGENT_INFO_SALE_SOP = "RPT_ODS.AD_SP_GET_AGENT_INFO_SALE_SOP";
    
    private static final String SP_GET_LIST_REQUEST_POLICY = "RPT_ODS.ADP_SP_GET_LIST_REQUEST_POLICY";
    private static final String SP_GET_DETAIL_REQUEST_POLICY = "RPT_ODS.ADP_SP_GET_DETAIL_REQUEST_POLICY";
    private static final String STORE_GET_LIST_REQUEST_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_REQUEST_POLICY_EXP";
    
    private static final String SP_GET_LIST_CLAIM_POLICY = "RPT_ODS.ADP_SP_GET_LIST_CLAIM_POLICY";
    private static final String STORE_GET_LIST_CLAIM_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_CLAIM_POLICY_EXP";
    
    /** Quản lý hợp đồng phát sinh phí **/
    // Hợp đồng phát sinh phí
    private static final String STORE_GET_LIST_FEE_POLICY = "RPT_ODS.ADP_SP_GET_LIST_FEE_POLICY";
    private static final String STORE_GET_LIST_FEE_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_FEE_POLICY_EXP";
    // Hợp đồng mới
    private static final String STORE_GET_LIST_NEW_POLICY = "RPT_ODS.ADP_SP_GET_LIST_NEW_POLICY";
    private static final String STORE_GET_LIST_NEW_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_NEW_POLICY_EXP";
    // Hợp đồng tái tục
    private static final String STORE_GET_LIST_RENEWAL_POLICY = "RPT_ODS.ADP_SP_GET_LIST_RENEWAL_POLICY";
    private static final String STORE_GET_LIST_RENEWAL_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_RENEWAL_POLICY_EXP";
    // Hợp đồng hủy
    private static final String STORE_GET_LIST_CANCELED_POLICY = "RPT_ODS.ADP_SP_GET_LIST_CANCELED_POLICY";    
    private static final String STORE_GET_LIST_CANCELED_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_CANCELED_POLICY_EXP";
    
    /** Danh sách nhắc thu phí tái tục **/
    // Danh sách đến hạn thu phí / Danh sách đến hạn T-16 đến T-90
    private static final String STORE_GET_LIST_DUE_POLICY = "RPT_ODS.ADP_SP_GET_LIST_DUE_POLICY";
    private static final String STORE_GET_LIST_DUE_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_DUE_POLICY_EXP";
    private static final String STORE_GET_DETAIL_DUE_DATE_POLICY = "RPT_ODS.ADP_SP_GET_DETAIL_DUE_DATE_POLICY";
    
    /** Thông tin cấu trúc của đại lý **/
    private static final String STORE_GET_AGENT_INFO = "RPT_ODS.ADP_SP_GET_AGENT_INFO";    
    private static final String STORE_GET_LIST_UP_AGENT_INFO = "RPT_ODS.ADP_SP_GET_LIST_UP_AGENT_INFO";
    private static final String STORE_GET_LIST_DOWN_AGENT_INFO = "RPT_ODS.ADP_SP_GET_LIST_DOWN_AGENT_INFO";
    
    /** Báo cáo **/
    private static final String STORE_GET_REPORT_GENERAL = "RPT_ODS.ADP_SP_GET_REPORT_GENERAL";
    private static final String STORE_GET_REPORT_K2_K2P = "RPT_ODS.ADP_SP_GET_REPORT_K2_K2P";
    private static final String STORE_GET_REPORT_K2_K2P_EXP = "RPT_ODS.ADP_SP_GET_REPORT_K2_K2P_EXP";
    private static final String STORE_GET_REPORT_PF = "RPT_ODS.ADP_SP_GET_REPORT_PF";
    private static final String STORE_GET_REPORT_PF_EXP = "RPT_ODS.ADP_SP_GET_REPORT_PF_EXP";
    
    private static final String STORE_GET_AGGREGATE_REPORT_BY_PARTNER = "RPT_ODS.ADP_SP_GET_AGGREGATE_REPORT_BY_PARTNER";
    private static final String STORE_GET_AGGREGATE_REPORT_BY_AREA = "RPT_ODS.ADP_SP_GET_AGGREGATE_REPORT_BY_AREA";
    private static final String STORE_GET_AGGREGATE_REPORT_BY_REGION = "RPT_ODS.ADP_SP_GET_AGGREGATE_REPORT_BY_REGION";
    private static final String STORE_GET_AGGREGATE_REPORT_BY_ZONE = "RPT_ODS.ADP_SP_GET_AGGREGATE_REPORT_BY_ZONE";
    
    private static final String STORE_GET_PERMISSION_BY_AGENT = "RPT_ODS.ADP_SP_GET_PERMISSION_BY_AGENT";
    
    private static final String STORE_GET_CONTACT_HISTORY_BY_POLICY = "RPT_ODS.ADP_SP_GET_CONTACT_HISTORY_BY_POLICY";
    private static final String STORE_GET_LIST_CARE_CONTACT_POLICY = "RPT_ODS.ADP_SP_GET_LIST_CARE_CONTACT_POLICY";
    private static final String STORE_GET_LIST_CARE_CONTACT_POLICY_EXP = "RPT_ODS.ADP_SP_GET_LIST_CARE_CONTACT_POLICY_EXP";
    private static final String STORE_GET_LIST_CONTACT_HISTORY_BY_AGENT = "RPT_ODS.ADP_SP_GET_LIST_CONTACT_HISTORY_BY_AGENT";
    private static final String STORE_GET_LIST_CONTACT_HISTORY_BY_AGENT_EXP = "RPT_ODS.ADP_SP_GET_LIST_CONTACT_HISTORY_BY_AGENT_EXP";
    
    private static final String STORE_GET_CLAIM_BY_POLICY = "RPT_ODS.ADP_SP_GET_CLAIM_BY_POLICY";
    private static final String STORE_GET_REQUEST_BY_POLICY = "RPT_ODS.ADP_SP_GET_REQUEST_BY_POLICY";
    private static final String STORE_GET_CLAIM_ASSESSOR_COMMENT = "RPT_ODS.ADP_SP_GET_CLAIM_ASSESSOR_COMMENT";
    
    public AdportalServiceImpl() {
        super();
        dataOutput = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES)
                .build(new CacheLoader<String, AggregateReportDataRes>() {
                    public AggregateReportDataRes load(String key) {
                        return null;
                    }
                });
    }
    
    @Override
    public TotalProposalDto getTotalProposal(ProposalSearchDto searchDto) {
        String user = UserProfileUtils.getFaceMask();
        if (StringUtils.isEmpty(user)) {
            user = searchDto.getAgentCode();
        }

        TotalProposalParamDto paramDto = new TotalProposalParamDto();
        paramDto.agentCode = user;
        sqlManagerDb2Service.call(STORE_GET_TOTAL_INSURANCE, paramDto);
        if (paramDto.data.size() > 0) {
            return paramDto.data.get(0);
        }
        return null;
    }

    @Override
    public TotalPolicyDto getTotalPolicy(String agentCode) {
        String user = UserProfileUtils.getFaceMask();
        if (StringUtils.isEmpty(user)) {
            user = agentCode;
        }

        TotalPolicyPersonalParam paramDto = new TotalPolicyPersonalParam();
        paramDto.agentCode = user;
        sqlManagerDb2Service.call(STORE_GET_TOTAL_POLICY, paramDto);
        if (paramDto.data.size() > 0) {
            return paramDto.data.get(0);
        }
        return null;
    }
    
    public CmsCommonPagination<PersonalInsuranceDto> getListInsuranceByStatus(
			PersonalInsuranceSearchDto searchDto) {
		CmsCommonPagination<PersonalInsuranceDto> rs = new CmsCommonPagination<>();
		PersonalInsuranceParamDto param = new PersonalInsuranceParamDto();
		if ("1".equalsIgnoreCase(searchDto.getType())) {
			searchDto.setFunctionCode("POL_DOC_SUBMITTED"); // DA NOP
		} else if ("2".equalsIgnoreCase(searchDto.getType())) {
			searchDto.setFunctionCode("POL_DOC_ADDITIONAL"); // BO SUNG
		} else if ("3".equalsIgnoreCase(searchDto.getType())) {
			searchDto.setFunctionCode("POL_DOC_RELEASE"); // DA PHAT HANH
		} else {
			searchDto.setFunctionCode("POL_DOC_REFUSE"); // BI TU CHOI
		}
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common;
		common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());

		param.agentCode = searchDto.getAgentCode();
		param.partner = searchDto.getPartner();
		param.regionCode = searchDto.getRegionCode();
		param.zoneCode = searchDto.getZoneCode();
		param.uoCode = searchDto.getUoCode();
		param.areaCodeDLVN = searchDto.getAreaCodeDLVN();
		param.regionCodeDLVN = searchDto.getRegionCodeDLVN();
		param.zoneCodeDLVN = searchDto.getZoneCodeDLVN();
		param.ilCode = searchDto.getIlCode();
		param.isCode = searchDto.getIsCode();
		param.smCode = searchDto.getSmCode();
		param.type = searchDto.getType();
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		param.sort = common.getSort();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(StringUtils.isNotEmpty(searchDto.getFromDate())){
			try {
				param.fromDate = sdf.parse(searchDto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(searchDto.getToDate())){
			try {
				param.toDate = sdf.parse(searchDto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		param.search = common.getSearch();
		sqlManagerDb2Service.call(STORE_GET_LIST_INSURANCE, param);
		List<PersonalInsuranceDto> lstData = param.lstData;
		rs.setTotalData(param.totalRows);
		rs.setData(lstData);
		return rs;
	}
    
    @Override
	public ObjectDataRes<PersonalPolicyResultDto> getListPolicyByType(PersonalPolicySearchDto dto) {
		dto.setFunctionCode("ADP_PERSONAL_POLICY");
		dto.setSearchType("ALL");

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, dto.getFunctionCode());

		PersonalPolicyParamDto param = new PersonalPolicyParamDto();
		param.agentCode= dto.getAgentCode();
		param.partner = dto.getPartner();
		param.regionCode = dto.getRegionCode();
		param.zoneCode = dto.getZoneCode();
		param.uoCode = dto.getUoCode();
		param.areaCodeDLVN = dto.getAreaCodeDLVN();
		param.regionCodeDLVN = dto.getRegionCodeDLVN();
		param.zoneCodeDLVN = dto.getZoneCodeDLVN();
		param.ilCode = dto.getIlCode();
		param.isCode = dto.getIsCode();
		param.smCode = dto.getSmCode();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(StringUtils.isNotEmpty(dto.getFromDate())){
			try {
				param.fromDate = sdf.parse(dto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(dto.getToDate())){
			try {
				param.toDate = sdf.parse(dto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		param.policyType = dto.getTypeEffect();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(STORE_GET_LIST_POLICY, param);
		
		List<PersonalPolicyResultDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			if(StringUtils.equalsIgnoreCase(dto.getTypeEffect(), "INACTIVE"))
			data.stream().forEach(x->{x.setExpirationDate(x.getDateDue());});
			
			if (param.total != null) {
				total = param.total;
			}
		}
		
		ObjectDataRes<PersonalPolicyResultDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}
    
    @Override
	public ObjectDataRes<PersonalPolicyResultDto> getListPolicyByKeyword(PolicySearchDto dto) {
    	PolicySearchParamDto param = new PolicySearchParamDto();
    	if (dto.getKeyword() != null && dto.getKeyword().length() >= 6) {
    		param.agentCode = UserProfileUtils.getFaceMask();
    		param.page = dto.getPage();
    		param.pageSize = dto.getPageSize();
    		if (dto.getKeyword().chars().allMatch(Character::isDigit)) {
    			param.search = " and (P.POLICY_KEY like '%" + dto.getKeyword() + "%' or P.PROPOSAL_CODE like '%" + dto.getKeyword() + "%')";
    		} else {
    			param.search = " and (P.PROPOSAL_CODE like '%" + dto.getKeyword() + "%' or RPT_ODS.DS_FN_REMOVEMARK(P.PO_NAME) like RPT_ODS.DS_FN_REMOVEMARK(UPPER('%" + dto.getKeyword() + "%')))";
    		}
    		sqlManagerDb2Service.call(STORE_GET_LIST_POLICY_BY_KEYWORD, param);
    	}
		ObjectDataRes<PersonalPolicyResultDto> resObj = new ObjectDataRes<>(param.total, param.data);
		return resObj;
	}
    
    @Override
    public ProposalDetailDto getProposalDetail(ProposalDetailSearchDto searchDto) {
        String user = UserProfileUtils.getFaceMask();
        ProposalDetailParamDto paramDto = new ProposalDetailParamDto();
        paramDto.agentCode = user;
        paramDto.type = searchDto.getType();
        paramDto.docNo = searchDto.getProposalNo();
        sqlManagerDb2Service.call(STORE_GET_PROPOSAL_DETAIL, paramDto);
        if (paramDto.lstData != null && paramDto.lstData.size() > 0) {
        	if (checkPermission(searchDto.getProposalNo())) {
        		return paramDto.lstData.get(0);	
        	}
        }
        return null;
    }

    @Override
    public CmsCommonPagination<ProductDetailDto> getListProduct(ProductDetailSearch dto) {
        ProductDetailParam param = new ProductDetailParam();
        param.polId = dto.getPolicyNo();
        sqlManagerDb2Service.call(STORE_GET_LIST_PRODUCT, param);
        List<ProductDetailDto> datas = param.data;
        CmsCommonPagination<ProductDetailDto> resultData = new CmsCommonPagination<>();
        if (checkPermission(String.valueOf(dto.getPolicyNo()))) {
        	resultData.setData(datas);	
        }
        return resultData;
    }

    @Override
    public CmsCommonPagination<BenefiDto> getListBenefi(String policyNo) {
        BenefiParamDto paramDto = new BenefiParamDto();
        paramDto.policyNo = policyNo;
        sqlManagerDb2Service.call(STORE_GET_LIST_BENEFI, paramDto);
        List<BenefiDto> datas = paramDto.lstData;
        CmsCommonPagination<BenefiDto> resultData = new CmsCommonPagination<>();
        if (checkPermission(policyNo)) {
        	resultData.setData(datas);	
        }
        return resultData;
    }

    private CmsCommonPagination<PolicyFeeDetailPersonalDto> getPolicyFeeDetailDataExport(PersonalInsuranceSearchDto dto, int reportType) {
        CmsCommonPagination<PolicyFeeDetailPersonalDto> rs = new CmsCommonPagination<>();
        try {
	        PolicyFeeDetailPersonalParamDto param = new PolicyFeeDetailPersonalParamDto();
	        param.agentCode = UserProfileUtils.getFaceMask();
			param.partner = dto.getPartner();
			param.regionCode = dto.getRegionCode();
			param.zoneCode = dto.getZoneCode();
			param.uoCode = dto.getUoCode();
			param.ilCode = dto.getIlCode();
			param.isCode = dto.getIsCode();
			param.smCode = dto.getSmCode();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			if(StringUtils.isNotEmpty(dto.getFromDate())){			
				param.fromDate = sdf.parse(dto.getFromDate());
			}
			if(StringUtils.isNotEmpty(dto.getToDate())){				
				param.toDate = sdf.parse(dto.getToDate());
			}
			
			String procedureName = getProcedureNameByReportType(true, reportType);			
			sqlManagerDb2Service.call(procedureName, param);	
			
	        rs.setData(param.lstData);
        } catch (Exception e) {
            logger.error("###getPolicyFeeDetailDataExport###", e);
		}
        return rs;
    }
    
    private CmsCommonPagination<DuePolicyDetailPersonalDto> getDuePolicyDetailDataExport(PersonalInsuranceSearchDto dto) {
        CmsCommonPagination<DuePolicyDetailPersonalDto> rs = new CmsCommonPagination<>();
        try {
        	DuePolicyDetailPersonalParamDto param = new DuePolicyDetailPersonalParamDto();
	        param.agentCode = UserProfileUtils.getFaceMask();
			param.partner = dto.getPartner();
			param.regionCode = dto.getRegionCode();
			param.zoneCode = dto.getZoneCode();
			param.uoCode = dto.getUoCode();
			param.ilCode = dto.getIlCode();
			param.isCode = dto.getIsCode();
			param.smCode = dto.getSmCode();
			param.policyType = dto.getType();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			if(StringUtils.isNotEmpty(dto.getFromDate())){			
				param.fromDate = sdf.parse(dto.getFromDate());
			}
			if(StringUtils.isNotEmpty(dto.getToDate())){				
				param.toDate = sdf.parse(dto.getToDate());
			}
									
			sqlManagerDb2Service.call(STORE_GET_LIST_DUE_POLICY_EXP, param);	
			
	        rs.setData(param.lstData);
        } catch (Exception e) {
            logger.error("###getDuePolicyDetailDataExport###", e);
		}
        return rs;
    }
    
    
    private String getProcedureNameByReportType(boolean isExport, int reportType) {
    	logger.info("##getProcedureNameByReportType## .isExport:" + isExport + " .reportType:" + reportType);
    	String procedureName = "";    	
		switch(reportType) {
			case 1 : 
				if(isExport) {    	
					procedureName = STORE_GET_LIST_FEE_POLICY_EXP;
				} else {
					procedureName = STORE_GET_LIST_FEE_POLICY;
				}
				break;
			case 2 :
				if(isExport) {    	
					procedureName = STORE_GET_LIST_NEW_POLICY_EXP;
				} else {
					procedureName = STORE_GET_LIST_NEW_POLICY;
				}
				break;
			case 3 :
				if(isExport) {    	
					procedureName = STORE_GET_LIST_RENEWAL_POLICY_EXP;
				} else {
					procedureName = STORE_GET_LIST_RENEWAL_POLICY;
				}
				break;
			case 4 :
				if(isExport) {    	
					procedureName = STORE_GET_LIST_CANCELED_POLICY_EXP;
				} else {
					procedureName = STORE_GET_LIST_CANCELED_POLICY;
				}
				break;
		}
		logger.info("##getProcedureNameByReportType## .procedureName:" + procedureName);
		return procedureName;
    }
    
    private CmsCommonPagination<PersonalInsuranceDto> getListDataExport(PersonalInsuranceSearchDto dto, String reportType) {
        CmsCommonPagination<PersonalInsuranceDto> rs = new CmsCommonPagination<>();
        PersonalInsuranceParamDto param = new PersonalInsuranceParamDto();
        param.agentCode = UserProfileUtils.getFaceMask();
		param.partner = dto.getPartner();
		param.regionCode = dto.getRegionCode();
		param.zoneCode = dto.getZoneCode();
		param.uoCode = dto.getUoCode();
		param.areaCodeDLVN = dto.getAreaCodeDLVN();
		param.regionCodeDLVN = dto.getRegionCodeDLVN();
		param.zoneCodeDLVN = dto.getZoneCodeDLVN();
		param.ilCode = dto.getIlCode();
		param.isCode = dto.getIsCode();
		param.smCode = dto.getSmCode();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(StringUtils.isNotEmpty(dto.getFromDate())){
			try {
				param.fromDate = sdf.parse(dto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(dto.getToDate())){
			try {
				param.toDate = sdf.parse(dto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        if ("HSYCBH".equals(reportType)) {
        	param.type = dto.getType();
            sqlManagerDb2Service.call(STORE_GET_LIST_INSURANCE_EXP, param);
        } else if ("HDBH".equals(reportType)) {
        	param.type = dto.getTypeEffect();
            sqlManagerDb2Service.call(STORE_GET_LIST_POLICY_EXP, param);
        } 
        List<PersonalInsuranceDto> lstData = param.lstData;
        rs.setData(lstData);
        return rs;
    }

    private CmsCommonPagination<PolicyRequestSearchResultDto> getListRequestPolicyExport(PersonalInsuranceSearchDto dto) {
        CmsCommonPagination<PolicyRequestSearchResultDto> rs = new CmsCommonPagination<>();
        PolicyRequestSearchParam param = new PolicyRequestSearchParam();
        param.agentCode = UserProfileUtils.getFaceMask();
		param.partner = dto.getPartner();
		param.regionCode = dto.getRegionCode();
		param.zoneCode = dto.getZoneCode();
		param.uoCode = dto.getUoCode();
		param.areaCodeDLVN = dto.getAreaCodeDLVN();
		param.regionCodeDLVN = dto.getRegionCodeDLVN();
		param.zoneCodeDLVN = dto.getZoneCodeDLVN();
		param.ilCode = dto.getIlCode();
		param.isCode = dto.getIsCode();
		param.smCode = dto.getSmCode();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(StringUtils.isNotEmpty(dto.getFromDate())){
			try {
				param.fromDate = sdf.parse(dto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(dto.getToDate())){
			try {
				param.toDate = sdf.parse(dto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if ("1".equals(dto.getType())) {
  			param.policyType = "Chờ bổ sung thông tin";
  		} else if ("2".equals(dto.getType())) {
  			param.policyType = "Đã thực hiện";
  		} else if ("3".equals(dto.getType())) {
  			param.policyType = "Đang thực hiện";
  		} else if ("4".equals(dto.getType())) {
  			param.policyType = "Hẹn thực hiện";
  		} else if ("5".equals(dto.getType())) {
  			param.policyType = "Từ chối";
  		}
		sqlManagerDb2Service.call(STORE_GET_LIST_REQUEST_POLICY_EXP, param);
        List<PolicyRequestSearchResultDto> lstData = param.datas;
        rs.setData(lstData);
        return rs;
    }
    
    private CmsCommonPagination<PolicyClaimSearchResultDto> getListClaimPolicyExport(PersonalInsuranceSearchDto dto) {
        CmsCommonPagination<PolicyClaimSearchResultDto> rs = new CmsCommonPagination<>();
        PolicyClaimSearchParam param = new PolicyClaimSearchParam();
        param.agentCode = UserProfileUtils.getFaceMask();
		param.partner = dto.getPartner();
		param.regionCode = dto.getRegionCode();
		param.zoneCode = dto.getZoneCode();
		param.uoCode = dto.getUoCode();
		param.areaCodeDLVN = dto.getAreaCodeDLVN();
		param.regionCodeDLVN = dto.getRegionCodeDLVN();
		param.zoneCodeDLVN = dto.getZoneCodeDLVN();
		param.ilCode = dto.getIlCode();
		param.isCode = dto.getIsCode();
		param.smCode = dto.getSmCode();
		param.policyType = dto.getType();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(StringUtils.isNotEmpty(dto.getFromDate())){
			try {
				param.fromDate = sdf.parse(dto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(dto.getToDate())){
			try {
				param.toDate = sdf.parse(dto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		sqlManagerDb2Service.call(STORE_GET_LIST_CLAIM_POLICY_EXP, param);
        List<PolicyClaimSearchResultDto> lstData = param.datas;
        rs.setData(lstData);
        return rs;
    }
    
    @Override
    public ResponseEntity exportListPrersonalInsuranceDocuments(PersonalInsuranceSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            if (StringUtils.isEmpty(searchDto.getAgentCode())) {
                searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<PersonalInsuranceDto> common = getListDataExport(searchDto, "HSYCBH");

            String datePattern = datePattern = "dd/MM/yyyy";
            String templateName = "";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            templateName = "AD_HSYCBH.xlsx";
            ImportExcelUtil.setListColumnExcel(EnumExportListPrersonalInsurance.class, cols);
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A6";

            List<PersonalInsuranceDto> lstdata = common.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        PersonalInsuranceDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);

        }
        return res;
    }

    public ResponseEntity exportListPrersonalPolicy(PersonalInsuranceSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            if (StringUtils.isEmpty(searchDto.getAgentCode())) {
                searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<PersonalInsuranceDto> common = getListDataExport(searchDto, "HDBH");

            String datePattern = datePattern = "dd/MM/yyyy";
            String templateName = "";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            templateName = "AD_HDBH.xlsx";
            ImportExcelUtil.setListColumnExcel(EnumExportListPrersonalPolicy.class, cols);
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            logger.info("###exportListPrersonalPolicy### .templatePath:" + templatePath);
            String startRow = "A6";

            List<PersonalInsuranceDto> lstdata = common.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                // setDataHeader(xssfWorkbook, 0,"Tổng số HDBH: " + common.getTotalData(),agentInfo,mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        PersonalInsuranceDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);

        }
        return res;
    }
    
    //HD NGHIEP VU
  	@Override
  	public ObjectDataRes<PolicyRequestSearchResultDto> getListRequestPolicy(PersonalInsuranceSearchDto dto) {
  		dto.setFunctionCode("REQUEST_POLICY");
  		dto.setSearchType("ALL");

  		ObjectMapper mapper = new ObjectMapper();
  		String stringJsonParam ="";
  		try {
  			stringJsonParam = mapper.writeValueAsString(dto);
  		} catch (JsonProcessingException e) {
  			logger.error("Exception ", e);
  		}
  		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, dto.getFunctionCode());
  		PolicyRequestSearchParam param = new PolicyRequestSearchParam();
  		param.search=common.getSearch();
  		param.agentCode= dto.getAgentCode();
		param.partner = dto.getPartner();
		param.regionCode = dto.getRegionCode();
		param.zoneCode = dto.getZoneCode();
		param.uoCode = dto.getUoCode();
		param.areaCodeDLVN = dto.getAreaCodeDLVN();
		param.regionCodeDLVN = dto.getRegionCodeDLVN();
		param.zoneCodeDLVN = dto.getZoneCodeDLVN();
		param.ilCode = dto.getIlCode();
		param.isCode = dto.getIsCode();
		param.smCode = dto.getSmCode();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(StringUtils.isNotEmpty(dto.getFromDate())){
			try {
				param.fromDate = sdf.parse(dto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(dto.getToDate())){
			try {
				param.toDate = sdf.parse(dto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if ("1".equals(dto.getType())) {
  			param.policyType = "Chờ bổ sung thông tin";
  		} else if ("2".equals(dto.getType())) {
  			param.policyType = "Đã thực hiện";
  		} else if ("3".equals(dto.getType())) {
  			param.policyType = "Đang thực hiện";
  		} else if ("4".equals(dto.getType())) {
  			param.policyType = "Hẹn thực hiện";
  		} else if ("5".equals(dto.getType())) {
  			param.policyType = "Từ chối";
  		}
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.search = common.getSearch();
  		sqlManagerDb2Service.call(SP_GET_LIST_REQUEST_POLICY, param);
  		
  		List<PolicyRequestSearchResultDto> data = new ArrayList<>();
  		int total = 0;
  		if (CommonCollectionUtil.isNotEmpty(param.datas)) {
  			data = param.datas;
  			if (param.total != null) {
  				total = param.total;
  			}
  		}

  		ObjectDataRes<PolicyRequestSearchResultDto> resObj = new ObjectDataRes<>(total, data);
  		return resObj;
  	}
  	
  	@Override
  	public PolicyRequestSearchResultDto getDetailRequestPolicy(PolicyRequestSearchDto dto) {
  		String user = UserProfileUtils.getFaceMask();
  		PolicyRequestDetailParam paramDto = new PolicyRequestDetailParam();
        paramDto.agentCode = user;
        paramDto.policyNo = dto.getPolicyNo();
        paramDto.processTypeCode = dto.getProcesstypecode();
        sqlManagerDb2Service.call(SP_GET_DETAIL_REQUEST_POLICY, paramDto);
        if (paramDto.datas != null && paramDto.datas.size() > 0) {
            return paramDto.datas.get(0);
        }
        return null;
  	}
    
  	@Override
  	public ResponseEntity exportListRequestPolicy(PersonalInsuranceSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            if (StringUtils.isEmpty(searchDto.getAgentCode())) {
                searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<PolicyRequestSearchResultDto> common = getListRequestPolicyExport(searchDto);

            String datePattern = datePattern = "dd/MM/yyyy";
            String templateName = "";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            templateName = "AD_HD_Yeu_cau_dieu_chinh_thong_tin.xlsx";
            ImportExcelUtil.setListColumnExcel(EnumExportListRequestPolicy.class, cols);
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A6";

            List<PolicyRequestSearchResultDto> lstdata = common.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, null, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                		PolicyRequestSearchResultDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);

        }
        return res;
    }
  	
  	@Override
  	public ObjectDataRes<PolicyClaimSearchResultDto> getListClaimPolicy(PersonalInsuranceSearchDto dto) {
  		dto.setFunctionCode("CLAIM_POLICY");
  		dto.setSearchType("ALL");

  		ObjectMapper mapper = new ObjectMapper();
  		String stringJsonParam ="";
  		try {
  			stringJsonParam = mapper.writeValueAsString(dto);
  		} catch (JsonProcessingException e) {
  			logger.error("Exception ", e);
  		}
  		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, dto.getFunctionCode());
  		PolicyClaimSearchParam param = new PolicyClaimSearchParam();
  		param.search=common.getSearch();
  		param.agentCode= dto.getAgentCode();
		param.partner = dto.getPartner();
		param.regionCode = dto.getRegionCode();
		param.zoneCode = dto.getZoneCode();
		param.uoCode = dto.getUoCode();
		param.areaCodeDLVN = dto.getAreaCodeDLVN();
		param.regionCodeDLVN = dto.getRegionCodeDLVN();
		param.zoneCodeDLVN = dto.getZoneCodeDLVN();
		param.ilCode = dto.getIlCode();
		param.isCode = dto.getIsCode();
		param.smCode = dto.getSmCode();
		param.policyType = dto.getType();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(StringUtils.isNotEmpty(dto.getFromDate())){
			try {
				param.fromDate = sdf.parse(dto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(dto.getToDate())){
			try {
				param.toDate = sdf.parse(dto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.search = common.getSearch();
  		sqlManagerDb2Service.call(SP_GET_LIST_CLAIM_POLICY, param);
  		
  		List<PolicyClaimSearchResultDto> data = new ArrayList<>();
  		int total = 0;
  		if (CommonCollectionUtil.isNotEmpty(param.datas)) {
  			data = param.datas;
  			if (param.total != null) {
  				total = param.total;
  			}
  		}

  		ObjectDataRes<PolicyClaimSearchResultDto> resObj = new ObjectDataRes<>(total, data);
  		return resObj;
  	}
    
  	@Override
  	public ResponseEntity exportListClaimPolicy(PersonalInsuranceSearchDto searchDto) {
        ResponseEntity res = null;
        try {
            if (StringUtils.isEmpty(searchDto.getAgentCode())) {
                searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<PolicyClaimSearchResultDto> common = getListClaimPolicyExport(searchDto);

            String datePattern = datePattern = "dd/MM/yyyy";
            String templateName = "";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            templateName = "AD_Ho_So_Yeucauboithuong.xlsx";
            ImportExcelUtil.setListColumnExcel(EnumExportListClaimPolicy.class, cols);
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A6";

            List<PolicyClaimSearchResultDto> lstdata = common.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, null, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                		PolicyClaimSearchResultDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);

        }
        return res;
    }
  	
    @Override
    public CmsCommonPagination<PolicyFeeCardPersonalDto> getListFeePolicy(PersonalInsuranceSearchDto searchDto) {
        CmsCommonPagination<PolicyFeeCardPersonalDto> rs = new CmsCommonPagination<>();
        try {
	        String user = UserProfileUtils.getFaceMask();
	        if (StringUtils.isEmpty(user)) {
	            user = searchDto.getAgentCode();
	        }
	        searchDto.setFunctionCode("ADP_PERSONAL_POLICY");
	        ObjectMapper mapper = new ObjectMapper();
	        String stringJsonParam =  mapper.writeValueAsString(searchDto);
	        
	        CommonSearchWithPagingDto common;
	        common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());
	
	        PolicyFeeCardPersonalParamDto paramDto = new PolicyFeeCardPersonalParamDto();
	        paramDto.page = searchDto.getPage();
	        paramDto.pageSize = searchDto.getPageSize();

	        paramDto.agentCode = user;
	        paramDto.partner = searchDto.getPartner();
	        paramDto.regionCode = searchDto.getRegionCode();
	        paramDto.zoneCode = searchDto.getZoneCode();
	        paramDto.uoCode = searchDto.getUoCode();
	        
	        paramDto.areaDlvnCode = searchDto.getAreaCodeDLVN();
	        paramDto.regionDlvnCode = searchDto.getRegionCodeDLVN();
	        paramDto.zoneDlvnCode = searchDto.getZoneCodeDLVN();
	        
	        paramDto.ilCode = searchDto.getIlCode();
	        paramDto.isCode = searchDto.getIsCode();
	        paramDto.smCode = searchDto.getSmCode();
	        
	    	paramDto.policyType = searchDto.getType();	
	        
	        paramDto.searchStr = common.getSearch();
	        paramDto.sort = common.getSort();
	        
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        if(StringUtils.isNotEmpty(searchDto.getFromDate())){			
	        	paramDto.fromDate = sdf.parse(searchDto.getFromDate());
			}
			if(StringUtils.isNotEmpty(searchDto.getToDate())){				
				paramDto.toDate = sdf.parse(searchDto.getToDate());
			}
	        
			int reportType = Integer.parseInt(searchDto.getType());
			
			String procedureName = getProcedureNameByReportType(false, reportType);
	        sqlManagerDb2Service.call(procedureName, paramDto);
	        
	        rs.setTotalData(paramDto.totalRows);
	        rs.setData(paramDto.lstData);	      
        
        } catch (Exception e) {
            logger.error("###getListFeePolicy###", e);
        }
        return rs;
    }
    
    @Override
    public ResponseEntity exportListFeePolicy(PersonalInsuranceSearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
            if (StringUtils.isEmpty(searchDto.getAgentCode())) {
                searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            
            int reportType = Integer.parseInt(searchDto.getType());
            
            CmsCommonPagination<PolicyFeeDetailPersonalDto> common = getPolicyFeeDetailDataExport(searchDto, reportType);

            String datePattern = datePattern = "dd/MM/yyyy";
            String templateName = "";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            
            switch(reportType) {
    			case 1 : 
    				templateName = "AD_HD_Phat_sinh_phi.xlsx";
    				ImportExcelUtil.setListColumnExcel(EnumExportListFeePolicy.class, cols);
    				break;
    			case 2 : 
    				templateName = "AD_HD_Moi.xlsx";
    				ImportExcelUtil.setListColumnExcel(EnumExportListNewPolicy.class, cols);
    				break;
    			case 3 : 
    				templateName = "AD_HD_Tai_Tuc.xlsx";
    				ImportExcelUtil.setListColumnExcel(EnumExportListRenewPolicy.class, cols);
    				break;
    			case 4 : 
    				templateName = "AD_HD_Huy.xlsx";
    				ImportExcelUtil.setListColumnExcel(EnumExportListCancelPolicy.class, cols);
    				break;
        	}        
            
            logger.info("###exportListFeePolicy### .templateName:" + templateName);
            
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            
            String startRow = "A5";

            List<PolicyFeeDetailPersonalDto> lstdata = common.getData();
            
            logger.info("###exportListFeePolicy### .lstdata:" + lstdata.size());
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, null, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                logger.info("###exportListFeePolicy### .path" + path);
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                		PolicyFeeDetailPersonalDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            
        } catch (Exception e) {
            logger.error("###exportListFeePolicy###", e);
        }
        return res;
    }
    
    @Override
    public CmsCommonPagination<DuePolicyCardPersonalDto> getListDuePolicy(PersonalInsuranceSearchDto searchDto) {
        CmsCommonPagination<DuePolicyCardPersonalDto> rs = new CmsCommonPagination<>();
        try {
	        String user = UserProfileUtils.getFaceMask();
	        if (StringUtils.isEmpty(user)) {
	            user = searchDto.getAgentCode();
	        }
	        searchDto.setFunctionCode("ADP_PERSONAL_POLICY");
	        ObjectMapper mapper = new ObjectMapper();
	        String stringJsonParam =  mapper.writeValueAsString(searchDto);
	        
	        CommonSearchWithPagingDto common;
	        common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());
	
	        DuePolicyCardPersonalParamDto paramDto = new DuePolicyCardPersonalParamDto();
	        paramDto.page = searchDto.getPage();
	        paramDto.pageSize = searchDto.getPageSize();

	        paramDto.agentCode = user;
	        paramDto.partner = searchDto.getPartner();
	        paramDto.regionCode = searchDto.getRegionCode();
	        paramDto.zoneCode = searchDto.getZoneCode();
	        paramDto.uoCode = searchDto.getUoCode();
	        
	        paramDto.areaDlvnCode = searchDto.getAreaCodeDLVN();
	        paramDto.regionDlvnCode = searchDto.getRegionCodeDLVN();
	        paramDto.zoneDlvnCode = searchDto.getZoneCodeDLVN();
	        
	        paramDto.ilCode = searchDto.getIlCode();
	        paramDto.isCode = searchDto.getIsCode();
	        paramDto.smCode = searchDto.getSmCode();
	    	paramDto.policyType = searchDto.getType();	
	    	
	        paramDto.sort = common.getSort();
	        paramDto.searchStr = common.getSearch();
	    	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        if(StringUtils.isNotEmpty(searchDto.getFromDate())){			
	        	paramDto.fromDate = sdf.parse(searchDto.getFromDate());
			}
			if(StringUtils.isNotEmpty(searchDto.getToDate())){				
				paramDto.toDate = sdf.parse(searchDto.getToDate());
			}
	        
	        sqlManagerDb2Service.call(STORE_GET_LIST_DUE_POLICY, paramDto);
	        
	        rs.setTotalData(paramDto.totalRows);
	        rs.setData(paramDto.lstData);	      
        
        } catch (Exception e) {
            logger.error("###getListDuePolicy###", e);
        }
        return rs;
    }
    
    @Override
    public ResponseEntity exportListDuePolicy(PersonalInsuranceSearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
            if (StringUtils.isEmpty(searchDto.getAgentCode())) {
                searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            
            CmsCommonPagination<DuePolicyDetailPersonalDto> common = getDuePolicyDetailDataExport(searchDto);

            String datePattern = datePattern = "dd/MM/yyyy";
            String templateName = "AD_DS_Nhac_thu_phi_tai_tuc.xlsx";
            String title = null;
            if ("2".equals(searchDto.getType())) {
            	templateName = "AD_DS_Den_han_T16_den_T90.xlsx";
            	title = "DANH SÁCH ĐẾN HẠN T-16 ĐẾN T-90";
            }
            List<ItemColsExcelDto> cols = new ArrayList<>();
    		ImportExcelUtil.setListColumnExcel(EnumExportListDuePolicy.class, cols);
            
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            
            String startRow = "A5";

            List<DuePolicyDetailPersonalDto> lstdata = common.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, title, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                logger.info("###exportListDuePolicy### .path" + path);
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                		DuePolicyDetailPersonalDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            
        } catch (Exception e) {
            logger.error("###exportListDuePolicy###", e);
        }
        return res;
    }
    
   
    public CmsCommonPagination<ItemDto> getListPartner() {
        PartnerParamDto paramDto = new PartnerParamDto();
        paramDto.agentCode = UserProfileUtils.getFaceMask();
        sqlManagerDb2Service.call(STORE_GET_LIST_PARTNER, paramDto);
        
        CmsCommonPagination<ItemDto> rs = new CmsCommonPagination<>();
        rs.setData(paramDto.lstData);
        return rs;
    }

    public CmsCommonPagination<ItemDto> getListItemCombobox(String parentCode, String itemName) {
        ItemParamDto paramDto = new ItemParamDto();
        paramDto.agentCode = UserProfileUtils.getFaceMask();
        paramDto.parentCode = parentCode;
        paramDto.itemName = itemName;
        sqlManagerDb2Service.call(STORE_GET_LIST_ITEM, paramDto);
        
        CmsCommonPagination<ItemDto> rs = new CmsCommonPagination<>();
        rs.setData(paramDto.lstData);
        return rs;
    }

    private void setDataHeader(XSSFWorkbook xssfWorkbook, int sheet, String title, String reportDate, Map<String, CellStyle> mapColStyle) {
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheet);

        CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
        CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();
        CellStyle no = xssfSheet.getWorkbook().createCellStyle();

        Font fontNo = xssfWorkbook.createFont();
        fontNo.setFontName("Times New Roman");
        fontNo.setFontHeightInPoints((short) 11);
        no.setFont(fontNo);
        no.setAlignment(HorizontalAlignment.CENTER);
        no.setVerticalAlignment(VerticalAlignment.CENTER);
        no.setBorderBottom(BorderStyle.THIN);
        no.setBorderTop(BorderStyle.THIN);
        no.setBorderRight(BorderStyle.THIN);
        no.setBorderLeft(BorderStyle.THIN);

        Font fontTitleDate = xssfWorkbook.createFont();
        fontTitleDate.setFontName("Times New Roman");
        titleStyleDate.setFont(fontTitleDate);

        mapColStyle.put("NO", no);
        if (StringUtils.isNotEmpty(title)) {
        	if (xssfSheet.getRow(0) != null)
                xssfSheet.getRow(0).getCell(0).setCellValue(title);
            else
                xssfSheet.createRow(0).createCell(0).setCellValue(title);
        }
        if (xssfSheet.getRow(1) != null)
            xssfSheet.getRow(1).getCell(0).setCellValue(reportDate);
        else
            xssfSheet.createRow(1).createCell(0).setCellValue(reportDate);

        xssfSheet.getRow(1).getCell(0).setCellStyle(titleStyleDate);
    }

    @Override
    public CmsCommonPagination<PolicyDeliveryDto> getListPolicyDelivery(PolicyDeliverySearchDto searchDto, boolean isExport) {
        CmsCommonPagination<PolicyDeliveryDto> rs = new CmsCommonPagination<>();

        String user = UserProfileUtils.getFaceMask();
        if (StringUtils.isEmpty(user)) {
            user = searchDto.getAgentCode();
        }

        if ("1".equals(searchDto.getType()) || "2".equals(searchDto.getType())) {
        	searchDto.setFunctionCode("ADP_POLICY_DELIVERY");	
        } else {
        	searchDto.setFunctionCode("ADP_POLICY_TRANFERED");
        }
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam = "";
        try {
            stringJsonParam = mapper.writeValueAsString(searchDto);
        } catch (JsonProcessingException e) {
            logger.error("Exception during JSON conversion: ", e);
        }

        CommonSearchWithPagingDto common;
        common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());

        // Initialize paramDto
        PolicyDeliveryParamDto paramDto = new PolicyDeliveryParamDto();
        PolicyDeliveryParamCustomDto paramDtoCustom = new PolicyDeliveryParamCustomDto();

        try {
            // Handle type-based logic
            if ("1".equals(searchDto.getType()) || "2".equals(searchDto.getType())) {
                populateParamDto(paramDto, searchDto, common, user);
                paramDto.search = common.getSearch();
                if ("1".equals(searchDto.getType())) {
                	if (isExport) {
                		sqlManagerDb2Service.call(STORE_GET_LIST_POLICY_DELIVERY_EXP, paramDto);
                	} else {
                		sqlManagerDb2Service.call(STORE_GET_LIST_POLICY_DELIVERY, paramDto);
                	}
                } else {
                	if (isExport) {
                		sqlManagerDb2Service.call(STORE_GET_LIST_POLICY_CONFIRMED_EXP, paramDto);	
                	} else {
                		sqlManagerDb2Service.call(STORE_GET_LIST_POLICY_CONFIRMED, paramDto);
                	}
                }

                // Set result based on paramDto for types 1 and 2
                rs.setTotalData(paramDto.totalRows != null ? paramDto.totalRows : 0);
                rs.setData(paramDto.lstData != null ? paramDto.lstData : new ArrayList<>());

            } else {
            	populateParamDtoCustom(paramDtoCustom, searchDto, common, user);
            	paramDtoCustom.search = common.getSearch();
            	if (isExport) {
            		sqlManagerDb2Service.call(STORE_GET_LIST_POLICY_TRANSFERRED_EXP, paramDtoCustom);
            	} else {
            		sqlManagerDb2Service.call(STORE_GET_LIST_POLICY_TRANSFERRED, paramDtoCustom);
            	}

                // Set result based on paramDtoCustom for other types
                rs.setTotalData(paramDtoCustom.totalRows != null ? paramDtoCustom.totalRows : 0);
                rs.setData(paramDtoCustom.lstData != null ? paramDtoCustom.lstData : new ArrayList<>());
            }

        } catch (Exception e) {
            logger.error("getListPolicyDelivery error: ", e);
            rs.setTotalData(0);
            rs.setData(new ArrayList<>());
        }

        return rs;
    }

    private void populateParamDto(PolicyDeliveryParamDto paramDto, PolicyDeliverySearchDto searchDto,
                                  CommonSearchWithPagingDto common, String user) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(StringUtils.isNotEmpty(searchDto.getFromDate())){			
        	try {
				paramDto.fromDate = sdf.parse(searchDto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(searchDto.getToDate())){
			try {
				paramDto.toDate = sdf.parse(searchDto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        paramDto.page = searchDto.getPage();
        paramDto.size = searchDto.getPageSize();
        paramDto.search = common.getSearch();
        paramDto.sort = common.getSort();
        paramDto.agentCode = user;
        paramDto.partner = searchDto.getPartner();
        paramDto.uoCode = (searchDto.getUoCode() != null && !searchDto.getUoCode().isEmpty())
                ? searchDto.getUoCode() : "";
    }

    private void populateParamDtoCustom(PolicyDeliveryParamCustomDto paramDtoCustom, PolicyDeliverySearchDto searchDto,
                                        CommonSearchWithPagingDto common, String user) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(StringUtils.isNotEmpty(searchDto.getFromDate())){
        	try {
        		paramDtoCustom.fromDate = sdf.parse(searchDto.getFromDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(searchDto.getToDate())){				
			try {
				paramDtoCustom.toDate = sdf.parse(searchDto.getToDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        paramDtoCustom.page = searchDto.getPage();
        paramDtoCustom.size = searchDto.getPageSize();
        paramDtoCustom.search = common.getSearch();
        paramDtoCustom.sort = common.getSort();
        paramDtoCustom.agentCode = user;
        paramDtoCustom.partner = searchDto.getPartner();
        paramDtoCustom.uoCode = (searchDto.getUoCode() != null && !searchDto.getUoCode().isEmpty())
                ? searchDto.getUoCode() : "";
        paramDtoCustom.regionCode = searchDto.getRegionCode();
        paramDtoCustom.zoneCode = searchDto.getZoneCode();
    }


    @Override
    public ResponseEntity exportListPolicyDelivery(PolicyDeliverySearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
            searchDto.setPage(null);
            searchDto.setPageSize(null);
            CmsCommonPagination<PolicyDeliveryDto> policys = getListPolicyDelivery(searchDto, true);

            String datePattern = "dd/MM/yyyy";
            String templateName = "";

            List<ItemColsExcelDto> cols = new ArrayList<>();
            if ("1".equals(searchDto.getType())) {
                ImportExcelUtil.setListColumnExcel(EnumExportListPolicyDelivery.class, cols);
                templateName = "AD_Quan_ly_buu_kien_hop_dong.xlsx";
            } else if ("2".equals(searchDto.getType())) {
                ImportExcelUtil.setListColumnExcel(EnumExportListConfirmed.class, cols);
                templateName = "AD_DLBH_Xac_nhan_nhan_hop_dong.xlsx";
            } else {
                ImportExcelUtil.setListColumnExcel(EnumExportListTransferred.class, cols);
                templateName = "AD_Danh_Sach_Giao_Nhan_Hop_dong.xlsx";
            }
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A5";

            List<PolicyDeliveryDto> lstdata = policys.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = new HashMap<>();
            Map<String, Object> setMapColDefaultValue = null;
            
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, null, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        PolicyDeliveryDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }

    @Override
    public List<PolicyDeliveryDetailsDto> getDetailPolicyDelivery(PolicyDeliveryDetailsSearchDto searchDto) {
        PolicyDeliveryParamDetailsDto param = new PolicyDeliveryParamDetailsDto();
        List<PolicyDeliveryDetailsDto> data = new ArrayList<>();
        try {
            param.agentCode = UserProfileUtils.getFaceMask();
            param.policyNo = searchDto.getPolicyNo();

            if ("1".equals(searchDto.getType())) {
                sqlManagerDb2Service.call(STORE_GET_DETAIL_POLICY_DELIVERY, param);
            } else if ("2".equals(searchDto.getType())) {
                sqlManagerDb2Service.call(STORE_GET_DETAIL_POLICY_CONFIRMED, param);
            } else if ("3".equals(searchDto.getType())) {
                sqlManagerDb2Service.call(STORE_GET_DETAIL_POLICY_TRANFERED, param);
            } else {
                // Handle default or error case if necessary
                logger.error("Invalid type provided: " + searchDto.getType());
            }

            data = param.datas;
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return data;
    }

    @Override
    public CmsCommonPagination<TrackingReportDto> getTrackingReport(TrackingReportSearchDto searchDto) {
        CmsCommonPagination<TrackingReportDto> rs = new CmsCommonPagination<>();

        // Lấy thông tin người dùng hoặc mã đại lý
        String user = UserProfileUtils.getFaceMask();
        if (StringUtils.isEmpty(user)) {
            user = searchDto.getAgentCode();
        }

        // Khởi tạo paramDto và chuyển đổi type thành Integer
        TrackingReportParamDto paramDto = new TrackingReportParamDto();
        paramDto.agentCode = UserProfileUtils.getFaceMask();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(StringUtils.isNotEmpty(searchDto.getMonthYear())){
        	try {
				paramDto.fromDate = sdf.parse("01/" + searchDto.getMonthYear());
				Calendar lastDayOfMonth = Calendar.getInstance();
				lastDayOfMonth.setTime(paramDto.fromDate);
				lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
				paramDto.toDate = lastDayOfMonth.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        paramDto.reportType = searchDto.getType();
        sqlManagerDb2Service.call(STORE_GET_GET_REPORT_TRACKING, paramDto);

        rs.setData(paramDto.lstData);
        return rs;
    }

    private CmsCommonPagination<TrackingDetailReportDto> getTrackingDetailReport(TrackingReportSearchDto searchDto) {
        CmsCommonPagination<TrackingDetailReportDto> rs = new CmsCommonPagination<>();

        // Lấy thông tin người dùng hoặc mã đại lý
        String user = UserProfileUtils.getFaceMask();
        if (StringUtils.isEmpty(user)) {
            user = searchDto.getAgentCode();
        }

        // Khởi tạo paramDto và chuyển đổi type thành Integer
        TrackingDetailReportParamDto paramDto = new TrackingDetailReportParamDto();
        paramDto.agentCode = UserProfileUtils.getFaceMask();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(StringUtils.isNotEmpty(searchDto.getMonthYear())){
        	try {
				paramDto.fromDate = sdf.parse("01/" + searchDto.getMonthYear());
				Calendar lastDayOfMonth = Calendar.getInstance();
				lastDayOfMonth.setTime(paramDto.fromDate);
				lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
				paramDto.toDate = lastDayOfMonth.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        paramDto.reportType = searchDto.getType();
        sqlManagerDb2Service.call(STORE_GET_GET_REPORT_TRACKING_DETAIL, paramDto);

        rs.setData(paramDto.lstData);
        return rs;
    }
    
    @Override
    public ResponseEntity exportTrackingReport(TrackingReportSearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
            CmsCommonPagination<TrackingReportDto> policys = getTrackingReport(searchDto);
            CmsCommonPagination<TrackingDetailReportDto> details = getTrackingDetailReport(searchDto);
            
            String datePattern = "dd/MM/yyyy HH:mm";
            String templateName;
            List<ItemColsExcelDto> cols = new ArrayList<>();
            if ("1".equals(searchDto.getType())) {
            	templateName = "AD_Bao_cao_Tracking_theo_doi_tac.xlsx";
            	ImportExcelUtil.setListColumnExcel(EnumExportPartnerTrackingReport.class, cols);
            } else {
            	templateName = "AD_Bao_cao_Tracking_theo_khu_vuc_DLVN.xlsx";
            	ImportExcelUtil.setListColumnExcel(EnumExportAreaTrackingReport.class, cols);
            }
            
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A6";

            List<TrackingReportDto> lstdata = policys.getData();
            List<TrackingDetailReportDto> lstDetail = details.getData();
            List<ItemColsExcelDto> detailCols = new ArrayList<>();
            ImportExcelUtil.setListColumnExcel(EnumExportTrackingDetailReport.class, detailCols);
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = new HashMap<>();// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, null, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                setDataHeader(xssfWorkbook, 1, null, "Tháng: " + searchDto.getMonthYear(), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                        TrackingReportDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
                
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 1, null, lstDetail,
                        TrackingDetailReportDto.class, detailCols, datePattern, "A4", mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }

    @Override
    public ContractSearchDueDateResultDto getDetailDueDateContract(String policyNo) {
        PolicyDueDateDetailParamDto param = new PolicyDueDateDetailParamDto();
        ContractSearchDueDateResultDto result = new ContractSearchDueDateResultDto();
        try {
            param.agentCode = UserProfileUtils.getFaceMask();
            param.policyNo = policyNo;

            sqlManagerDb2Service.call(STORE_GET_DETAIL_DUE_DATE_POLICY, param);

            if (param.datas.size() > 0) {
            	result = param.datas.get(0);
            }
        } catch (Exception e) {
            logger.error("getDetailDueDateContract has error: ", e);
        }
        return result;
    }
    
    @Override
    public CmsCommonPagination<AgentInfoSearchResultDto> getAgentInfo(AgentInfoSearchDto searchDto) {
    	AgentInfoSearchParamDto param = new AgentInfoSearchParamDto();
    	CmsCommonPagination<AgentInfoSearchResultDto> rs = new CmsCommonPagination<>();
        try {
        	if ("1".equals(searchDto.getType())) {
        		searchDto.setFunctionCode("ADP_AGENT_INFO");
            } else {
            	searchDto.setFunctionCode("ADP_UP_DOWN_AGENT");
            } 

      		ObjectMapper mapper = new ObjectMapper();
      		String stringJsonParam ="";
      		try {
      			stringJsonParam = mapper.writeValueAsString(searchDto);
      		} catch (JsonProcessingException e) {
      			logger.error("Exception ", e);
      		}
      		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());
            param.agentCode = UserProfileUtils.getFaceMask();
            param.search = common.getSearch();
            if ("1".equals(searchDto.getType())) {
            	sqlManagerDb2Service.call(STORE_GET_AGENT_INFO, param);
            } else if ("2".equals(searchDto.getType())) {
            	sqlManagerDb2Service.call(STORE_GET_LIST_UP_AGENT_INFO, param);
            } else if ("3".equals(searchDto.getType())) {
            	sqlManagerDb2Service.call(STORE_GET_LIST_DOWN_AGENT_INFO, param);
            }

            rs.setData(param.datas);
        } catch (Exception e) {
            logger.error("getAgentInfo has error: ", e);
        }
        return rs;
    }
    
    @Override
    public ResponseEntity exportAgentInfo(AgentInfoSearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
            searchDto.setPage(null);
            searchDto.setPageSize(null);
            CmsCommonPagination<AgentInfoSearchResultDto> datas = getAgentInfo(searchDto);

            String datePattern = "dd/MM/yyyy";
            String templateName = "";

            List<ItemColsExcelDto> cols = new ArrayList<>();
            if ("1".equals(searchDto.getType())) {
            	ImportExcelUtil.setListColumnExcel(EnumExportAgentInfo.class, cols);
            	templateName = "AD_Thong_tin_noi_lam_viec.xlsx";
            } else if ("2".equals(searchDto.getType())) {
            	ImportExcelUtil.setListColumnExcel(EnumExportUpDownAgentInfo.class, cols);
            	templateName = "AD_Thong_tin_cap_tren.xlsx";
            } else if ("3".equals(searchDto.getType())) {
            	ImportExcelUtil.setListColumnExcel(EnumExportUpDownAgentInfo.class, cols);
                templateName = "AD_Thong_tin_cap_duoi.xlsx";
            }
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A4";

            List<AgentInfoSearchResultDto> lstdata = datas.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = new HashMap<>();
            Map<String, Object> setMapColDefaultValue = null;
            
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, null, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                		AgentInfoSearchResultDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }
    
    @Override
    public GeneralReportDto getGeneralReportInfo(GeneralReportSearchDto searchDto) {
        String user = UserProfileUtils.getFaceMask();
        if (StringUtils.isEmpty(user)) {
            user = searchDto.getAgentCode();
        }

        GeneralReportParamDto paramDto = new GeneralReportParamDto();
        paramDto.agentCode = user;
        paramDto.partner = searchDto.getPartner();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(StringUtils.isNotEmpty(searchDto.getMonthYear())){			
        	try {
				paramDto.fromDate = sdf.parse("01/" + searchDto.getMonthYear());
				Calendar lastDayOfMonth = Calendar.getInstance();
				lastDayOfMonth.setTime(paramDto.fromDate);
				lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
				paramDto.toDate = lastDayOfMonth.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        sqlManagerDb2Service.call(STORE_GET_REPORT_GENERAL, paramDto);
        if (paramDto.lstData.size() > 0) {
            return paramDto.lstData.get(0);
        }
        return new GeneralReportDto();
    }
    
    @Override
    public ReportK2K2PlusPagination<ReportK2K2PlusDto> getK2K2PlusReport(ReportK2K2PlusSearchDto searchDto, boolean isExport) {
    	ReportK2K2PlusPagination<ReportK2K2PlusDto> rs = new ReportK2K2PlusPagination<>();

        // Lấy thông tin người dùng hoặc mã đại lý
        String user = UserProfileUtils.getFaceMask();
        if (StringUtils.isEmpty(user)) {
            user = searchDto.getAgentCode();
        }

        if ("K2".equals(searchDto.getDataType())) {
        	searchDto.setFunctionCode("ADP_REPORT_K2");
    	} else if ("K2P".equals(searchDto.getDataType())) {
    		searchDto.setFunctionCode("ADP_REPORT_K2P");
    	} else {
    		searchDto.setFunctionCode("ADP_REPORT_PF");
    	}
        
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam = "";
        try {
            stringJsonParam = mapper.writeValueAsString(searchDto);
        } catch (JsonProcessingException e) {
            logger.error("Exception :", e);
        }

        CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());

        if (isExport) {
        	ReportK2K2PlusExportParamDto paramDto = new ReportK2K2PlusExportParamDto();
        	paramDto.agentCode = UserProfileUtils.getFaceMask();
            paramDto.partner = searchDto.getPartner();
            paramDto.regionCode = searchDto.getRegionCode();
            paramDto.zoneCode = searchDto.getZoneCode();
            paramDto.uoCode = searchDto.getUoCode();
            paramDto.areaCodeDLVN = searchDto.getAreaCodeDLVN();
            paramDto.regionCodeDLVN = searchDto.getRegionCodeDLVN();
            paramDto.zoneCodeDLVN = searchDto.getZoneCodeDLVN();
            paramDto.ilCode = searchDto.getIlCode();
            paramDto.isCode = searchDto.getIsCode();
            paramDto.smCode = searchDto.getSmCode();
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if(StringUtils.isNotEmpty(searchDto.getMonthYear())){			
            	try {
            		Date firstDay = sdf.parse("01/" + searchDto.getMonthYear());
    				SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
    				paramDto.dateKey = df.format(firstDay);
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}
            paramDto.dataType = searchDto.getDataType();
            paramDto.option = searchDto.getOption();
            paramDto.page = searchDto.getPage();
            paramDto.pageSize = searchDto.getPageSize();
            paramDto.search = common.getSearch();
            if ("PF".equals(searchDto.getDataType())) {
        		sqlManagerDb2Service.call(STORE_GET_REPORT_PF_EXP, paramDto);
        	} else {
        		sqlManagerDb2Service.call(STORE_GET_REPORT_K2_K2P_EXP, paramDto);
        	}
            rs.setData(paramDto.lstData);
        } else {
        	ReportK2K2PlusParamDto paramDto = new ReportK2K2PlusParamDto();
        	paramDto.agentCode = UserProfileUtils.getFaceMask();
            paramDto.partner = searchDto.getPartner();
            paramDto.regionCode = searchDto.getRegionCode();
            paramDto.zoneCode = searchDto.getZoneCode();
            paramDto.uoCode = searchDto.getUoCode();
            paramDto.areaCodeDLVN = searchDto.getAreaCodeDLVN();
            paramDto.regionCodeDLVN = searchDto.getRegionCodeDLVN();
            paramDto.zoneCodeDLVN = searchDto.getZoneCodeDLVN();
            paramDto.ilCode = searchDto.getIlCode();
            paramDto.isCode = searchDto.getIsCode();
            paramDto.smCode = searchDto.getSmCode();
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if(StringUtils.isNotEmpty(searchDto.getMonthYear())){			
            	try {
    				Date firstDay = sdf.parse("01/" + searchDto.getMonthYear());
    				SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
    				paramDto.dateKey = df.format(firstDay);
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}
            paramDto.dataType = searchDto.getDataType();
            paramDto.option = searchDto.getOption();
            paramDto.page = searchDto.getPage();
            paramDto.pageSize = searchDto.getPageSize();
            paramDto.search = common.getSearch();
            if ("PF".equals(searchDto.getDataType())) {
        		sqlManagerDb2Service.call(STORE_GET_REPORT_PF, paramDto);
        	} else {
        		sqlManagerDb2Service.call(STORE_GET_REPORT_K2_K2P, paramDto);
        	}
            rs.setData(paramDto.lstData);
            rs.setTotalData(paramDto.totalRows);
            rs.setTotalApp(paramDto.totalApp);
            rs.setTotalEpp(paramDto.totalEpp);
            rs.setTotalTp(paramDto.totalTp);
            rs.setTotalEp(paramDto.totalEp);
        }

        return rs;
    }
    
    @Override
    public ResponseEntity exportK2K2PlusReport(ReportK2K2PlusSearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
        	ReportK2K2PlusPagination<ReportK2K2PlusDto> policys = getK2K2PlusReport(searchDto, true);

            String datePattern = "dd/MM/yyyy";
            String templateName;
            List<ItemColsExcelDto> cols = new ArrayList<>();
            if ("K2".equals(searchDto.getDataType())) {
            	templateName = "AD_K2.xlsx";
            	ImportExcelUtil.setListColumnExcel(EnumExportK2K2plusReport.class, cols);
            } else if ("K2P".equals(searchDto.getDataType())) {
            	templateName = "AD_K2Plus.xlsx";
            	ImportExcelUtil.setListColumnExcel(EnumExportK2K2plusReport.class, cols);
            } else {
            	templateName = "AD_PF.xlsx";
            	ImportExcelUtil.setListColumnExcel(EnumExportPFReport.class, cols);
            }
            
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A6";

            List<ReportK2K2PlusDto> lstdata = policys.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = new HashMap<>();// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();
            mapColFormat.put("K2RATE", CommonConstant.PERCENT);
            
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, null, "Tháng / năm báo cáo: " + searchDto.getMonthYear(), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                		ReportK2K2PlusDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }
    
    @Override
    public AggregateReportDataRes getAggregateReport(AggregateReportSearchDto searchDto) {
  		AggregateReportParamDto param = new AggregateReportParamDto();
  		param.agentCode=UserProfileUtils.getFaceMask();
		param.areaCode = searchDto.getAreaCode();
		param.regionCode = searchDto.getRegionCode();
		param.zoneCode = searchDto.getZoneCode();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(StringUtils.isNotEmpty(searchDto.getMonthYear())){
        	try {
        		param.fromDate = sdf.parse("01/" + searchDto.getMonthYear());
				Calendar lastDayOfMonth = Calendar.getInstance();
				lastDayOfMonth.setTime(param.fromDate);
				lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
				param.toDate = lastDayOfMonth.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        if ("1".equals(searchDto.getType())) {
        	sqlManagerDb2Service.call(STORE_GET_AGGREGATE_REPORT_BY_PARTNER, param);	
        } else if ("2".equals(searchDto.getType())) {
        	sqlManagerDb2Service.call(STORE_GET_AGGREGATE_REPORT_BY_AREA, param);
        } else if ("3".equals(searchDto.getType())) {
        	sqlManagerDb2Service.call(STORE_GET_AGGREGATE_REPORT_BY_REGION, param);
        } else if ("4".equals(searchDto.getType())) {
        	sqlManagerDb2Service.call(STORE_GET_AGGREGATE_REPORT_BY_ZONE, param);
        }
  		
  		List<AggregateReportDto> lstData = new ArrayList<>();
  		if (CommonCollectionUtil.isNotEmpty(param.lstData)) {
  			lstData = param.lstData;
  		}

    	ArrayList<String> headers = new ArrayList<String>();
    	headers.add("STT");
    	headers.add("TIÊU CHÍ");
    	headers.add("Tổng số");
    	List<String[]> dataview = new ArrayList<String[]>();
    	List<Integer> totalNum = new ArrayList<Integer>();
    	List<BigDecimal> totalAmount = new ArrayList<BigDecimal>();
    	for (int i=0; i < 33; i++) {
    		String[] data = new String[lstData.size() + 1];
    		dataview.add(data);
    		if (i < 11) {
    			totalNum.add(0);
    		} else {
    			totalAmount.add(new BigDecimal(0));
    		}
    	}
    	totalAmount.add(new BigDecimal(0));
    	totalAmount.add(new BigDecimal(0));
    	
    	int index = 1;
    	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
    	for (AggregateReportDto item : lstData) {
    		headers.add(item.getPartner());
    		dataview.get(0)[index] = decimalFormat.format(item.getNumFeeLastMonth());	// HSYCBH - Tháng trước chuyển sang
    		dataview.get(1)[index] = decimalFormat.format(item.getNumFeeInMonth());		// HSYCBH - Trong tháng
    		dataview.get(2)[index] = decimalFormat.format(item.getNumFeeLastMonth() + item.getNumFeeInMonth());	// HSYCBH - Tổng HS đang xử lý
    		dataview.get(3)[index] = decimalFormat.format(item.getNumPenLastMonth());	// HS Pending - Tháng trước chuyển sang
    		dataview.get(4)[index] = decimalFormat.format(item.getNumPenInMonth());		// HS Pending - Trong tháng
    		dataview.get(5)[index] = decimalFormat.format(item.getNumPenLastMonth() + item.getNumPenInMonth());	// HS Pending - Tổng HS đang xử lý
    		dataview.get(6)[index] = decimalFormat.format(item.getNumRejLastMonth());	// HS Từ chối - Tháng trước chuyển sang
    		dataview.get(7)[index] = decimalFormat.format(item.getNumRejInMonth());		// HS Từ chối - Trong tháng
    		dataview.get(8)[index] = decimalFormat.format(item.getNumRejLastMonth() + item.getNumRejInMonth());	// HS Từ chối - Tổng HS đang xử lý
    		dataview.get(9)[index] = decimalFormat.format(item.getNumRelease());		// HĐ phát hành
    		dataview.get(10)[index] = decimalFormat.format(item.getNumCancel());		// HĐ hủy
    		dataview.get(11)[index] = decimalFormat.format(item.getIpcsLastMonth());	// IPCS thu được - Tháng trước chuyển sang
    		dataview.get(12)[index] = decimalFormat.format(item.getIpcsInMonth());		// IPCS thu được - Trong tháng
    		dataview.get(13)[index] = decimalFormat.format(item.getIpcsLastMonth().add(item.getIpcsInMonth()));	// HS Từ chối - Tổng HS đang xử lý
    		dataview.get(14)[index] = decimalFormat.format(item.getIpcsPending());	// IP_CS của HS pending
    		dataview.get(15)[index] = decimalFormat.format(item.getIpcsRejected());	// IP_CS của HS từ chối
    		dataview.get(16)[index] = decimalFormat.format(item.getIpcsRelease());	// 1. IP_CS phát hành
    		dataview.get(17)[index] = decimalFormat.format(item.getFypRelease());	// 2. FYP phát hành
    		dataview.get(18)[index] = decimalFormat.format(item.getIpcsReleaseYesterday());	// 3. IP_CS phát hành ngày hôm qua
    		dataview.get(19)[index] = decimalFormat.format(item.getFypReleaseYesterday());	// 4. FYP phát hành ngày hôm qua
    		dataview.get(20)[index] = decimalFormat.format(item.getRfyp());			// FYP Tái tục (RFYP)
    		dataview.get(21)[index] = decimalFormat.format(item.getIpcsCancel());	// 1. IP_CS HĐ hủy
    		dataview.get(22)[index] = decimalFormat.format(item.getFypCancel());	// 2. FYP HĐ hủy
    		dataview.get(23)[index] = decimalFormat.format(item.getFyp());	// TỔNG FYP
    		dataview.get(24)[index] = decimalFormat.format(item.getTp1());	// TP năm 1
    		dataview.get(25)[index] = decimalFormat.format(item.getEp1());	// EP năm 1
    		dataview.get(26)[index] = decimalFormat.format(item.getRyp());	// RYP
    		dataview.get(27)[index] = decimalFormat.format(item.getTp2());	// TP năm 2
    		dataview.get(28)[index] = decimalFormat.format(item.getEp2());	// EP năm 2
    		dataview.get(29)[index] = decimalFormat.format(item.getTp3());	// TP năm 3+ (Từ năm 3 trở đi)
    		dataview.get(30)[index] = decimalFormat.format(item.getEp3());	// EP năm 3+ (Từ năm 3 trở đi)
    		// % K2 (Tỷ lệ duy trì phí năm 2)
    		if (BigDecimal.ZERO.compareTo(item.getEpp()) != 0) {
        		dataview.get(31)[index] = String.valueOf(item.getApp().multiply(new BigDecimal(100)).divide(item.getEpp(), 2, RoundingMode.FLOOR)) + "%";
        	}
    		// % K2+ (Tỷ lệ duy trì phí từ năm 2 trở đi)
    		if (BigDecimal.ZERO.compareTo(item.getEpp2()) != 0) {
        		dataview.get(32)[index] = String.valueOf(item.getApp2().multiply(new BigDecimal(100)).divide(item.getEpp2(), 2, RoundingMode.FLOOR)) + "%";
        	}
    		
    		// Tính Tổng
    		totalNum.set(0, totalNum.get(0) + item.getNumFeeLastMonth());
    		totalNum.set(1, totalNum.get(1) + item.getNumFeeInMonth());
    		totalNum.set(2, totalNum.get(2) + item.getNumFeeLastMonth() + item.getNumFeeInMonth());
    		totalNum.set(3, totalNum.get(3) + item.getNumPenLastMonth());
    		totalNum.set(4, totalNum.get(4) + item.getNumPenInMonth());
    		totalNum.set(5, totalNum.get(5) + item.getNumPenLastMonth() + item.getNumPenInMonth());
    		totalNum.set(6, totalNum.get(6) + item.getNumRejLastMonth());
    		totalNum.set(7, totalNum.get(7) + item.getNumRejInMonth());
    		totalNum.set(8, totalNum.get(8) + item.getNumRejLastMonth() + item.getNumRejInMonth());
    		totalNum.set(9, totalNum.get(9) + item.getNumRelease());
    		totalNum.set(10, totalNum.get(10) + item.getNumCancel());
    		totalAmount.set(0, totalAmount.get(0).add(item.getIpcsLastMonth()));
    		totalAmount.set(1, totalAmount.get(1).add(item.getIpcsInMonth()));
    		totalAmount.set(2, totalAmount.get(2).add(item.getIpcsLastMonth()).add(item.getIpcsInMonth()));
    		totalAmount.set(3, totalAmount.get(3).add(item.getIpcsPending()));
    		totalAmount.set(4, totalAmount.get(4).add(item.getIpcsRejected()));
    		totalAmount.set(5, totalAmount.get(5).add(item.getIpcsRelease()));
    		totalAmount.set(6, totalAmount.get(6).add(item.getFypRelease()));
    		totalAmount.set(7, totalAmount.get(7).add(item.getIpcsReleaseYesterday()));
    		totalAmount.set(8, totalAmount.get(8).add(item.getFypReleaseYesterday()));
    		totalAmount.set(9, totalAmount.get(9).add(item.getRfyp()));
    		totalAmount.set(10, totalAmount.get(10).add(item.getIpcsCancel()));
    		totalAmount.set(11, totalAmount.get(11).add(item.getFypCancel()));
    		totalAmount.set(12, totalAmount.get(12).add(item.getFyp()));
    		totalAmount.set(13, totalAmount.get(13).add(item.getTp1()));
    		totalAmount.set(14, totalAmount.get(14).add(item.getEp1()));
    		totalAmount.set(15, totalAmount.get(15).add(item.getRyp()));
    		totalAmount.set(16, totalAmount.get(16).add(item.getTp2()));
    		totalAmount.set(17, totalAmount.get(17).add(item.getEp2()));
    		totalAmount.set(18, totalAmount.get(18).add(item.getTp3()));
    		totalAmount.set(19, totalAmount.get(19).add(item.getEp3()));
    		totalAmount.set(20, totalAmount.get(20).add(item.getEpp()));
    		totalAmount.set(21, totalAmount.get(21).add(item.getApp()));
    		totalAmount.set(22, totalAmount.get(22).add(item.getEpp2()));
    		totalAmount.set(23, totalAmount.get(23).add(item.getApp2()));
    		
    		index++;
    	}
    	// Edit giá trị cho cột Tổng
    	for (int i=0; i < 31; i++) {
    		if (i < 11) {
    			dataview.get(i)[0] = decimalFormat.format(totalNum.get(i));
    		} else {
    			dataview.get(i)[0] = decimalFormat.format(totalAmount.get(i-11));
    		}
    	}
    	if (BigDecimal.ZERO.compareTo(totalAmount.get(20)) != 0) {
    		dataview.get(31)[0] = String.valueOf(totalAmount.get(21).multiply(new BigDecimal(100)).divide(totalAmount.get(20), 2, RoundingMode.FLOOR)) + "%";
    	}
    	if (BigDecimal.ZERO.compareTo(totalAmount.get(22)) != 0) {
    		dataview.get(32)[0] = String.valueOf(totalAmount.get(23).multiply(new BigDecimal(100)).divide(totalAmount.get(22), 2, RoundingMode.FLOOR)) + "%";
    	}
    	
    	List<AggregateReportRowDetailDto> dataRows = new ArrayList<AggregateReportRowDetailDto>();
    	AggregateReportRowDetailDto dataRow = new AggregateReportRowDetailDto();
    	dataRow.setCategory("I. Hồ sơ yêu cầu/Hợp đồng bảo hiểm");
    	dataRow.setColspan(headers.size() + 1);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("1");
    	dataRow.setCategory("HSYCBH");
    	dataRow.setDetails(new String[] {"Tháng trước chuyển sang","Trong tháng","Tổng HS đang xử lý"});
    	String[][] data = new String[][] {dataview.get(0), dataview.get(1), dataview.get(2)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("2");
    	dataRow.setCategory("HS pending");
    	dataRow.setDetails(new String[] {"Tháng trước chuyển sang","Trong tháng","Tổng HS đang xử lý"});
    	data = new String[][] {dataview.get(3), dataview.get(4), dataview.get(5)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("3");
    	dataRow.setCategory("HS từ chối");
    	dataRow.setDetails(new String[] {"Tháng trước chuyển sang","Trong tháng","Tổng HS đang xử lý"});
    	data = new String[][] {dataview.get(6), dataview.get(7), dataview.get(8)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("4");
    	dataRow.setCategory("HĐ phát hành");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(9)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("5");
    	dataRow.setCategory("HĐ hủy");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(10)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setCategory("II. Doanh thu");
    	dataRow.setColspan(headers.size() + 1);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("6");
    	dataRow.setCategory("IP_CS thu được");
    	dataRow.setDetails(new String[] {"Tháng trước chuyển sang","Trong tháng","Tổng HS đang xử lý"});
    	data = new String[][] {dataview.get(11), dataview.get(12), dataview.get(13)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("7");
    	dataRow.setCategory("IP_CS của HS pending");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(14)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("8");
    	dataRow.setCategory("IP_CS của HS từ chối");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(15)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);

    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("9");
    	dataRow.setCategory("1. IP_CS phát hành");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(16)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("9");
    	dataRow.setCategory("2. FYP phát hành");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(17)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("9");
    	dataRow.setCategory("3. IP_CS phát hành ngày hôm qua");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(18)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("9");
    	dataRow.setCategory("4. FYP phát hành ngày hôm qua");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(19)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("10");
    	dataRow.setCategory("FYP tái tục (RFYP)");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(20)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("11");
    	dataRow.setCategory("1. IP_CS HĐ hủy");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(21)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("11");
    	dataRow.setCategory("2. FYP HĐ hủy");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(22)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("12");
    	dataRow.setCategory("TỔNG FYP (9.2)+(10)+(11.2)");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(23)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("12");
    	dataRow.setCategory("    TP năm 1");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(24)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("12");
    	dataRow.setCategory("    EP năm 1");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(25)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("13");
    	dataRow.setCategory("RYP");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(26)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("13");
    	dataRow.setCategory("    TP năm 2");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(27)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("13");
    	dataRow.setCategory("    EP năm 2");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(28)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("13");
    	dataRow.setCategory("    TP năm 3+ (Từ năm 3 trở đi)");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(29)};
    	data[0][1] = "0";
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("13");
    	dataRow.setCategory("    EP năm 3+ (Từ năm 3 trở đi)");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(30)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("14");
    	dataRow.setCategory("% K2 (Tỷ lệ duy trì phí năm 2)");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(31)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);
    	
    	dataRow = new AggregateReportRowDetailDto();
    	dataRow.setId("15");
    	dataRow.setCategory("% K2+ (Tỷ lệ duy trì phí từ năm 2 trở đi)");
    	dataRow.setColspan(2);
    	data = new String[][] {dataview.get(32)};
    	dataRow.setDatas(data);
    	dataRows.add(dataRow);        	
    	
    	AggregateReportDataRes resObj = new AggregateReportDataRes();
    	resObj.setHeaders(headers);
    	resObj.setDataRows(dataRows);
    	dataOutput.put(UserProfileUtils.getFaceMask(), resObj);
    	
  		return resObj;
    }
    
    @Override
    public ResponseEntity exportAggregateReport(AggregateReportSearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
            AggregateReportDataRes datas = dataOutput.get(UserProfileUtils.getFaceMask());

            String datePattern = "dd/MM/yyyy HH:mm";
            String template = "AD_Bao_cao_ket_qua_kinh_tong_hop.xlsx";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
            String type = "doi_tac";
            if ("2".equals(searchDto.getType())) {
            	type = "khu_vuc";
            } else if ("3".equals(searchDto.getType())) {
            	type = "vung";
            } else if ("4".equals(searchDto.getType())) {
            	type = "cum";
            }
            
            String templateName = template.replace(CommonConstant.TYPE_EXCEL, "") + "_" + type + CommonConstant.TYPE_EXCEL;
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataAggregateReport(xssfWorkbook, mapColStyle, datas, searchDto.getType());
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDate = formatDateExport.format(new Date());
                xssfWorkbook.write(out);
                //update to service
        		
                String pathFile = Paths.get(path,CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
                		templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString();
                
                File file = new File(pathFile);
                try (OutputStream os = new FileOutputStream(file)) {
                    xssfWorkbook.write(os);
                }
                
                String pathOut = (File.separator + Paths.get(CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN
                		, templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString()).replace("\\", "/");
                
                ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());               
                HttpHeaders headers = new HttpHeaders();
                headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
                        + currentDate + CommonConstant.TYPE_EXCEL + "\""+";path="+pathOut);
                
                headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);
                
                 return ResponseEntity
                            .ok()
                            .eTag(pathOut)
                            .headers(headers)
                            .contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
                            .body(new InputStreamResource(in));
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }
    
    @Override
    public AgentInfoSaleSopDto getAgentInfoSaleSop() {
    	AgentInfoSaleSopParamDto param = new AgentInfoSaleSopParamDto();
        try {
            param.agentCode = UserProfileUtils.getFaceMask();

            sqlManagerDb2Service.call(AD_SP_GET_AGENT_INFO_SALE_SOP, param);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        if (param.datas.size() > 0) {
        	return param.datas.get(0);
        } else {
        	return null;
        }
    }
    
    private void setDataAggregateReport(XSSFWorkbook xssfWorkbook, Map<String, CellStyle> mapColStyle, AggregateReportDataRes datas, String type) {
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		// set col title
		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short) 20);
		titleStyle.setFont(fontTitle);

		String titleName = "BÁO CÁO KẾT QUẢ KINH DOANH TỔNG HỢP THEO ĐỐI TÁC";
		if ("2".equals(type)) {
			titleName = "BÁO CÁO KẾT QUẢ KINH DOANH TỔNG HỢP THEO KHU VỰC";
		} else if ("3".equals(type)) {
			titleName = "BÁO CÁO KẾT QUẢ KINH DOANH TỔNG HỢP THEO VÙNG";
		} else if ("4".equals(type)) {
			titleName = "BÁO CÁO KẾT QUẢ KINH DOANH TỔNG HỢP THEO CỤM";
		}
		if (xssfSheet.getRow(0) != null) xssfSheet.getRow(0).getCell(0).setCellValue(titleName);
		else xssfSheet.createRow(0).createCell(0).setCellValue(titleName);
		xssfSheet.getRow(0).getCell(0).setCellStyle(titleStyle);

		//set title table
		Font font = xssfWorkbook.createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		
		CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Times New Roman", true, "");

		// Header
		int colIdx = 0;
		int rowIdx = 3;
		XSSFRow row = xssfSheet.getRow(rowIdx);
		if (row == null) {
			row = xssfSheet.createRow(rowIdx);
		}
		XSSFCell cell = null;
		for (String header : datas.getHeaders()) {
			colIdx ++;
			if (colIdx < 4) {
				continue;
			}
			cell = row.getCell(colIdx);
			if (cell == null) {
				cell = row.createCell(colIdx);
			}
			cell.setCellValue(header);
			cell.setCellStyle(headerCellStyle);
		}
		
		// I. Hồ sơ yêu cầu/Hợp đồng Bảo hiểm
		// II. Doanh thu
		rowIdx = 4;
		for (AggregateReportRowDetailDto dataRow : datas.getDataRows()) {
			String[][] data = dataRow.getDatas();
			if (data == null) {
				rowIdx ++;
				continue;
			}
			for (String[] rowDetail : data) {
				row = xssfSheet.getRow(rowIdx);
				if (row == null) {
					row = xssfSheet.createRow(rowIdx);
				}
				colIdx = 3;
				for (String str : rowDetail) {
					cell = row.getCell(colIdx);
					if (cell == null) {
						cell = row.createCell(colIdx);
					}
                    if (rowIdx >= 5 && rowIdx <= 36) {
                    	BigDecimal valueBigdecimal = new BigDecimal(str.replace(",", ""));
                    	cell.setCellValue(valueBigdecimal.doubleValue());
                        cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal3());
                    } else {
                    	Double valueOfDouble = Double.parseDouble(str.replace("%", ""));
                    	cell.setCellValue(valueOfDouble / 100);
                    	cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1WithPercent());
                    }
					colIdx ++;
				}
				rowIdx ++;
			}
		}
		xssfSheet.addMergedRegion(new CellRangeAddress(4, 4, 0, datas.getHeaders().size()));
		xssfSheet.addMergedRegion(new CellRangeAddress(16, 16, 0, datas.getHeaders().size()));
    }
    
    private boolean checkPermission(String policyKey) {
    	if (!policyKey.chars().allMatch(Character::isDigit)) {
    		return true;
    	}
        CheckPermissionParamDto paramDto = new CheckPermissionParamDto();
        paramDto.agentCode = UserProfileUtils.getFaceMask();
        paramDto.policyKey = policyKey;
        sqlManagerDb2Service.call(STORE_GET_PERMISSION_BY_AGENT, paramDto);
        if (paramDto.permision > 0) {
            return true;
        }
    	return false;
    }
    
    @Override
	public CmsCommonPagination<ContactHistoryDto> getContactHistoryByPolicy(String agentCode, String policyNo) {
    	ContactHistorySearchParamDto param = new ContactHistorySearchParamDto();
    	param.agentCode = UserProfileUtils.getFaceMask();
    	param.policyNo = policyNo;
    	sqlManagerDb2Service.call(STORE_GET_CONTACT_HISTORY_BY_POLICY, param);
    	CmsCommonPagination<ContactHistoryDto> rs = new CmsCommonPagination<>();
    	rs.setData(param.datas);
		return rs;
	}
    
    @Override
    public CmsCommonPagination<CareContactPolicyDto> getListCareContactPolicy(CareContactPolicySearchDto searchDto) {
        CmsCommonPagination<CareContactPolicyDto> rs = new CmsCommonPagination<>();
        try {
	        String user = UserProfileUtils.getFaceMask();
	        if (StringUtils.isEmpty(user)) {
	            user = searchDto.getAgentCode();
	        }
	        searchDto.setFunctionCode("ADP_CARE_CONTACT_POLICY");
	        ObjectMapper mapper = new ObjectMapper();
	        String stringJsonParam =  mapper.writeValueAsString(searchDto);
	        
	        CommonSearchWithPagingDto common;
	        common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());
	
	        CareContactPolicyParamDto paramDto = new CareContactPolicyParamDto();
	        paramDto.page = searchDto.getPage();
	        paramDto.pageSize = searchDto.getPageSize();

	        paramDto.agentCode = user;
	        paramDto.partner = searchDto.getPartner();
	        paramDto.regionCode = searchDto.getRegionCode();
	        paramDto.zoneCode = searchDto.getZoneCode();
	        paramDto.uoCode = searchDto.getUoCode();
	        
	        paramDto.areaDlvnCode = searchDto.getAreaCodeDLVN();
	        paramDto.regionDlvnCode = searchDto.getRegionCodeDLVN();
	        paramDto.zoneDlvnCode = searchDto.getZoneCodeDLVN();
	        
	        paramDto.ilCode = searchDto.getIlCode();
	        paramDto.isCode = searchDto.getIsCode();
	        paramDto.smCode = searchDto.getSmCode();
	    	paramDto.policyType = searchDto.getType();
	    	paramDto.contactResult = searchDto.getContactResult();	
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if(StringUtils.isNotEmpty(searchDto.getFromDate())){
				try {
					paramDto.fromDate = sdf.parse(searchDto.getFromDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(StringUtils.isNotEmpty(searchDto.getToDate())){
				try {
					paramDto.toDate = sdf.parse(searchDto.getToDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
	        paramDto.sort = common.getSort();
	        paramDto.searchStr = common.getSearch();
	        
	        sqlManagerDb2Service.call(STORE_GET_LIST_CARE_CONTACT_POLICY, paramDto);
	        
	        rs.setTotalData(paramDto.totalRows);
	        rs.setData(paramDto.lstData);
        
        } catch (Exception e) {
            logger.error("###getListCareContactPolicy###", e);
        }
        return rs;
    }
    
    @Override
    public ResponseEntity exportListCareContactPolicy(CareContactPolicySearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
            if (StringUtils.isEmpty(searchDto.getAgentCode())) {
                searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            
            CmsCommonPagination<CareContactPolicyDto> common = getListCareContactDataExport(searchDto);

            String datePattern = datePattern = "dd/MM/yyyy";
            String templateName = "AD_DS_Den_han_T-90_den_T-30.xlsx";
            String title = "DANH SÁCH ĐẾN HẠN T-90 ĐẾN T-30";
            if ("2".equals(searchDto.getType())) {
            	templateName = "AD_DS_Den_han_T-29_den_T-10.xlsx";
            	title = "DANH SÁCH ĐẾN HẠN T-29 ĐẾN T-10";
            } else if ("3".equals(searchDto.getType())) {
            	templateName = "AD_DS_Den_han_T-9_den_T+15.xlsx";
            	title = "DANH SÁCH ĐẾN HẠN T-9 ĐẾN T+15";
            } else if ("4".equals(searchDto.getType())) {
            	templateName = "AD_DS_Den_han_T+16_den_T+60.xlsx";
            	title = "DANH SÁCH ĐẾN HẠN T+16 ĐẾN T+60";
            }
            List<ItemColsExcelDto> cols = new ArrayList<>();
    		ImportExcelUtil.setListColumnExcel(EnumExportListContactHistory.class, cols);
            
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/AD_Lich_su_lien_he_khach_hang.xlsx");
            
            String startRow = "A5";

            List<CareContactPolicyDto> lstdata = common.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, title, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                logger.info("###exportListDuePolicy### .path" + path);
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                		CareContactPolicyDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            
        } catch (Exception e) {
            logger.error("###exportListCareContactPolicy###", e);
        }
        return res;
    }
    
    @Override
    public CmsCommonPagination<PolicyContactHistoryDto> getContactHistoryByAgent(PolicyContactHistorySearchDto searchDto) {
        CmsCommonPagination<PolicyContactHistoryDto> rs = new CmsCommonPagination<>();
        try {
	        searchDto.setFunctionCode("ADP_CONTACT_HISTORY_POLICY");
	        ObjectMapper mapper = new ObjectMapper();
	        String stringJsonParam =  mapper.writeValueAsString(searchDto);
	        
	        CommonSearchWithPagingDto common;
	        common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());
	
	        PolicyContactHistoryParamDto paramDto = new PolicyContactHistoryParamDto();
	        paramDto.page = searchDto.getPage();
	        paramDto.pageSize = searchDto.getPageSize();

	        paramDto.agentCode = UserProfileUtils.getFaceMask();
	        paramDto.partner = searchDto.getPartner();
	        paramDto.regionCode = searchDto.getRegionCode();
	        paramDto.zoneCode = searchDto.getZoneCode();
	        paramDto.uoCode = searchDto.getUoCode();
	        
	        paramDto.areaDlvnCode = searchDto.getAreaCodeDLVN();
	        paramDto.regionDlvnCode = searchDto.getRegionCodeDLVN();
	        paramDto.zoneDlvnCode = searchDto.getZoneCodeDLVN();
	        
	        paramDto.ilCode = searchDto.getIlCode();
	        paramDto.isCode = searchDto.getIsCode();
	        paramDto.smCode = searchDto.getSmCode();
	    	
	        paramDto.sort = common.getSort();
	        paramDto.searchStr = common.getSearch();
	        paramDto.contactResult = searchDto.getContactResult();
	        paramDto.contactGroup = searchDto.getContactGroup();
	        
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        if(StringUtils.isNotEmpty(searchDto.getFromDate())){
	        	paramDto.fromDate = sdf.parse(searchDto.getFromDate());
			}
			if(StringUtils.isNotEmpty(searchDto.getToDate())){				
				paramDto.toDate = sdf.parse(searchDto.getToDate());
			}
			if(StringUtils.isNotEmpty(searchDto.getContactFromDate())){
	        	paramDto.contactFromDate = sdf.parse(searchDto.getContactFromDate());
			}
			if(StringUtils.isNotEmpty(searchDto.getContactToDate())){
				paramDto.contactToDate = sdf.parse(searchDto.getContactToDate());
			}
			
	        sqlManagerDb2Service.call(STORE_GET_LIST_CONTACT_HISTORY_BY_AGENT, paramDto);
	        
	        rs.setTotalData(paramDto.totalRows);
	        rs.setData(paramDto.lstData);
        
        } catch (Exception e) {
            logger.error("###getContactHistoryByAgent###", e);
        }
        return rs;
    }
    
    @Override
    public ResponseEntity exportListContactHistoryByAgent(PolicyContactHistorySearchDto searchDto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            
            CmsCommonPagination<PolicyContactHistoryDto> common = getListContactHistoryDataExport(searchDto);

            String datePattern = datePattern = "dd/MM/yyyy";
            String templateName = "AD_Lich_su_lien_he_khach_hang.xlsx";
            List<ItemColsExcelDto> cols = new ArrayList<>();
    		ImportExcelUtil.setListColumnExcel(EnumExportListContactHistory.class, cols);
            
            String templatePath = servletContext
                    .getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            
            String startRow = "A5";

            List<PolicyContactHistoryDto> lstdata = common.getData();
            // start fill data to workbook
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;// setMapColFormat();
            Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                setDataHeader(xssfWorkbook, 0, null, "Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"), mapColStyle);
                String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
                String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
                		PolicyContactHistoryDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            logger.error("###exportListContactHistoryByAgent###", e);
        }
        return res;
    }
    
    private CmsCommonPagination<CareContactPolicyDto> getListCareContactDataExport(CareContactPolicySearchDto dto) {
        CmsCommonPagination<CareContactPolicyDto> rs = new CmsCommonPagination<>();
        try {
        	CareContactPolicyParamDto param = new CareContactPolicyParamDto();
	        param.agentCode = UserProfileUtils.getFaceMask();
			param.partner = dto.getPartner();
			param.regionCode = dto.getRegionCode();
			param.zoneCode = dto.getZoneCode();
			param.uoCode = dto.getUoCode();
			param.areaDlvnCode = dto.getAreaCodeDLVN();
			param.regionDlvnCode = dto.getRegionCodeDLVN();
			param.zoneDlvnCode = dto.getZoneCodeDLVN();
			param.ilCode = dto.getIlCode();
			param.isCode = dto.getIsCode();
			param.smCode = dto.getSmCode();
			param.policyType = dto.getType();
			param.contactResult = dto.getContactResult();
	    	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if(StringUtils.isNotEmpty(dto.getFromDate())){			
				param.fromDate = sdf.parse(dto.getFromDate());
			}
			if(StringUtils.isNotEmpty(dto.getToDate())){				
				param.toDate = sdf.parse(dto.getToDate());
			}
			
			sqlManagerDb2Service.call(STORE_GET_LIST_CARE_CONTACT_POLICY_EXP, param);
			
	        rs.setData(param.lstData);
        } catch (Exception e) {
            logger.error("###getDuePolicyDetailDataExport###", e);
		}
        return rs;
    }
    
    private CmsCommonPagination<PolicyContactHistoryDto> getListContactHistoryDataExport(PolicyContactHistorySearchDto dto) {
        CmsCommonPagination<PolicyContactHistoryDto> rs = new CmsCommonPagination<>();
        try {
        	PolicyContactHistoryParamDto param = new PolicyContactHistoryParamDto();
        	param.agentCode = UserProfileUtils.getFaceMask();
        	param.partner = dto.getPartner();
        	param.regionCode = dto.getRegionCode();
        	param.zoneCode = dto.getZoneCode();
        	param.uoCode = dto.getUoCode();
	        
        	param.areaDlvnCode = dto.getAreaCodeDLVN();
        	param.regionDlvnCode = dto.getRegionCodeDLVN();
        	param.zoneDlvnCode = dto.getZoneCodeDLVN();
	        
        	param.ilCode = dto.getIlCode();
        	param.isCode = dto.getIsCode();
        	param.smCode = dto.getSmCode();
	    	
        	param.contactResult = dto.getContactResult();
        	param.contactGroup = dto.getContactGroup();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if(StringUtils.isNotEmpty(dto.getFromDate())){
				param.fromDate = sdf.parse(dto.getFromDate());
			}
			if(StringUtils.isNotEmpty(dto.getToDate())){
				param.toDate = sdf.parse(dto.getToDate());
			}
			if(StringUtils.isNotEmpty(dto.getContactFromDate())){
				param.contactFromDate = sdf.parse(dto.getContactFromDate());
			}
			if(StringUtils.isNotEmpty(dto.getContactToDate())){
				param.contactToDate = sdf.parse(dto.getContactToDate());
			}
			
			sqlManagerDb2Service.call(STORE_GET_LIST_CONTACT_HISTORY_BY_AGENT_EXP, param);	
			
	        rs.setData(param.lstData);
        } catch (Exception e) {
            logger.error("###getListContactHistoryDataExport###", e);
		}
        return rs;
    }
    
    @Override
  	public ObjectDataRes<PolicyClaimSearchResultDto> getClaimByPolicyNo(String policyNo) {
    	PolicyClaimSearchByNoParam param = new PolicyClaimSearchByNoParam();
  		param.policyNo=policyNo;
  		
  		sqlManagerDb2Service.call(STORE_GET_CLAIM_BY_POLICY, param);
  		List<PolicyClaimSearchResultDto> data = new ArrayList<>();
  		int total = 0;
  		if (CommonCollectionUtil.isNotEmpty(param.datas)) {
  			data = param.datas;
  			total = data.size();
  		}

  		ObjectDataRes<PolicyClaimSearchResultDto> resObj = new ObjectDataRes<>(total, data);
  		return resObj;
  	}
    
    @Override
    public ObjectDataRes<ClaimAssessorCommentResultDto> getAssessorCommentByClaimNo(String claimNo) {
    	ClaimAssessorCommentParamDto param = new ClaimAssessorCommentParamDto();
  		param.claimNo=claimNo;
  		
  		sqlManagerDb2Service.call(STORE_GET_CLAIM_ASSESSOR_COMMENT, param);
  		List<ClaimAssessorCommentResultDto> data = new ArrayList<>();
  		int total = 0;
  		if (CommonCollectionUtil.isNotEmpty(param.datas)) {
  			data = param.datas;
  			total = data.size();
  		}

  		ObjectDataRes<ClaimAssessorCommentResultDto> resObj = new ObjectDataRes<>(total, data);
  		return resObj;
    }
    
    @Override
  	public ObjectDataRes<PolicyRequestSearchResultDto> getRequestByPolicyNo(String policyNo) {
  		PolicyRequestSearchByNoParam param = new PolicyRequestSearchByNoParam();
  		param.policyNo=policyNo;
  		
  		sqlManagerDb2Service.call(STORE_GET_REQUEST_BY_POLICY, param);  		
  		List<PolicyRequestSearchResultDto> data = new ArrayList<>();
  		int total = 0;
  		if (CommonCollectionUtil.isNotEmpty(param.datas)) {
  			data = param.datas;
  			total = data.size();
  		}

  		ObjectDataRes<PolicyRequestSearchResultDto> resObj = new ObjectDataRes<>(total, data);
  		return resObj;
  	}
    
    @Override
  	public GroupDocumentDto getGroupDocument(String docNo) {
    	GroupDocumentParamDto param = new GroupDocumentParamDto();
    	param.agentCode = UserProfileUtils.getFaceMask();
  		param.docNo = docNo;
  		
  		sqlManagerDb2Service.call("RPT_ODS.ADP_SP_GET_GROUP_AGENT_DOCUMENT", param);
  		if (CommonCollectionUtil.isNotEmpty(param.datas)) {
  			return param.datas.get(0);
  		}

  		return null;
  	}
}

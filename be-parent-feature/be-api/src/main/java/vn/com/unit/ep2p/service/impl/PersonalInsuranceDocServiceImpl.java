package vn.com.unit.ep2p.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationDto;
import vn.com.unit.cms.core.module.customerManagement.dto.AdditionalDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.AdditionalDetailParam;
import vn.com.unit.cms.core.module.customerManagement.dto.FileSubmittedDto;
import vn.com.unit.cms.core.module.customerManagement.dto.FileSubmittedParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceContractDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceContractParamSumDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceContractParamSumListDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceDocParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceDocParamSumDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceTypeSumSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PersonalInsuranceDocParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PersonalInsuranceDocumentDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PersonalInsuranceDocumentSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PersonalInsuranceParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ProductDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ProductParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.RequestAdditionalDto;
import vn.com.unit.cms.core.module.customerManagement.dto.RequestAdditionalParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.RequestAppraisalDto;
import vn.com.unit.cms.core.module.customerManagement.dto.RequestAppraisalParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.SubmittedDetailParam;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.enumdef.GroupInsuranceContractExportEnum;
import vn.com.unit.ep2p.enumdef.GroupInsuranceDocumentsExportEnum;
import vn.com.unit.ep2p.enumdef.PrersonalInsuranceAdditionalExportEnum;
import vn.com.unit.ep2p.enumdef.PrersonalInsuranceReleaseExportEnum;
import vn.com.unit.ep2p.enumdef.PrersonalInsuranceSubmittedExportEnum;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.ApiCustomerInformationService;
import vn.com.unit.ep2p.service.ApiEmulateService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.ep2p.service.PersonalInsuranceDocService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class PersonalInsuranceDocServiceImpl extends AbstractCommonService implements PersonalInsuranceDocService {

	@Autowired
	private Db2ApiService db2ApiService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private EventsImportServiceImpl eventsImportService;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	ApiAgentDetailService apiAgentDetailService;
	@Autowired
	ApiCustomerInformationService apiCustomerInformationService;

	private static final String STORE_HSYCBH = "RPT_ODS.DS_SP_GET_LIST_INSURANCE_CLAIM_DOCUMENTS";
	private static final String STORE_HSYCBH_BY_TYPE = "RPT_ODS.DS_SP_GET_LIST_INSURANCE_DOCUMENTS";
	// Realtime data 
	private static final String STORE_HSYCBH_BY_TYPE_V3 = "RPT_ODS.DS_SP_GET_LIST_INSURANCE_DOCUMENTS_V3";
	private static final String STORE_PRODUCT = "";
	private static final String STORE_REQUEST_APPRAISAL = "";
	private static final String STORE_REQUEST_ADDITIONAL = "RPT_ODS.DS_SP_GET_LIST_INSURANCE_DOCUMENTS";
	private static final String STORE_FILE_SUBMITTED = "";
	private static final String STORE_OFFICE_DOCUMENT = "RPT_ODS.DS_SP_GET_LIST_OF_GROUP_INSURANCE_DOCUMENTS";
	private static final String STORE_OFFICE_DOCUMENT_SUM = "RPT_ODS.DS_SP_GET_LIST_INSURANCE_DOCUMENTS_LEADER_SUM";
	private static final String DS_SP_GET_LIST_POLICY_BY_LEADER_SUM_LIST = "RPT_ODS.DS_SP_GET_LIST_POLICY_BY_LEADER_SUM_LIST";
	private static final String STORE_OFFICE_CONTRACT_DETAIL = "RPT_ODS.DS_SP_GET_LIST_POLICY_BY_LEADER";
	
	// JCA Setting
	// Realtime mode
    public static final String REALTIME_INSURANCE_DOC = "REALTIME_INSURANCE_DOC";
    // interval Offline (unit: hours)
    public static final String INTERVAL_OFFLINE_INSURANCE_DOC = "INTERVAL_OFFLINE_INSURANCE_DOC";    
    
	
	private static final String DS_SP_EXPORT_LIST_POLICY_BY_LEADER = "RPT_ODS.DS_SP_EXPORT_LIST_POLICY_BY_LEADER";

	private static final Logger logger = LoggerFactory.getLogger(PersonalInsuranceDocServiceImpl.class);
	private final int batchSize = 10000;

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	@Override
	public CmsCommonPagination<PersonalInsuranceDocumentDto> getListDocByStatus(
			PersonalInsuranceDocumentSearchDto searchDto) {
		CmsCommonPagination<PersonalInsuranceDocumentDto> rs = new CmsCommonPagination<>();
		
		// detect mode search
		if(StringUtils.isEmpty(searchDto.getCountMode())) {
			searchDto.setCountMode("0");
		}
		// prepare params
		PersonalInsuranceDocParamDto personalInsuranceParamDto = prepareParams2Search(searchDto);
	
		sqlManagerDb2Service.call(STORE_HSYCBH_BY_TYPE_V3, personalInsuranceParamDto);
		List<PersonalInsuranceDocumentDto> lstData = personalInsuranceParamDto.lstData;
		lstData.forEach(e->{
			if(!StringUtils.isBlank(e.getPolicyNo())){
				e.setPolicyNo(formatPolicyNumber(9, e.getPolicyNo()));
			}
		});
		rs.setTotalData(personalInsuranceParamDto.totalRows);
		rs.setData(lstData);
		return rs;
	}

	private PersonalInsuranceDocParamDto prepareParams2Search(PersonalInsuranceDocumentSearchDto searchDto) {
		PersonalInsuranceDocParamDto personalInsuranceParamDto = new PersonalInsuranceDocParamDto();
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
		if ("POL_DOC_SUBMITTED".equalsIgnoreCase(searchDto.getFunctionCode())) {
			common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "POL_DOC_SUBMITTED");
		} else if ("POL_DOC_ADDITIONAL".equalsIgnoreCase(searchDto.getFunctionCode())) {
			common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "POL_DOC_ADDITIONAL");
		} else if ("POL_DOC_RELEASE".equalsIgnoreCase(searchDto.getFunctionCode())) {
			common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "POL_DOC_RELEASE");
		} else {
			common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "POL_DOC_REFUSE");
		}

		personalInsuranceParamDto.agentCode = searchDto.getAgentCode();
		personalInsuranceParamDto.type = searchDto.getType();
		personalInsuranceParamDto.page = searchDto.getPage();
		personalInsuranceParamDto.pageSize = searchDto.getPageSize();
		personalInsuranceParamDto.sort = common.getSort();
		// set realtime mode
		personalInsuranceParamDto.realTime = 0;
		personalInsuranceParamDto.intervalOffline = 2;
		
		try {
			personalInsuranceParamDto.countMode = Integer.parseInt(searchDto.getCountMode());
			String modeRealtime = systemConfig.getConfig(REALTIME_INSURANCE_DOC);
			String intervalOffline = systemConfig.getConfig(INTERVAL_OFFLINE_INSURANCE_DOC);
			if(CommonStringUtil.isNotBlank(modeRealtime)) {
				personalInsuranceParamDto.realTime = Integer.parseInt(modeRealtime);
			}
			if(CommonStringUtil.isNotBlank(intervalOffline)) {
				personalInsuranceParamDto.intervalOffline = Integer.parseInt(intervalOffline);
			}
		} catch (NumberFormatException e) {
			personalInsuranceParamDto.realTime = 0;
			personalInsuranceParamDto.intervalOffline = 2;
			personalInsuranceParamDto.countMode = 0;
		}
		if (StringUtils.isNotEmpty(searchDto.getDocNoString())) {
			personalInsuranceParamDto.docNo = searchDto.getDocNoString();
		}
		if (searchDto.getMtdDate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String dateMtd = sdf.format(searchDto.getMtdDate());
            common.setSearch(common.getSearch().concat(" AND LEFT(DATE_KEY, 6) = "+dateMtd));
		}
		personalInsuranceParamDto.search = common.getSearch();
		return personalInsuranceParamDto;
	}

	
	@Override
	public PersonalInsuranceDocumentDto getDetailDocumentByDocNo(String docNo) {
		PersonalInsuranceParamDto personalInsuranceParamDto = new PersonalInsuranceParamDto();
		personalInsuranceParamDto.docNo = docNo;
		sqlManagerDb2Service.call(STORE_HSYCBH, personalInsuranceParamDto);

		List<PersonalInsuranceDocumentDto> lstData = personalInsuranceParamDto.lstData;
		PersonalInsuranceDocumentDto rs = new PersonalInsuranceDocumentDto();
		if (!lstData.isEmpty()) {
			rs = lstData.get(0);
		}
		return rs;
	}

	@Override
	public CmsCommonPagination<ProductDto> getListProductByDocNo(String docNo, Integer page, Integer size,
			String agentCode) {
		ProductParamDto productParamDto = new ProductParamDto();
		productParamDto.docNo = docNo;
		productParamDto.page = page;
		productParamDto.size = size;
		productParamDto.agentCode = agentCode;
		sqlManagerDb2Service.call(STORE_PRODUCT, productParamDto);
		List<ProductDto> lstData = productParamDto.lstData;
		CmsCommonPagination<ProductDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(productParamDto.totalRows);
		rs.setData(lstData);
		return rs;
	}

	@Override

	public CmsCommonPagination<RequestAppraisalDto> getListRequestAppraisal(String docNo, Integer page, Integer size,
			String agentCode) {
		RequestAppraisalParamDto requestAppraisalParamDto = new RequestAppraisalParamDto();
		requestAppraisalParamDto.docNo = docNo;
		requestAppraisalParamDto.page = page;
		requestAppraisalParamDto.size = size;
		requestAppraisalParamDto.agentCode = agentCode;
		sqlManagerDb2Service.call(STORE_REQUEST_APPRAISAL, requestAppraisalParamDto);
		List<RequestAppraisalDto> lstData = requestAppraisalParamDto.lstData;
		CmsCommonPagination<RequestAppraisalDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(requestAppraisalParamDto.totalRows);
		rs.setData(lstData);
		return rs;
	}

	@Override
	public CmsCommonPagination<RequestAdditionalDto> getListRequestAdditional(String docNo, Integer page, Integer size,
			String agentCode) {
		RequestAdditionalParamDto requestAdditionalParamDto = new RequestAdditionalParamDto();
		requestAdditionalParamDto.docNo = docNo;
		requestAdditionalParamDto.page = page;
		requestAdditionalParamDto.pageSize = size;
		requestAdditionalParamDto.agentCode = agentCode;
		sqlManagerDb2Service.call(STORE_REQUEST_ADDITIONAL, requestAdditionalParamDto);
		CmsCommonPagination<RequestAdditionalDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(requestAdditionalParamDto.totalRows);
		rs.setData(requestAdditionalParamDto.lstData);
		return rs;
	}

	@Override
	public CmsCommonPagination<FileSubmittedDto> getListFileSubmitted(String docNo, Integer page, Integer size,
			String agentCode) {
		FileSubmittedParamDto fileSubmittedParamDto = new FileSubmittedParamDto();
		fileSubmittedParamDto.docNo = docNo;
		fileSubmittedParamDto.page = page;
		fileSubmittedParamDto.size = size;
		fileSubmittedParamDto.agentCode = agentCode;
		sqlManagerDb2Service.call(STORE_FILE_SUBMITTED, fileSubmittedParamDto);
		CmsCommonPagination<FileSubmittedDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(fileSubmittedParamDto.totalRows);
		rs.setData(fileSubmittedParamDto.lstData);
		return rs;
	}

	// CALL "RPT_ODS"."DS_SP_GET_LIST_OF_GROUP_INSURANCE_DOCUMENTS"('260990',
	// '','OH', 0, 30,null, @V_TOTALROWS);
// CALL "RPT_ODS"."DS_SP_GET_LIST_OF_GROUP_INSURANCE_DOCUMENTS"('260990', '260988','OH', 0, 30,null, @V_TOTALROWS);
	@Override
	public CmsCommonPagination<OfficeInsuranceDto> getListOfficeDocument(OfficeInsuranceSearchDto searchDto) {
		OfficeInsuranceDocParamDto officeInsuranceDocParamDto = new OfficeInsuranceDocParamDto();
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(searchDto.getAgentGroup())) {
			searchDto.setAgentType(searchDto.getAgentGroup());
		}
        String stringJsonParam = "";
        searchDto.setFunctionCode("GROUP_"+searchDto.getAgentType());
        OfficeInsuranceSearchDto searchBd1 = objectMapper.convertValue(searchDto, OfficeInsuranceSearchDto.class);
        OfficeInsuranceSearchDto searchBd2 = objectMapper.convertValue(searchDto, OfficeInsuranceSearchDto.class);
    	String bd1 = "1 = 1";
    	String bd2 = "1 = 1";
        try {
        	setConditionSearch(searchBd1, 1);
            stringJsonParam = mapper.writeValueAsString(searchBd1);
        } catch (JsonProcessingException e) {
            logger.error("getListOfficeDocument", e);
        }
        CommonSearchWithPagingDto commonLv1 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam, searchDto.getAgentType());
        if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
        	bd1 = commonLv1.getSearch();
        }
        try {
        	setConditionSearch(searchBd2, 2);
            stringJsonParam = mapper.writeValueAsString(searchBd2);
        } catch (JsonProcessingException e) {
            logger.error("getListOfficeDocument", e);
        }
        CommonSearchWithPagingDto commonLv2 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam, searchDto.getAgentType());
        if(StringUtils.isNotEmpty(commonLv2.getSearch())) {
        	bd2 = commonLv2.getSearch();
        	if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
        		// lay data cap con thuoc cap cha
            	bd2 = bd2 +" AND "+commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.Parent_Agent_Name,''))");
            }
        } else {
        	if(StringUtils.isNotEmpty(commonLv1.getSearch())) {
        		// lay data cap con thuoc cap cha
            	bd2 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.Parent_Agent_Name,''))");
            }
        }
		officeInsuranceDocParamDto.agentCode = searchDto.getAgentCode();
		if ("GA".equals(searchDto.getAgentType()) || "SO".equals(searchDto.getAgentType())) {
			officeInsuranceDocParamDto.orgId = searchDto.getOrgId();
		}
		officeInsuranceDocParamDto.agentGroup = searchDto.getAgentType();
		officeInsuranceDocParamDto.page = searchDto.getPage();
		officeInsuranceDocParamDto.size = searchDto.getSize();
		officeInsuranceDocParamDto.sort = searchDto.getSort();
		String searchAll = "";
		if (StringUtils.isNotEmpty(searchDto.getKeyword())) {
    		searchAll = " AND (L.TREE_LEVEL = 0  or ((UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  and L.TREE_LEVEL = 1) or (UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  AND UPPER(nvl(L.Parent_Agent_Name,''))  LIKE UPPER(N'%"+searchDto.getKeyword()+"%')  and L.TREE_LEVEL = 2)) )";
    	}
		officeInsuranceDocParamDto.search = searchAll +"AND (L.TREE_LEVEL = 0  or (("+ bd1 +" and L.TREE_LEVEL = 1) or ("+bd2+" and L.TREE_LEVEL = 2)) )";
		sqlManagerDb2Service.call(STORE_OFFICE_DOCUMENT, officeInsuranceDocParamDto);
		List<OfficeInsuranceDto> data = officeInsuranceDocParamDto.lstData;
    	sumPolicy(data, 0);
    	data = groupAgent(searchDto, data);
		for (OfficeInsuranceDto ls : data) {

			mapAgent(ls, searchDto.getAgentType(), ls.getTreeLevel() == 0);

			if (StringUtils.isNotBlank(searchDto.getAgentType())) {

				ls.setAgentGroup(ls.getOrgId() + "-" + ls.getAgentCode() + "-" + ls.getAgentName());

				if (StringUtils.equalsIgnoreCase(searchDto.getAgentType(), "BM")|| StringUtils.equalsIgnoreCase(searchDto.getAgentType(), "SBM")) {
					ls.setAgentGroup(ls.getAgentType() + ":" + ls.getAgentCode() + "-" + ls.getAgentName());
				}
				if (StringUtils.contains(ls.getAgentName(), "Dummy Sales")) {
					ls.setAgentGroup(ls.getOrgId() + "-" + ls.getAgentName());
				}
			}
		}

		CmsCommonPagination<OfficeInsuranceDto> resultData = new CmsCommonPagination<>();
		resultData.setTotalData(officeInsuranceDocParamDto.totalRows);
		resultData.setData(data);

		return resultData;
	}

	private List<OfficeInsuranceDto> groupAgent(OfficeInsuranceSearchDto searchDto, List<OfficeInsuranceDto> datas) {
		if ("AH".equals(searchDto.getAgentType()) ) {
			List<OfficeInsuranceDto> listTree2 = new ArrayList<OfficeInsuranceDto>();
			List<OfficeInsuranceDto> lstTree1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1))).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(lstTree1)) {
				lstTree1.forEach(item -> {
					List<OfficeInsuranceDto> lstTree2 = datas.stream()
							.filter(e -> e.getTreeLevel().equals(new Integer(2)) && e.getOrgParentId().equals(item.getOrgId()) && e.getParentAgentCode().equals(item.getAgentCode()))
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(lstTree2)) {
						if ("TH".equals(searchDto.getAgentType())) {
							Map<String, List<OfficeInsuranceDto>> maplv2Group = lstTree2.stream()
									.filter(e -> e.getTreeLevel() == 2)
									.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
							for (Entry<String, List<OfficeInsuranceDto>> entry : maplv2Group.entrySet()) {
							    String key = entry.getKey();
							    if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
							    	sumPolicy(entry.getValue(), 2);
							    	listTree2.addAll(entry.getValue());
							    }
							}
						} else {
							lstTree2.forEach(item2 -> {
								item2.setOrgParentId(item.getAgentCode());
							});
							listTree2.addAll(lstTree2);
						}
					}
					
				});
				datas.removeIf(e -> e.getTreeLevel().equals(new Integer(2)));
				Map<String, List<OfficeInsuranceDto>> maplv1 = lstTree1.stream()
						.filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
				if (CollectionUtils.isNotEmpty(maplv1.entrySet())) {
					datas.removeIf(e -> e.getTreeLevel().equals(new Integer(1)));
					for (Entry<String, List<OfficeInsuranceDto>> entry : maplv1.entrySet()) {
					    String key = entry.getKey();
					    if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
					    	sumPolicy(entry.getValue(), 1);
					    	datas.addAll(entry.getValue());
					    }
					}
			    	datas.addAll(listTree2);
				}
				
			}
		} else if ("OH".equals(searchDto.getAgentType())) {
			List<OfficeInsuranceDto> lv2 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(2))).collect(Collectors.toList());
			lv2.forEach(item2 -> {
				item2.setTotalheadofdepartment(null);
			});
		}
		else if ("GA".equals(searchDto.getAgentType()) || "SO".equals(searchDto.getAgentType())) {
			List<OfficeInsuranceDto> resultNew = new ArrayList<>();
			//lv2 = lv1
			List<OfficeInsuranceDto> lv1ToLv2 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1))).collect(Collectors.toList());
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(2)));
			// lv1 = lv0
			List<OfficeInsuranceDto> lv0ToLv1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(0))).collect(Collectors.toList());

			OfficeInsuranceDto lv0 = new OfficeInsuranceDto();
			if (CollectionUtils.isNotEmpty(lv0ToLv1) && ObjectUtils.isNotEmpty(lv0ToLv1.get(0))) {
				resultNew.add(mapObject(lv0ToLv1.get(0), lv0));
			}
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(1)));
			if (CollectionUtils.isNotEmpty(lv0ToLv1)) {
				lv0ToLv1.forEach(item2 -> {
					item2.setTreeLevel(1);
					item2.setOrgParentId(item2.getOrgId());
					item2.setParentAgentCode(item2.getAgentCode());
					resultNew.add(item2);
				});
			}
			// remove lv2
			if (CollectionUtils.isNotEmpty(lv1ToLv2)) {
				lv1ToLv2.forEach(item2 -> {
					item2.setTreeLevel(2);
					item2.setTotalheadofdepartment(null);
					resultNew.add(item2);
				});
			}
			return resultNew;
		}
		return datas;
	}

	private OfficeInsuranceDto mapObject(OfficeInsuranceDto source, OfficeInsuranceDto tag) {
		tag.setNo(source.getNo());
		tag.setAgentType(source.getAgentType());
		tag.setAgentName(source.getAgentName());
		tag.setGadName(source.getGadName());
		tag.setAgentGroup(source.getAgentGroup());
		tag.setTotal(source.getTotal());
		tag.setTotalheadofdepartment(source.getTotalheadofdepartment());
		tag.setTotalmanager(source.getTotalmanager());
		tag.setTotaltvtc(source.getTotaltvtc());
		tag.setTotaltvtcsa(source.getTotaltvtcsa());
		tag.setChildGroup(source.getChildGroup());
		tag.setAgentCode(source.getAgentCode());
		tag.setParentAgentCode(source.getParentAgentCode());
		tag.setParentAgentType(source.getParentAgentType());
		tag.setParentAgentName(source.getParentAgentName());
		tag.setBdohCode(source.getBdohCode());
		tag.setBdohName(source.getBdohName());
		tag.setBdohType(source.getBdohType());
		tag.setOrgId(source.getOrgId());
		tag.setOrgName(source.getOrgName());
		tag.setParentGroup(source.getParentGroup());
		tag.setOrgParentId(source.getOrgParentId());
		tag.setAgentLevel(source.getAgentLevel());
		tag.setTreeLevel(source.getTreeLevel());
		tag.setOrgNameNew(source.getOrgNameNew());
		tag.setSumOfDocumentSubmitted(source.getSumOfDocumentSubmitted());
		tag.setSumOfDocumentAddition(source.getSumOfDocumentAddition());
		tag.setSumOfDocumentReleased(source.getSumOfDocumentReleased());
		tag.setSumOfDocumentRejected(source.getSumOfDocumentRejected());
		tag.setSumOfPolicyReleased(source.getSumOfPolicyReleased());
		tag.setSumOfPolicyCanceled(source.getSumOfPolicyCanceled());
		return tag;
	}

	private void sumPolicy(List<OfficeInsuranceDto> datas, int treeLevel){
		if(!datas.isEmpty()){
			List<OfficeInsuranceDto> lstRoot = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(treeLevel))).collect(Collectors.toList());
			List<String> adresses = lstRoot.stream()
				    .map(OfficeInsuranceDto::getOrgName)
				    .collect(Collectors.toList());
			String orgNameNew = String.join(", ", adresses);
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(treeLevel)));
			if(!lstRoot.isEmpty()){
				OfficeInsuranceDto dto = lstRoot.get(0);
				if(treeLevel == 1 && "OH".equalsIgnoreCase(dto.getChildGroup())) {
					dto.setOrgId(dto.getAgentCode());
				}
				dto.setOrgNameNew(orgNameNew);
				dto.setSumOfDocumentSubmitted(lstRoot.stream().filter(x -> !isNullOrZero(x.getSumOfDocumentSubmitted())).map(OfficeInsuranceDto:: getSumOfDocumentSubmitted).reduce(BigDecimal.ZERO,(a, b) -> a.add(b)));
				dto.setSumOfDocumentAddition(lstRoot.stream().filter(x -> !isNullOrZero(x.getSumOfDocumentAddition())).map(OfficeInsuranceDto:: getSumOfDocumentAddition).reduce(BigDecimal.ZERO,(a, b) -> a.add(b)));
				dto.setSumOfDocumentReleased(lstRoot.stream().filter(x -> !isNullOrZero(x.getSumOfDocumentReleased())).map(OfficeInsuranceDto:: getSumOfDocumentReleased).reduce(BigDecimal.ZERO,(a, b) -> a.add(b)));
				dto.setSumOfDocumentRejected(lstRoot.stream().filter(x -> !isNullOrZero(x.getSumOfDocumentRejected())).map(OfficeInsuranceDto:: getSumOfDocumentRejected).reduce(BigDecimal.ZERO,(a, b) -> a.add(b)));
				dto.setSumOfPolicyReleased(lstRoot.stream().filter(x -> !isNullOrZero(x.getSumOfPolicyReleased())).map(OfficeInsuranceDto:: getSumOfPolicyReleased).reduce(BigDecimal.ZERO,(a, b) -> a.add(b)));
				dto.setSumOfPolicyCanceled(lstRoot.stream().filter(x -> !isNullOrZero(x.getSumOfPolicyCanceled())).map(OfficeInsuranceDto:: getSumOfPolicyCanceled).reduce(BigDecimal.ZERO,(a, b) -> a.add(b)));
				datas.add(dto);
			}
		}
	}
	public static boolean isNullOrZero(BigDecimal number) {
		boolean isBigDecimalValueNullOrZero = false;
		if (number == null)
			isBigDecimalValueNullOrZero = true;
		else if (number != null && number.compareTo(BigDecimal.ZERO) == 0)
			isBigDecimalValueNullOrZero = true;

		return isBigDecimalValueNullOrZero;
	}
	private void setConditionSearch(OfficeInsuranceSearchDto data, int level) {
		if(StringUtils.isNotEmpty(data.getAgentType())) {
			switch (data.getAgentType()) {
			case "CAO":
				if(level == 1) data.setBdahName(null);
				if(level == 2) data.setBdthName(null);
				break;
			case "TH":
				if(level == 1) data.setBdrhName(null);
				if(level == 2) data.setBdahName(null);
				break;
			case "AH":
				if(level == 1) data.setBdohName(null);
				if(level == 2) data.setBdrhName(null);
				break;
			case "RH":
				if(level == 1) data.setGaName(null);
				if(level == 2) data.setBdohName(null);
				break;
			case "OH":
				if(level == 1) data.setBranchName(null);
				if(level == 2) data.setGaName(null);
				break;
			case "GA":
				if(level == 1) data.setBranchName(null);
				if(level == 2) data.setGaName(null);
				break;
			case "SO":
				if(level == 1) data.setBranchName(null);
				if(level == 2) data.setGaName(null);
				break;
			default:
				break;
			}
		}
		
	}

	// CALL "RPT_ODS"."DS_SP_GET_LIST_INSURANCE_DOCUMENTS_LEADER_SUM"('260990',
	// 'BM', '1', 0,100 , @V_TOTALROWS);
	// CALL "RPT_ODS"."DS_SP_GET_LIST_INSURANCE_DOCUMENTS_LEADER_SUM"('260990',
	// 'BM', '2', 0,100 , @V_TOTALROWS);
	// CALL "RPT_ODS"."DS_SP_GET_LIST_INSURANCE_DOCUMENTS_LEADER_SUM"('129178',
	// 'BM', '4', 0,100 , @V_TOTALROWS);
	@Override
	public CmsCommonPagination<OfficeInsuranceDetailDto> getListOfficeDocumentByType(
			OfficeInsuranceTypeSumSearchDto searchDto) {
		OfficeInsuranceDocParamSumDto officeInsuranceDocParamSumDto = new OfficeInsuranceDocParamSumDto();
		searchDto.setFunctionCode("HSYCBH_GROUP_"+searchDto.getAgentType());
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
				searchDto.getAgentType());
		officeInsuranceDocParamSumDto.agentCode = searchDto.getAgentCode();
		officeInsuranceDocParamSumDto.orgCode = searchDto.getOrgId();
		officeInsuranceDocParamSumDto.agentGroup = searchDto.getAgentType();
		officeInsuranceDocParamSumDto.docStatus = searchDto.getType();
		officeInsuranceDocParamSumDto.page = searchDto.getPage();
		officeInsuranceDocParamSumDto.size = searchDto.getSize();
		officeInsuranceDocParamSumDto.sort = common.getSort();
		officeInsuranceDocParamSumDto.search = common.getSearch();
		sqlManagerDb2Service.call(STORE_OFFICE_DOCUMENT_SUM, officeInsuranceDocParamSumDto);
		List<OfficeInsuranceDetailDto> data = officeInsuranceDocParamSumDto.lstData;

		for (OfficeInsuranceDetailDto ls : data) {
			ls.setOrgId(ls.getOrgCode());
			ls.setManager(ls.getLv2Agenttype() + ":" + ls.getLv2Agentcode().replace("A", "").replace("B", "").replace("C", "") + "-" + ls.getLv2Agentname());
			ls.setAgent(ls.getLv3Agenttype() + ":" + ls.getLv3Agentcode() + "-" + ls.getLv3Agentname());
		}

		CmsCommonPagination<OfficeInsuranceDetailDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(officeInsuranceDocParamSumDto.totalRows);
		rs.setData(data);
		return rs;
	}

	// CALL "RPT_ODS"."DS_SP_GET_LIST_POLICY_BY_LEADER_SUM_LIST"('260990','BM', 'ACTIVE',
	// 0, 100,null,null, @V_TOTALROWS);

	@Override
	public CmsCommonPagination<OfficeInsuranceContractDetailDto> getListOfficeDocumentContractByType(
			OfficeInsuranceTypeSumSearchDto searchDto) {
		OfficeInsuranceContractParamSumListDto officeInsuranceDocParamSumDto = new OfficeInsuranceContractParamSumListDto();
		searchDto.setFunctionCode("HSYCBH_GROUP_"+searchDto.getAgentType());
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());


		officeInsuranceDocParamSumDto.agentCode = searchDto.getAgentCode();
		officeInsuranceDocParamSumDto.agentGroup = searchDto.getAgentType();
		officeInsuranceDocParamSumDto.orgCode = searchDto.getOrgId();
		officeInsuranceDocParamSumDto.type = searchDto.getType();
		officeInsuranceDocParamSumDto.dateKey = searchDto.getDateKey();
		officeInsuranceDocParamSumDto.page = searchDto.getPage();
		officeInsuranceDocParamSumDto.size = searchDto.getPageSize();
		officeInsuranceDocParamSumDto.sort = common.getSort();
		officeInsuranceDocParamSumDto.search = common.getSearch();
		sqlManagerDb2Service.call(DS_SP_GET_LIST_POLICY_BY_LEADER_SUM_LIST, officeInsuranceDocParamSumDto);
		List<OfficeInsuranceContractDetailDto> data = officeInsuranceDocParamSumDto.lstData;

		for (OfficeInsuranceContractDetailDto ls : data) {
			ls.setTotalProposal(ls.getTotalContract());
			if (StringUtils.isNotBlank(searchDto.getType())) {
				ls.setOrgId(ls.getOrgCode());
				ls.setManager(
						ls.getManagerAgentType() + ":" + ls.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + ls.getManagerAgentName());
				ls.setAgent(ls.getAgentType() + ":" + ls.getAgentCode() + "-" + ls.getAgentName());

				if (StringUtils.equalsIgnoreCase(searchDto.getType(), "CANCEL")) {
					ls.setOffice(ls.getOfficeCode() + "-" + ls.getOfficeName());
				}
			}
		}

		CmsCommonPagination<OfficeInsuranceContractDetailDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(officeInsuranceDocParamSumDto.totalRows);
		rs.setData(data);
		return rs;
	}
	// CALL "RPT_ODS"."DS_SP_GET_LIST_POLICY_BY_LEADER"('260990','BM', 'ACTIVE', 0,
	// 100,null,null, @V_TOTALROWS);

	@Override
	public CmsCommonPagination<OfficeInsuranceContractDetailDto> getListOfficeDocumentContractDetailByType(
			OfficeInsuranceTypeSumSearchDto searchDto) {
		if (StringUtils.isNotEmpty(searchDto.getAgentGroup())) {
			searchDto.setAgentType(searchDto.getAgentGroup());
		}
		OfficeInsuranceContractParamSumDto officeInsuranceDocParamSumDto = new OfficeInsuranceContractParamSumDto();
			searchDto.setFunctionCode("HSYCBH_GROUP_"+searchDto.getAgentType());
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());

		officeInsuranceDocParamSumDto.agentCode = searchDto.getAgentCode();
		officeInsuranceDocParamSumDto.agentGroup = searchDto.getAgentType();
		officeInsuranceDocParamSumDto.type = searchDto.getType();
		officeInsuranceDocParamSumDto.page = searchDto.getPage();
		officeInsuranceDocParamSumDto.size = searchDto.getPageSize();
		officeInsuranceDocParamSumDto.sort = common.getSort();
		officeInsuranceDocParamSumDto.search = common.getSearch();
		sqlManagerDb2Service.call(STORE_OFFICE_CONTRACT_DETAIL, officeInsuranceDocParamSumDto);
		List<OfficeInsuranceContractDetailDto> data = officeInsuranceDocParamSumDto.lstData;

		for (OfficeInsuranceContractDetailDto ls : data) {
			ls.setAgent(ls.getAgentType() + ":" + ls.getAgentCode() + "-" + ls.getAgentName());
			ls.setManager(ls.getManagerAgentType() + ":" + ls.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + ls.getManagerAgentName());

		}

		CmsCommonPagination<OfficeInsuranceContractDetailDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(officeInsuranceDocParamSumDto.totalRows);
		rs.setData(data);
		return rs;
	}
	
	@Override
	public CmsCommonPagination<OfficeInsuranceContractDetailDto> exportListPolicyByAgentByConditionBMUM(
			OfficeInsuranceTypeSumSearchDto searchDto) {
		if (StringUtils.isNotEmpty(searchDto.getAgentGroup())) {
			searchDto.setAgentType(searchDto.getAgentGroup());
		}
		OfficeInsuranceContractParamSumDto officeInsuranceDocParamSumDto = new OfficeInsuranceContractParamSumDto();
			searchDto.setFunctionCode("HSYCBH_GROUP_"+searchDto.getAgentType());
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, searchDto.getFunctionCode());

		officeInsuranceDocParamSumDto.agentCode = searchDto.getAgentCode();
		officeInsuranceDocParamSumDto.agentGroup = searchDto.getAgentType();
		officeInsuranceDocParamSumDto.type = searchDto.getType();
		officeInsuranceDocParamSumDto.page = searchDto.getPage();
		officeInsuranceDocParamSumDto.size = searchDto.getPageSize();
		officeInsuranceDocParamSumDto.sort = common.getSort();
		officeInsuranceDocParamSumDto.search = common.getSearch();
		sqlManagerDb2Service.call(DS_SP_EXPORT_LIST_POLICY_BY_LEADER, officeInsuranceDocParamSumDto);
		
		CmsCommonPagination<OfficeInsuranceContractDetailDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(officeInsuranceDocParamSumDto.totalRows);
		rs.setData(officeInsuranceDocParamSumDto.lstData);
		return rs;
	}

	// nhóm phòng CAO - TH - AH - RH - OH - GA 
	@Override
	public ResponseEntity exportListOfficeDocument(OfficeInsuranceSearchDto searchDto) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(0);
			searchDto.setSize(0);
			CmsCommonPagination<OfficeInsuranceDto> resObj = getListOfficeDocument(searchDto);
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String template = "Danh_sach_HSYCBH_cap_phong_ban.xlsx";
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
			String templateName = "HSYCBH_cap_phong_ban.xlsx";

			String startRow = "A8";
			List<OfficeInsuranceDto> lstdata = resObj.getData();
			OfficeInsuranceDto root = new OfficeInsuranceDto();
			if(ObjectUtils.isNotEmpty(lstdata)) {
			 root = lstdata.stream().filter(e->e.getTreeLevel().equals(0)).findFirst().get();
			}
			List<OfficeInsuranceDto> allData = new ArrayList<>();
			// get lv1
			List<OfficeInsuranceDto> lv1 = lstdata.stream().filter(e->e.getTreeLevel() == 1).collect(Collectors.toList());
			for (OfficeInsuranceDto groupLv1 : lv1) {
				allData.add(groupLv1);
				// get lv2
				List<OfficeInsuranceDto> lv2 = lstdata.stream().filter(e->e.getTreeLevel() == 2 
						&& e.getParentAgentCode().equals(groupLv1.getAgentCode()) && e.getOrgParentId().equals(groupLv1.getOrgId())).collect(Collectors.toList());
				allData.addAll(lv2);
			}
			allData.add(root);
//			int index = total.indexOf(root);
//			logger.error("index = " +index);
//			lstdata.add(total.get(index));
//			lstdata.remove(index);

			addDateInColumn(allData, searchDto, searchDto.getAgentType());

			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(GroupInsuranceDocumentsExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
				AgentInfoDb2 dataz = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(),searchDto.getAgentType(),null);
				writeDateNow(searchDto.getAgentType(), xssfWorkbook, 0, new Date(),allData.size() ,dataz.getOrgName(),dataz.getAgentType() +": "+ dataz.getAgentCode().replace(dataz.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "") + " - " + dataz.getAgentName(),mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, allData,
						OfficeInsuranceDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				logger.error("##ExportList##", e);
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked" })
	public <T, E extends Enum<E>, M> ResponseEntity exportListDataWithHeader(List<T> resultDto, String view, String titleName, String[] titleHeader, String row, Class<E> enumDto, Class<M> className,String agentCode,String agentType,String agentName, String orgName, int total, String type) {
		ResponseEntity res = null;
		try {
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "HSYCBH.xlsx";
			String fileName = "HSYCBH.xlsx";
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = row;
			List<ItemColsExcelDto> cols = new ArrayList<>();

			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;	
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
				fileName+="_"+view;
				ImportExcelUtil.setListColumnExcel(enumDto, cols);

				setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, titleHeader, titleName,total,orgName ,agentType +": "+ agentCode + " - " + agentName ,type,view, mapColStyle);
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

	public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader, String titleName,int total ,String office ,String leader ,String agentType,String view, Map<String, CellStyle> mapColStyle) {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);

		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();
		CellStyle no = xssfSheet.getWorkbook().createCellStyle();


		Font fontTitleDate = xssfWorkbook.createFont();
		fontTitleDate.setColor(IndexedColors.BLUE.index);
		fontTitleDate.setFontName("Times New Roman");
		titleStyleDate.setFont(fontTitleDate);
		fontTitleDate.setBold(true);

		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setColor(IndexedColors.BLUE.index);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short)15);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setFont(fontTitle);

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

		if(agentType.equals("BM") && (view.equals("phat_hanh_nhom_phong") || view.equals("huy_nhom_phong"))){
			xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		} else if(agentType.equals("UM")&& (view.equals("phat_hanh_nhom_phong") || view.equals("huy_nhom_phong"))){
			xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		}else{
			if(agentType.equals("BM"))
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
			else
				xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		}
		if(xssfSheet.getRow(0) != null) {
			xssfSheet.getRow(0).getCell(0).setCellValue(titleName);
			xssfSheet.getRow(0).getCell(0).setCellStyle(titleStyle);
		}else{
			xssfSheet.createRow(0).createCell(0).setCellValue(titleName);
			xssfSheet.createRow(0).createCell(0).setCellStyle(titleStyle);
		}
		if(xssfSheet.getRow(2) != null) {
			xssfSheet.getRow(2).getCell(0).setCellValue("Office/GA: " + office);
			xssfSheet.getRow(2).getCell(0).setCellStyle(titleStyleDate);
		}else{
			xssfSheet.createRow(2).createCell(0).setCellValue("Office/GA: " + office);
			xssfSheet.createRow(2).createCell(0).setCellStyle(titleStyleDate);
		}
		if(xssfSheet.getRow(3) != null) {
			xssfSheet.getRow(3).getCell(0).setCellValue(leader);
			xssfSheet.getRow(3).getCell(0).setCellStyle(titleStyleDate);
		}else{
			xssfSheet.createRow(3).createCell(0).setCellValue(leader);
			xssfSheet.createRow(3).createCell(0).setCellStyle(titleStyleDate);
		}
		if(xssfSheet.getRow(4) != null) {
			xssfSheet.getRow(4).getCell(0).setCellValue("Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
			xssfSheet.getRow(4).getCell(0).setCellStyle(titleStyleDate);
		}else{
			xssfSheet.createRow(4).createCell(0).setCellValue("Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
			xssfSheet.createRow(4).createCell(0).setCellStyle(titleStyleDate);
		}
		if(xssfSheet.getRow(5) != null) {
			xssfSheet.getRow(5).getCell(0).setCellValue("Tổng số HSYCBH: " + total);
			xssfSheet.getRow(5).getCell(0).setCellStyle(titleStyleDate);
		}else{
			xssfSheet.createRow(5).createCell(0).setCellValue("Tổng số HSYCBH: " + total);
			xssfSheet.createRow(5).createCell(0).setCellStyle(titleStyleDate);
		}

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

		XSSFRow row4 = xssfSheet.getRow(7);
		if(row4 == null) row4 = xssfSheet.createRow(7);

		for (int i = 0; i < titleHeader.length; i++) {
			XSSFCell cell4 = row4.getCell(i);
			if (cell4 == null) cell4 = row4.createCell(i);
			cell4.setCellValue(titleHeader[i]);
			cell4.setCellStyle(headerCellStyle);
		}
	}
	
	@Override
	public ResponseEntity exportListOfficeDocumentBMUMDetail(OfficeInsuranceTypeSumSearchDto searchDto) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(0);
			searchDto.setSize(0);
			searchDto.setPageSize(0);
			CmsCommonPagination<OfficeInsuranceContractDetailDto> resObj = getListOfficeDocumentContractDetailByType(searchDto);

			AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), searchDto.getAgentType(), searchDto.getOrgId());
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String template = "GroupInsuranceContactDetail.xlsx";
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
			String templateName = "";
			if (StringUtils.endsWithIgnoreCase(searchDto.getType(), "ACTIVE")) {
				 templateName="Danh_sach_hop_dong_phat_hanh_trong_thang";
			}
			else if (StringUtils.endsWithIgnoreCase(searchDto.getType(), "CANCEL")) {
				 templateName="Danh_sach_hop_dong_huy_trong_thang";
			}

			String startRow = "A9";
			List<OfficeInsuranceContractDetailDto> lstdata = resObj.getData();

			if(ObjectUtils.isNotEmpty(lstdata))
				lstdata.forEach(x->{
					if(x.getInsuranceBuyer() !=null)
						x.setInsuranceBuyer(x.getInsuranceBuyer().toUpperCase());
				});
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(GroupInsuranceContractExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();

				String titleName = "";
				if (StringUtils.endsWithIgnoreCase(searchDto.getType(), "ACTIVE"))
					titleName = "DANH SÁCH HỢP ĐỒNG PHÁT HÀNH TRONG THÁNG";
				else titleName = "DANH SÁCH HỢP ĐỒNG HỦY TRONG THÁNG";

				String[] titleHeader = new String[] { "Số HĐBH","Bên mua BH","Ngày hiệu lực"};
				setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, titleHeader, titleName,resObj.getTotalData(),agentDetail.getOrgName() ,agentDetail.getAgentType() +": "+ agentDetail.getAgentCode().replace(agentDetail.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "") + " - " + agentDetail.getAgentName() , searchDto.getAgentGroup(),"", mapColStyle);

				//END			
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						OfficeInsuranceContractDetailDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				logger.error("##ExportList##", e);
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	private void mapAgent(OfficeInsuranceDto data, String agentGroup, boolean first) {
		if (first) {
			switch (data.getChildGroup()) {
			case "CAO":
				data.setBdthCode(data.getAgentCode());
				data.setBdthName("Tổng cộng");
				data.setBdthType(data.getAgentType());
				break;
			case "TH":
				data.setBdahCode(data.getAgentCode());
				data.setBdahName("Tổng cộng");
				data.setBdahType(data.getAgentType());
				break;
			case "AH":
				data.setBdohCode(data.getAgentCode());
				data.setBdohName("Tổng cộng");
				data.setBdohType(data.getAgentType());
				break;
			case "RH":
				data.setBdohCode(data.getAgentCode());
				data.setBdohName("Tổng cộng");
				data.setBdohType(data.getAgentType());
				break;
			case "OH":
				data.setGaCode(data.getAgentCode());
				data.setGaName("Tổng cộng");
				data.setGaType(data.getAgentType());
				break;
			case "GA":
				data.setGaCode(data.getAgentCode());
				data.setGaName("Tổng cộng");
				data.setGaType(data.getAgentType());
				break;
			case "SO":
				data.setGaCode(data.getAgentCode());
				data.setGaName("Tổng cộng");
				data.setGaType(data.getAgentType());
				break;
			case "BM":
				data.setUnitCode(data.getAgentCode());
				data.setUnitName("Tổng cộng");
				data.setUnitType(data.getAgentType());
				break;
			case "UM":
				data.setUnitCode(data.getAgentCode());
				data.setUnitName("Tổng cộng");
				data.setUnitType(data.getAgentType());
				break;
			default:
				data.setAgentCode(data.getAgentCode());
				break;
			}
		} else {
			switch (data.getChildGroup()) {
			case "CAO":
				data.setCaoCode(data.getAgentCode());
				data.setCaoName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
				data.setCaoType(data.getAgentType());
				break;
			case "TH":
				data.setBdthCode(data.getAgentCode());
				data.setBdthName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
				data.setBdthType(data.getAgentType());
				break;
			case "AH":
				data.setBdahCode(data.getAgentCode());
				data.setBdahName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
				data.setBdahType(data.getAgentType());
				break;
			case "RH":
				data.setBdrhCode(data.getAgentCode());
				data.setBdrhName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
				data.setBdrhType(data.getAgentType());
				break;
			case "OH":
				String orgId = data.getAgentType();
				if (data.getTreeLevel() == 2) {
					orgId = data.getOrgId();
					data.setOrgCode(data.getOrgId());
				}
				
				data.setBdohName(orgId + "-" + data.getAgentCode() + "-" + data.getAgentName());
				data.setBdohCode(data.getAgentCode());
				data.setBdohType(data.getAgentType());				
				if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
					data.setBdohName(data.getAgentName());
				}				
				break;
			case "SO":
				data.setGaCode(data.getAgentCode());
				data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getParentAgentName());
				if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
					data.setGaName(data.getParentAgentName());

				}
				data.setGaType(data.getAgentType());
				break;
			case "GA":
				data.setGaCode(data.getAgentCode());
				data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getParentAgentName());
				if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
					data.setGaName(data.getParentAgentName());

				}
				data.setGaType(data.getAgentType());
				break;
			case "BM":
				data.setBranchCode(data.getAgentCode());
				data.setBranchName(data.getAgentType() + ": " + data.getAgentCode().replace(data.getOrgId(), "") + "-" + data.getAgentName());
				data.setBranchType(data.getAgentType());
				if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
					data.setBranchName(data.getAgentName());
				}
				break;
			case "UM":
				data.setUnitCode(data.getAgentCode());
				data.setUnitName(data.getAgentType() + ": " + data.getAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + data.getAgentName());
				data.setUnitType(data.getAgentType());
				if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
					data.setUnitName(data.getAgentName());
				}
				break;
			default:
				data.setAgentCode(data.getAgentCode());
				break;
			}
		}
	}

	@Override
	public CmsCommonPagination<OfficeInsuranceDetailDto> getDetailOfficeDocumentPolicyByAgentCode(String agentCode,
			String agentType, String status, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Autowired
    private ApiEmulateService emulateService;

	private String formatPolicyNumber(int digits, String policyNumber){
		if(StringUtils.isEmpty(policyNumber)) {
			return "";
		}
		return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining("")).concat(policyNumber);
	}

	@Override
	public ResponseEntity exportListPrersonalInsuranceDocByCondition(PersonalInsuranceDocumentSearchDto searchDto,
			HttpServletResponse response, Locale locale) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(0);
			searchDto.setPageSize(0);
			CmsCommonPagination<PersonalInsuranceDocumentDto> common = getListDocByStatus(searchDto);
			common.getData().forEach(e->{
				if(StringUtils.isNotEmpty(e.getPolicyNo())){
					e.setPolicyNo(formatPolicyNumber(9, e.getPolicyNo()));
				}
				if(StringUtils.isNotEmpty(e.getInsuranceBuyer())) e.setInsuranceBuyer(e.getInsuranceBuyer().toUpperCase());
			});

			AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), searchDto.getAgentType(), searchDto.getOrgId());
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			templateName = "HSYCBH_tu_choi_ca_nhan.xlsx"; // BI TU CHOI
			ImportExcelUtil.setListColumnExcel(PrersonalInsuranceSubmittedExportEnum.class, cols);
			if ("1".equalsIgnoreCase(searchDto.getType())) {
				templateName = "HSYCBH_ca_nhan.xlsx";
				ImportExcelUtil.setListColumnExcel(PrersonalInsuranceSubmittedExportEnum.class, cols);
			} else if ("2".equalsIgnoreCase(searchDto.getType())) {
				templateName = "HSYCBH_cho_bo_sung_ca_nhan.xlsx";
				ImportExcelUtil.setListColumnExcel(PrersonalInsuranceAdditionalExportEnum.class, cols);
			} else if ("3".equalsIgnoreCase(searchDto.getType())) {
				templateName = "HSYCBH_da_phat_hanh_ca_nhan.xlsx";
				ImportExcelUtil.setListColumnExcel(PrersonalInsuranceReleaseExportEnum.class, cols);
			}
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A8";

			List<PersonalInsuranceDocumentDto> lstdata = common.getData();
			// start fill data to workbook
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
				setDataHeaderToXSSFWorkbookHSYCBH(xssfWorkbook, 0,common.getTotalData(),agentDetail.getOrgName() ,agentDetail.getAgentType() +": "+ agentDetail.getAgentCode().replace(agentDetail.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "") + " - " + agentDetail.getAgentName(),mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						PersonalInsuranceDocumentDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e);

		}
		return res;
	}

	private void setDataHeaderToXSSFWorkbookHSYCBH(XSSFWorkbook xssfWorkbook, int sheet, Integer total, String orgName, String leader, Map<String, CellStyle> mapColStyle) {
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
			xssfSheet.getRow (2).getCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		else xssfSheet.createRow (2).createCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));


		if(xssfSheet.getRow(3) != null)
			xssfSheet.getRow (3).getCell(0).setCellValue(leader);
		else xssfSheet.createRow (3).createCell(0).setCellValue(leader);

		if(xssfSheet.getRow(4) != null)
			xssfSheet.getRow (4).getCell(0).setCellValue("Tổng số: " + total);
		else xssfSheet.createRow (4).createCell(0).setCellValue("Tổng số: " + total);

		xssfSheet.getRow (4).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow (3).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow (2).getCell(0).setCellStyle(titleStyleDate);

	}

	public void writeDateNow(String agentType, XSSFWorkbook xssfWorkbook, int sheetNumber, Date runDate,int total,String office, String leader,Map<String, CellStyle> mapColStyle)
			throws IOException {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);

		xssfSheet.addMergedRegion(new CellRangeAddress(total+6, total+6, 0, 1));


		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setColor(IndexedColors.BLUE.index);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short)15);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setFont(fontTitle);

		CellStyle titleCell = xssfSheet.getWorkbook().createCellStyle();
		Font fontCell = xssfWorkbook.createFont();
		fontCell.setFontName("Times New Roman");
		fontCell.setBold(true);
		titleCell.setFont(fontCell);
		titleCell.setBorderTop(BorderStyle.THIN);
		titleCell.setBorderLeft(BorderStyle.THIN);
		titleCell.setBorderRight(BorderStyle.THIN);
		titleCell.setBorderBottom(BorderStyle.THIN);
		mapColStyle.put("PAREN",titleCell);
		
		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();

		Font fontTitleDate = xssfWorkbook.createFont();
		fontTitleDate.setColor(IndexedColors.BLUE.index);
		fontTitleDate.setFontName("Times New Roman");
		titleStyleDate.setFont(fontTitleDate);
		
	    xssfSheet.getRow(2).getCell(0).setCellStyle(titleStyleDate);
	    
	    xssfSheet.getRow(3).getCell(0).setCellValue(leader);
	    xssfSheet.getRow(3).getCell(0).setCellStyle(titleStyleDate);

	    xssfSheet.getRow(4).getCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
	    xssfSheet.getRow(4).getCell(0).setCellStyle(titleStyleDate);

		
		XSSFRow row1 = xssfSheet.getRow(0);
		XSSFCell cellIndex3 = row1.getCell(0);
		cellIndex3.setCellValue("DANH SÁCH HỒ SƠ YÊU CẦU BẢO HIỂM CẤP PHÒNG BAN");
		cellIndex3.setCellStyle(titleStyle);
		
		XSSFRow row = xssfSheet.getRow(6);
		XSSFCell cellIndex1 = row.getCell(0);
		XSSFCell cellIndex2 = row.getCell(1);
		
		
		if (StringUtils.endsWithIgnoreCase(agentType, "CAO")) {
			cellIndex1.setCellValue("BDTH");
			cellIndex2.setCellValue("BDAH");
			xssfSheet.getRow(2).getCell(0).setCellValue("Nation Wide: " + office);

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "TH")) {
			cellIndex1.setCellValue("BDAH");
			cellIndex2.setCellValue("BDOH");
			xssfSheet.getRow(2).getCell(0).setCellValue("Territory: " + office);

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "AH")) {
			cellIndex1.setCellValue("BDOH");
			cellIndex2.setCellValue("Văn phòng/Tổng đại lý");
			xssfSheet.getRow(2).getCell(0).setCellValue("Region: " + office);
		}
		if (StringUtils.endsWithIgnoreCase(agentType, "OH")) {
			cellIndex1.setCellValue("Văn phòng/Tổng đại lý");
			cellIndex2.setCellValue("Trưởng Ban Kinh doanh");
			xssfSheet.getRow(2).getCell(0).setCellValue("Office: " + office);

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "GA")) {
			cellIndex1.setCellValue("Văn phòng/Tổng đại lý");
			cellIndex2.setCellValue("Trưởng Ban Kinh doanh");
			xssfSheet.getRow(2).getCell(0).setCellValue("GA: " + office);

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "SO")) {
			cellIndex1.setCellValue("Văn phòng/Tổng đại lý");
			cellIndex2.setCellValue("Trưởng Ban Kinh doanh");
			xssfSheet.getRow(2).getCell(0).setCellValue("SO: " + office);
		}
	}

	public void addDateInColumn(List<OfficeInsuranceDto> lstdata, OfficeInsuranceSearchDto searchDto,
			String agentType) {

		for (OfficeInsuranceDto ls : lstdata) {

			if (StringUtils.endsWithIgnoreCase(agentType, "CAO")) {
				ls.setParen(ls.getBdthName());
				ls.setChild(ls.getBdahName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "TH")) {
				ls.setParen(ls.getBdahName());
				ls.setChild(ls.getBdohName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "AH")) {
				ls.setParen(ls.getBdohName());
				ls.setChild(ls.getGaName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "OH") || StringUtils.endsWithIgnoreCase(agentType, "GA") || StringUtils.endsWithIgnoreCase(agentType, "SO")) {
				ls.setParen(ls.getGaName());
				ls.setChild(ls.getBranchName());
			}
//			if (StringUtils.endsWithIgnoreCase(agentType, "GA")) {
//				ls.setParen(ls.getBranchName());
//				ls.setChild(ls.getUnitName());
//			}
			
		}
	}
	private static final String STORE_DETAIL_SUBMITTED = "RPT_ODS.SP_GET_BASICCOVERAGEINFO";
	private static final String STORE_DETAIL_ADDITIONAL = "RPT_ODS.SP_GET_PROPOSALREQUEST";
	
	//personal submit
	@Override
    public List<ProductInformationDto> getDetailProduct(String policyNo) {
     SubmittedDetailParam param = new SubmittedDetailParam();
     List<ProductInformationDto> data = new ArrayList<>();
     try {
         param.policyNo=policyNo;
         sqlManagerDb2Service.call(STORE_DETAIL_SUBMITTED, param);
//         data = apiCustomerInformationService.orderbyProduct(param.datas);
         if (CollectionUtils.isNotEmpty(param.datas)) {
        	 data = param.datas;
         }
     }catch (Exception e) {
         logger.error("Exception ", e);
    }
        return data;
    }

    @Override
    public PersonalInsuranceDocumentDto getDetailProfile(String docType, String docNo) {
    	PersonalInsuranceDocParamDto param = new PersonalInsuranceDocParamDto();
        PersonalInsuranceDocumentDto data = new PersonalInsuranceDocumentDto();
        try {
            param.type = docType;
            param.docNo = docNo;
            param.realTime = 0;
            param.intervalOffline = 2;
            try {
				String modeRealtime = systemConfig.getConfig(REALTIME_INSURANCE_DOC);
				String intervalOffline = systemConfig.getConfig(INTERVAL_OFFLINE_INSURANCE_DOC);
				if(CommonStringUtil.isNotBlank(modeRealtime)) {
					param.realTime = Integer.parseInt(modeRealtime);
				}
				if(CommonStringUtil.isNotBlank(intervalOffline)) {
					param.intervalOffline = Integer.parseInt(intervalOffline);
				}
			} catch (NumberFormatException e) {
				 param.realTime = 0;
				 param.intervalOffline = 2;
			}

            sqlManagerDb2Service.call(STORE_HSYCBH_BY_TYPE_V3, param);
            if(CollectionUtils.isNotEmpty(param.lstData)) {
            	data = param.lstData.get(0);
                data.setCheckData(true);
            }
        }catch (Exception e) {
            logger.error("Exception ", e);
            logger.error(e.getMessage());
       }
        return data;
    }

    
      @Override
        public List<AdditionalDetailDto> getDetailAdditional(String policyNo) {
          AdditionalDetailParam param = new AdditionalDetailParam();
          List<AdditionalDetailDto> data = new ArrayList<>();
         try {
             param.policyNo=policyNo;
             sqlManagerDb2Service.call(STORE_DETAIL_ADDITIONAL, param);
             data = param.datas;
         }catch (Exception e) {
             logger.error("Exception ", e);
        }
            return data;
        }
}

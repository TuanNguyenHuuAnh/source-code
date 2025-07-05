package vn.com.unit.ep2p.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.lang.Strings;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.customer.dto.ClientNickNameDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerDetailParam;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationParam;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationSearchDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInteractionHistoryDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInteractionHistoryParam;
import vn.com.unit.cms.core.module.customer.dto.CustomerInteractionHistorySearchDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerPotentialDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerPotentialParam;
import vn.com.unit.cms.core.module.customer.dto.InsuranceFeesInformationDto;
import vn.com.unit.cms.core.module.customer.dto.InsuranceFeesInformationParam;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationDto;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationParam;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationSearchDto;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationDto;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationParam;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationSearch;
import vn.com.unit.cms.core.module.customer.entity.ClientNickName;
import vn.com.unit.cms.core.module.customer.repository.ClientNickNameRepository;
import vn.com.unit.cms.core.module.events.dto.EventsGuestDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestParamDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestSearchDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.dto.CustomerInformationDetailDto;
import vn.com.unit.ep2p.admin.dto.PolicyInfoDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.enumdef.CustomerAdExportEnum;
import vn.com.unit.ep2p.enumdef.CustomerExportEnum;
import vn.com.unit.ep2p.enumdef.CustomerInteractionHistoryExportEnum;
import vn.com.unit.ep2p.enumdef.CustomerPotentialExportEnum;
import vn.com.unit.ep2p.enumdef.PolicyInformationAdExportEnum;
import vn.com.unit.ep2p.enumdef.PolicyInformationExportEnum;
import vn.com.unit.ep2p.service.ApiCustomerInformationService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;
@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiCustomerInformationServiceImpl implements ApiCustomerInformationService {

	@Autowired
	private Db2ApiService db2ApiService;

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
    private SystemConfig systemConfig;

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	@Autowired
	private ClientNickNameRepository clientNickNameRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ApiCustomerInformationServiceImpl.class);
	
	
	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;
	
	private static final String SP_PAGINATION_COMMOM = "RPT_ODS.DS_SP_GET_LIST_OF_CUSTOMERS";
	private static final String SP_ALL_CUSTOMER_BY_AGENTCODE = "RPT_ODS.DS_SP_GET_LIST_OF_CUSTOMERS";
	private static final String SP_POLYCI= "RPT_ODS.DS_SP_GET_LIST_POLICY_BY_AGENT";
	private static final String SP_PRODUCT= "RPT_ODS.SP_GET_BASICCOVERAGEINFO";
	private static final String SP_GET_LIST_CUSTOMER_POTENTIAL = "RPT_ODS.DS_SP_GET_LIST_CUSTOMER_POTENTIAL";
	private static final String SP_GET_LIST_INTERACTION_HISTORY = "RPT_ODS.DS_SP_GET_LIST_INTERACTION_HISTORY";
	
	private String formatPolicyNumber(int digits, String policyNumber){
		if(StringUtils.isEmpty(policyNumber)) {
			return "";
		}
		return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining("")).concat(policyNumber);
	}
 
	@Override
	public CmsCommonPagination<CustomerInformationDto> getListCustomerByCondition(CustomerInformationSearchDto dto) {
		CustomerInformationParam param = new CustomerInformationParam();
		dto.setFunctionCode("CUSTOMER");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
				"CUSTOMER");
		param.agentCode = dto.getAgentCode();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();

		sqlManagerDb2Service.call(SP_ALL_CUSTOMER_BY_AGENTCODE, param);
		List<CustomerInformationDto> datas = param.data;

		CmsCommonPagination<CustomerInformationDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalRows);
		return resultData;
	}
	
	@Override
	public CustomerInformationDetailDto getListCustomerByCustomerNo(String customerCode, String agentCode) {

		// resultData
		CustomerInformationDetailDto resultData = db2ApiService.getCustomerInformationDetail(agentCode, customerCode);
		if(resultData != null) {			
			if (StringUtils.isNotBlank(resultData.getGender())) {
				resultData.setGender(revertItem(resultData.getGender()));
			}
			if (StringUtils.isNotBlank(resultData.getMaritalStatus())) {
				resultData.setMaritalStatus(revertItem(resultData.getMaritalStatus()));
			}
		} else {
			resultData = new CustomerInformationDetailDto();
		}

		return resultData;
	}
	
	private String revertItem(String item) {
		return  item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase();

	}
	
	@Override
	public CmsCommonPagination<PolicyInformationDto> getListPolicyByCondition(PolicyInformationSearchDto searchDto) {
		if (searchDto.getPage() == 0 || searchDto.getPage() == null) {
			searchDto.setPage(0);
		}
		if (searchDto.getPageSize() == 0 || searchDto.getPageSize() == null) {
			searchDto.setPageSize(0);
		}
		PolicyInformationParam param = new PolicyInformationParam();
		if ("AD".equals(UserProfileUtils.getChannel())) {
			searchDto.setFunctionCode("AD_POLICY_INFO");
		} else {
			searchDto.setFunctionCode("POLICY_INFO");
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
		param.custNo = searchDto.getCustomerNo();
		param.policytype = searchDto.getPolicytype();
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(SP_POLYCI, param);
		List<PolicyInformationDto> datas = param.data;

		CmsCommonPagination<PolicyInformationDto> resultData = new CmsCommonPagination<>();
		for (PolicyInformationDto item : datas) {
			item.setPolAgtShrPct(item.getPolAgtShrPct().replace(".00", "%"));

			if (!StringUtils.isBlank(item.getInsuranceContract())) {
				item.setInsuranceContract(formatPolicyNumber(9, item.getInsuranceContract()));
			}

			if (isNullOrZero(item.getHangingFee())
					|| (!isNullOrZero(item.getHangingFee()) && item.getHangingFee().compareTo(new BigDecimal(0)) < 0)) {
				item.setHangingFee(new BigDecimal(0));
			}
		}

		resultData.setData(datas);
		resultData.setTotalData(param.totalrows);
		return resultData;
	}

	public static boolean isNullOrZero(BigDecimal number) {
		boolean isBigDecimalValueNullOrZero = false;
		if (number == null)
			isBigDecimalValueNullOrZero = true;
		else if (number != null && number.compareTo(BigDecimal.ZERO) == 0)
			isBigDecimalValueNullOrZero = true;

		return isBigDecimalValueNullOrZero;
	}
	
	@Override
	public PolicyInfoDto getListPolicyByPolicyNo(String policyNo) {
		PolicyInfoDto data =  db2ApiService.getDetailContract(Integer.valueOf(policyNo));
		if(data.getPolPremSuspAmt()!=null  && data.getPolMiscSuspAmt() != null) data.setPolCount(data.getPolPremSuspAmt().subtract(data.getPolMiscSuspAmt()));
		return data;
	}
	
	@Override
	public CmsCommonPagination<ProductInformationDto> getListProductByCondition(ProductInformationSearch dto) {
		ProductInformationParam param = new ProductInformationParam();
		param.polId=dto.getPolicyNo();
		sqlManagerDb2Service.call(SP_PRODUCT, param);
		List<ProductInformationDto> datas = param.data;
		CmsCommonPagination<ProductInformationDto> resultData = new CmsCommonPagination<>();
//		List<ProductInformationDto> data = orderbyProduct(datas);
		datas.forEach(x-> x.setCliSexCd(x.getCliSexCd().equals("F")? "Nữ":"Nam"));

		resultData.setData(datas);
		return resultData;
	}
	@Override
	public List<ProductInformationDto> orderbyProduct(List<ProductInformationDto> data){
		List<ProductInformationDto> orderBy = new ArrayList<>();
		List<ProductInformationDto> other = new ArrayList<>();
		//nhom hieu luc
		List<ProductInformationDto> h1 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("1")).collect(Collectors.toList());
		List<ProductInformationDto> h2 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("2")).collect(Collectors.toList());
		List<ProductInformationDto> h3 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("3")).collect(Collectors.toList());
		List<ProductInformationDto> h15 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("5")).collect(Collectors.toList());
		//Nhóm đang xử lý
		List<ProductInformationDto> h4 = data.stream().filter(x -> x.getCvgStatusCd().startsWith("P")).collect(Collectors.toList());
		//Nhóm đáo hạn
		List<ProductInformationDto> h5 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("F")).collect(Collectors.toList());
		//Nhóm hết hạn
		List<ProductInformationDto> h6 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("B")).collect(Collectors.toList());
		List<ProductInformationDto> h7 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("E")).collect(Collectors.toList());
		List<ProductInformationDto> h8 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("D")).collect(Collectors.toList());
		List<ProductInformationDto> h9 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("A")).collect(Collectors.toList());
		List<ProductInformationDto> h10 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("R")).collect(Collectors.toList());
		List<ProductInformationDto> h11 = data.stream().filter(x -> Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("H")).collect(Collectors.toList());

		if(ObjectUtils.isNotEmpty(h1))
			orderBy.addAll(h1);
		if(ObjectUtils.isNotEmpty(h2))
			orderBy.addAll(h2);
		if(ObjectUtils.isNotEmpty(h3))
			orderBy.addAll(h3);
		if(ObjectUtils.isNotEmpty(h15))
			orderBy.addAll(h15);
		if(ObjectUtils.isNotEmpty(h4))
			orderBy.addAll(h4);
		if(ObjectUtils.isNotEmpty(h5))
			orderBy.addAll(h5);
		if(ObjectUtils.isNotEmpty(h6))
			orderBy.addAll(h6);
		if(ObjectUtils.isNotEmpty(h7))
			orderBy.addAll(h7);
		if(ObjectUtils.isNotEmpty(h8))
			orderBy.addAll(h8);
		if(ObjectUtils.isNotEmpty(h9))
			orderBy.addAll(h9);
		if(ObjectUtils.isNotEmpty(h10))
			orderBy.addAll(h10);
		if(ObjectUtils.isNotEmpty(h11))
			orderBy.addAll(h11);
		data.stream()
				.forEach(x->{
					if(!Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("1")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("2")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("3")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("5")
					&& !x.getCvgStatusCd().startsWith("P")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("F")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("B")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("E")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("D")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("A")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("R")
					&& !Strings.trimAllWhitespace(x.getCvgStatusCd()).equals("H"))
					 other.add(x);
				});
		orderBy.addAll(other);

	return orderBy;
	}
	@Override
	public CmsCommonPagination<InsuranceFeesInformationDto> getListInsuranceFeesByCondition(String stringJsonParam) {
		InsuranceFeesInformationParam param = new InsuranceFeesInformationParam();
		param.stringJsonParam = stringJsonParam;
		sqlManagerDb2Service.call(SP_PAGINATION_COMMOM, param);
		List<InsuranceFeesInformationDto> datas = param.data;
		CmsCommonPagination<InsuranceFeesInformationDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	@Transactional
	public ClientNickNameDto editOrAddEvents(ClientNickNameDto dto) throws SQLException {
		String agentCode = UserProfileUtils.getFaceMask();
		dto.setAgentCode(agentCode);
		ClientNickName entity = clientNickNameRepository.findByCondition(dto);
		if(entity == null){
			entity = new ClientNickName();
			entity.setCreateDate(new Date());
			entity.setCreateBy(dto.getAgentCode());
			entity.setNickName(dto.getNickName());
			entity.setNotes(dto.getNotes());
		} else {
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(dto.getAgentCode());
			if (StringUtils.isNotEmpty(dto.getNickName())) {
				entity.setNickName(dto.getNickName());	
			}
			if (StringUtils.isNotEmpty(dto.getNotes())) {
				entity.setNotes(dto.getNotes());
			}
		}
		entity.setClientId(dto.getCustomerNo());
		entity.setAgentCode(dto.getAgentCode());
		clientNickNameRepository.save(entity);
		return dto;
	}

	@Override
	public ResponseEntity exportListCustomerAgentByCondition(CustomerInformationSearchDto searchDto,
			HttpServletResponse response, Locale locale,String agentCode) {
		ResponseEntity res = null;
		try {
			    searchDto.setAgentCode(agentCode);
				searchDto.setPage(0);
				searchDto.setPageSize(0);
				searchDto.setSize(0);
			CmsCommonPagination<CustomerInformationDto> common = getListCustomerByCondition(searchDto);
			List<CustomerInformationDto> lstdata = common.getData();
			
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            String templateName = "";
            if ("AD".equals(UserProfileUtils.getChannel())) {
            	templateName = "AD_Danh_sach_khach_hang.xlsx";
            	ImportExcelUtil.setListColumnExcel(CustomerAdExportEnum.class, cols);
            	for (CustomerInformationDto item : lstdata) {
            		if ("0".equals(item.getDcActivate())) {
            			item.setDcActivate("Không");
            		} else {
            			item.setDcActivate("Có");
            		}
            		if ("0".equals(item.getAutoDebit())) {
            			item.setAutoDebit("Không");
            		} else {
            			item.setAutoDebit("Có");
            		}
				}
            } else {
            	templateName = "Danh_sach_khach_hang.xlsx";
            	ImportExcelUtil.setListColumnExcel(CustomerExportEnum.class, cols);
				for (CustomerInformationDto item : lstdata) {
					if ("0".equals(item.getDcActivate())) {
						item.setDcActivate("Chưa");
					} else {
						item.setDcActivate("Có");
					}
					if ("0".equals(item.getAutoDebit())) {
						item.setAutoDebit("Chưa");
					} else {
						item.setAutoDebit("Có");
					}
				}
            }
    		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
    		String startRow = "A5";
            
            // start fill data to workbook
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();
			String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
    		String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
    	        Map<String, CellStyle> mapColStyle = new HashMap<>();

	    	   	 Font fontAmount = xssfWorkbook.createFont();
			     fontAmount.setFontName("Times New Roman");
			     fontAmount.setFontHeightInPoints((short) 11);
			     
    			CellStyle cellStyleMain = xssfWorkbook.createCellStyle();
    			cellStyleMain.setAlignment(HorizontalAlignment.RIGHT);
    			cellStyleMain.setWrapText(false);
    			cellStyleMain.setFont(fontAmount);
    			cellStyleMain.setBorderTop(BorderStyle.THIN);
    			cellStyleMain.setBorderLeft(BorderStyle.THIN);
    			cellStyleMain.setBorderRight(BorderStyle.THIN);
    			cellStyleMain.setBorderBottom(BorderStyle.THIN);

    			cellStyleMain.setVerticalAlignment(VerticalAlignment.CENTER);

				cellStyleMain.setDataFormat(xssfWorkbook.getCreationHelper().createDataFormat().getFormat("#,##0.0"));
    			mapColStyle.put("REWARDPOINTS", cellStyleMain);
            	
            	res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata, CustomerInformationDto.class
            			, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
				throw new Exception(e);
			}
          } catch (Exception e) {
			logger.error("Export",e);
        }
		return res;

	}

	@Override
	public ResponseEntity exportListPolicyByAgentByCondition(PolicyInformationSearchDto searchDto,
			HttpServletResponse response, Locale locale, String agentCode, String customerNo, String policytype) {
		ResponseEntity res = null;
		try {
			searchDto.setPolicytype(policytype);
			searchDto.setCustomerNo(customerNo);
			searchDto.setAgentCode(agentCode);
			searchDto.setPage(0);
			searchDto.setPageSize(0);
			searchDto.setSize(0);
			CmsCommonPagination<PolicyInformationDto> common = getListPolicyByCondition(searchDto);
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String template = "HDBH_theo_khach_hang.xlsx";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(PolicyInformationExportEnum.class, cols);
			if ("AD".equals(UserProfileUtils.getChannel())) {
				template = "AD_HDBH_theo_khach_hang.xlsx";
				ImportExcelUtil.setListColumnExcel(PolicyInformationAdExportEnum.class, cols);
			}
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);

			String startRow = "A5";

			List<PolicyInformationDto> lstdata = common.getData();
			if(CollectionUtils.isNotEmpty(lstdata)){
				lstdata.forEach(e->{
					if(StringUtils.equalsIgnoreCase(e.getPolicyType(), "INACTIVE")) {
						e.setExpirationDate(e.getDateDue());
					} else {
						e.setExpirationDate(null);
					}
				});
			}
			// start fill data to workbook
			ExportExcelUtil<PolicyInformationDto> exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				String repo = systemConfig.getConfig(SystemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						PolicyInformationDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, template, true, path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e);

		}
		return res;
	}

	@Override
	@Transactional
	public String getNickName(String agentCode, String customerNo) {
		return clientNickNameRepository.getNickNameByCondition(agentCode, customerNo);
	}

	@Override
	@Transactional
	public String getNickNameByAgentCode(String agentCode, String customerNo) {
		return clientNickNameRepository.findUsername(agentCode,customerNo);
	}

	@Override
	public void callStoreCustomerBirthdayDetail(String store, CustomerDetailParam param) {
		sqlManagerDb2Service.call(store, param);
	}
	
	@Override
	public CmsCommonPagination<EventsGuestDetailDto> getListGuestsOfEvent(EventsGuestSearchDto searchDto) {
		searchDto.setFunctionCode("M_EVENT_GUEST");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
				"M_EVENT_GUEST");
		searchDto.setSort(common.getSort());
		String condition = common.getSearch().trim();
		condition = condition.replace("UPPER(A.STATUS)  = UPPER('1')", "A.ATTENDANCE_TIME is not null");
		condition = condition.replace("UPPER(A.STATUS)  = UPPER('0')", "A.ATTENDANCE_TIME is null");
		
		EventsGuestParamDto paramDto = new EventsGuestParamDto();
		paramDto.eventId = searchDto.getEventId();
		paramDto.search = condition;
		paramDto.page = searchDto.getPage();
		paramDto.pageSize = searchDto.getPageSize();
		
		sqlManagerDb2Service.call("STG_DS.DS_SP_GET_LIST_GUESTS_OF_EVENT", paramDto);
		
		List<EventsGuestDetailDto> lstData = paramDto.lstData;
		CmsCommonPagination<EventsGuestDetailDto> rs = new CmsCommonPagination<>();
		rs.setTotalData(paramDto.totalRows);
		rs.setData(lstData);
		return rs;
	}
	
	@Override
	public CmsCommonPagination<CustomerPotentialDto> getListCustomerPotential(CustomerInformationSearchDto dto) {
		CustomerPotentialParam param = new CustomerPotentialParam();
		dto.setFunctionCode("CUSTOMER_POTENTIAL");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
				"CUSTOMER_POTENTIAL");
		param.agentCode = dto.getAgentCode();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();

		sqlManagerDb2Service.call(SP_GET_LIST_CUSTOMER_POTENTIAL, param);
		List<CustomerPotentialDto> datas = param.data;

		CmsCommonPagination<CustomerPotentialDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalRows);
		return resultData;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEntity exportListCustomerPotential(CustomerInformationSearchDto searchDto,
			HttpServletResponse response, Locale locale,String agentCode) {
		ResponseEntity res = null;
		try {
		    searchDto.setAgentCode(agentCode);
			searchDto.setPage(0);
			searchDto.setPageSize(0);
			searchDto.setSize(0);
			CmsCommonPagination<CustomerPotentialDto> common = getListCustomerPotential(searchDto);
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Danh_sach_khach_hang_tiem_nang.xlsx";
    		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
    		String startRow = "A7";
            
            List<CustomerPotentialDto> lstdata = common.getData();
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
			ImportExcelUtil.setListColumnExcel(CustomerPotentialExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();
			String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
    		String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
    	        Map<String, CellStyle> mapColStyle = new HashMap<>();

    	   	 	Font fontAmount = xssfWorkbook.createFont();
    	   	 	fontAmount.setFontName("Times New Roman");
    	   	 	fontAmount.setFontHeightInPoints((short) 11);
			     
    			CellStyle cellStyleMain = xssfWorkbook.createCellStyle();
    			cellStyleMain.setAlignment(HorizontalAlignment.RIGHT);
    			cellStyleMain.setWrapText(false);
    			cellStyleMain.setFont(fontAmount);
    			cellStyleMain.setBorderTop(BorderStyle.THIN);
    			cellStyleMain.setBorderLeft(BorderStyle.THIN);
    			cellStyleMain.setBorderRight(BorderStyle.THIN);
    			cellStyleMain.setBorderBottom(BorderStyle.THIN);

    			cellStyleMain.setVerticalAlignment(VerticalAlignment.CENTER);
            	
    			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
    			if(xssfSheet.getRow(2) != null)
    				xssfSheet.getRow(2).getCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
    			else xssfSheet.createRow(2).createCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
    			DecimalFormat df = new DecimalFormat("###,###,###");
    			if(xssfSheet.getRow(3) != null)
    				xssfSheet.getRow(3).getCell(0).setCellValue("Tổng số KH tiềm năng: " + df.format(common.getTotalData()));
    			else xssfSheet.createRow(3).createCell(0).setCellValue("Tổng số KH tiềm năng: " + df.format(common.getTotalData()));

            	res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata, CustomerPotentialDto.class
            			, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
				throw new Exception(e);
			}
        } catch (Exception e) {
			logger.error("Export",e);
        }
		return res;
	}
	
	@Override
	public CustomerPotentialDto getDetailCustomerPotential(String agentCode, String phoneNumber) {
		CustomerPotentialParam param = new CustomerPotentialParam();
		param.agentCode = agentCode;
		param.phoneNumber = phoneNumber;
		
		sqlManagerDb2Service.call(SP_GET_LIST_CUSTOMER_POTENTIAL, param);
		List<CustomerPotentialDto> datas = param.data;
		if (!datas.isEmpty()) {
			return datas.get(0);
		}
		return new CustomerPotentialDto();
	}
	
	@Override
	public CmsCommonPagination<CustomerInteractionHistoryDto> getListInteractionHistory(CustomerInteractionHistorySearchDto dto) {
		CustomerInteractionHistoryParam param = new CustomerInteractionHistoryParam();
		dto.setFunctionCode("CUSTOMER_INTERACTION_HISTORY");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
				"CUSTOMER_INTERACTION_HISTORY");
		param.agentCode = dto.getAgentCode();
		param.phoneNumber = dto.getPhoneNumber();
		param.proposalNo = dto.getProposalNo();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();

		sqlManagerDb2Service.call(SP_GET_LIST_INTERACTION_HISTORY, param);
		List<CustomerInteractionHistoryDto> datas = param.data;

		CmsCommonPagination<CustomerInteractionHistoryDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalRows);
		return resultData;
	}
	
	@Override
	public CustomerInteractionHistoryDto getDetailInteractionHistory(String agentCode, String phoneNumber, String proposalNo) {
		CustomerInteractionHistoryParam param = new CustomerInteractionHistoryParam();
		param.agentCode = agentCode;
		param.phoneNumber = phoneNumber;
		param.proposalNo = proposalNo;
		
		sqlManagerDb2Service.call(SP_GET_LIST_INTERACTION_HISTORY, param);
		List<CustomerInteractionHistoryDto> datas = param.data;
		if (!datas.isEmpty()) {
			return datas.get(0);
		}
		return new CustomerInteractionHistoryDto();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEntity exportListInteractionHistory(CustomerInteractionHistorySearchDto searchDto,
			HttpServletResponse response, Locale locale, String agentCode, String phoneNumber) {
		ResponseEntity res = null;
		try {
		    searchDto.setAgentCode(agentCode);
		    searchDto.setPhoneNumber(phoneNumber);
			searchDto.setPage(0);
			searchDto.setPageSize(0);
			searchDto.setSize(0);
			CmsCommonPagination<CustomerInteractionHistoryDto> common = getListInteractionHistory(searchDto);
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Lich_su_tuong_tac_chi_tiet_Khach_hang_tiem_nang.xlsx";
    		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
    		String startRow = "A6";
            
            List<CustomerInteractionHistoryDto> lstdata = common.getData();
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
			ImportExcelUtil.setListColumnExcel(CustomerInteractionHistoryExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();
			String repo = systemConfig.getConfig(SystemConfig.REPO_UPLOADED_MAIN);
    		String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
    	        Map<String, CellStyle> mapColStyle = new HashMap<>();

    	   	 	Font fontAmount = xssfWorkbook.createFont();
    	   	 	fontAmount.setFontName("Times New Roman");
    	   	 	fontAmount.setFontHeightInPoints((short) 11);
			     
    			CellStyle cellStyleMain = xssfWorkbook.createCellStyle();
    			cellStyleMain.setAlignment(HorizontalAlignment.RIGHT);
    			cellStyleMain.setWrapText(false);
    			cellStyleMain.setFont(fontAmount);
    			cellStyleMain.setBorderTop(BorderStyle.THIN);
    			cellStyleMain.setBorderLeft(BorderStyle.THIN);
    			cellStyleMain.setBorderRight(BorderStyle.THIN);
    			cellStyleMain.setBorderBottom(BorderStyle.THIN);

    			cellStyleMain.setVerticalAlignment(VerticalAlignment.CENTER);
            	
    			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
    			if(xssfSheet.getRow(1) != null)
    				xssfSheet.getRow(1).getCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
    			else xssfSheet.createRow(1).createCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
    			if(xssfSheet.getRow(2) != null)
    				xssfSheet.getRow(2).getCell(0).setCellValue("Số điện thoại: " + phoneNumber);
    			else xssfSheet.createRow(2).createCell(0).setCellValue("Số điện thoại: " + phoneNumber);

            	res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata, CustomerInteractionHistoryDto.class
            			, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);
            } catch (Exception e) {
				throw new Exception(e);
			}
        } catch (Exception e) {
			logger.error("Export",e);
        }
		return res;
	}
}

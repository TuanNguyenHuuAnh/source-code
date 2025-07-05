package vn.com.unit.ep2p.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
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

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.report.dto.ReportDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportDetailParam;
import vn.com.unit.cms.core.module.report.dto.ReportDetailSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportGetDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportGetDetailParam;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailExportDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailExportParam;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailParam;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportListDetailSearchDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.enumdef.*;
import vn.com.unit.ep2p.service.ApiReportDetailService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiReportDetailServiceImpl implements ApiReportDetailService {

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private Db2ApiService db2ApiService;

	@Autowired
	private SystemConfig systemConfig;

	private static int COUNT = 0;

	private static final Logger logger = LoggerFactory.getLogger(OfficePolicyServiceImpl.class);

	private static final String SP_REPORT_DETAIL = "RPT_ODS.DS_SP_GET_LIST_OF_K2_K2PLUS_DEATIL";
	private static final String SP_LIST_REPORT_DETAIL = "RPT_ODS.DS_SP_GET_LIST_CONTRACT_K2_K2PLUS_DEATIL";
	private static final String SP_REPORT_GROUP_DETAIL = "RPT_ODS.DS_SP_GET_LIST_OF_GROUP_K2_K2PLUS";
	private static final String DS_SP_GET_LIST_OF_K2_K2PLUS_DEATIL_LEADER = "RPT_ODS.DS_SP_GET_LIST_OF_K2_K2PLUS_DEATIL_LEADER";
	private static final String DS_SP_GET_LIST_OF_K2_K2PLUS_DETAIL_CURRENT_LEADER = "RPT_ODS.DS_SP_GET_LIST_OF_K2_K2PLUS_DETAIL_CURRENT_LEADER";
	private static final String DS_SP_GET_LIST_CONTRACT_K2_K2PLUS_DEATIL_LEADER = "RPT_ODS.DS_SP_GET_LIST_CONTRACT_K2_K2PLUS_DEATIL_LEADER";
	private static final String DS_SP_GET_LIST_OF_GROUP_K2_K2PLUS_LEADER = "RPT_ODS.DS_SP_GET_LIST_OF_GROUP_K2_K2PLUS_LEADER";
	private static final String SP_REPORT_GROUP_DETAIL_EXPORT = "";

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	@Override
	public CmsCommonPagination<ReportGetDetailDto> getReportDetailByAgentCode(ReportDetailSearchDto searchDto)
			throws DetailException {
		searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));

		searchDto.setPage(0);
		searchDto.setPageSize(10);

		ReportGetDetailParam param = new ReportGetDetailParam();
		param.agentCode = searchDto.getAgentCode();
		param.orgCode = searchDto.getOrgCode();
		param.agentGroup = searchDto.getAgentGroup();
		param.dateKey = searchDto.getYyyyMM();
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		param.sort = searchDto.getSort();
		param.search = searchDto.getSearch();
		sqlManagerDb2Service.call(SP_REPORT_DETAIL, param);
		CmsCommonPagination<ReportGetDetailDto> resultData = new CmsCommonPagination<>();
		List<ReportGetDetailDto> finalDatas = new ArrayList<>();
		List<ReportGetDetailDto> datas = param.data;
		if (ObjectUtils.isNotEmpty(datas)) {
			ReportGetDetailDto data = datas.stream().filter(x -> x.getLv3Agentcode().equals(searchDto.getAgentCode())).findFirst().orElse(new ReportGetDetailDto());
			finalDatas.add(data);
			finalDatas.stream().filter(x -> x.getK2Epp().intValue() != 0).forEach(x -> {
				x.setK2(String.valueOf((x.getK2App().multiply(new BigDecimal(100))).divide(x.getK2Epp(), 2, RoundingMode.FLOOR)).concat("%"));
			});
			finalDatas.stream().filter(x -> x.getK2plusEpp().intValue() != 0).forEach(x -> {
				x.setK2plus(String.valueOf((x.getK2plusApp().multiply(new BigDecimal(100))).divide(x.getK2plusEpp(), 2, RoundingMode.FLOOR)).concat("%"));
			});
			finalDatas.stream().filter(x -> x.getEstimateK2ToGetTheRatioK2() != null).forEach(x -> {
				x.setEstimateK2ToGetTheRatioK2(String.valueOf(new BigDecimal(x.getEstimateK2ToGetTheRatioK2())));
			});
			finalDatas.stream().filter(x -> x.getRYPNeedsToCollectlK2() != null).forEach(x -> {
				x.setRYPNeedsToCollectlK2(Double.valueOf(new Double(x.getRYPNeedsToCollectlK2())));
			});

			finalDatas.stream().filter(x -> x.getEstimateK2ToGetTheRatioK2Plus() != null).forEach(x -> {
				x.setEstimateK2ToGetTheRatioK2Plus(
						String.valueOf(new BigDecimal(x.getEstimateK2ToGetTheRatioK2Plus())));
			});
			finalDatas.stream().filter(x -> x.getRYPNeedsToCollectlK2Plus() != null).forEach(x -> {
				x.setRYPNeedsToCollectlK2Plus(Double.valueOf(new Double(x.getRYPNeedsToCollectlK2Plus())));
			});

			resultData.setData(finalDatas);
			resultData.setTotalData(param.totalData);

			return resultData;
		}
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportDetailDto> getListReportDetailByAgentCode(ReportListDetailSearchDto dto) {
		dto.setYyyyMM(formatYyyyMM(dto.getYear(), dto.getMonth()));
		ReportDetailParam param = new ReportDetailParam();
		dto.setFunctionCode("PERSONAL_REPORT_K2K2PLUS");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
				"PERSONAL_REPORT_K2K2PLUS");

		param.agentCode = dto.getAgentCode();
		param.orgCode = dto.getOrgCode();
		param.agentGroup = dto.getAgentGroup();
		param.dateKey = dto.getYyyyMM();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		try {
			sqlManagerDb2Service.call(SP_LIST_REPORT_DETAIL, param);
		} catch (Exception e) {

			logger.error("Exception", e);
		}
		List<ReportDetailDto> datas = param.datas;
		for (ReportDetailDto item : datas) {

			if (item.getK2Epp() != null && item.getK2App() != null) {
				BigDecimal result = item.getK2Epp().subtract(item.getK2App());
				item.setK2ChargesIssue(result);
			}
			if (item.getK2plusEpp() != null && item.getK2plusApp() != null) {
				BigDecimal result = item.getK2plusEpp().subtract(item.getK2plusApp());
				item.setKk2ChargesIssue(result);
			}

		}
		datas.stream().filter(x -> isNullOrZero(x.getK2ChargesIssue()) || (!isNullOrZero(x.getK2ChargesIssue()) &&x.getK2ChargesIssue().compareTo(new BigDecimal(0)) < 0))
				.forEach(x -> x.setK2ChargesIssue(new BigDecimal(0)));
		datas.stream().filter(x -> isNullOrZero(x.getKk2ChargesIssue()) || (!isNullOrZero(x.getKk2ChargesIssue()) && x.getKk2ChargesIssue().compareTo(new BigDecimal(0)) < 0))
				.forEach(x -> x.setKk2ChargesIssue(new BigDecimal(0)));

		CmsCommonPagination<ReportDetailDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public ResponseEntity exportResultDetail(ReportListDetailSearchDto searchDto, HttpServletResponse response,
			Locale locale) {
		ResponseEntity res = null;
		try {
			searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
			searchDto.setPage(0);
			searchDto.setPageSize(0);

			CmsCommonPagination<ReportDetailDto> resObj = getListReportDetailByAgentCode(searchDto);
			List<ReportDetailDto> lstdata = resObj.getData();

			if (lstdata.size() > 0)
				lstdata.stream().filter(x -> StringUtils.isNotBlank(x.getOpm())).forEach(x -> {
					if ("1".equals(x.getOpm())) {
						x.setOpm("OPM");
					} else
						x.setOpm("");
				});
			// start fill data to workbook
			List<ItemColsExcelDto> cols = new ArrayList<>();
			String template = "ReportDetail.xlsx";
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
			String templateName = "Bao_cao_chi_tiet_K2_K2plus_ViewCaNhan.xlsx";

			String startRow = "A6";

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";

			ImportExcelUtil.setListColumnExcel(ReportDetailEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();

			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				writeUpdateDateNow(searchDto, xssfWorkbook, 0, new Date());
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
						ReportDetailDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
		}
		return res;
	}

	// code o day

	private String agentPosition(String agentType, String agentCode, String agentName) {
		return agentType + ": " + agentCode + " - " + agentName;
	}

	public static boolean isNullOrZero(BigDecimal number) {
		boolean isBigDecimalValueNullOrZero = false;
		if (number == null)
			isBigDecimalValueNullOrZero = true;
		else if (number != null && number.compareTo(BigDecimal.ZERO) == 0)
			isBigDecimalValueNullOrZero = true;

		return isBigDecimalValueNullOrZero;
	}

	@SuppressWarnings("deprecation")
	@Override
	public CmsCommonPagination<ReportGroupDetailDto> getListReportGroupDetailByAgentGroup(
			ReportGroupDetailSearchDto dto) {
		dto.setYyyyMM(formatYyyyMM(dto.getYear(), dto.getMonth()));
		String store = SP_REPORT_GROUP_DETAIL;
		String storeTotal = SP_REPORT_GROUP_DETAIL;
		if (StringUtils.equalsIgnoreCase("BM", dto.getAgentGroup())
				|| StringUtils.equalsIgnoreCase("UM", dto.getAgentGroup())
				|| StringUtils.equalsIgnoreCase("FC", dto.getAgentGroup())) {
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
			try {
				store = DS_SP_GET_LIST_OF_K2_K2PLUS_DETAIL_CURRENT_LEADER;
				// nếu cutoff date tháng < tháng hiện tại lấy cấu trúc trong quá khứ
				Calendar c = Calendar.getInstance(); // this takes current date
				c.set(Calendar.DAY_OF_MONTH, 1);
				Date currentDate = c.getTime();
				String curDate = DateUtils.formatDateToString(currentDate, "yyyyMM");
				currentDate = ft.parse(curDate.concat("01"));
				Date selectedDate = ft.parse(dto.getYyyyMM().concat("01"));
				String faceMask = UserProfileUtils.getFaceMask();
				
				if (currentDate.compareTo(selectedDate) > 0 && ("BM".equals(dto.getAgentGroupLogin()) || "UM".equals(dto.getAgentGroupLogin()))) {
					Db2AgentDto hist = db2ApiService.getAgentInfoHist(dto.getAgentCode(), dto.getYyyyMM());
					if (ObjectUtils.isNotEmpty(hist)) {
						dto.setAgentGroup(hist.getAgentGroup());
						dto.setOrgCode(hist.getOrgCode());
					}
					storeTotal = DS_SP_GET_LIST_OF_GROUP_K2_K2PLUS_LEADER;
					store = DS_SP_GET_LIST_OF_K2_K2PLUS_DEATIL_LEADER;
					if (StringUtils.equalsIgnoreCase("FC", dto.getAgentGroup())) {
						store = DS_SP_GET_LIST_CONTRACT_K2_K2PLUS_DEATIL_LEADER;
					}
				}
			} catch (ParseException e) {
				logger.error("ParseException", e);
			}
		}
		CmsCommonPagination<ReportGroupDetailDto> rs = new CmsCommonPagination<>();
		List<ReportGroupDetailDto> datas = new ArrayList<>();
		ReportGroupDetailSearchDto searchBd1 = objectMapper.convertValue(dto, ReportGroupDetailSearchDto.class);
		ReportGroupDetailSearchDto searchBd2 = objectMapper.convertValue(dto, ReportGroupDetailSearchDto.class);
		try {
			if (dto.getAgentGroup().equals("UM") || dto.getAgentGroup().equals("BM")) {
				rs = callStoreUm(store, storeTotal, dto, searchBd1, searchBd2);
			} else {
				ReportGroupDetailParam param = new ReportGroupDetailParam();
				param.agentCode = dto.getAgentCode();
				param.orgCode = dto.getOrgCode();
				param.agentGroup = dto.getAgentGroup();
				param.dateKey = dto.getYyyyMM();
				param.page = dto.getPage();
				param.pageSize = dto.getPageSize();
				param.sort = dto.getSort();
				param.search = searchAdvance(searchBd1, searchBd2, dto, dto.getAgentGroup(), dto.getKeyword());

				try {
					sqlManagerDb2Service.call(store, param);
				} catch (Exception e) {
					logger.error("Call " + store, e);
				}
				datas = param.data;
				sumPolicy(datas, 0);
				groupAgent(dto, datas);
				BigDecimal result;
				BigDecimal resultItem;
				int t = 0;
				for (ReportGroupDetailDto item : datas) {
					item.setAgentAll(agentPosition(item.getAgentType(), item.getAgentCode(), item.getAgentName()));
					item.setNo(t);
					t = t + 1;
					mapAgent(item, dto.getAgentGroup(), item.getTreeLevel() == 0);
					if (item.getK2Epp() != null && item.getK2App() != null) {
						result = item.getK2App().subtract(item.getK2Epp());
						if (isNullOrZero(result) || (!isNullOrZero(result) && result.compareTo(new BigDecimal(0)) < 0)) {
							result = new BigDecimal(0);
						}
						item.setK2ChargesIssue(result);
					}
					if (item.getK2plusEpp() != null && item.getK2plusApp() != null) {
						resultItem = item.getK2App().subtract(item.getK2Epp());
						if (isNullOrZero(resultItem) || (!isNullOrZero(resultItem) && resultItem.compareTo(new BigDecimal(0)) < 0)) {
							resultItem = new BigDecimal(0);
						}
						item.setKk2ChargesIssue(resultItem);
					}
				}
				ReportGroupDetailDto root = new ReportGroupDetailDto();
				List<ReportGroupDetailDto> allData = new ArrayList<>();
				List<ReportGroupDetailDto> lstdata = datas;

				lstdata.stream().filter(x -> x.getK2() != null).forEach(x -> {
					x.setK2(String.valueOf(
							new BigDecimal(x.getK2()).multiply(new BigDecimal(100)).setScale(2, RoundingMode.FLOOR))
							.concat("%"));
				});
				lstdata.stream().filter(x -> x.getK2Epp().intValue() == 0).forEach(x -> {
					x.setK2("");
				});
						
				lstdata.stream().filter(x -> x.getK2plus() != null).forEach(x -> {
					x.setK2plus(String.valueOf(new BigDecimal(x.getK2plus()).multiply(new BigDecimal(100)).setScale(2,
							RoundingMode.FLOOR)).concat("%"));
				});
				lstdata.stream().filter(x -> x.getK2plusEpp().intValue() == 0).forEach(x -> {
					x.setK2plus("");
				});
				datas.stream().forEach(item -> {
					if (item.getK2Epp() != null && item.getK2App() != null) {
						item.setK2ChargesIssue(item.getK2Epp().subtract(item.getK2App()));
						if (isNullOrZero(item.getK2ChargesIssue()) || (!isNullOrZero(item.getK2ChargesIssue()) && item.getK2ChargesIssue().compareTo(new BigDecimal(0)) < 0)) {
							item.setK2ChargesIssue(new BigDecimal(0));
						}
					}
					if (item.getK2plusEpp() != null && item.getK2plusApp() != null) {
						item.setKk2ChargesIssue(item.getK2plusEpp().subtract(item.getK2plusApp()));
						if (isNullOrZero(item.getKk2ChargesIssue()) || (!isNullOrZero(item.getKk2ChargesIssue()) && item.getKk2ChargesIssue().compareTo(new BigDecimal(0)) < 0)) {
							item.setKk2ChargesIssue(new BigDecimal(0));
						}
					}
				});

				ReportGroupDetailDto rootUMBM = null;
				if ("BM".equalsIgnoreCase(dto.getAgentGroup()) && StringUtils.isNotEmpty(dto.getKeyword())) {

					if (lstdata.isEmpty() == false) {

						for (ReportGroupDetailDto item : lstdata) {
							if (item.getTreeLevel() == 0 && "BM".equalsIgnoreCase(dto.getAgentGroup())) {
								rootUMBM = item;
							}
						}
						lstdata.remove(rootUMBM);

					}
					if (rootUMBM != null) {
						if ("BM".equalsIgnoreCase(dto.getAgentGroup())) {
							rootUMBM.setAgentAll(null);

						} else if ("RH".equalsIgnoreCase(dto.getAgentGroup())) {
							rootUMBM.setAgentName(null);
						}
						lstdata.add(rootUMBM);
					}

					rs.setTotalData(param.totalData);
					rs.setData(allData.size() > 1 ? allData : null);
					return rs;
				} else if ("BM".equalsIgnoreCase(dto.getAgentGroup())
						&& (dto.getAgentAll() != null || dto.getUnitName() != null)) {

					if (lstdata.isEmpty() == false) {

						for (ReportGroupDetailDto item : lstdata) {
							if (item.getTreeLevel() == 0 && "BM".equalsIgnoreCase(dto.getAgentGroup())) {
								rootUMBM = item;
							}
						}
						lstdata.remove(rootUMBM);
					}
					if (rootUMBM != null) {

						if ("BM".equalsIgnoreCase(dto.getAgentGroup())) {
							rootUMBM.setAgentAll(null);
						} else if ("RH".equalsIgnoreCase(dto.getAgentGroup())) {
							rootUMBM.setAgentName(null);
						}

						lstdata.add(rootUMBM);
					}

					rs.setTotalData(param.totalData);
					rs.setData(allData.size() > 1 ? allData : null);
					return rs;
				}

				if (lstdata.isEmpty() == false) {
					root = lstdata.stream().filter(e -> e.getTreeLevel() == 0).findFirst().get();

					// get lv1
					List<ReportGroupDetailDto> lv1 = lstdata.stream().filter(e -> e.getTreeLevel() == 1)
							.collect(Collectors.toList());
					for (ReportGroupDetailDto groupLv1 : lv1) {
						allData.add(groupLv1);
						// get lv2
						List<ReportGroupDetailDto> lv2 = lstdata.stream()
								.filter(e -> e.getTreeLevel() == 2
										&& e.getParentAgentCode().equals(groupLv1.getAgentCode())
										&& e.getOrgParentId().equals(groupLv1.getOrgId()))
								.collect(Collectors.toList());
						allData.addAll(lv2);
					}
					if ("BM".equalsIgnoreCase(dto.getAgentGroup())) {
						root.setAgentAll(null);
					} else if ("RH".equalsIgnoreCase(dto.getAgentGroup())) {
						root.setAgentName(null);
					}
					allData.add(root);
				}

				rs.setTotalData(param.totalData);
				rs.setData(allData.size() > 1 ? allData : null);
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return rs;
	}

	private void groupAgent(ReportGroupDetailSearchDto searchDto, List<ReportGroupDetailDto> datas) {
		if ("AH".equals(searchDto.getAgentGroup())) {
			List<ReportGroupDetailDto> listTree2 = new ArrayList<ReportGroupDetailDto>();
			List<ReportGroupDetailDto> lstTree1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1)))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(lstTree1)) {
				lstTree1.forEach(item -> {
					List<ReportGroupDetailDto> lstTree2 = datas.stream()
							.filter(e -> e.getTreeLevel().equals(new Integer(2))
									&& e.getOrgParentId().equals(item.getOrgId())
									&& e.getParentAgentCode().equals(item.getAgentCode()))
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(lstTree2)) {

						if ("TH".equals(searchDto.getAgentGroup())) {
							Map<String, List<ReportGroupDetailDto>> maplv2Group = lstTree2.stream()
									.filter(e -> e.getTreeLevel() == 2)
									.collect(Collectors.groupingBy(bd -> bd.getAgentCode()));
							for (Map.Entry<String, List<ReportGroupDetailDto>> entry : maplv2Group.entrySet()) {
								String key = entry.getKey();
								if (StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty(entry.getValue())) {
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
				Map<String, List<ReportGroupDetailDto>> maplv1 = lstTree1.stream().filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.groupingBy(bd -> bd.getAgentCode()));
				if (CollectionUtils.isNotEmpty(maplv1.entrySet())) {
					datas.removeIf(e -> e.getTreeLevel().equals(new Integer(1)));
					for (Map.Entry<String, List<ReportGroupDetailDto>> entry : maplv1.entrySet()) {
						String key = entry.getKey();
						if (StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty(entry.getValue())) {
							sumPolicy(entry.getValue(), 1);
							datas.addAll(entry.getValue());
						}
					}
					datas.addAll(listTree2);
				}

			}
		}
	}

	public CmsCommonPagination<ReportGroupDetailDto> callStoreUm(String store, String storeTotal,
			ReportGroupDetailSearchDto dto, ReportGroupDetailSearchDto searchBd1, ReportGroupDetailSearchDto searchBd2)
			throws JsonProcessingException {
		List<ReportGroupDetailDto> datas = new ArrayList<>();
		CmsCommonPagination<ReportGroupDetailDto> rs = new CmsCommonPagination<>();

		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			dto.setFunctionCode("GROUP_" + dto.getAgentGroup());
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			logger.error("Exception", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
				"GROUP_" + dto.getAgentGroup());

		ReportGetDetailParam param = new ReportGetDetailParam();
		param.agentCode = dto.getAgentCode();
		param.orgCode = dto.getOrgCode();
		param.agentGroup = dto.getAgentGroup();
		param.dateKey = dto.getYyyyMM();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.sort = common.getSort();
		String db = null;
		if (StringUtils.isNotEmpty(common.getSearch())) {
			String db1 = common.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.LV3_AGENTNAME,''))");
			String db2 = common.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.LV3_AGENTCODE,''))");
			String db2_1 = db2.replace("and", "OR");
			String db3 = common.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.LV3_AGENTTYPE,''))");
			String db3_1 = db3.replace("and", "OR");
			db = db1 + db2_1 + db3_1;
		}
		param.search = db;
		sqlManagerDb2Service.call(store, param);
		List<ReportGetDetailDto> dataUm = param.data;
		datas.addAll(dataUm);
		datas.stream().forEach(item -> item.setUnitName(agentPosition(item.getLv2Agenttype(),
				item.getLv2Agentcode().replace("A", "").replace("B", "").replace("C", ""), item.getLv2Agentname())));
		datas.stream().forEach(item -> item
				.setAgentAll(agentPosition(item.getLv3Agenttype(), item.getLv3Agentcode(), item.getLv3Agentname())));
        for (ReportGroupDetailDto item : datas) {
            if(!isNullOrZero(item.getK2Epp())) {
            	item.setK2(String.valueOf((item.getK2App().multiply(new BigDecimal(100))).divide(item.getK2Epp(), 2, RoundingMode.FLOOR)).concat("%"));
            } else if (!isNullOrZero(item.getK2App())) {
            	item.setK2("0.00%");
            } else {
            	item.setK2("");
            }
            if(!isNullOrZero(item.getK2plusEpp())) {
            	item.setK2plus(String.valueOf((item.getK2plusApp().multiply(new BigDecimal(100))).divide(item.getK2plusEpp(), 2, RoundingMode.FLOOR)).concat("%"));
            } else if (!isNullOrZero(item.getK2App())) {
            	item.setK2plus("0.00%");
            } else {
            	item.setK2plus("");
            }
        }
		datas.stream().forEach(item -> item.setTreeLevel(1));
		datas.stream().forEach(item -> {
			if (item.getK2Epp() != null && item.getK2App() != null) {
				item.setK2ChargesIssue(item.getK2Epp().subtract(item.getK2App()));
				if (isNullOrZero(item.getK2ChargesIssue()) || (!isNullOrZero(item.getK2ChargesIssue()) && item.getK2ChargesIssue().compareTo(new BigDecimal(0)) < 0)) {
					item.setK2ChargesIssue(new BigDecimal(0));
				}
			}
			if (item.getK2plusEpp() != null && item.getK2plusApp() != null) {
				item.setKk2ChargesIssue(item.getK2plusEpp().subtract(item.getK2plusApp()));
				if (isNullOrZero(item.getKk2ChargesIssue()) || (!isNullOrZero(item.getKk2ChargesIssue()) && item.getKk2ChargesIssue().compareTo(new BigDecimal(0)) < 0)) {
					item.setKk2ChargesIssue(new BigDecimal(0));
				}
			}
		});
		if (CollectionUtils.isNotEmpty(dataUm)) {
			datas.add(callGrandtotal(dto, searchBd1, searchBd2, storeTotal));
		}
		rs.setTotalData(param.totalData);
		rs.setData(datas);
		return rs;
	}

	public ReportGroupDetailDto callGrandtotal(ReportGroupDetailSearchDto dto, ReportGroupDetailSearchDto searchBd1,
			ReportGroupDetailSearchDto searchBd2, String storeTotal) throws JsonProcessingException {
		ReportGroupDetailDto grandTotal = new ReportGroupDetailDto();
		ReportGroupDetailParam paramUm = new ReportGroupDetailParam();
		paramUm.agentCode = dto.getAgentCode();
		paramUm.orgCode = dto.getOrgCode();
		paramUm.agentGroup = dto.getAgentGroup();
		paramUm.dateKey = dto.getYyyyMM();
		paramUm.page = 0;
		paramUm.pageSize = dto.getPageSize();
		paramUm.sort = dto.getSort();
		paramUm.search = searchAdvance(searchBd1, searchBd2, dto, dto.getAgentGroup(), dto.getKeyword());
		sqlManagerDb2Service.call(storeTotal, paramUm);

		if (dto.getAgentGroup().equals("UM"))
			grandTotal.setAgentAll("Tổng cộng");
		grandTotal.setUnitName("Tổng cộng");
		grandTotal.setTreeLevel(0);
		if (CollectionUtils.isEmpty(paramUm.data)) {
			return grandTotal;
		}
		ReportGroupDetailDto root = paramUm.data.stream().filter(x -> x.getTreeLevel() == 0).findFirst().get();
		if (!isNullOrZero(root.getK2Epp())) {
			grandTotal.setK2Epp(root.getK2Epp());
		}
		if (!isNullOrZero(root.getK2App())) {
			grandTotal.setK2App(root.getK2App());
		}
		if(!isNullOrZero(root.getK2App()) && !isNullOrZero(root.getK2Epp())) {
			grandTotal.setK2(String.valueOf((root.getK2App().multiply(new BigDecimal(100))).divide(root.getK2Epp(), 2, RoundingMode.FLOOR)).concat("%"));
        }

		if (!isNullOrZero(root.getK2Epp())) {
			grandTotal.setK2ChargesIssue(root.getK2Epp().subtract(root.getK2App()));
			if (isNullOrZero(grandTotal.getK2ChargesIssue()) || (!isNullOrZero(grandTotal.getK2ChargesIssue()) && grandTotal.getK2ChargesIssue().compareTo(new BigDecimal(0)) < 0)) {
				grandTotal.setK2ChargesIssue(new BigDecimal(0));
			}
		}
		if (!isNullOrZero(root.getK2plusEpp())) {
			grandTotal.setKk2ChargesIssue(root.getK2plusEpp().subtract(root.getK2plusApp()));
			if (isNullOrZero(grandTotal.getKk2ChargesIssue()) || (!isNullOrZero(grandTotal.getKk2ChargesIssue()) && grandTotal.getKk2ChargesIssue().compareTo(new BigDecimal(0)) < 0)) {
				grandTotal.setKk2ChargesIssue(new BigDecimal(0));
			}
			grandTotal.setK2plusEpp(root.getK2plusEpp());
		}
		if (!isNullOrZero(root.getK2plusApp())) {
			grandTotal.setK2plusApp(root.getK2plusApp());
		}
		if(!isNullOrZero(root.getK2plusApp()) && !isNullOrZero(root.getK2plusEpp())) {
			grandTotal.setK2plus(String.valueOf((root.getK2plusApp().multiply(new BigDecimal(100))).divide(root.getK2plusEpp(), 2, RoundingMode.FLOOR)).concat("%"));
        }
		return grandTotal;
	}

	private void sumPolicy(List<ReportGroupDetailDto> datas, int treeLevel) {
		BigDecimal result = new BigDecimal(0);
		if (!datas.isEmpty()) {
			List<ReportGroupDetailDto> lstRoot = datas.stream()
					.filter(e -> e.getTreeLevel().equals(new Integer(treeLevel))).collect(Collectors.toList());
			List<String> adresses = lstRoot.stream().map(ReportGroupDetailDto::getOrgName).collect(Collectors.toList());
			String orgNameNew = String.join(", ", adresses);
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(treeLevel)));
			if (!lstRoot.isEmpty()) {
				ReportGroupDetailDto dto = lstRoot.get(0);
				if (treeLevel == 1 && "OH".equalsIgnoreCase(dto.getChildGroup())) {
					dto.setOrgId(dto.getAgentCode());
				}
				dto.setOrgNameNew(orgNameNew);
				dto.setSumK2epp(lstRoot.stream().filter(x -> !isNullOrZero(x.getK2Epp()))
						.map(ReportGroupDetailDto::getK2Epp).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				dto.setSumK2app(lstRoot.stream().filter(x -> !isNullOrZero(x.getK2App()))
						.map(ReportGroupDetailDto::getK2App).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				if (!isNullOrZero(dto.getSumK2epp()) && !isNullOrZero(dto.getSumK2app())) {
					dto.setSumK2(dto.getSumK2app().divide(dto.getSumK2epp(), 4, RoundingMode.HALF_UP).toString());
				}
				if (dto.getSumK2epp() != null && dto.getSumK2app() != null) {
					result = dto.getSumK2epp().subtract(dto.getSumK2app());
					if (isNullOrZero(result) || (!isNullOrZero(result) && result.compareTo(new BigDecimal(0)) < 0)) {
						result = new BigDecimal(0);
					}
					dto.setSumK2issue(result);
				}

				dto.setSumK2pepp(lstRoot.stream().filter(x -> !isNullOrZero(x.getK2plusEpp()))
						.map(ReportGroupDetailDto::getK2plusEpp).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				dto.setSumK2papp(lstRoot.stream().filter(x -> !isNullOrZero(x.getK2plusApp()))
						.map(ReportGroupDetailDto::getK2plusApp).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				if (!isNullOrZero(dto.getSumK2pepp()) && !isNullOrZero(dto.getSumK2papp())) {
					dto.setSumK2p(dto.getSumK2papp().divide(dto.getSumK2pepp(), 4, RoundingMode.HALF_UP).toString());
				}
				if (dto.getSumK2pepp() != null && dto.getSumK2papp() != null) {
					result = dto.getSumK2pepp().subtract(dto.getSumK2papp());
					if (isNullOrZero(result) || (!isNullOrZero(result) && result.compareTo(new BigDecimal(0)) < 0)) {
						result = new BigDecimal(0);
					}
					dto.setSumk2pissue(result);
				}
				dto.setK2App(dto.getSumK2app());
				dto.setK2Epp(dto.getSumK2epp());
				dto.setK2(dto.getSumK2());
				dto.setK2ChargesIssue(dto.getSumK2issue());

				dto.setK2plusApp(dto.getSumK2papp());
				dto.setK2plusEpp(dto.getSumK2pepp());
				dto.setK2plus(dto.getSumK2p());
				dto.setK2ChargesIssue(dto.getSumk2pissue());
				datas.add(dto);
			}
		}
	}

	private void setConditionSearch(ReportGroupDetailSearchDto data, int level) {
		if (StringUtils.isNotEmpty(data.getAgentGroup())) {
			switch (data.getAgentGroup()) {
			case "CAO":
				if (level == 1)
					data.setBdahName(null);
				if (level == 2)
					data.setBdthName(null);
				break;
			case "TH":
				if (level == 1)
					data.setBdrhName(null);
				if (level == 2)
					data.setBdahName(null);
				break;
			case "AH":
				if (level == 1)
					data.setBdohName(null);
				if (level == 2)
					data.setBdrhName(null);
				break;
			case "RH":
				if (level == 1)
					data.setGaName(null);
				if (level == 2)
					data.setBdohName(null);
				break;
			case "OH":
				if (level == 1)
					data.setBranchName(null);
				if (level == 2)
					data.setGaName(null);
				break;
			case "GA":
				if (level == 1)
					data.setUnitName(null);
				if (level == 2)
					data.setBranchName(null);
				break;
			default:
				break;
			}
		}

	}

	private void mapAgent(ReportGroupDetailDto data, String agentGroup, boolean first) {
		if (StringUtils.isNotEmpty(data.getChildGroup())) {
			if (first) {
				switch (data.getChildGroup()) {
				case "CAO":
					data.setBdthCode(data.getAgentCode());
					data.setBdthName("Tổng cộng");
					data.setBdthType(data.getAgentType());
					data.setNo(null);
					break;
				case "TH":
					data.setBdahCode(data.getAgentCode());
					data.setBdahName("Tổng cộng");
					data.setBdahType(data.getAgentType());
					data.setNo(null);
					break;
				case "AH":
					data.setBdohCode(data.getAgentCode());
					data.setBdohName("Tổng cộng");
					data.setBdohType(data.getAgentType());
					data.setNo(null);
					break;
				case "RH":
					data.setBdohCode(data.getAgentCode());
					data.setBdohName("Tổng cộng");
					data.setBdohType(data.getAgentType());
					data.setNo(null);
					break;
				case "OH":
					data.setGaCode(data.getAgentCode());
					data.setGaName("Tổng cộng");
					data.setGaType(data.getAgentType());
					data.setNo(null);
					break;
				case "GA":
					data.setBranchCode(data.getAgentCode());
					data.setBranchName("Tổng cộng");
					data.setBranchType(data.getAgentType());
					data.setNo(null);
					break;
				case "BM":
					data.setUnitCode(data.getAgentCode());
					data.setUnitName("Tổng cộng");
					data.setUnitType(data.getAgentType());
					data.setNo(null);
					break;
				case "UM":
					data.setUnitCode(data.getAgentCode());
					data.setUnitName("Tổng cộng");
					data.setUnitType(data.getAgentType());
					data.setNo(null);
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
					data.setBdohCode(data.getAgentCode());
					String orgId = data.getAgentType();
					if (data.getTreeLevel() == 2) {
						orgId = data.getOrgId();
						data.setOrgCode(data.getOrgId());
					}

					data.setBdohName(orgId + "-" + data.getAgentCode() + "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setBdohName(data.getAgentName());

					}
					data.setBdohType(data.getAgentType());
					break;
				case "GA":
					data.setGaCode(data.getAgentCode());
					data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setGaName(data.getAgentName());

					}
					data.setGaType(data.getAgentType());
					break;
				case "SO":
					data.setGaCode(data.getAgentCode());
					data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setGaName(data.getAgentName());

					}
					data.setGaType(data.getAgentType());
					break;
				case "BM":
					data.setBranchCode(data.getAgentCode());
					data.setBranchName(data.getAgentType() + ": " + data.getAgentCode().replace(data.getOrgId(), "")
							+ "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setBranchName(data.getAgentName());

					}
					data.setBranchType(data.getAgentType());
					break;
				case "UM":
					data.setUnitCode(data.getAgentCode());
					data.setUnitName(data.getAgentType() + ": "
							+ data.getAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-"
							+ data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setUnitName(data.getAgentName());

					}
				default:
					data.setAgentCode(data.getAgentCode());
					break;
				}
			}
		}
	}

	public CmsCommonPagination<ReportGroupDetailExportDto> getListExportReportGroupDetailByAgentGroup(
			ReportGroupDetailSearchDto dto) {
		dto.setYyyyMM(formatYyyyMM(dto.getYear(), dto.getMonth()));
		ReportGroupDetailExportParam param = new ReportGroupDetailExportParam();
		param.agentCode = dto.getAgentCode();
		param.agentGroup = dto.getAgentGroup();
		param.orgCode = dto.getOrgCode();
		sqlManagerDb2Service.call(SP_REPORT_GROUP_DETAIL_EXPORT, param);
		List<ReportGroupDetailExportDto> datas = param.data;
		CmsCommonPagination<ReportGroupDetailExportDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		return resultData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportResulGrouptDetail(ReportGroupDetailSearchDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(0);
			searchDto.setSize(0);
			searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
			CmsCommonPagination<ReportGroupDetailDto> resObj = getListReportGroupDetailByAgentGroup(searchDto);
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String template = "";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook

			if ("UM".equals(searchDto.getAgentGroup())) {
				template = "RepostGroupDetailUM.xlsx";
				ImportExcelUtil.setListColumnExcel(EnumExportReportGroupDetailUM.class, cols);
			} else if ("RH".equals(searchDto.getAgentGroup())) {
				template = "RepostGroupDetailRH.xlsx";
				ImportExcelUtil.setListColumnExcel(EnumExportReportGroupDetailRH.class, cols);
			} else if ("OH".equals(searchDto.getAgentGroup()) || "GA".equals(searchDto.getAgentGroup())
					|| "SO".equals(searchDto.getAgentGroup())) {
				template = "RepostGroupDetailOH.xlsx";
				ImportExcelUtil.setListColumnExcel(EnumExportReportGroupDetailOH.class, cols);
			} else {
				template = "RepostGroupDetail.xlsx";
				ImportExcelUtil.setListColumnExcel(EnumExportReportGroupDetailCAO.class, cols);
			}
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);

			String templateName = null;
			if ("CAO".equals(searchDto.getAgentGroup())) {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewCAO.xlsx";
			} else if ("TH".equals(searchDto.getAgentGroup())) {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewTH.xlsx";
			} else if ("AH".equals(searchDto.getAgentGroup())) {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewAH.xlsx";
			} else if ("RH".equals(searchDto.getAgentGroup())) {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewRH.xlsx";
			} else if ("OH".equals(searchDto.getAgentGroup())) {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewOH.xlsx";
			} else if ("GA".equals(searchDto.getAgentGroup())) {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewGA.xlsx";
			} else if ("SO".equals(searchDto.getAgentGroup())) {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewSO.xlsx";
			} else if ("BM".equals(searchDto.getAgentGroup())) {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewBM.xlsx";
			} else {
				templateName = "Bao_cao_chi_tiet_K2_K2plus_cap_phong_ban_viewUM.xlsx";
			}

			String startRow = "A6";
			List<ReportGroupDetailDto> lstdata = resObj.getData();
			if (ObjectUtils.isNotEmpty(lstdata))
				addDateInColumnManPower(lstdata, searchDto, searchDto.getAgentGroup());
			// SR14140 - Điều chỉnh cách hiển thị K2 và K2+ đối với cá nhân/nhóm/phòng chưa có K2 và K2+
			/*for (ReportGroupDetailDto item : lstdata) {
				if (item.getK2Epp().intValue() == 0) {
					item.setK2("");
				}
				if (item.getK2plusEpp().intValue() == 0) {
					item.setK2plus("");
				}
			}*/

			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;

				writeDateNow(searchDto.getAgentGroup(), searchDto, xssfWorkbook, 0, new Date());
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						ReportGroupDetailDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);

			} catch (Exception e) {
				logger.error("##ExportList##", e.getMessage());
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	public void addDateInColumnManPower(List<ReportGroupDetailDto> lstdata, ReportGroupDetailSearchDto searchDto,
			String agentType) {

		for (ReportGroupDetailDto ls : lstdata) {

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
			if (StringUtils.endsWithIgnoreCase(agentType, "OH")) {
				ls.setParen(ls.getGaName());
				ls.setChild(ls.getBranchName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "GA")) {
				ls.setParen(ls.getBranchName());
				ls.setChild(ls.getUnitName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "SO")) {
				ls.setParen(ls.getBranchName());
				ls.setChild(ls.getUnitName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "BM")) {
				ls.setParen(ls.getUnitName());
				ls.setChild(ls.getAgentAll());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "UM")) {
				ls.setChild(ls.getAgentAll());
			}

		}
	}

	public void writeDateNow(String agentType, ReportGroupDetailSearchDto searchDto, XSSFWorkbook xssfWorkbook,
			int sheetNumber, Date runDate) throws IOException {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		XSSFRow row = null;
		XSSFRow rowTitle = null;
		XSSFRow rowTitleName = null;

		int Index1 = 0;
		int Index2 = 1;

		rowTitle = xssfSheet.getRow(1);
		rowTitleName = xssfSheet.getRow(0);
		XSSFCell cellIndex3 = rowTitle.getCell(Index1);
		XSSFCell cellIndex4 = rowTitleName.getCell(Index1);
		if (cellIndex3 == null)
			cellIndex3 = rowTitle.createCell(Index1);
		if (cellIndex4 == null)
			cellIndex4 = rowTitle.createCell(Index1);
		row = xssfSheet.getRow(3);

		XSSFCell cellIndex1 = row.getCell(Index1);
		if (cellIndex1 == null)
			cellIndex1 = row.createCell(Index1);
		XSSFCell cellIndex2 = row.getCell(Index2);
		if (cellIndex2 == null)
			cellIndex2 = row.createCell(Index2);

		cellIndex3.setCellValue("Cập nhật đến ngày " + DateUtils.formatDateToString(new Date(runDate.getTime() -  86400000), "dd/MM/yyyy"));
		if (Integer.valueOf(searchDto.getMonth()) < 10) {
			searchDto.setMonth("0" + searchDto.getMonth());
		}
		if (StringUtils.endsWithIgnoreCase(agentType, "CAO")) {
			cellIndex1.setCellValue("BDTH");
			cellIndex2.setCellValue("BDAH");
			cellIndex4.setCellValue(
					"BÁO CÁO CHI TIẾT K2/K2+ CẤP PHÒNG BAN VIEW CAO " + searchDto.getMonth() + "/" + searchDto.getYear());

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "TH")) {
			cellIndex1.setCellValue("BDAH");
			cellIndex2.setCellValue("BDOH");
			cellIndex4.setCellValue(
					"BÁO CÁO CHI TIẾT K2/K2+ CẤP PHÒNG BAN VIEW TH " + searchDto.getMonth() + "/" + searchDto.getYear());

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "AH")) {
			cellIndex1.setCellValue("BDOH");
			cellIndex2.setCellValue("Văn phòng/Tổng đại lý");
			cellIndex4.setCellValue(
					"BÁO CÁO CHI TIẾT K2/K2+ CẤP PHÒNG BAN VIEW AH " + searchDto.getMonth() + "/" + searchDto.getYear());

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "OH")) {
			cellIndex1.setCellValue("Văn phòng/Tổng đại lý");
			cellIndex2.setCellValue("Tên GAD");
			cellIndex4.setCellValue(
					"BÁO CÁO CHI TIẾT K2/K2+ CẤP PHÒNG BAN VIEW OH " + searchDto.getMonth() + "/" + searchDto.getYear());

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "GA")) {
			cellIndex1.setCellValue("Tên GAD");
			cellIndex2.setCellValue("Quản lý");
			cellIndex4.setCellValue(
					"BÁO CÁO CHI TIẾT K2/K2+ CẤP PHÒNG BAN VIEW GA " + searchDto.getMonth() + "/" + searchDto.getYear());

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "Office")) {
			cellIndex1.setCellValue("Văn phòng/Tổng đại lý");
			cellIndex2.setCellValue("Tên GAD");
			cellIndex4.setCellValue("BÁO CÁO CHI TIẾT K2/K2+ CẤP PHÒNG BAN VIEW Office " + searchDto.getMonth() + "/"
					+ searchDto.getYear());

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "BM")) {
			cellIndex1.setCellValue("Quản lý");
			cellIndex2.setCellValue("Tư vấn tài chính");
			cellIndex4.setCellValue(
					"BÁO CÁO CHI TIẾT K2/K2+ CẤP PHÒNG BAN " + searchDto.getMonth() + "/" + searchDto.getYear());

		}
		if (StringUtils.endsWithIgnoreCase(agentType, "UM")) {
			cellIndex1.setCellValue("Tư vấn tài chính");
			cellIndex4.setCellValue(
					"BÁO CÁO CHI TIẾT K2/K2+ CẤP PHÒNG BAN  " + searchDto.getMonth() + "/" + searchDto.getYear());

		}

	}

	private String formatYyyyMM(String year, String month) {
		Date currentDate = new Date();
		if (month != null && Integer.parseInt(month) < 10) {
			month = ("0" + Integer.parseInt(month));
		}
		// default current date
		if (year == null) {
			year = (new SimpleDateFormat("yyyy").format(currentDate));
		}
		if (month == null) {
			month = (new SimpleDateFormat("MM").format(currentDate));
		}
		String yyyyMM = year + month;
		return yyyyMM;
	}

	public void writeUpdateDateNow(ReportListDetailSearchDto searchDto, XSSFWorkbook xssfWorkbook, int sheetNumber,
			Date runDate) throws IOException {

		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		XSSFRow rowTitle = null;
		XSSFRow rowTitleName = null;

		int Index1 = 0;

		rowTitle = xssfSheet.getRow(2);
		rowTitleName = xssfSheet.getRow(0);
		XSSFCell cellIndex3 = rowTitle.getCell(Index1);
		XSSFCell cellIndex4 = rowTitleName.getCell(Index1);

		cellIndex3.setCellValue("Cập nhật đến ngày " + DateUtils.formatDateToString(new Date(runDate.getTime() -  86400000), "dd/MM/yyyy"));

		if (Integer.valueOf(searchDto.getMonth()) < 10) {
			searchDto.setMonth("0" + searchDto.getMonth());
		}

		cellIndex4.setCellValue(
				"DANH SÁCH HỢP ĐỒNG ẢNH HƯỞNG K2/K2+ " + searchDto.getMonth() + "/" + searchDto.getYear());

	}

	private String searchAdvance(ReportGroupDetailSearchDto searchBd1, ReportGroupDetailSearchDto searchBd2,
			ReportGroupDetailSearchDto dto, String agentGroup, String keyword) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		String bd1 = "1 = 1";
		String bd2 = "1 = 1";
		String stringJsonParam = null;
		try {
			setConditionSearch(searchBd1, 1);
			stringJsonParam = mapper.writeValueAsString(searchBd1);
		} catch (JsonProcessingException e) {
			logger.error("getListOfficeDocument", e);
		}
		stringJsonParam = mapper.writeValueAsString(dto);
		CommonSearchWithPagingDto commonLv1 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam,
				agentGroup);

		if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
			logger.info(commonLv1.getSearch());
			String bd11 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_CODE,''))");
			String bd12 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.ORG_NAME,''))");
			if (("UM".equalsIgnoreCase(agentGroup) || "BM".equalsIgnoreCase(agentGroup))) {
				bd12 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_TYPE,''))");
			}
			bd1 = commonLv1.getSearch() + " OR " + bd11 + " OR " + bd12;
		}

		try {
			setConditionSearch(searchBd2, 2);
			stringJsonParam = mapper.writeValueAsString(searchBd2);
		} catch (JsonProcessingException e) {
			logger.error("getListOfficeDocument", e);
		}
		CommonSearchWithPagingDto commonLv2 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam,
				agentGroup);
		if (StringUtils.isNotEmpty(commonLv2.getSearch())) {
			String bd21 = commonLv2.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_CODE,''))");
			String bd22 = commonLv2.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_TYPE,''))");
			bd2 = commonLv2.getSearch() + " OR " + bd21 + " OR " + bd22;
			if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
				logger.info(commonLv2.getSearch());
				// lay data cap con thuoc cap cha
				bd2 = bd2 + " AND " + commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
						"UPPER(nvl(L.Parent_Agent_Name,''))");
			}
		} else {
			if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
				// lay data cap con thuoc cap cha
				String bd21 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
						"UPPER(nvl(L.Parent_Agent_Name,''))");
				String bd22 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
						"UPPER(nvl(L.PARENT_AGENT_CODE,''))");
				String bd23 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
						"UPPER(nvl(L.ORG_PARENT_NAME,''))");
				if (("UM".equalsIgnoreCase(agentGroup) || "BM".equalsIgnoreCase(agentGroup))) {
					bd23 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))", "UPPER(nvl(L.AGENT_TYPE,''))");
				}
				bd2 = bd21 + " OR " + bd22 + " OR " + bd23;
			}
		}
		String searchAll = " ";
		if (StringUtils.isNotEmpty(keyword)) {
			searchAll = "AND (L.TREE_LEVEL = 0  " + "OR (((UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%" + keyword
					+ "%') OR UPPER(nvl(L.AGENT_CODE,''))  LIKE UPPER(N'%" + keyword
					+ "%') OR UPPER(nvl(L.ORG_NAME,''))  LIKE UPPER(N'%" + keyword + "%') )  and L.TREE_LEVEL = 1)"
					+ "OR (UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%" + keyword
					+ "%')  AND UPPER(nvl(L.Parent_Agent_Name,''))  LIKE UPPER(N'%" + keyword
					+ "%')  and L.TREE_LEVEL = 2)" + "OR (UPPER(nvl(L.AGENT_CODE,''))  LIKE UPPER(N'%" + keyword
					+ "%')  AND UPPER(nvl(L.PARENT_AGENT_CODE,''))  LIKE UPPER(N'%" + keyword
					+ "%')  and L.TREE_LEVEL = 2)" + "OR (UPPER(nvl(L.ORG_NAME,''))  LIKE UPPER(N'%" + keyword
					+ "%')  AND UPPER(nvl(L.ORG_PARENT_NAME,''))  LIKE UPPER(N'%" + keyword
					+ "%')  and L.TREE_LEVEL = 2)" + "))";
		}
		if (StringUtils.isNotEmpty(keyword)
				&& ("UM".equalsIgnoreCase(agentGroup) || "BM".equalsIgnoreCase(agentGroup))) {
			return searchAll.replace("UPPER(nvl(L.ORG_NAME,''))", "UPPER(nvl(L.AGENT_TYPE,''))")
					+ "AND (L.TREE_LEVEL = 0  or ((" + bd1 + " and L.TREE_LEVEL = 1) or (" + bd2
					+ " and L.TREE_LEVEL = 2)) )";
		} else if (!("UM".equalsIgnoreCase(agentGroup) || "BM".equalsIgnoreCase(agentGroup)
				|| "FC".equalsIgnoreCase(agentGroup))) {
			return searchAll + "AND (L.TREE_LEVEL = 0  or ((" + bd1 + " and L.TREE_LEVEL = 1) or (" + bd2
					+ " and L.TREE_LEVEL = 2)) )";
		} else {
			return searchAll;
		}
	}

}

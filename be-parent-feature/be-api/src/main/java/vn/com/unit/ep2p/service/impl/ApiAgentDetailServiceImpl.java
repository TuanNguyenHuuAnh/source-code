package vn.com.unit.ep2p.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONObject;
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

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.account.dto.ConditionTable;
import vn.com.unit.cms.core.module.agent.dto.CheckAccessByAgentLogin;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetailPagingParam;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetailParam;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetailSearchDto;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentTerminationInfor;
import vn.com.unit.cms.core.module.agent.dto.CmsCheckAgentParamDto;
import vn.com.unit.cms.core.module.agent.dto.DepbitInfomation;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentBdPagingParam;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentDto;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentPagingParam;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.CheckAgentChildParam;
import vn.com.unit.common.dto.AaGaOfficeDto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.AgentContactInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.exception.HandlerCastException;
import vn.com.unit.ep2p.enumdef.ListAgentAhEnum;
import vn.com.unit.ep2p.enumdef.ListAgentBmEnum;
import vn.com.unit.ep2p.enumdef.ListAgentCaoEnum;
import vn.com.unit.ep2p.enumdef.ListAgentGaEnum;
import vn.com.unit.ep2p.enumdef.ListAgentOhEnum;
import vn.com.unit.ep2p.enumdef.ListAgentThEnum;
import vn.com.unit.ep2p.enumdef.ListAgentUmEnum;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.CmsEmailService;
import vn.com.unit.ep2p.service.JcaZipcodeService;
import vn.com.unit.ep2p.service.OtpService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiAgentDetailServiceImpl implements ApiAgentDetailService {

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	private CmsEmailService cmsEmailService;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	Db2ApiService db2ApiService;
    @Autowired
    JcaZipcodeService jcaZipcodeService;

    @Autowired
    private OtpService optpService;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

	private static final String SP_GET_INFORMATION_AGENT = "RPT_ODS.DS_SP_GET_INFORMATION_AGENT";
	// private static final String SP_PAGINATION_COMMOM = "SP_PAGINATION_COMMOM";
	private static final String DS_SP_CHECK_AGENT_DETAIL_BY_CODE = "RPT_ODS.DS_SP_CHECK_AGENT_DETAIL_BY_CODE";
	private static final String DS_SP_GET_DEBT_LIST_AND_RECEIPT_BOOK = "RPT_ODS.DS_SP_GET_DEBT_LIST_AND_RECEIPT_BOOK";
	private static final String DS_SP_LIST_AGENT = "RPT_ODS.DS_SP_GET_LIST_OF_AGENTS_BY_LEADER";
	private static final String DS_SP_LIST_AGENT_DETAIL = "RPT_ODS.DS_SP_GET_LIST_OF_AGENTS_BY_LEADER_DETAIL";
	private static final String DS_SP_GET_ODS_AGENT_DETAIL = "RPT_ODS.DS_SP_GET_ODS_AGENT_DETAIL";
	private static final String DS_SP_CHECK_AGENTS_BY_LEADER = "RPT_ODS.DS_SP_CHECK_AGENTS_BY_LEADER";
	private static final String DS_SP_GET_ODS_AGENT_DETAIL_CHILD="RPT_ODS.DS_SP_GET_ODS_AGENT_DETAIL";
	private static final String SP_GET_INFORMATION_AGENT_LOGIN="RPT_ODS.DS_SP_GET_INFORMATION_AGENT_LOGIN";

//	@Autowired
//	private ApiEmulateService emulateService;

	@Autowired
    protected HandlerCastException handlerCastException;

	@Override
    public CmsAgentDetail getCmsAgentDetailByUsername(String username) throws DetailException {
        CmsAgentDetailParam param = new CmsAgentDetailParam();
        CmsAgentDetail entity = new CmsAgentDetail();
        String agentParent = UserProfileUtils.getFaceMask();
        if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(username)) {
            boolean isChild = checkAgentChild(agentParent, username);
            if (!isChild) {
                throw new DetailException(AppApiExceptionCodeConstant.E4027102_APPAPI_LESS_THAN_NUMBER_RECORD);
            }
        }
            param.username = username;
            sqlManagerDb2Service.call(SP_GET_INFORMATION_AGENT, param);

            if (CommonCollectionUtil.isNotEmpty(param.dataDetail)) {
                entity = param.dataDetail.get(0);
                if (StringUtils.isNotEmpty(entity.getTakingWinner())) {
                    List<String> list = new ArrayList<>();
                    CollectionUtils.addAll(list, entity.getTakingWinner().split(","));
                    entity.setTakingWinnerList(list);
                }
                if (entity.getAgentLevel() == 1 && "AGENT".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("AGENT");
                else if (entity.getAgentLevel() > 1 && "AGENT".equalsIgnoreCase(entity.getGroupType())
                        && !"GA".equalsIgnoreCase(entity.getAgentGroup()))
                    entity.setAgentGroupType("LEADER");
                else if (entity.getAgentLevel() == 4 && "STAFF".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("BD");
                else if (entity.getAgentLevel() == 99 && "STAFF".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("SUPPORT");
                else
                    entity.setAgentGroupType("");
				if(ObjectUtils.isEmpty(entity.getOrgId())) entity.setOrgId(entity.getOrgCode());

            }
        return entity;
    }
	@Override
    public CmsAgentDetail getCmsAgentLoginByUsername(String username) {
        CmsAgentDetailParam param = new CmsAgentDetailParam();
        CmsAgentDetail entity = new CmsAgentDetail();
            param.username = username;
            sqlManagerDb2Service.call(SP_GET_INFORMATION_AGENT_LOGIN, param);

            if (CommonCollectionUtil.isNotEmpty(param.dataDetail)) {
                entity = param.dataDetail.get(0);
                if (entity.getAgentLevel() == 1 && "AGENT".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("AGENT");
                else if (entity.getAgentLevel() > 1 && "AGENT".equalsIgnoreCase(entity.getGroupType())
                        && !"GA".equalsIgnoreCase(entity.getAgentGroup()))
                    entity.setAgentGroupType("LEADER");
                else if (entity.getAgentLevel() == 4 && "STAFF".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("BD");
                else if (entity.getAgentLevel() == 99 && "STAFF".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("SUPPORT");
                else
                    entity.setAgentGroupType("");
				if(ObjectUtils.isEmpty(entity.getOrgId())) entity.setOrgId(entity.getOrgCode());

            }
        return entity;
    }

	@Override
	public boolean checkAgentChild(String agentParent, String agentChild) {
        try {
        	if (agentParent.equalsIgnoreCase(agentChild)) {
        		return true;
        	}
            CheckAgentChildParam param = new CheckAgentChildParam();
            param.agentParent=CommonStringUtil.isNotEmpty(agentParent) ? ";"+agentParent+";":null;
            param.agentChild=CommonStringUtil.isNotEmpty(agentChild) ? ";"+agentChild+";":null;
            sqlManagerDb2Service.call(DS_SP_GET_ODS_AGENT_DETAIL_CHILD, param);
            if(CommonCollectionUtil.isNotEmpty(param.data)) {
                return true;
            }
        } catch (Exception e) {
        	logger.error("Exception ", e);
        }
        return false;
    }

	@Override
	public List<CmsAgentDetail> getListAgentByCondition(CmsAgentDetailSearchDto searchDto) {
		CmsAgentDetailPagingParam param = new CmsAgentDetailPagingParam();
		param.territory = searchDto.getTerritory();
		param.region = searchDto.getRegion();
		param.office = searchDto.getOffice();
		param.area = searchDto.getArea();
		param.position = searchDto.getPosition();
		param.agentCode = searchDto.getAgentCode();
		sqlManagerDb2Service.call(DS_SP_GET_ODS_AGENT_DETAIL, param);
		List<CmsAgentDetail> resultData = new ArrayList<>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			resultData = param.data;
		}
		return resultData;
	}

	@Override
	public CmsAgentTerminationInfor checkAgentByCode(String code, String email, String otp) throws IOException {
		CmsCheckAgentParamDto param = new CmsCheckAgentParamDto();
		param.CODE = code;
		sqlManagerDb2Service.call(DS_SP_CHECK_AGENT_DETAIL_BY_CODE, param);
		CmsAgentTerminationInfor entity = new CmsAgentTerminationInfor();
		entity.setCheckAgentExist(-1);
		boolean check = false;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
	        String cacheOtp = optpService.getOtp(code);
	        check = StringUtils.equalsIgnoreCase(cacheOtp, otp);
	        if (check) {
				entity = param.data.get(0);
	        }
			DepbitInfomation debp = new DepbitInfomation();
			debp.agentCode = code;
			sqlManagerDb2Service.call(DS_SP_GET_DEBT_LIST_AND_RECEIPT_BOOK, debp);
			entity.setCheckAgentExist(1);
			entity.setCheckEmailExist(-1);
			entity.setEmailAddressPersonal(param.data.get(0).getEmailAddressPersonal());
			entity.setAgentCode(param.data.get(0).getAgentCode() == null ? "" : param.data.get(0).getAgentCode());
			entity.setAgentStatus(param.data.get(0).getAgentCode() == null ? "" : param.data.get(0).getAgentStatus());
			entity.setAgentStatusCode(param.data.get(0).getAgentStatusCode());
			if (CommonStringUtil.isNotEmpty(param.data.get(0).getEmailAddressPersonal())) {
				entity.setCheckEmailExist(1);
				if (entity.getCheckEmailExist() == 1 && entity.getAgentStatusCode() == 0) {
					cmsEmailService.sendMailOtp(entity.getAgentCode(), entity.getEmailAddressPersonal());
				}
			}
			if (CollectionUtils.isNotEmpty(debp.data) && ObjectUtils.isNotEmpty(debp.data.get(0)) && check) {
				CmsAgentTerminationInfor entityDebp = new CmsAgentTerminationInfor();
				entityDebp = debp.data.get(0);
				entity.setDebt(entityDebp.getDebt());
				entity.setReceipts(entityDebp.getSoNoQuyenPhieuThu());
			}
		}
		return entity;
	}

	@Override
	public CmsCommonPagination<InfoAgentDto> getListInfoAgent(InfoAgentSearchDto searchDto) {
		InfoAgentBdPagingParam param = new InfoAgentBdPagingParam();
		ObjectMapper mapper = new ObjectMapper();

		String stringJsonParam = "";
		searchDto.setFunctionCode("GROUP_" + searchDto.getAgentType());
		InfoAgentSearchDto searchBd2 = objectMapper.convertValue(searchDto, InfoAgentSearchDto.class);
		InfoAgentSearchDto searchBd1 = objectMapper.convertValue(searchDto, InfoAgentSearchDto.class);
		String bd1 = "1 = 1";
		String bd2 = "1 = 1";
		String agentType = searchDto.getAgentType();
		try {
			setConditionSearch(searchBd1, 1);
			stringJsonParam = mapper.writeValueAsString(searchBd1);
		} catch (JsonProcessingException e) {
			logger.error("getListInfoAgent", e);
		}
		CommonSearchWithPagingDto commonLv1 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam,
				searchDto.getAgentType());
		if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
			bd1 = commonLv1.getSearch();
		}
		try {
			setConditionSearch(searchBd2, 2);
			stringJsonParam = mapper.writeValueAsString(searchBd2);
		} catch (JsonProcessingException e) {
			logger.error("getListInfoAgent", e);
		}
		CommonSearchWithPagingDto commonLv2 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam,
				searchDto.getAgentType());
		if (StringUtils.isNotEmpty(commonLv2.getSearch())) {
			bd2 = commonLv2.getSearch();
			if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
				// lay data cap con thuoc cap cha
				bd2 = bd2 + " AND " + commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
						"UPPER(nvl(L.Parent_Agent_Name,''))");
			}
		} else {
			if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
				// lay data cap con thuoc cap cha
				bd2 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
						"UPPER(nvl(L.Parent_Agent_Name,''))");
			}
		}
		param.agentCode = searchDto.getUsername();
		param.agentGroup = searchDto.getAgentType();
		if ("OH".equals(searchDto.getAgentType()) || "GA".equals(searchDto.getAgentType()) || "SO".equals(searchDto.getAgentType())) {
			param.orgCode = searchDto.getRegion();
			agentType = "OH";
		}
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		param.sort = searchDto.getSort();
		// param.search=common.getSearch();// "and (UPPER(nvl(L.Parent_Agent_Name,''))
		// LIKE UPPER(N'%Phi%') OR UPPER(nvl(L.Agent_Name,'')) LIKE UPPER(N'%Phi%') OR
		// L.AGENT_LEVEL = 0) "; //
		String searchAll = "";
		if (StringUtils.isNotEmpty(searchDto.getKeyword())) {
			searchAll = " AND (L.TREE_LEVEL = 0  or ((UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"
					+ searchDto.getKeyword()
					+ "%')  and L.TREE_LEVEL = 1) or (UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"
					+ searchDto.getKeyword() + "%')  AND UPPER(nvl(L.Parent_Agent_Name,''))  LIKE UPPER(N'%"
					+ searchDto.getKeyword() + "%')  and L.TREE_LEVEL = 2)) )";
		}
		param.search = searchAll + "AND (L.TREE_LEVEL = 0  or ((" + bd1 + " and L.TREE_LEVEL = 1) or (" + bd2
				+ " and L.TREE_LEVEL = 2)) )";
		sqlManagerDb2Service.call(DS_SP_LIST_AGENT, param);
		List<InfoAgentDto> datas = param.data;
		sumPolicy(datas, 0);
		datas = groupAgent(searchDto, datas);
		for (InfoAgentDto ls : datas) {
			mapAgent(ls, agentType, ls.getTreeLevel() == 0);
			ls.setGad(ls.getGadType() + ": "+ ls.getGadCode() +" - " + ls.getGadName());
			if (searchDto.getAgentType() != null) {
				ls.setAgentGroup(ls.getOrgId() + "-" + ls.getAgentCode() + "-" + ls.getAgentName());
				if (searchDto.getAgentType() == "BM" || searchDto.getAgentType() == "SBM") {
					ls.setAgentGroup(ls.getAgentTyle() + "-" + ls.getAgentCode() + "-" + ls.getAgentName());
				}
				if (searchDto.getAgentType() == "Dummy sales") {
					ls.setAgentGroup(ls.getAgentName());
				}

			}
		}
		CmsCommonPagination<InfoAgentDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);

		resultData.setTotalData(0);
		return resultData;
	}
	private List<InfoAgentDto> groupAgent(InfoAgentSearchDto searchDto, List<InfoAgentDto> datas) {
		if ("AH".equals(searchDto.getAgentType())) {
			List<InfoAgentDto> listTree2 = new ArrayList<InfoAgentDto>();
			List<InfoAgentDto> lstTree1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1))).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(lstTree1)) {
				lstTree1.forEach(item -> {
					List<InfoAgentDto> lstTree2 = datas.stream()
							.filter(e -> e.getTreeLevel().equals(new Integer(2)) && e.getOrgParentId().equals(item.getOrgId()) && e.getParentAgentCode().equals(item.getAgentCode()))
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(lstTree2)) {
						
						if ("TH".equals(searchDto.getAgentType())) {
							Map<String, List<InfoAgentDto>> maplv2Group = lstTree2.stream()
									.filter(e -> e.getTreeLevel() == 2)
									.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
							for (Entry<String, List<InfoAgentDto>> entry : maplv2Group.entrySet()) {
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
				Map<String, List<InfoAgentDto>> maplv1 = lstTree1.stream()
						.filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
				if (CollectionUtils.isNotEmpty(maplv1.entrySet())) {
					datas.removeIf(e -> e.getTreeLevel().equals(new Integer(1)));
					for (Entry<String, List<InfoAgentDto>> entry : maplv1.entrySet()) {
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
			List<InfoAgentDto> lv2 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(2))).collect(Collectors.toList());
			lv2.forEach(item2 -> {
				item2.setTotalheadofdepartment(null);
			});
		}
		else if ("GA".equals(searchDto.getAgentType()) || "SO".equals(searchDto.getAgentType())) {
			List<InfoAgentDto> resultNew = new ArrayList<>();
			//lv2 = lv1
			List<InfoAgentDto> lv1ToLv2 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1))).collect(Collectors.toList());
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(2)));
			// lv1 = lv0
			List<InfoAgentDto> lv0ToLv1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(0))).collect(Collectors.toList());

			InfoAgentDto lv0 = new InfoAgentDto();
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
	
	private InfoAgentDto mapObject(InfoAgentDto source, InfoAgentDto tag) {
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
		return tag;
	}

	private void sumPolicy(List<InfoAgentDto> datas, int treeLevel){
		if(!datas.isEmpty()){
			List<InfoAgentDto> lstRoot = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(treeLevel))).collect(Collectors.toList());
			List<String> adresses = lstRoot.stream()
				    .map(InfoAgentDto::getOrgName)
				    .collect(Collectors.toList());
			String orgNameNew = String.join(", ", adresses);
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(treeLevel)));
			if(!lstRoot.isEmpty()){
				InfoAgentDto rootTmp = lstRoot.get(0);
				if(treeLevel == 1 && "OH".equalsIgnoreCase(rootTmp.getChildGroup())) {
					rootTmp.setOrgCode(null); // 
					rootTmp.setOrgId(rootTmp.getAgentCode());
				}
				rootTmp.setOrgNameNew(orgNameNew);
				rootTmp.setTotal(lstRoot.stream().map(InfoAgentDto :: getTotal).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setTotalheadofdepartment(lstRoot.stream().map(InfoAgentDto :: getTotalheadofdepartment).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setTotalmanager(lstRoot.stream().map(InfoAgentDto :: getTotalmanager).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setTotaltvtc(lstRoot.stream().map(InfoAgentDto :: getTotaltvtc).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setTotaltvtcsa(lstRoot.stream().map(InfoAgentDto :: getTotaltvtcsa).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				datas.add(rootTmp);
			}
		}
	}
	private void setConditionSearch(InfoAgentSearchDto data, int level) {
		switch (data.getAgentType()) {
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
		case "SO":
			if (level == 1)
				data.setUnitName(null);
			if (level == 2)
				data.setBranchName(null);
			break;
		default:
			break;
		}

	}

	@Override
	public CmsCommonPagination<InfoAgentDto> getListInfoBranchAgent(InfoAgentSearchDto searchDto) {
		InfoAgentPagingParam param = new InfoAgentPagingParam();
		searchDto.setFunctionCode(searchDto.getAgentType());
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,
				searchDto.getAgentType());
		if(StringUtils.isBlank(searchDto.getSort())){
			common.setSort(searchDto.getAgentType().equals("BM")?  "ORDER BY MANAGER_AGENT_TYPE, MANAGER_AGENT_NAME":"ORDER BY A.APPOINTED_DATE desc");		
		}
		param.agentCode = searchDto.getUsername();
		param.agentGroup = searchDto.getAgentType();
		param.orgCode = searchDto.getRegion();
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		param.sort = common.getSort();
		param.search = this.getSearchAgent(searchDto, common.getSearch());

		sqlManagerDb2Service.call(DS_SP_LIST_AGENT_DETAIL, param);
		List<InfoAgentDto> datas = param.data;
		for (InfoAgentDto ls : datas) {
			ls.setManagerAgentName(
					ls.getManagerAgentType() + ": " + ls.getMangerAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + ls.getManagerAgentName());
			ls.setAgentAll(ls.getAgentType() + ": " + ls.getAgentCode() + "-" + ls.getAgentName());
		}
		CmsCommonPagination<InfoAgentDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.total != null ? param.total : 0);
		return resultData;
	}

	@Override
	public InfoAgentDto getListInfoAgentDetail(InfoAgentSearchDto searchDto) {
		InfoAgentPagingParam param = new InfoAgentPagingParam();
		param.agentCode = searchDto.getUsername();
		param.agentGroup = searchDto.getAgentType();
		param.orgCode = searchDto.getRegion();
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		sqlManagerDb2Service.call(DS_SP_LIST_AGENT_DETAIL, param);
		InfoAgentDto resultData = new InfoAgentDto();
		resultData = param.data.get(0);
		resultData.setGadName(resultData.getGadCode() + "-" + resultData.getGadName());
		resultData.setPresenterName(resultData.getPresenterCode() + "-" + resultData.getPresenterName());
		resultData.setLeaderName(resultData.getLeaderCode() + "-" + resultData.getLeaderName());
		resultData.setManagerAgentName(resultData.getMangerAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + resultData.getManagerAgentName());
		return resultData;
	}

	private void mapAgent(InfoAgentDto data, String agentGroup, boolean first) {
		if (StringUtils.isNotEmpty(data.getChildGroup())) {
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
//					data.setBranchCode(data.getAgentCode());
//					data.setBranchName("Tổng cộng");
//					data.setBranchType(data.getAgentType());
					data.setGaCode(data.getAgentCode());
					data.setGaName("Tổng cộng");
					data.setGaType(data.getAgentType());
					break;
				case "SO":
//					data.setBranchCode(data.getAgentCode());
//					data.setBranchName("Tổng cộng");
//					data.setBranchType(data.getAgentType());
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
					data.setCaoName(data.getOrgId() + "-" + data.getAgentCode() + "-" + data.getAgentName());
					data.setCaoType(data.getAgentType());
					break;
				case "TH":
					data.setBdthCode(data.getAgentCode());
					data.setBdthName(data.getOrgId() + "-" + data.getAgentCode() + "-" + data.getAgentName());
					data.setBdthType(data.getAgentType());
					break;
				case "AH":
					data.setBdahCode(data.getAgentCode());
					data.setBdahName(data.getOrgId() + "-" + data.getAgentCode() + "-" + data.getAgentName());
					data.setBdahType(data.getAgentType());
					break;
				case "RH":
					data.setBdrhCode(data.getAgentCode());
					data.setBdrhName(data.getOrgId() + "-" + data.getAgentCode() + "-" + data.getAgentName());
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
					data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getParentAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setGaName(data.getParentAgentName());

					}
					data.setGaType(data.getAgentType());
					break;
				case "SO":
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
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setBranchName(data.getAgentName());

					}
					data.setBranchType(data.getAgentType());
					break;
				case "UM":
					data.setUnitCode(data.getAgentCode());
					data.setUnitName(data.getAgentType() + ": " + data.getAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setUnitName(data.getAgentName());

					}
					data.setUnitType(data.getAgentType());
					break;
				default:
					data.setAgentCode(data.getAgentCode());
					break;
				}
			}
		}
	}

	@Autowired
	public SystemConfig systemConfig;
	@Autowired
	private ServletContext servletContext;
	private Logger logger = LoggerFactory.getLogger(getClass());

	// export
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity exportListInfoAgent(InfoAgentSearchDto dto, HttpServletResponse response, Locale locale) {
		ResponseEntity res = null;
		try {
			dto.setPage(0);
			dto.setSize(0);
			dto.setPageSize(0);
			CmsCommonPagination<InfoAgentDto> common = new CmsCommonPagination<InfoAgentDto>();
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "Danh_sach_dai_ly.xlsx";
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A5";

			List<ItemColsExcelDto> cols = new ArrayList<>();
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();
			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {

				Font fontMain = xssfWorkbook.createFont();
				fontMain.setBold(true);
				fontMain.setFontName("Times New Roman");

				CellStyle cellStyleMain = xssfWorkbook.createCellStyle();
				cellStyleMain.setFont(fontMain);
				cellStyleMain.setAlignment(HorizontalAlignment.LEFT);
				cellStyleMain.setWrapText(false);

				Map<String, CellStyle> mapColStyle = new HashMap<>();

				String titleName = "DANH SÁCH ĐẠI LÝ";
				String[] titleHeader = new String[] { "", "", "", "Tổng số", "Tổng số Cấp Trưởng Ban", "Tổng số Cấp Trưởng Phòng",
						"Tổng số TVTC", "Tổng số TVTC phục vụ (SA)" };
				List<String> listHeader = new ArrayList<String>();

				templateName += "_";
				if (dto.getAgentType().equalsIgnoreCase("CAO") || dto.getAgentType().equalsIgnoreCase("AH")
						|| dto.getAgentType().equalsIgnoreCase("TH") || dto.getAgentType().equalsIgnoreCase("RH")
						|| dto.getAgentType().equalsIgnoreCase("OH") || dto.getAgentType().equalsIgnoreCase("GA")
						|| dto.getAgentType().equalsIgnoreCase("SO")) {

					listHeader = Stream.of(titleHeader).collect(Collectors.toList());
					common = getListInfoAgent(dto);
					// titleHeader = new String[] {"", "", "Tổng số", "Tổng số cấp trưởng phòng",
					// "Tổng số cấp nhóm", "Tổng số TVTC", "Tổng số TVTC phục vụ (SA)"};
					if (dto.getAgentType().equalsIgnoreCase("CAO")) {
						listHeader.set(0, "BDTH");
						listHeader.set(1, "BDAH");
						listHeader.remove(2);
						templateName += "CAO";
						mapColStyle.put("BDTHNAME", cellStyleMain);

						ImportExcelUtil.setListColumnExcel(ListAgentCaoEnum.class, cols);
					} else if (dto.getAgentType().equalsIgnoreCase("TH")) {
						listHeader.set(0, "BDAH");
						listHeader.set(1, "BDOH");
						listHeader.remove(2);
						templateName += "TH";
						mapColStyle.put("BDAHNAME", cellStyleMain);
						ImportExcelUtil.setListColumnExcel(ListAgentThEnum.class, cols);
					} else if (dto.getAgentType().equalsIgnoreCase("AH")) {
						listHeader.set(0, "BDOH");
						listHeader.set(1, "Văn phòng/ Tổng đại lý");
						listHeader.set(2, "GAD");
						templateName += "AH";
						mapColStyle.put("BDOHNAME", cellStyleMain);
						ImportExcelUtil.setListColumnExcel(ListAgentAhEnum.class, cols);
					} else if (dto.getAgentType().equalsIgnoreCase("OH") || dto.getAgentType().equalsIgnoreCase("GA") || dto.getAgentType().equalsIgnoreCase("SO")) {
						listHeader.set(0, "Văn phòng/ Tổng đại lý");
						listHeader.set(1, "Trưởng Ban Kinh doanh");
						listHeader.remove(2);
						mapColStyle.put("GANAME", cellStyleMain);
						templateName += "OH";
						ImportExcelUtil.setListColumnExcel(ListAgentOhEnum.class, cols);
					} else if (dto.getAgentType().equalsIgnoreCase("GA")) {// OFFICE
						listHeader.set(0, "Trưởng Ban Kinh doanh");
						listHeader.set(1, "Trưởng Phòng Kinh doanh");
						listHeader.remove(2);
						listHeader.remove(3);
						listHeader.set(3, "Tổng số TVTC");
						listHeader.set(4, "Tổng số TVTC phục vụ (SA)");
						listHeader.remove(5);
						mapColStyle.put("BRANCHNAME", cellStyleMain);
						templateName += "GA";
						ImportExcelUtil.setListColumnExcel(ListAgentGaEnum.class, cols);
					} else if (dto.getAgentType().equalsIgnoreCase("SO")) {// OFFICE
						listHeader.set(0, "Trưởng Ban Kinh doanh");
						listHeader.set(1, "Trưởng Phòng Kinh doanh");
						listHeader.remove(2);
						listHeader.remove(3);
						listHeader.set(3, "Tổng số TVTC");
						listHeader.set(4, "Tổng số TVTC phục vụ (SA)");
						listHeader.remove(5);
						mapColStyle.put("BRANCHNAME", cellStyleMain);
						templateName += "SO";
						ImportExcelUtil.setListColumnExcel(ListAgentGaEnum.class, cols);
					}
				} else {

					common = getListInfoBranchAgent(dto);
					titleHeader = new String[] { "STT","Quản lý", "Tư vấn tài chính", "Giới tính","Ngày sinh","Trạng thái",
							"Điện thoại", "Ngày bắt đầu hoạt động", "Ngày chấm dứt hoạt động" };
					listHeader = Stream.of(titleHeader).collect(Collectors.toList());

					if (dto.getAgentType().equalsIgnoreCase("BM")) {
						templateName += "BM";
						ImportExcelUtil.setListColumnExcel(ListAgentBmEnum.class, cols);
					} else if (dto.getAgentType().equalsIgnoreCase("UM")) {
						templateName += "UM";
						listHeader.remove(1);
						ImportExcelUtil.setListColumnExcel(ListAgentUmEnum.class, cols);
					}

				}
				List<InfoAgentDto> lstdata = common.getData();

				int total = common.getTotalData();
				List<InfoAgentDto> allData = new ArrayList<>();
				if(ObjectUtils.isNotEmpty(lstdata)) {
					//list data có level null
					List<InfoAgentDto> data = lstdata.stream().filter(e -> e.getTreeLevel() == null).collect(Collectors.toList());
					if (data.isEmpty()) {

						InfoAgentDto root = lstdata.stream().filter(e -> e.getTreeLevel().equals(0)).findFirst().get();
						total = root.getTotal().intValue();
						// get lv1
						List<InfoAgentDto> lv1 = lstdata.stream().filter(e -> e.getTreeLevel() == 1)
								.collect(Collectors.toList());
						for (InfoAgentDto groupLv1 : lv1) {
							allData.add(groupLv1);
							// get lv2
							List<InfoAgentDto> lv2 = lstdata.stream()
									.filter(e -> e.getTreeLevel() == 2
											&& e.getParentAgentCode().equals(groupLv1.getAgentCode())
											&& e.getOrgParentId().equals(groupLv1.getOrgId()))
									.collect(Collectors.toList());
							allData.addAll(lv2);
						}
						allData.add(root);
					} else {
						allData.addAll(lstdata);
					}
				}
				String[] listHeaderStr = listHeader.toArray(new String[0]);
				String office = null;
				String leader = null;
					AgentInfoDb2 dataz = db2ApiService.getParentByAgentCode(dto.getUsername(),dto.getAgentType(), dto.getRegion());
					if(ObjectUtils.isNotEmpty(dataz)) {
						office = dataz.getOrgName();
						leader = dataz.getAgentType() + ": " + dataz.getAgentCode().replace(dataz.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "") + " - " + dataz.getAgentName();
					}
	    			startRow = "A9";

					setDataHeaderToXSSFWorkbookSheetDto(xssfWorkbook, 0, listHeaderStr, titleName,total,office,leader,dto.getAgentType());
				
				

				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, allData,
						InfoAgentDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, false, templateName, true,path);
			} catch (Exception e) {
				logger.error("##doExport##", e);
			}

		} catch (Exception e) {
			logger.error("##exportListInfoAgent##", e);
		}
		return res;
	}
	public void setDataHeaderToXSSFWorkbookSheetDto(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader, String titleName,int total,String office,String leader,String agentType) {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);

		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();

		Font fontTitleDate = xssfWorkbook.createFont();
		fontTitleDate.setColor(IndexedColors.BLUE.index);
		fontTitleDate.setFontName("Times New Roman");
		titleStyleDate.setFont(fontTitleDate);

		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setColor(IndexedColors.BLUE.index);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short)20);

		titleStyle.setFont(fontTitle);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		 if(agentType.equals("RH")||agentType.equals("BM")) {
			xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		} else if(agentType.equals("GA") || agentType.equals("OH")) {
			xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		}
		 else {
			xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		}
		
		xssfSheet.getRow(0).getCell(0).setCellValue(titleName);
		xssfSheet.getRow(0).getCell(0).setCellStyle(titleStyle);
		
	    xssfSheet.getRow(2).getCell(0).setCellValue("Office/GA: " + office);
	    xssfSheet.getRow(2).getCell(0).setCellStyle(titleStyleDate);
	    
	    xssfSheet.getRow(3).getCell(0).setCellValue(leader);
	    xssfSheet.getRow(3).getCell(0).setCellStyle(titleStyleDate);

	    xssfSheet.getRow(4).getCell(0).setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
	    xssfSheet.getRow(4).getCell(0).setCellStyle(titleStyleDate);
	    
	    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		String amount = "";
			amount = decimalFormat.format(total);
	    xssfSheet.getRow(5).getCell(0).setCellValue("Tổng số TVTC: " + amount);
	    xssfSheet.getRow(5).getCell(0).setCellStyle(titleStyleDate);


		XSSFRow row4 = xssfSheet.getRow(7);
		if(ObjectUtils.isEmpty(row4)) row4 = xssfSheet.createRow(7);
	
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
			if (cell4 == null) cell4 = row4.createCell(i);
				cell4.setCellValue(titleHeader[i]);
				cell4.setCellStyle(headerCellStyle);

		}
	}

	@Override
	public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader, String titleName, Map<String, CellStyle> mapColStyle) {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);

		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
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

		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setColor(IndexedColors.BLUE.index);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short)20);

		mapColStyle.put("NO",no);


		titleStyle.setFont(fontTitle);
		XSSFRow row1 = xssfSheet.getRow(0);
		XSSFCell cell1 = row1.getCell(1);
		cell1.setCellValue(titleName);
		cell1.setCellStyle(titleStyle);

		XSSFRow row2 = xssfSheet.getRow(1);
		XSSFCell cell2 = row2.getCell(0);
		cell2.setCellValue("Ngày báo cáo: "+ DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		cell2.setCellStyle(titleStyleDate);

		XSSFRow row4 = xssfSheet.getRow(3);

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
			cell4.setCellValue(titleHeader[i]);
			cell4.setCellStyle(headerCellStyle);
		}
	}
	@Override
	public Integer checkDataByCondition(ConditionTable condition) {
		CheckAccessByAgentLogin param = new CheckAccessByAgentLogin();
		if (condition.getGadFlag() == 1) {
			param.agentCode = UserProfileUtils.getFaceMask();
			param.orgCode = condition.getOrgCode();
			param.agentGroup = "GAD";
			param.agentCodeChild = condition.getAgentCode();
			sqlManagerDb2Service.call(DS_SP_CHECK_AGENTS_BY_LEADER, param);
			if (param.total == 0) {
				param.agentCode = UserProfileUtils.getFaceMask();
				param.orgCode = condition.getOrgCode();
				param.agentGroup = condition.getAgentGroup();
				param.agentCodeChild = condition.getAgentCode();
				sqlManagerDb2Service.call(DS_SP_CHECK_AGENTS_BY_LEADER, param);
				return param.total;
			}
			return param.total;
			
		}
		param.agentCode = UserProfileUtils.getFaceMask();
		param.orgCode = condition.getOrgCode();
		param.agentGroup = condition.getAgentGroup();
		param.agentCodeChild = condition.getAgentCode();
		sqlManagerDb2Service.call(DS_SP_CHECK_AGENTS_BY_LEADER, param);
		return param.total;
	}
	

	public List<String> getOffceCode(String agentCode) {
//		CmsAgentDetailParam param = new CmsAgentDetailParam();
//		param.username = agentCode;
//		sqlManagerDb2Service.call(SP_GET_INFORMATION_AGENT, param);
//
//		if (CommonCollectionUtil.isNotEmpty(param.dataDetail)) {
//			CmsAgentDetail entity = param.dataDetail.get(0);
//			return jcaZipcodeService.getOrdId(entity.getOfficeCode());
//		}
		return jcaZipcodeService.getOrdId(agentCode);
	}

	@Override
	public CmsAgentDetail getCmsAgentDetailByFaceMask(String username) {
		CmsAgentDetailParam param = new CmsAgentDetailParam();
        CmsAgentDetail entity = new CmsAgentDetail();
            param.username = username;
            sqlManagerDb2Service.call(SP_GET_INFORMATION_AGENT, param);
            if (CommonCollectionUtil.isNotEmpty(param.dataDetail)) {
                entity = param.dataDetail.get(0);
                if (StringUtils.isNotEmpty(entity.getTakingWinner())) {
                    List<String> list = new ArrayList<>();
                    CollectionUtils.addAll(list, entity.getTakingWinner().split(","));
                    entity.setTakingWinnerList(list);
                }
                if (entity.getAgentLevel() == 1 && "AGENT".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("AGENT");
                else if (entity.getAgentLevel() > 1 && "AGENT".equalsIgnoreCase(entity.getGroupType())
                        && !"GA".equalsIgnoreCase(entity.getAgentGroup()))
                    entity.setAgentGroupType("LEADER");
                else if (entity.getAgentLevel() == 4 && "STAFF".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("BD");
                else if (entity.getAgentLevel() == 99 && "STAFF".equalsIgnoreCase(entity.getGroupType()))
                    entity.setAgentGroupType("SUPPORT");
                else
                    entity.setAgentGroupType("");

                if ("AGENT".equalsIgnoreCase(entity.getGroupType())
                        && entity.getIsGad() != null && entity.getIsGad() == 1) {
                    entity.setAgentGroupType("GAD");
                    List<AaGaOfficeDto> datas = db2ApiService.getListOfficeByGad(username, null, "0");
                    entity.setListGa(datas);
                }
				if(ObjectUtils.isEmpty(entity.getOrgId())) entity.setOrgId(entity.getOrgCode());
            }
        return entity;
	}
	//----------------kk.quan add feature--------------
	@Override
	public AgentContactInfoDto getContactAndCommonInfo(Long agentCode) {
		AgentContactInfoDto infoAgentDto=db2ApiService.getContactAndCommonInfo(agentCode);
		return infoAgentDto;
		
	}
	//----------------kk.quan add feature--------------
	
	private String getSearchAgent(InfoAgentSearchDto searchDto, String searchStr) {
		String search1 = "";
		String search2 = "";
		String search3 = "";
		String keyword1 = "";
		String keyword2 = "";
		String keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getManagerAgentName())) {
			JSONObject jsonObj = new JSONObject(searchDto.getManagerAgentName().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.MANAGER_AGENT_NAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(A.MANGER_AGENT_CODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(A.MANAGER_AGENT_TYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.MANAGER_AGENT_NAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(A.MANGER_AGENT_CODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(A.MANAGER_AGENT_TYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.MANAGER_AGENT_NAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(A.MANGER_AGENT_CODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(A.MANAGER_AGENT_TYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getManagerAgentName())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		search1 = "";
		search2 = "";
		search3 = "";
		keyword1 = "";
		keyword2 = "";
		keyword3 = "";
		if (ObjectUtils.isNotEmpty(searchDto.getAgentAll())) {
			JSONObject jsonObj = new JSONObject(searchDto.getAgentAll().toString());
	        String operator = jsonObj.getString("operator");
	        String paramFrom = jsonObj.getString("paramFrom");
	        if ("Gần đúng".equals(operator)) {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.AGENT_NAME,'')) LIKE UPPER(N'%"+paramFrom+"%')";
	        	search2 = "nvl(A.AGENT_CODE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        	search3 = "nvl(A.AGENT_TYPE,'') LIKE UPPER(N'%"+paramFrom+"%')";
	        } else {
	        	search1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.AGENT_NAME,'')) = UPPER(N'"+paramFrom+"')";
	        	search2 = "nvl(A.AGENT_CODE,'') = UPPER(N'"+paramFrom+"')";
	        	search3 = "nvl(A.AGENT_TYPE,'') = UPPER(N'"+paramFrom+"')";
	        }
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			keyword1 = "RPT_ODS.DS_FN_REMOVEMARK(nvl(A.AGENT_NAME,'')) like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword2 = "nvl(A.AGENT_CODE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
			keyword3 = "nvl(A.AGENT_TYPE,'') like UPPER(N'%"+searchDto.getKeyword()+"%')";
		}
		
		if (ObjectUtils.isNotEmpty(searchDto.getAgentAll())) {
			String search = "(" + search1 + " or " + search2 + " or " + search3 + ")";
			searchStr = searchStr.replace(search1, search);
		}
		if (ObjectUtils.isNotEmpty(searchDto.getKeyword())) {
			String keyword = "(" + keyword1 + " or " + keyword2 + " or " + keyword3 + ")";
			searchStr = searchStr.replace(keyword1, keyword);
		}
		
		return searchStr;
	}
}
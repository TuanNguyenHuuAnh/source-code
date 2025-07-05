package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.core.module.events.entity.Events;
import vn.com.unit.cms.core.module.events.entity.EventsApplicableDetail;
import vn.com.unit.cms.core.module.events.imports.dto.EventsImportDto;
import vn.com.unit.cms.core.module.events.imports.repository.EventsImportRepository;
import vn.com.unit.cms.core.module.events.repository.EventsApplicableDetailRepository;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.dto.SpCommonSubmitParamDto;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class EventsImportServiceImpl implements ImportExcelInterfaceService<EventsImportDto>{

	private static final String SP_IMPORT_EVENTS_VALID = "SP_IMPORT_EVENTS_VALID";
    private static final String SP_IMPORT_EVENTS_SUBMIT = "SP_IMPORT_EVENTS_SUBMIT";
	
	@Autowired
    ConnectionProvider connectionProvider;
    
    /** MessageSource */
    @Autowired
    private MessageSource msg;

    /** SystemConfigure */
    @Autowired
    SystemConfig systemConfig;

    @Autowired
    ServletContext servletContext;

	@Autowired
	@Qualifier("sqlManagerServicePr")
	private SqlManagerServiceImpl sqlManager;

    @Autowired
    Db2ApiService db2ApiService;

    @Autowired
    EventsImportRepository eventsImportRepository;
    
    @Autowired
    EventsApplicableDetailRepository eventsApplicableDetailRepository;
	
    private static final Logger logger = LoggerFactory.getLogger(EventsImportServiceImpl.class);
    
	@Override
	public ConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}
	
	@Override
	public MessageSource getMessageSource() {
		return msg;
	}
	
	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

    @Override
    public SqlManager getSqlManager() {
        return null;
    }
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getImportDto() {
		return EventsImportDto.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntity() {
		return Events.class;
	}

	@Override
	public int countData(String sessionKey) {
		return eventsImportRepository.countData(sessionKey);
	}

	@Override
	public int countError(String sessionKey) {
		return eventsImportRepository.countError(sessionKey);
	}
	
	@Override
	public List<EventsImportDto> getListData(int page, String sessionKey, int sizeOfPage) {
		return eventsImportRepository.findListData(page, sizeOfPage, sessionKey);
	}

	@Override
	public List<EventsImportDto> getListDataExport(String sessionKey) {
		return eventsImportRepository.findListDataExport(sessionKey);
	}

	@Override
	public List<EventsImportDto> getAllDatas(String sessionKey) {
		return eventsImportRepository.findAllDatas(sessionKey);
	}

	@Override
	public boolean validateBusiness(String sessionKey, ImportExcelSearchDto searchDto, Map<String, Object> mapParams,
			List<EventsImportDto> listDataValidate) {
	    try{
			List<EventsImportDto> lstDataImport = getAllDatas(sessionKey);
			List<EventsApplicableDetail> lstRegistered = new ArrayList<>();
			List<String> lstCode = new ArrayList<>();
			List<String> lstIdNumber = new ArrayList<>();
			List<String> lstTel = new ArrayList<>();
			boolean hasError = false;
			Long eventId = (Long) mapParams.get("id");
			if (eventId != null) {
				lstRegistered = eventsApplicableDetailRepository.getListRegistered(eventId);
				for(EventsApplicableDetail detail : lstRegistered) {
					if (detail.getAgentCode() != null) {
						lstCode.add(detail.getAgentCode().toString());
					}
					if (StringUtils.isNotEmpty(detail.getIdNumber())) {
						lstIdNumber.add(detail.getIdNumber());
					}
				}
			}
			List<String> lstAgentCode = lstDataImport.stream()
					.filter(i -> ("1".equals(i.getType()) && i.getAgentCode().chars().allMatch(Character::isDigit)))
					.map(EventsImportDto::getAgentCode).collect(Collectors.toList());
			List<Db2AgentDto> lstAgent = null;
			if (!lstAgentCode.isEmpty()) {
				lstAgent = db2ApiService.getAgentInfoByCode(lstAgentCode);
			}
			for(EventsImportDto data : lstDataImport) {
				if ("1".equals(data.getType()) && lstAgent != null) {
					List<Db2AgentDto> agents = lstAgent.stream().filter(
							e -> StringUtils.equalsIgnoreCase(e.getAgentCode(), data.getAgentCode())).collect(Collectors.toList());
					if (agents.size() > 0) {
						eventsImportRepository.updateAgentInfo(data.getId(), agents.get(0));
						data.setIdNumber(agents.get(0).getIdNumber());
						data.setTel(agents.get(0).getMobilePhone());
					}
				}
				if (hasError) {
					checkDetail(data, lstCode, lstIdNumber, lstTel);
				} else {
					hasError = checkDetail(data, lstCode, lstIdNumber, lstTel);
				}
				if ("1".equals(data.getType())) {
					if (StringUtils.isNotEmpty(data.getAgentCode())) {
						lstCode.add(data.getAgentCode());
					}
					if (StringUtils.isNotEmpty(data.getIdNumber())) {
						lstIdNumber.add(data.getIdNumber());
					}
				} else if ("2".equals(data.getType()) || "3".equals(data.getType())) {
					if (StringUtils.isNotEmpty(data.getAgentCode())) {
						lstIdNumber.add(data.getAgentCode());
					}
				}
				if (StringUtils.isNotEmpty(data.getTel())) {
					lstTel.add(data.getTel());
				}
			}
			searchDto.setIsError(hasError);
			return hasError;
        } catch (Exception e) {
            logger.error("Error validateBusiness: ", e);
            return false;
        }
	}

    @Override
    public List<String> initHeaderTemplate() {
        List<String> results = new LinkedList<String>();
        results.add("common.import.no");
        results.add("events.import.type");
        results.add("events.import.agent.code");
        results.add("events.import.idnumber");
        results.add("events.import.name");
        results.add("events.import.gender");
        results.add("events.import.email");
        results.add("events.import.tel");
        results.add("events.import.address");
        results.add("events.import.refer.code");
        return results;
    }
 
    @Override
    public void saveListImport(List<EventsImportDto> listDataSave
    		, String sessionKey, Locale locale, String username)
            throws Exception {
        try {
            SpCommonSubmitParamDto validParam = new SpCommonSubmitParamDto();
            validParam.sessionKey = sessionKey;
            validParam.username = username;
            getSqlManager().call(SP_IMPORT_EVENTS_SUBMIT, validParam);
        } catch (Exception e) {
            logger.error("####saveListImport####", e);
            throw new Exception(e);
        }
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity exportExcelRestApi(List<EventsImportDto> lstData, String templateName, Class enums, Class dtoExport,
            String columsStart, HttpServletRequest req, HttpServletResponse res, Locale locale) throws Exception {
        ResponseEntity resEntity = null;

        parseData(lstData, true, locale);

        String datePattern = getSystemConfig().getConfig(CommonConstant.DATE_PATTERN, null);

        List<ItemColsExcelDto> cols = new ArrayList<>();

        // start fill data to workbook
        ImportExcelUtil.setListColumnExcel(enums, cols);
        ExportExcelUtil exportExcel = new ExportExcelUtil<>();

        Map<String, String> mapColFormat = setMapColFormat();

        Map<String, Object> setMapColDefaultValue = setMapColDefaultValue();
        String templatePath = getServletContext().getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
        try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {

            Map<String, CellStyle> mapColStyle = setMapColStyle(xssfWorkbook);
            String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
			String path = systemConfig.getPhysicalPathById(repo, null); //path up service
			resEntity = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstData,
					EventsImportDto.class, cols, datePattern, columsStart, mapColFormat, mapColStyle,
					setMapColDefaultValue, null, true, templateName, true,path);
        } catch (Exception e) {
            logger.error("#####exportExcel#####: ", e);
            throw new Exception(e.getMessage());
        }
        return resEntity;
    }
    
    private boolean checkDetail(EventsImportDto data, List<String> lstCode, List<String> lstIdNumber, List<String> lstTel) {
    	String error = data.getMessageError();
    	if (error == null) {
    		error = "";
    	}
    	String warning = data.getMessageWarning();
    	if (warning == null) {
    		warning = "";
    	}
    	//check Loại
    	if (data.getType() != null && !data.getType().isEmpty()
    			&& !"1".equals(data.getType()) && !"2".equals(data.getType()) && !"3".equals(data.getType())) {
    		error += "F009.E006" + "@;-";
    	}
    	// Là TVTC
    	if ("1".equals(data.getType())) {
    		if (data.getAgentCode() == null || "".equals(data.getAgentCode().trim())) {
    			error += "IMP.ERROR.EMPTY@:-events.import.agent.code@;-";
    		} else {
    			// Mã TVTC có chứa ký tự chữ (mã nhân viên) hoặc ít hơn 6 ký tự số
    			if (data.getAgentCode().length() < 6 || !data.getAgentCode().chars().allMatch(Character::isDigit)) {
    				error += "F009.E007" + "@;-";
    			} else {
					// Mã TVTC không tồn tại trên hệ thống (error) 
					Db2AgentDto agentDto = db2ApiService.getAgentInfoByCondition(data.getAgentCode());
					if(agentDto == null) {
						error += "F009.E005" + "@;-";
					} else {
						// Mã TVTC đã chấm dứt hoạt động (warning)
						if (agentDto.getAgentStatusCode() == 0) {
							warning += "F009.W001" + "@;-";
						} else {
							// Mã TVTC đang bị kỷ luật (warning)
							if (db2ApiService.checkDiscipline(data.getAgentCode())) {
								warning += "F009.W002" + "@;-";	
							}
						}
					}
    			}
    		}
    	} else if ("2".equals(data.getType()) || "3".equals(data.getType())) {
    		// CMND/CCCD buộc nhập
    		if (data.getAgentCode() == null || "".equals(data.getAgentCode().trim())) {
    			error += "IMP.ERROR.EMPTY@:-events.import.idnumber@;-";
    		}
    		// Họ và Tên bắt  buộc nhập
    		if (data.getName() == null || "".equals(data.getName().trim())) {
    			error += "IMP.ERROR.EMPTY@:-events.import.name@;-";
    		}
    		// Số CMND/CCCD phải dài ít nhất 6 ký tự (error)
    		if (data.getAgentCode() != null && data.getAgentCode().length() < 6) {
    			error += "F009.E009" + "@;-";
    		}
    		// Số điện thoại bắt buộc nhập
    		if (data.getTel() == null || "".equals(data.getTel().trim())) {
    			error += "IMP.ERROR.EMPTY@:-events.import.tel@;-";
    		}
    		// Giới tính bắt buộc nhập
    		if (data.getGender() == null || "".equals(data.getGender().trim())) {
    			error += "IMP.ERROR.EMPTY@:-events.import.gender@;-";
    		} else if (!"Nam".equals(data.getGender()) && !"Nữ".equals(data.getGender())) {
    			// Giới tính chỉ được nhập Nam hoặc Nữ (error) 
    			warning += "F009.W004" + "@;-";
    		}
    		// Email không đúng định dạng (error)
    		if (data.getEmail() != null) {
    			String regexPattern = "[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        		if (!Pattern.compile(regexPattern).matcher(data.getEmail()).matches()) {
        			error += "F009.E012" + "@;-";	
        		}
    		}
    		// Số điện thoại không được trùng nhau trong danh sách khách mời
			if (data.getTel() != null && lstTel.contains(data.getTel())) {
				error += "F009.E014" + "@;-";
	    	}
    	}
    	// Báo lỗi nếu Mã TVTC hoặc CMND/CCCD bị trùng (warning)
    	if (data.getAgentCode() != null) {
    		if ("1".equals(data.getType()) && lstCode.contains(data.getAgentCode())) {
    			warning += "F009.W003" + "@;-";
    		}
    		if (("2".equals(data.getType()) || "3".equals(data.getType())) && lstIdNumber.contains(data.getAgentCode())) {
    			warning += "F009.W003" + "@;-";
    		}
    	}
		// Mã số TVTC giới thiệu không tồn tại trên hệ thống (error) 
		if (data.getReferCode() != null) {
			Db2AgentDto agentDto = db2ApiService.getAgentInfoByCondition(data.getReferCode());
			if(agentDto == null) {
				error += "F009.E013" + "@;-";
			}
		}
		if (StringUtils.isEmpty(error)) {
			error = null;
		}
		if (StringUtils.isEmpty(warning)) {
			warning = null;
		}
		if (error != null || warning != null) {
			eventsImportRepository.updateMessageErrorAndWarning(error, warning, data.getId());
		}
    	return (error != null);
    }
}

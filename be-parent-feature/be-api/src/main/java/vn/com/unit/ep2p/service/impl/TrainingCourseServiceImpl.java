package vn.com.unit.ep2p.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.google.zxing.WriterException;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.cms.core.module.notify.entity.NotifysApplicableDetail;
import vn.com.unit.cms.core.module.notify.repository.NotifyRepository;
import vn.com.unit.cms.core.module.notify.repository.NotifysApplicableDetailRepository;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseExportDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseSearchDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingTraineeDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingTraineeSearchDto;
import vn.com.unit.cms.core.module.trainingCourse.entity.TrainingCoursesDetailEntity;
import vn.com.unit.cms.core.module.trainingCourse.entity.TrainingCoursesEntity;
import vn.com.unit.cms.core.module.trainingCourse.repository.TrainingCourseDetailRepository;
import vn.com.unit.cms.core.module.trainingCourse.repository.TrainingCourseRepository;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.common.utils.QRCodeUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.dto.OfficeDto;
import vn.com.unit.ep2p.admin.dto.OfficeParamDto;
import vn.com.unit.ep2p.admin.dto.TrainingTraineeDB2Dto;
import vn.com.unit.ep2p.admin.dto.TrainingTraineeDB2Param;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.enumdef.EnumExportListTrainingCourses;
import vn.com.unit.ep2p.service.EventsService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.ep2p.service.TrainingCourseService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TrainingCourseServiceImpl extends AbstractCommonService implements TrainingCourseService {

	@Autowired
    private Environment env;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private TrainingCourseDetailRepository trainingCourseDetailRepository;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private TrainingCourseRepository trainingCourseRepository;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;

	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired
	private NotifyRepository notifyRepository;
	
	@Autowired
	private NotifysApplicableDetailRepository notifyDetailRepository;

	@Autowired
    JcaAccountService jcaAccountService;
    
    @Autowired
    SystemLogsService systemLogsService;
    
    @Autowired
	private EventsService eventsService;
    
    private static final String TITLE = "Thông báo Hoạt động huấn luyện";
	private static final String CONTENT_CREATE = "<p><b>%s</b> đã mời bạn tham dự HĐ huấn luyện <b>%s</b> diễn ra vào lúc <b>%s</b> và kết thúc <b>%s</b> tại <b>%s</b>. Vui lòng vào chức năng Quản lý HĐ học tập để xem chi tiết thông tin</p>";
	private static final String CONTENT_END = "<p><b>%s</b> trình duyệt HĐ huấn luyện <b>%s</b> được kết thúc vào lúc <b>%s</b>. Vui lòng vào chức năng Quản lý HĐ huấn luyện để duyệt</p>";
	private static final String CONTENT_APPROVE = "<p><b>%s</b> đã duyệt HĐ huấn luyện <b>%s</b> diễn ra vào ngày <b>%s</b> vào lúc <b>%s</b>. Vui lòng vào chức năng Quản lý HĐ huấn luyện để xem chi tiết thông tin</p>";
	private static final String CONTENT_REJECT = "<p><b>%s</b> đã từ chối HĐ huấn luyện <b>%s</b> diễn ra vào ngày <b>%s</b> vào lúc <b>%s</b> với lý do <b>%s</b>. Vui lòng vào chức năng Quản lý HĐ huấn luyện để xem chi tiết thông tin</p>";
	private static final String LINK_TRAINEE = "/training-management/learning-activities/trainee-details/";
	private static final String LINK_TRAINING = "/training-management/course/training-details/";
	private static final String STORE_GET_LIST_AGENT_BY_LEADER = "RPT_ODS.DS_SP_GET_LIST_AGENT_BY_LEADER";
	private static final String STORE_GET_LIST_AGENT_BY_INTRODUCE = "RPT_ODS.DS_SP_GET_LIST_AGENT_BY_INTRODUCE";
	private static final String STORE_GET_LIST_OFFICE_OF_AGENT_LEADER = "RPT_ODS.DS_SP_GET_LIST_OFFICE_OF_AGENT_LEADER";
	private static final Logger logger = LoggerFactory.getLogger(TrainingCourseServiceImpl.class);

	public TrainingCourseServiceImpl() {
		super();
	}

	@Override
	public CmsCommonPagination<TrainingTraineeDB2Dto> getListGuestsOfTraining(TrainingTraineeSearchDto searchDto) {
		List<TrainingTraineeDB2Dto> datas = new ArrayList<>();
		CmsCommonPagination<TrainingTraineeDB2Dto> rs = new CmsCommonPagination<>();
		TrainingTraineeDB2Param param = new TrainingTraineeDB2Param();
		param.agentCode = searchDto.getAgentCode();
		param.oficeCode = searchDto.getOfficeCode();
		if (StringUtils.isEmpty(searchDto.getStatus())) {
			try {
				if ("LEADER".equalsIgnoreCase(searchDto.getAgentGroupType())) {
					db2ApiService.callStoredb2GetListAgentByLeader(STORE_GET_LIST_AGENT_BY_LEADER, param);
				} else {
					db2ApiService.callStoredb2GetListAgentByLeader(STORE_GET_LIST_AGENT_BY_INTRODUCE, param);
				}
				rs.setData(param.lstData);
			} catch (Exception e) {
				rs.setTotalData(0);
				rs.setData(Collections.emptyList());
			}
			return rs;
		} else if ("1".equals(searchDto.getStatus())) {
			datas = trainingCourseDetailRepository.getListCourseGuest(Long.valueOf(searchDto.getCourseId()));
			if (!"BD".equalsIgnoreCase(searchDto.getAgentGroupType())) {
				if ("LEADER".equalsIgnoreCase(searchDto.getAgentGroupType())) {
					db2ApiService.callStoredb2GetListAgentByLeader(STORE_GET_LIST_AGENT_BY_LEADER, param);
				} else {
					db2ApiService.callStoredb2GetListAgentByLeader(STORE_GET_LIST_AGENT_BY_INTRODUCE, param);
				}
				// Tạo danh sách mới để lưu dữ liệu đã gộp
				List<TrainingTraineeDB2Dto> mergedList = new ArrayList<>();
				// Lấy danh sách invited
				Set<String> lstInvited = datas.stream()
						.map(TrainingTraineeDB2Dto::getAgentCode)
						.collect(Collectors.toSet());
				for (TrainingTraineeDB2Dto invited : datas) {
					invited.setIsUserInvited("1");
					mergedList.add(invited);
				}
				for (TrainingTraineeDB2Dto item : param.lstData) {
					if (!lstInvited.contains(item.getAgentCode())) {
						mergedList.add(item);
					}
				}
				rs.setData(mergedList);
				rs.setTotalData(mergedList.size());
			} else {
				rs.setData(datas);
				rs.setTotalData(datas.size());
			}
		} else {
			datas = trainingCourseDetailRepository.getListCourseGuest(Long.valueOf(searchDto.getCourseId()));
			rs.setData(datas);
			rs.setTotalData(datas.size());
		}
		return rs;
	}

	@Override
	public CmsCommonPagination<TrainingCourseDto> getListTrainingCourseByCondition(TrainingCourseSearchDto searchDto) {
		List<TrainingCourseDto> datas = new ArrayList<>();
		searchDto.setFunctionCode("M_TRAINING_COURSES");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getParamSearch(stringJsonParam,
				"M_TRAINING_COURSES");
		searchDto.setSort(common.getSort());
		searchDto.setSearch(common.getSearch().trim());
		if ("1".equals(searchDto.getAttendance())) {
			searchDto.setAttendance("IS NOT NULL");
		} else if ("0".equals(searchDto.getAttendance())) {
			searchDto.setAttendance("IS NULL");
		}
		if ("BD".equalsIgnoreCase(searchDto.getAgentGroupType())) {
			List<String> offices = db2ApiService.getListOfficeByBd(UserProfileUtils.getFaceMask());
			searchDto.setOffices(offices);
		} else {
			searchDto.setAgentFlg("1");
		}
		int count = 1;
		if (searchDto.getPage() != null) {
			count = trainingCourseRepository.countTrainingCourseByCondition(searchDto);
			Integer offset = searchDto.getPage() == null ? null : Utility.calculateOffsetSQL(searchDto.getPage() + 1, searchDto.getPageSize());
			searchDto.setOffset(offset);
		}
		if (count > 0) datas = trainingCourseRepository.getListTrainingCourseByCondition(searchDto);
		CmsCommonPagination<TrainingCourseDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(count);
		return resultData;
	}

	/**
	 * Generate QRcode for trainingCourse
	 * @param trainingCourse
	 * @return path of QRCodeImage
	 */
	private void generateQRCode(TrainingCoursesEntity trainingCourseEntity) throws WriterException, IOException {
		SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDate = formatDateExport.format(new Date());
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
		String path = systemConfig.getPhysicalPathById(repo, null);
		path = Paths.get(path, "QR-Code").toString();
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String pathFile = Paths.get(path, trainingCourseEntity.getCourseCode() + "_" + currentDate + ".png").toString();
		UUID GUI = UUID.randomUUID();
		String data = trainingCourseEntity.getCourseCode() + "@" + GUI.toString() + "@" + currentDate;
		QRCodeUtil.generateQRCodeImage(data, 280, 280, pathFile);
		trainingCourseEntity.setQrCode(pathFile);
	}

	public byte[] readQrCode(String fileName) throws IOException {
		// create file object
		File file = new File(fileName);
		// initialize a byte array of size of the file
		byte[] fileContent = new byte[(int) file.length()];
		FileInputStream inputStream = null;
		try {
			// create an input stream pointing to the file
			inputStream = new FileInputStream(file);
			// read the contents of file into byte array
			inputStream.read(fileContent);
		} catch (IOException e) {
			throw new IOException("Unable to convert file to byte array. " + e.getMessage());
		} finally {
			// close input stream
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return fileContent;
	}

	@Override
	public TrainingCourseDto addTrainingCourse(TrainingCourseDto dto) throws WriterException, IOException, DetailException {
		if (dto.getTrainees() == null || dto.getTrainees().isEmpty()) {
			throw new DetailException(AppApiExceptionCodeConstant.E9029101_APPAPI_DETAIL_EMPTY_ERROR);
		}
		TrainingCoursesEntity entity = new TrainingCoursesEntity();
		entity.setCourseCode(CommonUtil.getNextBannerCode("HL",
				trainingCourseRepository.getMaxCourseCode("HL")));
		entity.setCourseNameCode(dto.getCourseNameCode());
		entity.setOffice(dto.getOffice());
		entity.setLocation(dto.getLocation());
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setContents(dto.getContents());
		entity.setNotes(dto.getNotes());
		entity.setStatus(1);
		entity.setCreatedBy(UserProfileUtils.getFaceMask());
		entity.setCreatedName(UserProfileUtils.getFullName());
		entity.setCreatedDate(new Date());
		AgentInfoDb2 bdUser = db2ApiService.getBdByOffice(dto.getOffice());
		if (bdUser != null) {
			entity.setApprovedBy(bdUser.getAgentCode());
			entity.setApprovedName(bdUser.getAgentName());
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E9029101_APPAPI_DETAIL_EMPTY_ERROR);
		}
		generateQRCode(entity);
		trainingCourseRepository.save(entity);
		// Thêm danh sách học viên
		List<String> agentCodes = insertTrainees(entity.getId(), dto.getTrainees(), new ArrayList<String>());
		if (agentCodes != null && !agentCodes.isEmpty()) {
			String courseName = null;
			List<EventsMasterDataDto> masterData = eventsService.getListMasterData("TRAINING_ONLINE", null, entity.getCourseNameCode());
			if (masterData != null && masterData.size() > 0) {
				courseName = masterData.get(0).getName();
			}
			String content = String.format(CONTENT_CREATE, entity.getCreatedName()
					, courseName
					, CommonDateUtil.formatDateToString(entity.getStartDate(), "dd/MM/yyyy HH:mm")
					, CommonDateUtil.formatDateToString(entity.getEndDate(), "dd/MM/yyyy HH:mm")
					, entity.getLocation());
			pushNotify(agentCodes, TITLE, content, LINK_TRAINEE + entity.getCourseCode() + "/" + entity.getId());
		}

		dto.setId(entity.getId());
		dto.setCourseCode(entity.getCourseCode());

		return dto;
	}

	@Override
	public TrainingCourseDto updateTrainingCourse(TrainingCourseDto dto) throws DetailException {
		TrainingCoursesEntity entity = trainingCourseRepository.findOne(dto.getId());
		if (ObjectUtils.isNotEmpty(entity)) {
			entity.setCourseNameCode(dto.getCourseNameCode());
			entity.setOffice(dto.getOffice());
			entity.setLocation(dto.getLocation());
			entity.setStartDate(dto.getStartDate());
			entity.setEndDate(dto.getEndDate());
			entity.setContents(dto.getContents());
			entity.setNotes(dto.getNotes());
			entity.setUpdatedBy(UserProfileUtils.getFaceMask());
			entity.setUpdatedDate(new Date());
			if (StringUtils.isNotEmpty(dto.getApprovedBy())) {
				entity.setApprovedBy(dto.getApprovedBy());	
			}
			trainingCourseRepository.save(entity);
			
			if (dto.getTrainees() != null && !dto.getTrainees().isEmpty()) {
				if (dto.isChangeDetails()) {
					List<TrainingTraineeDB2Dto> lstTrainee = trainingCourseDetailRepository.getListCourseGuest(entity.getId());
					List<String> oldCodes = lstTrainee.stream().map(TrainingTraineeDB2Dto::getCode).collect(Collectors.toList());
					List<String> agentCodes = insertTrainees(dto.getId(), dto.getTrainees(), oldCodes);
					String courseName = null;
					List<EventsMasterDataDto> masterData = eventsService.getListMasterData("TRAINING_ONLINE", null, entity.getCourseNameCode());
					if (masterData != null && masterData.size() > 0) {
						courseName = masterData.get(0).getName();
					}
					String content = String.format(CONTENT_CREATE, entity.getCreatedName()
							, courseName
							, CommonDateUtil.formatDateToString(entity.getStartDate(), "dd/MM/yyyy HH:mm")
							, CommonDateUtil.formatDateToString(entity.getEndDate(), "dd/MM/yyyy HH:mm")
							, entity.getLocation());
					pushNotify(agentCodes, TITLE, content, LINK_TRAINEE + entity.getCourseCode() + "/" + entity.getId());
				}
			}
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);
		}

		return dto;
	}

	@Override
	public TrainingCourseDto getDetailTrainingCourse(TrainingCourseSearchDto searchDto) throws DetailException {
		searchDto.setAgentCode(UserProfileUtils.getFaceMask());
		if ("BD".equalsIgnoreCase(searchDto.getAgentGroupType())) {
			List<String> offices = db2ApiService.getListOfficeByBd(UserProfileUtils.getFaceMask());
			searchDto.setOffices(offices);
		} else {
			searchDto.setAgentFlg("1");
		}
		TrainingCourseDto trainingCourseDto = trainingCourseRepository.getDetailTrainingCourse(searchDto);
		if (trainingCourseDto != null) {
			/*if (StringUtils.isNotEmpty(trainingCourseDto.getApprovedBy())) {
				List<String> agentCodes = new ArrayList<>();
				agentCodes.add(trainingCourseDto.getApprovedBy());
				List<Db2AgentDto> agentInfos = db2ApiService.getAgentInfoByCode(agentCodes);
				if (agentInfos.size() > 0) {
					trainingCourseDto.setApprovedName(agentInfos.get(0).getAgentCode() + "-" + agentInfos.get(0).getAgentName());
				}
			}*/
			if (StringUtils.isNotEmpty(trainingCourseDto.getCreatedBy())) {
				List<OfficeDto> office = getListOfficeByAgent(trainingCourseDto.getCreatedBy());
				for (OfficeDto officeDto : office) {
					if (trainingCourseDto.getOffice().equalsIgnoreCase(officeDto.getOfficeCode())) {
						trainingCourseDto.setOfficeName(officeDto.getOfficeName());
						break;
					}
				}
			}
			byte[] bytes = null;
			try {
				bytes = readQrCode(trainingCourseDto.getQrCode());
			} catch (IOException e) {
				bytes = null;
			}
			if (bytes != null) {
				trainingCourseDto.setQrCodeData(Base64.getEncoder().encodeToString(bytes));
			}
			return trainingCourseDto;
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);
		}
	}
	
	@Override
	public List<OfficeDto> getListOfficeByAgent(String agentCode) {
		// Prepare parameters for DB query
		OfficeParamDto param = new OfficeParamDto();
		param.agentCode = agentCode;
		try {
			db2ApiService.callStoreDb2GetListOffice(STORE_GET_LIST_OFFICE_OF_AGENT_LEADER, param);
			return param.lstData;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void updateSatus(TrainingCourseDto dto) throws DetailException {
		TrainingCoursesEntity entity = trainingCourseRepository.findOne(dto.getId());
		if (ObjectUtils.isNotEmpty(entity)) {
			String content = null;
			String courseName = null;
			List<EventsMasterDataDto> masterData = eventsService.getListMasterData("TRAINING_ONLINE", null, entity.getCourseNameCode());
			if (masterData != null && masterData.size() > 0) {
				courseName = masterData.get(0).getName();
			}
			List<String> agentCodes = new ArrayList<String>();
			if ("END".equalsIgnoreCase(dto.getAction())) {
				if (!UserProfileUtils.getFaceMask().equals(entity.getCreatedBy())
						|| entity.getStatus() != 1) {
					throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);
				}
				entity.setStatus(2);
				entity.setCompletedDate(new Date());
				agentCodes.add(entity.getApprovedBy());
				content = String.format(CONTENT_END, entity.getCreatedName()
						, courseName
						, CommonDateUtil.formatDateToString(new Date(), "dd/MM/yyyy HH:mm"));
				pushNotify(agentCodes, TITLE, content, LINK_TRAINING + entity.getCourseCode() + "/" + entity.getId());
			} else if ("APPROVE".equalsIgnoreCase(dto.getAction())) {
				if (!UserProfileUtils.getFaceMask().equals(entity.getApprovedBy())
						|| entity.getStatus() != 2) {
					throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);
				}
				entity.setStatus(3);
				entity.setApprovedBy(UserProfileUtils.getFaceMask());
				entity.setApprovedName(UserProfileUtils.getFullName());
				entity.setApprovedDate(new Date());
				agentCodes.add(entity.getCreatedBy());
				content = String.format(CONTENT_APPROVE, entity.getApprovedName()
						, courseName
						, CommonDateUtil.formatDateToString(entity.getStartDate(), "dd/MM/yyyy HH:mm")
						, CommonDateUtil.formatDateToString(new Date(), "dd/MM/yyyy HH:mm"));
				pushNotify(agentCodes, TITLE, content, LINK_TRAINING + entity.getCourseCode() + "/" + entity.getId());
			} else if ("REJECT".equalsIgnoreCase(dto.getAction())) {
				if (!UserProfileUtils.getFaceMask().equals(entity.getApprovedBy())
						|| entity.getStatus() != 2) {
					throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);
				}
				if (StringUtils.isEmpty(dto.getRejectedReason())) {
					throw new DetailException(AppApiExceptionCodeConstant.E9029100_APPAPI_REJECT_REASON_REQUIRED);
				}
				entity.setStatus(4);
				entity.setRejectedBy(UserProfileUtils.getFaceMask());
				entity.setRejectedName(UserProfileUtils.getFullName());
				entity.setRejectedReason(dto.getRejectedReason());
				entity.setRejectedDate(new Date());
				agentCodes.add(entity.getCreatedBy());
				content = String.format(CONTENT_REJECT, entity.getApprovedName()
						, courseName
						, CommonDateUtil.formatDateToString(entity.getStartDate(), "dd/MM/yyyy HH:mm")
						, CommonDateUtil.formatDateToString(new Date(), "dd/MM/yyyy HH:mm")
						, entity.getRejectedReason());
				pushNotify(agentCodes, TITLE, content, LINK_TRAINING + entity.getCourseCode() + "/" + entity.getId());
			} else if ("DELETE".equalsIgnoreCase(dto.getAction())) {
				if (!UserProfileUtils.getFaceMask().equals(entity.getCreatedBy())) {
					throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);
				}
				entity.setDeleteFlg(true);
			}
			trainingCourseRepository.save(entity);
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);
		}
	}
	
	@Override
	public int checkinTrainingCourse(String courseCode, String agentCode) throws DetailException {
		int updCount = trainingCourseDetailRepository.updateAttendance(courseCode, agentCode);
		if (updCount > 0) {
			return updCount;
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E9029102_APPAPI_ATTENDANCED_ERROR);
		}
	}
	
	@Override
	public ResponseEntity exportListTrainingCourses(TrainingCourseSearchDto searchDto,
			HttpServletResponse response, Locale locale) {
		ResponseEntity res = null;
		try {
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			if ("1".equals(searchDto.getAttendance())) {
				searchDto.setAttendance("IS NOT NULL");
			} else if ("0".equals(searchDto.getAttendance())) {
				searchDto.setAttendance("IS NULL");
			}
			if ("BD".equalsIgnoreCase(searchDto.getAgentGroupType())) {
				List<String> offices = db2ApiService.getListOfficeByBd(UserProfileUtils.getFaceMask());
				searchDto.setOffices(offices);
			} else {
				searchDto.setAgentFlg("1");
			}
			List<TrainingCourseExportDto> datas = trainingCourseRepository.getListExportTrainingCourses(searchDto);
			String datePattern = "dd/MM/yyyy HH:mm";
			String templateName = "Danh_sach_hoat_dong_huan_luyen.xlsx";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(EnumExportListTrainingCourses.class, cols);
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A6";
			// start fill data to workbook
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat =  new HashMap<>();// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, datas,
						TrainingCourseExportDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}
	
	public TrainingCourseDto getTrainingCourseByCode(String courseCode, String agentCode) {
		return trainingCourseRepository.getTrainingCourseByCode(courseCode, agentCode);
	}
	
	private List<String> insertTrainees(Long courseId, List<TrainingTraineeDto> dtos, List<String> oldCodes) {
		List<String> agentCodes = new ArrayList<String>();
		List<String> newCodes = dtos.stream() .map(dto -> dto.getAgentCode() + ":" + dto.getIdNumber()).collect(Collectors.toList());
		List<String> deletes = oldCodes.stream().filter(code -> !newCodes.contains(code)).collect(Collectors.toList());
		List<String> inserts = newCodes.stream().filter(code -> !oldCodes.contains(code)).collect(Collectors.toList());
		Date current = new Date();
		for (TrainingTraineeDto dto : dtos) {
			if (inserts.contains(dto.getAgentCode() + ":" + dto.getIdNumber())) {
				TrainingCoursesDetailEntity entity = new TrainingCoursesDetailEntity();
				entity.setCourseId(courseId);
				entity.setIdNumber(dto.getIdNumber());
				entity.setName(dto.getName());
				entity.setEffectivedDate(dto.getEffectivedDate());
				entity.setAttendanceTime(dto.getAttendanceTime());
				entity.setAgentType(dto.getAgentType());
				entity.setUmCode(dto.getUmCode());
				entity.setUmName(dto.getUmName());
				entity.setBmCode(dto.getBmCode());
				entity.setBmName(dto.getBmName());
				entity.setOfficeCode(dto.getOfficeCode());
				entity.setOfficeName(dto.getOfficeName());
				entity.setRegisterTime(current);
				if (StringUtils.isNotEmpty(dto.getAgentCode())) {
					entity.setAgentCode(dto.getAgentCode());
					agentCodes.add(dto.getAgentCode());
				} else {
					agentCodes.add(dto.getIdNumber());
				}
				trainingCourseDetailRepository.save(entity);
			}
		}
		if (deletes != null && deletes.size() > 0) {
			trainingCourseDetailRepository.deleteTrainees(courseId, deletes);
		}
		return agentCodes;
	}
	
	/**
     * Push notification when create or update training course
     * @param agentCodes
     * @param title
     * @param contents
     * @param link
     * @return
     */
    private Long pushNotify(List<String> agentCodes, String title, String contents, String link) {
    	//save notify
		Notify entity = new Notify();
		entity.setNotifyCode(getNotifyCode());
		entity.setNotifyTitle(title);
		entity.setNotifyType(2);
		entity.setContents(contents);
		entity.setLinkNotify(link);
		entity.setSendImmediately(true);
		entity.setActive(true);
		entity.setApplicableObject("ALL");
		entity.setCreateBy("system");
		entity.setSendDate(new Date());
		entity.setSend(true);
		entity.setFc(false);
		entity.setCreateDate(new Date());
		entity.setNotifyType(1);
		entity.setSaveDetail(false);
		notifyRepository.save(entity);
		
		//save notify detail
		for (String agentCode : agentCodes) {
			NotifysApplicableDetail entityDetail = new NotifysApplicableDetail();
			entityDetail.setNotifyId(entity.getId());
			entityDetail.setAgentCode(new Long(agentCode));
			entityDetail.setReadAlready(false);
			entityDetail.setCreateDate(new Date());
			notifyDetailRepository.save(entityDetail);
		}
		try {
			pushNotificationToFirebaseCloud(entity, agentCodes);
		} catch (Exception e) {
			logger.error("##pushNotify##" + e);
		}
		
		return entity.getId();
	}
    
    private String getNotifyCode() {
		SimpleDateFormat format = new SimpleDateFormat("yy");
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		return CommonUtil.getNextBannerCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT,
				notifyRepository.getMaxNotifyCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT
						+ format.format(new Date()) + formatMM.format(new Date())));
	}
    
    private void pushNotificationToFirebaseCloud(Notify dto, List<String> listAgent) throws Exception {
    	try {
            String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(fileName));

            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            try {
                List<String> lstToken = new ArrayList<>();
                List<String> lstAgentNotToken = new ArrayList<>();
                if (!listAgent.isEmpty()) {
                	int total = listAgent.size();
 					int maxParm = 2000;
 					int fromIndex = 0;
 					int toIndex = total > (fromIndex + maxParm) ? (fromIndex + maxParm) : total;
 					while (fromIndex < total) {
 						List<String> lstAgent = listAgent.subList(fromIndex, toIndex);
 						String dataList = String.join(",", lstAgent);
 						List<JcaAccount> lstAccount = jcaAccountService
 								.getListByUserNameList(dataList);
 						
 						for (JcaAccount acc : lstAccount) {
 							if (StringUtils.isNotEmpty(acc.getDeviceTokenMobile())) {
 								// Add List Token
 								lstToken.add(acc.getDeviceTokenMobile());
 							} else {
 								// Add list agent not token
 								lstAgentNotToken.add(acc.getUsername());
 							}
 						}

 						fromIndex = toIndex;
 						toIndex = total > (fromIndex + maxParm) ? (fromIndex + maxParm) : total;
 					}
 					
 					MulticastMessage multicastMessage = allPlatformsMessage(lstToken, dto);
 					BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage);
 					
 					if (response.getFailureCount() > 0) {
 						List<SendResponse> responses = response.getResponses();
 						List<String> failedTokens = new ArrayList<>();
 						for (int i = 0; i < responses.size(); i++) {
 							if (!responses.get(i).isSuccessful()) {
 								// The order of responses corresponds to the order of the registration tokens.
 								failedTokens.add(lstToken.get(i));
 							}
 						}
 						
 						if (!failedTokens.isEmpty()) {
 					        systemLogsService.writeSystemLogs("NOTIFY_CODE_EVENT", "Send notif device", "List of tokens that caused failures: " + String.join(",", failedTokens), null);
 							logger.error("List of tokens that caused failures: " + String.join(",", failedTokens));
 						}
 					}
 					
 					if (!lstAgentNotToken.isEmpty()) {
 						logger.error("List agent has not token: " + String.join(",", lstAgentNotToken));
 					}
                     
                 } else {
                     logger.error("List token is empty!");
                 }
             } catch (Exception ex) {
 		        systemLogsService.writeSystemLogs("NOTIFY_CODE_EVENT", "Send notif device", ex.getMessage(), null);
                 logger.error("PUSH_NOTIFICATION", ex);
             }
         } catch (Exception e) {
             logger.error(e.getMessage(), e);
             systemLogsService.writeSystemLogs("NOTIFY_CODE_EVENT", "Send notif device", e.getMessage(), null);
             throw new Exception("Push notification to firebase failed!");
         }
     }

     public MulticastMessage allPlatformsMessage(List<String> deviceToken, Notify dto) {
         final String ACTION = "FLUTTER_NOTIFICATION_CLICK";
         final String NOTIFICATION = "notificationPage";
         MulticastMessage multicastMessage = MulticastMessage.builder()
                 .setNotification(Notification.builder()
                         .setTitle(dto.getNotifyTitle())
                         .setBody(dto.getContents().replaceAll("<[^>]*>", "").replace("    ", " "))
                         .build())
                 .putData("click_action", ACTION)
                 .putData("route", NOTIFICATION)
                 .addAllTokens(deviceToken)
                 .build();
         return multicastMessage;
     }
}

package vn.com.unit.ep2p.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerDetailParam;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestSearchDto;
import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.events.dto.EventsSearchDto;
import vn.com.unit.cms.core.module.events.entity.Events;
import vn.com.unit.cms.core.module.events.entity.EventsApplicableDetail;
import vn.com.unit.cms.core.module.events.imports.dto.EventsImportDto;
import vn.com.unit.cms.core.module.events.imports.repository.EventsImportRepository;
import vn.com.unit.cms.core.module.events.repository.EventsApplicableDetailRepository;
import vn.com.unit.cms.core.module.events.repository.EventsMasterDataRepository;
import vn.com.unit.cms.core.module.events.repository.EventsRepository;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.cms.core.module.notify.repository.NotifyRepository;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.common.utils.QRCodeUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayDto;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayInMonthDto;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayInMonthParam;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayParam;
import vn.com.unit.ep2p.admin.dto.CustomerChargeDto;
import vn.com.unit.ep2p.admin.dto.CustomerChargeInMonthParam;
import vn.com.unit.ep2p.admin.dto.CustomerChargeParam;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.EventDetailDto;
import vn.com.unit.ep2p.admin.repository.AccountRepository;
import vn.com.unit.cms.core.module.appointment.repository.AppointmentRepository;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.enumdef.EnumExportReportListEvents;
import vn.com.unit.ep2p.enumdef.EnumExportReportListGuests;
import vn.com.unit.ep2p.service.ApiCustomerInformationService;
import vn.com.unit.ep2p.service.EventsService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

import vn.com.unit.ep2p.admin.dto.CustomerAppointmentDto;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EventsServiceImpl extends AbstractCommonService implements EventsService{
	
	@Autowired
    private Environment env;
	
	@Autowired
	private EventsRepository eventsRepository;
	
	@Autowired
    EventsImportRepository eventsImportRepository;
	
	@Autowired
	private NotifyRepository notifyRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private ServletContext servletContext;

	@Autowired
    private SystemConfig systemConfig;
	
	@Autowired
	private EventsMasterDataRepository eventsMasterDataRepository;
	
	@Autowired
	private EventsApplicableDetailRepository eventsApplicableDetailRepository;
	
	@Autowired
	private Db2ApiService db2ApiService;

	@Autowired
	private EventsImportServiceImpl eventsImportService;

	@Autowired
	private ApiCustomerInformationService apiCustomerInformationService;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

    @Autowired
    JcaAccountService jcaAccountService;
    
    @Autowired
    SystemLogsService systemLogsService;
    
	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	private static final String STORE_CUSTOMER_BIRTHDAY = "RPT_ODS.DS_SP_GET_LIST_CUSTOMERS_BIRTHDAY";
	private static final String STORE_CUSTOMER_CHARGE = "RPT_ODS.DS_SP_GET_INSURANCE_PREMIUM_SCHEDULE";
	private static final String STORE_CUSTOMER_BIRTHDAY_D2D = "RPT_ODS.DS_SP_GET_LIST_CUSTOMERS_BIRTHDAY_D2D";
	private static final String STORE_CUSTOMER_CHARGE_D2D = "RPT_ODS.DS_SP_GET_INSURANCE_PREMIUM_SCHEDULE_D2D";
	private static final String SP_DETAIL_CUSTOMER_BY_CUSTOMER_CODE = "RPT_ODS.DS_SP_GET_CUSTOMERS_INFOR";
	private static final Logger logger = LoggerFactory.getLogger(EventsServiceImpl.class);
	private static final String PREFIX = "EVE";
	private static final String NOTIFY_TITLE = "Thông báo sự kiện";
	private static final String LINK_DETAIL = "/events-management/detail-event/";
	private static final String NOTIFY_DELETE = "<p>Chúng tôi rất tiếc sự kiện <b>%s</b> đã bị hủy vì lý do khách quan.</p>";
	private static final String NOTIFY_ADD_NEW = "<p>Anh/Chị được mời tham dự sự kiện <b>%s</b> vào lúc <b>%s</b> ngày <b>%s</b>. "
												+ "Hân hạnh được đón tiếp Anh/Chị.</p>    <p>Trân trọng kính mời!</p>";
	private static final String NOTIFY_UPDATE = "<p>Sự kiện <b>%s</b> có thay đổi thời gian và địa điểm sẽ diễn ra vào lúc <b>%s</b> ngày <b>%s</b>. "
												+ "Chúng tôi rất tiếc vì sự thay đổi đột ngột này!</p>";
	private static final String NOTIFY_UPDATE_DELETE_GUEST = "<p>Do có sự thay đổi, Anh/Chị vui lòng bỏ qua lịch tham dự sự kiện <b>%s</b> diễn ra vào lúc <b>%s</b> ngày <b>%s</b>. "
												+ "Chúng tôi rất tiếc vì sự thay đổi đột ngột này!</p>";
	
	private final int batchSize = 10000;
	public EventsDto editOrAddEvents(EventsDto dto) throws WriterException, IOException, SQLException {
		
		Events entity = new Events();
		entity.setEventCode(dto.getEventCode());
		entity.setEventCode(CommonUtil.getNextBannerCode(PREFIX,
				eventsRepository.getMaxEventCode(PREFIX)));
		entity.setApplicableObject(dto.getApplicableObject());
		entity.setArea(dto.getArea());
		entity.setContents(dto.getContents());
		entity.setCreateBy(UserProfileUtils.getFaceMask());
		entity.setCreateDate(new Date());
		entity.setEventDate(dto.getEventDate());
		entity.setEventLocation(dto.getEventLocation());
		entity.setEventTitle(dto.getEventTitle());
		entity.setLinkNotify(dto.getLinkNotify());
		entity.setNotes(dto.getNote());
		entity.setOffice(dto.getOffice());
		entity.setPosition(dto.getPosition());
		entity.setRegion(dto.getRegion());
		entity.setTerritorry(dto.getTerritorry());
		//----------SR13922(start)----------
		entity.setEventType(dto.getEventType());
		entity.setGroupEventCode(dto.getGroupEventCode());
		entity.setActivityEventCode(dto.getActivityEventCode());
		entity.setEndDate(dto.getEndDate());
		generateQRCode(entity);
		//----------SR13922(end)----------
		//eventsRepository.save(entity);
		//save detail
		if (StringUtils.equalsIgnoreCase(dto.getApplicableObject() , "ALL")) {
			// Sự kiện có khách mời
			if ("2".equals(dto.getEventType())) {
				entity.setSaveDetail(true);
			}
		}
		if (StringUtils.equalsIgnoreCase(dto.getApplicableObject() , "SEL")) {
			entity.setSaveDetail(true);
		}
		if (StringUtils.equalsIgnoreCase(dto.getApplicableObject() , "IMP")) {
			entity.setSaveDetail(false);
		}
		eventsRepository.save(entity);
		if (StringUtils.equalsIgnoreCase(dto.getApplicableObject() , "IMP")) {
			insertToDatabaseWithBatch(entity.getId(), dto.getSessionKey());
			pushNotifyAddNew(entity, null);
		}
		dto.setId(entity.getId());
		dto.setEventCode(entity.getEventCode());
		
		return dto;
	}
	
	private List<String> insertToDatabaseWithBatch(Long eventId, String sessionKey) throws SQLException {
		List<EventsImportDto> lstDataImport = eventsImportService.getAllDatas(sessionKey);
		if (lstDataImport == null || lstDataImport.isEmpty()) {
			return null;
		}
		List<String> lstAgentCode = new ArrayList<>();
		Connection connection = connectionProvider.getConnection();
		connection.setAutoCommit(false);
		String query = "INSERT INTO dbo.M_EVENTS_APPLICABLE_DETAIL"
				+ " (EVENT_ID, TERRITORRY, AREA, REGION, OFFICE, POSITION, AGENT_CODE, ID_NUMBER, NAME, GENDER, EMAIL, TEL, ADDRESS, REFER_CODE, GUEST_TYPE)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst = connection.prepareStatement(query);
		int startRow = 0;
		for(EventsImportDto data : lstDataImport){
			pst.setLong(1,eventId);
			pst.setString(2, data.getTerritorry());
			pst.setString(3, data.getArea());
			pst.setString(4, data.getRegion());
			pst.setString(5, data.getOffice());
			pst.setString(6, data.getPosition());
			if ("1".equals(data.getType())) {
				lstAgentCode.add(data.getAgentCode());
				pst.setString(7, data.getAgentCode());
				pst.setString(8, data.getIdNumber());
			} else {
				pst.setString(7, null);
				pst.setString(8, data.getAgentCode());
			}
			pst.setString(9, data.getName());
			pst.setString(10, data.getGender());
			pst.setString(11, data.getEmail());
			pst.setString(12, data.getTel());
			pst.setString(13, data.getAddress());
			pst.setString(14, data.getReferCode());
			pst.setString(15, data.getType());
			pst.addBatch();
			startRow ++;
			if (startRow % batchSize == 0) {
				pst.executeBatch();
				pst.clearBatch();
				startRow = 0;
			}
		}
		pst.executeBatch();
		pst.clearBatch();
		connection.commit();
		
		return lstAgentCode;
	}
	
	@Override
	public CmsCommonPagination<EventsDto> getListEventByDate(Date eventDate, Integer page, Integer pageSize, CommonSearchWithPagingDto search) {
		// String user = UserProfileUtils.getFaceMask();
		String user = UserProfileUtils.getUserPrincipal().getUsername();
		CmsCommonPagination<EventsDto> resultData = new CmsCommonPagination<EventsDto>();
		List<EventsDto> lstData = new ArrayList<>();
		if(user != null){
			int count = eventsRepository.countByCondition(eventDate, user);
			Integer offset = page == null ? null : Utility.calculateOffsetSQL(page + 1, pageSize);
			if(count > 0) lstData = eventsRepository.getListEventByDate(eventDate, offset, pageSize, user, search);
			resultData.setData(lstData);
			resultData.setTotalData(count);
		}
		return resultData;
	}

	@Override
	public  CmsCommonPagination<CustomerBirthdayDto> getListCustomerBirthDay(Date eventDate, Integer page, Integer pageSize, String customerRole, CommonSearchWithPagingDto searchDto) {
		String user = UserProfileUtils.getFaceMask();
		CustomerBirthdayParam customerBirthdayParam = new CustomerBirthdayParam();
		CmsCommonPagination<CustomerBirthdayDto> resultData = new CmsCommonPagination<CustomerBirthdayDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if(eventDate != null){
			customerBirthdayParam.eventDate = sdf.format(eventDate);
		}
		customerBirthdayParam.agentCode = user;
		customerBirthdayParam.page = page;
		customerBirthdayParam.pageSize = pageSize;
		customerBirthdayParam.customerRole=customerRole;
		if(!StringUtils.contains(searchDto.getSort(),"c.NO ")){
			customerBirthdayParam.sort=StringUtils.isBlank(searchDto.getSort()) ? null : "ORDER BY c.CLI_NAME asc";
		}
		try{
			db2ApiService.callStoredb2Birthday(STORE_CUSTOMER_BIRTHDAY, customerBirthdayParam);
			resultData.setData(customerBirthdayParam.lstData);
			resultData.setTotalData(customerBirthdayParam.totalRows);
		} catch (Exception e){
			logger.error(e.getMessage());
			resultData.setData(new ArrayList<>());
			resultData.setTotalData(0);
		}

		return resultData;
	}
	public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant()
				.atZone(ZoneId.of("Asia/Hong_Kong"))
				.toLocalDateTime();
	}
	@Override
	public CmsCommonPagination<CustomerChargeDto> getListCustomerChargeByDate(Date eventDate, Integer page, Integer pageSize, String status, CommonSearchWithPagingDto searchDto) {
		String user = UserProfileUtils.getFaceMask();
		CustomerChargeParam customerChargeParam = new CustomerChargeParam();
		CmsCommonPagination<CustomerChargeDto> resultData = new CmsCommonPagination<CustomerChargeDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if(eventDate != null){
			customerChargeParam.eventDate = sdf.format(eventDate);
		}
		customerChargeParam.agentCode = user;
		customerChargeParam.page = page;
		customerChargeParam.pageSize = pageSize;
		customerChargeParam.status = status;
		if(!StringUtils.contains(searchDto.getSort(),"c.NO ")){
			customerChargeParam.sort=StringUtils.isBlank(searchDto.getSort()) ? null : "ORDER BY " + searchDto.getSort();
		}
		try {
			db2ApiService.callStoreCustomerCharge(STORE_CUSTOMER_CHARGE, customerChargeParam);
			resultData.setData(customerChargeParam.lstData);
			resultData.setTotalData(customerChargeParam.totalRows);
		} catch (Exception e){
			logger.error(e.getMessage());
			resultData.setData(new ArrayList<>());
			resultData.setTotalData(0);
		}
		return resultData;
	}

	@Override
	public List<EventDetailDto> getAllEventsInMonth(Date eventDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// String user = UserProfileUtils.getFaceMask();
		String user = UserProfileUtils.getUserPrincipal().getUsername();
		List<EventDetailDto> lstEvents = new ArrayList<>();
		if(eventDate != null && user != null){

			Calendar firstDayOfMonth = Calendar.getInstance();
			firstDayOfMonth.setTime(eventDate);
			firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);

			Calendar lastDayOfMonth = Calendar.getInstance();
			lastDayOfMonth.setTime(eventDate);
			lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));

			try {
				String firstDayOfMonthStr = sdf.format(firstDayOfMonth.getTime());
				String lastDayOfMonthStr = sdf.format(lastDayOfMonth.getTime());
				if (user.chars().allMatch(Character::isDigit)) {
					CustomerBirthdayInMonthParam customerBirthdayParam = new CustomerBirthdayInMonthParam();
					customerBirthdayParam.agentCode = user;
					customerBirthdayParam.fromDate = firstDayOfMonthStr;
					customerBirthdayParam.toDate = lastDayOfMonthStr;
	
					db2ApiService.callStoredBirthdayInMonth(STORE_CUSTOMER_BIRTHDAY_D2D, customerBirthdayParam);
	
					List<CustomerBirthdayInMonthDto> lstData = customerBirthdayParam.lstData;
					if(!lstData.isEmpty()){
						lstData.stream().forEach(e->{
							Calendar cal = Calendar.getInstance();
							cal.setTime(e.getDateOfBirth());
							cal.set(Calendar.YEAR, 2000);
							e.setDateOfBirth(cal.getTime());
						});
						//distinct data with same day and month
	//					List<CustomerBirthdayInMonthDto> lstResult = lstData.stream()
	//							.filter(distinctByKey(CustomerBirthdayInMonthDto::getDateOfBirth))
	//							.collect(Collectors.toList());
						Map<Date,List<CustomerBirthdayInMonthDto>> mapResult = lstData.stream().collect(Collectors.groupingBy(CustomerBirthdayInMonthDto::getDateOfBirth));
						for (Map.Entry<Date, List<CustomerBirthdayInMonthDto>> entry : mapResult.entrySet()) {
							Calendar birthDayWithoutYear = Calendar.getInstance();
							Calendar eventDateCal = Calendar.getInstance();
	
							eventDateCal.setTime(eventDate);
	
							birthDayWithoutYear.setTime(entry.getKey());
							birthDayWithoutYear.set(Calendar.YEAR, eventDateCal.get(Calendar.YEAR));
	
							EventDetailDto eventDetailDto = new EventDetailDto();
							eventDetailDto.setTitle("Sinh nhật khách hàng");
							eventDetailDto.setStart(birthDayWithoutYear.getTime());
							eventDetailDto.setEnd(birthDayWithoutYear.getTime());
							eventDetailDto.setType("3");
							eventDetailDto.setCount(entry.getValue().size());
							lstEvents.add(eventDetailDto);
						}
					}
				}

				//normal event
				Calendar cal = Calendar.getInstance();
				cal.setTime(eventDate);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				int month=cal.get(Calendar.MONTH);

				while (month==cal.get(Calendar.MONTH)) {
					int countEvent = eventsRepository.countByCondition(cal.getTime(), user);
					if(countEvent > 0){
						EventDetailDto eventDetailDto = new EventDetailDto();
						eventDetailDto.setType("1");
						eventDetailDto.setTitle("sự kiện");
						eventDetailDto.setStart(cal.getTime());
						eventDetailDto.setEnd(cal.getTime());
						lstEvents.add(eventDetailDto);
					}

					// Add appointment schedule
					int countAppointment = appointmentRepository.countByCondition(cal.getTime(), user);
					if (countAppointment > 0) {
						List<CustomerAppointmentDto> lstAppointment = appointmentRepository
								.getListAppointmentByCondition(cal.getTime(), null, null, user, null);
						if (!lstAppointment.isEmpty()) {
							EventDetailDto appointmentDto = new EventDetailDto();
							appointmentDto.setType("4");
							appointmentDto.setTitle("Lịch đặt hẹn");
							appointmentDto.setStart(cal.getTime());
							appointmentDto.setEnd(cal.getTime());
							appointmentDto.setCount(lstAppointment.size());
							lstEvents.add(appointmentDto);
						}
					}

					cal.add(Calendar.DAY_OF_MONTH, 1);
				}

				if (user.chars().allMatch(Character::isDigit)) {
					CustomerChargeInMonthParam customerChargeInMonthParam = new CustomerChargeInMonthParam();
					customerChargeInMonthParam.agentCode = user;
					customerChargeInMonthParam.fromDate = firstDayOfMonthStr;
					customerChargeInMonthParam.toDate = lastDayOfMonthStr;
					db2ApiService.callStoreCustomerChargeInMonth(STORE_CUSTOMER_CHARGE_D2D, customerChargeInMonthParam);
	
					List<CustomerChargeDto> lstCharge = customerChargeInMonthParam.lstData;
					if(!lstCharge.isEmpty()){
	//					List<CustomerChargeDto> lstResult = lstCharge.stream()
	//							.filter(e->Objects.nonNull(e.getPolPaidToDate()))
	//							.filter(distinctByKey(CustomerChargeDto::getPolPaidToDate))
	//							.collect(Collectors.toList());
						Map<Date,List<CustomerChargeDto>> mapResult = lstCharge.stream().collect(Collectors.groupingBy(CustomerChargeDto::getPolPaidToDate));
						for (Map.Entry<Date, List<CustomerChargeDto>> entry : mapResult.entrySet()) {
							EventDetailDto eventDetailDto = new EventDetailDto();
							eventDetailDto.setType("2");
							eventDetailDto.setTitle("Thu phí khách hàng");
							eventDetailDto.setStart(entry.getKey());
							eventDetailDto.setEnd(entry.getKey());
							eventDetailDto.setCount(entry.getValue().size());
							lstEvents.add(eventDetailDto);
						}
					}
				}
			} catch (Exception e){
				logger.error("get list event in month error: ", e.getMessage());
			}
		}
		return lstEvents;
	}

	@Override
	public CustomerInformationDetailDto getDetailCustomer(String customerId) {
		CustomerInformationDetailDto customerInformationDetailDto = new CustomerInformationDetailDto();
		// check agent đăng nhập có quyền xem thông tin chi tiết KH này không
		String user = UserProfileUtils.getFaceMask();
		CustomerBirthdayParam customerBirthdayParam = new CustomerBirthdayParam();
		customerBirthdayParam.agentCode = user;
		customerBirthdayParam.clientId = new Integer(customerId);
		customerBirthdayParam.page = 0;
		customerBirthdayParam.pageSize = 5;
		db2ApiService.callStoredb2Birthday(STORE_CUSTOMER_BIRTHDAY, customerBirthdayParam);

		if (customerBirthdayParam.lstData == null || customerBirthdayParam.lstData.isEmpty()) {
			customerInformationDetailDto.setResult(false);
			return customerInformationDetailDto;
		}

		// lấy thông tin chi tiết
		CustomerDetailParam param = new CustomerDetailParam();
		param.customerNo = customerId;
		apiCustomerInformationService.callStoreCustomerBirthdayDetail(SP_DETAIL_CUSTOMER_BY_CUSTOMER_CODE, param);

		if (param.data != null && !param.data.isEmpty()) {
			customerInformationDetailDto = param.data.get(0);
			customerInformationDetailDto.setResult(true);
		}

		return customerInformationDetailDto;
	}
	
	@Override
	public List<EventsMasterDataDto> getListMasterData(String type, String parentId, String code) {
		return eventsMasterDataRepository.getListDataByType(type, parentId, code);
	}
	
	@Override
	public CmsCommonPagination<EventsDto> getListEventsByCondition(EventsSearchDto searchDto) {
		List<EventsDto> datas = new ArrayList<>();
		searchDto.setFunctionCode("M_EVENT");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getParamSearch(stringJsonParam,
				"M_EVENT");
		searchDto.setSort(common.getSort());
		searchDto.setSearch(common.getSearch().trim());
		
		int count = 1;
		if (searchDto.getPage() != null) {
			count = eventsRepository.countEventsByCondition(searchDto);
			Integer offset = searchDto.getPage() == null ? null : Utility.calculateOffsetSQL(searchDto.getPage() + 1, searchDto.getPageSize());
			searchDto.setOffset(offset);	
		}
		if(count > 0) datas = eventsRepository.getListEventsByCondition(searchDto);
		CmsCommonPagination<EventsDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(count);
		return resultData;
	}
	
	@Override
	public EventsDto getDetailEvent(String eventId, String userLogin) {
		EventsDto event = eventsRepository.getDetailEvent(eventId, userLogin);
		JcaAccount acountCreated = accountRepository.findByUserNameAndCompanyId(event.getCreateBy(), null);
		if ("1".equals(acountCreated.getAccountType())) {
			event.setCreateName(acountCreated.getUsername() + " - " + acountCreated.getFullname());
		} else {
			List<String> agentCodes = new ArrayList<>();
			agentCodes.add(event.getCreateBy());
			List<Db2AgentDto> agentInfos = db2ApiService.getAgentInfoByCode(agentCodes);
			if (agentInfos.size() > 0) {
				event.setCreateName(agentInfos.get(0).getAgentType() + ": " + agentInfos.get(0).getAgentCode()
						+ " - " + agentInfos.get(0).getAgentName());
			}
		}
		byte[] bytes = null;
		try {
			bytes = readQrCode(event.getQrCode());
		} catch (IOException e) {
			bytes = null;
		}
		if (bytes != null) {
			event.setQrCodeData(Base64.getEncoder().encodeToString(bytes));	
		}
		return event;
	}
	
	@Override
	public EventsDto getEventByCode(String eventCode) {
		return eventsRepository.getEventByCode(eventCode);
	}
	
	@Override
	public void updateEvent(EventsDto dto) throws DetailException, SQLException {
		boolean hasChangeTimeOrLocation = false;
		Events entity = eventsRepository.findOne(dto.getId());
		if(ObjectUtils.isNotEmpty(entity)) {
			if (!StringUtils.equalsIgnoreCase(entity.getEventLocation(), dto.getEventLocation())
					|| (entity.getEventDate().compareTo(dto.getEventDate()) != 0)) {
				hasChangeTimeOrLocation = true;
			}
			//Cập nhật sự kiện
			entity.setEventTitle(dto.getEventTitle());
			entity.setEventDate(dto.getEventDate());
			entity.setEndDate(dto.getEndDate());
			entity.setGroupEventCode(dto.getGroupEventCode());
			entity.setActivityEventCode(dto.getActivityEventCode());
			entity.setEventLocation(dto.getEventLocation());
			entity.setLinkNotify(dto.getLinkNotify());
			entity.setNotes(dto.getNote());
			entity.setContents(dto.getContents());
			entity.setUpdateBy(UserProfileUtils.getFaceMask());
			entity.setUpdateDate(new Date());
			
			List<String> lstAgentCodeDel = null;
			List<String> lstAgentCodeIns = null;
			// có import danh sách KM
			if (StringUtils.isNotEmpty(dto.getActionType())) {
				lstAgentCodeDel = notifyRepository.getAgentCodeDel(dto.getId(), dto.getSessionKey());
				lstAgentCodeIns = notifyRepository.getAgentCodeIns(dto.getId(), dto.getSessionKey());
				// thêm KM
				if ("INS".equals(dto.getActionType())) {
					// thêm khách mời
					List<String> agentCodes = insertToDatabaseWithBatch(dto.getId(), dto.getSessionKey());
					pushNotifyAddNew(entity, agentCodes);
				} else if ("MOD".equals(dto.getActionType())) {
					List<EventsImportDto> lstCodeDuplicate = eventsImportRepository.getListCodeDuplicate(dto.getSessionKey());
					List<String> lstAgentCode = new ArrayList<>();
					List<String> lstIdNumber = new ArrayList<>();
					for(EventsImportDto detail : lstCodeDuplicate) {
						if (detail.getAgentCode() != null) {
							lstAgentCode.add(detail.getAgentCode());
						}
						if (StringUtils.isNotEmpty(detail.getIdNumber())) {
							lstIdNumber.add(detail.getIdNumber());
						}
					}
					
					entity.setSaveDetail(false);
					// push cho người được mời sau đó ko được mời
					if (!lstAgentCodeDel.isEmpty()) {
						pushNotifyUpdateDeleteGuest(dto, lstAgentCodeDel);
					}
					// thực hiện xóa detail đã import trước đó
					eventsApplicableDetailRepository.deleteByEventId(dto.getId(), lstAgentCode, lstIdNumber);
					// insert mới
					insertToDatabaseWithBatch(dto.getId(), dto.getSessionKey());
					// push cho người được mời bổ sung
					if (!lstAgentCodeIns.isEmpty()) {
						pushNotifyAddNew(entity, lstAgentCodeIns);
					}
					// push khi có thay đổi thời gian, địa điểm
					if (hasChangeTimeOrLocation && !lstAgentCode.isEmpty()) {
						pushNotifyUpdate(dto, lstAgentCode);
					}
				}
				entity.setApplicableObject(dto.getApplicableObject());
			} else {
				// push khi có thay đổi thời gian, địa điểm
				if (hasChangeTimeOrLocation) {
					pushNotifyUpdate(dto, null);
				}
			}
			eventsRepository.save(entity);
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);  
		}
	}
	
	@Override
	public void deleteEventById(Long id, String deleteBy) throws DetailException {
		Events entity = eventsRepository.findOne(id);
		if(ObjectUtils.isNotEmpty(entity)) {
			entity.setDelFlg(true);
			entity.setDeleteBy(deleteBy);
			entity.setDeleteDate(new Date());
			eventsRepository.save(entity);
			// push notify
			pushNotifyDelete(id);
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E4027101_APPAPI_ID_EXISTS_ERROR);  
		}
	}
	
	@Override
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
	public EventsDto getParticipantInfo(String eventCode) {
		return eventsRepository.getParticipantInfo(eventCode);
	}
	
	@Override
	public CmsCommonPagination<EventsGuestDetailDto> getListGuestsOfEvent(EventsGuestSearchDto searchDto) {
		List<EventsGuestDetailDto> datas = new ArrayList<>();
		searchDto.setFunctionCode("M_EVENT_GUEST");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getParamSearch(stringJsonParam,
				"M_EVENT_GUEST");
		searchDto.setSort(common.getSort());
		String condition = common.getSearch().trim();
		int statusSIdx = condition.lastIndexOf("or UPPER(ISNULL(A.STATUS,'')) like UPPER");
		int statusEIdx = condition.lastIndexOf("))");
		if (statusSIdx > 0 && statusEIdx > statusSIdx) {
			condition = condition.replace(condition.substring(statusSIdx, statusEIdx + 1), "");
		}
		condition = condition.replace("UPPER(A.STATUS)  = UPPER('1')", "A.ATTENDANCE_TIME is not null");
		condition = condition.replace("UPPER(A.STATUS)  = UPPER('0')", "A.ATTENDANCE_TIME is null");
		searchDto.setSearch(condition);
		
		int count = eventsApplicableDetailRepository.countEventsGuest(searchDto);
		Integer offset = searchDto.getPage() == null ? null : Utility.calculateOffsetSQL(searchDto.getPage() + 1, searchDto.getPageSize());
		searchDto.setOffset(offset);
		if(count > 0) datas = eventsApplicableDetailRepository.getListEventsGuest(searchDto);
		CmsCommonPagination<EventsGuestDetailDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(count);
		return resultData;
	}
	
	@Override
	public EventsGuestDetailDto addGuest(EventsGuestDetailDto dto) {
		EventsApplicableDetail entity = new EventsApplicableDetail();
		entity.setGuestType("2");
		entity.setEventId(dto.getEventId());
		if (dto.getAgentCode() != null && !dto.getAgentCode().isEmpty()) {
			entity.setAgentCode(Long.valueOf(dto.getAgentCode()));
		}
		entity.setIdNumber(dto.getIdNumber());
		entity.setName(dto.getName());
		entity.setGender(dto.getGender());
		entity.setEmail(dto.getEmail());
		entity.setTel(dto.getTel());
		entity.setAddress(dto.getAddress());
		if (dto.getReferCode() != null && !dto.getReferCode().isEmpty()) {
			entity.setReferCode(Long.valueOf(dto.getReferCode()));
		}
		entity.setAttendanceTime(new Date());
		if (ObjectUtils.isNotEmpty(dto.getAgentCode())) {
			// TVTC, lấy thông tin cá nhân từ Agent code
			List<String> agentCodes = new ArrayList<>();
			agentCodes.add(dto.getAgentCode());
			List<Db2AgentDto> agentInfos = db2ApiService.getAgentInfoByCode(agentCodes);
			if (agentInfos.size() > 0) {
				Db2AgentDto agentInfo = agentInfos.get(0);
				entity.setGuestType("1");
				entity.setAgentCode(Long.valueOf(agentInfo.getAgentCode()));
				entity.setName(agentInfo.getAgentName());
				entity.setIdNumber(agentInfo.getIdNumber());
				entity.setTerritorry(agentInfo.getTerritorry());
				entity.setArea(agentInfo.getArea());
				entity.setRegion(agentInfo.getRegion());
				entity.setOffice(agentInfo.getOffice());
				entity.setPosition(agentInfo.getPosition());
				entity.setGender(agentInfo.getGender());
				entity.setEmail(agentInfo.getEmailAddress1());
				entity.setTel(agentInfo.getMobilePhone());
				entity.setAddress(agentInfo.getFullAddress());
			}
		}
		eventsApplicableDetailRepository.save(entity);
		return dto;
	}
	
	@Override
	public int updateGuest(EventsGuestDetailDto dto) {
		return eventsApplicableDetailRepository.updateGuest(dto);
	}
	
	@Override
	public boolean checkProcessing(Long id) {
		return (eventsRepository.getEventProcessing(id) > 0);
	}
	
	@Override
	public ResponseEntity exportListEvents(EventsSearchDto searchDto,
			HttpServletResponse response, Locale locale) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(null);
			searchDto.setPageSize(null);
			CmsCommonPagination<EventsDto> events = getListEventsByCondition(searchDto);
			
			String createBy = "";
			String office = "";
			UserPrincipal userLogin = UserProfileUtils.getUserPrincipal();
			if ("1".equals(userLogin.getAccountType())) {
				createBy = userLogin.getUsername() + " - " + userLogin.getFullname();
				office = "BACKOFFICE";
			} else {
				List<String> agentCodes = new ArrayList<>();
				agentCodes.add(searchDto.getCreateBy());
				List<Db2AgentDto> agentInfos = db2ApiService.getAgentInfoByCode(agentCodes);
				if (agentInfos.size() > 0) {
					createBy = agentInfos.get(0).getAgentType() + ": " + agentInfos.get(0).getAgentCode()
							+ " - " + agentInfos.get(0).getAgentName();
					office = agentInfos.get(0).getSalesOfficeCode() + " - " + agentInfos.get(0).getSalesOfficeName();
				}
			}
			String datePattern = "dd/MM/yyyy HH:mm";
			String templateName = "Danh sach su kien.xlsx";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(EnumExportReportListEvents.class, cols);
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A9";

			List<EventsDto> lstdata = events.getData();
			// start fill data to workbook
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat =  new HashMap<>();// setMapColFormat();
			mapColFormat.put("ATTENDANCERATIO", CommonConstant.PERCENT);
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
				setDataHeaderForListEvents(xssfWorkbook, 0, lstdata.size(), createBy, office, mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						EventsDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

	@Override
	public ResponseEntity exportListGuestsOfEvent(EventsGuestSearchDto searchDto,
			HttpServletResponse response, Locale locale) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(null);
			searchDto.setPageSize(null);
			CmsCommonPagination<EventsGuestDetailDto> guests = apiCustomerInformationService.getListGuestsOfEvent(searchDto);
			
			String datePattern = "dd/MM/yyyy HH:mm";
			String templateName = "Danh sach tham du.xlsx";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(EnumExportReportListGuests.class, cols);
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A12";
			
			EventsDto event = getDetailEvent(searchDto.getEventId(), null);
			List<EventsGuestDetailDto> lstdata = guests.getData();
			// start fill data to workbook
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
				setDataHeaderForListGuests(xssfWorkbook, 0, guests.getTotalData(), event, mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						EventsGuestDetailDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}
	
	private Long pushNotifyDelete(Long eventId) {
		Notify entity = new Notify();
		
		entity.setNotifyCode(getNotifyCode());
		Events event = eventsRepository.findOne(eventId);
		String contents = String.format(NOTIFY_DELETE, event.getEventTitle());
		entity.setNotifyTitle(NOTIFY_TITLE);
		entity.setNotifyType(2);
		entity.setContents(contents);
		entity.setLinkNotify(null);
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
		notifyRepository.insertDetailFromEvent(entity.getId(), eventId);
		
		List<String> lstAgent = notifyRepository.getLsAgentFromEventId(eventId);
		try {
			this.pushNotificationToFirebaseCloud(entity, lstAgent);
		} catch (Exception e) {
			logger.error("##Error##" + e);
		}
		
		return entity.getId();
	}
	
	private Long pushNotifyAddNew(Events event, List<String> agentCodes) {
		Notify entity = new Notify();
		
		entity.setNotifyCode(getNotifyCode());
		String contents = String.format(NOTIFY_ADD_NEW, event.getEventTitle()
				, CommonDateUtil.formatDateToString(event.getEventDate(), "HH:mm")
				, CommonDateUtil.formatDateToString(event.getEventDate(), "dd/MM/yyyy"));
		entity.setNotifyTitle(NOTIFY_TITLE);
		entity.setNotifyType(2);
		entity.setContents(contents);
		entity.setLinkNotify(LINK_DETAIL + event.getEventCode() + "/" + event.getId().toString());
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
		List<String> lstAgent = null;
		if (agentCodes == null) {
			notifyRepository.insertDetailFromEvent(entity.getId(), event.getId());
			lstAgent = notifyRepository.getLsAgentFromEventId(event.getId());
		} else if (!agentCodes.isEmpty()) {
			notifyRepository.insertDetailFromEventByAgent(entity.getId(), event.getId(), agentCodes);
			lstAgent = agentCodes;
		}
		
		try {
			this.pushNotificationToFirebaseCloud(entity, lstAgent);
		} catch (Exception e) {
			logger.error("##Error##" + e);
		}
		
		return entity.getId();
	}
	
	private Long pushNotifyUpdate(EventsDto event, List<String> agentCodes) {
		Notify entity = new Notify();
		
		entity.setNotifyCode(getNotifyCode());
		String contents = String.format(NOTIFY_UPDATE, event.getEventTitle()
				, CommonDateUtil.formatDateToString(event.getEventDate(), "HH:mm")
				, CommonDateUtil.formatDateToString(event.getEventDate(), "dd/MM/yyyy"));
		entity.setNotifyTitle(NOTIFY_TITLE);
		entity.setNotifyType(2);
		entity.setContents(contents);
		entity.setLinkNotify(LINK_DETAIL + event.getEventCode() + "/" + event.getId().toString());
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
		List<String> lstAgent = null;
		if (agentCodes == null) {
			notifyRepository.insertDetailFromEvent(entity.getId(), event.getId());
			lstAgent = notifyRepository.getLsAgentFromEventId(event.getId());
		} else if (!agentCodes.isEmpty()) {
			notifyRepository.insertDetailFromEventByAgent(entity.getId(), event.getId(), agentCodes);
			lstAgent = agentCodes;
		}
		
		try {
			this.pushNotificationToFirebaseCloud(entity, lstAgent);
		} catch (Exception e) {
			logger.error("##Error##" + e);
		}
		
		return entity.getId();
	}
	
	private Long pushNotifyUpdateDeleteGuest(EventsDto event, List<String> agentCodes) {
		Notify entity = new Notify();

		entity.setNotifyCode(getNotifyCode());
		String contents = String.format(NOTIFY_UPDATE_DELETE_GUEST, event.getEventTitle()
				, CommonDateUtil.formatDateToString(event.getEventDate(), "HH:mm")
				, CommonDateUtil.formatDateToString(event.getEventDate(), "dd/MM/yyyy"));
		entity.setNotifyTitle(NOTIFY_TITLE);
		entity.setNotifyType(2);
		entity.setContents(contents);
		entity.setLinkNotify(null);
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
		notifyRepository.insertDetailFromEventByAgent(entity.getId(), event.getId(), agentCodes);
		try {
			this.pushNotificationToFirebaseCloud(entity, agentCodes);
		} catch (Exception e) {
			logger.error("##Error##" + e);
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
	
	/**
	 * Generate QRcode for event
	 * @param event
	 * @return path of QRCodeImage
	 */
	private void generateQRCode(Events event) throws WriterException, IOException {
		SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDate = formatDateExport.format(new Date());
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
        String path = systemConfig.getPhysicalPathById(repo, null);
        path = Paths.get(path, "QR-Code").toString();
        File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String pathFile = Paths.get(path, event.getEventCode() + "_" + currentDate + ".png").toString();
		UUID GUI = UUID.randomUUID();
		String data = event.getEventCode() + "@" + GUI.toString() + "@" + currentDate;
		//String dataEncode = CommonBase64Util.encode(data);
		QRCodeUtil.generateQRCodeImage(data, 280, 280, pathFile);
		event.setQrCode(pathFile);
		event.setQrId(data);
	}
	
	private void setDataHeaderForListEvents(XSSFWorkbook xssfWorkbook, int sheet, Integer total, String createBy
			, String office, Map<String, CellStyle> mapColStyle) {
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
			xssfSheet.getRow(3).getCell(0).setCellValue("Người tạo: " + createBy);
		else xssfSheet.createRow(3).createCell(0).setCellValue("Người tạo: " + createBy);
		
		if(xssfSheet.getRow(4) != null)
			xssfSheet.getRow(4).getCell(0).setCellValue("Văn phòng/GA: " + office);
		else xssfSheet.createRow(4).createCell(0).setCellValue("Văn phòng/GA: " + office);

		DecimalFormat df = new DecimalFormat("###,###,###");
		if(xssfSheet.getRow(5) != null)
			xssfSheet.getRow(5).getCell(0).setCellValue("Số lượng sự kiện: " + df.format(total));
		else xssfSheet.createRow(5).createCell(0).setCellValue("Số lượng sự kiện: " + df.format(total));

		xssfSheet.getRow(5).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(4).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(3).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(2).getCell(0).setCellStyle(titleStyleDate);
	}
	
	private void setDataHeaderForListGuests(XSSFWorkbook xssfWorkbook, int sheet, Integer total, EventsDto event, Map<String, CellStyle> mapColStyle) {
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
			xssfSheet.getRow(2).getCell(0).setCellValue("Mã sự kiện: " + event.getEventCode());
		else xssfSheet.createRow(2).createCell(0).setCellValue("Mã sự kiện: " + event.getEventCode());
		
		if(xssfSheet.getRow(3) != null)
			xssfSheet.getRow(3).getCell(0).setCellValue("Tên sự kiện: " + event.getEventTitle());
		else xssfSheet.createRow(3).createCell(0).setCellValue("Tên sự kiện: " + event.getEventTitle());
		
		String createBy = "";
		List<String> agentCodes = new ArrayList<>();
		agentCodes.add(event.getCreateBy());
		List<Db2AgentDto> agentInfos = db2ApiService.getAgentInfoByCode(agentCodes);
		if (agentInfos.size() > 0) {
			createBy = agentInfos.get(0).getAgentType() + ": " + agentInfos.get(0).getAgentCode()
					+ " - " + agentInfos.get(0).getAgentName();
		}
		if(xssfSheet.getRow(4) != null)
			xssfSheet.getRow(4).getCell(0).setCellValue("Người tổ chức: " + createBy);
		else xssfSheet.createRow(4).createCell(0).setCellValue("Người tổ chức: " + createBy);

		String strTime = CommonDateUtil.formatDateToString(event.getEventDate(), "dd/MM/yyyy HH:mm") +
				" - " + CommonDateUtil.formatDateToString(event.getEndDate(), "dd/MM/yyyy HH:mm");
		if(xssfSheet.getRow(5) != null)
			xssfSheet.getRow(5).getCell(0).setCellValue("Thời gian: " + strTime);
		else xssfSheet.createRow(5).createCell(0).setCellValue("Thời gian: " + strTime);
		
		if(xssfSheet.getRow(6) != null)
			xssfSheet.getRow(6).getCell(0).setCellValue("Địa điểm: " + event.getEventLocation());
		else xssfSheet.createRow(6).createCell(0).setCellValue("Địa điểm: " + event.getEventLocation());

		DecimalFormat df = new DecimalFormat("###,###,###");
		String strAttendance = df.format(event.getAttendanceQuantity()) + "/" + df.format(event.getQuantity()); 
		if(xssfSheet.getRow(7) != null)
			xssfSheet.getRow(7).getCell(0).setCellValue("Số lượng người tham dự: " + strAttendance);
		else xssfSheet.createRow(7).createCell(0).setCellValue("Số lượng người tham dự: " + strAttendance);
		
		if(xssfSheet.getRow(8) != null)
			xssfSheet.getRow(8).getCell(0).setCellValue("Tỷ lệ tham dự: " + event.getAttendanceRatio().toString() + "%");
		else xssfSheet.createRow(8).createCell(0).setCellValue("Tỷ lệ tham dự: " + event.getAttendanceRatio().toString() + "%");
		
		xssfSheet.getRow(8).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(7).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(6).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(5).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(4).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(3).getCell(0).setCellStyle(titleStyleDate);
		xssfSheet.getRow(2).getCell(0).setCellStyle(titleStyleDate);
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

     public MulticastMessage allPlatformsMessage(List<String> deviceToken,  Notify dto) {
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

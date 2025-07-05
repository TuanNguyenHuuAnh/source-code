package vn.com.unit.cms.admin.all.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.db2.service.Db2Service;
import vn.com.unit.cms.admin.all.enumdef.exports.NotifyExportEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.service.NotifyImportService;
import vn.com.unit.cms.admin.all.service.NotifyService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.module.notify.dto.NotifyEditDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyImportDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchResultDto;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.cms.core.module.notify.repository.NotifyRepository;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentNotifyParamDto;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotifyServiceImpl implements NotifyService {

	@Autowired
	public NotifyRepository notifyRepository;

	@Autowired
	private Environment env;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	private NotifyImportService notifyImportService;

	@Autowired
	private CommonSearchFilterUtils commonSearchFilterUtils;

	@Qualifier("messageSource")
	@Autowired
    private MessageSource msg;
    @Autowired
    private Db2Service db2Service;
    @Autowired
    private CmsCommonService comService;
    
    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    @Autowired
	@Qualifier("connectionProvider")
    private ConnectionProvider connectionProvider;
	@Autowired
    private JcaAccountService jcaAccountService;

    @Autowired
    private ServletContext servletContext;
	private static final Logger logger = LoggerFactory.getLogger(NotifyServiceImpl.class);
	@Override
	public List<NotifySearchResultDto> getListByCondition(NotifySearchDto searchDto, Pageable pageable) {
		return notifyRepository.findAllNotify(searchDto, pageable).getContent();
	}

	@Override
	public int countListByCondition(NotifySearchDto searchDto) {
		return notifyRepository.countByNotifySearchDto(searchDto);
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
	public CmsCommonService getCmsCommonService() {
		return comService;
	}

	@Override
	public JcaDatatableConfigService getJcaDatatableConfigService() {
		return jcaDatatableConfigService;
	}

	@Override
	public CommonSearchFilterUtils getCommonSearchFilterUtils() {
		return commonSearchFilterUtils;
	}
	
	@Autowired
    SystemLogsService systemLogsService;

	@Override
	public NotifyEditDto getEditDtoById(Long id, Locale locale) {
		NotifyEditDto dto = new NotifyEditDto();
		if(id == null){
			return dto;
		}
		Notify entity = notifyRepository.findOne(id);

		if(entity != null){
			dto.setId(entity.getId());
			dto.setNotifyCode(entity.getNotifyCode());
			dto.setNotifyTitle(entity.getNotifyTitle());
			dto.setContents(entity.getContents());
			dto.setLinkNotify(entity.getLinkNotify());
			dto.setSendImmediately(entity.isSendImmediately());
			dto.setActive(entity.isActive());
			dto.setApplicableObject(entity.getApplicableObject());
			dto.setArea(entity.getArea());
			dto.setTerritory(entity.getTerritorry());
			dto.setRegion(entity.getRegion());
			dto.setOffice(entity.getOffice());
			dto.setType(entity.getPosition());
			dto.setFc(entity.isFc());
			dto.setSendDate(entity.getSendDate());
		} else {
			throw  new BusinessException("id not found");
		}

		return dto;
	}

	@Override
	public void saveOrUpdate(NotifyEditDto editDto, Locale locale) throws Exception {

		Notify entity = new Notify();
		Long id = editDto.getId();
		String user = UserProfileUtils.getUserNameLogin();
		Date currentDate = new Date();
		List<String> lstAgent = new ArrayList<>();
		List<String> lstContent = new ArrayList<>();
		List<String> lstTitle = new ArrayList<>();
		
		
		if(id != null){
			logger.error("Debug Start update");
			entity = notifyRepository.findOne(id);
			if(entity == null) throw new BusinessException("entity not found");
			updateData(entity, editDto);
			entity.setUpdateBy(user);
			entity.setUpdateDate(currentDate);
			entity.setNotifyType(2);
			notifyRepository.save(entity);
			notifyRepository.deleteAgentNotifyDetailByNotifyId(id);
			logger.error("Debug End update");
		} else {
			if (!StringUtils.equalsIgnoreCase(editDto.getApplicableObject(), "IP")) {
				logger.error("Debug Start update 1");
				updateData(entity, editDto);
				// code generation
				SimpleDateFormat format = new SimpleDateFormat("yy");
				SimpleDateFormat formatMM = new SimpleDateFormat("MM");
							
				
				entity.setNotifyCode(CommonUtil.getNextBannerCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT,
						notifyRepository.getMaxNotifyCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT + format.format(new Date()) + formatMM.format(new Date()))));
				entity.setCreateBy(user);
				entity.setCreateDate(currentDate);
				entity.setNotifyType(2);
				//delete old detail by notify id
				notifyRepository.save(entity);
				editDto.setNotifyCode(entity.getNotifyCode());
	
				logger.error("Debug End update1");
			}
		}
		Long notifyId = entity.getId();
		//save detail agent notify
		String sessionKey = editDto.getSessionKey();
		List<Db2AgentDto> dataInsert = new ArrayList<>();
		if(StringUtils.equalsIgnoreCase(editDto.getApplicableObject(), "ALL")) {
			if(editDto.isSendImmediately() == true) {
				entity.setSaveDetail(false);
				dataInsert = db2Service.getAllAgentCode();
			}else{
				entity.setSaveDetail(true);
			}
		}
		if(StringUtils.equalsIgnoreCase(editDto.getApplicableObject(), "SEL")) {
			entity.setSaveDetail(false);

			logger.error("Debug Start update SEL");
			Db2AgentNotifyParamDto db2AgentNotifyParamDto = prepareDateCallStoreDb2(entity);
			db2Service.callStoreDb2("RPT_ODS.DS_SP_GET_AGENT_NOTIFY_DETAIL", db2AgentNotifyParamDto);
			dataInsert = db2AgentNotifyParamDto.lstAgent;

			logger.error("Debug End update SEL");
		}
		/*   ---SR14061---
		 *   @author lmi.quan
		 *   @createdDate 25/07/2024
		 *   Fix import multiple files (save and send) */
		
		
		if (StringUtils.equalsIgnoreCase(editDto.getApplicableObject(), "IMP")) {
			entity.setSaveDetail(false);
			logger.error("Debug Start update IMP");
			//call store db2 get list agent info
			Db2AgentNotifyParamDto db2AgentNotifyParamDto = new Db2AgentNotifyParamDto();
			db2Service.callStoreDb2("RPT_ODS.DS_SP_GET_AGENT_NOTIFY_DETAIL", db2AgentNotifyParamDto);
			List<Db2AgentDto> lstData =  db2AgentNotifyParamDto.lstAgent;
			List<NotifyImportDto> lstDataImport = notifyImportService.getAllDatas(sessionKey);    // get AgentCode in file import
			List<String> lstAgentCodeImport = lstDataImport.stream().map(NotifyImportDto::getAgentCode).map(Object::toString).collect(Collectors.toList());
			dataInsert = lstData.parallelStream().filter(e -> lstAgentCodeImport.contains(e.getAgentCode())).collect(Collectors.toList());
			logger.error("Debug End update IMP");
		}
		
		/*   ---SR14061--- */
		if (StringUtils.equalsIgnoreCase(editDto.getApplicableObject(), "IP")) {
			entity.setSaveDetail(false);
			logger.error("Debug Start update IP");
			//call store db2 get list agent info
			Db2AgentNotifyParamDto db2AgentNotifyParamDto = new Db2AgentNotifyParamDto();
			db2Service.callStoreDb2("RPT_ODS.DS_SP_GET_AGENT_NOTIFY_DETAIL", db2AgentNotifyParamDto);
			List<Db2AgentDto> lstData =  db2AgentNotifyParamDto.lstAgent;
			List<NotifyImportDto> lstDataImport = notifyImportService.getAllDatas(sessionKey);    // get AgentCode in file import			
			
			Map<String, List<NotifyImportDto>> groupedMap = new LinkedHashMap<>();
	        for (NotifyImportDto dto : lstDataImport) {
	            String key = (dto.getTitle().trim() + dto.getContent().trim());
	            groupedMap.putIfAbsent(key, new ArrayList<>());
	            groupedMap.get(key).add(dto);
	        }

	
	        Map<String, List<String>> idAgentCodesMap = new LinkedHashMap<>();
	        for (Map.Entry<String, List<NotifyImportDto>> entry : groupedMap.entrySet()) {
	            StringBuilder keyBuilder = new StringBuilder();
	            List<String> agentCodes = new ArrayList<>();
	            for (NotifyImportDto dto : entry.getValue()) {
	                if (keyBuilder.length() > 0) {
	                    keyBuilder.append("#");
	                }
	                keyBuilder.append(dto.getId());
	                agentCodes.add(dto.getAgentCode());
	            }
	            idAgentCodesMap.put(keyBuilder.toString(), agentCodes);
	        }

      
	        for (Map.Entry<String, List<String>> entry : idAgentCodesMap.entrySet()) {	        	
	        	String ids = entry.getKey();
	        	List<String> lstAgentCodeImport = entry.getValue();
	        	String arrayId[] = ids.split("#");
	        	if(arrayId.length > 0) {
	        		  NotifyImportDto notifyImp = lstDataImport.stream()
                              .filter(dto -> dto.getId().compareTo(Long.valueOf(arrayId[0])) == 0)
                              .findFirst()
                              .orElse(null);
	        		dataInsert = lstData.parallelStream().filter(e -> lstAgentCodeImport.contains(e.getAgentCode())).collect(Collectors.toList());
	            	// Insert to M_NOTIFYS
	  	            Notify newEntity = new Notify();
	  	            NotifyEditDto newEdit = new  NotifyEditDto();
	  	            newEdit.setContents(notifyImp.getContent());
	  	            newEdit.setSessionKey(editDto.getSessionKey());
	  	            newEdit.setApplicableObject("IMP");
	  	            newEdit.setSendDate(editDto.getSendDate());
	  	            newEdit.setNotifyTitle(notifyImp.getTitle());
	  	            newEdit.setLinkNotify(editDto.getLinkNotify());
	  	            newEdit.setSendImmediately(editDto.isSendImmediately());
	  	            newEdit.setActive(editDto.isActive());
	  	            updateData(newEntity, newEdit);
	  	    		SimpleDateFormat format = new SimpleDateFormat("yy");
	  				SimpleDateFormat formatMM = new SimpleDateFormat("MM");
	  							
	  				
	  				newEntity.setNotifyCode(CommonUtil.getNextBannerCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT,
	  						notifyRepository.getMaxNotifyCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT + format.format(new Date()) + formatMM.format(new Date()))));
	  				newEntity.setCreateBy(user);
	  				newEntity.setCreateDate(currentDate);
	  				newEntity.setNotifyType(2);
	  			
	  	            notifyRepository.save(newEntity);
	  	        	if (editDto.isSendImmediately()) {
	  					notifyRepository.updateIsSend(newEntity.getId());
	  				}
	  	          
	  	            this.insertToDatabaseWithBatch(dataInsert, newEntity.getId(), newEntity.getSendDate());
	  	            
	  	            // push notification to firebase      /// Gửi ngay lập tức
					if (editDto.isSendImmediately()) {
						logger.error("Debug Start push notif");
						NotifyEditDto notiDto = new NotifyEditDto();
						notiDto.setContents(notifyImp.getContent());
						notiDto.setNotifyTitle(notifyImp.getTitle());
						pushNotificationToFirebaseCloud(dataInsert ,notiDto);
						logger.error("Debug End push notif");
					}
	        	}
	        	
	        }
	        
			logger.error("Debug End update IP");
		} else {			
			notifyRepository.save(entity);
			this.insertToDatabaseWithBatch(dataInsert, notifyId, entity.getSendDate());		
			// push notification to firebase      /// Gửi ngay lập tức
			if (editDto.isSendImmediately()) {
				logger.error("Debug Start push notif");
				pushNotificationToFirebaseCloud(dataInsert, editDto);
				notifyRepository.updateIsSend(notifyId);
				logger.error("Debug End push notif");
			}
		}
		
		editDto.setId(entity.getId());
	}
	
	private void pushNotificationToFirebaseCloud(List<Db2AgentDto> LstData, NotifyEditDto dto) throws Exception {
		
		List<String> lstToken = new ArrayList<>();

		try {

			String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);
			GoogleCredentials googleCredentials = GoogleCredentials
					.fromStream(getClass().getClassLoader().getResourceAsStream(fileName));

			@SuppressWarnings("deprecation")
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
			// check agent login before?
			List<JcaAccount> lstAccount = jcaAccountService.getListByUserName(null);

			Map<String, JcaAccount> accountMap = new HashMap<>();
			for (JcaAccount account : lstAccount) {
				accountMap.put(account.getUsername(), account);
			}

			for (Db2AgentDto agentDto : LstData) {

				JcaAccount account = accountMap.get(agentDto.getAgentCode());
				if (account != null) {
					String token = account.getDeviceTokenMobile();
					if (StringUtils.isNotBlank(token)) {
						lstToken.add(token);
					}
				}
			}

		} catch (Exception e) {
			logger.error("pushNotificationToFirebaseCloud", e);

			systemLogsService.writeSystemLogs("pushNotificationToFirebaseCloud", "pushNotificationToFirebaseCloud",
					e.getMessage(), null);

			throw new Exception("Push notification to firebase failed!");
		}

		if (!lstToken.isEmpty()) {

			String firebaseExc = StringUtils.EMPTY;

			// chia nhỏ payload trước khi call FCM
			// FCM maximum payload: 4000 bytes
			int maxParm = 400;
			int total = lstToken.size();
			int fromIndex = 0;
			int toIndex = total > (fromIndex + maxParm) ? (fromIndex + maxParm) : total;
			
			while (fromIndex < total) {
				try {
					List<String> lstTokenSend = lstToken.subList(fromIndex, toIndex);
					MulticastMessage multicastMessage = allPlatformsMessage(lstTokenSend, dto);
					BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage);

					if (response.getFailureCount() > 0) {
						List<SendResponse> responses = response.getResponses();
						if (responses != null) {
							for (int i = 0; i < responses.size(); i++) {
								if (!responses.get(i).isSuccessful()) {
									if (responses.get(i).getException() != null) {
										firebaseExc = responses.get(i).getException().getMessage();
										if (!StringUtils.isBlank(firebaseExc)) {
											systemLogsService.writeSystemLogs("pushNotificationToFirebaseCloud",
													"pushNotificationToFirebaseCloud", firebaseExc, null);
											logger.error("pushNotificationToFirebaseCloud", firebaseExc);
										}
									}

								}
							}
						}
					}
					fromIndex = toIndex;
					toIndex = total > (fromIndex + maxParm) ? (fromIndex + maxParm) : total;
				} catch (Exception e) {
					logger.error("pushNotificationToFirebaseCloud-send", e);

					systemLogsService.writeSystemLogs("pushNotificationToFirebaseCloud-send", "pushNotificationToFirebaseCloud",
							e.getMessage(), null);
				}
				
			}

		} else {
			logger.error("List token is empty!");
		}
	}
	public MulticastMessage allPlatformsMessage(List<String> deviceToken,  NotifyEditDto dto) {
		final String ACTION = "FLUTTER_NOTIFICATION_CLICK";
		final String NOTIFICATION = "notificationPage";
		MulticastMessage multicastMessage = MulticastMessage.builder()
				.setNotification(Notification.builder()
						.setTitle(dto.getNotifyTitle())
						.setBody(StringEscapeUtils.unescapeHtml(dto.getContents().replaceAll("<[^>]*>", "")))
						.build())
				.putData("click_action", ACTION)
				.putData("route", NOTIFICATION)
				.addAllTokens(deviceToken)
				.build();

//		Message message = Message.builder()
//				.setNotification(Notification.builder()
//						.setTitle(dto.getTitle())
//						.setBody(dto.getContents())
//						.build())
//				.putData("click_action", ACTION)
//				.putData("route", NOTIFICATION)
//				.setToken(deviceToken)
//				.build();
		return multicastMessage;
	}
	public void insertToDatabaseWithBatch(List<Db2AgentDto> lstData, Long notifyId,Date sendDate) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		connection.setAutoCommit(false);
		String query = "INSERT INTO dbo.M_NOTIFYS_APPLICABLE_DETAIL (NOTIFY_ID, TERRITORRY, AREA, REGION, OFFICE, POSITION, AGENT_CODE, IS_READ_ALREADY, AGENT_NAME,CREATE_DATE) VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst = connection.prepareStatement(query);
		int startRow = 0;
		int totoalRow = 0;

		String value = jcaSystemConfigService.getValueByKey("BATCH_SIZE", 1L);
		int batchSize = Integer.parseInt(value);
	    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Db2AgentDto data : lstData) {
			pst.setLong(1, notifyId);
			pst.setString(2, data.getTerritorry());
			pst.setString(3, data.getArea());
			pst.setString(4, data.getRegion());
			pst.setString(5, data.getOffice());
			pst.setString(6, data.getPosition());
			pst.setString(7, data.getAgentCode());
			pst.setBoolean(8, false);			
			pst.setString(9, data.getAgentName());				
	        pst.setString(10, formatDate.format( sendDate));

			
			pst.addBatch();
			startRow ++;
			totoalRow++;
			if (startRow % batchSize == 0) {
				pst.executeBatch();
				pst.clearBatch();
				startRow = 0;
			}
		}
		pst.executeBatch();
		pst.clearBatch();
		logger.error("PST = "+totoalRow);
		connection.commit();

	}

	private Db2AgentNotifyParamDto prepareDateCallStoreDb2(Notify notify){

		Db2AgentNotifyParamDto db2AgentNotifyParamDto = new Db2AgentNotifyParamDto();
		db2AgentNotifyParamDto.territory = StringUtils.isBlank(notify.getTerritorry()) ? null : (";" + notify.getTerritorry().replace(",", ";") + ";");
		db2AgentNotifyParamDto.region = StringUtils.isBlank(notify.getArea()) ? null : (";" + notify.getArea().replace(",", ";") + ";");
		db2AgentNotifyParamDto.office = StringUtils.isBlank(notify.getOffice()) ? null : (";" + notify.getOffice().replace(",", ";") + ";");
		db2AgentNotifyParamDto.area = StringUtils.isBlank(notify.getRegion()) ? null : (";" + notify.getRegion().replace(",", ";") + ";");
		db2AgentNotifyParamDto.position = StringUtils.isBlank(notify.getPosition()) ? null : (";" + notify.getPosition().replace(",", ";") + ";");
		return db2AgentNotifyParamDto;
	}

	private void updateData(Notify entity, NotifyEditDto dto){
		entity.setNotifyTitle(dto.getNotifyTitle());
		entity.setContents(dto.getContents());
		entity.setLinkNotify(dto.getLinkNotify());
		entity.setSendImmediately(dto.isSendImmediately());
		entity.setActive(dto.isActive());
		entity.setApplicableObject(dto.getApplicableObject());
		if(dto.getApplicableObject() != null && StringUtils.equalsIgnoreCase(dto.getApplicableObject() , "SEL")) {
			entity.setTerritorry(dto.getTerritory());
			entity.setRegion(dto.getRegion());
			entity.setOffice(dto.getOffice());
			entity.setArea(dto.getArea());
			entity.setPosition(dto.getType());
		}
//		entity.setFc(dto.isFc());
		entity.setSendDate(dto.getSendDate());
	}
	@Override
	public void deleteDataById(Long id) throws Exception {
		Notify entity = notifyRepository.findOne(id);
        if (entity != null) {
            entity.setDeleteBy(UserProfileUtils.getUserNameLogin());
            entity.setDeleteDate(new Date());

            notifyRepository.save(entity);
        }
	}

	@Override
	public List<NotifySearchResultDto> getListForSort(NotifySearchDto searchDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSortAll(NotifySearchDto searchDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CommonSearchFilterDto> initListSearchFilter(NotifySearchDto searchDto, Locale locale) {
		List<CommonSearchFilterDto> list = NotifyService.super.initListSearchFilter(searchDto, locale);
		return list;
	}
	
	@Override
	public Class getEnumColumnForExport() {
		return NotifyExportEnum.class;
	}

	@Override
	public String getTemplateNameForExport(Locale locale) {
		return "Notify";
	}
	
	
    @SuppressWarnings({ "unchecked"})
    public void exportExcel(NotifySearchDto searchDto, Pageable pageable, Class<NotifySearchResultDto> classSearchResult,
            HttpServletResponse response, Locale locale) {
        try {
            String templateName = getTemplateNameForExport(locale);
            String template = getServletContext().getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/"
                    + templateName + CmsCommonConstant.TYPE_EXCEL;

            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            
            setSearchParm(searchDto);
            
            List<NotifySearchResultDto> lstdata = notifyRepository.getAgentByNotifyId(searchDto);
            
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(getEnumColumnForExport(), cols);
            ExportExcelUtil<NotifySearchResultDto> exportExcel = new ExportExcelUtil<>();
            // do export
            exportExcel.exportExcelWithXSSFNonPass(template, locale, lstdata, classSearchResult, cols, datePattern,
                    response, templateName);
        } catch (Exception e) {

        }
    }
}

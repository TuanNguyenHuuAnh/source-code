package vn.com.unit.ep2p.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.module.notify.dto.NotifyAddDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyInputDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyReadDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchLike;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.cms.core.module.notify.entity.NotifysApplicableDetail;
import vn.com.unit.cms.core.module.notify.repository.NotifyCoreRepository;
import vn.com.unit.cms.core.module.notify.repository.NotifyRepository;
import vn.com.unit.cms.core.module.notify.repository.NotifysApplicableDetailRepository;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.ers.dto.AuthenticationExceptionMockup;
import vn.com.unit.ep2p.service.ApiNotifyService;
import vn.com.unit.ep2p.utils.LangugeUtil;
import vn.com.unit.ep2p.utils.RestUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiNotifyServiceImpl implements ApiNotifyService {
	private static final Logger logger = LoggerFactory.getLogger(ApiNotifyServiceImpl.class);

	@Autowired
	private NotifyCoreRepository notifyCoreRepository;

	@Autowired
	private NotifysApplicableDetailRepository notifysApplicableDetailRepository;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;

	@Autowired
	MessageSource messageSource;

	@Autowired
	private Environment env;

	@Autowired
	public NotifyRepository notifyRepository;

	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private ServletContext servletContext;


	@Override
	public NotifyReadDto saveNotifyCheckRead(Long id, Long agentCode, Integer isReadAlready) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		if (agentCode == null) {
			String messageError = checkErrorMessage("agent.code.is.null", null, locale);
			throw new AuthenticationExceptionMockup(messageError);
		}
		NotifysApplicableDetail entity = notifysApplicableDetailRepository.findOne(id);

		if (entity != null) {
			if (isReadAlready == 1) {
				entity.setReadAlready(true);
			} else {
				entity.setReadAlready(false);
			}
			notifysApplicableDetailRepository.save(entity);
		}

		NotifyReadDto dto = notifyCoreRepository.getNotifyByRead(agentCode);
		return dto;
	}
    public int countNotifyByType(Long agentCode, Integer notifyType, Integer modeView,Integer isReadAlready,String searchValues,Integer isLike) {
		if(agentCode == null) {
			return 0;
		}
        return notifyCoreRepository.countNotifyByType(agentCode, notifyType,modeView,isReadAlready,searchValues,isLike);
    }

    @Override
    public List<NotifyDto> getListNotifyByType(Integer page, Integer size, Long agentCode, Integer notifyType,Integer modeView,Integer isReadAlready,String searchValues,Integer isLike) {
        try {
            int offsetSQL = Utility.calculateOffsetSQL(page, size);
            List<NotifyDto> data = notifyCoreRepository.getListNotifyByType(offsetSQL, size, agentCode, notifyType, modeView,isReadAlready,searchValues,isLike);
            for(NotifyDto item : data) {
				try {
					item.setContents(convertImageContentString(item.getContents()));
				} catch (Exception e) {
					logger.error("Exception ", e);
				}
            
            }
            return data;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
        
    }
    
	private String convertImageContentString(String content) {
		try {

			String pathAdmin = systemConfig.getConfig(SystemConfig.PATH_DOMAIN_ADMIN);
			String baseAdmin = systemConfig.getConfig(SystemConfig.BASE_DOMAIN_ADMIN);

			String pathApi = systemConfig.getConfig(SystemConfig.PATH_DOMAIN_API);
			String baseApi = systemConfig.getConfig(SystemConfig.BASE_DOMAIN_API);
			String contents = RestUtil.replaceUrlImg("personal", "news", request, content, pathAdmin, pathApi, servletContext);
			String templatePath = baseAdmin+"/"+pathApi;
	        String pathAdmins = baseApi+"/"+pathAdmin+"/static/CMS";
	        return contents.replace(pathAdmins, templatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		}
		return null;
	}

	@Override
	public NotifyReadDto getTotalUnread(Long agentCode) {
		if (agentCode == null) {
			return new NotifyReadDto();
		}
		return notifyCoreRepository.getNotifyByRead(agentCode);
	}

	public String checkErrorMessage(String codeError, Object[] param, Locale locale) {
		String messageError = messageSource.getMessage(codeError, param, locale);

		return messageError;
	}

	@Override
	public String addNotifyByType(List<NotifyInputDto> dto) throws Exception {
		String user = "system";
		String message = "D000";
		SimpleDateFormat format = new SimpleDateFormat("yy");
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		String maxCode = CmsPrefixCodeConstant.PREFIX_CODE_NOT + format.format(new Date()) + formatMM.format(new Date())
				+ ".";
		;
		try {
			
			String value = jcaSystemConfigService.getValueByKey("BATCH_SIZE", 1L);
			int batchSize = Integer.parseInt(value);
			
			for (NotifyInputDto ls : dto) {
				Notify entity = new Notify();
				entity.setNotifyCode(CommonUtil.getNextBannerCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT,
						notifyRepository.getMaxNotifyCode(maxCode)));
				if (StringUtils.isNotBlank(user)) {
					entity.setCreateBy(user);
				} else {
					entity.setCreateBy("");
				}
				entity.setCreateDate(new Date());
				entity.setNotifyTitle(ls.getNotifyTitlte());
				entity.setContents(ls.getContent());
				entity.setLinkNotify(ls.getLinkNotify());
				entity.setSendImmediately(ls.isSendImmediately());
				entity.setSendDate(ls.getSendDate());
				entity.setActive(ls.isActive());
				entity.setApplicableObject("SEL");
				entity.setNotifyType(1);
				entity.setSend(true);
				notifyRepository.save(entity);
				logger.error(String.valueOf(entity.getNotifyCode()));
				try {
					insertNotifyDetail(ls.getLstData(), entity.getId(), batchSize);
				} catch (SQLException e) {
					message = "Error" + e;

				}
				if (entity.isActive() == true) {
					if (entity.isSendImmediately() == true) {
						try {
							pushNotificationToFirebaseCloud(ls.getLstData(), entity);
						} catch (Exception e) {
							message = "Error" + e;
						}
					}
				}
			}
		}catch (Exception e){
			message = "Error" + e;
		}
		return message;
	}

	public void insertNotifyDetail(List<NotifyAddDto> dto, Long notifyId, int batchSize) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		connection.setAutoCommit(false);
		String query = "INSERT INTO dbo.M_NOTIFYS_APPLICABLE_DETAIL (NOTIFY_ID, AGENT_CODE, IS_READ_ALREADY, MESSAGE_SYSTEM) VALUES (?,?,?,?)";
		PreparedStatement pst = connection.prepareStatement(query);
		int startRow = 0;
		for (NotifyAddDto ls : dto) {
			pst.setLong(1, notifyId);
			pst.setInt(2, ls.getAgentCode());
			pst.setBoolean(3, false);
			pst.setString(4, ls.getMessage());
			pst.addBatch();
			startRow++;

			if (startRow % batchSize == 0) {
				pst.executeBatch();
				pst.clearBatch();
				startRow = 0;
			}
		}
		pst.executeBatch();
		pst.clearBatch();
		connection.commit();
	}

	private void pushNotificationToFirebaseCloud(List<NotifyAddDto> dto,Notify entity) throws Exception {

		final String MESSAGE = "Message";
		final String NOTIFICATION = "notifications";
		try {
			String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);
			GoogleCredentials googleCredentials = GoogleCredentials
					.fromStream(getClass().getClassLoader().getResourceAsStream(fileName));
			@SuppressWarnings("deprecation")
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
			Firestore db = FirestoreClient.getFirestore();

			for (NotifyAddDto item : dto) {
				Map<String, Object> docData = new HashMap<>();
				docData.put("notifyCode", entity.getNotifyCode());
				docData.put("notifyTitle", entity.getNotifyTitle());
				docData.put("contents", entity.getContents());
				docData.put("linkNotify", entity.getLinkNotify());
				docData.put("createdDate", entity.getCreateDate());
				docData.put("createBy", entity.getCreateBy());

				try {
					ApiFuture<WriteResult> future = db.collection(NOTIFICATION).document(item.getAgentCode().toString())
							.collection(MESSAGE).document(entity.getNotifyCode()).set(docData, SetOptions.merge());
					if(future.isDone()){
						logger.error("Update time : " + future.get().getUpdateTime());
					} else {
						logger.error("Write Firestore not done!");
					}
				} catch (Exception ex){
					logger.error("Firestore Error", ex);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception("Push notification to firebase failed!");
		}
	}
	@Override
	public boolean likeMessage(NotifySearchLike searchDto) {
		
		NotifysApplicableDetail entity = notifysApplicableDetailRepository.getDetailByIsLike(searchDto.getAgentCode(),searchDto.getMessageId());
		
		if(entity !=null) {
			if(searchDto.getIsLike().equals("0")) {
				entity.setLike(false);
				notifysApplicableDetailRepository.save(entity);
				return false;
			}
			else {
				entity.setLike(true);
				notifysApplicableDetailRepository.save(entity);
			}
			
			
		}
		
		return true;
		
	}

	@Override
	public String getTemplatePopupMaintain(String code) {
		return notifyRepository.getTemplateContentByCode(code);
	}
}

package vn.com.unit.cms.admin.all.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import org.apache.commons.collections4.CollectionUtils;
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
import vn.com.unit.cms.admin.all.dto.OrderEditDto;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.DocumentCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.service.OrderService;
import vn.com.unit.cms.admin.all.service.ParentPathService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.cms.core.module.notify.entity.NotifysApplicableDetail;
import vn.com.unit.cms.core.module.notify.repository.NotifyRepository;
import vn.com.unit.cms.core.module.notify.repository.NotifysApplicableDetailRepository;
import vn.com.unit.cms.core.module.order.dto.OrderLanguageSearchDto;
import vn.com.unit.cms.core.module.order.dto.OrderSearchDto;
import vn.com.unit.cms.core.module.order.entity.Order;
import vn.com.unit.cms.core.module.order.repository.OrderRepository;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.exception.BusinessException;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

	private static final String PATH_PARENT_TYPE = "M_ORDER";
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
    private Environment env;
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DocumentCategoryLanguageRepository documentCategoryLanguageRepository;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private CmsCommonService comService;

	@Autowired
	private NotifyRepository notifyRepository;
	
	@Autowired
	private NotifysApplicableDetailRepository notifysApplicableDetailRepository;
	
	@Autowired
    JcaAccountService jcaAccountService;
	
	@Autowired
    Db2ApiService db2ApiService;
	
	@Autowired
    SystemLogsService systemLogsService;

	@Autowired
	private JcaDatatableConfigService jcaDatatableConfigService;

	@Autowired
	private CommonSearchFilterUtils commonSearchFilterUtils;

	@Autowired
	private MessageSource msg;

	@Autowired
	private ParentPathService parentPathService;

	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;
	
	@Override
	public List<OrderLanguageSearchDto> getListByCondition(OrderSearchDto searchDto,
			Pageable pageable) {
		searchDto.setCompanyId(UserProfileUtils.getCompanyId());
		return orderRepository.findListSearch(searchDto, pageable).getContent();
	}

	@Override
	public int countListByCondition(OrderSearchDto searchDto) {
		searchDto.setCompanyId(UserProfileUtils.getCompanyId());
		int count =orderRepository.countList(searchDto);
//		int count = 4;
		return count ;
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
	public List<CommonSearchFilterDto> initListSearchFilter(OrderSearchDto searchDto, Locale locale) {
		List<CommonSearchFilterDto> list = OrderService.super.initListSearchFilter(searchDto, locale);
		List<CommonSearchFilterDto> rs = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(list)) {
			for (CommonSearchFilterDto filter : list) {
//				if ("categoryType".equals(filter.getField())) {
//					filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
//							filter.getFieldName(), searchDto.getCategoryType(), listDataType);
//					rs.add(filter);
//				}else {
					rs.add(filter);
//				}
			}
		}
		return rs;
	}

	@Override
	public OrderEditDto getEditDtoById(Long id, Locale locale) {
		OrderEditDto resultDto = new OrderEditDto();

		if (id == null) {
			resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
			return resultDto;
		}

		// set FaqsCategory
		Order entity = orderRepository.findOne(id);

		// dữ liệu ko tồn tại hoặc đã bị xóa
		if (entity == null || entity.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}

		if (null != entity) {
			resultDto.setId(entity.getId());
			resultDto.setCode(entity.getCode());
			resultDto.setAgentCode(entity.getAgentCode());
			resultDto.setAgentName(entity.getAgentName());
			resultDto.setOfficeCode(entity.getOfficeCode());
			resultDto.setPhone(entity.getPhone());
			resultDto.setAttachmentPhysicalImg(entity.getAttachmentPhysicalImg());
			resultDto.setStatusOrder(entity.getStatusOrder());
			resultDto.setNotes(entity.getNotes());
		}

		return resultDto;
	}

	@Override
	public void saveOrUpdate(OrderEditDto editDto, Locale locale) throws Exception {
		String username = UserProfileUtils.getUserNameLogin();

		createOrEditData(editDto, username);

		parentPathService.deleteMenuPathByDescendantId(editDto.getId(), PATH_PARENT_TYPE);
	}

	private void createOrEditData(OrderEditDto editDto, String userName) {
		Order entity = new Order();
		String contents = null;
		
		if (null != editDto.getId()) {
			entity = orderRepository.findOne(editDto.getId());

			if (null == entity) {
				throw new BusinessException("Not found order with id=" + editDto.getId());
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(userName);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(userName);
		}
		if ("5".equals(editDto.getStatusOrder())) {
			entity.setCancelDate(new Date());
			entity.setCancelBy(userName);
			contents = String.format("<p>Đơn đặt hàng <b>%s</b> đã bị hủy. %s</p>", editDto.getCode(), editDto.getNotes());
		} else if ("4".equals(editDto.getStatusOrder())) {
			contents = String.format("<p>Đơn đặt hàng <b>%s</b> chưa được xác nhận. %s</p>", editDto.getCode(), editDto.getNotes());
		}
		entity.setCode(editDto.getCode());
		entity.setAgentCode(editDto.getAgentCode());
		entity.setAgentName(editDto.getAgentName());
		entity.setOfficeCode(editDto.getOfficeCode());
		entity.setPhone(editDto.getPhone());
		entity.setAttachmentPhysicalImg(editDto.getAttachmentPhysicalImg());
		entity.setStatusOrder(editDto.getStatusOrder());
		entity.setNotes(editDto.getNotes());
		orderRepository.save(entity);
		editDto.setId(entity.getId());
		if ("4".equals(editDto.getStatusOrder()) || "5".equals(editDto.getStatusOrder())) {
			try {
				this.pushNotifyWhenEdit(editDto.getAgentCode(), editDto.getAgentName(), editDto.getCode(), entity.getChannel(), contents);
			} catch (SQLException e) {
				logger.error("##Error##" + e);
			}
		}
	}

	private Long pushNotifyWhenEdit(String agentCode, String agentName, String orderCode, String channel, String contents) throws SQLException {
		Notify entity = new Notify();
		
		entity.setNotifyCode(getNotifyCode());
		entity.setNotifyTitle("Thông báo đơn đặt hàng " + orderCode);
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
		NotifysApplicableDetail entityDetail = new NotifysApplicableDetail();
		entityDetail.setNotifyId(entity.getId());
		entityDetail.setAgentCode(Long.valueOf(agentCode));
		entityDetail.setAgentName(agentName);
		entityDetail.setReadAlready(false);
		entityDetail.setCreateDate(new Date());
		notifysApplicableDetailRepository.save(entityDetail);
		
		List<String> lstAgent = new ArrayList<String>();
		lstAgent.add(agentCode);
		try {
			if ("AG".equals(channel)) {
				this.pushNotificationToFirebaseCloud(entity, lstAgent);
			} else {
				List<ADPDeviceTokenDto> deviceToken = db2ApiService.getDeviceTokenInfo(lstAgent);
				if (!deviceToken.isEmpty()) {
					this.insertNotify(deviceToken, contents.replaceAll("<[^>]*>", ""));
				}
			}
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
 					
 					if (!lstToken.isEmpty()) {
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
	
	private void insertNotify(List<ADPDeviceTokenDto> dataInsert, String content) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		connection.setAutoCommit(false);
		String query = "INSERT INTO dbo.tbNotification"
				+ " (sAgentId, sDeviceToken, sMessage, nType, project, dtSubmitDate, nActive, dtCreatedDate, IsSend)"
				+ " VALUES (?,?,?,'20','eApp_AD',GETDATE(),0,GETDATE(),1)";
		PreparedStatement pst = connection.prepareStatement(query);
		for(ADPDeviceTokenDto data : dataInsert){
			pst.setString(1, data.getUserId());
			pst.setString(2, data.getDeviceToken());
			pst.setString(3, content);
			pst.addBatch();
		}
		pst.executeBatch();
		pst.clearBatch();
		connection.commit();
	}
	
	@Override
	public void deleteDataById(Long id) throws Exception {
		// userName
		String userName = UserProfileUtils.getUserNameLogin();

		Date deleteDate = new Date();

		Order entity = orderRepository.findOne(id);
		entity.setDeleteBy(userName);
		entity.setDeleteDate(deleteDate);
		orderRepository.update(entity);
		documentCategoryLanguageRepository.deleteData(id, userName);
	}

	@Override
	public List<OrderLanguageSearchDto> getListForSort(OrderSearchDto searchDto) {
//		return documentCategoryRepository.findListForSort(searchDto);
		return null;
	}

	@Override
	public void updateSortAll(OrderSearchDto searchDto) {

	}

	@Override
	public Class<?> getEnumColumnForExport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateNameForExport(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonSearchFilterUtils getCommonSearchFilterUtils() {
		return commonSearchFilterUtils;
	}
	
	@Override
	public List<OrderLanguageSearchDto> getListForExportGeneral() {
		return orderRepository.getListForExportGeneral();
	}
	
	@Override
	public List<OrderLanguageSearchDto> getListForExportDetail() {
		return orderRepository.getListForExportDetail();
	}
}

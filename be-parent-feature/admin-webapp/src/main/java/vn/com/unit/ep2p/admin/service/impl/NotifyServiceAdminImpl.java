package vn.com.unit.ep2p.admin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.admin.repository.NotifyAdminRepository;
import vn.com.unit.ep2p.admin.repository.NotifyDetailAdminRepository;
import vn.com.unit.ep2p.admin.service.NotifyAdminService;
import vn.com.unit.ep2p.dto.NotifyAdminEditDto;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotifyServiceAdminImpl implements NotifyAdminService {

	@Autowired
	public NotifyAdminRepository notifyRepository;

	@Autowired
	public NotifyDetailAdminRepository notifyDetailAdminRepository;

	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;

	@Autowired
	private Environment env;

	@Autowired
	private JcaAccountService jcaAccountService;
	
	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

	
	private static final Logger logger = LoggerFactory.getLogger(NotifyServiceAdminImpl.class);


	@Override
	public List<NotifyAdminEditDto> getSendDate(String sendDate) {
		return notifyRepository.getDateExit(sendDate);
	}

	@Override
	public void updateIsSend(Long id) {
		notifyRepository.updateIsSend(id);
	}

	@Override
	public NotifyAdminEditDto findNotifyId(String notifyCode) {
		return notifyRepository.findNotifyIdByCode(notifyCode);
	}

	@Override
	public void updateSendDate(Long id,String code) {
		NotifyAdmin entity = notifyRepository.findOne(id);
		entity.setId(id);
		entity.setSendDate(new Date());
		entity.setUpdateBy("system");
		entity.setUpdateDate(new Date());
		notifyRepository.save(entity);
	}

	@Override
	public List<Db2AgentDto> getNotifySaveDetail(Date date) {
		return notifyRepository.findNotifySaveDetail(date);
	}

	@Override
	public List<Db2AgentDto> getLsNotifyDetail(Long id) {
		return notifyRepository.getLsNotifyDetailById(id);
	}

	@Override
	public List<String> insertToDatabaseWithBatch(List<Db2AgentDto> lstData, Long notifyId, String contest, int batchSize)
			throws SQLException {
		
		//add list to string
		List<String> dataResult = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(lstData)) {
        	int totalDeleted = lstData.size();
        	int maxParm = 1000;
        	int fromIndex = 0;
        	int toIndex = totalDeleted > (fromIndex + maxParm) ? (fromIndex + maxParm) : totalDeleted;
        	while (fromIndex < totalDeleted) {
        		List<Db2AgentDto> dataAgent = new ArrayList<>();
        		dataAgent.addAll(lstData.subList(fromIndex, toIndex));
        		List<String> StringlstData = new ArrayList<String>();
        		dataAgent.stream().forEach(x ->{StringlstData.add(String.valueOf(x.getAgentCode()));});
        		String StringData = StringlstData.stream().collect(Collectors.joining(";"));

        		//get agent is send
        		List<String> listAgent =  notifyRepository.checkSendDate(StringData,notifyId);
        		
        		Connection connection = connectionProvider.getConnection();
        		connection.setAutoCommit(false);
        		String query = "INSERT INTO dbo.M_NOTIFYS_APPLICABLE_DETAIL (NOTIFY_ID, AGENT_CODE, IS_READ_ALREADY, MESSAGE_SYSTEM,CREATE_DATE) VALUES (?,?,?,?,?)";
        		PreparedStatement pst = connection.prepareStatement(query);
        		int startRow = 0;
        		
        	    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		
        		for (Db2AgentDto data : dataAgent) {	
        			List<String> result = listAgent.stream()                // convert list to stream
        	                .filter(line -> !line.equals(data.getAgentCode().toString()))     // we dont like mkyong
        	                .collect(Collectors.toList());
        			if(CollectionUtils.isNotEmpty(result))
        				continue;
        			pst.setLong(1, notifyId);
        			pst.setString(2, data.getAgentCode());
        			pst.setBoolean(3, false);
        			pst.setString(4, contest);
        			pst.setString(5, formatDate.format(new Date()));
        			dataResult.add(data.getAgentCode());
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
        		logger.error("##SAVE-DETAIL##");
        		
        		fromIndex = toIndex;
        		toIndex = totalDeleted > (fromIndex + maxParm) ? (fromIndex + maxParm) : totalDeleted;
        	}
        }
		return dataResult;
	}

	@Override
	public void insertDetailToDatabaseWithBatch(List<Db2AgentDto> lstData, Long notifyId, Date sendDate) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		connection.setAutoCommit(false);
		String query = "INSERT INTO dbo.M_NOTIFYS_APPLICABLE_DETAIL (NOTIFY_ID, TERRITORRY, AREA, REGION, OFFICE, POSITION, AGENT_CODE, IS_READ_ALREADY, AGENT_NAME,CREATE_DATE) VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst = connection.prepareStatement(query);
		int startRow = 0;
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
			pst.setString(10, formatDate.format(sendDate));

			pst.addBatch();
			startRow ++;
			if (startRow % batchSize == 0) {
				pst.executeBatch();
				pst.clearBatch();
				startRow = 0;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		pst.executeBatch();
		pst.clearBatch();
		connection.commit();
	}

	@Override
	public void updateCheckSave(Long id) {
		notifyRepository.updateCheckSave(id);
	}

	@Override
	public void pushNotificationToFirebaseCloud(List<Db2AgentDto> dataInsert, Db2AgentDto dto) throws Exception {
		final String MESSAGE = "Message";
		final String NOTIFICATION = "notifications";
		try {
			List<String> lstDocument = dataInsert.parallelStream().map(Db2AgentDto::getAgentCode).collect(Collectors.toList());

			String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);
			GoogleCredentials googleCredentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(fileName));

			@SuppressWarnings("deprecation")
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
			Firestore db = FirestoreClient.getFirestore();
			Map<String, Object> docData = new HashMap<>();
			//check agent login before?
			List<JcaAccount> lstAccount = jcaAccountService.getListByUserName(null);
			List<String> lstToken = new ArrayList<>();
			for (String agentCode : lstDocument) {
				JcaAccount  account = lstAccount.stream().filter(x->x.getUsername().equals(agentCode.toString())).findAny().orElse(null);
				if(account == null) continue;
				docData.put("notifyCode", dto.getNotifyCode());
				docData.put("notifyTitle", dto.getNotifyTitle());
				docData.put("contents", dto.getContents());
				docData.put("linkNotify", dto.getLinkNotify());
				docData.put("createdDate", dto.getCreateDate());
				docData.put("createBy", dto.getCreateBy());

				try {
					ApiFuture<WriteResult> future = db.collection(NOTIFICATION)
							.document(agentCode.toString())
							.collection(MESSAGE)
							.document(dto.getNotifyCode())
							.set(docData, SetOptions.merge());
					if(future.isDone()){
						logger.error("Update time : " + future.get().getUpdateTime());
					} else {
						logger.error("Write Firestore not done!");
					}
				} catch (Exception ex){
					logger.error("Firestore Error", ex);
				}
				//check agent login before?
				if(lstAccount.size() > 0){
					String token = account.getDeviceTokenMobile();
					if (StringUtils.isNotBlank(token)) {
						lstToken.add(token);
					}
				}
			}
			try {
				if(!lstToken.isEmpty()){
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

						logger.error("List of tokens that caused failures: " + failedTokens);
					}
				} else {
					logger.error("List token is empty!");
				}
			} catch (Exception ex) {
				logger.error("PUSH_NOTIFICATION", ex);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception("Push notification to firebase failed!");
		}
	}
	public MulticastMessage allPlatformsMessage(List<String> deviceToken,  Db2AgentDto dto) {
		final String ACTION = "FLUTTER_NOTIFICATION_CLICK";
		final String NOTIFICATION = "notificationPage";
		MulticastMessage multicastMessage = MulticastMessage.builder()
				.setNotification(Notification.builder()
						.setTitle(dto.getNotifyTitle())
						.setBody(dto.getContents().replaceAll("<[^>]*>", ""))
						.build())
				.putData("click_action", ACTION)
				.putData("route", NOTIFICATION)
				.addAllTokens(deviceToken)
				.build();
		return multicastMessage;
	}
}

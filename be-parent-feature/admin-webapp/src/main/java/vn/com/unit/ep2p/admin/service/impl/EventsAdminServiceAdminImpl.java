package vn.com.unit.ep2p.admin.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.common.utils.QRCodeUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.utils.DtsStringUtil;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.OfficeDto;
import vn.com.unit.ep2p.admin.entity.EventsAdmin;
import vn.com.unit.ep2p.admin.repository.EventAdminRepository;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.EventsAdminService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EventsAdminServiceAdminImpl implements EventsAdminService {

	@Autowired
	public EventAdminRepository eventAdminRepository;

	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;

	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired
    private JcaEmailService jcaEmailService;
	
	@Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;
	
	@Autowired
    private SystemConfig systemConfig;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(EventsAdminServiceAdminImpl.class);
	private static final String PREFIX = "ATO";
	private static final String AUTO_TITLE = "Điểm danh hàng ngày";
	private static final String AUTO_CONTENT = "GẶP NHAU HÀNG NGÀY – CÙNG NHAU TƯƠNG TÁC";

	@Override
	public List<Db2AgentDto> getEventSaveDetail(Date date) {
		return eventAdminRepository.findEventSaveDetail(date);
	}

	@Override
	public void updateCheckSave(Long id) {
		eventAdminRepository.updateCheckSave(id);
	}

	@Override
	public void insertDetailToDatabaseWithBatch(List<Db2AgentDto> dataInsert, Long eventId)throws SQLException  {
		Connection connection = connectionProvider.getConnection();
		connection.setAutoCommit(false);
		String query = "INSERT INTO dbo.M_EVENTS_APPLICABLE_DETAIL"
				+ " (EVENT_ID, TERRITORRY, AREA, REGION, OFFICE, POSITION, AGENT_CODE, ID_NUMBER, NAME, GENDER, EMAIL, TEL, ADDRESS, GUEST_TYPE)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,'1')";
		PreparedStatement pst = connection.prepareStatement(query);
		int startRow = 0;
		String value = jcaSystemConfigService.getValueByKey("BATCH_SIZE", 1L);
		int batchSize = Integer.parseInt(value);
		for(Db2AgentDto data : dataInsert){
			pst.setLong(1,eventId);
			pst.setString(2, data.getTerritorry());
			pst.setString(3, data.getArea());
			pst.setString(4, data.getRegion());
			pst.setString(5, data.getOffice());
			pst.setString(6, data.getPosition());
			pst.setString(7, data.getAgentCode());
			pst.setString(8, data.getIdNumber());
			pst.setString(9, data.getAgentName());
			pst.setString(10, data.getGender());
			pst.setString(11, data.getEmailAddress1());
			pst.setString(12, data.getMobilePhone());
			pst.setString(13, data.getFullAddress());
			pst.addBatch();
			startRow ++;
			if (startRow % batchSize == 0) {
				pst.executeBatch();
				logger.error("PST = "+startRow);
				pst.clearBatch();
				startRow = 0;
			}
		}
		pst.executeBatch();
		pst.clearBatch();
		connection.commit();
	}
	
	@Override
	public void updateProcessing(Long id) {
		eventAdminRepository.updateProcessing(id);
	}
	
	@Override
	public boolean isUserLDAP(String agentCode) {
		return (eventAdminRepository.isUserLDAP(agentCode) > 0);
	}
	
	@Override
	public HashMap<OfficeDto, EventsAdmin> addAutoEvent() throws WriterException, IOException {
		List<OfficeDto> offices = db2ApiService.getListOfficeActive();
		HashMap<OfficeDto, EventsAdmin> events = new HashMap<>();
		EventsAdmin entity = null;
		Date current = null;
		for (OfficeDto office : offices) {
			if (StringUtils.isEmpty(office.getEmail())) {
				continue;
			}
			current = new Date();
			LocalDateTime eventDateTime = LocalDate.now().atTime(LocalTime.parse("08:00"));
			Instant instantEventDate = eventDateTime.atZone(ZoneId.systemDefault()).toInstant();
			LocalDateTime endDateTime = LocalDate.now().atTime(LocalTime.parse("18:00"));
			Instant instantEndDate = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
			
			entity = new EventsAdmin();
			entity.setEventCode(getNextBannerCode(PREFIX, eventAdminRepository.getMaxEventCode(PREFIX)));
			entity.setEventTitle(AUTO_TITLE);
			entity.setApplicableObject("ALL");
			entity.setContents(AUTO_CONTENT);
			entity.setCreateBy("AutoEvent");
			entity.setCreateDate(current);
			entity.setEventDate(Date.from(instantEventDate));
			entity.setEndDate(Date.from(instantEndDate));
			entity.setEventLocation(office.getOfficeCode() + " - " + office.getOfficeName());
			entity.setEventType("1");
			entity.setGroupEventCode("9");
			entity.setActivityEventCode("205");
			generateQRCode(entity);
			eventAdminRepository.save(entity);
			// put info when sendmail
			events.put(office, entity);
		}
		
		return events;
	}
	
	@Override
	public void sendMailQrCode(HashMap<OfficeDto, EventsAdmin> events) {
		events.forEach((office, event) -> 
			sendEmail(office, event)
		);
	}
	
	/**
	 * Send email to SS or CS
	 * @param event
	 * @param office
	 */
	private void sendEmail(OfficeDto office, EventsAdmin event) {
        List<String> toAddress = new ArrayList<>();
        String email = office.getEmail().replace("; ", ";");
        String[] arrEmail = email.split(";");
        for (int i=0; i < arrEmail.length; i++) {
        	if (!StringUtils.isEmpty(arrEmail[i])) {
        		toAddress.add(arrEmail[i]);
        	}
        }
        JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode("TEMPLATE_MAIL_AUTO_EVENT");
        // set email
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        String subject = String.format(templateDto.getTemplateSubject()
        		, formatDate.format(event.getEventDate())
        		, event.getEventLocation());
        JcaEmailDto emailDto = objectMapper.convertValue(templateDto, JcaEmailDto.class);
        emailDto.setSubject(subject);
        emailDto.setToString(String.join(",", toAddress));
        emailDto.setToAddress(toAddress);
        emailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
        String qrCode = "";
        try {
        	byte[] bytes = readQrCode(event.getQrCode());
			qrCode = Base64.getEncoder().encodeToString(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
        emailDto.setEmailContent(String.format(templateDto.getTemplateContent(), qrCode));
        emailDto.setContentType("text/html; charset=utf-8");
        jcaEmailService.sendEmail(emailDto);
	}
	
	/**
	 * Generate QRcode for event
	 * @param event
	 * @return path of QRCodeImage
	 * @throws java.io.IOException 
	 * @throws WriterException 
	 */
	private void generateQRCode(EventsAdmin event) throws WriterException, IOException {
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
	
	private byte[] readQrCode(String fileName) throws IOException {
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
	
	private String getNextBannerCode(String prefix, String maxCode) {
		int number = 5;

		if (StringUtils.isNotBlank(prefix)) {
			SimpleDateFormat format = new SimpleDateFormat("yy");
			SimpleDateFormat formatMM = new SimpleDateFormat("MM");
			
			prefix = prefix + format.format(new Date()) + formatMM.format(new Date()) +  ".";
		}
		
		StringBuffer nextCode = new StringBuffer(prefix);
		if (maxCode == null) {
			return nextCode.append("00001").toString();
		}
		try {
			maxCode = maxCode.replaceAll(prefix.toString(), "");
			return nextCode.append(DtsStringUtil.leftPad(String.valueOf(Long.valueOf(maxCode) + 1), number, "0"))
					.toString();
		} catch (Exception e) {
			return nextCode.append("00001").toString();
		}
	}
}

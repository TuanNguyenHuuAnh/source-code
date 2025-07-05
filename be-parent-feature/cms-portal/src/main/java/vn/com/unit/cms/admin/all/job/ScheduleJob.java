package vn.com.unit.cms.admin.all.job;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import vn.com.unit.cms.admin.all.dto.ContactBookingDto;
//import vn.com.unit.cms.admin.all.repository.ContactBookingRepository;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.dto.EmailDto;
//import vn.com.unit.jcanary.service.EmailService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
@EnableScheduling
public class ScheduleJob {

	/*@Autowired
	ContactBookingRepository contactBookingRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	MessageSource msg;
	
	@Autowired
	SystemConfig sysConfig;
	
	@Scheduled(cron="0 0 0 * * *")
	public int sendCustomerAppointmentNotificationMail() {
		List<ContactBookingDto> notiBookingList = contactBookingRepository.findBookingForNotification(new Date(), 1);
		for(ContactBookingDto notiBooking : notiBookingList){
			this.sendCustomerNotificationMail(notiBooking);
		}
		return 0;
	}
	
	private void sendCustomerNotificationMail(ContactBookingDto bookingDto){
//		Branch branchEntity = branchRepository.findOne(bookingEntity.getPlaceBookingBranchId());
		EmailDto emaildto = new EmailDto();
		emaildto.setSubject("[Viet Capital Bank] Thư nhắc hẹn");
		emaildto.setReceiveAddress(bookingDto.getEmail());
		emaildto.setTemplateFile("contact-booking-notification-template_" + "vi" + ".ftl");
		Map<String, Object> dataFillContentMail = new HashMap<String, Object>();
		dataFillContentMail.put("timeBooking", bookingDto.getTimeBooking());
		dataFillContentMail.put("placeBooking", bookingDto.getPlaceBooking());
		String patternDate = sysConfig.getConfig(AppSystemConfig.DATE_PATTERN);
		if (bookingDto.getDateBooking() != null) {
			DateFormat df = new SimpleDateFormat(patternDate);
			String strDateBooking = df.format(bookingDto.getDateBooking());
			dataFillContentMail.put("dateBooking", strDateBooking);
		} else {
			dataFillContentMail.put("dateBooking", "");
		}
		emaildto.setData(dataFillContentMail);
		emailService.sendEmail(emaildto);
	}*/

}

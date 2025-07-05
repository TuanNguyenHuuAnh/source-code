package vn.com.unit.ep2p.admin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.admin.repository.EventAdminRepository;
import vn.com.unit.ep2p.admin.repository.NotifyAdminRepository;
import vn.com.unit.ep2p.admin.service.NotifyEventsService;
import vn.com.unit.ep2p.dto.NotifyEventsDto;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotifyEventsServiceImpl implements NotifyEventsService {

	@Autowired
	public EventAdminRepository eventAdminRepository;
	
	@Autowired
	public NotifyAdminRepository notifyRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(NotifyEventsServiceImpl.class);
	private static final String PREFIX = "NOT";
	
	@Override
	public List<NotifyEventsDto> getListNotifyEvents() throws ParseException {
		Date now = CommonDateUtil.getSystemDate();
		Date fromDate = CommonDateUtil.formatStringToDate(
				CommonDateUtil.formatDateToString(now, CoreConstant.YYYYMMDD) + "070000",
				CoreConstant.YYYYMMDD_TIME);
		Date endDate = CommonDateUtil.addHours(fromDate, 48);
		return eventAdminRepository.getListNotifyEvents(fromDate, endDate);
	}
	
	@Override
	public Long addNewNotify(NotifyEventsDto dto, Date createDate) {
		NotifyAdmin entity = new NotifyAdmin();
		
		SimpleDateFormat format = new SimpleDateFormat("yy");
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		entity.setNotifyCode(CommonUtil.getNextBannerCode(PREFIX,
				notifyRepository.getMaxNotifyCode(PREFIX + format.format(new Date()) + formatMM.format(new Date()))));
		entity.setNotifyTitle("Thông báo sự kiện");
		entity.setNotifyType(2);
		entity.setContents(dto.getContents());
		entity.setLinkNotify(dto.getLinkNotify());
		entity.setSendImmediately(true);
		entity.setActive(true);
		entity.setApplicableObject("ALL");
		entity.setCreateBy("system");
		entity.setSendDate(createDate);
		entity.setSend(true);
		entity.setFc(false);
		entity.setCreateDate(createDate);
		entity.setNotifyType(1);
		entity.setSaveDetail(false);
		notifyRepository.save(entity);
		//save notify detail
		notifyRepository.insertDetailFromEvent(entity.getId(), dto.getEventId());
		
		return entity.getId();
	}
	
	@Override
	public List<String> getLsAgentFromEventId(Long eventId) {
		return notifyRepository.getLsAgentFromEventId(eventId);
	}
}

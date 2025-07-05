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
import vn.com.unit.ep2p.admin.dto.PolicyMaturedInfoDto;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.admin.repository.EventAdminRepository;
import vn.com.unit.ep2p.admin.repository.NotifyAdminRepository;
import vn.com.unit.ep2p.admin.repository.NotifyDetailAdminRepository;
import vn.com.unit.ep2p.admin.service.NotifyEventsService;
import vn.com.unit.ep2p.admin.service.NotifyPolicyMaturedService;
import vn.com.unit.ep2p.dto.NotifyDetailAdminEditDto;
import vn.com.unit.ep2p.dto.NotifyEventsDto;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotifyPolicyMaturedServiceImpl implements NotifyPolicyMaturedService {

	@Autowired
	public EventAdminRepository eventAdminRepository;
	
	@Autowired
	public NotifyAdminRepository notifyRepository;
	
	@Autowired
	public NotifyDetailAdminRepository notifyDetailRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(NotifyPolicyMaturedServiceImpl.class);
	private static final String PREFIX = "NOT";
	
	@Override
	public Long addNewNotify(PolicyMaturedInfoDto dto, Date createDate) {
		NotifyAdmin entity = new NotifyAdmin();
		
		SimpleDateFormat format = new SimpleDateFormat("yyMM");
		entity.setNotifyCode(CommonUtil.getNextBannerCode(PREFIX,
				notifyRepository.getMaxNotifyCode(PREFIX + format.format(new Date()))));
		entity.setNotifyTitle("Khách hàng có nhu cầu mua mới sau đáo hạn");
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
		NotifyDetailAdminEditDto detailentity = new NotifyDetailAdminEditDto();
		detailentity.setNotifyId(entity.getId());
		detailentity.setAgentCode(Long.valueOf(dto.getAgentCode()));
		detailentity.setReadAlready(false);
		notifyDetailRepository.save(detailentity);
		
		return entity.getId();
	}
}

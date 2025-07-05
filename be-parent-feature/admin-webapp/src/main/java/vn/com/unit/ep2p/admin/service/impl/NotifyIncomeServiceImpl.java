package vn.com.unit.ep2p.admin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.dto.IncomeDto;
import vn.com.unit.ep2p.admin.entity.NotifyAdmin;
import vn.com.unit.ep2p.admin.repository.NotifyAdminRepository;
import vn.com.unit.ep2p.admin.repository.NotifyDetailAdminRepository;
import vn.com.unit.ep2p.admin.service.NotifyIncomeService;
import vn.com.unit.ep2p.dto.NotifyDetailAdminEditDto;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotifyIncomeServiceImpl implements NotifyIncomeService {
	
	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;
	
	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;
	
	@Autowired
	private NotifyAdminRepository notifyRepository;
	
	@Autowired
	private NotifyDetailAdminRepository notifyDetailRepository;

	public static final String PREFIX_CODE_NOT = "NOT";
	
	@Override
	public Long pushListNotifyForWeb(List<IncomeDto> incomeInfo) {
		Long id = 0L;
		for (IncomeDto incomeDto : incomeInfo) {
			if (id == 0L) {
				//save notify
				NotifyAdmin entity = new NotifyAdmin();
				entity.setNotifyCode(getNotifyCode());
				entity.setNotifyTitle(incomeDto.getTitle());
				entity.setNotifyType(2);
				entity.setContents(incomeDto.getContents());
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
				id = entity.getId();
			}
			//save notify detail
			NotifyDetailAdminEditDto entityDetail = new NotifyDetailAdminEditDto();
			entityDetail.setNotifyId(id);
			entityDetail.setAgentCode(new Long(incomeDto.getAgentCode()));
			entityDetail.setReadAlready(false);
			entityDetail.setCreateDate(new Date());
			notifyDetailRepository.save(entityDetail);
		}
		
		return id;
	}
	
	private String getNotifyCode() {
		SimpleDateFormat format = new SimpleDateFormat("yy");
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		return CommonUtil.getNextBannerCode(PREFIX_CODE_NOT,
				notifyRepository.getMaxNotifyCode(PREFIX_CODE_NOT
						+ format.format(new Date()) + formatMM.format(new Date())));
	}

}

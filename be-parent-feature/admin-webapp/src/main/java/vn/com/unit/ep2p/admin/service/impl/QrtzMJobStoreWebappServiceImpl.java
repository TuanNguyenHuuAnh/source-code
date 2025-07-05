package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.repository.AppQrtzMJobStoreRepository;
import vn.com.unit.ep2p.admin.service.QrtzMJobStoreWebappService;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;

@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzMJobStoreWebappServiceImpl implements QrtzMJobStoreWebappService {

	@Autowired
	AppQrtzMJobStoreRepository qMJobStoreRepository;

	@Override
	public QrtzMJobStore getByGroupCode(String groupCode) {
		QrtzMJobStore jobStore = qMJobStoreRepository.getByGroupCode(groupCode);
		return jobStore == null ? new QrtzMJobStore() : jobStore;
	}

	@Override
	public QrtzMJobStore getById(Long id) {
		QrtzMJobStore qSchedule = qMJobStoreRepository.findOne(id);
		return qSchedule == null ? new QrtzMJobStore() : qSchedule;
	}

	@Override
	public List<String> getGroupIds() {
		List<String> groupIds = qMJobStoreRepository.getGroupIds();
		return groupIds == null ? new ArrayList<>() : groupIds;
	}

	@Override
	public boolean isGroupCodeExists(String groupCode) {
		QrtzMJobStore jobStore = getByGroupCode(groupCode);
		return jobStore != null && jobStore.getId() != null;
	}

	@Override
	public boolean save(QrtzMJobStore jobStoreEntity) {
		try {
//			String userName = UserProfileUtils.getUserNameLogin();
//			jobStoreEntity.setCreatedBy(userName);
			jobStoreEntity.setCreatedDate(new Date());
			jobStoreEntity.setValidflag(1L);
			if (null != jobStoreEntity.getId()) {
			    qMJobStoreRepository.update(jobStoreEntity);
            } else {
                qMJobStoreRepository.create(jobStoreEntity);
            }
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}

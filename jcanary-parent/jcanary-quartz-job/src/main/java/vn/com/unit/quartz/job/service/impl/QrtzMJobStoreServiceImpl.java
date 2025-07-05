/*******************************************************************************
 * Class        ：QrtzMJobStoreServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobStoreDto;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;
import vn.com.unit.quartz.job.repository.QrtzMJobStoreRepository;
import vn.com.unit.quartz.job.service.QrtzMJobStoreService;


/**
 * <p>
 * QrtzMJobStoreServiceImpl
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzMJobStoreServiceImpl extends AbstractQrtzJobService implements QrtzMJobStoreService {

	/** The q M job store repository. */
	@Autowired
	private QrtzMJobStoreRepository qMJobStoreRepository;
	
	private static final Long SYSTEM_ID = 0l;

	/* (non-Javadoc)
	 * @see vn.com.unit.quartz.job.service.QrtzMJobStoreService#getByGroupCode(java.lang.String)
	 */
	@Override
	public QrtzMJobStore getByGroupCode(String groupCode) {
		QrtzMJobStore jobStore = qMJobStoreRepository.getByGroupCode(groupCode);
		return jobStore == null ? new QrtzMJobStore() : jobStore;
	}

	

	/* (non-Javadoc)
	 * @see vn.com.unit.quartz.job.service.QrtzMJobStoreService#getGroupIds()
	 */
	@Override
	public List<String> getGroupIds() {
		List<String> groupIds = qMJobStoreRepository.getGroupIds();
		return groupIds == null ? new ArrayList<>() : groupIds;
	}

	/* (non-Javadoc)
	 * @see vn.com.unit.quartz.job.service.QrtzMJobStoreService#isGroupCodeExists(java.lang.String)
	 */
	@Override
	public boolean isGroupCodeExists(String groupCode) {
		QrtzMJobStore jobStore = getByGroupCode(groupCode);
		return jobStore != null && jobStore.getId() != null;
	}

	/////////////////////////////NEW/////////////////////////////////////
	
    @Override
    public QrtzMJobStoreDto create(QrtzMJobStoreDto qrtzMJobStore) throws DetailException {
        if (null == qrtzMJobStore) {
            throw new DetailException("REQUSET NULL");
        }
        QrtzMJobStore entity = null;
        if (null != qrtzMJobStore.getId()) {
            entity = qMJobStoreRepository.findOne(qrtzMJobStore.getId());
        }
        if (null != entity) {
            entity = mapper.convertValue(qrtzMJobStore, QrtzMJobStore.class);
            entity.setUpdatedDate(new Date());
            entity.setUpdatedId(SYSTEM_ID);
            entity = qMJobStoreRepository.update(entity);
        } else {
            entity = mapper.convertValue(qrtzMJobStore, QrtzMJobStore.class);
            entity.setCreatedDate(new Date());
            entity.setCreatedId(SYSTEM_ID);
            entity = qMJobStoreRepository.create(entity);
        }
        return mapper.convertValue(entity, QrtzMJobStoreDto.class);
    }

    @Override
    public QrtzMJobStoreDto update(QrtzMJobStoreDto qrtzMJobStore) throws DetailException {
        if (null == qrtzMJobStore) {
            throw new DetailException("REQUSET NULL");
        }
        if (null != qrtzMJobStore.getId()) {
            QrtzMJobStore entity = qMJobStoreRepository.findOne(qrtzMJobStore.getId());
            if (null != entity) {
                entity = mapper.convertValue(qrtzMJobStore, QrtzMJobStore.class);
                entity.setUpdatedDate(new Date());
                entity.setUpdatedId(SYSTEM_ID);
                entity = qMJobStoreRepository.update(entity);
                return mapper.convertValue(entity, QrtzMJobStoreDto.class);
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }
    }

    @Override
    public boolean delete(Long id) throws DetailException {
        if (id != null) {
            QrtzMJobStore entity = qMJobStoreRepository.findOne(id);
            if (null != entity) {
                entity.setDeletedDate(new Date());
                entity.setDeletedId(SYSTEM_ID);
                qMJobStoreRepository.update(entity);
                return true;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }
    }

    @Override
    public List<QrtzMJobStoreDto> list(Pageable pageable) throws DetailException {
        return qMJobStoreRepository.getQrtzMJobStore(pageable).getContent();
    }

    @Override
    public int count() throws DetailException {
        return qMJobStoreRepository.countQrtzMJobStore();
    }

    @Override
    public QrtzMJobStoreDto detail(Long id) throws DetailException {
        if (null != id) {
            QrtzMJobStore entity = qMJobStoreRepository.findOne(id);
            if (null != entity) {
                QrtzMJobStoreDto qrtzMJobStoreDto = mapper.convertValue(entity, QrtzMJobStoreDto.class);
                return qrtzMJobStoreDto;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("REQUEST NULL");
        }
    }

}

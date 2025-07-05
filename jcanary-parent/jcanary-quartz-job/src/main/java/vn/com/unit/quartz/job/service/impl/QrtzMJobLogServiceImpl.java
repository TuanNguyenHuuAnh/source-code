/*******************************************************************************
 * Class        ：QrtzMJobLogServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobLogDto;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.repository.QrtzMJobLogRepository;
import vn.com.unit.quartz.job.service.QrtzMJobLogService;


/**
 * <p>
 * QrtzMJobLogServiceImpl
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional
public class QrtzMJobLogServiceImpl extends AbstractQrtzJobService implements QrtzMJobLogService {

	/** The q M job log repository. */
	@Autowired
	private QrtzMJobLogRepository qMJobLogRepository;
	
	private static final Long SYSTEM_ID = 0l;

	/* (non-Javadoc)
	 * @see vn.com.unit.quartz.job.service.QrtzMJobLogService#getByJobNameRefAndStartTime(java.lang.Long)
	 */
	@Override
	public QrtzMJobLog getByJobNameRefAndStartTime(Long id) {
		QrtzMJobLog qMJobLog = qMJobLogRepository.getByJobNameRefAndStartTime(id);
		return qMJobLog == null ? new QrtzMJobLog() : qMJobLog;
	}

	///////////////////////////////////////NEW////////////////////////////////////////////////
	
    @Override
    public QrtzMJobLogDto create(QrtzMJobLogDto qrtzMJobLog) throws DetailException{
        if (null == qrtzMJobLog) {
            throw new DetailException("REQUSET NULL");
        }
        QrtzMJobLog entity = null;
        if (null != qrtzMJobLog.getId()) {
            entity = qMJobLogRepository.findOne(qrtzMJobLog.getId());
        }
        if (null != entity) {
            entity = mapper.convertValue(qrtzMJobLog, QrtzMJobLog.class);
            entity.setUpdatedDate(new Date());
            entity.setUpdatedId(SYSTEM_ID);
            
        } else {
            entity = mapper.convertValue(qrtzMJobLog, QrtzMJobLog.class);
            entity.setCreatedDate(new Date());
            entity.setCreatedId(SYSTEM_ID);
            entity = qMJobLogRepository.create(entity);
        }
        return mapper.convertValue(entity, QrtzMJobLogDto.class);
    }

    @Override
    public QrtzMJobLogDto update(QrtzMJobLogDto qrtzMJobLog) throws DetailException{
        if (null == qrtzMJobLog) {
            throw new DetailException("REQUSET NULL");
        }
        if (null != qrtzMJobLog.getId()) {
            QrtzMJobLog entity = qMJobLogRepository.findOne(qrtzMJobLog.getId());
            if (null != entity) {
                entity = mapper.convertValue(qrtzMJobLog, QrtzMJobLog.class);
                entity.setUpdatedDate(new Date());
                entity.setUpdatedId(SYSTEM_ID);
                entity = qMJobLogRepository.update(entity);
                return mapper.convertValue(entity, QrtzMJobLogDto.class);
            } else {
                throw new DetailException("NOT FOUND");
            }
        }else {
            throw new DetailException("ID NULL");
        }
        
    }

    @Override
    public boolean delete(Long id) throws DetailException{
        if (null != id) {
            QrtzMJobLog entity = qMJobLogRepository.findOne(id);
            if (null != entity) {
                entity.setDeletedDate(new Date());
                entity.setDeletedId(SYSTEM_ID);
                qMJobLogRepository.update(entity);
                return true;
            } else {
                throw new DetailException("NOT FOUND");
            }
        }else {
            throw new DetailException("ID NULL");
        }
    }

    @Override
    public List<QrtzMJobLogDto> list(Pageable pageable) throws DetailException{
        return qMJobLogRepository.findQrtzMJobLog(pageable).getContent();
    }

    @Override
    public int count() throws DetailException {
        return qMJobLogRepository.countQrtzMJobLog();
    }

    @Override
    public QrtzMJobLogDto detail(Long id) throws DetailException {
        if (null != id) {
            QrtzMJobLog entity = qMJobLogRepository.findOne(id);
            if (null != entity) {
                QrtzMJobLogDto qrtzMJobLogDto = mapper.convertValue(entity, QrtzMJobLogDto.class);
                return qrtzMJobLogDto;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("REQUEST NULL");
        }
    }
}

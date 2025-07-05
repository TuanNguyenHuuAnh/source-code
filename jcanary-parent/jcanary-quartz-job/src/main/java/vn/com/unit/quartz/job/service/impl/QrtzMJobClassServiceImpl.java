/*******************************************************************************
 * Class        ：QrtzMJobClassServiceImpl
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
import vn.com.unit.quartz.job.dto.QrtzMJobClassDto;
import vn.com.unit.quartz.job.entity.QrtzMJobClass;
import vn.com.unit.quartz.job.repository.QrtzMJobClassRepository;
import vn.com.unit.quartz.job.service.QrtzMJobClassService;

/**
 * <p>
 * QrtzMJobClassServiceImpl
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional
public class QrtzMJobClassServiceImpl extends AbstractQrtzJobService implements QrtzMJobClassService{

    /** The qrtz M job class repository. */
    @Autowired
    private QrtzMJobClassRepository qrtzMJobClassRepository;
    
    private static final Long SYSTEM_ID = 0l;
    
    /* (non-Javadoc)
     * @see vn.com.unit.quartz.job.service.QrtzMJobClassService#create(vn.com.unit.quartz.job.entity.QrtzMJobClass)
     */
    @Override
    public QrtzMJobClassDto create(QrtzMJobClassDto qrtzMJobClassDto) throws DetailException {
        if (null == qrtzMJobClassDto) {
            throw new DetailException("REQUSET NULL");
        }
        
        QrtzMJobClass entity = null;
        if (null != qrtzMJobClassDto.getId()) {
            entity = qrtzMJobClassRepository.findOne(qrtzMJobClassDto.getId());
        }
        if (null != entity) {
            entity = mapper.convertValue(qrtzMJobClassDto, QrtzMJobClass.class);
            entity.setUpdatedDate(new Date());
            entity.setUpdatedId(SYSTEM_ID);
            entity = qrtzMJobClassRepository.update(entity);
        } else {
            entity = mapper.convertValue(qrtzMJobClassDto, QrtzMJobClass.class);
            entity.setCreatedDate(new Date());
            entity.setCreatedId(SYSTEM_ID);
            entity = qrtzMJobClassRepository.create(entity);
        }
        return mapper.convertValue(entity, QrtzMJobClassDto.class);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.quartz.job.service.QrtzMJobClassService#update(vn.com.unit.quartz.job.entity.QrtzMJobClass)
     */
    @Override
    public QrtzMJobClassDto update(QrtzMJobClassDto qrtzMJobClassDto) throws DetailException {
        if (null == qrtzMJobClassDto) {
            throw new DetailException("REQUSET NULL");
        } 
        if (null != qrtzMJobClassDto.getId()) {
            QrtzMJobClass entity = qrtzMJobClassRepository.findOne(qrtzMJobClassDto.getId());
            if (null != entity) {
                entity = mapper.convertValue(qrtzMJobClassDto, QrtzMJobClass.class);
                entity.setUpdatedDate(new Date());
                entity.setUpdatedId(SYSTEM_ID);
                entity = qrtzMJobClassRepository.update(entity);
                return mapper.convertValue(entity, QrtzMJobClassDto.class);
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }
        
    }

    /* (non-Javadoc)
     * @see vn.com.unit.quartz.job.service.QrtzMJobClassService#delete(java.lang.Long)
     */
    @Override
    public boolean delete(Long id) throws DetailException {
        if (null != id) {
            QrtzMJobClass entity = qrtzMJobClassRepository.findOne(id);
            if (null != entity) {
                entity.setDeletedDate(new Date());
                entity.setDeletedId(SYSTEM_ID);
                qrtzMJobClassRepository.update(entity);
                return true;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.quartz.job.service.QrtzMJobClassService#list(org.springframework.data.domain.Pageable)
     */
    @Override
    public List<QrtzMJobClassDto> list(Pageable pageable) throws DetailException {
        return qrtzMJobClassRepository.getQrtzMJobClass(pageable).getContent();
    }

    @Override
    public int count() throws DetailException {
        return qrtzMJobClassRepository.countQrtzMJobClass();
    }

    @Override
    public QrtzMJobClassDto detail(Long id) throws DetailException {
        if (null != id) {
            QrtzMJobClass entity = qrtzMJobClassRepository.findOne(id);
            if (null != entity) {
                QrtzMJobClassDto qrtzMJobClassDto = mapper.convertValue(entity, QrtzMJobClassDto.class);
                return qrtzMJobClassDto;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("REQUEST NULL");
        }
    }

}

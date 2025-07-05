package vn.com.unit.quartz.job.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobTypeDto;
import vn.com.unit.quartz.job.entity.QrtzMJobType;
import vn.com.unit.quartz.job.repository.QrtzMJobTypeRepository;
import vn.com.unit.quartz.job.service.QrtzMJobTypeService;

@Service
@Transactional
public class QrtzMJobTypeServiceImpl extends AbstractQrtzJobService implements QrtzMJobTypeService{
    
    @Autowired
    private QrtzMJobTypeRepository qrtzMJobTypeRepository;
    
    private static final Long SYSTEM_ID = 0l;
    
    @Override
    public QrtzMJobTypeDto create(QrtzMJobTypeDto qrtzMJobType) throws DetailException {
        if (null == qrtzMJobType) {
            throw new DetailException("REQUSET NULL");
        }
        
        QrtzMJobType entity = null;
        if (null != qrtzMJobType.getId()) {
            entity = qrtzMJobTypeRepository.findOne(qrtzMJobType.getId());
        }
        if (null != entity) {
            entity = mapper.convertValue(qrtzMJobType, QrtzMJobType.class);
            entity.setUpdatedDate(new Date());
            entity.setUpdatedId(SYSTEM_ID);
            entity = qrtzMJobTypeRepository.update(entity);
        } else {
            entity = mapper.convertValue(qrtzMJobType, QrtzMJobType.class);
            entity.setCreatedDate(new Date());
            entity.setCreatedId(SYSTEM_ID);
            entity = qrtzMJobTypeRepository.create(entity);
        }
        return mapper.convertValue(entity, QrtzMJobTypeDto.class);
    }

    @Override
    public QrtzMJobTypeDto update(QrtzMJobTypeDto qrtzMJobType) throws DetailException {
        if (null == qrtzMJobType) {
            throw new DetailException("REQUSET NULL");
        } 
        if (null != qrtzMJobType.getId()) {
            QrtzMJobType entity = qrtzMJobTypeRepository.findOne(qrtzMJobType.getId());
            if (null != entity) {
                entity = mapper.convertValue(qrtzMJobType, QrtzMJobType.class);
                entity.setUpdatedDate(new Date());
                entity.setUpdatedId(SYSTEM_ID);
                entity = qrtzMJobTypeRepository.update(entity);
                return mapper.convertValue(entity, QrtzMJobTypeDto.class);
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }
    }

    @Override
    public boolean delete(Long id) throws DetailException {
        if (null != id) {
            QrtzMJobType entity = qrtzMJobTypeRepository.findOne(id);
            if (null != entity) {
                entity.setDeletedDate(new Date());
                entity.setDeletedId(SYSTEM_ID);
                qrtzMJobTypeRepository.update(entity);
                return true;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }
    }

    @Override
    public List<QrtzMJobTypeDto> list(Pageable pageable) throws DetailException {
        return qrtzMJobTypeRepository.getQrtzMJobType(pageable).getContent();
    }

    @Override
    public int count() throws DetailException {
        return qrtzMJobTypeRepository.countQrtzMJobType();
    }

    @Override
    public QrtzMJobTypeDto detail(Long id) throws DetailException {
        if (null != id) {
            QrtzMJobType entity = qrtzMJobTypeRepository.findOne(id);
            if (null != entity) {
                QrtzMJobTypeDto qrtzMJobTypeDto = mapper.convertValue(entity, QrtzMJobTypeDto.class);
                return qrtzMJobTypeDto;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("REQUEST NULL");
        }
    }

}

package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.QrtzMJobTypeReq;
import vn.com.unit.ep2p.service.QrtzJobTypeService;
import vn.com.unit.quartz.job.dto.QrtzMJobTypeDto;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.service.QrtzMJobTypeService;

@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzJobTypeServiceImpl extends AbstractCommonService implements QrtzJobTypeService{
    
    @Autowired
    private QrtzMJobTypeService qrtzMJobTypeService;
    
    @Override
    public ObjectDataRes<QrtzMJobTypeDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<QrtzMJobTypeDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, QrtzMJobSchedule.class, QrtzJobTypeService.TABLE_QRTZ_JOB_TYPE);

            int totalData = qrtzMJobTypeService.count();
            List<QrtzMJobTypeDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = qrtzMJobTypeService.list(pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, "QRTZ_LIST_ERROR");
        }
        return resObj;
    }

    @Override
    public QrtzMJobTypeDto save(QrtzMJobTypeDto objectDto) throws DetailException {
        return qrtzMJobTypeService.create(objectDto);
    }

    @Override
    public void delete(Long id) throws DetailException {
        boolean delete = qrtzMJobTypeService.delete(id);
        if (!delete) {
            throw new DetailException("DELETE_ERROR");
        }
        
    }

    @Override
    public QrtzMJobTypeDto detail(Long id) throws DetailException {
        return qrtzMJobTypeService.detail(id);
    }

    @Override
    public QrtzMJobTypeDto create(QrtzMJobTypeReq qrtzMJobTypeReq) throws DetailException {
        QrtzMJobTypeDto requset = objectMapper.convertValue(qrtzMJobTypeReq, QrtzMJobTypeDto.class);
        return qrtzMJobTypeService.create(requset);
    }

    @Override
    public QrtzMJobTypeDto update(QrtzMJobTypeReq qrtzMJobTypeReq) throws DetailException {
        QrtzMJobTypeDto requset = objectMapper.convertValue(qrtzMJobTypeReq, QrtzMJobTypeDto.class);
        return qrtzMJobTypeService.update(requset);
    }

}

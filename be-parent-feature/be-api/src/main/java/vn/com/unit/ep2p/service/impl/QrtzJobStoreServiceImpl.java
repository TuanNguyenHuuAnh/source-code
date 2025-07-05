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
import vn.com.unit.ep2p.dto.req.QrtzMJobStoreReq;
import vn.com.unit.ep2p.service.QrtzJobStoreService;
import vn.com.unit.quartz.job.dto.QrtzMJobStoreDto;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.service.QrtzMJobStoreService;

@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzJobStoreServiceImpl extends AbstractCommonService implements QrtzJobStoreService {

    @Autowired
    private QrtzMJobStoreService qrtzMJobStoreService;

    @Override
    public ObjectDataRes<QrtzMJobStoreDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<QrtzMJobStoreDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, QrtzMJobSchedule.class, QrtzJobStoreService.TABLE_QRTZ_JOB_STORE);

            int totalData = qrtzMJobStoreService.count();
            List<QrtzMJobStoreDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = qrtzMJobStoreService.list(pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, "QRTZ_LIST_ERROR");
        }
        return resObj;
    }

    @Override
    public QrtzMJobStoreDto save(QrtzMJobStoreDto objectDto) throws DetailException {
        return qrtzMJobStoreService.create(objectDto);
    }

    @Override
    public void delete(Long id) throws DetailException {
        boolean delete = qrtzMJobStoreService.delete(id);
        if (!delete) {
            throw new DetailException("DELETE_ERROR");
        }
    }

    @Override
    public QrtzMJobStoreDto detail(Long id) throws DetailException {
        return qrtzMJobStoreService.detail(id);
    }

    @Override
    public QrtzMJobStoreDto create(QrtzMJobStoreReq qrtzMJobStoreReq) throws DetailException {
        QrtzMJobStoreDto request = objectMapper.convertValue(qrtzMJobStoreReq, QrtzMJobStoreDto.class);
        return qrtzMJobStoreService.create(request);
    }

    @Override
    public QrtzMJobStoreDto update(QrtzMJobStoreReq qrtzMJobStoreReq) throws DetailException {
        QrtzMJobStoreDto request = objectMapper.convertValue(qrtzMJobStoreReq, QrtzMJobStoreDto.class);
        return qrtzMJobStoreService.update(request);
    }

}

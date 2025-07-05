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
import vn.com.unit.ep2p.dto.req.QrtzMJobLogReq;
import vn.com.unit.ep2p.service.QrtzJobLogService;
import vn.com.unit.quartz.job.dto.QrtzMJobLogDto;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.quartz.job.service.QrtzMJobLogService;

@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzJobLogServiceImpl extends AbstractCommonService implements QrtzJobLogService{
    
    @Autowired
    private QrtzMJobLogService qrtzMJobLogService;

    @Override
    public ObjectDataRes<QrtzMJobLogDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<QrtzMJobLogDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, QrtzMJobLog.class, QrtzJobLogService.TABLE_QRTZ_JOB_LOG);
            
            int totalData = qrtzMJobLogService.count();
            List<QrtzMJobLogDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = qrtzMJobLogService.list(pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, "QRTZ_LIST_ERROR");
        }
        return resObj;
    }

    @Override
    public QrtzMJobLogDto save(QrtzMJobLogDto objectDto) throws DetailException {
        return qrtzMJobLogService.create(objectDto);
    }

    @Override
    public void delete(Long id) throws DetailException {
        boolean delete = qrtzMJobLogService.delete(id);
        if (!delete) {
            throw new DetailException("DELETE_ERROR");
        }
    }

    @Override
    public QrtzMJobLogDto detail(Long id) throws DetailException {
        return qrtzMJobLogService.detail(id);
    }

    @Override
    public QrtzMJobLogDto create(QrtzMJobLogReq qrtzMJobLogReq) throws DetailException {
        QrtzMJobLogDto request = objectMapper.convertValue(qrtzMJobLogReq, QrtzMJobLogDto.class);
        return qrtzMJobLogService.create(request);
    }

    @Override
    public QrtzMJobLogDto update(QrtzMJobLogReq qrtzMJobLogReq) throws DetailException {
        QrtzMJobLogDto request = objectMapper.convertValue(qrtzMJobLogReq, QrtzMJobLogDto.class);
        return qrtzMJobLogService.update(request);
    }

}

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
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.QrtzMScheduleReq;
import vn.com.unit.ep2p.service.QrtzScheduleService;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.service.QrtzMScheduleService;

@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzScheduleServiceImpl extends AbstractCommonService implements QrtzScheduleService{
    @Autowired
    private QrtzMScheduleService qrtzMScheduleService;

    @Override
    public ObjectDataRes<QrtzMScheduleDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<QrtzMScheduleDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, QrtzMJobSchedule.class, QrtzScheduleService.TABLE_QRTZ_SCHEDULE);
            QrtzMScheduleSearchDto searchDto = buildQrtzMScheduleSearchDto(commonSearch);
            
            int totalData = qrtzMScheduleService.count(searchDto);
            List<QrtzMScheduleDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = qrtzMScheduleService.list(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, "QRTZ_LIST_ERROR");
        }
        return resObj;
    }

    @Override
    public QrtzMScheduleDto save(QrtzMScheduleDto objectDto) throws DetailException {
        return qrtzMScheduleService.create(objectDto);
    }

    @Override
    public void delete(Long id) throws DetailException {
        boolean delete = qrtzMScheduleService.delete(id);
        if (!delete) {
            throw new DetailException("DELETE_ERROR");
        }
                
    }

    @Override
    public QrtzMScheduleDto detail(Long id) throws DetailException {
        return qrtzMScheduleService.detail(id);
    }

    @Override
    public QrtzMScheduleDto create(QrtzMScheduleReq qrtzMScheduleReq) throws DetailException {
        QrtzMScheduleDto request = objectMapper.convertValue(qrtzMScheduleReq, QrtzMScheduleDto.class);
        return qrtzMScheduleService.create(request);
    }

    @Override
    public QrtzMScheduleDto update(QrtzMScheduleReq qrtzMScheduleReq) throws DetailException {
        QrtzMScheduleDto request = objectMapper.convertValue(qrtzMScheduleReq, QrtzMScheduleDto.class);
        return qrtzMScheduleService.update(request);
    }
    
    
    private QrtzMScheduleSearchDto buildQrtzMScheduleSearchDto(MultiValueMap<String, String> commonSearch) {
        QrtzMScheduleSearchDto qrtzMScheduleSearchDto = new QrtzMScheduleSearchDto();
        String schedCode = null != commonSearch.getFirst("schedCode") ? commonSearch.getFirst("schedCode") : AppApiConstant.EMPTY;
        String schedName = null != commonSearch.getFirst("schedName") ? commonSearch.getFirst("schedName") : AppApiConstant.EMPTY;
        String cronExpression = null != commonSearch.getFirst("cronExpression") ? commonSearch.getFirst("cronExpression") : AppApiConstant.EMPTY;
        String description = null != commonSearch.getFirst("description") ? commonSearch.getFirst("description") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        String companyName = null != commonSearch.getFirst("companyName") ? commonSearch.getFirst("companyName") : AppApiConstant.EMPTY;
        qrtzMScheduleSearchDto.setSchedCode(schedCode);
        qrtzMScheduleSearchDto.setSchedName(schedName);
        qrtzMScheduleSearchDto.setCronExpression(cronExpression);
        qrtzMScheduleSearchDto.setDescription(description);
        qrtzMScheduleSearchDto.setCompanyId(companyId);
        qrtzMScheduleSearchDto.setCompanyName(companyName);
        return qrtzMScheduleSearchDto;
    }
}

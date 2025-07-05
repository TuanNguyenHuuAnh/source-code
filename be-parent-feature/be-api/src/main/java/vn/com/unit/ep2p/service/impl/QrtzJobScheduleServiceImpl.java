package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import vn.com.unit.ep2p.dto.req.QrtzMJobScheduleReq;
import vn.com.unit.ep2p.service.QrtzJobScheduleService;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.service.QrtzMJobScheduleService;

@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzJobScheduleServiceImpl extends AbstractCommonService implements QrtzJobScheduleService{

    @Autowired
    private QrtzMJobScheduleService qrtzMJobScheduleService;
    
    @Override
    public ObjectDataRes<QrtzMJobScheduleDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<QrtzMJobScheduleDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, QrtzMJobSchedule.class, QrtzJobScheduleService.TABLE_QRTZ_JOB_SCHEDULE);
            QrtzMJobScheduleSearchDto searchDto = buildQrtzMJobScheduleSearchDto(commonSearch);
            int totalData = qrtzMJobScheduleService.count(searchDto);
            List<QrtzMJobScheduleDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = qrtzMJobScheduleService.list(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, "QRTZ_LIST_ERROR");
        }
        return resObj;
    }

    @Override
    public QrtzMJobScheduleDto save(QrtzMJobScheduleDto objectDto) throws DetailException {
        return qrtzMJobScheduleService.create(objectDto);
    }

    @Override
    public void delete(Long id) throws DetailException {
        qrtzMJobScheduleService.deleteScheduler(id);
    }

    @Override
    public QrtzMJobScheduleDto detail(Long id) throws DetailException {
        return qrtzMJobScheduleService.detail(id);
    }

    @Override
    public QrtzMJobScheduleDto create(QrtzMJobScheduleReq qrtzMJobScheduleReq) throws DetailException {
        QrtzMJobScheduleDto requset = objectMapper.convertValue(qrtzMJobScheduleReq, QrtzMJobScheduleDto.class);
        return qrtzMJobScheduleService.create(requset);
    }

    @Override
    public QrtzMJobScheduleDto update(QrtzMJobScheduleReq qrtzMJobScheduleReq) throws DetailException {
        QrtzMJobScheduleDto requset = objectMapper.convertValue(qrtzMJobScheduleReq, QrtzMJobScheduleDto.class);
        return qrtzMJobScheduleService.update(requset);
    }

    @Override
    public void runScheduler(Long id) throws DetailException {
        qrtzMJobScheduleService.runScheduler(id);
    }

    @Override
    public void resumeScheduler(Long id) throws DetailException {
        qrtzMJobScheduleService.resumeScheduler(id);
    }

    @Override
    public void pauseScheduler(Long id) throws DetailException {
        qrtzMJobScheduleService.pauseScheduler(id);
    }
    
    private QrtzMJobScheduleSearchDto buildQrtzMJobScheduleSearchDto(MultiValueMap<String, String> commonSearch) {
        QrtzMJobScheduleSearchDto qrtzMJobScheduleSearchDto = new QrtzMJobScheduleSearchDto();
        String jobCode = null != commonSearch.getFirst("jobCode") ? commonSearch.getFirst("jobCode") : AppApiConstant.EMPTY;
        String schedCode = null != commonSearch.getFirst("schedCode") ? commonSearch.getFirst("schedCode") : AppApiConstant.EMPTY;
        String startTime = null != commonSearch.getFirst("startTime") ? commonSearch.getFirst("startTime") : AppApiConstant.EMPTY;
        String endTime = null != commonSearch.getFirst("endTime") ? commonSearch.getFirst("endTime") : AppApiConstant.EMPTY;
        Long status = null != commonSearch.getFirst("status") ? Long.valueOf(commonSearch.getFirst("status")) : null;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        String companyName = null != commonSearch.getFirst("companyName") ? commonSearch.getFirst("companyName") : AppApiConstant.EMPTY;
        Date startDate = null != commonSearch.getFirst("startDate") ? new Date(commonSearch.getFirst("startDate")) : null;
        Date endDate = null != commonSearch.getFirst("endDate") ? new Date(commonSearch.getFirst("endDate")) : null;
        qrtzMJobScheduleSearchDto.setJobCode(jobCode);
        qrtzMJobScheduleSearchDto.setSchedCode(schedCode);
        qrtzMJobScheduleSearchDto.setStartTime(startTime);
        qrtzMJobScheduleSearchDto.setEndTime(endTime);
        qrtzMJobScheduleSearchDto.setStartDate(startDate);
        qrtzMJobScheduleSearchDto.setEndDate(endDate);
        if (status != null) {
            qrtzMJobScheduleSearchDto.setStatus(status.toString());
        }
        qrtzMJobScheduleSearchDto.setCompanyId(companyId);
        qrtzMJobScheduleSearchDto.setCompanyName(companyName);
        return qrtzMJobScheduleSearchDto;
    }

}

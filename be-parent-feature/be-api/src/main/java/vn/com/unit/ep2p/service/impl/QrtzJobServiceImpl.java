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
import vn.com.unit.ep2p.dto.req.QrtzMJobReq;
import vn.com.unit.ep2p.service.QrtzJobService;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;
import vn.com.unit.quartz.job.dto.QrtzMJobSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.service.QrtzMJobService;

@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzJobServiceImpl extends AbstractCommonService implements QrtzJobService{

    @Autowired
    private QrtzMJobService qrtzMJobService;
    
    @Override
    public ObjectDataRes<QrtzMJobDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<QrtzMJobDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, QrtzMJobSchedule.class, QrtzJobService.TABLE_QRTZ_JOB);
            QrtzMJobSearchDto searchDto = buildQrtzMJobSearchDto(commonSearch);
            
            int totalData = qrtzMJobService.count(searchDto);
            List<QrtzMJobDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = qrtzMJobService.list(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, "QRTZ_LIST_ERROR");
        }
        return resObj;
    }

    @Override
    public QrtzMJobDto save(QrtzMJobDto objectDto) throws DetailException {
        return qrtzMJobService.create(objectDto);
    }

    @Override
    public void delete(Long id) throws DetailException {
        boolean delete = qrtzMJobService.delete(id);
        if (!delete) {
            throw new DetailException("DELETE_ERROR");
        }         
    }

    @Override
    public QrtzMJobDto detail(Long id) throws DetailException {
        return qrtzMJobService.detail(id);
    }

    @Override
    public QrtzMJobDto create(QrtzMJobReq qrtzMJobReq) throws DetailException {
        QrtzMJobDto request = objectMapper.convertValue(qrtzMJobReq, QrtzMJobDto.class);
        return qrtzMJobService.create(request);
    }

    @Override
    public QrtzMJobDto update(QrtzMJobReq qrtzMJobReq) throws DetailException {
        QrtzMJobDto request = objectMapper.convertValue(qrtzMJobReq, QrtzMJobDto.class);
        return qrtzMJobService.update(request);
    }
    
    
    private QrtzMJobSearchDto buildQrtzMJobSearchDto(MultiValueMap<String, String> commonSearch) {
        QrtzMJobSearchDto qrtzMJobSearchDto = new QrtzMJobSearchDto();
        String jobCode = null != commonSearch.getFirst("jobCode") ? commonSearch.getFirst("jobCode") : AppApiConstant.EMPTY;
        String jobName = null != commonSearch.getFirst("jobName") ? commonSearch.getFirst("jobName") : AppApiConstant.EMPTY;
        String jobType = null != commonSearch.getFirst("jobType") ? commonSearch.getFirst("jobType") : AppApiConstant.EMPTY;
        String jobClassName = null != commonSearch.getFirst("jobClassName") ? commonSearch.getFirst("jobClassName") : AppApiConstant.EMPTY;
        String storeName = null != commonSearch.getFirst("storeName") ? commonSearch.getFirst("storeName") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        String companyName = null != commonSearch.getFirst("companyName") ? commonSearch.getFirst("companyName") : AppApiConstant.EMPTY;
        qrtzMJobSearchDto.setJobCode(jobCode);
        qrtzMJobSearchDto.setJobName(jobName);
        qrtzMJobSearchDto.setJobType(jobType);
        qrtzMJobSearchDto.setJobClassName(jobClassName);
        qrtzMJobSearchDto.setStoreName(storeName);
        qrtzMJobSearchDto.setCompanyId(companyId);
        qrtzMJobSearchDto.setCompanyName(companyName);
        return qrtzMJobSearchDto;
    }

}

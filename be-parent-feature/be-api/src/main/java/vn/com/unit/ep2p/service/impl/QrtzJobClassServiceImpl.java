/*******************************************************************************
 * Class        ：QrtzJobClassServiceImpl
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
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
import vn.com.unit.ep2p.dto.req.QrtzJobClassReq;
import vn.com.unit.ep2p.service.QrtzJobClassService;
import vn.com.unit.quartz.job.dto.QrtzMJobClassDto;
import vn.com.unit.quartz.job.entity.QrtzMJobClass;
import vn.com.unit.quartz.job.service.QrtzMJobClassService;

/**
 * <p>
 * QrtzJobClassServiceImpl
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzJobClassServiceImpl extends AbstractCommonService implements QrtzJobClassService{
    
    /** The qrtz M job class service. */
    @Autowired
    private QrtzMJobClassService qrtzMJobClassService;

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap, org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<QrtzMJobClassDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<QrtzMJobClassDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, QrtzMJobClass.class, QrtzJobClassService.TABLE_QRTZ_JOB_CLASS);
            
            int totalData = qrtzMJobClassService.count();
            List<QrtzMJobClassDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = qrtzMJobClassService.list(pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, "QRTZ_LIST_ERROR");
        }
        return resObj;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#save(java.lang.Object)
     */
    @Override
    public QrtzMJobClassDto save(QrtzMJobClassDto objectDto) throws DetailException {
        return qrtzMJobClassService.create(objectDto);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#delete(java.lang.Long)
     */
    @Override
    public void delete(Long id) throws DetailException {
        boolean delete = qrtzMJobClassService.delete(id);
        if (!delete) {
            throw new DetailException("DELETE_ERROR");
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#detail(java.lang.Long)
     */
    @Override
    public QrtzMJobClassDto detail(Long id) throws DetailException {
        return qrtzMJobClassService.detail(id);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.QrtzJobClassService#create(vn.com.unit.mbal.api.req.dto.QrtzJobClassReq)
     */
    @Override
    public QrtzMJobClassDto create(QrtzJobClassReq qrtzJobClassReq) throws DetailException {
        QrtzMJobClassDto request = objectMapper.convertValue(qrtzJobClassReq, QrtzMJobClassDto.class);
        return qrtzMJobClassService.create(request);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.QrtzJobClassService#update(vn.com.unit.mbal.api.req.dto.QrtzJobClassReq)
     */
    @Override
    public QrtzMJobClassDto update(QrtzJobClassReq qrtzJobClassReq) throws DetailException {
        QrtzMJobClassDto request = objectMapper.convertValue(qrtzJobClassReq, QrtzMJobClassDto.class);
        return qrtzMJobClassService.update(request);
    }
   
}

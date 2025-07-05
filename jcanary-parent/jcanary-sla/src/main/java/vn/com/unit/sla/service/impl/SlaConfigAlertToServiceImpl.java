/*******************************************************************************
 * Class        ：SlaConfigAlertToServiceImpl
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.constant.SlaExceptionCodeConstant;
import vn.com.unit.sla.dto.SlaConfigAlertToDto;
import vn.com.unit.sla.entity.SlaConfigAlertTo;
import vn.com.unit.sla.repository.SlaConfigAlertToRepository;
import vn.com.unit.sla.service.SlaConfigAlertToService;

/**
 * SlaConfigAlertToServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SlaConfigAlertToServiceImpl extends AbstractSlaService implements SlaConfigAlertToService {
    
    @Autowired 
    private SlaConfigAlertToRepository slaConfigAlertToRepository;
   
    @Override
    public SlaConfigAlertToDto getSlaConfigAlertToDtoById(Long id) {
        if (null != id) {
            SlaConfigAlertTo slaConfigAlertTo = slaConfigAlertToRepository.findOne(id);
            if (null != slaConfigAlertTo) {
                return mapper.convertValue(slaConfigAlertTo, SlaConfigAlertToDto.class);
            }
        }
        return null;
    }

    
    @Override
    public List<SlaConfigAlertToDto> getListByConfigDetailId(Long slaConfigDetailId) {
        return slaConfigAlertToRepository.getListByConfigDetailId(slaConfigDetailId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByConfigDetailId(Long slaConfigDetailId) {
        slaConfigAlertToRepository.deleteByConfigDetailId(slaConfigDetailId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaConfigAlertTo saveSlaConfigAlertTo(SlaConfigAlertTo slaConfigAlertTo) throws DetailException {
        
        SlaConfigAlertTo entity = null;
        if (null != slaConfigAlertTo.getId()) {
            entity = slaConfigAlertToRepository.findOne(slaConfigAlertTo.getId());
        }
        Long userId = SlaConstant.SYSTEM_ID;
        if (null != entity) {
            slaConfigAlertTo.setUpdatedDate(new Date());
            slaConfigAlertTo.setUpdatedId(userId);
            return slaConfigAlertToRepository.update(slaConfigAlertTo);
        } else {
            slaConfigAlertTo.setCreatedDate(new Date());
            slaConfigAlertTo.setCreatedId(userId);
            return slaConfigAlertToRepository.create(slaConfigAlertTo);
        }
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaConfigAlertTo saveSlaConfigAlertToDto(SlaConfigAlertToDto slaConfigAlertToDto) throws DetailException {
        if (null != slaConfigAlertToDto) {
            SlaConfigAlertTo slaConfigAlertTo = mapper.convertValue(slaConfigAlertToDto, SlaConfigAlertTo.class);
            this.saveSlaConfigAlertTo(slaConfigAlertTo);
            slaConfigAlertToDto.setId(slaConfigAlertTo.getId());
            return slaConfigAlertTo;
        } else {
            throw new DetailException(SlaExceptionCodeConstant.E2011001_SLA_CONFIG_ALERT_TO_REQUEST_NULL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cloneSlaConfigAlertTosBySlaConfigDetailId(Long oldSlaConfigDetailId, Long newSlaConfigDetailId) throws DetailException {
        Long userId = SlaConstant.SYSTEM_ID;
        Date sysDate = CommonDateUtil.getSystemDateTime();
        slaConfigAlertToRepository.cloneSlaConfigAlertTosBySlaConfigDetailId(oldSlaConfigDetailId, newSlaConfigDetailId, userId, sysDate);
    }


    @Override
    public DbRepository<SlaConfigAlertTo, Long> initRepo() {
        return slaConfigAlertToRepository;
    }
}

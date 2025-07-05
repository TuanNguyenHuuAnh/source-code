/*******************************************************************************
 * Class        ：SlaConfigServiceImpl
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaExceptionCodeConstant;
import vn.com.unit.sla.dto.SlaConfigDto;
import vn.com.unit.sla.entity.SlaConfig;
import vn.com.unit.sla.repository.SlaConfigRepository;
import vn.com.unit.sla.service.SlaConfigDetailService;
import vn.com.unit.sla.service.SlaConfigService;

/**
 * SlaConfigServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaConfigServiceImpl extends AbstractSlaService implements SlaConfigService {
	
	@Autowired
	private SlaConfigRepository slaConfigRepository;
	
	@Autowired
	private SlaConfigDetailService slaConfigDetailService;
	
	private static final Logger logger = LoggerFactory.getLogger(SlaConfigServiceImpl.class);
	
    @Override
    public SlaConfigDto getSlaConfigDtoById(Long id) throws DetailException {
        if (null == id) {
            logger.error("[SlaConfigServiceImpl] [getSlaConfigDtoById] ConfigId is null");
            throw new DetailException(SlaExceptionCodeConstant.E201701_SLA_VALIDATE_REQUIRED_ERROR, new String[] { "slaConfigId" },
                    true);
        } else {
            return slaConfigRepository.getSlaConfigById(id);
        }
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SlaConfigDto createSlaConfig(SlaConfigDto slaConfigDto) throws DetailException {
	    if (null != slaConfigDto) {
	        SlaConfig slaConfig = null;
	        if (null != slaConfigDto.getId()) {
	            slaConfig = slaConfigRepository.findOne(slaConfigDto.getId());
            }
	        Long userId = UserProfileUtils.getAccountId();
	        if (null != slaConfig) {
	            slaConfig = mapper.convertValue(slaConfigDto, SlaConfig.class);
	            slaConfig.setUpdatedId(userId);
	            slaConfig.setUpdatedDate(new Date());
	            slaConfig = slaConfigRepository.update(slaConfig);
                return mapper.convertValue(slaConfig, SlaConfigDto.class);
	        } else {
	            slaConfig = mapper.convertValue(slaConfigDto, SlaConfig.class);
	            slaConfig.setCreatedId(userId);
	            slaConfig.setCreatedDate(new Date());
	            slaConfig = slaConfigRepository.create(slaConfig);
	            return mapper.convertValue(slaConfig, SlaConfigDto.class); 
	        }
        } else {
            throw new DetailException(SlaExceptionCodeConstant.E201801_SLA_CONFIG_REQUEST_NULL);
        }
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public SlaConfigDto updateSlaConfig(SlaConfigDto slaConfigDto) throws DetailException {
	    if (null != slaConfigDto) {
	        SlaConfig slaConfig = null;
            if (null != slaConfigDto.getId()) {
                slaConfig = slaConfigRepository.findOne(slaConfigDto.getId());
            }
	        if (null != slaConfig) {
	            Long userId = UserProfileUtils.getAccountId();
	            slaConfig = mapper.convertValue(slaConfigDto, SlaConfig.class);
	            slaConfig.setUpdatedId(userId);
	            slaConfig.setUpdatedDate(new Date());
	            slaConfig = slaConfigRepository.update(slaConfig);
	            return mapper.convertValue(slaConfig, SlaConfigDto.class);
	        } else {
	            throw new DetailException(SlaExceptionCodeConstant.E201802_SLA_CONFIG_NOT_FOUND_ID);
	        } 
        } else {
            throw new DetailException(SlaExceptionCodeConstant.E201801_SLA_CONFIG_REQUEST_NULL);
        }
	    
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) throws DetailException {
	    Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();
        boolean result = false;
        SlaConfig slaConfig = slaConfigRepository.findOne(id);
        if (null != slaConfig) {
            slaConfig.setDeletedDate(sysDate);
            slaConfig.setDeletedId(userId);
            slaConfigRepository.update(slaConfig);
            result = true;
        } else {
            logger.error("[SlaConfigServiceImpl] [deleteById] data not found, id: {} ", id);
            throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
        }
        return result;
	}
	
    @Override
    public List<SlaConfigDto> getListConfig(Pageable pageable) {
        return slaConfigRepository.getListConfig(pageable).getContent();
    }

    @Override
    public int countListConfig() {
        return slaConfigRepository.countListConfig();
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaConfigService#saveSlaConfig(vn.com.unit.sla.entity.SlaConfig)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaConfig saveSlaConfig(SlaConfig slaConfig) throws DetailException {
        Long id = slaConfig.getId();
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();
        if (null != id) {
            SlaConfig oldConfig = slaConfigRepository.findOne(id);
            if (null != oldConfig) {
                slaConfig.setUpdatedDate(sysDate);
                slaConfig.setUpdatedId(userId);
                slaConfigRepository.update(slaConfig);
            } else {
                logger.error("[SlaConfigServiceImpl] [saveSlaConfig] data not found, id: {}", id);
                throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
            }
        } else {
            slaConfig.setCreatedDate(sysDate);
            slaConfig.setCreatedId(userId);
            slaConfig.setUpdatedDate(sysDate);
            slaConfig.setUpdatedId(userId);
            slaConfigRepository.create(slaConfig);
        }
        return slaConfig;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaConfigService#saveSlaConfigDto(vn.com.unit.sla.dto.SlaConfigDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaConfigDto saveSlaConfigDto(SlaConfigDto slaConfigDto) throws DetailException {
        SlaConfig slaConfig = mapper.convertValue(slaConfigDto, SlaConfig.class);
        this.saveSlaConfig(slaConfig);
        slaConfigDto.setId(slaConfig.getId());
        return slaConfigDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaConfigDto cloneSlaConfigDtoById(Long id) throws DetailException {
        SlaConfigDto oldSlaConfigDto = slaConfigRepository.getSlaConfigById(id);
        SlaConfigDto newSlaConfigDto = mapper.convertValue(oldSlaConfigDto, SlaConfigDto.class);
        newSlaConfigDto.setId(null);
        newSlaConfigDto = this.saveSlaConfigDto(oldSlaConfigDto);
        slaConfigDetailService.cloneSlaConfigDetailsBySlaConfigId(id, newSlaConfigDto.getId());
        return newSlaConfigDto;
    }

    @Override
    public DbRepository<SlaConfig, Long> initRepo() {
        return slaConfigRepository;
    }
}

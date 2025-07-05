/*******************************************************************************
 * Class        ：SlaServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.sla.service.SlaService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaConfigDto;
import vn.com.unit.sla.service.SlaConfigService;

/**
 * <p>
 * SlaServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaServiceImpl implements SlaService {

    @Autowired
    private SlaConfigService slaConfigService;
    
    @Override
    public Long cloneSlaConfig(Long slaConfigId) throws DetailException {
        Long resultId = null;
        if(null != slaConfigId) {
            SlaConfigDto slaConfigDto = slaConfigService.cloneSlaConfigDtoById(slaConfigId);
            resultId = slaConfigDto.getId();
        }
        return resultId;
    }

}

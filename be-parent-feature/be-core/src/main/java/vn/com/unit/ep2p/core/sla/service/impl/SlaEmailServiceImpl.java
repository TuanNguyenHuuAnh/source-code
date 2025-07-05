/*******************************************************************************
 * Class        ：SlaEmailServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.sla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.sla.email.dto.SlaEmailDto;
import vn.com.unit.sla.email.dto.SlaEmailResultDto;
import vn.com.unit.sla.email.service.SlaEmailService;

/**
 * <p>
 * SlaEmailServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaEmailServiceImpl extends AbstractCommonService implements SlaEmailService{
    
    /** The jca email service. */
    @Autowired
    private JcaEmailService jcaEmailService;
    
    /**
     * <p>
     * Send email.
     * </p>
     *
     * @author TrieuVD
     * @param slaEmailDto
     *            type {@link SlaEmailDto}
     * @return {@link SlaEmailResultDto}
     */
    @Override
    public SlaEmailResultDto sendEmail(SlaEmailDto slaEmailDto) {
        JcaEmailDto jcaEmailDto = objectMapper.convertValue(slaEmailDto, JcaEmailDto.class);
        EmailResultDto responseEmailDto = jcaEmailService.sendEmail(jcaEmailDto);
        SlaEmailResultDto result = objectMapper.convertValue(responseEmailDto, SlaEmailResultDto.class);
        return result;
    }

}

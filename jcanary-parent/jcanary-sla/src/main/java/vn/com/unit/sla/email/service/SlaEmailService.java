/*******************************************************************************
 * Class        ：SlaEmailService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.email.service;

import vn.com.unit.sla.email.dto.SlaEmailDto;
import vn.com.unit.sla.email.dto.SlaEmailResultDto;

/**
 * SlaEmailService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaEmailService {

    /**
     * sendEmail.
     *
     * @param slaEmailDto
     *            type SlaEmailDto
     * @return SlaEmailResultDto
     * @author TrieuVD
     */
    public SlaEmailResultDto sendEmail(SlaEmailDto slaEmailDto);
}

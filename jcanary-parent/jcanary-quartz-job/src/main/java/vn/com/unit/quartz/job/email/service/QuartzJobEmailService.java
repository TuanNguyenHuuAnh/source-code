/*******************************************************************************
 * Class        ：QuartzJobEmailService
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：khadm
 * Change log   ：2021/01/27：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.email.service;

import java.util.Map;

import vn.com.unit.quartz.job.email.dto.QuartzEmailDto;
import vn.com.unit.quartz.job.email.dto.QuartzJobEmailResult;


/**
 * <p>
 * QuartzJobEmailService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QuartzJobEmailService {
    
    /**
     * <p>
     * Send email.
     * </p>
     *
     * @author khadm
     * @param quartzEmailDto
     *            type {@link QuartzEmailDto}
     * @param configEmail
     *            type {@link Map<String,String>}
     * @return {@link QuartzJobEmailResult}
     */
    public QuartzJobEmailResult sendEmail(QuartzEmailDto quartzEmailDto);
}

/*******************************************************************************
 * Class        ：EmailService
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.service;

import java.util.Map;

import vn.com.unit.common.dto.AbstractEmailDto;
import vn.com.unit.common.dto.EmailResultDto;

/**
 * EmailService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface EmailService {
   
    /**
     * <p>
     * Send email.
     * </p>
     *
     * @param emailDto
     *            type {@link AbstractEmailDto}
     * @param configMail
     *            type {@link Map<String,String>}
     * @return {@link EmailResultDto}
     * @author TrieuVD
     */
    public EmailResultDto sendEmail(AbstractEmailDto emailDto, Map<String, String> configMail);
}

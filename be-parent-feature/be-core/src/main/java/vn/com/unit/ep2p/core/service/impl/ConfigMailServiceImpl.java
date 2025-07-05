/*******************************************************************************
 * Class        ：ConfigMailServiceImpl
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonPasswordUtil;
import vn.com.unit.core.service.JcaConfigMailService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.smtp.mail.constant.SmtpMailConstant;

/**
 * ConfigMailServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ConfigMailServiceImpl implements JcaConfigMailService {

    @Autowired
    @Qualifier("appSystemConfigServiceImpl")
    private JcaSystemConfigService jcaSystemConfigService;

    public static final String EMAIL_HOST = "EMAIL_HOST";
    public static final String EMAIL_DEFAULT = "EMAIL_DEFAULT";
    public static final String EMAIL_PASSWORD = "EMAIL_PASSWORD";
    public static final String SEND_EMAIL_TYPE_DEFAULT = "SEND_EMAIL_TYPE_DEFAULT";
    public static final String REPO_URL_ATTACH_FILE = "REPO_URL_ATTACH_FILE";
    public static final String EMAIL_PORT = "EMAIL_PORT";
    public static final String EMAIL_PORT_INBOX = "EMAIL_PORT_INBOX";
    public static final String EMAIL_DEFAULT_NAME = "EMAIL_DEFAULT_NAME";

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.JcaConfigMailService#getConfigMailByCompanyId(java.
     * lang.Long)
     */
    @Override
    public Map<String, String> getConfigMailByCompanyId(Long companyId) {

        Map<String, String> configMail = new HashMap<String, String>();
        // get config from system config
        if (companyId == null) {
            companyId = 1L;
        }

        // config mail
        final String emailHost = jcaSystemConfigService.getValueByKey(EMAIL_HOST, companyId);
        final int emailPort = Integer.parseInt(jcaSystemConfigService.getValueByKey(EMAIL_PORT, companyId));
        final String passMail = CommonPasswordUtil
                .decryptString(jcaSystemConfigService.getValueByKey(EMAIL_PASSWORD, companyId));
        final String senderAddress = jcaSystemConfigService.getValueByKey(EMAIL_DEFAULT, companyId);

        // TODO
        configMail.put(SmtpMailConstant.SMTP_HOST, emailHost);
        configMail.put(SmtpMailConstant.SMTP_PORT, String.valueOf(emailPort));
        configMail.put(SmtpMailConstant.SMTP_SENDER_ADDRESS, senderAddress);
        configMail.put(SmtpMailConstant.SMTP_PASS, passMail);
        configMail.put(SmtpMailConstant.SMTP_SSL_FLAG, SmtpMailConstant.STR_FALSE);

        configMail.put(SmtpMailConstant.SMTP_HEADER_CONTENT_TYPE, "text/html;charset=UTF-8");
        configMail.put(SmtpMailConstant.SMTP_HEADER_FORMAT, "text/html;charset=UTF-8");
        configMail.put(SmtpMailConstant.SMTP_HEADER_ENCODING, "8bit");
        configMail.put(SmtpMailConstant.SMTP_HEADER_CONTENT_TYPE, "text/html;charset=UTF-8");

        return configMail;
    }

}

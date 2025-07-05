/*******************************************************************************
 * Class        ：QrtzMJobReq
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Get url.
 * </p>
 *
 * @author khadm
 * @return {@link String}
 */
@Getter

/**
 * <p>
 * Sets the url.
 * </p>
 *
 * @author khadm
 * @param url
 *            the new url
 */
@Setter
public class QrtzMJobReq {
    
    /** The id. */
    private Long id;

    /** The job code. */
    private String jobCode;

    /** The job name. */
    private String jobName;

    /** The job group. */
    private String jobGroup;

    /** The job class name. */
    private String jobClassName;

    /** The description. */
    private String description;

    /** The validflag. */
    private Long validflag;

    /** The job type. */
    private Long jobType;

    /** The send notification. */
    private Boolean sendNotification;
    
    /** The send status. */
    private String sendStatus;

    /** The email template. */
    private Long emailTemplate;

    /** The template file name. */
    private String templateFileName;

    /** The recipient address. */
    private String recipientAddress;
    
    /** The recipient name. */
    private String recipientName;

    /** The bcc address. */
    private String bccAddress;

    /** The cc address. */
    private String ccAddress;

    /** The subject. */
    private String subject;

    /** The company id. */
    private Long companyId;
    
    /** The url. */
    private String url;
}

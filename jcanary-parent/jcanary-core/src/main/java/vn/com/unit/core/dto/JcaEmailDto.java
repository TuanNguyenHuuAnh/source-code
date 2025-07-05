/*******************************************************************************
 * Class        ：JcaEmailDto
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractEmailDto;

/**
 * JcaEmailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class JcaEmailDto extends AbstractEmailDto {

    private Long id;
    private String senderAddress;
    private String sendEmailType;
    private String toString;
    private String ccString;
    private String bccString;
    private Date sendDate;
    private String sendStatus;
    private Long accountId;
    private Long companyId;
    private Long departmentId;
	private String statusSendMail;
	private Long emailId;

	private Integer numberAttachFile;

	private String uuidEmail;

	private String receiveAddress;

}

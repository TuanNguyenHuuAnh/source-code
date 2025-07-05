/*******************************************************************************
 * Class        EmailSearchDto
 * Created date 2018/08/22
 * Lasted date  2018/08/22
 * Author       phatvt
 * Change log   2018/08/2201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.dto.AbstractCompanyDto;

/**
 * EmailSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Getter
@Setter
public class EmailSearchDto extends AbstractCompanyDto {

    /** fieldSearch */
    private String fieldSearch;
    /** fieldValues */
    private String fieldValues;
    private String receiveAddress;
    private String senderAddress;
    private String subject;
    private Date fromDate;
    private Date toDate;
    private String sendStatus;
    private String contentEmail;
}

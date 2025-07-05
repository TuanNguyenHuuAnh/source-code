/*******************************************************************************
 * Class        ：AbstractEmailDto
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * AbstractEmailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public abstract class AbstractEmailDto {
    private List<String> toAddress;
    private List<String> ccAddress;
    private List<String> bccAddress;
    private String subject;
    private String emailContent;
    private String contentType;
    private List<AttachFileEmailDto> attachFile;
}

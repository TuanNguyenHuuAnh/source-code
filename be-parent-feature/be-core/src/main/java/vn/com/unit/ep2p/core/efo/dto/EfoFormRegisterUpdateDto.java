/*******************************************************************************
 * Class        ：EfoFormRegisterUpdateDto
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：taitt
 * Change log   ：2020/12/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * EfoFormRegisterUpdateDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class EfoFormRegisterUpdateDto extends EfoFormRegisterDto{

    private Long companyId;
    private String companyName;
    private MultipartFile file;
    private Long categoryId;
    private String businessCode;
    private String functionCode;
    private String description;
}

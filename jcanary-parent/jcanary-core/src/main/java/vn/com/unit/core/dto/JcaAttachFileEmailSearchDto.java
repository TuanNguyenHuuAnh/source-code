/*******************************************************************************
 * Class        ：JcaAttachFileSearchDto
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JcaAttachFileSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class JcaAttachFileEmailSearchDto {
    
    private Long companyId;
    private String attachType;
    private Long referenceId;
    private String referenceKey;
    private String fileName;
}

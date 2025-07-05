/*******************************************************************************
 * Class        ：JcaAttachFileDto
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AttachFileEmailDto;

/**
 * JcaAttachFileDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class JcaAttachFileEmailDto extends AttachFileEmailDto {

    private Long attachFileId;
    private Long companyId;
    private Long repositoryId;
    private String filePath;
    private Long referenceId;
    private String referenceKey;
    private String attachType;
    private String uuidEmail;
}

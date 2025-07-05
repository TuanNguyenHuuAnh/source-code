/*******************************************************************************
 * Class        ：FileUploadResult
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：tantm
 * Change log   ：2020/07/21：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * AbstractUploadResult
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public abstract class AbstractUploadResult {

    private Long repositoryId;
    private String filePath;
    private String fileName;

    private boolean success;
}

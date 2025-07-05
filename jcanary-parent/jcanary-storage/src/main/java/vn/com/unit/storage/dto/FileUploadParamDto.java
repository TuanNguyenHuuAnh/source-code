/*******************************************************************************
 * Class        ：FileUploadParam
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：tantm
 * Change log   ：2020/07/21：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.dto;

import java.nio.file.Path;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractUploadParam;

/**
 * 
 * FileUploadParam
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class FileUploadParamDto extends AbstractUploadParam {

    private Long repositoryId;
    private String mimeType;
    private boolean major;
    private String subFilePathWithRule;
    private String urlUpload;
    private String nameFile;
    private String extension;
    private String fullFileName;
    private Path path;

}

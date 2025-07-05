/*******************************************************************************
 * Class        ：UserGuideDto
 * Created date ：2019/11/12
 * Lasted date  ：2019/11/12
 * Author       ：taitt
 * Change log   ：2019/11/12：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * UserGuideDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class JcaUserGuideDto {

    private Long id;
    private Long companyId;
    private String filePath;
    private String fileName;
    private Long langId;
    private String langCode;
    private Long fileRepoId;
    private String appCode;
    
    private List<JcaUserGuideDto> listUserGuilde;
    private MultipartFile fileUserGuide;
    private File fileUserGuideType;
    private String filePathUserGuide;
    private String fileNameUserGuide; 
    private String urlRedirect;

}

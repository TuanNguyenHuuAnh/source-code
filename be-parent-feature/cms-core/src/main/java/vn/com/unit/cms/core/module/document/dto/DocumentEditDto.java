/*******************************************************************************
 * Class        ：DocumentEditDto
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/20
 * Author       ：TaiTM
 * Change log   ：2017/04/20：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;

/**
 * DocumentEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class DocumentEditDto extends CmsCommonEditDto {

    private Long categoryId;

    private String categoryName;

    private int currentVersion;

    private boolean privateReading;

    private boolean forDownload;

    private String physicalFileName;

    private Long fileId;
    
    private String fileName;

    private String imageUrl;

    private String imageTempUrl;

    private Double fileSize;

    private String fileType;

    private List<DocumentLanguageDto> listLanguage;

    private List<DocumentVersionDto> versions;

    private boolean enableSharing;

    private String typeName;

    private String strSearchTag;

    private List<String> lstSearchTag;

    private String tempFileUrl;

    private boolean viewEnabled;

    private String linkAlias;

    private String docComment;

    private String note;
    
    private String channel;
    
    private List<String> channelList;
}

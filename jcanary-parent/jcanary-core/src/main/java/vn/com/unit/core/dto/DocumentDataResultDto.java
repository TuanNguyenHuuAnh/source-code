/*******************************************************************************
 * Class        ：DocumentDataResultDto
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：taitt
 * Change log   ：2021/01/21：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.utils.CommonDateUtil;

/**
 * DocumentDataResultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class DocumentDataResultDto extends AbstractDocumentDto{

    private String submittedName;
    private Date submittedDate;
    private String submittedOrgName;
    private String submittedPosName;
    
    private String assignedName;
    private Date assignedDate;
    private String assignedOrgName;
    private String assignedPosName;
    
    private String stepCode;
    
    private String priorityName;
    private String statusName;
    private String processStatusName;
    
    private String orgName;
    
    private String staffName;
    private Date createdDate; 
    
    private Long mainFileId;
    private String mainFileNameView;
    private Long mainFileMajorVersion;
    private Long mainFileMinorVersion;
    
    private String formName;
    private String ozFilePath;
    
    public String getCreatedDate() {
        if (null != createdDate) {
            return CommonDateUtil.formatDateToString(createdDate, CommonDateUtil.YYYYMMDDHHMMSS);
        }
        return null;
    }
    
    public String getSubmittedDate() {
        if (null != submittedDate) {
            return CommonDateUtil.formatDateToString(submittedDate, CommonDateUtil.YYYYMMDDHHMMSS);
        }
        return null;
    }
    
}

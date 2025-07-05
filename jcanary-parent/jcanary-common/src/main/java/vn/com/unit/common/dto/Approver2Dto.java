/*******************************************************************************
 * Class        ：Approver2Dto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：tantm
 * Change log   ：2021/01/20：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Approver2Dto {

    /** The approver group name. */
    private String approverGroupName;
    
    /** The department name. */
    private String departmentName;
    
    /** The approver name. */
    private String approverName;
    
    /** The title name. */
    private String titleName;
    
    /** The comment. */
    private String comment;
    
    /** The signature image path. */
    private String signatureImagePath;
    
    /** The approve time. */
    private String approveTime;
    
    /** The is signed. */
    private String isSigned;
    
    /** The url. */
    private String url;
    
    /** The doc url. */
    private String docUrl;
    
    /** The doc name. */
    private String docName;
    
    /** The upload date. */
    private String uploadDate;

}

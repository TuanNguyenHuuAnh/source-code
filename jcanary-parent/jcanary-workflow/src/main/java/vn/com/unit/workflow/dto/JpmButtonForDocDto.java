/*******************************************************************************
 * Class        ：JpmButtonForDocDto
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：tantm
 * Change log   ：2020/11/18：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * JpmButtonForDocDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class JpmButtonForDocDto {

    private Long id;

    private String buttonCode;

    private String buttonText;

    private String buttonValue;

    private String buttonClass;

    private String buttonType;

    private Long orders;
    
    private boolean saveForm;
    private boolean saveEform;
    private boolean authenticate;
    private boolean signed;
    private boolean exportPdf;
    private boolean showHistory;
    private boolean fieldToEform;
    private boolean deleted;
    private String permissionCode;
    private String buttonIcon;
    private String buttonNamePassive;
    private boolean useClaimButton;
    
    /** coreTaskId, docId, */
    private String coreTaskId;
    private Long docId;

}

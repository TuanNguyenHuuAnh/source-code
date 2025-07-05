/*******************************************************************************
 * Class        ：StepActionEnum
 * Created date ：2018/10/18
 * Lasted date  ：2018/10/18
 * Author       ：hand
 * Change log   ：2018/10/18：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * StepActionEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum StepActionEnum {

    // Save draft
    SAVE("prc-btn-save-id", "process.action.save"),

    // Waiting for approval
    SUBMIT("prc-btn-submit-id", "process.action.submit"),

    // Approved
    APPROVE("prc-btn-approval-id", "process.action.approve"),

    // Rejected
    REJECT("prc-btn-reject-id", "process.action.reject"),

    // Rejected
    CANCEL("prc-btn-cancel-id", "process.action.cancel"),

    RETURN("prc-btn-return-id", "process.action.return"),
    
    PUBLISH("prc-btn-publish-id", "process.action.publish");
    
    private String code;

    private String name;

    private StepActionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}

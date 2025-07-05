/*******************************************************************************
 * Class        ：AuthorityCommentDto
 * Created date ：2019/11/30
 * Lasted date  ：2019/11/30
 * Author       ：trieuvd
 * Change log   ：2019/11/30：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto.authority;


/**
 * AuthorityCommentDto
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public class AuthorityCommentDto {
    private Long id;
    private Long businessId;
    private Long processDeployId;
    private Long functionId;
    private Long stepDeployId;
    private String stepName;
    private boolean commentFlag;
    private boolean opinionFlag;
    
    /**
     * Get id
     * @return Long
     * @author trieuvd
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  trieuvd
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get businessId
     * @return Long
     * @author trieuvd
     */
    public Long getBusinessId() {
        return businessId;
    }
    
    /**
     * Set businessId
     * @param   businessId
     *          type Long
     * @return
     * @author  trieuvd
     */
    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
    
    /**
     * Get processDeployId
     * @return Long
     * @author trieuvd
     */
    public Long getProcessDeployId() {
        return processDeployId;
    }
    
    /**
     * Set processDeployId
     * @param   processDeployId
     *          type Long
     * @return
     * @author  trieuvd
     */
    public void setProcessDeployId(Long processDeployId) {
        this.processDeployId = processDeployId;
    }
    
    /**
     * Get functionId
     * @return Long
     * @author trieuvd
     */
    public Long getFunctionId() {
        return functionId;
    }
    
    /**
     * Set functionId
     * @param   functionId
     *          type Long
     * @return
     * @author  trieuvd
     */
    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }
    
    /**
     * Get stepDeployId
     * @return Long
     * @author trieuvd
     */
    public Long getStepDeployId() {
        return stepDeployId;
    }
    
    /**
     * Set stepDeployId
     * @param   stepDeployId
     *          type Long
     * @return
     * @author  trieuvd
     */
    public void setStepDeployId(Long stepDeployId) {
        this.stepDeployId = stepDeployId;
    }
    
    /**
     * Get stepName
     * @return String
     * @author trieuvd
     */
    public String getStepName() {
        return stepName;
    }
    
    /**
     * Set stepName
     * @param   stepName
     *          type String
     * @return
     * @author  trieuvd
     */
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    /**
     * Get commentFlag
     * @return boolean
     * @author trieuvd
     */
    public boolean isCommentFlag() {
        return commentFlag;
    }

    /**
     * Set commentFlag
     * @param   commentFlag
     *          type boolean
     * @return
     * @author  trieuvd
     */
    public void setCommentFlag(boolean commentFlag) {
        this.commentFlag = commentFlag;
    }

    /**
     * Get opinionFlag
     * @return boolean
     * @author trieuvd
     */
    public boolean isOpinionFlag() {
        return opinionFlag;
    }

    /**
     * setOpinionFlag
     * @param opinionFlag
     * @author trieuvd
     */
    public void setOpinionFlag(boolean opinionFlag) {
        this.opinionFlag = opinionFlag;
    }
}

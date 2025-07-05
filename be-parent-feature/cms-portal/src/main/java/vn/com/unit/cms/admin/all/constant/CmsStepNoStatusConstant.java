/*******************************************************************************
 * Class        StepNoStatusConstant
 * Created date 2018/08/02
 * Lasted date  2018/08/02
 * Author       VinhLT
 * Change log   2018/08/0201-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.constant;

import org.springframework.stereotype.Component;

/**
 * StepNoStatusConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
@Component("cmsStepNoStatusConstant")
public class CmsStepNoStatusConstant {

    /** STEP SAVE DRAFT */
    public static final Integer STEP_DRAFT = 1;

    /** STEP REJECTED */
    public static final Integer STEP_REJECTED = 98;

    /** STEP APPROVED */
    public static final Integer STEP_APPROVED = 99;

    /** STEP CANCEL */
    public static final Integer STEP_CANCEL = 100;

}

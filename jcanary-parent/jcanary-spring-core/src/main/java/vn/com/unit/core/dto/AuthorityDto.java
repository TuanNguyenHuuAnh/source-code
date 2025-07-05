/*******************************************************************************
 * Class        ：AuthorityDto
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：vinhlt
 * Change log   ：2021/01/28：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.dts.utils.DtsStringUtil;

/**
 * AuthorityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@Getter
@Setter
public class AuthorityDto {

    // Dto for entity
    private long id;
    private Long roleId;
    private Long itemId;
    private String accessFlag;

    // Dto for get role
    private String functionName;
    private String functionCode;
    private String functionType;
    private String statusCode;
    private long processId;

    // Other
    private Long statusAuthorityId;
    private boolean canAccessFlg;
    private boolean canDispFlg;
    private boolean canEditFlg;
    private String authorityType;

    private String menuType;

    // business code
    private String businessCode;

    private String subType;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String findFunction() {
        StringBuilder function = new StringBuilder();
        if (DtsStringUtil.isNotEmpty(functionCode)) {
            function.append(functionCode);
            if (DtsStringUtil.isNotEmpty(accessFlag) && !accessFlag.equals(CommonConstant.STR_ZERO)) {
                if (CommonConstant.STR_ONE.equals(accessFlag)) {
                    function.append(CommonConstant.COLON_DISP);
                } else if (CommonConstant.STR_TWO.equals(accessFlag)) {
                    function.append(CommonConstant.COLON_EDIT);
                } else if (CommonConstant.STR_THREE.equals(accessFlag)) {
                    function.append(CommonConstant.COLON_DELETE);
                }
            }

            if (functionType != null && functionType.equals(CommonConstant.STR_ONE)) {
                if (DtsStringUtil.isNotEmpty(statusCode)) {
                    function.append(CommonConstant.COLON);
                    function.append(statusCode);
                }

                if (processId > 0) {
                    function.append(CommonConstant.COLON);
                    function.append(processId);
                }
            }
        }

        return function.toString();
    }
}

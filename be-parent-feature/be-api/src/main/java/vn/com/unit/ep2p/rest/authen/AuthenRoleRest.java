/*******************************************************************************
 * Class        ：AuthenRest
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.authen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;

/**
 * AuthenRest
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_AUTHEN_ROLE)
@Api(tags = { AppApiConstant.API_AUTHEN_ROLE_AUTHENTICAITON_DESCR })
public class AuthenRoleRest extends AbstractRest {
	@Autowired
	private Db2ApiService db2ApiService;
	
    @PostMapping(AppApiConstant.LIST)
    @ApiOperation("Get is item role")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse authenticationToLogin() {
        long start = System.currentTimeMillis();

        try {
        	// SR14148 check role of iTrust Portal
        	List<String> authorities = UserProfileUtils.getListItem();
    		if (authorities.contains("SCR#FE_QLKH_QLQM_ITRUST")) {
    			String itrustFlg = db2ApiService.getITrustFlag(UserProfileUtils.getUserNameLogin());
    			// Khong co quyen
    			if (itrustFlg == null || "0".equals(itrustFlg)) {
    				authorities.remove("SCR#FE_QLKH_QLQM_ITRUST");
    			}
    		}
            return this.successHandler.handlerSuccess(authorities, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
        /** END */
    }

}

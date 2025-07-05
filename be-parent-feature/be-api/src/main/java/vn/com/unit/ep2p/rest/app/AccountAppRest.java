/*******************************************************************************
 * Class        ：AccountAppRest
 * Created date ：2021/02/26
 * Lasted date  ：2021/02/26
 * Author       ：taitt
 * Change log   ：2021/02/26：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.AccountAppUpdateReq;
import vn.com.unit.ep2p.dto.res.AccountInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.AccountAppService;

/**
 * AccountAppRest
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
//@Api(tags = { AppApiConstant.API_APP_COMBOBOX_DESCR })
public class AccountAppRest  extends AbstractRest {

    @Autowired
    private AccountAppService accountAppService;
    
    
    
    @GetMapping("/userProfile")
    @ApiOperation("detail of user profile")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getUserprofile() {
        long start = System.currentTimeMillis();
        try {   
            
            AccountInfoRes resObj = accountAppService.getUserProfile();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    
    @PutMapping("/userProfile")
    @ApiOperation("Update user profile")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse updateAccount(
            @ApiParam(name = "body", value = "Account information to update") @RequestBody AccountAppUpdateReq accountUpdateDtoReq) {
        long start = System.currentTimeMillis();
        try {
        	accountAppService.updateUserProfile(accountUpdateDtoReq);         
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

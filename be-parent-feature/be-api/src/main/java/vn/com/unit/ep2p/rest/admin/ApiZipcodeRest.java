/*******************************************************************************
 * Class        ：AccountOrgRest
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：SonND
 * Change log   ：2020/12/16：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.JcaZipcodeService;

/**
 * AccountOrgRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ZIPCODE)
@Api(tags = {AppApiConstant.API_ADMIN_ACCOUNT_ORG_DESCR})
public class ApiZipcodeRest extends AbstractRest{
	
	@Autowired
	JcaZipcodeService jcazipcodeService;
	
	@PostMapping("/province")
	@ApiOperation("Get all province")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden")})
	public DtsApiResponse getJcaZipcode() {
        long start = System.currentTimeMillis();
        try {
        	List<Select2Dto> resObj = jcazipcodeService.getListProvinceOds();
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	@PostMapping("/province-ods")
	@ApiOperation("Get all province ods")
	@ApiResponses(value = {
			@ApiResponse(code = 200 , message = "Success"),
			@ApiResponse(code = 500 , message = "Internal server error"),
			@ApiResponse(code = 401 , message = "Unauthorized"),
			@ApiResponse(code = 402 , message = "Forbidden")})
	public DtsApiResponse getDb2Zipcode() {
		long start = System.currentTimeMillis();
		try {
			List<Select2Dto> resObj = jcazipcodeService.getListProvinceOds();
			return this.successHandler.handlerSuccess(resObj, start);
		}catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}
}

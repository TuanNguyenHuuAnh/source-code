/*******************************************************************************
 * Class        ：Product
 * Created date ：2023/08/04
 * Lasted date  ：2023/08/04
 * Author       ：thu.thai
 * Change log   ：2023/08/04 hand create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.cms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.product.entity.Product;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiProductService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_PRODUCT)
@Api(tags = { CmsApiConstant.API_CMS_PRODUCT_DESCR })
public class ProductRest extends AbstractRest {
	@Autowired 
	ApiProductService apiProductService ;
	
	@GetMapping("/productList")
    @ApiOperation("get all product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse getProductList(){
		long start = System.currentTimeMillis();
		try {
			List<Product> resObj = apiProductService.getAllProduct();
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
}

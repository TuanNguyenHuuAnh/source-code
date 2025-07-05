/*******************************************************************************
 * Class        ：RoleForCompanyRest
 * Created date ：2020/12/18
 * Lasted date  ：2020/12/18
 * Author       ：ngannh
 * Change log   ：2020/12/18：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.RoleForCompanyAddListReq;
import vn.com.unit.ep2p.dto.res.RoleForCompanyInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.RoleForCompanyService;

/**
 * RoleForCompanyRest
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_ROLE_FOR_COMPANY_DESCR })
public class RoleForCompanyRest extends AbstractRest {

}

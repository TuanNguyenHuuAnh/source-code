/*******************************************************************************
 * Class        ：JpmSvcBoardRest
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.res.JpmSvcBoardRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.JpmSvcBoardService;

/**
 * JpmSvcBoardRest
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
@Api(tags = {AppApiConstant.API_APP_JPM_SVC_BOARD_DESCR})
public class JpmSvcBoardRest extends AbstractRest {
    
    @Autowired
    private JpmSvcBoardService jpmSvcBoardService;
    
    @GetMapping(AppApiConstant.API_APP_JPM_SVC_BOARD)
    @ApiOperation("List of process service board")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024801, message = "Error process get list svc board"),
            @ApiResponse(code = 4024802, message = "Error process get list image for svc board")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "1", dataTypeClass = String.class, paramType = "query"),
            
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") 
     })
    public DtsApiResponse listForm(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JpmSvcBoardRes> resObj = jpmSvcBoardService.svcBoardList(requestParams,pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

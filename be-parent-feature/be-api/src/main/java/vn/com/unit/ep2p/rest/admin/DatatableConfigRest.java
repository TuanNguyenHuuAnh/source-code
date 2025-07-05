/*******************************************************************************
 * Class        ：DatatableConfigRest
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：vinhlt
 * Change log   ：2021/01/27：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.core.entity.JcaDatatableConfig;
import vn.com.unit.core.req.DatatableHeaderReq;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;

/**
 * DatatableConfigRest
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + "/datatable")
@Api(tags = { "Data table config" })
public class DatatableConfigRest extends AbstractRest {
    
    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    /**
     * getHeader
     * @param datatableHeaderReq
     * @return
     * @author vinhlt
     */
    @PostMapping("/header")
    @ApiOperation("get header")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getHeader(
            @ApiParam(name = "body", value = "user id with function code to get config header") @RequestBody DatatableHeaderReq datatableHeaderReq) {
        long start = System.currentTimeMillis();
        try {
            JcaDatatableConfig jcaDatatableConfig = jcaDatatableConfigService.findConfigByRequest(datatableHeaderReq);
            return this.successHandler.handlerSuccess(jcaDatatableConfig != null ? jcaDatatableConfig.getJsonConfig() : null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PostMapping("/header-default")
    @ApiOperation("get header default")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getHeaderDefault(
            @ApiParam(name = "body", value = "user id with function code to get config header default") @RequestBody DatatableHeaderReq datatableHeaderReq) {
        long start = System.currentTimeMillis();
        try {
            JcaDatatableConfig jcaDatatableConfig = jcaDatatableConfigService.findConfigDefaultByRequest(datatableHeaderReq);
            return this.successHandler.handlerSuccess(jcaDatatableConfig != null ? jcaDatatableConfig.getJsonConfig() : null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * addAccount
     * 
     * @param datatableHeaderReq
     * @return
     * @author vinhlt
     */
    @PostMapping("/update-header")
    @ApiOperation("update header")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse addAccount(
            @ApiParam(name = "body", value = "save new or update existing header config") @RequestBody DatatableHeaderReq datatableHeaderReq) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(jcaDatatableConfigService.createOrUpdate(datatableHeaderReq), start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

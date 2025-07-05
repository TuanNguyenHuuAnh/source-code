package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.SlaConfigDetailAddReq;
import vn.com.unit.ep2p.dto.req.SlaConfigDetailUpdateReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.SlaConfigDetailEnterpriseService;
import vn.com.unit.sla.dto.SlaConfigDetailDto;

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_SLA_CONFIG_DETAIL_DESCR })
public class SlaConfigDetailRest extends AbstractRest {

    @Autowired
    private SlaConfigDetailEnterpriseService slaConfigDetailEnterpriseService;

    @PostMapping(AppApiConstant.API_SLA_CONFIG_DETAIL)
    @ApiOperation("Create sla config detail")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse addSlaConfigDetai(
            @ApiParam(name = "body", value = "Sla config detail information to add new") @RequestBody SlaConfigDetailAddReq slaConfigDetailReq) {
        long start = System.currentTimeMillis();
        try {
            SlaConfigDetailDto result = slaConfigDetailEnterpriseService.createSlaConfigDetail(slaConfigDetailReq);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_SLA_CONFIG_DETAIL)
    @ApiOperation("Update sla config detail")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse updateSlaConfigDetai(
            @ApiParam(name = "body", value = "Sla config detail information to update") @RequestBody SlaConfigDetailUpdateReq slaConfigDetailReq) {
        long start = System.currentTimeMillis();
        try {
            SlaConfigDetailDto result = slaConfigDetailEnterpriseService.updateSlaConfigDetail(slaConfigDetailReq);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @DeleteMapping(AppApiConstant.API_SLA_CONFIG_DETAIL)
    @ApiOperation("Delete sla config detail")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024102, message = "Error delete sla config tail") })
    public DtsApiResponse deleteSlaConfigDetai(
            @ApiParam(name = "slaConfigDetailId", value = "Delete sla config detail on system by id", example = "123") @RequestParam("slaConfigDetailId") Long slaConfigDetailId) {
        long start = System.currentTimeMillis();
        try {
            slaConfigDetailEnterpriseService.delete(slaConfigDetailId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_SLA_CONFIG_DETAIL)
    @ApiOperation("List of sla config detail")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 402801, message = "Error process list account"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 4024101, message = "Error get list sla config tail") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = SlaConfigDetailEnterpriseService.SEARCH_SLA_CONFIG_ID, value = "1", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = SlaConfigDetailEnterpriseService.SEARCH_ALERT_TYPE, value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listSlaConfigDetai(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,
            @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<SlaConfigDetailDto> result = slaConfigDetailEnterpriseService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_SLA_CONFIG_DETAIL + "/{slaConfigDetaiId}")
    @ApiOperation("Api provides sla config detail information detail on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            // @ApiResponse(code = 201601, message = "Error id is required"),
            // @ApiResponse(code = 402604, message = "Error process get slaConfig information detail")
    })
    public DtsApiResponse getDetail(
            @ApiParam(name = "slaConfigDetaiId", value = "Get sla config detail information detail on system by id", example = "1", required = true) @PathVariable("slaConfigDetaiId") Long slaConfigDetaiId) {
        long start = System.currentTimeMillis();
        try {
            SlaConfigDetailDto resObj = slaConfigDetailEnterpriseService.detail(slaConfigDetaiId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

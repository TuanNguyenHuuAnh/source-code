/*******************************************************************************
 * Class        ：CalendarTypeRest
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.List;

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
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.CalendarTypeAddReq;
import vn.com.unit.ep2p.dto.req.CalendarTypeUpdateReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.CalendarTypeService;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;

/**
 * CalendarTypeRest
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_CALENDAR_TYPE_DESCR })
public class CalendarTypeRest extends AbstractRest {

    @Autowired
    private CalendarTypeService calendarTypeService;

    @GetMapping(AppApiConstant.API_ADMIN_CALENDAR_TYPE)
    @ApiOperation("Api provides a list CalendarType on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list calendar type") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "orgId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column CALENDAR_TYPE_CODE, CALENDAR_TYPE_NAME, DESCRIPTION"),
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listCalendarType(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,
            @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<SlaCalendarTypeDto> resObj = calendarTypeService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_ADMIN_CALENDAR_TYPE)
    @ApiOperation("Api provides add new calendar type on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402602, message = "Error process add calendar type") })
    public DtsApiResponse addCalendarType(
            @ApiParam(name = "body", value = "Calendar type information to add new") @RequestBody CalendarTypeAddReq reqCalendarTypeAddDto) {
        long start = System.currentTimeMillis();
        try {
            SlaCalendarTypeDto resObj = calendarTypeService.createCalendarType(reqCalendarTypeAddDto);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_ADMIN_CALENDAR_TYPE)
    @ApiOperation("Api provides update information calendar type on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 201601, message = "Error id is required"),
            @ApiResponse(code = 201602, message = "Error calendar type not found"),
            @ApiResponse(code = 402603, message = "Error process update info calendar type") })
    public DtsApiResponse updateCalendarType(
            @ApiParam(name = "body", value = "CalendarType information to update") @RequestBody CalendarTypeUpdateReq reqCalendarTypeUpdateDto) {
        long start = System.currentTimeMillis();
        try {
            calendarTypeService.updateCalendarType(reqCalendarTypeUpdateDto);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_CALENDAR_TYPE + "/{calendarTypeId}")
    @ApiOperation("Api provides CalendarType information detail on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 201601, message = "Error id is required"),
            @ApiResponse(code = 402604, message = "Error process get CalendarType information detail") })
    public DtsApiResponse detailCalendarType(
            @ApiParam(name = "calendarTypeId", value = "Get CalendarType information detail on system by id", example = "1") @PathVariable("calendarTypeId") Long calendarTypeId) {
        long start = System.currentTimeMillis();
        try {
            SlaCalendarTypeDto resObj = calendarTypeService.detail(calendarTypeId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @DeleteMapping(AppApiConstant.API_ADMIN_CALENDAR_TYPE)
    @ApiOperation("Api provides delete CalendarType on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 201601, message = "Error id is required"),
            @ApiResponse(code = 201602, message = "Error calendar type not found"),
            @ApiResponse(code = 402605, message = "Error process delete CalendarType") })
    public DtsApiResponse deleteCalendarType(
            @ApiParam(name = "calendarTypeId", value = "Delete CalendarType on system by id", example = "1") @RequestParam Long calendarTypeId) {
        long start = System.currentTimeMillis();
        try {
            calendarTypeService.delete(calendarTypeId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_CALENDAR_TYPE + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = calendarTypeService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

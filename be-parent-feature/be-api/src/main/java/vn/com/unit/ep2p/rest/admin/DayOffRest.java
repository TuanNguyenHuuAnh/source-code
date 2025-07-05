/*******************************************************************************
 * Class        ：DayOffRest
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：TrieuVD
 * Change log   ：2020/12/16：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.DayOffUpdateReq;
import vn.com.unit.ep2p.dto.res.DayOffListObjectRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.DayOffService;
import vn.com.unit.ep2p.service.impl.DayOffServiceImpl;
import vn.com.unit.sla.dto.SlaCalendarDto;

/**
 * DayOffRest
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_DAY_OFF_DESCR })
public class DayOffRest extends AbstractRest {
    
    @Autowired
    private DayOffService dayOffService;

    @GetMapping(AppApiConstant.API_ADMIN_DAY_OFF)
    @ApiOperation("Api provides a list day off on systems")
    @ApiImplicitParams({
            @ApiImplicitParam(name = DayOffServiceImpl.SEARCH_YEAR, value = "year", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = DayOffServiceImpl.SEARCH_CALENDAR_TYPE_ID, value = "1", required = true, dataTypeClass = String.class, paramType = "query")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list day off") })
    public DtsApiResponse getDayOffList(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams) {
        long start = System.currentTimeMillis();
        try {
            DayOffListObjectRes resObj = dayOffService.getDayOffListBySearchReq(requestParams);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_DAY_OFF + "/{dayOffId}")
    @ApiOperation("Api provides day off information detail on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 201601, message = "Error id is required"),
            @ApiResponse(code = 402604, message = "Error process get day off information detail") })
    public DtsApiResponse detailCalendarType(
            @ApiParam(name = "dayOffId", value = "Get day off information detail on system by id", example = "1") @PathVariable("dayOffId") Long dayOffId) {
        long start = System.currentTimeMillis();
        try {
            SlaCalendarDto resObj = dayOffService.getSlaDayOffDtoById(dayOffId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PutMapping(AppApiConstant.API_ADMIN_DAY_OFF)
    @ApiOperation("Api provides update information day off on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 201601, message = "Error id is required"),
            @ApiResponse(code = 201602, message = "Error calendar type not found"),
            @ApiResponse(code = 402603, message = "Error process update info calendar type") })
    public DtsApiResponse updateCalendarType(
            @ApiParam(name = "body", value = "CalendarType information to update") @RequestBody DayOffUpdateReq reqDayOffUpdateDto) {
        long start = System.currentTimeMillis();
        try {
            dayOffService.updateDateOff(reqDayOffUpdateDto);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

/*******************************************************************************
 * Class        ：AppInBoxRest
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：SonND
 * Change log   ：2021/02/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import vn.com.unit.core.dto.JcaAppInboxDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.res.NotificationInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.NotificationService;

/**
 * AppInBoxRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_APP_NOTIFICATION_DESCR})
public class NotificationRest extends AbstractRest{
	
    public static final String USER_ID = "userId";
    
	@Autowired
	private NotificationService notificationService;
	
	@ApiOperation("Detail of notification")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4025001, message = "Error not found notification"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    @GetMapping(AppApiConstant.API_APP_NOTIFICATION + "/get-detail/{notificationId}")
    public DtsApiResponse getInfoNotification(
            @ApiParam(name = "notificationId", value = "Get notification information by id", example = "1") @PathVariable("notificationId") Long notificationId) {
        long start = System.currentTimeMillis();
        try {
            NotificationInfoRes resObj = notificationService.getNoticationById(notificationId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@ApiOperation("Get all notification")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4025006, message = "Error process get all notification information"),
            @ApiResponse(code = 500, message = "Internal server error"),})
	 @ApiImplicitParams({
         @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
         @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
         @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                 + "Default sort order is ascending. " + "Multiple sort criteria are supported. Sort about:ID, CREATED_ID, CREATED_DATE, UPDATED_ID,UPDATED_DATE. Example: ID,asc. ") 
  })
    @GetMapping(AppApiConstant.API_APP_NOTIFICATION + "/get-all")
    public DtsApiResponse getAllInfoNotification(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JcaAppInboxDto> resObj = notificationService.getAllNotication(requestParams,pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_APP_NOTIFICATION + "/delete/{notificationId}")
    @ApiOperation("Delete notification")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "Success"),
                @ApiResponse(code = 500 , message = "Internal server error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4025001 , message = "Error not found notification"),
                @ApiResponse(code = 4025003 , message = "Error process delete notification"),})
    public DtsApiResponse deleteNotification(@ApiParam(name = "notificationId", value = "Delete notification by id" , example = "1")@PathVariable("notificationId") Long notificationId) {
        long start = System.currentTimeMillis();
        try {
            notificationService.deleteNotificationById(notificationId);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_APP_NOTIFICATION + "/delete-all")
    @ApiOperation("Delete all notification")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "Success"),
                @ApiResponse(code = 500 , message = "Internal server error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4025004 , message = "Error process delete all notification"),})
    public DtsApiResponse deleteAllNotification() {
        long start = System.currentTimeMillis();
        try {
            notificationService.deleteAllNotification();
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@ApiOperation("Read notification")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4025001, message = "Error not found notification"),
            @ApiResponse(code = 4025007, message = "Error process read notification"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    @GetMapping(AppApiConstant.API_APP_NOTIFICATION + "/read")
    public DtsApiResponse readNotification(
            @ApiParam(name = "notificationId", value = "Get notification information by id", example = "1") @RequestParam("notificationId") Long notificationId
            ,@ApiParam(name = "readFlag", value = "Get notification information by id", example = "1") @RequestParam("readFlag") boolean readFlag) {
        long start = System.currentTimeMillis();
        try {
            notificationService.readNotification(notificationId, readFlag);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @ApiOperation("Read all notification")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4025008, message = "Error process read all notification"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    @GetMapping(AppApiConstant.API_APP_NOTIFICATION + "/read-all")
    public DtsApiResponse readAllNotification(@ApiParam(name = "readFlag", value = "Get notification information by id", example = "1") @RequestParam("readFlag") boolean readFlag) {
        long start = System.currentTimeMillis();
        try {
            notificationService.readAllNotification(readFlag);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}

/*******************************************************************************
 * Class        ：QrtzMJobScheduleRest
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
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
import vn.com.unit.ep2p.dto.req.QrtzMJobScheduleReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.QrtzJobScheduleService;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;

/**
 * <p>
 * QrtzMJobScheduleRest
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_QRTZ_JOB_SCHEDULE_DESCR })
public class QrtzMJobScheduleRest extends AbstractRest {

    /** The qrtz job schedule service. */
    @Autowired
    private QrtzJobScheduleService qrtzJobScheduleService;

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobScheduleReq
     *            type {@link QrtzMJobScheduleReq}
     * @return {@link DtsApiResponse}
     */
    @PostMapping(AppApiConstant.API_QRTZ_JOB_SCHEDULE)
    @ApiOperation("Create qrtz job schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse create(
            @ApiParam(name = "body", value = "Qrtz job schedule information to add new") @RequestBody QrtzMJobScheduleReq qrtzMJobScheduleReq) {
        long start = System.currentTimeMillis();
        try {
            QrtzMJobScheduleDto result = qrtzJobScheduleService.create(qrtzMJobScheduleReq);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobScheduleReq
     *            type {@link QrtzMJobScheduleReq}
     * @return {@link DtsApiResponse}
     */
    @PutMapping(AppApiConstant.API_QRTZ_JOB_SCHEDULE)
    @ApiOperation("Update qrtz job schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse update(
            @ApiParam(name = "body", value = "Qrtz job schedule information to update") @RequestBody QrtzMJobScheduleReq qrtzMJobScheduleReq) {
        long start = System.currentTimeMillis();
        try {
            QrtzMJobScheduleDto result = qrtzJobScheduleService.update(qrtzMJobScheduleReq);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * <p>
     * Delete.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @return {@link DtsApiResponse}
     */
    @DeleteMapping(AppApiConstant.API_QRTZ_JOB_SCHEDULE + "/{id}")
    @ApiOperation("Delete qrtz job schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse delete(
            @ApiParam(name = "id", value = "Delete qrtz job schedule on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            qrtzJobScheduleService.delete(id);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * <p>
     * List.
     * </p>
     *
     * @author khadm
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link DtsApiResponse}
     */
    @GetMapping(AppApiConstant.API_QRTZ_JOB_SCHEDULE)
    @ApiOperation("List of qrtz job schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 402801, message = "Error process list account"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobCode", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "schedCode", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse list(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams, @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<QrtzMJobScheduleDto> result = qrtzJobScheduleService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * <p>
     * Detail.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @return {@link DtsApiResponse}
     */
    @GetMapping(AppApiConstant.API_QRTZ_JOB_SCHEDULE + "/{id}")
    @ApiOperation("Detail qrtz job schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse detail(
            @ApiParam(name = "id", value = "Detail qrtz job schedule on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            QrtzMJobScheduleDto result = qrtzJobScheduleService.detail(id);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_QRTZ_JOB_SCHEDULE_RESUM + "/{id}")
    @ApiOperation("Resum qrtz job schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse resum(
            @ApiParam(name = "id", value = "Resum qrtz job schedule on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            qrtzJobScheduleService.resumeScheduler(id);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_QRTZ_JOB_SCHEDULE_PAUSE + "/{id}")
    @ApiOperation("Pause qrtz job schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse pause(
            @ApiParam(name = "id", value = "Pause qrtz job schedule on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            qrtzJobScheduleService.pauseScheduler(id);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_QRTZ_JOB_SCHEDULE_RUN + "/{id}")
    @ApiOperation("Run qrtz job schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse run(
            @ApiParam(name = "id", value = "Run qrtz job schedule on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            qrtzJobScheduleService.runScheduler(id);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

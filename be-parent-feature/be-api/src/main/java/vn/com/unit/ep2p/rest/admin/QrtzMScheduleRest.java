/*******************************************************************************
 * Class        ：QrtzMScheduleRest
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
import vn.com.unit.ep2p.dto.req.QrtzMScheduleReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.QrtzScheduleService;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;

/**
 * <p>
 * QrtzMScheduleRest
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_QRTZ_SCHEDULE_DESCR })
public class QrtzMScheduleRest extends AbstractRest {

    /** The qrtz schedule service. */
    @Autowired
    private QrtzScheduleService qrtzScheduleService;

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMScheduleReq
     *            type {@link QrtzMScheduleReq}
     * @return {@link DtsApiResponse}
     */
    @PostMapping(AppApiConstant.API_QRTZ_SCHEDULE)
    @ApiOperation("Create qrtz schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse create(
            @ApiParam(name = "body", value = "Qrtz schedule information to add new") @RequestBody QrtzMScheduleReq qrtzMScheduleReq) {
        long start = System.currentTimeMillis();
        try {
            QrtzMScheduleDto result = qrtzScheduleService.create(qrtzMScheduleReq);
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
     * @param qrtzMScheduleReq
     *            type {@link QrtzMScheduleReq}
     * @return {@link DtsApiResponse}
     */
    @PutMapping(AppApiConstant.API_QRTZ_SCHEDULE)
    @ApiOperation("Update qrtz schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse update(
            @ApiParam(name = "body", value = "Qrtz schedule information to update") @RequestBody QrtzMScheduleReq qrtzMScheduleReq) {
        long start = System.currentTimeMillis();
        try {
            QrtzMScheduleDto result = qrtzScheduleService.update(qrtzMScheduleReq);
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
    @DeleteMapping(AppApiConstant.API_QRTZ_SCHEDULE + "/{id}")
    @ApiOperation("Delete qrtz schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse delete(
            @ApiParam(name = "id", value = "Delete qrtz schedule on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            qrtzScheduleService.delete(id);
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
    @GetMapping(AppApiConstant.API_QRTZ_SCHEDULE)
    @ApiOperation("List of qrtz schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 402801, message = "Error process list account"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schedCode", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "schedName", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "cronExpression", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "description", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse list(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams, @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<QrtzMScheduleDto> result = qrtzScheduleService.search(requestParams, pageable);
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
    @GetMapping(AppApiConstant.API_QRTZ_SCHEDULE + "/{id}")
    @ApiOperation("Detail qrtz schedule")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse detail(
            @ApiParam(name = "id", value = "Detail qrtz schedule on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            QrtzMScheduleDto result = qrtzScheduleService.detail(id);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

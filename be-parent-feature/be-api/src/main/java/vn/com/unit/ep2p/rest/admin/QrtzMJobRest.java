/*******************************************************************************
 * Class        ：QrtzMJobRest
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
import vn.com.unit.ep2p.dto.req.QrtzMJobReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.QrtzJobService;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;

/**
 * <p>
 * QrtzMJobRest
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_QRTZ_JOB_DESCR })
public class QrtzMJobRest extends AbstractRest {

    /** The qrtz job service. */
    @Autowired
    private QrtzJobService qrtzJobService;

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobReq
     *            type {@link QrtzMJobReq}
     * @return {@link DtsApiResponse}
     */
    @PostMapping(AppApiConstant.API_QRTZ_JOB)
    @ApiOperation("Create qrtz job")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse create(@ApiParam(name = "body", value = "Qrtz job information to add new") @RequestBody QrtzMJobReq qrtzMJobReq) {
        long start = System.currentTimeMillis();
        try {
            QrtzMJobDto result = qrtzJobService.create(qrtzMJobReq);
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
     * @param qrtzMJobReq
     *            type {@link QrtzMJobReq}
     * @return {@link DtsApiResponse}
     */
    @PutMapping(AppApiConstant.API_QRTZ_JOB)
    @ApiOperation("Update qrtz job")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse update(@ApiParam(name = "body", value = "Qrtz job information to update") @RequestBody QrtzMJobReq qrtzMJobReq) {
        long start = System.currentTimeMillis();
        try {
            QrtzMJobDto result = qrtzJobService.update(qrtzMJobReq);
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
    @DeleteMapping(AppApiConstant.API_QRTZ_JOB + "/{id}")
    @ApiOperation("Delete qrtz job")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse delete(
            @ApiParam(name = "id", value = "Delete qrtz job on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            qrtzJobService.delete(id);
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
    @GetMapping(AppApiConstant.API_QRTZ_JOB)
    @ApiOperation("List of qrtz job")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 402801, message = "Error process list account"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobCode", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "jobName", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "jobType", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "jobClassName", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "storeName", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse list(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams, @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<QrtzMJobDto> result = qrtzJobService.search(requestParams, pageable);
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
    @GetMapping(AppApiConstant.API_QRTZ_JOB + "/{id}")
    @ApiOperation("Detail qrtz job")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse detail(
            @ApiParam(name = "id", value = "Detail qrtz job on system by id", example = "123") @PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        try {
            QrtzMJobDto result = qrtzJobService.detail(id);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

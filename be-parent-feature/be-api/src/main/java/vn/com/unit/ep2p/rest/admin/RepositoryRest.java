/*******************************************************************************
 * Class        ：RepositoryRest
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
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
import vn.com.unit.ep2p.dto.req.RepositoryAddReq;
import vn.com.unit.ep2p.dto.req.RepositoryUpdateReq;
import vn.com.unit.ep2p.dto.res.RepositoryInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.FileRepositoryService;
import vn.com.unit.storage.dto.JcaRepositoryDto;

/**
 * 
 * RepositoryRest
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_REPOSITORY_DESCR })
public class RepositoryRest extends AbstractRest {

    @Autowired
    private FileRepositoryService fileRepositoryService;

    @GetMapping(AppApiConstant.API_ADMIN_REPOSITORY)
    @ApiOperation("List of repositorys")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022401, message = "Error process list repository") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "keySearch", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column CODE, NAME, PHYSICAL_PATH, SUB_FOLDER_PATH"),

            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listRepository(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,
            @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JcaRepositoryDto> resObj = fileRepositoryService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_ADMIN_REPOSITORY)
    @ApiOperation("Create repository")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022402, message = "Error process add repository") })
    public DtsApiResponse addRepository(
            @ApiParam(name = "body", value = "Repository information to add new") @RequestBody RepositoryAddReq repositoryAddDtoReq) {
        long start = System.currentTimeMillis();
        try {
            JcaRepositoryDto resObj = fileRepositoryService.create(repositoryAddDtoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_ADMIN_REPOSITORY)
    @ApiOperation("Update repository")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022403, message = "Error process update info repository"),
            @ApiResponse(code = 4022404, message = "Error repository not found") })
    public DtsApiResponse updateRepository(
            @ApiParam(name = "body", value = "Repository information to update") @RequestBody RepositoryUpdateReq repositoryUpdateDtoReq) {
        long start = System.currentTimeMillis();
        try {
            fileRepositoryService.update(repositoryUpdateDtoReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @DeleteMapping(AppApiConstant.API_ADMIN_REPOSITORY)
    @ApiOperation("Delete repository")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022405, message = "Error process delete repository"),
            @ApiResponse(code = 4022404, message = "Error repository not found") })
    public DtsApiResponse deleteRepository(
            @ApiParam(name = "id", value = "Delete repository on system by id", example = "123") @RequestParam("id") Long repositoryId) {
        long start = System.currentTimeMillis();
        try {
            fileRepositoryService.delete(repositoryId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_REPOSITORY + "/{repositoryId}")
    @ApiOperation("Detail of repository")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022406, message = "Error process get repository information detail"),
            @ApiResponse(code = 4022404, message = "Error repository not found") })
    public DtsApiResponse detailRepository(
            @ApiParam(name = "repositoryId", value = "Get repository information detail on system by id", example = "123") @PathVariable("repositoryId") Long repositoryId) {
        long start = System.currentTimeMillis();
        try {
            RepositoryInfoRes resObj = fileRepositoryService.getRepositoryInfoResById(repositoryId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_REPOSITORY + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = fileRepositoryService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

}

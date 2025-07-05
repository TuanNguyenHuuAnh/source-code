/*******************************************************************************
 * Class        ：CategoryRest
 * Created date ：2020/12/17
 * Lasted date  ：2020/12/17
 * Author       ：taitt
 * Change log   ：2020/12/17：01-00 taitt create a new
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
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.CategoryAddReq;
import vn.com.unit.ep2p.dto.req.CategoryUpdateReq;
import vn.com.unit.ep2p.dto.res.CategoryInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.CategoryService;



/**
 * <p>
 * CategoryRest
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_CATEGORY_DESCR })
public class CategoryRest extends AbstractRest {
   
    /** The category service. */
    @Autowired
    private CategoryService categoryService;

    /**
     * <p>
     * List account.
     * </p>
     *
     * @param requestParams
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link DtsApiResponse}
     * @author taitt
     */
    @GetMapping(AppApiConstant.API_ADMIN_CATEGORY)
    @ApiOperation("List of category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022701, message = "Error process list category"),
            @ApiResponse(code = 500, message = "Internal server error"),
            })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "active", value = "true", dataType = "boolean", dataTypeClass = Boolean.class, paramType = "query"),
        @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column NAME, DESCRIPTION"),
        
        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.") 
 })
public DtsApiResponse listAccount(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
    long start = System.currentTimeMillis();
    try {
        ObjectDataRes<EfoCategoryDto> resObj = categoryService.search(requestParams,pageable);
        return this.successHandler.handlerSuccess(resObj, start);
    } catch (Exception ex) {
        return this.errorHandler.handlerException(ex, start);
    }
}

    /**
     * <p>
     * Adds the category.
     * </p>
     *
     * @param categoryAddReq
     *            type {@link CategoryAddReq}
     * @return {@link DtsApiResponse}
     * @author taitt
     */
    @PostMapping(AppApiConstant.API_ADMIN_CATEGORY)
    @ApiOperation("Create category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022702, message = "Error process add category"),
            @ApiResponse(code = 500, message = "Internal server srror"),})
    public DtsApiResponse addCategory(
            @ApiParam(name = "body", value = "Category information to add new") @RequestBody CategoryAddReq categoryAddReq) {
        long start = System.currentTimeMillis();
        try {
            EfoCategoryDto resObj = categoryService.create(categoryAddReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * <p>
     * Update category.
     * </p>
     *
     * @param categoryUpdateReq
     *            type {@link CategoryUpdateReq}
     * @return {@link DtsApiResponse}
     * @author taitt
     */
    @PutMapping(AppApiConstant.API_ADMIN_CATEGORY)
    @ApiOperation("Update category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022703, message = "Error process update info category"),
            @ApiResponse(code = 4022706, message = "Error account not found"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse updateCategory(
            @ApiParam(name = "body", value = "Category information to update") @RequestBody CategoryUpdateReq categoryUpdateReq) {
        long start = System.currentTimeMillis();
        try {
            categoryService.update(categoryUpdateReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * <p>
     * Delete category.
     * </p>
     *
     * @param categoryId
     *            type {@link Long}
     * @return {@link DtsApiResponse}
     * @author taitt
     */
    @DeleteMapping(AppApiConstant.API_ADMIN_CATEGORY)
    @ApiOperation("Delete category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022704, message = "Error process delete category"),
            @ApiResponse(code = 4022706, message = "Error category not found"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse deleteCategory(
            @ApiParam(name = "categoryId", value = "Delete category on system by id", example = "1") @RequestParam("categoryId") Long categoryId) {
        long start = System.currentTimeMillis();
        try {
            categoryService.delete(categoryId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * <p>
     * Detail category.
     * </p>
     *
     * @param categoryId
     *            type {@link Long}
     * @return {@link DtsApiResponse}
     * @author taitt
     */
    @GetMapping(AppApiConstant.API_ADMIN_CATEGORY + "/{categoryId}")
    @ApiOperation("Detail of category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4022706, message = "Error category not found"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse detailCategory(
            @ApiParam(name = "categoryId", value = "Get category information detail on system by id", example = "1") @PathVariable("categoryId") Long categoryId) {
        long start = System.currentTimeMillis();
        try {
            CategoryInfoRes resObj = categoryService.getCategoryInfoResById(categoryId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_CATEGORY + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = categoryService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

}

/*******************************************************************************
 * Class        ：ItemRest
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：MinhNV
 * Change log   ：2020/12/07：01-00 MinhNV create a new
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
import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.ItemAddReq;
import vn.com.unit.ep2p.dto.req.ItemUpdateReq;
import vn.com.unit.ep2p.dto.res.ItemInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ItemService;
import vn.com.unit.ep2p.service.PagingService;

/**
 * ItemRest
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_ITEM_DESCR })
public class ItemRest extends AbstractRest {

    @Autowired
    PagingService pagingService;

    @Autowired
    ItemService itemService;

    private static final String URL_ITEM_ID = "/{itemId}";
    private static final String ITEM_ID = "itemId";
    
    @ApiOperation("List items")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021100, message = "Error process list item") })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "displayFlag", value = "true", dataType = "boolean", dataTypeClass = Boolean.class, paramType = "query"),
        @ApiImplicitParam(name = "keySearch", value = "Minh", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column FUNCTION_CODE, FUNCTION_NAME, DESCRIPTION"),
        
        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported. Sort about:ID, FUNCTION_CODE, FUNCTION_NAME, DESCRIPTION,  CREATED_ID, CREATED_DATE, UPDATED_ID,UPDATED_DATE. Example: ID,asc") 
 })
    @GetMapping(AppApiConstant.API_ADMIN_ITEM)
    public DtsApiResponse listItem(
            @ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JcaItemDto> resObj = itemService.search(requestParams,pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @ApiOperation("Create an item")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021101, message = "Error process add item"),
            @ApiResponse(code = 4021106, message = "Error function code is duplicated"),
            })
    @PostMapping(AppApiConstant.API_ADMIN_ITEM)
    public DtsApiResponse addItem(
            @ApiParam(name = "body", value = "Position information to add new") @RequestBody ItemAddReq reqItemAddDto) {
        long start = System.currentTimeMillis();
        try {
            ItemInfoRes resObj = itemService.create(reqItemAddDto);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @ApiOperation("Update item")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021102, message = "Error process update info item"),
            @ApiResponse(code = 4021105, message = "Error item not found") })
    @PutMapping(AppApiConstant.API_ADMIN_ITEM)
    public DtsApiResponse updateInfoItem(
            @ApiParam(name = "body", value = "Position information to update") @RequestBody ItemUpdateReq reqItemUpdateDto) {
        long start = System.currentTimeMillis();
        try {
            itemService.update(reqItemUpdateDto);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @ApiOperation("Delete item")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021103, message = "Error process delete item"),
            @ApiResponse(code = 4021105, message = "Error item not found") })
    @DeleteMapping(AppApiConstant.API_ADMIN_ITEM)
    public DtsApiResponse deleteItem(
            @ApiParam(name = ITEM_ID, value = "Delete account on system by id", example = "1") @RequestParam(ITEM_ID) Long itemId) {
        long start = System.currentTimeMillis();
        try {
            itemService.delete(itemId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @ApiOperation("Item information by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021105, message = "Error item not found") })
    @GetMapping(AppApiConstant.API_ADMIN_ITEM + URL_ITEM_ID)
    public DtsApiResponse getInfoItem(
            @ApiParam(name = ITEM_ID, value = "Get item information detail on system by id", example = "1") @PathVariable(ITEM_ID) Long itemId) {
        long start = System.currentTimeMillis();
        try {
            ItemInfoRes resObj =itemService.getItemInfoById(itemId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_ITEM + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = itemService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

/*******************************************************************************
 * Class        ：PositionRest
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：MinhNV
 * Change log   ：2020/12/02：01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.common.tree.TreeObject;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.PositionAddReq;
import vn.com.unit.ep2p.dto.req.PositionUpdateReq;
import vn.com.unit.ep2p.dto.res.PositionInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.PagingService;
import vn.com.unit.ep2p.service.PositionService;

/**
 * PositionRest
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_POSITION_DESCR })
public class PositionRest extends AbstractRest {

    @Autowired
    PagingService pagingService;

    @Autowired
    private PositionService positionService;

    private static final String URL_POSITION_ID = "/{positionId}";
    private static final String POSITION_ID = "positionId";

    @ApiOperation("List positions")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402901, message = "Error process list position"),
            @ApiResponse(code = 500, message = "Internal server error"),
            })
    @GetMapping(value = AppApiConstant.API_ADMIN_POSITION)
    public DtsApiResponse listPosition() {
        long start = System.currentTimeMillis();
        try {
            List<TreeObject<JcaPositionDto>> resObj = positionService.getListJcaPositionDto();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @ApiOperation("Create a position")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402902, message = "Error process add position"),
            @ApiResponse(code = 402907, message = "Error not found information position parent"),
            @ApiResponse(code = 4023301, message = "Error process add position path"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @PostMapping(AppApiConstant.API_ADMIN_POSITION)
    public DtsApiResponse addPosition(
            @ApiParam(name = "body", value = "Position information to add new") @RequestBody PositionAddReq reqPositionAddDto) {
        long start = System.currentTimeMillis();
        try {
            PositionInfoRes resObj = positionService.create(reqPositionAddDto);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @ApiOperation("Update position")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402903, message = "Error process update info position"),
            @ApiResponse(code = 402906, message = "Error position not found"),
            @ApiResponse(code = 402907, message = "Error not found information position parent"),
            @ApiResponse(code = 4023301, message = "Error process add position path"),
            @ApiResponse(code = 500, message = "Internal server error"),
            })
    @PutMapping(AppApiConstant.API_ADMIN_POSITION)
    public DtsApiResponse updateInfoPosition(
            @ApiParam(name = "body", value = "Position information to update") @RequestBody PositionUpdateReq reqPositionUpdateDto) {
        long start = System.currentTimeMillis();
        try {
            positionService.update(reqPositionUpdateDto);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @ApiOperation("Delete position")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402904, message = "Error process delete position"),
            @ApiResponse(code = 402906, message = "Error position not found"),
            @ApiResponse(code = 500, message = "Internal server error"),
            })
    @DeleteMapping(AppApiConstant.API_ADMIN_POSITION)
    public DtsApiResponse deleteInfoPosition(
            @ApiParam(name = POSITION_ID, value = "Delete account on system by id", example = "1") @RequestParam(POSITION_ID) Long positionId) {
        long start = System.currentTimeMillis();
        try {
            positionService.delete(positionId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @ApiOperation("Detail of position")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402905, message = "Error process get position information detail"),
            @ApiResponse(code = 402906, message = "Error position not found"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    @GetMapping(AppApiConstant.API_ADMIN_POSITION + URL_POSITION_ID)
    public DtsApiResponse getInfoPosition(
            @ApiParam(name = POSITION_ID, value = "Get position information detail on system by id", example = "1") @PathVariable(POSITION_ID) Long positionId) {
        long start = System.currentTimeMillis();
        try {
            PositionInfoRes resObj = positionService.getPositionInfoById(positionId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

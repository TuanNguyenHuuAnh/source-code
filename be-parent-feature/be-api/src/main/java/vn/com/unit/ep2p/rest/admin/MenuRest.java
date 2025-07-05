/*******************************************************************************
 * Class        ：MenuRest
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：SonND
 * Change log   ：2020/12/09：01-00 SonND create a new
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
import vn.com.unit.core.dto.JcaMenuDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.MenuAddReq;
import vn.com.unit.ep2p.dto.req.MenuUpdateReq;
import vn.com.unit.ep2p.dto.res.MenuInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.MenuService;
import vn.com.unit.ep2p.service.PagingService;

/**
 * MenuRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_MENU_DESCR })
public class MenuRest extends AbstractRest {

	@Autowired
	PagingService pagingService;

	@Autowired
	private MenuService menuService;

	@GetMapping(AppApiConstant.API_ADMIN_MENU)
	@ApiOperation("List of menu")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
			@ApiResponse(code = 4021604, message = "Error process list menu"), })
	public DtsApiResponse listMenu(@RequestParam("companyId") Long companyId) {
		long start = System.currentTimeMillis();
		try {
			List<TreeObject<JcaMenuDto>> resObj = menuService.getListMenu(companyId);
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@PostMapping(AppApiConstant.API_ADMIN_MENU)
	@ApiOperation("Create menu")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
			@ApiResponse(code = 4021602, message = "Error menu not found"),
			@ApiResponse(code = 4021105, message = "Error not found information item"),
			@ApiResponse(code = 4021605, message = "Error process add menu"),

	})
	public DtsApiResponse addMenu(
			@ApiParam(name = "body", value = "Menu information to add new") @RequestBody MenuAddReq menuAddReq) {
		long start = System.currentTimeMillis();
		try {
			MenuInfoRes resObj = menuService.create(menuAddReq);
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@PutMapping(AppApiConstant.API_ADMIN_MENU)
	@ApiOperation("Update menu")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
			@ApiResponse(code = 4021602, message = "Error menu not found"),
			@ApiResponse(code = 4021105, message = "Error not found information item"),
			@ApiResponse(code = 4021606, message = "Error process update information menu"), })
	public DtsApiResponse updateMenu(
			@ApiParam(name = "body", value = "Menu information to update") @RequestBody MenuUpdateReq menuUpdateReq) {
		long start = System.currentTimeMillis();
		try {
			menuService.update(menuUpdateReq);
			return this.successHandler.handlerSuccess(null, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@GetMapping(AppApiConstant.API_ADMIN_MENU + "/{menuId}")
	@ApiOperation("Detail of menu")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
			@ApiResponse(code = 4021602, message = "Error menu not found"), })
	public DtsApiResponse detailMenu(
			@ApiParam(name = "menuId", value = "Get menu information detail on system by id", example = "1") @PathVariable("menuId") Long menuId) {
		long start = System.currentTimeMillis();
		try {
			MenuInfoRes resObj = menuService.getMenuInfoResById(menuId);
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@DeleteMapping(AppApiConstant.API_ADMIN_MENU)
	@ApiOperation("Delete menu")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
			@ApiResponse(code = 4021602, message = "Error menu not found"),
			@ApiResponse(code = 4021608, message = "Error process delete menu"), })
	public DtsApiResponse deleteAccount(
			@ApiParam(name = "menuId", value = "Delete menu on system by id", example = "1") @RequestParam("menuId") Long menuId) {
		long start = System.currentTimeMillis();
		try {
			menuService.delete(menuId);
			return this.successHandler.handlerSuccess(null, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@GetMapping(AppApiConstant.API_ADMIN_MENU_TREE_BY_USER_ID)
	@ApiOperation("Tree menu by user id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
			@ApiResponse(code = 4021604, message = "Error process list menu"), })
	public DtsApiResponse treeMenuByUserId(@RequestParam("userId") Long userId,
			@RequestParam("langCode") String langCode, @RequestParam("companyId") Long companyId) {
		long start = System.currentTimeMillis();
		try {
			List<TreeObject<JcaMenuDto>> resObj = menuService.getTreeMenuByUser(companyId, langCode, userId);
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@GetMapping(AppApiConstant.API_ADMIN_MENU_BY_USER_ID)
	@ApiOperation("List of menu by user id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
			@ApiResponse(code = 4021604, message = "Error process list menu"), })
	public DtsApiResponse listMenuByUserId(@RequestParam("userId") Long userId,
			@RequestParam("langCode") String langCode, @RequestParam("companyId") Long companyId) {
		long start = System.currentTimeMillis();
		try {
			List<JcaMenuDto> resObj = menuService.getListMenuByUser(companyId, langCode, userId);
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}
	
	@GetMapping("menu/fe")
	@ApiOperation("Tree menu by user id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
			@ApiResponse(code = 4021604, message = "Error process list menu"), })
	public DtsApiResponse listMenu(@RequestParam(name = "langCode", required = false, defaultValue = "vi") String langCode) {
		long start = System.currentTimeMillis();
		try {
			List<TreeObject<JcaMenuDto>> resObj = menuService.getTreeMenuByUser(UserProfileUtils.getCompanyId(), langCode, UserProfileUtils.getAccountId());
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}
	
    @GetMapping("menu/fe-ignore-root")
    @ApiOperation("Tree menu by user id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021604, message = "Error process list menu"), })
    public DtsApiResponse listMenuFeIgnoreRoot(@RequestParam(name = "langCode", required = false, defaultValue = "vi") String langCode) {
        long start = System.currentTimeMillis();
        try {
            List<TreeObject<JcaMenuDto>> resObj = menuService.getTreeMenuByUser(UserProfileUtils.getCompanyId(), langCode, UserProfileUtils.getAccountId());
            return this.successHandler.handlerSuccess(resObj.get(0).getChildren(), start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

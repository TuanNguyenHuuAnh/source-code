package vn.com.unit.ep2p.rest.app;
//package vn.com.unit.mbal.app.rest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import springfox.documentation.annotations.ApiIgnore;
//import vn.com.unit.core.res.ObjectDataRes;
//import vn.com.unit.dts.web.rest.common.DtsApiResponse;
//import vn.com.unit.mbal.AbstractRest;
//import vn.com.unit.mbal.api.constant.AppApiConstant;
//import vn.com.unit.mbal.api.req.dto.CopManagementReqSearchDto;
//import vn.com.unit.mbal.core.ers.dto.CopManagementDto;
//import vn.com.unit.mbal.ers.constant.ErsApiConstant;
//import vn.com.unit.mbal.service.CopListService;
//
////@RestController
////@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP + ErsApiConstant.COP_MANAGEMENT)
////@Api(tags = { "Cop management" })
//public class CopManagement extends AbstractRest {
//
//	@Autowired
//	CopListService copListService;
//
//	@PostMapping("/list")
//	@ApiOperation("Cop managemant")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//			@ApiResponse(code = 500, message = "Internal server error"), })
//
//	public DtsApiResponse doSearch(@RequestBody CopManagementReqSearchDto cond, @ApiIgnore Pageable pageable) {
//		long start = System.currentTimeMillis();
//		try {
//			ObjectDataRes<CopManagementDto> resObj = copListService.doSearch(cond, pageable);
//			return this.successHandler.handlerSuccess(resObj, start);
//		} catch (Exception ex) {
//			return this.errorHandler.handlerException(ex, start);
//		}
//	}
//	
//	@SuppressWarnings("rawtypes")
//	@GetMapping("/export")
//    @ApiOperation("Export cop management")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 500, message = "Internal server error"), })
//	public ResponseEntity export(CopManagementReqSearchDto cond) {
//        ResponseEntity resObj = null;
//        try {
//            resObj = copListService.export(cond);
//        } catch (Exception ex) {
//        }
//        return resObj;
//    }
//	
//	@SuppressWarnings("rawtypes")
//	@DeleteMapping("/{id}")
//	 @ApiOperation("delete cop management")
//    public String delete(@PathVariable Long id) {      
//		boolean rs  = copListService.delete(id);	
//		return "ok";      
//    }
//
//}

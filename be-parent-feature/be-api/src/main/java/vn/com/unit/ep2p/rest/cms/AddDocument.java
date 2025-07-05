package vn.com.unit.ep2p.rest.cms;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.document.dto.DocumentInformationSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.AddDocumentsService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_ODS_URL + CmsApiConstant.API_CMS_ADD_DOC)
@Api(tags = { CmsApiConstant.API_CMS_ADD_DOC_DESCR })
public class AddDocument extends AbstractRest {

	@Autowired
	private AddDocumentsService addDocumentsService;

	@GetMapping(AppApiConstant.API_LIST_DOC_INFORMATION)
	@ApiOperation("Get Information by polocy, doc ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), 
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListInformation(HttpServletRequest request, DocumentInformationSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			CmsCommonPagination<DocumentInformationSearchDto> common = addDocumentsService.getListInformation(searchDto);
			ObjectDataRes<DocumentInformationSearchDto> resObj = new ObjectDataRes<>(common.getTotalData(),common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	@GetMapping(AppApiConstant.API_LIST_ALL_DOC_ADD)
	@ApiOperation("Get List document add ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListDocumentAdd(HttpServletRequest request, 
			 @RequestParam(defaultValue = "0") Integer modeView,
    		 @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
             @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
             DocumentInformationSearchDto searchDto)  {
		long start = System.currentTimeMillis();
		try {
			CmsCommonPagination<DocumentInformationSearchDto> common = addDocumentsService.getListDocumentAdd(searchDto);
			ObjectDataRes<DocumentInformationSearchDto> resObj = new ObjectDataRes<>(common.getTotalData(),	common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_LIST_All_DOC_ADD_SUBMIT)
    @ApiOperation("Get customer by customer")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden"),
                            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListDocumentSubmit(HttpServletRequest request,
             @RequestParam(defaultValue = "0") Integer modeView,
    		 @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
             @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
             DocumentInformationSearchDto searchDto)  {
		
		long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	CmsCommonPagination<DocumentInformationSearchDto> common = addDocumentsService.getListDocumentSubmit(searchDto);
        	ObjectDataRes<DocumentInformationSearchDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}


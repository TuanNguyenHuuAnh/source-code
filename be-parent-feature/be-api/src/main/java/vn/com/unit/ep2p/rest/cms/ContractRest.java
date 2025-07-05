package vn.com.unit.ep2p.rest.cms;

import java.util.Locale;

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
import vn.com.unit.cms.core.module.customerManagement.dto.ContractExpiresPersonalDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ContractService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_CONTRACT)
@Api(tags = { CmsApiConstant.API_CMS_CONTRACT_DESC })
public class ContractRest extends AbstractRest{
	
	@Autowired
	private ContractService contractService;
	
	@GetMapping(AppApiConstant.API_LIST_LAPSED_CONTRACT_INDIVIDUAL)
    @ApiOperation("Get list contract by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListLapsedContractIndividual(HttpServletRequest request
    	, @RequestParam(value = "contractOwnerName", required = false) String contractOwnerName
        , @RequestParam(value = "contractNumber", required = false) Integer contractNumber
        , @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "5") Integer size)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            ObjectDataRes<ContractExpiresPersonalDto> resObj = contractService.getListContractExpiresPersonal(contractNumber, contractOwnerName, page, size);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}

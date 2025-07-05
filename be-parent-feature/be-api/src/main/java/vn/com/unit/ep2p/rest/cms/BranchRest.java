package vn.com.unit.ep2p.rest.cms;

import java.util.ArrayList;
import java.util.List;
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
import vn.com.unit.cms.core.dto.SelectDto;
import vn.com.unit.cms.core.module.branch.dto.CmsBranchDto;
import vn.com.unit.cms.core.module.branch.dto.CmsBranchSearchDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiBranchService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_BRANCH)
@Api(tags = { CmsApiConstant.API_CMS_BRANCH_DESCR })
public class BranchRest extends AbstractRest{

	@Autowired
	ApiBranchService apiBranchService;
	
	@GetMapping(AppApiConstant.API_BRANCH_BY_CONDITION)
    @ApiOperation("Api provides branchs on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListCategoryByPageType(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "5") Integer size,
			CmsBranchSearchDto dto
    		, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
//        	List<CmsBranchDto> resObj = apiBranchService.getBranchByCondition(city, address, modeView);
        	List<CmsBranchDto> datas = new ArrayList<>();
        	int count = apiBranchService.countBranchByCondition(dto, modeView);
            if (count > 0) {
                datas = apiBranchService.getBranchByCondition(page, size, dto, modeView);
            }
            
            ObjectDataRes<CmsBranchDto> resObj = new ObjectDataRes<>(count, datas);
        	
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);//SelectDto
        }
    }
	
	@GetMapping(AppApiConstant.API_REGION)
    @ApiOperation("Api provides region on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListRegionByCountry(HttpServletRequest request
    		, @RequestParam(defaultValue = "194") Long countryId
    		, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	List<SelectDto> resObj = apiBranchService.getListRegionByCountry(countryId, locale.getLanguage(), modeView);
        	
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);//SelectDto
        }
    }

	@GetMapping(AppApiConstant.API_CITY_BY_COUNTRY)
    @ApiOperation("Api provides branchs on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListCityByCountry(HttpServletRequest request
    		, @RequestParam(defaultValue = "194") Long countryId
    		, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	List<Select2Dto> resObj = apiBranchService.getListCityByCountry(countryId, locale.getLanguage(), modeView);
        	
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);//SelectDto
        }
    }

	@GetMapping(AppApiConstant.API_DISTRICT_BY_CITY)
    @ApiOperation("Api provides branchs on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListDistrictByCity(HttpServletRequest request
    		, @RequestParam(defaultValue = "") String city
    		, @RequestParam(defaultValue = "194") String counttryId
    		, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	List<Select2Dto> resObj = apiBranchService.getListDistrictByCity(counttryId, city, locale.getLanguage(), modeView);
        	
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);//SelectDto
        }
    }
	
	@GetMapping(AppApiConstant.API_CONSTANT_BY_KIND_AND_CODE)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListConstantByKindAndCode(HttpServletRequest request
    		, String kind, String code)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	List<SelectDto> resObj = apiBranchService.getListConstantByKindAndCode(kind, code, locale.getLanguage());
        	
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);//SelectDto
        }
    }
	
	@GetMapping(AppApiConstant.API_WARD_BY_DISTRICT)
    @ApiOperation("Api provides branchs on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListWardByDistrict(HttpServletRequest request
    		, @RequestParam(defaultValue = "") String district
    		, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
        	List<Select2Dto> resObj = apiBranchService.getListWardByDistrict(district);
        	
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);//SelectDto
        }
    }
}

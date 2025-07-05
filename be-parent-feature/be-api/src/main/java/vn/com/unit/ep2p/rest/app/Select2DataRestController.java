/*******************************************************************************
 * Class        ：AccountAppRest
 * Created date ：2021/02/26
 * Lasted date  ：2021/02/26
 * Author       ：TaiTM
 * Change log   ：2021/02/26：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * AccountAppRest
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
@Api(tags = { "Select2 Data" })
public class Select2DataRestController extends AbstractRest {

    @Autowired
    private Select2DataService select2DataService;

    @GetMapping("/select2/province")
    @ApiOperation("List of Province")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getProvinceList() {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getProvinceList();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * getDistrictByProvince
     *
     * @param province
     * @return
     * @author TaiTM
     */
    @GetMapping("/select2/district/{province}")
    @ApiOperation("List of District by Province")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getDistrictByProvince(@PathVariable("province") String province) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getAllDistrictByProvince(province);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * getWardByDistrict
     *
     * @param district
     * @return
     * @author TaiTM
     */
    @GetMapping("/select2/ward/{province}/{district}")
    @ApiOperation("List of Ward by District")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getWardByDistrict(@PathVariable("province") String province,
            @PathVariable("district") String district) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getAllWardByDistrict(province, district);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * getBankList
     *
     * @param lang
     * @return
     * @author TaiTM
     */
    @GetMapping("/select2/bank")
    @ApiOperation("List of Bank")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getBankList(@RequestParam(value = "lang", defaultValue = "en") String lang) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getConstantData("JCA_BANK", "BANK", lang);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    /**
     * @author PhatLT
     * @param lang
     * @return
     */
    @GetMapping("/select2/bank-province")
    @ApiOperation("List Province of Bank")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getBankProvinceList(@RequestParam(value = "lang", defaultValue = "en") String lang) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getConstantData("JCA_PROVINCE_CITY", "PROVINCE_CITY", lang);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * getBankList
     *
     * @param lang
     * @return
     * @author TaiTM
     */
    @GetMapping("/select2/bank-branch/{province}")
    @ApiOperation("List of Bank")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getBankBranch(@PathVariable("province") String province,
            @RequestParam(value = "lang", defaultValue = "en") String lang) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getAllBankBranchByProvince(province, lang);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * getBankList
     *
     * @param lang
     * @return
     * @author TaiTM
     */
    @GetMapping("/select2/job")
    @ApiOperation("List of Job")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getJob(@RequestParam(value = "lang", defaultValue = "en") String lang) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getConstantData("JCA_JOB_LIST", "JOB_LIST", lang);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    /**
     * getGender
     *
     * @param lang
     * @return
     * @author TaiTM
     */
    @GetMapping("/select2/gender")
    @ApiOperation("List of Gender")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getGender(@RequestParam(value = "lang", defaultValue = "en") String lang) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getConstantData("JCA_ADMIN_GENDER", "GENDER_TYPE", lang);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @ApiOperation("List of region bancas")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    @GetMapping("/select2/region-bancas")
    public DtsApiResponse getRegionBancas() {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getRegionBancasList();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @ApiOperation("List of manager by region bancas")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    @GetMapping("/select2/manager-by-region-bancas")
    public DtsApiResponse getManagerByRegionBancas(@RequestParam(value = "regionManagerCode") String regionManagerCode) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getManagerByRegionBancas(regionManagerCode);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @ApiOperation("List of RM manager by region bancas")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    @GetMapping("/select2/rm-manager-bancas")
    public DtsApiResponse getRMManagerBancas() {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getRMManagerBancas();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @ApiOperation("List of class info bancas")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    @GetMapping("/select2/class-info-bancas")
    public DtsApiResponse getClassBancas() {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getClassInfoNoStartBancas();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @ApiOperation("List of RM AM manager by region bancas")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    @GetMapping("/select2/rm-am-manager-bancas")
    public DtsApiResponse getRMAMManagerBancas() {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getRMAMManagerBancas();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @ApiOperation("List Security Question")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    @GetMapping("/select2/security-question")
    public DtsApiResponse getSecurityQuestion(Locale locale) {
        long start = System.currentTimeMillis();
        try {
            List<Select2Dto> resObj = select2DataService.getSecurityQuestion(locale.toString());
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    @GetMapping("/select2/constant-by-group-and-kind")
    @ApiOperation("List of Kind")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getListConstantByKind(HttpServletRequest request
    		, @RequestParam(defaultValue = "") String groupCode, @RequestParam(defaultValue = "") String kind) {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            List<Select2Dto> resObj = select2DataService.getConstantData(groupCode, kind, locale.getLanguage());
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping("/select2/constant-by-group-and-kind-order")
    @ApiOperation("List of Kind")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getListConstantByKindOrderBy(HttpServletRequest request
    		, @RequestParam(defaultValue = "") String groupCode, @RequestParam(defaultValue = "") String kind) {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            List<Select2Dto> resObj = select2DataService.getListConstantByKindOrderBy(groupCode, kind, locale.getLanguage());
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}

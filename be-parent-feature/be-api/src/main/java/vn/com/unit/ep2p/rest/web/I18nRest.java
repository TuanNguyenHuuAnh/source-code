/*******************************************************************************
 * Class        ：I18nRest
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NhanNV
 * Change log   ：2020/11/11：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.web;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.com.unit.core.dto.I18nLocaleDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.service.I18nLocaleService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.I18nLocaleReq;
import vn.com.unit.ep2p.rest.AbstractRest;

/**
 * I18nRest
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_I18N_LOCALE})
public class I18nRest extends AbstractRest{
    
	@Autowired
	private I18nLocaleService i18nLocaleService;
	
	@Autowired
	private LanguageService languageService;
    
    @GetMapping(AppApiConstant.API_APP_GET_TRANSLATION)
    @ApiOperation("Get the translation by company")
    public JSONObject getTranslation(@PathVariable String lng, @RequestParam String companyId) {
    	JSONObject translation = new JSONObject();
    	List<I18nLocaleDto> i18nLocaleDtos = new ArrayList<>();
    	i18nLocaleDtos.clear();
    	i18nLocaleDtos.addAll(i18nLocaleService.findI18nLocaleDtoByCompanyIdAndLocale(/*companyId*/ Long.parseLong(companyId), lng.toUpperCase()));
    	
    	// convert to json
    	for(I18nLocaleDto i18nLocaleDto : i18nLocaleDtos) {
    		translation.put(i18nLocaleDto.getMessageKey(), i18nLocaleDto.getMessageContent());
    	}
        return translation;
    }

    @PostMapping(AppApiConstant.API_APP_CLONE_TRANSLATION)
    @ApiOperation("Clone the translation")
    public DtsApiResponse cloneTranslation(@RequestBody I18nLocaleReq i18nLocaleReq) {
    	long start = System.currentTimeMillis();
    	
    	boolean flag = i18nLocaleService.cloneTranslation(i18nLocaleReq.getCompanyId(), i18nLocaleReq.getNewLanguageCode());
    	if(flag) {
            return this.successHandler.handlerSuccess(null, start);
    	}else {
            return this.errorHandler.handlerException(null, start);
    	}
    }
    
    @GetMapping(AppApiConstant.API_APP_GET_VERSION_OF_TRANSLATION)
    @ApiOperation("Get the verison of the translation")
    public DtsApiResponse getVersionTranslation(@RequestParam Long companyId, @RequestParam String code) {
    	long start = System.currentTimeMillis();
    	
    	LanguageDto version = languageService.findByCompanyIdAndCode(companyId, code);
    	if(version != null) {
            return this.successHandler.handlerSuccess(version, start);
    	}else {
            return this.errorHandler.handlerException(null, start);
    	}
    }
}

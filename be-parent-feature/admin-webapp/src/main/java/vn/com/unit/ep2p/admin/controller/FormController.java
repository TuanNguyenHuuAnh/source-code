/*******************************************************************************
 * Class        :FormController
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :KhoaNA
 * Change log   :2019/04/16:01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.workflow.enumdef.ProcessType;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.service.CategoryService;
import vn.com.unit.ep2p.admin.service.FormService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.common.dto.Select2Dto;

/**
 * FormController
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
//@Controller
@RequestMapping(UrlConst.FORM)
public class FormController {

//    private static final Logger logger = LoggerFactory.getLogger(FormController.class);

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    FormService formService;
    
    /**
     * getFormListForSelect
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @return
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.GET_LIST_FOR_SELECT, method = RequestMethod.POST)
    @ResponseBody
    public Object getFormListForSelect(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) boolean isPaging, Locale locale) {
        Select2ResultDto obj = new Select2ResultDto();
        
        Long accountId = UserProfileUtils.getAccountId();
        List<Select2Dto> lst = formService.getFormListByCompanyId(keySearch, companyId, accountId, isPaging, locale.getLanguage());
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
    
    /**
     * getStatusListForSelect
     * @param keySearch
     * @param companyId
     * @param formId
     * @param isPaging
     * @return
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.STATUS_GET_LIST_FOR_SELECT, method = RequestMethod.POST)
    @ResponseBody
    public Object getStatusListForSelect(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
    		@RequestParam(required = false) Long processId,
            @RequestParam(required = false) boolean isPaging, Locale locale) {
        Select2ResultDto obj = new Select2ResultDto();
        
        List<Select2Dto> lst = null;
        String lang = locale.getLanguage();
        if( processId == null ) {
        	lst = formService.getStatusListCommon();
        } else {
        	lst = formService.getStatusListByCompanyIdAndFormId(keySearch, companyId, processId, isPaging, lang);
        }
        
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
    
    @PostMapping(value = "/get-list-for-user-ignoge-integrate")
    @ResponseBody
    public Object getFormListForUserIgnoresIntegrate(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) boolean isPaging, Locale locale) {
        Select2ResultDto obj = new Select2ResultDto();
        Long accountId = UserProfileUtils.getAccountId();
        String lang = locale.getLanguage();
        List<String> processTypeIgnores = Arrays.asList(ProcessType.INTEGRATE.toString());
        List<Select2Dto> lst = formService.getSelect2DtoForUser(keySearch, companyId, accountId, processTypeIgnores, isPaging, lang);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
}
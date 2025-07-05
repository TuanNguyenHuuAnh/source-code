/*******************************************************************************
 * Class        ：MockupController
 * Created date ：2017/03/23
 * Lasted date  ：2017/03/23
 * Author       ：TaiTM
 * Change log   ：2017/03/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.ep2p.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.service.CompanyService;

/**
 * ItemHtmlController
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Controller(value = "")
public class ItemHtmlController {

    @Autowired
    CompanyService companyService;

    @RequestMapping(value = "/item-html/select2", method = RequestMethod.GET)
    public ModelAndView htmlSelect2(@RequestParam(required = false, value = "isRequired") boolean isRequired,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "id") String id,
            @RequestParam(required = false, value = "value") String value) {
        ModelAndView mav = new ModelAndView("/views/item-html/select2.html");

        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        setAttr(mav, isRequired, name, id, value);

        return mav;
    }

    @RequestMapping(value = "/item-html/input", method = RequestMethod.GET)
    public ModelAndView htmlInput(@RequestParam(required = false, value = "isRequired") boolean isRequired,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "id") String id,
            @RequestParam(required = false, value = "value") String value,
            @RequestParam(required = false, value = "placeholder") String placeholder) {
        ModelAndView mav = new ModelAndView("/views/item-html/input.html");

        setAttr(mav, isRequired, name, id, value);

        mav.addObject("placeholder", placeholder);

        return mav;
    }

    private void setAttr(ModelAndView mav, boolean isRequired, String name, String id, String value) {
        mav.addObject("isRequired", isRequired);
        mav.addObject("name", name);
        mav.addObject("id", id);
        mav.addObject("value", value);
    }
}

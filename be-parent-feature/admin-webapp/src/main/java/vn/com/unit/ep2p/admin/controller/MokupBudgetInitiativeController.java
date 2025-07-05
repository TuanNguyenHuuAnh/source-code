package vn.com.unit.ep2p.admin.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.ep2p.constant.UrlConst;

@Controller
@RequestMapping("/mokup-budget-initiactive")
public class MokupBudgetInitiativeController {

	@GetMapping(value = UrlConst.LIST)
    public ModelAndView getList(HttpServletRequest request, Locale locale) {
		
        ModelAndView mav = new ModelAndView("/views/mokup/mokup-budget-initiactive-list.html");         
        return mav;
    }
	
	@GetMapping(value = UrlConst.EDIT)
    public ModelAndView getEdit(HttpServletRequest request, Locale locale) {
		
        ModelAndView mav = new ModelAndView("/views/mokup/mokup-budget-initiactive-edit.html");         
        return mav;
    }
}

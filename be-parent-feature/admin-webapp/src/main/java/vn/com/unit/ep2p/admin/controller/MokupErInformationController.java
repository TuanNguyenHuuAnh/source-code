package vn.com.unit.ep2p.admin.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mockup")
public class MokupErInformationController {

	@GetMapping(value = "/select-item")
    public ModelAndView getList(HttpServletRequest request, Locale locale) {
		
        ModelAndView mav = new ModelAndView("/views/mokup/select-item.html");         
        return mav;
    }
	
	@GetMapping(value = "/er-information")
    public ModelAndView getErInformation(HttpServletRequest request, Locale locale) {
		
        ModelAndView mav = new ModelAndView("/views/mokup/mokup-er-information.html");         
        return mav;
    }
	
	@GetMapping(value = "/supporting-document")
    public ModelAndView supportingDocument(HttpServletRequest request, Locale locale) {
		
        ModelAndView mav = new ModelAndView("/views/mokup/supporting-document.html");         
        return mav;
    }
	
	@GetMapping(value = "/expense-requisition-list")
    public ModelAndView expenseRequisition(HttpServletRequest request, Locale locale) {
		
        ModelAndView mav = new ModelAndView("/views/mokup/expense-requisition-list.html");         
        return mav;
    }
	
	@GetMapping(value = "/review-bi")
    public ModelAndView review(HttpServletRequest request, Locale locale) {
		
        ModelAndView mav = new ModelAndView("/views/mokup/review-bi.html");         
        return mav;
    }
}

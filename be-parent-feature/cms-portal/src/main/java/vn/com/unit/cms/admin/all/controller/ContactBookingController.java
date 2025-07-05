/*******************************************************************************
 * Class ：ChatController Created date ：2017/05/10 Lasted date ：2017/05/10 Author ：phunghn Change log ：2017/05/10：01-00
 * phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.cms.admin.all.constant.AdminRoleConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.dto.ContactBookingDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingEditDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingSearchDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingStatusDto;
import vn.com.unit.cms.admin.all.dto.ContactBookingUpdateItemDto;
import vn.com.unit.cms.admin.all.dto.ContactCommentDto;
import vn.com.unit.cms.admin.all.enumdef.ContactBookingExportEnum;
import vn.com.unit.cms.admin.all.enumdef.ContactBookingStatusEnum;
import vn.com.unit.cms.admin.all.jcanary.common.util.ConstantImport;
//import vn.com.unit.cms.admin.all.jcanary.common.util.ConstantImport;
import vn.com.unit.cms.admin.all.service.ContactBookingService;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.util.ConstantImport;

/**
 * ChatController
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Controller
@RequestMapping("contact")
public class ContactBookingController {

	private static final String CONTACT_BOOKING_REJECT_SUCCESS_MESSAGE = "contact.booking.reject.success-message";

	private static final String CONTACT_BOOKING_TRANSFER_SUCCESS_MESSAGE = "contact.booking.transfer.success-message";

	private static final String CONTACT_BOOKING_DONE_SUCCESS_MESSAGE = "contact.booking.done.success-message";
	@Autowired
	private ContactBookingService contactBookingService;

	@Autowired
	private MessageSource msg;

	@Autowired
	ServletContext servletContext;

	@Autowired
	SystemConfig systemConfig;

	private static final Logger logger = LoggerFactory.getLogger(ContactBookingController.class);

	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
			request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		}
		// The date format to parse or output your dates
		String patternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

	private List<ContactBookingStatusDto> loadBookingStatusList() {
		List<ContactBookingStatusDto> bookingStatusList = new ArrayList<ContactBookingStatusDto>();
		for (ContactBookingStatusEnum statusEnum : ContactBookingStatusEnum.class.getEnumConstants()) {
			ContactBookingStatusDto statusObject = new ContactBookingStatusDto();
			statusObject.setStatusName(statusEnum.getStatusName());
			statusObject.setStatusTitle(statusEnum.getStatusAlias());
			bookingStatusList.add(statusObject);
		}
		return bookingStatusList;
	}
	
	@RequestMapping(value = "booking/list", method = RequestMethod.GET)
	public ModelAndView getList(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @ModelAttribute(value = "searchDto") ContactBookingSearchDto searchDto,
            Locale locale, Model model){
		 if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING)) {
	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	     }
	     ModelAndView mav = new ModelAndView("contact.booking.list");
	     PageWrapper<ContactBookingDto> pageWrapper = contactBookingService.getBookingList(searchDto, page);
	     List<SearchKeyDto> searchKeyList = contactBookingService.genSearchKeyList(locale);
	     List<ContactBookingStatusDto> statusOptions = this.loadBookingStatusList();
	    
	     String url = "contact/booking".concat(AdminUrlConst.LIST);
	     model.addAttribute("searchDto", searchDto);
	     model.addAttribute("searchKeyList", searchKeyList);
	     model.addAttribute("statusOptions", statusOptions);
	     mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
	     mav.addObject("pageUrl", url);
	     return mav;
	}
	
	@RequestMapping(value = "booking/list", method = RequestMethod.POST)
	public ModelAndView ajaxPostList(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @ModelAttribute(value = "searchDto") ContactBookingSearchDto searchDto,
            Locale locale, Model model){
		 if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING)) {
	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	     }
	     ModelAndView mav = new ModelAndView("contact.booking.table");
	     PageWrapper<ContactBookingDto> pageWrapper = contactBookingService.getBookingList(searchDto, page);
	     List<SearchKeyDto> searchKeyList = contactBookingService.genSearchKeyList(locale);
	     List<ContactBookingStatusDto> statusOptions = this.loadBookingStatusList();
	     String url = "contact/booking".concat(AdminUrlConst.LIST);
	     model.addAttribute("searchDto", searchDto);
	     model.addAttribute("searchKeyList", searchKeyList);
	     model.addAttribute("statusOptions", statusOptions);
	     mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
	     mav.addObject("pageUrl", url);
	     return mav;
	}
	
//	@RequestMapping(value = "booking/update-history/list", method = RequestMethod.POST)
//	public ModelAndView ajaxUpdateHistoryList(
//            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//            Locale locale, Model model){
//		 if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING.concat(ConstantCore.COLON_DISP))) {
//	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//	     }
//	     ModelAndView mav = new ModelAndView("contact.booking.table");
//	     PageWrapper<ContactBookingDto> pageWrapper = contactBookingService(searchDto, page);
//	     List<SearchKeyDto> searchKeyList = contactBookingService.genSearchKeyList(locale);
//	     String url = "contact/booking".concat(AdminUrlConst.LIST);
//	     model.addAttribute("searchDto", new CommonSearchDto());
//	     model.addAttribute("searchKeyList", searchKeyList);
//	     mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//	     mav.addObject("pageUrl", url);
//	     return mav;
//	}
//	
	@RequestMapping(value = "booking/exportList", method = { RequestMethod.POST })
	@ResponseBody
	public void exportHistory(@ModelAttribute(value = "searchDto") ContactBookingSearchDto searchDto,
			Locale locale,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		List<ContactBookingDto> bookingList =  contactBookingService.getFullBookingList(searchDto);
		exportBookingToExcel(bookingList,  response,  locale);
	}
	
	@RequestMapping(value = "booking/done", method = RequestMethod.POST)
	public ModelAndView postDetailBookingDone(
			@ModelAttribute(value = "editModel") ContactBookingEditDto bookingEditModel,
            @RequestParam(value = "id", required = true) Long bookingId,
            Locale locale, RedirectAttributes redirectAttributes){
		  if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING.concat(ConstantCore.COLON_EDIT))
				  && !UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING)) {
	          return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	      }
	      ModelAndView mav = new ModelAndView("contact.booking.detail");
	      ContactBookingDto bookingDetail = contactBookingService.processDoneBooking(bookingEditModel, bookingId, locale);
	      MessageList messageList = new MessageList(Message.SUCCESS);
	      String msgError = msg.getMessage(CONTACT_BOOKING_DONE_SUCCESS_MESSAGE, null, locale);
	      messageList.add(msgError);
	      String url = "contact/booking".concat(UrlConst.DETAIL).concat("?id=") + bookingId;
	      bookingDetail.setUrl(url);
	      List<ContactCommentDto> commentOptions = contactBookingService.getCommentOptions();
	      mav.addObject("commentOptions", commentOptions);
	      mav.addObject("bookingModel", bookingDetail);
	      mav.addObject(ConstantCore.MSG_LIST, messageList);
	      mav.addObject("bookingEditModel", bookingEditModel);
	      String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/booking/detail").concat("?id=") + bookingId;
	      redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		 return new ModelAndView(viewName);
	}
	
	@RequestMapping(value = "booking/transfer", method = RequestMethod.POST)
	public ModelAndView postDetailBookingTransfer(
			@ModelAttribute(value = "editModel") ContactBookingEditDto bookingEditModel,
            @RequestParam(value = "id", required = true) Long bookingId,
            Locale locale, RedirectAttributes redirectAttributes){
		  if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING.concat(ConstantCore.COLON_EDIT)) 
				  && !UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING)) {
	          return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	      }
	      ModelAndView mav = new ModelAndView("contact.booking.detail");
	      ContactBookingDto bookingDetail = contactBookingService.processTransferBooking(bookingEditModel, bookingId, locale);
	      MessageList messageList = new MessageList(Message.SUCCESS);
	      String msgError = msg.getMessage(CONTACT_BOOKING_TRANSFER_SUCCESS_MESSAGE, null, locale);
	      messageList.add(msgError);
	      String url = "contact/booking".concat(UrlConst.DETAIL).concat("?id=") + bookingId;
	      bookingDetail.setUrl(url);
	      List<ContactCommentDto> commentOptions = contactBookingService.getCommentOptions();
	      mav.addObject("commentOptions", commentOptions);
	      mav.addObject("bookingModel", bookingDetail);
	      mav.addObject(ConstantCore.MSG_LIST, messageList);
	      mav.addObject("bookingEditModel", bookingEditModel);
	      String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/booking/detail").concat("?id=") + bookingId;
	      redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		 return new ModelAndView(viewName);
	}
	
	@RequestMapping(value = "booking/reject", method = RequestMethod.POST)
	public ModelAndView postDetailBookingReject(
			@ModelAttribute(value = "editModel") ContactBookingEditDto bookingEditModel,
            @RequestParam(value = "id", required = true) Long bookingId,
            Locale locale, RedirectAttributes redirectAttributes){
		  if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING.concat(ConstantCore.COLON_EDIT))
				  && !UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING)) {
	          return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	      }
	      ModelAndView mav = new ModelAndView("contact.booking.detail");
	      ContactBookingDto bookingDetail = contactBookingService.processRejectBooking(bookingEditModel, bookingId, locale);
	      MessageList messageList = new MessageList(Message.SUCCESS);
	      String msgError = msg.getMessage(CONTACT_BOOKING_REJECT_SUCCESS_MESSAGE, null, locale);
	      messageList.add(msgError);
	      String url = "contact/booking".concat(UrlConst.DETAIL).concat("?id=") + bookingId;
	      bookingDetail.setUrl(url);
	      List<ContactCommentDto> commentOptions = contactBookingService.getCommentOptions();
	      mav.addObject("commentOptions", commentOptions);
	      mav.addObject("bookingModel", bookingDetail);
	      mav.addObject(ConstantCore.MSG_LIST, messageList);
	      mav.addObject("bookingEditModel", bookingEditModel);
	      String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/booking/detail").concat("?id=") + bookingId;
	      redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		 return new ModelAndView(viewName);
	 }
	 
	 @RequestMapping(value = "booking/detail", method = RequestMethod.GET)
	 public ModelAndView getDetail(
			 @ModelAttribute(value = "editModel") ContactBookingEditDto bookingEditModel,
            @RequestParam(value = "id", required = true) Long bookingId,
            Locale locale){
		  if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING.concat(ConstantCore.COLON_EDIT))
				  && !UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING)) {
	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	      }
	      ModelAndView mav = new ModelAndView("contact.booking.detail");
	      ContactBookingDto bookingDetail = contactBookingService.getBookingDetail(bookingId, locale);
	      List<ContactBookingUpdateItemDto> updateHistory = contactBookingService.getUpdateHistory(bookingId);
	      List<ContactCommentDto> commentOptions = contactBookingService.getCommentOptions();
	      String url = "contact/booking".concat(UrlConst.DETAIL).concat("?id=") + bookingId;
	      bookingDetail.setUrl(url);
	      mav.addObject("commentOptions", commentOptions);
	      mav.addObject("updateHistory", updateHistory);
	      mav.addObject("bookingModel", bookingDetail);
	      mav.addObject("bookingEditModel", bookingEditModel);
		 return mav;
	 }
	 
	 
	 @RequestMapping(value = "booking/detail/delete", method = RequestMethod.POST)
	 public ModelAndView bookingDetailGetDelete(
            @RequestParam(value = "id", required = true) Long bookingId,
            Locale locale, RedirectAttributes redirectAttributes){
		  if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING.concat(ConstantCore.COLON_DISP))) {
	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	      }
	      ModelAndView mav = null;
	      boolean deleted = contactBookingService.deleteBooking(bookingId);
	      if(!deleted){
	    	  mav = new ModelAndView("contact.booking.detail");
	    	  String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/booking").concat(UrlConst.DETAIL).
	    			  concat("?id=").concat(String.valueOf(bookingId));
	    	  mav = new ModelAndView(viewName);
	          // Init message success list
	          MessageList messageList = new MessageList(Message.ERROR);
	          String msgInfo = msg.getMessage("contact.booking.message.could-not-delete", null, locale);
	          messageList.add(msgInfo);
	          redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
	      }else{
	    	  String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/booking").concat(UrlConst.LIST);
	    	  mav = new ModelAndView(viewName);
	          // Init message success list
	          MessageList messageList = new MessageList(Message.SUCCESS);
	          String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
	          messageList.add(msgInfo);
	          redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
	      }
		  return mav;
	 }
	 
	 @RequestMapping(value = "booking/list/delete", method = RequestMethod.POST)
	 public ModelAndView bookingListGetDelete(
            @RequestParam(value = "id", required = true) Long bookingId,
            Locale locale, RedirectAttributes redirectAttributes){
		  if(!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_BOOKING.concat(ConstantCore.COLON_DISP))) {
	           return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	      }
	      ModelAndView mav = null;
	      boolean deleted = contactBookingService.deleteBooking(bookingId);
    	  String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/booking").concat(UrlConst.LIST);
    	  mav = new ModelAndView(viewName);
          // Init message success list
          MessageList messageList = new MessageList(Message.SUCCESS);
          if(deleted){
        	  messageList.setStatus(Message.SUCCESS);
	          String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
	          messageList.add(msgInfo);
          }else{
        	  messageList.setStatus(Message.ERROR);
        	  String msgInfo = msg.getMessage("contact.booking.message.could-not-delete", null, locale);
	          messageList.add(msgInfo);
          }
          redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		  return mav;
	}
	 
	public void exportBookingToExcel(List<ContactBookingDto> recDto, HttpServletResponse response, Locale locale) throws Exception {
		    // tuanh fix so dem tu 0 ->1
		    int count = 1; 
	        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	        DateFormat timeFormat = new SimpleDateFormat("HH:mm");  
			for(ContactBookingDto bookingDto : recDto){
				bookingDto.setStt(count ++);
				if(bookingDto.getStatusName() != null){
					String statusTitle =  msg.getMessage(bookingDto.getStatusName(), null, locale);
					bookingDto.setStatusTitle(statusTitle);
				}
				if(bookingDto.getPhoneNumber() != null){
					bookingDto.setPhoneNumber(bookingDto.getPhoneNumber());
				}
				if(bookingDto.getUpdateDate() != null){
				    bookingDto.setUpdateDate((Date)bookingDto.getUpdateDate());
				}
				if(!StringUtils.isEmpty(bookingDto.getCommentCode())){
					String comment = msg.getMessage(bookingDto.getCommentCode(), null, locale);
					bookingDto.setComment(comment);
				}
				if(null != bookingDto.getDateBooking()){
					bookingDto.setDateBookingExport(dateFormat.format(bookingDto.getDateBooking()));
				}
				if(null != bookingDto.getTimeBooking()){
					bookingDto.setTimeBookingExport(timeFormat.format(bookingDto.getTimeBooking()));
				}
				if(null != bookingDto.getCreateDate()){
					bookingDto.setCreateDateExport(dateFormat.format(bookingDto.getCreateDate()));
				}
			}
	        String templateName = CmsCommonConstant.TEMPLATE_CONTACTBOOKING;
	        String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName;
	        String datePattent = "dd-MM-yyyy  HH:mm:ss";
	        List<ItemColsExcelDto> cols = new ArrayList<ItemColsExcelDto>();
	        ConstantImport.setListColumnExcel(ContactBookingExportEnum.class, cols);	
		//	ConstantExport<ContactBookingDto> exportExcel = new ConstantExport<ContactBookingDto>();	
			ExportExcelUtil<ContactBookingDto> exportExcel = new ExportExcelUtil<>();
			try {
	      //      exportExcel.exportExcel(template, locale, recDto, ContactBookingDto.class, cols, datePattent);
	            exportExcel.exportExcelWithXSSFNonPass(template, locale, recDto, ContactBookingDto.class, cols, datePattent, response, templateName);
	        } catch (Exception e1) {
	            logger.error("__exportBookingToExcel__", e1);
	        }
		}
}

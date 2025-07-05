package vn.com.unit.cms.admin.all.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.controller.common.CmsCommonController;
import vn.com.unit.cms.admin.all.dto.OrderEditDto;
import vn.com.unit.cms.admin.all.enumdef.OrderDetailExportEnum;
import vn.com.unit.cms.admin.all.enumdef.OrderGeneralExportEnum;
import vn.com.unit.cms.admin.all.jcanary.common.util.ConstantImport;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.OrderService;
import vn.com.unit.cms.admin.all.validator.DocumentCategoryValidator;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.order.dto.OrderLanguageSearchDto;
import vn.com.unit.cms.core.module.order.dto.OrderSearchDto;
import vn.com.unit.cms.core.module.product.dto.ProductSearchDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.Utils;

@Controller
@RequestMapping(value = UrlConst.ROOT + "order")
public class OrderController extends
CmsCommonController<OrderSearchDto, OrderLanguageSearchDto, OrderEditDto> {

	private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DocumentCategoryValidator categoryValidator;

    @Autowired
    private MessageSource msg;

    @Autowired
    private OrderService orderService;

    @Autowired
    private Select2DataService select2DataService;
    
    @Autowired
	ServletContext servletContext;

    private static final String VIEW_EDIT = "views/CMS/all/order/order-edit.html";

    private static final String VIEW_LIST = "views/CMS/all/order/order-list.html";
    
    private static final String VIEW_TABLE = "views/CMS/all/order/order-table.html";

    @Override
    public String viewList() {
        return VIEW_LIST;
    }
    
    @Override
    public String viewListTable() {
        return VIEW_TABLE;
    }
    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
    		OrderSearchDto searchDto, Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

    }

    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("order.list.title", null, locale);
    }

    @Override
    public void initScreenEdit(ModelAndView mav, OrderEditDto editDto, Locale locale) {
    	List<Select2Dto> listStatusOrder = select2DataService.getConstantData("M_ORDER", "STATUS_EDIT", locale.toString());
   	 	mav.addObject("listStatusOrder", listStatusOrder);

    }

    @Override
    public String getFunctionCode() {
        return "M_ORDER";
    }

    @Override
    public String getTableForGenCode() {
        return "M_ORDER";
    }

    @Override
    public String getPrefixCode() {
        return "OD";
    }

    @Override
    public String viewListSort() {
        return null;
    }

    @Override
    public String viewListSortTable() {
        return null;
    }

    @Override
    public String viewEdit() {
        return VIEW_EDIT;
    }


    @Override
    public Class<OrderLanguageSearchDto> getClassSearchResult() {
        return OrderLanguageSearchDto.class;
    }

    @Override
    public boolean hasRoleForList() {
        return UserProfileUtils.hasRole("SCREEN#CMS#PAGE_LIST_ORDER");
    }
    @Override
    public boolean hasRoleForEdit() {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_ORDER_EDIT.concat(CoreConstant.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForDetail() {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_ORDER);
    }

    @Override
    public CmsCommonSearchFillterService<OrderSearchDto, OrderLanguageSearchDto, OrderEditDto> getCmsCommonSearchFillterService() {
        return orderService;
    }

    @Override
    public void validate(OrderEditDto editDto, BindingResult bindingResult) {
        categoryValidator.validate(editDto, bindingResult);
    }

	@Override
	public String getUrlByAlias() {
		return "order";
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_RESULT_GROUP_DETAIL_EXPORT)
	public ResponseEntity exportResultGroupDetailByAgentGroup(HttpServletRequest request,@ModelAttribute ProductSearchDto searchDto,Locale locale) {
		ResponseEntity resObj = null;
		return resObj;
	}
	
	@RequestMapping(value = "/export-general", method = { RequestMethod.POST })
	@ResponseBody
	public void exportGeneral(@ModelAttribute(value = "searchDto") OrderSearchDto searchDto,
			Locale locale,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Utils.addCookieForExport(searchDto.getToken(), request, response);
		List<OrderLanguageSearchDto> orderList =  orderService.getListForExportGeneral();
		exportGeneralToExcel(orderList, response, locale);
	}
	
	private void exportGeneralToExcel(List<OrderLanguageSearchDto> recDto, HttpServletResponse response, Locale locale) throws Exception {
        String templateName = "OrderGeneral.xlsx";
        String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName;
        String datePattent = "dd-MM-yyyy HH:mm:ss";
        List<ItemColsExcelDto> cols = new ArrayList<ItemColsExcelDto>();
        ConstantImport.setListColumnExcel(OrderGeneralExportEnum.class, cols);
		ExportExcelUtil<OrderLanguageSearchDto> exportExcel = new ExportExcelUtil<>();
		try {
            exportExcel.exportExcelWithXSSFNonPass(template, locale, recDto, OrderLanguageSearchDto.class, cols, datePattent, response, templateName);
        } catch (Exception e1) {
            logger.error("__exportBookingToExcel__", e1);
        }
	}
	
	@RequestMapping(value = "/export-detail", method = { RequestMethod.POST })
	@ResponseBody
	public void exportDetail(@ModelAttribute(value = "searchDto") OrderSearchDto searchDto,
			Locale locale,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Utils.addCookieForExport(searchDto.getToken(), request, response);
		List<OrderLanguageSearchDto> orderList =  orderService.getListForExportDetail();
		exportDetailToExcel(orderList, response, locale);
	}
	
	private void exportDetailToExcel(List<OrderLanguageSearchDto> recDto, HttpServletResponse response, Locale locale) throws Exception {
        String templateName = "OrderDetail.xlsx";
        String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName;
        String datePattent = "dd-MM-yyyy HH:mm:ss";
        List<ItemColsExcelDto> cols = new ArrayList<ItemColsExcelDto>();
        ConstantImport.setListColumnExcel(OrderDetailExportEnum.class, cols);
		ExportExcelUtil<OrderLanguageSearchDto> exportExcel = new ExportExcelUtil<>();
		try {
            exportExcel.exportExcelWithXSSFNonPass(template, locale, recDto, OrderLanguageSearchDto.class, cols, datePattent, response, templateName);
        } catch (Exception e1) {
            logger.error("__exportBookingToExcel__", e1);
        }
	}
}

/*******************************************************************************
 * Class        ：ExchangeRateController
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.cms.admin.all.dto.CurrencyDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateListDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateSearchDto;
import vn.com.unit.cms.admin.all.dto.ExchnageRateHistoryDto;
import vn.com.unit.cms.admin.all.enumdef.ExchangeRateImportEnum;
import vn.com.unit.cms.admin.all.enumdef.ExchangeRateSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.common.util.ConstantImport;
import vn.com.unit.cms.admin.all.service.CurrencyService;
import vn.com.unit.cms.admin.all.service.ExchangeRateService;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.SearchUtil;
//import vn.com.unit.util.ConstantImport;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;

/**
 * ExchangeRateController
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Controller
@RequestMapping(UrlConst.ROOT + UrlConst.EXCHANGE_RATE)
public class ExchangeRateController {
	@Autowired
	ExchangeRateService exRateService;
	
	@Autowired
	CurrencyService currencyService;
	
	@Autowired
	private MessageSource msg;
	
	@Autowired
	private SystemConfig systemConfig;

	private String EXCHANGE_RATE_LIST = "exchange.rate.list";
	private String EXCHANGE_RATE_EDIT = "exchange.rate.edit";
	private String EXCHANGE_RATE_TABLE = "exchange.rate.table";
	private String EXCHANGE_RATE_TABLE_HISTORY = "exchange.rate.table.history";
	
	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
			request.getSession().setAttribute("formatDate", "dd-MM-yyyy");
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
	/**
	 * getList
	 *
	 * @param typeSearch
	 * @param page
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET })
	public ModelAndView getList(@ModelAttribute(value = "typeSearch") ExchangeRateSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView(EXCHANGE_RATE_LIST);
		// search
		SearchUtil.setSearchSelect(ExchangeRateSearchEnum.class, mav);
		//set language code
		typeSearch.setLangCode(locale.toString());
		PageWrapper<ExchangeRateDto> pageWrapper = exRateService.findAllActive(page, typeSearch);

		if (pageWrapper.getCountAll() == 0) {
			// Init message list
			MessageList messageList = new MessageList(Message.INFO);
			String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
			messageList.add(msgInfo);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
		}
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		mav.addObject("typeSearch", typeSearch);
		return mav;
	}
	/**
	 * ajaxList
	 *
	 * @param typeSearch
	 * @param page
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxList(@ModelAttribute(value = "typeSearch") ExchangeRateSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView(EXCHANGE_RATE_TABLE);
		//set language code
		typeSearch.setLangCode(locale.toString());
		PageWrapper<ExchangeRateDto> pageWrapper = exRateService.findAllActive(page, typeSearch);
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		return mav;
	}
	/**
	 * addOrEditEchangeRate
	 *
	 * @param id
	 * @param typeSearch
	 * @param page
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.GET })
	public ModelAndView addOrEditEchangeRate(@RequestParam(value = "id", required = false) Long id,
			@ModelAttribute(value = "typeSearch") ExchangeRateSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
			Locale locale) {
		ModelAndView mav = new ModelAndView(EXCHANGE_RATE_EDIT);		
		// get list exchange rate latest
		List<ExchangeRateDto> listExchangerate = new ArrayList<ExchangeRateDto>();
		String langeCode = locale.toString();
		listExchangerate = exRateService.getListExchangeRateById(id, langeCode);
		ExchangeRateListDto exchangeRateListDto = new ExchangeRateListDto();
		exchangeRateListDto.setData(listExchangerate);
		exchangeRateListDto.setExchangeRateId(id);
		//set url redirec
		String url = UrlConst.EXCHANGE_RATE.concat(UrlConst.EDIT);        
		exchangeRateListDto.setUrl(url);
		mav.addObject("ExRateEdit",exchangeRateListDto);
		
		// get list history update exchange rate		
		SearchUtil.setSearchSelect(ExchangeRateSearchEnum.class, mav);
		//set language code
		typeSearch.setLangCode(locale.toString());	
		typeSearch.setDisplayDate(new Date());	
		PageWrapper<ExchnageRateHistoryDto> pageWrapper = exRateService.findAllActiveHistory(page, typeSearch);
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		mav.addObject("typeSearch", typeSearch);
		return mav;
	}
	/**
	 * postAjaxEdit
	 *
	 * @param exchangeRateListDto
	 * @param bindingResult
	 * @param locale
	 * @param redirectAttributes
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = UrlConst.AJAX_EDIT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView postAjaxEdit(
    		@ModelAttribute(value = "exchangeRateListDto") ExchangeRateListDto exchangeRateListDto,    		
    		BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView(EXCHANGE_RATE_EDIT);              
        
        MessageList messageList = new MessageList(Message.SUCCESS);
        if (bindingResult.hasErrors()) {        	
			// Add message error
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
			messageList.add(msgError);
			mav.addObject(ConstantCore.MSG_LIST, messageList);	
			
		}
        else{
        	exchangeRateListDto.setLangCode(locale.toString());
            exRateService.CreateOrEditExchangeRate(exchangeRateListDto);            
            String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
    		messageList.add(msgInfo);
    		mav.addObject(ConstantCore.MSG_LIST, messageList);	
        }	
        //set url redirec
      	String url = UrlConst.EXCHANGE_RATE.concat(UrlConst.EDIT);        
      	exchangeRateListDto.setUrl(url);
        //redirect    
      	String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.EXCHANGE_RATE).concat(UrlConst.EDIT);
      	mav.setViewName(viewName);
      	redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        return mav;
    }
	/**
	 * uploadExcel
	 *
	 * @param multipartHttpServletRequest
	 * @param page
	 * @param locale
	 * @param redirectAttributes
	 * @return ModelAndView
	 * @throws Exception
	 * @author phunghn
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/upload-excel", method = RequestMethod.POST)  
	@ResponseBody
    public ModelAndView  uploadExcel(MultipartHttpServletRequest multipartHttpServletRequest,
    		@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
    		Locale locale,
    		RedirectAttributes redirectAttributes) throws Exception {
		ExchangeRateListDto exchangeRateListDto = new ExchangeRateListDto();
		Iterator<String> itr = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = multipartHttpServletRequest.getFile(itr.next());
		if (multipartFile == null) {
			throw new Exception("Vui lòng nhập file import");
		}
		String contentType = multipartFile.getContentType();
		
		Workbook workbook = null;

		if (contentType.equals("application/vnd.ms-excel")) {
			workbook = new HSSFWorkbook(multipartFile.getInputStream());
		} else {
			workbook = new XSSFWorkbook(multipartFile.getInputStream());
		}
		//
		int isResult ;
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		//get row header
		Row rowHeader = iterator.next();
		// set row header to list
		List<ItemColsExcelDto> cols = new ArrayList<ItemColsExcelDto>();			
		ConstantImport.setListColumnExcel(ExchangeRateImportEnum.class,cols);		
		// check file invalid
	
		if(ConstantImport.isValidFileExcelImport(rowHeader,cols)){
			// get data
			ConstantImport<ExchangeRateDto> exRateExcel = new ConstantImport<ExchangeRateDto>();
			exRateExcel.setDataFileExcel(iterator, cols, rowHeader, ExchangeRateDto.class);						
			if(exRateExcel.getData()==null){
				isResult = 2;
			}
			else{
				// set data
				List<ExchangeRateDto> list = new ArrayList<ExchangeRateDto>();
				Map<Long,CurrencyDto> map = new HashMap<Long,CurrencyDto>();
				map = buildMapCurrency(locale.toString());
				for(ExchangeRateDto item : exRateExcel.getData()){
					if(map.get(item.getmCurrencyId()) != null){
						CurrencyDto currencyDto =  map.get(item.getmCurrencyId());
						item.setmCurrencyName(currencyDto.getName());
						list.add(item);
					}
				}				
				exchangeRateListDto.setLangCode(locale.toString());				
				exchangeRateListDto.setData(list);
				isResult = 0;
			}						
		}	
		else{
			isResult = 1;
		}		
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.EXCHANGE_RATE).concat(UrlConst.EDIT);
        ModelAndView mav = new ModelAndView(viewName);
		MessageList messageList = new MessageList(Message.SUCCESS);
		if(isResult == 0){
			//check currency valid 
			
			// binding data to view
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
			messageList.add(msgInfo);
			String url = UrlConst.EXCHANGE_RATE.concat(UrlConst.EDIT);
			exchangeRateListDto.setUrl(url);
			mav.setViewName(EXCHANGE_RATE_EDIT);
			mav.addObject("ExRateEdit", exchangeRateListDto);
			//
			// get list history update exchange rate		
			SearchUtil.setSearchSelect(ExchangeRateSearchEnum.class, mav);
			//set language code
			ExchangeRateSearchDto typeSearch = new ExchangeRateSearchDto();
			typeSearch.setLangCode(locale.toString());		
			typeSearch.setDisplayDate(new Date());	
			PageWrapper<ExchnageRateHistoryDto> pageWrapper = exRateService.findAllActiveHistory(page, typeSearch);
			mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
			mav.addObject("typeSearch", typeSearch);
			return mav;
		}
		else if(isResult==1){
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MESSAGE_INVALID_COLUMN_EXCEL, null, locale);
			messageList.add(msgError);				
		}
		else if(isResult==2){
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MESSAGE_INVALID_COLUMN_EXCEL_NOTFOUND_DATA, null, locale);
			messageList.add(msgError);				
		}	
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return mav;
    }
	private Map<Long,CurrencyDto> buildMapCurrency(String language){
		List<CurrencyDto> listCurrency = new ArrayList<CurrencyDto>();
		listCurrency = currencyService.findAllActive(language);
		Map<Long,CurrencyDto> map = new HashMap<Long,CurrencyDto>();
		for(CurrencyDto item : listCurrency){
			map.put(item.getId(),item);
		}
		return map;
		
	}
	/**
	 * ajaxListHistory
	 *
	 * @param typeSearch
	 * @param page
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = "/ajaxListHistory", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxListHistory(@ModelAttribute(value = "typeSearch") ExchangeRateSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView(EXCHANGE_RATE_TABLE_HISTORY);
		//set language code
		typeSearch.setLangCode(locale.toString());		
		PageWrapper<ExchnageRateHistoryDto> pageWrapper = exRateService.findAllActiveHistory(page, typeSearch);
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		return mav;
	}
	
}


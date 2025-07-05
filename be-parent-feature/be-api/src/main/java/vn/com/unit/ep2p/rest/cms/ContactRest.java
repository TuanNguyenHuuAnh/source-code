package vn.com.unit.ep2p.rest.cms;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;

//import java.util.List;

//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.ModelAttribute;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;

//import javax.servlet.http.HttpServletRequest;

//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.contact.dto.CmsContactDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
//import vn.com.unit.common.utils.CommonStringUtil;
//import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.dto.res.CheckCaptchaRes;
//import vn.com.unit.ep2p.core.constant.AppApiConstant;
//import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementDto;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.CmsContactService;
import vn.com.unit.ep2p.service.ReCaptchaService;
//import vn.com.unit.ep2p.utils.LangugeUtil;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_CONTACT)
@Api(tags = { CmsApiConstant.API_CMS_CONTACT_DESCR })
public class ContactRest extends AbstractRest {

	@Autowired
	CmsContactService cmsContactService;
	
	@Autowired
	Select2DataService select2DataService;

	@Autowired
	private ReCaptchaService reCaptchaService;
//	@GetMapping(AppApiConstant.API_NEWS_LIST)
//	@ApiOperation("Api provides a list News on systems")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
//			@ApiResponse(code = 500, message = "Internal Server Error"),
//			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
//			@ApiResponse(code = 402601, message = "Error process list contact") })
//	public DtsApiResponse listNews(HttpServletRequest request, ContactEmailSearchDto searchDto,
//			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
//		long start = System.currentTimeMillis();
//
//		Locale locale = LangugeUtil.getLanguageFromHeader(request); // locale.getLanguage()
//
//		try {
//			List<ContactEmailSearchDto> datas = new ArrayList<>();
//			int count = cmsContactService.countByCondition(searchDto);
//			if (count > 0) {
//				datas = cmsContactService.searchByCondition(searchDto, page, size, locale.getLanguage());
//			}
//
//			ObjectDataRes<ContactEmailSearchDto> resObj = new ObjectDataRes<>(count, datas);
//
//			return this.successHandler.handlerSuccess(resObj, start, null, null);
//		} catch (Exception ex) {
//			return this.errorHandler.handlerException(ex, start, null, null);
//		}
//	}

//	@PostMapping("/")
	// , consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	@RequestMapping(path = "", method = RequestMethod.POST)
//	public DtsApiResponse create(@RequestParam("file") MultipartFile[] file, @RequestBody CmsContactDto dto) {
	// @ModelAttribute CmsContactDto dto,
//	public DtsApiResponse create(@RequestParam MultipartFile[] file, @RequestParam(required = true) String name,
//			@RequestParam(required = true) String email, @RequestParam(required = true) String address,
//			@RequestParam(required = true) String phone, @RequestParam(required = true) String title,
//			@RequestParam(required = true) String content) {
	public DtsApiResponse create(@ModelAttribute CmsContactDto dto, HttpServletRequest request) {
		// TODO Need config permission here
//		if (!hasRoleEdit()) {
//			return this.errorHandler.handlerException(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString());
//		}

		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
//			CmsContactDto dto = new CmsContactDto();
//
//			dto.setName(name);
//			dto.setEmail(email);
//			dto.setAddress(address);
//			dto.setPhone(phone);
//			dto.setTitle(title);
//			dto.setContent(content);
			String key = dto.getAgentCode();
        	CheckCaptchaRes captchaRes = reCaptchaService.verifyCaptcha(key, dto.getValueToken());
			if (captchaRes != null) {
				return this.successHandler.handlerSuccess(captchaRes, start, key, null);
			}
			dto.init();
			
			List<Select2Dto> subject = select2DataService.getConstantData("CMS_CONTACT_EMAIL", "CMS_CONTACT_EMAIL", dto.getTitle(), locale.getLanguage());
			if(CommonCollectionUtil.isNotEmpty(subject)) {
				dto.setTitleName(subject.get(0).getName());
			}
			
			CmsContactDto resObj = cmsContactService.create(dto, request);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			System.out.print(ex);
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

}

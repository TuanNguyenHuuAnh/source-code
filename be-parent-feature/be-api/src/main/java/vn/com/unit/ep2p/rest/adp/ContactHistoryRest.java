package vn.com.unit.ep2p.rest.adp;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.adp.dto.CareContactPolicyDto;
import vn.com.unit.ep2p.adp.dto.CareContactPolicySearchDto;
import vn.com.unit.ep2p.adp.dto.ContactHistoryDto;
import vn.com.unit.ep2p.adp.dto.PolicyContactHistoryDto;
import vn.com.unit.ep2p.adp.dto.PolicyContactHistorySearchDto;
import vn.com.unit.ep2p.adp.service.AdportalService;
import vn.com.unit.ep2p.adp.service.ContactHistoryService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/contact-history")
@Api(tags = {"API For Contact History Management Desc"})
public class ContactHistoryRest {

    @Autowired
    protected ErrorHandler errorHandler;

    @Autowired
    protected SuccessHandler successHandler;

    @Autowired
    private AdportalService adpService;
    
    @Autowired
    private ContactHistoryService contactHistoryService;
    
    @Autowired
    MessageSource messageSource;

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String FUNCTION_ID = "SCREEN#FONTEND#CUSTOMER_CARE_CONTACT";

    @GetMapping("/get-contact-history-by-policy")
    @ApiOperation("Get contact history by policy no")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getContactHistoryByPolicy(@RequestParam(value = "policyNo") String policyNo) {
        long start = System.currentTimeMillis();
        try {
        	CmsCommonPagination<ContactHistoryDto> common = adpService.getContactHistoryByPolicy(UserProfileUtils.getFaceMask(), policyNo);
            ObjectDataRes<ContactHistoryDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            logger.error("##getAgentInfo##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping("/edit-contact-history")
    @ApiOperation("Api add/edit contact history")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse eidtContactHistory(HttpServletRequest request, @RequestBody ContactHistoryDto dto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
        	ContactHistoryDto resObj = null;
            if (dto.getId() == null) {
            	resObj = contactHistoryService.addContactHistory(dto);
            } else {
            	resObj = contactHistoryService.updateContactHistory(dto);
            }
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping("/get-list-care-contact-policy")
    @ApiOperation("Get all care and contact policy by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListCareContactPolicy(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, 
    		@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, CareContactPolicySearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(page);
            searchDto.setPageSize(pageSize);

            // Lấy dữ liệu từ service
            CmsCommonPagination<CareContactPolicyDto> common = adpService.getListCareContactPolicy(searchDto);

            // Lấy danh sách dữ liệu
            List<CareContactPolicyDto> dataList = common.getData();

            // Tạo đối tượng phản hồi
            ObjectDataRes<CareContactPolicyDto> resObj = new ObjectDataRes<>(common.getTotalData(), dataList);

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getListCareContactPolicy##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/export-list-care-contact-policy")
    @ApiOperation("Api export excel list care and contact policy")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportListCareContactPolicy(HttpServletRequest request, HttpServletResponse response, @RequestBody CareContactPolicySearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        searchDto.setLanguage(locale.getLanguage());
        ResponseEntity resObj = null;
        try {
             resObj = adpService.exportListCareContactPolicy(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportListCareContactPolicy##", e.getMessage());
        }
        return resObj;
    }
    
    @GetMapping("/get-contact-history-by-agent")
    @ApiOperation("Get contact history by agent code")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getContactHistoryByAgent(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, 
    		@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, PolicyContactHistorySearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	CmsCommonPagination<PolicyContactHistoryDto> common = adpService.getContactHistoryByAgent(searchDto);
            ObjectDataRes<PolicyContactHistoryDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            logger.error("##getAgentInfo##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/export-contact-history-by-agent")
    @ApiOperation("Api export excel list care and contact policy")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportListContactHistoryByAgent(HttpServletRequest request, HttpServletResponse response, @RequestBody PolicyContactHistorySearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        searchDto.setLanguage(locale.getLanguage());
        ResponseEntity resObj = null;
        try {
             resObj = adpService.exportListContactHistoryByAgent(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportListContactHistoryByAgent##", e.getMessage());
        }
        return resObj;
    }
}


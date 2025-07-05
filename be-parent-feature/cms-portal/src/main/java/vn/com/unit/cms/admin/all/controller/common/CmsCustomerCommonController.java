package vn.com.unit.cms.admin.all.controller.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hornetq.utils.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.dto.UploadDto;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.util.HDBankUtil;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;
import vn.com.unit.cms.core.module.banner.dto.ImageDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.dto.JcaDatatableDefaultConfigDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;
import vn.com.unit.imp.excel.utils.Utils;

/**
 * @author TaiTM
 * @description Controller common giành cho CMS có sử dụng customerAlias
 */
public abstract class CmsCustomerCommonController<SEARCH_DTO extends CmsCommonSearchFilterDto, SEARCH_RESULT_DTO extends CmsCommonSearchResultFilterDto, EDIT_DTO extends CmsCommonEditDto> {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private MessageSource msg;

    @Autowired
    private CmsFileService fileService;
    
    @Autowired
    private CmsCommonService cmsCommonService;
    
    private static final String VIEW_LIST = "views/CMS/all/common/common-list.html";
    private static final String VIEW_TABLE = "views/CMS/all/common/common-table.html";
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Logger getLogger() {
        return logger;
    }

    /**
     * @author TaiTM
     * @description Dùng để Khởi tạo dữ liệu ban đầu để đổ vào SELECT BOX/CHECK
     *              BOX/INPUT cho màn hình List search
     */
    public void initScreenList(ModelAndView mav, SEARCH_DTO searchDto, Locale locale) {
        // TODO
    }
    
    /**
     * @author TaiTM
     * @description Tiêu đề của trang List Search
     */
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("searchfield.list.title", null, locale);
    }
    
    /**
     * @author TaiTM
     * @description Có hiện nút export excel hay không
     */
    public boolean hasExportExcel() {
        return false;
    }
    
    /**
     * @author TaiTM
     * @description Dùng để Khởi tạo dữ liệu ban đầu để đổ vào SELECT BOX/CHECK
     *              BOX/INPUT cho màn hình List Sort
     */
    abstract public void initScreenListSort(ModelAndView mav, SEARCH_DTO searchDto, Locale locale);

    /**
     * @author TaiTM
     * @description Dùng để Khởi tạo dữ liệu ban đầu để đổ vào SELECT BOX/CHECK
     *              BOX/INPUT cho màn hình Edit/Detail
     */
    abstract public void initScreenEdit(ModelAndView mav, EDIT_DTO editDto, Locale locale);

    /**
     * @author TaiTM
     * @description Là FUNCTION_CODE dùng truy vẫn dữ liệu từ bảng
     *              JCA_DATATABLE_CONFIG_DEFAULT để lấy ra các cột ở danh sách table
     *              trong màn hình List search
     */
    abstract public String getFunctionCode();

    /**
     * @author TaiTM
     * @description Attribute name của search Dto khi add vào ModelAndView của màn
     *              hình List search. Mặc định có giá trị là "searchDto"
     * @constant searchDto
     */
    public String mavObjectSearchName() {
        return "searchDto";
    }

    /**
     * @author TaiTM
     * @description Attribute name của edit Dto khi add vào ModelAndView của màn
     *              hình Edit/Detail. Mặc định có giá trị là "editDto"
     * @constant editDto
     */
    public String mavObjectEditName() {
        return "editDto";
    }
    
    /**
     * @author TaiTM
     * @description Tên của TABLE để truy vấn, phục vụ cho việc tạo code tự động của
     *              dữ liệu
     * @example "M_EXAMPLE"
     */
    abstract public String getTableForGenCode();

    /**
     * @author TaiTM
     * @description Mã tiền tố dùng để tạo mã
     * @example "EXP"
     */
    abstract public String getPrefixCode();

    /**
     * @author TaiTM
     * @description Đường dẫn để trỏ để file HTML list
     * @example "views/CMS/example/example-list.html"
     */
    public String viewList(String customerAlias) {
        return VIEW_LIST;
    }

    /**
     * @author TaiTM
     * @description Đường dẫn để trỏ để file HTML table
     * @example "views/CMS/example/example-table.html"
     */
    public String viewListTable(String customerAlias) {
        return VIEW_TABLE;
    }

    /**
     * @author TaiTM
     * @description Đường dẫn để trỏ để file HTML list sort
     * @example "views/CMS/example/example-list-sort.html"
     */
    abstract public String viewListSort(String customerAlias);

    /**
     * @author TaiTM
     * @description Đường dẫn để trỏ để file HTML list sort table
     * @example "views/CMS/example/example-list-sort-table.html"
     */
    abstract public String viewListSortTable(String customerAlias);

    /**
     * @author TaiTM
     * @description Đường dẫn để trỏ để file HTML edit
     * @example "views/CMS/example/example-edit.html"
     */
    abstract public String viewEdit(String customerAlias);

    /**
     * @author TaiTM
     * @description Link của controller, phục vụ cho việc điều hướng trang khi Thêm
     *              mới/Chỉnh sửa/Xoá/Cập nhật vị trí thành công
     * @example "customerAlias/example"
     */
    abstract public String getUrlByCustomerAlias(String customerAlias);

    /**
     * @author TaiTM
     * @description Class của Search result Dto, dùng lấy thông tin các cột cho
     *              table và export excel
     */
    abstract public Class<SEARCH_RESULT_DTO> getClassSearchResult();
    
    /**
     * @author TaiTM
     * @description Trả về giá trị để kiểm tra là có quyền vào màn hình List search
     *              hay không
     * @example UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_EXAMPLE);
     */
    abstract public boolean hasRoleForList(String customerAlias);

    /**
     * @author TaiTM
     * @description Trả về giá trị để kiểm tra là có quyền thêm mới/chỉnh sửa
     * @example UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_EXAMPLE_EDIT.concat(CoreConstant.COLON_EDIT));
     */
    abstract public boolean hasRoleForEdit(String customerAlias);

    /**
     * @author TaiTM
     * @description Trả về giá trị để kiểm tra là có quyền xoá dữ liệu hay không.
     *              Mặc định sẽ là quyền Edit
     */
    public boolean hasRoleForDelete(String customerAlias) {
        // Mặc định là quyền edit
        return hasRoleForEdit(customerAlias);
    }

    /**
     * @author TaiTM
     * @description Trả về giá trị để kiểm tra là có quyền vào màn hình List sort và
     *              cập nhật dữ liệu ở màn hình này hay không. Mặc định sẽ là quyền
     *              Edit
     */
    public boolean hasRoleForListSort(String customerAlias) {
        // Mặc định là quyền edit
        return hasRoleForEdit(customerAlias);
    }

    /**
     * @author TaiTM
     * @description Trả về giá trị để kiểm tra là có quyền vào màn hình Detail hay
     *              không
     * @example UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_EXAMPLE);
     */
    abstract public boolean hasRoleForDetail(String customerAlias);

    /**
     * @author TaiTM
     * @description Service được implement CmsCommonSearchFillterService để xử lý
     *              các nghiệp vụ
     * @example ExampleService
     */
    abstract public CmsCommonSearchFillterService<SEARCH_DTO, SEARCH_RESULT_DTO, EDIT_DTO> getCmsCommonSearchFillterService();

    /**
     * @author TaiTM
     * @description Validate dữ liệu khi Thêm mới/Chỉnh sửa
     * @example exampleValidator.validate(editDto, bindingResult);
     */
    abstract public void validate(EDIT_DTO editDto, BindingResult bindingResult);

    /**
     * @author TaiTM
     * @description Định dạng dữ liệu mặc định khi nhận dữ liệu truyền từ client
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
            request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
        }
        // The date format to parse or output your dates
        String patternDate = ConstantCore.FORMAT_DATE_FULL;
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));

        customeBinder(binder, request, locale);
    }

    /**
     * @author TaiTM
     * @description Thêm các dạng định dạng dữ liệu tự định nghĩa khi nhận dữ liệu
     *              truyền từ client
     */
    public void customeBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        // TODO: Override
    }
    
    /**
     * @author TaiTM
     * @description Format date
     */
    public void customDateFormat(ModelAndView mav) {
        mav.addObject("dateFormat", "FULL_DATE");
    }

    /**
     * @author TaiTM
     * @description Màn hình List search. Cấu trúc của màn hình bao gồm 
     *  1. checkRole : Kiểm tra phân quyền 
     *  2. doSeach : thực hiện chức năng search 
     *  3. nitScreen : Khởi tạo dữ liệu ban đầu màn hình
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET })
    public ModelAndView doGetList(@PathVariable("customerAlias") String customerAlias,
            @ModelAttribute SEARCH_DTO searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Pageable pageable,
            HttpServletRequest request, Locale locale) throws DetailException {
        // Start check Role
        if (!hasRoleForList(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End check role
        
        ModelAndView mav = new ModelAndView(viewList(customerAlias));

        // Start do Search
        int pageSize = pageSizeParam
                .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
        int page = pageParam.orElse(1);

        // Session Search
        ConditionSearchUtils<SEARCH_DTO> searchUtil = new ConditionSearchUtils<SEARCH_DTO>();
        String[] urlContains = new String[] { getUrlByCustomerAlias(customerAlias)+"/add", getUrlByCustomerAlias(customerAlias)+"/edit", getUrlByCustomerAlias(customerAlias)+"/detail", getUrlByCustomerAlias(customerAlias)+"/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);
        // End do Session


        searchDto.setFunctionCode(getFunctionCode());
        searchDto.setLanguageCode(locale.toString());
        searchDto.setCustomerTypeId(HDBankUtil.getCustomerType(customerAlias));
        
        PageWrapper<SEARCH_RESULT_DTO> pageWrapper = getCmsCommonSearchFillterService().doSearch(page, pageSize, searchDto, pageable);

        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);


        // End do Search
        
        // Start init Screen
        mav.addObject("customerAlias", customerAlias);
        initScreenList(mav, searchDto, locale);
        initListColumn(mav, searchDto, getClassSearchResult(), request, locale);
        initListSearchFilter(mav, searchDto, locale);
        mav.addObject(mavObjectSearchName(), searchDto);
        mav.addObject("controllerUrl", getUrlByCustomerAlias(customerAlias));
        mav.addObject("functionCode", getFunctionCode());
        mav.addObject("hasRoleForEdit", hasRoleForEdit(customerAlias));
        mav.addObject("hasRoleForDetail", hasRoleForDetail(customerAlias));
        mav.addObject("hasRoleForDelete", hasRoleForDelete(customerAlias));
        mav.addObject("hasRoleForListSort", hasRoleForListSort(customerAlias));
        mav.addObject("titlePageList", getTitlePageList(locale));
        mav.addObject("showExportExcel", hasExportExcel());
        customDateFormat(mav);
        // End init Screen
        
        return mav;
    }

    /**
     * @author TaiTM
     * @description Chức năng ajax search
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@PathVariable("customerAlias") String customerAlias,
            @ModelAttribute SEARCH_DTO searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Pageable pageable,
            HttpServletRequest request, Locale locale) throws DetailException {

        // Start check role
        if (!hasRoleForList(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End check role
        
        ModelAndView mav = new ModelAndView(viewListTable(customerAlias));

        // Start do Search
        int pageSize = pageSizeParam
                .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
        int page = pageParam.orElse(1);
        
        searchDto.setFunctionCode(getFunctionCode());
        searchDto.setLanguageCode(locale.toString());
        searchDto.setCustomerTypeId(HDBankUtil.getCustomerType(customerAlias));
        int typeSearch = searchDto.getSearchType();

        // Session Search
        PageWrapper<SEARCH_RESULT_DTO> pageWrapper = getCmsCommonSearchFillterService().doSearch(page, pageSize, searchDto, pageable);
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
        // End do Session Search

        ConditionSearchUtils<SEARCH_DTO> searchUtil = new ConditionSearchUtils<SEARCH_DTO>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        // End do Session

        searchDto.setSearchType(typeSearch);
        // End do Search
        
        // Start init Screen
        mav.addObject("customerAlias", customerAlias);
         initScreenList(mav, searchDto, locale);
        initListColumn(mav, searchDto, getClassSearchResult(), request, locale);
        initListSearchFilter(mav, searchDto, locale);
        mav.addObject(mavObjectSearchName(), searchDto);
        mav.addObject("controllerUrl", getUrlByCustomerAlias(customerAlias));
        mav.addObject("functionCode", getFunctionCode());
        mav.addObject("hasRoleForEdit", hasRoleForEdit(customerAlias));
        mav.addObject("hasRoleForDetail", hasRoleForDetail(customerAlias));
        mav.addObject("hasRoleForDelete", hasRoleForDelete(customerAlias));
        mav.addObject("hasRoleForListSort", hasRoleForListSort(customerAlias));
        mav.addObject("showExportExcel", hasExportExcel());
        mav.addObject("search", searchDto);

        customDateFormat(mav);
        // End init Screen
        
        return mav;
    }

    /**
     * @author TaiTM
     * @description Khởi tạo dữ liệu ban đầu cho vùng search tại màn hình màn hình
     *              List search
     */
    public void initListSearchFilter(ModelAndView mav, SEARCH_DTO searchDto, Locale locale) {
        List<CommonSearchFilterDto> listSearchFilter = getCmsCommonSearchFillterService().initListSearchFilter(searchDto, locale);
        mav.addObject("listSearchFilter", listSearchFilter);
    }

    /**
     * @author TaiTM
     * @description Khởi tạo dữ liệu ban đầu cho các cột của table và phần ẩn hiện
     *              cột của table tại màn hình List search
     */
    public void initListColumn(ModelAndView mav, SEARCH_DTO searchDto, Class<SEARCH_RESULT_DTO> classSearchResult, HttpServletRequest request, Locale locale) {
        Object session = request.getSession().getAttribute("orderColumn");

        List<JcaDatatableDefaultConfigDto> order = getCmsCommonSearchFillterService().getListColumnInTable(getFunctionCode(),
                locale.toString());

        if (CollectionUtils.isNotEmpty(searchDto.getListCheckInFilterTable())) {
            for (JcaDatatableDefaultConfigDto colum : order) {
                if (!colum.isPrimary()) {
                    if (searchDto.getListCheckInFilterTable().contains(colum.getColumn())) {
                        colum.setChecked(true);
                    } else {
                        colum.setChecked(false);
                    }
                }
            }
        }
        
        if (session == null) {
            session = order;
        } else {
            request.getSession().setAttribute("orderColumn", session);
        }
        mav.addObject("order", session);
        
        Field[] chidrenFields = classSearchResult.getDeclaredFields();
        Field[] parentFields = classSearchResult.getSuperclass().getDeclaredFields();
        Field[] fields = new Field[parentFields.length + chidrenFields.length];
        int index = 0;
        for (Field f : chidrenFields) {
            fields[index] = f;
            index++;
        }
        for (Field f : parentFields) {
            boolean inList = false;

            for (Field fp : fields) {
                if (fp == null) {
                    break;
                } else if (f.getName().equals(fp.getName())) {
                    inList = true;
                    break;
                }
            }
            if (!inList) {
                fields[index] = f;
                index++;
            }
        }
        mav.addObject("fields", fields);
    }

    /**
     * @author TaiTM
     * @description Lấy ra Edit dto, để phục vụ cho màn hình edit/detail
     */
    private EDIT_DTO getData(Long id, String customerAlias, SEARCH_DTO condition, String searchDtoJson, boolean isEdit, Locale locale) {
        // Get Edit dto
        EDIT_DTO editDto = getCmsCommonSearchFillterService().getEditDtoById(id, locale);

        // Session for List Search
        String searchDto = CommonJsonUtil.convertObjectToJsonString(condition);

        if (searchDtoJson != null) {
            searchDto = searchDtoJson;
        }
        editDto.setSearchDto(searchDto);
        editDto.setIndexLangActive(0);
        editDto.setHasEdit(isEdit);
        editDto.setCustomerTypeId(HDBankUtil.getCustomerType(customerAlias));
        
        return editDto;
    }
    
    /**
     * @author TaiTM
     * @description Màn hình detail
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView doGetDetail(@PathVariable(value = "customerAlias") String customerAlias,
            @ModelAttribute(value = "searchDto") SEARCH_DTO condition,
            @RequestParam(value = "searchDtoJson", required = false) String searchDtoJson, Locale locale,
            @RequestParam(value = "id", required = false) Long id) {
        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));

        // Start check role
        if (!hasRoleForDetail(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End check role
        
        // Init List Language
        CmsLanguageUtils.initLanguageList(mav);
        
        // Start get data
        EDIT_DTO editDto = getData(id, customerAlias, condition, searchDtoJson, false, locale);
        mav.addObject(mavObjectEditName(), editDto);
        // End get data
        
        // Init Screen
        initScreenEdit(mav, editDto, locale);

        return mav;
    }

    /**
     * @author TaiTM
     * @description Màn hình Edit
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView doGetEdit(@PathVariable(value = "customerAlias") String customerAlias,
            @ModelAttribute(value = "searchDto") SEARCH_DTO condition,
            @RequestParam(value = "searchDtoJson", required = false) String searchDtoJson, Locale locale,
            @RequestParam(value = "id", required = false) Long id) {
        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));

        // Start check role
        if (!hasRoleForEdit(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End check role

        // Init List Language
        CmsLanguageUtils.initLanguageList(mav);

        // Start get data
        EDIT_DTO editDto = getData(id, customerAlias, condition, searchDtoJson, true, locale);
        mav.addObject(mavObjectEditName(), editDto);
        // End get data

        initScreenEdit(mav, editDto, locale);
        mav.addObject("hasRoleForEdit", hasRoleForEdit(customerAlias));
        return mav;
    }

    /**
     * @author TaiTM
     * @description Trả về màn hình edit khi và thông báo lỗi
     */
    private ModelAndView returnError(EDIT_DTO editDto, String customerAlias, Locale locale) {
        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));
        MessageList messageList = editDto.getMessageList();
        if (messageList == null) {
        	
        	if (editDto.getExpirationDate() != null) {
                if (editDto.getExpirationDate().before(editDto.getPostedDate())) {
                	messageList = new MessageList(Message.ERROR);

                    // Add message error
                    String msgError = msg.getMessage(ConstantCore.MSG_EMULATE_ERROR_EXPIRATION_DATE, null, locale);
                    messageList.add(msgError);
                }
            }
        	else {
        	
	            messageList = new MessageList(Message.ERROR);
	
	            // Add message error
	            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
	            messageList.add(msgError);
        	}
        }

        // Init master data
        CmsLanguageUtils.initLanguageList(mav);

        mav.addObject(ConstantCore.MSG_LIST, messageList);

        if (editDto.getIndexLangActive() == null) {
            editDto.setIndexLangActive(0);
        }
        
        initScreenEdit(mav, editDto, locale);
        mav.addObject(mavObjectEditName(), editDto);

        return mav;
    }
    
    /**
     * @author TaiTM
     * @description Save dữ liệu ở màn hình thêm mới/chỉnh sửa
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView doSubmit(@PathVariable(value = "customerAlias") String customerAlias,
            @ModelAttribute EDIT_DTO condition, BindingResult bindingResult, Locale locale,
            RedirectAttributes redirectAttributes) {

        // Start check role
        if (!hasRoleForEdit(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End check role

        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));
        Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
        
        Long id = condition.getId();
        condition.setLanguageCode(locale.toString());
        
        // Start validate
        validate(condition, bindingResult);
        if (bindingResult.hasErrors()) {
            if (id == null) {
                condition.setCode(null);
                condition.setId(id);
            }

            return returnError(condition, customerAlias, locale);
        }
        // End validate
        
        // Start do Save/Submit
        try {
            condition.setCustomerTypeId(customerTypeId);
            
            // code generation
            if (StringUtils.isBlank(condition.getCode())) {
                condition.setCode(CommonUtil.getNextBannerCode(getPrefixCode(),
                        cmsCommonService.getMaxCodeYYMM(getTableForGenCode(), getPrefixCode())));
            }
            
            getCmsCommonSearchFillterService().saveOrUpdate(condition, locale);
        } catch (Exception e) {
            if (id == null) {
                condition.setCode(null);
                condition.setId(id);
            }

            return returnError(condition, customerAlias, locale);
        }
        // End do Save/Submit
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        // success redirect edit page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(getUrlByCustomerAlias(customerAlias))
                .concat(UrlConst.EDIT);
        redirectAttributes.addAttribute("id", condition.getId());
        redirectAttributes.addAttribute("searchDtoJson", condition.getSearchDto());
        mav.setViewName(viewName);

        return mav;
    }

    /**
     * @author TaiTM
     * @description Xoá dữ liệu tại màn hình List search
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView doDeleteData(@ModelAttribute SEARCH_DTO searchDto,@PathVariable(value = "customerAlias") String customerAlias,
            @RequestParam(value = "id", required = true) Long id, Locale locale,
            RedirectAttributes redirectAttributes) {

        // Start check role
        if (!hasRoleForEdit(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End check role

        // Init message success list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);

        // Start do delete
        try {
            getCmsCommonSearchFillterService().deleteDataById(id);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        // End do delete
        
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(getUrlByCustomerAlias(customerAlias))
                .concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);

        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(searchDto);

        return mav;
    }
    
    /**
     * @author TaiTM
     * @description truyền dữ liệu cho search dto ở màn hình List sort
     */
    public void setParamSearchForListSort(SEARCH_DTO searchDto, Locale locale) {

    }

    /**
     * @author TaiTM
     * @description Màn hình List sort
     */
    @RequestMapping(value = AdminUrlConst.LIST_SORT, method = { RequestMethod.GET })
    public ModelAndView doGetListSort(@PathVariable(value = "customerAlias") String customerAlias,
            @ModelAttribute SEARCH_DTO searchDto, Locale locale,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page) {

        // Start check role
        if (!hasRoleForListSort(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End check role

        ModelAndView mav = new ModelAndView(viewListSort(customerAlias));
        mav.addObject("customerAlias", customerAlias);

        Long typeId = HDBankUtil.getCustomerType(customerAlias);
        searchDto.setCustomerTypeId(typeId);

        // set language
        searchDto.setLanguageCode(locale.toString());
        
        // set param search for list sort
        setParamSearchForListSort(searchDto, locale);
        
        // Start do search
        List<SEARCH_RESULT_DTO> list = getCmsCommonSearchFillterService().getListForSort(searchDto);
        mav.addObject("sortPageModel", list);
        // End do search
        
        // Init screen
        initScreenListSort(mav, searchDto, locale);
        mav.addObject(mavObjectSearchName(), searchDto);
        return mav;
    }

    /**
     * @author TaiTM
     * @description ajax List sort
     */
    @RequestMapping(value = AdminUrlConst.AJAX_LIST_SORT, method = RequestMethod.POST)
    public ModelAndView ajaxListSort(@PathVariable(value = "customerAlias") String customerAlias,
            @ModelAttribute SEARCH_DTO searchDto, Locale locale) {

        ModelAndView mav = new ModelAndView(viewListSortTable(customerAlias));
        mav.addObject("customerAlias", customerAlias);

        searchDto.setCustomerTypeId(HDBankUtil.getCustomerType(customerAlias));

        // set language
        searchDto.setLanguageCode(locale.toString());
        
        // set param search for list sort
        setParamSearchForListSort(searchDto, locale);
        
        // Start do search
        List<SEARCH_RESULT_DTO> list = getCmsCommonSearchFillterService().getListForSort(searchDto);
        mav.addObject("sortPageModel", list);
        // End do search
        
        return mav;
    }

    /**
     * @author TaiTM
     * @description Save List sort
     */
    @RequestMapping(value = AdminUrlConst.LIST_SORT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView doSaveListSort(@PathVariable(value = "customerAlias") String customerAlias,
            @RequestBody SEARCH_DTO searchDto, Locale locale, RedirectAttributes redirectAttributes) {
        // Start check role
        if (!hasRoleForEdit(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End check role

        MessageList messageList = new MessageList(Message.SUCCESS);
        // uppdate sort all
        try {
            getCmsCommonSearchFillterService().updateSortAll(searchDto);

            String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
            messageList.add(msgInfo);

        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);

            String msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgInfo);
        }

        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(getUrlByCustomerAlias(customerAlias))
                .concat(UrlConst.LIST).concat("/sort");
        
        // update success redirect sort list page
        ModelAndView mav = new ModelAndView(viewName);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(mavObjectSearchName(), searchDto);

        customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

        return mav;
    }

    /**
     * @author TaiTM
     * @description Khi Save list sort thành công, định nghĩa thêm các biến sẽ được
     *              truyền đến màn hình list sort
     */
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes, SEARCH_DTO searchDto,
            Locale locale) {

    }

    /**
     * @author TaiTM
     * @description Folder để lưu các file liên quan đến CKEDITOR. Mặc định là
     *              data/editor
     * @constant data/editor
     */
    public String getFolderEditor() {
        return "data/editor";
    }
    
    /**
     * @author TaiTM
     * @description Folder để lưu các file khi upload. Mặc định là data
     * @constant data
     */
    public String getFolder() {
        return "data";
    }

    /**
     * @author TaiTM
     * @description Download hình ảnh
     */
    @RequestMapping(value = UrlConst.URL_DOWNLOAD_IMAGE, method = RequestMethod.GET)
    public void download(@PathVariable String customerAlias,
            @RequestParam(required = true, value = "fileName") String fileName, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (fileName.contains(ConstantCore.AT_FILE)) {
            fileService.downloadTemp(fileName, request, response);
        } else {
            fileService.download(fileName, request, response);
        }
    }

    /**
     * @author TaiTM
     * @description Download file
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam(required = true, value = "fileName") String fileName, HttpServletRequest request,
            HttpServletResponse response) {
        if (fileName.contains(ConstantCore.AT_FILE)) {
            fileService.downloadTemp(fileName, request, response);
        } else {
            fileService.download(fileName, request, response);
        }
    }

    /**
     * @author TaiTM
     * @description Update file Temp
     */
    @RequestMapping(value = "/uploadTemp", method = RequestMethod.POST)
    public @ResponseBody String uploadTemp(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = false, value = "requestToken") String requestToken,
            @RequestParam(required = false, value = "widthImg") Integer widthImg,
            @RequestParam(required = false, value = "heightImg") Integer heightImg) throws IOException {
        String pathFile = StringUtils.isNotBlank(requestToken) ? Paths.get(getFolder(), requestToken).toString()
                : Paths.get(getFolder()).toString();
        return fileService.uploadImageTemp(request, request2, model, response, "", pathFile, widthImg, heightImg)
                .replace("\\", "/");
    }

    /**
     * @author TaiTM
     * @description Download cho CKEDITOR
     */
    @RequestMapping(value = UrlConst.URL_EDITOR_DOWNLOAD, method = RequestMethod.GET)
    public void editorDownload(@PathVariable("customerAlias") String customerAlias,
            @RequestParam(required = true, value = "url") String fileUrl, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String url = getFolderEditor() + "/" + fileUrl;
        fileService.requestEditorDownload(url, request, response);
    }

    /**
     * @author TaiTM
     * @description Update file Temp cho CKEDITOR
     */
    @RequestMapping(value = UrlConst.URL_EDITOR_UPLOAD, method = RequestMethod.POST)
    public @ResponseBody String editorUpload(@PathVariable("customerAlias") String customerAlias,
            MultipartHttpServletRequest request, HttpServletRequest request2, Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = fileService.uploadTemp(request, request2, model, response, getFolderEditor(), requestToken);
        String fileUrl = URLEncoder.encode(fileName, "UTF-8");
        String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT)
                .concat(getUrlByCustomerAlias(customerAlias)).concat(UrlConst.URL_EDITOR_DOWNLOAD).concat("?url=")
                .concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }

    /**
     * @author TaiTM
     * @description Update file Temp cho CKEDITOR
     */
	@RequestMapping(value = AdminUrlConst.URL_EDITOR_FILE_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody String editorFileUpload(@PathVariable("customerAlias") String customerAlias,
			MultipartHttpServletRequest request, HttpServletRequest request2, Model model, HttpServletResponse response,
			@RequestParam(required = true, value = "requesttoken") String requestToken,
			@RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
			throws JSONException, IOException {
		String fileName = fileService.uploadTemp(request, request2, model, response, getFolderEditor(), requestToken);
		String fileUrl = URLEncoder.encode(fileName, "UTF-8");
		String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT)
				.concat(getUrlByCustomerAlias(customerAlias)).concat(UrlConst.URL_EDITOR_DOWNLOAD).concat("?url=")
				.concat(fileUrl);
		UploadDto jSONObject = new UploadDto();
		jSONObject.setFileName(fileName);
		jSONObject.setUploaded(1);
		jSONObject.setUrl(downloadUrl);
		String result = CommonJsonUtil.convertObjectToJsonString(jSONObject);
		return result;
	}
    
    /**
     * @author TaiTM
     * @description Trả về path json của hình ảnh
     */
    @RequestMapping(value = "/path/images/list", method = RequestMethod.GET)
    @ResponseBody
    public String getPathImagesJson(HttpServletRequest request) throws IOException {

        List<ImageDto> resultList = CmsUtils.getFilePathListServer(getFolder(), request.getContextPath(), null);

        String result = CommonJsonUtil.convertObjectToJsonString(resultList);

        return result;
    }
    
    /**
     * @author TaiTM
     * @description Export Excel
     */
    @PostMapping(AdminUrlConst.EXPORT_EXCEL)
    public void exportExcel(@PathVariable("customerAlias") String customerAlias, @ModelAttribute SEARCH_DTO searchDto,
            Pageable pageable, Locale locale, HttpServletRequest request, HttpServletResponse response) {
        try {
            // add cookie for close loading on client
            Utils.addCookieForExport(searchDto.getToken(), request, response);
            searchDto.setCustomerTypeId(HDBankUtil.getCustomerType(customerAlias));
            searchDto.setLanguageCode(locale.toString());
            searchDto.setFunctionCode(getFunctionCode());
            
            getCmsCommonSearchFillterService().exportExcel(searchDto, pageable, getClassSearchResult(), response,
                    locale);
        } catch (Exception e) {
            logger.error("##exportLis##", e.getMessage());
        }
    }
}

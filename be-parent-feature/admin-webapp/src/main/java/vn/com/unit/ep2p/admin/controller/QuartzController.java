package vn.com.unit.ep2p.admin.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.JobConstant;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.QrtzMScheduleSearchDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.QrtzMJobScheduleWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobStoreWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMJobWebappService;
import vn.com.unit.ep2p.admin.service.QrtzMScheduleWebappService;
import vn.com.unit.ep2p.admin.service.TemplateService;
import vn.com.unit.ep2p.admin.validators.ScheduleValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.ConditionSearchUtils2;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleSearchDto;
import vn.com.unit.quartz.job.dto.QrtzMJobSearchDto;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;
import vn.com.unit.quartz.job.enumdef.QrtzMJobSearchEnum;
import vn.com.unit.quartz.job.enumdef.QrtzMScheduleSearchEnum;

@Controller("jobController")
@RequestMapping(QuartzController.CONTROLLER_QUARTZ)
public class QuartzController {

	private static final Logger logger = LoggerFactory.getLogger(QuartzController.class);

	//private static final String CONTROLLER_CRON_CHECK = "/cron-check";

	private static final String CONTROLLER_DELETE = "/delete";

	public static final String CONTROLLER_JOB = "/job";

	private static final String CONTROLLER_JOB_CLASS_LIST = "/getListJobClass";

	private static final String CONTROLLER_JOB_LIST = "/getListJob";

	public static final String CONTROLLER_JOB_SCHEDULE = "/job-schedule";

	//private static final String CONTROLLER_JOB_TYPE_LIST = "/getListJobType";

	private static final String CONTROLLER_PAUSE = "/pause";

	public static final String CONTROLLER_QUARTZ = "/quartz";

	private static final String CONTROLLER_RESUME = "/resume";

	private static final String CONTROLLER_RUN = "/run";

	private static final String CONTROLLER_SAVE = "/save";

	public static final String CONTROLLER_SCHEDULE = "/schedule";

	private static final String CONTROLLER_SCHEDULE_LIST = "/getListSchedule";
	
	private static final String CONTROLLER_COMPANY_LIST = "/getListCompany";
	
	private static final String CONTROLLER_EMAIL_LIST = "/getListEmailByCompanyId";

	public static final String CONTROLLER_UPSERT = "/upsert";

	private static final String HOUR_PATTERN = "hh:mm:ss";

	private static final String JOB_SCHEDULE = "jobSchedules";

	private static final String JOBS = "jobs";

	private static final String JOBS_SEARCH = "jobSearch";

	private static final String SCHEDULES = "schedules";

	private static final String SCHEDULES_SEARCH = "schedSearch";

    public static final String SCREEN_FUNCTION_CODE = JobConstant.SC1_S02_JOBSCHEDULE;
    public static final String SCREEN_FUNCTION_CODE_JOB = JobConstant.SC1_S03_JOBMASTER;
    public static final String SCREEN_FUNCTION_CODE_SCHEDULE = JobConstant.SC1_S04_SCHEDULEMASTER;

	private static final String VIEW_JOB = "/views/job-management/job.html";

	private static final String VIEW_JOB_CREATE = "/views/job-management/job-create.html";

	private static final String VIEW_JOB_SCHEDULE = "/views/job-management/job-schedule.html";

	private static final String VIEW_JOB_SCHEDULE_CREATE = "/views/job-management/job-schedule-create.html";

	private static final String VIEW_JOB_SCHEDULE_TABLE = "/views/job-management/job-schedule-table.html";

	private static final String VIEW_JOB_TABLE = "/views/job-management/job-table.html";

	//private static final String VIEW_MESSAGE = "/views/commons/message-alert.html";

	private static final String VIEW_SCHEDULE = "/views/job-management/schedule.html";

	private static final String VIEW_SCHEDULE_ADD = "/views/job-management/schedule-create.html";

	private static final String VIEW_SCHEDULE_TABLE = "/views/job-management/schedule-table.html";
	
	private static final String OBJ_COMPANY = "companyList";

	private static final String IMMEDIATE = "isImmediate";

	public static final String ONETIME = "isOneTime";

	public static final String RECURRING = "isdRecurring";

	public static final String ON = "on";
	
	private static final String JOB_CODE_EXIST = "quartz.message.job.code.exist";
	private static final String SCHEDULE_CODE_EXIST = "quartz.message.schedule.code.exist";
	private static final String JOB_SCHEDULE_DATE_EROR = "quartz.message.job.schedule.date.error";
	
	@Autowired
	QrtzMJobScheduleWebappService jobScheduleService;

	@Autowired
	QrtzMJobWebappService jobService;

	@Autowired
	QrtzMJobStoreWebappService jobStoreService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	QrtzMScheduleWebappService scheduleService;

	@Autowired
	ScheduleValidator scheduleValidator;

	@Autowired
	CommonService comService;

	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
    private TemplateService templateService;
/*
	@RequestMapping(value = CONTROLLER_SCHEDULE + CONTROLLER_CRON_CHECK, method = RequestMethod.GET)
	public boolean cronCheck(@RequestParam(required = false) String cron) {
		return scheduleService.cronCheck(cron);
	}
*/
	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		request.getSession().setAttribute("formatDate",
				systemConfig.getConfig(SystemConfig.DATE_PATTERN) + StringUtils.SPACE + HOUR_PATTERN);
		// The date format to parse or output your dates
		String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

	@RequestMapping(value = CONTROLLER_JOB + CONTROLLER_DELETE, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView deleteJob(@ModelAttribute(value = "jobSearch") QrtzMJobSearchDto jobSearch,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, @RequestParam("id") String id,
			Locale locale, RedirectAttributes redirectAttributes) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(VIEW_JOB_TABLE);
		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = messageSource.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
		boolean haveError = false;
		try {
			QrtzMJob jobEntity = jobService.getById(Long.parseLong(id));
			if (jobEntity.getId() == null) {
				haveError = true;
				msgInfo = messageSource.getMessage("quartz.message.job.not.exists", null, locale);
			} else if (jobService.isJobInUse(jobEntity.getId())) {
				haveError = true;
				msgInfo = messageSource.getMessage("quartz.error.job.in.use", null, locale);
			} else {
				jobService.deleteJob(jobEntity.getId());
			}
		} catch (Exception e) {
			haveError = true;
			msgInfo = e.getMessage();
		}
		if (haveError) {
			messageList.setStatus(Message.ERROR);
		}
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ)
				.concat(CONTROLLER_JOB).concat(UrlConst.LIST);
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB_SCHEDULE + CONTROLLER_DELETE, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView deleteJobSchedule(@ModelAttribute(value = "jobSchedule") QrtzMJobScheduleSearchDto jobSchedule,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, @RequestParam("id") String id,
			Locale locale, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(VIEW_JOB_SCHEDULE);

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = messageSource.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
		try {
			jobScheduleService.deleteScheduler(Long.parseLong(id));
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			msgInfo = e.getMessage();
		}
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ).concat(CONTROLLER_JOB_SCHEDULE).concat(UrlConst.LIST);
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_SCHEDULE + CONTROLLER_DELETE, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView deleteSchedule(@ModelAttribute(value = "schedSearch") QrtzMScheduleSearchDto schedSearch,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, @RequestParam("id") String id,
			Locale locale, RedirectAttributes redirectAttributes) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(VIEW_SCHEDULE_TABLE);
		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = messageSource.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
		boolean haveError = false;
		try {
			QrtzMSchedule scheduleEntity = scheduleService.getById(Long.parseLong(id));
			if (scheduleEntity.getId() == null) {
				haveError = true;
				msgInfo = messageSource.getMessage("quartz.message.schedule.not.exists", null, locale);
			} else if (scheduleService.isScheduleInUse(scheduleEntity.getId(), scheduleEntity.getCompanyId())) {
				haveError = true;
				msgInfo = messageSource.getMessage("quartz.error.schedule.in.use", null, locale);
			} else {
				scheduleService.deleteSchedule(scheduleEntity.getId());
			}
		} catch (Exception e) {
			haveError = true;
			msgInfo = e.getMessage();
		}
		if (haveError) {
			messageList.setStatus(Message.ERROR);
		}
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ)
				.concat(CONTROLLER_SCHEDULE).concat(UrlConst.LIST);
		mav.setViewName(viewName);
		return mav;
	}
	
	@RequestMapping(value = CONTROLLER_JOB_CLASS_LIST, method = RequestMethod.POST)
	@ResponseBody
	public Object getListJobClass(@RequestParam(required = false) String term, @RequestParam(value = "id", required = false) Long id) {
		Select2ResultDto obj = new Select2ResultDto();
		try {
			List<Select2Dto> lst = jobService.getListJobClass(term, id);
			obj.setTotal(lst.size());
			obj.setResults(lst);
		} catch (Exception e) {
			logger.error("#getListJob", e);
		}
		return obj;
	}

	@RequestMapping(value = CONTROLLER_JOB_LIST, method = RequestMethod.POST)
	@ResponseBody
	public Object getListJob(@RequestParam(required = false) String term, @RequestParam(value = "companyId", required = false) Long companyId,
	        @RequestParam(value = "isPaging", required = false) Boolean isPaging) {
		Select2ResultDto obj = new Select2ResultDto();
		try {
			List<Select2Dto> lst = jobService.getListSelect2DtoByCompanyId(term, companyId, isPaging);
			obj.setTotal(lst.size());
			obj.setResults(lst);
		} catch (Exception e) {
			logger.error("#getListJob", e);
		}
		return obj;
	}

	@RequestMapping(value = CONTROLLER_SCHEDULE_LIST, method = RequestMethod.POST)
	@ResponseBody
	public Object getListSchedule(@RequestParam(value = "term", required = false) String term, @RequestParam(value = "companyId", required = false) Long companyId,
            @RequestParam(value = "isPaging", required = false) Boolean isPaging) {
		Select2ResultDto obj = new Select2ResultDto();
		try {
			List<Select2Dto> lst = scheduleService.getListSelect2DtoByCompanyId(term, companyId, isPaging);
			obj.setTotal(lst.size());
			obj.setResults(lst);
		} catch (Exception e) {
			logger.error("#getListJob", e);
		}
		return obj;
	}
	
	@RequestMapping(value = CONTROLLER_COMPANY_LIST, method = RequestMethod.POST)
	@ResponseBody
	public Object getListCompany(@RequestParam(required = false) String term) {
		Select2ResultDto obj = new Select2ResultDto();
		try {
//			List<Select2Dto> lst = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
//					UserProfileUtils.isCompanyAdmin());
//			obj.setTotal(lst.size());
//			obj.setResults(lst);
		} catch (Exception e) {
			logger.error("#getListCompany", e);
		}

		return obj;
	}
	
	@RequestMapping(value = CONTROLLER_EMAIL_LIST, method = RequestMethod.POST)
    @ResponseBody
    public Object getListEmail(@RequestParam(value = "key", required = false) String key, @RequestParam(required = false) Long id) {
		Select2ResultDto obj = new Select2ResultDto();
		try {
			List<Select2Dto> lst = templateService.getTemplateByCompanyId(key, null, id);	
			obj.setTotal(lst.size());
			obj.setResults(lst);
		} catch (Exception e) {
			logger.error("#getListEmail", e);
		}

		return obj;
    }

	@RequestMapping(value = CONTROLLER_JOB + UrlConst.LIST, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView listJob(@ModelAttribute(value = "jobSearch") QrtzMJobSearchDto jobSearch,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);

		ModelAndView mav = new ModelAndView(VIEW_JOB);
		try {
			// Get company list
			List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
					UserProfileUtils.isCompanyAdmin());
			mav.addObject(OBJ_COMPANY, companyList);
			if(null ==jobSearch.getCompanyId()) {
			    jobSearch.setCompanyId(UserProfileUtils.getCompanyId());
			}
			// Convert to list companyId
			/*List<Long> lstCompanyId = new ArrayList<>();
			if (!CollectionUtils.isEmpty(companyList)) {
				List<String> strings = companyList.stream().map(Select2Dto::getId).collect(Collectors.toList());
				lstCompanyId = strings.stream().map(Long::valueOf).collect(Collectors.toList());
			} else {
				lstCompanyId.add(UserProfileUtils.getCompanyId());
			}
			// Set companyId list
			jobSearch.setCompanyIdList(lstCompanyId);*/
			// set init search
			CommonSearchUtil.setSearchSelect(QrtzMJobSearchEnum.class, mav);
			
			// Session Search
	        ConditionSearchUtils2<QrtzMJobSearchDto> searchUtil = new ConditionSearchUtils2<QrtzMJobSearchDto>();
	        String[] urlContains = new String[] { "quartz/job/upsert", "quartz/job/list" };
	        jobSearch = searchUtil.getConditionSearch(this.getClass(), jobSearch, urlContains, request, page, pageSize);
	        pageSize = Optional.ofNullable(jobSearch.getPageSize()).orElse(pageSize);
	        page = Optional.ofNullable(jobSearch.getPage()).orElse(page);
	        
			PageWrapper<QrtzMJobDto> jobs = jobService.getJobs(jobSearch, pageSize, page);
			mav.addObject(JOBS_SEARCH, jobSearch);
			mav.addObject(JOBS, jobs);
		} catch (Exception e) {
			logger.error("#listJob", e);
		}
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB + UrlConst.AJAXLIST, method = { RequestMethod.POST })
	public ModelAndView listJobAjax(@ModelAttribute(value = "jobSearch") QrtzMJobSearchDto jobSearch,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);

		ModelAndView mav = new ModelAndView(VIEW_JOB_TABLE);
		try {
			// set init search
			CommonSearchUtil.setSearchSelect(QrtzMJobSearchEnum.class, mav);
			/*Long comId = jobSearch.getCompanyId();
			if(comId == null || comId > 0) {
				jobSearch.setCompanyIdList(null);
			} else {
				jobSearch.setCompanyIdList(companyIds);
			}*/
			PageWrapper<QrtzMJobDto> jobs = jobService.getJobs(jobSearch, pageSize, page);
			// Session Search
	        ConditionSearchUtils2<QrtzMJobSearchDto> searchUtil = new ConditionSearchUtils2<QrtzMJobSearchDto>();
	        searchUtil.setConditionSearch(request, this.getClass(), jobSearch, page, pageSize);
			mav.addObject(JOBS_SEARCH, jobSearch);
			mav.addObject(JOBS, jobs);
		} catch (Exception e) {
			logger.error("#listJobAjax", e);
		}
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB_SCHEDULE + UrlConst.AJAXLIST, method = { RequestMethod.POST })
	public ModelAndView listJobSchedule(@ModelAttribute(value = "jobSchedule") QrtzMJobScheduleSearchDto jobSchedule,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam) {
		ModelAndView mav = new ModelAndView(VIEW_JOB_SCHEDULE_TABLE);

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		/*Long comId = jobSchedule.getCompanyId();
		if(comId == null || comId > 0) {
			jobSchedule.setCompanyIdList(null);
		} else {
			jobSchedule.setCompanyIdList(companyIds);
		}*/
		PageWrapper<QrtzMJobScheduleDto> jobSchedules = jobScheduleService.getJobSchedules(jobSchedule, pageSize, page);
		mav.addObject(JOB_SCHEDULE, jobSchedules);

		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB_SCHEDULE + UrlConst.LIST, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView listJobSchedule(@ModelAttribute(value = "jobSchedule") QrtzMJobScheduleSearchDto jobSchedule,
			Locale locale, @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam) {

		ModelAndView mav = new ModelAndView(VIEW_JOB_SCHEDULE);

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		// Get company list
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
				UserProfileUtils.isCompanyAdmin());
		mav.addObject(OBJ_COMPANY, companyList);
		if(null ==jobSchedule.getCompanyId()) {
		    jobSchedule.setCompanyId(UserProfileUtils.getCompanyId());
        }
		// Convert to list companyId
		/*List<Long> lstCompanyId = new ArrayList<>();
		if (!CollectionUtils.isEmpty(companyList)) {
			List<String> strings = companyList.stream().map(Select2Dto::getId).collect(Collectors.toList());
			lstCompanyId = strings.stream().map(Long::valueOf).collect(Collectors.toList());
		} else {
			lstCompanyId.add(UserProfileUtils.getCompanyId());
		}
		// Set companyId list
		jobSchedule.setCompanyIdList(lstCompanyId);*/
		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		try {
			DateFormat dateFormat = new SimpleDateFormat(systemConfig.getConfig(SystemConfig.DATE_PATTERN));
			Calendar cal = Calendar.getInstance();
			Date sysDate = comService.getSystemDateTime();
			cal.setTime(sysDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date pastDate = cal.getTime();
			cal.setTime(sysDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Date futureDate = cal.getTime();
//			jobSchedule.setStartTime(dateFormat.format(pastDate));
//			jobSchedule.setEndTime(dateFormat.format(futureDate));
			PageWrapper<QrtzMJobScheduleDto> jobSchedules = jobScheduleService.getJobSchedules(jobSchedule, pageSize, page);
			mav.addObject(JOB_SCHEDULE, jobSchedules);
		} catch (Exception e) {
			logger.error("##listJobSchedule##", e);
		}
		mav.addObject("jobSchedule", jobSchedule);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_SCHEDULE + UrlConst.LIST, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView listSchedule(@ModelAttribute(value = "schedSearch") QrtzMScheduleSearchDto schedSearch,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);

		ModelAndView mav = new ModelAndView(VIEW_SCHEDULE);
		try {
			// Get company list
			List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
					UserProfileUtils.isCompanyAdmin());
			mav.addObject(OBJ_COMPANY, companyList);
			
			if(null == schedSearch.getCompanyId()) {
			    schedSearch.setCompanyId(UserProfileUtils.getCompanyId());
            }
			// Convert to list companyId
			/*List<Long> lstCompanyId = new ArrayList<>();
			if (!CollectionUtils.isEmpty(companyList)) {
				List<String> strings = companyList.stream().map(Select2Dto::getId).collect(Collectors.toList());
				lstCompanyId = strings.stream().map(Long::valueOf).collect(Collectors.toList());
			} else {
				lstCompanyId.add(UserProfileUtils.getCompanyId());
			}
			// Set companyId list
			schedSearch.setCompanyIdList(lstCompanyId);*/
			// set init search
			CommonSearchUtil.setSearchSelect(QrtzMScheduleSearchEnum.class, mav);
			// Session Search
	        ConditionSearchUtils2<QrtzMScheduleSearchDto> searchUtil = new ConditionSearchUtils2<QrtzMScheduleSearchDto>();
	        String[] urlContains = new String[] { "quartz/schedule/upsert", "quartz/schedule/list" };
	        schedSearch = searchUtil.getConditionSearch(this.getClass(), schedSearch, urlContains, request, page, pageSize);
	        pageSize = Optional.ofNullable(schedSearch.getPageSize()).orElse(pageSize);
	        page = Optional.ofNullable(schedSearch.getPage()).orElse(page);
			PageWrapper<QrtzMScheduleDto> schedules = scheduleService.getSchedules(schedSearch, pageSize, page);
			mav.addObject(SCHEDULES, schedules);
		} catch (Exception e) {
			logger.error("#listSchedule", e);
		}		
		mav.addObject(SCHEDULES_SEARCH, schedSearch);		
		return mav;
	}
	
	@RequestMapping(value = CONTROLLER_SCHEDULE + UrlConst.AJAXLIST, method = { RequestMethod.POST })
	public ModelAndView searchListJobSchedule(@ModelAttribute(value = "schedSearch") QrtzMScheduleSearchDto schedSearch,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);

		ModelAndView mav = new ModelAndView(VIEW_SCHEDULE_TABLE);
		try {
			// set init search
			CommonSearchUtil.setSearchSelect(QrtzMScheduleSearchEnum.class, mav);
			Long comId = schedSearch.getCompanyId();
			if(comId == 0) {
				schedSearch.setCompanyIdList(null);
			} else {
				schedSearch.setCompanyIdList(UserProfileUtils.getCompanyIdList());
			}
			PageWrapper<QrtzMScheduleDto> schedules = scheduleService.getSchedules(schedSearch, pageSize, page);
			// Session Search
	        ConditionSearchUtils2<QrtzMScheduleSearchDto> searchUtil = new ConditionSearchUtils2<QrtzMScheduleSearchDto>();
	        searchUtil.setConditionSearch(request, this.getClass(), schedSearch, page, pageSize);
			mav.addObject(SCHEDULES, schedules);
		} catch (Exception e) {
			logger.error("##searchListJobSchedule##", e);
		}	
		mav.addObject(SCHEDULES_SEARCH, schedSearch);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB_SCHEDULE + CONTROLLER_PAUSE, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView pauseJobSchedule(@ModelAttribute(value = "jobSchedule") QrtzMJobScheduleSearchDto jobSchedule,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, @RequestParam("id") String id,
			Locale locale, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(VIEW_JOB_SCHEDULE_TABLE);

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = messageSource.getMessage("quartz.job.pause.trigger", null, locale);
		try {
			jobScheduleService.pauseScheduler(Long.parseLong(id));
		} catch (Exception e) {
			/*mav.setViewName(VIEW_MESSAGE);
			messageList.setStatus(Message.ERROR);
			messageList.add(e.getMessage());
			mav.addObject(ConstantCore.MSG_LIST, messageList);
			return mav;*/
		    messageList.setStatus(Message.ERROR);
            msgInfo = e.getMessage();
		}
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ).concat(CONTROLLER_JOB_SCHEDULE).concat(UrlConst.LIST);
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB_SCHEDULE + CONTROLLER_RESUME, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView resumeJobSchedule(@ModelAttribute(value = "jobSchedule") QrtzMJobScheduleSearchDto jobSchedule,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, @RequestParam("id") String id,
			Locale locale, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(VIEW_JOB_SCHEDULE_TABLE);

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init page size
		//int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		//int page = pageParam.orElse(1);

		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = messageSource.getMessage("quartz.job.resume.trigger", null, locale);
		try {
			jobScheduleService.resumeScheduler(Long.parseLong(id));
		} catch (Exception e) {
			/*mav.setViewName(VIEW_MESSAGE);
			messageList.setStatus(Message.ERROR);
			messageList.add(e.getMessage());
			mav.addObject(ConstantCore.MSG_LIST, messageList);
			return mav;*/
		    messageList.setStatus(Message.ERROR);
            msgInfo = e.getMessage();
		}
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ)
				.concat(CONTROLLER_JOB_SCHEDULE).concat(UrlConst.LIST);
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB_SCHEDULE + CONTROLLER_RUN, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView runJobSchedule(@ModelAttribute(value = "jobSchedule") QrtzMJobScheduleSearchDto jobSchedule,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, @RequestParam("id") String id,
			Locale locale, RedirectAttributes redirectAttributes) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init page size
		//int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		//int page = pageParam.orElse(1);

		ModelAndView mav = new ModelAndView(VIEW_JOB_SCHEDULE_TABLE);
		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = messageSource.getMessage("quartz.job.play.trigger", null, locale);
		try {
			jobScheduleService.runScheduler(Long.parseLong(id));
		} catch (Exception e) {
			/*mav.setViewName(VIEW_MESSAGE);
			messageList.setStatus(Message.ERROR);
			messageList.add(e.getMessage());
			mav.addObject(ConstantCore.MSG_LIST, messageList);
			return mav;*/
		    messageList.setStatus(Message.ERROR);
		    msgInfo = e.getMessage();
		}
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ).concat(CONTROLLER_JOB_SCHEDULE).concat(UrlConst.LIST);
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB + CONTROLLER_SAVE, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView saveJob(@ModelAttribute(value = "jobEntity") QrtzMJob jobEntity,
			@ModelAttribute(value = "jobStoreEntity") QrtzMJobStore jobStoreEntity, RedirectAttributes redirectAttributes, Locale locale,
			HttpServletRequest request) {
		// Security for this page.

		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(VIEW_JOB_CREATE);
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msg = messageSource.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
		try {
			if (jobEntity.getSendNotification() == null || !jobEntity.getSendNotification()) {
				jobEntity.setSendStatus(null);
				jobEntity.setEmailTemplate(null);
				jobEntity.setRecipientAddress(null);
				jobEntity.setRecipientName(null);
				jobEntity.setCcAddress(null);
				jobEntity.setBccAddress(null);
			}
			Long id = jobEntity.getId();
			Long comId = jobEntity.getCompanyId();
			String code = jobEntity.getJobCode();
			boolean isExisted = jobService.hasCode(comId, code, id);
			if(null != id) {
				msg = messageSource.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
			}
			if(isExisted) {
				messageList.setStatus(Message.ERROR);
				msg = messageSource.getMessage(JOB_CODE_EXIST, null, locale);
			} else {
				jobService.save(jobEntity, jobStoreEntity, locale);
			}
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			msg = e.getMessage();
		}
		List<String> lstSendStatus = new ArrayList<>();		
		try {
			String statuses = jobEntity.getSendStatus();
			if (statuses != null && !StringUtils.isEmpty(statuses)) {
				String[] arrSendStatus = statuses.replaceAll(StringUtils.SPACE, StringUtils.EMPTY).split(ConstantCore.COMMA);
				for (String itemSendStatus : arrSendStatus) {
					lstSendStatus.add(itemSendStatus);
				}
			}
			jobStoreEntity = jobStoreService.getByGroupCode(jobEntity.getJobGroup());
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			msg = e.getMessage();
		}
		try {
			jobService.initScreen(mav, jobEntity, jobStoreEntity, lstSendStatus);
		} catch (Exception e) {
			msg = e.getMessage();
			logger.error("##saveJob##", e);
		}
		messageList.add(msg);
        if(Message.ERROR.equals(messageList.getStatus())) {
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }else {
            mav = new ModelAndView();
            String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ).concat(CONTROLLER_JOB).concat(CONTROLLER_UPSERT);
            Long id = jobEntity.getId();
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
            mav.setViewName(viewName);
        }
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB_SCHEDULE + CONTROLLER_SAVE, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView saveJobSchedule(@ModelAttribute(value = "scheduleJob") QrtzMJobSchedule scheduleJob,
			@RequestParam(value = "isImmediate", required = false) String isImmediate,
			@RequestParam(value = "isOneTime", required = false) String isOneTime,
			@RequestParam(value = "isdRecurring", required = false) String isdRecurring, 
			@RequestParam(value = "companyId", required = false) Long companyId, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Locale locale) {
		ModelAndView mav = new ModelAndView(VIEW_JOB_SCHEDULE_CREATE);

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = scheduleJob.getId() == null
				? messageSource.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale)
				: messageSource.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);

		try {
			if (bindingResult.hasErrors()) {
				messageList.setStatus(Message.ERROR);
				String msgError = scheduleJob.getId() == null
						? messageSource.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale)
						: messageSource.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
				messageList.add(msgError);
				mav.addObject(ConstantCore.MSG_LIST, messageList);
				jobScheduleService.initCreateScreenForError(mav, scheduleJob, companyId, locale);
				return mav;
			}
			Long id = scheduleJob.getId();
			if(id == null) {
				List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
						UserProfileUtils.isCompanyAdmin());
				if(companyList != null && companyList.size() == 1) {
					Long comId = Long.valueOf(companyList.get(0).getId());
					scheduleJob.setCompanyId(comId);
				}
			}
			scheduleJob = jobScheduleService.createJobSchedule(scheduleJob);
			messageList.add(msgInfo);
			String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ).concat(CONTROLLER_JOB_SCHEDULE).concat(UrlConst.LIST);
			
			redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
			String url = CONTROLLER_QUARTZ.concat(CONTROLLER_JOB_SCHEDULE).concat(UrlConst.LIST).substring(1);
			redirectAttributes.addFlashAttribute("url", url);
			redirectAttributes.addAttribute("id", scheduleJob.getId());
			mav.setViewName(viewName);
			return mav;
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			msgInfo = scheduleJob.getId() == null ? messageSource.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale)
					: messageSource.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
			if(e instanceof SchedulerException) {
				msgInfo = msgInfo + StringUtils.SPACE + messageSource.getMessage(JOB_SCHEDULE_DATE_EROR, null, locale);
			}
			messageList.add(e.getMessage());
			jobScheduleService.initCreateScreenForError(mav, scheduleJob, companyId, locale);
		}
		if ((boolean) Boolean.valueOf(isImmediate)) {
			mav.addObject(IMMEDIATE, ON);
		} else if ((boolean) Boolean.valueOf(isOneTime)) {
			mav.addObject(ONETIME, ON);
		} else {
			mav.addObject(RECURRING, ON);
		}
		messageList.add(msgInfo);
		mav.addObject(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_SCHEDULE + CONTROLLER_SAVE, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView saveSchedule(@Valid @ModelAttribute(value = "scheduleDto") QrtzMScheduleDto scheduleDto,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Locale locale) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(VIEW_SCHEDULE_ADD);
		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		Long id = scheduleDto.getId();
		String msgInfo = id == null
				? messageSource.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale)
				: messageSource.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);	
		Select2Dto company = new Select2Dto();
		Long comId = scheduleDto.getCompanyId();
		QrtzMSchedule scheduleEntity = new QrtzMSchedule();
		try {
			scheduleValidator.validate(scheduleDto, bindingResult);
			boolean isValid = scheduleService.cronCheck(scheduleDto.getCronExpression());
			if(comId == null) {
				comId = Long.valueOf(0);
			}
			String code = scheduleDto.getSchedCode();
			boolean isExisted = scheduleDto.getId() == null ? scheduleService.hasCode(comId, code, id) : Boolean.FALSE;
			if (bindingResult.hasErrors() || !isValid || isExisted) {
				if(comId != null) {
					company = jobService.getCompanyById(comId);
				}
				messageList.setStatus(Message.ERROR);
				String msgError = id == null
						? messageSource.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale)
						: messageSource.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
				if(!isValid) {
					msgError = messageSource.getMessage(ConstantCore.MSG_INVALID_CRON_REGEX, null, locale);
				}
				if(isExisted) {
					msgError = messageSource.getMessage(SCHEDULE_CODE_EXIST, null, locale);
				}
				messageList.add(msgError);
				
				try {
					for (ObjectError err : bindingResult.getAllErrors()) {
						messageList.add(messageSource.getMessage(err.getCode(), null, locale));
					}
				} catch (Exception e) {
				}

				mav.addObject(ConstantCore.MSG_LIST, messageList);
//				List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
//						UserProfileUtils.isCompanyAdmin());
//				mav.addObject("companyList", companyList);
				mav.addObject("/views/job-management/schedule.html", scheduleDto);
				
				mav.addObject("company", company);

//				jobScheduleService.initCreateScreen(mav, id, comId, locale);
				return mav;
			}
			//Long id = scheduleDto.getId();
			/*if(id == null) {
				List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
						UserProfileUtils.isCompanyAdmin());
				if(companyList != null && companyList.size() == 1) {
					comId = Long.valueOf(companyList.get(0).getId());
					scheduleDto.setCompanyId(comId);
				}
			} else {
				List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
						UserProfileUtils.isCompanyAdmin());
				if(companyList != null && companyList.size() == 1) {
					comId = Long.valueOf(companyList.get(0).getId());
					scheduleDto.setCompanyId(comId);
				}
			}*/
			scheduleEntity = scheduleService.save(scheduleDto);
			id = scheduleEntity.getId();
			jobScheduleService.initScheduleCreateScreen(mav, id);
		} catch (Exception e) {
			try {
				if(comId != null) {
					company = jobService.getCompanyById(comId);
				}
			} catch (Exception e2) {
				messageList.setStatus(Message.ERROR);
				String msgError = e.getMessage();
				messageList.add(msgError);
			}
			messageList.setStatus(Message.ERROR);
			msgInfo = id == null
					? messageSource.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale)
					: messageSource.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
			messageList.add(e.getMessage());
//			List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
//					UserProfileUtils.isCompanyAdmin());
//			mav.addObject("companyList", companyList);
			mav.addObject("/views/job-management/schedule.html", scheduleDto);
			mav.addObject("company", company);
		}
		messageList.add(msgInfo);
		mav.addObject(ConstantCore.MSG_LIST, messageList);
		/*
		if(!Message.ERROR.equals(messageList.getStatus())) {
		    scheduleDto.setUrl(CONTROLLER_QUARTZ.replace(ConstantCore.SLASH, "").concat(CONTROLLER_SCHEDULE).concat(CONTROLLER_UPSERT).concat(DelegateController.DELEGATE_QUERY).concat(String.valueOf(id)));

		    mav = new ModelAndView();
            String viewName = UrlConst.REDIRECT.concat(CONTROLLER_QUARTZ).concat(CONTROLLER_SCHEDULE).concat(CONTROLLER_UPSERT);
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
            mav.setViewName(viewName);

        }
		*/
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB + CONTROLLER_UPSERT, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView upsertJob(@RequestParam(value = "id", required = false) Long id,
			@ModelAttribute(ConstantCore.MSG_LIST) MessageList messageList, Locale locale) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_JOB.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(VIEW_JOB_CREATE);
        
		String msgInfo = StringUtils.EMPTY;

		try {
			Long jobId = id;
			if(null != jobId) {
//			    QrtzMJob jobEntity = jobService.getById(jobId);
	            // Security for data
//	            if (null == jobEntity || !UserProfileUtils.hasRoleForCompany(jobEntity.getCompanyId())) {
//	                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//	            }
			}
            jobService.initCreateScreen(mav, jobId);
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			msgInfo = e.getMessage();
		}

		messageList.add(msgInfo);
		mav.addObject(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_JOB_SCHEDULE + CONTROLLER_UPSERT, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView upsertJobSchedule(@RequestParam(value = "id", required = false) Long id, 
			@RequestParam(value = "companyId", required = false) Long companyId, 
			@ModelAttribute(ConstantCore.MSG_LIST) MessageList messageList, Locale locale) {
		ModelAndView mav = new ModelAndView(VIEW_JOB_SCHEDULE_CREATE);
		// Security for this page.

		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init message list
		if (messageList == null) {
			messageList = new MessageList();
		}
		String msgInfo = StringUtils.EMPTY;

		try {
		    if(null != id) {
//		        QrtzMJobSchedule jobSchedule = jobScheduleService.getById(id);
	            // Security for data
//	            if (null == jobSchedule || !UserProfileUtils.hasRoleForCompany(jobSchedule.getCompanyId())) {
//	                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//	            }
		    }
			jobScheduleService.initCreateScreen(mav, id, companyId, locale);
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			msgInfo = e.getMessage();
		}

		messageList.add(msgInfo);
		mav.addObject(ConstantCore.MSG_LIST, messageList);
		//mav.addObject(ONETIME, ON);
		return mav;
	}

	@RequestMapping(value = CONTROLLER_SCHEDULE + CONTROLLER_UPSERT, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView upsertSchedule(@RequestParam(value = "id", required = false) Long scheduleId,
			@ModelAttribute(ConstantCore.MSG_LIST) MessageList messageList, Locale locale) {
		ModelAndView mav = new ModelAndView(VIEW_SCHEDULE_ADD);
		// Security for this page.
		
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_SCHEDULE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		
		if (messageList == null) {
			messageList = new MessageList();
		}
		String msgInfo = StringUtils.EMPTY;

		/*Long id = null;
		try {
			id = Long.parseLong(scheduleId);
		} catch (Exception e) {
			id = null;
		}*/
		if(null != scheduleId) {
//		    QrtzMSchedule scheduleEntity = scheduleService.getById(scheduleId);
		    // Security for data
//            if (null == scheduleEntity || !UserProfileUtils.hasRoleForCompany(scheduleEntity.getCompanyId())) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
		}
		try {
			jobScheduleService.initScheduleCreateScreen(mav, scheduleId);
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			msgInfo = e.getMessage();
		}

		messageList.add(msgInfo);
		mav.addObject(ConstantCore.MSG_LIST, messageList);

		return mav;
	}

}

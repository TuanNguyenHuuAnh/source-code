package vn.com.unit.ep2p.admin.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.config.PageSizeConfig;
import vn.com.unit.ep2p.admin.dto.QrtzMScheduleSearchDto;
import vn.com.unit.ep2p.admin.repository.AppQrtzMScheduleRepository;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.QrtzMScheduleWebappService;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;
import vn.com.unit.quartz.job.enumdef.QrtzMScheduleSearchEnum;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;

@Service
@Transactional
public class QrtzMScheduleWebappServiceImpl implements QrtzMScheduleWebappService {
	
	private static final Logger logger = LoggerFactory.getLogger(QrtzMScheduleWebappServiceImpl.class);

	private static final int[] DATE_PATTERN_INDEX = { 4, 7 };

	private static final String REGEX_COLON = ":";

	private static final String REGEX_SLASH = "/";

	private static final Long SCHEDULE_TYPE = 1L;
	
	private static final String START_TIME = "000000";
	
	private static final String END_TIME = "235959";

	@Autowired
	AppQrtzMScheduleRepository scheduleRepository;

	@Autowired
	CommonService comService;

	@Autowired
    PageSizeConfig pageSizeConfig;

	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	CommonService commonService;

	@Override
	public Boolean cronCheck(String cron) {
		return CronExpression.isValidExpression(cron);
	}

	@Override
	public void deleteSchedule(Long id) {
//	    String user = UserProfileUtils.getUserNameLogin();
        Date date = commonService.getSystemDateTime();
		QrtzMSchedule scheduleEntity = scheduleRepository.findOne(id);
		scheduleEntity.setValidflag(ValidFlagEnum.DELETED.toLong());
		scheduleEntity.setDeletedId(UserProfileUtils.getAccountId());
		scheduleEntity.setDeletedDate(date);
		scheduleRepository.update(scheduleEntity);
	}

	@Override
	public QrtzMSchedule getById(Long id) {
		QrtzMSchedule scheduleEntity = scheduleRepository.findOne(id);
		return scheduleEntity == null ? new QrtzMSchedule() : scheduleEntity;
	}

	@Override
	public QrtzMSchedule getByScheduleId(Long schedId, Long companyId) {
		QrtzMSchedule qSchedMaster = scheduleRepository.getByScheduleId(schedId, companyId);
		return qSchedMaster == null ? new QrtzMSchedule() : qSchedMaster;
	}

	@Override
	public List<Select2Dto> getListForCombobox(String term, List<Long> companyIdList) throws SQLException {
		return scheduleRepository.getListForCombobox(term, companyIdList);
	}

	@Override
	public PageWrapper<QrtzMScheduleDto> getSchedules(QrtzMScheduleSearchDto schedSearch, int pageSize, int page) {
		setSearchParm(schedSearch);
		List<Integer> listPageSize = pageSizeConfig.getListPage(pageSize, systemConfig);
		int sizeOfPage = pageSizeConfig.getSizeOfPage(listPageSize, pageSize, systemConfig);
		PageWrapper<QrtzMScheduleDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
		pageWrapper.setListPageSize(listPageSize);
		pageWrapper.setSizeOfPage(sizeOfPage);
		List<QrtzMScheduleDto> result = new ArrayList<>();
		int count = 0;
		try {
			count = scheduleRepository.countScheduleByCondition(schedSearch);
			if (count > 0) {
				int currentPage = pageWrapper.getCurrentPage();
				int startIndex = CommonUtil.verifyOverflowStartIndex(currentPage, sizeOfPage);
				result = scheduleRepository.getSchedules(schedSearch, startIndex, sizeOfPage);
			}
		} catch (Exception e) {
			logger.error("#getSchedules", e);
		}
		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	@Override
	public Select2Dto getSelect2ByJobIdAndCompanyId(Long jobId, Long id) {
		try {
			Select2Dto schedule = scheduleRepository.findSelect2ByJobIdAndCompanyId(jobId, id);
			return schedule == null ? new Select2Dto() : schedule;
		} catch (Exception e) {
			logger.error("#getSelect2ByCompanyId", e);
		}
		return new Select2Dto();
	}

	@Override
	public Boolean isScheduleInUse(Long scheduleId, Long companyId) {
		return scheduleRepository.isScheduleInUse(scheduleId, companyId) != null;
	}

	public String refactorDate(String date) {
		List<String> dateElementList = Arrays.asList(date.split(REGEX_SLASH));
		List<String> reverseDateElementList = Lists.reverse(dateElementList);
		return refactorDbDateTime(String.join(StringUtils.EMPTY, reverseDateElementList), DATE_PATTERN_INDEX, StringUtils.EMPTY);
	}

	private String refactorDbDateTime(String value, int[] indexes, String interval) {
		StringBuilder refactoredDate = new StringBuilder(value);
		Arrays.stream(indexes).forEach(index -> refactoredDate.insert(index, interval));
		return refactoredDate.toString();
	}

	public String refactorTime(String time) {
		return time.replaceAll(REGEX_COLON, StringUtils.EMPTY);
	}

	@Override
	public QrtzMSchedule save(QrtzMScheduleDto scheduleDto) throws Exception {

		QrtzMSchedule scheduleEntity = new QrtzMSchedule();
		Date sysaDate = comService.getSystemDateTime();
		@SuppressWarnings("unused")
		String userName = UserProfileUtils.getUserNameLogin();
		Long type = scheduleEntity.getSchedType() == null ? SCHEDULE_TYPE : scheduleEntity.getSchedType();
		Long flag = scheduleEntity.getValidflag() == null ? ValidFlagEnum.ACTIVED.toLong() : scheduleEntity.getValidflag();
		if (scheduleDto.getId() != null) {
			scheduleEntity = getById(scheduleDto.getId());
			if (scheduleEntity.getId() == null) {
				scheduleEntity.setCreatedDate(sysaDate);
				scheduleEntity.setCreatedId(UserProfileUtils.getAccountId());
			} else {
				scheduleEntity.setUpdatedDate(sysaDate);
				scheduleEntity.setUpdatedId(UserProfileUtils.getAccountId());
			}

		} else {
			scheduleEntity.setCreatedDate(sysaDate);
			scheduleEntity.setCreatedId(UserProfileUtils.getAccountId());
			scheduleEntity.setUpdatedDate(sysaDate);
			scheduleEntity.setUpdatedId(UserProfileUtils.getAccountId());
		}
		Long companyId = scheduleDto.getCompanyId();
		scheduleEntity.setCompanyId(companyId);
		scheduleEntity.setStartDate(refactorDate(scheduleDto.getStartDate()));
		scheduleEntity.setStartTime(START_TIME);
		scheduleEntity.setEndDate(refactorDate(scheduleDto.getEndDate()));
		scheduleEntity.setEndTime(END_TIME);
		String code = StringUtils.isBlank(scheduleDto.getSchedCode()) ? StringUtils.EMPTY : scheduleDto.getSchedCode().toUpperCase();
		scheduleEntity.setSchedCode(code);
		scheduleEntity.setSchedName(scheduleDto.getSchedName());
		scheduleEntity.setDescription(scheduleDto.getDescription());
		scheduleEntity.setCronExpression(scheduleDto.getCronExpression());
		scheduleEntity.setSchedType(type);
		scheduleEntity.setValidflag(flag);
		if (scheduleEntity.getId()!=null) {
		    return scheduleRepository.update(scheduleEntity); 
        } else {
            return scheduleRepository.create(scheduleEntity);
        }
		

	}

	private void setSearchParm(QrtzMScheduleSearchDto schedSearch) {
		List<String> fieldValues = schedSearch.getFieldValues();
		String fieldSearch = schedSearch.getFieldSearch();
        if (fieldValues == null || fieldValues.isEmpty()) {
        	schedSearch.setSchedCode(fieldSearch);
        	schedSearch.setSchedName(fieldSearch);
        	schedSearch.setCronExpression(fieldSearch);
        	schedSearch.setDescription(fieldSearch);
        } else {
			for (String i : schedSearch.getFieldValues()) {
				if (i.equals(QrtzMScheduleSearchEnum.SCHED_CODE.name())) {
					schedSearch.setSchedCode(fieldSearch);
				}
				if (i.equals(QrtzMScheduleSearchEnum.SCHED_NAME.name())) {
					schedSearch.setSchedName(fieldSearch);
				}
				if (i.equals(QrtzMScheduleSearchEnum.CRON_EXPRESSION.name())) {
					schedSearch.setCronExpression(fieldSearch);
				}
				if (i.equals(QrtzMScheduleSearchEnum.DESCRIPTION.name())) {
					schedSearch.setDescription(fieldSearch);
				}
			}
		}
//        schedSearch.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
//        schedSearch.setCompanyIdList(UserProfileUtils.getCompanyIdList());
	}

	@Override
	public boolean hasCode(Long companyId, String code, Long id) throws SQLException {
		return scheduleRepository.hasCode(companyId, code, id) > 0 ? true : false;
	}

    @Override
    public List<Select2Dto> getListSelect2DtoByCompanyId(String term, Long companyId, boolean isPaging) throws SQLException {
        if(null!= companyId && companyId.equals(0L)) {
            companyId = UserProfileUtils.getCompanyId();
        }
        return scheduleRepository.getListSelect2DtoByCompanyId(term, companyId, isPaging);
    }
}

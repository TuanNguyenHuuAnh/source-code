/*******************************************************************************
 * Class        ：QrtzMScheduleServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;
import vn.com.unit.quartz.job.repository.QrtzMScheduleRepository;
import vn.com.unit.quartz.job.service.QrtzMScheduleService;

/**
 * <p>
 * QrtzMScheduleServiceImpl
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional
public class QrtzMScheduleServiceImpl extends AbstractQrtzJobService implements QrtzMScheduleService {

    /** The Constant DATE_PATTERN_INDEX. */
    private static final int[] DATE_PATTERN_INDEX = { 4, 7 };

    /** The Constant REGEX_COLON. */
    private static final String REGEX_COLON = ":";

    /** The Constant REGEX_SLASH. */
    private static final String REGEX_SLASH = "/";

    /** The schedule repository. */
    @Autowired
    private QrtzMScheduleRepository scheduleRepository;
    
    private static final Long SYSTEM_ID = 0l;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMScheduleService#cronCheck(java.lang.String)
     */
    @Override
    public Boolean cronCheck(String cron) {
        return CronExpression.isValidExpression(cron);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMScheduleService#getByScheduleCode(java.lang.String, java.lang.Long)
     */
    @Override
    public QrtzMSchedule getByScheduleId(Long schedId, Long companyId) {
        QrtzMSchedule qSchedMaster = scheduleRepository.getByScheduleId(schedId, companyId);
        return qSchedMaster == null ? new QrtzMSchedule() : qSchedMaster;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMScheduleService#isScheduleInUse(java.lang.String, java.lang.Long)
     */
    @Override
    public Boolean isScheduleInUse(String scheduleCode, Long companyId) {
        return scheduleRepository.isScheduleInUse(scheduleCode, companyId) != null;
    }

    /**
     * <p>
     * Refactor date.
     * </p>
     *
     * @author khadm
     * @param date
     *            type {@link String}
     * @return {@link String}
     */
    public String refactorDate(String date) {
        List<String> dateElementList = Arrays.asList(date.split(REGEX_SLASH));
        List<String> reverseDateElementList = Lists.reverse(dateElementList);
        return refactorDbDateTime(String.join(StringUtils.EMPTY, reverseDateElementList), DATE_PATTERN_INDEX, StringUtils.EMPTY);
    }

    /**
     * <p>
     * Refactor db date time.
     * </p>
     *
     * @author khadm
     * @param value
     *            type {@link String}
     * @param indexes
     *            type {@link int[]}
     * @param interval
     *            type {@link String}
     * @return {@link String}
     */
    private String refactorDbDateTime(String value, int[] indexes, String interval) {
        StringBuilder refactoredDate = new StringBuilder(value);
        Arrays.stream(indexes).forEach(index -> refactoredDate.insert(index, interval));
        return refactoredDate.toString();
    }

    /**
     * <p>
     * Refactor time.
     * </p>
     *
     * @author khadm
     * @param time
     *            type {@link String}
     * @return {@link String}
     */
    public String refactorTime(String time) {
        return time.replaceAll(REGEX_COLON, StringUtils.EMPTY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMScheduleService#hasCode(java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    public boolean hasCode(Long companyId, String code, Long id) throws SQLException {
        return scheduleRepository.hasCode(companyId, code, id) > 0 ? true : false;
    }

    //////////////////////// NEW/////////////////////////////////////

    @Override
    public QrtzMScheduleDto create(QrtzMScheduleDto qrtzMSchedule) throws DetailException {
        if (null == qrtzMSchedule) {
            throw new DetailException("REQUSET NULL");
        }
        QrtzMSchedule entity = null;
        if (null != qrtzMSchedule.getId()) {
            entity = scheduleRepository.findOne(qrtzMSchedule.getId());
        }
        if (null != entity) {
            entity = mapper.convertValue(qrtzMSchedule, QrtzMSchedule.class);
            entity.setUpdatedDate(new Date());
            entity.setUpdatedId(SYSTEM_ID);
            entity = scheduleRepository.update(entity);
        } else {
            entity = mapper.convertValue(qrtzMSchedule, QrtzMSchedule.class);
            entity.setCreatedDate(new Date());
            entity.setCreatedId(SYSTEM_ID);
            entity = scheduleRepository.create(entity);
        }
        return mapper.convertValue(entity, QrtzMScheduleDto.class);
    }

    @Override
    public QrtzMScheduleDto update(QrtzMScheduleDto qrtzMSchedule) throws DetailException {
        if (null == qrtzMSchedule) {
            throw new DetailException("REQUSET NULL");
        }
        if (null != qrtzMSchedule.getId()) {
            QrtzMSchedule entity = scheduleRepository.findOne(qrtzMSchedule.getId());
            if (null != entity) {
                entity = mapper.convertValue(qrtzMSchedule, QrtzMSchedule.class);
                entity.setUpdatedDate(new Date());
                entity.setUpdatedId(SYSTEM_ID);
                entity = scheduleRepository.update(entity);
                return mapper.convertValue(entity, QrtzMScheduleDto.class);
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }

    }

    @Override
    public boolean delete(Long id) throws DetailException {
        if (null != id) {
            QrtzMSchedule entity = scheduleRepository.findOne(id);
            if (null != entity) {
                entity.setDeletedDate(new Date());
                entity.setDeletedId(SYSTEM_ID);
                scheduleRepository.update(entity);
                return true;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }
    }

    @Override
    public List<QrtzMScheduleDto> list(QrtzMScheduleSearchDto searchDto, Pageable pageable) throws DetailException {
        return scheduleRepository.getSchedules(searchDto, pageable).getContent();
    }

    @Override
    public int count(QrtzMScheduleSearchDto searchDto) throws DetailException {
        return scheduleRepository.countScheduleByCondition(searchDto);
    }

    @Override
    public QrtzMScheduleDto detail(Long id) throws DetailException {
        if (null != id) {
            QrtzMSchedule entity = scheduleRepository.findOne(id);
            if (null != entity) {
                QrtzMScheduleDto qrtzMScheduleDto = mapper.convertValue(entity, QrtzMScheduleDto.class);
                return qrtzMScheduleDto;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("REQUEST NULL");
        }
    }
}

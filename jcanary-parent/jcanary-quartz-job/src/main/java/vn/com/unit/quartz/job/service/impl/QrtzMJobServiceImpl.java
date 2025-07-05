/*******************************************************************************
 * Class        ：QrtzMJobServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;
import vn.com.unit.quartz.job.dto.QrtzMJobSearchDto;
import vn.com.unit.quartz.job.dto.QrtzMJobStoreDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;
import vn.com.unit.quartz.job.enumdef.JobTypeEnum;
import vn.com.unit.quartz.job.enumdef.ValidFlagEnum;
import vn.com.unit.quartz.job.repository.QrtzMJobClassRepository;
import vn.com.unit.quartz.job.repository.QrtzMJobRepository;
import vn.com.unit.quartz.job.service.QrtzMJobService;
import vn.com.unit.quartz.job.service.QrtzMJobStoreService;

/**
 * <p>
 * QrtzMJobServiceImpl
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QrtzMJobServiceImpl extends AbstractQrtzJobService implements QrtzMJobService {

    /** The job repository. */
    /*
     * private static final String JOB_TYPE = "JOB_TYPE";
     * 
     * private static final String REGEX_MATCH_ANY_CHAR = ".*";
     */
    @Autowired
    private QrtzMJobRepository jobRepository;

    /** The job class repository. */
    @Autowired
    private QrtzMJobClassRepository jobClassRepository;

    /** The job type repository. */
   // @Autowired
   // private QrtzMJobTypeRepository jobTypeRepository;

    /** The job store service. */
    @Autowired
    private QrtzMJobStoreService jobStoreService;


    /** The msg. */
    @Autowired
    private MessageSource msg;
    
    private static final Long SYSTEM_ID = 0l;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobService#getByJobCode(java.lang.String)
     */
    @Override
    public QrtzMJob getByJobId(Long jobId) throws DetailException {
        QrtzMJob qJobMaster = jobRepository.getByJobId(jobId);
        return qJobMaster == null ? new QrtzMJob() : qJobMaster;
    }

    
    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobService#isJobInUse(java.lang.String)
     */
    @Override
    public boolean isJobInUse(String jobCode) {
        return jobRepository.getJobInUse(jobCode) != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobService#save(vn.com.unit.quartz.job.entity.QrtzMJob,
     * vn.com.unit.quartz.job.entity.QrtzMJobStore, java.util.Locale)
     */
    @Override
    public void save(QrtzMJob jobEntity, QrtzMJobStore jobStoreEntity, Locale locale) throws DetailException {
        try {
            boolean hasExisted = jobStoreService.isGroupCodeExists(jobEntity.getJobGroup());
            if (jobEntity.getId() == null && hasExisted) {
                throw new Exception(msg.getMessage("quartz.error.group.code.exists", null, locale));
            }

            if (JobTypeEnum.EXECUTE_SP.name().equalsIgnoreCase(String.valueOf(jobEntity.getJobType()))
                    || JobTypeEnum.REMIND_SP.name().equalsIgnoreCase(String.valueOf(jobEntity.getJobType()))) {
                QrtzMJobStore jobStoreTemp = jobStoreService.getByGroupCode(jobEntity.getJobGroup());
                jobStoreTemp.setGroupCode(jobEntity.getJobCode());
                jobStoreTemp.setExecOrder(1L);
                jobStoreTemp.setStoreName(jobStoreEntity.getStoreName());
                jobStoreService.create(mapper.convertValue(jobStoreTemp, QrtzMJobStoreDto.class));
            }
            jobEntity.setJobGroup(jobEntity.getJobCode());
            this.create(mapper.convertValue(jobEntity, QrtzMJobDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DetailException(e.getMessage());
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.dmcs.service.QrtzMJobService#getByJobGroup(java.lang.String)
     */
    @Override
    public QrtzMJob getByJobGroup(Long companyId, String jobGroup) throws DetailException {
        return jobRepository.getByJobGroup(companyId, jobGroup);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobService#getClassNameByPath(java.lang.String)
     */
    @Override
    public String getClassNameByPath(String path) throws DetailException {
        return jobClassRepository.findClassNameByPath(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.quartz.job.service.QrtzMJobService#hasCode(java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    public boolean hasCode(Long companyId, String code, Long id) throws SQLException {
        return jobRepository.hasCode(companyId, code, id) > 0 ? true : false;
    }

    ////////////////////////////// NEW///////////////////////////////////////

    @Override
    public QrtzMJobDto create(QrtzMJobDto qrtzMJob) throws DetailException {
        if (null == qrtzMJob) {
            throw new DetailException("REQUSET NULL");
        }
        QrtzMJob entity = null;
        if (null != qrtzMJob.getId()) {
            entity = jobRepository.findOne(qrtzMJob.getId());
        }
        if (null != entity) {
            entity = mapper.convertValue(qrtzMJob, QrtzMJob.class);
            entity.setUpdatedDate(new Date());
            entity.setUpdatedId(SYSTEM_ID);
            entity = jobRepository.update(entity);
        } else {
            entity = mapper.convertValue(qrtzMJob, QrtzMJob.class);
            entity.setCreatedDate(new Date());
            entity.setCreatedId(SYSTEM_ID);
            entity = jobRepository.create(entity);
        }
        return mapper.convertValue(entity, QrtzMJobDto.class);
    }

    @Override
    public QrtzMJobDto update(QrtzMJobDto qrtzMJob) throws DetailException {
        if (null == qrtzMJob) {
            throw new DetailException("REQUSET NULL");
        }
        if (null != qrtzMJob.getId()) {
            QrtzMJob entity = jobRepository.findOne(qrtzMJob.getId());
            if (null != entity) {
                entity = mapper.convertValue(qrtzMJob, QrtzMJob.class);
                entity.setUpdatedDate(new Date());
                entity.setUpdatedId(SYSTEM_ID);
                entity = jobRepository.update(entity);
                return mapper.convertValue(entity, QrtzMJobDto.class);
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
            QrtzMJob entity = jobRepository.findOne(id);
            if (null != entity) {
                entity.setDeletedDate(new Date());
                entity.setValidflag(ValidFlagEnum.DELETED.toLong());
                entity.setDeletedId(SYSTEM_ID);
                jobRepository.update(entity);
                return true;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("ID NULL");
        }
    }

    @Override
    public List<QrtzMJobDto> list(QrtzMJobSearchDto searchDto, Pageable pageable) throws DetailException {
        return jobRepository.getJobs(searchDto, pageable).getContent();
    }

    @Override
    public int count(QrtzMJobSearchDto searchDto) throws DetailException {
        return jobRepository.countJobByCondition(searchDto);
    }

    @Override
    public QrtzMJobDto detail(Long id) throws DetailException {
        if (null != id) {
            QrtzMJob entity = jobRepository.findOne(id);
            if (null != entity) {
                QrtzMJobDto qrtzMJobDto = mapper.convertValue(entity, QrtzMJobDto.class);
                return qrtzMJobDto;
            } else {
                throw new DetailException("NOT FOUND");
            }
        } else {
            throw new DetailException("REQUEST NULL");
        }
    }

}

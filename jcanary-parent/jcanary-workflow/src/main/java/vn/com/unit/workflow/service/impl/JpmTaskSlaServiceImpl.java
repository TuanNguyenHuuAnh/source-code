/*******************************************************************************
 * Class        ：JpmTaskSlaServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.sla.dto.SlaDateResult;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmTaskSlaDto;
import vn.com.unit.workflow.entity.JpmHiTaskSla;
import vn.com.unit.workflow.entity.JpmTaskSla;
import vn.com.unit.workflow.repository.JpmTaskSlaRepository;
import vn.com.unit.workflow.service.JpmHiTaskSlaService;
import vn.com.unit.workflow.service.JpmTaskSlaService;

/**
 * <p>
 * JpmTaskSlaServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmTaskSlaServiceImpl implements JpmTaskSlaService {

    /** The jpm task sla repository. */
    @Autowired
    private JpmTaskSlaRepository jpmTaskSlaRepository;
    
    @Autowired
    private JpmHiTaskSlaService jpmHiTaskSlaService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public DbRepository<JpmTaskSla, Long> initRepo() {
        return jpmTaskSlaRepository;
    }

    @Override
    public JpmTaskSla createJpmTaskSlaBySlaDateResult(Long jpmTaskId, SlaDateResult slaDateResult) {
        JpmTaskSla jpmTaskSla = new JpmTaskSla();
      jpmTaskSla.setJpmTaskId(jpmTaskId);
      jpmTaskSla.setSlaConfigId(slaDateResult.getSlaConfigId());
      jpmTaskSla.setPlanStartDate(slaDateResult.getPlanStartDate());
      jpmTaskSla.setPlanDueDate(slaDateResult.getPlanDueDate());
      jpmTaskSla.setPlanEstimateTime(Long.valueOf(slaDateResult.getPlanEstimateTime()));
      jpmTaskSla.setPlanCalandarType(slaDateResult.getPlanCalandarType());
      jpmTaskSla.setPlanEstimateUnitTime(slaDateResult.getPlanEstimateUnitTime());
      jpmTaskSla.setPlanTotalTime(slaDateResult.getPlanTotalTime());
      jpmTaskSla.setActualStartDate(slaDateResult.getActualStartDate());
      return jpmTaskSlaRepository.create(jpmTaskSla);
    }

    @Override
    public void moveJpmTaskSlaToJpmHiTaskSla(Long jpmTaskId, Date completeDate, Long elapsedMinutes) {
        JpmTaskSla jpmTaskSla = jpmTaskSlaRepository.findOne(jpmTaskId);
        if(null != jpmTaskSla) {
            jpmTaskSla.setActualElapseTime(elapsedMinutes);
            jpmTaskSla.setActualEndDate(completeDate);
            // move to his
            JpmHiTaskSla jpmHiTaskSla = objectMapper.convertValue(jpmTaskSla, JpmHiTaskSla.class);
            jpmHiTaskSlaService.create(jpmHiTaskSla);
            //delete jpmTaskSla
            jpmTaskSlaRepository.delete(jpmTaskId);
        }
    }

    @Override
    public JpmTaskSlaDto getJpmTaskSlaDtoByJpmTaskId(Long jpmTaskId) {
        return jpmTaskSlaRepository.getJpmTaskSlaDtoByJpmTaskId(jpmTaskId);
    }

    @Override
    public List<JpmTaskSlaDto> getJpmTaskSlaDtoListByDocId(Long docId) {
        return jpmTaskSlaRepository.getJpmTaskSlaDtoListByDocId(docId);
    }

}

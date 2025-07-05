/*******************************************************************************
 * Class        ：JpmHiTaskSlaServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmHiTaskSlaDto;
import vn.com.unit.workflow.entity.JpmHiTaskSla;
import vn.com.unit.workflow.repository.JpmHiTaskSlaRepository;
import vn.com.unit.workflow.service.JpmHiTaskSlaService;

/**
 * <p>
 * JpmHiTaskSlaServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiTaskSlaServiceImpl implements JpmHiTaskSlaService{

    @Autowired
    private JpmHiTaskSlaRepository jpmHiTaskSlaRepository;
    
    @Override
    public DbRepository<JpmHiTaskSla, Long> initRepo() {
        return jpmHiTaskSlaRepository;
    }

    @Override
    public List<JpmHiTaskSlaDto> getJpmHiTaskSlaDtoListByDocId(Long docId) {
        return jpmHiTaskSlaRepository.getJpmHiTaskSlaDtoListByDocId(docId);
    }

}

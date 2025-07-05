/*******************************************************************************
* Class        JpmProcessDmnServiceImpl
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmProcessDmnDto;
import vn.com.unit.workflow.entity.JpmProcessDmn;
import vn.com.unit.workflow.repository.JpmProcessDmnRepository;
import vn.com.unit.workflow.service.JpmProcessDmnService;

/**
 * JpmProcessDmnServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmProcessDmnServiceImpl implements JpmProcessDmnService {

    @Autowired
    private JpmProcessDmnRepository jpmProcessDmnRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmProcessDmnDto getJpmProcessDmnDtoById(Long id) {
        JpmProcessDmnDto jpmProcessDmnDto = new JpmProcessDmnDto();
        if (null != id) {
            JpmProcessDmn jpmProcessDmn = jpmProcessDmnRepository.findOne(id);
            if (null != jpmProcessDmn) {
                jpmProcessDmnDto = objectMapper.convertValue(jpmProcessDmn, JpmProcessDmnDto.class);
            }
        }
        return jpmProcessDmnDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmProcessDmn jpmProcessDmn = jpmProcessDmnRepository.findOne(id);
            if (null != jpmProcessDmn && Long.valueOf(0L).equals(jpmProcessDmn.getDeletedId())) {
                jpmProcessDmn.setDeletedId(userId);
                jpmProcessDmn.setDeletedDate(sysDate);
                jpmProcessDmnRepository.update(jpmProcessDmn);
                res = true;
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
	public JpmProcessDmn saveJpmProcessDmn(JpmProcessDmn jpmProcessDmn){
		Long userId =UserProfileUtils.getAccountId();
		Date sysDate = CommonDateUtil.getSystemDateTime();
		Long id = jpmProcessDmn.getId();
		if (null != id){
			JpmProcessDmn oldJpmProcessDmn = jpmProcessDmnRepository.findOne(id);
			if (null != oldJpmProcessDmn && Long.valueOf(0L).equals(oldJpmProcessDmn.getDeletedId())){
				jpmProcessDmn.setCreatedId(oldJpmProcessDmn.getCreatedId());
				jpmProcessDmn.setCreatedDate(oldJpmProcessDmn.getCreatedDate());
				jpmProcessDmn.setUpdatedId(userId);
				jpmProcessDmn.setUpdatedDate(sysDate);
				jpmProcessDmnRepository.update(jpmProcessDmn);
			}
		} else {
			jpmProcessDmn.setCreatedId(userId);
			jpmProcessDmn.setCreatedDate(sysDate);
			jpmProcessDmnRepository.create(jpmProcessDmn);
		}
		return jpmProcessDmn;
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcessDmn saveJpmProcessDmnDto(JpmProcessDmnDto jpmProcessDmnDto) {
        JpmProcessDmn jpmProcessDmn = objectMapper.convertValue(jpmProcessDmnDto, JpmProcessDmn.class);
        return this.saveJpmProcessDmn(jpmProcessDmn);
    }

    @Override
    public List<JpmProcessDmnDto> getJpmProcessDmnDtosByProcessId(Long processId) {
        List<JpmProcessDmnDto> processDmnDtos = new ArrayList<>();
        if (null != processId) {
            processDmnDtos = jpmProcessDmnRepository.getJpmProcessDmnDtosByProcessId(processId);
        }
        return processDmnDtos;
    }

}
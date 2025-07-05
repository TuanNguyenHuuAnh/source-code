/*******************************************************************************
* Class        JpmButtonStepDefaultServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmButtonStepDefaultDto;
import vn.com.unit.workflow.entity.JpmButtonStepDefault;
import vn.com.unit.workflow.repository.JpmButtonStepDefaultRepository;
import vn.com.unit.workflow.service.JpmButtonStepDefaultService;

/**
 * JpmButtonStepDefaultServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmButtonStepDefaultServiceImpl implements JpmButtonStepDefaultService{

	@Autowired
	private JpmButtonStepDefaultRepository jpmButtonStepDefaultRepository;

	// Object mapper
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public JpmButtonStepDefaultDto getJpmButtonStepDefaultDtoById(Long id){
		JpmButtonStepDefaultDto jpmButtonStepDefaultDto = new JpmButtonStepDefaultDto();
		if (null != id){
			JpmButtonStepDefault jpmButtonStepDefault = jpmButtonStepDefaultRepository.findOne(id);
			if (null != jpmButtonStepDefault){
			jpmButtonStepDefaultDto = objectMapper.convertValue(jpmButtonStepDefault, JpmButtonStepDefaultDto.class);
			}
		}
		return jpmButtonStepDefaultDto;
	}

	@Override
	public boolean deleteById(Long id){
		boolean res = false;
        if (null != id) {
            try {
                jpmButtonStepDefaultRepository.delete(id);
                res = true;
            } catch (Exception e) {
            }
        }
        return res;
	}

	@Override
	public JpmButtonStepDefault saveJpmButtonStepDefault(JpmButtonStepDefault jpmButtonStepDefault){
		Long userId = UserProfileUtils.getAccountId();
		Date sysDate = CommonDateUtil.getSystemDateTime();
		Long id = jpmButtonStepDefault.getId();
		if (null != id){
			JpmButtonStepDefault oldJpmButtonStepDefault = jpmButtonStepDefaultRepository.findOne(id);
			if (null != oldJpmButtonStepDefault){
				jpmButtonStepDefault.setCreatedId(oldJpmButtonStepDefault.getCreatedId());
				jpmButtonStepDefault.setCreatedDate(oldJpmButtonStepDefault.getCreatedDate());
				jpmButtonStepDefaultRepository.update(jpmButtonStepDefault);
			}
		} else {
			jpmButtonStepDefault.setCreatedId(userId);
			jpmButtonStepDefault.setCreatedDate(sysDate);
			jpmButtonStepDefaultRepository.create(jpmButtonStepDefault);
		}
		return jpmButtonStepDefault;
	}

	@Override
	public JpmButtonStepDefault saveJpmButtonStepDefaultDto(JpmButtonStepDefaultDto jpmButtonStepDefaultDto){
		JpmButtonStepDefault jpmButtonStepDefault = objectMapper.convertValue(jpmButtonStepDefaultDto, JpmButtonStepDefault.class);
		return this.saveJpmButtonStepDefault(jpmButtonStepDefault);
	}

}
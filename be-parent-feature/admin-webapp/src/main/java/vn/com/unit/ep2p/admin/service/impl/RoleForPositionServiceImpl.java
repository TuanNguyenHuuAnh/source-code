/*******************************************************************************
 * Class        PositionAuthorityServiceImpl
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/0801-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaPositionAuthorityDto;
import vn.com.unit.core.dto.JcaRoleForPositionDto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.core.entity.JcaRoleForPosition;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.repository.PositionRepository;
import vn.com.unit.ep2p.admin.repository.RoleForPositionRepository;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.RoleForPositionService;

/**
 * PositionAuthorityServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForPositionServiceImpl implements RoleForPositionService {
	
	/** PositionAuthorityRepository */
	@Autowired
	private RoleForPositionRepository roleForPositionRepository;
	
	@Autowired
    private CommonService comService;
	
	@Autowired
	private PositionRepository positionRepository;
	
	@Override
	public List<JcaRoleForPositionDto> getRoleForPositionDtoListByPositionId(Long positionId) {	
		JcaPosition position = positionRepository.findOne(positionId);
        Long companyId = null != position ? position.getCompanyId() : null;
		List<JcaRoleForPositionDto> roleForPositionDtoList = new ArrayList<>();
		if (null != positionId) {
			roleForPositionDtoList = roleForPositionRepository.findRoleForPositionDtoListByPositionId(positionId, companyId);
		}
		return roleForPositionDtoList;
	}

	@Override
	@Transactional
	public void savePositionAuthorityDto(JcaPositionAuthorityDto positionAuthorityDto) {
		List<JcaRoleForPosition> roleForPositionList = new ArrayList<>();
		Date systemDate = comService.getSystemDateTime();
		
		List<JcaRoleForPositionDto> roleForPositionDtoList = positionAuthorityDto.getData();
		if( roleForPositionDtoList != null && !roleForPositionDtoList.isEmpty() ) {
			Long positionId = positionAuthorityDto.getPositionId();
			
			for (JcaRoleForPositionDto roleForPositionDto : roleForPositionDtoList) {
				boolean checked = Optional.ofNullable(roleForPositionDto.getChecked()).orElse(Boolean.FALSE);
				Long roleId = roleForPositionDto.getRoleId();
				
				if( checked ) {
				    JcaRoleForPositionDto jcaRoleForPositionDto = roleForPositionRepository.findByPositionIdAndRoleId(positionId, roleId);
					if (jcaRoleForPositionDto == null) {
					    JcaRoleForPosition roleForPosition = new JcaRoleForPosition();
                        roleForPosition.setPositionId(positionId);
                        roleForPosition.setRoleId(roleId);
                        roleForPosition.setCreatedId(UserProfileUtils.getAccountId());
                        roleForPosition.setCreatedDate(systemDate);
                        roleForPositionList.add(roleForPosition);
                        roleForPositionRepository.create(roleForPosition);
                    }    
				} else {
				    roleForPositionRepository.deleteByPositionIdAndRoleId(positionId, roleId);
				}
			}
		}
	}
	
	public void saveRoleForPositon(List<JcaRoleForPosition> roleForPositionList) {
	    for (JcaRoleForPosition jcaRoleForPosition : roleForPositionList) {
                roleForPositionRepository.create(jcaRoleForPosition);
        }
	}
	
	/**
	 * UpdateRolePositionParamDto use for call procedure
	 * 
	 * @version 01-00
	 * @since 01-00
	 * @author KhuongTH
	 */
	/**
		private class UpdateRolePositionParamDto {
	        @In
	        public Long positionId;
	        @Out
	        public String mes;
	    }
    */
}

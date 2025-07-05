/*******************************************************************************
 * Class        ：JcaRoleForPositionServiceImpl
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：taitt
 * Change log   ：2021/01/25：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaPositionAuthorityDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForPositionDto;
import vn.com.unit.core.entity.JcaRoleForPosition;
import vn.com.unit.core.repository.JcaRoleForPositionRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaRoleForPositionService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRoleForPositionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true ,rollbackFor = Exception.class)
public class JcaRoleForPositionServiceImpl implements JcaRoleForPositionService{

    @Autowired
    private JcaRoleForPositionRepository jcaRoleForPositionRepository;
    
    @Autowired
    private JCommonService commonService;

    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaRoleForPosition saveJcaRoleForPosition(JcaRoleForPosition jcaRoleForPosition) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = 1l;//jcaRoleForPosition.getId();
        if(null != id) {
            JcaRoleForPosition oldJcaRoleForPosition =  jcaRoleForPositionRepository.findOne(id);
            if (null != oldJcaRoleForPosition) {
                jcaRoleForPosition.setCreatedDate(oldJcaRoleForPosition.getCreatedDate());
                jcaRoleForPosition.setCreatedId(oldJcaRoleForPosition.getCreatedId());
                jcaRoleForPositionRepository.update(jcaRoleForPosition);
            }
            
        }else {
            jcaRoleForPosition.setCreatedDate(sysDate);
            jcaRoleForPosition.setCreatedId(userId);
            jcaRoleForPositionRepository.create(jcaRoleForPosition);
        }
        return jcaRoleForPosition;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJcaPositionAuthorityDto(JcaPositionAuthorityDto jcaPositionAuthorityDto) {
        List<Long> idDeleteList = new ArrayList<>();
        Date systemDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        
        List<JcaRoleForPositionDto> jcaRoleForPositionDtoList = jcaPositionAuthorityDto.getData();
        if(CommonCollectionUtil.isNotEmpty(jcaRoleForPositionDtoList)) {
            Long positionId = jcaPositionAuthorityDto.getPositionId();
            
            for (JcaRoleForPositionDto roleForPositionDto : jcaRoleForPositionDtoList) {
                Long idRoleForPosition = roleForPositionDto.getId();
                Boolean checked = roleForPositionDto.getChecked();
                if(checked) {
                    if( idRoleForPosition == null ) {
                        JcaRoleForPosition jcaRoleForPosition = new JcaRoleForPosition();
                        jcaRoleForPosition.setPositionId(positionId);
                        
                        Long roleId = roleForPositionDto.getRoleId();
                        jcaRoleForPosition.setRoleId(roleId);
                        jcaRoleForPosition.setCreatedId(userId);
                        jcaRoleForPosition.setCreatedDate(systemDate);
                        
                        this.saveJcaRoleForPosition(jcaRoleForPosition);
                    }
                } else {
                    if( idRoleForPosition != null ) {
                        idDeleteList.add(idRoleForPosition);
                    }
                }
            }
            
            if( !idDeleteList.isEmpty() ) {
                jcaRoleForPositionRepository.deleteJcaRoleForPositionByIds(idDeleteList, userId, systemDate);
            }
        }
    }
    
    @Override
    public List<JcaRoleForPositionDto> getJcaRoleForPositionDtoListByPositionId(Long positionId,Long companyId) {    
        List<JcaRoleForPositionDto> roleForPositionDtoList = new ArrayList<>();
        if (null != positionId) {
            roleForPositionDtoList = jcaRoleForPositionRepository.getJcaRoleForPositionDtoListByPositionId(positionId, companyId);
        }
        return roleForPositionDtoList;
    }

    @Override
    public List<JcaRoleDto> getJcaRoleDtoByPositionIdAndCompanyId(Long positionId, Long companyId) {
        return jcaRoleForPositionRepository.getJcaRoleDtoByPositionIdAndCompanyId(positionId, companyId);
    }

    @Override
    public DbRepository<JcaRoleForPosition, Long> initRepo() {
        return jcaRoleForPositionRepository;
    }
    
}

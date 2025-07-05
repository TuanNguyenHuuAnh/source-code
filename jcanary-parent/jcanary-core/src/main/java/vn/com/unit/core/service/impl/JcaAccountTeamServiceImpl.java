/*******************************************************************************
 * Class        ：JcaAccountTeamServiceImpl
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountTeamSearchDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountTeam;
import vn.com.unit.core.repository.JcaAccountTeamRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountTeamService;

/**
 * JcaAccountTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaAccountTeamServiceImpl implements JcaAccountTeamService{

    @Autowired
    private JcaAccountTeamRepository jcaAccountTeamRepository;
    
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaAccountTeamService#getJcaAccountByTeamId(java.lang.Long)
     */
    @Override
    public List<JcaAccountDto> getJcaAccountDtoByTeamId(Long teamId) {
        return jcaAccountTeamRepository.getJcaAccountDtoByTeamId(teamId);
    }
    
    @Override
    public List<JcaAccountTeam> getJcaAccountTeamByTeamIdAndAccountIds(Long teamId,List<Long> accountIds) {
        return jcaAccountTeamRepository.getJcaAccountTeamByTeamIdAndAccountIds(teamId, accountIds);
    }
    
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaAccountTeam saveJcaAccountTeam(JcaAccountTeam jcaAccountTeam) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long accId = jcaAccountTeam.getAccountId();
        Long teamId = jcaAccountTeam.getTeamId();
        Long userId = UserProfileUtils.getUserPrincipal() != null ? UserProfileUtils.getUserPrincipal().getAccountId() : 2L;
        if((null != accId) && (null != teamId)) {
            JcaAccountTeam oldJcaAccountTeam =  jcaAccountTeamRepository.findOne(accId, teamId);
            if (null != oldJcaAccountTeam) {
                jcaAccountTeam.setCreatedDate(oldJcaAccountTeam.getCreatedDate());
                jcaAccountTeam.setCreatedId(oldJcaAccountTeam.getCreatedId());
                jcaAccountTeamRepository.update(jcaAccountTeam);
            } else {
                jcaAccountTeam.setCreatedDate(sysDate);
                jcaAccountTeam.setCreatedId(userId);
                jcaAccountTeamRepository.create(jcaAccountTeam);
            }
            
        }else {
            jcaAccountTeam.setCreatedDate(sysDate);
            jcaAccountTeam.setCreatedId(userId);
            jcaAccountTeamRepository.create(jcaAccountTeam);
        }
        return jcaAccountTeam;
    }

    @Override
    public void removeAccountGroup(JcaAccount jcaAccount, List<String> lstGroupId) {
        if(lstGroupId!= null && !lstGroupId.isEmpty()){
            Long accountId = jcaAccount.getId();
            if(accountId != null) {
                jcaAccountTeamRepository.removeAccountGroup(accountId, lstGroupId);
            }
        }
    }

    @Override
    public List<JcaTeamDto> getJcaTeamDtoByCondition(JcaAccountTeamSearchDto jcaAccountTeamSearchDto, Pageable pagable) {
        return jcaAccountTeamRepository.getJcaTeamDtoByCondition(jcaAccountTeamSearchDto, pagable).getContent();
    }
    
    @Override
    public int countJcaTeamDtoByCondition(JcaAccountTeamSearchDto jcaAccountTeamSearchDto) {
        return jcaAccountTeamRepository.countJcaTeamDtoByCondition(jcaAccountTeamSearchDto);
    }
}

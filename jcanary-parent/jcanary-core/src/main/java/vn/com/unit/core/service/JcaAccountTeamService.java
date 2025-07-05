/*******************************************************************************
 * Class        ：JcaAccountTeamService
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountTeamDto;
import vn.com.unit.core.dto.JcaAccountTeamSearchDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountTeam;

/**
 * JcaAccountTeamService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface JcaAccountTeamService {
    static final String TABLE_ALIAS_JCA_ACCOUNT_TEAM = "accTeam";
    
    List<JcaAccountDto> getJcaAccountDtoByTeamId(Long teamId);

    /**
     * saveJcaAccountTeam
     * @param jcaAccountTeam
     * @return
     * @author taitt
     */
    JcaAccountTeam saveJcaAccountTeam(JcaAccountTeam jcaAccountTeam);

    /**
     * getJcaAccountTeamByTeamIdAndAccountIds
     * @param teamId
     * @param accountIds
     * @return
     * @author taitt
     */
    List<JcaAccountTeam> getJcaAccountTeamByTeamIdAndAccountIds(Long teamId, List<Long> accountIds);

    /**
     * getJcaAccountTeamDtoByCondition
     * @param jcaAccountTeamSearchDto
     * @param pagable
     * @return
     * @author taitt
     */
    List<JcaTeamDto> getJcaTeamDtoByCondition(JcaAccountTeamSearchDto jcaAccountTeamSearchDto, Pageable pagable);

    /**
     * countJcaAccountTeamDtoByCondition
     * @param jcaAccountTeamSearchDto
     * @param pagable
     * @return
     * @author taitt
     */
    int countJcaTeamDtoByCondition(JcaAccountTeamSearchDto jcaAccountTeamSearchDto);

    void removeAccountGroup(JcaAccount jcaAccount, List<String> lstGroup);
}

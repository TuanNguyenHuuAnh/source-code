/*******************************************************************************
 * Class        ：JcaAccountTeamRepository
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountTeamDto;
import vn.com.unit.core.dto.JcaAccountTeamSearchDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaAccountTeam;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaAccountTeamRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface JcaAccountTeamRepository extends DbRepository<JcaAccountTeam, Long>{
    
    
    int countJcaTeamDtoByCondition(@Param("jcaAccountTeamSearchDto") JcaAccountTeamSearchDto jcaAccountTeamSearchDto);
    
    Page<JcaTeamDto> getJcaTeamDtoByCondition(@Param("jcaAccountTeamSearchDto") JcaAccountTeamSearchDto jcaAccountTeamSearchDto, Pageable pageable);
    
    JcaAccountTeamDto getJcaAccountTeamDtoById(@Param("id") Long id);
    
    List<JcaAccountDto> getJcaAccountDtoByTeamId(@Param("teamId") Long teamId);
    
    List<JcaAccountTeam> getJcaAccountTeamByTeamIdAndAccountIds(@Param("teamId") Long teamId,@Param("accountIds") List<Long> accountIds);
    
    JcaAccountTeam findOne(@Param("accId") Long accId,@Param("teamId") Long teamId);

    @Modifying
    void removeAccountGroup(@Param("accountId") Long accountId, @Param("lstGroupId") List<String> lstGroupId);
}

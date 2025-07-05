/*******************************************************************************
 * Class        TeamRepository
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phatvt
 * Change log   2017/09/1201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaTeam;
import vn.com.unit.core.repository.JcaTeamRepository;
import vn.com.unit.ep2p.admin.dto.TeamSearchDto;

/**
 * TeamRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public interface TeamRepository extends JcaTeamRepository{
	
	/**
	 * Update all team to disable.
	 * TeamRepository_updateAllTeamToDisable
	 * 
	 * void
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	@Modifying
	public void updateAllTeamToDisable();
		
	/**
	 * Find by code.
	 * TeamRepository_findByCode
	 * @param code
	 * @return
	 * List<Team>
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public List<JcaTeam> findByCode(@Param("code") String code);
	
    /**
     * @param offset
     * @param sizeOfPage
     * @param searchDto
     * @return
     */
    public List<JcaTeamDto> findAllAccountTeamByCondition(
            @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage,
            @Param("searchDto") TeamSearchDto searchDto);
    
    public List<JcaTeamDto> findAllAccountTeamByConditionOracle(
            @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage,
            @Param("searchDto") TeamSearchDto searchDto);
    /**
     * 
     * @param searchDto
     * @return
     */
    public int countFindAllAccountTeam(
            @Param("searchDto") TeamSearchDto searchDto);
    
    public int countFindAllAccountTeamOracle(
            @Param("searchDto") TeamSearchDto searchDto);
    /**
     * 
     * @param id
     * @return
     */
    public JcaTeamDto getTeamById(
            @Param("id") Long id);
    /**
     * 
     * @param teamId
     * @return
     */
    public List<JcaAccountDto> getListAccountForTeam(@Param("teamid") Long id);
    /**
     * 
     * @return
     */
    public List<JcaAccountDto> getListAccount(@Param("companyId") Long companyId);
    
    @Modifying
    public void deleteAllUserTeam(@Param("teamid") Long id);
    
    @Modifying
    public void disableAllUserTeam(@Param("teamid") Long id, @Param("userId") Long userId);

    @Modifying
    public void disableAllUserTeamOracle(@Param("teamid") Long id, @Param("userId") Long userId);

    /**
     * findAllTeam
     *
     * @return
     * @author hand
     */
    public List<JcaTeamDto> findAllTeam(@Param("companyId") Long companyId, @Param("companyAdmin") boolean companyAdmin);
    
    /**
     * findAllTeamForOracle
     *
     * @return
     * @author phatvt
     */
    public List<JcaTeamDto> findAllTeamForOracle(@Param("companyId") Long companyId, @Param("companyAdmin") boolean companyAdmin);
    
    /**
     * countRoleTypeOfAccount
     * @param accountId
     * @param teamId
     * @return
     * @author HungHT
     */
    public int countRoleTypeOfAccount(@Param("accountId") Long accountId,  @Param("teamId") Long teamId);
    
    /**
     * findByCodeAndCompanyId
     * 
     * @param code
     * @param companyId
     * @return
     * @author HungHT
     */
    public JcaTeamDto findByCodeAndCompanyId(@Param("code") String code, @Param("companyId") Long companyId);
    
    /**
     * findByCompanyId
     * @param companyId
     * @return
     * @author trieuvd
     */
    public List<JcaTeamDto> findByCompanyId(@Param("companyId") Long companyId);
    
    /**
     * findByCompanyIdForUser
     * @param companyId
     * @return
     * @author trieuvd
     */
    public List<JcaTeamDto> findByCompanyIdForUser(@Param("companyId") Long companyId);
    
    /**
     * countAccountForTeam
     * @param teamId
     * @return int
     * @author KhuongTH
     */
    public int countAccountForTeam(@Param("keySearch") String keySearch
            , @Param("teamId") Long teamId);
    
    public List<JcaAccountDto> findAccountForTeam(@Param("keySearch") String keySearch
            , @Param("teamId") Long teamId
            , @Param("startIndex") int startIndex
            , @Param("sizeOfPage") int sizeOfPage);

    List<String> getListTeamIdByCode(@Param("lstGroup") List<String> lstGroup, @Param("companyId") Long companyId);
}

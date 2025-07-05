/*******************************************************************************
 * Class        AccountTeamService
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phatvt
 * Change log   2017/09/1201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaTeam;
import vn.com.unit.core.service.JcaTeamService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.TeamSearchDto;

/**
 * AccountTeamService
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
/**
 * TeamService
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface TeamService extends JcaTeamService{
    /**
     * 
     * @param page
     * @param searchDto
     * @param pageSize
     * @return
     * @throws DetailException 
     */
    public PageWrapper<JcaTeamDto> search(int page, TeamSearchDto searchDto,int pageSize) throws DetailException;
    /**
     * 
     * @param teamId
     * @return
     */
    public JcaTeamDto getTeam(String teamId);
    
    /**
     * setListAccountOfTeam
     * 
     * @param team
     * @author HungHT
     */
    public void setListAccountOfTeam(JcaTeamDto team);
    
    /**
     * 
     * @param teamDto
     */
    public void editTeamDto(JcaTeamDto teamDto) throws ParseException;
    /**
     * 
     * @param id
     */
    public void deleteTeamDto(Long id);
    
    /**
     * countRoleTypeOfAccount
     * @param accountId
     * @param teamId
     * @return
     * @author HungHT
     */
    int countRoleTypeOfAccount(Long accountId, Long teamId);
    
    /**
     * findByCodeAndCompanyId
     * 
     * @param code
     * @param companyId
     * @return
     * @author HungHT
     */
    public JcaTeamDto findByCodeAndCompanyId(String code, Long companyId);
    
    /**
     * findTeamByCompanyId
     * @param companyId
     * @return
     * @author trieuvd
     */
    public List<JcaTeamDto> findTeamByCompanyId(Long companyId);
    
    /**
     * getListUserForTeam
     * @param teamId
     * @param companyId
     * @return
     * @author trieuvd
     */
   
    public JcaTeamDto getListUserForTeam(Long teamId, Long companyId);
    
    /**
     * findTeamByCompanyIdForUser
     * @param companyId
     * @return
     * @author trieuvd
     */
    public List<JcaTeamDto> findTeamByCompanyIdForUser(Long companyId);
    
    /**
     * getListUserForTeam
     * @param keySearch
     * @param teamId
     * @param pageSize
     * @param page
     * @return PageWrapper<JcaAccountDto>
     * @author KhuongTH
     */
    public PageWrapper<JcaAccountDto> getListUserForTeam(String keySearch, Long teamId, int pageSize, int page);
    
    /**
     * addListUserForTeam
     * @param userIds
     * @param teamId
     * @return
     * @throws ParseException int
     * @author KhuongTH
     */
    public int addListUserForTeam(List<Long> userIds, Long teamId)  throws ParseException;
    
    /**
     * deleteListUserForTeam
     * @param userIds
     * @param teamId
     * @return int
     * @author KhuongTH
     */
    public int deleteListUserForTeam(List<Long> userIds, Long teamId);
    
    void getConstantDisplay(ModelAndView mav);
	/**
	 * @param code
	 * @return
	 */
	List<JcaTeam> listTeam(String code);

    List<String> getListTeamIdByCode(List<String> lstGroup, Long companyId);
}

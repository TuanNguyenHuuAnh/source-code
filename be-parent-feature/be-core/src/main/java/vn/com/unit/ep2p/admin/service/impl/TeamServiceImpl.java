/*******************************************************************************
 * Class        AccountTeamServiceImpl
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phatvt
 * Change log   2017/09/1201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaAccountTeam;
import vn.com.unit.core.entity.JcaTeam;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.impl.JcaTeamServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.TeamSearchDto;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.admin.repository.AccountTeamRepository;
import vn.com.unit.ep2p.admin.repository.TeamRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.TeamService;
import vn.com.unit.ep2p.enumdef.TeamEnum;

/**
 * AccountTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TeamServiceImpl extends JcaTeamServiceImpl implements TeamService, AbstractCommonService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    AccountTeamRepository accountTeamRepository;

    @Autowired
    private JCommonService commonService;

    @Autowired
    CompanyService companyService;

    private void setSearchParm(TeamSearchDto searchDto) {
        if (searchDto.getFieldValues() == null || searchDto.getFieldValues().isEmpty()) {
            searchDto.setNameAbv(searchDto.getFieldSearch());
            searchDto.setCode(searchDto.getFieldSearch());
        } else {
            for (String field : searchDto.getFieldValues()) {
                if (StringUtils.equals(field, TeamEnum.TEAMNAME.name())) {
                    searchDto.setNameAbv(searchDto.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, TeamEnum.TEAMCODE.name())) {
                    searchDto.setCode(searchDto.getFieldSearch().trim());
                    continue;
                }
            }
        }

        // Add company_id
//        searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        searchDto.setCompanyIdList(UserProfileUtils.getCompanyIdList());
        searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
    }

    @Override
    public PageWrapper<JcaTeamDto> search(int page, TeamSearchDto searchDto, int pageSize) {
        setSearchParm(searchDto);

        // Init PageWrapper
        PageWrapper<JcaTeamDto> wrapper = new PageWrapper<JcaTeamDto>();
        int sizeOfPage = systemConfig.settingPageSizeList(pageSize, wrapper, page);

        List<JcaTeamDto> list = new ArrayList<JcaTeamDto>();
        wrapper.setSizeOfPage(sizeOfPage);

        int count = teamRepository.countFindAllAccountTeam(searchDto);
        if (count > 0) {
            int currentPage = wrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            list = teamRepository.findAllAccountTeamByCondition(startIndex, sizeOfPage, searchDto);
        }
        
        wrapper.setDataAndCount(list, count);
        return wrapper;
    }

    @Override
    public JcaTeamDto getTeam(String teamId) {
        JcaTeamDto team = null;
        if (null != teamId) {
            team = teamRepository.getTeamById(Long.parseLong(teamId));
            if (null != team)
                setListAccountOfTeam(team);
        }
        return team;
    }

    public void setListAccountOfTeam(JcaTeamDto team) {
        List<JcaAccountDto> listA = null;
        listA = teamRepository.getListAccount(team.getCompanyId());
        if (null != team.getTeamId()) {
            // list account for team
            List<JcaAccountDto> listAcc = teamRepository.getListAccountForTeam(team.getTeamId());
            team.setListAccountOfTeam(listAcc);
            //
            List<JcaAccountDto> listAccountDto = new ArrayList<JcaAccountDto>();
            // set Map
            Map<Long, JcaAccountDto> mapData = new HashMap<Long, JcaAccountDto>();
            for (JcaAccountDto acc : listAcc) {
                mapData.put(acc.getUserId(), acc);
            }
            //
            for (JcaAccountDto acctDto : listA) {
                JcaAccountDto item = new JcaAccountDto();
                // item.setFlgChecked(false);
//                if (mapData.containsKey(acctDto.getAccountId())) {
//                    item.setFlgChecked(true);
//                }
                item.setUserId(acctDto.getUserId());
                item.setUsername(acctDto.getUsername());
                item.setFullname(acctDto.getFullname());
                // item.setTeamId(acctDto.getTeamId());
                item.setEmail(acctDto.getEmail());
                listAccountDto.add(item);
            }
            team.setListAccountOfTeam(listAccountDto);
        } else {
            // listA = teamRepository.getListAccount(UserProfileUtils.getCompanyId());
            team.setListAccountOfTeam(listA);
            team.setActived(true);
        }
    }

    @Override
    @Transactional
    public void editTeamDto(JcaTeamDto teamDto) throws ParseException {
        Long userId = UserProfileUtils.getAccountId();
        Long teamDtoId = teamDto.getTeamId();

        // update data
        JcaTeam editTeam = new JcaTeam();
        if (null != teamDtoId) {
            editTeam = teamRepository.findOne(teamDtoId);
            if (null == editTeam) {
                throw new BusinessException("Not found Team by id= " + teamDtoId);
            }
            editTeam.setUpdatedId(userId);
            editTeam.setUpdatedDate(CommonDateUtil.getSystemDateTime());
        } else {
            editTeam.setCreatedId(userId);
            editTeam.setCreatedDate(CommonDateUtil.getSystemDateTime());
            editTeam.setDeletedDate(null);
            editTeam.setDeletedId((long) 0);
            // Add company_id
            editTeam.setCompanyId(teamDto.getCompanyId());
        }
        editTeam.setCode(teamDto.getCode());
        editTeam.setNameAbv(teamDto.getNameAbv());
        editTeam.setName(teamDto.getNameAbv());
        editTeam.setDescription(teamDto.getDescription());
        // editTeam.setActived(teamDto.getActived());
        editTeam.setActived(true);

        // Admin group always active
        /*
         * if (systemConfig.getConfig(SystemConfig.ADMIN_GROUP_CODE).equalsIgnoreCase(
         * editTeam.getCode())) { editTeam.setActived(true); }
         */
        //
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//        Date effectedDate = CommonDateUtil.getSystemDate();
//        Date expiredDate = formatter.parse("31/12/9999 00:00:00");

        // teamRepository.save(editTeam);
        if (editTeam.getId() != null)
            teamRepository.update(editTeam);
        else
            teamRepository.create(editTeam);
        if (teamDtoId == null) {
            teamDtoId = editTeam.getId();
        }
        teamDto.setTeamId(teamDtoId);
        // save user into group
//        jpmIdentityService.deleteMembershipByTeamId(teamDtoId);
//        teamRepository.deleteAllUserTeam(teamDtoId);
//        if(teamDto.getData() != null){
//        	for(JcaAccountDto dto : teamDto.getData()){
//        		if(dto.isFlgChecked() == true){
//        			AccountTeam accTeam = new AccountTeam();
//                	accTeam.setAccountId(dto.getAccountId());
//                	accTeam.setTeamId(teamDtoId);
//                	accTeam.setEffectedDate(effectedDate);
//                	accTeam.setExpiredDate(expiredDate);
//                	accTeam.setCreatedBy(userNameLogin);
//                	accTeam.setCreatedDate(CommonDateUtil.getSystemDateTime());
//                	accountTeamRepository.save(accTeam);
//        		}        		
//        	}
//        }
//        
//        /**CALL PROCEDURE UPDATE ROLE FOR TEAM ACT_MEMBERSHIP*/
//        UpdateRoleTeamParamDto updateRoleTeamParamDto = new UpdateRoleTeamParamDto();
//        updateRoleTeamParamDto.teamId = teamDtoId;
//        updateRoleTeamParamDto.isDelete = ConstantCore.NUMBER_ZERO;
//        sqlManager.call("SP_UPDATE_ROLE_FOR_TEAM", updateRoleTeamParamDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeamDto(Long id) {
        Long userId = UserProfileUtils.getAccountId();
        JcaTeam team = new JcaTeam();
        if (id != null) {
            team = teamRepository.findOne(id);
        }
        team.setDeletedDate(CommonDateUtil.getSystemDateTime());
        team.setDeletedId(userId);
        try {
            if (team.getId() != null)
                teamRepository.update(team);
            else
                teamRepository.create(team);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
        if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
            teamRepository.disableAllUserTeamOracle(id, userId);
        } else {
            teamRepository.disableAllUserTeam(id, userId);
        }

    }

    @Override
    public int countRoleTypeOfAccount(Long accountId, Long teamId) {
        int result = 0;
        try {
            result = teamRepository.countRoleTypeOfAccount(accountId, teamId);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public JcaTeamDto findByCodeAndCompanyId(String code, Long companyId) {
        return teamRepository.findByCodeAndCompanyId(code, companyId);
    }

    @Override
    public List<String> getListTeamIdByCode(List<String> lstGroup, Long companyId) {
        return teamRepository.getListTeamIdByCode(lstGroup, companyId);
    }

    @Override
    public List<JcaTeamDto> findTeamByCompanyId(Long companyId) {
        return teamRepository.findByCompanyId(companyId);
    }

    @Override
    public JcaTeamDto getListUserForTeam(Long teamId, Long companyId) {
        JcaTeamDto team = new JcaTeamDto();
        if (null != teamId) {
            team = teamRepository.getTeamById(teamId);
        }
        team.setCompanyId(companyId);
        setListAccountOfTeam(team);
        return team;

    }

    @Override
    public List<JcaTeamDto> findTeamByCompanyIdForUser(Long companyId) {
        return teamRepository.findByCompanyIdForUser(companyId);
    }

    /**
     * @author KhuongTH
     */
    @Override
    public PageWrapper<JcaAccountDto> getListUserForTeam(String keySearch, Long teamId, int pageSize, int page) {

        List<Integer> listPageSize = systemConfig.getListPageSize();
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<JcaAccountDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);

        int count = teamRepository.countAccountForTeam(keySearch, teamId);
        List<JcaAccountDto> result = new ArrayList<>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = teamRepository.findAccountForTeam(keySearch, teamId, startIndex, sizeOfPage);
        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    /**
     * @author KhuongTH
     * @throws ParseException
     */
    @Override
    public int addListUserForTeam(List<Long> userIds, Long teamId) throws ParseException {
        int res = 0;
        List<JcaAccountDto> listCurrentAccount = teamRepository.getListAccountForTeam(teamId);
        List<Long> listCurrentAccountId = (listCurrentAccount == null || CollectionUtils.isEmpty(listCurrentAccount))
                ? new ArrayList<Long>()
                : listCurrentAccount.stream().map(JcaAccountDto::getUserId).collect(Collectors.toList());

        Long userCreateId = UserProfileUtils.getAccountId();
        String newUserIds = new String();
        for (Long userId : userIds) {
            if (!listCurrentAccountId.contains(userId)) {
                // List userId as string use for call procedure update act role.
                newUserIds = newUserIds.concat(String.valueOf(userId)).concat(ConstantCore.COMMA);

                JcaAccountTeam accTeam = new JcaAccountTeam();
                accTeam.setAccountId(userId);
                accTeam.setTeamId(teamId);
                accTeam.setCreatedId(userCreateId);
                accTeam.setCreatedDate(CommonDateUtil.getSystemDateTime());

                accountTeamRepository.create(accTeam);

                res++;
            }
        }

        return res;
    }

    /**
     * @author KhuongTH
     */
    @Override
    @Transactional
    public int deleteListUserForTeam(List<Long> userIds, Long teamId) {
        int res = 0;
        List<JcaAccountTeam> accountTeams = accountTeamRepository.findByAccountIdsAndTeamId(userIds, teamId);

        for (JcaAccountTeam accountTeam : accountTeams) {
            // List userId as string use for call procedure update act role.
            Long userId = accountTeam.getAccountId();

            accountTeamRepository.deleteAccountForTeam(userId, teamId);
            res++;
        }

        return res;
    }

    /**
     * UpdateRoleTeamParamDto use for call procedure
     * 
     * @version 01-00
     * @since 01-00
     * @author KhuongTH
     */
    @SuppressWarnings("unused")
    private class UpdateRoleTeamParamDto {
        @In
        public Long teamId;
        @In
        public String userIds;
        @Out
        public String mes;
    }

    @Override
    public JCommonService getCommonService() {
        return commonService;
    }

    @Override
    public void getConstantDisplay(ModelAndView mav) {
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

    }

    @Override
    public List<JcaTeam> listTeam(String code) {
        return teamRepository.findByCode(code);
    }

}

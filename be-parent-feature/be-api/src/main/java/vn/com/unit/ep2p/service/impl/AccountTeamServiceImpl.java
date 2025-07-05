/*******************************************************************************
 * Class        ：AccountTeamServiceImpl
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountTeamSearchDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountTeam;
import vn.com.unit.core.enumdef.param.JcaAccountTeamSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaAccountTeamService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.utils.DtsCollectionUtil;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.AccountTeamUpdateAccountReq;
import vn.com.unit.ep2p.dto.res.AccountTeamInfoRes;
import vn.com.unit.ep2p.dto.res.TeamInfoRes;
import vn.com.unit.ep2p.service.AccountTeamService;
import vn.com.unit.ep2p.service.TeamService;

/**
 * AccountTeamServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AccountTeamServiceImpl extends AbstractCommonService implements AccountTeamService {

    /** The team service. */
    @Autowired
    private TeamService teamService; 
    
    /** The jca account team service. */
    @Autowired
    private JcaAccountTeamService jcaAccountTeamService;
    
    @Autowired
    private MasterCommonService masterCommonService;
    
    
    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountTeamService#addAccountForTeam(vn.com.unit.mbal.api.req.dto.AccountTeamUpdateAccountReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAccountForTeam(AccountTeamUpdateAccountReq accountTeamAddAccountReq) throws DetailException {
        String newUserIds = CommonStringUtil.EMPTY;
        List<Long> accountIds = accountTeamAddAccountReq.getAccountIds();
        Long teamId = accountTeamAddAccountReq.getTeamId();
        try {
            this.validTeamId(teamId);
            
            List<JcaAccountDto> listCurrentAccount = jcaAccountTeamService.getJcaAccountDtoByTeamId(teamId);
            List<Long> listCurrentAccountId = (listCurrentAccount == null || DtsCollectionUtil.isEmpty(listCurrentAccount)) ? new ArrayList<>() :  listCurrentAccount.stream().map(JcaAccountDto::getUserId).collect(Collectors.toList());
            
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date effectedDate = commonService.getSystemDate();
//            Date expiredDate = formatter.parse("31/12/9999 00:00:00");
            Long userNameLogin = 1L;
            
            for(Long userId : accountIds){
                if(!listCurrentAccountId.contains(userId)){
                    //List userId as string use for call procedure update act role.
                    newUserIds = newUserIds.concat(String.valueOf(userId)).concat(AppApiConstant.COMMA);
                    
                    JcaAccountTeam accTeam = new JcaAccountTeam();
                    accTeam.setAccountId(userId);
                    accTeam.setTeamId(teamId);
                    accTeam.setCreatedId(userNameLogin);
                    accTeam.setCreatedDate(effectedDate);
                    jcaAccountTeamService.saveJcaAccountTeam(accTeam);
                }
            }
        }catch (Exception ex) {
            handlerCastException.castException(ex, AppApiExceptionCodeConstant.E4024201_APPAPI_ACCOUNT_TEAM_ADD_ERROR);
        }
    }
    
    /**
     * <p>
     * Valid team id.
     * </p>
     *
     * @param teamId
     *            type {@link Long}
     * @return {@link TeamInfoRes}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    private TeamInfoRes validTeamId(Long teamId) throws Exception {
        TeamInfoRes teamInfoRes = teamService.getTeamInfoById(teamId);
        if (null == teamInfoRes) {
            throw new DetailException(AppApiExceptionCodeConstant.E4024205_APPAPI_ACCOUNT_TEAM_ID_NOT_FOUND, new String[] { teamId.toString()} ,true);
        }
        return teamInfoRes;
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountTeamService#deleteListUserForTeam(vn.com.unit.mbal.api.req.dto.AccountTeamUpdateAccountReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteListUserForTeam(AccountTeamUpdateAccountReq accountTeamDeleteAccountReq) throws DetailException {
        String deletedUserIds = CommonStringUtil.EMPTY;
        List<Long> accountIds = accountTeamDeleteAccountReq.getAccountIds();
        Long teamId = accountTeamDeleteAccountReq.getTeamId();
        try {
            List<JcaAccountTeam> accountTeams = jcaAccountTeamService.getJcaAccountTeamByTeamIdAndAccountIds(teamId,accountIds);
            if(null != accountTeams && !CommonCollectionUtil.isEmpty(accountTeams)){
//                Date sysDate = commonService.getSystemDate();
//                Long userNameLogin = 1L;
                for(JcaAccountTeam accountTeam : accountTeams){
                    //List userId as string use for call procedure update act role.
                    Long userId = accountTeam.getAccountId();
                    deletedUserIds = deletedUserIds.concat(String.valueOf(userId)).concat(AppApiConstant.COMMA);
                    
//                    accountTeam.setDeletedId(userNameLogin);
//                    accountTeam.setDeletedDate(sysDate);
                    jcaAccountTeamService.saveJcaAccountTeam(accountTeam);
                }    
            }
        }catch (Exception ex) {
            handlerCastException.castException(ex, AppApiExceptionCodeConstant.E4024201_APPAPI_ACCOUNT_TEAM_ADD_ERROR);
        }

    }   



    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountTeamService#search(org.springframework.util.MultiValueMap, org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<JcaTeamDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<JcaTeamDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaAccount.class,JcaAccountService.TABLE_ALIAS_JCA_ACCOUNT);
            /** init param search repository */
            JcaAccountTeamSearchDto reqSearch = this.buildJcaAccountTeamSearchDto(commonSearch);
            
            int totalData = jcaAccountTeamService.countJcaTeamDtoByCondition(reqSearch);
            List<JcaTeamDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = jcaAccountTeamService.getJcaTeamDtoByCondition(reqSearch,pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024207_APPAPI_PROCESS_ACCOUNT_TEAM_ERROR);
        }
        return resObj;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountTeamService#detail(java.lang.Long)
     */
    @Override
    public AccountTeamInfoRes detail(Long teamId) throws DetailException {
        AccountTeamInfoRes accountTeamInfoRes = new AccountTeamInfoRes();
        try {
            TeamInfoRes teamInfoRes = teamService.getTeamInfoById(teamId);
            
            List<JcaAccountDto> jcaAccountDtoLst = jcaAccountTeamService.getJcaAccountDtoByTeamId(teamId);

            if (DtsCollectionUtil.isNotEmpty(jcaAccountDtoLst)){
                accountTeamInfoRes.setAccounts(jcaAccountDtoLst);
                accountTeamInfoRes.setCompanyId(teamInfoRes.getCompanyId());
                accountTeamInfoRes.setCompanyName(teamInfoRes.getCompanyName());
                accountTeamInfoRes.setDescription(teamInfoRes.getDescription());
                accountTeamInfoRes.setTeamCode(teamInfoRes.getCode());
                accountTeamInfoRes.setTeamId(teamId);
                accountTeamInfoRes.setTeamName(teamInfoRes.getNameAbv());
            }
        }catch (Exception ex) {
            handlerCastException.castException(ex, AppApiExceptionCodeConstant.E4024204_APPAPI_ACCOUNT_TEAM_NOT_FOUND);
        }
        return accountTeamInfoRes;
    }
    
    /**
     * <p>
     * Builds the jca account team search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaAccountTeamSearchDto}
     * @author taitt
     */
    private JcaAccountTeamSearchDto buildJcaAccountTeamSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaAccountTeamSearchDto reqSearch = new JcaAccountTeamSearchDto();
        
        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Boolean nonData = null != commonSearch.getFirst("nonData") ? Boolean.valueOf(commonSearch.getFirst("nonData")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        reqSearch.setNonData(nonData);
        reqSearch.setCompanyId(companyId);

        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaAccountTeamSearchEnum.valueOf(enumValue)) {
                case TEAMCODE:
                    reqSearch.setTeamCode(keySearch);
                    break;
                case TEAMNAME:
                    reqSearch.setTeamName(keySearch);
                    break;

                default:
                    reqSearch.setTeamCode(keySearch);
                    reqSearch.setTeamName(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setTeamCode(keySearch);
            reqSearch.setTeamName(keySearch);
        }
        
        return  reqSearch;
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaAccountTeamSearchEnum.values());         
    }

}

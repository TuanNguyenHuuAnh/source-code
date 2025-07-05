/*******************************************************************************
 * Class        AccountOrgService
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.ep2p.admin.dto.AccountListDto;
import vn.com.unit.ep2p.admin.dto.OrgNode;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountEditDto;

/**
 * AccountOrgService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */

public interface AccountOrgService {

    /**
     * Find list orgId frm AccountOrg by accountId
     * @param accountId
     *          type Long
     * @return List<Long>
     * @author KhoaNA
     */ 
    public List<Long> findOrgIdListByAccountId( Long accountId );
    
    /**
     * findAccountOrgDtoByAccountId
     * @param accountId
     * @return
     * @author trieuvd
     */
    public List<JcaAccountOrgDto> findAccountOrgDtoByAccountId(Long accountId);
    
    /**
     * deleteById
     * @param id
     * @author trieuvd
     */
    public void deleteById(Long id);
    
    /**
     * checkStartEndDate
     * @param listOrgForAccountDto
     * @return
     * @author trieuvd
     */
    public boolean checkStartEndDate(List<JcaAccountOrgDto> listOrgForAccountDto);
    
    /**
     * validateOrgList
     * @param accountOrgDtos
     * @author trieuvd
     */
    public String validateOrgList(List<JcaAccountOrgDto> accountOrgDtos, Locale locale);
    
    /**
     * updateOrgForAccount
     * @param accountDto
     * @author trieuvd
     * @throws ParseException 
     */
    public void updateOrgForAccount(JcaAccountEditDto accountDto) throws ParseException;
    
    /**
     * findOrgByAccountId
     * @param accountId
     * @return
     * @author trieuvd
     */
    public List<OrgNode> findOrgByAccountId(Long accountId);
    
    /**
     * findSelect2DtoByAccountId
     * @param accountId
     * @param companyId
     * @param keySearch
     * @param isPaging
     * @return
     * @author trieuvd
     */
    public List<Select2Dto> findSelect2DtoByAccountId(Long accountId, Long companyId, String keySearch, boolean isPaging);
    
    /**
     * updateOrgIdByOrgCode
     * @param orgId
     * @param orgCode
     * @param companyId
     * @author trieuvd
     */
    public void updateOrgIdByOrgCode(Long orgId, String orgCode, Long companyId);
    
    /**
     * findAccountByOrgId
     * @param orgId
     * @return
     * @author trieuvd
     */
    public PageWrapper<AccountListDto> findAccountByOrgId(int page, int pageSize, Long orgId);
    
    /**
     * findSelect2DtoUserOwnsByAccountId
     * @param accountId
     * @param keySearch
     * @param isPaging
     * @return List<Select2Dto>
     * @author KhuongTH
     */
    public List<Select2Dto> findSelect2DtoUserOwnsByAccountId(Long accountId, String keySearch, boolean isPaging);
    
    /**
     * updatePositionIdByPositionCode
     * @param positionId
     * @param positionCode
     * @param companyId
     * @author trieuvd
     */
    public void updatePositionIdByPositionCode(Long positionId, String positionCode, Long companyId);
    
    /**
     * 
     * findSelect2DtoUserOwnsByAccountIdOrId
     * @param accountId
     * @param id
     * @return
     * @author taitt
     */
    public List<Select2Dto> findSelect2DtoUserOwnsByAccountIdOrId(Long accountId,Long id);
    
    /**
     * getPositionByOrgId
     * 
     * @param accountId
     * @param orgId
     * @return
     * @author HungHT
     */
    public Select2Dto getPositionByOrgId(Long accountId, Long orgId);
    
    /**
     * findPositionMergeByAccountId
     * @param accountId
     * @return
     * @author trieuvd
     */
    public Select2Dto findPositionMergeByAccountId(Long accountId);

	public PageWrapper<AccountListDto> findAccountByPositionId(int page, int pageSize, Long orgId);

    /**
     * <p>
     * Delete jca account org by PK.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @param orgId
     *            type {@link Long}
     * @param positionId
     *            type {@link Long}
     * @author tantm
     */
    public void deleteJcaAccountOrgByPK(Long id, Long orgId, Long positionId);
}

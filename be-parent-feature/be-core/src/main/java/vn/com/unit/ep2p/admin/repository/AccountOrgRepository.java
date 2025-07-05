/*******************************************************************************
 * Class        AccountOrgRepository
 * Created date 2016/07/19
 * Lasted date  2016/07/19
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.entity.JcaAccountOrg;
import vn.com.unit.core.repository.JcaAccountOrgRepository;
import vn.com.unit.ep2p.admin.dto.AccountListDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountOrgDelegateDto;

/**
 * AccountOrgRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AccountOrgRepository extends JcaAccountOrgRepository {
	/**
	 * Find list orgIds from AccountOrg by accountId
	 * 
	 * @param accountId
	 *         type Long
	 * @return List<Long>
	 * @author KhoaNA
	 */
	List<Long> findOrgIdListByAccountId(@Param("accountId") Long accountId);
	List<Long> findOrgIdListByAccountIdOracle(@Param("accountId") Long accountId);
	List<JcaAccountOrg> getAccountOrg(@Param("accountId") Long accountId, @Param("orgId") Long orgId);
	
	/**
	 * findAccountOrgDtoByAccountId
	 * @param accountId
	 * @return
	 * @author trieuvd
	 */
	public List<JcaAccountOrgDto> findAccountOrgDtoByAccountId(@Param("accountId") Long accountId);
	
	/**
	 * findOrgNameByAccountId
	 * @param accountId
	 * @return
	 * @author trieuvd
	 */
	public List<String> findOrgNameByAccountId(@Param("accountId") Long accountId);
	
	/**
	 * findSelect2DtoByAccountId
	 * @param keySearch
	 * @param accountId
	 * @param companyId
	 * @param isPaging
	 * @return
	 * @author trieuvd
	 */
	public List<Select2Dto> findSelect2DtoByAccountId(@Param("keySearch") String keySearch, @Param("accountId") Long accountId,  @Param("companyId") Long companyId, @Param("isPaging") boolean isPaging);
	
	/**
	 * 
	 * findOrgByAccountId
	 * @param keySearch
	 * @param accountId
	 * @param isPaging
	 * @return
	 * @author taitt
	 */
	public List<JcaAccountOrgDto> findOrgByAccountId(@Param("keySearch") String keySearch, @Param("accountId") Long accountId, @Param("isPaging") boolean isPaging);
	/**
	 * updateOrgIdByOrgCode
	 * @param orgId
	 * @param orgCode
	 * @param companyId
	 * @author trieuvd
	 */
	@Modifying
	public void updateOrgIdByOrgCode(@Param("orgId")Long orgId, @Param("orgCode")String orgCode, @Param("companyId")Long companyId);
	
	/**
	 * getByAccountIdOrgIdAndOrgCode
	 * @param accountId
	 * @param orgId
	 * @param orgCode
	 * @return
	 * @author trieuvd
	 */
	public JcaAccountOrg getByAccountIdOrgIdAndOrgCode(@Param("accountId") Long accountId, @Param("orgId")Long orgId, @Param("orgCode")String orgCode);
	
	/**
	 * countAccountByOrgId
	 * @param orgId
	 * @return
	 * @author trieuvd
	 */
	public int countAccountByOrgId(@Param("orgId") Long orgId);
	
	/**
	 * findAccountByOrgId
	 * @param page
	 * @param pageSize
	 * @param orgId
	 * @return
	 * @author trieuvd
	 */
	public List<AccountListDto> findAccountByOrgId(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage, @Param("orgId")Long orgId);
	
	/**
	 * findSelect2DtoUserOwnsByAccountId
	 * @param keySearch
	 * @param accountId
	 * @param isPaging
	 * @return List<Select2Dto>
	 * @author KhuongTH
	 */
	public List<Select2Dto> findSelect2DtoUserOwnsByAccountId(@Param("keySearch") String keySearch, @Param("accountId") Long accountId, @Param("isPaging") boolean isPaging);

	/**
	 * updatePositionIdByPositionCode
	 * @param positionId
	 * @param positionCode
	 * @param companyId
	 * @author trieuvd
	 */
	@Modifying
    public void updatePositionIdByPositionCode(@Param("positionId")Long positionId, @Param("positionCode")String positionCode, @Param("companyId")Long companyId);
	
	/**
	 * 
	 * findSelect2DtoUserOwnsToViewsOrgByAccountId
	 * @param accountId
	 * @return
	 * @author taitt
	 */
	public List<Select2Dto> findSelect2DtoUserOwnsToViewsOrgByAccountId( @Param("accountId") Long accountId);
	
    /**
     * getPositionByOrgId
     * 
     * @param accountId
     * @param orgId
     * @return
     * @author HungHT
     */
    public Select2Dto getPositionByOrgId(@Param("accountId") Long accountId, @Param("orgId") Long orgId);
    
    /**
     * findPositionMergeByAccountId
     * @param accountId
     * @return
     * @author trieuvd
     */
    public Select2Dto findPositionMergeByAccountId(@Param("accountId") Long accountId);
    
    /**
     * findAccountByOrgAndKey
     * @param key
     * @param orgId
     * @param isPaging
     * @return
     * @author KhuongTH
     */
    public List<Select2Dto>  findAccountByOrgAndKey(@Param("key") String key, @Param("orgId") Long orgId, @Param("isPaging") boolean isPaging);

    /**
     * findPositionsByAccountId
     * @param accountId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Long> findPositionsByAccountId(@Param("accountId") Long accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    /**
     * countOldOrgForDelegate
     * @param id
     * @param accountId
     * @param orgName
     * @return
     * @author hiennt
     */
    Integer countOldOrgForDelegate(@Param("id") Long id, @Param("accountId") Long accountId, @Param("orgName") String orgName);
    
    /**
     * findOldOrgForDelegate
     * @param id
     * @param accountId
     * @param orgName
     * @return
     * @author hiennt
     */
    List<JcaAccountOrgDelegateDto> findOldOrgForDelegate(@Param("id") Long id, @Param("accountId") Long accountId, @Param("orgName") String orgName, @Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage);

    /**
     * findOrgsForDelegator
     * @param accountId
     * @param ids
     * @param expiredDate
     * @return
     * @author hiennt
     */
    List<JcaAccountOrgDto> findOrgsForDelegator(@Param("accountId") Long accountId, @Param("ids") List<Long> ids, @Param("expiredDate") Date expiredDate);
    
    
	int countAccountByPositionId(@Param("positionId") Long positionId);
	List<AccountListDto> findAccountByPositionId(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage, @Param("positionId")Long positionId);


	public JcaAccountOrgDto findByAccountIdAndPositionIdAndOrgId(@Param("accountId") Long accountId,@Param("positionId") Long positionId, @Param("orgId") Long orgId);

	@Modifying
	public void deleteByAccountId(@Param("accountId") Long accountId);
}

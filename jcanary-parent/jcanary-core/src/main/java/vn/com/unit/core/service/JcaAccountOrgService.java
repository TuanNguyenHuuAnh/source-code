/*******************************************************************************
 * Class        :JcaAccountOrgService
 * Created date :2020/12/16
 * Lasted date  :2020/12/16
 * Author       :SonND
 * Change log   :2020/12/16 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountOrgSearchDto;
import vn.com.unit.core.entity.JcaAccountOrg;

/**
 * JcaAccountOrgService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaAccountOrgService {

    /** The Constant TABLE_ALIAS_JCA_ACCOUNT_ORG. */
    static final String TABLE_ALIAS_JCA_ACCOUNT_ORG = "acc_org";
    
    /**
     * <p>
     * Count jca account org dto by condition.
     * </p>
     *
     * @param jcaAccountOrgSearchDto
     *            type {@link JcaAccountOrgSearchDto}
     * @return {@link int}
     * @author sonnd
     */
    public  int countJcaAccountOrgDtoByCondition(JcaAccountOrgSearchDto jcaAccountOrgSearchDto);
    
	/**
     * <p>
     * Gets the jca account org dto by condition.
     * </p>
     *
     * @param jcaAccountOrgSearchDto
     *            type {@link JcaAccountOrgSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the jca account org dto by condition
     * @author sonnd
     */
	public  List<JcaAccountOrgDto> getJcaAccountOrgDtoByCondition(JcaAccountOrgSearchDto jcaAccountOrgSearchDto,  Pageable pageable);
	
	/**
     * <p>
     * Gets the jca account org by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca account org by id
     * @author sonnd
     */
	public JcaAccountOrg getJcaAccountOrgById(Long id);
	
	
	/**
     * <p>
     * Save jca account org.
     * </p>
     *
     * @param jcaAccountOrg
     *            type {@link JcaAccountOrg}
     * @return {@link JcaAccountOrg}
     * @author SonND
     */
	public JcaAccountOrg saveJcaAccountOrg(JcaAccountOrg jcaAccountOrg);
	
	
	/**
     * <p>
     * Save jca account org dto.
     * </p>
     *
     * @param jcaAccountOrgDto
     *            type {@link JcaAccountOrgDto}
     * @return {@link JcaAccountOrg}
     * @author SonND
     */
	public JcaAccountOrg saveJcaAccountOrgDto(JcaAccountOrgDto jcaAccountOrgDto);
	
	/**
     * <p>
     * Delete jca account org by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author SonND
     */
	public void deleteJcaAccountOrgById(Long id);
	
	/**
     * <p>
     * Gets the jca account org dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca account org dto by id
     * @author SonND
     */
	public JcaAccountOrgDto getJcaAccountOrgDtoById(Long id);
	
	/**
     * <p>
     * Get jca account org dto by account id.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @return {@link JcaAccountOrgDto}
     * @author TrieuVD
     */
	public JcaAccountOrgDto getJcaAccountOrgDtoByAccountId(Long accountId);

    /**
     * getListJcaAccountOrgDtoByAccountId
     * @param accountId
     * @return
     * @author taitt
     */
    List<JcaAccountOrgDto> getListJcaAccountOrgDtoByAccountId(Long accountId);
    
    /**
     * <p>
     * Get main jca account org dto by account id.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @return {@link JcaAccountOrgDto}
     * @author tantm
     */
    JcaAccountOrgDto getMainJcaAccountOrgDtoByAccountId(Long accountId);
	
}

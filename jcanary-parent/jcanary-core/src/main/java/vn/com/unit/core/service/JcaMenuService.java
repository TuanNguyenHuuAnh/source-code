/*******************************************************************************
 * Class        :JcaMenuService
 * Created date :2020/12/09
 * Lasted date  :2020/12/09
 * Author       :SonND
 * Change log   :2020/12/09 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaMenuDto;
import vn.com.unit.core.entity.JcaMenu;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * <p>
 * JcaMenuService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface JcaMenuService extends DbRepositoryService<JcaMenu, Long>{

    /**
     * <p>
     * Get jca menu dto.
     * </p>
     *
     * @return {@link List<JcaMenuDto>}
     * @author taitt
     */
    public List<JcaMenuDto> getListJcaMenuDto(Long companyId);

    /**
     * <p>
     * Save jca menu.
     * </p>
     *
     * @param jcaMenu
     *            type {@link JcaMenu}
     * @return {@link JcaMenu}
     * @author taitt
     */
    public JcaMenu saveJcaMenu(JcaMenu jcaMenu);

    /**
     * <p>
     * Save jca menu dto.
     * </p>
     *
     * @param jcaMenuDto
     *            type {@link JcaMenuDto}
     * @return {@link JcaMenu}
     * @author taitt
     */
    public JcaMenu saveJcaMenuDto(JcaMenuDto jcaMenuDto);

    /**
     * <p>
     * Get jca menu dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link JcaMenuDto}
     * @author taitt
     */
    public JcaMenuDto getJcaMenuDtoById(Long id);

    /**
     * <p>
     * Delete jca menu by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author taitt
     */
    public void deleteJcaMenuById(Long id);
    
    /**
     * <p>
     * Builds the menu by company.
     * </p>
     *
     * @author TrieuVD
     * @param companyId
     *            type {@link Long}
     */
    public void buildMenuByCompany(Long companyId);

	/**
	 * @author vunt
	 * @param companyId
	 * @param langCode
	 * @param userId
	 * @return
	 */
	public List<JcaMenuDto> getTreeMenuByUser(Long companyId, String langCode, Long userId);

	/**
	 * @author vunt
	 * @param companyId
	 * @param langCode
	 * @param userId
	 * @return
	 */
	public List<JcaMenuDto> getListMenuByUser(Long companyId, String langCode, Long userId);

}

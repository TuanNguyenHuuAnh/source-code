/*******************************************************************************
 * Class        :JcaMenuRepository
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaMenuDto;
import vn.com.unit.core.entity.JcaMenu;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaMenuRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaMenuRepository extends DbRepository<JcaMenu, Long> {

    /**
     * 
     * <p>
     * Get list menu
     * </p>
     * 
     * @return List<JcaMenuDto>
     * @author SonND
     */
    List<JcaMenuDto> getListJcaMenuDto(@Param("companyId")Long companyId);

    /**
     * 
     * <p>
     * Get JcaMenuDto by id
     * </p>
     * 
     * @param id
     * @return JcaMenuDto
     * @author SonND
     */
    List<JcaMenuDto> getJcaMenuDtoById(@Param("id") Long id);
    
    /**
     * <p>
     * Get jca menu dto list default.
     * </p>
     *
     * @author TrieuVD
     * @return {@link List<JcaMenuDto>}
     */
    public List<JcaMenuDto> getJcaMenuDtoListDefault();

	/**
	 * @param companyId
	 * @param langCode
	 * @param userId
	 * @return
	 */
	List<JcaMenuDto> getListMenuByUserId(@Param("companyId")Long companyId, @Param("langCode")String langCode,@Param("userId") Long userId);
}
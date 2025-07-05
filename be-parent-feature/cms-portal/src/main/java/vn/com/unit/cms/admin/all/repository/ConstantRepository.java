/*******************************************************************************
 * Class        ：ConstantRepository
 * Created date ：2017/10/17
 * Lasted date  ：2017/10/17
 * Author       ：TranLTH
 * Change log   ：2017/10/17：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.ConstantDto;
import vn.com.unit.cms.admin.all.entity.Constant;
import vn.com.unit.cms.admin.all.entity.ConstantLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * ConstantRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface ConstantRepository extends DbRepository<Constant, Long> {
    /**
     * countByConstantDto
     *
     * @param constantDto
     * @return
     * @author TranLTH
     */
    public int countConstantByCondition(@Param("constantDto") ConstantDto constantDto);
    /**
     * findLimitByConstantDto
     *
     * @param offset
     * @param sizeOfPage
     * @param constantDto
     * @return
     * @author TranLTH
     */
    public List<ConstantDto> findConstantLimitByCondition(@Param("startIndex") int startIndex,
            @Param("sizeOfPage") int sizeOfPage, @Param("constantDto") ConstantDto constantDto);
    /**
     * findByType
     *
     * @param type
     * @return
     * @author TranLTH
     */
    public List<ConstantDto> findByType (@Param("type") String type, @Param("languageCode") String languageCode);
    /**
     * findMaxIdConstant
     *
     * @return
     * @author TranLTH
     */
    public int findMaxIdConstant();
    /**
     * findConstantIdLanguage
     *
     * @param constantId
     * @return
     * @author TranLTH
     */
    public List<ConstantLanguage> findConstantIdLanguage(@Param("constantCode") String constantCode);
    /**
     * checkUpdateDelete
     *
     * @param constantCode
     * @return
     * @author TranLTH
     */
    public Boolean checkUpdateDelete(@Param("constantCode") String constantCode);
}
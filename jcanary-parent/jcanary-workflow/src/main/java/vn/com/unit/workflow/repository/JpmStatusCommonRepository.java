/*******************************************************************************
 * Class        ：JpmStatusCommonRepository
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：KhuongTH
 * Change log   ：2021/03/04：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.entity.JpmStatusCommon;

/**
 * <p>JpmStatusCommonRepository</p>.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface JpmStatusCommonRepository extends DbRepository<JpmStatusCommon, Long> {
    
    /**
     * <p>Gets the status common dto by lang.</p>
     *
     * @param lang type {@link String}
     * @return the status common dto by lang
     * @author KhuongTH
     */
    List<JpmStatusCommonDto> getStatusCommonDtoByLang(@Param("lang") String lang);
    
    public JpmStatusCommonDto findStatusCommon(@Param("statusCode") String statusCode, @Param("lang") String lang); 
}

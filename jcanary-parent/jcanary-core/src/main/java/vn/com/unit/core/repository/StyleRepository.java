/*******************************************************************************
 * Class        :StyleRepository
 * Created date :2019/06/10
 * Lasted date  :2019/06/10
 * Author       :HungHT
 * Change log   :2019/06/10:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import vn.com.unit.core.dto.JcaStyleDto;
import vn.com.unit.core.entity.JcaStyle;
import vn.com.unit.db.repository.DbRepository;

/**
 * StyleRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface StyleRepository extends DbRepository<JcaStyle, Long> {

    /**
     * getStyleList
     * 
     * @return
     * @author HungHT
     */
    List<JcaStyleDto> getStyleList();
}
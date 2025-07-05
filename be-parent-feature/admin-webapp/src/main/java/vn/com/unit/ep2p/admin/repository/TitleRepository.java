/*******************************************************************************
 * Class        ：TitleRepository
 * Created date ：2020/03/17
 * Lasted date  ：2020/03/17
 * Author       ：KhuongTH
 * Change log   ：2020/03/17：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import vn.com.unit.db.repository.DbRepository;
import org.springframework.data.repository.query.Param;

/**
 * TitleRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface TitleRepository extends DbRepository<Object, Long>{
    String getHighestTitleByListTitleCode(@Param("titleCodes") List<String> titleCodes, @Param("companyId") Long companyId);
}

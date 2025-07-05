/*******************************************************************************
 * Class        ：DocumentRepository
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.ecard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.ecard.dto.ECardSearchDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardSearchResultDto;
import vn.com.unit.cms.core.module.ecard.entity.ECard;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.dto.ECardPdfDto;

import java.util.List;

public interface ECardRepository extends DbRepository<ECard, Long> {

    public int countList(@Param("searchDto") ECardSearchDto searchDto);

    public Page<ECardSearchResultDto> findListSearch(@Param("searchDto") ECardSearchDto searchDto, Pageable pageable);

    List<ECardPdfDto> findAllECard(@Param("channel") String channel);
}

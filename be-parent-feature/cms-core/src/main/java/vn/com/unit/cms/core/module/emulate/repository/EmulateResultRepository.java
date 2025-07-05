package vn.com.unit.cms.core.module.emulate.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.entity.Emulate;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface EmulateResultRepository extends DbRepository<Emulate, Long> {

    /**
     *
     * @param searchDto
     * @return
     * @author TaiTM
     */
    public int countList(@Param("searchDto") EmulateSearchDto searchDto);

    /**
     * @param searchDto
     * @param pageable
     * @return
     * @author TaiTM
     */
    public Page<EmulateResultSearchResultDto> findListSearch(@Param("searchDto") EmulateSearchDto searchDto,
            Pageable pageable);
    
    
    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author TaiTM
     */
    @Modifying
    public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

    /**
     * Find news list for sorting
     * 
     * @param EmulateSearchDto
     * @return List<EmulateSearchResultDto>
     */
    public List<EmulateResultSearchResultDto> findListSorting(@Param("searchDto") EmulateSearchDto dto);

	public List<EmulateResultSearchResultDto> getDeatilByMemo(EmulateResultSearchDto searchDto);
    
}

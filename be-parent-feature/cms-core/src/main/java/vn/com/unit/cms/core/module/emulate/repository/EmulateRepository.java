package vn.com.unit.cms.core.module.emulate.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.emulate.dto.ContestDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateResp;
import vn.com.unit.cms.core.module.emulate.entity.Emulate;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface EmulateRepository extends DbRepository<Emulate, Long> {

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
    public Page<EmulateSearchResultDto> findListSearch(@Param("searchDto") EmulateSearchDto searchDto,
            Pageable pageable);

    /**
     *
     * @param code
     * @return
     * @author TaiTM
     */
    public Emulate findByCode(@Param("code") String code);

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
    public List<EmulateSearchResultDto> findListSorting(@Param("searchDto") EmulateSearchDto dto);
    
    /**
     * Get list emulate in Month
     * @param searchDto
     * @param pageable
     * @return List<EmulateSearchResultDto>
     */
    public Page<EmulateResp> getListEmulateInMonth(@Param("searchDto") EmulateSearchDto searchDto,
            Pageable pageable, Integer modeView);
    
    /**
    *
    * @param searchDto
    * @return
    */
    public int countListEmulateInMonth(@Param("searchDto") EmulateSearchDto searchDto);
    
    public  EmulateResp getHotEmulateInMonth(@Param("searchDto") EmulateSearchDto searchDto);
    
    public List<EmulateSearchDto> findByContestType();
    
    public Page<EmulateSearchDto> getEmulateAndChallengePersonal(@Param("searchDto") EmulateSearchDto searchDto, Pageable pageable);
    
    public int countEmulateAndChallengePersonal(@Param("searchDto") EmulateSearchDto searchDto);
    
    public List<EmulateResultSearchResultDto> getDetailEmulateChallengePersonal(@Param("searchDto") EmulateSearchDto searchDto);

}

package vn.com.unit.cms.core.module.emulate.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.emulate.dto.ContestDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateResp;
import vn.com.unit.cms.core.module.emulate.entity.ContestSummary;
import vn.com.unit.cms.core.module.emulate.entity.Emulate;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface ContestRepository extends DbRepository<ContestDto, Long> {
    public List<ContestDto> findByMemo(@Param("memo") String memo);
    public List<ContestDto> findByMemoAndCode(@Param("memo") String memo, @Param("code") String code);

}

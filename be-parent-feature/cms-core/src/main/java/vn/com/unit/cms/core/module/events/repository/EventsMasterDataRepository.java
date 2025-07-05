package vn.com.unit.cms.core.module.events.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.events.entity.EventsMasterData;
import vn.com.unit.db.repository.DbRepository;

public interface EventsMasterDataRepository extends DbRepository<EventsMasterData, Long>{

    List<EventsMasterDataDto> getListDataByType(@Param("type") String type, @Param("parentId") String parentId, @Param("code") String code);
}

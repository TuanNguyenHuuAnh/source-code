package vn.com.unit.cms.core.module.events.imports.repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.events.dto.EventsDto;
import vn.com.unit.cms.core.module.events.dto.EventsSearchDto;
import vn.com.unit.cms.core.module.events.imports.dto.EventsImportDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.imp.excel.repository.ImportExcelInterfaceRepository;

public interface EventsImportRepository extends ImportExcelInterfaceRepository<EventsImportDto>{

    @Modifying
    void updateMessageErrorAgentNotExist(@Param("error") String error, @Param("id") Long id);
    
    @Modifying
    void updateMessageErrorAndWarning(@Param("error") String error, @Param("warning") String warning, @Param("id") Long id);
    
    @Modifying
    void updateAgentInfo(@Param("id") Long id, @Param("agent") Db2AgentDto agent);
    
    List<EventsImportDto> getListCodeDuplicate(@Param("sessionKey") String sessionKey);
}

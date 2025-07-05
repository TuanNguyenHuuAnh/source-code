/**
 * @author TaiTM
 * @date Aug 18, 2020
 */

package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.EmulateResultImport;
import vn.com.unit.cms.core.module.emulate.dto.ContestDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportSearchDto;
import vn.com.unit.imp.excel.repository.ImportExcelInterfaceRepository;

public interface EmulateResultImportRepository extends ImportExcelInterfaceRepository<EmulateResultImportDto> {
    public int countDataBySearchDto(@Param("searchDto") EmulateResultImportSearchDto searchDto);

    public List<EmulateResultImportDto> findListDataBySearchDto(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("searchDto") EmulateResultImportSearchDto searchDto);

    public List<EmulateResultImportDto> findListDataExportBySearchDto(
            @Param("searchDto") EmulateResultImportSearchDto searchDto);

    public List<Long> findMovementIdListBySessionKey(@Param("sessionKey") String sessionKey);

    @Modifying
    public void updateDataBySessionKey(@Param("sessionKey") String sessionKey, @Param("username") String username);

    public List<String> checkWarningCaseByCase(@Param("searchDto") EmulateResultImportSearchDto searchDto);
    
    public EmulateResultImport findByCode (@Param("memo") String memo);
    
    public List<ContestDto> findByMemoAndCode (@Param("memo")String memo);


}

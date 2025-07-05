/**
 * @author TaiTM
 * @date Aug 18, 2020
 */

package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.EmulateResultImport;
import vn.com.unit.cms.core.module.emulate.dto.EmulateImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateImportSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportSearchDto;
import vn.com.unit.imp.excel.repository.ImportExcelInterfaceRepository;

public interface EmulateImportRepository extends ImportExcelInterfaceRepository<EmulateImportDto> {
    
	public int countDataBySearchDto(@Param("searchDto") EmulateImportSearchDto searchDto);

	public List<EmulateImportDto> findListDataBySearchDto(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("searchDto") EmulateImportSearchDto searchDto);

	 @Modifying
	 public void updateDataBySessionKey(@Param("sessionKey") String sessionKey, @Param("username") String username);

	  public List<EmulateImportDto> findListDataExportBySearchDto(
	            @Param("searchDto") EmulateImportSearchDto searchDto);
	  
	 List<EmulateImportDto> findListDataImport(@Param("sessionKey") String sessionKey);

}

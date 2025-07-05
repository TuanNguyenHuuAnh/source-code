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
import vn.com.unit.cms.core.module.emulate.entity.ContestSummaryImport;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.imp.excel.repository.ImportExcelInterfaceRepository;

public interface EmulateImportStRepository extends DbRepository<ContestSummaryImport,Long> {
      
	 List<ContestSummaryImport> findListDataImport(@Param("sessionKey") String sessionKey);

}

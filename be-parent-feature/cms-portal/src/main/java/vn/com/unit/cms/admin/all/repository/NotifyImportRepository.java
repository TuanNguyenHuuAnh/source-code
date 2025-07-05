/**
 * @author TaiTM
 * @date Aug 18, 2020
 */

package vn.com.unit.cms.admin.all.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.notify.dto.NotifyImportDto;
import vn.com.unit.imp.excel.repository.ImportExcelInterfaceRepository;

public interface NotifyImportRepository extends ImportExcelInterfaceRepository<NotifyImportDto> {

	@Modifying
	void updateMessageErrorAgentTer(@Param("messageError") String string, @Param("id") Long id);
	@Modifying
	void updateMessageErrorAgentNotExist(@Param("messageError") String string, @Param("id") Long id);

}

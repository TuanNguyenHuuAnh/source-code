// 2021-04-06 LocLT Task #40894

package vn.com.unit.ep2p.core.service;

import java.io.File;
import java.util.List;

import vn.com.unit.ep2p.core.dto.ImportCommonDto;

public interface ImportExcelService {

	<E extends Enum<E>, T extends ImportCommonDto<E>> List<T> getDataFromExcel(File file, int startRow, Class<T> dto,
			Class<E> enumType) throws Exception;

}

/*******************************************************************************
 * Class        ：CommonImportExcelService
 * Created date ：2019/02/15
 * Lasted date  ：2019/02/15
 * Author       ：VinhLT
 * Change log   ：2019/02/15：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.repository.CommonImportExcelRepository;

/**
 * CommonImportExcelService
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
@Transactional
public interface CommonImportExcelService<T> {

	/**
	 * getSystemConfig
	 *
	 * @return
	 * @author VinhLT
	 */
	SystemConfig getSystemConfig();

	/**
	 * getCommonImportExcelRepository
	 *
	 * @return
	 * @author VinhLT
	 */
	@SuppressWarnings("rawtypes")
	CommonImportExcelRepository getCommonImportExcelRepository();

	/** searchImport
	 *
	 * @param page
	 * @param importId
	 * @param clazzEntity
	 * @return
	 * @throws Exception
	 * @author VinhLT
	 */
	@SuppressWarnings("unchecked")
	default public PageWrapper<T> searchImport(int page, String importId, Class<?> clazzEntity) throws Exception {
		int sizeOfPage = getSystemConfig().getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<T> wrapper = new PageWrapper<T>(page, sizeOfPage);
		String tableName = getTableNameOfEntityClass(clazzEntity);
		List<T> list = new ArrayList<T>();
		int count = getCommonImportExcelRepository().countImportDataInProcess(importId, tableName);
		if (count > 0) {
			int currentPage = wrapper.getCurrentPage();
			int startIndex = (currentPage - 1) * sizeOfPage;	
			list = (List<T>) getCommonImportExcelRepository().findListImportDataInProcess(startIndex, sizeOfPage,
					importId, tableName);
		}
		wrapper.setDataAndCount(list, count);
		return wrapper;
	}

	/** getTableNameOfEntityClass
	 *
	 * @param entityClazzz
	 * @return
	 * @throws Exception
	 * @author VinhLT
	 */
	default String getTableNameOfEntityClass(Class<?> entityClazzz) throws Exception {

		Table table = entityClazzz.getAnnotation(Table.class);
		String tableName = table.name();

		if (StringUtils.isBlank(tableName)) {
			throw new Exception("Table name not defined on entity");
		} else {
			return tableName;
		}

	}

}

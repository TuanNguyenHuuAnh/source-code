/*******************************************************************************
 * Class        ：CommonImportExcelRepository
 * Created date ：2019/02/15
 * Lasted date  ：2019/02/15
 * Author       ：VinhLT
 * Change log   ：2019/02/15：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import vn.com.unit.db.repository.DbRepository;
import org.springframework.data.repository.query.Param;

/**
 * CommonImportExcelRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public interface CommonImportExcelRepository<T> extends DbRepository<T, Long> {

	/**
	 * countImportDataInProcess
	 *
	 * @param importId
	 * @param tableName
	 * @return
	 * @author VinhLT
	 */
	int countImportDataInProcess(@Param("importId") String importId, @Param("tableName") String tableName);

	/**
	 * findListImportDataInProcess
	 *
	 * @param startIndex
	 * @param sizeOfPage
	 * @param importId
	 * @param tableName
	 * @return
	 * @author VinhLT
	 */
	List<T> findListImportDataInProcess(@Param("offset") int startIndex, @Param("sizeOfPage") int sizeOfPage,
			@Param("importId") String importId, @Param("tableName") String tableName);

}

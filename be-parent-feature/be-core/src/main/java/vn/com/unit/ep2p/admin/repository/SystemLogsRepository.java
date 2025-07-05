/*******************************************************************************
 * Class        SystemLogsRepository
 * Created date 2018/01/08
 * Lasted date  2018/01/08
 * Author       HUNGHT
 * Change log   2018/01/0801-00 HUNGHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SystemLogsDto;
import vn.com.unit.ep2p.admin.entity.SystemLogs;

import org.springframework.data.repository.query.Param;

/**
 * SystemLogsRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author HUNGHT
 */
public interface SystemLogsRepository extends DbRepository<SystemLogs, Long> {
	
    List<SystemLogsDto> searchSystemLogsOracle(@Param("systemLogsDto") SystemLogsDto systemLogsDto, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage);
	
	int countRecordsOracle(@Param("systemLogsDto") SystemLogsDto systemLogsDto);
	/**
	 * searchSystemLogsSQL
	 *
	 * @param systemLogsDto
	 * @return
	 * @author phatvt
	 */
    List<SystemLogsDto> searchSystemLogsSQL(@Param("systemLogsDto") SystemLogsDto systemLogsDto, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage);
	/**
	 *     countRecordsSQL
	 *
	 * @param systemLogsDto
	 * @return
	 * @author phatvt
	 */
    int countRecordsSQL(@Param("systemLogsDto") SystemLogsDto systemLogsDto);
}

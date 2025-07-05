/*******************************************************************************
 * Class        QuartzJobRepository
 * Created date 2018/08/14
 * Lasted date  2018/08/14
 * Author       hangnkm
 * Change log   2018/08/1401-00 hangnkm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.QuartzTriggersDto;
import vn.com.unit.ep2p.admin.entity.QuartzTriggers;
import vn.com.unit.ep2p.admin.sla.dto.QuartzJobSearchDto;




/**
 * QuartzJobRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hangnkm
 */
public interface QuartzJobRepository extends DbRepository<QuartzTriggers, Long> {
    
    /** getQuartzJob
     *
     * @param quartzTriggersDto
     * @return
     * @author hangnkm
     */
    List<QuartzTriggers> getQuartzJob(@Param("quartzTriggersDto") QuartzTriggersDto quartzTriggersDto);
    
    
    /** countByQuartzJobSearchDto
     *
     * @param quartzSearchDto
     * @return
     * @author hangnkm
     */
    int countByQuartzJobSearchDto(@Param("quartzSearchDto")QuartzJobSearchDto quartzSearchDto);
    
    
    /** findListByQuartzSearchDtoSQLServer
     *
     * @param offset
     * @param sizeOfPage
     * @param quartzSearchDto
     * @return
     * @author hangnkm
     */
    List<QuartzTriggersDto> findListByQuartzSearchDtoSQLServer(
            @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage,
            @Param("quartzSearchDto")QuartzJobSearchDto quartzSearchDto);
}

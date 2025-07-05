/*******************************************************************************
 * Class        EmailRepository
 * Created date 2018/08/14
 * Lasted date  2018/08/14
 * Author       phatvt
 * Change log   2018/08/1401-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.entity.JcaEmail;
import vn.com.unit.core.repository.JcaEmailRepository;
import vn.com.unit.ep2p.dto.EmailSearchDto;

/**
 * EmailRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public interface EmailRepository extends JcaEmailRepository{
   /**
    * countEmailByCondition
    *
    * @param searchDto
    * @return
    * @author phatvt
    */
    public int countEmailByCondition( @Param("searchDto")EmailSearchDto searchDto);
    
//    /**
//     * findAllCityListByCondition
//     *
//     * @param searchDto
//     * @return
//     * @author phatvt
//     */
//    public List<EmailDto> findAllEmailListByCondition(
//            @Param("offset") int offset,
//            @Param("sizeOfPage") int sizeOfPage, 
//            @Param("searchDto") EmailSearchDto searchDto);
    
    /**
     * findAllEmailByStatus
     *
     * @param status
     * @return
     * @author phatvt
     */
    public List<JcaEmail> findAllEmailByStatus(@Param("listStatus") List<String> listStatus);
    
    /**
     * findAllEmailByStatusAndCompanyId
     * @param listStatus
     * @param companyId
     * @return
     * @author trieuvd
     */
    public List<JcaEmail> findAllEmailByStatusAndCompanyId(@Param("listStatus") List<String> listStatus, @Param("companyId") Long companyId);
}

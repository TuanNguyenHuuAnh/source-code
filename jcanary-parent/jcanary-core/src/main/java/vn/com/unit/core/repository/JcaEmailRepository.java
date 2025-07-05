/*******************************************************************************
 * Class        ：JcaEmailRepository
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailSearchDto;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.core.entity.JcaEmail;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaEmailRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaEmailRepository extends DbRepository<JcaEmail, Long>{
    
    /**
     * <p>
     * Get jca email dto list by search dto.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JcaEmailSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<JcaEmailDto>}
     */
    public Page<JcaEmailDto> getJcaEmailDtoListBySearchDto(@Param("searchDto") JcaEmailSearchDto searchDto, Pageable pageable);
    
    /**
     * <p>
     * Count by search.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JcaEmailSearchDto}
     * @return {@link int}
     */
    public int countJcaEmailDtoBySearchDto(@Param("searchDto") JcaEmailSearchDto searchDto);
    
    /**
     * <p>
     * Get jca email dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link JcaEmailDto}
     */
    public JcaEmailDto getJcaEmailDtoById(@Param("id") Long id);
    @Modifying
	public void updateEmailId(@Param("emailId")Long emailId, @Param("uuidEmail")String uuidEmail);
    

    public List<JcaAttachFileEmail> getAttachFileWithEmailId(@Param("emailId") Long emailId);

}

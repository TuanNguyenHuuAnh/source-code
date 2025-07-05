/*******************************************************************************
 * Class        ：ShareHolderManagementRepository
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ShareHolderSearchDto;
import vn.com.unit.cms.admin.all.entity.ShareHolder;
import vn.com.unit.db.repository.DbRepository;

public interface ShareHolderManRepository extends DbRepository<ShareHolder, Long> {
    public List<ShareHolder> findAllActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage);

    public List<ShareHolder> findActiveByConditions(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("condition") ShareHolderSearchDto condition);

    public ShareHolder findDetailById(@Param("id") Long id);
    
    public ShareHolder findByCode(@Param("code") String code);

    public int findActiveItemCount();

    public int countActiveByConditions(@Param("condition") ShareHolderSearchDto condition);

    public int countById(@Param("id") Long id);

    public int countByCode(@Param("code") String code);

    @Modifying
    public int updateShareHolder(@Param("sHolderObj") ShareHolder updateModel);

    @Modifying
    public int updateDeleteFields(@Param("id") long id, @Param("deleteBy") String deleteBy,
            @Param("deleteDate") Date deleteDate);

	public List<ShareHolder> findAllActiveNonPaging();
	
	public List<String> findAllActiveCode();

	/**
	 * @return
	 */
	public Long findMaxSort();

}

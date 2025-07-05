/*******************************************************************************
 * Class        ：AppInboxRepository
 * Created date ：2019/08/30
 * Lasted date  ：2019/08/30
 * Author       ：KhuongTH
 * Change log   ：2019/08/30：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAppInboxDto;
import vn.com.unit.core.entity.JcaAppInbox;
import vn.com.unit.core.repository.JcaAppInboxRepository;


/**
 * AppInboxRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppInboxRepository extends JcaAppInboxRepository {
    
    JcaAppInbox findOneByIdAndAccountId(@Param("accountId") Long accountId, @Param("appInboxId") Long appInboxId);
    
    List<JcaAppInboxDto> findListAppInboxDtoByAccountId(@Param("accountId") Long accountId,@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("isPaging")int isPaging,@Param("lang")String lang) throws Exception;
    
    List<JcaAppInboxDto> findListAppInboxDtoByAccountIdAndStatusRead(@Param("accountId") Long accountId,
    		@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage, @Param("lang") String lang);
    
    @Modifying
    int markAllReadInbox(@Param("accountId") Long accountId, @Param("isRead") boolean isRead);

    @Modifying
    int markReadInboxByInboxId(@Param("accountId") Long accountId, @Param("appInboxId") Long appInboxId,
            @Param("isRead") boolean isRead);
    
	int countStatusReadInboxByAccountId(@Param("accountId") Long appUserId, @Param("isRead") boolean isRead);
	
	int countFindLstAppInboxByAccountId(@Param("accountId") Long appUserId);

	@Modifying
    int markReadInboxByDocId(@Param("accountId") Long accountId, @Param("docId") Long docId);
	
    @Modifying
    void deleteAppInbox(@Param("accountId") Long accountId, @Param("appInboxId") Long appInboxId, @Param("user") String user,
            @Param("sysDate") Date sysDate);
    
    List<JcaAppInbox> getListAppInboxByDocIdAndAccId(@Param("accountId") Long accountId, @Param("docId") Long docId);
    
    @Modifying
    void moveAppInboxOldToHis(@Param("ids") List<Long> ids);
    
    @Modifying
    void deleteAppInboxOldAfterMoveToHis(@Param("ids") List<Long> ids);
      
    List<Long> getListIdForDeleteAppInboxOldAfterMoveToHis(@Param("sysDate") Date sysDate,@Param("dateSum") int dateSum,@Param("offset") int page,
            @Param("sizeOfPage") int pageSize);
    
    Long countForDeleteAppInboxOldAfterMoveToHis(@Param("sysDate") Date sysDate,@Param("dateSum") int dateSum);
}

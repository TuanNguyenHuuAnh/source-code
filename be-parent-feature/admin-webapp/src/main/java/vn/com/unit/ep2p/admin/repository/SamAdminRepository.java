package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.dto.SamWaitingApprovalDto;

/**
 * @version 01-00
 * @since 01-00
 * @author nt.tinh
 * @Last updated: 15/05/2024	nt.tinh SR16451 - Enhance SAM mADP/ADPortal
 */
public interface SamAdminRepository extends DbRepository<SamWaitingApprovalDto, Long> {
	/**
	 * Get List Waiting Approval
	 * @return
	 */
	List<SamWaitingApprovalDto> getWaitingApproval();
	
	@Modifying
	int updateWfCreated(@Param("actId") Long actId, @Param("type") String type);
}

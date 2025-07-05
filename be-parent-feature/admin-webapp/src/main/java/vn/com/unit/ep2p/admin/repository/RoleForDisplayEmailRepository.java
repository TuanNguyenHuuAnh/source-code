package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.RoleForDisplayEmailDto;
import vn.com.unit.ep2p.admin.entity.RoleForDisplayEmail;


/**
 * RoleForDisplayEmailRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public interface RoleForDisplayEmailRepository extends DbRepository<RoleForDisplayEmail, Long> {
    
	/**
	 * findByRoleId
	 * @param roleId
	 * @param companyIds
	 * @param isAdmin
	 * @return
	 * @author trieuvd
	 */
	public List<RoleForDisplayEmailDto> findByRoleId(@Param("roleId") Long roleId, @Param("companyIds") List<Long> companyIds, @Param("isAdmin") Boolean isAdmin);
	
}

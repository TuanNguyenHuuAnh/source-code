package vn.com.unit.ep2p.core.ds.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import vn.com.unit.ep2p.core.ds.entity.Version;
import vn.com.unit.ep2p.core.ds.dto.VersionDto;
import vn.com.unit.db.repository.DbRepository;

/**
 * @author ntr.bang
 */
public interface VersionRepository extends DbRepository<Version, Integer>{

	/**
	 * Get Version of platform
	 * @param user
	 * @return
	 */
    List<VersionDto> getAllVersionByPlatform(@Param("platform") String platform);
}

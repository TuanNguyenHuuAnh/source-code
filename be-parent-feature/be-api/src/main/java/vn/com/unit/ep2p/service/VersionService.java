package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.ep2p.core.ds.dto.VersionDto;

public interface VersionService {

	/**
	 * Get all version of platform
	 * @return
	 */
    List<VersionDto> getAllVersion(String platform);
}

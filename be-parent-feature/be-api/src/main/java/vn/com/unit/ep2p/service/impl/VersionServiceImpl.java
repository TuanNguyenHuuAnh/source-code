package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.core.ds.dto.VersionDto;
import vn.com.unit.ep2p.core.ds.repository.VersionRepository;
import vn.com.unit.ep2p.service.VersionService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class VersionServiceImpl implements VersionService {
	
	/**
	 * Logger
	 */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private VersionRepository repository;
	
	@Override
	public List<VersionDto> getAllVersion(String platform) {
		log.info("Begin getAllVersion(String platform)");
		log.debug("Platform: ", platform);
		List<VersionDto> lstData = new ArrayList<>();
		try {

			lstData = repository.getAllVersionByPlatform(platform);
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return lstData;
	}
}

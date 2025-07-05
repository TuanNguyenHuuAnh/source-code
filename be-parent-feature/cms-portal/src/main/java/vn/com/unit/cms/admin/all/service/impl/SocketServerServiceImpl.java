package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.SocketServerDto;
import vn.com.unit.cms.admin.all.repository.SocketServerRepository;
import vn.com.unit.cms.admin.all.service.SocketServerService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SocketServerServiceImpl implements SocketServerService {

	@Autowired
	private SocketServerRepository socketServerRepository;

	@Override
	public List<SocketServerDto> getListUserOnline() {
		List<SocketServerDto> result = socketServerRepository.findListUserOnline();
		List<SocketServerDto> sortResult = new ArrayList<>();
		
		Map<String, SocketServerDto> map = new LinkedHashMap<String, SocketServerDto>();

		if (null != result && !result.isEmpty()) {
			for (SocketServerDto server : result) {
				if (!map.isEmpty()) {
					map.put(server.getUsername(), server);
				} else {
					if (!map.containsKey(server.getUsername())) {
						map.put(server.getUsername(), server);
					}
				}
			}

			if (null != map) {
				Set<String> keys = map.keySet();
				for (String key : keys) {
					sortResult.add(map.get(key));
				}
			}
		}
		return sortResult;
	}
}

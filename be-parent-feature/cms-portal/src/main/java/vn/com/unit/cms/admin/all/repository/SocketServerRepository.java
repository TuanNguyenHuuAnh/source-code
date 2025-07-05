package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;

import vn.com.unit.cms.admin.all.dto.SocketServerDto;
import vn.com.unit.cms.admin.all.entity.SocketServer;
import vn.com.unit.db.repository.DbRepository;

public interface SocketServerRepository extends DbRepository<SocketServer, Long> {

	public List<SocketServerDto> findListUserOnline();

}

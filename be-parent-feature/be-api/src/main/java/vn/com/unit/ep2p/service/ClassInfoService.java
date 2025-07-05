package vn.com.unit.ep2p.service;

import org.springframework.http.ResponseEntity;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.ers.dto.ErsClassInfoDto;
import vn.com.unit.ep2p.dto.req.ClassInfoSearchReq;

public interface ClassInfoService {

    ObjectDataRes<ErsClassInfoDto> doSearch(ClassInfoSearchReq cond, Integer pageNumber, Integer pageSize) throws DetailException;

    @SuppressWarnings("rawtypes")
    ResponseEntity export(ClassInfoSearchReq cond) throws Exception;

    boolean delete(Long id);

}

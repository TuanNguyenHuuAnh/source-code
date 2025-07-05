package vn.com.unit.ep2p.service;

//import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.ep2p.core.ers.dto.ErsMicDto;
import vn.com.unit.ep2p.dto.req.MicSearchReq;

import java.util.List;

public interface MicService {

    ObjectDataRes<ErsMicDto> doSearch(MicSearchReq cond,  Integer page, Integer size) throws Exception;
    
    
    
    
    List<Select2Dto> getAllMIC();

    @SuppressWarnings("rawtypes")
    ResponseEntity export(MicSearchReq cond) throws Exception;

    boolean delete(Long id);

    List<Select2Dto> getListRegion();

    List<Select2Dto> getListArea();
}

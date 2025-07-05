package vn.com.unit.ep2p.service;

import vn.com.unit.cms.core.module.ecard.dto.ECardReqDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardResDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface ApiECardService {

    List<ECardResDto> geranateECard(ECardReqDto eCardReqDto) throws FileNotFoundException, IllegalAccessException;
}

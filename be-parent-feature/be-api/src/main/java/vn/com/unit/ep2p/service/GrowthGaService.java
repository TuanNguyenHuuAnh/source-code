package vn.com.unit.ep2p.service;

import java.util.Locale;

import org.springframework.http.ResponseEntity;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.ga.dto.GrowthGaDto;
import vn.com.unit.cms.core.module.ga.dto.search.GrowthSearchGa;
import vn.com.unit.core.config.SystemConfig;


public interface GrowthGaService {

    CmsCommonPagination<GrowthGaDto> getListGrowthGa(GrowthSearchGa searchDto);

    ResponseEntity exporttListGrowthGa(GrowthSearchGa searchDto,Locale locale);

     SystemConfig getSystemConfig();

    ResponseEntity exporttListGrowthGaBonus(GrowthSearchGa searchDto, Locale locale);

	CmsCommonPagination<GrowthGaDto> getListGrowthGaBonus(GrowthSearchGa searchDto);
}

package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

public interface JcaZipcodeService {
	List<Select2Dto> getListProvince();

    List<Select2Dto> getListProvinceOds();

	List<String> getOrdId(String officeCode);
}

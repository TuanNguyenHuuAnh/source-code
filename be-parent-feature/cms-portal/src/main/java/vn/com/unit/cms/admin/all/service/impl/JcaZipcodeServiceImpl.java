package vn.com.unit.cms.admin.all.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.service.JcaZipcodeService;
import vn.com.unit.cms.core.module.zipcode.repository.ZipcodeRepository;
import vn.com.unit.common.dto.Select2Dto;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaZipcodeServiceImpl implements JcaZipcodeService{
	
	@Autowired
	ZipcodeRepository zipcodeRepository;
	
	@Override
	public List<Select2Dto> getListProvince() {
		// TODO Auto-generated method stub
		return zipcodeRepository.findAllProvince();
	}
	
}

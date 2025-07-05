package vn.com.unit.cms.admin.all.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.db2.service.Db2Service;
import vn.com.unit.cms.core.dto.ConditionSearchDb2;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.constant.UrlConst;

import java.util.List;

@Controller
@RequestMapping(UrlConst.ROOT + "common")
public class MasterDataCommonController {

	@Autowired
	Db2Service db2Service;

	/**
	 * @author: TriNT
	 * @since: 11/10/2021 7:13 CH
	 * @description:  get all Territory by condition
	 * @update:
	 *
	 * */
	@GetMapping("/territory")
	@ResponseBody
	public List<Select2Dto> getListTerritoryByCondition(@RequestParam(value = "conditionSearchDb2", required = false) ConditionSearchDb2 conditionSearchDb2){
		return db2Service.findAllTerritory(conditionSearchDb2);
	}

	/**
	 * @author: TriNT
	 * @since: 11/10/2021 7:13 CH
	 * @description:  get all Region by condition
	 * @update:
	 *
	 * */
	@GetMapping("/region")
	@ResponseBody
	public List<Select2Dto> getListRegionByCondition(@ModelAttribute(value = "conditionSearchDb2") ConditionSearchDb2 conditionSearchDb2){
		return db2Service.findAllRegion(conditionSearchDb2);
	}
	/**
	 * @author: TriNT
	 * @since: 11/10/2021 7:13 CH
	 * @description:  get all Area by condition
	 * @update:
	 *
	 * */
	@GetMapping("/area")
	@ResponseBody
	public List<Select2Dto> getListAreaByCondition(@ModelAttribute(value = "conditionSearchDb2") ConditionSearchDb2 conditionSearchDb2){
		return db2Service.findAllArea(conditionSearchDb2);
	}

	@GetMapping("/office")
	@ResponseBody
	public List<Select2Dto> getListOfficeByCondition(@ModelAttribute(value = "conditionSearchDb2") ConditionSearchDb2 conditionSearchDb2){
		return db2Service.findAllOffice(conditionSearchDb2);
	}
}

package vn.com.unit.ep2p.sam.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import vn.com.unit.cms.core.module.sam.dto.ActivitiesDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesResponse;
import vn.com.unit.cms.core.module.sam.dto.BuDto;
import vn.com.unit.cms.core.module.sam.dto.PartnerDto;
import vn.com.unit.core.security.UserProfileUtils;

/**
 * @author ntr.bang
 * business logic of SAM Project
 * SR16172 - create date 20/4/2024 - Add API get role permission (getRolesPermission and getAgentTypeAndRoles)
 */
@Service
public class SamBaseServiceImpl {

	/**
	 * Logger
	 */
	private static final Logger log = LoggerFactory.getLogger(SamBaseServiceImpl.class);

	public String getLoginUser() {
		String user = null;
		try {
			user = UserProfileUtils.getUserPrincipal().getUsername();
			log.debug("Log-in by: ", user);
		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		return user;
	}
	/**
	 * Build activities list of  dash board screen
	 * @param lstData
	 * @return
	 */
	public List<ActivitiesResponse> buildResponseData(List<ActivitiesDto> lstData, Set<String> approvedZoneLst) {
		log.info("Begin buildResponseData(List<ActivitiesDto> lstData)");
		List<ActivitiesResponse> actResLst = new ArrayList<>();
		List<PartnerDto> actLst = new ArrayList<>();
		PartnerDto partnerDto = null;
		List<String> partnerZoneLst = new ArrayList<>();
		for (ActivitiesDto activitiesDto : lstData) {
			partnerDto = new PartnerDto();

			// Set data
			partnerDto.setPartner(activitiesDto.getPartner());
			partnerDto.setZone(activitiesDto.getZone());
			if (!partnerZoneLst.contains(activitiesDto.getPartner() + "|" + activitiesDto.getZone())) {
				actLst.add(partnerDto);
				partnerZoneLst.add(partnerDto.getPartnerAndZone());
			}
		}
		
		ActivitiesResponse activitiesResponse = null;
		for (PartnerDto partner : actLst) {
			activitiesResponse = new ActivitiesResponse();
			activitiesResponse.setPartner(partner.getPartner());
			activitiesResponse.setZone(partner.getZone());
			
			// Check and set approved flag and response for front-end SR16172 - @author ntr.bang - create date 20/4/2024
			if (approvedZoneLst.contains(partner.getZone())) {
				activitiesResponse.setIsApproved(1);
			}
			
			// Filter BU list
			List<ActivitiesDto> lstDataF = lstData.stream()
				    .filter(x -> x.getPartner().equalsIgnoreCase(partner.getPartner()) && x.getZone().equalsIgnoreCase(partner.getZone()))
				    .collect(Collectors.toList());
			
			// Process and get information of BU from BU list of PARTNER
			List<BuDto> buLst = new ArrayList<>();
			BuDto buDto = null;
			List<String> buFilterLst = new ArrayList<>();
			for (ActivitiesDto bu : lstDataF) {
				// Only filter with BU is not existing in buFilterLst
				if(buFilterLst.contains(bu.getBu())) {
					continue;
				}
				buDto = new BuDto();
				buDto.setBu(bu.getBu());
				
				// Filter all activities of a BU
				List<ActivitiesDto> buDataF = lstData.stream()
					    .filter(x -> x.getPartner().equalsIgnoreCase(partner.getPartner())
					    		&& x.getZone().equalsIgnoreCase(partner.getZone())
					    		&& x.getBu().equalsIgnoreCase(bu.getBu())).collect(Collectors.toList());
				
				// Set activities of a BU to DTO
				buDto.setActivityLst(buDataF);
				
				// Add to BU list
				buLst.add(buDto);
				buFilterLst.add(bu.getBu());
			}
			activitiesResponse.setBuLst(buLst);
			actResLst.add(activitiesResponse);
		}
		return actResLst;
	}

	/**
	 * Left pad the format A000000001
	 * @param value
	 * @return
	 */
	public String leftPad(String value) {
		return "A" + StringUtils.leftPad(value, 9, "0");
	}
	
	/**
	 * Convert String to date
	 * @param dateVal
	 * @return
	 */
	public Date convertToDate(String dateVal, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try{
        	date = sdf.parse(dateVal);
        } catch (ParseException e){
        	date = null;
        }
        return date;
	}
}

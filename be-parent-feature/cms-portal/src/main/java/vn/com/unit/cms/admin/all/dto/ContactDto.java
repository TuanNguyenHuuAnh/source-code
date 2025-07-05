package vn.com.unit.cms.admin.all.dto;

import java.util.List;

public class ContactDto {
	List<RoomClientDto> lstContact;

	/**
	 * @return the lstContact
	 * @author taitm
	 */
	public List<RoomClientDto> getLstContact() {
		return lstContact;
	}

	/**
	 * @param lstContact the lstContact to set
	 * @author taitm
	 */
	public void setLstContact(List<RoomClientDto> lstContact) {
		this.lstContact = lstContact;
	}

}

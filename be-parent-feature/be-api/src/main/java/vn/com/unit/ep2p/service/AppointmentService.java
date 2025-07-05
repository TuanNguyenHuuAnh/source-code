package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import com.google.zxing.WriterException;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.appointment.dto.AppointmentDto;
import vn.com.unit.ep2p.admin.dto.CustomerAppointmentDto;
import vn.com.unit.dts.exception.DetailException;

public interface AppointmentService {
	public AppointmentDto addAppointment(AppointmentDto dto)
			throws WriterException, IOException, SQLException, DetailException;
	CmsCommonPagination<CustomerAppointmentDto> getListAppointment(Date appointmentDate, Integer page, Integer pageSize, Integer appointmentId);
}
